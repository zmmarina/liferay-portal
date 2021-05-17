/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.internal.upgrade.v3_0_0;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.saml.persistence.internal.upgrade.v3_0_0.util.SamlSpSessionTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stian Sigvartsen
 */
public class SamlSpSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("samlSpSession", "samlPeerBindingId")) {
				alter(
					SamlSpSessionTable.class,
					new AlterTableAddColumn("samlPeerBindingId", "LONG null"));
			}

			int samlSpSessionIdOffset = 0;

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select min(samlSpSessionId) - 1 from SamlSpSession");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					samlSpSessionIdOffset = resultSet.getInt(1);
				}
			}

			int latestSamlPeerBindingId = _getLatestSamlPeerBindingId();

			runSQL(
				StringBundler.concat(
					"insert into SamlPeerBinding (samlPeerBindingId, ",
					"companyId, createDate, userId, userName, deleted, ",
					"samlNameIdFormat, samlNameIdNameQualifier, ",
					"samlNameIdSpProvidedId, samlNameIdValue, ",
					"samlPeerEntityId) select min(samlSpSessionId) + ",
					-samlSpSessionIdOffset + latestSamlPeerBindingId,
					", companyId, min(createDate), userId, userName, '0' as ",
					"deleted, nameIdFormat, nameIdNameQualifier, null as ",
					"nameIdSpProvidedId, nameIdValue, samlIdpEntityId from ",
					"SamlSpSession group by companyId, userId, userName, ",
					"samlIdpEntityId, nameIdFormat, nameIdNameQualifier, ",
					"nameIdSPNameQualifier, nameIdValue, samlIdpEntityId"));

			runSQL(
				StringBundler.concat(
					"update SamlSpSession sss set samlPeerBindingId = (",
					"select SamlPeerBindingId from SamlPeerBinding spb where ",
					"sss.companyId = spb.companyId and sss.userId = ",
					"spb.userId and sss.samlIdpEntityId = ",
					"spb.samlPeerEntityId and sss.nameIdFormat = ",
					"spb.samlNameIdFormat and sss.nameIdNameQualifier = ",
					"spb.samlNameIdNameQualifier and sss.nameIdValue = ",
					"spb.samlNameIdValue)"));

			CounterLocalServiceUtil.reset(
				"com.liferay.saml.persistence.model.SamlPeerBinding",
				_getLatestSamlPeerBindingId() + 1);
		}
	}

	private int _getLatestSamlPeerBindingId() throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select max(samlPeerBindingId) from SamlPeerBinding");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		return 0;
	}

}