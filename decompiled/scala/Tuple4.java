/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product4;
import scala.Tuple4$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u00055h\u0001B\u000f\u001f\u0005\u0006B\u0001\"\u0014\u0001\u0003\u0016\u0004%\tA\u0014\u0005\t\u001f\u0002\u0011\t\u0012)A\u0005U!A\u0001\u000b\u0001BK\u0002\u0013\u0005\u0011\u000b\u0003\u0005S\u0001\tE\t\u0015!\u00036\u0011!\u0019\u0006A!f\u0001\n\u0003!\u0006\u0002C+\u0001\u0005#\u0005\u000b\u0011\u0002\u001d\t\u0011Y\u0003!Q3A\u0005\u0002]C\u0001\u0002\u0017\u0001\u0003\u0012\u0003\u0006Ia\u000f\u0005\u00063\u0002!\tA\u0017\u0005\u0006A\u0002!\t%\u0019\u0005\bU\u0002\t\t\u0011\"\u0001l\u0011\u001dQ\b!%A\u0005\u0002mD\u0011\"a\u0006\u0001#\u0003%\t!!\u0007\t\u0013\u0005\u001d\u0002!%A\u0005\u0002\u0005%\u0002\"CA\u001c\u0001E\u0005I\u0011AA\u001d\u0011%\t9\u0005AA\u0001\n\u0003\nI\u0005C\u0005\u0002Z\u0001\t\t\u0011\"\u0011\u0002\\!I\u0011\u0011\u000e\u0001\u0002\u0002\u0013\u0005\u00111\u000e\u0005\n\u0003o\u0002\u0011\u0011!C!\u0003sB\u0011\"a!\u0001\u0003\u0003%\t%!\"\t\u0013\u0005\u001d\u0005!!A\u0005B\u0005%u!CAG=\u0005\u0005\t\u0012AAH\r!ib$!A\t\u0002\u0005E\u0005BB-\u0018\t\u0003\ti\n\u0003\u0005a/\u0005\u0005IQIAP\u0011%\t\tkFA\u0001\n\u0003\u000b\u0019\u000bC\u0005\u0002B^\t\t\u0011\"!\u0002D\"I\u00111]\f\u0002\u0002\u0013%\u0011Q\u001d\u0002\u0007)V\u0004H.\u001a\u001b\u000b\u0003}\tQa]2bY\u0006\u001c\u0001!F\u0003#YYJDhE\u0003\u0001G\u001dr\u0014\t\u0005\u0002%K5\ta$\u0003\u0002'=\t1\u0011I\\=SK\u001a\u0004b\u0001\n\u0015+kaZ\u0014BA\u0015\u001f\u0005!\u0001&o\u001c3vGR$\u0004CA\u0016-\u0019\u0001!a!\f\u0001\u0005\u0006\u0004q#A\u0001+2#\ty#\u0007\u0005\u0002%a%\u0011\u0011G\b\u0002\b\u001d>$\b.\u001b8h!\t!3'\u0003\u00025=\t\u0019\u0011I\\=\u0011\u0005-2DAB\u001c\u0001\t\u000b\u0007aF\u0001\u0002UeA\u00111&\u000f\u0003\u0007u\u0001!)\u0019\u0001\u0018\u0003\u0005Q\u001b\u0004CA\u0016=\t\u0019i\u0004\u0001\"b\u0001]\t\u0011A\u000b\u000e\t\u0003I}J!\u0001\u0011\u0010\u0003\u000fA\u0013x\u000eZ;diB\u0011!I\u0013\b\u0003\u0007\"s!\u0001R$\u000e\u0003\u0015S!A\u0012\u0011\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0012BA%\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0013'\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005%s\u0012AA02+\u0005Q\u0013aA02A\u0005\u0011qLM\u000b\u0002k\u0005\u0019qL\r\u0011\u0002\u0005}\u001bT#\u0001\u001d\u0002\u0007}\u001b\u0004%\u0001\u0002`iU\t1(A\u0002`i\u0001\na\u0001P5oSRtD#B.];z{\u0006C\u0002\u0013\u0001UUB4\bC\u0003N\u0013\u0001\u0007!\u0006C\u0003Q\u0013\u0001\u0007Q\u0007C\u0003T\u0013\u0001\u0007\u0001\bC\u0003W\u0013\u0001\u00071(\u0001\u0005u_N#(/\u001b8h)\u0005\u0011\u0007CA2h\u001d\t!W\r\u0005\u0002E=%\u0011aMH\u0001\u0007!J,G-\u001a4\n\u0005!L'AB*ue&twM\u0003\u0002g=\u0005!1m\u001c9z+\u0015aw.]:v)\u0015igo\u001e=z!\u0019!\u0003A\u001c9siB\u00111f\u001c\u0003\u0006[-\u0011\rA\f\t\u0003WE$QaN\u0006C\u00029\u0002\"aK:\u0005\u000biZ!\u0019\u0001\u0018\u0011\u0005-*H!B\u001f\f\u0005\u0004q\u0003bB'\f!\u0003\u0005\rA\u001c\u0005\b!.\u0001\n\u00111\u0001q\u0011\u001d\u00196\u0002%AA\u0002IDqAV\u0006\u0011\u0002\u0003\u0007A/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0013q\fy!!\u0005\u0002\u0014\u0005UQ#A?+\u0005)r8&A@\u0011\t\u0005\u0005\u00111B\u0007\u0003\u0003\u0007QA!!\u0002\u0002\b\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u0013q\u0012AC1o]>$\u0018\r^5p]&!\u0011QBA\u0002\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006[1\u0011\rA\f\u0003\u0006o1\u0011\rA\f\u0003\u0006u1\u0011\rA\f\u0003\u0006{1\u0011\rAL\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+)\tY\"a\b\u0002\"\u0005\r\u0012QE\u000b\u0003\u0003;Q#!\u000e@\u0005\u000b5j!\u0019\u0001\u0018\u0005\u000b]j!\u0019\u0001\u0018\u0005\u000bij!\u0019\u0001\u0018\u0005\u000buj!\u0019\u0001\u0018\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gUQ\u00111FA\u0018\u0003c\t\u0019$!\u000e\u0016\u0005\u00055\"F\u0001\u001d\u007f\t\u0015icB1\u0001/\t\u00159dB1\u0001/\t\u0015QdB1\u0001/\t\u0015idB1\u0001/\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"\"a\u000f\u0002@\u0005\u0005\u00131IA#+\t\tiD\u000b\u0002<}\u0012)Qf\u0004b\u0001]\u0011)qg\u0004b\u0001]\u0011)!h\u0004b\u0001]\u0011)Qh\u0004b\u0001]\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a\u0013\u0011\t\u00055\u0013qK\u0007\u0003\u0003\u001fRA!!\u0015\u0002T\u0005!A.\u00198h\u0015\t\t)&\u0001\u0003kCZ\f\u0017b\u00015\u0002P\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002^A)\u0011qLA3e5\u0011\u0011\u0011\r\u0006\u0004\u0003Gr\u0012AC2pY2,7\r^5p]&!\u0011qMA1\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u00055\u00141\u000f\t\u0004I\u0005=\u0014bAA9=\t9!i\\8mK\u0006t\u0007\u0002CA;%\u0005\u0005\t\u0019\u0001\u001a\u0002\u0007a$\u0013'\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA&\u0003wB\u0011\"!\u001e\u0014\u0003\u0003\u0005\r!! \u0011\u0007\u0011\ny(C\u0002\u0002\u0002z\u00111!\u00138u\u0003!A\u0017m\u001d5D_\u0012,GCAA?\u0003\u0019)\u0017/^1mgR!\u0011QNAF\u0011!\t)(FA\u0001\u0002\u0004\u0011\u0014A\u0002+va2,G\u0007\u0005\u0002%/M!qcIAJ!\u0011\t)*a'\u000e\u0005\u0005]%\u0002BAM\u0003'\n!![8\n\u0007-\u000b9\n\u0006\u0002\u0002\u0010R\u0011\u00111J\u0001\u0006CB\u0004H._\u000b\u000b\u0003K\u000bY+a,\u00024\u0006]FCCAT\u0003s\u000bY,!0\u0002@BQA\u0005AAU\u0003[\u000b\t,!.\u0011\u0007-\nY\u000bB\u0003.5\t\u0007a\u0006E\u0002,\u0003_#Qa\u000e\u000eC\u00029\u00022aKAZ\t\u0015Q$D1\u0001/!\rY\u0013q\u0017\u0003\u0006{i\u0011\rA\f\u0005\u0007\u001bj\u0001\r!!+\t\rAS\u0002\u0019AAW\u0011\u0019\u0019&\u00041\u0001\u00022\"1aK\u0007a\u0001\u0003k\u000bq!\u001e8baBd\u00170\u0006\u0006\u0002F\u0006E\u0017Q[Am\u0003;$B!a2\u0002`B)A%!3\u0002N&\u0019\u00111\u001a\u0010\u0003\r=\u0003H/[8o!)!\u0003!a4\u0002T\u0006]\u00171\u001c\t\u0004W\u0005EG!B\u0017\u001c\u0005\u0004q\u0003cA\u0016\u0002V\u0012)qg\u0007b\u0001]A\u00191&!7\u0005\u000biZ\"\u0019\u0001\u0018\u0011\u0007-\ni\u000eB\u0003>7\t\u0007a\u0006C\u0005\u0002bn\t\t\u00111\u0001\u0002N\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\b\u0003BA'\u0003SLA!a;\u0002P\t1qJ\u00196fGR\u0004")
public final class Tuple4<T1, T2, T3, T4>
implements Product4<T1, T2, T3, T4>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;

    public static <T1, T2, T3, T4> Option<Tuple4<T1, T2, T3, T4>> unapply(Tuple4<T1, T2, T3, T4> x$0) {
        return Tuple4$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> apply(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new Tuple4<T1, T2, T3, T4>(_1, _2, _3, _4);
    }

    @Override
    public int productArity() {
        return Product4.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product4.productElement$(this, n);
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

    @Override
    public T4 _4() {
        return this._4;
    }

    public String toString() {
        return new StringBuilder(5).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(")").toString();
    }

    public <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> copy(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new Tuple4<T1, T2, T3, T4>(_1, _2, _3, _4);
    }

    public <T1, T2, T3, T4> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4> T4 copy$default$4() {
        return this._4();
    }

    @Override
    public String productPrefix() {
        return "Tuple4";
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
        return x$1 instanceof Tuple4;
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
            case 3: {
                return "_4";
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
                if (!(x$1 instanceof Tuple4)) break block3;
                Tuple4 tuple4 = (Tuple4)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple4._1()) || !BoxesRunTime.equals(this._2(), tuple4._2()) || !BoxesRunTime.equals(this._3(), tuple4._3()) || !BoxesRunTime.equals(this._4(), tuple4._4())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }
}

