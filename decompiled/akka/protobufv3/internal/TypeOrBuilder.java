/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.Field;
import akka.protobufv3.internal.FieldOrBuilder;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Option;
import akka.protobufv3.internal.OptionOrBuilder;
import akka.protobufv3.internal.SourceContext;
import akka.protobufv3.internal.SourceContextOrBuilder;
import akka.protobufv3.internal.Syntax;
import java.util.List;

public interface TypeOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public List<Field> getFieldsList();

    public Field getFields(int var1);

    public int getFieldsCount();

    public List<? extends FieldOrBuilder> getFieldsOrBuilderList();

    public FieldOrBuilder getFieldsOrBuilder(int var1);

    public List<String> getOneofsList();

    public int getOneofsCount();

    public String getOneofs(int var1);

    public ByteString getOneofsBytes(int var1);

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

