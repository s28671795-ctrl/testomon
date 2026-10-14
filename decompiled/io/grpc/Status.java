/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import io.grpc.Metadata;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@CheckReturnValue
@Immutable
public final class Status {
    private static final List<Status> STATUS_LIST = Status.buildStatusList();
    public static final Status OK = Code.OK.toStatus();
    public static final Status CANCELLED = Code.CANCELLED.toStatus();
    public static final Status UNKNOWN = Code.UNKNOWN.toStatus();
    public static final Status INVALID_ARGUMENT = Code.INVALID_ARGUMENT.toStatus();
    public static final Status DEADLINE_EXCEEDED = Code.DEADLINE_EXCEEDED.toStatus();
    public static final Status NOT_FOUND = Code.NOT_FOUND.toStatus();
    public static final Status ALREADY_EXISTS = Code.ALREADY_EXISTS.toStatus();
    public static final Status PERMISSION_DENIED = Code.PERMISSION_DENIED.toStatus();
    public static final Status UNAUTHENTICATED = Code.UNAUTHENTICATED.toStatus();
    public static final Status RESOURCE_EXHAUSTED = Code.RESOURCE_EXHAUSTED.toStatus();
    public static final Status FAILED_PRECONDITION = Code.FAILED_PRECONDITION.toStatus();
    public static final Status ABORTED = Code.ABORTED.toStatus();
    public static final Status OUT_OF_RANGE = Code.OUT_OF_RANGE.toStatus();
    public static final Status UNIMPLEMENTED = Code.UNIMPLEMENTED.toStatus();
    public static final Status INTERNAL = Code.INTERNAL.toStatus();
    public static final Status UNAVAILABLE = Code.UNAVAILABLE.toStatus();
    public static final Status DATA_LOSS = Code.DATA_LOSS.toStatus();
    static final Metadata.Key<Status> CODE_KEY = Metadata.Key.of("grpc-status", false, new StatusCodeMarshaller());
    private static final Metadata.TrustedAsciiMarshaller<String> STATUS_MESSAGE_MARSHALLER = new StatusMessageMarshaller();
    static final Metadata.Key<String> MESSAGE_KEY = Metadata.Key.of("grpc-message", false, STATUS_MESSAGE_MARSHALLER);
    private final Code code;
    private final String description;
    private final Throwable cause;

    private static List<Status> buildStatusList() {
        TreeMap<Integer, Status> canonicalizer = new TreeMap<Integer, Status>();
        for (Code code : Code.values()) {
            Status replaced = canonicalizer.put(code.value(), new Status(code));
            if (replaced == null) continue;
            throw new IllegalStateException("Code value duplication between " + replaced.getCode().name() + " & " + code.name());
        }
        return Collections.unmodifiableList(new ArrayList(canonicalizer.values()));
    }

    public static Status fromCodeValue(int codeValue) {
        if (codeValue < 0 || codeValue >= STATUS_LIST.size()) {
            return UNKNOWN.withDescription("Unknown code " + codeValue);
        }
        return STATUS_LIST.get(codeValue);
    }

    private static Status fromCodeValue(byte[] asciiCodeValue) {
        if (asciiCodeValue.length == 1 && asciiCodeValue[0] == 48) {
            return OK;
        }
        return Status.fromCodeValueSlow(asciiCodeValue);
    }

    private static Status fromCodeValueSlow(byte[] asciiCodeValue) {
        int index = 0;
        int codeValue = 0;
        switch (asciiCodeValue.length) {
            case 2: {
                if (asciiCodeValue[index] < 48 || asciiCodeValue[index] > 57) break;
                codeValue += (asciiCodeValue[index++] - 48) * 10;
            }
            case 1: {
                if (asciiCodeValue[index] < 48 || asciiCodeValue[index] > 57 || (codeValue += asciiCodeValue[index] - 48) >= STATUS_LIST.size()) break;
                return STATUS_LIST.get(codeValue);
            }
        }
        return UNKNOWN.withDescription("Unknown code " + new String(asciiCodeValue, Charsets.US_ASCII));
    }

    public static Status fromCode(Code code) {
        return code.toStatus();
    }

    public static Status fromThrowable(Throwable t2) {
        for (Throwable cause = Preconditions.checkNotNull(t2, "t"); cause != null; cause = cause.getCause()) {
            if (cause instanceof StatusException) {
                return ((StatusException)cause).getStatus();
            }
            if (!(cause instanceof StatusRuntimeException)) continue;
            return ((StatusRuntimeException)cause).getStatus();
        }
        return UNKNOWN.withCause(t2);
    }

    @Nullable
    public static Metadata trailersFromThrowable(Throwable t2) {
        for (Throwable cause = Preconditions.checkNotNull(t2, "t"); cause != null; cause = cause.getCause()) {
            if (cause instanceof StatusException) {
                return ((StatusException)cause).getTrailers();
            }
            if (!(cause instanceof StatusRuntimeException)) continue;
            return ((StatusRuntimeException)cause).getTrailers();
        }
        return null;
    }

    static String formatThrowableMessage(Status status) {
        if (status.description == null) {
            return status.code.toString();
        }
        return (Object)((Object)status.code) + ": " + status.description;
    }

    private Status(Code code) {
        this(code, null, null);
    }

