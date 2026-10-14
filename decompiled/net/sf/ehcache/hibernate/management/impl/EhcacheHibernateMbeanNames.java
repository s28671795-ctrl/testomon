/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public abstract class EhcacheHibernateMbeanNames {
    public static final String GROUP_ID = "net.sf.ehcache.hibernate";
    public static final String EHCACHE_HIBERNATE_TYPE = "EhcacheHibernateStats";

    public static String mbeanSafe(String s2) {
        return s2 == null ? "" : s2.replaceAll(",|:|=|\n", ".");
    }

    public static ObjectName getCacheManagerObjectName(String cacheManagerClusterUUID, String name) throws MalformedObjectNameException {
        ObjectName objectName = new ObjectName("net.sf.ehcache.hibernate:type=EhcacheHibernateStats,name=" + EhcacheHibernateMbeanNames.mbeanSafe(name) + EhcacheHibernateMbeanNames.getBeanNameSuffix(cacheManagerClusterUUID));
        return objectName;
    }

    private static String getBeanNameSuffix(String cacheManagerClusterUUID) {
        String suffix = "";
        if (!EhcacheHibernateMbeanNames.isBlank(cacheManagerClusterUUID)) {
            suffix = ",node=" + cacheManagerClusterUUID;
        }
        return suffix;
    }

    private static boolean isBlank(String param2) {
        return param2 == null || "".equals(param2.trim());
    }
}

