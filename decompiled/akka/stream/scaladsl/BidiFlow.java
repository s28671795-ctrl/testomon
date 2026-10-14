/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Keep$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\t\u0015h\u0001\u0002\u0010 \u0005\u0019B\u0001\u0002\u0014\u0001\u0003\u0006\u0004%\t%\u0014\u0005\t)\u0002\u0011\t\u0011)A\u0005\u001d\"AQ\u000b\u0001BC\u0002\u0013\u0005c\u000b\u0003\u0005X\u0001\t\u0005\t\u0015!\u00033\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u0015q\u0006\u0001\"\u0001`\u0011\u0015Q\b\u0001\"\u0001|\u0011\u001d\t9\u0002\u0001C\u0001\u00033Aq!a\u0011\u0001\t\u0003\t)\u0005C\u0004\u0002`\u0001!\t!!\u0019\t\u000f\u0005e\u0004\u0001\"\u0001\u0002|!9\u0011q\u0010\u0001\u0005\u0002\u0005\u0005\u0005bBAK\u0001\u0011\u0005\u0013q\u0013\u0005\b\u0003G\u0003A\u0011IAS\u0011\u001d\tI\u000b\u0001C!\u0003WCq!a2\u0001\t\u0003\nI\rC\u0004\u0002H\u0002!\t%a3\t\u000f\u0005\u001d\u0007\u0001\"\u0011\u0002R\"9\u0011q\u001c\u0001\u0005B\u0005\u0005xaBAr?!\u0005\u0011Q\u001d\u0004\u0007=}A\t!a:\t\ra+B\u0011AAu\u0011!\tY/\u0006Q\u0001\n\u00055\bbBA|+\u0011\u0005\u0011\u0011 \u0005\b\u0005\u0017)B\u0011\u0001B\u0007\u0011\u001d\u0011y#\u0006C\u0001\u0005cAqA!\u001c\u0016\t\u0003\u0011y\u0007C\u0004\u0003\u001aV!\tAa'\t\u000f\tuV\u0003\"\u0001\u0003@\nA!)\u001b3j\r2|wO\u0003\u0002!C\u0005A1oY1mC\u0012\u001cHN\u0003\u0002#G\u000511\u000f\u001e:fC6T\u0011\u0001J\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\r\u001d:\u0014\tR$K'\r\u0001\u0001F\f\t\u0003S1j\u0011A\u000b\u0006\u0002W\u0005)1oY1mC&\u0011QF\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\t=\u0002$'S\u0007\u0002C%\u0011\u0011'\t\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0007_M*\u0004i\u0011$\n\u0005Q\n#!\u0003\"jI&\u001c\u0006.\u00199f!\t1t\u0007\u0004\u0001\u0005\ra\u0002\u0001R1\u0001:\u0005\tI\u0015'\u0005\u0002;{A\u0011\u0011fO\u0005\u0003y)\u0012qAT8uQ&tw\r\u0005\u0002*}%\u0011qH\u000b\u0002\u0004\u0003:L\bC\u0001\u001cB\t\u0019\u0011\u0005\u0001\"b\u0001s\t\u0011q*\r\t\u0003m\u0011#a!\u0012\u0001\t\u0006\u0004I$AA%3!\t1t\t\u0002\u0004I\u0001\u0011\u0015\r!\u000f\u0002\u0003\u001fJ\u0002\"A\u000e&\u0005\r-\u0003AQ1\u0001:\u0005\ri\u0015\r^\u0001\u0011iJ\fg/\u001a:tC2\u0014U/\u001b7eKJ,\u0012A\u0014\t\u0003\u001fJk\u0011\u0001\u0015\u0006\u0003#\u0006\nA![7qY&\u00111\u000b\u0015\u0002\u0011)J\fg/\u001a:tC2\u0014U/\u001b7eKJ\f\u0011\u0003\u001e:bm\u0016\u00148/\u00197Ck&dG-\u001a:!\u0003\u0015\u0019\b.\u00199f+\u0005\u0011\u0014AB:iCB,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u00045rk\u0006cB.\u0001k\u0001\u001be)S\u0007\u0002?!)A*\u0002a\u0001\u001d\")Q+\u0002a\u0001e\u00051\u0011m\u001d&bm\u0006,b\u0001Y4l_N<X#A1\u0011\u000f\t,gM\u001b8sm6\t1M\u0003\u0002eC\u00059!.\u0019<bINd\u0017B\u0001\u0010d!\t1t\rB\u0003i\r\t\u0007\u0011NA\u0002K\u0013F\n\"AO\u001b\u0011\u0005YZG!\u00027\u0007\u0005\u0004i'a\u0001&PcE\u0011\u0001)\u0010\t\u0003m=$Q\u0001\u001d\u0004C\u0002E\u00141AS%3#\tQ4\t\u0005\u00027g\u0012)AO\u0002b\u0001k\n\u0019!j\u0014\u001a\u0012\u0005\u0019k\u0004C\u0001\u001cx\t\u0015AhA1\u0001z\u0005\u0011QU*\u0019;\u0012\u0005%k\u0014\u0001B1u_B,b\u0001`@\u0002\u0006\u0005MAcA?\u0002\nAA1\fA\u001b\u007f\u0003\u00071\u0015\n\u0005\u00027\u007f\u00121\u0011\u0011A\u0004C\u0002e\u00121aT(2!\r1\u0014Q\u0001\u0003\u0007\u0003\u000f9!\u0019A\u001d\u0003\u0007%K%\u0007C\u0004\u0002\f\u001d\u0001\r!!\u0004\u0002\t\tLG-\u001b\t\u0007_A\ny!!\u0005\u0011\u000f=\u001a\u0004I`A\u0002\u0007B\u0019a'a\u0005\u0005\r\u0005UqA1\u0001:\u0005\u0011i\u0015\r\u001e\u001a\u0002\u000f\u0005$x\u000e]'biVQ\u00111DA\u0012\u0003O\tY$a\u000b\u0015\t\u0005u\u0011Q\b\u000b\u0005\u0003?\ty\u0003\u0005\u0006\\\u0001U\n\t#!\nG\u0003S\u00012ANA\u0012\t\u0019\t\t\u0001\u0003b\u0001sA\u0019a'a\n\u0005\r\u0005\u001d\u0001B1\u0001:!\r1\u00141\u0006\u0003\u0007\u0003[A!\u0019A\u001d\u0003\u00035Cq!!\r\t\u0001\u0004\t\u0019$A\u0004d_6\u0014\u0017N\\3\u0011\u0011%\n)$SA\u001d\u0003SI1!a\u000e+\u0005%1UO\\2uS>t'\u0007E\u00027\u0003w!a!!\u0006\t\u0005\u0004I\u0004bBA\u0006\u0011\u0001\u0007\u0011q\b\t\u0007_A\n\t%!\u000f\u0011\u0011=\u001a\u0004)!\t\u0002&\r\u000bAA[8j]V!\u0011qIA/)\u0011\tI%a\u0014\u0011\rm\u000bY%\u000e$J\u0013\r\tie\b\u0002\u0005\r2|w\u000fC\u0004\u0002R%\u0001\r!a\u0015\u0002\t\u0019dwn\u001e\t\u0007_A\n)&a\u0017\u0011\u000b=\n9\u0006Q\"\n\u0007\u0005e\u0013EA\u0005GY><8\u000b[1qKB\u0019a'!\u0018\u0005\r\u0005U\u0011B1\u0001:\u0003\u001dQw.\u001b8NCR,b!a\u0019\u0002t\u0005-D\u0003BA3\u0003k\"B!a\u001a\u0002nA91,a\u00136\r\u0006%\u0004c\u0001\u001c\u0002l\u00111\u0011Q\u0006\u0006C\u0002eBq!!\r\u000b\u0001\u0004\ty\u0007\u0005\u0005*\u0003kI\u0015\u0011OA5!\r1\u00141\u000f\u0003\u0007\u0003+Q!\u0019A\u001d\t\u000f\u0005E#\u00021\u0001\u0002xA1q\u0006MA+\u0003c\n\u0001B]3wKJ\u001cX\rZ\u000b\u0003\u0003{\u0002ra\u0017\u0001D\rV\u0002\u0015*\u0001\u000bnCBl\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/Z\u000b\u0005\u0003\u0007\u000bI\t\u0006\u0003\u0002\u0006\u0006-\u0005\u0003C.\u0001k\u0001\u001be)a\"\u0011\u0007Y\nI\t\u0002\u0004\u0002\u00161\u0011\r!\u000f\u0005\b\u0003\u001bc\u0001\u0019AAH\u0003\u00051\u0007CB\u0015\u0002\u0012&\u000b9)C\u0002\u0002\u0014*\u0012\u0011BR;oGRLwN\\\u0019\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR\u0019!,!'\t\u000f\u0005mU\u00021\u0001\u0002\u001e\u0006!\u0011\r\u001e;s!\ry\u0013qT\u0005\u0004\u0003C\u000b#AC!uiJL'-\u001e;fg\u0006i\u0011\r\u001a3BiR\u0014\u0018NY;uKN$2AWAT\u0011\u001d\tYJ\u0004a\u0001\u0003;\u000bQA\\1nK\u0012$2AWAW\u0011\u001d\tyk\u0004a\u0001\u0003c\u000bAA\\1nKB!\u00111WAa\u001d\u0011\t),!0\u0011\u0007\u0005]&&\u0004\u0002\u0002:*\u0019\u00111X\u0013\u0002\rq\u0012xn\u001c;?\u0013\r\tyLK\u0001\u0007!J,G-\u001a4\n\t\u0005\r\u0017Q\u0019\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005}&&A\u0003bgft7-F\u0001[)\rQ\u0016Q\u001a\u0005\b\u0003\u001f\f\u0002\u0019AAY\u0003)!\u0017n\u001d9bi\u000eDWM\u001d\u000b\u00065\u0006M\u0017Q\u001b\u0005\b\u0003\u001f\u0014\u0002\u0019AAY\u0011\u001d\t9N\u0005a\u0001\u00033\fq\"\u001b8qkR\u0014UO\u001a4feNK'0\u001a\t\u0004S\u0005m\u0017bAAoU\t\u0019\u0011J\u001c;\u0002\u001b\u001d,G/\u0011;ue&\u0014W\u000f^3t+\t\ti*\u0001\u0005CS\u0012Lg\t\\8x!\tYVc\u0005\u0002\u0016QQ\u0011\u0011Q]\u0001\n?&$WM\u001c;jif\u0004\u0002b\u0017\u0001>{uj\u0014q\u001e\t\u0005\u0003c\f\u00190D\u0001$\u0013\r\t)p\t\u0002\b\u001d>$Xk]3e\u0003!IG-\u001a8uSRLXCBA~\u0005\u0003\u00119!\u0006\u0002\u0002~Ba1\fAA\u0000\u0003\u007f\u0014)A!\u0002\u0002pB\u0019aG!\u0001\u0005\r\t\r\u0001D1\u0001:\u0005\u0005\t\u0005c\u0001\u001c\u0003\b\u00111!\u0011\u0002\rC\u0002e\u0012\u0011AQ\u0001\nMJ|Wn\u0012:ba\",BBa\u0004\u0003\u0016\te!Q\u0004B\u0011\u0005K!BA!\u0005\u0003(Aa1\f\u0001B\n\u0005/\u0011YBa\b\u0003$A\u0019aG!\u0006\u0005\u000baJ\"\u0019A\u001d\u0011\u0007Y\u0012I\u0002B\u0003C3\t\u0007\u0011\bE\u00027\u0005;!Q!R\rC\u0002e\u00022A\u000eB\u0011\t\u0015A\u0015D1\u0001:!\r1$Q\u0005\u0003\u0006\u0017f\u0011\r!\u000f\u0005\b\u0005SI\u0002\u0019\u0001B\u0016\u0003\u00159'/\u00199i!\u0019y\u0003G!\f\u0003$AQqf\rB\n\u0005/\u0011YBa\b\u0002\u0019\u0019\u0014x.\u001c$m_^\u001cX*\u0019;\u0016!\tM\"1\bB \u0005\u0007\u00129Ea\u0015\u0003Z\t-CC\u0002B\u001b\u0005;\u0012)\u0007\u0006\u0003\u00038\t5\u0003\u0003D.\u0001\u0005s\u0011iD!\u0011\u0003F\t%\u0003c\u0001\u001c\u0003<\u0011)\u0001H\u0007b\u0001sA\u0019aGa\u0010\u0005\u000b\tS\"\u0019A\u001d\u0011\u0007Y\u0012\u0019\u0005B\u0003F5\t\u0007\u0011\bE\u00027\u0005\u000f\"Q\u0001\u0013\u000eC\u0002e\u00022A\u000eB&\t\u0019\tiC\u0007b\u0001s!9\u0011\u0011\u0007\u000eA\u0002\t=\u0003#C\u0015\u00026\tE#q\u000bB%!\r1$1\u000b\u0003\u0007\u0005+R\"\u0019A\u001d\u0003\u00055\u000b\u0004c\u0001\u001c\u0003Z\u00111!1\f\u000eC\u0002e\u0012!!\u0014\u001a\t\u000f\t}#\u00041\u0001\u0003b\u0005)a\r\\8xcA1q\u0006\rB2\u0005#\u0002raLA,\u0005s\u0011i\u0004C\u0004\u0003hi\u0001\rA!\u001b\u0002\u000b\u0019dwn\u001e\u001a\u0011\r=\u0002$1\u000eB,!\u001dy\u0013q\u000bB!\u0005\u000b\n\u0011B\u001a:p[\u001acwn^:\u0016\u001d\tE$q\u000fB>\u0005\u007f\u0012\u0019I!$\u0003\u0018R1!1\u000fBC\u0005\u001f\u0003Bb\u0017\u0001\u0003v\te$Q\u0010BA\u0003_\u00042A\u000eB<\t\u0015A4D1\u0001:!\r1$1\u0010\u0003\u0006\u0005n\u0011\r!\u000f\t\u0004m\t}D!B#\u001c\u0005\u0004I\u0004c\u0001\u001c\u0003\u0004\u0012)\u0001j\u0007b\u0001s!9!qL\u000eA\u0002\t\u001d\u0005CB\u00181\u0005\u0013\u0013Y\tE\u00040\u0003/\u0012)H!\u001f\u0011\u0007Y\u0012i\t\u0002\u0004\u0003Vm\u0011\r!\u000f\u0005\b\u0005OZ\u0002\u0019\u0001BI!\u0019y\u0003Ga%\u0003\u0016B9q&a\u0016\u0003~\t\u0005\u0005c\u0001\u001c\u0003\u0018\u00121!1L\u000eC\u0002e\nQB\u001a:p[\u001a+hn\u0019;j_:\u001cXC\u0003BO\u0005G\u00139Ka+\u00030R1!q\u0014BY\u0005o\u0003Bb\u0017\u0001\u0003\"\n\u0015&\u0011\u0016BW\u0003_\u00042A\u000eBR\t\u0015ADD1\u0001:!\r1$q\u0015\u0003\u0006\u0005r\u0011\r!\u000f\t\u0004m\t-F!B#\u001d\u0005\u0004I\u0004c\u0001\u001c\u00030\u0012)\u0001\n\bb\u0001s!9!1\u0017\u000fA\u0002\tU\u0016\u0001C8vi\n|WO\u001c3\u0011\u000f%\n\tJ!)\u0003&\"9!\u0011\u0018\u000fA\u0002\tm\u0016aB5oE>,h\u000e\u001a\t\bS\u0005E%\u0011\u0016BW\u0003a\u0011\u0017\u000eZ5sK\u000e$\u0018n\u001c8bY&#G.\u001a+j[\u0016|W\u000f^\u000b\u0007\u0005\u0003\u00149M!4\u0015\t\t\r'\u0011\u001b\t\r7\u0002\u0011)M!2\u0003L\n-\u0017q\u001e\t\u0004m\t\u001dGA\u0002Be;\t\u0007\u0011HA\u0001J!\r1$Q\u001a\u0003\u0007\u0005\u001fl\"\u0019A\u001d\u0003\u0003=CqAa5\u001e\u0001\u0004\u0011).A\u0004uS6,w.\u001e;\u0011\t\t]'\u0011]\u0007\u0003\u00053TAAa7\u0003^\u0006AA-\u001e:bi&|gNC\u0002\u0003`*\n!bY8oGV\u0014(/\u001a8u\u0013\u0011\u0011\u0019O!7\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0002")
public final class BidiFlow<I1, O1, I2, O2, Mat>
implements Graph<BidiShape<I1, O1, I2, O2>, Mat> {
    private final TraversalBuilder traversalBuilder;
    private final BidiShape<I1, O1, I2, O2> shape;

    public static <I, O> BidiFlow<I, I, O, O, NotUsed> bidirectionalIdleTimeout(FiniteDuration timeout2) {
        return BidiFlow$.MODULE$.bidirectionalIdleTimeout(timeout2);
    }

    public static <I1, O1, I2, O2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFunctions(Function1<I1, O1> outbound, Function1<I2, O2> inbound) {
        return BidiFlow$.MODULE$.fromFunctions(outbound, inbound);
    }

    public static <I1, O1, I2, O2, M1, M2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFlows(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2) {
        return BidiFlow$.MODULE$.fromFlows(flow1, flow2);
    }

    public static <I1, O1, I2, O2, M1, M2, M> BidiFlow<I1, O1, I2, O2, M> fromFlowsMat(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2, Function2<M1, M2, M> combine) {
        return BidiFlow$.MODULE$.fromFlowsMat(flow1, flow2, combine);
    }

    public static <I1, O1, I2, O2, Mat> BidiFlow<I1, O1, I2, O2, Mat> fromGraph(Graph<BidiShape<I1, O1, I2, O2>, Mat> graph) {
        return BidiFlow$.MODULE$.fromGraph(graph);
    }

    public static <A, B> BidiFlow<A, A, B, B, NotUsed> identity() {
        return BidiFlow$.MODULE$.identity();
    }

    @Override
    public TraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public BidiShape<I1, O1, I2, O2> shape() {
        return this.shape;
    }

    public <JI1 extends I1, JO1, JI2 extends I2, JO2, JMat> akka.stream.javadsl.BidiFlow<JI1, JO1, JI2, JO2, JMat> asJava() {
        return new akka.stream.javadsl.BidiFlow(this);
    }

    public <OO1, II2, Mat2> BidiFlow<I1, OO1, II2, O2, Mat> atop(Graph<BidiShape<O1, OO1, II2, I2>, Mat2> bidi) {
        return this.atopMat(bidi, Keep$.MODULE$.left());
    }

    public <OO1, II2, Mat2, M> BidiFlow<I1, OO1, II2, O2, M> atopMat(Graph<BidiShape<O1, OO1, II2, I2>, Mat2> bidi, Function2<Mat, Mat2, M> combine) {
        if (this == BidiFlow$.MODULE$.identity()) {
            if (combine == Keep$.MODULE$.right()) {
                return BidiFlow$.MODULE$.fromGraph(bidi);
            }
            if (combine == Keep$.MODULE$.left() || combine == Keep$.MODULE$.none()) {
                return BidiFlow$.MODULE$.fromGraph(bidi).mapMaterializedValue((Function1<Object, NotUsed$> & Serializable)x$1 -> NotUsed$.MODULE$);
            }
            return BidiFlow$.MODULE$.fromGraph(bidi).mapMaterializedValue((Function1<Object, Object> & Serializable)mat2 -> combine.apply((Nothing$)((Object)NotUsed$.MODULE$), (Nothing$)mat2));
        }
        if (bidi == BidiFlow$.MODULE$.identity()) {
            if (combine == Keep$.MODULE$.left()) {
                return this;
            }
            if (combine == Keep$.MODULE$.right() || combine == Keep$.MODULE$.none()) {
                return this.mapMaterializedValue((Function1<Object, NotUsed$> & Serializable)x$2 -> NotUsed$.MODULE$);
            }
            return this.mapMaterializedValue((Function1<Object, Object> & Serializable)mat -> combine.apply((Nothing$)mat, (Nothing$)((Object)NotUsed$.MODULE$)));
        }
        Shape newBidi1Shape = ((BidiShape)this.shape()).deepCopy();
        Shape newBidi2Shape = bidi.shape().deepCopy();
        TraversalBuilder newTraversalBuilder = TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(this.traversalBuilder(), newBidi1Shape, Keep$.MODULE$.right()).add(bidi.traversalBuilder(), newBidi2Shape, combine).wire(((BidiShape)newBidi1Shape).out1(), ((BidiShape)newBidi2Shape).in1()).wire(((BidiShape)newBidi2Shape).out2(), ((BidiShape)newBidi1Shape).in2());
        return new BidiFlow(newTraversalBuilder, new BidiShape(((BidiShape)newBidi1Shape).in1(), ((BidiShape)newBidi2Shape).out1(), ((BidiShape)newBidi2Shape).in2(), ((BidiShape)newBidi1Shape).out2()));
    }

    public <Mat2> Flow<I1, O2, Mat> join(Graph<FlowShape<O1, I2>, Mat2> flow) {
        return this.joinMat(flow, Keep$.MODULE$.left());
    }

    public <Mat2, M> Flow<I1, O2, M> joinMat(Graph<FlowShape<O1, I2>, Mat2> flow, Function2<Mat, Mat2, M> combine) {
        Shape newBidiShape = ((BidiShape)this.shape()).deepCopy();
        Shape newFlowShape = flow.shape().deepCopy();
        TraversalBuilder resultBuilder = TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(this.traversalBuilder(), newBidiShape, Keep$.MODULE$.right()).add(flow.traversalBuilder(), newFlowShape, combine).wire(((BidiShape)newBidiShape).out1(), ((FlowShape)newFlowShape).in()).wire(((FlowShape)newFlowShape).out(), ((BidiShape)newBidiShape).in2());
        FlowShape newShape = new FlowShape(((BidiShape)newBidiShape).in1(), ((BidiShape)newBidiShape).out2());
        return new Flow(LinearTraversalBuilder$.MODULE$.fromBuilder(resultBuilder, newShape, Keep$.MODULE$.right()), newShape);
    }

    public BidiFlow<I2, O2, I1, O1, Mat> reversed() {
        return new BidiFlow(this.traversalBuilder(), new BidiShape(((BidiShape)this.shape()).in2(), ((BidiShape)this.shape()).out2(), ((BidiShape)this.shape()).in1(), ((BidiShape)this.shape()).out1()));
    }

    public <Mat2> BidiFlow<I1, O1, I2, O2, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.traversalBuilder().transformMat(f), this.shape());
    }

    public BidiFlow<I1, O1, I2, O2, Mat> withAttributes(Attributes attr) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.traversalBuilder().setAttributes(attr), this.shape());
    }

    public BidiFlow<I1, O1, I2, O2, Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async() {
        return (BidiFlow)Graph.async$(this);
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async(String dispatcher) {
        return (BidiFlow)Graph.async$(this, dispatcher);
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async(String dispatcher, int inputBufferSize) {
        return (BidiFlow)Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Attributes getAttributes() {
        return this.traversalBuilder().attributes();
    }

    public BidiFlow(TraversalBuilder traversalBuilder, BidiShape<I1, O1, I2, O2> shape) {
        this.traversalBuilder = traversalBuilder;
        this.shape = shape;
        Graph.$init$(this);
    }
}

