/*
 * Decompiled with CFR 0.152.
 */
package com.google.protobuf;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldSet;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyField;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DynamicMessage
extends AbstractMessage {
    private final Descriptors.Descriptor type;
    private final FieldSet<Descriptors.FieldDescriptor> fields;
    private final Descriptors.FieldDescriptor[] oneofCases;
    private final UnknownFieldSet unknownFields;
    private int memoizedSize = -1;

    DynamicMessage(Descriptors.Descriptor type, FieldSet<Descriptors.FieldDescriptor> fields, Descriptors.FieldDescriptor[] oneofCases, UnknownFieldSet unknownFields) {
        this.type = type;
        this.fields = fields;
        this.oneofCases = oneofCases;
        this.unknownFields = unknownFields;
    }

    public static DynamicMessage getDefaultInstance(Descriptors.Descriptor type) {
        int oneofDeclCount = type.toProto().getOneofDeclCount();
        Descriptors.FieldDescriptor[] oneofCases = new Descriptors.FieldDescriptor[oneofDeclCount];
        return new DynamicMessage(type, FieldSet.emptySet(), oneofCases, UnknownFieldSet.getDefaultInstance());
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, CodedInputStream input) throws IOException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(input)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, CodedInputStream input, ExtensionRegistry extensionRegistry) throws IOException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(input, (ExtensionRegistryLite)extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, ByteString data) throws InvalidProtocolBufferException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(data)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, ByteString data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(data, (ExtensionRegistryLite)extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, byte[] data) throws InvalidProtocolBufferException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(data)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, byte[] data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(data, (ExtensionRegistryLite)extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, InputStream input) throws IOException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(input)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptors.Descriptor type, InputStream input, ExtensionRegistry extensionRegistry) throws IOException {
        return ((Builder)DynamicMessage.newBuilder(type).mergeFrom(input, (ExtensionRegistryLite)extensionRegistry)).buildParsed();
    }

    public static Builder newBuilder(Descriptors.Descriptor type) {
        return new Builder(type);
    }

    public static Builder newBuilder(Message prototype) {
        return new Builder(prototype.getDescriptorForType()).mergeFrom(prototype);
    }

    @Override
    public Descriptors.Descriptor getDescriptorForType() {
        return this.type;
    }

    @Override
    public DynamicMessage getDefaultInstanceForType() {
        return DynamicMessage.getDefaultInstance(this.type);
    }

    @Override
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return this.fields.getAllFields();
    }

    @Override
    public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
        this.verifyOneofContainingType(oneof);
        Descriptors.FieldDescriptor field2 = this.oneofCases[oneof.getIndex()];
        return field2 != null;
    }

    @Override
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
        this.verifyOneofContainingType(oneof);
        return this.oneofCases[oneof.getIndex()];
    }

    @Override
    public boolean hasField(Descriptors.FieldDescriptor field2) {
        this.verifyContainingType(field2);
        return this.fields.hasField(field2);
    }

    @Override
    public Object getField(Descriptors.FieldDescriptor field2) {
        this.verifyContainingType(field2);
        Object result = this.fields.getField(field2);
        if (result == null) {
            result = field2.isRepeated() ? Collections.emptyList() : (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? DynamicMessage.getDefaultInstance(field2.getMessageType()) : field2.getDefaultValue());
        }
        return result;
    }

    @Override
    public int getRepeatedFieldCount(Descriptors.FieldDescriptor field2) {
        this.verifyContainingType(field2);
        return this.fields.getRepeatedFieldCount(field2);
    }

    @Override
    public Object getRepeatedField(Descriptors.FieldDescriptor field2, int index) {
        this.verifyContainingType(field2);
        return this.fields.getRepeatedField(field2, index);
    }

    @Override
    public UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    static boolean isInitialized(Descriptors.Descriptor type, FieldSet<Descriptors.FieldDescriptor> fields) {
        for (Descriptors.FieldDescriptor field2 : type.getFields()) {
            if (!field2.isRequired() || fields.hasField(field2)) continue;
            return false;
        }
        return fields.isInitialized();
    }

    @Override
    public boolean isInitialized() {
        return DynamicMessage.isInitialized(this.type, this.fields);
    }

    @Override
    public void writeTo(CodedOutputStream output) throws IOException {
        if (this.type.getOptions().getMessageSetWireFormat()) {
            this.fields.writeMessageSetTo(output);
            this.unknownFields.writeAsMessageSetTo(output);
        } else {
            this.fields.writeTo(output);
            this.unknownFields.writeTo(output);
        }
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        if (this.type.getOptions().getMessageSetWireFormat()) {
            size = this.fields.getMessageSetSerializedSize();
            size += this.unknownFields.getSerializedSizeAsMessageSet();
        } else {
            size = this.fields.getSerializedSize();
            size += this.unknownFields.getSerializedSize();
        }
        this.memoizedSize = size;
        return size;
    }

    @Override
    public Builder newBuilderForType() {
        return new Builder(this.type);
    }

    @Override
    public Builder toBuilder() {
        return this.newBuilderForType().mergeFrom(this);
    }

    public Parser<DynamicMessage> getParserForType() {
        return new AbstractParser<DynamicMessage>(){

            @Override
            public DynamicMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DynamicMessage.newBuilder(DynamicMessage.this.type);
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e) {
                    throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };
    }

    private void verifyContainingType(Descriptors.FieldDescriptor field2) {
        if (field2.getContainingType() != this.type) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
        }
    }

    private void verifyOneofContainingType(Descriptors.OneofDescriptor oneof) {
        if (oneof.getContainingType() != this.type) {
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }
    }

    public static final class Builder
    extends AbstractMessage.Builder<Builder> {
        private final Descriptors.Descriptor type;
        private FieldSet.Builder<Descriptors.FieldDescriptor> fields;
        private final Descriptors.FieldDescriptor[] oneofCases;
        private UnknownFieldSet unknownFields;

        private Builder(Descriptors.Descriptor type) {
            this.type = type;
            this.fields = FieldSet.newBuilder();
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.oneofCases = new Descriptors.FieldDescriptor[type.toProto().getOneofDeclCount()];
        }

        @Override
        public Builder clear() {
            this.fields = FieldSet.newBuilder();
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            return this;
        }

        @Override
        public Builder mergeFrom(Message other) {
            if (other instanceof DynamicMessage) {
                DynamicMessage otherDynamicMessage = (DynamicMessage)other;
                if (otherDynamicMessage.type != this.type) {
                    throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
                }
                this.fields.mergeFrom(otherDynamicMessage.fields);
                this.mergeUnknownFields(otherDynamicMessage.unknownFields);
                for (int i = 0; i < this.oneofCases.length; ++i) {
                    if (this.oneofCases[i] == null) {
                        this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                        continue;
                    }
                    if (otherDynamicMessage.oneofCases[i] == null || this.oneofCases[i] == otherDynamicMessage.oneofCases[i]) continue;
                    this.fields.clearField(this.oneofCases[i]);
                    this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                }
                return this;
            }
            return (Builder)super.mergeFrom(other);
        }

        @Override
        public DynamicMessage build() {
            if (!this.isInitialized()) {
                throw Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields.build(), Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields));
            }
            return this.buildPartial();
        }

        private DynamicMessage buildParsed() throws InvalidProtocolBufferException {
            if (!this.isInitialized()) {
                throw Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields.build(), Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields)).asInvalidProtocolBufferException();
            }
            return this.buildPartial();
        }

        @Override
        public DynamicMessage buildPartial() {
            if (this.type.getOptions().getMapEntry()) {
                for (Descriptors.FieldDescriptor field2 : this.type.getFields()) {
                    if (!field2.isOptional() || this.fields.hasField(field2)) continue;
                    if (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        this.fields.setField(field2, DynamicMessage.getDefaultInstance(field2.getMessageType()));
                        continue;
                    }
                    this.fields.setField(field2, field2.getDefaultValue());
                }
            }
            DynamicMessage result = new DynamicMessage(this.type, this.fields.buildPartial(), Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields);
            return result;
        }

        @Override
        public Builder clone() {
            Builder result = new Builder(this.type);
            result.fields.mergeFrom(this.fields.build());
            result.mergeUnknownFields(this.unknownFields);
            System.arraycopy(this.oneofCases, 0, result.oneofCases, 0, this.oneofCases.length);
            return result;
        }

        @Override
        public boolean isInitialized() {
            for (Descriptors.FieldDescriptor field2 : this.type.getFields()) {
                if (!field2.isRequired() || this.fields.hasField(field2)) continue;
                return false;
            }
            return this.fields.isInitialized();
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.type;
        }

        @Override
        public DynamicMessage getDefaultInstanceForType() {
            return DynamicMessage.getDefaultInstance(this.type);
        }

        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return this.fields.getAllFields();
        }

        @Override
        public Builder newBuilderForField(Descriptors.FieldDescriptor field2) {
            this.verifyContainingType(field2);
            if (field2.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new IllegalArgumentException("newBuilderForField is only valid for fields with message type.");
            }
            return new Builder(field2.getMessageType());
        }

        @Override
        public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            Descriptors.FieldDescriptor field2 = this.oneofCases[oneof.getIndex()];
            return field2 != null;
        }

        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            return this.oneofCases[oneof.getIndex()];
        }

        @Override
        public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            Descriptors.FieldDescriptor field2 = this.oneofCases[oneof.getIndex()];
            if (field2 != null) {
                this.clearField(field2);
            }
            return this;
        }

        @Override
        public boolean hasField(Descriptors.FieldDescriptor field2) {
            this.verifyContainingType(field2);
            return this.fields.hasField(field2);
        }

        @Override
        public Object getField(Descriptors.FieldDescriptor field2) {
            this.verifyContainingType(field2);
            Object result = this.fields.getField(field2);
            if (result == null) {
                result = field2.isRepeated() ? Collections.emptyList() : (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? DynamicMessage.getDefaultInstance(field2.getMessageType()) : field2.getDefaultValue());
            }
            return result;
        }

        @Override
        public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
            this.verifyContainingType(field2);
            this.verifyType(field2, value);
            Descriptors.OneofDescriptor oneofDescriptor = field2.getContainingOneof();
            if (oneofDescriptor != null) {
                int index = oneofDescriptor.getIndex();
                Descriptors.FieldDescriptor oldField = this.oneofCases[index];
                if (oldField != null && oldField != field2) {
                    this.fields.clearField(oldField);
                }
                this.oneofCases[index] = field2;
            } else if (!field2.hasPresence() && !field2.isRepeated() && value.equals(field2.getDefaultValue())) {
                this.fields.clearField(field2);
                return this;
            }
            this.fields.setField(field2, value);
            return this;
        }

        @Override
        public Builder clearField(Descriptors.FieldDescriptor field2) {
            int index;
            this.verifyContainingType(field2);
            Descriptors.OneofDescriptor oneofDescriptor = field2.getContainingOneof();
            if (oneofDescriptor != null && this.oneofCases[index = oneofDescriptor.getIndex()] == field2) {
                this.oneofCases[index] = null;
            }
            this.fields.clearField(field2);
            return this;
        }

        @Override
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor field2) {
            this.verifyContainingType(field2);
            return this.fields.getRepeatedFieldCount(field2);
        }

        @Override
        public Object getRepeatedField(Descriptors.FieldDescriptor field2, int index) {
            this.verifyContainingType(field2);
            return this.fields.getRepeatedField(field2, index);
        }

        @Override
        public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
            this.verifyContainingType(field2);
            this.verifySingularValueType(field2, value);
            this.fields.setRepeatedField(field2, index, value);
            return this;
        }

        @Override
        public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
            this.verifyContainingType(field2);
            this.verifySingularValueType(field2, value);
            this.fields.addRepeatedField(field2, value);
            return this;
        }

        @Override
        public UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override
        public Builder setUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = unknownFields;
            return this;
        }

        @Override
        public Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build();
            return this;
        }

        private void verifyContainingType(Descriptors.FieldDescriptor field2) {
            if (field2.getContainingType() != this.type) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        private void verifyOneofContainingType(Descriptors.OneofDescriptor oneof) {
            if (oneof.getContainingType() != this.type) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
        }

        private void verifySingularValueType(Descriptors.FieldDescriptor field2, Object value) {
            switch (field2.getType()) {
                case ENUM: {
                    Internal.checkNotNull(value);
                    if (value instanceof Descriptors.EnumValueDescriptor) break;
                    throw new IllegalArgumentException("DynamicMessage should use EnumValueDescriptor to set Enum Value.");
                }
                case MESSAGE: {
                    if (!(value instanceof Message.Builder)) break;
                    throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[]{field2.getNumber(), field2.getLiteType().getJavaType(), value.getClass().getName()}));
                }
            }
        }

        private void verifyType(Descriptors.FieldDescriptor field2, Object value) {
            if (field2.isRepeated()) {
                for (Object item : (List)value) {
                    this.verifySingularValueType(field2, item);
                }
            } else {
                this.verifySingularValueType(field2, value);
            }
        }

        @Override
        public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field2) {
            this.verifyContainingType(field2);
            if (field2.isMapField()) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }
            if (field2.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
            Object existingValue = this.fields.getFieldAllowBuilders(field2);
            Message.Builder builder = existingValue == null ? new Builder(field2.getMessageType()) : Builder.toMessageBuilder(existingValue);
            this.fields.setField(field2, builder);
            return builder;
        }

        @Override
        public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor field2, int index) {
            this.verifyContainingType(field2);
            if (field2.isMapField()) {
                throw new UnsupportedOperationException("Map fields cannot be repeated");
            }
            if (field2.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            Message.Builder builder = Builder.toMessageBuilder(this.fields.getRepeatedFieldAllowBuilders(field2, index));
            this.fields.setRepeatedField(field2, index, builder);
            return builder;
        }

        private static Message.Builder toMessageBuilder(Object o) {
            if (o instanceof Message.Builder) {
                return (Message.Builder)o;
            }
            if (o instanceof LazyField) {
                o = ((LazyField)o).getValue();
            }
            if (o instanceof Message) {
                return ((Message)o).toBuilder();
            }
            throw new IllegalArgumentException(String.format("Cannot convert %s to Message.Builder", o.getClass()));
        }
    }
}

