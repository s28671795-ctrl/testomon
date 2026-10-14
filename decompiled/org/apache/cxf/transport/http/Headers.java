/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.transport.http.Cookie;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.apache.cxf.version.Version;

public class Headers {
    public static final String KEY_HTTP_CONNECTION = "http.connection";
    public static final String ADD_HEADERS_PROPERTY = "org.apache.cxf.http.add-headers";
    public static final String PROTOCOL_HEADERS_CONTENT_TYPE = "Content-Type".toLowerCase();
    public static final String HTTP_HEADERS_SETCOOKIE = "Set-Cookie";
    public static final String HTTP_HEADERS_LINK = "Link";
    public static final String EMPTY_REQUEST_PROPERTY = "org.apache.cxf.empty.request";
    private static final String SET_EMPTY_REQUEST_CT_PROPERTY = "set.content.type.for.empty.request";
    private static final TimeZone TIME_ZONE_GMT = TimeZone.getTimeZone("GMT");
    private static final Logger LOG = LogUtils.getL7dLogger(Headers.class);
    private static final List<String> SENSITIVE_HEADERS = Arrays.asList("Authorization", "Proxy-Authorization");
    private static final List<Object> SENSITIVE_HEADER_MARKER = Arrays.asList("***");
    private static final String ALLOW_LOGGING_SENSITIVE_HEADERS = "allow.logging.sensitive.headers";
    private static final String USER_AGENT = Headers.initUserAgent();
    private final Message message;
    private final Map<String, List<String>> headers;

    public Headers(Message message) {
        this.message = message;
        this.headers = Headers.getSetProtocolHeaders(message);
    }

    public Headers() {
        this.headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
        this.message = null;
    }

    public static String getUserAgent() {
        return USER_AGENT;
    }

    private static String initUserAgent() {
        String name = Version.getName();
        if ("Apache CXF".equals(name)) {
            name = "Apache-CXF";
        }
        String version = Version.getCurrentVersion();
        return name + "/" + version;
    }

    static String toString(Map<String, List<Object>> headers, boolean logSensitiveHeaders) {
        TreeMap<String, List<Object>> filteredHeaders = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        filteredHeaders.putAll(headers);
        if (!logSensitiveHeaders) {
            for (String filteredKey : SENSITIVE_HEADERS) {
                filteredHeaders.put(filteredKey, SENSITIVE_HEADER_MARKER);
            }
        }
        return ((Object)filteredHeaders).toString();
    }

    public Map<String, List<String>> headerMap() {
        return this.headers;
    }

    public void writeSessionCookies(Map<String, Cookie> sessionCookies) {
        List<Object> cookies = null;
        for (String s2 : this.headers.keySet()) {
            if (!"Cookie".equalsIgnoreCase(s2)) continue;
            cookies = this.headers.remove(s2);
            break;
        }
        cookies = cookies == null ? new ArrayList() : new ArrayList(cookies);
        this.headers.put("Cookie", cookies);
        for (Cookie c : sessionCookies.values()) {
            cookies.add(c.requestCookieHeader());
        }
    }

    void setFromClientPolicy(HTTPClientPolicy policy) {
        if (policy == null) {
            return;
        }
        if (policy.isSetCacheControl()) {
            this.headers.put("Cache-Control", Headers.createMutableList(policy.getCacheControl()));
        }
        if (policy.isSetHost()) {
            this.headers.put("Host", Headers.createMutableList(policy.getHost()));
        }
        if (policy.isSetConnection()) {
            this.headers.put("Connection", Headers.createMutableList(policy.getConnection().value()));
        }
        if (policy.isSetAccept()) {
            this.headers.put("Accept", Headers.createMutableList(policy.getAccept()));
        } else if (!this.headers.containsKey("Accept")) {
            this.headers.put("Accept", Headers.createMutableList("*/*"));
        }
        if (policy.isSetAcceptEncoding()) {
            this.headers.put("Accept-Encoding", Headers.createMutableList(policy.getAcceptEncoding()));
        }
        if (policy.isSetAcceptLanguage()) {
            this.headers.put("Accept-Language", Headers.createMutableList(policy.getAcceptLanguage()));
        }
        if (policy.isSetContentType()) {
            this.message.put("Content-Type", policy.getContentType());
        }
        if (policy.isSetCookie()) {
            this.headers.put("Cookie", Headers.createMutableList(policy.getCookie()));
        }
        if (policy.isSetBrowserType()) {
            this.headers.put("User-Agent", Headers.createMutableList(policy.getBrowserType()));
        }
        if (policy.isSetReferer()) {
            this.headers.put("Referer", Headers.createMutableList(policy.getReferer()));
        }
    }

