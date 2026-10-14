/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.generic.IsIterable;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005u3q!\u0003\u0006\u0011\u0002\u0007\u0005\u0011\u0003C\u0003\u0018\u0001\u0011\u0005\u0001\u0004B\u0003\u001d\u0001\t\u0005Q\u0004C\u0004%\u0001\t\u0007I\u0011A\u0013\t\u000bu\u0002a\u0011\u0001 \b\u000b\u0005S\u0001\u0012\u0001\"\u0007\u000b%Q\u0001\u0012\u0001#\t\u000b!3A\u0011A%\t\u000b)3A1A&\u0003\u001d%\u001b\u0018\n^3sC\ndWm\u00148dK*\u00111\u0002D\u0001\bO\u0016tWM]5d\u0015\tia\"\u0001\u0006d_2dWm\u0019;j_:T\u0011aD\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t\u00112f\u0005\u0002\u0001'A\u0011A#F\u0007\u0002\u001d%\u0011aC\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005I\u0002C\u0001\u000b\u001b\u0013\tYbB\u0001\u0003V]&$(!A!\u0012\u0005y\t\u0003C\u0001\u000b \u0013\t\u0001cBA\u0004O_RD\u0017N\\4\u0011\u0005Q\u0011\u0013BA\u0012\u000f\u0005\r\te._\u0001\u000bG>tg/\u001a:tS>tW#\u0001\u0014\u0011\tQ9\u0013&L\u0005\u0003Q9\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005)ZC\u0002\u0001\u0003\u0006Y\u0001\u0011\r!\b\u0002\u0005%\u0016\u0004(\u000fE\u0002/_Ej\u0011\u0001D\u0005\u0003a1\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004\"A\r\u0002\u000e\u0003\u0001Aca\u0001\u001b8qiZ\u0004C\u0001\u000b6\u0013\t1dB\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001:\u0003):3m\u001c8wKJ\u001c\u0018n\u001c8(A%\u001c\bE\\8xA\u0005\u0004S.\u001a;i_\u0012\u0004c.Y7fI\u0002:\u0013\r\u001d9ms\u001e\nQa]5oG\u0016\f\u0013\u0001P\u0001\u0007e9\n4G\f\u0019\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u00055z\u0004\"\u0002!\u0005\u0001\u0004I\u0013\u0001B2pY2\fa\"S:Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002D\r5\t!bE\u0002\u0007'\u0015\u0003\"a\u0011$\n\u0005\u001dS!!G%t\u0013R,'/\u00192mK>s7-\u001a'poB\u0013\u0018n\u001c:jif\fa\u0001P5oSRtD#\u0001\"\u00025%$XM]1cY\u0016|enY3Jg&#XM]1cY\u0016|enY3\u0016\u00071\u0013&,F\u0001N%\tq\u0005K\u0002\u0003P\r\u0001i%\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004cA\"\u0001#B\u0019!FU-\u0005\u000bMC!\u0019\u0001+\u0003\u0007\r\u001b\u0005'\u0006\u0002V1F\u0011aD\u0016\t\u0004]=:\u0006C\u0001\u0016Y\t\u0015a\"K1\u0001\u001e!\tQ#\fB\u0003\\\u0011\t\u0007QD\u0001\u0002Ba\u0015!AD\u0014\u0001Z\u0001")
public interface IsIterableOnce<Repr> {
    public static <CC0 extends IterableOnce<Object>, A0> IsIterableOnce<CC0> iterableOnceIsIterableOnce() {
        return new IsIterableOnce<CC0>(){
            private Function1<CC0, IterableOnce<A0>> conversion;

            public Function1<CC0, IterableOnce<A0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<A0>> x$1) {
                this.conversion = x$1;
            }

            public IterableOnce<A0> apply(CC0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static <Repr> IsIterableOnce<Repr> isIterableLikeIsIterableOnce(IsIterable<Repr> isIterableLike) {
        return isIterableLike;
    }

    public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<Repr, IterableOnce<Object>> var1);

    public Function1<Repr, IterableOnce<Object>> conversion();

    public IterableOnce<Object> apply(Repr var1);

    public static void $init$(IsIterableOnce $this) {
        $this.scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq((Function1<Object, IterableOnce> & Serializable)x$1 -> $this.apply(x$1));
    }
}

