/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.windowsshare;

import com.incoresoft.middleware.windowsshare.WindowsShareConfig;

public class WindowsShareConfigImpl
implements WindowsShareConfig {
    private final Integer id;
    private final String hostname;
    private final String username;
    private final String password;
    private final String domain;
    private final String shareName;

    public WindowsShareConfigImpl(Integer id, String hostname, String username, String password, String domain, String shareName) {
        this.id = id;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.domain = domain;
        this.shareName = shareName;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getHostname() {
        return this.hostname;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getDomain() {
        return this.domain;
    }

    @Override
    public String getShareName() {
        return this.shareName;
    }
}

