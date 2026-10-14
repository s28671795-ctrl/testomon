/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005Mc\u0001B\f\u0019\u0005nA\u0001b\u000f\u0001\u0003\u0016\u0004%\t\u0001\u0010\u0005\t{\u0001\u0011\t\u0012)A\u0005C!)a\b\u0001C\u0001\u007f!)!\t\u0001C\u0001y!91\tAA\u0001\n\u0003!\u0005b\u0002&\u0001#\u0003%\ta\u0013\u0005\b1\u0002\t\t\u0011\"\u0011Z\u0011\u001d\u0011\u0007!!A\u0005\u0002\rDqa\u001a\u0001\u0002\u0002\u0013\u0005\u0001\u000eC\u0004l\u0001\u0005\u0005I\u0011\t7\t\u000fM\u0004\u0011\u0011!C\u0001i\"9\u0011\u0010AA\u0001\n\u0003R\bb\u0002?\u0001\u0003\u0003%\t% \u0005\b}\u0002\t\t\u0011\"\u0011\u0000\u0011%\t\t\u0001AA\u0001\n\u0003\n\u0019aB\u0005\u0002\u0012a\t\t\u0011#\u0001\u0002\u0014\u0019Aq\u0003GA\u0001\u0012\u0003\t)\u0002\u0003\u0004?#\u0011\u0005\u0011q\u0005\u0005\b}F\t\t\u0011\"\u0012\u0000\u0011%\tI#EA\u0001\n\u0003\u000bY\u0003C\u0005\u00028E\t\t\u0011\"!\u0002:!I\u0011\u0011J\t\u0002\u0002\u0013%\u00111\n\u0002\u0005'>lWMC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001H\u0012\u0014\t\u0001iBf\f\t\u0004=}\tS\"\u0001\r\n\u0005\u0001B\"AB(qi&|g\u000e\u0005\u0002#G1\u0001AA\u0002\u0013\u0001\t\u000b\u0007QEA\u0001B#\t1\u0013\u0006\u0005\u0002\u001fO%\u0011\u0001\u0006\u0007\u0002\b\u001d>$\b.\u001b8h!\tq\"&\u0003\u0002,1\t\u0019\u0011I\\=\u0011\u0005yi\u0013B\u0001\u0018\u0019\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\r\u001d\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b\u001b\u0003\u0019a$o\\8u}%\t\u0011$\u0003\u000281\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t9\u0004$A\u0003wC2,X-F\u0001\"\u0003\u00191\u0018\r\\;fA\u00051A(\u001b8jiz\"\"\u0001Q!\u0011\u0007y\u0001\u0011\u0005C\u0003<\u0007\u0001\u0007\u0011%A\u0002hKR\fAaY8qsV\u0011Q\t\u0013\u000b\u0003\r&\u00032A\b\u0001H!\t\u0011\u0003\nB\u0003%\u000b\t\u0007Q\u0005C\u0004<\u000bA\u0005\t\u0019A$\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011AjV\u000b\u0002\u001b*\u0012\u0011ET\u0016\u0002\u001fB\u0011\u0001+V\u0007\u0002#*\u0011!kU\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0016\r\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002W#\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b\u00112!\u0019A\u0013\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005Q\u0006CA.a\u001b\u0005a&BA/_\u0003\u0011a\u0017M\\4\u000b\u0003}\u000bAA[1wC&\u0011\u0011\r\u0018\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003\u0011\u0004\"AH3\n\u0005\u0019D\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA\u0015j\u0011\u001dQ\u0017\"!AA\u0002\u0011\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A7\u0011\u00079\f\u0018&D\u0001p\u0015\t\u0001\b$\u0001\u0006d_2dWm\u0019;j_:L!A]8\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003kb\u0004\"A\b<\n\u0005]D\"a\u0002\"p_2,\u0017M\u001c\u0005\bU.\t\t\u00111\u0001*\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005i[\bb\u00026\r\u0003\u0003\u0005\r\u0001Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA-\u0001\u0005u_N#(/\u001b8h)\u0005Q\u0016AB3rk\u0006d7\u000fF\u0002v\u0003\u000bAqA[\b\u0002\u0002\u0003\u0007\u0011\u0006\u000b\u0004\u0001\u0003\u0013Y\u0014q\u0002\t\u0004=\u0005-\u0011bAA\u00071\t\u00012+\u001a:jC24VM]:j_:,\u0016\n\u0012\u0010\t#\t\u0012 NXQ\fj\u0006!1k\\7f!\tq\u0012cE\u0003\u0012\u0003/\ti\u0002E\u0002\u001f\u00033I1!a\u0007\u0019\u0005\u0019\te.\u001f*fMB!\u0011qDA\u0013\u001b\t\t\tCC\u0002\u0002$y\u000b!![8\n\u0007e\n\t\u0003\u0006\u0002\u0002\u0014\u0005)\u0011\r\u001d9msV!\u0011QFA\u001a)\u0011\ty#!\u000e\u0011\ty\u0001\u0011\u0011\u0007\t\u0004E\u0005MB!\u0002\u0013\u0015\u0005\u0004)\u0003BB\u001e\u0015\u0001\u0004\t\t$A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005m\u0012\u0011\t\u000b\u0005\u0003{\t\u0019\u0005\u0005\u0003\u001f?\u0005}\u0002c\u0001\u0012\u0002B\u0011)A%\u0006b\u0001K!I\u0011QI\u000b\u0002\u0002\u0003\u0007\u0011qI\u0001\u0004q\u0012\u0002\u0004\u0003\u0002\u0010\u0001\u0003\u007f\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u0014\u0011\u0007m\u000by%C\u0002\u0002Rq\u0013aa\u00142kK\u000e$\b")
public final class Some<A>
extends Option<A> {
    private static final long serialVersionUID = 1234815782226070388L;
    private final A value;

    public static <A> Option<A> unapply(Some<A> x$0) {
        return Some$.MODULE$.unapply(x$0);
    }

    public static <A> Some<A> apply(A value) {
        return new Some<A>(value);
    }

    public A value() {
        return this.value;
    }

    @Override
    public A get() {
        return this.value();
    }

    public <A> Some<A> copy(A value) {
        return new Some<A>(value);
    }

    public <A> A copy$default$1() {
        return this.value();
    }

    @Override
    public String productPrefix() {
        return "Some";
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
        return x$1 instanceof Some;
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
                if (!(x$1 instanceof Some)) break block3;
                Some some = (Some)x$1;
                if (!BoxesRunTime.equals(this.value(), some.value())) break block3;
            }
            return true;
        }
        return false;
    }

    public Some(A value) {
        this.value = value;
    }
}

