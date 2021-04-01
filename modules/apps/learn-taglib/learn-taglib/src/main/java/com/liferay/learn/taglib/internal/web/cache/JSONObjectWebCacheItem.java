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

package com.liferay.learn.taglib.internal.web.cache;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONObjectWebCacheItem implements WebCacheItem {

	public static JSONObject get(String resource) {
		return (JSONObject)WebCachePoolUtil.get(
			JSONObjectWebCacheItem.class.getName() + StringPool.POUND +
				resource,
			new JSONObjectWebCacheItem(resource));
	}

	public JSONObjectWebCacheItem(String resource) {
		_resource = resource;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			String json = HttpUtil.URLtoString(
				"https://learn-resources.liferay.com/" + _resource + ".json");

			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.HOUR * 4;

	private static final Log _log = LogFactoryUtil.getLog(
		JSONObjectWebCacheItem.class);

	private final String _resource;

}