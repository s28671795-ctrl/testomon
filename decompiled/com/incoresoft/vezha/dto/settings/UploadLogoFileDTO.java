/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.settings;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="UploadLogoFileDTO", description="Upload logo file class structure")
public final class UploadLogoFileDTO {
    @JSONField(name="logo")
    @Schema(name="logo", description="Uploaded logo file", type="string", format="binary")
    private final String logoFile;

    public String getLogoFile() {
        return this.logoFile;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UploadLogoFileDTO)) {
            return false;
        }
        UploadLogoFileDTO other = (UploadLogoFileDTO)o;
        String this$logoFile = this.getLogoFile();
        String other$logoFile = other.getLogoFile();
        return !(this$logoFile == null ? other$logoFile != null : !this$logoFile.equals(other$logoFile));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $logoFile = this.getLogoFile();
        result = result * 59 + ($logoFile == null ? 43 : $logoFile.hashCode());
        return result;
    }

    public String toString() {
        return "UploadLogoFileDTO(logoFile=" + this.getLogoFile() + ")";
    }

    public UploadLogoFileDTO(String logoFile) {
        this.logoFile = logoFile;
    }
}

