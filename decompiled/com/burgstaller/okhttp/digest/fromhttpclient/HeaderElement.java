/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;

public interface HeaderElement {
    public String getName();

    public String getValue();

    public NameValuePair[] getParameters();

    public NameValuePair getParameterByName(String var1);

    public int getParameterCount();

    public NameValuePair getParameter(int var1);
}

