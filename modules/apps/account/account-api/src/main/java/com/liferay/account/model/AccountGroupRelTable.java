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

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AccountGroupRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRel
 * @generated
 */
public class AccountGroupRelTable
	extends BaseTable<AccountGroupRelTable> {

	public static final AccountGroupRelTable INSTANCE =
		new AccountGroupRelTable();

	public final Column<AccountGroupRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountGroupRelTable, Long>
		AccountGroupRelId = createColumn(
			"AccountGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AccountGroupRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> accountGroupId =
		createColumn(
			"accountGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> accountEntryId =
		createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountGroupRelTable() {
		super(
			"AccountGroupRel",
			AccountGroupRelTable::new);
	}

}