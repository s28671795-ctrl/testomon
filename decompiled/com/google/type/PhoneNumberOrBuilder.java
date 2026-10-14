/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.type.PhoneNumber;

public interface PhoneNumberOrBuilder
extends MessageOrBuilder {
    public boolean hasE164Number();

    public String getE164Number();

    public ByteString getE164NumberBytes();

    public boolean hasShortCode();

    public PhoneNumber.ShortCode getShortCode();

    public PhoneNumber.ShortCodeOrBuilder getShortCodeOrBuilder();

    public String getExtension();

    public ByteString getExtensionBytes();

    public PhoneNumber.KindCase getKindCase();
}

