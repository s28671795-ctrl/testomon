/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import liquibase.util.LiquibaseUtil;
import liquibase.util.NetUtil;

public class RuntimeInfo {
    private final String systemUsername = System.getProperty("user.name");
    private final String hostname = NetUtil.getLocalHostName();
    private final String os = System.getProperty("os.name");
    private String interfaceType;
    private String startTime;
    private String updateDuration;
    private final String liquibaseVersion = LiquibaseUtil.getBuildVersionInfo();
    private String javaVersion = System.getProperty("java.version");

    public String getSystemUsername() {
        return this.systemUsername;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getOs() {
        return this.os;
    }

    public String getInterfaceType() {
        return this.interfaceType;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getUpdateDuration() {
        return this.updateDuration;
    }

    public String getLiquibaseVersion() {
        return this.liquibaseVersion;
    }

    public String getJavaVersion() {
        return this.javaVersion;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setUpdateDuration(String updateDuration) {
        this.updateDuration = updateDuration;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RuntimeInfo)) {
            return false;
        }
        RuntimeInfo other = (RuntimeInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$systemUsername = this.getSystemUsername();
        String other$systemUsername = other.getSystemUsername();
        if (this$systemUsername == null ? other$systemUsername != null : !this$systemUsername.equals(other$systemUsername)) {
            return false;
        }
        String this$hostname = this.getHostname();
        String other$hostname = other.getHostname();
        if (this$hostname == null ? other$hostname != null : !this$hostname.equals(other$hostname)) {
            return false;
        }
        String this$os = this.getOs();
        String other$os = other.getOs();
        if (this$os == null ? other$os != null : !this$os.equals(other$os)) {
            return false;
        }
        String this$interfaceType = this.getInterfaceType();
        String other$interfaceType = other.getInterfaceType();
        if (this$interfaceType == null ? other$interfaceType != null : !this$interfaceType.equals(other$interfaceType)) {
            return false;
        }
        String this$startTime = this.getStartTime();
        String other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) {
            return false;
        }
        String this$updateDuration = this.getUpdateDuration();
        String other$updateDuration = other.getUpdateDuration();
        if (this$updateDuration == null ? other$updateDuration != null : !this$updateDuration.equals(other$updateDuration)) {
            return false;
        }
        String this$liquibaseVersion = this.getLiquibaseVersion();
        String other$liquibaseVersion = other.getLiquibaseVersion();
        if (this$liquibaseVersion == null ? other$liquibaseVersion != null : !this$liquibaseVersion.equals(other$liquibaseVersion)) {
            return false;
        }
        String this$javaVersion = this.getJavaVersion();
        String other$javaVersion = other.getJavaVersion();
        return !(this$javaVersion == null ? other$javaVersion != null : !this$javaVersion.equals(other$javaVersion));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RuntimeInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $systemUsername = this.getSystemUsername();
        result = result * 59 + ($systemUsername == null ? 43 : $systemUsername.hashCode());
        String $hostname = this.getHostname();
        result = result * 59 + ($hostname == null ? 43 : $hostname.hashCode());
        String $os = this.getOs();
        result = result * 59 + ($os == null ? 43 : $os.hashCode());
        String $interfaceType = this.getInterfaceType();
        result = result * 59 + ($interfaceType == null ? 43 : $interfaceType.hashCode());
        String $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        String $updateDuration = this.getUpdateDuration();
        result = result * 59 + ($updateDuration == null ? 43 : $updateDuration.hashCode());
        String $liquibaseVersion = this.getLiquibaseVersion();
        result = result * 59 + ($liquibaseVersion == null ? 43 : $liquibaseVersion.hashCode());
        String $javaVersion = this.getJavaVersion();
        result = result * 59 + ($javaVersion == null ? 43 : $javaVersion.hashCode());
        return result;
    }

    public String toString() {
        return "RuntimeInfo(systemUsername=" + this.getSystemUsername() + ", hostname=" + this.getHostname() + ", os=" + this.getOs() + ", interfaceType=" + this.getInterfaceType() + ", startTime=" + this.getStartTime() + ", updateDuration=" + this.getUpdateDuration() + ", liquibaseVersion=" + this.getLiquibaseVersion() + ", javaVersion=" + this.getJavaVersion() + ")";
    }
}

