/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.ScalaReflectionException$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005=b\u0001B\u000b\u0017\u0001fA\u0001B\f\u0001\u0003\u0016\u0004%\ta\f\u0005\tq\u0001\u0011\t\u0012)A\u0005a!)\u0011\b\u0001C\u0001u!9Q\bAA\u0001\n\u0003q\u0004b\u0002!\u0001#\u0003%\t!\u0011\u0005\b\u0019\u0002\t\t\u0011\"\u0011N\u0011\u001d)\u0006!!A\u0005\u0002YCqA\u0017\u0001\u0002\u0002\u0013\u00051\fC\u0004b\u0001\u0005\u0005I\u0011\t2\t\u000f%\u0004\u0011\u0011!C\u0001U\"9q\u000eAA\u0001\n\u0003\u0002\bb\u0002:\u0001\u0003\u0003%\te\u001d\u0005\bi\u0002\t\t\u0011\"\u0011v\u000f\u001d9h#!A\t\u0002a4q!\u0006\f\u0002\u0002#\u0005\u0011\u0010\u0003\u0004:\u001f\u0011\u0005\u00111\u0002\u0005\n\u0003\u001by\u0011\u0011!C#\u0003\u001fA\u0011\"!\u0005\u0010\u0003\u0003%\t)a\u0005\t\u0013\u0005]q\"!A\u0005\u0002\u0006e\u0001\"CA\u0013\u001f\u0005\u0005I\u0011BA\u0014\u0005a\u00196-\u00197b%\u00164G.Z2uS>tW\t_2faRLwN\u001c\u0006\u0002/\u0005)1oY1mC\u000e\u00011\u0003\u0002\u0001\u001bE\u0015\u0002\"aG\u0010\u000f\u0005qiR\"\u0001\f\n\u0005y1\u0012a\u00029bG.\fw-Z\u0005\u0003A\u0005\u0012\u0011\"\u0012=dKB$\u0018n\u001c8\u000b\u0005y1\u0002C\u0001\u000f$\u0013\t!cCA\u0004Qe>$Wo\u0019;\u0011\u0005\u0019bcBA\u0014\u001e\u001d\tA3&D\u0001*\u0015\tQ\u0003$\u0001\u0004=e>|GOP\u0005\u0002/%\u0011Q&\t\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0004[N<W#\u0001\u0019\u0011\u0005E*dB\u0001\u001a4!\tAc#\u0003\u00025-\u00051\u0001K]3eK\u001aL!AN\u001c\u0003\rM#(/\u001b8h\u0015\t!d#\u0001\u0003ng\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002<yA\u0011A\u0004\u0001\u0005\u0006]\r\u0001\r\u0001M\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002<\u007f!9a\u0006\u0002I\u0001\u0002\u0004\u0001\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u0005*\u0012\u0001gQ\u0016\u0002\tB\u0011QIS\u0007\u0002\r*\u0011q\tS\u0001\nk:\u001c\u0007.Z2lK\u0012T!!\u0013\f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002L\r\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005q\u0005CA(U\u001b\u0005\u0001&BA)S\u0003\u0011a\u0017M\\4\u000b\u0003M\u000bAA[1wC&\u0011a\u0007U\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002/B\u0011A\u0004W\u0005\u00033Z\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"\u0001X0\u0011\u0005qi\u0016B\u00010\u0017\u0005\r\te.\u001f\u0005\bA\"\t\t\u00111\u0001X\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t1\rE\u0002eOrk\u0011!\u001a\u0006\u0003MZ\t!bY8mY\u0016\u001cG/[8o\u0013\tAWM\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA6o!\taB.\u0003\u0002n-\t9!i\\8mK\u0006t\u0007b\u00021\u000b\u0003\u0003\u0005\r\u0001X\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002Oc\"9\u0001mCA\u0001\u0002\u00049\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003]\u000ba!Z9vC2\u001cHCA6w\u0011\u001d\u0001W\"!AA\u0002q\u000b\u0001dU2bY\u0006\u0014VM\u001a7fGRLwN\\#yG\u0016\u0004H/[8o!\tarb\u0005\u0003\u0010u\u0006\u0005\u0001\u0003B>\u007famj\u0011\u0001 \u0006\u0003{Z\tqA];oi&lW-\u0003\u0002\u0000y\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005\r\u0011\u0011B\u0007\u0003\u0003\u000bQ1!a\u0002S\u0003\tIw.C\u0002.\u0003\u000b!\u0012\u0001_\u0001\ti>\u001cFO]5oOR\ta*A\u0003baBd\u0017\u0010F\u0002<\u0003+AQA\f\nA\u0002A\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u001c\u0005\u0005\u0002\u0003\u0002\u000f\u0002\u001eAJ1!a\b\u0017\u0005\u0019y\u0005\u000f^5p]\"A\u00111E\n\u0002\u0002\u0003\u00071(A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u000b\u0011\u0007=\u000bY#C\u0002\u0002.A\u0013aa\u00142kK\u000e$\b")
public class ScalaReflectionException
extends Exception
implements Product {
    private final String msg;

    public static Option<String> unapply(ScalaReflectionException x$0) {
        return ScalaReflectionException$.MODULE$.unapply(x$0);
    }

    public static ScalaReflectionException apply(String msg) {
        return new ScalaReflectionException(msg);
    }

    public static <A$> Function1<String, A$> andThen(Function1<ScalaReflectionException, A$> g2) {
        return arg_0 -> Function1.$anonfun$andThen$1(ScalaReflectionException$.MODULE$, g2, arg_0);
    }

    public static <A$> Function1<A$, ScalaReflectionException> compose(Function1<A$, String> g2) {
        return arg_0 -> Function1.$anonfun$compose$1(ScalaReflectionException$.MODULE$, g2, arg_0);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public ScalaReflectionException copy(String msg) {
        return new ScalaReflectionException(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "ScalaReflectionException";
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
        return x$1 instanceof ScalaReflectionException;
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
                if (!(x$1 instanceof ScalaReflectionException)) break block3;
                ScalaReflectionException scalaReflectionException = (ScalaReflectionException)x$1;
                String string = this.msg();
                String string2 = scalaReflectionException.msg();
                if ((string != null ? !string.equals(string2) : string2 != null) || !scalaReflectionException.canEqual(this)) break block3;
            }
            return true;
        }
        return false;
    }

    public ScalaReflectionException(String msg) {
        this.msg = msg;
        super(msg);
    }
}

