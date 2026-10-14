/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple3$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=x!\u0002\u0010 \u0011\u00031c!\u0002\u0015 \u0011\u0003I\u0003\"\u0002\u001d\u0002\t\u0003I\u0004\"\u0002\u001e\u0002\t\u0003Y\u0004\"CAV\u0003\u0005\u0005I\u0011QAW\u0011%\t)-AA\u0001\n\u0003\u000b9\rC\u0005\u0002f\u0006\t\t\u0011\"\u0003\u0002h\u001a!\u0001f\b\"?\u0011!quA!f\u0001\n\u0003y\u0005\u0002C.\b\u0005#\u0005\u000b\u0011\u0002)\t\u0011q;!Q3A\u0005\u0002uC\u0001\"Y\u0004\u0003\u0012\u0003\u0006IA\u0018\u0005\tE\u001e\u0011)\u001a!C\u0001G\"Aqm\u0002B\tB\u0003%A\rC\u00039\u000f\u0011\u0005\u0001\u000eC\u0004n\u000f\t\u0007I\u0011\u00018\t\rE<\u0001\u0015!\u0003p\u0011\u001d\u0011x!!A\u0005\u0002MD\u0001b`\u0004\u0012\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003?9\u0011\u0013!C\u0001\u0003CA\u0011\"!\f\b#\u0003%\t!a\f\t\u0013\u0005mr!!A\u0005B\u0005u\u0002\"CA&\u000f\u0005\u0005I\u0011AA'\u0011%\t)fBA\u0001\n\u0003\t9\u0006C\u0005\u0002^\u001d\t\t\u0011\"\u0011\u0002`!I\u0011QN\u0004\u0002\u0002\u0013\u0005\u0011q\u000e\u0005\n\u0003s:\u0011\u0011!C!\u0003wB\u0011\"a \b\u0003\u0003%\t%!!\t\u0013\u0005\ru!!A\u0005B\u0005\u0015\u0005\"CAD\u000f\u0005\u0005I\u0011IAE\u0003\u0019!V\u000f\u001d7fg)\u0011\u0001%I\u0001\u0006iV\u0004H.\u001a\u0006\u0003E\r\nAA[1qS*\tA%\u0001\u0003bW.\f7\u0001\u0001\t\u0003O\u0005i\u0011a\b\u0002\u0007)V\u0004H.Z\u001a\u0014\u0007\u0005Q\u0003\u0007\u0005\u0002,]5\tAFC\u0001.\u0003\u0015\u00198-\u00197b\u0013\tyCF\u0001\u0004B]f\u0014VM\u001a\t\u0003cYj\u0011A\r\u0006\u0003gQ\n!![8\u000b\u0003U\nAA[1wC&\u0011qG\r\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0019\naa\u0019:fCR,Wc\u0002\u001f\u0002\u001c\u0006}\u00151\u0015\u000b\b{\u0005\u0015\u0016qUAU!!9s!!'\u0002\u001e\u0006\u0005V\u0003B S?\u0016\u001cBa\u0002\u0016A\u0007B\u00111&Q\u0005\u0003\u00052\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002E\u0019:\u0011QI\u0013\b\u0003\r&k\u0011a\u0012\u0006\u0003\u0011\u0016\na\u0001\u0010:p_Rt\u0014\"A\u0017\n\u0005-c\u0013a\u00029bG.\fw-Z\u0005\u0003o5S!a\u0013\u0017\u0002\u0005Q\fT#\u0001)\u0011\u0005E\u0013F\u0002\u0001\u0003\u0006'\u001e\u0011\r\u0001\u0016\u0002\u0003)F\n\"!\u0016-\u0011\u0005-2\u0016BA,-\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aK-\n\u0005ic#aA!os\u0006\u0019A/\r\u0011\u0002\u0005Q\u0014T#\u00010\u0011\u0005E{F!\u00021\b\u0005\u0004!&A\u0001+3\u0003\r!(\u0007I\u0001\u0003iN*\u0012\u0001\u001a\t\u0003#\u0016$QAZ\u0004C\u0002Q\u0013!\u0001V\u001a\u0002\u0007Q\u001c\u0004\u0005\u0006\u0003jU.d\u0007#B\u0014\b!z#\u0007\"\u0002(\u000f\u0001\u0004\u0001\u0006\"\u0002/\u000f\u0001\u0004q\u0006\"\u00022\u000f\u0001\u0004!\u0017a\u0002;p'\u000e\fG.Y\u000b\u0002_B)1\u0006\u001d)_I&\u0011\u0001\u0006L\u0001\ti>\u001c6-\u00197bA\u0005!1m\u001c9z+\u0011!x/_>\u0015\tUdXP \t\u0006O\u001d1\bP\u001f\t\u0003#^$QaU\tC\u0002Q\u0003\"!U=\u0005\u000b\u0001\f\"\u0019\u0001+\u0011\u0005E[H!\u00024\u0012\u0005\u0004!\u0006b\u0002(\u0012!\u0003\u0005\rA\u001e\u0005\b9F\u0001\n\u00111\u0001y\u0011\u001d\u0011\u0017\u0003%AA\u0002i\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0005\u0002\u0004\u0005e\u00111DA\u000f+\t\t)AK\u0002Q\u0003\u000fY#!!\u0003\u0011\t\u0005-\u0011QC\u0007\u0003\u0003\u001bQA!a\u0004\u0002\u0012\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003'a\u0013AC1o]>$\u0018\r^5p]&!\u0011qCA\u0007\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006'J\u0011\r\u0001\u0016\u0003\u0006AJ\u0011\r\u0001\u0016\u0003\u0006MJ\u0011\r\u0001V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+!\t\u0019#a\n\u0002*\u0005-RCAA\u0013U\rq\u0016q\u0001\u0003\u0006'N\u0011\r\u0001\u0016\u0003\u0006AN\u0011\r\u0001\u0016\u0003\u0006MN\u0011\r\u0001V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+!\t\t$!\u000e\u00028\u0005eRCAA\u001aU\r!\u0017q\u0001\u0003\u0006'R\u0011\r\u0001\u0016\u0003\u0006AR\u0011\r\u0001\u0016\u0003\u0006MR\u0011\r\u0001V\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\u0002\u0003BA!\u0003\u000fj!!a\u0011\u000b\u0007\u0005\u0015C'\u0001\u0003mC:<\u0017\u0002BA%\u0003\u0007\u0012aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA(!\rY\u0013\u0011K\u0005\u0004\u0003'b#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001-\u0002Z!I\u00111L\f\u0002\u0002\u0003\u0007\u0011qJ\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\u0005\u0004#BA2\u0003SBVBAA3\u0015\r\t9\u0007L\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA6\u0003K\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011\u0011OA<!\rY\u00131O\u0005\u0004\u0003kb#a\u0002\"p_2,\u0017M\u001c\u0005\t\u00037J\u0012\u0011!a\u00011\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ty$! \t\u0013\u0005m#$!AA\u0002\u0005=\u0013\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005=\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005}\u0012AB3rk\u0006d7\u000f\u0006\u0003\u0002r\u0005-\u0005\u0002CA.;\u0005\u0005\t\u0019\u0001-)\u000f\u001d\ty)!&\u0002\u0018B\u00191&!%\n\u0007\u0005MEF\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011\u0001E\u0002R\u00037#QaU\u0002C\u0002Q\u00032!UAP\t\u0015\u00017A1\u0001U!\r\t\u00161\u0015\u0003\u0006M\u000e\u0011\r\u0001\u0016\u0005\u0007\u001d\u000e\u0001\r!!'\t\rq\u001b\u0001\u0019AAO\u0011\u0019\u00117\u00011\u0001\u0002\"\u0006)\u0011\r\u001d9msVA\u0011qVA[\u0003s\u000bi\f\u0006\u0005\u00022\u0006}\u0016\u0011YAb!!9s!a-\u00028\u0006m\u0006cA)\u00026\u0012)1\u000b\u0002b\u0001)B\u0019\u0011+!/\u0005\u000b\u0001$!\u0019\u0001+\u0011\u0007E\u000bi\fB\u0003g\t\t\u0007A\u000b\u0003\u0004O\t\u0001\u0007\u00111\u0017\u0005\u00079\u0012\u0001\r!a.\t\r\t$\u0001\u0019AA^\u0003\u001d)h.\u00199qYf,\u0002\"!3\u0002V\u0006e\u0017Q\u001c\u000b\u0005\u0003\u0017\fy\u000eE\u0003,\u0003\u001b\f\t.C\u0002\u0002P2\u0012aa\u00149uS>t\u0007\u0003C\u0016q\u0003'\f9.a7\u0011\u0007E\u000b)\u000eB\u0003T\u000b\t\u0007A\u000bE\u0002R\u00033$Q\u0001Y\u0003C\u0002Q\u00032!UAo\t\u00151WA1\u0001U\u0011%\t\t/BA\u0001\u0002\u0004\t\u0019/A\u0002yIA\u0002\u0002bJ\u0004\u0002T\u0006]\u00171\\\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003S\u0004B!!\u0011\u0002l&!\u0011Q^A\"\u0005\u0019y%M[3di\u0002")
public final class Tuple3<T1, T2, T3>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final scala.Tuple3<T1, T2, T3> toScala;

    public static <T1, T2, T3> Option<scala.Tuple3<T1, T2, T3>> unapply(Tuple3<T1, T2, T3> x$0) {
        return Tuple3$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> apply(T1 t1, T2 t2, T3 t3) {
        return Tuple3$.MODULE$.apply(t1, t2, t3);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 t1, T2 t2, T3 t3) {
        return Tuple3$.MODULE$.create(t1, t2, t3);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public T1 t1() {
        return this.t1;
    }

    public T2 t2() {
        return this.t2;
    }

    public T3 t3() {
        return this.t3;
    }

    public scala.Tuple3<T1, T2, T3> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3> Tuple3<T1, T2, T3> copy(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<T1, T2, T3>(t1, t2, t3);
    }

    public <T1, T2, T3> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3> T3 copy$default$3() {
        return this.t3();
    }

    @Override
    public String productPrefix() {
        return "Tuple3";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.t1();
            }
            case 1: {
                return this.t2();
            }
            case 2: {
                return this.t3();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple3;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "t1";
            }
            case 1: {
                return "t2";
            }
            case 2: {
                return "t3";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Tuple3)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple3 tuple3 = (Tuple3)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple3.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple3.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple3.t3())) return false;
        return true;
    }

    public Tuple3(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        Product.$init$(this);
        this.toScala = new scala.Tuple3<T1, T2, T3>(t1, t2, t3);
    }
}

