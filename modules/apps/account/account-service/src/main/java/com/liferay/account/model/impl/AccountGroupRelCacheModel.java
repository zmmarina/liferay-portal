/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AccountGroupRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupRelCacheModel
	implements CacheModel<AccountGroupRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountGroupRelCacheModel)) {
			return false;
		}

		AccountGroupRelCacheModel accountGroupRelCacheModel =
			(AccountGroupRelCacheModel)object;

		if ((AccountGroupRelId ==
				accountGroupRelCacheModel.AccountGroupRelId) &&
			(mvccVersion == accountGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, AccountGroupRelId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", AccountGroupRelId=");
		sb.append(AccountGroupRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", accountGroupId=");
		sb.append(accountGroupId);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountGroupRel toEntityModel() {
		AccountGroupRelImpl accountGroupRelImpl = new AccountGroupRelImpl();

		accountGroupRelImpl.setMvccVersion(mvccVersion);
		accountGroupRelImpl.setAccountGroupRelId(AccountGroupRelId);
		accountGroupRelImpl.setCompanyId(companyId);
		accountGroupRelImpl.setAccountGroupId(accountGroupId);
		accountGroupRelImpl.setAccountEntryId(accountEntryId);

		accountGroupRelImpl.resetOriginalValues();

		return accountGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		AccountGroupRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		accountGroupId = objectInput.readLong();

		accountEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(AccountGroupRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(accountGroupId);

		objectOutput.writeLong(accountEntryId);
	}

	public long mvccVersion;
	public long AccountGroupRelId;
	public long companyId;
	public long accountGroupId;
	public long accountEntryId;

}