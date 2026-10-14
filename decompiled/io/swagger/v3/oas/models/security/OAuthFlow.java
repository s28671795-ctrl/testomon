/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.security;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.security.Scopes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class OAuthFlow {
    private String authorizationUrl = null;
    private String tokenUrl = null;
    private String refreshUrl = null;
    private Scopes scopes = null;
    private Map<String, Object> extensions = null;

    public String getAuthorizationUrl() {
        return this.authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public OAuthFlow authorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
        return this;
    }

    public String getTokenUrl() {
        return this.tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public OAuthFlow tokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public String getRefreshUrl() {
        return this.refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    public OAuthFlow refreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
        return this;
    }

    public Scopes getScopes() {
        return this.scopes;
    }

    public void setScopes(Scopes scopes) {
        this.scopes = scopes;
    }

    public OAuthFlow scopes(Scopes scopes) {
        this.scopes = scopes;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OAuthFlow oauthFlow = (OAuthFlow)o;
        return Objects.equals(this.authorizationUrl, oauthFlow.authorizationUrl) && Objects.equals(this.tokenUrl, oauthFlow.tokenUrl) && Objects.equals(this.refreshUrl, oauthFlow.refreshUrl) && Objects.equals(this.scopes, oauthFlow.scopes) && Objects.equals(this.extensions, oauthFlow.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.authorizationUrl, this.tokenUrl, this.refreshUrl, this.scopes, this.extensions);
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        this.addExtension(name, value);
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public OAuthFlow extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OAuthFlow {\n");
        sb.append("    authorizationUrl: ").append(this.toIndentedString(this.authorizationUrl)).append("\n");
        sb.append("    tokenUrl: ").append(this.toIndentedString(this.tokenUrl)).append("\n");
        sb.append("    refreshUrl: ").append(this.toIndentedString(this.refreshUrl)).append("\n");
        sb.append("    scopes: ").append(this.toIndentedString(this.scopes)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

