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

package com.liferay.object.internal.graphql;

import com.liferay.object.graphql.ObjectDefinitionGraphQL;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class ObjectDefinitionGraphQLImpl implements ObjectDefinitionGraphQL {

	public ObjectDefinitionGraphQLImpl(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		_objectDefinition = objectDefinition;
		_objectFields = objectFields;

		_entityModel = new EntityModel() {

			@Override
			public Map<String, EntityField> getEntityFieldsMap() {
				Map<String, EntityField> entityFieldMap =
					EntityModel.toEntityFieldsMap(
						new DateTimeEntityField(
							"dateCreated",
							locale -> Field.getSortableFieldName(
								Field.CREATE_DATE),
							locale -> Field.CREATE_DATE),
						new DateTimeEntityField(
							"dateModified",
							locale -> Field.getSortableFieldName(
								Field.MODIFIED_DATE),
							locale -> Field.MODIFIED_DATE),
						new IntegerEntityField(
							"objectDefinitionId",
							locale -> "objectDefinitionId"),
						new IntegerEntityField(
							"siteId", locale -> Field.GROUP_ID),
						new IntegerEntityField(
							"userId", locale -> Field.USER_ID));

				for (ObjectField objectField : objectFields) {
					String type = objectField.getType();

					if (type.equals(String.class.getSimpleName())) {
						String name = objectField.getName();

						entityFieldMap.put(
							name, new StringEntityField(name, locale -> name));
					}
				}

				return entityFieldMap;
			}

			@Override
			public String getName() {
				return objectDefinition.getName();
			}

		};
	}

	@Override
	public EntityModel getEntityModel() {
		return _entityModel;
	}

	@Override
	public ObjectDefinition getObjectDefinition() {
		return _objectDefinition;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return _objectFields;
	}

	private final EntityModel _entityModel;
	private final ObjectDefinition _objectDefinition;
	private final List<ObjectField> _objectFields;

}