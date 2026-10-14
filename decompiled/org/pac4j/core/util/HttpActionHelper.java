/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util;

import java.util.Map;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.FoundAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.OkAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.SeeOtherAction;
import org.pac4j.core.exception.http.UnauthorizedAction;

public final class HttpActionHelper {
    private static boolean useModernHttpCodes = true;
    private static boolean alwaysUse401ForUnauthenticated = true;

    public static HttpAction buildUnauthenticatedAction(WebContext context) {
        boolean hasHeader = context.getResponseHeader("WWW-Authenticate").isPresent();
        if (alwaysUse401ForUnauthenticated) {
            if (!hasHeader) {
                context.setResponseHeader("WWW-Authenticate", "Bearer realm=\"pac4j\"");
            }
            return new UnauthorizedAction();
        }
        if (hasHeader) {
            return new UnauthorizedAction();
        }
        return new ForbiddenAction();
    }

    public static RedirectionAction buildRedirectUrlAction(WebContext context, String location) {
        if (WebContextHelper.isPost(context) && useModernHttpCodes) {
            return new SeeOtherAction(location);
        }
        return new FoundAction(location);
    }

    public static RedirectionAction buildFormPostContentAction(WebContext context, String content) {
        return new OkAction(content);
    }

    public static String buildFormPostContent(WebContext context) {
        String requestedUrl = context.getFullRequestURL();
        Map<String, String[]> parameters = context.getRequestParameters();
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html>\n");
        buffer.append("<body>\n");
        buffer.append("<form action=\"" + HttpActionHelper.escapeHtml(requestedUrl) + "\" name=\"f\" method=\"post\">\n");
        if (parameters != null) {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String[] values2 = entry.getValue();
                if (values2 == null || values2.length <= 0) continue;
                buffer.append("<input type='hidden' name=\"" + HttpActionHelper.escapeHtml(entry.getKey()) + "\" value=\"" + values2[0] + "\" />\n");
            }
        }
        buffer.append("<input value='POST' type='submit' />\n");
        buffer.append("</form>\n");
        buffer.append("<script type='text/javascript'>document.forms['f'].submit();</script>\n");
        buffer.append("</body>\n");
        buffer.append("</html>\n");
        return buffer.toString();
    }

    protected static String escapeHtml(String s2) {
        return s2.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;");
    }

    public static boolean isUseModernHttpCodes() {
        return useModernHttpCodes;
    }

    public static void setUseModernHttpCodes(boolean useModernHttpCodes) {
        HttpActionHelper.useModernHttpCodes = useModernHttpCodes;
    }

    public static boolean isAlwaysUse401ForUnauthenticated() {
        return alwaysUse401ForUnauthenticated;
    }

    public static void setAlwaysUse401ForUnauthenticated(boolean alwaysUse401ForUnauthenticated) {
        HttpActionHelper.alwaysUse401ForUnauthenticated = alwaysUse401ForUnauthenticated;
    }
}

