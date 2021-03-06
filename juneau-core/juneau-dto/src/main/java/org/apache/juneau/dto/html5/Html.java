// ***************************************************************************************************************************
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file *
// * distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file        *
// * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance            *
// * with the License.  You may obtain a copy of the License at                                                              *
// *                                                                                                                         *
// *  http://www.apache.org/licenses/LICENSE-2.0                                                                             *
// *                                                                                                                         *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an  *
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the        *
// * specific language governing permissions and limitations under the License.                                              *
// ***************************************************************************************************************************
package org.apache.juneau.dto.html5;

import org.apache.juneau.annotation.*;

/**
 * DTO for an HTML {@doc HTML5.semantics#the-html-element <html>}
 * element.
 *
 * <h5 class='section'>See Also:</h5>
 * <ul class='doctree'>
 * 	<li class='link'>{@doc juneau-dto.HTML5}
 * </ul>
 */
@Bean(typeName="html")
public class Html extends HtmlElementContainer {

	/**
	 * {@doc HTML5.semantics#attr-html-manifest manifest} attribute.
	 *
	 * <p>
	 * Application cache manifest.
	 *
	 * @param manifest The new value for this attribute.
	 * @return This object (for method chaining).
	 */
	public final Html manifest(String manifest) {
		attr("manifest", manifest);
		return this;
	}


	//-----------------------------------------------------------------------------------------------------------------
	// Overridden methods
	//-----------------------------------------------------------------------------------------------------------------

	@Override /* HtmlElement */
	public final Html _class(String _class) {
		super._class(_class);
		return this;
	}

	@Override /* HtmlElement */
	public final Html id(String id) {
		super.id(id);
		return this;
	}

	@Override /* HtmlElement */
	public final Html style(String style) {
		super.style(style);
		return this;
	}

	@Override /* HtmlElementContainer */
	public final Html children(Object...children) {
		super.children(children);
		return this;
	}

	@Override /* HtmlElementContainer */
	public final Html child(Object child) {
		super.child(child);
		return this;
	}
}
