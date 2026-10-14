/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf.lite;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import io.grpc.ExperimentalApi;
import io.grpc.KnownLength;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.protobuf.lite.ProtoInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

@ExperimentalApi(value="Will remain experimental as protobuf lite API is not stable")
public final class ProtoLiteUtils {
    static volatile ExtensionRegistryLite globalRegistry = ExtensionRegistryLite.getEmptyRegistry();
    private static final int BUF_SIZE = 8192;
    @VisibleForTesting
    static final int DEFAULT_MAX_MESSAGE_SIZE = 0x400000;

    public static void setExtensionRegistry(ExtensionRegistryLite newRegistry) {
        globalRegistry = Preconditions.checkNotNull(newRegistry, "newRegistry");
    }

    public static <T extends MessageLite> MethodDescriptor.Marshaller<T> marshaller(T defaultInstance) {
        return new MessageMarshaller<T>(defaultInstance, -1);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/10108")
    public static <T extends MessageLite> MethodDescriptor.Marshaller<T> marshallerWithRecursionLimit(T defaultInstance, int recursionLimit) {
        return new MessageMarshaller<T>(defaultInstance, recursionLimit);
    }

    public static <T extends MessageLite> Metadata.BinaryMarshaller<T> metadataMarshaller(T defaultInstance) {
        return new MetadataMarshaller<T>(defaultInstance);
    }

    static long copy(InputStream from, OutputStream to) throws IOException {
        int r;
        Preconditions.checkNotNull(from, "inputStream cannot be null!");
        Preconditions.checkNotNull(to, "outputStream cannot be null!");
        byte[] buf = new byte[8192];
        long total = 0L;
        while ((r = from.read(buf)) != -1) {
            to.write(buf, 0, r);
            total += (long)r;
        }
        return total;
    }

    private ProtoLiteUtils() {
    }

    private static final class MetadataMarshaller<T extends MessageLite>
    implements Metadata.BinaryMarshaller<T> {
        private final T defaultInstance;

        MetadataMarshaller(T defaultInstance) {
            this.defaultInstance = defaultInstance;
        }

        @Override
        public byte[] toBytes(T value) {
            return value.toByteArray();
        }

        @Override
        public T parseBytes(byte[] serialized) {
            try {
                return (T)this.defaultInstance.getParserForType().parseFrom(serialized, globalRegistry);
            }
            catch (InvalidProtocolBufferException ipbe) {
                throw new IllegalArgumentException(ipbe);
            }
        }
    }

    private static final class MessageMarshaller<T extends MessageLite>
    implements MethodDescriptor.PrototypeMarshaller<T> {
        private static final ThreadLocal<Reference<byte[]>> bufs = new ThreadLocal();
        private final Parser<T> parser;
        private final T defaultInstance;
        private final int recursionLimit;

        MessageMarshaller(T defaultInstance, int recursionLimit) {
            this.defaultInstance = (MessageLite)Preconditions.checkNotNull(defaultInstance, "defaultInstance cannot be null");
            this.parser = defaultInstance.getParserForType();
            this.recursionLimit = recursionLimit;
        }

        @Override
        public Class<T> getMessageClass() {
            return this.defaultInstance.getClass();
        }

        @Override
        public T getMessagePrototype() {
            return this.defaultInstance;
        }

        @Override
        public InputStream stream(T value) {
            return new ProtoInputStream((MessageLite)value, this.parser);
        }

        @Override
        public T parse(InputStream stream) {
            ProtoInputStream protoStream;
            if (stream instanceof ProtoInputStream && (protoStream = (ProtoInputStream)stream).parser() == this.parser) {
                try {
                    MessageLite message = ((ProtoInputStream)stream).message();
                    return (T)message;
                }
                catch (IllegalStateException message) {
                    // empty catch block
                }
            }
            CodedInputStream cis = null;
            try {
                if (stream instanceof KnownLength) {
                    int size = stream.available();
                    if (size > 0 && size <= 0x400000) {
                        int position;
                        int remaining;
                        int count;
                        byte[] buf;
                        Reference<byte[]> ref = bufs.get();
                        if (ref == null || (buf = ref.get()) == null || buf.length < size) {
                            buf = new byte[size];
                            bufs.set(new WeakReference<byte[]>(buf));
                        }
                        for (remaining = size; remaining > 0 && (count = stream.read(buf, position = size - remaining, remaining)) != -1; remaining -= count) {
                        }
                        if (remaining != 0) {
                            position = size - remaining;
                            throw new RuntimeException("size inaccurate: " + size + " != " + position);
                        }
                        cis = CodedInputStream.newInstance(buf, 0, size);
                    } else if (size == 0) {
                        return this.defaultInstance;
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (cis == null) {
                cis = CodedInputStream.newInstance(stream);
            }
            cis.setSizeLimit(Integer.MAX_VALUE);
            if (this.recursionLimit >= 0) {
                cis.setRecursionLimit(this.recursionLimit);
            }
            try {
                return this.parseFrom(cis);
            }
            catch (InvalidProtocolBufferException ipbe) {
                throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence").withCause(ipbe).asRuntimeException();
            }
        }

        private T parseFrom(CodedInputStream stream) throws InvalidProtocolBufferException {
            MessageLite message = (MessageLite)this.parser.parseFrom(stream, globalRegistry);
            try {
                stream.checkLastTagWas(0);
                return (T)message;
            }
            catch (InvalidProtocolBufferException e) {
                e.setUnfinishedMessage(message);
                throw e;
            }
        }
    }
}

