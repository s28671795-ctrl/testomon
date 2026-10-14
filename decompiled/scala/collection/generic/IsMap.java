/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.Function1;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.View;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.LongMap;
import scala.collection.mutable.AnyRefMap;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0015aaB\b\u0011!\u0003\r\na\u0006\u0003\u0006Y\u0001\u0011\t!\n\u0003\u0006[\u0001\u0011\t!J\u0003\u0005]\u0001\u0001q\u0006C\u00036\u0001\u0019\u0005cg\u0002\u0004\u0003\u0004AA\t\u0001\u0014\u0004\u0006\u001fAA\t!\u0013\u0005\u0006\u0015\u001a!\taS\u0003\u0005\u001b\u001a\u0001a\nC\u0003`\r\u0011\r\u0001\rC\u0004\u0002$\u0019!\u0019!!\n\t\u000f\u0005mc\u0001b\u0001\u0002^!9\u00111\u0011\u0004\u0005\u0004\u0005\u0015\u0005bBAV\r\u0011\r\u0011Q\u0016\u0005\b\u0003\u001b4A1AAh\u0005\u0015I5/T1q\u0015\t\t\"#A\u0004hK:,'/[2\u000b\u0005M!\u0012AC2pY2,7\r^5p]*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005a\u00193c\u0001\u0001\u001a;A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0007yy\u0012%D\u0001\u0011\u0013\t\u0001\u0003C\u0001\u0006Jg&#XM]1cY\u0016\u0004\"AI\u0012\r\u0001\u0011)A\u0005\u0001b\u0001K\t!!+\u001a9s#\t1\u0013\u0006\u0005\u0002\u001bO%\u0011\u0001\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ\"&\u0003\u0002,)\t\u0019\u0011I\\=\u0003\u0003-\u0013\u0011A\u0016\u0002\u0002\u0003B!!\u0004\r\u001a5\u0013\t\tDC\u0001\u0004UkBdWM\r\t\u0003g\u0005i\u0011\u0001\u0001\t\u0003g\t\tQ!\u00199qYf$2aNA\u0000!\u001dA\u0014H\r\u001b<\u0003wl\u0011AE\u0005\u0003uI\u0011a!T1q\u001fB\u001cX\u0003\u0002\u001fB\u0003s\u00042\u0001O\u001f@\u0013\tq$C\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\u0015Q\u0002\u0007QA|!\t\u0011\u0013\tB\u0003C\u0007\n\u0007QEA\u0001Y\u0011\u0011!U\tA@\u0002\u0015qbwnY1mA\u0005\u0003h(B\u0003G\u000f\u0002\tIO\u0001\u0002Ba\u001a)\u0001J\u0002\u0001\u0002h\naAH]3gS:,W.\u001a8u}M\u0011a!G\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00031\u0003\"A\b\u0004\u0003\rQ+\b\u000f\\3e+\tyEK\u0005\u0002Q3\u0019!\u0001J\u0002\u0001P\u000b\u00111\u0005\u000b\u0001*\u0016\u0007M[V\fE\u0002#)f#Q!\u0016\u0005C\u0002Y\u0013\u0011AR\u000b\u0003K]#a\u0001\u0017+\u0005\u0006\u0004)#\u0001B0%IE\u0002BA\u0007\u0019[9B\u0011!e\u0017\u0003\u0006\u0005F\u0013\r!\n\t\u0003Eu#QAX)C\u0002\u0015\u0012\u0011!W\u0001\f[\u0006\u0004x\n]:Jg6\u000b\u0007/\u0006\u0004bM\u0006%\u0011qB\u000b\u0002EJ\u00111\r\u001a\u0004\u0005\u0011\u001a\u0001!\rE\u0002\u001f\u0001\u0015\u0004bA\t4\u0002\b\u00055A!B4\n\u0005\u0004A'aA\"DaU\u0019\u0011\u000e\u001c8\u0012\u0005\u0019R\u0007c\u0002\u001d:W6|\u0017Q\u0001\t\u0003E1$QA\u00114C\u0002\u0015\u0002\"A\t8\u0005\u000by3'\u0019A\u0013\u0016\tA\u001c\u00181\u0001\t\u0004qu\n\b#\u0002\u000e1e\u0006\u0005\u0001C\u0001\u0012t\t\u0015\u0011EO1\u0001&\u0011\u0011!U\u000fA@\u0006\t\u00193\b\u0001\u001f\u0004\u0005\u0011\u001a\u0001qO\u0005\u0002w3U\u0019\u0011\u0010 @\u0011\u0007aj$\u0010\u0005\u0003\u001baml\bC\u0001\u0012}\t\u0015\u0011UO1\u0001&!\t\u0011c\u0010B\u0003_k\n\u0007Qe\u0003\u0001\u0011\u0007\t\n\u0019\u0001B\u0003_i\n\u0007Q\u0005\u0005\u0003#M.l\u0007c\u0001\u0012\u0002\n\u00111\u00111B\u0005C\u0002\u0015\u0012!a\u0013\u0019\u0011\u0007\t\ny\u0001\u0002\u0004\u0002\u0012%\u0011\r!\n\u0002\u0003-B*Q\u0001L2\u0001\u0003\u000f)Q!L2\u0001\u0003\u001b)a!!\u0007d\u0001\u0005m!!A\"\u0011\r\t2\u0017QDA\u0011!\u0011\ty\"a\u0005\u000e\u0003\r\u0004B!a\b\u0002\u0016\u0005aQ.\u00199WS\u0016<\u0018j]'baVA\u0011qEA\u0019\u0003\u000f\nY%\u0006\u0002\u0002*I!\u00111FA\u0017\r\u0015Ae\u0001AA\u0015!\u0011q\u0002!a\f\u0011\u000f\t\n\t$!\u0012\u0002J\u00111qM\u0003b\u0001\u0003g)b!!\u000e\u0002@\u0005\r\u0013c\u0001\u0014\u00028A9\u0001(!\u000f\u0002>\u0005\u0005\u0013bAA\u001e%\t9Q*\u00199WS\u0016<\bc\u0001\u0012\u0002@\u00111!)!\rC\u0002\u0015\u00022AIA\"\t\u0019q\u0016\u0011\u0007b\u0001KA\u0019!%a\u0012\u0005\r\u0005-!B1\u0001&!\r\u0011\u00131\n\u0003\u0007\u0003#Q!\u0019A\u0013\u0006\r1\nY\u0003AA#\u000b\u0019i\u00131\u0006\u0001\u0002J\u00159\u0011\u0011DA\u0016\u0001\u0005M\u0003#\u0002\u001d\u0002V\u0005e\u0013bAA,%\t!a+[3x!\u0019Q\u0002'!\u0012\u0002J\u0005q\u0011M\\=SK\u001al\u0015\r]%t\u001b\u0006\u0004XCBA0\u0003k\nY(\u0006\u0002\u0002bI!\u00111MA3\r\u0015Ae\u0001AA1!\u0011q\u0002!a\u001a\u0011\u0011\u0005%\u0014qNA:\u0003sj!!a\u001b\u000b\u0007\u00055$#A\u0004nkR\f'\r\\3\n\t\u0005E\u00141\u000e\u0002\n\u0003:L(+\u001a4NCB\u00042AIA;\t\u001d\tYa\u0003b\u0001\u0003o\n\"AJ\r\u0011\u0007\t\nY\b\u0002\u0004\u0002\u0012-\u0011\r!J\u0003\u0007Y\u0005\r\u0004!a\u001d\u0006\r5\n\u0019\u0007AA=\u000b\u001d\tI\"a\u0019\u0001\u0003O\n1\"\u001b8u\u001b\u0006\u0004\u0018j]'baV!\u0011qQAO+\t\tII\u0005\u0003\u0002\f\u00065e!\u0002%\u0007\u0001\u0005%\u0005\u0003\u0002\u0010\u0001\u0003\u001f\u0003b!!%\u0002\u0018\u0006mUBAAJ\u0015\r\t)JE\u0001\nS6lW\u000f^1cY\u0016LA!!'\u0002\u0014\n1\u0011J\u001c;NCB\u00042AIAO\t\u0019\t\t\u0002\u0004b\u0001K\u00151A&a#\u0001\u0003C\u00032AGAR\u0013\r\t)\u000b\u0006\u0002\u0004\u0013:$XAB\u0017\u0002\f\u0002\tY*B\u0004\u0002\u001a\u0005-\u0005!a$\u0002\u00191|gnZ'ba&\u001bX*\u00199\u0016\t\u0005=\u0016qX\u000b\u0003\u0003c\u0013B!a-\u00026\u001a)\u0001J\u0002\u0001\u00022B!a\u0004AA\\!\u0019\t\t*!/\u0002>&!\u00111XAJ\u0005\u001dauN\\4NCB\u00042AIA`\t\u0019\t\t\"\u0004b\u0001K\u00151A&a-\u0001\u0003\u0007\u00042AGAc\u0013\r\t9\r\u0006\u0002\u0005\u0019>tw-\u0002\u0004.\u0003g\u0003\u0011QX\u0003\b\u00033\t\u0019\fAA\\\u0003MiW\u000f^1cY\u0016duN\\4NCBL5/T1q+\u0011\t\t.a8\u0016\u0005\u0005M'\u0003BAk\u0003/4Q\u0001\u0013\u0004\u0001\u0003'\u0004BA\b\u0001\u0002ZB1\u0011\u0011NAn\u0003;LA!a/\u0002lA\u0019!%a8\u0005\r\u0005EaB1\u0001&\u000b\u0019a\u0013Q\u001b\u0001\u0002D\u00161Q&!6\u0001\u0003;,q!!\u0007\u0002V\u0002\tIN\u0005\u0002H3U1\u00111^Ay\u0003k\u0004B\u0001O\u001f\u0002nB1!\u0004MAx\u0003g\u00042AIAy\t\u0015\u0011UI1\u0001&!\r\u0011\u0013Q\u001f\u0003\u0006=\u0016\u0013\r!\n\t\u0004E\u0005eH!\u00020D\u0005\u0004)\u0003cA\u001a\u0002~&\u0019\u0011\u0011D\u0010\t\r\t\u0005A\u00011\u0001\"\u0003\u0005\u0019\u0017!B%t\u001b\u0006\u0004\b")
public interface IsMap<Repr>
extends IsIterable<Repr> {
    public static <V0> IsMap<scala.collection.mutable.LongMap<V0>> mutableLongMapIsMap() {
        return new IsMap<scala.collection.mutable.LongMap<V0>>(){
            private Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> conversion;

            public Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<scala.collection.mutable.LongMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, scala.collection.mutable.LongMap<V0>> apply(scala.collection.mutable.LongMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <V0> IsMap<LongMap<V0>> longMapIsMap() {
        return new IsMap<LongMap<V0>>(){
            private Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> conversion;

            public Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<LongMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, LongMap<V0>> apply(LongMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <V0> IsMap<IntMap<V0>> intMapIsMap() {
        return new IsMap<IntMap<V0>>(){
            private Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> conversion;

            public Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<IntMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, IntMap<V0>> apply(IntMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <K0, V0> IsMap<AnyRefMap<K0, V0>> anyRefMapIsMap() {
        return new IsMap<AnyRefMap<K0, V0>>(){
            private Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> conversion;

            public Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<AnyRefMap<K0, V0>, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, AnyRefMap<K0, V0>> apply(AnyRefMap<K0, V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <CC0 extends MapView<Object, Object>, K0, V0> IsMap<CC0> mapViewIsMap() {
        return new IsMap<CC0>(){
            private Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> conversion;

            public Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, View<Tuple2<K0, V0>>> apply(CC0 c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <CC0 extends MapOps<Object, Object, ?, CC0>, K0, V0> IsMap<CC0> mapOpsIsMap() {
        return new IsMap<CC0>(){
            private Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> conversion;

            public Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, CC0> apply(CC0 c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public MapOps<Object, Object, ?, Object> apply(Repr var1);
}

