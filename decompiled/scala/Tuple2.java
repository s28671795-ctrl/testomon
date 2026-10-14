/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product2;
import scala.Tuple2$;
import scala.Tuple2$mcCC$sp;
import scala.Tuple2$mcCD$sp;
import scala.Tuple2$mcCI$sp;
import scala.Tuple2$mcCJ$sp;
import scala.Tuple2$mcCZ$sp;
import scala.Tuple2$mcDC$sp;
import scala.Tuple2$mcDD$sp;
import scala.Tuple2$mcDI$sp;
import scala.Tuple2$mcDJ$sp;
import scala.Tuple2$mcDZ$sp;
import scala.Tuple2$mcIC$sp;
import scala.Tuple2$mcID$sp;
import scala.Tuple2$mcII$sp;
import scala.Tuple2$mcIJ$sp;
import scala.Tuple2$mcIZ$sp;
import scala.Tuple2$mcJC$sp;
import scala.Tuple2$mcJD$sp;
import scala.Tuple2$mcJI$sp;
import scala.Tuple2$mcJJ$sp;
import scala.Tuple2$mcJZ$sp;
import scala.Tuple2$mcZC$sp;
import scala.Tuple2$mcZD$sp;
import scala.Tuple2$mcZI$sp;
import scala.Tuple2$mcZJ$sp;
import scala.Tuple2$mcZZ$sp;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\teb\u0001\u0002\r\u001a\u0005rA\u0001\u0002\u0019\u0001\u0003\u0016\u0004%\t!\u0019\u0005\tE\u0002\u0011\t\u0012)A\u0005K!A1\r\u0001BK\u0002\u0013\u0005A\r\u0003\u0005f\u0001\tE\t\u0015!\u0003I\u0011\u00151\u0007\u0001\"\u0001h\u0011\u0015Y\u0007\u0001\"\u0011m\u0011\u0015)\b\u0001\"\u0001w\u0011\u001dA\b!!A\u0005\u0002eD\u0011\"!\b\u0001#\u0003%\t!a\b\t\u0013\u0005M\u0003!%A\u0005\u0002\u0005U\u0003\"CA<\u0001\u0005\u0005I\u0011IA=\u0011%\tI\tAA\u0001\n\u0003\nY\tC\u0005\u0002\u001a\u0002\t\t\u0011\"\u0001\u0002\u001c\"I\u0011q\u0015\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0016\u0005\n\u0003g\u0003\u0011\u0011!C!\u0003kC\u0011\"a.\u0001\u0003\u0003%\t%!/\b\u0013\u0005u\u0016$!A\t\u0002\u0005}f\u0001\u0003\r\u001a\u0003\u0003E\t!!1\t\r\u0019\u0014B\u0011AAg\u0011!Y'#!A\u0005F\u0005=\u0007\"CAi%\u0005\u0005I\u0011QAj\u0011%\tiPEA\u0001\n\u0003\u000by\u0010C\u0005\u00030I\t\t\u0011\"\u0003\u00032\t1A+\u001e9mKJR\u0011AG\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rir%S\n\u0006\u0001y\u0011\u0013\u000b\u0016\t\u0003?\u0001j\u0011!G\u0005\u0003Ce\u0011a!\u00118z%\u00164\u0007\u0003B\u0010$K!K!\u0001J\r\u0003\u0011A\u0013x\u000eZ;diJ\u0002\"AJ\u0014\r\u0001\u0011I\u0001\u0006\u0001Q\u0001\u0002\u0013\u0015\r!\u000b\u0002\u0003)F\n\"AK\u0017\u0011\u0005}Y\u0013B\u0001\u0017\u001a\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\b\u0018\n\u0005=J\"aA!os\":q%\r\u001b9y\u0001#\u0005CA\u00103\u0013\t\u0019\u0014DA\u0006ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017\u0007\u0002\u00136m]r!a\b\u001c\n\u0005]J\u0012aA%oiF\"A%\u000f\u001e<\u001d\ty\"(\u0003\u0002<3\u0005!Aj\u001c8hc\u0011!SHP \u000f\u0005}q\u0014BA \u001a\u0003\u0019!u.\u001e2mKF\"A%\u0011\"D\u001d\ty\")\u0003\u0002D3\u0005!1\t[1sc\u0011!SIR$\u000f\u0005}1\u0015BA$\u001a\u0003\u001d\u0011un\u001c7fC:\u0004\"AJ%\u0005\u0013)\u0003\u0001\u0015!A\u0005\u0006\u0004I#A\u0001+3Q\u001dI\u0015\u0007T'O\u001fB\u000bD\u0001J\u001b7oE\"A%\u000f\u001e<c\u0011!SHP 2\t\u0011\n%iQ\u0019\u0005I\u00153u\t\u0005\u0002 %&\u00111+\u0007\u0002\b!J|G-^2u!\t)VL\u0004\u0002W7:\u0011qKW\u0007\u00021*\u0011\u0011lG\u0001\u0007yI|w\u000e\u001e \n\u0003iI!\u0001X\r\u0002\u000fA\f7m[1hK&\u0011al\u0018\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u00039f\t!aX\u0019\u0016\u0003\u0015\n1aX\u0019!\u0003\ty&'F\u0001I\u0003\ry&\u0007I\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007!L'\u000e\u0005\u0003 \u0001\u0015B\u0005\"\u00021\u0006\u0001\u0004)\u0003\"B2\u0006\u0001\u0004A\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00035\u0004\"A\u001c:\u000f\u0005=\u0004\bCA,\u001a\u0013\t\t\u0018$\u0001\u0004Qe\u0016$WMZ\u0005\u0003gR\u0014aa\u0015;sS:<'BA9\u001a\u0003\u0011\u0019x/\u00199\u0016\u0003]\u0004Ba\b\u0001IK\u0005!1m\u001c9z+\u0011QX0a\u0003\u0015\u000bm\fI\"a\u0007\u0011\u000b}\u0001A0!\u0003\u0011\u0005\u0019jH!\u0003\u0015\tA\u0003\u0005\tQ1\u0001*Q-i\u0018g`A\u0001\u0003\u0007\t)!a\u00022\t\u0011*dgN\u0019\u0005IeR4(\r\u0003%{yz\u0014\u0007\u0002\u0013B\u0005\u000e\u000bD\u0001J#G\u000fB\u0019a%a\u0003\u0005\u0013)C\u0001\u0015!A\u0001\u0006\u0004I\u0003&DA\u0006c\u0005=\u0011\u0011CA\n\u0003+\t9\"\r\u0003%kY:\u0014\u0007\u0002\u0013:um\nD\u0001J\u001f?\u007fE\"A%\u0011\"Dc\u0011!SIR$\t\u000f\u0001D\u0001\u0013!a\u0001y\"A1\r\u0003I\u0001\u0002\u0004\tI!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\r\u0005\u0005\u0012qGA#+\t\t\u0019CK\u0002&\u0003KY#!a\n\u0011\t\u0005%\u00121G\u0007\u0003\u0003WQA!!\f\u00020\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003cI\u0012AC1o]>$\u0018\r^5p]&!\u0011QGA\u0016\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\nQ%\u0001\u000b\u0011!AC\u0002%BS\"a\u000e2\u0003w\ti$a\u0010\u0002B\u0005\r\u0013\u0007\u0002\u00136m]\nD\u0001J\u001d;wE\"A%\u0010 @c\u0011!\u0013IQ\"2\t\u0011*ei\u0012\u0003\n\u0015&\u0001\u000b\u0011!AC\u0002%BS\"!\u00122\u0003\u0013\nY%!\u0014\u0002P\u0005E\u0013\u0007\u0002\u00136m]\nD\u0001J\u001d;wE\"A%\u0010 @c\u0011!\u0013IQ\"2\t\u0011*eiR\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0019\t9&a\u0017\u0002jU\u0011\u0011\u0011\f\u0016\u0004\u0011\u0006\u0015B!\u0003\u0015\u000bA\u0003\u0005\tQ1\u0001*Q5\tY&MA0\u0003C\n\u0019'!\u001a\u0002hE\"A%\u000e\u001c8c\u0011!\u0013HO\u001e2\t\u0011jdhP\u0019\u0005I\u0005\u00135)\r\u0003%\u000b\u001a;E!\u0003&\u000bA\u0003\u0005\tQ1\u0001*Q5\tI'MA7\u0003_\n\t(a\u001d\u0002vE\"A%\u000e\u001c8c\u0011!\u0013HO\u001e2\t\u0011jdhP\u0019\u0005I\u0005\u00135)\r\u0003%\u000b\u001a;\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002|A!\u0011QPAD\u001b\t\tyH\u0003\u0003\u0002\u0002\u0006\r\u0015\u0001\u00027b]\u001eT!!!\"\u0002\t)\fg/Y\u0005\u0004g\u0006}\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u00055\u0005#BAH\u0003+kSBAAI\u0015\r\t\u0019*G\u0001\u000bG>dG.Z2uS>t\u0017\u0002BAL\u0003#\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011QTAR!\ry\u0012qT\u0005\u0004\u0003CK\"a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003Kk\u0011\u0011!a\u0001[\u0005\u0019\u0001\u0010J\u0019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003w\nY\u000bC\u0005\u0002&:\t\t\u00111\u0001\u0002.B\u0019q$a,\n\u0007\u0005E\u0016DA\u0002J]R\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003[\u000ba!Z9vC2\u001cH\u0003BAO\u0003wC\u0001\"!*\u0011\u0003\u0003\u0005\r!L\u0001\u0007)V\u0004H.\u001a\u001a\u0011\u0005}\u00112\u0003\u0002\n\u001f\u0003\u0007\u0004B!!2\u0002L6\u0011\u0011q\u0019\u0006\u0005\u0003\u0013\f\u0019)\u0001\u0002j_&\u0019a,a2\u0015\u0005\u0005}FCAA>\u0003\u0015\t\u0007\u000f\u001d7z+\u0019\t).a7\u0002lR1\u0011q[A}\u0003w\u0004ba\b\u0001\u0002Z\u0006%\bc\u0001\u0014\u0002\\\u0012I\u0001&\u0006Q\u0001\u0002\u0003\u0015\r!\u000b\u0015\u000e\u00037\f\u0014q\\Aq\u0003G\f)/a:2\t\u0011*dgN\u0019\u0005IeR4(\r\u0003%{yz\u0014\u0007\u0002\u0013B\u0005\u000e\u000bD\u0001J#G\u000fB\u0019a%a;\u0005\u0013)+\u0002\u0015!A\u0001\u0006\u0004I\u0003&DAvc\u0005=\u0018\u0011_Az\u0003k\f90\r\u0003%kY:\u0014\u0007\u0002\u0013:um\nD\u0001J\u001f?\u007fE\"A%\u0011\"Dc\u0011!SIR$\t\r\u0001,\u0002\u0019AAm\u0011\u0019\u0019W\u00031\u0001\u0002j\u00069QO\\1qa2LXC\u0002B\u0001\u0005\u001b\u0011i\u0002\u0006\u0003\u0003\u0004\t-\u0002#B\u0010\u0003\u0006\t%\u0011b\u0001B\u00043\t1q\n\u001d;j_:\u0004ba\b\u0001\u0003\f\tm\u0001c\u0001\u0014\u0003\u000e\u0011I\u0001F\u0006Q\u0001\u0002\u0003\u0015\r!\u000b\u0015\u000e\u0005\u001b\t$\u0011\u0003B\n\u0005+\u00119B!\u00072\t\u0011*dgN\u0019\u0005IeR4(\r\u0003%{yz\u0014\u0007\u0002\u0013B\u0005\u000e\u000bD\u0001J#G\u000fB\u0019aE!\b\u0005\u0013)3\u0002\u0015!A\u0001\u0006\u0004I\u0003&\u0004B\u000fc\t\u0005\"1\u0005B\u0013\u0005O\u0011I#\r\u0003%kY:\u0014\u0007\u0002\u0013:um\nD\u0001J\u001f?\u007fE\"A%\u0011\"Dc\u0011!SIR$\t\u0013\t5b#!AA\u0002\t%\u0011a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011!1\u0007\t\u0005\u0003{\u0012)$\u0003\u0003\u00038\u0005}$AB(cU\u0016\u001cG\u000f")
public class Tuple2<T1, T2>
implements Product2<T1, T2>,
Serializable {
    public final T1 _1;
    public final T2 _2;

    public static <T1, T2> Option<Tuple2<T1, T2>> unapply(Tuple2<T1, T2> x$0) {
        return Tuple2$.MODULE$.unapply(x$0);
    }

    public static <T1, T2> Tuple2<T1, T2> apply(T1 _1, T2 _2) {
        return new Tuple2<T1, T2>(_1, _2);
    }

    @Override
    public int productArity() {
        return Product2.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product2.productElement$(this, n);
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

    public String toString() {
        return new StringBuilder(3).append("(").append(this._1()).append(",").append(this._2()).append(")").toString();
    }

    public Tuple2<T2, T1> swap() {
        return new Tuple2<T2, T1>(this._2(), this._1());
    }

    public <T1, T2> Tuple2<T1, T2> copy(T1 _1, T2 _2) {
        return new Tuple2<T1, T2>(_1, _2);
    }

    public <T1, T2> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2> T2 copy$default$2() {
        return this._2();
    }

    @Override
    public String productPrefix() {
        return "Tuple2";
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
        return x$1 instanceof Tuple2;
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
                if (!(x$1 instanceof Tuple2)) break block3;
                Tuple2 tuple2 = (Tuple2)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple2._1()) || !BoxesRunTime.equals(this._2(), tuple2._2())) break block3;
            }
            return true;
        }
        return false;
    }

    public boolean _1$mcZ$sp() {
        return BoxesRunTime.unboxToBoolean(this._1());
    }

    public char _1$mcC$sp() {
        return BoxesRunTime.unboxToChar(this._1());
    }

    @Override
    public double _1$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this._1());
    }

    @Override
    public int _1$mcI$sp() {
        return BoxesRunTime.unboxToInt(this._1());
    }

    @Override
    public long _1$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this._1());
    }

    public boolean _2$mcZ$sp() {
        return BoxesRunTime.unboxToBoolean(this._2());
    }

    public char _2$mcC$sp() {
        return BoxesRunTime.unboxToChar(this._2());
    }

    @Override
    public double _2$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this._2());
    }

    @Override
    public int _2$mcI$sp() {
        return BoxesRunTime.unboxToInt(this._2());
    }

    @Override
    public long _2$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this._2());
    }

    public Tuple2<Object, Object> swap$mcZZ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcZC$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcZD$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcZI$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcZJ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcCZ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcCC$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcCD$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcCI$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcCJ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcDZ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcDC$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcDD$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcDI$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcDJ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcIZ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcIC$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcID$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcII$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcIJ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcJZ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcJC$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcJD$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcJI$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> swap$mcJJ$sp() {
        return this.swap();
    }

    public Tuple2<Object, Object> copy$mZZc$sp(boolean _1, boolean _2) {
        return new Tuple2$mcZZ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mZCc$sp(boolean _1, char _2) {
        return new Tuple2$mcZC$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mZDc$sp(boolean _1, double _2) {
        return new Tuple2$mcZD$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mZIc$sp(boolean _1, int _2) {
        return new Tuple2$mcZI$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mZJc$sp(boolean _1, long _2) {
        return new Tuple2$mcZJ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mCZc$sp(char _1, boolean _2) {
        return new Tuple2$mcCZ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mCCc$sp(char _1, char _2) {
        return new Tuple2$mcCC$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mCDc$sp(char _1, double _2) {
        return new Tuple2$mcCD$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mCIc$sp(char _1, int _2) {
        return new Tuple2$mcCI$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mCJc$sp(char _1, long _2) {
        return new Tuple2$mcCJ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mDZc$sp(double _1, boolean _2) {
        return new Tuple2$mcDZ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mDCc$sp(double _1, char _2) {
        return new Tuple2$mcDC$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mDDc$sp(double _1, double _2) {
        return new Tuple2$mcDD$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mDIc$sp(double _1, int _2) {
        return new Tuple2$mcDI$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mDJc$sp(double _1, long _2) {
        return new Tuple2$mcDJ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mIZc$sp(int _1, boolean _2) {
        return new Tuple2$mcIZ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mICc$sp(int _1, char _2) {
        return new Tuple2$mcIC$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mIDc$sp(int _1, double _2) {
        return new Tuple2$mcID$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mIIc$sp(int _1, int _2) {
        return new Tuple2$mcII$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mIJc$sp(int _1, long _2) {
        return new Tuple2$mcIJ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mJZc$sp(long _1, boolean _2) {
        return new Tuple2$mcJZ$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mJCc$sp(long _1, char _2) {
        return new Tuple2$mcJC$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mJDc$sp(long _1, double _2) {
        return new Tuple2$mcJD$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mJIc$sp(long _1, int _2) {
        return new Tuple2$mcJI$sp(_1, _2);
    }

    public Tuple2<Object, Object> copy$mJJc$sp(long _1, long _2) {
        return new Tuple2$mcJJ$sp(_1, _2);
    }

    public <T1, T2> boolean copy$default$1$mcZ$sp() {
        return BoxesRunTime.unboxToBoolean(this.copy$default$1());
    }

    public <T1, T2> char copy$default$1$mcC$sp() {
        return BoxesRunTime.unboxToChar(this.copy$default$1());
    }

    public <T1, T2> double copy$default$1$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.copy$default$1());
    }

    public <T1, T2> int copy$default$1$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.copy$default$1());
    }

    public <T1, T2> long copy$default$1$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.copy$default$1());
    }

    public <T1, T2> boolean copy$default$2$mcZ$sp() {
        return BoxesRunTime.unboxToBoolean(this.copy$default$2());
    }

    public <T1, T2> char copy$default$2$mcC$sp() {
        return BoxesRunTime.unboxToChar(this.copy$default$2());
    }

    public <T1, T2> double copy$default$2$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.copy$default$2());
    }

    public <T1, T2> int copy$default$2$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.copy$default$2());
    }

    public <T1, T2> long copy$default$2$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.copy$default$2());
    }

    public boolean specInstance$() {
        return false;
    }

    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
}

