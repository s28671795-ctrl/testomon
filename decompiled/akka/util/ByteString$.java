/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteString;
import akka.util.ByteString$ByteString1$;
import akka.util.ByteString$ByteString1C$;
import akka.util.ByteStringBuilder;
import akka.util.CompactByteString$;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.math.Integral;
import scala.math.Numeric$IntIsIntegral$;
import scala.runtime.ScalaRunTime$;

public final class ByteString$ {
    public static final ByteString$ MODULE$ = new ByteString$();
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final ByteString empty = ByteString$ByteString1C$.MODULE$.empty();

    public ByteString fromInts(int ... array) {
        return this.fromInts(ScalaRunTime$.MODULE$.wrapIntArray(array));
    }

    public ByteString apply(byte[] bytes) {
        return CompactByteString$.MODULE$.apply(bytes);
    }

    public ByteString apply(Seq<Object> bytes) {
        return CompactByteString$.MODULE$.apply(bytes);
    }

    public ByteString apply(IterableOnce<Object> bytes) {
        return CompactByteString$.MODULE$.apply(bytes);
    }

    public <T> ByteString apply(Seq<T> bytes, Integral<T> num) {
        return CompactByteString$.MODULE$.apply(bytes, num);
    }

    public ByteString apply(ByteBuffer bytes) {
        return CompactByteString$.MODULE$.apply(bytes);
    }

    public ByteString apply(String string) {
        return this.apply(string, StandardCharsets.UTF_8);
    }

    public ByteString apply(String string, String charset) {
        return CompactByteString$.MODULE$.apply(string, charset);
    }

    public ByteString apply(String string, Charset charset) {
        return CompactByteString$.MODULE$.apply(string, charset);
    }

    public ByteString fromArray(byte[] array) {
        return this.apply(array);
    }

    public ByteString fromArrayUnsafe(byte[] array) {
        return ByteString$ByteString1C$.MODULE$.apply(array);
    }

    public ByteString fromArray(byte[] array, int offset, int length) {
        return CompactByteString$.MODULE$.fromArray(array, offset, length);
    }

    public ByteString fromArrayUnsafe(byte[] array, int offset, int length) {
        return ByteString$ByteString1$.MODULE$.apply(array, offset, length);
    }

    public ByteString fromInts(Seq<Object> array) {
        return this.apply(array, Numeric$IntIsIntegral$.MODULE$);
    }

    public ByteString fromString(String string) {
        return this.apply(string);
    }

    public ByteString fromString(String string, String charset) {
        return this.apply(string, charset);
    }

    public ByteString fromString(String string, Charset charset) {
        return this.apply(string, charset);
    }

    public String UTF_8() {
        return UTF_8;
    }

    public ByteString fromByteBuffer(ByteBuffer buffer) {
        return this.apply(buffer);
    }

    public ByteString empty() {
        return empty;
    }

    public ByteString emptyByteString() {
        return this.empty();
    }

    public ByteStringBuilder newBuilder() {
        return new ByteStringBuilder();
    }

    public ByteStringBuilder createBuilder() {
        return new ByteStringBuilder();
    }

    private ByteString$() {
    }
}

