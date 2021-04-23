<%--
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
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
String[] parts = clickToChatChatProviderAccountId.split("/");
%>

<script async defer id="hs-script-loader" src="//js-na1.hs-scripts.com/<%= parts[0] %>.js" type="text/javascript"></script>

<c:if test="<%= themeDisplay.isSignedIn() && (parts.length > 1) %>">
	<script type="text/javascript">
		window.hsConversationsSettings = {
			identificationEmail: '<%= user.getEmailAddress() %>',
			identificationToken: '<%= _getHubSpotToken(parts[1], user) %>',
			loadImmediately: false,
		};

		window.HubSpotConversations.widget.load();
	</script>
</c:if>

<%!
private String _getHubSpotToken(String hubSpotApiKey, User user) throws Exception {
	Http.Options options = new Http.Options();

	options.setBody(
		JSONUtil.put(
			"email", user.getEmailAddress()
		).put(
			"firstName", user.getFirstName()
		).put(
			"lastName", user.getLastName()
		).toString(),
		ContentTypes.APPLICATION_JSON, StringPool.UTF8);
	options.setLocation("https://api.hubspot.com/conversations/v3/visitor-identification/tokens/create?hapikey=" + hubSpotApiKey);
	options.setPost(true);

	String json = HttpUtil.URLtoString(options);

	JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

	return jsonObject.getString("token");
}
%>