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

import React, {useCallback, useMemo, useState} from 'react';

import Context from './TableContext';

function ContextProvider({children, columnsNames}) {
	const [tableWidth, updateTableWidth] = useState(null);
	const [columnsDefinitions, updateColumnsDefinitions] = useState(new Map());
	const [draggingColumnName, updateDraggingColumnName] = useState(null);
	const [draggingAllowed, updateDraggingAllowed] = useState(true);

	const registerColumn = (name, width, resizable) => {
		updateColumnsDefinitions((definitions) => {
			const updatedDefinitions = new Map(definitions);

			updatedDefinitions.set(name, {
				resizable,
				width,
			});

			return updatedDefinitions;
		});
	};

	const isFixed = useMemo(() => {
		const columnsAreAllRegistered = columnsNames.reduce(
			(registered, name) => {
				return registered && !!columnsDefinitions.get(name);
			},
			true
		);

		return columnsAreAllRegistered;
	}, [columnsDefinitions, columnsNames]);

	const resizeColumn = useCallback(
		(name, width) => {
			if (isFixed) {
				updateColumnsDefinitions((definitions) => {
					const resizedColumn = definitions.get(name);

					const isColumnReducing = resizedColumn.width > width;

					let totalColsWidth = 0;

					definitions.forEach((definition) => {
						totalColsWidth += definition.width;
					});

					const nextColumnName =
						columnsNames[columnsNames.indexOf(name) + 1];
					const nextColumn = definitions.get(nextColumnName);
					const columnsAreShorterThanContainer =
						totalColsWidth < tableWidth;

					if (
						(isColumnReducing &&
							columnsAreShorterThanContainer &&
							!nextColumn?.resizable) ||
						width < 40
					) {
						updateDraggingAllowed(false);

						return definitions;
					}

					updateDraggingAllowed(true);

					const newDefinitions = new Map(definitions);

					if (isColumnReducing && columnsAreShorterThanContainer) {
						newDefinitions.set(nextColumnName, {
							...nextColumn,
							width:
								nextColumn.width + resizedColumn.width - width,
						});
					}

					newDefinitions.set(name, {
						...resizedColumn,
						width,
					});

					return newDefinitions;
				});
			}
		},
		[columnsNames, tableWidth, isFixed]
	);

	return (
		<Context.Provider
			value={{
				columnsDefinitions,
				columnsNames,
				draggingAllowed,
				draggingColumnName,
				isFixed,
				registerColumn,
				resizeColumn,
				tableWidth,
				updateDraggingAllowed,
				updateDraggingColumnName,
				updateTableWidth,
			}}
		>
			{children}
		</Context.Provider>
	);
}

export default ContextProvider;
