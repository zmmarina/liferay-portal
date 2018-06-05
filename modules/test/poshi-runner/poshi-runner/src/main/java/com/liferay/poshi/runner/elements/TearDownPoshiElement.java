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

package com.liferay.poshi.runner.elements;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class TearDownPoshiElement extends CommandPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new TearDownPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (_isElementType(poshiScript)) {
			return new TearDownPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	protected TearDownPoshiElement() {
	}

	protected TearDownPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected TearDownPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected TearDownPoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected TearDownPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	@Override
	protected String getBlockName() {
		return "tearDown";
	}

	private boolean _isElementType(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (!isBalancedPoshiScript(poshiScript)) {
			return false;
		}

		if (!poshiScript.endsWith("}")) {
			return false;
		}

		for (String line : poshiScript.split("\n")) {
			line = line.trim();

			if (line.startsWith("@")) {
				continue;
			}

			if (!line.equals("tearDown {")) {
				return false;
			}

			break;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "tear-down";

}