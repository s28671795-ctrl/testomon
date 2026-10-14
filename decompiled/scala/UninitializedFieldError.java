/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.UninitializedFieldError$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005]b\u0001\u0002\f\u0018\u0005jA\u0001b\f\u0001\u0003\u0016\u0004%\t\u0001\r\u0005\ts\u0001\u0011\t\u0012)A\u0005c!)!\b\u0001C\u0001w!)!\b\u0001C\u0001}!9A\tAA\u0001\n\u0003)\u0005bB$\u0001#\u0003%\t\u0001\u0013\u0005\b'\u0002\t\t\u0011\"\u0011U\u0011\u001da\u0006!!A\u0005\u0002uCq!\u0019\u0001\u0002\u0002\u0013\u0005!\rC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYx#!A\t\u0002q4qAF\f\u0002\u0002#\u0005Q\u0010\u0003\u0004;!\u0011\u0005\u00111\u0003\u0005\n\u0003+\u0001\u0012\u0011!C#\u0003/A\u0011\"!\u0007\u0011\u0003\u0003%\t)a\u0007\t\u0013\u0005}\u0001#!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017!\u0005\u0005I\u0011BA\u0018\u0005])f.\u001b8ji&\fG.\u001b>fI\u001aKW\r\u001c3FeJ|'OC\u0001\u0019\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019B\u0001A\u000e$MA\u0011A\u0004\t\b\u0003;yi\u0011aF\u0005\u0003?]\tq\u0001]1dW\u0006<W-\u0003\u0002\"E\t\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\u0006\u0003?]\u0001\"!\b\u0013\n\u0005\u0015:\"a\u0002)s_\u0012,8\r\u001e\t\u0003O5r!\u0001\u000b\u0010\u000f\u0005%bS\"\u0001\u0016\u000b\u0005-J\u0012A\u0002\u001fs_>$h(C\u0001\u0019\u0013\tq#E\u0001\u0007TKJL\u0017\r\\5{C\ndW-A\u0002ng\u001e,\u0012!\r\t\u0003eYr!a\r\u001b\u0011\u0005%:\u0012BA\u001b\u0018\u0003\u0019\u0001&/\u001a3fM&\u0011q\u0007\u000f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005U:\u0012\u0001B7tO\u0002\na\u0001P5oSRtDC\u0001\u001f>!\ti\u0002\u0001C\u00030\u0007\u0001\u0007\u0011\u0007\u0006\u0002=\u007f!)\u0001\t\u0002a\u0001\u0003\u0006\u0019qN\u00196\u0011\u0005u\u0011\u0015BA\"\u0018\u0005\r\te._\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002=\r\"9q&\u0002I\u0001\u0002\u0004\t\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u0013*\u0012\u0011GS\u0016\u0002\u0017B\u0011A*U\u0007\u0002\u001b*\u0011ajT\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001U\f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002S\u001b\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005)\u0006C\u0001,\\\u001b\u00059&B\u0001-Z\u0003\u0011a\u0017M\\4\u000b\u0003i\u000bAA[1wC&\u0011qgV\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002=B\u0011QdX\u0005\u0003A^\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"!Q2\t\u000f\u0011L\u0011\u0011!a\u0001=\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012a\u001a\t\u0004Q.\fU\"A5\u000b\u0005)<\u0012AC2pY2,7\r^5p]&\u0011A.\u001b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002peB\u0011Q\u0004]\u0005\u0003c^\u0011qAQ8pY\u0016\fg\u000eC\u0004e\u0017\u0005\u0005\t\u0019A!\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0003+VDq\u0001\u001a\u0007\u0002\u0002\u0003\u0007a,\u0001\u0005iCND7i\u001c3f)\u0005q\u0016AB3rk\u0006d7\u000f\u0006\u0002pu\"9AMDA\u0001\u0002\u0004\t\u0015aF+oS:LG/[1mSj,GMR5fY\u0012,%O]8s!\ti\u0002c\u0005\u0003\u0011}\u0006%\u0001#B@\u0002\u0006EbTBAA\u0001\u0015\r\t\u0019aF\u0001\beVtG/[7f\u0013\u0011\t9!!\u0001\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\f\u0005EQBAA\u0007\u0015\r\ty!W\u0001\u0003S>L1ALA\u0007)\u0005a\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003U\u000bQ!\u00199qYf$2\u0001PA\u000f\u0011\u0015y3\u00031\u00012\u0003\u001d)h.\u00199qYf$B!a\t\u0002*A!Q$!\n2\u0013\r\t9c\u0006\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005-B#!AA\u0002q\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\t\u0004E\u0002W\u0003gI1!!\u000eX\u0005\u0019y%M[3di\u0002")
public final class UninitializedFieldError
extends RuntimeException
implements Product {
    private final String msg;

    public static Option<String> unapply(UninitializedFieldError x$0) {
        return UninitializedFieldError$.MODULE$.unapply(x$0);
    }

    public static UninitializedFieldError apply(String msg) {
        return new UninitializedFieldError(msg);
    }

    public static <A$> Function1<String, A$> andThen(Function1<UninitializedFieldError, A$> g2) {
        return arg_0 -> Function1.$anonfun$andThen$1(UninitializedFieldError$.MODULE$, g2, arg_0);
    }

    public static <A$> Function1<A$, UninitializedFieldError> compose(Function1<A$, String> g2) {
        return arg_0 -> Function1.$anonfun$compose$1(UninitializedFieldError$.MODULE$, g2, arg_0);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public UninitializedFieldError copy(String msg) {
        return new UninitializedFieldError(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "UninitializedFieldError";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.msg();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof UninitializedFieldError;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "msg";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof UninitializedFieldError)) break block3;
                UninitializedFieldError uninitializedFieldError = (UninitializedFieldError)x$1;
                String string = this.msg();
                String string2 = uninitializedFieldError.msg();
                if (string != null ? !string.equals(string2) : string2 != null) break block3;
            }
            return true;
        }
        return false;
    }

    public UninitializedFieldError(String msg) {
        this.msg = msg;
        super(msg);
    }

    public UninitializedFieldError(Object obj) {
        this(String.valueOf(obj));
    }
}

