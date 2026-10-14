/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.license.License;
import com.incoresoft.license.model.LicenseStatus;

public record MetricsLicenseDTO(@JSONField(name="key") String key, @JSONField(name="status") LicenseStatus status) {
    public MetricsLicenseDTO(License license) {
        this(license.getLicenseKey(), license.getStatus());
    }
}

