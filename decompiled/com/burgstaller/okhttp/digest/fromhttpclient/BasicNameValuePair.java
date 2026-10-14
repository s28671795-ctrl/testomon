/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import com.burgstaller.okhttp.digest.fromhttpclient.LangUtils;
import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;
import java.io.Serializable;

public class BasicNameValuePair
implements NameValuePair,
Cloneable,
Serializable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;

    public BasicNameValuePair(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String toString() {
        if (this.value == null) {
            return this.name;
        }
        int len = this.name.length() + 1 + this.value.length();
        StringBuilder buffer = new StringBuilder(len);
        buffer.append(this.name);
        buffer.append("=");
        buffer.append(this.value);
        return buffer.toString();
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof NameValuePair)) {
            return false;
        }
        BasicNameValuePair that = (BasicNameValuePair)object;
        return this.name.equals(that.name) && this.value == that.value || this.value != null && this.value.equals(that.value);
    }

    public int hashCode() {
        int hash = 17;
        int hash1 = LangUtils.hashCode(hash, this.name);
        hash1 = LangUtils.hashCode(hash1, this.value);
        return hash1;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

