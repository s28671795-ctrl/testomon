/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionRegistry;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.FieldSet;
import akka.protobufv3.internal.GeneratedMessage;
import akka.protobufv3.internal.LazyField;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.UnknownFieldSet;
import akka.protobufv3.internal.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class MessageReflection {
    MessageReflection() {
    }

    static void writeMessageTo(Message message, Map<Descriptors.FieldDescriptor, Object> fields, CodedOutputStream output, boolean alwaysWriteRequiredFields) throws IOException {
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        if (alwaysWriteRequiredFields) {
            fields = new TreeMap<Descriptors.FieldDescriptor, Object>(fields);
            for (Descriptors.FieldDescriptor field2 : message.getDescriptorForType().getFields()) {
                if (!field2.isRequired() || fields.containsKey(field2)) continue;
                fields.put(field2, message.getField(field2));
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            Descriptors.FieldDescriptor field3 = entry.getKey();
            Object value = entry.getValue();
            if (isMessageSet && field3.isExtension() && field3.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !field3.isRepeated()) {
                output.writeMessageSetExtension(field3.getNumber(), (Message)value);
                continue;
            }
            FieldSet.writeField(field3, value, output);
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            unknownFields.writeAsMessageSetTo(output);
        } else {
            unknownFields.writeTo(output);
        }
    }

    static int getSerializedSize(Message message, Map<Descriptors.FieldDescriptor, Object> fields) {
        int size = 0;
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            Descriptors.FieldDescriptor field2 = entry.getKey();
            Object value = entry.getValue();
            if (isMessageSet && field2.isExtension() && field2.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !field2.isRepeated()) {
                size += CodedOutputStream.computeMessageSetExtensionSize(field2.getNumber(), (Message)value);
                continue;
            }
            size += FieldSet.computeFieldSize(field2, value);
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        size = isMessageSet ? (size += unknownFields.getSerializedSizeAsMessageSet()) : (size += unknownFields.getSerializedSize());
        return size;
    }

    static String delimitWithCommas(List<String> parts) {
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(part);
        }
        return result.toString();
    }

    static boolean isInitialized(MessageOrBuilder message) {
        for (Descriptors.FieldDescriptor fieldDescriptor : message.getDescriptorForType().getFields()) {
            if (!fieldDescriptor.isRequired() || message.hasField(fieldDescriptor)) continue;
            return false;
        }
        for (Map.Entry entry : message.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor field3 = (Descriptors.FieldDescriptor)entry.getKey();
            if (field3.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) continue;
            if (field3.isRepeated()) {
                for (Message element : (List)entry.getValue()) {
                    if (element.isInitialized()) continue;
                    return false;
                }
                continue;
            }
            if (((Message)entry.getValue()).isInitialized()) continue;
            return false;
        }
        return true;
    }

    private static String subMessagePrefix(String prefix, Descriptors.FieldDescriptor field2, int index) {
        StringBuilder result = new StringBuilder(prefix);
        if (field2.isExtension()) {
            result.append('(').append(field2.getFullName()).append(')');
        } else {
            result.append(field2.getName());
        }
        if (index != -1) {
            result.append('[').append(index).append(']');
        }
        result.append('.');
        return result.toString();
    }

    private static void findMissingFields(MessageOrBuilder message, String prefix, List<String> results) {
        for (Descriptors.FieldDescriptor fieldDescriptor : message.getDescriptorForType().getFields()) {
            if (!fieldDescriptor.isRequired() || message.hasField(fieldDescriptor)) continue;
            results.add(prefix + fieldDescriptor.getName());
        }
        for (Map.Entry entry : message.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor field3 = (Descriptors.FieldDescriptor)entry.getKey();
            Object value = entry.getValue();
            if (field3.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) continue;
            if (field3.isRepeated()) {
                int i = 0;
                for (Object element : (List)value) {
                    MessageReflection.findMissingFields((MessageOrBuilder)element, MessageReflection.subMessagePrefix(prefix, field3, i++), results);
                }
                continue;
            }
            if (!message.hasField(field3)) continue;
            MessageReflection.findMissingFields((MessageOrBuilder)value, MessageReflection.subMessagePrefix(prefix, field3, -1), results);
        }
    }

    static List<String> findMissingFields(MessageOrBuilder message) {
        ArrayList<String> results = new ArrayList<String>();
        MessageReflection.findMissingFields(message, "", results);
        return results;
    }

    static boolean mergeFieldFrom(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target, int tag) throws IOException {
        Descriptors.FieldDescriptor field2;
        if (type.getOptions().getMessageSetWireFormat() && tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
            MessageReflection.mergeMessageSetExtensionFromCodedStream(input, unknownFields, extensionRegistry, type, target);
            return true;
        }
        int wireType = WireFormat.getTagWireType(tag);
        int fieldNumber = WireFormat.getTagFieldNumber(tag);
        Message defaultInstance = null;
        if (type.isExtensionNumber(fieldNumber)) {
            if (extensionRegistry instanceof ExtensionRegistry) {
                ExtensionRegistry.ExtensionInfo extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, fieldNumber);
                if (extension == null) {
                    field2 = null;
                } else {
                    field2 = extension.descriptor;
                    defaultInstance = extension.defaultInstance;
                    if (defaultInstance == null && field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        throw new IllegalStateException("Message-typed extension lacked default instance: " + field2.getFullName());
                    }
                }
            } else {
                field2 = null;
            }
        } else {
            field2 = target.getContainerType() == MergeTarget.ContainerType.MESSAGE ? type.findFieldByNumber(fieldNumber) : null;
        }
        boolean unknown = false;
        boolean packed = false;
        if (field2 == null) {
            unknown = true;
        } else if (wireType == FieldSet.getWireFormatForFieldType(field2.getLiteType(), false)) {
            packed = false;
        } else if (field2.isPackable() && wireType == FieldSet.getWireFormatForFieldType(field2.getLiteType(), true)) {
            packed = true;
        } else {
            unknown = true;
        }
        if (unknown) {
            if (unknownFields != null) {
                return unknownFields.mergeFieldFrom(tag, input);
            }
            return input.skipField(tag);
        }
        if (packed) {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (field2.getLiteType() == WireFormat.FieldType.ENUM) {
                while (input.getBytesUntilLimit() > 0) {
                    int rawValue = input.readEnum();
                    if (field2.getFile().supportsUnknownEnumValue()) {
                        target.addRepeatedField(field2, field2.getEnumType().findValueByNumberCreatingIfUnknown(rawValue));
                        continue;
                    }
                    Descriptors.EnumValueDescriptor value = field2.getEnumType().findValueByNumber(rawValue);
                    if (value == null) {
                        if (unknownFields == null) continue;
                        unknownFields.mergeVarintField(fieldNumber, rawValue);
                        continue;
                    }
                    target.addRepeatedField(field2, value);
                }
            } else {
                while (input.getBytesUntilLimit() > 0) {
                    Object value = WireFormat.readPrimitiveField(input, field2.getLiteType(), target.getUtf8Validation(field2));
                    target.addRepeatedField(field2, value);
                }
            }
            input.popLimit(limit);
        } else {
            Object value;
            switch (field2.getType()) {
                case GROUP: {
                    value = target.parseGroup(input, extensionRegistry, field2, defaultInstance);
                    break;
                }
                case MESSAGE: {
                    value = target.parseMessage(input, extensionRegistry, field2, defaultInstance);
                    break;
                }
                case ENUM: {
                    int rawValue = input.readEnum();
                    if (field2.getFile().supportsUnknownEnumValue()) {
                        value = field2.getEnumType().findValueByNumberCreatingIfUnknown(rawValue);
                        break;
                    }
                    value = field2.getEnumType().findValueByNumber(rawValue);
                    if (value != null) break;
                    if (unknownFields != null) {
                        unknownFields.mergeVarintField(fieldNumber, rawValue);
                    }
                    return true;
                }
                default: {
                    value = WireFormat.readPrimitiveField(input, field2.getLiteType(), target.getUtf8Validation(field2));
                }
            }
            if (field2.isRepeated()) {
                target.addRepeatedField(field2, value);
            } else {
                target.setField(field2, value);
            }
        }
        return true;
    }

    private static void mergeMessageSetExtensionFromCodedStream(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target) throws IOException {
        int tag;
        int typeId = 0;
        ByteString rawBytes = null;
        ExtensionRegistry.ExtensionInfo extension = null;
        while ((tag = input.readTag()) != 0) {
            if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                typeId = input.readUInt32();
                if (typeId == 0 || !(extensionRegistry instanceof ExtensionRegistry)) continue;
                extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, typeId);
                continue;
            }
            if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (typeId != 0 && extension != null && ExtensionRegistryLite.isEagerlyParseMessageSets()) {
                    MessageReflection.eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, target);
                    rawBytes = null;
                    continue;
                }
                rawBytes = input.readBytes();
                continue;
            }
            if (input.skipField(tag)) continue;
            break;
        }
        input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
        if (rawBytes != null && typeId != 0) {
            if (extension != null) {
                MessageReflection.mergeMessageSetExtensionFromBytes(rawBytes, extension, extensionRegistry, target);
            } else if (rawBytes != null && unknownFields != null) {
                unknownFields.mergeField(typeId, UnknownFieldSet.Field.newBuilder().addLengthDelimited(rawBytes).build());
            }
        }
    }

    private static void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        Descriptors.FieldDescriptor field2 = extension.descriptor;
        boolean hasOriginalValue = target.hasField(field2);
        if (hasOriginalValue || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            Object value = target.parseMessageFromBytes(rawBytes, extensionRegistry, field2, extension.defaultInstance);
            target.setField(field2, value);
        } else {
            LazyField lazyField = new LazyField(extension.defaultInstance, extensionRegistry, rawBytes);
            target.setField(field2, lazyField);
        }
    }

    private static void eagerlyMergeMessageSetExtension(CodedInputStream input, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        Descriptors.FieldDescriptor field2 = extension.descriptor;
        Object value = target.parseMessage(input, extensionRegistry, field2, extension.defaultInstance);
        target.setField(field2, value);
    }

    static class ExtensionAdapter
    implements MergeTarget {
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        ExtensionAdapter(FieldSet<Descriptors.FieldDescriptor> extensions) {
            this.extensions = extensions;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
        }

        @Override
        public Object getField(Descriptors.FieldDescriptor field2) {
            return this.extensions.getField(field2);
        }

        @Override
        public boolean hasField(Descriptors.FieldDescriptor field2) {
            return this.extensions.hasField(field2);
        }

        @Override
        public MergeTarget setField(Descriptors.FieldDescriptor field2, Object value) {
            this.extensions.setField(field2, value);
            return this;
        }

        @Override
        public MergeTarget clearField(Descriptors.FieldDescriptor field2) {
            this.extensions.clearField(field2);
            return this;
        }

        @Override
        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
            this.extensions.setRepeatedField(field2, index, value);
            return this;
        }

        @Override
        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
            this.extensions.addRepeatedField(field2, value);
            return this;
        }

        @Override
        public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
            return false;
        }

        @Override
        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
            return this;
        }

        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
            return null;
        }

        @Override
        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.EXTENSION_SET;
        }

        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        @Override
        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readGroup(field2.getNumber(), subBuilder, registry);
            return subBuilder.buildPartial();
        }

        @Override
        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readMessage(subBuilder, registry);
            return subBuilder.buildPartial();
        }

        @Override
        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            subBuilder.mergeFrom(bytes, registry);
            return subBuilder.buildPartial();
        }

        @Override
        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
            throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
        }

        @Override
        public MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
            throw new UnsupportedOperationException("newEmptyTargetForField() called on FieldSet object");
        }

        @Override
        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }

        @Override
        public Object finish() {
            throw new UnsupportedOperationException("finish() called on FieldSet object");
        }
    }

    static class BuilderAdapter
    implements MergeTarget {
        private final Message.Builder builder;

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.builder.getDescriptorForType();
        }

        public BuilderAdapter(Message.Builder builder) {
            this.builder = builder;
        }

        @Override
        public Object getField(Descriptors.FieldDescriptor field2) {
            return this.builder.getField(field2);
        }

        @Override
        public boolean hasField(Descriptors.FieldDescriptor field2) {
            return this.builder.hasField(field2);
        }

        @Override
        public MergeTarget setField(Descriptors.FieldDescriptor field2, Object value) {
            this.builder.setField(field2, value);
            return this;
        }

        @Override
        public MergeTarget clearField(Descriptors.FieldDescriptor field2) {
            this.builder.clearField(field2);
            return this;
        }

        @Override
        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
            this.builder.setRepeatedField(field2, index, value);
            return this;
        }

        @Override
        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
            this.builder.addRepeatedField(field2, value);
            return this;
        }

        @Override
        public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
            return this.builder.hasOneof(oneof);
        }

        @Override
        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
            this.builder.clearOneof(oneof);
            return this;
        }

        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
            return this.builder.getOneofFieldDescriptor(oneof);
        }

        @Override
        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.MESSAGE;
        }

        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        @Override
        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance != null ? defaultInstance.newBuilderForType() : this.builder.newBuilderForField(field2);
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readGroup(field2.getNumber(), subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        @Override
        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance != null ? defaultInstance.newBuilderForType() : this.builder.newBuilderForField(field2);
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readMessage(subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        @Override
        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field2, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance != null ? defaultInstance.newBuilderForType() : this.builder.newBuilderForField(field2);
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            subBuilder.mergeFrom(bytes, extensionRegistry);
            return subBuilder.buildPartial();
        }

        @Override
        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor field2, Message defaultInstance) {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance != null ? defaultInstance.newBuilderForType() : this.builder.newBuilderForField(field2);
            if (!field2.isRepeated() && (originalMessage = (Message)this.getField(field2)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            return new BuilderAdapter(subBuilder);
        }

        @Override
        public MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor field2, Message defaultInstance) {
            Message.Builder subBuilder = defaultInstance != null ? defaultInstance.newBuilderForType() : this.builder.newBuilderForField(field2);
            return new BuilderAdapter(subBuilder);
        }

        @Override
        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            if (!descriptor.isRepeated() && this.builder instanceof GeneratedMessage.Builder) {
                return WireFormat.Utf8Validation.LAZY;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }

        @Override
        public Object finish() {
            return this.builder.buildPartial();
        }
    }

    static interface MergeTarget {
        public Descriptors.Descriptor getDescriptorForType();

        public ContainerType getContainerType();

        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry var1, String var2);

        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry var1, Descriptors.Descriptor var2, int var3);

        public Object getField(Descriptors.FieldDescriptor var1);

        public boolean hasField(Descriptors.FieldDescriptor var1);

        public MergeTarget setField(Descriptors.FieldDescriptor var1, Object var2);

        public MergeTarget clearField(Descriptors.FieldDescriptor var1);

        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor var1, int var2, Object var3);

        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor var1, Object var2);

        public boolean hasOneof(Descriptors.OneofDescriptor var1);

        public MergeTarget clearOneof(Descriptors.OneofDescriptor var1);

        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor var1);

        public Object parseGroup(CodedInputStream var1, ExtensionRegistryLite var2, Descriptors.FieldDescriptor var3, Message var4) throws IOException;

        public Object parseMessage(CodedInputStream var1, ExtensionRegistryLite var2, Descriptors.FieldDescriptor var3, Message var4) throws IOException;

        public Object parseMessageFromBytes(ByteString var1, ExtensionRegistryLite var2, Descriptors.FieldDescriptor var3, Message var4) throws IOException;

        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor var1);

        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor var1, Message var2);

        public MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor var1, Message var2);

        public Object finish();

        public static enum ContainerType {
            MESSAGE,
            EXTENSION_SET;

        }
    }
}