    private Status(Code code, @Nullable String description, @Nullable Throwable cause) {
        this.code = Preconditions.checkNotNull(code, "code");
        this.description = description;
        this.cause = cause;
    }

    public Status withCause(Throwable cause) {
        if (Objects.equal(this.cause, cause)) {
            return this;
        }
        return new Status(this.code, this.description, cause);
    }

    public Status withDescription(String description) {
        if (Objects.equal(this.description, description)) {
            return this;
        }
        return new Status(this.code, description, this.cause);
    }

    public Status augmentDescription(String additionalDetail) {
        if (additionalDetail == null) {
            return this;
        }
        if (this.description == null) {
            return new Status(this.code, additionalDetail, this.cause);
        }
        return new Status(this.code, this.description + "\n" + additionalDetail, this.cause);
    }

    public Code getCode() {
        return this.code;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public Throwable getCause() {
        return this.cause;
    }

    public boolean isOk() {
        return Code.OK == this.code;
    }

    public StatusRuntimeException asRuntimeException() {
        return new StatusRuntimeException(this);
    }

    public StatusRuntimeException asRuntimeException(@Nullable Metadata trailers) {
        return new StatusRuntimeException(this, trailers);
    }

    public StatusException asException() {
        return new StatusException(this);
    }

    public StatusException asException(@Nullable Metadata trailers) {
        return new StatusException(this, trailers);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("code", this.code.name()).add("description", this.description).add("cause", this.cause != null ? Throwables.getStackTraceAsString(this.cause) : this.cause).toString();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    private static final class StatusMessageMarshaller
    implements Metadata.TrustedAsciiMarshaller<String> {
        private static final byte[] HEX = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};

        private StatusMessageMarshaller() {
        }

        @Override
        public byte[] toAsciiString(String value) {
            byte[] valueBytes = value.getBytes(Charsets.UTF_8);
            for (int i = 0; i < valueBytes.length; ++i) {
                byte b = valueBytes[i];
                if (!StatusMessageMarshaller.isEscapingChar(b)) continue;
                return StatusMessageMarshaller.toAsciiStringSlow(valueBytes, i);
            }
            return valueBytes;
        }

        private static boolean isEscapingChar(byte b) {
            return b < 32 || b >= 126 || b == 37;
        }

        private static byte[] toAsciiStringSlow(byte[] valueBytes, int ri) {
            byte[] escapedBytes = new byte[ri + (valueBytes.length - ri) * 3];
            if (ri != 0) {
                System.arraycopy(valueBytes, 0, escapedBytes, 0, ri);
            }
            int wi = ri;
            while (ri < valueBytes.length) {
                byte b = valueBytes[ri];
                if (StatusMessageMarshaller.isEscapingChar(b)) {
                    escapedBytes[wi] = 37;
                    escapedBytes[wi + 1] = HEX[b >> 4 & 0xF];
                    escapedBytes[wi + 2] = HEX[b & 0xF];
                    wi += 3;
                } else {
                    escapedBytes[wi++] = b;
                }
                ++ri;
            }
            return Arrays.copyOf(escapedBytes, wi);
        }

        @Override
        public String parseAsciiString(byte[] value) {
            for (int i = 0; i < value.length; ++i) {
                byte b = value[i];
                if (b >= 32 && b < 126 && (b != 37 || i + 2 >= value.length)) continue;
                return StatusMessageMarshaller.parseAsciiStringSlow(value);
            }
            return new String(value, 0);
        }

        private static String parseAsciiStringSlow(byte[] value) {
            ByteBuffer buf = ByteBuffer.allocate(value.length);
            int i = 0;
            while (i < value.length) {
                if (value[i] == 37 && i + 2 < value.length) {
                    try {
                        buf.put((byte)Integer.parseInt(new String(value, i + 1, 2, Charsets.US_ASCII), 16));
                        i += 3;
                        continue;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                buf.put(value[i]);
                ++i;
            }
            return new String(buf.array(), 0, buf.position(), Charsets.UTF_8);
        }
    }

    private static final class StatusCodeMarshaller
    implements Metadata.TrustedAsciiMarshaller<Status> {
        private StatusCodeMarshaller() {
        }

        @Override
        public byte[] toAsciiString(Status status) {
            return status.getCode().valueAscii();
        }

        @Override
        public Status parseAsciiString(byte[] serialized) {
            return Status.fromCodeValue(serialized);
        }
    }

    public static enum Code {
        OK(0),
        CANCELLED(1),
        UNKNOWN(2),
        INVALID_ARGUMENT(3),
        DEADLINE_EXCEEDED(4),
        NOT_FOUND(5),
        ALREADY_EXISTS(6),
        PERMISSION_DENIED(7),
        RESOURCE_EXHAUSTED(8),
        FAILED_PRECONDITION(9),
        ABORTED(10),
        OUT_OF_RANGE(11),
        UNIMPLEMENTED(12),
        INTERNAL(13),
        UNAVAILABLE(14),
        DATA_LOSS(15),
        UNAUTHENTICATED(16);

        private final int value;
        private final byte[] valueAscii;

        private Code(int value) {
            this.value = value;
            this.valueAscii = Integer.toString(value).getBytes(Charsets.US_ASCII);
        }

        public int value() {
            return this.value;
        }

        public Status toStatus() {
            return (Status)STATUS_LIST.get(this.value);
        }

        private byte[] valueAscii() {
            return this.valueAscii;
        }
    }
}

