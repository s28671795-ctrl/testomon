/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientLibraryOrganization;
import com.google.api.ClientLibrarySettings;
import com.google.api.ClientLibrarySettingsOrBuilder;
import com.google.api.MethodSettings;
import com.google.api.MethodSettingsOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface PublishingOrBuilder
extends MessageOrBuilder {
    public List<MethodSettings> getMethodSettingsList();

    public MethodSettings getMethodSettings(int var1);

    public int getMethodSettingsCount();

    public List<? extends MethodSettingsOrBuilder> getMethodSettingsOrBuilderList();

    public MethodSettingsOrBuilder getMethodSettingsOrBuilder(int var1);

    public String getNewIssueUri();

    public ByteString getNewIssueUriBytes();

    public String getDocumentationUri();

    public ByteString getDocumentationUriBytes();

    public String getApiShortName();

    public ByteString getApiShortNameBytes();

    public String getGithubLabel();

    public ByteString getGithubLabelBytes();

    public List<String> getCodeownerGithubTeamsList();

    public int getCodeownerGithubTeamsCount();

    public String getCodeownerGithubTeams(int var1);

    public ByteString getCodeownerGithubTeamsBytes(int var1);

    public String getDocTagPrefix();

    public ByteString getDocTagPrefixBytes();

    public int getOrganizationValue();

    public ClientLibraryOrganization getOrganization();

    public List<ClientLibrarySettings> getLibrarySettingsList();

    public ClientLibrarySettings getLibrarySettings(int var1);

    public int getLibrarySettingsCount();

    public List<? extends ClientLibrarySettingsOrBuilder> getLibrarySettingsOrBuilderList();

    public ClientLibrarySettingsOrBuilder getLibrarySettingsOrBuilder(int var1);

    public String getProtoReferenceDocumentationUri();

    public ByteString getProtoReferenceDocumentationUriBytes();
}

