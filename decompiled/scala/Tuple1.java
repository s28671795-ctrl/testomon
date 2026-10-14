/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product1;
import scala.Tuple1$;
import scala.Tuple1$mcD$sp;
import scala.Tuple1$mcI$sp;
import scala.Tuple1$mcJ$sp;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0005-e\u0001\u0002\u000b\u0016\u0005bA\u0001b\u0013\u0001\u0003\u0016\u0004%\t\u0001\u0014\u0005\t\u001b\u0002\u0011\t\u0012)A\u0005C!)a\n\u0001C\u0001\u001f\")!\u000b\u0001C!'\"9A\fAA\u0001\n\u0003i\u0006bB4\u0001#\u0003%\t\u0001\u001b\u0005\bs\u0002\t\t\u0011\"\u0011{\u0011%\t)\u0001AA\u0001\n\u0003\n9\u0001C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0001\u0002\u0018!I\u00111\u0005\u0001\u0002\u0002\u0013\u0005\u0013Q\u0005\u0005\n\u0003_\u0001\u0011\u0011!C!\u0003cA\u0011\"a\r\u0001\u0003\u0003%\t%!\u000e\b\u0013\u0005eR#!A\t\u0002\u0005mb\u0001\u0003\u000b\u0016\u0003\u0003E\t!!\u0010\t\r9sA\u0011AA%\u0011!\u0011f\"!A\u0005F\u0005-\u0003\"CA'\u001d\u0005\u0005I\u0011QA(\u0011%\t\u0019GDA\u0001\n\u0003\u000b)\u0007C\u0005\u0002\u0002:\t\t\u0011\"\u0003\u0002\u0004\n1A+\u001e9mKFR\u0011AF\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tI2eE\u0003\u00015yat\b\u0005\u0002\u001c95\tQ#\u0003\u0002\u001e+\t1\u0011I\\=SK\u001a\u00042aG\u0010\"\u0013\t\u0001SC\u0001\u0005Qe>$Wo\u0019;2!\t\u00113\u0005\u0004\u0001\u0005\u0013\u0011\u0002\u0001\u0015!A\u0005\u0006\u0004)#A\u0001+2#\t1\u0013\u0006\u0005\u0002\u001cO%\u0011\u0001&\u0006\u0002\b\u001d>$\b.\u001b8h!\tY\"&\u0003\u0002,+\t\u0019\u0011I\\=)\u000b\rj\u0003\u0007\u000e\u001d\u0011\u0005mq\u0013BA\u0018\u0016\u0005-\u0019\b/Z2jC2L'0\u001a32\t\u0011\n$g\r\b\u00037IJ!aM\u000b\u0002\u0007%sG/\r\u0003%kY:dBA\u000e7\u0013\t9T#\u0001\u0003M_:<\u0017\u0007\u0002\u0013:umr!a\u0007\u001e\n\u0005m*\u0012A\u0002#pk\ndW\r\u0005\u0002\u001c{%\u0011a(\u0006\u0002\b!J|G-^2u!\t\u0001\u0005J\u0004\u0002B\r:\u0011!)R\u0007\u0002\u0007*\u0011AiF\u0001\u0007yI|w\u000e\u001e \n\u0003YI!aR\u000b\u0002\u000fA\f7m[1hK&\u0011\u0011J\u0013\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u000fV\t!aX\u0019\u0016\u0003\u0005\n1aX\u0019!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001+\u0015\t\u00047\u0001\t\u0003\"B&\u0004\u0001\u0004\t\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003Q\u0003\"!V-\u000f\u0005Y;\u0006C\u0001\"\u0016\u0013\tAV#\u0001\u0004Qe\u0016$WMZ\u0005\u00035n\u0013aa\u0015;sS:<'B\u0001-\u0016\u0003\u0011\u0019w\u000e]=\u0016\u0005y\u000bGCA0g!\rY\u0002\u0001\u0019\t\u0003E\u0005$\u0011\u0002J\u0003!\u0002\u0003\u0005)\u0019A\u0013)\u000b\u0005l3\rZ32\t\u0011\n$gM\u0019\u0005IU2t'\r\u0003%siZ\u0004bB&\u0006!\u0003\u0005\r\u0001Y\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\tIG/F\u0001kU\t\t3nK\u0001m!\ti'/D\u0001o\u0015\ty\u0007/A\u0005v]\u000eDWmY6fI*\u0011\u0011/F\u0001\u000bC:tw\u000e^1uS>t\u0017BA:o\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\nI\u0019\u0001\u000b\u0011!AC\u0002\u0015BS\u0001^\u0017wob\fD\u0001J\u00193gE\"A%\u000e\u001c8c\u0011!\u0013HO\u001e\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005Y\bc\u0001?\u0002\u00045\tQP\u0003\u0002\u007f\u007f\u0006!A.\u00198h\u0015\t\t\t!\u0001\u0003kCZ\f\u0017B\u0001.~\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0005!\u0015\tY!!\u0005*\u001b\t\tiAC\u0002\u0002\u0010U\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\u0019\"!\u0004\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u00033\ty\u0002E\u0002\u001c\u00037I1!!\b\u0016\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\t\n\u0003\u0003\u0005\r!K\u0001\u0004q\u0012\n\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2a_A\u0014\u0011%\t\tCCA\u0001\u0002\u0004\tI\u0003E\u0002\u001c\u0003WI1!!\f\u0016\u0005\rIe\u000e^\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011F\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005e\u0011q\u0007\u0005\t\u0003Ca\u0011\u0011!a\u0001S\u00051A+\u001e9mKF\u0002\"a\u0007\b\u0014\t9Q\u0012q\b\t\u0005\u0003\u0003\n9%\u0004\u0002\u0002D)\u0019\u0011QI@\u0002\u0005%|\u0017bA%\u0002DQ\u0011\u00111\b\u000b\u0002w\u0006)\u0011\r\u001d9msV!\u0011\u0011KA,)\u0011\t\u0019&!\u0019\u0011\tm\u0001\u0011Q\u000b\t\u0004E\u0005]C!\u0003\u0013\u0012A\u0003\u0005\tQ1\u0001&Q%\t9&LA.\u0003;\ny&\r\u0003%cI\u001a\u0014\u0007\u0002\u00136m]\nD\u0001J\u001d;w!11*\u0005a\u0001\u0003+\nq!\u001e8baBd\u00170\u0006\u0003\u0002h\u0005ED\u0003BA5\u0003w\u0002RaGA6\u0003_J1!!\u001c\u0016\u0005\u0019y\u0005\u000f^5p]B\u0019!%!\u001d\u0005\u0013\u0011\u0012\u0002\u0015!A\u0001\u0006\u0004)\u0003&CA9[\u0005U\u0014qOA=c\u0011!\u0013GM\u001a2\t\u0011*dgN\u0019\u0005IeR4\bC\u0005\u0002~I\t\t\u00111\u0001\u0002\u0000\u0005\u0019\u0001\u0010\n\u0019\u0011\tm\u0001\u0011qN\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u000b\u00032\u0001`AD\u0013\r\tI) \u0002\u0007\u001f\nTWm\u0019;")
public class Tuple1<T1>
implements Product1<T1>,
Serializable {
    public final T1 _1;

    public static <T1> Option<T1> unapply(Tuple1<T1> x$0) {
        return Tuple1$.MODULE$.unapply(x$0);
    }

    public static <T1> Tuple1<T1> apply(T1 _1) {
        return new Tuple1<T1>(_1);
    }

    @Override
    public int productArity() {
        return Product1.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product1.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    public String toString() {
        return new StringBuilder(2).append("(").append(this._1()).append(")").toString();
    }

    public <T1> Tuple1<T1> copy(T1 _1) {
        return new Tuple1<T1>(_1);
    }

    public <T1> T1 copy$default$1() {
        return this._1();
    }

    @Override
    public String productPrefix() {
        return "Tuple1";
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
        return x$1 instanceof Tuple1;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
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
                if (!(x$1 instanceof Tuple1)) break block3;
                Tuple1 tuple1 = (Tuple1)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple1._1())) break block3;
            }
            return true;
        }
        return false;
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

    public Tuple1<Object> copy$mDc$sp(double _1) {
        return new Tuple1$mcD$sp(_1);
    }

    public Tuple1<Object> copy$mIc$sp(int _1) {
        return new Tuple1$mcI$sp(_1);
    }

    public Tuple1<Object> copy$mJc$sp(long _1) {
        return new Tuple1$mcJ$sp(_1);
    }

    public <T1> double copy$default$1$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.copy$default$1());
    }

    public <T1> int copy$default$1$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.copy$default$1());
    }

    public <T1> long copy$default$1$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.copy$default$1());
    }

    public boolean specInstance$() {
        return false;
    }

    public Tuple1(T1 _1) {
        this._1 = _1;
    }
}

