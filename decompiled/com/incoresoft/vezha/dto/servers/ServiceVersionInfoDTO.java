/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;

public class ServiceVersionInfoDTO {
    @JSONField(name="service")
    String service;
    @JSONField(name="version")
    String version;

    public String getService() {
        return this.service;
    }

    public String getVersion() {
        return this.version;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

