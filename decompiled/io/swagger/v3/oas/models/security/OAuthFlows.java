/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.security;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.security.OAuthFlow;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class OAuthFlows {
    private OAuthFlow implicit = null;
    private OAuthFlow password = null;
    private OAuthFlow clientCredentials = null;
    private OAuthFlow authorizationCode = null;
    private Map<String, Object> extensions = null;

    public OAuthFlow getImplicit() {
        return this.implicit;
    }

    public void setImplicit(OAuthFlow implicit) {
        this.implicit = implicit;
    }

    public OAuthFlows implicit(OAuthFlow implicit) {
        this.implicit = implicit;
        return this;
    }

    public OAuthFlow getPassword() {
        return this.password;
    }

    public void setPassword(OAuthFlow password) {
        this.password = password;
    }

    public OAuthFlows password(OAuthFlow password) {
        this.password = password;
        return this;
    }

    public OAuthFlow getClientCredentials() {
        return this.clientCredentials;
    }

    public void setClientCredentials(OAuthFlow clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    public OAuthFlows clientCredentials(OAuthFlow clientCredentials) {
        this.clientCredentials = clientCredentials;
        return this;
    }

    public OAuthFlow getAuthorizationCode() {
        return this.authorizationCode;
    }

    public void setAuthorizationCode(OAuthFlow authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public OAuthFlows authorizationCode(OAuthFlow authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OAuthFlows oauthFlows = (OAuthFlows)o;
        return Objects.equals(this.implicit, oauthFlows.implicit) && Objects.equals(this.password, oauthFlows.password) && Objects.equals(this.clientCredentials, oauthFlows.clientCredentials) && Objects.equals(this.authorizationCode, oauthFlows.authorizationCode) && Objects.equals(this.extensions, oauthFlows.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.implicit, this.password, this.clientCredentials, this.authorizationCode, this.extensions);
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

    public OAuthFlows extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OAuthFlows {\n");
        sb.append("    implicit: ").append(this.toIndentedString(this.implicit)).append("\n");
        sb.append("    password: ").append(this.toIndentedString(this.password)).append("\n");
        sb.append("    clientCredentials: ").append(this.toIndentedString(this.clientCredentials)).append("\n");
        sb.append("    authorizationCode: ").append(this.toIndentedString(this.authorizationCode)).append("\n");
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

