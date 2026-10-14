/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.EnumValue;
import akka.protobufv3.internal.EnumValueOrBuilder;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Option;
import akka.protobufv3.internal.OptionOrBuilder;
import akka.protobufv3.internal.SourceContext;
import akka.protobufv3.internal.SourceContextOrBuilder;
import akka.protobufv3.internal.Syntax;
import java.util.List;

public interface EnumOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public List<EnumValue> getEnumvalueList();

    public EnumValue getEnumvalue(int var1);

    public int getEnumvalueCount();

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList();

    public EnumValueOrBuilder getEnumvalueOrBuilder(int var1);

    public List<Option> getOptionsList();

    public Option getOptions(int var1);

    public int getOptionsCount();

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    public OptionOrBuilder getOptionsOrBuilder(int var1);

    public boolean hasSourceContext();

    public SourceContext getSourceContext();

    public SourceContextOrBuilder getSourceContextOrBuilder();

    public int getSyntaxValue();

    public Syntax getSyntax();
}

