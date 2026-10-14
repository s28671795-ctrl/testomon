/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licenseparams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.license.model.LPRLicenseType;

public final class ALPRLicenseParams {
    @JSONField(name="license_type")
    private final LPRLicenseType licenseType;

    public ALPRLicenseParams(LPRLicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public LPRLicenseType getLicenseType() {
        return this.licenseType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ALPRLicenseParams)) {
            return false;
        }
        ALPRLicenseParams other = (ALPRLicenseParams)o;
        LPRLicenseType this$licenseType = this.getLicenseType();
        LPRLicenseType other$licenseType = other.getLicenseType();
        return !(this$licenseType == null ? other$licenseType != null : !((Object)((Object)this$licenseType)).equals((Object)other$licenseType));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        LPRLicenseType $licenseType = this.getLicenseType();
        result = result * 59 + ($licenseType == null ? 43 : ((Object)((Object)$licenseType)).hashCode());
        return result;
    }

    public String toString() {
        return "ALPRLicenseParams(licenseType=" + String.valueOf((Object)this.getLicenseType()) + ")";
    }
}

