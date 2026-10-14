/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.callback;

import java.util.HashMap;
import java.util.Map;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.util.CommonHelper;

public class QueryParameterCallbackUrlResolver
implements CallbackUrlResolver {
    private String clientNameParameter = "client_name";
    private Map<String, String> customParams = new HashMap<String, String>();

    public QueryParameterCallbackUrlResolver() {
    }

    public QueryParameterCallbackUrlResolver(Map<String, String> customParams) {
        this.customParams = customParams;
    }

    @Override
    public String compute(UrlResolver urlResolver, String url2, String clientName, WebContext context) {
        String newUrl = urlResolver.compute(url2, context);
        if (newUrl != null && !newUrl.contains(this.clientNameParameter + "=")) {
            newUrl = CommonHelper.addParameter(newUrl, this.clientNameParameter, clientName);
        }
        for (Map.Entry<String, String> entry : this.customParams.entrySet()) {
            newUrl = CommonHelper.addParameter(newUrl, entry.getKey(), entry.getValue());
        }
        return newUrl;
    }

    @Override
    public boolean matches(String clientName, WebContext context) {
        String name = context.getRequestParameter(this.clientNameParameter).orElse(null);
        return CommonHelper.areEqualsIgnoreCaseAndTrim(name, clientName);
    }

    public String getClientNameParameter() {
        return this.clientNameParameter;
    }

    public void setClientNameParameter(String clientNameParameter) {
        this.clientNameParameter = clientNameParameter;
    }
}

