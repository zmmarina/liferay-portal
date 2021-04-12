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

import {
	isValidEvent,
	validateEmptyString,
	validateMaxLength,
} from '../../src/utils/validators';

describe('isValidEvent()', () => {
	const originalError = console.error;

	beforeAll(() => {
		console.error = jest.fn();
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		console.error = originalError;
	});

	it('returns if an event is valid', () => {
		const event = {
			eventId: 'Small Event Name',
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(true);
		expect(console.error).not.toBeCalled();
	});

	it('returns false if an event id has more than 255 chars', () => {
		const event = {
			eventId: new Array(256).fill('a').join(''),
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event id is empty', () => {
		const event = {
			eventId: '',
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop key has more than 255 chars', () => {
		const event = {
			eventId: 'Small Event Name',
			eventProps: {
				[new Array(256).fill('a').join('')]: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop key is empty', () => {
		const event = {
			eventId: 'Small Event Name',
			eventProps: {
				[new Array(256).fill('a').join('')]: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop value has more than 1024 chars', () => {
		const event = {
			eventId: 'Small Event Name',
			eventProps: {
				someKey: new Array(1025).fill('a').join(''),
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('show all errors in console', () => {
		const event = {
			eventId: '',
			eventProps: {
				someKey: new Array(1025).fill('a').join(''),
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalledTimes(2);
	});
});

describe('validateEmptyString()', () => {
	it('returns an empty string when string is not empty', () => {
		const errorMsg = validateEmptyString('testLabel')('Value');

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when string is empty', () => {
		const errorMsg = validateEmptyString('testLabel')('');

		expect(errorMsg).toBeTruthy();
	});
});

describe('validateMaxLength()', () => {
	it('returns an empty string when string is not grater than limit', () => {
		const errorMsg = validateMaxLength(5)('Value');

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when string is empty', () => {
		const errorMsg = validateMaxLength(5)('Value1');

		expect(errorMsg).toBeTruthy();
	});
});
