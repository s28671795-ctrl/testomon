/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteString$ByteString1C$;
import akka.util.CompactByteString;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import scala.Array$;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.math.Integral;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class CompactByteString$
implements Serializable {
    public static final CompactByteString$ MODULE$ = new CompactByteString$();
    private static final CompactByteString empty = ByteString$ByteString1C$.MODULE$.empty();

    public CompactByteString apply(byte[] bytes) {
        if (ArrayOps$.MODULE$.isEmpty$extension(Predef$.MODULE$.byteArrayOps(bytes))) {
            return this.empty();
        }
        return ByteString$ByteString1C$.MODULE$.apply((byte[])bytes.clone());
    }

    public CompactByteString apply(Seq<Object> bytes) {
        if (bytes.isEmpty()) {
            return this.empty();
        }
        byte[] ar = new byte[bytes.size()];
        bytes.copyToArray(ar);
        return ByteString$ByteString1C$.MODULE$.apply(ar);
    }

    public CompactByteString apply(IterableOnce<Object> bytes) {
        Iterator<Object> it = bytes.iterator();
        if (it.isEmpty()) {
            return this.empty();
        }
        return ByteString$ByteString1C$.MODULE$.apply((byte[])it.toArray(ClassTag$.MODULE$.Byte()));
    }

    public <T> CompactByteString apply(Seq<T> bytes, Integral<T> num) {
        if (bytes.isEmpty()) {
            return this.empty();
        }
        return ByteString$ByteString1C$.MODULE$.apply((byte[])bytes.iterator().map((Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToByte(CompactByteString$.$anonfun$apply$2(num, x))).to(Array$.MODULE$.toFactory(Array$.MODULE$, ClassTag$.MODULE$.Byte())));
    }

    public CompactByteString apply(ByteBuffer bytes) {
        if (bytes.remaining() < 1) {
            return this.empty();
        }
        byte[] ar = new byte[bytes.remaining()];
        bytes.get(ar);
        return ByteString$ByteString1C$.MODULE$.apply(ar);
    }

    public CompactByteString apply(String string) {
        return this.apply(string, StandardCharsets.UTF_8);
    }

    public CompactByteString apply(String string, String charset) {
        if (string.isEmpty()) {
            return this.empty();
        }
        return ByteString$ByteString1C$.MODULE$.apply(string.getBytes(charset));
    }

    public CompactByteString apply(String string, Charset charset) {
        if (string.isEmpty()) {
            return this.empty();
        }
        return ByteString$ByteString1C$.MODULE$.apply(string.getBytes(charset));
    }

    public CompactByteString fromArray(byte[] array, int offset, int length) {
        int copyOffset = Math.max(offset, 0);
        int copyLength = Math.max(Math.min(array.length - copyOffset, length), 0);
        if (copyLength == 0) {
            return this.empty();
        }
        byte[] copyArray = new byte[copyLength];
        Array$.MODULE$.copy(array, copyOffset, copyArray, 0, copyLength);
        return ByteString$ByteString1C$.MODULE$.apply(copyArray);
    }

    public CompactByteString empty() {
        return empty;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CompactByteString$.class);
    }

    public static final /* synthetic */ byte $anonfun$apply$2(Integral num$1, Object x) {
        return (byte)num$1.toInt(x);
    }

    private CompactByteString$() {
    }
}

