/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.security;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.security.OAuthFlows;
import java.util.LinkedHashMap;
import java.util.Map;

public class SecurityScheme {
    private Type type = null;
    private String description = null;
    private String name = null;
    private String $ref = null;
    private In in = null;
    private String scheme = null;
    private String bearerFormat = null;
    private OAuthFlows flows = null;
    private String openIdConnectUrl = null;
    private Map<String, Object> extensions = null;

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SecurityScheme type(Type type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SecurityScheme description(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SecurityScheme name(String name) {
        this.name = name;
        return this;
    }

    public In getIn() {
        return this.in;
    }

    public void setIn(In in) {
        this.in = in;
    }

    public SecurityScheme in(In in) {
        this.in = in;
        return this;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme2) {
        this.scheme = scheme2;
    }

    public SecurityScheme scheme(String scheme2) {
        this.scheme = scheme2;
        return this;
    }

    public String getBearerFormat() {
        return this.bearerFormat;
    }

    public void setBearerFormat(String bearerFormat) {
        this.bearerFormat = bearerFormat;
    }

    public SecurityScheme bearerFormat(String bearerFormat) {
        this.bearerFormat = bearerFormat;
        return this;
    }

    public OAuthFlows getFlows() {
        return this.flows;
    }

    public void setFlows(OAuthFlows flows) {
        this.flows = flows;
    }

    public SecurityScheme flows(OAuthFlows flows) {
        this.flows = flows;
        return this;
    }

    public String getOpenIdConnectUrl() {
        return this.openIdConnectUrl;
    }

    public void setOpenIdConnectUrl(String openIdConnectUrl) {
        this.openIdConnectUrl = openIdConnectUrl;
    }

    public SecurityScheme openIdConnectUrl(String openIdConnectUrl) {
        this.openIdConnectUrl = openIdConnectUrl;
        return this;
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        this.addExtension(name, value);
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

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public SecurityScheme extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/securitySchemes/" + $ref;
        }
        this.$ref = $ref;
    }

    public SecurityScheme $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityScheme)) {
            return false;
        }
        SecurityScheme that = (SecurityScheme)o;
        if (this.type != that.type) {
            return false;
        }
        if (this.description != null ? !this.description.equals(that.description) : that.description != null) {
            return false;
        }
        if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
            return false;
        }
        if (this.$ref != null ? !this.$ref.equals(that.$ref) : that.$ref != null) {
            return false;
        }
        if (this.in != that.in) {
            return false;
        }
        if (this.scheme != null ? !this.scheme.equals(that.scheme) : that.scheme != null) {
            return false;
        }
        if (this.bearerFormat != null ? !this.bearerFormat.equals(that.bearerFormat) : that.bearerFormat != null) {
            return false;
        }
        if (this.flows != null ? !this.flows.equals(that.flows) : that.flows != null) {
            return false;
        }
        if (this.openIdConnectUrl != null ? !this.openIdConnectUrl.equals(that.openIdConnectUrl) : that.openIdConnectUrl != null) {
            return false;
        }
        return this.extensions != null ? this.extensions.equals(that.extensions) : that.extensions == null;
    }

    public int hashCode() {
        int result = this.type != null ? this.type.hashCode() : 0;
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.$ref != null ? this.$ref.hashCode() : 0);
        result = 31 * result + (this.in != null ? this.in.hashCode() : 0);
        result = 31 * result + (this.scheme != null ? this.scheme.hashCode() : 0);
        result = 31 * result + (this.bearerFormat != null ? this.bearerFormat.hashCode() : 0);
        result = 31 * result + (this.flows != null ? this.flows.hashCode() : 0);
        result = 31 * result + (this.openIdConnectUrl != null ? this.openIdConnectUrl.hashCode() : 0);
        result = 31 * result + (this.extensions != null ? this.extensions.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SecurityScheme {\n");
        sb.append("    type: ").append(this.toIndentedString((Object)this.type)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    in: ").append(this.toIndentedString((Object)this.in)).append("\n");
        sb.append("    scheme: ").append(this.toIndentedString(this.scheme)).append("\n");
        sb.append("    bearerFormat: ").append(this.toIndentedString(this.bearerFormat)).append("\n");
        sb.append("    flows: ").append(this.toIndentedString(this.flows)).append("\n");
        sb.append("    openIdConnectUrl: ").append(this.toIndentedString(this.openIdConnectUrl)).append("\n");
        sb.append("    $ref: ").append(this.toIndentedString(this.$ref)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public static enum In {
        COOKIE("cookie"),
        HEADER("header"),
        QUERY("query");

        private String value;

        private In(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    public static enum Type {
        APIKEY("apiKey"),
        HTTP("http"),
        OAUTH2("oauth2"),
        OPENIDCONNECT("openIdConnect"),
        MUTUALTLS("mutualTLS");

        private String value;

        private Type(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }
}

