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
package org.apache.juneau.rest.response;

import org.apache.juneau.http.annotation.*;

/**
 * Represents an <code>HTTP 208 Already Reported</code> response.
 *
 * <p>
 * The members of a DAV binding have already been enumerated in a preceding part of the (multistatus) response, and are not being included again.
 */
@Response(code=208, example="'Already Reported'")
public class AlreadyReported extends HttpResponse {

	/** Reusable instance. */
	public static final AlreadyReported INSTANCE = new AlreadyReported();

	/**
	 * Constructor using HTTP-standard message.
	 */
	public AlreadyReported() {
		this("Already Reported");
	}

	/**
	 * Constructor using custom message.
	 * @param message Message to send as the response.
	 */
	public AlreadyReported(String message) {
		super(message);
	}
}