/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicDomainHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        if (TextUtils.isBlank(value)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        }
        if (value.endsWith(".")) {
            return;
        }
        String domain = value;
        if (domain.startsWith(".")) {
            domain = domain.substring(1);
        }
        domain = domain.toLowerCase(Locale.ROOT);
        cookie.setDomain(domain);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(origin, "Cookie origin");
        String host2 = origin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie 'domain' may not be null");
        }
        if (!host2.equals(domain) && !BasicDomainHandler.domainMatch(domain, host2)) {
            throw new CookieRestrictionViolationException("Illegal 'domain' attribute \"" + domain + "\". Domain of origin: \"" + host2 + "\"");
        }
    }

    static boolean domainMatch(String domain, String host2) {
        String normalizedDomain;
        if (InetAddressUtils.isIPv4Address(host2) || InetAddressUtils.isIPv6Address(host2)) {
            return false;
        }
        String string = normalizedDomain = domain.startsWith(".") ? domain.substring(1) : domain;
        if (host2.endsWith(normalizedDomain)) {
            int prefix = host2.length() - normalizedDomain.length();
            if (prefix == 0) {
                return true;
            }
            if (prefix > 1 && host2.charAt(prefix - 1) == '.') {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin origin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(origin, "Cookie origin");
        String host2 = origin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        if (domain.startsWith(".")) {
            domain = domain.substring(1);
        }
        if (host2.equals(domain = domain.toLowerCase(Locale.ROOT))) {
            return true;
        }
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("domain")) {
            return BasicDomainHandler.domainMatch(domain, host2);
        }
        return false;
    }

    @Override
    public String getAttributeName() {
        return "domain";
    }
}

