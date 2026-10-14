/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.license;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder=SavedLicenseDTOBuilder.class)
public final class SavedLicenseDTO {
    @JsonProperty(value="license_key")
    @JSONField(name="license_key")
    private final String licenseKey;
    @JsonProperty(value="activated_license_text")
    @JSONField(name="activated_license_text")
    private final String activatedLicenseText;

    public static SavedLicenseDTOBuilder builder() {
        return new SavedLicenseDTOBuilder();
    }

    public String getLicenseKey() {
        return this.licenseKey;
    }

    public String getActivatedLicenseText() {
        return this.activatedLicenseText;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SavedLicenseDTO)) {
            return false;
        }
        SavedLicenseDTO other = (SavedLicenseDTO)o;
        String this$licenseKey = this.getLicenseKey();
        String other$licenseKey = other.getLicenseKey();
        if (this$licenseKey == null ? other$licenseKey != null : !this$licenseKey.equals(other$licenseKey)) {
            return false;
        }
        String this$activatedLicenseText = this.getActivatedLicenseText();
        String other$activatedLicenseText = other.getActivatedLicenseText();
        return !(this$activatedLicenseText == null ? other$activatedLicenseText != null : !this$activatedLicenseText.equals(other$activatedLicenseText));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $licenseKey = this.getLicenseKey();
        result = result * 59 + ($licenseKey == null ? 43 : $licenseKey.hashCode());
        String $activatedLicenseText = this.getActivatedLicenseText();
        result = result * 59 + ($activatedLicenseText == null ? 43 : $activatedLicenseText.hashCode());
        return result;
    }

    public String toString() {
        return "SavedLicenseDTO(licenseKey=" + this.getLicenseKey() + ", activatedLicenseText=" + this.getActivatedLicenseText() + ")";
    }

    public SavedLicenseDTO(String licenseKey, String activatedLicenseText) {
        this.licenseKey = licenseKey;
        this.activatedLicenseText = activatedLicenseText;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class SavedLicenseDTOBuilder {
        private String licenseKey;
        private String activatedLicenseText;

        SavedLicenseDTOBuilder() {
        }

        @JsonProperty(value="license_key")
        public SavedLicenseDTOBuilder licenseKey(String licenseKey) {
            this.licenseKey = licenseKey;
            return this;
        }

        @JsonProperty(value="activated_license_text")
        public SavedLicenseDTOBuilder activatedLicenseText(String activatedLicenseText) {
            this.activatedLicenseText = activatedLicenseText;
            return this;
        }

        public SavedLicenseDTO build() {
            return new SavedLicenseDTO(this.licenseKey, this.activatedLicenseText);
        }

        public String toString() {
            return "SavedLicenseDTO.SavedLicenseDTOBuilder(licenseKey=" + this.licenseKey + ", activatedLicenseText=" + this.activatedLicenseText + ")";
        }
    }
}

