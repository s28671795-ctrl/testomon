/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.onvif;

import com.incoresoft.onvif.models.OnvifMediaProfile;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="OnvifMediaProfileDTO", description="Onvif media profile class structure")
public final class OnvifMediaProfileDTO {
    @Schema(name="name", description="Onvif media profile name", implementation=String.class, example="Test stream")
    private final String name;
    @Schema(name="token", description="Onvif media profile token", implementation=String.class, example="Some onvif token")
    private final String token;

    public OnvifMediaProfileDTO(OnvifMediaProfile onvifMediaProfile) {
        this.name = onvifMediaProfile.getName();
        this.token = onvifMediaProfile.getToken();
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnvifMediaProfileDTO)) {
            return false;
        }
        OnvifMediaProfileDTO other = (OnvifMediaProfileDTO)o;
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$token = this.getToken();
        String other$token = other.getToken();
        return !(this$token == null ? other$token != null : !this$token.equals(other$token));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    public String toString() {
        return "OnvifMediaProfileDTO(name=" + this.getName() + ", token=" + this.getToken() + ")";
    }
}

