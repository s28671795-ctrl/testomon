/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.grpc.netty.shaded.io.grpc.netty.AbstractHttp2Headers;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.handler.codec.CharSequenceValueConverter;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Error;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class GrpcHttp2HeadersUtils {
    GrpcHttp2HeadersUtils() {
    }

    static final class GrpcHttp2ResponseHeaders
    extends GrpcHttp2InboundHeaders {
        GrpcHttp2ResponseHeaders(int numHeadersGuess) {
            super(numHeadersGuess);
        }

        @Override
        public Http2Headers add(CharSequence csName, CharSequence csValue) {
            AsciiString name = this.validateName(GrpcHttp2ResponseHeaders.requireAsciiString(csName));
            AsciiString value = GrpcHttp2ResponseHeaders.requireAsciiString(csValue);
            return this.add(name, value);
        }

        @Override
        public CharSequence get(CharSequence csName) {
            AsciiString name = GrpcHttp2ResponseHeaders.requireAsciiString(csName);
            return this.get(name);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append('[');
            builder.append(this.namesAndValuesToString()).append(']');
            return builder.toString();
        }
    }

    static final class GrpcHttp2RequestHeaders
    extends GrpcHttp2InboundHeaders {
        private static final AsciiString PATH_HEADER = AsciiString.of(":path");
        private static final AsciiString AUTHORITY_HEADER = AsciiString.of(":authority");
        private static final AsciiString METHOD_HEADER = AsciiString.of(":method");
        private static final AsciiString SCHEME_HEADER = AsciiString.of(":scheme");
        private AsciiString path;
        private AsciiString authority;
        private AsciiString method;
        private AsciiString scheme;
        private AsciiString te;

        GrpcHttp2RequestHeaders(int numHeadersGuess) {
            super(numHeadersGuess);
        }

        @Override
        public Http2Headers add(CharSequence csName, CharSequence csValue) {
            AsciiString name = this.validateName(GrpcHttp2RequestHeaders.requireAsciiString(csName));
            AsciiString value = GrpcHttp2RequestHeaders.requireAsciiString(csValue);
            if (GrpcHttp2RequestHeaders.isPseudoHeader(name)) {
                AsciiString previous = this.getPseudoHeader(name);
                if (previous != null) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Duplicate %s header", name));
                }
                this.setPseudoHeader(name, value);
                return this;
            }
            if (GrpcHttp2RequestHeaders.equals(Utils.TE_HEADER, name)) {
                this.te = value;
                return this;
            }
            return this.add(name, value);
        }

        @Override
        public CharSequence get(CharSequence csName) {
            AsciiString name = GrpcHttp2RequestHeaders.requireAsciiString(csName);
            if (GrpcHttp2RequestHeaders.isPseudoHeader(name)) {
                return this.getPseudoHeader(name);
            }
            if (GrpcHttp2RequestHeaders.equals(Utils.TE_HEADER, name)) {
                return this.te;
            }
            return this.get(name);
        }

        private AsciiString getPseudoHeader(AsciiString name) {
            if (GrpcHttp2RequestHeaders.equals(PATH_HEADER, name)) {
                return this.path;
            }
            if (GrpcHttp2RequestHeaders.equals(AUTHORITY_HEADER, name)) {
                return this.authority;
            }
            if (GrpcHttp2RequestHeaders.equals(METHOD_HEADER, name)) {
                return this.method;
            }
            if (GrpcHttp2RequestHeaders.equals(SCHEME_HEADER, name)) {
                return this.scheme;
            }
            return null;
        }

        private void setPseudoHeader(AsciiString name, AsciiString value) {
            if (GrpcHttp2RequestHeaders.equals(PATH_HEADER, name)) {
                this.path = value;
            } else if (GrpcHttp2RequestHeaders.equals(AUTHORITY_HEADER, name)) {
                this.authority = value;
            } else if (GrpcHttp2RequestHeaders.equals(METHOD_HEADER, name)) {
                this.method = value;
            } else if (GrpcHttp2RequestHeaders.equals(SCHEME_HEADER, name)) {
                this.scheme = value;
            } else {
                PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Illegal pseudo-header '%s' in request.", name));
                throw new AssertionError();
            }
        }

        @Override
        public CharSequence path() {
            return this.path;
        }

        @Override
        public CharSequence authority() {
            return this.authority;
        }

        @Override
        public CharSequence method() {
            return this.method;
        }

        @Override
        public CharSequence scheme() {
            return this.scheme;
        }

        @Override
        public List<CharSequence> getAll(CharSequence csName) {
            AsciiString name = GrpcHttp2RequestHeaders.requireAsciiString(csName);
            if (GrpcHttp2RequestHeaders.isPseudoHeader(name)) {
                AsciiString value = this.getPseudoHeader(name);
                if (value == null) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(value);
            }
            if (GrpcHttp2RequestHeaders.equals(Utils.TE_HEADER, name)) {
                return Collections.singletonList(this.te);
            }
            return super.getAll(csName);
        }

        @Override
        public boolean remove(CharSequence csName) {
            AsciiString name = GrpcHttp2RequestHeaders.requireAsciiString(csName);
            if (GrpcHttp2RequestHeaders.isPseudoHeader(name)) {
                if (this.getPseudoHeader(name) == null) {
                    return false;
                }
                this.setPseudoHeader(name, null);
                return true;
            }
            if (GrpcHttp2RequestHeaders.equals(Utils.TE_HEADER, name)) {
                boolean wasPresent = this.te != null;
                this.te = null;
                return wasPresent;
            }
            return super.remove(name);
        }

        @Override
        public int size() {
            int size = 0;
            if (this.path != null) {
                ++size;
            }
            if (this.authority != null) {
                ++size;
            }
            if (this.method != null) {
                ++size;
            }
            if (this.scheme != null) {
                ++size;
            }
            if (this.te != null) {
                ++size;
            }
            return size += super.size();
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append('[');
            boolean prependSeparator = false;
            if (this.path != null) {
                GrpcHttp2RequestHeaders.appendNameAndValue(builder, PATH_HEADER, this.path, prependSeparator);
                prependSeparator = true;
            }
            if (this.authority != null) {
                GrpcHttp2RequestHeaders.appendNameAndValue(builder, AUTHORITY_HEADER, this.authority, prependSeparator);
                prependSeparator = true;
            }
            if (this.method != null) {
                GrpcHttp2RequestHeaders.appendNameAndValue(builder, METHOD_HEADER, this.method, prependSeparator);
                prependSeparator = true;
            }
            if (this.scheme != null) {
                GrpcHttp2RequestHeaders.appendNameAndValue(builder, SCHEME_HEADER, this.scheme, prependSeparator);
                prependSeparator = true;
            }
            if (this.te != null) {
                GrpcHttp2RequestHeaders.appendNameAndValue(builder, Utils.TE_HEADER, this.te, prependSeparator);
            }
            String namesAndValues = this.namesAndValuesToString();
            if (builder.length() > 0 && namesAndValues.length() > 0) {
                builder.append(", ");
            }
            builder.append(namesAndValues);
            builder.append(']');
            return builder.toString();
        }
    }

    static abstract class GrpcHttp2InboundHeaders
    extends AbstractHttp2Headers {
        private static final AsciiString binaryHeaderSuffix = new AsciiString("-bin".getBytes(Charsets.US_ASCII));
        private byte[][] namesAndValues;
        private AsciiString[] values;
        private int namesAndValuesIdx;

        GrpcHttp2InboundHeaders(int numHeadersGuess) {
            Preconditions.checkArgument(numHeadersGuess > 0, "numHeadersGuess needs to be positive: %s", numHeadersGuess);
            this.namesAndValues = new byte[numHeadersGuess * 2][];
            this.values = new AsciiString[numHeadersGuess];
        }

        @Override
        protected Http2Headers add(AsciiString name, AsciiString value) {
            byte[] nameBytes = GrpcHttp2InboundHeaders.bytes(name);
            if (!name.endsWith(binaryHeaderSuffix)) {
                byte[] valueBytes = GrpcHttp2InboundHeaders.bytes(value);
                this.addHeader(value, nameBytes, valueBytes);
                return this;
            }
            int startPos = 0;
            int endPos = -1;
            while (endPos < value.length()) {
                int indexOfComma = value.indexOf(',', startPos);
                endPos = indexOfComma == -1 ? value.length() : indexOfComma;
                AsciiString curVal = value.subSequence(startPos, endPos, false);
                byte[] valueBytes = BaseEncoding.base64().decode(curVal);
                startPos = indexOfComma + 1;
                this.addHeader(curVal, nameBytes, valueBytes);
            }
            return this;
        }

        private void addHeader(AsciiString value, byte[] nameBytes, byte[] valueBytes) {
            if (this.namesAndValuesIdx == this.namesAndValues.length) {
                this.expandHeadersAndValues();
            }
            this.values[this.namesAndValuesIdx / 2] = value;
            this.namesAndValues[this.namesAndValuesIdx] = nameBytes;
            ++this.namesAndValuesIdx;
            this.namesAndValues[this.namesAndValuesIdx] = valueBytes;
            ++this.namesAndValuesIdx;
        }

        @Override
        protected CharSequence get(AsciiString name) {
            for (int i = 0; i < this.namesAndValuesIdx; i += 2) {
                if (!GrpcHttp2InboundHeaders.equals(name, this.namesAndValues[i])) continue;
                return this.values[i / 2];
            }
            return null;
        }

        @Override
        public boolean contains(CharSequence name) {
            return this.get(name) != null;
        }

        @Override
        public CharSequence status() {
            return this.get(Http2Headers.PseudoHeaderName.STATUS.value());
        }

        @Override
        public List<CharSequence> getAll(CharSequence csName) {
            AsciiString name = GrpcHttp2InboundHeaders.requireAsciiString(csName);
            ArrayList<CharSequence> returnValues = new ArrayList<CharSequence>(4);
            for (int i = 0; i < this.namesAndValuesIdx; i += 2) {
                if (!GrpcHttp2InboundHeaders.equals(name, this.namesAndValues[i])) continue;
                returnValues.add(this.values[i / 2]);
            }
            return returnValues;
        }

        @Override
        @CanIgnoreReturnValue
        public boolean remove(CharSequence csName) {
            int i;
            AsciiString name = GrpcHttp2InboundHeaders.requireAsciiString(csName);
            for (i = 0; i < this.namesAndValuesIdx && !GrpcHttp2InboundHeaders.equals(name, this.namesAndValues[i]); i += 2) {
            }
            if (i >= this.namesAndValuesIdx) {
                return false;
            }
            int dest = i;
            while (i < this.namesAndValuesIdx) {
                if (!GrpcHttp2InboundHeaders.equals(name, this.namesAndValues[i])) {
                    this.values[dest / 2] = this.values[i / 2];
                    this.namesAndValues[dest] = this.namesAndValues[i];
                    this.namesAndValues[dest + 1] = this.namesAndValues[i + 1];
                    dest += 2;
                }
                i += 2;
            }
            this.namesAndValuesIdx = dest;
            return true;
        }

        @Override
        public Http2Headers set(CharSequence name, CharSequence value) {
            this.remove(name);
            return this.add(name, value);
        }

        @Override
        public Http2Headers setLong(CharSequence name, long value) {
            return this.set(name, (CharSequence)AsciiString.of(CharSequenceValueConverter.INSTANCE.convertLong(value)));
        }

        byte[][] namesAndValues() {
            return this.namesAndValues;
        }

        protected int numHeaders() {
            return this.namesAndValuesIdx / 2;
        }

        protected static boolean equals(AsciiString str0, byte[] str1) {
            return GrpcHttp2InboundHeaders.equals(str0.array(), str0.arrayOffset(), str0.length(), str1, 0, str1.length);
        }

        protected static boolean equals(AsciiString str0, AsciiString str1) {
            return GrpcHttp2InboundHeaders.equals(str0.array(), str0.arrayOffset(), str0.length(), str1.array(), str1.arrayOffset(), str1.length());
        }

        protected static boolean equals(byte[] bytes0, int offset0, int length0, byte[] bytes1, int offset1, int length1) {
            if (length0 != length1) {
                return false;
            }
            return PlatformDependent.equals(bytes0, offset0, bytes1, offset1, length0);
        }

        protected static byte[] bytes(AsciiString str) {
            return str.isEntireArrayUsed() ? str.array() : str.toByteArray();
        }

        protected static AsciiString requireAsciiString(CharSequence cs) {
            if (!(cs instanceof AsciiString)) {
                throw new IllegalArgumentException("AsciiString expected. Was: " + cs.getClass().getName());
            }
            return (AsciiString)cs;
        }

        protected static boolean isPseudoHeader(AsciiString str) {
            return !str.isEmpty() && str.charAt(0) == ':';
        }

        protected AsciiString validateName(AsciiString str) {
            int offset = str.arrayOffset();
            int length = str.length();
            byte[] data = str.array();
            for (int i = offset; i < offset + length; ++i) {
                if (!AsciiString.isUpperCase(data[i])) continue;
                PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name '%s'", str));
            }
            return str;
        }

        private void expandHeadersAndValues() {
            int newValuesLen = Math.max(2, this.values.length + this.values.length / 2);
            int newNamesAndValuesLen = newValuesLen * 2;
            byte[][] newNamesAndValues = new byte[newNamesAndValuesLen][];
            AsciiString[] newValues = new AsciiString[newValuesLen];
            System.arraycopy(this.namesAndValues, 0, newNamesAndValues, 0, this.namesAndValues.length);
            System.arraycopy(this.values, 0, newValues, 0, this.values.length);
            this.namesAndValues = newNamesAndValues;
            this.values = newValues;
        }

        @Override
        public int size() {
            return this.numHeaders();
        }

        @Override
        public Iterator<Map.Entry<CharSequence, CharSequence>> iterator() {
            return this.namesAndValuesToImmutableList().iterator();
        }

        protected static void appendNameAndValue(StringBuilder builder, CharSequence name, CharSequence value, boolean prependSeparator) {
            if (prependSeparator) {
                builder.append(", ");
            }
            builder.append(name).append(": ").append(value);
        }

        private List<Map.Entry<CharSequence, CharSequence>> namesAndValuesToImmutableList() {
            ArrayList<AbstractMap.SimpleImmutableEntry<String, AsciiString>> list = new ArrayList<AbstractMap.SimpleImmutableEntry<String, AsciiString>>(this.values.length);
            for (int i = 0; i < this.namesAndValuesIdx; i += 2) {
                String name = new String(this.namesAndValues[i], Charsets.US_ASCII);
                AsciiString value = this.values[i / 2];
                list.add(new AbstractMap.SimpleImmutableEntry<String, AsciiString>(name, value));
            }
            return Collections.unmodifiableList(list);
        }

        protected final String namesAndValuesToString() {
            StringBuilder builder = new StringBuilder();
            boolean prependSeparator = false;
            for (Map.Entry<CharSequence, CharSequence> entry : this.namesAndValuesToImmutableList()) {
                GrpcHttp2InboundHeaders.appendNameAndValue(builder, entry.getKey(), entry.getValue(), prependSeparator);
                prependSeparator = true;
            }
            return builder.toString();
        }
    }

    static final class GrpcHttp2ClientHeadersDecoder
    extends DefaultHttp2HeadersDecoder {
        GrpcHttp2ClientHeadersDecoder(long maxHeaderListSize) {
            super(true, maxHeaderListSize);
        }

        @Override
        protected GrpcHttp2InboundHeaders newHeaders() {
            return new GrpcHttp2ResponseHeaders(this.numberOfHeadersGuess());
        }
    }

    static final class GrpcHttp2ServerHeadersDecoder
    extends DefaultHttp2HeadersDecoder {
        GrpcHttp2ServerHeadersDecoder(long maxHeaderListSize) {
            super(true, maxHeaderListSize);
        }

        @Override
        protected GrpcHttp2InboundHeaders newHeaders() {
            return new GrpcHttp2RequestHeaders(this.numberOfHeadersGuess());
        }
    }
}

