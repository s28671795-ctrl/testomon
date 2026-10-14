/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.OutPort;
import akka.stream.impl.ForwardWire$;
import akka.stream.impl.PhaseIsland;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-f\u0001\u0002\u0012$\r*B\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005\u0005\"Aa\t\u0001BK\u0002\u0013\u0005q\t\u0003\u0005M\u0001\tE\t\u0015!\u0003I\u0011!i\u0005A!f\u0001\n\u0003\t\u0005\u0002\u0003(\u0001\u0005#\u0005\u000b\u0011\u0002\"\t\u0011=\u0003!Q3A\u0005\u0002AC\u0001\u0002\u0016\u0001\u0003\u0012\u0003\u0006I!\u0015\u0005\t+\u0002\u0011)\u001a!C\u0001-\"A1\f\u0001B\tB\u0003%q\u000bC\u0003]\u0001\u0011\u0005Q\fC\u0003e\u0001\u0011\u0005S\rC\u0004o\u0001\u0005\u0005I\u0011A8\t\u000fU\u0004\u0011\u0013!C\u0001m\"I\u00111\u0001\u0001\u0012\u0002\u0013\u0005\u0011Q\u0001\u0005\t\u0003\u0013\u0001\u0011\u0013!C\u0001m\"I\u00111\u0002\u0001\u0012\u0002\u0013\u0005\u0011Q\u0002\u0005\n\u0003#\u0001\u0011\u0013!C\u0001\u0003'A\u0011\"a\u0006\u0001\u0003\u0003%\t%!\u0007\t\u0011\u0005%\u0002!!A\u0005\u0002\u0005C\u0011\"a\u000b\u0001\u0003\u0003%\t!!\f\t\u0013\u0005M\u0002!!A\u0005B\u0005U\u0002\"CA\"\u0001\u0005\u0005I\u0011AA#\u0011%\ty\u0005AA\u0001\n\u0003\n\t\u0006C\u0005\u0002V\u0001\t\t\u0011\"\u0011\u0002X!I\u0011\u0011\f\u0001\u0002\u0002\u0013\u0005\u00131L\u0004\n\u0003?\u001a\u0013\u0011!E\u0005\u0003C2\u0001BI\u0012\u0002\u0002#%\u00111\r\u0005\u00079r!\t!a\u001f\t\u0011\u0011d\u0012\u0011!C#\u0003{B\u0011\"a \u001d\u0003\u0003%\t)!!\t\u0013\u00055E$!A\u0005\u0002\u0006=\u0005\"CAQ9\u0005\u0005I\u0011BAR\u0005-1uN]<be\u0012<\u0016N]3\u000b\u0005\u0011*\u0013\u0001B5na2T!AJ\u0014\u0002\rM$(/Z1n\u0015\u0005A\u0013\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001WE\"\u0004C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#AB!osJ+g\r\u0005\u0002-e%\u00111'\f\u0002\b!J|G-^2u!\t)TH\u0004\u00027w9\u0011qGO\u0007\u0002q)\u0011\u0011(K\u0001\u0007yI|w\u000e\u001e \n\u00039J!\u0001P\u0017\u0002\u000fA\f7m[1hK&\u0011ah\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003y5\n!#[:mC:$w\t\\8cC2|eMZ:fiV\t!\t\u0005\u0002-\u0007&\u0011A)\f\u0002\u0004\u0013:$\u0018aE5tY\u0006tGm\u00127pE\u0006dwJ\u001a4tKR\u0004\u0013\u0001\u00024s_6,\u0012\u0001\u0013\t\u0003\u0013*k\u0011!J\u0005\u0003\u0017\u0016\u0012qaT;u!>\u0014H/A\u0003ge>l\u0007%\u0001\bu_\u001ecwNY1m\u001f\u001a47/\u001a;\u0002\u001fQ|w\t\\8cC2|eMZ:fi\u0002\n\u0001b\\;u'R\fw-Z\u000b\u0002#B\u0011AFU\u0005\u0003'6\u00121!\u00118z\u0003%yW\u000f^*uC\u001e,\u0007%A\u0003qQ\u0006\u001cX-F\u0001X!\rA\u0016,U\u0007\u0002G%\u0011!l\t\u0002\f!\"\f7/Z%tY\u0006tG-\u0001\u0004qQ\u0006\u001cX\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\ry{\u0006-\u00192d!\tA\u0006\u0001C\u0003A\u0017\u0001\u0007!\tC\u0003G\u0017\u0001\u0007\u0001\nC\u0003N\u0017\u0001\u0007!\tC\u0003P\u0017\u0001\u0007\u0011\u000bC\u0003V\u0017\u0001\u0007q+\u0001\u0005u_N#(/\u001b8h)\u00051\u0007CA4l\u001d\tA\u0017\u000e\u0005\u00028[%\u0011!.L\u0001\u0007!J,G-\u001a4\n\u00051l'AB*ue&twM\u0003\u0002k[\u0005!1m\u001c9z)\u0019q\u0006/\u001d:ti\"9\u0001)\u0004I\u0001\u0002\u0004\u0011\u0005b\u0002$\u000e!\u0003\u0005\r\u0001\u0013\u0005\b\u001b6\u0001\n\u00111\u0001C\u0011\u001dyU\u0002%AA\u0002ECq!V\u0007\u0011\u0002\u0003\u0007q+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003]T#A\u0011=,\u0003e\u0004\"A_@\u000e\u0003mT!\u0001`?\u0002\u0013Ut7\r[3dW\u0016$'B\u0001@.\u0003)\tgN\\8uCRLwN\\\u0005\u0004\u0003\u0003Y(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0004U\tA\u00050\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011q\u0002\u0016\u0003#b\fabY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u0002\u0016)\u0012q\u000b_\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005m\u0001\u0003BA\u000f\u0003Oi!!a\b\u000b\t\u0005\u0005\u00121E\u0001\u0005Y\u0006twM\u0003\u0002\u0002&\u0005!!.\u0019<b\u0013\ra\u0017qD\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r\t\u0016q\u0006\u0005\t\u0003c)\u0012\u0011!a\u0001\u0005\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u000e\u0011\u000b\u0005e\u0012qH)\u000e\u0005\u0005m\"bAA\u001f[\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\u0005\u00131\b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002H\u00055\u0003c\u0001\u0017\u0002J%\u0019\u00111J\u0017\u0003\u000f\t{w\u000e\\3b]\"A\u0011\u0011G\f\u0002\u0002\u0003\u0007\u0011+\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u000e\u0003'B\u0001\"!\r\u0019\u0003\u0003\u0005\rAQ\u0001\tQ\u0006\u001c\bnQ8eKR\t!)\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u000f\ni\u0006\u0003\u0005\u00022i\t\t\u00111\u0001R\u0003-1uN]<be\u0012<\u0016N]3\u0011\u0005ac2#\u0002\u000f\u0002f\u0005E\u0004CCA4\u0003[\u0012\u0005JQ)X=6\u0011\u0011\u0011\u000e\u0006\u0004\u0003Wj\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003_\nIGA\tBEN$(/Y2u\rVt7\r^5p]V\u0002B!a\u001d\u0002z5\u0011\u0011Q\u000f\u0006\u0005\u0003o\n\u0019#\u0001\u0002j_&\u0019a(!\u001e\u0015\u0005\u0005\u0005DCAA\u000e\u0003\u0015\t\u0007\u000f\u001d7z)-q\u00161QAC\u0003\u000f\u000bI)a#\t\u000b\u0001{\u0002\u0019\u0001\"\t\u000b\u0019{\u0002\u0019\u0001%\t\u000b5{\u0002\u0019\u0001\"\t\u000b={\u0002\u0019A)\t\u000bU{\u0002\u0019A,\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011SAO!\u0015a\u00131SAL\u0013\r\t)*\f\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u00111\nIJ\u0011%C#^K1!a'.\u0005\u0019!V\u000f\u001d7fk!A\u0011q\u0014\u0011\u0002\u0002\u0003\u0007a,A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!*\u0011\t\u0005u\u0011qU\u0005\u0005\u0003S\u000byB\u0001\u0004PE*,7\r\u001e")
public final class ForwardWire
implements Product,
Serializable {
    private final int islandGlobalOffset;
    private final OutPort from;
    private final int toGlobalOffset;
    private final Object outStage;
    private final PhaseIsland<Object> phase;

    public static Option<Tuple5<Object, OutPort, Object, Object, PhaseIsland<Object>>> unapply(ForwardWire x$0) {
        return ForwardWire$.MODULE$.unapply(x$0);
    }

    public static ForwardWire apply(int islandGlobalOffset, OutPort from, int toGlobalOffset, Object outStage, PhaseIsland<Object> phase) {
        return ForwardWire$.MODULE$.apply(islandGlobalOffset, from, toGlobalOffset, outStage, phase);
    }

    public static Function1<Tuple5<Object, OutPort, Object, Object, PhaseIsland<Object>>, ForwardWire> tupled() {
        return ForwardWire$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<OutPort, Function1<Object, Function1<Object, Function1<PhaseIsland<Object>, ForwardWire>>>>> curried() {
        return ForwardWire$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int islandGlobalOffset() {
        return this.islandGlobalOffset;
    }

    public OutPort from() {
        return this.from;
    }

    public int toGlobalOffset() {
        return this.toGlobalOffset;
    }

    public Object outStage() {
        return this.outStage;
    }

    public PhaseIsland<Object> phase() {
        return this.phase;
    }

    public String toString() {
        return new StringBuilder(56).append("ForwardWire(islandId = ").append(this.islandGlobalOffset()).append(", from = ").append(this.from()).append(", toGlobal = ").append(this.toGlobalOffset()).append(", phase = ").append(this.phase()).append(")").toString();
    }

    public ForwardWire copy(int islandGlobalOffset, OutPort from, int toGlobalOffset, Object outStage, PhaseIsland<Object> phase) {
        return new ForwardWire(islandGlobalOffset, from, toGlobalOffset, outStage, phase);
    }

    public int copy$default$1() {
        return this.islandGlobalOffset();
    }

    public OutPort copy$default$2() {
        return this.from();
    }

    public int copy$default$3() {
        return this.toGlobalOffset();
    }

    public Object copy$default$4() {
        return this.outStage();
    }

    public PhaseIsland<Object> copy$default$5() {
        return this.phase();
    }

    @Override
    public String productPrefix() {
        return "ForwardWire";
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
                return BoxesRunTime.boxToInteger(this.islandGlobalOffset());
            }
            case 1: {
                return this.from();
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.toGlobalOffset());
            }
            case 3: {
                return this.outStage();
            }
            case 4: {
                return this.phase();
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
        return x$1 instanceof ForwardWire;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "islandGlobalOffset";
            }
            case 1: {
                return "from";
            }
            case 2: {
                return "toGlobalOffset";
            }
            case 3: {
                return "outStage";
            }
            case 4: {
                return "phase";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.islandGlobalOffset());
        n = Statics.mix(n, Statics.anyHash(this.from()));
        n = Statics.mix(n, this.toGlobalOffset());
        n = Statics.mix(n, Statics.anyHash(this.outStage()));
        n = Statics.mix(n, Statics.anyHash(this.phase()));
        return Statics.finalizeHash(n, 5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ForwardWire)) return false;
        boolean bl = true;
        if (!bl) return false;
        ForwardWire forwardWire = (ForwardWire)x$1;
        if (this.islandGlobalOffset() != forwardWire.islandGlobalOffset()) return false;
        if (this.toGlobalOffset() != forwardWire.toGlobalOffset()) return false;
        OutPort outPort = this.from();
        OutPort outPort2 = forwardWire.from();
        if (outPort == null) {
            if (outPort2 != null) {
                return false;
            }
        } else if (!((Object)outPort).equals(outPort2)) return false;
        if (!BoxesRunTime.equals(this.outStage(), forwardWire.outStage())) return false;
        PhaseIsland<Object> phaseIsland = this.phase();
        PhaseIsland<Object> phaseIsland2 = forwardWire.phase();
        if (phaseIsland == null) {
            if (phaseIsland2 == null) return true;
            return false;
        } else {
            if (!phaseIsland.equals(phaseIsland2)) return false;
            return true;
        }
    }

    public ForwardWire(int islandGlobalOffset, OutPort from, int toGlobalOffset, Object outStage, PhaseIsland<Object> phase) {
        this.islandGlobalOffset = islandGlobalOffset;
        this.from = from;
        this.toGlobalOffset = toGlobalOffset;
        this.outStage = outStage;
        this.phase = phase;
        Product.$init$(this);
    }
}

