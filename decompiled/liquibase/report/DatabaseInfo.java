/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Arrays;
import java.util.List;
import liquibase.util.UrlUtil;

public class DatabaseInfo {
    private static final int CONNECTION_URL_MAX_LENGTH = 128;
    public static final List<String> DB_URL_VISIBLE_KEYS = Arrays.asList("servername", "database", "databaseName");
    private String databaseType;
    private String version;
    private String databaseUrl;

    public String getVisibleDatabaseUrl() {
        return this.getVisibleUrl(this.databaseUrl);
    }

    protected String getVisibleUrl(String originalUrl) {
        if (originalUrl == null) {
            return "";
        }
        String modifiedUrl = originalUrl.length() >= 128 ? UrlUtil.handleSqlServerDbUrlParameters(DB_URL_VISIBLE_KEYS, originalUrl) : originalUrl;
        return this.appendEllipsisIfDifferent(originalUrl, this.hideConnectionUrlLength(modifiedUrl));
    }

    private String hideConnectionUrlLength(String url2) {
        if (url2.length() >= 128) {
            return url2.substring(0, 127);
        }
        return url2;
    }

    private String appendEllipsisIfDifferent(String originalUrl, String maybeModifiedUrl) {
        if (!originalUrl.equalsIgnoreCase(maybeModifiedUrl)) {
            return String.format("%s...", maybeModifiedUrl);
        }
        return originalUrl;
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public String getVersion() {
        return this.version;
    }

    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseInfo)) {
            return false;
        }
        DatabaseInfo other = (DatabaseInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$databaseType = this.getDatabaseType();
        String other$databaseType = other.getDatabaseType();
        if (this$databaseType == null ? other$databaseType != null : !this$databaseType.equals(other$databaseType)) {
            return false;
        }
        String this$version = this.getVersion();
        String other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) {
            return false;
        }
        String this$databaseUrl = this.getDatabaseUrl();
        String other$databaseUrl = other.getDatabaseUrl();
        return !(this$databaseUrl == null ? other$databaseUrl != null : !this$databaseUrl.equals(other$databaseUrl));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DatabaseInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $databaseType = this.getDatabaseType();
        result = result * 59 + ($databaseType == null ? 43 : $databaseType.hashCode());
        String $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        String $databaseUrl = this.getDatabaseUrl();
        result = result * 59 + ($databaseUrl == null ? 43 : $databaseUrl.hashCode());
        return result;
    }

    public String toString() {
        return "DatabaseInfo(databaseType=" + this.getDatabaseType() + ", version=" + this.getVersion() + ", databaseUrl=" + this.getDatabaseUrl() + ")";
    }
}

