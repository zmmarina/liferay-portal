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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useMemo} from 'react';

import Cell from './Cell';
import TableContext from './TableContext';

function Row({children, className, paddingLeftCells}) {
	const {columnsDefinitions, columnsNames, isFixed} = useContext(
		TableContext
	);

	const marginLeft = useMemo(() => {
		let margin = 0;

		if (isFixed) {
			for (let i = 0; i < paddingLeftCells; i++) {
				margin += columnsDefinitions.get(columnsNames[i]).width;
			}
		}

		return margin;
	}, [columnsDefinitions, columnsNames, isFixed, paddingLeftCells]);

	const style = marginLeft
		? {
				marginLeft,
				minWidth: `calc(100% - ${marginLeft}px)`,
		  }
		: {};

	let counter = 0;
	const placeholderPaddingCells = !isFixed
		? new Array(paddingLeftCells).fill(<Cell key={counter++} />)
		: null;

	return (
		<div className={classNames('dnd-tr', className)} style={style}>
			{placeholderPaddingCells}
			{children}
		</div>
	);
}

Row.defaultProps = {
	paddingLeftCells: 0,
};

Row.propTypes = {
	paddingLeftCells: PropTypes.number,
};

export default Row;
