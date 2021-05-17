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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.saml.persistence.internal.upgrade.v3_0_0.util.SamlIdpSpSessionTable;

/**
 * @author Stian Sigvartsen
 */
public class SamlIdpSpSessionUpgradeProcess2 extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alter(
				SamlIdpSpSessionTable.class,
				new AlterTableDropColumn("samlSpEntityId"),
				new AlterTableDropColumn("nameIdFormat"),
				new AlterTableDropColumn("nameIdValue"));
		}
	}

}