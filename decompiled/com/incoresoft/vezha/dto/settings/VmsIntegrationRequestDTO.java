/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.settings;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.vezha.dto.settings.validations.VmsVersionNotCompatible;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

@VmsVersionNotCompatible
public final class VmsIntegrationRequestDTO {
    @NotNull
    @JSONField(name="username")
    @Schema(description="Username for VMS integration. All actions that should be performed on behalf of the system in VMS, will be performed using this username.")
    private final String username;
    @NotNull
    @JSONField(name="password")
    @Schema(description="Password for provided user.")
    private final String password;
    @NotNull
    @JSONField(name="public_key")
    @Schema(description="Public key required for validating JWT tokens from VMS. Encoded in Base64.")
    private final String publicKey;
    @NotNull
    @JSONField(name="vms_version")
    @Schema(description="Version of VMS that is used for integration")
    private final Version vmsVersion;

    public VmsIntegrationRequestDTO(String username, String password, String publicKey, Version vmsVersion) {
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
        this.vmsVersion = vmsVersion;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public Version getVmsVersion() {
        return this.vmsVersion;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VmsIntegrationRequestDTO)) {
            return false;
        }
        VmsIntegrationRequestDTO other = (VmsIntegrationRequestDTO)o;
        String this$username = this.getUsername();
        String other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) {
            return false;
        }
        String this$publicKey = this.getPublicKey();
        String other$publicKey = other.getPublicKey();
        if (this$publicKey == null ? other$publicKey != null : !this$publicKey.equals(other$publicKey)) {
            return false;
        }
        Version this$vmsVersion = this.getVmsVersion();
        Version other$vmsVersion = other.getVmsVersion();
        return !(this$vmsVersion == null ? other$vmsVersion != null : !((Object)this$vmsVersion).equals(other$vmsVersion));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        String $publicKey = this.getPublicKey();
        result = result * 59 + ($publicKey == null ? 43 : $publicKey.hashCode());
        Version $vmsVersion = this.getVmsVersion();
        result = result * 59 + ($vmsVersion == null ? 43 : ((Object)$vmsVersion).hashCode());
        return result;
    }

    public String toString() {
        return "VmsIntegrationRequestDTO(username=" + this.getUsername() + ", password=" + this.getPassword() + ", publicKey=" + this.getPublicKey() + ", vmsVersion=" + String.valueOf(this.getVmsVersion()) + ")";
    }

    public static final class Fields {
        public static final String username = "username";
        public static final String password = "password";
        public static final String publicKey = "publicKey";
        public static final String vmsVersion = "vmsVersion";
    }
}

