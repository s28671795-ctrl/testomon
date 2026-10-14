/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
import scala.Function1;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsMap;
import scala.collection.generic.IsSeq;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\raa\u0002\u0006\f!\u0003\r\tA\u0005\u0005\u0006O\u0001!\t\u0001\u000b\u0003\u0006Y\u0001\u0011\t\u0001\t\u0005\b[\u0001\u0011\r\u0011\"\u0011/\u0011\u0015A\u0005A\"\u0001J\u000f\u0015a5\u0002#\u0001N\r\u0015Q1\u0002#\u0001O\u0011\u0015\u0011f\u0001\"\u0001T\u0011\u0015!f\u0001b\u0001V\u0011\u0015Qg\u0001b\u0001l\u0005)I5/\u0013;fe\u0006\u0014G.\u001a\u0006\u0003\u00195\tqaZ3oKJL7M\u0003\u0002\u000f\u001f\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003A\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0014=M\u0019\u0001\u0001\u0006\r\u0011\u0005U1R\"A\b\n\u0005]y!AB!osJ+g\rE\u0002\u001a5qi\u0011aC\u0005\u00037-\u0011a\"S:Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002\u001e=1\u0001A!B\u0010\u0001\u0005\u0004\u0001#\u0001\u0002*faJ\f\"!\t\u0013\u0011\u0005U\u0011\u0013BA\u0012\u0010\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!F\u0013\n\u0005\u0019z!aA!os\u00061A%\u001b8ji\u0012\"\u0012!\u000b\t\u0003+)J!aK\b\u0003\tUs\u0017\u000e\u001e\u0002\u0002\u0007\u0006Q1m\u001c8wKJ\u001c\u0018n\u001c8\u0016\u0003=\u0002B!\u0006\u0019\u001de%\u0011\u0011g\u0004\u0002\n\rVt7\r^5p]F\u0002Ra\r\u001b7uuj\u0011!D\u0005\u0003k5\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011q\u0007O\u0007\u0002\u0001%\u0011\u0011H\u0007\u0002\u0002\u0003B\u00111gO\u0005\u0003y5\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003o\tAcaA C\u0007\u00163\u0005CA\u000bA\u0013\t\tuB\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001E\u0003):3m\u001c8wKJ\u001c\u0018n\u001c8(A%\u001c\bE\\8xA\u0005\u0004S.\u001a;i_\u0012\u0004c.Y7fI\u0002:\u0013\r\u001d9ms\u001e\nQa]5oG\u0016\f\u0013aR\u0001\u0007e9\n4G\f\u0019\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005IR\u0005\"B&\u0005\u0001\u0004a\u0012\u0001B2pY2\f!\"S:Ji\u0016\u0014\u0018M\u00197f!\tIbaE\u0002\u0007)=\u0003\"!\u0007)\n\u0005E[!!F%t\u0013R,'/\u00192mK2{w\u000f\u0015:j_JLG/_\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00035\u000bQ#\u001b;fe\u0006\u0014G.Z(qg&\u001b\u0018\n^3sC\ndW-F\u0002WMr+\u0012a\u0016\n\u00031j3A!\u0017\u0004\u0001/\naAH]3gS:,W.\u001a8u}A\u0019\u0011\u0004A.\u0011\u0007uaV\rB\u0003^\u0011\t\u0007aLA\u0002D\u0007B*\"a\u00182\u0012\u0005\u0005\u0002\u0007#B\u001a5Cj\"\u0007CA\u000fc\t\u0015\u0019GL1\u0001!\u0005\u0005A\u0006cA\u000f]CB\u0011QD\u001a\u0003\u0006O\"\u0011\r\u0001\t\u0002\u0003\u0003B*A!\u000f-\u0001K\u0016!A\u0006\u0017\u0001\\\u0003M\u0011\u0017\u000e^*fi>\u00038/S:Ji\u0016\u0014\u0018M\u00197f+\ta\u0017/F\u0001n%\tqwN\u0002\u0003Z\r\u0001i\u0007cA\r\u0001aB\u0011Q$\u001d\u0003\u0006e&\u0011\ra\u001d\u0002\u0003\u0007B\n\"!\t;\u0013\u0007U4\u0018P\u0002\u0003Z\r\u0001!\bCA\u001ax\u0013\tAXB\u0001\u0004CSR\u001cV\r\u001e\t\u0004gi\u0004\u0018BA>\u000e\u0005%\u0011\u0015\u000e^*fi>\u00038/\u0002\u0003:]\u0002i\bCA\u000b\u007f\u0013\tyxBA\u0002J]R,A\u0001\f8\u0001a\u0002")
public interface IsIterable<Repr>
extends IsIterableOnce<Repr> {
    public static <C0 extends BitSet & BitSetOps<C0>> IsIterable<C0> bitSetOpsIsIterable() {
        return new IsIterable<C0>(){
            private Function1<C0, IterableOps<Object, Iterable, C0>> conversion;

            public Function1<C0, IterableOps<Object, Iterable, C0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<C0, IterableOps<Object, Iterable, C0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<C0, IterableOnce<Object>> x$1) {
            }

            public IterableOps<Object, Iterable, C0> apply(C0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <A0, CC0 extends IterableOps<Object, Iterable, CC0>> IsIterable<CC0> iterableOpsIsIterable() {
        return new IsIterable<CC0>(){
            private Function1<CC0, IterableOps<A0, Iterable, CC0>> conversion;

            public Function1<CC0, IterableOps<A0, Iterable, CC0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<A0, Iterable, CC0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<A0>> x$1) {
            }

            public IterableOps<A0, Iterable, CC0> apply(CC0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <Repr> IsIterable<Repr> isMapLikeIsIterable(IsMap<Repr> isMapLike) {
        return isMapLike;
    }

    public static <Repr> IsIterable<Repr> isSeqLikeIsIterable(IsSeq<Repr> isSeqLike) {
        return isSeqLike;
    }

    public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<Repr, IterableOps<Object, Iterable, Object>> var1);

    @Override
    public Function1<Repr, IterableOps<Object, Iterable, Object>> conversion();

    public IterableOps<Object, Iterable, Object> apply(Repr var1);

    public static void $init$(IsIterable $this) {
        $this.scala$collection$generic$IsIterable$_setter_$conversion_$eq((Function1<Object, IterableOps> & Serializable)x$1 -> $this.apply(x$1));
    }
}

