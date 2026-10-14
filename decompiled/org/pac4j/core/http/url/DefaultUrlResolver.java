/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.url;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.http.url.UrlResolver;

public class DefaultUrlResolver
implements UrlResolver {
    private boolean completeRelativeUrl;

    public DefaultUrlResolver() {
    }

    public DefaultUrlResolver(boolean completeRelativeUrl) {
        this.completeRelativeUrl = completeRelativeUrl;
    }

    @Override
    public String compute(String url2, WebContext context) {
        if (this.completeRelativeUrl) {
            boolean relativeUrl;
            boolean bl = relativeUrl = url2 != null && !url2.startsWith("http") && !url2.startsWith("https");
            if (context != null && relativeUrl) {
                boolean notDefaultHttpsPort;
                StringBuilder sb = new StringBuilder();
                sb.append(context.getScheme()).append("://").append(context.getServerName());
                boolean notDefaultHttpPort = WebContextHelper.isHttp(context) && context.getServerPort() != 80;
                boolean bl2 = notDefaultHttpsPort = WebContextHelper.isHttps(context) && context.getServerPort() != 443;
                if (notDefaultHttpPort || notDefaultHttpsPort) {
                    sb.append(":").append(context.getServerPort());
                }
                sb.append((String)(url2.startsWith("/") ? url2 : "/" + url2));
                return sb.toString();
            }
        }
        return url2;
    }

    public boolean isCompleteRelativeUrl() {
        return this.completeRelativeUrl;
    }

    public void setCompleteRelativeUrl(boolean completeRelativeUrl) {
        this.completeRelativeUrl = completeRelativeUrl;
    }
}

