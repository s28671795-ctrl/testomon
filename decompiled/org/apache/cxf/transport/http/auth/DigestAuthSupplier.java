/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public class DigestAuthSupplier
implements HttpAuthSupplier {
    private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    final MessageDigest md5Helper;
    Map<URI, DigestInfo> authInfo = new ConcurrentHashMap<URI, DigestInfo>();

    public DigestAuthSupplier() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        this.md5Helper = md;
    }

    @Override
    public boolean requiresRequestCaching() {
        return true;
    }

    @Override
    public String getAuthorization(AuthorizationPolicy authPolicy, URI currentURI, Message message, String fullHeader) {
        Map<String, String> map;
        if (authPolicy == null || authPolicy.getUserName() == null && authPolicy.getPassword() == null) {
            return null;
        }
        if (fullHeader == null) {
            DigestInfo di = this.authInfo.get(currentURI);
            if (di != null) {
                return di.generateAuth(DigestAuthSupplier.getAuthURI(currentURI), authPolicy.getUserName(), authPolicy.getPassword());
            }
            return null;
        }
        HttpAuthHeader authHeader = new HttpAuthHeader(fullHeader);
        if (authHeader.authTypeIsDigest() && ("auth".equals((map = authHeader.getParams()).get("qop")) || !map.containsKey("qop"))) {
            DigestInfo di = new DigestInfo();
            di.qop = map.get("qop");
            di.realm = map.get("realm");
            di.nonce = map.get("nonce");
            di.opaque = map.get("opaque");
            if (map.containsKey("algorithm")) {
                di.algorithm = map.get("algorithm");
            }
            if (map.containsKey("charset")) {
                di.charset = map.get("charset");
            }
            di.method = (String)message.get("org.apache.cxf.request.method");
            if (di.method == null) {
                di.method = "POST";
            }
            this.authInfo.put(currentURI, di);
            return di.generateAuth(DigestAuthSupplier.getAuthURI(currentURI), authPolicy.getUserName(), authPolicy.getPassword());
        }
        return null;
    }

    private static String getAuthURI(URI currentURI) {
        String authURI = currentURI.getPath();
        if (currentURI.getQuery() != null) {
            authURI = authURI + '?' + currentURI.getQuery();
        }
        return authURI;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String createCnonce() throws UnsupportedEncodingException {
        String cnonce = Long.toString(System.currentTimeMillis());
        byte[] bytes = cnonce.getBytes("US-ASCII");
        MessageDigest messageDigest = this.md5Helper;
        synchronized (messageDigest) {
            bytes = this.md5Helper.digest(bytes);
        }
        return DigestAuthSupplier.encode(bytes);
    }

    private static String encode(byte[] binaryData) {
        int n = binaryData.length;
        char[] buffer = new char[n * 2];
        for (int i = 0; i < n; ++i) {
            int low = binaryData[i] & 0xF;
            int high = (binaryData[i] & 0xF0) >> 4;
            buffer[i * 2] = HEXADECIMAL[high];
            buffer[i * 2 + 1] = HEXADECIMAL[low];
        }
        return new String(buffer);
    }

    class DigestInfo {
        String qop;
        String realm;
        String nonce;
        String opaque;
        int nc;
        String algorithm = "MD5";
        String charset = "ISO-8859-1";
        String method = "POST";

        DigestInfo() {
        }

        synchronized String generateAuth(String uri, String username, String password) {
            try {
                ++this.nc;
                String ncstring = String.format("%08d", this.nc);
                String cnonce = DigestAuthSupplier.this.createCnonce();
                String digAlg = this.algorithm;
                if ("MD5-sess".equalsIgnoreCase(digAlg)) {
                    digAlg = "MD5";
                }
                MessageDigest digester = MessageDigest.getInstance(digAlg);
                String a1 = username + ":" + this.realm + ":" + password;
                if ("MD5-sess".equalsIgnoreCase(this.algorithm)) {
                    String tmp2 = DigestAuthSupplier.encode(digester.digest(a1.getBytes(this.charset)));
                    a1 = tmp2 + ':' + this.nonce + ':' + cnonce;
                }
                String hasha1 = DigestAuthSupplier.encode(digester.digest(a1.getBytes(this.charset)));
                String a2 = this.method + ":" + uri;
                String hasha2 = DigestAuthSupplier.encode(digester.digest(a2.getBytes("US-ASCII")));
                String serverDigestValue = null;
                serverDigestValue = this.qop == null ? hasha1 + ":" + this.nonce + ":" + hasha2 : hasha1 + ":" + this.nonce + ":" + ncstring + ":" + cnonce + ":" + this.qop + ":" + hasha2;
                String response = DigestAuthSupplier.encode(digester.digest(serverDigestValue.getBytes("US-ASCII")));
                HashMap<String, String> outParams = new HashMap<String, String>();
                if (this.qop != null) {
                    outParams.put("qop", "auth");
                }
                outParams.put("realm", this.realm);
                outParams.put("opaque", this.opaque);
                outParams.put("nonce", this.nonce);
                outParams.put("uri", uri);
                outParams.put("username", username);
                outParams.put("nc", ncstring);
                outParams.put("cnonce", cnonce);
                outParams.put("response", response);
                outParams.put("algorithm", this.algorithm);
                return new HttpAuthHeader("Digest", outParams).getFullHeader();
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

