/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import java.util.Optional;
import java.util.regex.Pattern;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class HeaderMatcher
implements Matcher {
    private String headerName;
    private String expectedValue;
    protected Pattern pattern;

    public HeaderMatcher() {
    }

    public HeaderMatcher(String headerName, String expectedValue) {
        this.setHeaderName(headerName);
        this.setExpectedValue(expectedValue);
    }

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotBlank("headerName", this.headerName);
        Optional<String> headerValue = context.getRequestHeader(this.headerName);
        boolean headerNull = this.expectedValue == null && !headerValue.isPresent();
        boolean headerMatches = headerValue.isPresent() && this.pattern != null && this.pattern.matcher(headerValue.get()).matches();
        return headerNull || headerMatches;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getExpectedValue() {
        return this.expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
        if (expectedValue != null) {
            this.pattern = Pattern.compile(expectedValue);
        }
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "headerName", this.headerName, "expectedValue", this.expectedValue);
    }
}

