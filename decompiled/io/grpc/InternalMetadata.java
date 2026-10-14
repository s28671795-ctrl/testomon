/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.io.BaseEncoding;
import io.grpc.Internal;
import io.grpc.Metadata;
import java.nio.charset.Charset;

@Internal
public final class InternalMetadata {
    @Internal
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    @Internal
    public static final BaseEncoding BASE64_ENCODING_OMIT_PADDING = Metadata.BASE64_ENCODING_OMIT_PADDING;

    @Internal
    public static <T> Metadata.Key<T> keyOf(String name, TrustedAsciiMarshaller<T> marshaller) {
        boolean isPseudo = name != null && !name.isEmpty() && name.charAt(0) == ':';
        return Metadata.Key.of(name, isPseudo, marshaller);
    }

    @Internal
    public static <T> Metadata.Key<T> keyOf(String name, Metadata.AsciiMarshaller<T> marshaller) {
        boolean isPseudo = name != null && !name.isEmpty() && name.charAt(0) == ':';
        return Metadata.Key.of(name, isPseudo, marshaller);
    }

    @Internal
    public static Metadata newMetadata(byte[] ... binaryValues) {
        return new Metadata(binaryValues);
    }

    @Internal
    public static Metadata newMetadata(int usedNames, byte[] ... binaryValues) {
        return new Metadata(usedNames, binaryValues);
    }

    @Internal
    public static byte[][] serialize(Metadata md) {
        return md.serialize();
    }

    @Internal
    public static int headerCount(Metadata md) {
        return md.headerCount();
    }

    @Internal
    public static Object[] serializePartial(Metadata md) {
        return md.serializePartial();
    }

    @Internal
    public static <T> Object parsedValue(Metadata.BinaryStreamMarshaller<T> marshaller, T value) {
        return new Metadata.LazyValue<T>(marshaller, value);
    }

    @Internal
    public static Metadata newMetadataWithParsedValues(int usedNames, Object[] namesAndValues) {
        return new Metadata(usedNames, namesAndValues);
    }

    @Internal
    public static interface TrustedAsciiMarshaller<T>
    extends Metadata.TrustedAsciiMarshaller<T> {
    }
}

