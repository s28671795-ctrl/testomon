/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.JwtLocation;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface JwtLocationOrBuilder
extends MessageOrBuilder {
    public boolean hasHeader();

    public String getHeader();

    public ByteString getHeaderBytes();

    public boolean hasQuery();

    public String getQuery();

    public ByteString getQueryBytes();

    public boolean hasCookie();

    public String getCookie();

    public ByteString getCookieBytes();

    public String getValuePrefix();

    public ByteString getValuePrefixBytes();

    public JwtLocation.InCase getInCase();
}

