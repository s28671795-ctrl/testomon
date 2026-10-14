/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Option;
import akka.protobufv3.internal.OptionOrBuilder;
import java.util.List;

public interface EnumValueOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public int getNumber();

    public List<Option> getOptionsList();

    public Option getOptions(int var1);

    public int getOptionsCount();

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    public OptionOrBuilder getOptionsOrBuilder(int var1);
}

