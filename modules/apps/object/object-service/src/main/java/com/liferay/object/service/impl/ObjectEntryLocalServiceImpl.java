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

package com.liferay.object.service.impl;

import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.base.ObjectEntryLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.jdbc.postgresql.PostgreSQLJDBCUtil;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectEntry",
	service = AopService.class
)
public class ObjectEntryLocalServiceImpl
	extends ObjectEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectEntry addObjectEntry(
			long userId, long groupId, long objectDefinitionId,
			Map<String, Serializable> values)
		throws PortalException {

		long objectEntryId = counterLocalService.increment();

		_insertIntoTable(
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId),
			objectEntryId, values);

		ObjectEntry objectEntry = objectEntryPersistence.create(objectEntryId);

		objectEntry.setGroupId(groupId);

		User user = _userLocalService.getUser(userId);

		objectEntry.setCompanyId(user.getCompanyId());
		objectEntry.setUserId(user.getUserId());
		objectEntry.setUserName(user.getFullName());

		objectEntry.setObjectDefinitionId(objectDefinitionId);
		objectEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		objectEntry.setStatusByUserId(user.getUserId());
		objectEntry.setStatusDate(new Date());

		return objectEntryPersistence.update(objectEntry);
	}

	@Override
	public ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectEntry deleteObjectEntry(ObjectEntry objectEntry)
		throws PortalException {

		objectEntry = objectEntryPersistence.remove(objectEntry);

		_deleteFromTable(objectEntry);

		return objectEntry;
	}

	@Override
	public List<ObjectEntry> getObjectEntries(
			long objectDefinitionId, int start, int end)
		throws PortalException {

		return objectEntryPersistence.findByObjectDefinitionId(
			objectDefinitionId, start, end);
	}

	@Override
	public int getObjectEntriesCount(long objectDefinitionId) {
		return objectEntryPersistence.countByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public Map<String, Serializable> getValues(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		return getValues(objectEntry);
	}

	@Override
	public Map<String, Serializable> getValues(ObjectEntry objectEntry)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId());

		Session session = objectEntryPersistence.openSession();

		try {
			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.selectDistinct(
					dynamicObjectDefinitionTable.getSelectExpressions()
				).from(
					dynamicObjectDefinitionTable
				).where(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn(
					).eq(
						objectEntry.getObjectEntryId()
					)
				));

			List<Object[]> rows = (List<Object[]>)QueryUtil.list(
				sqlQuery, objectEntryPersistence.getDialect(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			if (ListUtil.isEmpty(rows)) {
				throw new ObjectEntryValuesException(
					"No values exist for object entry " +
						objectEntry.getObjectEntryId());
			}

			return _getValues(dynamicObjectDefinitionTable, rows.get(0));
		}
		finally {
			objectEntryPersistence.closeSession(session);
		}
	}

	@Override
	public List<Map<String, Serializable>> getValuesList(
			long objectDefinitionId, int[] statuses, int start, int end)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId);

		Session session = objectEntryPersistence.openSession();

		try {
			Predicate predicate =
				ObjectEntryTable.INSTANCE.objectDefinitionId.eq(
					objectDefinitionId);

			if (!ArrayUtil.isEmpty(statuses)) {
				predicate = predicate.and(
					ObjectEntryTable.INSTANCE.status.in(
						ArrayUtil.toArray(statuses)));
			}

			if (PermissionThreadLocal.getPermissionChecker() != null) {
				predicate.and(
					_inlineSQLHelper.getPermissionWherePredicate(
						dynamicObjectDefinitionTable.getName(),
						dynamicObjectDefinitionTable.getPrimaryKeyColumn()));
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.selectDistinct(
					dynamicObjectDefinitionTable.getSelectExpressions()
				).from(
					dynamicObjectDefinitionTable
				).innerJoinON(
					ObjectEntryTable.INSTANCE,
					ObjectEntryTable.INSTANCE.objectEntryId.eq(
						dynamicObjectDefinitionTable.getPrimaryKeyColumn())
				).where(
					predicate
				));

			List<Object[]> rows = (List<Object[]>)QueryUtil.list(
				sqlQuery, objectEntryPersistence.getDialect(), start, end);

			List<Map<String, Serializable>> valuesList = new ArrayList<>(
				rows.size());

			for (Object[] objects : rows) {
				Map<String, Serializable> values = _getValues(
					dynamicObjectDefinitionTable, objects);

				valuesList.add(values);
			}

			return valuesList;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			objectEntryPersistence.closeSession(session);
		}
	}

	@Override
	public BaseModelSearchResult<ObjectEntry> searchObjectEntries(
			long objectDefinitionId, String keywords, int cur, int delta)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			ObjectEntry.class.getName()
		).emptySearchEnabled(
			true
		).from(
			cur
		).size(
			delta
		).withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"objectDefinitionId",
					objectDefinition.getObjectDefinitionId());
				searchContext.setCompanyId(objectDefinition.getCompanyId());
				searchContext.setKeywords(keywords);
			}
		);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		SearchHits searchHits = searchResponse.getSearchHits();

		List<ObjectEntry> objectEntries = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long objectEntryId = document.getLong(Field.ENTRY_CLASS_PK);

				return objectEntryPersistence.fetchByPrimaryKey(objectEntryId);
			});

		return new BaseModelSearchResult<>(
			objectEntries, searchResponse.getTotalHits());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectEntry updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		_updateTable(
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId()),
			objectEntryId, values);

		objectEntry.setModifiedDate(new Date());

		return objectEntryPersistence.update(objectEntry);
	}

	private void _deleteFromTable(ObjectEntry objectEntry)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		runSQL(
			StringBundler.concat(
				"delete from ", objectDefinition.getDBTableName(), " where ",
				objectDefinition.getDBPrimaryKeyColumnName(), " = ",
				objectEntry.getObjectEntryId()));
	}

	private DynamicObjectDefinitionTable _getDynamicObjectDefinitionTable(
			long objectDefinitionId)
		throws PortalException {

		// TODO Cache this across the cluster with proper invalidation when the
		// object definition or its object fields are updated

		return new DynamicObjectDefinitionTable(
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId),
			_objectFieldPersistence.findByObjectDefinitionId(
				objectDefinitionId));
	}

	private Map<String, Serializable> _getValues(
		DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
		Object[] objects) {

		Map<String, Serializable> values = new HashMap<>();

		Expression<?>[] selectExpressions =
			dynamicObjectDefinitionTable.getSelectExpressions();

		for (int i = 0; i < selectExpressions.length; i++) {
			Column<?, ?> column = (Column<?, ?>)selectExpressions[i];

			String columnName = column.getName();

			if (columnName.endsWith(StringPool.UNDERLINE)) {
				columnName = columnName.substring(0, columnName.length() - 1);
			}

			_putValue(column, columnName, objects[i], values);
		}

		return values;
	}

	private void _insertIntoTable(
			ObjectDefinition objectDefinition, long objectEntryId,
			Map<String, Serializable> values)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			objectEntryPersistence.getDataSource());

		StringBundler sb = new StringBundler();

		sb.append("insert into ");
		sb.append(objectDefinition.getDBTableName());
		sb.append(" (");
		sb.append(objectDefinition.getDBPrimaryKeyColumnName());

		int count = 1;

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectDefinition.getObjectDefinitionId());

		List<ObjectField> objectFields =
			dynamicObjectDefinitionTable.getObjectFields();

		for (ObjectField objectField : objectFields) {
			Object value = values.get(objectField.getName());

			if (value == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No value was provided for object field " +
							objectField.getName());
				}

				continue;
			}

			sb.append(", ");
			sb.append(objectField.getDBColumnName());

			count++;
		}

		if (count == 1) {
			throw new ObjectEntryValuesException(
				"No values were provided for object definition " +
					objectDefinition.getObjectDefinitionId());
		}

		sb.append(") values (?");

		for (int i = 1; i < count; i++) {
			sb.append(", ?");
		}

		sb.append(")");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			int index = 1;

			_setColumn(preparedStatement, index++, Types.BIGINT, objectEntryId);

			for (ObjectField objectField : objectFields) {
				Object value = values.get(objectField.getName());

				if (value == null) {
					continue;
				}

				Column<?, ?> column = dynamicObjectDefinitionTable.getColumn(
					objectField.getDBColumnName());

				_setColumn(
					preparedStatement, index++, column.getSQLType(), value);
			}

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _putValue(
		Column<?, ?> column, String name, Object object,
		Map<String, Serializable> values) {

		Class<?> clazz = column.getJavaType();

		if (clazz == BigDecimal.class) {
			values.put(name, (BigDecimal)object);
		}
		else if (clazz == Blob.class) {
			byte[] bytes = null;

			if (object != null) {
				if (object instanceof Blob) {

					// Hypersonic

					Blob blob = (Blob)object;

					try {
						bytes = blob.getBytes(1, (int)blob.length());
					}
					catch (SQLException sqlException) {
						throw new SystemException(sqlException);
					}
				}
				else if (object instanceof byte[]) {

					// MySQL

					bytes = (byte[])object;
				}
				else {
					Class<?> objectClass = object.getClass();

					throw new IllegalArgumentException(
						"Unknown object class " + objectClass.getName());
				}
			}

			values.put(name, bytes);
		}
		else if (clazz == Boolean.class) {
			if (object == null) {
				object = Boolean.FALSE;
			}

			if (object instanceof Byte) {
				Byte byteObject = (Byte)object;

				if (byteObject.intValue() == 0) {
					object = Boolean.FALSE;
				}
				else {
					object = Boolean.TRUE;
				}
			}

			values.put(name, (Boolean)object);
		}
		else if (clazz == Date.class) {
			values.put(name, (Date)object);
		}
		else if (clazz == Double.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Double.valueOf(0D);
			}
			else if (!(number instanceof Double)) {
				number = number.doubleValue();
			}

			values.put(name, number);
		}
		else if (clazz == Integer.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Integer.valueOf(0);
			}
			else if (!(number instanceof Integer)) {
				number = number.intValue();
			}

			values.put(name, number);
		}
		else if (clazz == Long.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Long.valueOf(0L);
			}
			else if (!(number instanceof Long)) {
				number = number.longValue();
			}

			values.put(name, number);
		}
		else if (clazz == String.class) {
			values.put(name, (String)object);
		}
		else {
			throw new IllegalArgumentException(
				"Unknown class " + clazz.getName());
		}
	}

	/**
	 * @see com.liferay.portal.upgrade.util.Table#setColumn
	 */
	private void _setColumn(
			PreparedStatement preparedStatement, int index, int sqlType,
			Object value)
		throws Exception {

		if (sqlType == Types.BIGINT) {
			preparedStatement.setLong(index, GetterUtil.getLong(value));
		}
		else if (sqlType == Types.BLOB) {
			if (PostgreSQLJDBCUtil.isPGStatement(preparedStatement)) {
				PostgreSQLJDBCUtil.setLargeObject(
					preparedStatement, index, (byte[])value);
			}
			else {
				preparedStatement.setBytes(index, (byte[])value);
			}
		}
		else if (sqlType == Types.BOOLEAN) {
			preparedStatement.setBoolean(index, GetterUtil.getBoolean(value));
		}
		else if (sqlType == Types.DATE) {
			Date date = (Date)value;

			preparedStatement.setTimestamp(
				index, new Timestamp(date.getTime()));
		}
		else if (sqlType == Types.DECIMAL) {
			preparedStatement.setBigDecimal(
				index, (BigDecimal)GetterUtil.get(value, BigDecimal.ZERO));
		}
		else if (sqlType == Types.DOUBLE) {
			preparedStatement.setDouble(index, GetterUtil.getDouble(value));
		}
		else if (sqlType == Types.INTEGER) {
			preparedStatement.setInt(index, GetterUtil.getInteger(value));
		}
		else if (sqlType == Types.VARCHAR) {
			preparedStatement.setString(index, String.valueOf(value));
		}
		else {
			throw new IllegalArgumentException("Unknown SQL type " + sqlType);
		}
	}

	private void _updateTable(
			ObjectDefinition objectDefinition, long objectEntryId,
			Map<String, Serializable> values)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			objectEntryPersistence.getDataSource());

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(objectDefinition.getDBTableName());
		sb.append(" set ");

		int count = 0;

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectDefinition.getObjectDefinitionId());

		List<ObjectField> objectFields =
			dynamicObjectDefinitionTable.getObjectFields();

		for (ObjectField objectField : objectFields) {
			Object value = values.get(objectField.getName());

			if (value == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No value was provided for object field " +
							objectField.getName());
				}

				continue;
			}

			if (count > 0) {
				sb.append(", ");
			}

			sb.append(objectField.getDBColumnName());
			sb.append(" = ?");

			count++;
		}

		if (count == 0) {
			throw new ObjectEntryValuesException(
				"No values were provided for object definition " +
					objectDefinition.getObjectDefinitionId());
		}

		sb.append(" where ");
		sb.append(objectDefinition.getDBPrimaryKeyColumnName());
		sb.append(" = ?");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			int index = 1;

			for (ObjectField objectField : objectFields) {
				Object value = values.get(objectField.getName());

				if (value == null) {
					continue;
				}

				Column<?, ?> column = dynamicObjectDefinitionTable.getColumn(
					objectField.getDBColumnName());

				_setColumn(
					preparedStatement, index++, column.getSQLType(), value);
			}

			_setColumn(preparedStatement, index++, Types.BIGINT, objectEntryId);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLocalServiceImpl.class);

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private UserLocalService _userLocalService;

}