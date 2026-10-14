/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.actor.ActorPath;
import akka.annotation.InternalApi;
import akka.stream.snapshot.RunningInterpreter;
import akka.stream.snapshot.StreamSnapshot;
import akka.stream.snapshot.StreamSnapshotImpl$;
import akka.stream.snapshot.UninitializedInterpreter;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001df!\u0002\u000f\u001e\u0005\u0006\u001a\u0003\u0002\u0003 \u0001\u0005+\u0007I\u0011A \t\u0011\u0019\u0003!\u0011#Q\u0001\n\u0001C\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t\u001f\u0002\u0011\t\u0012)A\u0005\u0013\"A\u0001\u000b\u0001BK\u0002\u0013\u0005\u0011\u000b\u0003\u0005W\u0001\tE\t\u0015!\u0003S\u0011\u00159\u0006\u0001\"\u0001Y\u0011\u0015i\u0006\u0001\"\u0011_\u0011\u001d9\u0007!!A\u0005\u0002!Dq\u0001\u001c\u0001\u0012\u0002\u0013\u0005Q\u000eC\u0004y\u0001E\u0005I\u0011A=\t\u000fm\u0004\u0011\u0013!C\u0001y\"9a\u0010AA\u0001\n\u0003z\b\"CA\b\u0001\u0005\u0005I\u0011AA\t\u0011%\tI\u0002AA\u0001\n\u0003\tY\u0002C\u0005\u0002(\u0001\t\t\u0011\"\u0011\u0002*!I\u0011q\u0007\u0001\u0002\u0002\u0013\u0005\u0011\u0011\b\u0005\n\u0003\u0007\u0002\u0011\u0011!C!\u0003\u000bB\u0011\"!\u0013\u0001\u0003\u0003%\t%a\u0013\t\u0013\u00055\u0003!!A\u0005B\u0005=sACA0;\u0005\u0005\t\u0012A\u0011\u0002b\u0019IA$HA\u0001\u0012\u0003\t\u00131\r\u0005\u0007/Z!\t!a\u001f\t\u0011u3\u0012\u0011!C#\u0003{B\u0011\"a \u0017\u0003\u0003%\t)!!\t\u0013\u0005%e#!A\u0005\u0002\u0006-\u0005\"CAO-\u0005\u0005I\u0011BAP\u0005I\u0019FO]3b[Ns\u0017\r]:i_RLU\u000e\u001d7\u000b\u0005yy\u0012\u0001C:oCB\u001c\bn\u001c;\u000b\u0005\u0001\n\u0013AB:ue\u0016\fWNC\u0001#\u0003\u0011\t7n[1\u0014\u000b\u0001!#FL\u0019\u0011\u0005\u0015BS\"\u0001\u0014\u000b\u0003\u001d\nQa]2bY\u0006L!!\u000b\u0014\u0003\r\u0005s\u0017PU3g!\tYC&D\u0001\u001e\u0013\tiSD\u0001\bTiJ,\u0017-\\*oCB\u001c\bn\u001c;\u0011\u0005\u0015z\u0013B\u0001\u0019'\u0005\u001d\u0001&o\u001c3vGR\u0004\"AM\u001e\u000f\u0005MJdB\u0001\u001b9\u001b\u0005)$B\u0001\u001c8\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0014\n\u0005i2\u0013a\u00029bG.\fw-Z\u0005\u0003yu\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!A\u000f\u0014\u0002\tM,GNZ\u000b\u0002\u0001B\u0011\u0011\tR\u0007\u0002\u0005*\u00111)I\u0001\u0006C\u000e$xN]\u0005\u0003\u000b\n\u0013\u0011\"Q2u_J\u0004\u0016\r\u001e5\u0002\u000bM,GN\u001a\u0011\u0002%\u0005\u001cG/\u001b<f\u0013:$XM\u001d9sKR,'o]\u000b\u0002\u0013B\u0019!G\u0013'\n\u0005-k$aA*fcB\u00111&T\u0005\u0003\u001dv\u0011!CU;o]&tw-\u00138uKJ\u0004(/\u001a;fe\u0006\u0019\u0012m\u0019;jm\u0016Le\u000e^3saJ,G/\u001a:tA\u0005Ia.Z<TQ\u0016dGn]\u000b\u0002%B\u0019!GS*\u0011\u0005-\"\u0016BA+\u001e\u0005a)f.\u001b8ji&\fG.\u001b>fI&sG/\u001a:qe\u0016$XM]\u0001\u000b]\u0016<8\u000b[3mYN\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003Z5nc\u0006CA\u0016\u0001\u0011\u0015qt\u00011\u0001A\u0011\u00159u\u00011\u0001J\u0011\u0015\u0001v\u00011\u0001S\u0003!!xn\u0015;sS:<G#A0\u0011\u0005\u0001$gBA1c!\t!d%\u0003\u0002dM\u00051\u0001K]3eK\u001aL!!\u001a4\u0003\rM#(/\u001b8h\u0015\t\u0019g%\u0001\u0003d_BLH\u0003B-jU.DqAP\u0005\u0011\u0002\u0003\u0007\u0001\tC\u0004H\u0013A\u0005\t\u0019A%\t\u000fAK\u0001\u0013!a\u0001%\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u00018+\u0005\u0001{7&\u00019\u0011\u0005E4X\"\u0001:\u000b\u0005M$\u0018!C;oG\",7m[3e\u0015\t)h%\u0001\u0006b]:|G/\u0019;j_:L!a\u001e:\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003iT#!S8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\tQP\u000b\u0002S_\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!!\u0001\u0011\t\u0005\r\u0011QB\u0007\u0003\u0003\u000bQA!a\u0002\u0002\n\u0005!A.\u00198h\u0015\t\tY!\u0001\u0003kCZ\f\u0017bA3\u0002\u0006\u0005a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u0003\t\u0004K\u0005U\u0011bAA\fM\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011QDA\u0012!\r)\u0013qD\u0005\u0004\u0003C1#aA!os\"I\u0011QE\b\u0002\u0002\u0003\u0007\u00111C\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005-\u0002CBA\u0017\u0003g\ti\"\u0004\u0002\u00020)\u0019\u0011\u0011\u0007\u0014\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u00026\u0005=\"\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u000f\u0002BA\u0019Q%!\u0010\n\u0007\u0005}bEA\u0004C_>dW-\u00198\t\u0013\u0005\u0015\u0012#!AA\u0002\u0005u\u0011A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u0001\u0002H!I\u0011Q\u0005\n\u0002\u0002\u0003\u0007\u00111C\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111C\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005m\u0012\u0011\u000b\u0005\n\u0003K!\u0012\u0011!a\u0001\u0003;A3\u0001AA+!\u0011\t9&a\u0017\u000e\u0005\u0005e#BA;\"\u0013\u0011\ti&!\u0017\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0013'R\u0014X-Y7T]\u0006\u00048\u000f[8u\u00136\u0004H\u000e\u0005\u0002,-M)a#!\u001a\u0002rAA\u0011qMA7\u0001&\u0013\u0016,\u0004\u0002\u0002j)\u0019\u00111\u000e\u0014\u0002\u000fI,h\u000e^5nK&!\u0011qNA5\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\t\u0005\u0003g\nI(\u0004\u0002\u0002v)!\u0011qOA\u0005\u0003\tIw.C\u0002=\u0003k\"\"!!\u0019\u0015\u0005\u0005\u0005\u0011!B1qa2LHcB-\u0002\u0004\u0006\u0015\u0015q\u0011\u0005\u0006}e\u0001\r\u0001\u0011\u0005\u0006\u000ff\u0001\r!\u0013\u0005\u0006!f\u0001\rAU\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti)!'\u0011\u000b\u0015\ny)a%\n\u0007\u0005EeE\u0001\u0004PaRLwN\u001c\t\u0007K\u0005U\u0005)\u0013*\n\u0007\u0005]eE\u0001\u0004UkBdWm\r\u0005\t\u00037S\u0012\u0011!a\u00013\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u0005\u0006\u0003BA\u0002\u0003GKA!!*\u0002\u0006\t1qJ\u00196fGR\u0004")
@InternalApi
public final class StreamSnapshotImpl
implements StreamSnapshot,
Product,
Serializable {
    private final ActorPath self;
    private final Seq<RunningInterpreter> activeInterpreters;
    private final Seq<UninitializedInterpreter> newShells;

    public static Option<Tuple3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>>> unapply(StreamSnapshotImpl x$0) {
        return StreamSnapshotImpl$.MODULE$.unapply(x$0);
    }

    public static StreamSnapshotImpl apply(ActorPath self, Seq<RunningInterpreter> activeInterpreters, Seq<UninitializedInterpreter> newShells) {
        return StreamSnapshotImpl$.MODULE$.apply(self, activeInterpreters, newShells);
    }

    public static Function1<Tuple3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>>, StreamSnapshotImpl> tupled() {
        return StreamSnapshotImpl$.MODULE$.tupled();
    }

    public static Function1<ActorPath, Function1<Seq<RunningInterpreter>, Function1<Seq<UninitializedInterpreter>, StreamSnapshotImpl>>> curried() {
        return StreamSnapshotImpl$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorPath self() {
        return this.self;
    }

    @Override
    public Seq<RunningInterpreter> activeInterpreters() {
        return this.activeInterpreters;
    }

    @Override
    public Seq<UninitializedInterpreter> newShells() {
        return this.newShells;
    }

    public String toString() {
        return new StringBuilder(20).append("StreamSnapshot(").append(this.self()).append(", ").append(this.activeInterpreters()).append(", ").append(this.newShells()).append(")").toString();
    }

    public StreamSnapshotImpl copy(ActorPath self, Seq<RunningInterpreter> activeInterpreters, Seq<UninitializedInterpreter> newShells) {
        return new StreamSnapshotImpl(self, activeInterpreters, newShells);
    }

    public ActorPath copy$default$1() {
        return this.self();
    }

    public Seq<RunningInterpreter> copy$default$2() {
        return this.activeInterpreters();
    }

    public Seq<UninitializedInterpreter> copy$default$3() {
        return this.newShells();
    }

    @Override
    public String productPrefix() {
        return "StreamSnapshotImpl";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.self();
            }
            case 1: {
                return this.activeInterpreters();
            }
            case 2: {
                return this.newShells();
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
        return x$1 instanceof StreamSnapshotImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "self";
            }
            case 1: {
                return "activeInterpreters";
            }
            case 2: {
                return "newShells";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof StreamSnapshotImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        StreamSnapshotImpl streamSnapshotImpl = (StreamSnapshotImpl)x$1;
        ActorPath actorPath = this.self();
        ActorPath actorPath2 = streamSnapshotImpl.self();
        if (actorPath == null) {
            if (actorPath2 != null) {
                return false;
            }
        } else if (!actorPath.equals(actorPath2)) return false;
        Seq<RunningInterpreter> seq = this.activeInterpreters();
        Seq<RunningInterpreter> seq2 = streamSnapshotImpl.activeInterpreters();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Seq<UninitializedInterpreter> seq3 = this.newShells();
        Seq<UninitializedInterpreter> seq4 = streamSnapshotImpl.newShells();
        if (seq3 == null) {
            if (seq4 == null) return true;
            return false;
        } else {
            if (!seq3.equals(seq4)) return false;
            return true;
        }
    }

    public StreamSnapshotImpl(ActorPath self, Seq<RunningInterpreter> activeInterpreters, Seq<UninitializedInterpreter> newShells) {
        this.self = self;
        this.activeInterpreters = activeInterpreters;
        this.newShells = newShells;
        Product.$init$(this);
    }
}

