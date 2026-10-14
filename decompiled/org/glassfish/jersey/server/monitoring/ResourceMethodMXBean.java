/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

public interface ResourceMethodMXBean {
    public String getMethodName();

    public String getPath();

    public String getHttpMethod();

    public String getDeclaringClassName();

    public String getConsumesMediaType();

    public String getProducesMediaType();
}