    void setFromServerPolicy(HTTPServerPolicy policy) {
        if (policy.isSetCacheControl()) {
            this.headers.put("Cache-Control", Headers.createMutableList(policy.getCacheControl()));
        }
        if (policy.isSetContentLocation()) {
            this.headers.put("Content-Location", Headers.createMutableList(policy.getContentLocation()));
        }
        if (policy.isSetContentEncoding()) {
            this.headers.put("Content-Encoding", Headers.createMutableList(policy.getContentEncoding()));
        }
        if (policy.isSetContentType()) {
            this.headers.put("Content-Type", Headers.createMutableList(policy.getContentType()));
        }
        if (policy.isSetServerType()) {
            this.headers.put("Server", Headers.createMutableList(policy.getServerType()));
        }
        if (policy.isSetHonorKeepAlive() && !policy.isHonorKeepAlive()) {
            this.headers.put("Connection", Headers.createMutableList("close"));
        } else if (policy.isSetKeepAliveParameters()) {
            this.headers.put("Keep-Alive", Headers.createMutableList(policy.getKeepAliveParameters()));
        }
    }

    public void removeAuthorizationHeaders() {
        this.headers.remove("Authorization");
        this.headers.remove("Proxy-Authorization");
    }

    public void setAuthorization(String authorization) {
        this.headers.put("Authorization", Headers.createMutableList(authorization));
    }

    public void setProxyAuthorization(String authorization) {
        this.headers.put("Proxy-Authorization", Headers.createMutableList(authorization));
    }

    public static Map<String, List<String>> getSetProtocolHeaders(Message message) {
        Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (null == headers) {
            headers = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        } else if (headers instanceof HashMap) {
            TreeMap<String, List<String>> headers2 = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
            headers2.putAll(headers);
            headers = headers2;
        }
        message.put(Message.PROTOCOL_HEADERS, headers);
        return headers;
    }

    public void readFromConnection(HttpURLConnection connection) {
        Map<String, List<String>> origHeaders = connection.getHeaderFields();
        this.headers.clear();
        for (Map.Entry<String, List<String>> entry : origHeaders.entrySet()) {
            if (entry.getKey() == null) continue;
            String key = HttpHeaderHelper.getHeaderKey(entry.getKey());
            List<String> old = this.headers.get(key);
            if (old != null) {
                ArrayList<String> nl = new ArrayList<String>(old.size() + entry.getValue().size());
                nl.addAll(old);
                nl.addAll((Collection)entry.getValue());
                this.headers.put(key, nl);
                continue;
            }
            this.headers.put(key, entry.getValue());
        }
    }

    private static List<String> createMutableList(String val) {
        return new ArrayList<String>(Arrays.asList(val));
    }

    static void logProtocolHeaders(Logger logger2, Level level, Map<String, List<Object>> headersMap, boolean logSensitiveHeaders) {
        if (logger2.isLoggable(level)) {
            for (Map.Entry<String, List<Object>> entry : headersMap.entrySet()) {
                String key = entry.getKey();
                boolean sensitive = !logSensitiveHeaders && SENSITIVE_HEADERS.contains(key);
                List<Object> headerList = sensitive ? SENSITIVE_HEADER_MARKER : entry.getValue();
                for (Object value : headerList) {
                    logger2.log(level, key + ": " + (value == null ? "<null>" : value.toString()));
                }
            }
        }
    }

    public void setProtocolHeadersInConnection(HttpURLConnection connection) throws IOException {
        boolean contentTypeSet = this.headers.containsKey("Content-Type");
        if (!contentTypeSet) {
            boolean emptyRequest;
            boolean dropContentType = false;
            boolean getRequest = "GET".equals(this.message.get("org.apache.cxf.request.method"));
            boolean bl = emptyRequest = getRequest || PropertyUtils.isTrue(this.message.get(EMPTY_REQUEST_PROPERTY));
            if (emptyRequest) {
                Object setCtForEmptyRequestProp = this.message.getContextualProperty(SET_EMPTY_REQUEST_CT_PROPERTY);
                if (setCtForEmptyRequestProp != null) {
                    dropContentType = PropertyUtils.isFalse(setCtForEmptyRequestProp);
                } else if (getRequest) {
                    dropContentType = true;
                }
            }
            if (!dropContentType) {
                String ct = emptyRequest && !contentTypeSet ? "*/*" : this.determineContentType();
                connection.setRequestProperty("Content-Type", ct);
            }
        } else {
            connection.setRequestProperty("Content-Type", this.determineContentType());
        }
        this.transferProtocolHeadersToURLConnection(connection);
        Map<String, List<Object>> theHeaders = CastUtils.cast(this.headers);
        Headers.logProtocolHeaders(LOG, Level.FINE, theHeaders, this.logSensitiveHeaders());
    }

    public String determineContentType() {
        String ct = null;
        List ctList = CastUtils.cast(this.headers.get("Content-Type"));
        ct = ctList != null && ctList.size() == 1 && ctList.get(0) != null ? ctList.get(0).toString() : (String)this.message.get("Content-Type");
        String enc = (String)this.message.get(Message.ENCODING);
        if (null != ct) {
            if (enc != null && ct.indexOf("charset=") == -1 && !ct.toLowerCase().contains("multipart/related")) {
                ct = ct + "; charset=" + enc;
            }
        } else {
            ct = enc != null ? "text/xml; charset=" + enc : "text/xml";
        }
        return ct;
    }

