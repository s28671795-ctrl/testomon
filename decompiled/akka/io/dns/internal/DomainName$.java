/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnce;
import scala.collection.StringOps$;
import scala.collection.mutable.StringBuilder;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@InternalApi
public final class DomainName$ {
    public static final DomainName$ MODULE$ = new DomainName$();

    public short length(String name) {
        return (short)(name.length() + 2);
    }

    public void write(ByteStringBuilder it, String name) {
        ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(name), '.')), (Function1<String, Object> & Serializable)label -> {
            DomainName$.$anonfun$write$1(it, label);
            return BoxedUnit.UNIT;
        });
        it.putByte((byte)0);
    }

    public String parse(ByteIterator it, ByteString msg) {
        StringBuilder ret = new StringBuilder();
        while (true) {
            byte length;
            if ((length = it.getByte()) == 0) {
                String r = ret.result();
                return r;
            }
            Serializable serializable = ret.nonEmpty() ? ret.append('.') : BoxedUnit.UNIT;
            if ((length & 0xC0) == 192) {
                int offset = (length & 0x3F) << 8 | it.getByte() & 0xFF;
                return new java.lang.StringBuilder(0).append(ret.result()).append(this.parse(msg.iterator().drop(offset), msg)).toString();
            }
            ret.appendAll((IterableOnce<Object>)it.clone().take(length).map((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToCharacter(DomainName$.$anonfun$parse$1(BoxesRunTime.unboxToByte(x$1)))));
            it.drop(length);
        }
    }

    public static final /* synthetic */ void $anonfun$write$1(ByteStringBuilder it$1, String label) {
        it$1.putByte((byte)label.length());
        StringOps$.MODULE$.foreach$extension(Predef$.MODULE$.augmentString(label), (Function1<Object, ByteStringBuilder> & Serializable)c -> it$1.putByte((byte)BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ char $anonfun$parse$1(byte x$1) {
        return (char)x$1;
    }

    private DomainName$() {
    }
}

