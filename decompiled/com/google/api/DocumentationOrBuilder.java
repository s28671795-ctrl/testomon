/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.DocumentationRule;
import com.google.api.DocumentationRuleOrBuilder;
import com.google.api.Page;
import com.google.api.PageOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface DocumentationOrBuilder
extends MessageOrBuilder {
    public String getSummary();

    public ByteString getSummaryBytes();

    public List<Page> getPagesList();

    public Page getPages(int var1);

    public int getPagesCount();

    public List<? extends PageOrBuilder> getPagesOrBuilderList();

    public PageOrBuilder getPagesOrBuilder(int var1);

    public List<DocumentationRule> getRulesList();

    public DocumentationRule getRules(int var1);

    public int getRulesCount();

    public List<? extends DocumentationRuleOrBuilder> getRulesOrBuilderList();

    public DocumentationRuleOrBuilder getRulesOrBuilder(int var1);

    public String getDocumentationRootUrl();

    public ByteString getDocumentationRootUrlBytes();

    public String getServiceRootUrl();

    public ByteString getServiceRootUrlBytes();

    public String getOverview();

    public ByteString getOverviewBytes();
}

