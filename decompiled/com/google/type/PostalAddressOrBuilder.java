/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface PostalAddressOrBuilder
extends MessageOrBuilder {
    public int getRevision();

    public String getRegionCode();

    public ByteString getRegionCodeBytes();

    public String getLanguageCode();

    public ByteString getLanguageCodeBytes();

    public String getPostalCode();

    public ByteString getPostalCodeBytes();

    public String getSortingCode();

    public ByteString getSortingCodeBytes();

    public String getAdministrativeArea();

    public ByteString getAdministrativeAreaBytes();

    public String getLocality();

    public ByteString getLocalityBytes();

    public String getSublocality();

    public ByteString getSublocalityBytes();

    public List<String> getAddressLinesList();

    public int getAddressLinesCount();

    public String getAddressLines(int var1);

    public ByteString getAddressLinesBytes(int var1);

    public List<String> getRecipientsList();

    public int getRecipientsCount();

    public String getRecipients(int var1);

    public ByteString getRecipientsBytes(int var1);

    public String getOrganization();

    public ByteString getOrganizationBytes();
}

