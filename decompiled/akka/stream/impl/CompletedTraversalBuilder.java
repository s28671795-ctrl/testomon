/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.BuilderKey;
import akka.stream.impl.CompletedTraversalBuilder$;
import akka.stream.impl.CompositeTraversalBuilder;
import akka.stream.impl.CompositeTraversalBuilder$;
import akka.stream.impl.EnterIsland;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.PopAttributes$;
import akka.stream.impl.PushAttributes;
import akka.stream.impl.Transform;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuildStep;
import akka.stream.impl.TraversalBuilder;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Tuple2;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0005e!B\u00193\u0005ZB\u0004\u0002C*\u0001\u0005+\u0007I\u0011\u0001+\t\u0011a\u0003!\u0011#Q\u0001\nUC\u0001\"\u0017\u0001\u0003\u0016\u0004%\tA\u0017\u0005\t=\u0002\u0011\t\u0012)A\u00057\"Aq\f\u0001BK\u0002\u0013\u0005\u0001\r\u0003\u0005n\u0001\tE\t\u0015!\u0003b\u0011!q\u0007A!f\u0001\n\u0003y\u0007\u0002C:\u0001\u0005#\u0005\u000b\u0011\u00029\t\u0011Q\u0004!Q3A\u0005\u0002UD\u0001b \u0001\u0003\u0012\u0003\u0006IA\u001e\u0005\b\u0003\u0003\u0001A\u0011AA\u0002\u0011\u001d\t\t\u0002\u0001C!\u0003'Aa!!\u0011\u0001\t\u0003\"\u0006bBA\"\u0001\u0011\u0005\u0013Q\t\u0005\b\u0003#\u0002A\u0011IA*\u0011\u001d\tI\u0006\u0001C!\u00037Bq!a\u0019\u0001\t\u0003\n)\u0007C\u0004\u0002t\u0001!\t%!\u001e\t\r\u0005e\u0004\u0001\"\u0011[\u0011\u001d\tY\b\u0001C!\u0003{Bq!!!\u0001\t\u0003\n\u0019\tC\u0004\u0002\f\u0002!\t%!$\t\u000f\u0005E\u0005\u0001\"\u0011\u0002\u0014\"9\u0011\u0011\u0013\u0001\u0005B\u0005]\u0005\"CAN\u0001\u0005\u0005I\u0011AAO\u0011%\tI\u000bAI\u0001\n\u0003\tY\u000bC\u0005\u0002B\u0002\t\n\u0011\"\u0001\u0002D\"I\u0011q\u0019\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u001a\u0005\n\u0003\u001b\u0004\u0011\u0013!C\u0001\u0003\u001fD\u0011\"a5\u0001#\u0003%\t!!6\t\u0013\u0005e\u0007!!A\u0005B\u0005m\u0007\u0002CAw\u0001\u0005\u0005I\u0011\u0001.\t\u0013\u0005=\b!!A\u0005\u0002\u0005E\b\"CA\u007f\u0001\u0005\u0005I\u0011IA\u0000\u0011%\u0011i\u0001AA\u0001\n\u0003\u0011y\u0001C\u0005\u0003\u0014\u0001\t\t\u0011\"\u0011\u0003\u0016!I!\u0011\u0004\u0001\u0002\u0002\u0013\u0005#1\u0004\u0005\n\u0005;\u0001\u0011\u0011!C!\u0005?A\u0011B!\t\u0001\u0003\u0003%\tEa\t\b\u0015\tM\"'!A\t\u0002Y\u0012)DB\u00052e\u0005\u0005\t\u0012\u0001\u001c\u00038!9\u0011\u0011A\u0015\u0005\u0002\t=\u0003\"\u0003B\u000fS\u0005\u0005IQ\tB\u0010\u0011%\u0011\t&KA\u0001\n\u0003\u0013\u0019\u0006C\u0005\u0003`%\n\n\u0011\"\u0001\u0002V\"I!\u0011M\u0015\u0002\u0002\u0013\u0005%1\r\u0005\n\u0005kJ\u0013\u0013!C\u0001\u0003+D\u0011Ba\u001e*\u0003\u0003%IA!\u001f\u00033\r{W\u000e\u001d7fi\u0016$GK]1wKJ\u001c\u0018\r\u001c\"vS2$WM\u001d\u0006\u0003gQ\nA![7qY*\u0011QGN\u0001\u0007gR\u0014X-Y7\u000b\u0003]\nA!Y6lCN)\u0001!O D\rB\u0011!(P\u0007\u0002w)\tA(A\u0003tG\u0006d\u0017-\u0003\u0002?w\t1\u0011I\\=SK\u001a\u0004\"\u0001Q!\u000e\u0003IJ!A\u0011\u001a\u0003!Q\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\bC\u0001\u001eE\u0013\t)5HA\u0004Qe>$Wo\u0019;\u0011\u0005\u001d\u0003fB\u0001%O\u001d\tIU*D\u0001K\u0015\tYE*\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005a\u0014BA(<\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0015*\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005=[\u0014A\u0004;sCZ,'o]1m'>4\u0015M]\u000b\u0002+B\u0011\u0001IV\u0005\u0003/J\u0012\u0011\u0002\u0016:bm\u0016\u00148/\u00197\u0002\u001fQ\u0014\u0018M^3sg\u0006d7k\u001c$be\u0002\nq!\u001b8TY>$8/F\u0001\\!\tQD,\u0003\u0002^w\t\u0019\u0011J\u001c;\u0002\u0011%t7\u000b\\8ug\u0002\n!\"\u001b8U_>3gm]3u+\u0005\t\u0007\u0003\u00022gSns!a\u00193\u0011\u0005%[\u0014BA3<\u0003\u0019\u0001&/\u001a3fM&\u0011q\r\u001b\u0002\u0004\u001b\u0006\u0004(BA3<!\tQ7.D\u00015\u0013\taGG\u0001\u0004J]B{'\u000f^\u0001\fS:$vn\u00144gg\u0016$\b%\u0001\u0006biR\u0014\u0018NY;uKN,\u0012\u0001\u001d\t\u0003UFL!A\u001d\u001b\u0003\u0015\u0005#HO]5ckR,7/A\u0006biR\u0014\u0018NY;uKN\u0004\u0013!C5tY\u0006tG\rV1h+\u00051\bcA<{y6\t\u0001P\u0003\u0002zm\u0005!Q\u000f^5m\u0013\tY\bPA\u0005PaRLwN\u001c,bYB\u0011\u0001)`\u0005\u0003}J\u0012\u0011\"S:mC:$G+Y4\u0002\u0015%\u001cH.\u00198e)\u0006<\u0007%\u0001\u0004=S:LGO\u0010\u000b\r\u0003\u000b\t9!!\u0003\u0002\f\u00055\u0011q\u0002\t\u0003\u0001\u0002AQaU\u0006A\u0002UCQ!W\u0006A\u0002mCQaX\u0006A\u0002\u0005DQA\\\u0006A\u0002ADq\u0001^\u0006\u0011\u0002\u0003\u0007a/A\u0002bI\u0012$raPA\u000b\u00033\t\u0019\u0003\u0003\u0004\u0002\u00181\u0001\raP\u0001\ngV\u0014Wn\u001c3vY\u0016Dq!a\u0007\r\u0001\u0004\ti\"A\u0003tQ\u0006\u0004X\rE\u0002k\u0003?I1!!\t5\u0005\u0015\u0019\u0006.\u00199f\u0011\u001d\t)\u0003\u0004a\u0001\u0003O\t!bY8nE&tW-T1u!\u0011\tI#a\u000f\u000f\t\u0005-\u0012q\u0007\b\u0005\u0003[\t)D\u0004\u0003\u00020\u0005MbbA%\u00022%\tq'\u0003\u00026m%\u00111\u0007N\u0005\u0004\u0003s\u0011\u0014\u0001\u0005+sCZ,'o]1m\u0005VLG\u000eZ3s\u0013\u0011\ti$a\u0010\u0003\u0019\u0005s\u0017PR;oGRLwN\u001c\u001a\u000b\u0007\u0005e\"'A\u0005ue\u00064XM]:bY\u0006aAO]1og\u001a|'/\\'biR\u0019q(a\u0012\t\u000f\u0005%c\u00021\u0001\u0002L\u0005\ta\r\u0005\u0003\u0002*\u00055\u0013\u0002BA(\u0003\u007f\u0011A\"\u00118z\rVt7\r^5p]F\n\u0001b\u001c4gg\u0016$xJ\u001a\u000b\u00047\u0006U\u0003BBA,\u001f\u0001\u0007\u0011.\u0001\u0002j]\u0006\u0019\u0012n\u001d+sCZ,'o]1m\u0007>l\u0007\u000f\\3uKV\u0011\u0011Q\f\t\u0004u\u0005}\u0013bAA1w\t9!i\\8mK\u0006t\u0017\u0001B<je\u0016$RaPA4\u0003cBq!!\u001b\u0012\u0001\u0004\tY'A\u0002pkR\u00042A[A7\u0013\r\ty\u0007\u000e\u0002\b\u001fV$\bk\u001c:u\u0011\u0019\t9&\u0005a\u0001S\u0006)\u0012N\u001c;fe:\fGnU3u\u0003R$(/\u001b2vi\u0016\u001cHcA \u0002x!)aN\u0005a\u0001a\u0006YQO\\<je\u0016$w*\u001e;t\u0003)i\u0017m[3Jg2\fg\u000e\u001a\u000b\u0004\u007f\u0005}\u0004\"\u0002;\u0015\u0001\u0004a\u0018AB1tg&<g\u000eF\u0003@\u0003\u000b\u000b9\tC\u0004\u0002jU\u0001\r!a\u001b\t\r\u0005%U\u00031\u0001\\\u00031\u0011X\r\\1uSZ,7\u000b\\8u\u00039ygMZ:fi>3Wj\u001c3vY\u0016$2aWAH\u0011\u001d\tIG\u0006a\u0001\u0003W\n\u0011\"[:V]^L'/\u001a3\u0015\t\u0005u\u0013Q\u0013\u0005\b\u0003S:\u0002\u0019AA6)\u0011\ti&!'\t\r\u0005]\u0003\u00041\u0001j\u0003\u0011\u0019w\u000e]=\u0015\u0019\u0005\u0015\u0011qTAQ\u0003G\u000b)+a*\t\u000fMK\u0002\u0013!a\u0001+\"9\u0011,\u0007I\u0001\u0002\u0004Y\u0006bB0\u001a!\u0003\u0005\r!\u0019\u0005\b]f\u0001\n\u00111\u0001q\u0011\u001d!\u0018\u0004%AA\u0002Y\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002.*\u001aQ+a,,\u0005\u0005E\u0006\u0003BAZ\u0003{k!!!.\u000b\t\u0005]\u0016\u0011X\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a/<\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u007f\u000b)LA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002F*\u001a1,a,\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u00111\u001a\u0016\u0004C\u0006=\u0016AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0003\u0003#T3\u0001]AX\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*\"!a6+\u0007Y\fy+A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003;\u0004B!a8\u0002j6\u0011\u0011\u0011\u001d\u0006\u0005\u0003G\f)/\u0001\u0003mC:<'BAAt\u0003\u0011Q\u0017M^1\n\t\u0005-\u0018\u0011\u001d\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111_A}!\rQ\u0014Q_\u0005\u0004\u0003o\\$aA!os\"A\u00111`\u0011\u0002\u0002\u0003\u00071,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u0003\u0001bAa\u0001\u0003\n\u0005MXB\u0001B\u0003\u0015\r\u00119aO\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B\u0006\u0005\u000b\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011Q\fB\t\u0011%\tYpIA\u0001\u0002\u0004\t\u00190\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAo\u0005/A\u0001\"a?%\u0003\u0003\u0005\raW\u0001\tQ\u0006\u001c\bnQ8eKR\t1,\u0001\u0005u_N#(/\u001b8h)\t\ti.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003;\u0012)\u0003C\u0005\u0002|\u001e\n\t\u00111\u0001\u0002t\"\u001a\u0001A!\u000b\u0011\t\t-\"qF\u0007\u0003\u0005[Q1!a/7\u0013\u0011\u0011\tD!\f\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u001a\u0007>l\u0007\u000f\\3uK\u0012$&/\u0019<feN\fGNQ;jY\u0012,'\u000f\u0005\u0002ASM)\u0011F!\u000f\u0003FAY!1\bB!+n\u000b\u0007O^A\u0003\u001b\t\u0011iDC\u0002\u0003@m\nqA];oi&lW-\u0003\u0003\u0003D\tu\"!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8okA!!q\tB'\u001b\t\u0011IE\u0003\u0003\u0003L\u0005\u0015\u0018AA5p\u0013\r\t&\u0011\n\u000b\u0003\u0005k\tQ!\u00199qYf$B\"!\u0002\u0003V\t]#\u0011\fB.\u0005;BQa\u0015\u0017A\u0002UCQ!\u0017\u0017A\u0002mCQa\u0018\u0017A\u0002\u0005DQA\u001c\u0017A\u0002ADq\u0001\u001e\u0017\u0011\u0002\u0003\u0007a/A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00136\u0003\u001d)h.\u00199qYf$BA!\u001a\u0003rA)!Ha\u001a\u0003l%\u0019!\u0011N\u001e\u0003\r=\u0003H/[8o!!Q$QN+\\CB4\u0018b\u0001B8w\t1A+\u001e9mKVB\u0011Ba\u001d/\u0003\u0003\u0005\r!!\u0002\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%N\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005w\u0002B!a8\u0003~%!!qPAq\u0005\u0019y%M[3di\u0002")
@InternalApi
public final class CompletedTraversalBuilder
implements TraversalBuilder,
Product,
Serializable {
    private final Traversal traversalSoFar;
    private final int inSlots;
    private final Map<InPort, Object> inToOffset;
    private final Attributes attributes;
    private final IslandTag islandTag;

    public static IslandTag $lessinit$greater$default$5() {
        return CompletedTraversalBuilder$.MODULE$.$lessinit$greater$default$5();
    }

    public static Option<Tuple5<Traversal, Object, Map<InPort, Object>, Attributes, OptionVal<IslandTag>>> unapply(CompletedTraversalBuilder x$0) {
        return CompletedTraversalBuilder$.MODULE$.unapply(x$0);
    }

    public static IslandTag apply$default$5() {
        return CompletedTraversalBuilder$.MODULE$.apply$default$5();
    }

    public static CompletedTraversalBuilder apply(Traversal traversalSoFar, int inSlots, Map<InPort, Object> inToOffset, Attributes attributes, IslandTag islandTag) {
        return CompletedTraversalBuilder$.MODULE$.apply(traversalSoFar, inSlots, inToOffset, attributes, islandTag);
    }

    public static Function1<Tuple5<Traversal, Object, Map<InPort, Object>, Attributes, OptionVal<IslandTag>>, CompletedTraversalBuilder> tupled() {
        return CompletedTraversalBuilder$.MODULE$.tupled();
    }

    public static Function1<Traversal, Function1<Object, Function1<Map<InPort, Object>, Function1<Attributes, Function1<OptionVal<IslandTag>, CompletedTraversalBuilder>>>>> curried() {
        return CompletedTraversalBuilder$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public TraversalBuilder setAttributes(Attributes attributes) {
        return TraversalBuilder.setAttributes$(this, attributes);
    }

    public Traversal traversalSoFar() {
        return this.traversalSoFar;
    }

    @Override
    public int inSlots() {
        return this.inSlots;
    }

    public Map<InPort, Object> inToOffset() {
        return this.inToOffset;
    }

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    public IslandTag islandTag() {
        return this.islandTag;
    }

    @Override
    public TraversalBuilder add(TraversalBuilder submodule, Shape shape, Function2<Nothing$, Nothing$, Object> combineMat) {
        BuilderKey key;
        BuilderKey builderKey = key = new BuilderKey();
        List<TraversalBuildStep> x$1 = Nil$.MODULE$.$colon$colon(builderKey);
        int x$2 = this.inSlots();
        Map<InPort, Object> x$3 = this.inToOffset();
        Map x$4 = (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), this)}));
        Attributes x$5 = this.attributes();
        Traversal x$6 = CompositeTraversalBuilder$.MODULE$.apply$default$1();
        Map<OutPort, Object> x$7 = CompositeTraversalBuilder$.MODULE$.apply$default$5();
        Map<OutPort, BuilderKey> x$8 = CompositeTraversalBuilder$.MODULE$.apply$default$7();
        int x$9 = CompositeTraversalBuilder$.MODULE$.apply$default$8();
        IslandTag x$10 = CompositeTraversalBuilder$.MODULE$.apply$default$10();
        return new CompositeTraversalBuilder(x$6, x$1, x$2, x$3, x$7, x$4, x$8, x$9, x$5, x$10).add(submodule, shape, combineMat);
    }

    @Override
    public Traversal traversal() {
        Traversal withIsland;
        Traversal traversal;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = OptionVal$Some$.MODULE$.unapply(islandTag);
        if (!OptionVal$.MODULE$.isEmpty$extension(islandTag2)) {
            IslandTag tag = OptionVal$.MODULE$.get$extension(islandTag2);
            traversal = new EnterIsland(tag).concat(this.traversalSoFar()).concat(ExitIsland$.MODULE$);
        } else {
            traversal = withIsland = this.traversalSoFar();
        }
        if (this.attributes() == Attributes$.MODULE$.none()) {
            return withIsland;
        }
        return new PushAttributes(this.attributes()).concat(withIsland).concat(PopAttributes$.MODULE$);
    }

    @Override
    public TraversalBuilder transformMat(Function1<Nothing$, Object> f) {
        return this.copy(this.traversalSoFar().concat(new Transform(f)), this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5());
    }

    @Override
    public int offsetOf(InPort in) {
        return BoxesRunTime.unboxToInt(this.inToOffset().apply(in));
    }

    @Override
    public boolean isTraversalComplete() {
        return true;
    }

    @Override
    public TraversalBuilder wire(OutPort out, InPort in) {
        throw new UnsupportedOperationException(new StringBuilder(46).append("Cannot wire ports in a completed builder. ").append(out.mappedTo()).append(" ~> ").append(in.mappedTo()).toString());
    }

    @Override
    public TraversalBuilder internalSetAttributes(Attributes attributes) {
        Attributes x$1 = attributes;
        Traversal x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        Map<InPort, Object> x$4 = this.copy$default$3();
        IslandTag x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$1, x$5);
    }

    @Override
    public int unwiredOuts() {
        return 0;
    }

    @Override
    public TraversalBuilder makeIsland(IslandTag islandTag) {
        IslandTag islandTag2 = this.islandTag();
        OptionVal$.MODULE$.None();
        IslandTag islandTag3 = islandTag2;
        if (!(null != null ? !((Object)null).equals(islandTag3) : islandTag3 != null)) {
            IslandTag x$1 = OptionVal$.MODULE$.apply(islandTag);
            Traversal x$2 = this.copy$default$1();
            int x$3 = this.copy$default$2();
            Map<InPort, Object> x$4 = this.copy$default$3();
            Attributes x$5 = this.copy$default$4();
            return this.copy(x$2, x$3, x$4, x$5, x$1);
        }
        return this;
    }

    @Override
    public TraversalBuilder assign(OutPort out, int relativeSlot) {
        throw new UnsupportedOperationException("Cannot assign ports to slots in a completed builder.");
    }

    @Override
    public int offsetOfModule(OutPort out) {
        throw new UnsupportedOperationException("Cannot look up offsets in a completed builder.");
    }

    @Override
    public boolean isUnwired(OutPort out) {
        return false;
    }

    @Override
    public boolean isUnwired(InPort in) {
        return this.inToOffset().contains(in);
    }

    public CompletedTraversalBuilder copy(Traversal traversalSoFar, int inSlots, Map<InPort, Object> inToOffset, Attributes attributes, IslandTag islandTag) {
        return new CompletedTraversalBuilder(traversalSoFar, inSlots, inToOffset, attributes, islandTag);
    }

    public Traversal copy$default$1() {
        return this.traversalSoFar();
    }

    public int copy$default$2() {
        return this.inSlots();
    }

    public Map<InPort, Object> copy$default$3() {
        return this.inToOffset();
    }

    public Attributes copy$default$4() {
        return this.attributes();
    }

    public IslandTag copy$default$5() {
        return this.islandTag();
    }

    @Override
    public String productPrefix() {
        return "CompletedTraversalBuilder";
    }

    @Override
    public int productArity() {
        return 5;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.traversalSoFar();
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.inSlots());
            }
            case 2: {
                return this.inToOffset();
            }
            case 3: {
                return this.attributes();
            }
            case 4: {
                return new OptionVal<IslandTag>(this.islandTag());
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
        return x$1 instanceof CompletedTraversalBuilder;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "traversalSoFar";
            }
            case 1: {
                return "inSlots";
            }
            case 2: {
                return "inToOffset";
            }
            case 3: {
                return "attributes";
            }
            case 4: {
                return "islandTag";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.traversalSoFar()));
        n = Statics.mix(n, this.inSlots());
        n = Statics.mix(n, Statics.anyHash(this.inToOffset()));
        n = Statics.mix(n, Statics.anyHash(this.attributes()));
        n = Statics.mix(n, Statics.anyHash(new OptionVal<IslandTag>(this.islandTag())));
        return Statics.finalizeHash(n, 5);
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
        if (!(object instanceof CompletedTraversalBuilder)) return false;
        boolean bl = true;
        if (!bl) return false;
        CompletedTraversalBuilder completedTraversalBuilder = (CompletedTraversalBuilder)x$1;
        if (this.inSlots() != completedTraversalBuilder.inSlots()) return false;
        Traversal traversal = this.traversalSoFar();
        Traversal traversal2 = completedTraversalBuilder.traversalSoFar();
        if (traversal == null) {
            if (traversal2 != null) {
                return false;
            }
        } else if (!traversal.equals(traversal2)) return false;
        Map<InPort, Object> map = this.inToOffset();
        Map<InPort, Object> map2 = completedTraversalBuilder.inToOffset();
        if (map == null) {
            if (map2 != null) {
                return false;
            }
        } else if (!map.equals(map2)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = completedTraversalBuilder.attributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = completedTraversalBuilder.islandTag();
        if (islandTag == null) {
            if (islandTag2 == null) return true;
            return false;
        } else {
            if (!islandTag.equals(islandTag2)) return false;
            return true;
        }
    }

    public CompletedTraversalBuilder(Traversal traversalSoFar, int inSlots, Map<InPort, Object> inToOffset, Attributes attributes, IslandTag islandTag) {
        this.traversalSoFar = traversalSoFar;
        this.inSlots = inSlots;
        this.inToOffset = inToOffset;
        this.attributes = attributes;
        this.islandTag = islandTag;
        TraversalBuilder.$init$(this);
        Product.$init$(this);
    }
}

