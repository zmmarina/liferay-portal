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

package com.liferay.portal.crypto.hash.internal;

import com.liferay.portal.crypto.hash.CryptoHasher;
import com.liferay.portal.crypto.hash.generation.CryptoHashGenerationResponse;
import com.liferay.portal.crypto.hash.verification.CryptoHashVerificationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHasher.class)
public class CryptoHasherImpl implements CryptoHasher {

	@Override
	public CryptoHashGenerationResponse generate(byte[] input)
		throws Exception {

		byte[] pepper = null;
		String pepperId = null;

		CryptoHashProviderResponse cryptoHashProviderResponse =
			_cryptoHashProvider.generate(
				pepper, _cryptoHashProvider.generateSalt(), input);

		return new CryptoHashGenerationResponse(
			cryptoHashProviderResponse.getHash(),
			new CryptoHashVerificationContext(
				pepperId, cryptoHashProviderResponse.getSalt(),
				cryptoHashProviderResponse.getCryptoHashProviderName(),
				cryptoHashProviderResponse.getCryptoHashProviderProperties()));
	}

	@Override
	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws Exception {

		for (CryptoHashVerificationContext cryptoHashVerificationContext :
				cryptoHashVerificationContexts) {

			CryptoHashProvider cryptoHashProvider = _getCryptoHashProvider(
				cryptoHashVerificationContext.getCryptoHashProviderName(),
				cryptoHashVerificationContext.
					getCryptoHashProviderProperties());

			// process pepper

			byte[] pepper = null;

			// process salt

			Optional<byte[]> optionalSalt =
				cryptoHashVerificationContext.getSaltOptional();

			CryptoHashProviderResponse hashProviderResponse =
				cryptoHashProvider.generate(
					pepper, optionalSalt.orElse(null), input);

			input = hashProviderResponse.getHash();
		}

		return Arrays.equals(input, hash);
	}

	private CryptoHashProvider _cryptoHashProvider;

}