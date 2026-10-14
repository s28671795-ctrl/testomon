/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Product;
import scala.Some;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015e\u0001B\r\u001b\u0005\u0006B\u0001B\u0011\u0001\u0003\u0016\u0004%\te\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005O!IQ\t\u0001BA\u0002\u0013\u0005aD\u0012\u0005\n%\u0002\u0011\t\u0019!C\u0001=MC\u0001\"\u0017\u0001\u0003\u0012\u0003\u0006Ka\u0012\u0005\u00065\u0002!\ta\u0017\u0005\u0006?\u0002!\t\u0005\u0019\u0005\u0006I\u0002!\t%\u001a\u0005\bM\u0002\t\t\u0011\"\u0001h\u0011\u001d\u0001\b!%A\u0005\u0002EDq\u0001\u001e\u0001\u0012\u0002\u0013\u0005Q\u000fC\u0004z\u0001%\u0005I\u0011\u0001$\t\u000fi\u0004\u0011\u0011!C!w\"I\u0011\u0011\u0002\u0001\u0002\u0002\u0013\u0005\u00111\u0002\u0005\n\u0003'\u0001\u0011\u0011!C\u0001\u0003+A\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\t\u0013\u0005\u0015\u0002!!A\u0005B\u0005\u001dr!CA\u00165\u0005\u0005\t\u0012AA\u0017\r!I\"$!A\t\u0002\u0005=\u0002B\u0002.\u0014\t\u0003\t\t\u0005C\u0005\u0002DM\t\t\u0011\"\u0012\u0002F!I\u0011qI\n\u0002\u0002\u0013\u0005\u0015\u0011\n\u0005\n\u00037\u001a\u0012\u0011!CA\u0003;B\u0011\"a\u001f\u0014\u0003\u0003%I!! \u0003\u0019\u0011\u001aw\u000e\\8oI\r|Gn\u001c8\u000b\u0005ma\u0012!C5n[V$\u0018M\u00197f\u0015\tib$\u0001\u0006d_2dWm\u0019;j_:T\u0011aH\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t\u0011\u0013f\u0005\u0003\u0001GM2\u0004c\u0001\u0013&O5\t!$\u0003\u0002'5\t!A*[:u!\tA\u0013\u0006\u0004\u0001\u0005\r)\u0002AQ1\u0001,\u0005\u0005\t\u0015C\u0001\u00171!\tic&D\u0001\u001f\u0013\tycDA\u0004O_RD\u0017N\\4\u0011\u00055\n\u0014B\u0001\u001a\u001f\u0005\r\te.\u001f\t\u0003[QJ!!\u000e\u0010\u0003\u000fA\u0013x\u000eZ;diB\u0011qg\u0010\b\u0003qur!!\u000f\u001f\u000e\u0003iR!a\u000f\u0011\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0012B\u0001 \u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001Q!\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005yr\u0012\u0001\u00025fC\u0012,\u0012aJ\u0001\u0006Q\u0016\fG\rI\u0001\u0005]\u0016DH/F\u0001H!\r!S\u0005\u0013\u0016\u0003O%[\u0013A\u0013\t\u0003\u0017Bk\u0011\u0001\u0014\u0006\u0003\u001b:\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005=s\u0012AC1o]>$\u0018\r^5p]&\u0011\u0011\u000b\u0014\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001\u00038fqR|F%Z9\u0015\u0005Q;\u0006CA\u0017V\u0013\t1fD\u0001\u0003V]&$\bb\u0002-\u0005\u0003\u0003\u0005\raR\u0001\u0004q\u0012\n\u0014!\u00028fqR\u0004\u0013A\u0002\u001fj]&$h\bF\u0002];z\u00032\u0001\n\u0001(\u0011\u0015\u0011e\u00011\u0001(\u0011\u0015)e\u00011\u0001H\u0003)AW-\u00193PaRLwN\\\u000b\u0002CB\u0019QFY\u0014\n\u0005\rt\"\u0001B*p[\u0016\fA\u0001^1jYV\t1%\u0001\u0003d_BLXC\u00015l)\rIG.\u001c\t\u0004I\u0001Q\u0007C\u0001\u0015l\t\u0015Q\u0013B1\u0001,\u0011\u001d\u0011\u0015\u0002%AA\u0002)Dq!R\u0005\u0011\u0002\u0003\u0007a\u000eE\u0002%K=T#A[%\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!o]\u000b\u0002\u0011\u0012)!F\u0003b\u0001W\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0001<y+\u00059(FA\u0012J\t\u0015Q3B1\u0001,\u00035qW\r\u001f;%C\u000e\u001cWm]:%c\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012\u0001 \t\u0004{\u0006\u0015Q\"\u0001@\u000b\u0007}\f\t!\u0001\u0003mC:<'BAA\u0002\u0003\u0011Q\u0017M^1\n\u0007\u0005\u001daP\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u001b\u00012!LA\b\u0013\r\t\tB\b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004a\u0005]\u0001\u0002\u0003-\u0010\u0003\u0003\u0005\r!!\u0004\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\b\u0011\u000b\u0005}\u0011\u0011\u0005\u0019\u000e\u0003qI1!a\t\u001d\u0005!IE/\u001a:bi>\u0014\u0018A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2\u0001`A\u0015\u0011!A\u0016#!AA\u0002\u00055\u0011\u0001\u0004\u0013d_2|g\u000eJ2pY>t\u0007C\u0001\u0013\u0014'\u0015\u0019\u0012\u0011GA\u001c!\ri\u00131G\u0005\u0004\u0003kq\"AB!osJ+g\r\u0005\u0003\u0002:\u0005}RBAA\u001e\u0015\u0011\ti$!\u0001\u0002\u0005%|\u0017b\u0001!\u0002<Q\u0011\u0011QF\u0001\ti>\u001cFO]5oOR\tA0A\u0003baBd\u00170\u0006\u0003\u0002L\u0005ECCBA'\u0003'\n)\u0006\u0005\u0003%\u0001\u0005=\u0003c\u0001\u0015\u0002R\u0011)!F\u0006b\u0001W!1!I\u0006a\u0001\u0003\u001fBa!\u0012\fA\u0002\u0005]\u0003\u0003\u0002\u0013&\u00033R3!a\u0014J\u0003\u001d)h.\u00199qYf,B!a\u0018\u0002pQ!\u0011\u0011MA;!\u0015i\u00131MA4\u0013\r\t)G\b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000f5\nI'!\u001c\u0002r%\u0019\u00111\u000e\u0010\u0003\rQ+\b\u000f\\33!\rA\u0013q\u000e\u0003\u0006U]\u0011\ra\u000b\t\u0005I\u0015\n\u0019HK\u0002\u0002n%C\u0011\"a\u001e\u0018\u0003\u0003\u0005\r!!\u001f\u0002\u0007a$\u0003\u0007\u0005\u0003%\u0001\u00055\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA@!\ri\u0018\u0011Q\u0005\u0004\u0003\u0007s(AB(cU\u0016\u001cG\u000f")
public final class $colon$colon<A>
extends List<A>
implements Product {
    private final A head;
    private List<A> next;

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public List<A> next$access$1() {
        return this.next;
    }

    @Override
    public A head() {
        return this.head;
    }

    public List<A> next() {
        return this.next;
    }

    public void next_$eq(List<A> x$1) {
        this.next = x$1;
    }

    @Override
    public Some<A> headOption() {
        return new Some<A>(this.head());
    }

    @Override
    public List<A> tail() {
        return this.next();
    }

    public <A> $colon$colon<A> copy(A head, List<A> next) {
        return new $colon$colon<A>(head, next);
    }

    public <A> A copy$default$1() {
        return this.head();
    }

    public <A> List<A> copy$default$2() {
        return this.next();
    }

    @Override
    public String productPrefix() {
        return "::";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.head();
            }
            case 1: {
                return this.next$access$1();
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
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "head";
            }
            case 1: {
                return "next";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public $colon$colon(A head, List<A> next) {
        this.head = head;
        this.next = next;
        Statics.releaseFence();
    }
}

