/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.FrameworkUtil
 */
package liquibase.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import liquibase.Scope;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class LiquibaseUtil {
    private static Properties liquibaseBuildProperties;

    public static String getBuildVersion() {
        return LiquibaseUtil.getBuildInfo("build.version");
    }

    public static String getBuildVersionInfo() {
        String version = LiquibaseUtil.getBuildInfo("build.version");
        if (version.equals("DEV")) {
            String buildCommit = LiquibaseUtil.getBuildInfo("build.commit");
            if (buildCommit.equals("unknown")) {
                version = "[local build]";
            } else {
                version = "[Core: " + LiquibaseUtil.getBuildInfo("build.repository.owner") + "/" + LiquibaseUtil.getBuildInfo("build.repository.name") + "/" + LiquibaseUtil.getBuildInfo("build.branch") + "/" + LiquibaseUtil.getBuildInfo("build.number") + "/" + buildCommit.substring(0, 6) + "/" + LiquibaseUtil.getBuildInfo("build.timestamp");
                if (!LiquibaseUtil.getBuildInfo("build.pro.number").equals("UNKNOWN")) {
                    version = version + ", Pro: " + LiquibaseUtil.getBuildInfo("build.pro.branch") + "/" + LiquibaseUtil.getBuildInfo("build.pro.number") + "/" + LiquibaseUtil.getBuildInfo("build.pro.commit").substring(0, 6) + "/" + LiquibaseUtil.getBuildInfo("build.pro.timestamp");
                }
                version = version + "]";
            }
        }
        return version;
    }

    public static String getBuildTime() {
        return LiquibaseUtil.getBuildInfo("build.timestamp");
    }

    public static String getBuildNumber() {
        return LiquibaseUtil.getBuildInfo("build.number");
    }

    private static String getBuildInfo(String propertyId) {
        if (liquibaseBuildProperties == null) {
            Boolean osgiPlatform = (Boolean)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.osgiPlatform, Boolean.class));
            if (Boolean.TRUE.equals(osgiPlatform)) {
                Bundle bundle2 = FrameworkUtil.getBundle(LiquibaseUtil.class);
                URL propURL = bundle2.getEntry("liquibase.build.properties");
                if (propURL == null) {
                    Scope.getCurrentScope().getLog(LiquibaseUtil.class).severe("Cannot read liquibase.build.properties");
                } else {
                    try (InputStream buildProperties = propURL.openStream();){
                        liquibaseBuildProperties = new Properties();
                        if (buildProperties != null) {
                            liquibaseBuildProperties.load(buildProperties);
                        }
                    }
                    catch (IOException e) {
                        Scope.getCurrentScope().getLog(LiquibaseUtil.class).severe("Cannot read liquibase.build.properties", e);
                    }
                }
            } else {
                try {
                    liquibaseBuildProperties = new Properties();
                    Enumeration<URL> propertiesUrls = Scope.getCurrentScope().getClassLoader().getResources("liquibase.build.properties");
                    while (propertiesUrls.hasMoreElements()) {
                        URL url2 = propertiesUrls.nextElement();
                        InputStream buildProperties = url2.openStream();
                        try {
                            if (buildProperties == null) continue;
                            liquibaseBuildProperties.load(buildProperties);
                        }
                        finally {
                            if (buildProperties == null) continue;
                            buildProperties.close();
                        }
                    }
                }
                catch (IOException e) {
                    Scope.getCurrentScope().getLog(LiquibaseUtil.class).severe("Cannot read liquibase.build.properties", e);
                }
            }
        }
        String value = null;
        if (liquibaseBuildProperties != null) {
            value = liquibaseBuildProperties.getProperty(propertyId);
        }
        if (value == null) {
            value = "UNKNOWN";
        }
        return value;
    }
}

