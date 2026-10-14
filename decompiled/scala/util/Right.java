/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.Option;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.Right$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001B\r\u001b\u0005~A\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005c!)a\t\u0001C\u0001\u000f\")!\n\u0001C\u0001\u0017\")q\n\u0001C\u0001\u0017\")\u0001\u000b\u0001C\u0001#\"9\u0001\fAA\u0001\n\u0003I\u0006bB1\u0001#\u0003%\tA\u0019\u0005\ba\u0002\t\t\u0011\"\u0011r\u0011\u001dQ\b!!A\u0005\u0002mD\u0001b \u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}\u0001\"CA\u0012\u0001\u0005\u0005I\u0011IA\u0013\u0011%\t9\u0003AA\u0001\n\u0003\nI\u0003C\u0005\u0002,\u0001\t\t\u0011\"\u0011\u0002.\u001dI\u0011\u0011\u0007\u000e\u0002\u0002#\u0005\u00111\u0007\u0004\t3i\t\t\u0011#\u0001\u00026!1ai\u0005C\u0001\u0003\u000fB\u0011\"a\n\u0014\u0003\u0003%)%!\u000b\t\u0013\u0005%3#!A\u0005\u0002\u0006-\u0003\"CA.'\u0005\u0005I\u0011QA/\u0011%\t)hEA\u0001\n\u0013\t9HA\u0003SS\u001eDGO\u0003\u0002\u001c9\u0005!Q\u000f^5m\u0015\u0005i\u0012!B:dC2\f7\u0001A\u000b\u0004A\u001d\u00124\u0003\u0002\u0001\"i]\u0002BAI\u0012&c5\t!$\u0003\u0002%5\t1Q)\u001b;iKJ\u0004\"AJ\u0014\r\u0001\u00111\u0001\u0006\u0001CC\u0002%\u0012\u0011!Q\t\u0003U9\u0002\"a\u000b\u0017\u000e\u0003qI!!\f\u000f\u0003\u000f9{G\u000f[5oOB\u00111fL\u0005\u0003aq\u00111!\u00118z!\t1#\u0007\u0002\u00044\u0001\u0011\u0015\r!\u000b\u0002\u0002\u0005B\u00111&N\u0005\u0003mq\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u00029\u0001:\u0011\u0011H\u0010\b\u0003uuj\u0011a\u000f\u0006\u0003yy\ta\u0001\u0010:p_Rt\u0014\"A\u000f\n\u0005}b\u0012a\u00029bG.\fw-Z\u0005\u0003\u0003\n\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!a\u0010\u000f\u0002\u000bY\fG.^3\u0016\u0003E\naA^1mk\u0016\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002I\u0013B!!\u0005A\u00132\u0011\u0015\u00195\u00011\u00012\u0003\u0019I7\u000fT3giV\tA\n\u0005\u0002,\u001b&\u0011a\n\b\u0002\b\u0005>|G.Z1o\u0003\u001dI7OU5hQR\f\u0001b^5uQ2+g\r^\u000b\u0003%V+\u0012a\u0015\t\u0005E\r\"\u0016\u0007\u0005\u0002'+\u0012)aK\u0002b\u0001/\n\u0011\u0011)M\t\u0003K9\nAaY8qsV\u0019!,X0\u0015\u0005m\u0003\u0007\u0003\u0002\u0012\u00019z\u0003\"AJ/\u0005\u000b!:!\u0019A\u0015\u0011\u0005\u0019zF!B\u001a\b\u0005\u0004I\u0003bB\"\b!\u0003\u0005\rAX\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\r\u0019gn\\\u000b\u0002I*\u0012\u0011'Z\u0016\u0002MB\u0011q\r\\\u0007\u0002Q*\u0011\u0011N[\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u001b\u000f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002nQ\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b!B!\u0019A\u0015\u0005\u000bMB!\u0019A\u0015\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\u0011\bCA:y\u001b\u0005!(BA;w\u0003\u0011a\u0017M\\4\u000b\u0003]\fAA[1wC&\u0011\u0011\u0010\u001e\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003q\u0004\"aK?\n\u0005yd\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001\u0018\u0002\u0004!A\u0011QA\u0006\u0002\u0002\u0003\u0007A0A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u0017\u0001R!!\u0004\u0002\u00149j!!a\u0004\u000b\u0007\u0005EA$\u0001\u0006d_2dWm\u0019;j_:LA!!\u0006\u0002\u0010\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\ra\u00151\u0004\u0005\t\u0003\u000bi\u0011\u0011!a\u0001]\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r\u0011\u0018\u0011\u0005\u0005\t\u0003\u000bq\u0011\u0011!a\u0001y\u0006A\u0001.Y:i\u0007>$W\rF\u0001}\u0003!!xn\u0015;sS:<G#\u0001:\u0002\r\u0015\fX/\u00197t)\ra\u0015q\u0006\u0005\t\u0003\u000b\t\u0012\u0011!a\u0001]\u0005)!+[4iiB\u0011!eE\n\u0006'\u0005]\u0012Q\b\t\u0004W\u0005e\u0012bAA\u001e9\t1\u0011I\\=SK\u001a\u0004B!a\u0010\u0002F5\u0011\u0011\u0011\t\u0006\u0004\u0003\u00072\u0018AA5p\u0013\r\t\u0015\u0011\t\u000b\u0003\u0003g\tQ!\u00199qYf,b!!\u0014\u0002T\u0005]C\u0003BA(\u00033\u0002bA\t\u0001\u0002R\u0005U\u0003c\u0001\u0014\u0002T\u0011)\u0001F\u0006b\u0001SA\u0019a%a\u0016\u0005\u000bM2\"\u0019A\u0015\t\r\r3\u0002\u0019AA+\u0003\u001d)h.\u00199qYf,b!a\u0018\u0002t\u0005%D\u0003BA1\u0003W\u0002RaKA2\u0003OJ1!!\u001a\u001d\u0005\u0019y\u0005\u000f^5p]B\u0019a%!\u001b\u0005\u000bM:\"\u0019A\u0015\t\u0013\u00055t#!AA\u0002\u0005=\u0014a\u0001=%aA1!\u0005AA9\u0003O\u00022AJA:\t\u0015AsC1\u0001*\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\tI\bE\u0002t\u0003wJ1!! u\u0005\u0019y%M[3di\u0002")
public final class Right<A, B>
extends Either<A, B> {
    private final B value;

    public static <A, B> Option<B> unapply(Right<A, B> x$0) {
        return Right$.MODULE$.unapply(x$0);
    }

    public static <A, B> Right<A, B> apply(B value) {
        return new Right<A, B>(value);
    }

    public B value() {
        return this.value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    public <A1> Either<A1, B> withLeft() {
        return this;
    }

    public <A, B> Right<A, B> copy(B value) {
        return new Right<A, B>(value);
    }

    public <A, B> B copy$default$1() {
        return this.value();
    }

    @Override
    public String productPrefix() {
        return "Right";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.value();
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
        return x$1 instanceof Right;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "value";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof Right)) break block3;
                Right right = (Right)x$1;
                if (!BoxesRunTime.equals(this.value(), right.value())) break block3;
            }
            return true;
        }
        return false;
    }

    public Right(B value) {
        this.value = value;
    }
}

