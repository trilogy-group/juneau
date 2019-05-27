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
package org.apache.juneau.reflect;

import java.lang.annotation.*;

import org.apache.juneau.annotation.*;

/**
 * Specialized {@link AnnotationsMap} that accepts only <js>"*Config"</js> annotations.
 */
public class ConfigAnnotationsMap extends AnnotationsMap {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(AnnotationInfo<? extends Annotation> a) {
		Class<? extends Annotation> aa = a.getAnnotation().annotationType();
		return aa.getAnnotation(PropertyStoreApply.class) != null;
	}
}
