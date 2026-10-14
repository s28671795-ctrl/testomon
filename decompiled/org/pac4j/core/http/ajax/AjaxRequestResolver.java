/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.ajax;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.redirect.RedirectionActionBuilder;

public interface AjaxRequestResolver {
    public boolean isAjax(WebContext var1, SessionStore var2);

    public HttpAction buildAjaxResponse(WebContext var1, SessionStore var2, RedirectionActionBuilder var3);
}

