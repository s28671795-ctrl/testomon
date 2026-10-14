/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import java.time.Instant;

public final class JwtAuthClaims {
    private final String email;
    private final Instant expiration;

    public JwtAuthClaims(String email, Instant expiration) {
        this.email = email;
        this.expiration = expiration;
    }

    public String getEmail() {
        return this.email;
    }

    public Instant getExpiration() {
        return this.expiration;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JwtAuthClaims)) {
            return false;
        }
        JwtAuthClaims other = (JwtAuthClaims)o;
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        Instant this$expiration = this.getExpiration();
        Instant other$expiration = other.getExpiration();
        return !(this$expiration == null ? other$expiration != null : !((Object)this$expiration).equals(other$expiration));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        Instant $expiration = this.getExpiration();
        result = result * 59 + ($expiration == null ? 43 : ((Object)$expiration).hashCode());
        return result;
    }

    public String toString() {
        return "JwtAuthClaims(email=" + this.getEmail() + ", expiration=" + String.valueOf(this.getExpiration()) + ")";
    }

    public static final class Fields {
        public static final String email = "email";
        public static final String expiration = "expiration";
    }
}

