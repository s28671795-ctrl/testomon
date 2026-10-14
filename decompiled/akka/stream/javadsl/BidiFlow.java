/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.stream.Attributes;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.impl.TraversalBuilder;
import akka.stream.javadsl.BidiFlow$;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.package$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tmv!B\u000f\u001f\u0011\u0003)c!B\u0014\u001f\u0011\u0003A\u0003\"B\u0018\u0002\t\u0003\u0001\u0004BB\u0019\u0002A\u0003%!\u0007C\u0004\u0002P\u0006!\t!!5\t\u000f\u0005\r\u0018\u0001\"\u0001\u0002f\"9!qA\u0001\u0005\u0002\t%\u0001b\u0002B$\u0003\u0011\u0005!\u0011\n\u0005\b\u0005g\nA\u0011\u0001B;\u0011\u001d\u00119*\u0001C\u0001\u000533Aa\n\u0010\u0003g!A1K\u0003B\u0001B\u0003%A\u000bC\u00030\u0015\u0011\u0005\u0011\fC\u0003]\u0015\u0011\u0005S\fC\u0003e\u0015\u0011\u0005S\rC\u0003g\u0015\u0011\u0005q\rC\u0003i\u0015\u0011\u0005\u0011\u000eC\u0003i\u0015\u0011\u0005\u0001\u0010C\u0004\u0002\")!\t!a\t\t\u000f\u0005\u0005\"\u0002\"\u0001\u00028!9\u0011Q\n\u0006\u0005\u0002\u0005=\u0003bBA*\u0015\u0011\u0005\u0011Q\u000b\u0005\b\u0003SRA\u0011IA6\u0011\u001d\t9H\u0003C!\u0003sBq!! \u000b\t\u0003\ny\bC\u0004\u0002\u001c*!\t%!(\t\u000f\u0005m%\u0002\"\u0011\u0002 \"9\u00111\u0014\u0006\u0005B\u0005\u0015\u0006bBAZ\u0015\u0011\u0005\u0013QW\u0001\t\u0005&$\u0017N\u00127po*\u0011q\u0004I\u0001\bU\u00064\u0018\rZ:m\u0015\t\t#%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002G\u0005!\u0011m[6b\u0007\u0001\u0001\"AJ\u0001\u000e\u0003y\u0011\u0001BQ5eS\u001acwn^\n\u0003\u0003%\u0002\"AK\u0017\u000e\u0003-R\u0011\u0001L\u0001\u0006g\u000e\fG.Y\u0005\u0003]-\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001&\u0003%y\u0016\u000eZ3oi&$\u0018\u0010\u0005\u0007'\u0015\u0005]\u0016qWA\\\u0003o\u000b9-\u0006\u00045}![e*U\n\u0004\u0015%*\u0004\u0003\u0002\u001c8sAk\u0011\u0001I\u0005\u0003q\u0001\u0012Qa\u0012:ba\"\u0004bA\u000e\u001e=\u000f*k\u0015BA\u001e!\u0005%\u0011\u0015\u000eZ5TQ\u0006\u0004X\r\u0005\u0002>}1\u0001A!B \u000b\u0005\u0004\u0001%AA%2#\t\tE\t\u0005\u0002+\u0005&\u00111i\u000b\u0002\b\u001d>$\b.\u001b8h!\tQS)\u0003\u0002GW\t\u0019\u0011I\\=\u0011\u0005uBE!B%\u000b\u0005\u0004\u0001%AA(2!\ti4\nB\u0003M\u0015\t\u0007\u0001I\u0001\u0002JeA\u0011QH\u0014\u0003\u0006\u001f*\u0011\r\u0001\u0011\u0002\u0003\u001fJ\u0002\"!P)\u0005\u000bIS!\u0019\u0001!\u0003\u00075\u000bG/\u0001\u0005eK2,w-\u0019;f!\u001d)\u0006\fP$K\u001bBk\u0011A\u0016\u0006\u0003/\u0002\n\u0001b]2bY\u0006$7\u000f\\\u0005\u0003OY#\"AW.\u0011\u000f\u0019RAh\u0012&N!\")1\u000b\u0004a\u0001)\u0006\u0001BO]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u000b\u0002=B\u0011qLY\u0007\u0002A*\u0011\u0011\rI\u0001\u0005S6\u0004H.\u0003\u0002dA\n\u0001BK]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u0001\u0006g\"\f\u0007/Z\u000b\u0002s\u00059\u0011m]*dC2\fW#\u0001+\u0002\t\u0005$x\u000e]\u000b\u0005U6\u0004h\u000f\u0006\u0002leB9aE\u0003\u001fm_6\u0003\u0006CA\u001fn\t\u0015q\u0007C1\u0001A\u0005\ryu*\r\t\u0003{A$Q!\u001d\tC\u0002\u0001\u00131!S%3\u0011\u0015\u0019\b\u00031\u0001u\u0003\u0011\u0011\u0017\u000eZ5\u0011\u000f\u0019Rq\t\\8KkB\u0011QH\u001e\u0003\u0006oB\u0011\r\u0001\u0011\u0002\u0005\u001b\u0006$('F\u0004zyz\fY!!\u0001\u0015\u000bi\f)!!\u0004\u0011\u000f\u0019RAh_?N\u007fB\u0011Q\b \u0003\u0006]F\u0011\r\u0001\u0011\t\u0003{y$Q!]\tC\u0002\u0001\u00032!PA\u0001\t\u0019\t\u0019!\u0005b\u0001\u0001\n\tQ\n\u0003\u0004t#\u0001\u0007\u0011q\u0001\t\tM)950 &\u0002\nA\u0019Q(a\u0003\u0005\u000b]\f\"\u0019\u0001!\t\u000f\u0005=\u0011\u00031\u0001\u0002\u0012\u000591m\\7cS:,\u0007\u0003CA\n\u0003;\u0001\u0016\u0011B@\u000e\u0005\u0005U!\u0002BA\f\u00033\t\u0001BZ;oGRLwN\u001c\u0006\u0004\u00037\u0011\u0013\u0001\u00026ba&LA!a\b\u0002\u0016\tIa)\u001e8di&|gNM\u0001\u0005U>Lg.\u0006\u0003\u0002&\u0005UB\u0003BA\u0014\u0003[\u0001bAJA\u0015y5\u0003\u0016bAA\u0016=\t!a\t\\8x\u0011\u001d\tyC\u0005a\u0001\u0003c\tAA\u001a7poB9a%!\u000bH\u0015\u0006M\u0002cA\u001f\u00026\u0011)qO\u0005b\u0001\u0001V1\u0011\u0011HA$\u0003\u007f!b!a\u000f\u0002B\u0005%\u0003c\u0002\u0014\u0002*qj\u0015Q\b\t\u0004{\u0005}BABA\u0002'\t\u0007\u0001\tC\u0004\u00020M\u0001\r!a\u0011\u0011\u000f\u0019\nIc\u0012&\u0002FA\u0019Q(a\u0012\u0005\u000b]\u001c\"\u0019\u0001!\t\u000f\u0005=1\u00031\u0001\u0002LAI\u00111CA\u000f!\u0006\u0015\u0013QH\u0001\te\u00164XM]:fIV\u0011\u0011\u0011\u000b\t\bM)QU\nP$Q\u0003Qi\u0017\r]'bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKV!\u0011qKA/)\u0011\tI&a\u0018\u0011\u0011\u0019RAh\u0012&N\u00037\u00022!PA/\t\u00159XC1\u0001A\u0011\u001d\t\t'\u0006a\u0001\u0003G\n\u0011A\u001a\t\b\u0003'\t)\u0007UA.\u0013\u0011\t9'!\u0006\u0003\u0011\u0019+hn\u0019;j_:\fab^5uQ\u0006#HO]5ckR,7\u000fF\u0002[\u0003[Bq!a\u001c\u0017\u0001\u0004\t\t(\u0001\u0003biR\u0014\bc\u0001\u001c\u0002t%\u0019\u0011Q\u000f\u0011\u0003\u0015\u0005#HO]5ckR,7/A\u0007bI\u0012\fE\u000f\u001e:jEV$Xm\u001d\u000b\u00045\u0006m\u0004bBA8/\u0001\u0007\u0011\u0011O\u0001\u0006]\u0006lW\r\u001a\u000b\u00045\u0006\u0005\u0005bBAB1\u0001\u0007\u0011QQ\u0001\u0005]\u0006lW\r\u0005\u0003\u0002\b\u0006Ue\u0002BAE\u0003#\u00032!a#,\u001b\t\tiIC\u0002\u0002\u0010\u0012\na\u0001\u0010:p_Rt\u0014bAAJW\u00051\u0001K]3eK\u001aLA!a&\u0002\u001a\n11\u000b\u001e:j]\u001eT1!a%,\u0003\u0015\t7/\u001f8d+\u0005QFc\u0001.\u0002\"\"9\u00111\u0015\u000eA\u0002\u0005\u0015\u0015A\u00033jgB\fGo\u00195feR)!,a*\u0002*\"9\u00111U\u000eA\u0002\u0005\u0015\u0005bBAV7\u0001\u0007\u0011QV\u0001\u0010S:\u0004X\u000f\u001e\"vM\u001a,'oU5{KB\u0019!&a,\n\u0007\u0005E6FA\u0002J]R\fQbZ3u\u0003R$(/\u001b2vi\u0016\u001cXCAA9!\u0011\tI,a1\u000e\u0005\u0005m&\u0002BA_\u0003\u007f\u000bA\u0001\\1oO*\u0011\u0011\u0011Y\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002F\u0006m&AB(cU\u0016\u001cG\u000f\u0005\u0003\u0002J\u0006-W\"\u0001\u0012\n\u0007\u00055'EA\u0004O_R,6/\u001a3\u0002\u0011%$WM\u001c;jif,b!a5\u0002Z\u0006}WCAAk!11#\"a6\u0002X\u0006u\u0017Q\\Ad!\ri\u0014\u0011\u001c\u0003\u0007\u00037$!\u0019\u0001!\u0003\u0003\u0005\u00032!PAp\t\u0019\t\t\u000f\u0002b\u0001\u0001\n\t!)A\u0005ge>lwI]1qQVa\u0011q]Aw\u0003c\f)0!?\u0002~R!\u0011\u0011^A\u0000!11#\"a;\u0002p\u0006M\u0018q_A~!\ri\u0014Q\u001e\u0003\u0006\u007f\u0015\u0011\r\u0001\u0011\t\u0004{\u0005EH!B%\u0006\u0005\u0004\u0001\u0005cA\u001f\u0002v\u0012)A*\u0002b\u0001\u0001B\u0019Q(!?\u0005\u000b=+!\u0019\u0001!\u0011\u0007u\ni\u0010\u0002\u0004\u0002\u0004\u0015\u0011\r\u0001\u0011\u0005\b\u0005\u0003)\u0001\u0019\u0001B\u0002\u0003\u00059\u0007C\u0002\u001c8\u0005\u000b\tY\u0010\u0005\u00067u\u0005-\u0018q^Az\u0003o\fAB\u001a:p[\u001acwn^:NCR,\u0002Ca\u0003\u0003\u0012\tU!\u0011\u0004B\u000f\u0005c\u0011yD!\t\u0015\u0011\t5!1\u0005B\u001b\u0005\u0007\u0002BB\n\u0006\u0003\u0010\tM!q\u0003B\u000e\u0005?\u00012!\u0010B\t\t\u0015ydA1\u0001A!\ri$Q\u0003\u0003\u0006\u0013\u001a\u0011\r\u0001\u0011\t\u0004{\teA!\u0002'\u0007\u0005\u0004\u0001\u0005cA\u001f\u0003\u001e\u0011)qJ\u0002b\u0001\u0001B\u0019QH!\t\u0005\r\u0005\raA1\u0001A\u0011\u001d\u0011)C\u0002a\u0001\u0005O\tQA\u001a7poF\u0002bAN\u001c\u0003*\t=\u0002c\u0002\u001c\u0003,\t=!1C\u0005\u0004\u0005[\u0001#!\u0003$m_^\u001c\u0006.\u00199f!\ri$\u0011\u0007\u0003\u0007\u0005g1!\u0019\u0001!\u0003\u00055\u000b\u0004b\u0002B\u001c\r\u0001\u0007!\u0011H\u0001\u0006M2|wO\r\t\u0007m]\u0012YD!\u0010\u0011\u000fY\u0012YCa\u0006\u0003\u001cA\u0019QHa\u0010\u0005\r\t\u0005cA1\u0001A\u0005\ti%\u0007C\u0004\u0002\u0010\u0019\u0001\rA!\u0012\u0011\u0015\u0005M\u0011Q\u0004B\u0018\u0005{\u0011y\"A\u0005ge>lg\t\\8xgVq!1\nB)\u0005+\u0012IF!\u0018\u0003h\tEDC\u0002B'\u0005?\u0012I\u0007\u0005\u0007'\u0015\t=#1\u000bB,\u00057\n9\rE\u0002>\u0005#\"QaP\u0004C\u0002\u0001\u00032!\u0010B+\t\u0015IuA1\u0001A!\ri$\u0011\f\u0003\u0006\u0019\u001e\u0011\r\u0001\u0011\t\u0004{\tuC!B(\b\u0005\u0004\u0001\u0005b\u0002B\u0013\u000f\u0001\u0007!\u0011\r\t\u0007m]\u0012\u0019G!\u001a\u0011\u000fY\u0012YCa\u0014\u0003TA\u0019QHa\u001a\u0005\r\tMrA1\u0001A\u0011\u001d\u00119d\u0002a\u0001\u0005W\u0002bAN\u001c\u0003n\t=\u0004c\u0002\u001c\u0003,\t]#1\f\t\u0004{\tEDA\u0002B!\u000f\t\u0007\u0001)A\u0007ge>lg)\u001e8di&|gn]\u000b\u000b\u0005o\u0012iH!!\u0003\u0006\n%EC\u0002B=\u0005\u0017\u0013\t\n\u0005\u0007'\u0015\tm$q\u0010BB\u0005\u000f\u000b9\rE\u0002>\u0005{\"Qa\u0010\u0005C\u0002\u0001\u00032!\u0010BA\t\u0015I\u0005B1\u0001A!\ri$Q\u0011\u0003\u0006\u0019\"\u0011\r\u0001\u0011\t\u0004{\t%E!B(\t\u0005\u0004\u0001\u0005b\u0002BG\u0011\u0001\u0007!qR\u0001\u0004i>\u0004\b\u0003CA\n\u0003K\u0012YHa \t\u000f\tM\u0005\u00021\u0001\u0003\u0016\u00061!m\u001c;u_6\u0004\u0002\"a\u0005\u0002f\t\r%qQ\u0001\u0019E&$\u0017N]3di&|g.\u00197JI2,G+[7f_V$XC\u0002BN\u0005C\u00139\u000b\u0006\u0003\u0003\u001e\n-\u0006\u0003\u0004\u0014\u000b\u0005?\u0013yJ!*\u0003&\u0006\u001d\u0007cA\u001f\u0003\"\u00121!1U\u0005C\u0002\u0001\u0013\u0011!\u0013\t\u0004{\t\u001dFA\u0002BU\u0013\t\u0007\u0001IA\u0001P\u0011\u001d\u0011i+\u0003a\u0001\u0005_\u000bq\u0001^5nK>,H\u000f\u0005\u0003\u00032\n]VB\u0001BZ\u0015\u0011\u0011),a0\u0002\tQLW.Z\u0005\u0005\u0005s\u0013\u0019L\u0001\u0005EkJ\fG/[8o\u0001")
public final class BidiFlow<I1, O1, I2, O2, Mat>
implements Graph<BidiShape<I1, O1, I2, O2>, Mat> {
    private final akka.stream.scaladsl.BidiFlow<I1, O1, I2, O2, Mat> delegate;

    public static <I, O> BidiFlow<I, I, O, O, NotUsed> bidirectionalIdleTimeout(Duration timeout2) {
        return BidiFlow$.MODULE$.bidirectionalIdleTimeout(timeout2);
    }

    public static <I1, O1, I2, O2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFunctions(Function<I1, O1> top, Function<I2, O2> bottom) {
        return BidiFlow$.MODULE$.fromFunctions(top, bottom);
    }

    public static <I1, O1, I2, O2, M1, M2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFlows(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2) {
        return BidiFlow$.MODULE$.fromFlows(flow1, flow2);
    }

    public static <I1, O1, I2, O2, M1, M2, M> BidiFlow<I1, O1, I2, O2, M> fromFlowsMat(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2, Function2<M1, M2, M> combine) {
        return BidiFlow$.MODULE$.fromFlowsMat(flow1, flow2, combine);
    }

    public static <I1, O1, I2, O2, M> BidiFlow<I1, O1, I2, O2, M> fromGraph(Graph<BidiShape<I1, O1, I2, O2>, M> g2) {
        return BidiFlow$.MODULE$.fromGraph(g2);
    }

    public static <A, B> BidiFlow<A, A, B, B, NotUsed> identity() {
        return BidiFlow$.MODULE$.identity();
    }

    @Override
    public TraversalBuilder traversalBuilder() {
        return this.delegate.traversalBuilder();
    }

    @Override
    public BidiShape<I1, O1, I2, O2> shape() {
        return this.delegate.shape();
    }

    public akka.stream.scaladsl.BidiFlow<I1, O1, I2, O2, Mat> asScala() {
        return this.delegate;
    }

    public <OO1, II2, Mat2> BidiFlow<I1, OO1, II2, O2, Mat> atop(BidiFlow<O1, OO1, II2, I2, Mat2> bidi) {
        return new BidiFlow(this.delegate.atop(bidi.asScala()));
    }

    public <OO1, II2, Mat2, M> BidiFlow<I1, OO1, II2, O2, M> atop(BidiFlow<O1, OO1, II2, I2, Mat2> bidi, Function2<Mat, Mat2, M> combine) {
        return new BidiFlow(this.delegate.atopMat(bidi.asScala(), package$.MODULE$.combinerToScala(combine)));
    }

    public <Mat2> Flow<I1, O2, Mat> join(Flow<O1, I2, Mat2> flow) {
        return new Flow<I1, O2, Mat>(this.delegate.join(flow.asScala()));
    }

    public <Mat2, M> Flow<I1, O2, M> join(Flow<O1, I2, Mat2> flow, Function2<Mat, Mat2, M> combine) {
        return new Flow<I1, O2, M>(this.delegate.joinMat(flow.asScala(), package$.MODULE$.combinerToScala(combine)));
    }

    public BidiFlow<I2, O2, I1, O1, Mat> reversed() {
        return new BidiFlow<I2, O2, I1, O1, Mat>(this.delegate.reversed());
    }

    public <Mat2> BidiFlow<I1, O1, I2, O2, Mat2> mapMaterializedValue(Function<Mat, Mat2> f) {
        return new BidiFlow(this.delegate.mapMaterializedValue((Function1<Object, Object> & Serializable)(Mat param2) -> f.apply(param2)));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> withAttributes(Attributes attr) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.withAttributes(attr));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> addAttributes(Attributes attr) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.addAttributes(attr));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> named(String name) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.named(name));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async() {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.async());
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async(String dispatcher) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.async(dispatcher));
    }

    public BidiFlow<I1, O1, I2, O2, Mat> async(String dispatcher, int inputBufferSize) {
        return new BidiFlow<I1, O1, I2, O2, Mat>(this.delegate.async(dispatcher, inputBufferSize));
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate.getAttributes();
    }

    public BidiFlow(akka.stream.scaladsl.BidiFlow<I1, O1, I2, O2, Mat> delegate) {
        this.delegate = delegate;
        Graph.$init$(this);
    }
}

