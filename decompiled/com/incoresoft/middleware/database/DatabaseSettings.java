/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.database;

public class DatabaseSettings {
    private String url;
    private String schemaName;
    private String login;
    private String password;

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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseSettings)) {
            return false;
        }
        DatabaseSettings other = (DatabaseSettings)o;
        if (!other.canEqual(this)) {
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
        return other instanceof DatabaseSettings;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
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
        return "DatabaseSettings(url=" + this.getUrl() + ", schemaName=" + this.getSchemaName() + ", login=" + this.getLogin() + ", password=" + this.getPassword() + ")";
    }
}

