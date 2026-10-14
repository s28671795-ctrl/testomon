/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.TransferPhase$;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]c!\u0002\r\u001a\u0005vy\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011q\u0002!\u0011#Q\u0001\naB\u0001\"\u0010\u0001\u0003\u0006\u0004%\tA\u0010\u0005\t\u000b\u0002\u0011\t\u0011)A\u0005\u007f!)a\t\u0001C\u0001\u000f\"9A\nAA\u0001\n\u0003i\u0005bB)\u0001#\u0003%\tA\u0015\u0005\b;\u0002\t\t\u0011\"\u0011_\u0011\u001d9\u0007!!A\u0005\u0002!Dq\u0001\u001c\u0001\u0002\u0002\u0013\u0005Q\u000eC\u0004t\u0001\u0005\u0005I\u0011\t;\t\u000fm\u0004\u0011\u0011!C\u0001y\"I\u00111\u0001\u0001\u0002\u0002\u0013\u0005\u0013Q\u0001\u0005\n\u0003\u0013\u0001\u0011\u0011!C!\u0003\u0017A\u0011\"!\u0004\u0001\u0003\u0003%\t%a\u0004\t\u0013\u0005E\u0001!!A\u0005B\u0005MqACA\u00123\u0005\u0005\t\u0012A\u000f\u0002&\u0019I\u0001$GA\u0001\u0012\u0003i\u0012q\u0005\u0005\u0007\rJ!\t!a\r\t\u0013\u00055!#!A\u0005F\u0005=\u0001\"CA\u001b%\u0005\u0005I\u0011QA\u001c\u0011%\tyDEA\u0001\n\u0003\u000b\t\u0005C\u0005\u0002NI\t\t\u0011\"\u0003\u0002P\tiAK]1og\u001a,'\u000f\u00155bg\u0016T!AG\u000e\u0002\t%l\u0007\u000f\u001c\u0006\u00039u\taa\u001d;sK\u0006l'\"\u0001\u0010\u0002\t\u0005\\7.Y\n\u0005\u0001\u00012\u0013\u0006\u0005\u0002\"I5\t!EC\u0001$\u0003\u0015\u00198-\u00197b\u0013\t)#E\u0001\u0004B]f\u0014VM\u001a\t\u0003C\u001dJ!\u0001\u000b\u0012\u0003\u000fA\u0013x\u000eZ;diB\u0011!f\r\b\u0003WEr!\u0001\f\u0019\u000e\u00035R!AL\u0018\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011aI\u0005\u0003e\t\nq\u0001]1dW\u0006<W-\u0003\u00025k\ta1+\u001a:jC2L'0\u00192mK*\u0011!GI\u0001\raJ,7m\u001c8eSRLwN\\\u000b\u0002qA\u0011\u0011HO\u0007\u00023%\u00111(\u0007\u0002\u000e)J\fgn\u001d4feN#\u0018\r^3\u0002\u001bA\u0014XmY8oI&$\u0018n\u001c8!\u0003\u0019\t7\r^5p]V\tq\bE\u0002\"\u0001\nK!!\u0011\u0012\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004CA\u0011D\u0013\t!%E\u0001\u0003V]&$\u0018aB1di&|g\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005![ECA%K!\tI\u0004\u0001C\u0003>\u000b\u0001\u0007q\bC\u00037\u000b\u0001\u0007\u0001(\u0001\u0003d_BLHC\u0001(Q)\tIu\nC\u0003>\r\u0001\u0007q\bC\u00047\rA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t1K\u000b\u00029).\nQ\u000b\u0005\u0002W76\tqK\u0003\u0002Y3\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u00035\n\n!\"\u00198o_R\fG/[8o\u0013\tavKA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A0\u0011\u0005\u0001,W\"A1\u000b\u0005\t\u001c\u0017\u0001\u00027b]\u001eT\u0011\u0001Z\u0001\u0005U\u00064\u0018-\u0003\u0002gC\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u001b\t\u0003C)L!a\u001b\u0012\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u00059\f\bCA\u0011p\u0013\t\u0001(EA\u0002B]fDqA\u001d\u0006\u0002\u0002\u0003\u0007\u0011.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002kB\u0019a/\u001f8\u000e\u0003]T!\u0001\u001f\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002{o\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\ri\u0018\u0011\u0001\t\u0003CyL!a \u0012\u0003\u000f\t{w\u000e\\3b]\"9!\u000fDA\u0001\u0002\u0004q\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2aXA\u0004\u0011\u001d\u0011X\"!AA\u0002%\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002S\u0006AAo\\*ue&tw\rF\u0001`\u0003\u0019)\u0017/^1mgR\u0019Q0!\u0006\t\u000fI\u0004\u0012\u0011!a\u0001]\"\u001a\u0001!!\u0007\u0011\t\u0005m\u0011qD\u0007\u0003\u0003;Q!AW\u000f\n\t\u0005\u0005\u0012Q\u0004\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.A\u0007Ue\u0006t7OZ3s!\"\f7/\u001a\t\u0003sI\u0019BA\u0005\u0011\u0002*A!\u00111FA\u0019\u001b\t\tiCC\u0002\u00020\r\f!![8\n\u0007Q\ni\u0003\u0006\u0002\u0002&\u0005)\u0011\r\u001d9msR!\u0011\u0011HA\u001f)\rI\u00151\b\u0005\u0006{U\u0001\ra\u0010\u0005\u0006mU\u0001\r\u0001O\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\u0019%!\u0013\u0011\t\u0005\n)\u0005O\u0005\u0004\u0003\u000f\u0012#AB(qi&|g\u000e\u0003\u0005\u0002LY\t\t\u00111\u0001J\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003#\u00022\u0001YA*\u0013\r\t)&\u0019\u0002\u0007\u001f\nTWm\u0019;")
@InternalApi
public final class TransferPhase
implements Product,
Serializable {
    private final TransferState precondition;
    private final Function0<BoxedUnit> action;

    public static Option<TransferState> unapply(TransferPhase x$0) {
        return TransferPhase$.MODULE$.unapply(x$0);
    }

    public static TransferPhase apply(TransferState precondition, Function0<BoxedUnit> action) {
        return TransferPhase$.MODULE$.apply(precondition, action);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public TransferState precondition() {
        return this.precondition;
    }

    public Function0<BoxedUnit> action() {
        return this.action;
    }

    public TransferPhase copy(TransferState precondition, Function0<BoxedUnit> action) {
        return new TransferPhase(precondition, action);
    }

    public TransferState copy$default$1() {
        return this.precondition();
    }

    @Override
    public String productPrefix() {
        return "TransferPhase";
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
                return this.precondition();
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
        return x$1 instanceof TransferPhase;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "precondition";
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
        if (!(object instanceof TransferPhase)) return false;
        boolean bl = true;
        if (!bl) return false;
        TransferPhase transferPhase = (TransferPhase)x$1;
        TransferState transferState = this.precondition();
        TransferState transferState2 = transferPhase.precondition();
        if (transferState != null) {
            if (!transferState.equals(transferState2)) return false;
            return true;
        }
        if (transferState2 == null) return true;
        return false;
    }

    public TransferPhase(TransferState precondition, Function0<BoxedUnit> action) {
        this.precondition = precondition;
        this.action = action;
        Product.$init$(this);
    }
}

