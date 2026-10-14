/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licenseparams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.license.licenseparams.ALPRLicenseParams;

public class LicenseParamsContainer {
    @JSONField(name="alpr")
    ALPRLicenseParams alpr;

    public ALPRLicenseParams getAlpr() {
        return this.alpr;
    }

    public void setAlpr(ALPRLicenseParams alpr) {
        this.alpr = alpr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseParamsContainer)) {
            return false;
        }
        LicenseParamsContainer other = (LicenseParamsContainer)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ALPRLicenseParams this$alpr = this.getAlpr();
        ALPRLicenseParams other$alpr = other.getAlpr();
        return !(this$alpr == null ? other$alpr != null : !((Object)this$alpr).equals(other$alpr));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LicenseParamsContainer;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ALPRLicenseParams $alpr = this.getAlpr();
        result = result * 59 + ($alpr == null ? 43 : ((Object)$alpr).hashCode());
        return result;
    }

    public String toString() {
        return "LicenseParamsContainer(alpr=" + String.valueOf(this.getAlpr()) + ")";
    }
}

