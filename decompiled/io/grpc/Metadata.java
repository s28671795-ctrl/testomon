/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import io.grpc.ExperimentalApi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class Metadata {
    private static final Logger logger = Logger.getLogger(Metadata.class.getName());
    public static final String BINARY_HEADER_SUFFIX = "-bin";
    public static final BinaryMarshaller<byte[]> BINARY_BYTE_MARSHALLER = new BinaryMarshaller<byte[]>(){

        @Override
        public byte[] toBytes(byte[] value) {
            return value;
        }

        @Override
        public byte[] parseBytes(byte[] serialized) {
            return serialized;
        }
    };
    public static final AsciiMarshaller<String> ASCII_STRING_MARSHALLER = new AsciiMarshaller<String>(){

        @Override
        public String toAsciiString(String value) {
            return value;
        }

        @Override
        public String parseAsciiString(String serialized) {
            return serialized;
        }
    };
    static final BaseEncoding BASE64_ENCODING_OMIT_PADDING = BaseEncoding.base64().omitPadding();
    private Object[] namesAndValues;
    private int size;

    Metadata(byte[] ... binaryValues) {
        this(binaryValues.length / 2, binaryValues);
    }

    Metadata(int usedNames, byte[] ... binaryValues) {
        this(usedNames, (Object[])binaryValues);
    }

    Metadata(int usedNames, Object[] namesAndValues) {
        assert ((namesAndValues.length & 1) == 0) : "Odd number of key-value pairs " + namesAndValues.length;
        this.size = usedNames;
        this.namesAndValues = namesAndValues;
    }

    private byte[] name(int i) {
        return (byte[])this.namesAndValues[i * 2];
    }

    private void name(int i, byte[] name) {
        this.namesAndValues[i * 2] = name;
    }

    private Object value(int i) {
        return this.namesAndValues[i * 2 + 1];
    }

    private void value(int i, byte[] value) {
        this.namesAndValues[i * 2 + 1] = value;
    }

    private void value(int i, Object value) {
        if (this.namesAndValues instanceof byte[][]) {
            this.expand(this.cap());
        }
        this.namesAndValues[i * 2 + 1] = value;
    }

    private byte[] valueAsBytes(int i) {
        Object value = this.value(i);
        if (value instanceof byte[]) {
            return (byte[])value;
        }
        return ((LazyValue)value).toBytes();
    }

    private Object valueAsBytesOrStream(int i) {
        Object value = this.value(i);
        if (value instanceof byte[]) {
            return value;
        }
        return ((LazyValue)value).toStream();
    }

    private <T> T valueAsT(int i, Key<T> key) {
        Object value = this.value(i);
        if (value instanceof byte[]) {
            return key.parseBytes((byte[])value);
        }
        return ((LazyValue)value).toObject(key);
    }

    private int cap() {
        return this.namesAndValues != null ? this.namesAndValues.length : 0;
    }

    private int len() {
        return this.size * 2;
    }

    private boolean isEmpty() {
        return this.size == 0;
    }

    public Metadata() {
    }

    int headerCount() {
        return this.size;
    }

    public boolean containsKey(Key<?> key) {
        for (int i = 0; i < this.size; ++i) {
            if (!this.bytesEqual(key.asciiName(), this.name(i))) continue;
            return true;
        }
        return false;
    }

    @Nullable
    public <T> T get(Key<T> key) {
        for (int i = this.size - 1; i >= 0; --i) {
            if (!this.bytesEqual(key.asciiName(), this.name(i))) continue;
            return this.valueAsT(i, key);
        }
        return null;
    }

    @Nullable
    public <T> Iterable<T> getAll(Key<T> key) {
        for (int i = 0; i < this.size; ++i) {
            if (!this.bytesEqual(key.asciiName(), this.name(i))) continue;
            return new IterableAt(key, i);
        }
        return null;
    }

    public Set<String> keys() {
        if (this.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<String> ks = new HashSet<String>(this.size);
        for (int i = 0; i < this.size; ++i) {
            ks.add(new String(this.name(i), 0));
        }
        return Collections.unmodifiableSet(ks);
    }

    public <T> void put(Key<T> key, T value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        this.maybeExpand();
        this.name(this.size, key.asciiName());
        if (key.serializesToStreams()) {
            this.value(this.size, LazyValue.create(key, value));
        } else {
            this.value(this.size, key.toBytes(value));
        }
        ++this.size;
    }

    private void maybeExpand() {
        if (this.len() == 0 || this.len() == this.cap()) {
            this.expand(Math.max(this.len() * 2, 8));
        }
    }

    private void expand(int newCapacity) {
        Object[] newNamesAndValues = new Object[newCapacity];
        if (!this.isEmpty()) {
            System.arraycopy(this.namesAndValues, 0, newNamesAndValues, 0, this.len());
        }
        this.namesAndValues = newNamesAndValues;
    }

    public <T> boolean remove(Key<T> key, T value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        for (int i = 0; i < this.size; ++i) {
            T stored;
            if (!this.bytesEqual(key.asciiName(), this.name(i)) || !value.equals(stored = this.valueAsT(i, key))) continue;
            int writeIdx = i * 2;
            int readIdx = (i + 1) * 2;
            int readLen = this.len() - readIdx;
            System.arraycopy(this.namesAndValues, readIdx, this.namesAndValues, writeIdx, readLen);
            --this.size;
            this.name(this.size, null);
            this.value(this.size, null);
            return true;
        }
        return false;
    }

    public <T> Iterable<T> removeAll(Key<T> key) {
        if (this.isEmpty()) {
            return null;
        }
        int writeIdx = 0;
        ArrayList<T> ret = null;
        for (int readIdx = 0; readIdx < this.size; ++readIdx) {
            if (this.bytesEqual(key.asciiName(), this.name(readIdx))) {
                ret = ret != null ? ret : new ArrayList<T>();
                ret.add(this.valueAsT(readIdx, key));
                continue;
            }
            this.name(writeIdx, this.name(readIdx));
            this.value(writeIdx, this.value(readIdx));
            ++writeIdx;
        }
        int newSize = writeIdx;
        Arrays.fill(this.namesAndValues, writeIdx * 2, this.len(), null);
        this.size = newSize;
        return ret;
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4691")
    public <T> void discardAll(Key<T> key) {
        if (this.isEmpty()) {
            return;
        }
        int writeIdx = 0;
        for (int readIdx = 0; readIdx < this.size; ++readIdx) {
            if (this.bytesEqual(key.asciiName(), this.name(readIdx))) continue;
            this.name(writeIdx, this.name(readIdx));
            this.value(writeIdx, this.value(readIdx));
            ++writeIdx;
        }
        int newSize = writeIdx;
        Arrays.fill(this.namesAndValues, writeIdx * 2, this.len(), null);
        this.size = newSize;
    }

    @Nullable
    byte[][] serialize() {
        byte[][] serialized = new byte[this.len()][];
        if (this.namesAndValues instanceof byte[][]) {
            System.arraycopy(this.namesAndValues, 0, serialized, 0, this.len());
        } else {
            for (int i = 0; i < this.size; ++i) {
                serialized[i * 2] = this.name(i);
                serialized[i * 2 + 1] = this.valueAsBytes(i);
            }
        }
        return serialized;
    }

    @Nullable
    Object[] serializePartial() {
        Object[] serialized = new Object[this.len()];
        for (int i = 0; i < this.size; ++i) {
            serialized[i * 2] = this.name(i);
            serialized[i * 2 + 1] = this.valueAsBytesOrStream(i);
        }
        return serialized;
    }

    public void merge(Metadata other) {
        if (other.isEmpty()) {
            return;
        }
        int remaining = this.cap() - this.len();
        if (this.isEmpty() || remaining < other.len()) {
            this.expand(this.len() + other.len());
        }
        System.arraycopy(other.namesAndValues, 0, this.namesAndValues, this.len(), other.len());
        this.size += other.size;
    }

    public void merge(Metadata other, Set<Key<?>> keys2) {
        Preconditions.checkNotNull(other, "other");
        HashMap asciiKeys = new HashMap(keys2.size());
        for (Key<?> key : keys2) {
            asciiKeys.put(ByteBuffer.wrap(key.asciiName()), key);
        }
        for (int i = 0; i < other.size; ++i) {
            ByteBuffer wrappedNamed = ByteBuffer.wrap(other.name(i));
            if (!asciiKeys.containsKey(wrappedNamed)) continue;
            this.maybeExpand();
            this.name(this.size, other.name(i));
            this.value(this.size, other.value(i));
            ++this.size;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Metadata(");
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                sb.append(',');
            }
            String headerName = new String(this.name(i), Charsets.US_ASCII);
            sb.append(headerName).append('=');
            if (headerName.endsWith(BINARY_HEADER_SUFFIX)) {
                sb.append(BASE64_ENCODING_OMIT_PADDING.encode(this.valueAsBytes(i)));
                continue;
            }
            String headerValue = new String(this.valueAsBytes(i), Charsets.US_ASCII);
            sb.append(headerValue);
        }
        return sb.append(')').toString();
    }

    private boolean bytesEqual(byte[] left, byte[] right) {
        return Arrays.equals(left, right);
    }

    private static byte[] streamToBytes(InputStream stream) {
        try {
            return ByteStreams.toByteArray(stream);
        }
        catch (IOException ioe) {
            throw new RuntimeException("failure reading serialized stream", ioe);
        }
    }

    @Immutable
    static interface TrustedAsciiMarshaller<T> {
        public byte[] toAsciiString(T var1);

        public T parseAsciiString(byte[] var1);
    }

    private static final class TrustedAsciiKey<T>
    extends Key<T> {
        private final TrustedAsciiMarshaller<T> marshaller;

        private TrustedAsciiKey(String name, boolean pseudo, TrustedAsciiMarshaller<T> marshaller) {
            super(name, pseudo, marshaller);
            Preconditions.checkArgument(!name.endsWith(Metadata.BINARY_HEADER_SUFFIX), "ASCII header is named %s.  Only binary headers may end with %s", (Object)name, (Object)Metadata.BINARY_HEADER_SUFFIX);
            this.marshaller = Preconditions.checkNotNull(marshaller, "marshaller");
        }

        @Override
        byte[] toBytes(T value) {
            return Preconditions.checkNotNull(this.marshaller.toAsciiString(value), "null marshaller.toAsciiString()");
        }

        @Override
        T parseBytes(byte[] serialized) {
            return this.marshaller.parseAsciiString(serialized);
        }
    }

    private static class AsciiKey<T>
    extends Key<T> {
        private final AsciiMarshaller<T> marshaller;

        private AsciiKey(String name, boolean pseudo, AsciiMarshaller<T> marshaller) {
            super(name, pseudo, marshaller);
            Preconditions.checkArgument(!name.endsWith(Metadata.BINARY_HEADER_SUFFIX), "ASCII header is named %s.  Only binary headers may end with %s", (Object)name, (Object)Metadata.BINARY_HEADER_SUFFIX);
            this.marshaller = Preconditions.checkNotNull(marshaller, "marshaller");
        }

        @Override
        byte[] toBytes(T value) {
            String encoded = Preconditions.checkNotNull(this.marshaller.toAsciiString(value), "null marshaller.toAsciiString()");
            return encoded.getBytes(Charsets.US_ASCII);
        }

        @Override
        T parseBytes(byte[] serialized) {
            return this.marshaller.parseAsciiString(new String(serialized, Charsets.US_ASCII));
        }
    }

    static final class LazyValue<T> {
        private final BinaryStreamMarshaller<T> marshaller;
        private final T value;
        private volatile byte[] serialized;

        static <T> LazyValue<T> create(Key<T> key, T value) {
            return new LazyValue<T>(Preconditions.checkNotNull(LazyValue.getBinaryStreamMarshaller(key)), value);
        }

        LazyValue(BinaryStreamMarshaller<T> marshaller, T value) {
            this.marshaller = marshaller;
            this.value = value;
        }

        InputStream toStream() {
            return Preconditions.checkNotNull(this.marshaller.toStream(this.value), "null marshaller.toStream()");
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        byte[] toBytes() {
            if (this.serialized == null) {
                LazyValue lazyValue = this;
                synchronized (lazyValue) {
                    if (this.serialized == null) {
                        this.serialized = Metadata.streamToBytes(this.toStream());
                    }
                }
            }
            return this.serialized;
        }

        <T2> T2 toObject(Key<T2> key) {
            BinaryStreamMarshaller<T2> marshaller;
            if (key.serializesToStreams() && (marshaller = LazyValue.getBinaryStreamMarshaller(key)) != null) {
                return marshaller.parseStream(this.toStream());
            }
            return key.parseBytes(this.toBytes());
        }

        @Nullable
        private static <T> BinaryStreamMarshaller<T> getBinaryStreamMarshaller(Key<T> key) {
            return key.getMarshaller(BinaryStreamMarshaller.class);
        }
    }

    private static class LazyStreamBinaryKey<T>
    extends Key<T> {
        private final BinaryStreamMarshaller<T> marshaller;

        private LazyStreamBinaryKey(String name, BinaryStreamMarshaller<T> marshaller) {
            super(name, false, marshaller);
            Preconditions.checkArgument(name.endsWith(Metadata.BINARY_HEADER_SUFFIX), "Binary header is named %s. It must end with %s", (Object)name, (Object)Metadata.BINARY_HEADER_SUFFIX);
            Preconditions.checkArgument(name.length() > Metadata.BINARY_HEADER_SUFFIX.length(), "empty key name");
            this.marshaller = Preconditions.checkNotNull(marshaller, "marshaller is null");
        }

        @Override
        byte[] toBytes(T value) {
            return Metadata.streamToBytes(Preconditions.checkNotNull(this.marshaller.toStream(value), "null marshaller.toStream()"));
        }

        @Override
        T parseBytes(byte[] serialized) {
            return this.marshaller.parseStream(new ByteArrayInputStream(serialized));
        }

        @Override
        boolean serializesToStreams() {
            return true;
        }
    }

    private static class BinaryKey<T>
    extends Key<T> {
        private final BinaryMarshaller<T> marshaller;

        private BinaryKey(String name, BinaryMarshaller<T> marshaller) {
            super(name, false, marshaller);
            Preconditions.checkArgument(name.endsWith(Metadata.BINARY_HEADER_SUFFIX), "Binary header is named %s. It must end with %s", (Object)name, (Object)Metadata.BINARY_HEADER_SUFFIX);
            Preconditions.checkArgument(name.length() > Metadata.BINARY_HEADER_SUFFIX.length(), "empty key name");
            this.marshaller = Preconditions.checkNotNull(marshaller, "marshaller is null");
        }

        @Override
        byte[] toBytes(T value) {
            return Preconditions.checkNotNull(this.marshaller.toBytes(value), "null marshaller.toBytes()");
        }

        @Override
        T parseBytes(byte[] serialized) {
            return this.marshaller.parseBytes(serialized);
        }
    }

    @Immutable
    public static abstract class Key<T> {
        private static final BitSet VALID_T_CHARS = Key.generateValidTChars();
        private final String originalName;
        private final String name;
        private final byte[] nameBytes;
        private final Object marshaller;

        public static <T> Key<T> of(String name, BinaryMarshaller<T> marshaller) {
            return new BinaryKey(name, marshaller);
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6575")
        public static <T> Key<T> of(String name, BinaryStreamMarshaller<T> marshaller) {
            return new LazyStreamBinaryKey(name, marshaller);
        }

        public static <T> Key<T> of(String name, AsciiMarshaller<T> marshaller) {
            return Key.of(name, false, marshaller);
        }

        static <T> Key<T> of(String name, boolean pseudo, AsciiMarshaller<T> marshaller) {
            return new AsciiKey(name, pseudo, marshaller);
        }

        static <T> Key<T> of(String name, boolean pseudo, TrustedAsciiMarshaller<T> marshaller) {
            return new TrustedAsciiKey(name, pseudo, marshaller);
        }

        private static BitSet generateValidTChars() {
            int c;
            BitSet valid = new BitSet(127);
            valid.set(45);
            valid.set(95);
            valid.set(46);
            for (c = 48; c <= 57; c = (int)((char)(c + 1))) {
                valid.set(c);
            }
            for (c = 97; c <= 122; c = (int)((char)(c + 1))) {
                valid.set(c);
            }
            return valid;
        }

        private static String validateName(String n, boolean pseudo) {
            Preconditions.checkNotNull(n, "name");
            Preconditions.checkArgument(!n.isEmpty(), "token must have at least 1 tchar");
            if (n.equals("connection")) {
                logger.log(Level.WARNING, "Metadata key is 'Connection', which should not be used. That is used by HTTP/1 for connection-specific headers which are not to be forwarded. There is probably an HTTP/1 conversion bug. Simply removing the Connection header is not enough; you should remove all headers it references as well. See RFC 7230 section 6.1", new RuntimeException("exception to show backtrace"));
            }
            for (int i = 0; i < n.length(); ++i) {
                char tChar = n.charAt(i);
                if (pseudo && tChar == ':' && i == 0) continue;
                Preconditions.checkArgument(VALID_T_CHARS.get(tChar), "Invalid character '%s' in key name '%s'", tChar, (Object)n);
            }
            return n;
        }

        private Key(String name, boolean pseudo, Object marshaller) {
            this.originalName = Preconditions.checkNotNull(name, "name");
            this.name = Key.validateName(this.originalName.toLowerCase(Locale.ROOT), pseudo);
            this.nameBytes = this.name.getBytes(Charsets.US_ASCII);
            this.marshaller = marshaller;
        }

        public final String originalName() {
            return this.originalName;
        }

        public final String name() {
            return this.name;
        }

        @VisibleForTesting
        byte[] asciiName() {
            return this.nameBytes;
        }

        public final boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Key key = (Key)o;
            return this.name.equals(key.name);
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "Key{name='" + this.name + "'}";
        }

        abstract byte[] toBytes(T var1);

        abstract T parseBytes(byte[] var1);

        boolean serializesToStreams() {
            return false;
        }

        @Nullable
        final <M> M getMarshaller(Class<M> marshallerClass) {
            if (marshallerClass.isInstance(this.marshaller)) {
                return marshallerClass.cast(this.marshaller);
            }
            return null;
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/6575")
    public static interface BinaryStreamMarshaller<T> {
        public InputStream toStream(T var1);

        public T parseStream(InputStream var1);
    }

    public static interface AsciiMarshaller<T> {
        public String toAsciiString(T var1);

        public T parseAsciiString(String var1);
    }

    public static interface BinaryMarshaller<T> {
        public byte[] toBytes(T var1);

        public T parseBytes(byte[] var1);
    }

    private final class IterableAt<T>
    implements Iterable<T> {
        private final Key<T> key;
        private int startIdx;

        private IterableAt(Key<T> key, int startIdx) {
            this.key = key;
            this.startIdx = startIdx;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>(){
                private boolean hasNext = true;
                private int idx = IterableAt.access$000(IterableAt.this);

                @Override
                public boolean hasNext() {
                    if (this.hasNext) {
                        return true;
                    }
                    while (this.idx < Metadata.this.size) {
                        if (Metadata.this.bytesEqual(IterableAt.this.key.asciiName(), Metadata.this.name(this.idx))) {
                            this.hasNext = true;
                            return this.hasNext;
                        }
                        ++this.idx;
                    }
                    return false;
                }

                @Override
                public T next() {
                    if (this.hasNext()) {
                        this.hasNext = false;
                        return Metadata.this.valueAsT(this.idx++, IterableAt.this.key);
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        static /* synthetic */ int access$000(IterableAt x0) {
            return x0.startIdx;
        }
    }
}

