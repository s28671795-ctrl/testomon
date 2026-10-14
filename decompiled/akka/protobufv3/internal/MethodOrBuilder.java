/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Option;
import akka.protobufv3.internal.OptionOrBuilder;
import akka.protobufv3.internal.Syntax;
import java.util.List;

public interface MethodOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getRequestTypeUrl();

    public ByteString getRequestTypeUrlBytes();

    public boolean getRequestStreaming();

    public String getResponseTypeUrl();

    public ByteString getResponseTypeUrlBytes();

    public boolean getResponseStreaming();

    public List<Option> getOptionsList();

    public Option getOptions(int var1);

    public int getOptionsCount();

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    public OptionOrBuilder getOptionsOrBuilder(int var1);

    public int getSyntaxValue();

    public Syntax getSyntax();
}

