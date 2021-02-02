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

package com.liferay.account.service.persistence.test;

import com.liferay.account.exception.NoSuchGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.account.service.persistence.AccountGroupRelPersistence;
import com.liferay.account.service.persistence.AccountGroupRelUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AccountGroupRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountGroupRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountGroupRel> iterator =
			_accountGroupRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupRel accountGroupRel =
			_persistence.create(pk);

		Assert.assertNotNull(accountGroupRel);

		Assert.assertEquals(accountGroupRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		_persistence.remove(newAccountGroupRel);

		AccountGroupRel existingAccountGroupRel =
			_persistence.fetchByPrimaryKey(
				newAccountGroupRel.getPrimaryKey());

		Assert.assertNull(existingAccountGroupRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountGroupRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupRel newAccountGroupRel =
			_persistence.create(pk);

		newAccountGroupRel.setMvccVersion(
			RandomTestUtil.nextLong());

		newAccountGroupRel.setCompanyId(RandomTestUtil.nextLong());

		newAccountGroupRel.setAccountGroupId(
			RandomTestUtil.nextLong());

		newAccountGroupRel.setAccountEntryId(
			RandomTestUtil.nextLong());

		_accountGroupRels.add(
			_persistence.update(newAccountGroupRel));

		AccountGroupRel existingAccountGroupRel =
			_persistence.findByPrimaryKey(
				newAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupRel.getMvccVersion(),
			newAccountGroupRel.getMvccVersion());
		Assert.assertEquals(
			existingAccountGroupRel.
				getAccountGroupRelId(),
			newAccountGroupRel.getAccountGroupRelId());
		Assert.assertEquals(
			existingAccountGroupRel.getCompanyId(),
			newAccountGroupRel.getCompanyId());
		Assert.assertEquals(
			existingAccountGroupRel.getAccountGroupId(),
			newAccountGroupRel.getAccountGroupId());
		Assert.assertEquals(
			existingAccountGroupRel.getAccountEntryId(),
			newAccountGroupRel.getAccountEntryId());
	}

	@Test
	public void testCountByAccountGroupId() throws Exception {
		_persistence.countByAccountGroupId(RandomTestUtil.nextLong());

		_persistence.countByAccountGroupId(0L);
	}

	@Test
	public void testCountByAccountEntryId() throws Exception {
		_persistence.countByAccountEntryId(RandomTestUtil.nextLong());

		_persistence.countByAccountEntryId(0L);
	}

	@Test
	public void testCountByAGI_AEI() throws Exception {
		_persistence.countByAGI_AEI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByAGI_AEI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		AccountGroupRel existingAccountGroupRel =
			_persistence.findByPrimaryKey(
				newAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupRel,
			newAccountGroupRel);
	}

	@Test(expected = NoSuchGroupAccountEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AccountGroupRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AccountGroupRel", "mvccVersion", true,
			"AccountGroupRelId", true, "companyId", true,
			"accountGroupId", true, "accountEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		AccountGroupRel existingAccountGroupRel =
			_persistence.fetchByPrimaryKey(
				newAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupRel,
			newAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupRel missingAccountGroupRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountGroupRel newAccountGroupRel1 =
			addAccountGroupRel();
		AccountGroupRel newAccountGroupRel2 =
			addAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupRel1.getPrimaryKey());
		primaryKeys.add(newAccountGroupRel2.getPrimaryKey());

		Map<Serializable, AccountGroupRel>
			accountGroupRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, accountGroupRels.size());
		Assert.assertEquals(
			newAccountGroupRel1,
			accountGroupRels.get(
				newAccountGroupRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountGroupRel2,
			accountGroupRels.get(
				newAccountGroupRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountGroupRel>
			accountGroupRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(accountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountGroupRel>
			accountGroupRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, accountGroupRels.size());
		Assert.assertEquals(
			newAccountGroupRel,
			accountGroupRels.get(
				newAccountGroupRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountGroupRel>
			accountGroupRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(accountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupRel.getPrimaryKey());

		Map<Serializable, AccountGroupRel>
			accountGroupRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, accountGroupRels.size());
		Assert.assertEquals(
			newAccountGroupRel,
			accountGroupRels.get(
				newAccountGroupRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountGroupRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AccountGroupRel>() {

				@Override
				public void performAction(
					AccountGroupRel accountGroupRel) {

					Assert.assertNotNull(accountGroupRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupRelId",
				newAccountGroupRel.
					getAccountGroupRelId()));

		List<AccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountGroupRel existingAccountGroupRel =
			result.get(0);

		Assert.assertEquals(
			existingAccountGroupRel,
			newAccountGroupRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupRelId", RandomTestUtil.nextLong()));

		List<AccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("AccountGroupRelId"));

		Object newAccountGroupRelId =
			newAccountGroupRel.getAccountGroupRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"AccountGroupRelId",
				new Object[] {newAccountGroupRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountGroupRelId = result.get(0);

		Assert.assertEquals(
			existingAccountGroupRelId,
			newAccountGroupRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("AccountGroupRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"AccountGroupRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAccountGroupRel.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		AccountGroupRel newAccountGroupRel =
			addAccountGroupRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupRelId",
				newAccountGroupRel.
					getAccountGroupRelId()));

		List<AccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AccountGroupRel accountGroupRel) {

		Assert.assertEquals(
			Long.valueOf(accountGroupRel.getAccountGroupId()),
			ReflectionTestUtil.<Long>invoke(
				accountGroupRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "accountGroupId"));
		Assert.assertEquals(
			Long.valueOf(accountGroupRel.getAccountEntryId()),
			ReflectionTestUtil.<Long>invoke(
				accountGroupRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "accountEntryId"));
	}

	protected AccountGroupRel addAccountGroupRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AccountGroupRel accountGroupRel =
			_persistence.create(pk);

		accountGroupRel.setMvccVersion(RandomTestUtil.nextLong());

		accountGroupRel.setCompanyId(RandomTestUtil.nextLong());

		accountGroupRel.setAccountGroupId(
			RandomTestUtil.nextLong());

		accountGroupRel.setAccountEntryId(
			RandomTestUtil.nextLong());

		_accountGroupRels.add(
			_persistence.update(accountGroupRel));

		return accountGroupRel;
	}

	private List<AccountGroupRel> _accountGroupRels =
		new ArrayList<AccountGroupRel>();
	private AccountGroupRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}