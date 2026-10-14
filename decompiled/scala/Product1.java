/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005u;Q!\u0003\u0006\t\u000251Qa\u0004\u0006\t\u0002AAQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]1qa\u0004\u0006\u0011\u0002\u0007\u0005Q\u0004C\u0003&\t\u0011\u0005a\u0005C\u0003+\t\u0011\u00053\u0006C\u00030\t\u0011\u0005\u0003\u0007C\u0003@\t\u0019\u0005\u0001)\u0001\u0005Qe>$Wo\u0019;2\u0015\u0005Y\u0011!B:dC2\f7\u0001\u0001\t\u0003\u001d\u0005i\u0011A\u0003\u0002\t!J|G-^2ucM\u0011\u0011!\u0005\t\u0003\u001dII!a\u0005\u0006\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQ\"A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0005aQFCA\r\\!\rq!\u0004H\u0005\u00037)\u0011aa\u00149uS>t\u0007c\u0001\b\u00053V\u0011adQ\n\u0004\t}\u0011\u0003C\u0001\b!\u0013\t\t#BA\u0002B]f\u0004\"AD\u0012\n\u0005\u0011R!a\u0002)s_\u0012,8\r^\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\u001d\u0002\"A\u0004\u0015\n\u0005%R!\u0001B+oSR\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\f\t\u0003\u001d5J!A\f\u0006\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005}\t\u0004\"\u0002\u001a\b\u0001\u0004a\u0013!\u00018)\u0007\u001d!d\bE\u0002\u000fk]J!A\u000e\u0006\u0003\rQD'o\\<t!\tA4H\u0004\u0002\u000fs%\u0011!HC\u0001\ba\u0006\u001c7.Y4f\u0013\taTHA\rJ]\u0012,\u0007pT;u\u001f\u001a\u0014u.\u001e8eg\u0016C8-\u001a9uS>t'B\u0001\u001e\u000bG\u00059\u0014AA02+\u0005\t\u0005C\u0001\"D\u0019\u0001!\u0011\u0002\u0012\u0003!\u0002\u0003%)\u0019A#\u0003\u0005Q\u000b\u0014C\u0001$ !\tqq)\u0003\u0002I\u0015\t9aj\u001c;iS:<\u0007&B\"K\u001bF+\u0006C\u0001\bL\u0013\ta%BA\u0006ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017\u0007\u0002\u0013O\u001fBs!AD(\n\u0005AS\u0011aA%oiF\"AEU*U\u001d\tq1+\u0003\u0002U\u0015\u0005!Aj\u001c8hc\u0011!ck\u0016-\u000f\u000599\u0016B\u0001-\u000b\u0003\u0019!u.\u001e2mKB\u0011!I\u0017\u0003\u0006\t\u000e\u0011\r!\u0012\u0005\u00069\u000e\u0001\r\u0001H\u0001\u0002q\u0002")
public interface Product1<T1>
extends Product {
    public static <T1> Option<Product1<T1>> unapply(Product1<T1> x) {
        return new Some<Product1<T1>>(x);
    }

    public static /* synthetic */ int productArity$(Product1 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 1;
    }

    public static /* synthetic */ Object productElement$(Product1 $this, int n) {
        return $this.productElement(n);
    }

    @Override
    default public Object productElement(int n) throws IndexOutOfBoundsException {
        switch (n) {
            case 0: {
                return this._1();
            }
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 0)").toString());
    }

    public T1 _1();

    public static /* synthetic */ double _1$mcD$sp$(Product1 $this) {
        return $this._1$mcD$sp();
    }

    default public double _1$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this._1());
    }

    public static /* synthetic */ int _1$mcI$sp$(Product1 $this) {
        return $this._1$mcI$sp();
    }

    default public int _1$mcI$sp() {
        return BoxesRunTime.unboxToInt(this._1());
    }

    public static /* synthetic */ long _1$mcJ$sp$(Product1 $this) {
        return $this._1$mcJ$sp();
    }

    default public long _1$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this._1());
    }

    public static void $init$(Product1 $this) {
    }
}

