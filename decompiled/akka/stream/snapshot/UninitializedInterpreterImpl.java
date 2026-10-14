/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.InternalApi;
import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.UninitializedInterpreter;
import akka.stream.snapshot.UninitializedInterpreterImpl$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Mc!\u0002\f\u0018\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011\u0015\u0003!\u0011#Q\u0001\niBQA\u0012\u0001\u0005\u0002\u001dCqA\u0013\u0001\u0002\u0002\u0013\u00051\nC\u0004N\u0001E\u0005I\u0011\u0001(\t\u000fe\u0003\u0011\u0011!C!5\"91\rAA\u0001\n\u0003!\u0007b\u00025\u0001\u0003\u0003%\t!\u001b\u0005\b_\u0002\t\t\u0011\"\u0011q\u0011\u001d)\b!!A\u0005\u0002YDqa\u001f\u0001\u0002\u0002\u0013\u0005C\u0010C\u0004\u007f\u0001\u0005\u0005I\u0011I@\t\u0013\u0005\u0005\u0001!!A\u0005B\u0005\r\u0001\"CA\u0003\u0001\u0005\u0005I\u0011IA\u0004\u000f)\t9bFA\u0001\u0012\u0003Y\u0012\u0011\u0004\u0004\n-]\t\t\u0011#\u0001\u001c\u00037AaA\u0012\t\u0005\u0002\u0005M\u0002\"CA\u0001!\u0005\u0005IQIA\u0002\u0011%\t)\u0004EA\u0001\n\u0003\u000b9\u0004C\u0005\u0002<A\t\t\u0011\"!\u0002>!I\u0011\u0011\n\t\u0002\u0002\u0013%\u00111\n\u0002\u001d+:Lg.\u001b;jC2L'0\u001a3J]R,'\u000f\u001d:fi\u0016\u0014\u0018*\u001c9m\u0015\tA\u0012$\u0001\u0005t]\u0006\u00048\u000f[8u\u0015\tQ2$\u0001\u0004tiJ,\u0017-\u001c\u0006\u00029\u0005!\u0011m[6b'\u0015\u0001a\u0004\n\u0015,!\ty\"%D\u0001!\u0015\u0005\t\u0013!B:dC2\f\u0017BA\u0012!\u0005\u0019\te.\u001f*fMB\u0011QEJ\u0007\u0002/%\u0011qe\u0006\u0002\u0019+:Lg.\u001b;jC2L'0\u001a3J]R,'\u000f\u001d:fi\u0016\u0014\bCA\u0010*\u0013\tQ\u0003EA\u0004Qe>$Wo\u0019;\u0011\u00051*dBA\u00174\u001d\tq#'D\u00010\u0015\t\u0001\u0014'\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\t\u0013B\u0001\u001b!\u0003\u001d\u0001\u0018mY6bO\u0016L!AN\u001c\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Q\u0002\u0013A\u00027pO&\u001c7/F\u0001;!\rY\u0004IQ\u0007\u0002y)\u0011QHP\u0001\nS6lW\u000f^1cY\u0016T!a\u0010\u0011\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002By\t\u00191+Z9\u0011\u0005\u0015\u001a\u0015B\u0001#\u0018\u00055aunZ5d':\f\u0007o\u001d5pi\u00069An\\4jGN\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002I\u0013B\u0011Q\u0005\u0001\u0005\u0006q\r\u0001\rAO\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002I\u0019\"9\u0001\b\u0002I\u0001\u0002\u0004Q\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u001f*\u0012!\bU\u0016\u0002#B\u0011!kV\u0007\u0002'*\u0011A+V\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0016\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002Y'\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005Y\u0006C\u0001/b\u001b\u0005i&B\u00010`\u0003\u0011a\u0017M\\4\u000b\u0003\u0001\fAA[1wC&\u0011!-\u0018\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003\u0015\u0004\"a\b4\n\u0005\u001d\u0004#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u00016n!\ty2.\u0003\u0002mA\t\u0019\u0011I\\=\t\u000f9D\u0011\u0011!a\u0001K\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012!\u001d\t\u0004eNTW\"\u0001 \n\u0005Qt$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"a\u001e>\u0011\u0005}A\u0018BA=!\u0005\u001d\u0011un\u001c7fC:DqA\u001c\u0006\u0002\u0002\u0003\u0007!.\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GCA.~\u0011\u001dq7\"!AA\u0002\u0015\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002K\u0006AAo\\*ue&tw\rF\u0001\\\u0003\u0019)\u0017/^1mgR\u0019q/!\u0003\t\u000f9t\u0011\u0011!a\u0001U\"\u001a\u0001!!\u0004\u0011\t\u0005=\u00111C\u0007\u0003\u0003#Q!AV\u000e\n\t\u0005U\u0011\u0011\u0003\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u000fV]&t\u0017\u000e^5bY&TX\rZ%oi\u0016\u0014\bO]3uKJLU\u000e\u001d7\u0011\u0005\u0015\u00022#\u0002\t\u0002\u001e\u0005%\u0002CBA\u0010\u0003KQ\u0004*\u0004\u0002\u0002\")\u0019\u00111\u0005\u0011\u0002\u000fI,h\u000e^5nK&!\u0011qEA\u0011\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003W\t\t$\u0004\u0002\u0002.)\u0019\u0011qF0\u0002\u0005%|\u0017b\u0001\u001c\u0002.Q\u0011\u0011\u0011D\u0001\u0006CB\u0004H.\u001f\u000b\u0004\u0011\u0006e\u0002\"\u0002\u001d\u0014\u0001\u0004Q\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u007f\t)\u0005\u0005\u0003 \u0003\u0003R\u0014bAA\"A\t1q\n\u001d;j_:D\u0001\"a\u0012\u0015\u0003\u0003\u0005\r\u0001S\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA'!\ra\u0016qJ\u0005\u0004\u0003#j&AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class UninitializedInterpreterImpl
implements UninitializedInterpreter,
Product,
Serializable {
    private final Seq<LogicSnapshot> logics;

    public static Option<Seq<LogicSnapshot>> unapply(UninitializedInterpreterImpl x$0) {
        return UninitializedInterpreterImpl$.MODULE$.unapply(x$0);
    }

    public static UninitializedInterpreterImpl apply(Seq<LogicSnapshot> logics) {
        return UninitializedInterpreterImpl$.MODULE$.apply(logics);
    }

    public static <A> Function1<Seq<LogicSnapshot>, A> andThen(Function1<UninitializedInterpreterImpl, A> g2) {
        return UninitializedInterpreterImpl$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, UninitializedInterpreterImpl> compose(Function1<A, Seq<LogicSnapshot>> g2) {
        return UninitializedInterpreterImpl$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Seq<LogicSnapshot> logics() {
        return this.logics;
    }

    public UninitializedInterpreterImpl copy(Seq<LogicSnapshot> logics) {
        return new UninitializedInterpreterImpl(logics);
    }

    public Seq<LogicSnapshot> copy$default$1() {
        return this.logics();
    }

    @Override
    public String productPrefix() {
        return "UninitializedInterpreterImpl";
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
                return this.logics();
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
        return x$1 instanceof UninitializedInterpreterImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "logics";
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
        if (!(object instanceof UninitializedInterpreterImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        UninitializedInterpreterImpl uninitializedInterpreterImpl = (UninitializedInterpreterImpl)x$1;
        Seq<LogicSnapshot> seq = this.logics();
        Seq<LogicSnapshot> seq2 = uninitializedInterpreterImpl.logics();
        if (seq != null) {
            if (!seq.equals(seq2)) return false;
            return true;
        }
        if (seq2 == null) return true;
        return false;
    }

    public UninitializedInterpreterImpl(Seq<LogicSnapshot> logics) {
        this.logics = logics;
        Product.$init$(this);
    }
}

