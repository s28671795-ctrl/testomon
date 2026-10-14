/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsrfAuthorizer
implements Authorizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfAuthorizer.class);
    private String parameterName = "pac4jCsrfToken";
    private String headerName = "pac4jCsrfToken";
    private boolean checkAllRequests = false;

    public CsrfAuthorizer() {
    }

    public CsrfAuthorizer(String parameterName, String headerName) {
        this.parameterName = parameterName;
        this.headerName = headerName;
    }

    public CsrfAuthorizer(String parameterName, String headerName, boolean checkAllRequests) {
        this(parameterName, headerName);
        this.checkAllRequests = checkAllRequests;
    }

    @Override
    public boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        boolean checkRequest;
        boolean bl = checkRequest = this.checkAllRequests || WebContextHelper.isPost(context) || WebContextHelper.isPut(context) || WebContextHelper.isPatch(context) || WebContextHelper.isDelete(context);
        if (checkRequest) {
            String parameterToken = context.getRequestParameter(this.parameterName).orElse(null);
            String headerToken = context.getRequestHeader(this.headerName).orElse(null);
            LOGGER.debug("parameterToken: {}", (Object)parameterToken);
            LOGGER.debug("headerToken: {}", (Object)headerToken);
            Optional<Object> sessionPreviousToken = sessionStore.get(context, "pac4jPreviousCsrfToken");
            Optional<Object> sessionToken = sessionStore.get(context, "pac4jCsrfToken");
            Optional<Object> sessionDate = sessionStore.get(context, "pac4jCsrfTokenExpirationDate");
            if (sessionStore.getSessionId(context, false).isPresent()) {
                sessionStore.set(context, "pac4jPreviousCsrfToken", null);
            }
            boolean hasSessionData = sessionToken.isPresent() & sessionDate.isPresent();
            String previousToken = (String)sessionPreviousToken.orElse("");
            LOGGER.debug("previous token: {}", (Object)previousToken);
            String token = (String)sessionToken.orElse("");
            LOGGER.debug("token: {}", (Object)token);
            boolean isGoodCurrentToken = this.hashEquals(token, parameterToken) | this.hashEquals(token, headerToken);
            boolean isGoodPreviousToken = this.hashEquals(previousToken, parameterToken) | this.hashEquals(previousToken, headerToken);
            boolean isGoodToken = isGoodCurrentToken | isGoodPreviousToken;
            Long expirationDate = (Long)sessionDate.orElse(0L);
            long now = new Date().getTime();
            boolean isDateExpired = expirationDate < now;
            if (!hasSessionData | !isGoodToken | isDateExpired) {
                return false;
            }
        }
        return true;
    }

    protected boolean hashEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return a.hashCode() == b.hashCode();
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public boolean isCheckAllRequests() {
        return this.checkAllRequests;
    }

    public void setCheckAllRequests(boolean checkAllRequests) {
        this.checkAllRequests = checkAllRequests;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "parameterName", this.parameterName, "headerName", this.headerName, "checkAllRequests", this.checkAllRequests);
    }
}

