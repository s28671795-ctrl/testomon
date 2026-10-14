/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ListBuffer$;
import scala.collection.mutable.MutationTracker;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\t\u0005h\u0001B\u001b7\u0001uBQA\u001b\u0001\u0005\u0002-Da\u0001\u001c\u0001!B\u0013i\u0007b\u0002;\u0001\u0001\u0004%I!\u001e\u0005\bm\u0002\u0001\r\u0011\"\u0003x\u0011\u0019i\b\u0001)Q\u00057\"9a\u0010\u0001a\u0001\n\u0013y\b\"CA\u0004\u0001\u0001\u0007I\u0011BA\u0005\u0011!\ti\u0001\u0001Q!\n\u0005\u0005\u0001\u0002CA\b\u0001\u0001\u0006K!!\u0005\t\u000f\u0005]\u0001\u0001)Q\u0005[\u00161\u0011\u0011\u0004\u0001\u0005\u00037Aq!!\n\u0001\t\u0003\t9\u0003C\u0004\u00020\u0001!\t%!\r\t\u000f\u0005e\u0002\u0001\"\u0001\u0002<!9\u0011q\u0014\u0001\u0005\u0002\u0005\u0005\u0006bBAR\u0001\u0011\u0005\u0013\u0011\u0015\u0005\b\u0003K\u0003A\u0011IAT\u0011\u001d\tI\u000b\u0001C\u0005\u0003WCq!!,\u0001\t\u0013\tY\u000b\u0003\u0004\u00020\u0002!\t%\u001e\u0005\b\u0003c\u0003A\u0011AAZ\u0011\u001d\t)\f\u0001C\u0001\u0003oCq!!0\u0001\t\u0003\tY\u000bC\u0004\u0002@\u0002!)!!1\t\u000f\u0005%\u0007\u0001\"\u0003\u0002L\"9\u0011Q\u001b\u0001\u0005F\u0005]\u0007bBAn\u0001\u0011\u0005\u0013Q\u001c\u0005\b\u0003C\u0004A\u0011BAr\u0011\u001d\tI\u000f\u0001C\u0005\u0003WDq!!=\u0001\t\u0013\t\u0019\u0010C\u0004\u0002z\u0002!\t!a?\t\u000f\t\r\u0001\u0001\"\u0001\u0003\u0006!9!1\u0002\u0001\u0005\u0002\t5\u0001b\u0002B\t\u0001\u0011%!1\u0003\u0005\b\u0005;\u0001A\u0011\u0001B\u0010\u0011\u001d\u00119\u0003\u0001C\u0001\u0005SAqAa\n\u0001\t\u0003\u0011i\u0003C\u0004\u00036\u0001!IAa\u000e\t\u000f\t}\u0002\u0001\"\u0001\u0003B!9!Q\n\u0001\u0005\u0002\t=\u0003b\u0002B+\u0001\u0011\u0005!q\u000b\u0005\b\u0005;\u0002A\u0011\u0001B0\u0011\u001d\u0011i\u0007\u0001C!\u0005_BqA!\u001d\u0001\t\u0003\u0012\u0019\b\u0003\u0005\u0003|\u0001\u0001K\u0011\u000bB?\u000f\u001d\u0011yI\u000eE\u0001\u0005#3a!\u000e\u001c\t\u0002\tM\u0005B\u000260\t\u0003\u0011\t\u000bC\u0004\u0003d=\"\tAa)\t\u000f\tMv\u0006\"\u0001\u00036\"9!QY\u0018\u0005\u0002\t\u001d\u0007\"\u0003Bi_\u0005\u0005I\u0011\u0002Bj\u0005)a\u0015n\u001d;Ck\u001a4WM\u001d\u0006\u0003oa\nq!\\;uC\ndWM\u0003\u0002:u\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003m\nQa]2bY\u0006\u001c\u0001!\u0006\u0002?\u000bN9\u0001aP(U1\u0006$\u0007c\u0001!B\u00076\ta'\u0003\u0002Cm\tq\u0011IY:ue\u0006\u001cGOQ;gM\u0016\u0014\bC\u0001#F\u0019\u0001!QA\u0012\u0001C\u0002\u001d\u0013\u0011!Q\t\u0003\u00112\u0003\"!\u0013&\u000e\u0003iJ!a\u0013\u001e\u0003\u000f9{G\u000f[5oOB\u0011\u0011*T\u0005\u0003\u001dj\u00121!\u00118z!\u0015\u0001\u0005k\u0011*T\u0013\t\tfG\u0001\u0004TKF|\u0005o\u001d\t\u0003\u0001\u0002\u00012\u0001\u0011\u0001D!\u0015)fk\u0011*T\u001b\u0005A\u0014BA,9\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9PaN\u0004B\u0001Q-D7&\u0011!L\u000e\u0002\u0010%\u0016,8/\u00192mK\n+\u0018\u000e\u001c3feB\u0019AlX\"\u000e\u0003uS!A\u0018\u001d\u0002\u0013%lW.\u001e;bE2,\u0017B\u00011^\u0005\u0011a\u0015n\u001d;\u0011\tU\u00137IU\u0005\u0003Gb\u0012q#\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f#fM\u0006,H\u000e^:\u0011\u0005\u0015DW\"\u00014\u000b\u0005\u001dD\u0014aB4f]\u0016\u0014\u0018nY\u0005\u0003S\u001a\u00141\u0003R3gCVdGoU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#A*\u0002\u001b5,H/\u0019;j_:\u001cu.\u001e8u!\tIe.\u0003\u0002pu\t\u0019\u0011J\u001c;)\u0005\t\t\bCA%s\u0013\t\u0019(HA\u0005ue\u0006t7/[3oi\u0006)a-\u001b:tiV\t1,A\u0005gSJ\u001cHo\u0018\u0013fcR\u0011\u0001p\u001f\t\u0003\u0013fL!A\u001f\u001e\u0003\tUs\u0017\u000e\u001e\u0005\by\u0012\t\t\u00111\u0001\\\u0003\rAH%M\u0001\u0007M&\u00148\u000f\u001e\u0011\u0002\u000b1\f7\u000f\u001e\u0019\u0016\u0005\u0005\u0005\u0001\u0003\u0002/\u0002\u0004\rK1!!\u0002^\u00051!3m\u001c7p]\u0012\u001aw\u000e\\8o\u0003%a\u0017m\u001d;1?\u0012*\u0017\u000fF\u0002y\u0003\u0017A\u0001\u0002`\u0004\u0002\u0002\u0003\u0007\u0011\u0011A\u0001\u0007Y\u0006\u001cH\u000f\r\u0011\u0002\u000f\u0005d\u0017.Y:fIB\u0019\u0011*a\u0005\n\u0007\u0005U!HA\u0004C_>dW-\u00198\u0002\u00071,gNA\u0006Qe\u0016$WmY3tg>\u0014X\u0003BA\u000f\u0003C\u0001R\u0001XA\u0002\u0003?\u00012\u0001RA\u0011\t\u0019\t\u0019c\u0003b\u0001\u000f\n\u0011\u0011\tM\u0001\tSR,'/\u0019;peV\u0011\u0011\u0011\u0006\t\u0005+\u0006-2)C\u0002\u0002.a\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ssV\u0011\u00111\u0007\t\u0005+\u0006U\"+C\u0002\u00028a\u0012!bU3r\r\u0006\u001cGo\u001c:z\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0019\u0015Q\b\u0005\u0007\u0003\u007fq\u0001\u0019A7\u0002\u0003%DSADA\"\u00033\u0002R!SA#\u0003\u0013J1!a\u0012;\u0005\u0019!\bN]8xgB!\u00111JA+\u001b\t\tiE\u0003\u0003\u0002P\u0005E\u0013\u0001\u00027b]\u001eT!!a\u0015\u0002\t)\fg/Y\u0005\u0005\u0003/\niEA\rJ]\u0012,\u0007pT;u\u001f\u001a\u0014u.\u001e8eg\u0016C8-\u001a9uS>t\u0017g\u0002\u0010\u0002\\\u0005E\u0014Q\u0014\t\u0005\u0003;\nYG\u0004\u0003\u0002`\u0005\u001d\u0004cAA1u5\u0011\u00111\r\u0006\u0004\u0003Kb\u0014A\u0002\u001fs_>$h(C\u0002\u0002ji\na\u0001\u0015:fI\u00164\u0017\u0002BA7\u0003_\u0012aa\u0015;sS:<'bAA5uEJ1%a\u001d\u0002|\u0005M\u0015QP\u000b\u0005\u0003k\n9(\u0006\u0002\u0002\\\u00119\u0011\u0011\u0010\u001fC\u0002\u0005\r%!\u0001+\n\t\u0005u\u0014qP\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u000b\u0007\u0005\u0005%(\u0001\u0004uQJ|wo]\t\u0004\u0011\u0006\u0015\u0005\u0003BAD\u0003\u001bs1!SAE\u0013\r\tYIO\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\ty)!%\u0003\u0013QC'o\\<bE2,'bAAFuEJ1%!&\u0002\u0018\u0006e\u0015\u0011\u0011\b\u0004\u0013\u0006]\u0015bAAAuE*!%\u0013\u001e\u0002\u001c\n)1oY1mCF\u001aa%!\u0013\u0002\r1,gn\u001a;i+\u0005i\u0017!C6o_^t7+\u001b>f\u0003\u001dI7/R7qif,\"!!\u0005\u0002\u0013\r|\u0007/_#mK6\u001cH#\u0001=\u0002\u001f\u0015t7/\u001e:f+:\fG.[1tK\u0012\fa\u0001^8MSN$\u0018A\u0002:fgVdG\u000fF\u0001\\\u00035\u0001(/\u001a9f]\u0012$v\u000eT5tiR\u00191,!/\t\r\u0005mf\u00031\u0001\\\u0003\tA8/A\u0003dY\u0016\f'/\u0001\u0004bI\u0012|e.\u001a\u000b\u0005\u0003\u0007\f)-D\u0001\u0001\u0011\u0019\t9\r\u0007a\u0001\u0007\u0006!Q\r\\3n\u0003%1'/Z:i\rJ|W\u000e\u0006\u0003\u0002D\u00065\u0007bBA^3\u0001\u0007\u0011q\u001a\t\u0005+\u0006E7)C\u0002\u0002Tb\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\fa!\u00193e\u00032dG\u0003BAb\u00033Dq!a/\u001b\u0001\u0004\ty-A\u0006tk\n$(/Y2u\u001f:,G\u0003BAb\u0003?Da!a2\u001c\u0001\u0004\u0019\u0015A\u0004:fIV\u001cW\rT3oORD')\u001f\u000b\u0004q\u0006\u0015\bBBAt9\u0001\u0007Q.A\u0002ok6\fa\u0001\\8dCR,G\u0003BAw\u0003_\u0004B!a1\f\u0007\"1\u0011qH\u000fA\u00025\fqaZ3u\u001d\u0016DH\u000fF\u0002\\\u0003kDq!a>\u001f\u0001\u0004\ti/A\u0001q\u0003\u0019)\b\u000fZ1uKR)\u00010!@\u0003\u0002!1\u0011q`\u0010A\u00025\f1!\u001b3y\u0011\u0019\t9m\ba\u0001\u0007\u00061\u0011N\\:feR$R\u0001\u001fB\u0004\u0005\u0013Aa!a@!\u0001\u0004i\u0007BBAdA\u0001\u00071)A\u0004qe\u0016\u0004XM\u001c3\u0015\t\u0005\r'q\u0002\u0005\u0007\u0003\u000f\f\u0003\u0019A\"\u0002\u0017%t7/\u001a:u\u0003\u001a$XM\u001d\u000b\u0006q\nU!\u0011\u0004\u0005\b\u0005/\u0011\u0003\u0019AAw\u0003\u0011\u0001(/\u001a<\t\r\tm!\u00051\u0001T\u0003\u00151'/Z:i\u0003%Ign]3si\u0006cG\u000eF\u0003y\u0005C\u0011\u0019\u0003\u0003\u0004\u0002\u0000\u000e\u0002\r!\u001c\u0005\b\u0005K\u0019\u0003\u0019AAh\u0003\u0015)G.Z7t\u0003\u0019\u0011X-\\8wKR\u00191Ia\u000b\t\r\u0005}H\u00051\u0001n)\u0015A(q\u0006B\u0019\u0011\u0019\ty0\na\u0001[\"1!1G\u0013A\u00025\fQaY8v]R\f1B]3n_Z,\u0017I\u001a;feR)\u0001P!\u000f\u0003<!9!q\u0003\u0014A\u0002\u00055\bB\u0002B\u001fM\u0001\u0007Q.A\u0001o\u0003)i\u0017\r]%o!2\f7-\u001a\u000b\u0005\u0003\u0007\u0014\u0019\u0005C\u0004\u0003F\u001d\u0002\rAa\u0012\u0002\u0003\u0019\u0004R!\u0013B%\u0007\u000eK1Aa\u0013;\u0005%1UO\\2uS>t\u0017'\u0001\bgY\u0006$X*\u00199J]Bc\u0017mY3\u0015\t\u0005\r'\u0011\u000b\u0005\b\u0005\u000bB\u0003\u0019\u0001B*!\u0019I%\u0011J\"\u0002P\u0006ia-\u001b7uKJLe\u000e\u00157bG\u0016$B!a1\u0003Z!9\u0011q_\u0015A\u0002\tm\u0003CB%\u0003J\r\u000b\t\"\u0001\u0007qCR\u001c\u0007.\u00138QY\u0006\u001cW\r\u0006\u0005\u0002D\n\u0005$Q\rB5\u0011\u0019\u0011\u0019G\u000ba\u0001[\u0006!aM]8n\u0011\u001d\u00119G\u000ba\u0001\u0003\u001f\fQ\u0001]1uG\"DaAa\u001b+\u0001\u0004i\u0017\u0001\u0003:fa2\f7-\u001a3\u0002\t1\f7\u000f^\u000b\u0002\u0007\u0006QA.Y:u\u001fB$\u0018n\u001c8\u0016\u0005\tU\u0004\u0003B%\u0003x\rK1A!\u001f;\u0005\u0019y\u0005\u000f^5p]\u0006a1\u000f\u001e:j]\u001e\u0004&/\u001a4jqV\u0011!q\u0010\t\u0005\u0003\u0017\u0012\t)\u0003\u0003\u0002n\u00055\u0003f\u0002\u0001\u0003\u0006\n-%Q\u0012\t\u0004\u0013\n\u001d\u0015b\u0001BEu\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\t\u0017#\t|d3|;\u0010\u0006QA*[:u\u0005V4g-\u001a:\u0011\u0005\u0001{3#B\u0018\u0003\u0016\nm\u0005cA%\u0003\u0018&\u0019!\u0011\u0014\u001e\u0003\r\u0005s\u0017PU3g!\u0011)&Q\u0014*\n\u0007\t}\u0005HA\rTiJL7\r^(qi&l\u0017N_3e'\u0016\fh)Y2u_JLHC\u0001BI+\u0011\u0011)Ka+\u0015\t\t\u001d&Q\u0016\t\u0005\u0001\u0002\u0011I\u000bE\u0002E\u0005W#QAR\u0019C\u0002\u001dCqAa,2\u0001\u0004\u0011\t,\u0001\u0003d_2d\u0007#B+\u0002R\n%\u0016A\u00038fo\n+\u0018\u000e\u001c3feV!!q\u0017Ba+\t\u0011I\fE\u0004A\u0005w\u0013yLa1\n\u0007\tufGA\u0004Ck&dG-\u001a:\u0011\u0007\u0011\u0013\t\rB\u0003Ge\t\u0007q\t\u0005\u0003A\u0001\t}\u0016!B3naRLX\u0003\u0002Be\u0005\u001f,\"Aa3\u0011\t\u0001\u0003!Q\u001a\t\u0004\t\n=G!\u0002$4\u0005\u00049\u0015\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001Bk!\u0011\tYEa6\n\t\te\u0017Q\n\u0002\u0007\u001f\nTWm\u0019;)\u000f=\u0012)Ia#\u0003^z\t1\u0001K\u0004/\u0005\u000b\u0013YI!8")
public class ListBuffer<A>
extends AbstractBuffer<A>
implements StrictOptimizedSeqOps<A, ListBuffer, ListBuffer<A>>,
ReusableBuilder<A, List<A>>,
DefaultSerializable {
    private static final long serialVersionUID = -8428291952499836345L;
    private transient int mutationCount = 0;
    private List<A> first = Nil$.MODULE$;
    private $colon$colon<A> last0 = null;
    private boolean aliased = false;
    private int len = 0;

    public static <A> Builder<A, ListBuffer<A>> newBuilder() {
        return ListBuffer$.MODULE$.newBuilder();
    }

    public static <A> ListBuffer<A> from(IterableOnce<A> coll) {
        return ListBuffer$.MODULE$.from((IterableOnce)coll);
    }

    public static SeqOps tabulate(int n, Function1 f) {
        Builder tabulate_b = ListBuffer$.MODULE$.newBuilder();
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem) {
        Builder fill_b = ListBuffer$.MODULE$.newBuilder();
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        ListBuffer$ tabulate_this = ListBuffer$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var12_12;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var15_15;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var18_18;
                    Builder tabulate_b4 = tabulate_this.newBuilder();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var21_21;
                        Builder tabulate_b5 = tabulate_this.newBuilder();
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var21_21 = null;
                        }
                        Object var19_19 = null;
                        var21_21 = null;
                        SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var18_18 = null;
                    }
                    Object var16_16 = null;
                    var18_18 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        ListBuffer$ tabulate_this = ListBuffer$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var14_14;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var17_17;
                    Builder tabulate_b4 = tabulate_this.newBuilder();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        ListBuffer$ tabulate_this = ListBuffer$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var13_13;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        ListBuffer$ tabulate_this = ListBuffer$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var9_9;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        ListBuffer$ fill_this = ListBuffer$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var12_12;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var15_15;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var18_18;
                    Builder fill_b4 = fill_this.newBuilder();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var21_21;
                        Builder fill_b5 = fill_this.newBuilder();
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var21_21 = null;
                        }
                        Object var19_19 = null;
                        var21_21 = null;
                        SeqOps fill_$plus$eq_elem = (SeqOps)fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var18_18 = null;
                    }
                    Object var16_16 = null;
                    var18_18 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        ListBuffer$ fill_this = ListBuffer$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var14_14;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var17_17;
                    Builder fill_b4 = fill_this.newBuilder();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        ListBuffer$ fill_this = ListBuffer$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var13_13;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        ListBuffer$ fill_this = ListBuffer$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var9_9;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(ListBuffer$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(ListBuffer$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        ListBuffer$ unfold_this = ListBuffer$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        ListBuffer$ iterate_this = ListBuffer$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<A, NewTo> mapResult(Function1<List<A>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object prepended(Object elem) {
        return StrictOptimizedSeqOps.prepended$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return StrictOptimizedSeqOps.appendedAll$(this, suffix);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public Tuple2<ListBuffer<A>, ListBuffer<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ListBuffer<A>, ListBuffer<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ListBuffer<A1>, ListBuffer<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ListBuffer<A1>, ListBuffer<A2>, ListBuffer<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<ListBuffer<A1>, ListBuffer<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    private List<A> first() {
        return this.first;
    }

    private void first_$eq(List<A> x$1) {
        this.first = x$1;
    }

    private $colon$colon<A> last0() {
        return this.last0;
    }

    private void last0_$eq($colon$colon<A> x$1) {
        this.last0 = x$1;
    }

    @Override
    public Iterator<A> iterator() {
        return new MutationTracker.CheckedIterator<A>(this.first().iterator(), () -> $this.mutationCount);
    }

    @Override
    public SeqFactory<ListBuffer> iterableFactory() {
        return ListBuffer$.MODULE$;
    }

    @Override
    public A apply(int i) throws IndexOutOfBoundsException {
        List<A> list = this.first();
        if (list == null) {
            throw null;
        }
        return (A)LinearSeqOps.apply$(list, i);
    }

    @Override
    public int length() {
        return this.len;
    }

    @Override
    public int knownSize() {
        return this.len;
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    private void copyElems() {
        ListBuffer<A> buf = new ListBuffer<A>().scala$collection$mutable$ListBuffer$$freshFrom(this);
        this.first_$eq(super.first());
        this.last0_$eq(super.last0());
        this.aliased = false;
    }

    private void ensureUnaliased() {
        ++this.mutationCount;
        if (this.aliased) {
            this.copyElems();
            return;
        }
    }

    @Override
    public List<A> toList() {
        this.aliased = this.nonEmpty();
        Statics.releaseFence();
        return this.first();
    }

    @Override
    public List<A> result() {
        return this.toList();
    }

    public List<A> prependToList(List<A> xs) {
        if (this.isEmpty()) {
            return xs;
        }
        this.ensureUnaliased();
        this.last0().next_$eq(xs);
        return this.toList();
    }

    @Override
    public void clear() {
        ++this.mutationCount;
        this.first_$eq(Nil$.MODULE$);
        this.len = 0;
        this.last0_$eq(null);
        this.aliased = false;
    }

    @Override
    public final ListBuffer<A> addOne(A elem) {
        this.ensureUnaliased();
        $colon$colon<Nothing$> last1 = new $colon$colon<Nothing$>((Nothing$)elem, Nil$.MODULE$);
        if (this.len == 0) {
            this.first_$eq(last1);
        } else {
            this.last0().next_$eq(last1);
        }
        this.last0_$eq(last1);
        ++this.len;
        return this;
    }

    public ListBuffer<A> scala$collection$mutable$ListBuffer$$freshFrom(IterableOnce<A> xs) {
        Iterator<A> it = xs.iterator();
        if (it.hasNext()) {
            int len = 1;
            $colon$colon<Nothing$> last0 = new $colon$colon<Nothing$>((Nothing$)it.next(), Nil$.MODULE$);
            this.first_$eq(last0);
            while (it.hasNext()) {
                $colon$colon<Nothing$> last1 = new $colon$colon<Nothing$>((Nothing$)it.next(), Nil$.MODULE$);
                last0.next_$eq(last1);
                last0 = last1;
                ++len;
            }
            this.len = len;
            this.last0_$eq(last0);
        }
        return this;
    }

    @Override
    public final ListBuffer<A> addAll(IterableOnce<A> xs) {
        Iterator<A> it = xs.iterator();
        if (it.hasNext()) {
            ListBuffer<A> fresh = new ListBuffer<A>().scala$collection$mutable$ListBuffer$$freshFrom(it);
            this.ensureUnaliased();
            if (this.len == 0) {
                this.first_$eq(super.first());
            } else {
                this.last0().next_$eq(super.first());
            }
            this.last0_$eq(super.last0());
            this.len += fresh.length();
        }
        return this;
    }

    @Override
    public ListBuffer<A> subtractOne(A elem) {
        this.ensureUnaliased();
        if (!this.isEmpty()) {
            if (BoxesRunTime.equals(this.first().head(), elem)) {
                this.first_$eq((List)this.first().tail());
                this.reduceLengthBy(1);
            } else {
                List cursor = this.first();
                while (!((List)cursor.tail()).isEmpty() && !BoxesRunTime.equals(((IterableOps)cursor.tail()).head(), elem)) {
                    cursor = (List)cursor.tail();
                }
                if (!((List)cursor.tail()).isEmpty()) {
                    $colon$colon z = ($colon$colon)cursor;
                    List list = z.next();
                    $colon$colon<A> $colon$colon = this.last0();
                    if (!(list != null ? !((Object)list).equals($colon$colon) : $colon$colon != null)) {
                        this.last0_$eq(z);
                    }
                    z.next_$eq((List)((IterableOps)cursor.tail()).tail());
                    this.reduceLengthBy(1);
                }
            }
        }
        return this;
    }

    private void reduceLengthBy(int num) {
        this.len -= num;
        if (this.len <= 0) {
            this.last0_$eq(null);
            return;
        }
    }

    private $colon$colon<A> locate(int i) {
        if (i == 0) {
            return null;
        }
        if (i == this.len) {
            return this.last0();
        }
        List p = this.first();
        for (int j = i - 1; j > 0; --j) {
            p = (List)p.tail();
        }
        return ($colon$colon)p;
    }

    private List<A> getNext($colon$colon<A> p) {
        if (p == null) {
            return this.first();
        }
        return p.next();
    }

    @Override
    public void update(int idx, A elem) {
        this.ensureUnaliased();
        if (idx < 0 || idx >= this.len) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.len - 1).append(")").toString());
        }
        if (idx == 0) {
            $colon$colon<A> newElem = new $colon$colon<A>(elem, (List)this.first().tail());
            if (this.last0() == this.first()) {
                this.last0_$eq(newElem);
            }
            this.first_$eq(newElem);
            return;
        }
        $colon$colon<A> p = this.locate(idx);
        if (p == null) {
            throw null;
        }
        $colon$colon<A> newElem = new $colon$colon<A>(elem, (List)p.next().tail());
        if (this.last0() == p.next()) {
            this.last0_$eq(newElem);
        }
        p.next_$eq(newElem);
    }

    @Override
    public void insert(int idx, A elem) {
        this.ensureUnaliased();
        if (idx < 0 || idx > this.len) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.len - 1).append(")").toString());
        }
        if (idx == this.len) {
            this.addOne((Object)elem);
            return;
        }
        $colon$colon<A> p = this.locate(idx);
        List<A> list = this.getNext(p);
        if (list == null) {
            throw null;
        }
        List<A> $colon$colon_this = list;
        Object var5_4 = null;
        $colon$colon<A> nx = new $colon$colon<A>(elem, $colon$colon_this);
        if (p == null) {
            this.first_$eq(nx);
        } else {
            p.next_$eq(nx);
        }
        ++this.len;
    }

    @Override
    public ListBuffer<A> prepend(A elem) {
        this.insert(0, elem);
        return this;
    }

    private void insertAfter($colon$colon<A> prev, ListBuffer<A> fresh) {
        if (!fresh.isEmpty()) {
            List<A> follow = this.getNext(prev);
            if (prev == null) {
                this.first_$eq(super.first());
            } else {
                prev.next_$eq(super.first());
            }
            super.last0().next_$eq(follow);
            this.len += fresh.length();
            return;
        }
    }

    @Override
    public void insertAll(int idx, IterableOnce<A> elems) {
        if (idx < 0 || idx > this.len) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.len - 1).append(")").toString());
        }
        Iterator<A> it = elems.iterator();
        if (it.hasNext()) {
            if (idx == this.len) {
                this.addAll((IterableOnce)it);
                return;
            }
            ListBuffer<A> fresh = new ListBuffer<A>().scala$collection$mutable$ListBuffer$$freshFrom(it);
            this.ensureUnaliased();
            this.insertAfter(this.locate(idx), fresh);
            return;
        }
    }

    @Override
    public A remove(int idx) {
        this.ensureUnaliased();
        if (idx < 0 || idx >= this.len) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.len - 1).append(")").toString());
        }
        $colon$colon<A> p = this.locate(idx);
        List<A> nx = this.getNext(p);
        if (p == null) {
            this.first_$eq((List)nx.tail());
            if (this.first().isEmpty()) {
                this.last0_$eq(null);
            }
        } else {
            if (this.last0() == nx) {
                this.last0_$eq(p);
            }
            p.next_$eq((List)nx.tail());
        }
        --this.len;
        return nx.head();
    }

    @Override
    public void remove(int idx, int count) {
        if (count > 0) {
            this.ensureUnaliased();
            if (idx < 0 || idx + count > this.len) {
                throw new IndexOutOfBoundsException(new StringBuilder(35).append(idx).append(" to ").append(idx + count).append(" is out of bounds (min 0, max ").append(this.len - 1).append(")").toString());
            }
            this.removeAfter(this.locate(idx), count);
            return;
        }
        if (count < 0) {
            throw new IllegalArgumentException(new StringBuilder(38).append("removing negative number of elements: ").append(count).toString());
        }
    }

    private void removeAfter($colon$colon<A> prev, int n) {
        List nx = this.ahead$1(this.getNext(prev), n);
        if (prev == null) {
            this.first_$eq(nx);
        } else {
            prev.next_$eq(nx);
        }
        if (nx.isEmpty()) {
            this.last0_$eq(prev);
        }
        this.len -= n;
    }

    public ListBuffer<A> mapInPlace(Function1<A, A> f) {
        ++this.mutationCount;
        ListBuffer buf = new ListBuffer();
        this.foreach((Function1<Object, ListBuffer> & Serializable)elem -> {
            Object $plus$eq_elem = f.apply(elem);
            return (ListBuffer)buf.addOne((Object)$plus$eq_elem);
        });
        this.first_$eq(super.first());
        this.last0_$eq(super.last0());
        this.aliased = false;
        return this;
    }

    public ListBuffer<A> flatMapInPlace(Function1<A, IterableOnce<A>> f) {
        ++this.mutationCount;
        List src = this.first();
        $colon$colon<Nothing$> dst = null;
        this.last0_$eq(null);
        this.len = 0;
        while (!src.isEmpty()) {
            Iterator<A> it = f.apply(src.head()).iterator();
            while (it.hasNext()) {
                $colon$colon<Nothing$> v = new $colon$colon<Nothing$>((Nothing$)it.next(), Nil$.MODULE$);
                if (dst == null) {
                    dst = v;
                } else {
                    this.last0().next_$eq(v);
                }
                this.last0_$eq(v);
                ++this.len;
            }
            src = (List)src.tail();
        }
        this.first_$eq(dst == null ? Nil$.MODULE$ : dst);
        this.aliased = false;
        return this;
    }

    public ListBuffer<A> filterInPlace(Function1<A, Object> p) {
        this.ensureUnaliased();
        $colon$colon prev = null;
        List cur = this.first();
        while (!cur.isEmpty()) {
            List follow = (List)cur.tail();
            if (!BoxesRunTime.unboxToBoolean(p.apply(cur.head()))) {
                if (prev == null) {
                    this.first_$eq(follow);
                } else {
                    prev.next_$eq(follow);
                }
                --this.len;
            } else {
                prev = ($colon$colon)cur;
            }
            cur = follow;
        }
        this.last0_$eq(prev);
        return this;
    }

    @Override
    public ListBuffer<A> patchInPlace(int from, IterableOnce<A> patch, int replaced) {
        boolean nonEmptyReplace;
        int _len = this.len;
        int max_y = 0;
        int _from = Math.max(from, max_y);
        int max_y2 = 0;
        int _replaced = Math.max(replaced, max_y2);
        Iterator<A> it = patch.iterator();
        boolean nonEmptyPatch = it.hasNext();
        boolean bl = nonEmptyReplace = _from < _len && _replaced > 0;
        if (nonEmptyPatch || nonEmptyReplace) {
            ListBuffer<A> fresh = new ListBuffer<A>().scala$collection$mutable$ListBuffer$$freshFrom(it);
            this.ensureUnaliased();
            int i = Math.min(_from, _len);
            int n = Math.min(_replaced, _len);
            $colon$colon<A> p = this.locate(i);
            int min_y = _len - i;
            this.removeAfter(p, Math.min(n, min_y));
            this.insertAfter(p, fresh);
        }
        return this;
    }

    @Override
    public A last() {
        if (this.last0() == null) {
            throw new NoSuchElementException("last of empty ListBuffer");
        }
        return this.last0().head();
    }

    @Override
    public Option<A> lastOption() {
        if (this.last0() == null) {
            return None$.MODULE$;
        }
        return new Some<A>(this.last0().head());
    }

    @Override
    public String stringPrefix() {
        return "ListBuffer";
    }

    private final List ahead$1(List p, int n) {
        while (n != 0) {
            --n;
            p = (List)p.tail();
        }
        return p;
    }
}

