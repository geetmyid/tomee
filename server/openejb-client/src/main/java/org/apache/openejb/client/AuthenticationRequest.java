/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class AuthenticationRequest implements Request {

    private static final long serialVersionUID = 7009531340198948330L;
    private transient String realm;
    private transient String username;
    private transient String credentials;
    private transient ProtocolMetaData metaData;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(final String principal, final String credentials) {
        this(null, principal, credentials);
    }

    public AuthenticationRequest(final String realm, final String principal, final String credentials) {
        this.realm = realm;
        this.username = principal;
        this.credentials = credentials;
    }

    @Override
    public void setMetaData(final ProtocolMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.AUTH_REQUEST;
    }

    public String getRealm() {
        return realm;
    }

    public String getUsername() {
        return username;
    }

    public String getCredentials() {
        return credentials;
    }

    /**
     * Changes to this method must observe the optional {@link #metaData} version
     */
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final byte version = in.readByte(); // future use

        realm = (String) in.readObject();
        username = (String) in.readObject();
        credentials = (String) in.readObject();
    }

    /**
     * Changes to this method must observe the optional {@link #metaData} version
     */
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        // write out the version of the serialized data for future use
        out.writeByte(1);

        out.writeObject(realm);
        out.writeObject(username);
        out.writeObject(credentials);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder(50);
        sb.append(null != realm ? realm : "Unknown realm").append(':');
        sb.append(null != username ? username : "Unknown user").append(':');
        sb.append(null != credentials ? credentials : "Unknown credentials");
        return sb.toString();
    }
}

