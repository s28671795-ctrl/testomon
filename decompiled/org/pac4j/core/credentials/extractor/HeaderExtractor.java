/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;

public class HeaderExtractor
implements CredentialsExtractor {
    private String headerName;
    private String prefixHeader;
    private boolean trimValue;

    public String getHeaderName() {
        return this.headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getPrefixHeader() {
        return this.prefixHeader;
    }

    public void setPrefixHeader(String prefixHeader) {
        this.prefixHeader = prefixHeader;
    }

    public boolean isTrimValue() {
        return this.trimValue;
    }

    public void setTrimValue(boolean trimValue) {
        this.trimValue = trimValue;
    }

    public HeaderExtractor() {
    }

    public HeaderExtractor(String headerName, String prefixHeader) {
        this.headerName = headerName;
        this.prefixHeader = prefixHeader;
    }

    @Override
    public Optional<Credentials> extract(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotBlank("headerName", this.headerName);
        CommonHelper.assertNotNull("prefixHeader", this.prefixHeader);
        Optional<String> header = context.getRequestHeader(this.headerName);
        if (!header.isPresent() && !(header = context.getRequestHeader(this.headerName.toLowerCase())).isPresent()) {
            return Optional.empty();
        }
        if (!header.get().startsWith(this.prefixHeader)) {
            throw new CredentialsException("Wrong prefix for header: " + this.headerName);
        }
        String headerWithoutPrefix = header.get().substring(this.prefixHeader.length());
        if (this.trimValue) {
            headerWithoutPrefix = headerWithoutPrefix.trim();
        }
        return Optional.of(new TokenCredentials(headerWithoutPrefix));
    }
}

