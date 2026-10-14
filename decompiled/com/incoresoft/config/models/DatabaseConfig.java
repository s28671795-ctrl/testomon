/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.DatabaseConfigDTO;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig {
    private String url;
    private String schemaName;
    private String login;
    private String password;
    private boolean encrypted;

    public DatabaseConfig(DatabaseConfigDTO dto) {
        this.url = dto.getUrl();
        this.schemaName = dto.getSchemaName();
        this.login = dto.getLogin();
        this.password = dto.getPassword();
        this.encrypted = dto.getEncrypted() != null ? dto.getEncrypted() : true;
    }

    public Map<String, String> convertToMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", this.url);
        map.put("login", this.login);
        map.put("password", this.password);
        if (this.schemaName != null) {
            map.put("schema_name", this.schemaName);
        }
        return Collections.unmodifiableMap(map);
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

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseConfig)) {
            return false;
        }
        DatabaseConfig other = (DatabaseConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isEncrypted() != other.isEncrypted()) {
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

    protected boolean canEqual(Object other) {
        return other instanceof DatabaseConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isEncrypted() ? 79 : 97);
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
        return "DatabaseConfig(url=" + this.getUrl() + ", schemaName=" + this.getSchemaName() + ", login=" + this.getLogin() + ", password=" + this.getPassword() + ", encrypted=" + this.isEncrypted() + ")";
    }

    public DatabaseConfig(String url2, String schemaName, String login, String password, boolean encrypted) {
        this.url = url2;
        this.schemaName = schemaName;
        this.login = login;
        this.password = password;
        this.encrypted = encrypted;
    }
}

