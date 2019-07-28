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

package com.liferay.layout.util.template;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Eudaldo Alonso
 */
public class LayoutRow {

	public LayoutRow(Layout layout) {
		_layout = layout;
	}

	public void addLayoutColumn(Consumer<LayoutColumn> consumer) {
		LayoutColumn layoutColumn = new LayoutColumn(_layout);

		try {
			consumer.accept(layoutColumn);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_layoutColumns.add(layoutColumn);
	}

	public void addLayoutColumn(Consumer<LayoutColumn>... consumers) {
		for (Consumer<LayoutColumn> consumer : consumers) {
			addLayoutColumn(consumer);
		}
	}

	@Override
	public String toString() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			for (LayoutColumn layoutColumn : _layoutColumns) {
				jsonArray.put(
					JSONFactoryUtil.createJSONObject(layoutColumn.toString()));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		JSONObject jsonObject = JSONUtil.put(
			"columns", jsonArray
		).put(
			"rowId",
			String.valueOf(
				CounterLocalServiceUtil.increment(LayoutRow.class.getName()))
		).put(
			"type", String.valueOf(FragmentConstants.TYPE_COMPONENT)
		);

		return jsonObject.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutRow.class);

	private final Layout _layout;
	private final List<LayoutColumn> _layoutColumns = new ArrayList<>();

}