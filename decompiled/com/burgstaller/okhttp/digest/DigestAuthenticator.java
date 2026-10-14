/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest;

import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.fromhttpclient.BasicHeaderValueFormatter;
import com.burgstaller.okhttp.digest.fromhttpclient.BasicHeaderValueParser;
import com.burgstaller.okhttp.digest.fromhttpclient.BasicNameValuePair;
import com.burgstaller.okhttp.digest.fromhttpclient.CharArrayBuffer;
import com.burgstaller.okhttp.digest.fromhttpclient.HeaderElement;
import com.burgstaller.okhttp.digest.fromhttpclient.HttpEntityDigester;
import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;
import com.burgstaller.okhttp.digest.fromhttpclient.ParserCursor;
import com.burgstaller.okhttp.digest.fromhttpclient.UnsupportedDigestAlgorithmException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.platform.Platform;

public class DigestAuthenticator
implements CachingAuthenticator {
    public static final String PROXY_AUTH = "Proxy-Authenticate";
    public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
    public static final String WWW_AUTH = "WWW-Authenticate";
    public static final String WWW_AUTH_RESP = "Authorization";
    private static final String CREDENTIAL_CHARSET = "http.auth.credential-charset";
    private static final int QOP_UNKNOWN = -1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_AUTH = 2;
    private static final String TAG = "OkDigest";
    private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private AtomicReference<Map<String, String>> parametersRef = new AtomicReference();
    private Charset credentialsCharset = Charset.forName("ASCII");
    private final Credentials credentials;
    private String lastNonce;
    private long nounceCount;
    private String cnonce;
    private String a1;
    private String a2;
    private boolean proxy;

    public DigestAuthenticator(Credentials credentials) {
        this.credentials = credentials;
    }

    private static MessageDigest createMessageDigest(String digAlg) {
        try {
            return MessageDigest.getInstance(digAlg);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unsupported algorithm in HTTP Digest authentication: " + digAlg, e);
        }
    }

    public static String createCnonce() {
        SecureRandom rnd = new SecureRandom();
        byte[] tmp = new byte[8];
        rnd.nextBytes(tmp);
        return DigestAuthenticator.encode(tmp);
    }

    static String encode(byte[] binaryData) {
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

    protected void parseChallenge(String buffer, int pos, int len, Map<String, String> params) {
        BasicHeaderValueParser parser = BasicHeaderValueParser.INSTANCE;
        ParserCursor cursor = new ParserCursor(pos, buffer.length());
        CharArrayBuffer buf = new CharArrayBuffer(len);
        buf.append(buffer);
        HeaderElement[] elements = parser.parseElements(buf, cursor);
        if (elements.length == 0) {
            throw new IllegalArgumentException("Authentication challenge is empty");
        }
        for (HeaderElement element : elements) {
            params.put(element.getName(), element.getValue());
        }
    }

    @Override
    public synchronized Request authenticate(Route route, Response response) throws IOException {
        String header = this.findDigestHeader(response.headers(), this.getHeaderName(response.code()));
        HashMap<String, String> parameters = new HashMap<String, String>();
        this.parseChallenge(header, 7, header.length() - 7, parameters);
        this.copyHeaderMap(response.headers(), parameters);
        this.parametersRef.set(Collections.unmodifiableMap(parameters));
        if (parameters.get("nonce") == null) {
            throw new IllegalArgumentException("missing nonce in challenge header: " + header);
        }
        return this.authenticateWithState(route, response.request(), parameters);
    }

    private String getHeaderName(int httpStatus) {
        if (httpStatus == 401) {
            this.setProxy(false);
            return WWW_AUTH;
        }
        if (httpStatus == 407) {
            this.setProxy(true);
            return PROXY_AUTH;
        }
        return "";
    }

    private String findDigestHeader(Headers headers, String name) {
        List<String> authHeaders = headers.values(name);
        for (String header : authHeaders) {
            if (!header.startsWith("Digest")) continue;
            return header;
        }
        throw new IllegalArgumentException("unsupported auth scheme: " + authHeaders);
    }

    @Override
    public Request authenticateWithState(Route route, Request request) throws IOException {
        Map<String, String> ref = this.parametersRef.get();
        HashMap<String, String> parameters = ref == null ? new HashMap<String, String>() : new HashMap<String, String>(ref);
        return this.authenticateWithState(route, request, parameters);
    }

    private Request authenticateWithState(Route route, Request request, Map<String, String> parameters) throws IOException {
        String uri;
        String method2;
        String realm = parameters.get("realm");
        if (realm == null) {
            return null;
        }
        String nonce = parameters.get("nonce");
        if (nonce == null) {
            throw new IllegalArgumentException("missing nonce in challenge");
        }
        String stale = parameters.get("stale");
        boolean isStale = "true".equalsIgnoreCase(stale);
        if (this.havePreviousDigestAuthorizationAndShouldAbort(request, nonce, isStale)) {
            Platform.get().log(5, "previous digest authentication with same nonce failed, returning null", null);
            return null;
        }
        if (route == null || !route.requiresTunnel()) {
            method2 = request.method();
            uri = RequestLine.requestPath(request.url());
            parameters.put("methodname", method2);
            parameters.put("uri", uri);
        } else {
            method2 = "CONNECT";
            uri = request.url().host() + ':' + request.url().port();
            parameters.put("methodname", "CONNECT");
            parameters.put("uri", uri);
        }
        String charset = parameters.get("charset");
        if (charset == null) {
            String credentialsCharset = this.getCredentialsCharset(request);
            parameters.put("charset", credentialsCharset);
        }
        NameValuePair digestHeader = this.createDigestHeader(this.credentials, request, parameters);
        return request.newBuilder().header(digestHeader.getName(), digestHeader.getValue()).build();
    }

    private boolean havePreviousDigestAuthorizationAndShouldAbort(Request request, String nonce, boolean isStale) {
        String headerKey = this.isProxy() ? PROXY_AUTH_RESP : WWW_AUTH_RESP;
        String previousAuthorizationHeader = request.header(headerKey);
        if (previousAuthorizationHeader != null && previousAuthorizationHeader.startsWith("Digest")) {
            return !isStale;
        }
        return false;
    }

    private void copyHeaderMap(Headers headers, Map<String, String> dest) {
        for (int i = 0; i < headers.size(); ++i) {
            dest.put(headers.name(i), headers.value(i));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private synchronized NameValuePair createDigestHeader(Credentials credentials, Request request, Map<String, String> parameters) throws AuthenticationException {
        String digestValue;
        MessageDigest digester;
        String digAlg;
        String uri = parameters.get("uri");
        String realm = parameters.get("realm");
        String nonce = parameters.get("nonce");
        String opaque = parameters.get("opaque");
        String method2 = parameters.get("methodname");
        String algorithm = parameters.get("algorithm");
        if (algorithm == null) {
            algorithm = "MD5";
        }
        HashSet<String> qopset = new HashSet<String>(8);
        int qop = -1;
        String qoplist = parameters.get("qop");
        if (qoplist != null) {
            StringTokenizer tok = new StringTokenizer(qoplist, ",");
            while (tok.hasMoreTokens()) {
                String variant = tok.nextToken().trim();
                qopset.add(variant.toLowerCase(Locale.US));
            }
            if (request.body() != null && qopset.contains("auth-int")) {
                qop = 1;
            } else if (qopset.contains("auth")) {
                qop = 2;
            }
        } else {
            qop = 0;
        }
        if (qop == -1) {
            throw new AuthenticationException("None of the qop methods is supported: " + qoplist);
        }
        String charset = parameters.get("charset");
        if (charset == null) {
            charset = "ISO-8859-1";
        }
        if ("MD5-sess".equalsIgnoreCase(digAlg = algorithm)) {
            digAlg = "MD5";
        }
        try {
            digester = DigestAuthenticator.createMessageDigest(digAlg);
        }
        catch (UnsupportedDigestAlgorithmException ex) {
            throw new AuthenticationException("Unsuppported digest algorithm: " + digAlg, ex);
        }
        String uname = credentials.getUserName();
        String pwd = credentials.getPassword();
        if (nonce.equals(this.lastNonce)) {
            ++this.nounceCount;
        } else {
            this.nounceCount = 1L;
            this.cnonce = null;
            this.lastNonce = nonce;
        }
        StringBuilder sb = new StringBuilder(256);
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%08x", this.nounceCount);
        formatter.close();
        String nc = sb.toString();
        if (this.cnonce == null) {
            this.cnonce = DigestAuthenticator.createCnonce();
        }
        this.a1 = null;
        this.a2 = null;
        if ("MD5-sess".equalsIgnoreCase(algorithm)) {
            sb.setLength(0);
            sb.append(uname).append(':').append(realm).append(':').append(pwd);
            String checksum = DigestAuthenticator.encode(digester.digest(this.getBytes(sb.toString(), charset)));
            sb.setLength(0);
            sb.append(checksum).append(':').append(nonce).append(':').append(this.cnonce);
            this.a1 = sb.toString();
        } else {
            sb.setLength(0);
            sb.append(uname).append(':').append(realm).append(':').append(pwd);
            this.a1 = sb.toString();
        }
        String hasha1 = DigestAuthenticator.encode(digester.digest(this.getBytes(this.a1, charset)));
        if (qop == 2) {
            this.a2 = method2 + ':' + uri;
        } else if (qop == 1) {
            RequestBody entity = request.body();
            if (entity != null) {
                if (!qopset.contains("auth")) throw new AuthenticationException("Qop auth-int cannot be used with a non-repeatable entity");
                qop = 2;
                this.a2 = method2 + ':' + uri;
            } else {
                HttpEntityDigester entityDigester = new HttpEntityDigester(digester);
                try {
                    entityDigester.close();
                }
                catch (IOException ex) {
                    throw new AuthenticationException("I/O error reading entity content", ex);
                }
                this.a2 = method2 + ':' + uri + ':' + DigestAuthenticator.encode(entityDigester.getDigest());
            }
        } else {
            this.a2 = method2 + ':' + uri;
        }
        String hasha2 = DigestAuthenticator.encode(digester.digest(this.getBytes(this.a2, charset)));
        if (qop == 0) {
            sb.setLength(0);
            sb.append(hasha1).append(':').append(nonce).append(':').append(hasha2);
            digestValue = sb.toString();
        } else {
            sb.setLength(0);
            sb.append(hasha1).append(':').append(nonce).append(':').append(nc).append(':').append(this.cnonce).append(':').append(qop == 1 ? "auth-int" : "auth").append(':').append(hasha2);
            digestValue = sb.toString();
        }
        String digest = DigestAuthenticator.encode(digester.digest(DigestAuthenticator.getAsciiBytes(digestValue)));
        StringBuilder buffer = new StringBuilder(128);
        String headerKey = this.isProxy() ? PROXY_AUTH_RESP : WWW_AUTH_RESP;
        buffer.append("Digest ");
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(20);
        params.add(new BasicNameValuePair("username", uname));
        params.add(new BasicNameValuePair("realm", realm));
        params.add(new BasicNameValuePair("nonce", nonce));
        params.add(new BasicNameValuePair("uri", uri));
        params.add(new BasicNameValuePair("response", digest));
        if (qop != 0) {
            params.add(new BasicNameValuePair("qop", qop == 1 ? "auth-int" : "auth"));
            params.add(new BasicNameValuePair("nc", nc));
            params.add(new BasicNameValuePair("cnonce", this.cnonce));
        }
        params.add(new BasicNameValuePair("algorithm", algorithm));
        if (opaque != null) {
            params.add(new BasicNameValuePair("opaque", opaque));
        }
        for (int i = 0; i < params.size(); ++i) {
            String name;
            NameValuePair param2 = (NameValuePair)params.get(i);
            if (i > 0) {
                buffer.append(", ");
            }
            boolean noQuotes = "nc".equals(name = param2.getName()) || "qop".equals(name) || "algorithm".equals(name);
            BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(buffer, param2, !noQuotes);
        }
        return new BasicNameValuePair(headerKey, buffer.toString());
    }

    public Charset getCredentialsCharset() {
        return this.credentialsCharset;
    }

    String getCredentialsCharset(Request request) {
        String charset = request.header(CREDENTIAL_CHARSET);
        if (charset == null) {
            charset = this.getCredentialsCharset().name();
        }
        return charset;
    }

    private byte[] getBytes(String s2, String charset) {
        try {
            return s2.getBytes(charset);
        }
        catch (UnsupportedEncodingException e) {
            return s2.getBytes();
        }
    }

    public static byte[] getAsciiBytes(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }
        try {
            return data.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            throw new Error("HttpClient requires ASCII support", e);
        }
    }

    public boolean isProxy() {
        return this.proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    private static class AuthenticationException
    extends IllegalStateException {
        public AuthenticationException(String s2) {
            super(s2);
        }

        public AuthenticationException(String message, Exception ex) {
            super(message, ex);
        }
    }
}

