/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.Inlet;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.Shape$;
import akka.stream.impl.AtomicTraversalBuilder$;
import akka.stream.impl.BuilderKey;
import akka.stream.impl.CompletedTraversalBuilder;
import akka.stream.impl.CompletedTraversalBuilder$;
import akka.stream.impl.CompositeTraversalBuilder;
import akka.stream.impl.CompositeTraversalBuilder$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.MaterializeAtomic;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuildStep;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.scaladsl.Keep$;
import java.io.Serializable;
import java.util.Arrays;
import scala.$less$colon$less$;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Tuple2;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tUc!\u0002\u0017.\u0005F\u001a\u0004\u0002\u0003(\u0001\u0005+\u0007I\u0011A(\t\u0011\u0011\u0004!\u0011#Q\u0001\nAC\u0001\"\u001a\u0001\u0003\u0016\u0004%\tA\u001a\u0005\t[\u0002\u0011\t\u0012)A\u0005O\"Aa\u000e\u0001BK\u0002\u0013\u0005q\u000e\u0003\u0005q\u0001\tE\t\u0015!\u0003k\u0011!\t\bA!f\u0001\n\u0003\u0011\b\u0002\u0003<\u0001\u0005#\u0005\u000b\u0011B:\t\u000b]\u0004A\u0011\u0001=\t\u000by\u0004A\u0011I@\t\u000f\u0005m\u0001\u0001\"\u0011\u0002\u001e!A\u0011\u0011\u0006\u0001C\u0002\u0013\u0005s\u000eC\u0004\u0002,\u0001\u0001\u000b\u0011\u00026\t\u000f\u00055\u0002\u0001\"\u0011\u00020!9\u00111\b\u0001\u0005B\u0005u\u0002bBA%\u0001\u0011\u0005\u00131\n\u0005\b\u0003'\u0002A\u0011IA+\u0011\u001d\t\u0019\u0006\u0001C!\u00033Bq!!\u0018\u0001\t\u0003\ny\u0006C\u0004\u0002f\u0001!\t%a\u001a\t\u000f\u0005=\u0004\u0001\"\u0011\u0002r!9\u0011Q\u000f\u0001\u0005B\u0005]\u0004\"CAB\u0001\u0005\u0005I\u0011AAC\u0011%\ty\tAI\u0001\n\u0003\t\t\nC\u0005\u0002(\u0002\t\n\u0011\"\u0001\u0002*\"I\u0011Q\u0016\u0001\u0012\u0002\u0013\u0005\u0011q\u0016\u0005\n\u0003g\u0003\u0011\u0013!C\u0001\u0003kC\u0011\"!/\u0001\u0003\u0003%\t%a/\t\u0011\u00055\u0007!!A\u0005\u0002=D\u0011\"a4\u0001\u0003\u0003%\t!!5\t\u0013\u0005]\u0007!!A\u0005B\u0005e\u0007\"CAt\u0001\u0005\u0005I\u0011AAu\u0011%\ti\u000fAA\u0001\n\u0003\ny\u000fC\u0005\u0002t\u0002\t\t\u0011\"\u0011\u0002v\"I\u0011q\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0011 \u0005\n\u0003w\u0004\u0011\u0011!C!\u0003{<!B!\u0004.\u0003\u0003E\t!\rB\b\r%aS&!A\t\u0002E\u0012\t\u0002\u0003\u0004xM\u0011\u0005!\u0011\u0006\u0005\n\u0003o4\u0013\u0011!C#\u0003sD\u0011Ba\u000b'\u0003\u0003%\tI!\f\t\u0013\t]b%!A\u0005\u0002\ne\u0002\"\u0003B&M\u0005\u0005I\u0011\u0002B'\u0005Y\tEo\\7jGR\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014(B\u0001\u00180\u0003\u0011IW\u000e\u001d7\u000b\u0005A\n\u0014AB:ue\u0016\fWNC\u00013\u0003\u0011\t7n[1\u0014\u000b\u0001!$HP!\u0011\u0005UBT\"\u0001\u001c\u000b\u0003]\nQa]2bY\u0006L!!\u000f\u001c\u0003\r\u0005s\u0017PU3g!\tYD(D\u0001.\u0013\tiTF\u0001\tUe\u00064XM]:bY\n+\u0018\u000e\u001c3feB\u0011QgP\u0005\u0003\u0001Z\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002C\u0017:\u00111)\u0013\b\u0003\t\"k\u0011!\u0012\u0006\u0003\r\u001e\u000ba\u0001\u0010:p_Rt4\u0001A\u0005\u0002o%\u0011!JN\u0001\ba\u0006\u001c7.Y4f\u0013\taUJ\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Km\u00051Qn\u001c3vY\u0016,\u0012\u0001\u0015\t\u0005#jk\u0016M\u0004\u0002S1:\u00111k\u0016\b\u0003)Zs!\u0001R+\n\u0003IJ!\u0001M\u0019\n\u00059z\u0013BA-.\u00031\u0019FO]3b[2\u000b\u0017p\\;u\u0013\tYFL\u0001\u0007Bi>l\u0017nY'pIVdWM\u0003\u0002Z[A\u0011alX\u0007\u0002_%\u0011\u0001m\f\u0002\u0006'\"\f\u0007/\u001a\t\u0003k\tL!a\u0019\u001c\u0003\u0007\u0005s\u00170A\u0004n_\u0012,H.\u001a\u0011\u0002\u0013=,H\u000fV8TY>$X#A4\u0011\u0007UB'.\u0003\u0002jm\t)\u0011I\u001d:bsB\u0011Qg[\u0005\u0003YZ\u00121!\u00138u\u0003)yW\u000f\u001e+p'2|G\u000fI\u0001\fk:<\u0018N]3e\u001fV$8/F\u0001k\u00031)hn^5sK\u0012|U\u000f^:!\u0003)\tG\u000f\u001e:jEV$Xm]\u000b\u0002gB\u0011a\f^\u0005\u0003k>\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003-\tG\u000f\u001e:jEV$Xm\u001d\u0011\u0002\rqJg.\u001b;?)\u0015I(p\u001f?~!\tY\u0004\u0001C\u0003O\u0013\u0001\u0007\u0001\u000bC\u0003f\u0013\u0001\u0007q\rC\u0003o\u0013\u0001\u0007!\u000eC\u0003r\u0013\u0001\u00071/A\u0002bI\u0012$rAOA\u0001\u0003\u000b\tI\u0001\u0003\u0004\u0002\u0004)\u0001\rAO\u0001\ngV\u0014Wn\u001c3vY\u0016Da!a\u0002\u000b\u0001\u0004i\u0016!B:iCB,\u0007bBA\u0006\u0015\u0001\u0007\u0011QB\u0001\u000bG>l'-\u001b8f\u001b\u0006$\b\u0003BA\b\u0003+q1AUA\t\u0013\r\t\u0019\"L\u0001\u0011)J\fg/\u001a:tC2\u0014U/\u001b7eKJLA!a\u0006\u0002\u001a\ta\u0011I\\=Gk:\u001cG/[8oe)\u0019\u00111C\u0017\u0002\u0019Q\u0014\u0018M\\:g_JlW*\u0019;\u0015\u0007i\ny\u0002C\u0004\u0002\"-\u0001\r!a\t\u0002\u0003\u0019\u0004B!a\u0004\u0002&%!\u0011qEA\r\u00051\te.\u001f$v]\u000e$\u0018n\u001c82\u0003\u001dIgn\u00157piN\f\u0001\"\u001b8TY>$8\u000fI\u0001\u000f_\u001a47/\u001a;PM6{G-\u001e7f)\rQ\u0017\u0011\u0007\u0005\b\u0003gq\u0001\u0019AA\u001b\u0003\ryW\u000f\u001e\t\u0004=\u0006]\u0012bAA\u001d_\t9q*\u001e;Q_J$\u0018\u0001C8gMN,Go\u00144\u0015\u0007)\fy\u0004C\u0004\u0002B=\u0001\r!a\u0011\u0002\u0005%t\u0007c\u00010\u0002F%\u0019\u0011qI\u0018\u0003\r%s\u0007k\u001c:u\u0003MI7\u000f\u0016:bm\u0016\u00148/\u00197D_6\u0004H.\u001a;f+\t\ti\u0005E\u00026\u0003\u001fJ1!!\u00157\u0005\u001d\u0011un\u001c7fC:\f\u0011\"[:V]^L'/\u001a3\u0015\t\u00055\u0013q\u000b\u0005\b\u0003g\t\u0002\u0019AA\u001b)\u0011\ti%a\u0017\t\u000f\u0005\u0005#\u00031\u0001\u0002D\u0005!q/\u001b:f)\u0015Q\u0014\u0011MA2\u0011\u001d\t\u0019d\u0005a\u0001\u0003kAq!!\u0011\u0014\u0001\u0004\t\u0019%\u0001\u0004bgNLwM\u001c\u000b\u0006u\u0005%\u00141\u000e\u0005\b\u0003g!\u0002\u0019AA\u001b\u0011\u0019\ti\u0007\u0006a\u0001U\u0006a!/\u001a7bi&4Xm\u00157pi\u0006)\u0012N\u001c;fe:\fGnU3u\u0003R$(/\u001b2vi\u0016\u001cHc\u0001\u001e\u0002t!)\u0011/\u0006a\u0001g\u0006QQ.Y6f\u0013Nd\u0017M\u001c3\u0015\u0007i\nI\bC\u0004\u0002|Y\u0001\r!! \u0002\u0013%\u001cH.\u00198e)\u0006<\u0007cA\u001e\u0002\u0000%\u0019\u0011\u0011Q\u0017\u0003\u0013%\u001bH.\u00198e)\u0006<\u0017\u0001B2paf$\u0012\"_AD\u0003\u0013\u000bY)!$\t\u000f9;\u0002\u0013!a\u0001!\"9Qm\u0006I\u0001\u0002\u00049\u0007b\u00028\u0018!\u0003\u0005\rA\u001b\u0005\bc^\u0001\n\u00111\u0001t\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a%+\u0007A\u000b)j\u000b\u0002\u0002\u0018B!\u0011\u0011TAR\u001b\t\tYJ\u0003\u0003\u0002\u001e\u0006}\u0015!C;oG\",7m[3e\u0015\r\t\tKN\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAS\u00037\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!a++\u0007\u001d\f)*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005E&f\u00016\u0002\u0016\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAA\\U\r\u0019\u0018QS\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005u\u0006\u0003BA`\u0003\u0013l!!!1\u000b\t\u0005\r\u0017QY\u0001\u0005Y\u0006twM\u0003\u0002\u0002H\u0006!!.\u0019<b\u0013\u0011\tY-!1\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$2!YAj\u0011!\t)NHA\u0001\u0002\u0004Q\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\\B)\u0011Q\\ArC6\u0011\u0011q\u001c\u0006\u0004\u0003C4\u0014AC2pY2,7\r^5p]&!\u0011Q]Ap\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u00055\u00131\u001e\u0005\t\u0003+\u0004\u0013\u0011!a\u0001C\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ti,!=\t\u0011\u0005U\u0017%!AA\u0002)\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002U\u0006AAo\\*ue&tw\r\u0006\u0002\u0002>\u00061Q-];bYN$B!!\u0014\u0002\u0000\"A\u0011Q\u001b\u0013\u0002\u0002\u0003\u0007\u0011\rK\u0002\u0001\u0005\u0007\u0001BA!\u0002\u0003\n5\u0011!q\u0001\u0006\u0004\u0003C\u000b\u0014\u0002\u0002B\u0006\u0005\u000f\u00111\"\u00138uKJt\u0017\r\\!qS\u00061\u0012\t^8nS\u000e$&/\u0019<feN\fGNQ;jY\u0012,'\u000f\u0005\u0002<MM)aEa\u0005\u0003 AI!Q\u0003B\u000e!\u001eT7/_\u0007\u0003\u0005/Q1A!\u00077\u0003\u001d\u0011XO\u001c;j[\u0016LAA!\b\u0003\u0018\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001b\u0011\t\t\u0005\"qE\u0007\u0003\u0005GQAA!\n\u0002F\u0006\u0011\u0011n\\\u0005\u0004\u0019\n\rBC\u0001B\b\u0003\u0015\t\u0007\u000f\u001d7z)%I(q\u0006B\u0019\u0005g\u0011)\u0004C\u0003OS\u0001\u0007\u0001\u000bC\u0003fS\u0001\u0007q\rC\u0003oS\u0001\u0007!\u000eC\u0003rS\u0001\u00071/A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\tm\"q\t\t\u0006k\tu\"\u0011I\u0005\u0004\u0005\u007f1$AB(qi&|g\u000eE\u00046\u0005\u0007\u0002vM[:\n\u0007\t\u0015cG\u0001\u0004UkBdW\r\u000e\u0005\t\u0005\u0013R\u0013\u0011!a\u0001s\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t=\u0003\u0003BA`\u0005#JAAa\u0015\u0002B\n1qJ\u00196fGR\u0004")
@InternalApi
public final class AtomicTraversalBuilder
implements TraversalBuilder,
Product,
Serializable {
    private final StreamLayout.AtomicModule<Shape, Object> module;
    private final int[] outToSlot;
    private final int unwiredOuts;
    private final Attributes attributes;
    private final int inSlots;

    public static Option<Tuple4<StreamLayout.AtomicModule<Shape, Object>, int[], Object, Attributes>> unapply(AtomicTraversalBuilder x$0) {
        return AtomicTraversalBuilder$.MODULE$.unapply(x$0);
    }

    public static AtomicTraversalBuilder apply(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlot, int unwiredOuts, Attributes attributes) {
        return AtomicTraversalBuilder$.MODULE$.apply(module, outToSlot, unwiredOuts, attributes);
    }

    public static Function1<Tuple4<StreamLayout.AtomicModule<Shape, Object>, int[], Object, Attributes>, AtomicTraversalBuilder> tupled() {
        return AtomicTraversalBuilder$.MODULE$.tupled();
    }

    public static Function1<StreamLayout.AtomicModule<Shape, Object>, Function1<int[], Function1<Object, Function1<Attributes, AtomicTraversalBuilder>>>> curried() {
        return AtomicTraversalBuilder$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public TraversalBuilder setAttributes(Attributes attributes) {
        return TraversalBuilder.setAttributes$(this, attributes);
    }

    @Override
    public Traversal traversal() {
        return TraversalBuilder.traversal$(this);
    }

    public StreamLayout.AtomicModule<Shape, Object> module() {
        return this.module;
    }

    public int[] outToSlot() {
        return this.outToSlot;
    }

    @Override
    public int unwiredOuts() {
        return this.unwiredOuts;
    }

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public TraversalBuilder add(TraversalBuilder submodule, Shape shape, Function2<Nothing$, Nothing$, Object> combineMat) {
        Attributes x$1 = this.attributes();
        Traversal x$2 = CompositeTraversalBuilder$.MODULE$.apply$default$1();
        List<TraversalBuildStep> x$3 = CompositeTraversalBuilder$.MODULE$.apply$default$2();
        int x$4 = CompositeTraversalBuilder$.MODULE$.apply$default$3();
        Map<InPort, Object> x$5 = CompositeTraversalBuilder$.MODULE$.apply$default$4();
        Map<OutPort, Object> x$6 = CompositeTraversalBuilder$.MODULE$.apply$default$5();
        Map<BuilderKey, TraversalBuilder> x$7 = CompositeTraversalBuilder$.MODULE$.apply$default$6();
        Map<OutPort, BuilderKey> x$8 = CompositeTraversalBuilder$.MODULE$.apply$default$7();
        int x$9 = CompositeTraversalBuilder$.MODULE$.apply$default$8();
        IslandTag x$10 = CompositeTraversalBuilder$.MODULE$.apply$default$10();
        return new CompositeTraversalBuilder(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1, x$10).add(this, (Shape)this.module().shape(), Keep$.MODULE$.right()).add(submodule, shape, combineMat);
    }

    @Override
    public TraversalBuilder transformMat(Function1<Nothing$, Object> f) {
        return TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(this, (Shape)this.module().shape(), Keep$.MODULE$.right()).transformMat(f);
    }

    @Override
    public int inSlots() {
        return this.inSlots;
    }

    @Override
    public int offsetOfModule(OutPort out) {
        return 0;
    }

    @Override
    public int offsetOf(InPort in) {
        return in.id();
    }

    @Override
    public boolean isTraversalComplete() {
        return false;
    }

    @Override
    public boolean isUnwired(OutPort out) {
        return true;
    }

    @Override
    public boolean isUnwired(InPort in) {
        return true;
    }

    @Override
    public TraversalBuilder wire(OutPort out, InPort in) {
        return this.assign(out, this.offsetOf(in) - this.offsetOfModule(out));
    }

    @Override
    public TraversalBuilder assign(OutPort out, int relativeSlot) {
        int[] newOutToSlot = Arrays.copyOf(this.outToSlot(), this.outToSlot().length);
        newOutToSlot[out.id()] = relativeSlot;
        int newUnwiredOuts = this.unwiredOuts() - 1;
        if (newUnwiredOuts == 0) {
            Seq<Inlet<?>> inlets = ((Shape)this.module().shape()).inlets();
            Map<InPort, Object> inToOffset = inlets.isEmpty() ? Predef$.MODULE$.Map().empty() : (Shape$.MODULE$.hasOnePort(inlets) ? new Map.Map1(inlets.head(), BoxesRunTime.boxToInteger(((InPort)inlets.head()).id())) : inlets.iterator().map((Function1<Inlet, Tuple2> & Serializable)in -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(in), BoxesRunTime.boxToInteger(in.id()))).toMap($less$colon$less$.MODULE$.refl()));
            return new CompletedTraversalBuilder(new MaterializeAtomic(this.module(), newOutToSlot), this.inSlots(), inToOffset, this.attributes(), CompletedTraversalBuilder$.MODULE$.apply$default$5());
        }
        int[] x$1 = newOutToSlot;
        int x$2 = newUnwiredOuts;
        StreamLayout.AtomicModule<Shape, Object> x$3 = this.copy$default$1();
        Attributes x$4 = this.copy$default$4();
        return this.copy(x$3, x$1, x$2, x$4);
    }

    @Override
    public TraversalBuilder internalSetAttributes(Attributes attributes) {
        Attributes x$1 = attributes;
        StreamLayout.AtomicModule<Shape, Object> x$2 = this.copy$default$1();
        int[] x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        return this.copy(x$2, x$3, x$4, x$1);
    }

    @Override
    public TraversalBuilder makeIsland(IslandTag islandTag) {
        return TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(this, (Shape)this.module().shape(), Keep$.MODULE$.right()).makeIsland(islandTag);
    }

    public AtomicTraversalBuilder copy(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlot, int unwiredOuts, Attributes attributes) {
        return new AtomicTraversalBuilder(module, outToSlot, unwiredOuts, attributes);
    }

    public StreamLayout.AtomicModule<Shape, Object> copy$default$1() {
        return this.module();
    }

    public int[] copy$default$2() {
        return this.outToSlot();
    }

    public int copy$default$3() {
        return this.unwiredOuts();
    }

    public Attributes copy$default$4() {
        return this.attributes();
    }

    @Override
    public String productPrefix() {
        return "AtomicTraversalBuilder";
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
                return this.module();
            }
            case 1: {
                return this.outToSlot();
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.unwiredOuts());
            }
            case 3: {
                return this.attributes();
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
        return x$1 instanceof AtomicTraversalBuilder;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "module";
            }
            case 1: {
                return "outToSlot";
            }
            case 2: {
                return "unwiredOuts";
            }
            case 3: {
                return "attributes";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.module()));
        n = Statics.mix(n, Statics.anyHash(this.outToSlot()));
        n = Statics.mix(n, this.unwiredOuts());
        n = Statics.mix(n, Statics.anyHash(this.attributes()));
        return Statics.finalizeHash(n, 4);
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
        if (!(object instanceof AtomicTraversalBuilder)) return false;
        boolean bl = true;
        if (!bl) return false;
        AtomicTraversalBuilder atomicTraversalBuilder = (AtomicTraversalBuilder)x$1;
        if (this.unwiredOuts() != atomicTraversalBuilder.unwiredOuts()) return false;
        StreamLayout.AtomicModule<Shape, Object> atomicModule = this.module();
        StreamLayout.AtomicModule<Shape, Object> atomicModule2 = atomicTraversalBuilder.module();
        if (atomicModule == null) {
            if (atomicModule2 != null) {
                return false;
            }
        } else if (!atomicModule.equals(atomicModule2)) return false;
        if (this.outToSlot() != atomicTraversalBuilder.outToSlot()) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = atomicTraversalBuilder.attributes();
        if (attributes == null) {
            if (attributes2 == null) return true;
            return false;
        } else {
            if (!((Object)attributes).equals(attributes2)) return false;
            return true;
        }
    }

    public AtomicTraversalBuilder(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlot, int unwiredOuts, Attributes attributes) {
        this.module = module;
        this.outToSlot = outToSlot;
        this.unwiredOuts = unwiredOuts;
        this.attributes = attributes;
        TraversalBuilder.$init$(this);
        Product.$init$(this);
        this.inSlots = ((Shape)module.shape()).inlets().size();
    }
}

