/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.ComplianceViolation;
import org.eclipse.jetty.http.CookieCompliance;
import org.eclipse.jetty.http.CookieParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cookies
implements CookieParser.Handler {
    protected static final Logger LOG = LoggerFactory.getLogger(Cookies.class);
    protected final List<String> _rawFields = new ArrayList<String>();
    protected final List<Cookie> _cookieList = new ArrayList<Cookie>();
    private int _addedFields;
    private boolean _parsed = false;
    private Cookie[] _cookies;
    private boolean _set = false;
    private final CookieParser _parser;

    public Cookies() {
        this(CookieCompliance.RFC6265, null);
    }

    public Cookies(CookieCompliance compliance, ComplianceViolation.Listener complianceListener) {
        this._parser = CookieParser.newParser(this, compliance, complianceListener);
    }

    public void addCookieField(String rawField) {
        if (this._set) {
            throw new IllegalStateException();
        }
        if (rawField == null) {
            return;
        }
        if ((rawField = rawField.trim()).length() == 0) {
            return;
        }
        if (this._rawFields.size() > this._addedFields) {
            if (rawField.equals(this._rawFields.get(this._addedFields))) {
                ++this._addedFields;
                return;
            }
            while (this._rawFields.size() > this._addedFields) {
                this._rawFields.remove(this._addedFields);
            }
        }
        this._rawFields.add(this._addedFields++, rawField);
        this._parsed = false;
    }

    public Cookie[] getCookies() {
        if (this._set) {
            return this._cookies;
        }
        while (this._rawFields.size() > this._addedFields) {
            this._rawFields.remove(this._addedFields);
            this._parsed = false;
        }
        if (this._parsed) {
            return this._cookies;
        }
        try {
            this._parser.parseFields(this._rawFields);
        }
        catch (CookieParser.InvalidCookieException invalidCookieException) {
            throw new BadMessageException(400, invalidCookieException.getMessage(), invalidCookieException);
        }
        this._cookies = this._cookieList.toArray(new Cookie[this._cookieList.size()]);
        this._cookieList.clear();
        this._parsed = true;
        return this._cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this._cookies = cookies;
        this._set = true;
    }

    public void reset() {
        if (this._set) {
            this._cookies = null;
        }
        this._set = false;
        this._addedFields = 0;
    }

    @Override
    public void addCookie(String name, String value, int version, String domain, String path, String comment) {
        try {
            Cookie cookie = new Cookie(name, value);
            if (domain != null) {
                cookie.setDomain(domain);
            }
            if (path != null) {
                cookie.setPath(path);
            }
            if (version > 0) {
                cookie.setVersion(version);
            }
            if (comment != null) {
                cookie.setComment(comment);
            }
            this._cookieList.add(cookie);
        }
        catch (Exception e) {
            LOG.debug("Unable to add Cookie name={}, value={}, domain={}, path={}, version={}, comment={}", name, value, domain, path, version, comment, e);
        }
    }
}

