/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.JwtLocation;
import com.google.api.JwtLocationOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface AuthProviderOrBuilder
extends MessageOrBuilder {
    public String getId();

    public ByteString getIdBytes();

    public String getIssuer();

    public ByteString getIssuerBytes();

    public String getJwksUri();

    public ByteString getJwksUriBytes();

    public String getAudiences();

    public ByteString getAudiencesBytes();

    public String getAuthorizationUrl();

    public ByteString getAuthorizationUrlBytes();

    public List<JwtLocation> getJwtLocationsList();

    public JwtLocation getJwtLocations(int var1);

    public int getJwtLocationsCount();

    public List<? extends JwtLocationOrBuilder> getJwtLocationsOrBuilderList();

    public JwtLocationOrBuilder getJwtLocationsOrBuilder(int var1);
}

