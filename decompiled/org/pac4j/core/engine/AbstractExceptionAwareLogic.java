/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.profile.factory.ProfileManagerFactoryAware;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExceptionAwareLogic
extends ProfileManagerFactoryAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExceptionAwareLogic.class);
    private String errorUrl;

    protected Object handleException(Exception e, HttpActionAdapter httpActionAdapter, WebContext context) {
        if (httpActionAdapter == null || context == null) {
            throw this.runtimeException(e);
        }
        if (e instanceof HttpAction) {
            HttpAction action = (HttpAction)e;
            LOGGER.debug("extra HTTP action required in security: {}", (Object)action.getCode());
            return httpActionAdapter.adapt(action, context);
        }
        if (CommonHelper.isNotBlank(this.errorUrl)) {
            RedirectionAction action = HttpActionHelper.buildRedirectUrlAction(context, this.errorUrl);
            return httpActionAdapter.adapt(action, context);
        }
        throw this.runtimeException(e);
    }

    protected RuntimeException runtimeException(Exception exception) {
        if (exception instanceof RuntimeException) {
            throw (RuntimeException)exception;
        }
        throw new RuntimeException(exception);
    }

    public String getErrorUrl() {
        return this.errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}

