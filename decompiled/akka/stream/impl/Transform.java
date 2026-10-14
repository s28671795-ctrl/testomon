/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.MaterializedValueOp;
import akka.stream.impl.Transform$;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rd!B\f\u0019\u0005rq\u0002\u0002C\u001d\u0001\u0005+\u0007I\u0011\u0001\u001e\t\u0011!\u0003!\u0011#Q\u0001\nmBQ!\u0013\u0001\u0005\u0002)CQ!\u0014\u0001\u0005\u00029Cq\u0001\u0016\u0001\u0002\u0002\u0013\u0005Q\u000bC\u0004X\u0001E\u0005I\u0011\u0001-\t\u000f\r\u0004\u0011\u0011!C!I\"9Q\u000eAA\u0001\n\u0003q\u0007b\u0002:\u0001\u0003\u0003%\ta\u001d\u0005\bm\u0002\t\t\u0011\"\u0011x\u0011\u001dq\b!!A\u0005\u0002}D\u0011\"!\u0003\u0001\u0003\u0003%\t%a\u0003\t\u0013\u0005=\u0001!!A\u0005B\u0005E\u0001\"CA\n\u0001\u0005\u0005I\u0011IA\u000b\u0011%\t9\u0002AA\u0001\n\u0003\nIb\u0002\u0006\u0002*a\t\t\u0011#\u0001\u001d\u0003W1\u0011b\u0006\r\u0002\u0002#\u0005A$!\f\t\r%\u000bB\u0011AA#\u0011%\t\u0019\"EA\u0001\n\u000b\n)\u0002\u0003\u0005N#\u0005\u0005I\u0011QA$\u0011%\tY%EA\u0001\n\u0003\u000bi\u0005C\u0005\u0002ZE\t\t\u0011\"\u0003\u0002\\\tIAK]1og\u001a|'/\u001c\u0006\u00033i\tA![7qY*\u00111\u0004H\u0001\u0007gR\u0014X-Y7\u000b\u0003u\tA!Y6lCN)\u0001aH\u0013*YA\u0011\u0001eI\u0007\u0002C)\t!%A\u0003tG\u0006d\u0017-\u0003\u0002%C\t1\u0011I\\=SK\u001a\u0004\"AJ\u0014\u000e\u0003aI!\u0001\u000b\r\u0003'5\u000bG/\u001a:jC2L'0\u001a3WC2,Xm\u00149\u0011\u0005\u0001R\u0013BA\u0016\"\u0005\u001d\u0001&o\u001c3vGR\u0004\"!\f\u001c\u000f\u00059\"dBA\u00184\u001b\u0005\u0001$BA\u00193\u0003\u0019a$o\\8u}\r\u0001\u0011\"\u0001\u0012\n\u0005U\n\u0013a\u00029bG.\fw-Z\u0005\u0003oa\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!!N\u0011\u0002\r5\f\u0007\u000f]3s+\u0005Y\u0004C\u0001\u001fF\u001d\ti4I\u0004\u0002?\u0005:\u0011q(\u0011\b\u0003_\u0001K\u0011!H\u0005\u00037qI!!\u0007\u000e\n\u0005\u0011C\u0012\u0001\u0005+sCZ,'o]1m\u0005VLG\u000eZ3s\u0013\t1uI\u0001\u0007B]f4UO\\2uS>t\u0017G\u0003\u0002E1\u00059Q.\u00199qKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002L\u0019B\u0011a\u0005\u0001\u0005\u0006s\r\u0001\raO\u0001\u0006CB\u0004H.\u001f\u000b\u0003\u001fJ\u0003\"\u0001\t)\n\u0005E\u000b#aA!os\")1\u000b\u0002a\u0001\u001f\u0006\u0019\u0011M]4\u0002\t\r|\u0007/\u001f\u000b\u0003\u0017ZCq!O\u0003\u0011\u0002\u0003\u00071(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003eS#a\u000f.,\u0003m\u0003\"\u0001X1\u000e\u0003uS!AX0\u0002\u0013Ut7\r[3dW\u0016$'B\u00011\"\u0003)\tgN\\8uCRLwN\\\u0005\u0003Ev\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tQ\r\u0005\u0002gW6\tqM\u0003\u0002iS\u0006!A.\u00198h\u0015\u0005Q\u0017\u0001\u00026bm\u0006L!\u0001\\4\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005y\u0007C\u0001\u0011q\u0013\t\t\u0018EA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002Pi\"9Q/CA\u0001\u0002\u0004y\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001y!\rIHpT\u0007\u0002u*\u001110I\u0001\u000bG>dG.Z2uS>t\u0017BA?{\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0005\u0011q\u0001\t\u0004A\u0005\r\u0011bAA\u0003C\t9!i\\8mK\u0006t\u0007bB;\f\u0003\u0003\u0005\raT\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002f\u0003\u001bAq!\u001e\u0007\u0002\u0002\u0003\u0007q.\u0001\u0005iCND7i\u001c3f)\u0005y\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003\u0015\fa!Z9vC2\u001cH\u0003BA\u0001\u00037Aq!^\b\u0002\u0002\u0003\u0007q\nK\u0002\u0001\u0003?\u0001B!!\t\u0002&5\u0011\u00111\u0005\u0006\u0003ArIA!a\n\u0002$\tY\u0011J\u001c;fe:\fG.\u00119j\u0003%!&/\u00198tM>\u0014X\u000e\u0005\u0002'#M)\u0011#a\f\u0002<A1\u0011\u0011GA\u001cw-k!!a\r\u000b\u0007\u0005U\u0012%A\u0004sk:$\u0018.\\3\n\t\u0005e\u00121\u0007\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u001f\u0003\u0007j!!a\u0010\u000b\u0007\u0005\u0005\u0013.\u0001\u0002j_&\u0019q'a\u0010\u0015\u0005\u0005-BcA&\u0002J!)\u0011\b\u0006a\u0001w\u00059QO\\1qa2LH\u0003BA(\u0003+\u0002B\u0001IA)w%\u0019\u00111K\u0011\u0003\r=\u0003H/[8o\u0011!\t9&FA\u0001\u0002\u0004Y\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\f\t\u0004M\u0006}\u0013bAA1O\n1qJ\u00196fGR\u0004")
@InternalApi
public final class Transform
implements MaterializedValueOp,
Product,
Serializable {
    private final Function1<Nothing$, Object> mapper;

    public static Option<Function1<Nothing$, Object>> unapply(Transform x$0) {
        return Transform$.MODULE$.unapply(x$0);
    }

    public static <A> Function1<Function1<Nothing$, Object>, A> andThen(Function1<Transform, A> g2) {
        return Transform$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Transform> compose(Function1<A, Function1<Nothing$, Object>> g2) {
        return Transform$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    public Function1<Nothing$, Object> mapper() {
        return this.mapper;
    }

    public Object apply(Object arg) {
        return this.mapper().apply((Nothing$)arg);
    }

    public Transform copy(Function1<Nothing$, Object> mapper2) {
        return new Transform(mapper2);
    }

    public Function1<Nothing$, Object> copy$default$1() {
        return this.mapper();
    }

    @Override
    public String productPrefix() {
        return "Transform";
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
                return this.mapper();
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
        return x$1 instanceof Transform;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "mapper";
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
        if (!(object instanceof Transform)) return false;
        boolean bl = true;
        if (!bl) return false;
        Transform transform = (Transform)x$1;
        Function1<Nothing$, Object> function1 = this.mapper();
        Function1<Nothing$, Object> function12 = transform.mapper();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public Transform(Function1<Nothing$, Object> mapper2) {
        this.mapper = mapper2;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

