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

package com.liferay.account.service.persistence;

import com.liferay.account.exception.NoSuchGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account group account entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface AccountGroupRelPersistence
	extends BasePersistence<AccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountGroupRelUtil} to access the account group account entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId);

	/**
	 * Returns a range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel findByAccountGroupId_First(
			long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAccountGroupId_First(
		long accountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel findByAccountGroupId_Last(
			long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAccountGroupId_Last(
		long accountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param AccountGroupRelId the primary key of the current account group account entry rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public AccountGroupRel[] findByAccountGroupId_PrevAndNext(
			long AccountGroupRelId, long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Removes all the account group account entry rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	public void removeByAccountGroupId(long accountGroupId);

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group account entry rels
	 */
	public int countByAccountGroupId(long accountGroupId);

	/**
	 * Returns all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountEntryId(
		long accountEntryId);

	/**
	 * Returns a range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountEntryId(
		long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param AccountGroupRelId the primary key of the current account group account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public AccountGroupRel[] findByAccountEntryId_PrevAndNext(
			long AccountGroupRelId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountGroupRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Removes all the account group account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel findByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId);

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public AccountGroupRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId, boolean useFinderCache);

	/**
	 * Removes the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the account group account entry rel that was removed
	 */
	public AccountGroupRel removeByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63; and accountEntryId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	public int countByAGI_AEI(long accountGroupId, long accountEntryId);

	/**
	 * Caches the account group account entry rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupRel the account group account entry rel
	 */
	public void cacheResult(
		AccountGroupRel accountGroupRel);

	/**
	 * Caches the account group account entry rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupRels the account group account entry rels
	 */
	public void cacheResult(
		java.util.List<AccountGroupRel>
			accountGroupRels);

	/**
	 * Creates a new account group account entry rel with the primary key. Does not add the account group account entry rel to the database.
	 *
	 * @param AccountGroupRelId the primary key for the new account group account entry rel
	 * @return the new account group account entry rel
	 */
	public AccountGroupRel create(
		long AccountGroupRelId);

	/**
	 * Removes the account group account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param AccountGroupRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel that was removed
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public AccountGroupRel remove(
			long AccountGroupRelId)
		throws NoSuchGroupAccountEntryRelException;

	public AccountGroupRel updateImpl(
		AccountGroupRel accountGroupRel);

	/**
	 * Returns the account group account entry rel with the primary key or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param AccountGroupRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public AccountGroupRel findByPrimaryKey(
			long AccountGroupRelId)
		throws NoSuchGroupAccountEntryRelException;

	/**
	 * Returns the account group account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param AccountGroupRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel, or <code>null</code> if a account group account entry rel with the primary key could not be found
	 */
	public AccountGroupRel fetchByPrimaryKey(
		long AccountGroupRelId);

	/**
	 * Returns all the account group account entry rels.
	 *
	 * @return the account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findAll();

	/**
	 * Returns a range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account group account entry rels
	 */
	public java.util.List<AccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account group account entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account group account entry rels.
	 *
	 * @return the number of account group account entry rels
	 */
	public int countAll();

}