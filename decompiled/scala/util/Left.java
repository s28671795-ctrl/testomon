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
import scala.util.Left$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001B\r\u001b\u0005~A\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005K!)a\t\u0001C\u0001\u000f\")!\n\u0001C\u0001\u0017\")q\n\u0001C\u0001\u0017\")\u0001\u000b\u0001C\u0001#\"9\u0001\fAA\u0001\n\u0003I\u0006bB1\u0001#\u0003%\tA\u0019\u0005\ba\u0002\t\t\u0011\"\u0011r\u0011\u001dQ\b!!A\u0005\u0002mD\u0001b \u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}\u0001\"CA\u0012\u0001\u0005\u0005I\u0011IA\u0013\u0011%\t9\u0003AA\u0001\n\u0003\nI\u0003C\u0005\u0002,\u0001\t\t\u0011\"\u0011\u0002.\u001dI\u0011\u0011\u0007\u000e\u0002\u0002#\u0005\u00111\u0007\u0004\t3i\t\t\u0011#\u0001\u00026!1ai\u0005C\u0001\u0003\u000fB\u0011\"a\n\u0014\u0003\u0003%)%!\u000b\t\u0013\u0005%3#!A\u0005\u0002\u0006-\u0003\"CA.'\u0005\u0005I\u0011QA/\u0011%\t)hEA\u0001\n\u0013\t9H\u0001\u0003MK\u001a$(BA\u000e\u001d\u0003\u0011)H/\u001b7\u000b\u0003u\tQa]2bY\u0006\u001c\u0001!F\u0002!OI\u001aB\u0001A\u00115oA!!eI\u00132\u001b\u0005Q\u0012B\u0001\u0013\u001b\u0005\u0019)\u0015\u000e\u001e5feB\u0011ae\n\u0007\u0001\t\u0019A\u0003\u0001\"b\u0001S\t\t\u0011)\u0005\u0002+]A\u00111\u0006L\u0007\u00029%\u0011Q\u0006\b\u0002\b\u001d>$\b.\u001b8h!\tYs&\u0003\u000219\t\u0019\u0011I\\=\u0011\u0005\u0019\u0012DAB\u001a\u0001\t\u000b\u0007\u0011FA\u0001C!\tYS'\u0003\u000279\t9\u0001K]8ek\u000e$\bC\u0001\u001dA\u001d\tIdH\u0004\u0002;{5\t1H\u0003\u0002==\u00051AH]8pizJ\u0011!H\u0005\u0003\u007fq\tq\u0001]1dW\u0006<W-\u0003\u0002B\u0005\na1+\u001a:jC2L'0\u00192mK*\u0011q\bH\u0001\u0006m\u0006dW/Z\u000b\u0002K\u00051a/\u00197vK\u0002\na\u0001P5oSRtDC\u0001%J!\u0011\u0011\u0003!J\u0019\t\u000b\r\u001b\u0001\u0019A\u0013\u0002\r%\u001cH*\u001a4u+\u0005a\u0005CA\u0016N\u0013\tqEDA\u0004C_>dW-\u00198\u0002\u000f%\u001c(+[4ii\u0006Iq/\u001b;i%&<\u0007\u000e^\u000b\u0003%V+\u0012a\u0015\t\u0005E\r*C\u000b\u0005\u0002'+\u0012)aK\u0002b\u0001/\n\u0011!)M\t\u0003c9\nAaY8qsV\u0019!,X0\u0015\u0005m\u0003\u0007\u0003\u0002\u0012\u00019z\u0003\"AJ/\u0005\u000b!:!\u0019A\u0015\u0011\u0005\u0019zF!B\u001a\b\u0005\u0004I\u0003bB\"\b!\u0003\u0005\r\u0001X\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\r\u0019gn\\\u000b\u0002I*\u0012Q%Z\u0016\u0002MB\u0011q\r\\\u0007\u0002Q*\u0011\u0011N[\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u001b\u000f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002nQ\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b!B!\u0019A\u0015\u0005\u000bMB!\u0019A\u0015\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\u0011\bCA:y\u001b\u0005!(BA;w\u0003\u0011a\u0017M\\4\u000b\u0003]\fAA[1wC&\u0011\u0011\u0010\u001e\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003q\u0004\"aK?\n\u0005yd\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001\u0018\u0002\u0004!A\u0011QA\u0006\u0002\u0002\u0003\u0007A0A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u0017\u0001R!!\u0004\u0002\u00149j!!a\u0004\u000b\u0007\u0005EA$\u0001\u0006d_2dWm\u0019;j_:LA!!\u0006\u0002\u0010\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\ra\u00151\u0004\u0005\t\u0003\u000bi\u0011\u0011!a\u0001]\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r\u0011\u0018\u0011\u0005\u0005\t\u0003\u000bq\u0011\u0011!a\u0001y\u0006A\u0001.Y:i\u0007>$W\rF\u0001}\u0003!!xn\u0015;sS:<G#\u0001:\u0002\r\u0015\fX/\u00197t)\ra\u0015q\u0006\u0005\t\u0003\u000b\t\u0012\u0011!a\u0001]\u0005!A*\u001a4u!\t\u00113cE\u0003\u0014\u0003o\ti\u0004E\u0002,\u0003sI1!a\u000f\u001d\u0005\u0019\te.\u001f*fMB!\u0011qHA#\u001b\t\t\tEC\u0002\u0002DY\f!![8\n\u0007\u0005\u000b\t\u0005\u0006\u0002\u00024\u0005)\u0011\r\u001d9msV1\u0011QJA*\u0003/\"B!a\u0014\u0002ZA1!\u0005AA)\u0003+\u00022AJA*\t\u0015AcC1\u0001*!\r1\u0013q\u000b\u0003\u0006gY\u0011\r!\u000b\u0005\u0007\u0007Z\u0001\r!!\u0015\u0002\u000fUt\u0017\r\u001d9msV1\u0011qLA5\u0003g\"B!!\u0019\u0002lA)1&a\u0019\u0002h%\u0019\u0011Q\r\u000f\u0003\r=\u0003H/[8o!\r1\u0013\u0011\u000e\u0003\u0006Q]\u0011\r!\u000b\u0005\n\u0003[:\u0012\u0011!a\u0001\u0003_\n1\u0001\u001f\u00131!\u0019\u0011\u0003!a\u001a\u0002rA\u0019a%a\u001d\u0005\u000bM:\"\u0019A\u0015\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005e\u0004cA:\u0002|%\u0019\u0011Q\u0010;\u0003\r=\u0013'.Z2u\u0001")
public final class Left<A, B>
extends Either<A, B> {
    private final A value;

    public static <A, B> Option<A> unapply(Left<A, B> x$0) {
        return Left$.MODULE$.unapply(x$0);
    }

    public static <A, B> Left<A, B> apply(A value) {
        return new Left<A, B>(value);
    }

    public A value() {
        return this.value;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    public <B1> Either<A, B1> withRight() {
        return this;
    }

    public <A, B> Left<A, B> copy(A value) {
        return new Left<A, B>(value);
    }

    public <A, B> A copy$default$1() {
        return this.value();
    }

    @Override
    public String productPrefix() {
        return "Left";
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
        return x$1 instanceof Left;
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
                if (!(x$1 instanceof Left)) break block3;
                Left left = (Left)x$1;
                if (!BoxesRunTime.equals(this.value(), left.value())) break block3;
            }
            return true;
        }
        return false;
    }

    public Left(A value) {
        this.value = value;
    }
}

