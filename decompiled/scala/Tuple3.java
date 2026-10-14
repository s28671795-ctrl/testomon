/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product3;
import scala.Tuple3$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005Mf\u0001\u0002\u000e\u001c\u0005zA\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t\u0013\u0002\u0011\t\u0012)A\u0005O!A!\n\u0001BK\u0002\u0013\u00051\n\u0003\u0005M\u0001\tE\t\u0015!\u00033\u0011!i\u0005A!f\u0001\n\u0003q\u0005\u0002C(\u0001\u0005#\u0005\u000b\u0011B\u001b\t\u000bA\u0003A\u0011A)\t\u000bY\u0003A\u0011I,\t\u000f\u0001\u0004\u0011\u0011!C\u0001C\"9Q\u000eAI\u0001\n\u0003q\u0007bB?\u0001#\u0003%\tA \u0005\n\u0003\u0013\u0001\u0011\u0013!C\u0001\u0003\u0017A\u0011\"a\u0006\u0001\u0003\u0003%\t%!\u0007\t\u0013\u0005%\u0002!!A\u0005B\u0005-\u0002\"CA\u001d\u0001\u0005\u0005I\u0011AA\u001e\u0011%\t9\u0005AA\u0001\n\u0003\nI\u0005C\u0005\u0002T\u0001\t\t\u0011\"\u0011\u0002V!I\u0011q\u000b\u0001\u0002\u0002\u0013\u0005\u0013\u0011L\u0004\n\u0003;Z\u0012\u0011!E\u0001\u0003?2\u0001BG\u000e\u0002\u0002#\u0005\u0011\u0011\r\u0005\u0007!R!\t!!\u001c\t\u0011Y#\u0012\u0011!C#\u0003_B\u0011\"!\u001d\u0015\u0003\u0003%\t)a\u001d\t\u0013\u0005-E#!A\u0005\u0002\u00065\u0005\"CAU)\u0005\u0005I\u0011BAV\u0005\u0019!V\u000f\u001d7fg)\tA$A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t}I3GN\n\u0006\u0001\u0001\"\u0003h\u000f\t\u0003C\tj\u0011aG\u0005\u0003Gm\u0011a!\u00118z%\u00164\u0007#B\u0011&OI*\u0014B\u0001\u0014\u001c\u0005!\u0001&o\u001c3vGR\u001c\u0004C\u0001\u0015*\u0019\u0001!aA\u000b\u0001\u0005\u0006\u0004Y#A\u0001+2#\tas\u0006\u0005\u0002\"[%\u0011af\u0007\u0002\b\u001d>$\b.\u001b8h!\t\t\u0003'\u0003\u000227\t\u0019\u0011I\\=\u0011\u0005!\u001aDA\u0002\u001b\u0001\t\u000b\u00071F\u0001\u0002UeA\u0011\u0001F\u000e\u0003\u0007o\u0001!)\u0019A\u0016\u0003\u0005Q\u001b\u0004CA\u0011:\u0013\tQ4DA\u0004Qe>$Wo\u0019;\u0011\u0005q\"eBA\u001fC\u001d\tq\u0014)D\u0001@\u0015\t\u0001U$\u0001\u0004=e>|GOP\u0005\u00029%\u00111iG\u0001\ba\u0006\u001c7.Y4f\u0013\t)eI\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002D7\u0005\u0011q,M\u000b\u0002O\u0005\u0019q,\r\u0011\u0002\u0005}\u0013T#\u0001\u001a\u0002\u0007}\u0013\u0004%\u0001\u0002`gU\tQ'A\u0002`g\u0001\na\u0001P5oSRtD\u0003\u0002*T)V\u0003R!\t\u0001(eUBQaR\u0004A\u0002\u001dBQAS\u0004A\u0002IBQ!T\u0004A\u0002U\n\u0001\u0002^8TiJLgn\u001a\u000b\u00021B\u0011\u0011,\u0018\b\u00035n\u0003\"AP\u000e\n\u0005q[\u0012A\u0002)sK\u0012,g-\u0003\u0002_?\n11\u000b\u001e:j]\u001eT!\u0001X\u000e\u0002\t\r|\u0007/_\u000b\u0005E\u0016<\u0017\u000e\u0006\u0003dU.d\u0007#B\u0011\u0001I\u001aD\u0007C\u0001\u0015f\t\u0015Q\u0013B1\u0001,!\tAs\rB\u00035\u0013\t\u00071\u0006\u0005\u0002)S\u0012)q'\u0003b\u0001W!9q)\u0003I\u0001\u0002\u0004!\u0007b\u0002&\n!\u0003\u0005\rA\u001a\u0005\b\u001b&\u0001\n\u00111\u0001i\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*Ba\u001c>|yV\t\u0001O\u000b\u0002(c.\n!\u000f\u0005\u0002tq6\tAO\u0003\u0002vm\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003on\t!\"\u00198o_R\fG/[8o\u0013\tIHOA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$QA\u000b\u0006C\u0002-\"Q\u0001\u000e\u0006C\u0002-\"Qa\u000e\u0006C\u0002-\nabY8qs\u0012\"WMZ1vYR$#'F\u0004\u0000\u0003\u0007\t)!a\u0002\u0016\u0005\u0005\u0005!F\u0001\u001ar\t\u0015Q3B1\u0001,\t\u0015!4B1\u0001,\t\u001594B1\u0001,\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0002\"!\u0004\u0002\u0012\u0005M\u0011QC\u000b\u0003\u0003\u001fQ#!N9\u0005\u000b)b!\u0019A\u0016\u0005\u000bQb!\u0019A\u0016\u0005\u000b]b!\u0019A\u0016\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tY\u0002\u0005\u0003\u0002\u001e\u0005\u001dRBAA\u0010\u0015\u0011\t\t#a\t\u0002\t1\fgn\u001a\u0006\u0003\u0003K\tAA[1wC&\u0019a,a\b\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\f\u0011\u000b\u0005=\u0012QG\u0018\u000e\u0005\u0005E\"bAA\u001a7\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005]\u0012\u0011\u0007\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002>\u0005\r\u0003cA\u0011\u0002@%\u0019\u0011\u0011I\u000e\u0003\u000f\t{w\u000e\\3b]\"A\u0011QI\b\u0002\u0002\u0003\u0007q&A\u0002yIE\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111DA&\u0011%\t)\u0005EA\u0001\u0002\u0004\ti\u0005E\u0002\"\u0003\u001fJ1!!\u0015\u001c\u0005\rIe\u000e^\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011QJ\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005u\u00121\f\u0005\t\u0003\u000b\u0012\u0012\u0011!a\u0001_\u00051A+\u001e9mKN\u0002\"!\t\u000b\u0014\tQ\u0001\u00131\r\t\u0005\u0003K\nY'\u0004\u0002\u0002h)!\u0011\u0011NA\u0012\u0003\tIw.C\u0002F\u0003O\"\"!a\u0018\u0015\u0005\u0005m\u0011!B1qa2LX\u0003CA;\u0003w\ny(a!\u0015\u0011\u0005]\u0014QQAD\u0003\u0013\u0003\u0002\"\t\u0001\u0002z\u0005u\u0014\u0011\u0011\t\u0004Q\u0005mD!\u0002\u0016\u0018\u0005\u0004Y\u0003c\u0001\u0015\u0002\u0000\u0011)Ag\u0006b\u0001WA\u0019\u0001&a!\u0005\u000b]:\"\u0019A\u0016\t\r\u001d;\u0002\u0019AA=\u0011\u0019Qu\u00031\u0001\u0002~!1Qj\u0006a\u0001\u0003\u0003\u000bq!\u001e8baBd\u00170\u0006\u0005\u0002\u0010\u0006m\u0015qTAR)\u0011\t\t*!*\u0011\u000b\u0005\n\u0019*a&\n\u0007\u0005U5D\u0001\u0004PaRLwN\u001c\t\tC\u0001\tI*!(\u0002\"B\u0019\u0001&a'\u0005\u000b)B\"\u0019A\u0016\u0011\u0007!\ny\nB\u000351\t\u00071\u0006E\u0002)\u0003G#Qa\u000e\rC\u0002-B\u0011\"a*\u0019\u0003\u0003\u0005\r!a&\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002.B!\u0011QDAX\u0013\u0011\t\t,a\b\u0003\r=\u0013'.Z2u\u0001")
public final class Tuple3<T1, T2, T3>
implements Product3<T1, T2, T3>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;

    public static <T1, T2, T3> Option<Tuple3<T1, T2, T3>> unapply(Tuple3<T1, T2, T3> x$0) {
        return Tuple3$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> apply(T1 _1, T2 _2, T3 _3) {
        return new Tuple3<T1, T2, T3>(_1, _2, _3);
    }

    @Override
    public int productArity() {
        return Product3.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product3.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    @Override
    public T2 _2() {
        return this._2;
    }

    @Override
    public T3 _3() {
        return this._3;
    }

    public String toString() {
        return new StringBuilder(4).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(")").toString();
    }

    public <T1, T2, T3> Tuple3<T1, T2, T3> copy(T1 _1, T2 _2, T3 _3) {
        return new Tuple3<T1, T2, T3>(_1, _2, _3);
    }

    public <T1, T2, T3> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3> T3 copy$default$3() {
        return this._3();
    }

    @Override
    public String productPrefix() {
        return "Tuple3";
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
        return x$1 instanceof Tuple3;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
            }
            case 1: {
                return "_2";
            }
            case 2: {
                return "_3";
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
                if (!(x$1 instanceof Tuple3)) break block3;
                Tuple3 tuple3 = (Tuple3)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple3._1()) || !BoxesRunTime.equals(this._2(), tuple3._2()) || !BoxesRunTime.equals(this._3(), tuple3._3())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }
}

