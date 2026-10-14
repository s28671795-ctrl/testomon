/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ExtendedActorSystem;
import akka.actor.SerializedActorRef$;
import akka.serialization.JavaSerializer$;
import akka.serialization.Serialization$;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005d!\u0002\r\u001a\u0005ni\u0002\u0002\u0003\u001b\u0001\u0005+\u0007I\u0011A\u001b\t\u0011y\u0002!\u0011#Q\u0001\nYBQa\u0010\u0001\u0005\n\u0001CQa\u0010\u0001\u0005\u0002\u0011CQA\u0013\u0001\u0005\u0002-Cq!\u0017\u0001\u0002\u0002\u0013\u0005!\fC\u0004]\u0001E\u0005I\u0011A/\t\u000f!\u0004\u0011\u0011!C!S\"9q\u000eAA\u0001\n\u0003\u0001\bb\u0002;\u0001\u0003\u0003%\t!\u001e\u0005\bw\u0002\t\t\u0011\"\u0011}\u0011%\t9\u0001AA\u0001\n\u0003\tI\u0001C\u0005\u0002\u0014\u0001\t\t\u0011\"\u0011\u0002\u0016!I\u0011\u0011\u0004\u0001\u0002\u0002\u0013\u0005\u00131\u0004\u0005\n\u0003;\u0001\u0011\u0011!C!\u0003?A\u0011\"!\t\u0001\u0003\u0003%\t%a\t\b\u0011\u0005M\u0012\u0004#\u0001\u001c\u0003k1q\u0001G\r\t\u0002m\t9\u0004\u0003\u0004@%\u0011\u0005\u0011Q\b\u0005\b\u0003\u007f\u0011B\u0011AA!\u0011%\tyDEA\u0001\n\u0003\u000b)\u0005C\u0005\u0002JI\t\t\u0011\"!\u0002L!I\u0011q\u000b\n\u0002\u0002\u0013%\u0011\u0011\f\u0002\u0013'\u0016\u0014\u0018.\u00197ju\u0016$\u0017i\u0019;peJ+gM\u0003\u0002\u001b7\u0005)\u0011m\u0019;pe*\tA$\u0001\u0003bW.\f7\u0003\u0002\u0001\u001fI\u001d\u0002\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0007CA\u0010&\u0013\t1\u0003EA\u0004Qe>$Wo\u0019;\u0011\u0005!\ndBA\u00150\u001d\tQc&D\u0001,\u0015\taS&\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\t\u0013B\u0001\u0019!\u0003\u001d\u0001\u0018mY6bO\u0016L!AM\u001a\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005A\u0002\u0013\u0001\u00029bi\",\u0012A\u000e\t\u0003omr!\u0001O\u001d\u0011\u0005)\u0002\u0013B\u0001\u001e!\u0003\u0019\u0001&/\u001a3fM&\u0011A(\u0010\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005i\u0002\u0013!\u00029bi\"\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002B\u0007B\u0011!\tA\u0007\u00023!)Ag\u0001a\u0001mQ\u0011\u0011)\u0012\u0005\u0006\r\u0012\u0001\raR\u0001\tC\u000e$xN\u001d*fMB\u0011!\tS\u0005\u0003\u0013f\u0011\u0001\"Q2u_J\u0014VMZ\u0001\fe\u0016\fGMU3t_24X\rF\u0001\u001fQ\r)Q\n\u0017\t\u0004?9\u0003\u0016BA(!\u0005\u0019!\bN]8xgB\u0011\u0011KV\u0007\u0002%*\u00111\u000bV\u0001\u0003S>T\u0011!V\u0001\u0005U\u00064\u0018-\u0003\u0002X%\n)rJ\u00196fGR\u001cFO]3b[\u0016C8-\u001a9uS>t7%\u0001)\u0002\t\r|\u0007/\u001f\u000b\u0003\u0003nCq\u0001\u000e\u0004\u0011\u0002\u0003\u0007a'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003yS#AN0,\u0003\u0001\u0004\"!\u00194\u000e\u0003\tT!a\u00193\u0002\u0013Ut7\r[3dW\u0016$'BA3!\u0003)\tgN\\8uCRLwN\\\u0005\u0003O\n\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t!\u000e\u0005\u0002l]6\tAN\u0003\u0002n)\u0006!A.\u00198h\u0013\taD.\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001r!\ty\"/\u0003\u0002tA\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011a/\u001f\t\u0003?]L!\u0001\u001f\u0011\u0003\u0007\u0005s\u0017\u0010C\u0004{\u0015\u0005\u0005\t\u0019A9\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005i\b\u0003\u0002@\u0002\u0004Yl\u0011a \u0006\u0004\u0003\u0003\u0001\u0013AC2pY2,7\r^5p]&\u0019\u0011QA@\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0017\t\t\u0002E\u0002 \u0003\u001bI1!a\u0004!\u0005\u001d\u0011un\u001c7fC:DqA\u001f\u0007\u0002\u0002\u0003\u0007a/\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,Gc\u00016\u0002\u0018!9!0DA\u0001\u0002\u0004\t\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003E\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002U\u00061Q-];bYN$B!a\u0003\u0002&!9!\u0010EA\u0001\u0002\u00041\bf\u0002\u0001\u0002*\u0005=\u0012\u0011\u0007\t\u0004?\u0005-\u0012bAA\u0017A\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005\u00112+\u001a:jC2L'0\u001a3BGR|'OU3g!\t\u0011%c\u0005\u0003\u0013=\u0005e\u0002cA)\u0002<%\u0011!G\u0015\u000b\u0003\u0003k\tQ!\u00199qYf$2!QA\"\u0011\u00151E\u00031\u0001H)\r\t\u0015q\t\u0005\u0006iU\u0001\rAN\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti%a\u0015\u0011\t}\tyEN\u0005\u0004\u0003#\u0002#AB(qi&|g\u000e\u0003\u0005\u0002VY\t\t\u00111\u0001B\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u00037\u00022a[A/\u0013\r\ty\u0006\u001c\u0002\u0007\u001f\nTWm\u0019;")
public final class SerializedActorRef
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final String path;

    public static Option<String> unapply(SerializedActorRef x$0) {
        return SerializedActorRef$.MODULE$.unapply(x$0);
    }

    public static SerializedActorRef apply(String path) {
        return SerializedActorRef$.MODULE$.apply(path);
    }

    public static SerializedActorRef apply(ActorRef actorRef2) {
        return SerializedActorRef$.MODULE$.apply(actorRef2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String path() {
        return this.path;
    }

    public Object readResolve() throws ObjectStreamException {
        ExtendedActorSystem extendedActorSystem = (ExtendedActorSystem)JavaSerializer$.MODULE$.currentSystem().value();
        if (extendedActorSystem == null) {
            throw new IllegalStateException("Trying to deserialize a serialized ActorRef without an ActorSystem in scope. Use 'akka.serialization.JavaSerializer.currentSystem.withValue(system) { ... }'");
        }
        return extendedActorSystem.provider().resolveActorRef(this.path());
    }

    public SerializedActorRef copy(String path) {
        return new SerializedActorRef(path);
    }

    public String copy$default$1() {
        return this.path();
    }

    @Override
    public String productPrefix() {
        return "SerializedActorRef";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.path();
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
        return x$1 instanceof SerializedActorRef;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "path";
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
        if (!(object instanceof SerializedActorRef)) return false;
        boolean bl = true;
        if (!bl) return false;
        SerializedActorRef serializedActorRef = (SerializedActorRef)x$1;
        String string = this.path();
        String string2 = serializedActorRef.path();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public SerializedActorRef(String path) {
        this.path = path;
        Product.$init$(this);
    }

    public SerializedActorRef(ActorRef actorRef2) {
        this(Serialization$.MODULE$.serializedActorPath(actorRef2));
    }
}

