/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BidiShape$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tUh\u0001\u0002\u0014(\u00052B\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t!\u0012\u0005\t=\u0002\u0011\t\u0012)A\u0005\r\"Aq\f\u0001BK\u0002\u0013\u0005\u0001\r\u0003\u0005i\u0001\tE\t\u0015!\u0003b\u0011!I\u0007A!f\u0001\n\u0003Q\u0007\u0002\u00039\u0001\u0005#\u0005\u000b\u0011B6\t\u0011E\u0004!Q3A\u0005\u0002ID\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006Ia\u001d\u0005\u0006s\u0002!\tA\u001f\u0005\n\u0003\u0003\u0001!\u0019!C!\u0003\u0007A\u0001\"a\b\u0001A\u0003%\u0011Q\u0001\u0005\n\u0003C\u0001!\u0019!C!\u0003GA\u0001\"!\r\u0001A\u0003%\u0011Q\u0005\u0005\u0007s\u0002!\t!a\r\t\u000f\u0005\u0015\u0003\u0001\"\u0011\u0002H!I\u0011\u0011\n\u0001\u0002\u0002\u0013\u0005\u00111\n\u0005\n\u0003s\u0002\u0011\u0013!C\u0001\u0003wB\u0011\"a#\u0001#\u0003%\t!!$\t\u0013\u0005u\u0005!%A\u0005\u0002\u0005}\u0005\"CAX\u0001E\u0005I\u0011AAY\u0011%\t\t\rAA\u0001\n\u0003\n\u0019\rC\u0005\u0002V\u0002\t\t\u0011\"\u0001\u0002X\"I\u0011q\u001c\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u001d\u0005\n\u0003O\u0004\u0011\u0011!C!\u0003SD\u0011\"a=\u0001\u0003\u0003%\t!!>\t\u0013\u0005}\b!!A\u0005B\t\u0005\u0001\"\u0003B\u0003\u0001\u0005\u0005I\u0011\tB\u0004\u0011%\u0011I\u0001AA\u0001\n\u0003\u0012Y\u0001C\u0005\u0003\u000e\u0001\t\t\u0011\"\u0011\u0003\u0010\u001d9!1C\u0014\t\u0002\tUaA\u0002\u0014(\u0011\u0003\u00119\u0002\u0003\u0004z?\u0011\u0005!\u0011\u0006\u0005\b\u0005WyB\u0011\u0001B\u0017\u0011\u001d\u0011\u0019f\bC\u0001\u0005+B\u0011Ba! \u0003\u0003%\tI!\"\t\u0013\tMv$!A\u0005\u0002\nU\u0006\"\u0003Bv?\u0005\u0005I\u0011\u0002Bw\u0005%\u0011\u0015\u000eZ5TQ\u0006\u0004XM\u0003\u0002)S\u000511\u000f\u001e:fC6T\u0011AK\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u000b5beM\u001c<\u0014\t\u0001q#\u0007\u000f\t\u0003_Aj\u0011aJ\u0005\u0003c\u001d\u0012Qa\u00155ba\u0016\u0004\"a\r\u001c\u000e\u0003QR\u0011!N\u0001\u0006g\u000e\fG.Y\u0005\u0003oQ\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002:\u0003:\u0011!h\u0010\b\u0003wyj\u0011\u0001\u0010\u0006\u0003{-\na\u0001\u0010:p_Rt\u0014\"A\u001b\n\u0005\u0001#\u0014a\u00029bG.\fw-Z\u0005\u0003\u0005\u000e\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u0011\u001b\u0002\u0007%t\u0017'F\u0001G!\rys)S\u0005\u0003\u0011\u001e\u0012Q!\u00138mKRT#AS+\u0011\u0005-cE\u0002\u0001\u0003\u0007\u001b\u0002A)\u0019\u0001(\u0003\u0007%s\u0017'\u0005\u0002P%B\u00111\u0007U\u0005\u0003#R\u0012qAT8uQ&tw\r\u0005\u00024'&\u0011A\u000b\u000e\u0002\u0004\u0003:L8&\u0001,\u0011\u0005]cV\"\u0001-\u000b\u0005eS\u0016!C;oG\",7m[3e\u0015\tYF'\u0001\u0006b]:|G/\u0019;j_:L!!\u0018-\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0003j]F\u0002\u0013\u0001B8viF*\u0012!\u0019\t\u0004_\t$\u0017BA2(\u0005\u0019yU\u000f\u001e7fi*\u0012Q-\u0016\t\u0003\u0017\u001a$aa\u001a\u0001\u0005\u0006\u0004q%\u0001B(viF\nQa\\;uc\u0001\n1!\u001b83+\u0005Y\u0007cA\u0018HY*\u0012Q.\u0016\t\u0003\u0017:$aa\u001c\u0001\t\u0006\u0004q%aA%oe\u0005!\u0011N\u001c\u001a!\u0003\u0011yW\u000f\u001e\u001a\u0016\u0003M\u00042a\f2uU\t)X\u000b\u0005\u0002Lm\u00121q\u000f\u0001CC\u00029\u0013AaT;ue\u0005)q.\u001e;3A\u00051A(\u001b8jiz\"Ra\u001f?~}~\u0004ba\f\u0001KK6,\b\"\u0002#\n\u0001\u00041\u0005\"B0\n\u0001\u0004\t\u0007\"B5\n\u0001\u0004Y\u0007\"B9\n\u0001\u0004\u0019\u0018AB5oY\u0016$8/\u0006\u0002\u0002\u0006A1\u0011qAA\t\u0003+i!!!\u0003\u000b\t\u0005-\u0011QB\u0001\nS6lW\u000f^1cY\u0016T1!a\u00045\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003'\tIAA\u0002TKF\u0004D!a\u0006\u0002\u001cA!qfRA\r!\rY\u00151\u0004\u0003\u000b\u0003;Y\u0011\u0011!A\u0001\u0006\u0003q%\u0001B0%eQ\nq!\u001b8mKR\u001c\b%A\u0004pkRdW\r^:\u0016\u0005\u0005\u0015\u0002CBA\u0004\u0003#\t9\u0003\r\u0003\u0002*\u00055\u0002\u0003B\u0018c\u0003W\u00012aSA\u0017\t)\ty#DA\u0001\u0002\u0003\u0015\tA\u0014\u0002\u0005?\u0012\u0012T'\u0001\u0005pkRdW\r^:!)\u0015Y\u0018QGA \u0011\u001d\t9D\u0004a\u0001\u0003s\t1\u0001^8q!\u0015y\u00131\b&f\u0013\r\tid\n\u0002\n\r2|wo\u00155ba\u0016Dq!!\u0011\u000f\u0001\u0004\t\u0019%\u0001\u0004c_R$x.\u001c\t\u0006_\u0005mR.^\u0001\tI\u0016,\u0007oQ8qsR\t10\u0001\u0003d_BLXCCA'\u0003'\n9&a\u0017\u0002`QQ\u0011qJA1\u0003O\ni'a\u001d\u0011\u0015=\u0002\u0011\u0011KA+\u00033\ni\u0006E\u0002L\u0003'\"Q!\u0014\tC\u00029\u00032aSA,\t\u00159\u0007C1\u0001O!\rY\u00151\f\u0003\u0006_B\u0011\rA\u0014\t\u0004\u0017\u0006}C!B<\u0011\u0005\u0004q\u0005\u0002\u0003#\u0011!\u0003\u0005\r!a\u0019\u0011\t=:\u0015Q\r\u0016\u0004\u0003#*\u0006\u0002C0\u0011!\u0003\u0005\r!!\u001b\u0011\t=\u0012\u00171\u000e\u0016\u0004\u0003+*\u0006\u0002C5\u0011!\u0003\u0005\r!a\u001c\u0011\t=:\u0015\u0011\u000f\u0016\u0004\u00033*\u0006\u0002C9\u0011!\u0003\u0005\r!!\u001e\u0011\t=\u0012\u0017q\u000f\u0016\u0004\u0003;*\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u000b\u0003{\n\u0019)!\"\u0002\b\u0006%UCAA@U\r\t\t)\u0016\t\u0004_\u001dSE!B'\u0012\u0005\u0004qE!B4\u0012\u0005\u0004qE!B8\u0012\u0005\u0004qE!B<\u0012\u0005\u0004q\u0015AD2paf$C-\u001a4bk2$HEM\u000b\u000b\u0003\u001f\u000b)*a&\u0002\u001a\u0006mUCAAIU\r\t\u0019*\u0016\t\u0004_\t,G!B'\u0013\u0005\u0004qE!B4\u0013\u0005\u0004qE!B8\u0013\u0005\u0004qE!B<\u0013\u0005\u0004q\u0015AD2paf$C-\u001a4bk2$HeM\u000b\u000b\u0003C\u000b9+!+\u0002,\u00065VCAARU\r\t)+\u0016\t\u0004_\u001dkG!B'\u0014\u0005\u0004qE!B4\u0014\u0005\u0004qE!B8\u0014\u0005\u0004qE!B<\u0014\u0005\u0004q\u0015AD2paf$C-\u001a4bk2$H\u0005N\u000b\u000b\u0003g\u000bI,a/\u0002>\u0006}VCAA[U\r\t9,\u0016\t\u0004_\t,H!B'\u0015\u0005\u0004qE!B4\u0015\u0005\u0004qE!B8\u0015\u0005\u0004qE!B<\u0015\u0005\u0004q\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002FB!\u0011qYAi\u001b\t\tIM\u0003\u0003\u0002L\u00065\u0017\u0001\u00027b]\u001eT!!a4\u0002\t)\fg/Y\u0005\u0005\u0003'\fIM\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u00033\u00042aMAn\u0013\r\ti\u000e\u000e\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004%\u0006\r\b\"CAs/\u0005\u0005\t\u0019AAm\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u001e\t\u0006\u0003[\fyOU\u0007\u0003\u0003\u001bIA!!=\u0002\u000e\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t90!@\u0011\u0007M\nI0C\u0002\u0002|R\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0002ff\t\t\u00111\u0001S\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\u0015'1\u0001\u0005\n\u0003KT\u0012\u0011!a\u0001\u00033\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u00033\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u000b\fa!Z9vC2\u001cH\u0003BA|\u0005#A\u0001\"!:\u001e\u0003\u0003\u0005\rAU\u0001\n\u0005&$\u0017n\u00155ba\u0016\u0004\"aL\u0010\u0014\u000b}\u0011IBa\b\u0011\u0007M\u0012Y\"C\u0002\u0003\u001eQ\u0012a!\u00118z%\u00164\u0007\u0003\u0002B\u0011\u0005Oi!Aa\t\u000b\t\t\u0015\u0012QZ\u0001\u0003S>L1A\u0011B\u0012)\t\u0011)\"A\u0005ge>lg\t\\8xgVQ!q\u0006B\u001b\u0005w\u0011\tEa\u0012\u0015\r\tE\"1\nB(!)y\u0003Aa\r\u0003:\t}\"Q\t\t\u0004\u0017\nUBA\u0002B\u001cC\t\u0007aJ\u0001\u0002JcA\u00191Ja\u000f\u0005\r\tu\u0012E1\u0001O\u0005\ty\u0015\u0007E\u0002L\u0005\u0003\"aAa\u0011\"\u0005\u0004q%AA%3!\rY%q\t\u0003\u0007\u0005\u0013\n#\u0019\u0001(\u0003\u0005=\u0013\u0004bBA\u001cC\u0001\u0007!Q\n\t\b_\u0005m\"1\u0007B\u001d\u0011\u001d\t\t%\ta\u0001\u0005#\u0002raLA\u001e\u0005\u007f\u0011)%\u0001\u0002pMVQ!q\u000bB/\u0005C\u0012)G!\u001b\u0015\u0015\te#1\u000eB9\u0005o\u0012i\b\u0005\u00060\u0001\tm#q\fB2\u0005O\u00022a\u0013B/\t\u0015i%E1\u0001O!\rY%\u0011\r\u0003\u0006O\n\u0012\rA\u0014\t\u0004\u0017\n\u0015D!B8#\u0005\u0004q\u0005cA&\u0003j\u0011)qO\tb\u0001\u001d\"1AI\ta\u0001\u0005[\u0002BaL$\u0003p)\u001a!1L+\t\r}\u0013\u0003\u0019\u0001B:!\u0011y#M!\u001e+\u0007\t}S\u000b\u0003\u0004jE\u0001\u0007!\u0011\u0010\t\u0005_\u001d\u0013YHK\u0002\u0003dUCa!\u001d\u0012A\u0002\t}\u0004\u0003B\u0018c\u0005\u0003S3Aa\u001aV\u0003\u0015\t\u0007\u000f\u001d7z+)\u00119I!$\u0003\u0012\nU%\u0011\u0014\u000b\u000b\u0005\u0013\u0013YJ!)\u0003(\n5\u0006CC\u0018\u0001\u0005\u0017\u0013yIa%\u0003\u0018B\u00191J!$\u0005\u000b5\u001b#\u0019\u0001(\u0011\u0007-\u0013\t\nB\u0003hG\t\u0007a\nE\u0002L\u0005+#Qa\\\u0012C\u00029\u00032a\u0013BM\t\u001598E1\u0001O\u0011\u0019!5\u00051\u0001\u0003\u001eB!qf\u0012BPU\r\u0011Y)\u0016\u0005\u0007?\u000e\u0002\rAa)\u0011\t=\u0012'Q\u0015\u0016\u0004\u0005\u001f+\u0006BB5$\u0001\u0004\u0011I\u000b\u0005\u00030\u000f\n-&f\u0001BJ+\"1\u0011o\ta\u0001\u0005_\u0003Ba\f2\u00032*\u001a!qS+\u0002\u000fUt\u0017\r\u001d9msVQ!q\u0017Bf\u0005'\u0014YNa9\u0015\t\te&Q\u001d\t\u0006g\tm&qX\u0005\u0004\u0005{#$AB(qi&|g\u000eE\u00064\u0005\u0003\u0014)M!4\u0003V\nu\u0017b\u0001Bbi\t1A+\u001e9mKR\u0002BaL$\u0003H*\u001a!\u0011Z+\u0011\u0007-\u0013Y\rB\u0003NI\t\u0007a\n\u0005\u00030E\n='f\u0001Bi+B\u00191Ja5\u0005\u000b\u001d$#\u0019\u0001(\u0011\t=:%q\u001b\u0016\u0004\u00053,\u0006cA&\u0003\\\u0012)q\u000e\nb\u0001\u001dB!qF\u0019BpU\r\u0011\t/\u0016\t\u0004\u0017\n\rH!B<%\u0005\u0004q\u0005\"\u0003BtI\u0005\u0005\t\u0019\u0001Bu\u0003\rAH\u0005\r\t\u000b_\u0001\u0011IM!5\u0003Z\n\u0005\u0018\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001Bx!\u0011\t9M!=\n\t\tM\u0018\u0011\u001a\u0002\u0007\u001f\nTWm\u0019;")
public final class BidiShape<In1, Out1, In2, Out2>
extends Shape
implements Product,
Serializable {
    private final Inlet<In1> in1;
    private final Outlet<Out1> out1;
    private final Inlet<In2> in2;
    private final Outlet<Out2> out2;
    private final Seq<Inlet<?>> inlets;
    private final Seq<Outlet<?>> outlets;

    public static <In1, Out1, In2, Out2> Option<Tuple4<Inlet<In1>, Outlet<Out1>, Inlet<In2>, Outlet<Out2>>> unapply(BidiShape<In1, Out1, In2, Out2> x$0) {
        return BidiShape$.MODULE$.unapply(x$0);
    }

    public static <In1, Out1, In2, Out2> BidiShape<In1, Out1, In2, Out2> apply(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        return BidiShape$.MODULE$.apply(in1, out1, in2, out2);
    }

    public static <In1, Out1, In2, Out2> BidiShape<In1, Out1, In2, Out2> of(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        return BidiShape$.MODULE$.of(in1, out1, in2, out2);
    }

    public static <I1, O1, I2, O2> BidiShape<I1, O1, I2, O2> fromFlows(FlowShape<I1, O1> top, FlowShape<I2, O2> bottom) {
        return BidiShape$.MODULE$.fromFlows(top, bottom);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Inlet<In1> in1() {
        return this.in1;
    }

    public Outlet<Out1> out1() {
        return this.out1;
    }

    public Inlet<In2> in2() {
        return this.in2;
    }

    public Outlet<Out2> out2() {
        return this.out2;
    }

    @Override
    public Seq<Inlet<?>> inlets() {
        return this.inlets;
    }

    @Override
    public Seq<Outlet<?>> outlets() {
        return this.outlets;
    }

    @Override
    public BidiShape<In1, Out1, In2, Out2> deepCopy() {
        return new BidiShape<In1, Out1, In2, Out2>(this.in1().carbonCopy(), this.out1().carbonCopy(), this.in2().carbonCopy(), this.out2().carbonCopy());
    }

    public <In1, Out1, In2, Out2> BidiShape<In1, Out1, In2, Out2> copy(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        return new BidiShape<In1, Out1, In2, Out2>(in1, out1, in2, out2);
    }

    public <In1, Out1, In2, Out2> Inlet<In1> copy$default$1() {
        return this.in1();
    }

    public <In1, Out1, In2, Out2> Outlet<Out1> copy$default$2() {
        return this.out1();
    }

    public <In1, Out1, In2, Out2> Inlet<In2> copy$default$3() {
        return this.in2();
    }

    public <In1, Out1, In2, Out2> Outlet<Out2> copy$default$4() {
        return this.out2();
    }

    @Override
    public String productPrefix() {
        return "BidiShape";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.in1();
            }
            case 1: {
                return this.out1();
            }
            case 2: {
                return this.in2();
            }
            case 3: {
                return this.out2();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof BidiShape;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "in1";
            }
            case 1: {
                return "out1";
            }
            case 2: {
                return "in2";
            }
            case 3: {
                return "out2";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof BidiShape)) return false;
        boolean bl = true;
        if (!bl) return false;
        BidiShape bidiShape = (BidiShape)x$1;
        Inlet<In1> inlet = this.in1();
        Inlet<In1> inlet2 = bidiShape.in1();
        if (inlet == null) {
            if (inlet2 != null) {
                return false;
            }
        } else if (!((Object)inlet).equals(inlet2)) return false;
        Outlet<Out1> outlet = this.out1();
        Outlet<Out1> outlet2 = bidiShape.out1();
        if (outlet == null) {
            if (outlet2 != null) {
                return false;
            }
        } else if (!((Object)outlet).equals(outlet2)) return false;
        Inlet<In2> inlet3 = this.in2();
        Inlet<In2> inlet4 = bidiShape.in2();
        if (inlet3 == null) {
            if (inlet4 != null) {
                return false;
            }
        } else if (!((Object)inlet3).equals(inlet4)) return false;
        Outlet<Out2> outlet3 = this.out2();
        Outlet<Out2> outlet4 = bidiShape.out2();
        if (outlet3 == null) {
            if (outlet4 == null) return true;
            return false;
        } else {
            if (!((Object)outlet3).equals(outlet4)) return false;
            return true;
        }
    }

    public BidiShape(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        this.in1 = in1;
        this.out1 = out1;
        this.in2 = in2;
        this.out2 = out2;
        Product.$init$(this);
        Inlet<In1> inlet = in1;
        Inlet<In2> inlet2 = in2;
        this.inlets = Nil$.MODULE$.$colon$colon(inlet2).$colon$colon(inlet);
        Outlet<Out1> outlet = out1;
        Outlet<Out2> outlet2 = out2;
        this.outlets = Nil$.MODULE$.$colon$colon(outlet2).$colon$colon(outlet);
    }

    public BidiShape(FlowShape<In1, Out1> top, FlowShape<In2, Out2> bottom) {
        this(top.in(), top.out(), bottom.in(), bottom.out());
    }
}

