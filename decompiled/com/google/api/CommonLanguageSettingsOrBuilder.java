/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientLibraryDestination;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface CommonLanguageSettingsOrBuilder
extends MessageOrBuilder {
    @Deprecated
    public String getReferenceDocsUri();

    @Deprecated
    public ByteString getReferenceDocsUriBytes();

    public List<ClientLibraryDestination> getDestinationsList();

    public int getDestinationsCount();

    public ClientLibraryDestination getDestinations(int var1);

    public List<Integer> getDestinationsValueList();

    public int getDestinationsValue(int var1);
}

