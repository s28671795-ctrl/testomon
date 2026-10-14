/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product5;
import scala.Tuple5$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t-b\u0001\u0002\u0011\"\u0005\u0012B\u0001b\u0015\u0001\u0003\u0016\u0004%\t\u0001\u0016\u0005\t+\u0002\u0011\t\u0012)A\u0005[!Aa\u000b\u0001BK\u0002\u0013\u0005q\u000b\u0003\u0005Y\u0001\tE\t\u0015!\u00039\u0011!I\u0006A!f\u0001\n\u0003Q\u0006\u0002C.\u0001\u0005#\u0005\u000b\u0011B\u001e\t\u0011q\u0003!Q3A\u0005\u0002uC\u0001B\u0018\u0001\u0003\u0012\u0003\u0006IA\u0010\u0005\t?\u0002\u0011)\u001a!C\u0001A\"A\u0011\r\u0001B\tB\u0003%\u0011\tC\u0003c\u0001\u0011\u00051\rC\u0003k\u0001\u0011\u00053\u000eC\u0004u\u0001\u0005\u0005I\u0011A;\t\u0013\u0005=\u0001!%A\u0005\u0002\u0005E\u0001\"CA\u001a\u0001E\u0005I\u0011AA\u001b\u0011%\t)\u0005AI\u0001\n\u0003\t9\u0005C\u0005\u0002X\u0001\t\n\u0011\"\u0001\u0002Z!I\u0011\u0011\u000e\u0001\u0012\u0002\u0013\u0005\u00111\u000e\u0005\n\u0003w\u0002\u0011\u0011!C!\u0003{B\u0011\"!$\u0001\u0003\u0003%\t%a$\t\u0013\u0005u\u0005!!A\u0005\u0002\u0005}\u0005\"CAV\u0001\u0005\u0005I\u0011IAW\u0011%\t9\fAA\u0001\n\u0003\nI\fC\u0005\u0002<\u0002\t\t\u0011\"\u0011\u0002>\u001eI\u0011\u0011Y\u0011\u0002\u0002#\u0005\u00111\u0019\u0004\tA\u0005\n\t\u0011#\u0001\u0002F\"1!M\u0007C\u0001\u0003#D\u0001B\u001b\u000e\u0002\u0002\u0013\u0015\u00131\u001b\u0005\n\u0003+T\u0012\u0011!CA\u0003/D\u0011\"a?\u001b\u0003\u0003%\t)!@\t\u0013\t\u0005\"$!A\u0005\n\t\r\"A\u0002+va2,WGC\u0001#\u0003\u0015\u00198-\u00197b\u0007\u0001)b!J\u0018:y}\u00125#\u0002\u0001'U\u0011;\u0005CA\u0014)\u001b\u0005\t\u0013BA\u0015\"\u0005\u0019\te.\u001f*fMB9qeK\u00179wy\n\u0015B\u0001\u0017\"\u0005!\u0001&o\u001c3vGR,\u0004C\u0001\u00180\u0019\u0001!a\u0001\r\u0001\u0005\u0006\u0004\t$A\u0001+2#\t\u0011T\u0007\u0005\u0002(g%\u0011A'\t\u0002\b\u001d>$\b.\u001b8h!\t9c'\u0003\u00028C\t\u0019\u0011I\\=\u0011\u00059JDA\u0002\u001e\u0001\t\u000b\u0007\u0011G\u0001\u0002UeA\u0011a\u0006\u0010\u0003\u0007{\u0001!)\u0019A\u0019\u0003\u0005Q\u001b\u0004C\u0001\u0018@\t\u0019\u0001\u0005\u0001\"b\u0001c\t\u0011A\u000b\u000e\t\u0003]\t#aa\u0011\u0001\u0005\u0006\u0004\t$A\u0001+6!\t9S)\u0003\u0002GC\t9\u0001K]8ek\u000e$\bC\u0001%Q\u001d\tIeJ\u0004\u0002K\u001b6\t1J\u0003\u0002MG\u00051AH]8pizJ\u0011AI\u0005\u0003\u001f\u0006\nq\u0001]1dW\u0006<W-\u0003\u0002R%\na1+\u001a:jC2L'0\u00192mK*\u0011q*I\u0001\u0003?F*\u0012!L\u0001\u0004?F\u0002\u0013AA03+\u0005A\u0014aA03A\u0005\u0011qlM\u000b\u0002w\u0005\u0019ql\r\u0011\u0002\u0005}#T#\u0001 \u0002\u0007}#\u0004%\u0001\u0002`kU\t\u0011)A\u0002`k\u0001\na\u0001P5oSRtDC\u00023fM\u001eD\u0017\u000eE\u0004(\u00015B4HP!\t\u000bM[\u0001\u0019A\u0017\t\u000bY[\u0001\u0019\u0001\u001d\t\u000be[\u0001\u0019A\u001e\t\u000bq[\u0001\u0019\u0001 \t\u000b}[\u0001\u0019A!\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\u001c\t\u0003[Ft!A\\8\u0011\u0005)\u000b\u0013B\u00019\"\u0003\u0019\u0001&/\u001a3fM&\u0011!o\u001d\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005A\f\u0013\u0001B2paf,rA^=|{~\f\u0019\u0001F\u0006x\u0003\u000b\t9!!\u0003\u0002\f\u00055\u0001\u0003C\u0014\u0001qjdh0!\u0001\u0011\u00059JH!\u0002\u0019\u000e\u0005\u0004\t\u0004C\u0001\u0018|\t\u0015QTB1\u00012!\tqS\u0010B\u0003>\u001b\t\u0007\u0011\u0007\u0005\u0002/\u007f\u0012)\u0001)\u0004b\u0001cA\u0019a&a\u0001\u0005\u000b\rk!\u0019A\u0019\t\u000fMk\u0001\u0013!a\u0001q\"9a+\u0004I\u0001\u0002\u0004Q\bbB-\u000e!\u0003\u0005\r\u0001 \u0005\b96\u0001\n\u00111\u0001\u007f\u0011!yV\u0002%AA\u0002\u0005\u0005\u0011AD2paf$C-\u001a4bk2$H%M\u000b\r\u0003'\tI#a\u000b\u0002.\u0005=\u0012\u0011G\u000b\u0003\u0003+Q3!LA\fW\t\tI\u0002\u0005\u0003\u0002\u001c\u0005\u0015RBAA\u000f\u0015\u0011\ty\"!\t\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0012C\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u001d\u0012Q\u0004\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!\u0002\u0019\u000f\u0005\u0004\tD!\u0002\u001e\u000f\u0005\u0004\tD!B\u001f\u000f\u0005\u0004\tD!\u0002!\u000f\u0005\u0004\tD!B\"\u000f\u0005\u0004\t\u0014AD2paf$C-\u001a4bk2$HEM\u000b\r\u0003o\tY$!\u0010\u0002@\u0005\u0005\u00131I\u000b\u0003\u0003sQ3\u0001OA\f\t\u0015\u0001tB1\u00012\t\u0015QtB1\u00012\t\u0015itB1\u00012\t\u0015\u0001uB1\u00012\t\u0015\u0019uB1\u00012\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*B\"!\u0013\u0002N\u0005=\u0013\u0011KA*\u0003+*\"!a\u0013+\u0007m\n9\u0002B\u00031!\t\u0007\u0011\u0007B\u0003;!\t\u0007\u0011\u0007B\u0003>!\t\u0007\u0011\u0007B\u0003A!\t\u0007\u0011\u0007B\u0003D!\t\u0007\u0011'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0019\u0005m\u0013qLA1\u0003G\n)'a\u001a\u0016\u0005\u0005u#f\u0001 \u0002\u0018\u0011)\u0001'\u0005b\u0001c\u0011)!(\u0005b\u0001c\u0011)Q(\u0005b\u0001c\u0011)\u0001)\u0005b\u0001c\u0011)1)\u0005b\u0001c\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*T\u0003DA7\u0003c\n\u0019(!\u001e\u0002x\u0005eTCAA8U\r\t\u0015q\u0003\u0003\u0006aI\u0011\r!\r\u0003\u0006uI\u0011\r!\r\u0003\u0006{I\u0011\r!\r\u0003\u0006\u0001J\u0011\r!\r\u0003\u0006\u0007J\u0011\r!M\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\u0004\u0003BAA\u0003\u0017k!!a!\u000b\t\u0005\u0015\u0015qQ\u0001\u0005Y\u0006twM\u0003\u0002\u0002\n\u0006!!.\u0019<b\u0013\r\u0011\u00181Q\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u0013\t\u0006\u0003'\u000bI*N\u0007\u0003\u0003+S1!a&\"\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u00037\u000b)J\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAQ\u0003O\u00032aJAR\u0013\r\t)+\t\u0002\b\u0005>|G.Z1o\u0011!\tI+FA\u0001\u0002\u0004)\u0014a\u0001=%c\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ty(a,\t\u0013\u0005%f#!AA\u0002\u0005E\u0006cA\u0014\u00024&\u0019\u0011QW\u0011\u0003\u0007%sG/\u0001\u0005iCND7i\u001c3f)\t\t\t,\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003C\u000by\f\u0003\u0005\u0002*b\t\t\u00111\u00016\u0003\u0019!V\u000f\u001d7fkA\u0011qEG\n\u00055\u0019\n9\r\u0005\u0003\u0002J\u0006=WBAAf\u0015\u0011\ti-a\"\u0002\u0005%|\u0017bA)\u0002LR\u0011\u00111\u0019\u000b\u0003\u0003\u007f\nQ!\u00199qYf,B\"!7\u0002`\u0006\r\u0018q]Av\u0003_$B\"a7\u0002r\u0006M\u0018Q_A|\u0003s\u0004Bb\n\u0001\u0002^\u0006\u0005\u0018Q]Au\u0003[\u00042ALAp\t\u0015\u0001TD1\u00012!\rq\u00131\u001d\u0003\u0006uu\u0011\r!\r\t\u0004]\u0005\u001dH!B\u001f\u001e\u0005\u0004\t\u0004c\u0001\u0018\u0002l\u0012)\u0001)\bb\u0001cA\u0019a&a<\u0005\u000b\rk\"\u0019A\u0019\t\rMk\u0002\u0019AAo\u0011\u00191V\u00041\u0001\u0002b\"1\u0011,\ba\u0001\u0003KDa\u0001X\u000fA\u0002\u0005%\bBB0\u001e\u0001\u0004\ti/A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0019\u0005}(1\u0002B\b\u0005'\u00119Ba\u0007\u0015\t\t\u0005!Q\u0004\t\u0006O\t\r!qA\u0005\u0004\u0005\u000b\t#AB(qi&|g\u000e\u0005\u0007(\u0001\t%!Q\u0002B\t\u0005+\u0011I\u0002E\u0002/\u0005\u0017!Q\u0001\r\u0010C\u0002E\u00022A\fB\b\t\u0015QdD1\u00012!\rq#1\u0003\u0003\u0006{y\u0011\r!\r\t\u0004]\t]A!\u0002!\u001f\u0005\u0004\t\u0004c\u0001\u0018\u0003\u001c\u0011)1I\bb\u0001c!I!q\u0004\u0010\u0002\u0002\u0003\u0007!qA\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0013!\u0011\t\tIa\n\n\t\t%\u00121\u0011\u0002\u0007\u001f\nTWm\u0019;")
public final class Tuple5<T1, T2, T3, T4, T5>
implements Product5<T1, T2, T3, T4, T5>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;

    public static <T1, T2, T3, T4, T5> Option<Tuple5<T1, T2, T3, T4, T5>> unapply(Tuple5<T1, T2, T3, T4, T5> x$0) {
        return Tuple5$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new Tuple5<T1, T2, T3, T4, T5>(_1, _2, _3, _4, _5);
    }

    @Override
    public int productArity() {
        return Product5.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product5.productElement$(this, n);
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

    @Override
    public T5 _5() {
        return this._5;
    }

    public String toString() {
        return new StringBuilder(6).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new Tuple5<T1, T2, T3, T4, T5>(_1, _2, _3, _4, _5);
    }

    public <T1, T2, T3, T4, T5> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5> T5 copy$default$5() {
        return this._5();
    }

    @Override
    public String productPrefix() {
        return "Tuple5";
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
        return x$1 instanceof Tuple5;
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
            case 4: {
                return "_5";
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
                if (!(x$1 instanceof Tuple5)) break block3;
                Tuple5 tuple5 = (Tuple5)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple5._1()) || !BoxesRunTime.equals(this._2(), tuple5._2()) || !BoxesRunTime.equals(this._3(), tuple5._3()) || !BoxesRunTime.equals(this._4(), tuple5._4()) || !BoxesRunTime.equals(this._5(), tuple5._5())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }
}

