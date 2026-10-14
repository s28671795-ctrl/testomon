/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.validations.DatabaseSchemaSpecificationShouldBeValid;
import com.incoresoft.middleware.database.DBType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

@DatabaseSchemaSpecificationShouldBeValid
@JsonDeserialize(builder=SetupDatabaseConfigDTOBuilder.class)
public final class SetupDatabaseConfigDTO {
    @JsonProperty(value="type")
    @Schema(name="type", description="Database type", implementation=DBType.class)
    @NotNull
    private final DBType type;
    @JsonProperty(value="host")
    @Schema(name="host", description="Database host", example="localhost", implementation=String.class)
    @NotNull
    private final String host;
    @JsonProperty(value="database_name")
    @Schema(name="database_name", description="Database name", example="vms", implementation=String.class)
    @NotNull
    private final String databaseName;
    @JsonProperty(value="schema_name")
    @Schema(name="schema_name", description="Database schema name", example="public", implementation=String.class)
    private final String schemaName;
    @JsonProperty(value="login")
    @Schema(name="login", description="Database login", example="postgres", implementation=String.class)
    @NotNull
    private final String login;
    @JsonProperty(value="password")
    @Schema(name="password", description="Database password", example="postgres", implementation=String.class)
    @NotNull
    private final String password;

    public static SetupDatabaseConfigDTOBuilder builder() {
        return new SetupDatabaseConfigDTOBuilder();
    }

    public DBType getType() {
        return this.type;
    }

    public String getHost() {
        return this.host;
    }

    public String getDatabaseName() {
        return this.databaseName;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SetupDatabaseConfigDTO)) {
            return false;
        }
        SetupDatabaseConfigDTO other = (SetupDatabaseConfigDTO)o;
        DBType this$type = this.getType();
        DBType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        if (this$host == null ? other$host != null : !this$host.equals(other$host)) {
            return false;
        }
        String this$databaseName = this.getDatabaseName();
        String other$databaseName = other.getDatabaseName();
        if (this$databaseName == null ? other$databaseName != null : !this$databaseName.equals(other$databaseName)) {
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
        DBType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        String $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        String $databaseName = this.getDatabaseName();
        result = result * 59 + ($databaseName == null ? 43 : $databaseName.hashCode());
        String $schemaName = this.getSchemaName();
        result = result * 59 + ($schemaName == null ? 43 : $schemaName.hashCode());
        String $login = this.getLogin();
        result = result * 59 + ($login == null ? 43 : $login.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "SetupDatabaseConfigDTO(type=" + String.valueOf((Object)this.getType()) + ", host=" + this.getHost() + ", databaseName=" + this.getDatabaseName() + ", schemaName=" + this.getSchemaName() + ", login=" + this.getLogin() + ", password=" + this.getPassword() + ")";
    }

    public SetupDatabaseConfigDTO(DBType type, String host2, String databaseName, String schemaName, String login, String password) {
        this.type = type;
        this.host = host2;
        this.databaseName = databaseName;
        this.schemaName = schemaName;
        this.login = login;
        this.password = password;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class SetupDatabaseConfigDTOBuilder {
        private DBType type;
        private String host;
        private String databaseName;
        private String schemaName;
        private String login;
        private String password;

        SetupDatabaseConfigDTOBuilder() {
        }

        @JsonProperty(value="type")
        public SetupDatabaseConfigDTOBuilder type(DBType type) {
            this.type = type;
            return this;
        }

        @JsonProperty(value="host")
        public SetupDatabaseConfigDTOBuilder host(String host2) {
            this.host = host2;
            return this;
        }

        @JsonProperty(value="database_name")
        public SetupDatabaseConfigDTOBuilder databaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        @JsonProperty(value="schema_name")
        public SetupDatabaseConfigDTOBuilder schemaName(String schemaName) {
            this.schemaName = schemaName;
            return this;
        }

        @JsonProperty(value="login")
        public SetupDatabaseConfigDTOBuilder login(String login) {
            this.login = login;
            return this;
        }

        @JsonProperty(value="password")
        public SetupDatabaseConfigDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SetupDatabaseConfigDTO build() {
            return new SetupDatabaseConfigDTO(this.type, this.host, this.databaseName, this.schemaName, this.login, this.password);
        }

        public String toString() {
            return "SetupDatabaseConfigDTO.SetupDatabaseConfigDTOBuilder(type=" + String.valueOf((Object)this.type) + ", host=" + this.host + ", databaseName=" + this.databaseName + ", schemaName=" + this.schemaName + ", login=" + this.login + ", password=" + this.password + ")";
        }
    }

    public static final class Fields {
        public static final String type = "type";
        public static final String host = "host";
        public static final String databaseName = "databaseName";
        public static final String schemaName = "schemaName";
        public static final String login = "login";
        public static final String password = "password";
    }
}

