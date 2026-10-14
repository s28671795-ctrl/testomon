/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.ajax;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.WithLocationAction;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.redirect.RedirectionActionBuilder;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;
import org.pac4j.core.util.Pac4jConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAjaxRequestResolver
implements AjaxRequestResolver,
HttpConstants,
Pac4jConstants {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean addRedirectionUrlAsHeader = false;

    @Override
    public boolean isAjax(WebContext context, SessionStore sessionStore) {
        boolean xmlHttpRequest = "XMLHttpRequest".equalsIgnoreCase(context.getRequestHeader("X-Requested-With").orElse(null));
        boolean hasDynamicAjaxParameter = Boolean.TRUE.toString().equalsIgnoreCase(context.getRequestHeader("is_ajax_request").orElse(null));
        boolean hasDynamicAjaxHeader = Boolean.TRUE.toString().equalsIgnoreCase(context.getRequestParameter("is_ajax_request").orElse(null));
        return xmlHttpRequest || hasDynamicAjaxParameter || hasDynamicAjaxHeader;
    }

    @Override
    public HttpAction buildAjaxResponse(WebContext context, SessionStore sessionStore, RedirectionActionBuilder redirectionActionBuilder) {
        RedirectionAction action;
        String url2 = null;
        if (this.addRedirectionUrlAsHeader && (action = (RedirectionAction)redirectionActionBuilder.getRedirectionAction(context, sessionStore).orElse(null)) instanceof WithLocationAction) {
            url2 = ((WithLocationAction)((Object)action)).getLocation();
        }
        if (!context.getRequestParameter("javax.faces.partial.ajax").isPresent()) {
            if (CommonHelper.isNotBlank(url2)) {
                context.setResponseHeader("Location", url2);
            }
            this.logger.debug("Faces is not used: returning unauthenticated error for url: {}", (Object)url2);
            return HttpActionHelper.buildUnauthenticatedAction(context);
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
        buffer.append("<partial-response>");
        if (CommonHelper.isNotBlank(url2)) {
            buffer.append("<redirect url=\"" + url2.replaceAll("&", "&amp;") + "\"></redirect>");
        }
        buffer.append("</partial-response>");
        this.logger.debug("Faces is used: returning partial response content for url: {}", (Object)url2);
        return HttpActionHelper.buildFormPostContentAction(context, buffer.toString());
    }

    public boolean isAddRedirectionUrlAsHeader() {
        return this.addRedirectionUrlAsHeader;
    }

    public void setAddRedirectionUrlAsHeader(boolean addRedirectionUrlAsHeader) {
        this.addRedirectionUrlAsHeader = addRedirectionUrlAsHeader;
    }
}

