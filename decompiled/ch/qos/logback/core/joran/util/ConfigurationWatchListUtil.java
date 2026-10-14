/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.joran.util;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.spi.ConfigurationWatchList;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.status.WarnStatus;
import java.net.URL;

public class ConfigurationWatchListUtil {
    static final ConfigurationWatchListUtil ORIGIN = new ConfigurationWatchListUtil();

    private ConfigurationWatchListUtil() {
    }

    public static void registerConfigurationWatchList(Context context, ConfigurationWatchList cwl) {
        context.putObject("CONFIGURATION_WATCH_LIST", cwl);
    }

    public static void setMainWatchURL(Context context, URL url2) {
        ConfigurationWatchList cwl = ConfigurationWatchListUtil.getConfigurationWatchList(context);
        if (cwl == null) {
            cwl = ConfigurationWatchListUtil.registerNewConfigurationWatchListWithContext(context);
        } else {
            cwl.clear();
        }
        cwl.setMainURL(url2);
    }

    public static boolean watchPredicateFulfilled(Context context) {
        ConfigurationWatchList cwl = ConfigurationWatchListUtil.getConfigurationWatchList(context);
        if (cwl == null) {
            return false;
        }
        return cwl.watchPredicateFulfilled();
    }

    public static URL getMainWatchURL(Context context) {
        ConfigurationWatchList cwl = ConfigurationWatchListUtil.getConfigurationWatchList(context);
        if (cwl == null) {
            return null;
        }
        return cwl.getMainURL();
    }

    public static void addToWatchList(Context context, URL url2) {
        ConfigurationWatchListUtil.addToWatchList(context, url2, false);
    }

    public static void addToWatchList(Context context, URL url2, boolean createCWL) {
        ConfigurationWatchList cwl = ConfigurationWatchListUtil.getConfigurationWatchList(context);
        if (cwl == null) {
            if (createCWL && ConfigurationWatchList.isWatchableProtocol(url2)) {
                cwl = ConfigurationWatchListUtil.registerNewConfigurationWatchListWithContext(context);
            } else {
                ConfigurationWatchListUtil.addWarn(context, "Null ConfigurationWatchList. Cannot add " + String.valueOf(url2));
                return;
            }
        }
        ConfigurationWatchListUtil.addInfo(context, "Adding [" + String.valueOf(url2) + "] to configuration watch list.");
        cwl.addToWatchList(url2);
    }

    private static ConfigurationWatchList registerNewConfigurationWatchListWithContext(Context context) {
        ConfigurationWatchList cwl = new ConfigurationWatchList();
        cwl.setContext(context);
        context.putObject("CONFIGURATION_WATCH_LIST", cwl);
        return cwl;
    }

    public static ConfigurationWatchList getConfigurationWatchList(Context context) {
        return (ConfigurationWatchList)context.getObject("CONFIGURATION_WATCH_LIST");
    }

    static void addStatus(Context context, Status s2) {
        if (context == null) {
            System.out.println("Null context in " + ConfigurationWatchList.class.getName());
            return;
        }
        StatusManager sm = context.getStatusManager();
        if (sm == null) {
            return;
        }
        sm.add(s2);
    }

    static void addInfo(Context context, String msg) {
        ConfigurationWatchListUtil.addStatus(context, new InfoStatus(msg, ORIGIN));
    }

    static void addWarn(Context context, String msg) {
        ConfigurationWatchListUtil.addStatus(context, new WarnStatus(msg, ORIGIN));
    }
}

