/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public final class DefaultBasicAuthSupplier
implements HttpAuthSupplier {
    private static final String ENCODE_BASIC_AUTH_WITH_ISO8859 = "encode.basicauth.with.iso8859";

    @Override
    public boolean requiresRequestCaching() {
        return false;
    }

    public static String getBasicAuthHeader(String userName, String passwd) {
        return DefaultBasicAuthSupplier.getBasicAuthHeader(userName, passwd, false);
    }

    public static String getBasicAuthHeader(String userName, String passwd, boolean useIso8859) {
        String userAndPass = userName + ":" + passwd;
        byte[] authBytes = useIso8859 ? userAndPass.getBytes(StandardCharsets.ISO_8859_1) : userAndPass.getBytes();
        return "Basic " + Base64Utility.encode(authBytes);
    }

    @Override
    public String getAuthorization(AuthorizationPolicy authPolicy, URI currentURI, Message message, String fullHeader) {
        if (authPolicy.getUserName() != null && authPolicy.getPassword() != null) {
            boolean encodeBasicAuthWithIso8859 = PropertyUtils.isTrue(message.getContextualProperty(ENCODE_BASIC_AUTH_WITH_ISO8859));
            return DefaultBasicAuthSupplier.getBasicAuthHeader(authPolicy.getUserName(), authPolicy.getPassword(), encodeBasicAuthWithIso8859);
        }
        return null;
    }
}

