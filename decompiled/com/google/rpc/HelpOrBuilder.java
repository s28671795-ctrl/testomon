/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.Help;
import java.util.List;

public interface HelpOrBuilder
extends MessageOrBuilder {
    public List<Help.Link> getLinksList();

    public Help.Link getLinks(int var1);

    public int getLinksCount();

    public List<? extends Help.LinkOrBuilder> getLinksOrBuilderList();

    public Help.LinkOrBuilder getLinksOrBuilder(int var1);
}

