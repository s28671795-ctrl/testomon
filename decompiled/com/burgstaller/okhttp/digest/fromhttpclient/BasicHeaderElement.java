/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import com.burgstaller.okhttp.digest.fromhttpclient.HeaderElement;
import com.burgstaller.okhttp.digest.fromhttpclient.LangUtils;
import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;

public class BasicHeaderElement
implements HeaderElement,
Cloneable {
    private final String name;
    private final String value;
    private final NameValuePair[] parameters;

    public BasicHeaderElement(String name, String value, NameValuePair[] parameters) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
        this.parameters = parameters != null ? parameters : new NameValuePair[0];
    }

    public BasicHeaderElement(String name, String value) {
        this(name, value, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public NameValuePair[] getParameters() {
        return (NameValuePair[])this.parameters.clone();
    }

    @Override
    public int getParameterCount() {
        return this.parameters.length;
    }

    @Override
    public NameValuePair getParameter(int index) {
        return this.parameters[index];
    }

    @Override
    public NameValuePair getParameterByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        NameValuePair found = null;
        for (int i = 0; i < this.parameters.length; ++i) {
            NameValuePair current = this.parameters[i];
            if (!current.getName().equalsIgnoreCase(name)) continue;
            found = current;
            break;
        }
        return found;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof HeaderElement)) {
            return false;
        }
        BasicHeaderElement that = (BasicHeaderElement)object;
        return this.name.equals(that.name) && LangUtils.equals(this.value, that.value) && LangUtils.equals(this.parameters, that.parameters);
    }

    public int hashCode() {
        int hash = 17;
        int var3 = LangUtils.hashCode(hash, this.name);
        var3 = LangUtils.hashCode(var3, this.value);
        for (int i = 0; i < this.parameters.length; ++i) {
            var3 = LangUtils.hashCode(var3, this.parameters[i]);
        }
        return var3;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(this.name);
        if (this.value != null) {
            buffer.append("=");
            buffer.append(this.value);
        }
        for (int i = 0; i < this.parameters.length; ++i) {
            buffer.append("; ");
            buffer.append(this.parameters[i]);
        }
        return buffer.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

