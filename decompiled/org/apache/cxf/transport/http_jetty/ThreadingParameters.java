/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty;

public class ThreadingParameters {
    private int minThreads;
    private int maxThreads;
    private String threadNamePrefix;
    private boolean minThreadsSet;
    private boolean maxThreadsSet;
    private boolean threadNamePrefixSet;

    public void setMinThreads(int number) {
        this.minThreadsSet = true;
        this.minThreads = number;
    }

    public void setMaxThreads(int number) {
        this.maxThreadsSet = true;
        this.maxThreads = number;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefixSet = true;
        this.threadNamePrefix = threadNamePrefix;
    }

    public int getMinThreads() {
        return this.minThreads;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public boolean isSetMaxThreads() {
        return this.maxThreadsSet;
    }

    public boolean isSetMinThreads() {
        return this.minThreadsSet;
    }

    public boolean isThreadNamePrefixSet() {
        return this.threadNamePrefixSet;
    }
}

