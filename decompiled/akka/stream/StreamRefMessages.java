/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.protobufv3.internal.AbstractParser;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionRegistry;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.GeneratedMessageV3;
import akka.protobufv3.internal.Internal;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Parser;
import akka.protobufv3.internal.SingleFieldBuilderV3;
import akka.protobufv3.internal.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class StreamRefMessages {
    private static final Descriptors.Descriptor internal_static_SinkRef_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_SinkRef_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_SourceRef_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_SourceRef_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_ActorRef_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_ActorRef_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Payload_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Payload_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_OnSubscribeHandshake_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_OnSubscribeHandshake_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_CumulativeDemand_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_CumulativeDemand_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_SequencedOnNext_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_SequencedOnNext_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_RemoteStreamFailure_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_RemoteStreamFailure_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_RemoteStreamCompleted_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_RemoteStreamCompleted_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private StreamRefMessages() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
        StreamRefMessages.registerAllExtensions((ExtensionRegistryLite)extensionRegistry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] stringArray = new String[]{"\n\u0017StreamRefMessages.proto\"'\n\u0007SinkRef\u0012\u001c\n\ttargetRef\u0018\u0001 \u0002(\u000b2\t.ActorRef\")\n\tSourceRef\u0012\u001c\n\toriginRef\u0018\u0001 \u0002(\u000b2\t.ActorRef\"\u0018\n\bActorRef\u0012\f\n\u0004path\u0018\u0001 \u0002(\t\"Q\n\u0007Payload\u0012\u0017\n\u000fenclosedMessage\u0018\u0001 \u0002(\f\u0012\u0014\n\fserializerId\u0018\u0002 \u0002(\u0005\u0012\u0017\n\u000fmessageManifest\u0018\u0003 \u0001(\f\"4\n\u0014OnSubscribeHandshake\u0012\u001c\n\ttargetRef\u0018\u0001 \u0002(\u000b2\t.ActorRef\"!\n\u0010CumulativeDemand\u0012\r\n\u0005seqNr\u0018\u0001 \u0002(\u0003\";\n\u000fSequencedOnNext\u0012\r\n\u0005seqNr\u0018\u0001 \u0002(\u0003\u0012\u0019\n\u0007payload\u0018\u0002 \u0002(\u000b2\b.Payload\"$\n\u0013RemoteStreamFailure\u0012\r\n\u0005cause\u0018\u0001 \u0001(\f\"&\n\u0015RemoteStreamCompleted\u0012\r\n\u0005seqNr\u0018\u0001 \u0002(\u0003B\u000f\n\u000bakka.streamH\u0001"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(stringArray, new Descriptors.FileDescriptor[0]);
        internal_static_SinkRef_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(0);
        internal_static_SinkRef_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_SinkRef_descriptor, new String[]{"TargetRef"});
        internal_static_SourceRef_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(1);
        internal_static_SourceRef_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_SourceRef_descriptor, new String[]{"OriginRef"});
        internal_static_ActorRef_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(2);
        internal_static_ActorRef_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_ActorRef_descriptor, new String[]{"Path"});
        internal_static_Payload_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(3);
        internal_static_Payload_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_Payload_descriptor, new String[]{"EnclosedMessage", "SerializerId", "MessageManifest"});
        internal_static_OnSubscribeHandshake_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(4);
        internal_static_OnSubscribeHandshake_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_OnSubscribeHandshake_descriptor, new String[]{"TargetRef"});
        internal_static_CumulativeDemand_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(5);
        internal_static_CumulativeDemand_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_CumulativeDemand_descriptor, new String[]{"SeqNr"});
        internal_static_SequencedOnNext_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(6);
        internal_static_SequencedOnNext_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_SequencedOnNext_descriptor, new String[]{"SeqNr", "Payload"});
        internal_static_RemoteStreamFailure_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(7);
        internal_static_RemoteStreamFailure_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_RemoteStreamFailure_descriptor, new String[]{"Cause"});
        internal_static_RemoteStreamCompleted_descriptor = StreamRefMessages.getDescriptor().getMessageTypes().get(8);
        internal_static_RemoteStreamCompleted_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_RemoteStreamCompleted_descriptor, new String[]{"SeqNr"});
    }

    public static final class RemoteStreamCompleted
    extends GeneratedMessageV3
    implements RemoteStreamCompletedOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SEQNR_FIELD_NUMBER = 1;
        private long seqNr_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RemoteStreamCompleted DEFAULT_INSTANCE = new RemoteStreamCompleted();
        @Deprecated
        public static final Parser<RemoteStreamCompleted> PARSER = new AbstractParser<RemoteStreamCompleted>(){

            @Override
            public RemoteStreamCompleted parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new RemoteStreamCompleted(codedInputStream, extensionRegistryLite);
            }
        };

        private RemoteStreamCompleted(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RemoteStreamCompleted() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new RemoteStreamCompleted();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RemoteStreamCompleted(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.seqNr_ = codedInputStream.readInt64();
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_RemoteStreamCompleted_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_RemoteStreamCompleted_fieldAccessorTable.ensureFieldAccessorsInitialized(RemoteStreamCompleted.class, Builder.class);
        }

        @Override
        public boolean hasSeqNr() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public long getSeqNr() {
            return this.seqNr_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasSeqNr()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt64(1, this.seqNr_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeInt64Size(1, this.seqNr_);
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof RemoteStreamCompleted)) {
                return super.equals(object);
            }
            RemoteStreamCompleted remoteStreamCompleted = (RemoteStreamCompleted)object;
            if (this.hasSeqNr() != remoteStreamCompleted.hasSeqNr()) {
                return false;
            }
            if (this.hasSeqNr() && this.getSeqNr() != remoteStreamCompleted.getSeqNr()) {
                return false;
            }
            return this.unknownFields.equals(remoteStreamCompleted.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + RemoteStreamCompleted.getDescriptor().hashCode();
            if (this.hasSeqNr()) {
                n = 37 * n + 1;
                n = 53 * n + Internal.hashLong(this.getSeqNr());
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static RemoteStreamCompleted parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static RemoteStreamCompleted parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static RemoteStreamCompleted parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static RemoteStreamCompleted parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static RemoteStreamCompleted parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static RemoteStreamCompleted parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static RemoteStreamCompleted parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static RemoteStreamCompleted parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static RemoteStreamCompleted parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static RemoteStreamCompleted parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static RemoteStreamCompleted parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static RemoteStreamCompleted parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return RemoteStreamCompleted.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RemoteStreamCompleted remoteStreamCompleted) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(remoteStreamCompleted);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static RemoteStreamCompleted getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RemoteStreamCompleted> parser() {
            return PARSER;
        }

        public Parser<RemoteStreamCompleted> getParserForType() {
            return PARSER;
        }

        @Override
        public RemoteStreamCompleted getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RemoteStreamCompletedOrBuilder {
            private int bitField0_;
            private long seqNr_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_RemoteStreamCompleted_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_RemoteStreamCompleted_fieldAccessorTable.ensureFieldAccessorsInitialized(RemoteStreamCompleted.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.seqNr_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_RemoteStreamCompleted_descriptor;
            }

            @Override
            public RemoteStreamCompleted getDefaultInstanceForType() {
                return RemoteStreamCompleted.getDefaultInstance();
            }

            @Override
            public RemoteStreamCompleted build() {
                RemoteStreamCompleted remoteStreamCompleted = this.buildPartial();
                if (!remoteStreamCompleted.isInitialized()) {
                    throw Builder.newUninitializedMessageException(remoteStreamCompleted);
                }
                return remoteStreamCompleted;
            }

            @Override
            public RemoteStreamCompleted buildPartial() {
                RemoteStreamCompleted remoteStreamCompleted = new RemoteStreamCompleted(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    remoteStreamCompleted.seqNr_ = this.seqNr_;
                    n2 |= 1;
                }
                remoteStreamCompleted.bitField0_ = n2;
                this.onBuilt();
                return remoteStreamCompleted;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof RemoteStreamCompleted) {
                    return this.mergeFrom((RemoteStreamCompleted)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(RemoteStreamCompleted remoteStreamCompleted) {
                if (remoteStreamCompleted == RemoteStreamCompleted.getDefaultInstance()) {
                    return this;
                }
                if (remoteStreamCompleted.hasSeqNr()) {
                    this.setSeqNr(remoteStreamCompleted.getSeqNr());
                }
                this.mergeUnknownFields(remoteStreamCompleted.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return this.hasSeqNr();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                RemoteStreamCompleted remoteStreamCompleted = null;
                try {
                    remoteStreamCompleted = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    remoteStreamCompleted = (RemoteStreamCompleted)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (remoteStreamCompleted != null) {
                        this.mergeFrom(remoteStreamCompleted);
                    }
                }
                return this;
            }

            @Override
            public boolean hasSeqNr() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public long getSeqNr() {
                return this.seqNr_;
            }

            public Builder setSeqNr(long l) {
                this.bitField0_ |= 1;
                this.seqNr_ = l;
                this.onChanged();
                return this;
            }

            public Builder clearSeqNr() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.seqNr_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface RemoteStreamCompletedOrBuilder
    extends MessageOrBuilder {
        public boolean hasSeqNr();

        public long getSeqNr();
    }

    public static final class RemoteStreamFailure
    extends GeneratedMessageV3
    implements RemoteStreamFailureOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CAUSE_FIELD_NUMBER = 1;
        private ByteString cause_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final RemoteStreamFailure DEFAULT_INSTANCE = new RemoteStreamFailure();
        @Deprecated
        public static final Parser<RemoteStreamFailure> PARSER = new AbstractParser<RemoteStreamFailure>(){

            @Override
            public RemoteStreamFailure parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new RemoteStreamFailure(codedInputStream, extensionRegistryLite);
            }
        };

        private RemoteStreamFailure(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RemoteStreamFailure() {
            this.cause_ = ByteString.EMPTY;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new RemoteStreamFailure();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RemoteStreamFailure(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 10: {
                            this.bitField0_ |= 1;
                            this.cause_ = codedInputStream.readBytes();
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_RemoteStreamFailure_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_RemoteStreamFailure_fieldAccessorTable.ensureFieldAccessorsInitialized(RemoteStreamFailure.class, Builder.class);
        }

        @Override
        public boolean hasCause() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ByteString getCause() {
            return this.cause_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeBytes(1, this.cause_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeBytesSize(1, this.cause_);
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof RemoteStreamFailure)) {
                return super.equals(object);
            }
            RemoteStreamFailure remoteStreamFailure = (RemoteStreamFailure)object;
            if (this.hasCause() != remoteStreamFailure.hasCause()) {
                return false;
            }
            if (this.hasCause() && !this.getCause().equals(remoteStreamFailure.getCause())) {
                return false;
            }
            return this.unknownFields.equals(remoteStreamFailure.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + RemoteStreamFailure.getDescriptor().hashCode();
            if (this.hasCause()) {
                n = 37 * n + 1;
                n = 53 * n + this.getCause().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static RemoteStreamFailure parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static RemoteStreamFailure parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static RemoteStreamFailure parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static RemoteStreamFailure parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static RemoteStreamFailure parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static RemoteStreamFailure parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static RemoteStreamFailure parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static RemoteStreamFailure parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static RemoteStreamFailure parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static RemoteStreamFailure parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static RemoteStreamFailure parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static RemoteStreamFailure parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return RemoteStreamFailure.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RemoteStreamFailure remoteStreamFailure) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(remoteStreamFailure);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static RemoteStreamFailure getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RemoteStreamFailure> parser() {
            return PARSER;
        }

        public Parser<RemoteStreamFailure> getParserForType() {
            return PARSER;
        }

        @Override
        public RemoteStreamFailure getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RemoteStreamFailureOrBuilder {
            private int bitField0_;
            private ByteString cause_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_RemoteStreamFailure_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_RemoteStreamFailure_fieldAccessorTable.ensureFieldAccessorsInitialized(RemoteStreamFailure.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.cause_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_RemoteStreamFailure_descriptor;
            }

            @Override
            public RemoteStreamFailure getDefaultInstanceForType() {
                return RemoteStreamFailure.getDefaultInstance();
            }

            @Override
            public RemoteStreamFailure build() {
                RemoteStreamFailure remoteStreamFailure = this.buildPartial();
                if (!remoteStreamFailure.isInitialized()) {
                    throw Builder.newUninitializedMessageException(remoteStreamFailure);
                }
                return remoteStreamFailure;
            }

            @Override
            public RemoteStreamFailure buildPartial() {
                RemoteStreamFailure remoteStreamFailure = new RemoteStreamFailure(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    n2 |= 1;
                }
                remoteStreamFailure.cause_ = this.cause_;
                remoteStreamFailure.bitField0_ = n2;
                this.onBuilt();
                return remoteStreamFailure;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof RemoteStreamFailure) {
                    return this.mergeFrom((RemoteStreamFailure)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(RemoteStreamFailure remoteStreamFailure) {
                if (remoteStreamFailure == RemoteStreamFailure.getDefaultInstance()) {
                    return this;
                }
                if (remoteStreamFailure.hasCause()) {
                    this.setCause(remoteStreamFailure.getCause());
                }
                this.mergeUnknownFields(remoteStreamFailure.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                RemoteStreamFailure remoteStreamFailure = null;
                try {
                    remoteStreamFailure = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    remoteStreamFailure = (RemoteStreamFailure)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (remoteStreamFailure != null) {
                        this.mergeFrom(remoteStreamFailure);
                    }
                }
                return this;
            }

            @Override
            public boolean hasCause() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public ByteString getCause() {
                return this.cause_;
            }

            public Builder setCause(ByteString byteString) {
                if (byteString == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.cause_ = byteString;
                this.onChanged();
                return this;
            }

            public Builder clearCause() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.cause_ = RemoteStreamFailure.getDefaultInstance().getCause();
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface RemoteStreamFailureOrBuilder
    extends MessageOrBuilder {
        public boolean hasCause();

        public ByteString getCause();
    }

    public static final class SequencedOnNext
    extends GeneratedMessageV3
    implements SequencedOnNextOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SEQNR_FIELD_NUMBER = 1;
        private long seqNr_;
        public static final int PAYLOAD_FIELD_NUMBER = 2;
        private Payload payload_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SequencedOnNext DEFAULT_INSTANCE = new SequencedOnNext();
        @Deprecated
        public static final Parser<SequencedOnNext> PARSER = new AbstractParser<SequencedOnNext>(){

            @Override
            public SequencedOnNext parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new SequencedOnNext(codedInputStream, extensionRegistryLite);
            }
        };

        private SequencedOnNext(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SequencedOnNext() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new SequencedOnNext();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SequencedOnNext(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block11: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block11;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.seqNr_ = codedInputStream.readInt64();
                            continue block11;
                        }
                        case 18: {
                            Payload.Builder builder2 = null;
                            if ((this.bitField0_ & 2) != 0) {
                                builder2 = this.payload_.toBuilder();
                            }
                            this.payload_ = codedInputStream.readMessage(Payload.PARSER, extensionRegistryLite);
                            if (builder2 != null) {
                                builder2.mergeFrom(this.payload_);
                                this.payload_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_SequencedOnNext_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_SequencedOnNext_fieldAccessorTable.ensureFieldAccessorsInitialized(SequencedOnNext.class, Builder.class);
        }

        @Override
        public boolean hasSeqNr() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public long getSeqNr() {
            return this.seqNr_;
        }

        @Override
        public boolean hasPayload() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Payload getPayload() {
            return this.payload_ == null ? Payload.getDefaultInstance() : this.payload_;
        }

        @Override
        public PayloadOrBuilder getPayloadOrBuilder() {
            return this.payload_ == null ? Payload.getDefaultInstance() : this.payload_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasSeqNr()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasPayload()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getPayload().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt64(1, this.seqNr_);
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeMessage(2, this.getPayload());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeInt64Size(1, this.seqNr_);
            }
            if ((this.bitField0_ & 2) != 0) {
                n += CodedOutputStream.computeMessageSize(2, this.getPayload());
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof SequencedOnNext)) {
                return super.equals(object);
            }
            SequencedOnNext sequencedOnNext = (SequencedOnNext)object;
            if (this.hasSeqNr() != sequencedOnNext.hasSeqNr()) {
                return false;
            }
            if (this.hasSeqNr() && this.getSeqNr() != sequencedOnNext.getSeqNr()) {
                return false;
            }
            if (this.hasPayload() != sequencedOnNext.hasPayload()) {
                return false;
            }
            if (this.hasPayload() && !this.getPayload().equals(sequencedOnNext.getPayload())) {
                return false;
            }
            return this.unknownFields.equals(sequencedOnNext.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + SequencedOnNext.getDescriptor().hashCode();
            if (this.hasSeqNr()) {
                n = 37 * n + 1;
                n = 53 * n + Internal.hashLong(this.getSeqNr());
            }
            if (this.hasPayload()) {
                n = 37 * n + 2;
                n = 53 * n + this.getPayload().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static SequencedOnNext parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static SequencedOnNext parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static SequencedOnNext parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static SequencedOnNext parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static SequencedOnNext parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static SequencedOnNext parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static SequencedOnNext parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static SequencedOnNext parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SequencedOnNext parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static SequencedOnNext parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SequencedOnNext parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static SequencedOnNext parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return SequencedOnNext.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SequencedOnNext sequencedOnNext) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(sequencedOnNext);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static SequencedOnNext getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SequencedOnNext> parser() {
            return PARSER;
        }

        public Parser<SequencedOnNext> getParserForType() {
            return PARSER;
        }

        @Override
        public SequencedOnNext getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SequencedOnNextOrBuilder {
            private int bitField0_;
            private long seqNr_;
            private Payload payload_;
            private SingleFieldBuilderV3<Payload, Payload.Builder, PayloadOrBuilder> payloadBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_SequencedOnNext_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_SequencedOnNext_fieldAccessorTable.ensureFieldAccessorsInitialized(SequencedOnNext.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getPayloadFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.seqNr_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.payloadBuilder_ == null) {
                    this.payload_ = null;
                } else {
                    this.payloadBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_SequencedOnNext_descriptor;
            }

            @Override
            public SequencedOnNext getDefaultInstanceForType() {
                return SequencedOnNext.getDefaultInstance();
            }

            @Override
            public SequencedOnNext build() {
                SequencedOnNext sequencedOnNext = this.buildPartial();
                if (!sequencedOnNext.isInitialized()) {
                    throw Builder.newUninitializedMessageException(sequencedOnNext);
                }
                return sequencedOnNext;
            }

            @Override
            public SequencedOnNext buildPartial() {
                SequencedOnNext sequencedOnNext = new SequencedOnNext(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    sequencedOnNext.seqNr_ = this.seqNr_;
                    n2 |= 1;
                }
                if ((n & 2) != 0) {
                    if (this.payloadBuilder_ == null) {
                        sequencedOnNext.payload_ = this.payload_;
                    } else {
                        sequencedOnNext.payload_ = this.payloadBuilder_.build();
                    }
                    n2 |= 2;
                }
                sequencedOnNext.bitField0_ = n2;
                this.onBuilt();
                return sequencedOnNext;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof SequencedOnNext) {
                    return this.mergeFrom((SequencedOnNext)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(SequencedOnNext sequencedOnNext) {
                if (sequencedOnNext == SequencedOnNext.getDefaultInstance()) {
                    return this;
                }
                if (sequencedOnNext.hasSeqNr()) {
                    this.setSeqNr(sequencedOnNext.getSeqNr());
                }
                if (sequencedOnNext.hasPayload()) {
                    this.mergePayload(sequencedOnNext.getPayload());
                }
                this.mergeUnknownFields(sequencedOnNext.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasSeqNr()) {
                    return false;
                }
                if (!this.hasPayload()) {
                    return false;
                }
                return this.getPayload().isInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                SequencedOnNext sequencedOnNext = null;
                try {
                    sequencedOnNext = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    sequencedOnNext = (SequencedOnNext)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (sequencedOnNext != null) {
                        this.mergeFrom(sequencedOnNext);
                    }
                }
                return this;
            }

            @Override
            public boolean hasSeqNr() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public long getSeqNr() {
                return this.seqNr_;
            }

            public Builder setSeqNr(long l) {
                this.bitField0_ |= 1;
                this.seqNr_ = l;
                this.onChanged();
                return this;
            }

            public Builder clearSeqNr() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.seqNr_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPayload() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public Payload getPayload() {
                if (this.payloadBuilder_ == null) {
                    return this.payload_ == null ? Payload.getDefaultInstance() : this.payload_;
                }
                return this.payloadBuilder_.getMessage();
            }

            public Builder setPayload(Payload payload) {
                if (this.payloadBuilder_ == null) {
                    if (payload == null) {
                        throw new NullPointerException();
                    }
                    this.payload_ = payload;
                    this.onChanged();
                } else {
                    this.payloadBuilder_.setMessage(payload);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setPayload(Payload.Builder builder) {
                if (this.payloadBuilder_ == null) {
                    this.payload_ = builder.build();
                    this.onChanged();
                } else {
                    this.payloadBuilder_.setMessage(builder.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergePayload(Payload payload) {
                if (this.payloadBuilder_ == null) {
                    this.payload_ = (this.bitField0_ & 2) != 0 && this.payload_ != null && this.payload_ != Payload.getDefaultInstance() ? Payload.newBuilder(this.payload_).mergeFrom(payload).buildPartial() : payload;
                    this.onChanged();
                } else {
                    this.payloadBuilder_.mergeFrom(payload);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearPayload() {
                if (this.payloadBuilder_ == null) {
                    this.payload_ = null;
                    this.onChanged();
                } else {
                    this.payloadBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public Payload.Builder getPayloadBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getPayloadFieldBuilder().getBuilder();
            }

            @Override
            public PayloadOrBuilder getPayloadOrBuilder() {
                if (this.payloadBuilder_ != null) {
                    return this.payloadBuilder_.getMessageOrBuilder();
                }
                return this.payload_ == null ? Payload.getDefaultInstance() : this.payload_;
            }

            private SingleFieldBuilderV3<Payload, Payload.Builder, PayloadOrBuilder> getPayloadFieldBuilder() {
                if (this.payloadBuilder_ == null) {
                    this.payloadBuilder_ = new SingleFieldBuilderV3(this.getPayload(), this.getParentForChildren(), this.isClean());
                    this.payload_ = null;
                }
                return this.payloadBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface SequencedOnNextOrBuilder
    extends MessageOrBuilder {
        public boolean hasSeqNr();

        public long getSeqNr();

        public boolean hasPayload();

        public Payload getPayload();

        public PayloadOrBuilder getPayloadOrBuilder();
    }

    public static final class CumulativeDemand
    extends GeneratedMessageV3
    implements CumulativeDemandOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SEQNR_FIELD_NUMBER = 1;
        private long seqNr_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final CumulativeDemand DEFAULT_INSTANCE = new CumulativeDemand();
        @Deprecated
        public static final Parser<CumulativeDemand> PARSER = new AbstractParser<CumulativeDemand>(){

            @Override
            public CumulativeDemand parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new CumulativeDemand(codedInputStream, extensionRegistryLite);
            }
        };

        private CumulativeDemand(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CumulativeDemand() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new CumulativeDemand();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CumulativeDemand(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.seqNr_ = codedInputStream.readInt64();
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_CumulativeDemand_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_CumulativeDemand_fieldAccessorTable.ensureFieldAccessorsInitialized(CumulativeDemand.class, Builder.class);
        }

        @Override
        public boolean hasSeqNr() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public long getSeqNr() {
            return this.seqNr_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasSeqNr()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt64(1, this.seqNr_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeInt64Size(1, this.seqNr_);
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof CumulativeDemand)) {
                return super.equals(object);
            }
            CumulativeDemand cumulativeDemand = (CumulativeDemand)object;
            if (this.hasSeqNr() != cumulativeDemand.hasSeqNr()) {
                return false;
            }
            if (this.hasSeqNr() && this.getSeqNr() != cumulativeDemand.getSeqNr()) {
                return false;
            }
            return this.unknownFields.equals(cumulativeDemand.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + CumulativeDemand.getDescriptor().hashCode();
            if (this.hasSeqNr()) {
                n = 37 * n + 1;
                n = 53 * n + Internal.hashLong(this.getSeqNr());
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static CumulativeDemand parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static CumulativeDemand parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static CumulativeDemand parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static CumulativeDemand parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static CumulativeDemand parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static CumulativeDemand parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static CumulativeDemand parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static CumulativeDemand parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static CumulativeDemand parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static CumulativeDemand parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static CumulativeDemand parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static CumulativeDemand parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return CumulativeDemand.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CumulativeDemand cumulativeDemand) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(cumulativeDemand);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static CumulativeDemand getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CumulativeDemand> parser() {
            return PARSER;
        }

        public Parser<CumulativeDemand> getParserForType() {
            return PARSER;
        }

        @Override
        public CumulativeDemand getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CumulativeDemandOrBuilder {
            private int bitField0_;
            private long seqNr_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_CumulativeDemand_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_CumulativeDemand_fieldAccessorTable.ensureFieldAccessorsInitialized(CumulativeDemand.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.seqNr_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_CumulativeDemand_descriptor;
            }

            @Override
            public CumulativeDemand getDefaultInstanceForType() {
                return CumulativeDemand.getDefaultInstance();
            }

            @Override
            public CumulativeDemand build() {
                CumulativeDemand cumulativeDemand = this.buildPartial();
                if (!cumulativeDemand.isInitialized()) {
                    throw Builder.newUninitializedMessageException(cumulativeDemand);
                }
                return cumulativeDemand;
            }

            @Override
            public CumulativeDemand buildPartial() {
                CumulativeDemand cumulativeDemand = new CumulativeDemand(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    cumulativeDemand.seqNr_ = this.seqNr_;
                    n2 |= 1;
                }
                cumulativeDemand.bitField0_ = n2;
                this.onBuilt();
                return cumulativeDemand;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof CumulativeDemand) {
                    return this.mergeFrom((CumulativeDemand)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(CumulativeDemand cumulativeDemand) {
                if (cumulativeDemand == CumulativeDemand.getDefaultInstance()) {
                    return this;
                }
                if (cumulativeDemand.hasSeqNr()) {
                    this.setSeqNr(cumulativeDemand.getSeqNr());
                }
                this.mergeUnknownFields(cumulativeDemand.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return this.hasSeqNr();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                CumulativeDemand cumulativeDemand = null;
                try {
                    cumulativeDemand = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    cumulativeDemand = (CumulativeDemand)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (cumulativeDemand != null) {
                        this.mergeFrom(cumulativeDemand);
                    }
                }
                return this;
            }

            @Override
            public boolean hasSeqNr() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public long getSeqNr() {
                return this.seqNr_;
            }

            public Builder setSeqNr(long l) {
                this.bitField0_ |= 1;
                this.seqNr_ = l;
                this.onChanged();
                return this;
            }

            public Builder clearSeqNr() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.seqNr_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface CumulativeDemandOrBuilder
    extends MessageOrBuilder {
        public boolean hasSeqNr();

        public long getSeqNr();
    }

    public static final class OnSubscribeHandshake
    extends GeneratedMessageV3
    implements OnSubscribeHandshakeOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TARGETREF_FIELD_NUMBER = 1;
        private ActorRef targetRef_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final OnSubscribeHandshake DEFAULT_INSTANCE = new OnSubscribeHandshake();
        @Deprecated
        public static final Parser<OnSubscribeHandshake> PARSER = new AbstractParser<OnSubscribeHandshake>(){

            @Override
            public OnSubscribeHandshake parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new OnSubscribeHandshake(codedInputStream, extensionRegistryLite);
            }
        };

        private OnSubscribeHandshake(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private OnSubscribeHandshake() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new OnSubscribeHandshake();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OnSubscribeHandshake(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 10: {
                            ActorRef.Builder builder2 = null;
                            if ((this.bitField0_ & 1) != 0) {
                                builder2 = this.targetRef_.toBuilder();
                            }
                            this.targetRef_ = codedInputStream.readMessage(ActorRef.PARSER, extensionRegistryLite);
                            if (builder2 != null) {
                                builder2.mergeFrom(this.targetRef_);
                                this.targetRef_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_OnSubscribeHandshake_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_OnSubscribeHandshake_fieldAccessorTable.ensureFieldAccessorsInitialized(OnSubscribeHandshake.class, Builder.class);
        }

        @Override
        public boolean hasTargetRef() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ActorRef getTargetRef() {
            return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
        }

        @Override
        public ActorRefOrBuilder getTargetRefOrBuilder() {
            return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasTargetRef()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getTargetRef().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeMessage(1, this.getTargetRef());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeMessageSize(1, this.getTargetRef());
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof OnSubscribeHandshake)) {
                return super.equals(object);
            }
            OnSubscribeHandshake onSubscribeHandshake = (OnSubscribeHandshake)object;
            if (this.hasTargetRef() != onSubscribeHandshake.hasTargetRef()) {
                return false;
            }
            if (this.hasTargetRef() && !this.getTargetRef().equals(onSubscribeHandshake.getTargetRef())) {
                return false;
            }
            return this.unknownFields.equals(onSubscribeHandshake.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + OnSubscribeHandshake.getDescriptor().hashCode();
            if (this.hasTargetRef()) {
                n = 37 * n + 1;
                n = 53 * n + this.getTargetRef().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static OnSubscribeHandshake parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static OnSubscribeHandshake parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static OnSubscribeHandshake parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static OnSubscribeHandshake parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static OnSubscribeHandshake parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static OnSubscribeHandshake parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static OnSubscribeHandshake parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static OnSubscribeHandshake parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static OnSubscribeHandshake parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static OnSubscribeHandshake parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static OnSubscribeHandshake parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static OnSubscribeHandshake parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return OnSubscribeHandshake.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OnSubscribeHandshake onSubscribeHandshake) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(onSubscribeHandshake);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static OnSubscribeHandshake getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OnSubscribeHandshake> parser() {
            return PARSER;
        }

        public Parser<OnSubscribeHandshake> getParserForType() {
            return PARSER;
        }

        @Override
        public OnSubscribeHandshake getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements OnSubscribeHandshakeOrBuilder {
            private int bitField0_;
            private ActorRef targetRef_;
            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> targetRefBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_OnSubscribeHandshake_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_OnSubscribeHandshake_fieldAccessorTable.ensureFieldAccessorsInitialized(OnSubscribeHandshake.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getTargetRefFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = null;
                } else {
                    this.targetRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_OnSubscribeHandshake_descriptor;
            }

            @Override
            public OnSubscribeHandshake getDefaultInstanceForType() {
                return OnSubscribeHandshake.getDefaultInstance();
            }

            @Override
            public OnSubscribeHandshake build() {
                OnSubscribeHandshake onSubscribeHandshake = this.buildPartial();
                if (!onSubscribeHandshake.isInitialized()) {
                    throw Builder.newUninitializedMessageException(onSubscribeHandshake);
                }
                return onSubscribeHandshake;
            }

            @Override
            public OnSubscribeHandshake buildPartial() {
                OnSubscribeHandshake onSubscribeHandshake = new OnSubscribeHandshake(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    if (this.targetRefBuilder_ == null) {
                        onSubscribeHandshake.targetRef_ = this.targetRef_;
                    } else {
                        onSubscribeHandshake.targetRef_ = this.targetRefBuilder_.build();
                    }
                    n2 |= 1;
                }
                onSubscribeHandshake.bitField0_ = n2;
                this.onBuilt();
                return onSubscribeHandshake;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof OnSubscribeHandshake) {
                    return this.mergeFrom((OnSubscribeHandshake)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(OnSubscribeHandshake onSubscribeHandshake) {
                if (onSubscribeHandshake == OnSubscribeHandshake.getDefaultInstance()) {
                    return this;
                }
                if (onSubscribeHandshake.hasTargetRef()) {
                    this.mergeTargetRef(onSubscribeHandshake.getTargetRef());
                }
                this.mergeUnknownFields(onSubscribeHandshake.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasTargetRef()) {
                    return false;
                }
                return this.getTargetRef().isInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                OnSubscribeHandshake onSubscribeHandshake = null;
                try {
                    onSubscribeHandshake = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    onSubscribeHandshake = (OnSubscribeHandshake)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (onSubscribeHandshake != null) {
                        this.mergeFrom(onSubscribeHandshake);
                    }
                }
                return this;
            }

            @Override
            public boolean hasTargetRef() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public ActorRef getTargetRef() {
                if (this.targetRefBuilder_ == null) {
                    return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
                }
                return this.targetRefBuilder_.getMessage();
            }

            public Builder setTargetRef(ActorRef actorRef2) {
                if (this.targetRefBuilder_ == null) {
                    if (actorRef2 == null) {
                        throw new NullPointerException();
                    }
                    this.targetRef_ = actorRef2;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.setMessage(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTargetRef(ActorRef.Builder builder) {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = builder.build();
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.setMessage(builder.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTargetRef(ActorRef actorRef2) {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = (this.bitField0_ & 1) != 0 && this.targetRef_ != null && this.targetRef_ != ActorRef.getDefaultInstance() ? ActorRef.newBuilder(this.targetRef_).mergeFrom(actorRef2).buildPartial() : actorRef2;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.mergeFrom(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTargetRef() {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = null;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public ActorRef.Builder getTargetRefBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getTargetRefFieldBuilder().getBuilder();
            }

            @Override
            public ActorRefOrBuilder getTargetRefOrBuilder() {
                if (this.targetRefBuilder_ != null) {
                    return this.targetRefBuilder_.getMessageOrBuilder();
                }
                return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
            }

            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> getTargetRefFieldBuilder() {
                if (this.targetRefBuilder_ == null) {
                    this.targetRefBuilder_ = new SingleFieldBuilderV3(this.getTargetRef(), this.getParentForChildren(), this.isClean());
                    this.targetRef_ = null;
                }
                return this.targetRefBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface OnSubscribeHandshakeOrBuilder
    extends MessageOrBuilder {
        public boolean hasTargetRef();

        public ActorRef getTargetRef();

        public ActorRefOrBuilder getTargetRefOrBuilder();
    }

    public static final class Payload
    extends GeneratedMessageV3
    implements PayloadOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int ENCLOSEDMESSAGE_FIELD_NUMBER = 1;
        private ByteString enclosedMessage_;
        public static final int SERIALIZERID_FIELD_NUMBER = 2;
        private int serializerId_;
        public static final int MESSAGEMANIFEST_FIELD_NUMBER = 3;
        private ByteString messageManifest_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Payload DEFAULT_INSTANCE = new Payload();
        @Deprecated
        public static final Parser<Payload> PARSER = new AbstractParser<Payload>(){

            @Override
            public Payload parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Payload(codedInputStream, extensionRegistryLite);
            }
        };

        private Payload(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Payload() {
            this.enclosedMessage_ = ByteString.EMPTY;
            this.messageManifest_ = ByteString.EMPTY;
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new Payload();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Payload(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block12: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block12;
                        }
                        case 10: {
                            this.bitField0_ |= 1;
                            this.enclosedMessage_ = codedInputStream.readBytes();
                            continue block12;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.serializerId_ = codedInputStream.readInt32();
                            continue block12;
                        }
                        case 26: {
                            this.bitField0_ |= 4;
                            this.messageManifest_ = codedInputStream.readBytes();
                            continue block12;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_Payload_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_Payload_fieldAccessorTable.ensureFieldAccessorsInitialized(Payload.class, Builder.class);
        }

        @Override
        public boolean hasEnclosedMessage() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ByteString getEnclosedMessage() {
            return this.enclosedMessage_;
        }

        @Override
        public boolean hasSerializerId() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public int getSerializerId() {
            return this.serializerId_;
        }

        @Override
        public boolean hasMessageManifest() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override
        public ByteString getMessageManifest() {
            return this.messageManifest_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasEnclosedMessage()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasSerializerId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeBytes(1, this.enclosedMessage_);
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeInt32(2, this.serializerId_);
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeBytes(3, this.messageManifest_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeBytesSize(1, this.enclosedMessage_);
            }
            if ((this.bitField0_ & 2) != 0) {
                n += CodedOutputStream.computeInt32Size(2, this.serializerId_);
            }
            if ((this.bitField0_ & 4) != 0) {
                n += CodedOutputStream.computeBytesSize(3, this.messageManifest_);
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof Payload)) {
                return super.equals(object);
            }
            Payload payload = (Payload)object;
            if (this.hasEnclosedMessage() != payload.hasEnclosedMessage()) {
                return false;
            }
            if (this.hasEnclosedMessage() && !this.getEnclosedMessage().equals(payload.getEnclosedMessage())) {
                return false;
            }
            if (this.hasSerializerId() != payload.hasSerializerId()) {
                return false;
            }
            if (this.hasSerializerId() && this.getSerializerId() != payload.getSerializerId()) {
                return false;
            }
            if (this.hasMessageManifest() != payload.hasMessageManifest()) {
                return false;
            }
            if (this.hasMessageManifest() && !this.getMessageManifest().equals(payload.getMessageManifest())) {
                return false;
            }
            return this.unknownFields.equals(payload.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + Payload.getDescriptor().hashCode();
            if (this.hasEnclosedMessage()) {
                n = 37 * n + 1;
                n = 53 * n + this.getEnclosedMessage().hashCode();
            }
            if (this.hasSerializerId()) {
                n = 37 * n + 2;
                n = 53 * n + this.getSerializerId();
            }
            if (this.hasMessageManifest()) {
                n = 37 * n + 3;
                n = 53 * n + this.getMessageManifest().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static Payload parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Payload parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static Payload parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static Payload parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static Payload parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static Payload parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static Payload parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static Payload parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static Payload parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static Payload parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static Payload parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static Payload parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return Payload.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Payload payload) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(payload);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static Payload getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Payload> parser() {
            return PARSER;
        }

        public Parser<Payload> getParserForType() {
            return PARSER;
        }

        @Override
        public Payload getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements PayloadOrBuilder {
            private int bitField0_;
            private ByteString enclosedMessage_ = ByteString.EMPTY;
            private int serializerId_;
            private ByteString messageManifest_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_Payload_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_Payload_fieldAccessorTable.ensureFieldAccessorsInitialized(Payload.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.enclosedMessage_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.serializerId_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                this.messageManifest_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_Payload_descriptor;
            }

            @Override
            public Payload getDefaultInstanceForType() {
                return Payload.getDefaultInstance();
            }

            @Override
            public Payload build() {
                Payload payload = this.buildPartial();
                if (!payload.isInitialized()) {
                    throw Builder.newUninitializedMessageException(payload);
                }
                return payload;
            }

            @Override
            public Payload buildPartial() {
                Payload payload = new Payload(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    n2 |= 1;
                }
                payload.enclosedMessage_ = this.enclosedMessage_;
                if ((n & 2) != 0) {
                    payload.serializerId_ = this.serializerId_;
                    n2 |= 2;
                }
                if ((n & 4) != 0) {
                    n2 |= 4;
                }
                payload.messageManifest_ = this.messageManifest_;
                payload.bitField0_ = n2;
                this.onBuilt();
                return payload;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof Payload) {
                    return this.mergeFrom((Payload)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(Payload payload) {
                if (payload == Payload.getDefaultInstance()) {
                    return this;
                }
                if (payload.hasEnclosedMessage()) {
                    this.setEnclosedMessage(payload.getEnclosedMessage());
                }
                if (payload.hasSerializerId()) {
                    this.setSerializerId(payload.getSerializerId());
                }
                if (payload.hasMessageManifest()) {
                    this.setMessageManifest(payload.getMessageManifest());
                }
                this.mergeUnknownFields(payload.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasEnclosedMessage()) {
                    return false;
                }
                return this.hasSerializerId();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                Payload payload = null;
                try {
                    payload = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    payload = (Payload)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (payload != null) {
                        this.mergeFrom(payload);
                    }
                }
                return this;
            }

            @Override
            public boolean hasEnclosedMessage() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public ByteString getEnclosedMessage() {
                return this.enclosedMessage_;
            }

            public Builder setEnclosedMessage(ByteString byteString) {
                if (byteString == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.enclosedMessage_ = byteString;
                this.onChanged();
                return this;
            }

            public Builder clearEnclosedMessage() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.enclosedMessage_ = Payload.getDefaultInstance().getEnclosedMessage();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSerializerId() {
                return (this.bitField0_ & 2) != 0;
            }

            @Override
            public int getSerializerId() {
                return this.serializerId_;
            }

            public Builder setSerializerId(int n) {
                this.bitField0_ |= 2;
                this.serializerId_ = n;
                this.onChanged();
                return this;
            }

            public Builder clearSerializerId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.serializerId_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMessageManifest() {
                return (this.bitField0_ & 4) != 0;
            }

            @Override
            public ByteString getMessageManifest() {
                return this.messageManifest_;
            }

            public Builder setMessageManifest(ByteString byteString) {
                if (byteString == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.messageManifest_ = byteString;
                this.onChanged();
                return this;
            }

            public Builder clearMessageManifest() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.messageManifest_ = Payload.getDefaultInstance().getMessageManifest();
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface PayloadOrBuilder
    extends MessageOrBuilder {
        public boolean hasEnclosedMessage();

        public ByteString getEnclosedMessage();

        public boolean hasSerializerId();

        public int getSerializerId();

        public boolean hasMessageManifest();

        public ByteString getMessageManifest();
    }

    public static final class ActorRef
    extends GeneratedMessageV3
    implements ActorRefOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int PATH_FIELD_NUMBER = 1;
        private volatile Object path_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ActorRef DEFAULT_INSTANCE = new ActorRef();
        @Deprecated
        public static final Parser<ActorRef> PARSER = new AbstractParser<ActorRef>(){

            @Override
            public ActorRef parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ActorRef(codedInputStream, extensionRegistryLite);
            }
        };

        private ActorRef(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ActorRef() {
            this.path_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new ActorRef();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ActorRef(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 10: {
                            ByteString byteString = codedInputStream.readBytes();
                            this.bitField0_ |= 1;
                            this.path_ = byteString;
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_ActorRef_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_ActorRef_fieldAccessorTable.ensureFieldAccessorsInitialized(ActorRef.class, Builder.class);
        }

        @Override
        public boolean hasPath() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public String getPath() {
            Object object = this.path_;
            if (object instanceof String) {
                return (String)object;
            }
            ByteString byteString = (ByteString)object;
            String string = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.path_ = string;
            }
            return string;
        }

        @Override
        public ByteString getPathBytes() {
            Object object = this.path_;
            if (object instanceof String) {
                ByteString byteString = ByteString.copyFromUtf8((String)object);
                this.path_ = byteString;
                return byteString;
            }
            return (ByteString)object;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasPath()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.path_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += GeneratedMessageV3.computeStringSize(1, this.path_);
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ActorRef)) {
                return super.equals(object);
            }
            ActorRef actorRef2 = (ActorRef)object;
            if (this.hasPath() != actorRef2.hasPath()) {
                return false;
            }
            if (this.hasPath() && !this.getPath().equals(actorRef2.getPath())) {
                return false;
            }
            return this.unknownFields.equals(actorRef2.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + ActorRef.getDescriptor().hashCode();
            if (this.hasPath()) {
                n = 37 * n + 1;
                n = 53 * n + this.getPath().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static ActorRef parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static ActorRef parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static ActorRef parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static ActorRef parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static ActorRef parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static ActorRef parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static ActorRef parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static ActorRef parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static ActorRef parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static ActorRef parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static ActorRef parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static ActorRef parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return ActorRef.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ActorRef actorRef2) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(actorRef2);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static ActorRef getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ActorRef> parser() {
            return PARSER;
        }

        public Parser<ActorRef> getParserForType() {
            return PARSER;
        }

        @Override
        public ActorRef getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ActorRefOrBuilder {
            private int bitField0_;
            private Object path_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_ActorRef_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_ActorRef_fieldAccessorTable.ensureFieldAccessorsInitialized(ActorRef.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.path_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_ActorRef_descriptor;
            }

            @Override
            public ActorRef getDefaultInstanceForType() {
                return ActorRef.getDefaultInstance();
            }

            @Override
            public ActorRef build() {
                ActorRef actorRef2 = this.buildPartial();
                if (!actorRef2.isInitialized()) {
                    throw Builder.newUninitializedMessageException(actorRef2);
                }
                return actorRef2;
            }

            @Override
            public ActorRef buildPartial() {
                ActorRef actorRef2 = new ActorRef(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    n2 |= 1;
                }
                actorRef2.path_ = this.path_;
                actorRef2.bitField0_ = n2;
                this.onBuilt();
                return actorRef2;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof ActorRef) {
                    return this.mergeFrom((ActorRef)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(ActorRef actorRef2) {
                if (actorRef2 == ActorRef.getDefaultInstance()) {
                    return this;
                }
                if (actorRef2.hasPath()) {
                    this.bitField0_ |= 1;
                    this.path_ = actorRef2.path_;
                    this.onChanged();
                }
                this.mergeUnknownFields(actorRef2.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return this.hasPath();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                ActorRef actorRef2 = null;
                try {
                    actorRef2 = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    actorRef2 = (ActorRef)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (actorRef2 != null) {
                        this.mergeFrom(actorRef2);
                    }
                }
                return this;
            }

            @Override
            public boolean hasPath() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public String getPath() {
                Object object = this.path_;
                if (!(object instanceof String)) {
                    ByteString byteString = (ByteString)object;
                    String string = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.path_ = string;
                    }
                    return string;
                }
                return (String)object;
            }

            @Override
            public ByteString getPathBytes() {
                Object object = this.path_;
                if (object instanceof String) {
                    ByteString byteString = ByteString.copyFromUtf8((String)object);
                    this.path_ = byteString;
                    return byteString;
                }
                return (ByteString)object;
            }

            public Builder setPath(String string) {
                if (string == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.path_ = string;
                this.onChanged();
                return this;
            }

            public Builder clearPath() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.path_ = ActorRef.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString byteString) {
                if (byteString == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.path_ = byteString;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface ActorRefOrBuilder
    extends MessageOrBuilder {
        public boolean hasPath();

        public String getPath();

        public ByteString getPathBytes();
    }

    public static final class SourceRef
    extends GeneratedMessageV3
    implements SourceRefOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int ORIGINREF_FIELD_NUMBER = 1;
        private ActorRef originRef_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SourceRef DEFAULT_INSTANCE = new SourceRef();
        @Deprecated
        public static final Parser<SourceRef> PARSER = new AbstractParser<SourceRef>(){

            @Override
            public SourceRef parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new SourceRef(codedInputStream, extensionRegistryLite);
            }
        };

        private SourceRef(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SourceRef() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new SourceRef();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SourceRef(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 10: {
                            ActorRef.Builder builder2 = null;
                            if ((this.bitField0_ & 1) != 0) {
                                builder2 = this.originRef_.toBuilder();
                            }
                            this.originRef_ = codedInputStream.readMessage(ActorRef.PARSER, extensionRegistryLite);
                            if (builder2 != null) {
                                builder2.mergeFrom(this.originRef_);
                                this.originRef_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_SourceRef_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_SourceRef_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceRef.class, Builder.class);
        }

        @Override
        public boolean hasOriginRef() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ActorRef getOriginRef() {
            return this.originRef_ == null ? ActorRef.getDefaultInstance() : this.originRef_;
        }

        @Override
        public ActorRefOrBuilder getOriginRefOrBuilder() {
            return this.originRef_ == null ? ActorRef.getDefaultInstance() : this.originRef_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasOriginRef()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getOriginRef().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeMessage(1, this.getOriginRef());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeMessageSize(1, this.getOriginRef());
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof SourceRef)) {
                return super.equals(object);
            }
            SourceRef sourceRef = (SourceRef)object;
            if (this.hasOriginRef() != sourceRef.hasOriginRef()) {
                return false;
            }
            if (this.hasOriginRef() && !this.getOriginRef().equals(sourceRef.getOriginRef())) {
                return false;
            }
            return this.unknownFields.equals(sourceRef.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + SourceRef.getDescriptor().hashCode();
            if (this.hasOriginRef()) {
                n = 37 * n + 1;
                n = 53 * n + this.getOriginRef().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static SourceRef parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static SourceRef parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static SourceRef parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static SourceRef parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static SourceRef parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static SourceRef parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static SourceRef parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static SourceRef parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SourceRef parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static SourceRef parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SourceRef parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static SourceRef parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return SourceRef.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SourceRef sourceRef) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(sourceRef);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static SourceRef getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SourceRef> parser() {
            return PARSER;
        }

        public Parser<SourceRef> getParserForType() {
            return PARSER;
        }

        @Override
        public SourceRef getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SourceRefOrBuilder {
            private int bitField0_;
            private ActorRef originRef_;
            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> originRefBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_SourceRef_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_SourceRef_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceRef.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getOriginRefFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.originRefBuilder_ == null) {
                    this.originRef_ = null;
                } else {
                    this.originRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_SourceRef_descriptor;
            }

            @Override
            public SourceRef getDefaultInstanceForType() {
                return SourceRef.getDefaultInstance();
            }

            @Override
            public SourceRef build() {
                SourceRef sourceRef = this.buildPartial();
                if (!sourceRef.isInitialized()) {
                    throw Builder.newUninitializedMessageException(sourceRef);
                }
                return sourceRef;
            }

            @Override
            public SourceRef buildPartial() {
                SourceRef sourceRef = new SourceRef(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    if (this.originRefBuilder_ == null) {
                        sourceRef.originRef_ = this.originRef_;
                    } else {
                        sourceRef.originRef_ = this.originRefBuilder_.build();
                    }
                    n2 |= 1;
                }
                sourceRef.bitField0_ = n2;
                this.onBuilt();
                return sourceRef;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof SourceRef) {
                    return this.mergeFrom((SourceRef)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(SourceRef sourceRef) {
                if (sourceRef == SourceRef.getDefaultInstance()) {
                    return this;
                }
                if (sourceRef.hasOriginRef()) {
                    this.mergeOriginRef(sourceRef.getOriginRef());
                }
                this.mergeUnknownFields(sourceRef.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasOriginRef()) {
                    return false;
                }
                return this.getOriginRef().isInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                SourceRef sourceRef = null;
                try {
                    sourceRef = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    sourceRef = (SourceRef)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (sourceRef != null) {
                        this.mergeFrom(sourceRef);
                    }
                }
                return this;
            }

            @Override
            public boolean hasOriginRef() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public ActorRef getOriginRef() {
                if (this.originRefBuilder_ == null) {
                    return this.originRef_ == null ? ActorRef.getDefaultInstance() : this.originRef_;
                }
                return this.originRefBuilder_.getMessage();
            }

            public Builder setOriginRef(ActorRef actorRef2) {
                if (this.originRefBuilder_ == null) {
                    if (actorRef2 == null) {
                        throw new NullPointerException();
                    }
                    this.originRef_ = actorRef2;
                    this.onChanged();
                } else {
                    this.originRefBuilder_.setMessage(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setOriginRef(ActorRef.Builder builder) {
                if (this.originRefBuilder_ == null) {
                    this.originRef_ = builder.build();
                    this.onChanged();
                } else {
                    this.originRefBuilder_.setMessage(builder.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeOriginRef(ActorRef actorRef2) {
                if (this.originRefBuilder_ == null) {
                    this.originRef_ = (this.bitField0_ & 1) != 0 && this.originRef_ != null && this.originRef_ != ActorRef.getDefaultInstance() ? ActorRef.newBuilder(this.originRef_).mergeFrom(actorRef2).buildPartial() : actorRef2;
                    this.onChanged();
                } else {
                    this.originRefBuilder_.mergeFrom(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearOriginRef() {
                if (this.originRefBuilder_ == null) {
                    this.originRef_ = null;
                    this.onChanged();
                } else {
                    this.originRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public ActorRef.Builder getOriginRefBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getOriginRefFieldBuilder().getBuilder();
            }

            @Override
            public ActorRefOrBuilder getOriginRefOrBuilder() {
                if (this.originRefBuilder_ != null) {
                    return this.originRefBuilder_.getMessageOrBuilder();
                }
                return this.originRef_ == null ? ActorRef.getDefaultInstance() : this.originRef_;
            }

            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> getOriginRefFieldBuilder() {
                if (this.originRefBuilder_ == null) {
                    this.originRefBuilder_ = new SingleFieldBuilderV3(this.getOriginRef(), this.getParentForChildren(), this.isClean());
                    this.originRef_ = null;
                }
                return this.originRefBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface SourceRefOrBuilder
    extends MessageOrBuilder {
        public boolean hasOriginRef();

        public ActorRef getOriginRef();

        public ActorRefOrBuilder getOriginRefOrBuilder();
    }

    public static final class SinkRef
    extends GeneratedMessageV3
    implements SinkRefOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TARGETREF_FIELD_NUMBER = 1;
        private ActorRef targetRef_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SinkRef DEFAULT_INSTANCE = new SinkRef();
        @Deprecated
        public static final Parser<SinkRef> PARSER = new AbstractParser<SinkRef>(){

            @Override
            public SinkRef parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new SinkRef(codedInputStream, extensionRegistryLite);
            }
        };

        private SinkRef(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SinkRef() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new SinkRef();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SinkRef(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistryLite == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
            try {
                boolean bl2 = false;
                block10: while (!bl2) {
                    int n = codedInputStream.readTag();
                    switch (n) {
                        case 0: {
                            bl2 = true;
                            continue block10;
                        }
                        case 10: {
                            ActorRef.Builder builder2 = null;
                            if ((this.bitField0_ & 1) != 0) {
                                builder2 = this.targetRef_.toBuilder();
                            }
                            this.targetRef_ = codedInputStream.readMessage(ActorRef.PARSER, extensionRegistryLite);
                            if (builder2 != null) {
                                builder2.mergeFrom(this.targetRef_);
                                this.targetRef_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                    }
                    if (this.parseUnknownField(codedInputStream, builder, extensionRegistryLite, n)) continue;
                    bl2 = true;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException.setUnfinishedMessage(this);
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = builder.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_SinkRef_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_SinkRef_fieldAccessorTable.ensureFieldAccessorsInitialized(SinkRef.class, Builder.class);
        }

        @Override
        public boolean hasTargetRef() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public ActorRef getTargetRef() {
            return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
        }

        @Override
        public ActorRefOrBuilder getTargetRefOrBuilder() {
            return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
        }

        @Override
        public final boolean isInitialized() {
            byte by = this.memoizedIsInitialized;
            if (by == 1) {
                return true;
            }
            if (by == 0) {
                return false;
            }
            if (!this.hasTargetRef()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getTargetRef().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeMessage(1, this.getTargetRef());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) != 0) {
                n += CodedOutputStream.computeMessageSize(1, this.getTargetRef());
            }
            this.memoizedSize = n += this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof SinkRef)) {
                return super.equals(object);
            }
            SinkRef sinkRef = (SinkRef)object;
            if (this.hasTargetRef() != sinkRef.hasTargetRef()) {
                return false;
            }
            if (this.hasTargetRef() && !this.getTargetRef().equals(sinkRef.getTargetRef())) {
                return false;
            }
            return this.unknownFields.equals(sinkRef.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int n = 41;
            n = 19 * n + SinkRef.getDescriptor().hashCode();
            if (this.hasTargetRef()) {
                n = 37 * n + 1;
                n = 53 * n + this.getTargetRef().hashCode();
            }
            this.memoizedHashCode = n = 29 * n + this.unknownFields.hashCode();
            return n;
        }

        public static SinkRef parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static SinkRef parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        public static SinkRef parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static SinkRef parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static SinkRef parseFrom(byte[] byArray) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray);
        }

        public static SinkRef parseFrom(byte[] byArray, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byArray, extensionRegistryLite);
        }

        public static SinkRef parseFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2);
        }

        public static SinkRef parseFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SinkRef parseDelimitedFrom(InputStream inputStream2) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2);
        }

        public static SinkRef parseDelimitedFrom(InputStream inputStream2, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream2, extensionRegistryLite);
        }

        public static SinkRef parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static SinkRef parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }

        @Override
        public Builder newBuilderForType() {
            return SinkRef.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SinkRef sinkRef) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(sinkRef);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            Builder builder = new Builder(builderParent);
            return builder;
        }

        public static SinkRef getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SinkRef> parser() {
            return PARSER;
        }

        public Parser<SinkRef> getParserForType() {
            return PARSER;
        }

        @Override
        public SinkRef getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SinkRefOrBuilder {
            private int bitField0_;
            private ActorRef targetRef_;
            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> targetRefBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_SinkRef_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_SinkRef_fieldAccessorTable.ensureFieldAccessorsInitialized(SinkRef.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getTargetRefFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = null;
                } else {
                    this.targetRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_SinkRef_descriptor;
            }

            @Override
            public SinkRef getDefaultInstanceForType() {
                return SinkRef.getDefaultInstance();
            }

            @Override
            public SinkRef build() {
                SinkRef sinkRef = this.buildPartial();
                if (!sinkRef.isInitialized()) {
                    throw Builder.newUninitializedMessageException(sinkRef);
                }
                return sinkRef;
            }

            @Override
            public SinkRef buildPartial() {
                SinkRef sinkRef = new SinkRef(this);
                int n = this.bitField0_;
                int n2 = 0;
                if ((n & 1) != 0) {
                    if (this.targetRefBuilder_ == null) {
                        sinkRef.targetRef_ = this.targetRef_;
                    } else {
                        sinkRef.targetRef_ = this.targetRefBuilder_.build();
                    }
                    n2 |= 1;
                }
                sinkRef.bitField0_ = n2;
                this.onBuilt();
                return sinkRef;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.setField(fieldDescriptor, object);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder)super.clearField(fieldDescriptor);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder)super.clearOneof(oneofDescriptor);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int n, Object object) {
                return (Builder)super.setRepeatedField(fieldDescriptor, n, object);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object object) {
                return (Builder)super.addRepeatedField(fieldDescriptor, object);
            }

            @Override
            public Builder mergeFrom(Message message) {
                if (message instanceof SinkRef) {
                    return this.mergeFrom((SinkRef)message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(SinkRef sinkRef) {
                if (sinkRef == SinkRef.getDefaultInstance()) {
                    return this;
                }
                if (sinkRef.hasTargetRef()) {
                    this.mergeTargetRef(sinkRef.getTargetRef());
                }
                this.mergeUnknownFields(sinkRef.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!this.hasTargetRef()) {
                    return false;
                }
                return this.getTargetRef().isInitialized();
            }

            @Override
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                SinkRef sinkRef = null;
                try {
                    sinkRef = PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                }
                catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                    sinkRef = (SinkRef)invalidProtocolBufferException.getUnfinishedMessage();
                    throw invalidProtocolBufferException.unwrapIOException();
                }
                finally {
                    if (sinkRef != null) {
                        this.mergeFrom(sinkRef);
                    }
                }
                return this;
            }

            @Override
            public boolean hasTargetRef() {
                return (this.bitField0_ & 1) != 0;
            }

            @Override
            public ActorRef getTargetRef() {
                if (this.targetRefBuilder_ == null) {
                    return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
                }
                return this.targetRefBuilder_.getMessage();
            }

            public Builder setTargetRef(ActorRef actorRef2) {
                if (this.targetRefBuilder_ == null) {
                    if (actorRef2 == null) {
                        throw new NullPointerException();
                    }
                    this.targetRef_ = actorRef2;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.setMessage(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTargetRef(ActorRef.Builder builder) {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = builder.build();
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.setMessage(builder.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTargetRef(ActorRef actorRef2) {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = (this.bitField0_ & 1) != 0 && this.targetRef_ != null && this.targetRef_ != ActorRef.getDefaultInstance() ? ActorRef.newBuilder(this.targetRef_).mergeFrom(actorRef2).buildPartial() : actorRef2;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.mergeFrom(actorRef2);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTargetRef() {
                if (this.targetRefBuilder_ == null) {
                    this.targetRef_ = null;
                    this.onChanged();
                } else {
                    this.targetRefBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            public ActorRef.Builder getTargetRefBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return this.getTargetRefFieldBuilder().getBuilder();
            }

            @Override
            public ActorRefOrBuilder getTargetRefOrBuilder() {
                if (this.targetRefBuilder_ != null) {
                    return this.targetRefBuilder_.getMessageOrBuilder();
                }
                return this.targetRef_ == null ? ActorRef.getDefaultInstance() : this.targetRef_;
            }

            private SingleFieldBuilderV3<ActorRef, ActorRef.Builder, ActorRefOrBuilder> getTargetRefFieldBuilder() {
                if (this.targetRefBuilder_ == null) {
                    this.targetRefBuilder_ = new SingleFieldBuilderV3(this.getTargetRef(), this.getParentForChildren(), this.isClean());
                    this.targetRef_ = null;
                }
                return this.targetRefBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.setUnknownFields(unknownFieldSet);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder)super.mergeUnknownFields(unknownFieldSet);
            }
        }
    }

    public static interface SinkRefOrBuilder
    extends MessageOrBuilder {
        public boolean hasTargetRef();

        public ActorRef getTargetRef();

        public ActorRefOrBuilder getTargetRefOrBuilder();
    }
}

