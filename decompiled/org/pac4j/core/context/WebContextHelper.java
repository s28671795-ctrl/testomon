/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import org.pac4j.core.context.Cookie;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.util.CommonHelper;

public final class WebContextHelper
implements HttpConstants {
    private static ZoneId GMT = ZoneId.of("GMT");
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).withZone(GMT);

    public static Cookie getCookie(Collection<Cookie> cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie == null || !CommonHelper.areEquals(name, cookie.getName())) continue;
                return cookie;
            }
        }
        return null;
    }

    public static Cookie getCookie(WebContext context, String name) {
        return WebContextHelper.getCookie(context.getRequestCookies(), name);
    }

    public static boolean isGet(WebContext context) {
        return HttpConstants.HTTP_METHOD.GET.name().equalsIgnoreCase(context.getRequestMethod());
    }

    public static boolean isPost(WebContext context) {
        return HttpConstants.HTTP_METHOD.POST.name().equalsIgnoreCase(context.getRequestMethod());
    }

    public static boolean isPut(WebContext context) {
        return HttpConstants.HTTP_METHOD.PUT.name().equalsIgnoreCase(context.getRequestMethod());
    }

    public static boolean isPatch(WebContext context) {
        return HttpConstants.HTTP_METHOD.PATCH.name().equalsIgnoreCase(context.getRequestMethod());
    }

    public static boolean isDelete(WebContext context) {
        return HttpConstants.HTTP_METHOD.DELETE.name().equalsIgnoreCase(context.getRequestMethod());
    }

    public static boolean isHttpsOrSecure(WebContext context) {
        return "https".equalsIgnoreCase(context.getScheme()) || context.isSecure();
    }

    public static boolean isHttp(WebContext context) {
        return "http".equalsIgnoreCase(context.getScheme());
    }

    public static boolean isHttps(WebContext context) {
        return "https".equalsIgnoreCase(context.getScheme());
    }

    public static String createCookieHeader(Cookie cookie) {
        String value;
        String sameSitePolicy;
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s=%s;", cookie.getName(), cookie.getValue()));
        if (cookie.getMaxAge() > -1) {
            builder.append(String.format(" Max-Age=%s;", cookie.getMaxAge()));
            long millis = cookie.getMaxAge() > 0 ? System.currentTimeMillis() + (long)(cookie.getMaxAge() * 1000) : 0L;
            Instant instant = Instant.ofEpochMilli(millis);
            ZonedDateTime time = ZonedDateTime.ofInstant(instant, GMT);
            builder.append(String.format(" Expires=%s;", DATE_FORMATTER.format(time)));
        }
        if (CommonHelper.isNotBlank(cookie.getDomain())) {
            builder.append(String.format(" Domain=%s;", cookie.getDomain()));
        }
        builder.append(String.format(" Path=%s;", CommonHelper.isNotBlank(cookie.getPath()) ? cookie.getPath() : "/"));
        switch (sameSitePolicy = cookie.getSameSitePolicy() == null ? "lax" : cookie.getSameSitePolicy().toLowerCase()) {
            case "strict": {
                builder.append(" SameSite=Strict;");
                break;
            }
            case "none": {
                builder.append(" SameSite=None;");
                break;
            }
            default: {
                builder.append(" SameSite=Lax;");
            }
        }
        if (cookie.isSecure() || "none".equals(sameSitePolicy)) {
            builder.append(" Secure;");
        }
        if (cookie.isHttpOnly()) {
            builder.append(" HttpOnly;");
        }
        if ((value = builder.toString()).endsWith(";")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }
}

