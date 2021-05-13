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

package com.liferay.digital.signature.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "digital-signature",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.digital.signature.internal.configuration.DigitalSignatureConfiguration",
	localization = "content/Language", name = "digital-signature-configuration"
)
public interface DigitalSignatureConfiguration {

	@Meta.AD(name = "enable")
	public boolean enable();

	@Meta.AD(name = "api-username")
	public String apiUsername();

	@Meta.AD(name = "api-account-id")
	public String apiAccountId();

	@Meta.AD(name = "account's-base-uri")
	public String accountBaseURI();

	@Meta.AD(name = "integration-key")
	public String integrationKey();

	@Meta.AD(name = "rsa-private-key")
	public String rsaPrivateKey();

}