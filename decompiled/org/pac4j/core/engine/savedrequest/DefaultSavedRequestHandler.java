/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine.savedrequest;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.savedrequest.SavedRequestHandler;
import org.pac4j.core.exception.http.FoundAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.OkAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSavedRequestHandler
implements SavedRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSavedRequestHandler.class);

    @Override
    public void save(WebContext context, SessionStore sessionStore) {
        String requestedUrl = this.getRequestedUrl(context, sessionStore);
        if (WebContextHelper.isPost(context)) {
            LOGGER.debug("requestedUrl with data: {}", (Object)requestedUrl);
            String formPost = HttpActionHelper.buildFormPostContent(context);
            sessionStore.set(context, "pac4jRequestedUrl", new OkAction(formPost));
        } else {
            LOGGER.debug("requestedUrl: {}", (Object)requestedUrl);
            sessionStore.set(context, "pac4jRequestedUrl", requestedUrl);
        }
    }

    protected String getRequestedUrl(WebContext context, SessionStore sessionStore) {
        return context.getFullRequestURL();
    }

    @Override
    public HttpAction restore(WebContext context, SessionStore sessionStore, String defaultUrl) {
        Optional<Object> optRequestedUrl = sessionStore.get(context, "pac4jRequestedUrl");
        Throwable requestedAction = null;
        if (optRequestedUrl.isPresent()) {
            sessionStore.set(context, "pac4jRequestedUrl", null);
            Object requestedUrl = optRequestedUrl.get();
            if (requestedUrl instanceof String) {
                requestedAction = new FoundAction((String)requestedUrl);
            } else if (requestedUrl instanceof RedirectionAction) {
                requestedAction = (RedirectionAction)requestedUrl;
            }
        }
        if (requestedAction == null) {
            requestedAction = new FoundAction(defaultUrl);
        }
        LOGGER.debug("requestedAction: {}", (Object)requestedAction.getMessage());
        if (requestedAction instanceof FoundAction) {
            return HttpActionHelper.buildRedirectUrlAction(context, ((FoundAction)requestedAction).getLocation());
        }
        return HttpActionHelper.buildFormPostContentAction(context, ((OkAction)requestedAction).getContent());
    }
}

