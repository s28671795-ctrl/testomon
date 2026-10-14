/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;

public class WSUsernameTokenPrincipalImpl
implements Serializable,
UsernameTokenPrincipal {
    private static final long serialVersionUID = 5608648208455259722L;
    private String name;
    private byte[] nonce;
    private transient String password;
    private String createdTime;
    private String passwordType;
    private boolean digest = false;

    public WSUsernameTokenPrincipalImpl(String name, boolean digest) {
        this.name = name;
        this.digest = digest;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isPasswordDigest() {
        return this.digest;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    @Override
    public byte[] getNonce() {
        return this.nonce;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public String toString() {
        return "WSUsernameTokenPrincipalImpl:  " + this.name;
    }

    @Override
    public String getPasswordType() {
        return this.passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsernameTokenPrincipal)) {
            return false;
        }
        UsernameTokenPrincipal that = (UsernameTokenPrincipal)o;
        if (this.digest != that.isPasswordDigest()) {
            return false;
        }
        if (this.name == null ? that.getName() != null : !this.name.equals(that.getName())) {
            return false;
        }
        if (this.nonce == null ? that.getNonce() != null : !Arrays.equals(this.nonce, that.getNonce())) {
            return false;
        }
        if (this.password == null ? that.getPassword() != null : !this.password.equals(that.getPassword())) {
            return false;
        }
        if (this.createdTime == null ? that.getCreatedTime() != null : !this.createdTime.equals(that.getCreatedTime())) {
            return false;
        }
        return !(this.passwordType == null ? that.getPasswordType() != null : !this.passwordType.equals(that.getPasswordType()));
    }

    @Override
    public int hashCode() {
        int hashcode = 17;
        hashcode = 31 * hashcode + (this.digest ? 1 : 0);
        hashcode = 31 * hashcode + (this.name == null ? 0 : this.name.hashCode());
        hashcode = 31 * hashcode + (this.nonce == null ? 0 : Arrays.hashCode(this.nonce));
        hashcode = 31 * hashcode + (this.password == null ? 0 : this.password.hashCode());
        hashcode = 31 * hashcode + (this.createdTime == null ? 0 : this.createdTime.hashCode());
        hashcode = 31 * hashcode + (this.passwordType == null ? 0 : this.passwordType.hashCode());
        return hashcode;
    }
}

