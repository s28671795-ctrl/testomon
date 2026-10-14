/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.concurrent.duration.Deadline$;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Ordered;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005\re\u0001\u0002\u0012$\u0001*B\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005\u000b\")\u0011\n\u0001C\u0005\u0015\")A\n\u0001C\u0001\u001b\")\u0001\u000b\u0001C\u0001#\")\u0001\u000b\u0001C\u0001'\")Q\u000b\u0001C\u0001\t\")a\u000b\u0001C\u0001/\")1\f\u0001C\u0001/\")A\f\u0001C\u0001;\"9!\rAA\u0001\n\u0003\u0019\u0007bB3\u0001#\u0003%\tA\u001a\u0005\bc\u0002\t\t\u0011\"\u0011s\u0011\u001dY\b!!A\u0005\u0002qDq! \u0001\u0002\u0002\u0013\u0005a\u0010C\u0005\u0002\n\u0001\t\t\u0011\"\u0011\u0002\f!I\u0011\u0011\u0004\u0001\u0002\u0002\u0013\u0005\u00111\u0004\u0005\n\u0003?\u0001\u0011\u0011!C!\u0003CA\u0011\"!\n\u0001\u0003\u0003%\t%a\n\t\u0013\u0005%\u0002!!A\u0005B\u0005-\u0002\"CA\u0017\u0001\u0005\u0005I\u0011IA\u0018\u000f\u001d\t\u0019d\tE\u0001\u0003k1aAI\u0012\t\u0002\u0005]\u0002BB%\u0018\t\u0003\t\u0019\u0005C\u0004\u0002F]!\t!a\u0012\b\u000f\u0005%s\u0003c\u0001\u0002L\u00199\u0011qJ\f\t\u0002\u0005E\u0003BB%\u001c\t\u0003\ty\u0006\u0003\u0004]7\u0011\u0005\u0011\u0011\r\u0005\n\u0003WZ\u0012\u0011!C\u0005\u0003[B\u0011\"a\u001c\u0018\u0003\u0003%\t)!\u001d\t\u0013\u0005Ut#!A\u0005\u0002\u0006]\u0004\"CA6/\u0005\u0005I\u0011BA7\u0005!!U-\u00193mS:,'B\u0001\u0013&\u0003!!WO]1uS>t'B\u0001\u0014(\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0002Q\u0005)1oY1mC\u000e\u00011#\u0002\u0001,_u\u0002\u0005C\u0001\u0017.\u001b\u00059\u0013B\u0001\u0018(\u0005\u0019\te.\u001f*fMB\u0019\u0001\u0007O\u001e\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b*\u0003\u0019a$o\\8u}%\t\u0001&\u0003\u00028O\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u0005\u001dy%\u000fZ3sK\u0012T!aN\u0014\u0011\u0005q\u0002Q\"A\u0012\u0011\u00051r\u0014BA (\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001M!\n\u0005\tS$\u0001D*fe&\fG.\u001b>bE2,\u0017\u0001\u0002;j[\u0016,\u0012!\u0012\t\u0003y\u0019K!aR\u0012\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006)A/[7fA\u00051A(\u001b8jiz\"\"aO&\t\u000b\r\u001b\u0001\u0019A#\u0002\u000b\u0011\u0002H.^:\u0015\u0005mr\u0005\"B(\u0005\u0001\u0004)\u0015!B8uQ\u0016\u0014\u0018A\u0002\u0013nS:,8\u000f\u0006\u0002<%\")q*\u0002a\u0001\u000bR\u0011Q\t\u0016\u0005\u0006\u001f\u001a\u0001\raO\u0001\ti&lW\rT3gi\u0006Y\u0001.Y:US6,G*\u001a4u)\u0005A\u0006C\u0001\u0017Z\u0013\tQvEA\u0004C_>dW-\u00198\u0002\u0013%\u001cxJ^3sIV,\u0017aB2p[B\f'/\u001a\u000b\u0003=\u0006\u0004\"\u0001L0\n\u0005\u0001<#aA%oi\")qJ\u0003a\u0001w\u0005!1m\u001c9z)\tYD\rC\u0004D\u0017A\u0005\t\u0019A#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqM\u000b\u0002FQ.\n\u0011\u000e\u0005\u0002k_6\t1N\u0003\u0002m[\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003]\u001e\n!\"\u00198o_R\fG/[8o\u0013\t\u00018NA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A:\u0011\u0005QLX\"A;\u000b\u0005Y<\u0018\u0001\u00027b]\u001eT\u0011\u0001_\u0001\u0005U\u00064\u0018-\u0003\u0002{k\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012AX\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ry\u0018Q\u0001\t\u0004Y\u0005\u0005\u0011bAA\u0002O\t\u0019\u0011I\\=\t\u0011\u0005\u001dq\"!AA\u0002y\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0007!\u0015\ty!!\u0006\u0000\u001b\t\t\tBC\u0002\u0002\u0014\u001d\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t9\"!\u0005\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u00041\u0006u\u0001\u0002CA\u0004#\u0005\u0005\t\u0019A@\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004g\u0006\r\u0002\u0002CA\u0004%\u0005\u0005\t\u0019\u00010\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012AX\u0001\ti>\u001cFO]5oOR\t1/\u0001\u0004fcV\fGn\u001d\u000b\u00041\u0006E\u0002\u0002CA\u0004+\u0005\u0005\t\u0019A@\u0002\u0011\u0011+\u0017\r\u001a7j]\u0016\u0004\"\u0001P\f\u0014\t]Y\u0013\u0011\b\t\u0005\u0003w\t\t%\u0004\u0002\u0002>)\u0019\u0011qH<\u0002\u0005%|\u0017b\u0001\"\u0002>Q\u0011\u0011QG\u0001\u0004]><X#A\u001e\u0002#\u0011+\u0017\r\u001a7j]\u0016L5o\u0014:eKJ,G\rE\u0002\u0002Nmi\u0011a\u0006\u0002\u0012\t\u0016\fG\r\\5oK&\u001bxJ\u001d3fe\u0016$7#B\u000e\u0002T\u0005e\u0003c\u0001;\u0002V%\u0019\u0011qK;\u0003\r=\u0013'.Z2u!\u0011\u0001\u00141L\u001e\n\u0007\u0005u#H\u0001\u0005Pe\u0012,'/\u001b8h)\t\tY\u0005F\u0003_\u0003G\n9\u0007\u0003\u0004\u0002fu\u0001\raO\u0001\u0002C\"1\u0011\u0011N\u000fA\u0002m\n\u0011AY\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\nQ!\u00199qYf$2aOA:\u0011\u0015\u0019u\u00041\u0001F\u0003\u001d)h.\u00199qYf$B!!\u001f\u0002\u0000A!A&a\u001fF\u0013\r\tih\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005\u0005\u0005%!AA\u0002m\n1\u0001\u001f\u00131\u0001")
public class Deadline
implements Ordered<Deadline>,
Product,
Serializable {
    private final FiniteDuration time;

    public static Option<FiniteDuration> unapply(Deadline x$0) {
        return Deadline$.MODULE$.unapply(x$0);
    }

    public static Deadline apply(FiniteDuration time) {
        return new Deadline(time);
    }

    public static Deadline now() {
        return Deadline$.MODULE$.now();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public boolean $less(Object that) {
        return Ordered.$less$(this, that);
    }

    @Override
    public boolean $greater(Object that) {
        return Ordered.$greater$(this, that);
    }

    @Override
    public boolean $less$eq(Object that) {
        return Ordered.$less$eq$(this, that);
    }

    @Override
    public boolean $greater$eq(Object that) {
        return Ordered.$greater$eq$(this, that);
    }

    @Override
    public int compareTo(Object that) {
        return Ordered.compareTo$(this, that);
    }

    public FiniteDuration time() {
        return this.time;
    }

    public Deadline $plus(FiniteDuration other) {
        return this.copy(this.time().$plus(other));
    }

    public Deadline $minus(FiniteDuration other) {
        return this.copy(this.time().$minus(other));
    }

    public FiniteDuration $minus(Deadline other) {
        return this.time().$minus(other.time());
    }

    public FiniteDuration timeLeft() {
        return this.$minus(Deadline$.MODULE$.now());
    }

    public boolean hasTimeLeft() {
        return !this.isOverdue();
    }

    public boolean isOverdue() {
        return this.time().toNanos() - System.nanoTime() < 0L;
    }

    @Override
    public int compare(Deadline other) {
        return this.time().compare(other.time());
    }

    public Deadline copy(FiniteDuration time) {
        return new Deadline(time);
    }

    public FiniteDuration copy$default$1() {
        return this.time();
    }

    @Override
    public String productPrefix() {
        return "Deadline";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.time();
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
        return x$1 instanceof Deadline;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "time";
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
                if (!(x$1 instanceof Deadline)) break block3;
                Deadline deadline = (Deadline)x$1;
                FiniteDuration finiteDuration = this.time();
                FiniteDuration finiteDuration2 = deadline.time();
                if ((finiteDuration != null ? !((Object)finiteDuration).equals(finiteDuration2) : finiteDuration2 != null) || !deadline.canEqual(this)) break block3;
            }
            return true;
        }
        return false;
    }

    public Deadline(FiniteDuration time) {
        this.time = time;
    }
}

