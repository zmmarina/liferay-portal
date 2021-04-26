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

package com.liferay.object.internal.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;

import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectDefinitionTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	public ObjectDefinitionTableClayDataSetDisplayView(
		ClayTableSchemaBuilderFactory clayTableSchemaBuilderFactory,
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		ClayTableSchemaBuilder clayTableSchemaBuilder =
			clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField clayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("id", "id");

		clayTableSchemaField.setContentRenderer("actionLink");

		for (ObjectField objectField : objectFields) {
			clayTableSchemaBuilder.addClayTableSchemaField(
				objectField.getDBColumnName(), objectField.getName());
		}

		_clayTableSchema = clayTableSchemaBuilder.build();
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		return _clayTableSchema;
	}

	private final ClayTableSchema _clayTableSchema;

}