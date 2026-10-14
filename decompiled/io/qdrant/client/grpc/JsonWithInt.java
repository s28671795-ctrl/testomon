/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.MapFieldBuilder;
import com.google.protobuf.MapFieldReflectionAccessor;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JsonWithInt {
    private static final Descriptors.Descriptor internal_static_qdrant_Struct_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_Struct_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_Struct_FieldsEntry_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_Struct_FieldsEntry_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_Value_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_Value_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_qdrant_ListValue_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_qdrant_ListValue_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private JsonWithInt() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        JsonWithInt.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static /* synthetic */ Descriptors.Descriptor access$200() {
        return internal_static_qdrant_Struct_FieldsEntry_descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0013json_with_int.proto\u0012\u0006qdrant\"r\n\u0006Struct\u0012*\n\u0006fields\u0018\u0001 \u0003(\u000b2\u001a.qdrant.Struct.FieldsEntry\u001a<\n\u000bFieldsEntry\u0012\u000b\n\u0003key\u0018\u0001 \u0001(\t\u0012\u001c\n\u0005value\u0018\u0002 \u0001(\u000b2\r.qdrant.Value:\u00028\u0001\"\u00e8\u0001\n\u0005Value\u0012'\n\nnull_value\u0018\u0001 \u0001(\u000e2\u0011.qdrant.NullValueH\u0000\u0012\u0016\n\fdouble_value\u0018\u0002 \u0001(\u0001H\u0000\u0012\u0017\n\rinteger_value\u0018\u0003 \u0001(\u0003H\u0000\u0012\u0016\n\fstring_value\u0018\u0004 \u0001(\tH\u0000\u0012\u0014\n\nbool_value\u0018\u0005 \u0001(\bH\u0000\u0012&\n\fstruct_value\u0018\u0006 \u0001(\u000b2\u000e.qdrant.StructH\u0000\u0012'\n\nlist_value\u0018\u0007 \u0001(\u000b2\u0011.qdrant.ListValueH\u0000B\u0006\n\u0004kind\"*\n\tListValue\u0012\u001d\n\u0006values\u0018\u0001 \u0003(\u000b2\r.qdrant.Value*\u001b\n\tNullValue\u0012\u000e\n\nNULL_VALUE\u0010\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_qdrant_Struct_descriptor = JsonWithInt.getDescriptor().getMessageTypes().get(0);
        internal_static_qdrant_Struct_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_Struct_descriptor, new String[]{"Fields"});
        internal_static_qdrant_Struct_FieldsEntry_descriptor = internal_static_qdrant_Struct_descriptor.getNestedTypes().get(0);
        internal_static_qdrant_Struct_FieldsEntry_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_Struct_FieldsEntry_descriptor, new String[]{"Key", "Value"});
        internal_static_qdrant_Value_descriptor = JsonWithInt.getDescriptor().getMessageTypes().get(1);
        internal_static_qdrant_Value_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_Value_descriptor, new String[]{"NullValue", "DoubleValue", "IntegerValue", "StringValue", "BoolValue", "StructValue", "ListValue", "Kind"});
        internal_static_qdrant_ListValue_descriptor = JsonWithInt.getDescriptor().getMessageTypes().get(2);
        internal_static_qdrant_ListValue_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_qdrant_ListValue_descriptor, new String[]{"Values"});
    }

    public static final class ListValue
    extends GeneratedMessageV3
    implements ListValueOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUES_FIELD_NUMBER = 1;
        private List<Value> values_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ListValue DEFAULT_INSTANCE = new ListValue();
        private static final Parser<ListValue> PARSER = new AbstractParser<ListValue>(){

            @Override
            public ListValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = ListValue.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private ListValue(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ListValue() {
            this.values_ = Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new ListValue();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_ListValue_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
        }

        @Override
        public List<Value> getValuesList() {
            return this.values_;
        }

        @Override
        public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
            return this.values_;
        }

        @Override
        public int getValuesCount() {
            return this.values_.size();
        }

        @Override
        public Value getValues(int index) {
            return this.values_.get(index);
        }

        @Override
        public ValueOrBuilder getValuesOrBuilder(int index) {
            return this.values_.get(index);
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.values_.size(); ++i) {
                output.writeMessage(1, this.values_.get(i));
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.values_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.values_.get(i));
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ListValue)) {
                return super.equals(obj);
            }
            ListValue other = (ListValue)obj;
            if (!this.getValuesList().equals(other.getValuesList())) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ListValue.getDescriptor().hashCode();
            if (this.getValuesCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValuesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static ListValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ListValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ListValue parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListValue parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ListValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ListValue parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ListValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ListValue.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ListValue prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static ListValue getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ListValue> parser() {
            return PARSER;
        }

        public Parser<ListValue> getParserForType() {
            return PARSER;
        }

        @Override
        public ListValue getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ListValueOrBuilder {
            private int bitField0_;
            private List<Value> values_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Value, Value.Builder, ValueOrBuilder> valuesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_ListValue_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                if (this.valuesBuilder_ == null) {
                    this.values_ = Collections.emptyList();
                } else {
                    this.values_ = null;
                    this.valuesBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_ListValue_descriptor;
            }

            @Override
            public ListValue getDefaultInstanceForType() {
                return ListValue.getDefaultInstance();
            }

            @Override
            public ListValue build() {
                ListValue result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ListValue buildPartial() {
                ListValue result = new ListValue(this);
                this.buildPartialRepeatedFields(result);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartialRepeatedFields(ListValue result) {
                if (this.valuesBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.values_ = Collections.unmodifiableList(this.values_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.values_ = this.values_;
                } else {
                    result.values_ = this.valuesBuilder_.build();
                }
            }

            private void buildPartial0(ListValue result) {
                int from_bitField0_ = this.bitField0_;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof ListValue) {
                    return this.mergeFrom((ListValue)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ListValue other) {
                if (other == ListValue.getDefaultInstance()) {
                    return this;
                }
                if (this.valuesBuilder_ == null) {
                    if (!other.values_.isEmpty()) {
                        if (this.values_.isEmpty()) {
                            this.values_ = other.values_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValuesIsMutable();
                            this.values_.addAll(other.values_);
                        }
                        this.onChanged();
                    }
                } else if (!other.values_.isEmpty()) {
                    if (this.valuesBuilder_.isEmpty()) {
                        this.valuesBuilder_.dispose();
                        this.valuesBuilder_ = null;
                        this.values_ = other.values_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valuesBuilder_ = alwaysUseFieldBuilders ? this.getValuesFieldBuilder() : null;
                    } else {
                        this.valuesBuilder_.addAllMessages(other.values_);
                    }
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 10: {
                                Value m4 = input.readMessage(Value.parser(), extensionRegistry);
                                if (this.valuesBuilder_ == null) {
                                    this.ensureValuesIsMutable();
                                    this.values_.add(m4);
                                    continue block9;
                                }
                                this.valuesBuilder_.addMessage(m4);
                                continue block9;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            private void ensureValuesIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.values_ = new ArrayList<Value>(this.values_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Value> getValuesList() {
                if (this.valuesBuilder_ == null) {
                    return Collections.unmodifiableList(this.values_);
                }
                return this.valuesBuilder_.getMessageList();
            }

            @Override
            public int getValuesCount() {
                if (this.valuesBuilder_ == null) {
                    return this.values_.size();
                }
                return this.valuesBuilder_.getCount();
            }

            @Override
            public Value getValues(int index) {
                if (this.valuesBuilder_ == null) {
                    return this.values_.get(index);
                }
                return this.valuesBuilder_.getMessage(index);
            }

            public Builder setValues(int index, Value value) {
                if (this.valuesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValuesIsMutable();
                    this.values_.set(index, value);
                    this.onChanged();
                } else {
                    this.valuesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValues(int index, Value.Builder builderForValue) {
                if (this.valuesBuilder_ == null) {
                    this.ensureValuesIsMutable();
                    this.values_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valuesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValues(Value value) {
                if (this.valuesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValuesIsMutable();
                    this.values_.add(value);
                    this.onChanged();
                } else {
                    this.valuesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValues(int index, Value value) {
                if (this.valuesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValuesIsMutable();
                    this.values_.add(index, value);
                    this.onChanged();
                } else {
                    this.valuesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValues(Value.Builder builderForValue) {
                if (this.valuesBuilder_ == null) {
                    this.ensureValuesIsMutable();
                    this.values_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valuesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValues(int index, Value.Builder builderForValue) {
                if (this.valuesBuilder_ == null) {
                    this.ensureValuesIsMutable();
                    this.values_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valuesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValues(Iterable<? extends Value> values2) {
                if (this.valuesBuilder_ == null) {
                    this.ensureValuesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.values_);
                    this.onChanged();
                } else {
                    this.valuesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValues() {
                if (this.valuesBuilder_ == null) {
                    this.values_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valuesBuilder_.clear();
                }
                return this;
            }

            public Builder removeValues(int index) {
                if (this.valuesBuilder_ == null) {
                    this.ensureValuesIsMutable();
                    this.values_.remove(index);
                    this.onChanged();
                } else {
                    this.valuesBuilder_.remove(index);
                }
                return this;
            }

            public Value.Builder getValuesBuilder(int index) {
                return this.getValuesFieldBuilder().getBuilder(index);
            }

            @Override
            public ValueOrBuilder getValuesOrBuilder(int index) {
                if (this.valuesBuilder_ == null) {
                    return this.values_.get(index);
                }
                return this.valuesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
                if (this.valuesBuilder_ != null) {
                    return this.valuesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.values_);
            }

            public Value.Builder addValuesBuilder() {
                return this.getValuesFieldBuilder().addBuilder(Value.getDefaultInstance());
            }

            public Value.Builder addValuesBuilder(int index) {
                return this.getValuesFieldBuilder().addBuilder(index, Value.getDefaultInstance());
            }

            public List<Value.Builder> getValuesBuilderList() {
                return this.getValuesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Value, Value.Builder, ValueOrBuilder> getValuesFieldBuilder() {
                if (this.valuesBuilder_ == null) {
                    this.valuesBuilder_ = new RepeatedFieldBuilderV3(this.values_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                    this.values_ = null;
                }
                return this.valuesBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ListValueOrBuilder
    extends MessageOrBuilder {
        public List<Value> getValuesList();

        public Value getValues(int var1);

        public int getValuesCount();

        public List<? extends ValueOrBuilder> getValuesOrBuilderList();

        public ValueOrBuilder getValuesOrBuilder(int var1);
    }

    public static final class Value
    extends GeneratedMessageV3
    implements ValueOrBuilder {
        private static final long serialVersionUID = 0L;
        private int kindCase_ = 0;
        private Object kind_;
        public static final int NULL_VALUE_FIELD_NUMBER = 1;
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 2;
        public static final int INTEGER_VALUE_FIELD_NUMBER = 3;
        public static final int STRING_VALUE_FIELD_NUMBER = 4;
        public static final int BOOL_VALUE_FIELD_NUMBER = 5;
        public static final int STRUCT_VALUE_FIELD_NUMBER = 6;
        public static final int LIST_VALUE_FIELD_NUMBER = 7;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Value DEFAULT_INSTANCE = new Value();
        private static final Parser<Value> PARSER = new AbstractParser<Value>(){

            @Override
            public Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Value.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private Value(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Value() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Value();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_Value_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
        }

        @Override
        public KindCase getKindCase() {
            return KindCase.forNumber(this.kindCase_);
        }

        @Override
        public boolean hasNullValue() {
            return this.kindCase_ == 1;
        }

        @Override
        public int getNullValueValue() {
            if (this.kindCase_ == 1) {
                return (Integer)this.kind_;
            }
            return 0;
        }

        @Override
        public NullValue getNullValue() {
            if (this.kindCase_ == 1) {
                NullValue result = NullValue.forNumber((Integer)this.kind_);
                return result == null ? NullValue.UNRECOGNIZED : result;
            }
            return NullValue.NULL_VALUE;
        }

        @Override
        public boolean hasDoubleValue() {
            return this.kindCase_ == 2;
        }

        @Override
        public double getDoubleValue() {
            if (this.kindCase_ == 2) {
                return (Double)this.kind_;
            }
            return 0.0;
        }

        @Override
        public boolean hasIntegerValue() {
            return this.kindCase_ == 3;
        }

        @Override
        public long getIntegerValue() {
            if (this.kindCase_ == 3) {
                return (Long)this.kind_;
            }
            return 0L;
        }

        @Override
        public boolean hasStringValue() {
            return this.kindCase_ == 4;
        }

        @Override
        public String getStringValue() {
            Object ref = "";
            if (this.kindCase_ == 4) {
                ref = this.kind_;
            }
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            if (this.kindCase_ == 4) {
                this.kind_ = s2;
            }
            return s2;
        }

        @Override
        public ByteString getStringValueBytes() {
            Object ref = "";
            if (this.kindCase_ == 4) {
                ref = this.kind_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.kindCase_ == 4) {
                    this.kind_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasBoolValue() {
            return this.kindCase_ == 5;
        }

        @Override
        public boolean getBoolValue() {
            if (this.kindCase_ == 5) {
                return (Boolean)this.kind_;
            }
            return false;
        }

        @Override
        public boolean hasStructValue() {
            return this.kindCase_ == 6;
        }

        @Override
        public Struct getStructValue() {
            if (this.kindCase_ == 6) {
                return (Struct)this.kind_;
            }
            return Struct.getDefaultInstance();
        }

        @Override
        public StructOrBuilder getStructValueOrBuilder() {
            if (this.kindCase_ == 6) {
                return (Struct)this.kind_;
            }
            return Struct.getDefaultInstance();
        }

        @Override
        public boolean hasListValue() {
            return this.kindCase_ == 7;
        }

        @Override
        public ListValue getListValue() {
            if (this.kindCase_ == 7) {
                return (ListValue)this.kind_;
            }
            return ListValue.getDefaultInstance();
        }

        @Override
        public ListValueOrBuilder getListValueOrBuilder() {
            if (this.kindCase_ == 7) {
                return (ListValue)this.kind_;
            }
            return ListValue.getDefaultInstance();
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.kindCase_ == 1) {
                output.writeEnum(1, (Integer)this.kind_);
            }
            if (this.kindCase_ == 2) {
                output.writeDouble(2, (Double)this.kind_);
            }
            if (this.kindCase_ == 3) {
                output.writeInt64(3, (Long)this.kind_);
            }
            if (this.kindCase_ == 4) {
                GeneratedMessageV3.writeString(output, 4, this.kind_);
            }
            if (this.kindCase_ == 5) {
                output.writeBool(5, (Boolean)this.kind_);
            }
            if (this.kindCase_ == 6) {
                output.writeMessage(6, (Struct)this.kind_);
            }
            if (this.kindCase_ == 7) {
                output.writeMessage(7, (ListValue)this.kind_);
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (this.kindCase_ == 1) {
                size += CodedOutputStream.computeEnumSize(1, (Integer)this.kind_);
            }
            if (this.kindCase_ == 2) {
                size += CodedOutputStream.computeDoubleSize(2, (Double)this.kind_);
            }
            if (this.kindCase_ == 3) {
                size += CodedOutputStream.computeInt64Size(3, (Long)this.kind_);
            }
            if (this.kindCase_ == 4) {
                size += GeneratedMessageV3.computeStringSize(4, this.kind_);
            }
            if (this.kindCase_ == 5) {
                size += CodedOutputStream.computeBoolSize(5, (Boolean)this.kind_);
            }
            if (this.kindCase_ == 6) {
                size += CodedOutputStream.computeMessageSize(6, (Struct)this.kind_);
            }
            if (this.kindCase_ == 7) {
                size += CodedOutputStream.computeMessageSize(7, (ListValue)this.kind_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Value)) {
                return super.equals(obj);
            }
            Value other = (Value)obj;
            if (!this.getKindCase().equals(other.getKindCase())) {
                return false;
            }
            switch (this.kindCase_) {
                case 1: {
                    if (this.getNullValueValue() == other.getNullValueValue()) break;
                    return false;
                }
                case 2: {
                    if (Double.doubleToLongBits(this.getDoubleValue()) == Double.doubleToLongBits(other.getDoubleValue())) break;
                    return false;
                }
                case 3: {
                    if (this.getIntegerValue() == other.getIntegerValue()) break;
                    return false;
                }
                case 4: {
                    if (this.getStringValue().equals(other.getStringValue())) break;
                    return false;
                }
                case 5: {
                    if (this.getBoolValue() == other.getBoolValue()) break;
                    return false;
                }
                case 6: {
                    if (this.getStructValue().equals(other.getStructValue())) break;
                    return false;
                }
                case 7: {
                    if (this.getListValue().equals(other.getListValue())) break;
                    return false;
                }
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Value.getDescriptor().hashCode();
            switch (this.kindCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getNullValueValue();
                    break;
                }
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getDoubleValue()));
                    break;
                }
                case 3: {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + Internal.hashLong(this.getIntegerValue());
                    break;
                }
                case 4: {
                    hash = 37 * hash + 4;
                    hash = 53 * hash + this.getStringValue().hashCode();
                    break;
                }
                case 5: {
                    hash = 37 * hash + 5;
                    hash = 53 * hash + Internal.hashBoolean(this.getBoolValue());
                    break;
                }
                case 6: {
                    hash = 37 * hash + 6;
                    hash = 53 * hash + this.getStructValue().hashCode();
                    break;
                }
                case 7: {
                    hash = 37 * hash + 7;
                    hash = 53 * hash + this.getListValue().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Value parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Value parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Value parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Value.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Value prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static Value getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Value> parser() {
            return PARSER;
        }

        public Parser<Value> getParserForType() {
            return PARSER;
        }

        @Override
        public Value getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ValueOrBuilder {
            private int kindCase_ = 0;
            private Object kind_;
            private int bitField0_;
            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> structValueBuilder_;
            private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> listValueBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_Value_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                if (this.structValueBuilder_ != null) {
                    this.structValueBuilder_.clear();
                }
                if (this.listValueBuilder_ != null) {
                    this.listValueBuilder_.clear();
                }
                this.kindCase_ = 0;
                this.kind_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_Value_descriptor;
            }

            @Override
            public Value getDefaultInstanceForType() {
                return Value.getDefaultInstance();
            }

            @Override
            public Value build() {
                Value result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Value buildPartial() {
                Value result = new Value(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.buildPartialOneofs(result);
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Value result) {
                int from_bitField0_ = this.bitField0_;
            }

            private void buildPartialOneofs(Value result) {
                result.kindCase_ = this.kindCase_;
                result.kind_ = this.kind_;
                if (this.kindCase_ == 6 && this.structValueBuilder_ != null) {
                    result.kind_ = this.structValueBuilder_.build();
                }
                if (this.kindCase_ == 7 && this.listValueBuilder_ != null) {
                    result.kind_ = this.listValueBuilder_.build();
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof Value) {
                    return this.mergeFrom((Value)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Value other) {
                if (other == Value.getDefaultInstance()) {
                    return this;
                }
                switch (other.getKindCase()) {
                    case NULL_VALUE: {
                        this.setNullValueValue(other.getNullValueValue());
                        break;
                    }
                    case DOUBLE_VALUE: {
                        this.setDoubleValue(other.getDoubleValue());
                        break;
                    }
                    case INTEGER_VALUE: {
                        this.setIntegerValue(other.getIntegerValue());
                        break;
                    }
                    case STRING_VALUE: {
                        this.kindCase_ = 4;
                        this.kind_ = other.kind_;
                        this.onChanged();
                        break;
                    }
                    case BOOL_VALUE: {
                        this.setBoolValue(other.getBoolValue());
                        break;
                    }
                    case STRUCT_VALUE: {
                        this.mergeStructValue(other.getStructValue());
                        break;
                    }
                    case LIST_VALUE: {
                        this.mergeListValue(other.getListValue());
                        break;
                    }
                }
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block15: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block15;
                            }
                            case 8: {
                                int rawValue = input.readEnum();
                                this.kindCase_ = 1;
                                this.kind_ = rawValue;
                                continue block15;
                            }
                            case 17: {
                                this.kind_ = input.readDouble();
                                this.kindCase_ = 2;
                                continue block15;
                            }
                            case 24: {
                                this.kind_ = input.readInt64();
                                this.kindCase_ = 3;
                                continue block15;
                            }
                            case 34: {
                                String s2 = input.readStringRequireUtf8();
                                this.kindCase_ = 4;
                                this.kind_ = s2;
                                continue block15;
                            }
                            case 40: {
                                this.kind_ = input.readBool();
                                this.kindCase_ = 5;
                                continue block15;
                            }
                            case 50: {
                                input.readMessage(this.getStructValueFieldBuilder().getBuilder(), extensionRegistry);
                                this.kindCase_ = 6;
                                continue block15;
                            }
                            case 58: {
                                input.readMessage(this.getListValueFieldBuilder().getBuilder(), extensionRegistry);
                                this.kindCase_ = 7;
                                continue block15;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            @Override
            public KindCase getKindCase() {
                return KindCase.forNumber(this.kindCase_);
            }

            public Builder clearKind() {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasNullValue() {
                return this.kindCase_ == 1;
            }

            @Override
            public int getNullValueValue() {
                if (this.kindCase_ == 1) {
                    return (Integer)this.kind_;
                }
                return 0;
            }

            public Builder setNullValueValue(int value) {
                this.kindCase_ = 1;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public NullValue getNullValue() {
                if (this.kindCase_ == 1) {
                    NullValue result = NullValue.forNumber((Integer)this.kind_);
                    return result == null ? NullValue.UNRECOGNIZED : result;
                }
                return NullValue.NULL_VALUE;
            }

            public Builder setNullValue(NullValue value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.kindCase_ = 1;
                this.kind_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearNullValue() {
                if (this.kindCase_ == 1) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
                return this;
            }

            @Override
            public boolean hasDoubleValue() {
                return this.kindCase_ == 2;
            }

            @Override
            public double getDoubleValue() {
                if (this.kindCase_ == 2) {
                    return (Double)this.kind_;
                }
                return 0.0;
            }

            public Builder setDoubleValue(double value) {
                this.kindCase_ = 2;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDoubleValue() {
                if (this.kindCase_ == 2) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
                return this;
            }

            @Override
            public boolean hasIntegerValue() {
                return this.kindCase_ == 3;
            }

            @Override
            public long getIntegerValue() {
                if (this.kindCase_ == 3) {
                    return (Long)this.kind_;
                }
                return 0L;
            }

            public Builder setIntegerValue(long value) {
                this.kindCase_ = 3;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearIntegerValue() {
                if (this.kindCase_ == 3) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
                return this;
            }

            @Override
            public boolean hasStringValue() {
                return this.kindCase_ == 4;
            }

            @Override
            public String getStringValue() {
                Object ref = "";
                if (this.kindCase_ == 4) {
                    ref = this.kind_;
                }
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    if (this.kindCase_ == 4) {
                        this.kind_ = s2;
                    }
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getStringValueBytes() {
                Object ref = "";
                if (this.kindCase_ == 4) {
                    ref = this.kind_;
                }
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    if (this.kindCase_ == 4) {
                        this.kind_ = b;
                    }
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setStringValue(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.kindCase_ = 4;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearStringValue() {
                if (this.kindCase_ == 4) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
                return this;
            }

            public Builder setStringValueBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Value.checkByteStringIsUtf8(value);
                this.kindCase_ = 4;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasBoolValue() {
                return this.kindCase_ == 5;
            }

            @Override
            public boolean getBoolValue() {
                if (this.kindCase_ == 5) {
                    return (Boolean)this.kind_;
                }
                return false;
            }

            public Builder setBoolValue(boolean value) {
                this.kindCase_ = 5;
                this.kind_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBoolValue() {
                if (this.kindCase_ == 5) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
                return this;
            }

            @Override
            public boolean hasStructValue() {
                return this.kindCase_ == 6;
            }

            @Override
            public Struct getStructValue() {
                if (this.structValueBuilder_ == null) {
                    if (this.kindCase_ == 6) {
                        return (Struct)this.kind_;
                    }
                    return Struct.getDefaultInstance();
                }
                if (this.kindCase_ == 6) {
                    return this.structValueBuilder_.getMessage();
                }
                return Struct.getDefaultInstance();
            }

            public Builder setStructValue(Struct value) {
                if (this.structValueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.kind_ = value;
                    this.onChanged();
                } else {
                    this.structValueBuilder_.setMessage(value);
                }
                this.kindCase_ = 6;
                return this;
            }

            public Builder setStructValue(Struct.Builder builderForValue) {
                if (this.structValueBuilder_ == null) {
                    this.kind_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.structValueBuilder_.setMessage(builderForValue.build());
                }
                this.kindCase_ = 6;
                return this;
            }

            public Builder mergeStructValue(Struct value) {
                if (this.structValueBuilder_ == null) {
                    this.kind_ = this.kindCase_ == 6 && this.kind_ != Struct.getDefaultInstance() ? Struct.newBuilder((Struct)this.kind_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else if (this.kindCase_ == 6) {
                    this.structValueBuilder_.mergeFrom(value);
                } else {
                    this.structValueBuilder_.setMessage(value);
                }
                this.kindCase_ = 6;
                return this;
            }

            public Builder clearStructValue() {
                if (this.structValueBuilder_ == null) {
                    if (this.kindCase_ == 6) {
                        this.kindCase_ = 0;
                        this.kind_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.kindCase_ == 6) {
                        this.kindCase_ = 0;
                        this.kind_ = null;
                    }
                    this.structValueBuilder_.clear();
                }
                return this;
            }

            public Struct.Builder getStructValueBuilder() {
                return this.getStructValueFieldBuilder().getBuilder();
            }

            @Override
            public StructOrBuilder getStructValueOrBuilder() {
                if (this.kindCase_ == 6 && this.structValueBuilder_ != null) {
                    return this.structValueBuilder_.getMessageOrBuilder();
                }
                if (this.kindCase_ == 6) {
                    return (Struct)this.kind_;
                }
                return Struct.getDefaultInstance();
            }

            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getStructValueFieldBuilder() {
                if (this.structValueBuilder_ == null) {
                    if (this.kindCase_ != 6) {
                        this.kind_ = Struct.getDefaultInstance();
                    }
                    this.structValueBuilder_ = new SingleFieldBuilderV3((Struct)this.kind_, this.getParentForChildren(), this.isClean());
                    this.kind_ = null;
                }
                this.kindCase_ = 6;
                this.onChanged();
                return this.structValueBuilder_;
            }

            @Override
            public boolean hasListValue() {
                return this.kindCase_ == 7;
            }

            @Override
            public ListValue getListValue() {
                if (this.listValueBuilder_ == null) {
                    if (this.kindCase_ == 7) {
                        return (ListValue)this.kind_;
                    }
                    return ListValue.getDefaultInstance();
                }
                if (this.kindCase_ == 7) {
                    return this.listValueBuilder_.getMessage();
                }
                return ListValue.getDefaultInstance();
            }

            public Builder setListValue(ListValue value) {
                if (this.listValueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.kind_ = value;
                    this.onChanged();
                } else {
                    this.listValueBuilder_.setMessage(value);
                }
                this.kindCase_ = 7;
                return this;
            }

            public Builder setListValue(ListValue.Builder builderForValue) {
                if (this.listValueBuilder_ == null) {
                    this.kind_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.listValueBuilder_.setMessage(builderForValue.build());
                }
                this.kindCase_ = 7;
                return this;
            }

            public Builder mergeListValue(ListValue value) {
                if (this.listValueBuilder_ == null) {
                    this.kind_ = this.kindCase_ == 7 && this.kind_ != ListValue.getDefaultInstance() ? ListValue.newBuilder((ListValue)this.kind_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else if (this.kindCase_ == 7) {
                    this.listValueBuilder_.mergeFrom(value);
                } else {
                    this.listValueBuilder_.setMessage(value);
                }
                this.kindCase_ = 7;
                return this;
            }

            public Builder clearListValue() {
                if (this.listValueBuilder_ == null) {
                    if (this.kindCase_ == 7) {
                        this.kindCase_ = 0;
                        this.kind_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.kindCase_ == 7) {
                        this.kindCase_ = 0;
                        this.kind_ = null;
                    }
                    this.listValueBuilder_.clear();
                }
                return this;
            }

            public ListValue.Builder getListValueBuilder() {
                return this.getListValueFieldBuilder().getBuilder();
            }

            @Override
            public ListValueOrBuilder getListValueOrBuilder() {
                if (this.kindCase_ == 7 && this.listValueBuilder_ != null) {
                    return this.listValueBuilder_.getMessageOrBuilder();
                }
                if (this.kindCase_ == 7) {
                    return (ListValue)this.kind_;
                }
                return ListValue.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> getListValueFieldBuilder() {
                if (this.listValueBuilder_ == null) {
                    if (this.kindCase_ != 7) {
                        this.kind_ = ListValue.getDefaultInstance();
                    }
                    this.listValueBuilder_ = new SingleFieldBuilderV3((ListValue)this.kind_, this.getParentForChildren(), this.isClean());
                    this.kind_ = null;
                }
                this.kindCase_ = 7;
                this.onChanged();
                return this.listValueBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }

        public static enum KindCase implements Internal.EnumLite,
        AbstractMessageLite.InternalOneOfEnum
        {
            NULL_VALUE(1),
            DOUBLE_VALUE(2),
            INTEGER_VALUE(3),
            STRING_VALUE(4),
            BOOL_VALUE(5),
            STRUCT_VALUE(6),
            LIST_VALUE(7),
            KIND_NOT_SET(0);

            private final int value;

            private KindCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static KindCase valueOf(int value) {
                return KindCase.forNumber(value);
            }

            public static KindCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return NULL_VALUE;
                    }
                    case 2: {
                        return DOUBLE_VALUE;
                    }
                    case 3: {
                        return INTEGER_VALUE;
                    }
                    case 4: {
                        return STRING_VALUE;
                    }
                    case 5: {
                        return BOOL_VALUE;
                    }
                    case 6: {
                        return STRUCT_VALUE;
                    }
                    case 7: {
                        return LIST_VALUE;
                    }
                    case 0: {
                        return KIND_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }
    }

    public static interface ValueOrBuilder
    extends MessageOrBuilder {
        public boolean hasNullValue();

        public int getNullValueValue();

        public NullValue getNullValue();

        public boolean hasDoubleValue();

        public double getDoubleValue();

        public boolean hasIntegerValue();

        public long getIntegerValue();

        public boolean hasStringValue();

        public String getStringValue();

        public ByteString getStringValueBytes();

        public boolean hasBoolValue();

        public boolean getBoolValue();

        public boolean hasStructValue();

        public Struct getStructValue();

        public StructOrBuilder getStructValueOrBuilder();

        public boolean hasListValue();

        public ListValue getListValue();

        public ListValueOrBuilder getListValueOrBuilder();

        public Value.KindCase getKindCase();
    }

    public static final class Struct
    extends GeneratedMessageV3
    implements StructOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int FIELDS_FIELD_NUMBER = 1;
        private MapField<String, Value> fields_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Struct DEFAULT_INSTANCE = new Struct();
        private static final Parser<Struct> PARSER = new AbstractParser<Struct>(){

            @Override
            public Struct parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Struct.newBuilder();
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (UninitializedMessageException e) {
                    throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };

        private Struct(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Struct() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Struct();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_qdrant_Struct_descriptor;
        }

        @Override
        protected MapFieldReflectionAccessor internalGetMapFieldReflection(int number) {
            switch (number) {
                case 1: {
                    return this.internalGetFields();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_qdrant_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
        }

        private MapField<String, Value> internalGetFields() {
            if (this.fields_ == null) {
                return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);
            }
            return this.fields_;
        }

        @Override
        public int getFieldsCount() {
            return this.internalGetFields().getMap().size();
        }

        @Override
        public boolean containsFields(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetFields().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, Value> getFields() {
            return this.getFieldsMap();
        }

        @Override
        public Map<String, Value> getFieldsMap() {
            return this.internalGetFields().getMap();
        }

        @Override
        public Value getFieldsOrDefault(String key, Value defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Value> map = this.internalGetFields().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public Value getFieldsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, Value> map = this.internalGetFields().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetFields(), FieldsDefaultEntryHolder.defaultEntry, 1);
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (Map.Entry<String, Value> entry : this.internalGetFields().getMap().entrySet()) {
                Message fields__ = ((MapEntry.Builder)FieldsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(1, fields__);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Struct)) {
                return super.equals(obj);
            }
            Struct other = (Struct)obj;
            if (!this.internalGetFields().equals(other.internalGetFields())) {
                return false;
            }
            return this.getUnknownFields().equals(other.getUnknownFields());
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Struct.getDescriptor().hashCode();
            if (!this.internalGetFields().getMap().isEmpty()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.internalGetFields().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Struct parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Struct parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Struct parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Struct parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Struct parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Struct parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Struct parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Struct parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Struct parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Struct parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Struct parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Struct parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Struct.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Struct prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static Struct getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Struct> parser() {
            return PARSER;
        }

        public Parser<Struct> getParserForType() {
            return PARSER;
        }

        @Override
        public Struct getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements StructOrBuilder {
            private int bitField0_;
            private static final FieldsConverter fieldsConverter = new FieldsConverter();
            private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> fields_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_qdrant_Struct_descriptor;
            }

            @Override
            protected MapFieldReflectionAccessor internalGetMapFieldReflection(int number) {
                switch (number) {
                    case 1: {
                        return this.internalGetFields();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected MapFieldReflectionAccessor internalGetMutableMapFieldReflection(int number) {
                switch (number) {
                    case 1: {
                        return this.internalGetMutableFields();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_qdrant_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
            }

            private Builder() {
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
            }

            @Override
            public Builder clear() {
                super.clear();
                this.bitField0_ = 0;
                this.internalGetMutableFields().clear();
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_qdrant_Struct_descriptor;
            }

            @Override
            public Struct getDefaultInstanceForType() {
                return Struct.getDefaultInstance();
            }

            @Override
            public Struct build() {
                Struct result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Struct buildPartial() {
                Struct result = new Struct(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Struct result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.fields_ = this.internalGetFields().build(FieldsDefaultEntryHolder.defaultEntry);
                }
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.setField(field2, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field2) {
                return (Builder)super.clearField(field2);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
                return (Builder)super.setRepeatedField(field2, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
                return (Builder)super.addRepeatedField(field2, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof Struct) {
                    return this.mergeFrom((Struct)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Struct other) {
                if (other == Struct.getDefaultInstance()) {
                    return this;
                }
                this.internalGetMutableFields().mergeFrom(other.internalGetFields());
                this.bitField0_ |= 1;
                this.mergeUnknownFields(other.getUnknownFields());
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                try {
                    boolean done = false;
                    block9: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block9;
                            }
                            case 10: {
                                MapEntry<String, Value> fields__ = input.readMessage(FieldsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableFields().ensureBuilderMap().put(fields__.getKey(), fields__.getValue());
                                this.bitField0_ |= 1;
                                continue block9;
                            }
                        }
                        if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                        done = true;
                    }
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.unwrapIOException();
                }
                finally {
                    this.onChanged();
                }
                return this;
            }

            private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> internalGetFields() {
                if (this.fields_ == null) {
                    return new MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder>(fieldsConverter);
                }
                return this.fields_;
            }

            private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> internalGetMutableFields() {
                if (this.fields_ == null) {
                    this.fields_ = new MapFieldBuilder(fieldsConverter);
                }
                this.bitField0_ |= 1;
                this.onChanged();
                return this.fields_;
            }

            @Override
            public int getFieldsCount() {
                return this.internalGetFields().ensureBuilderMap().size();
            }

            @Override
            public boolean containsFields(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetFields().ensureBuilderMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, Value> getFields() {
                return this.getFieldsMap();
            }

            @Override
            public Map<String, Value> getFieldsMap() {
                return this.internalGetFields().getImmutableMap();
            }

            @Override
            public Value getFieldsOrDefault(String key, Value defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, ValueOrBuilder> map = this.internalGetMutableFields().ensureBuilderMap();
                return map.containsKey(key) ? fieldsConverter.build(map.get(key)) : defaultValue;
            }

            @Override
            public Value getFieldsOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, ValueOrBuilder> map = this.internalGetMutableFields().ensureBuilderMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return fieldsConverter.build(map.get(key));
            }

            public Builder clearFields() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.internalGetMutableFields().clear();
                return this;
            }

            public Builder removeFields(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableFields().ensureBuilderMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, Value> getMutableFields() {
                this.bitField0_ |= 1;
                return this.internalGetMutableFields().ensureMessageMap();
            }

            public Builder putFields(String key, Value value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableFields().ensureBuilderMap().put(key, value);
                this.bitField0_ |= 1;
                return this;
            }

            public Builder putAllFields(Map<String, Value> values2) {
                for (Map.Entry<String, Value> e : values2.entrySet()) {
                    if (e.getKey() != null && e.getValue() != null) continue;
                    throw new NullPointerException();
                }
                this.internalGetMutableFields().ensureBuilderMap().putAll(values2);
                this.bitField0_ |= 1;
                return this;
            }

            public Value.Builder putFieldsBuilderIfAbsent(String key) {
                Map<String, ValueOrBuilder> builderMap = this.internalGetMutableFields().ensureBuilderMap();
                ValueOrBuilder entry = builderMap.get(key);
                if (entry == null) {
                    entry = Value.newBuilder();
                    builderMap.put(key, entry);
                }
                if (entry instanceof Value) {
                    entry = ((Value)entry).toBuilder();
                    builderMap.put(key, entry);
                }
                return (Value.Builder)entry;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }

            private static final class FieldsConverter
            implements MapFieldBuilder.Converter<String, ValueOrBuilder, Value> {
                private FieldsConverter() {
                }

                @Override
                public Value build(ValueOrBuilder val) {
                    if (val instanceof Value) {
                        return (Value)val;
                    }
                    return ((Value.Builder)val).build();
                }

                @Override
                public MapEntry<String, Value> defaultEntry() {
                    return FieldsDefaultEntryHolder.defaultEntry;
                }
            }
        }

        private static final class FieldsDefaultEntryHolder {
            static final MapEntry<String, Value> defaultEntry = MapEntry.newDefaultInstance(JsonWithInt.access$200(), WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

            private FieldsDefaultEntryHolder() {
            }
        }
    }

    public static interface StructOrBuilder
    extends MessageOrBuilder {
        public int getFieldsCount();

        public boolean containsFields(String var1);

        @Deprecated
        public Map<String, Value> getFields();

        public Map<String, Value> getFieldsMap();

        public Value getFieldsOrDefault(String var1, Value var2);

        public Value getFieldsOrThrow(String var1);
    }

    public static enum NullValue implements ProtocolMessageEnum
    {
        NULL_VALUE(0),
        UNRECOGNIZED(-1);

        public static final int NULL_VALUE_VALUE = 0;
        private static final Internal.EnumLiteMap<NullValue> internalValueMap;
        private static final NullValue[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static NullValue valueOf(int value) {
            return NullValue.forNumber(value);
        }

        public static NullValue forNumber(int value) {
            switch (value) {
                case 0: {
                    return NULL_VALUE;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<NullValue> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return NullValue.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return NullValue.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return JsonWithInt.getDescriptor().getEnumTypes().get(0);
        }

        public static NullValue valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != NullValue.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private NullValue(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<NullValue>(){

                @Override
                public NullValue findValueByNumber(int number) {
                    return NullValue.forNumber(number);
                }
            };
            VALUES = NullValue.values();
        }
    }
}

