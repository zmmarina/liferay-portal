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

import React, {useContext} from 'react';

import LabelField from './LabelField.es';
import RepeatableField from './RepeatableField.es';
import RequiredField from './RequiredField.es';
import {getDataDefinitionField, getFormattedState} from './shared/utils.es';

const withFormattedState = (Component) => {
	return ({AppContext, field, ...props}) => {
		const [state, dispatch] = useContext(AppContext);

		if (!getDataDefinitionField(state)) {
			return null;
		}

		return (
			<div className="ddm-field" data-field-name={field.fieldName}>
				<Component
					{...props}
					dispatch={dispatch}
					field={field}
					state={getFormattedState(state)}
				/>
			</div>
		);
	};
};

export default {
	label: withFormattedState(LabelField),
	repeatable: RepeatableField,
	required: withFormattedState(RequiredField),
};
