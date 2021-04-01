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

package com.liferay.learn.taglib.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTag extends IncludeTag {

	public String getKey() {
		return _key;
	}

	public String getResource() {
		return _resource;
	}

	@Override
	public int processEndTag() throws Exception {
		JSONObject jsonObject = (JSONObject)WebCachePoolUtil.get(
			MessageTag.class.getName() + StringPool.POUND + _resource,
			new JSONObjectWebCacheItem(_resource));

		if (jsonObject.length() == 0) {
			return EVAL_PAGE;
		}

		JSONObject keyJSONObject = jsonObject.getJSONObject(_key);

		if (keyJSONObject == null) {
			return EVAL_PAGE;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		JSONObject languageIdJSONObject = keyJSONObject.getJSONObject(
			languageId);

		if (languageIdJSONObject == null) {
			if (languageId.equals("en_US")) {
				return EVAL_PAGE;
			}

			languageIdJSONObject = keyJSONObject.getJSONObject("en_US");

			if (languageIdJSONObject == null) {
				return EVAL_PAGE;
			}
		}

		JspWriter jspWriter = pageContext.getOut();

		StringBundler sb = new StringBundler(5);

		sb.append("<a href=\"");
		sb.append(languageIdJSONObject.getString("url"));
		sb.append("\" target=\"_blank\">");
		sb.append(languageIdJSONObject.getString("message"));
		sb.append("</a>");

		jspWriter.write(sb.toString());

		return EVAL_PAGE;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setResource(String resource) {
		_resource = resource;
	}

	public class JSONObjectWebCacheItem implements WebCacheItem {

		public JSONObjectWebCacheItem(String resource) {
			_resource = resource;
		}

		@Override
		public JSONObject convert(String key) {
			try {
				String json = HttpUtil.URLtoString(
					"https://learn-resources.liferay.com/" + _resource +
						".json");

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

		private String _resource;

	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_key = null;
		_resource = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(MessageTag.class);

	private String _key;
	private String _resource;

}