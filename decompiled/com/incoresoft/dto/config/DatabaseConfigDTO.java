/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.DatabaseConfig;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Schema(name="DatabaseConfigDTO", description="Database config class structure")
@JsonDeserialize(builder=DatabaseConfigDTOBuilder.class)
public final class DatabaseConfigDTO {
    @JsonProperty(value="url")
    @Schema(name="url", description="URL", example="localhost", implementation=String.class)
    private final String url;
    @JsonProperty(value="schema_name")
    @Schema(name="schema_name", description="Schema name", example="videoanalytics", implementation=String.class)
    private final String schemaName;
    @JsonProperty(value="login")
    @Schema(name="login", description="Login", example="root", implementation=String.class)
    private final String login;
    @JsonProperty(value="password")
    @Schema(name="password", description="Password", example="root", implementation=String.class)
    private final String password;
    @JsonProperty(value="encrypted")
    @Schema(name="encrypted", description="Encrypted", example="true", implementation=Boolean.class)
    private final Boolean encrypted;

    public DatabaseConfigDTO(DatabaseConfig database) {
        this.url = database.getUrl();
        this.login = database.getLogin();
        this.password = database.getPassword();
        this.schemaName = database.getSchemaName();
        this.encrypted = database.isEncrypted();
    }

    public static DatabaseConfigDTOBuilder builder() {
        return new DatabaseConfigDTOBuilder();
    }

    public String getUrl() {
        return this.url;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean getEncrypted() {
        return this.encrypted;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseConfigDTO)) {
            return false;
        }
        DatabaseConfigDTO other = (DatabaseConfigDTO)o;
        Boolean this$encrypted = this.getEncrypted();
        Boolean other$encrypted = other.getEncrypted();
        if (this$encrypted == null ? other$encrypted != null : !((Object)this$encrypted).equals(other$encrypted)) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        String this$schemaName = this.getSchemaName();
        String other$schemaName = other.getSchemaName();
        if (this$schemaName == null ? other$schemaName != null : !this$schemaName.equals(other$schemaName)) {
            return false;
        }
        String this$login = this.getLogin();
        String other$login = other.getLogin();
        if (this$login == null ? other$login != null : !this$login.equals(other$login)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        return !(this$password == null ? other$password != null : !this$password.equals(other$password));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Boolean $encrypted = this.getEncrypted();
        result = result * 59 + ($encrypted == null ? 43 : ((Object)$encrypted).hashCode());
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        String $schemaName = this.getSchemaName();
        result = result * 59 + ($schemaName == null ? 43 : $schemaName.hashCode());
        String $login = this.getLogin();
        result = result * 59 + ($login == null ? 43 : $login.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "DatabaseConfigDTO(url=" + this.getUrl() + ", schemaName=" + this.getSchemaName() + ", login=" + this.getLogin() + ", password=" + this.getPassword() + ", encrypted=" + this.getEncrypted() + ")";
    }

    public DatabaseConfigDTO(String url2, String schemaName, String login, String password, Boolean encrypted) {
        this.url = url2;
        this.schemaName = schemaName;
        this.login = login;
        this.password = password;
        this.encrypted = encrypted;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class DatabaseConfigDTOBuilder {
        private String url;
        private String schemaName;
        private String login;
        private String password;
        private Boolean encrypted;

        DatabaseConfigDTOBuilder() {
        }

        @JsonProperty(value="url")
        public DatabaseConfigDTOBuilder url(String url2) {
            this.url = url2;
            return this;
        }

        @JsonProperty(value="schema_name")
        public DatabaseConfigDTOBuilder schemaName(String schemaName) {
            this.schemaName = schemaName;
            return this;
        }

        @JsonProperty(value="login")
        public DatabaseConfigDTOBuilder login(String login) {
            this.login = login;
            return this;
        }

        @JsonProperty(value="password")
        public DatabaseConfigDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        @JsonProperty(value="encrypted")
        public DatabaseConfigDTOBuilder encrypted(Boolean encrypted) {
            this.encrypted = encrypted;
            return this;
        }

        public DatabaseConfigDTO build() {
            return new DatabaseConfigDTO(this.url, this.schemaName, this.login, this.password, this.encrypted);
        }

        public String toString() {
            return "DatabaseConfigDTO.DatabaseConfigDTOBuilder(url=" + this.url + ", schemaName=" + this.schemaName + ", login=" + this.login + ", password=" + this.password + ", encrypted=" + this.encrypted + ")";
        }
    }
}

