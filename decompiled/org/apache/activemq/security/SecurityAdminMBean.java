/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

public interface SecurityAdminMBean {
    public static final String OPERATION_READ = "read";
    public static final String OPERATION_WRITE = "write";
    public static final String OPERATION_ADMIN = "admin";

    public void addRole(String var1);

    public void removeRole(String var1);

    public void addUserRole(String var1, String var2);

    public void removeUserRole(String var1, String var2);

    public void addTopicRole(String var1, String var2, String var3);

    public void removeTopicRole(String var1, String var2, String var3);

    public void addQueueRole(String var1, String var2, String var3);

    public void removeQueueRole(String var1, String var2, String var3);
}

