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
package org.apache.juneau.soap.annotation;

import static org.apache.juneau.soap.SoapXmlSerializer.*;
import org.apache.juneau.*;
import org.apache.juneau.reflect.*;
import org.apache.juneau.svl.*;

/**
 * Applies {@link SoapXmlConfig} annotations to a {@link PropertyStoreBuilder}.
 */
public class SoapXmlConfigApply extends ConfigApply<SoapXmlConfig> {

	/**
	 * Constructor.
	 *
	 * @param c The annotation class.
	 * @param r The resolver for resolving values in annotations.
	 */
	public SoapXmlConfigApply(Class<SoapXmlConfig> c, VarResolverSession r) {
		super(c, r);
	}

	@Override
	public void apply(AnnotationInfo<SoapXmlConfig> ai, PropertyStoreBuilder psb) {
		SoapXmlConfig a = ai.getAnnotation();
		if (! a.soapAction().isEmpty())
			psb.set(SOAPXML_SOAPAction, string(a.soapAction()));
	}
}