    private void transferProtocolHeadersToURLConnection(URLConnection connection) {
        boolean addHeaders = MessageUtils.getContextualBoolean(this.message, ADD_HEADERS_PROPERTY, false);
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            String header = entry.getKey();
            if ("Content-Type".equalsIgnoreCase(header)) continue;
            List<String> headerList = entry.getValue();
            if (addHeaders || "Cookie".equalsIgnoreCase(header)) {
                headerList.forEach(s2 -> connection.addRequestProperty(header, (String)s2));
                continue;
            }
            connection.setRequestProperty(header, String.join((CharSequence)",", headerList));
        }
        if (connection.getRequestProperty("User-Agent") == null) {
            connection.addRequestProperty("User-Agent", USER_AGENT);
        }
    }

    protected void copyFromRequest(HttpServletRequest req) {
        Enumeration e = req.getHeaderNames();
        while (e.hasMoreElements()) {
            String fname = (String)e.nextElement();
            String mappedName = HttpHeaderHelper.getHeaderKey(fname);
            List<String> values2 = this.headers.get(mappedName);
            if (values2 == null) {
                values2 = new ArrayList<String>();
                this.headers.put(mappedName, values2);
            }
            Enumeration e2 = req.getHeaders(fname);
            while (e2.hasMoreElements()) {
                String val = (String)e2.nextElement();
                if ("Accept".equals(mappedName) && !values2.isEmpty()) {
                    String firstAccept = values2.get(0);
                    firstAccept = firstAccept + ", " + val;
                    values2.set(0, firstAccept);
                }
                values2.add(val);
            }
        }
        if (!this.headers.containsKey("Content-Type")) {
            this.headers.put("Content-Type", Collections.singletonList(req.getContentType()));
        }
        if (LOG.isLoggable(Level.FINE)) {
            Map<String, List<Object>> theHeaders = CastUtils.cast(this.headers);
            LOG.log(Level.FINE, "Request Headers: " + Headers.toString(theHeaders, this.logSensitiveHeaders()));
        }
    }

    private boolean logSensitiveHeaders() {
        return PropertyUtils.isTrue(this.message.getContextualProperty(ALLOW_LOGGING_SENSITIVE_HEADERS));
    }

    private String getContentTypeFromMessage() {
        String ct = (String)this.message.get("Content-Type");
        String enc = (String)this.message.get(Message.ENCODING);
        if (null != ct && null != enc && ct.indexOf("charset=") == -1 && !ct.toLowerCase().contains("multipart/related")) {
            return ct + "; charset=" + enc;
        }
        return ct;
    }

    private boolean isResponseBodyAvailable() {
        List<String> ctLen = this.headers.get("Content-Length");
        if (ctLen == null || ctLen.size() != 1) {
            return true;
        }
        try {
            if (Integer.parseInt(ctLen.get(0)) == 0) {
                return false;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return true;
    }

    private boolean isSingleHeader(String header) {
        return HTTP_HEADERS_SETCOOKIE.equalsIgnoreCase(header) || HTTP_HEADERS_LINK.equalsIgnoreCase(header);
    }

    protected void copyToResponse(HttpServletResponse response) {
        String contentType = this.getContentTypeFromMessage();
        if (!this.headers.containsKey("Content-Type") && contentType != null && this.isResponseBodyAvailable()) {
            response.setContentType(contentType);
        }
        boolean addHeaders = MessageUtils.getContextualBoolean(this.message, ADD_HEADERS_PROPERTY, false);
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            String header = entry.getKey();
            List<String> headerList = entry.getValue();
            if (addHeaders || this.isSingleHeader(header)) {
                for (int i = 0; i < headerList.size(); ++i) {
                    String headerObject = headerList.get(i);
                    if (headerObject == null) continue;
                    response.addHeader(header, this.headerObjectToString(headerObject));
                }
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < headerList.size(); ++i) {
                String headerObject = headerList.get(i);
                if (headerObject != null) {
                    sb.append(this.headerObjectToString(headerObject));
                }
                if (i + 1 >= headerList.size()) continue;
                sb.append(',');
            }
            response.setHeader(header, sb.toString());
        }
    }

    private String headerObjectToString(Object headerObject) {
        if (headerObject.getClass() == String.class) {
            return headerObject.toString();
        }
        String headerString = headerObject instanceof Date ? Headers.toHttpDate((Date)headerObject) : (headerObject instanceof Locale ? Headers.toHttpLanguage((Locale)headerObject) : headerObject.toString());
        return headerString;
    }

    void removeContentType() {
        this.headers.remove(PROTOCOL_HEADERS_CONTENT_TYPE);
    }

    public String getAuthorization() {
        List<String> authorizationLines = this.headers.get("Authorization");
        if (authorizationLines != null && !authorizationLines.isEmpty()) {
            return authorizationLines.get(0);
        }
        return null;
    }

    public static SimpleDateFormat getHttpDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        dateFormat.setTimeZone(TIME_ZONE_GMT);
        return dateFormat;
    }

    public static String toHttpDate(Date date) {
        SimpleDateFormat format = Headers.getHttpDateFormat();
        return format.format(date);
    }

    public static String toHttpLanguage(Locale locale) {
        return locale.toString().replace('_', '-');
    }
}

