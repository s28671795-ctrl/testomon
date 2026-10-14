/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Method;
import akka.protobufv3.internal.MethodOrBuilder;
import akka.protobufv3.internal.Mixin;
import akka.protobufv3.internal.MixinOrBuilder;
import akka.protobufv3.internal.Option;
import akka.protobufv3.internal.OptionOrBuilder;
import akka.protobufv3.internal.SourceContext;
import akka.protobufv3.internal.SourceContextOrBuilder;
import akka.protobufv3.internal.Syntax;
import java.util.List;

public interface ApiOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public List<Method> getMethodsList();

    public Method getMethods(int var1);

    public int getMethodsCount();

    public List<? extends MethodOrBuilder> getMethodsOrBuilderList();

    public MethodOrBuilder getMethodsOrBuilder(int var1);

    public List<Option> getOptionsList();

    public Option getOptions(int var1);

    public int getOptionsCount();

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    public OptionOrBuilder getOptionsOrBuilder(int var1);

    public String getVersion();

    public ByteString getVersionBytes();

    public boolean hasSourceContext();

    public SourceContext getSourceContext();

    public SourceContextOrBuilder getSourceContextOrBuilder();

    public List<Mixin> getMixinsList();

    public Mixin getMixins(int var1);

    public int getMixinsCount();

    public List<? extends MixinOrBuilder> getMixinsOrBuilderList();

    public MixinOrBuilder getMixinsOrBuilder(int var1);

    public int getSyntaxValue();

    public Syntax getSyntax();
}

