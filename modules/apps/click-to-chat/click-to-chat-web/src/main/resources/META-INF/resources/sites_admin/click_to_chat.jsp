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

<%@ include file="/init.jsp" %>

<%
boolean clickToChatEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED));

boolean disabled = false;

ClickToChatConfiguration clickToChatConfiguration = (ClickToChatConfiguration)request.getAttribute(ClickToChatConfiguration.class.getName());

if (Objects.equals(clickToChatConfiguration.siteSettingsStrategy(), "always-inherit") && !clickToChatConfiguration.enabled()) {
	disabled = true;
}
%>

<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatEnabled--" type="toggle-switch" value="<%= clickToChatEnabled %>" />

<%
String clickToChatProviderId = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ID));
%>

<aui:select disabled="<%= disabled %>" label="chat-provider" name="TypeSettingsProperties--clickToChatProviderId--" value="<%= clickToChatProviderId %>">
	<aui:option label="" value="" />

	<%
	for (String curClickToChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_PROVIDER_IDS) {
	%>

		<aui:option label='<%= "chat-provider-" + curClickToChatProviderId %>' value="<%= curClickToChatProviderId %>" />

	<%
	}
	%>

</aui:select>

<aui:input disabled="<%= disabled %>" label="chat-provider-account-id" name="TypeSettingsProperties--clickToChatProviderAccountId--" type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ACCOUNT_ID)) %>" />

<c:if test="<%= Validator.isNotNull(clickToChatProviderId) %>">
	<liferay-learn:message
		key='<%= "chat-provider-account-id-help-" + clickToChatProviderId %>'
		resource="click-to-chat-web"
	/>
</c:if>

<%
boolean clickToChatGuestUsersAllowed = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED));
%>

<aui:input checked="<%= clickToChatGuestUsersAllowed %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatGuestUsersAllowed--" type="toggle-switch" value="<%= clickToChatGuestUsersAllowed %>" />