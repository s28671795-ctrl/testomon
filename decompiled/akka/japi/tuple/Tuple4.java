/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple4$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t=r!B\u0011#\u0011\u0003Ic!B\u0016#\u0011\u0003a\u0003\"B\u001e\u0002\t\u0003a\u0004\"B\u001f\u0002\t\u0003q\u0004\"CAq\u0003\u0005\u0005I\u0011QAr\u0011%\u0011\t!AA\u0001\n\u0003\u0013\u0019\u0001C\u0005\u0003&\u0005\t\t\u0011\"\u0003\u0003(\u0019!1F\t\"B\u0011!\tvA!f\u0001\n\u0003\u0011\u0006\u0002\u00030\b\u0005#\u0005\u000b\u0011B*\t\u0011};!Q3A\u0005\u0002\u0001D\u0001\u0002Z\u0004\u0003\u0012\u0003\u0006I!\u0019\u0005\tK\u001e\u0011)\u001a!C\u0001M\"A!n\u0002B\tB\u0003%q\r\u0003\u0005l\u000f\tU\r\u0011\"\u0001m\u0011!\u0001xA!E!\u0002\u0013i\u0007\"B\u001e\b\t\u0003\t\bbB<\b\u0005\u0004%\t\u0001\u001f\u0005\u0007w\u001e\u0001\u000b\u0011B=\t\u000fq<\u0011\u0011!C\u0001{\"I\u0011\u0011D\u0004\u0012\u0002\u0013\u0005\u00111\u0004\u0005\n\u0003w9\u0011\u0013!C\u0001\u0003{A\u0011\"a\u0013\b#\u0003%\t!!\u0014\t\u0013\u0005ms!%A\u0005\u0002\u0005u\u0003\"CA6\u000f\u0005\u0005I\u0011IA7\u0011%\tYhBA\u0001\n\u0003\ti\bC\u0005\u0002\u0006\u001e\t\t\u0011\"\u0001\u0002\b\"I\u0011QR\u0004\u0002\u0002\u0013\u0005\u0013q\u0012\u0005\n\u0003;;\u0011\u0011!C\u0001\u0003?C\u0011\"!+\b\u0003\u0003%\t%a+\t\u0013\u0005=v!!A\u0005B\u0005E\u0006\"CAZ\u000f\u0005\u0005I\u0011IA[\u0011%\t9lBA\u0001\n\u0003\nI,\u0001\u0004UkBdW\r\u000e\u0006\u0003G\u0011\nQ\u0001^;qY\u0016T!!\n\u0014\u0002\t)\f\u0007/\u001b\u0006\u0002O\u0005!\u0011m[6b\u0007\u0001\u0001\"AK\u0001\u000e\u0003\t\u0012a\u0001V;qY\u0016$4cA\u0001.gA\u0011a&M\u0007\u0002_)\t\u0001'A\u0003tG\u0006d\u0017-\u0003\u00023_\t1\u0011I\\=SK\u001a\u0004\"\u0001N\u001d\u000e\u0003UR!AN\u001c\u0002\u0005%|'\"\u0001\u001d\u0002\t)\fg/Y\u0005\u0003uU\u0012AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#A\u0015\u0002\r\r\u0014X-\u0019;f+%y\u00141ZAh\u0003'\f9\u000eF\u0005A\u00033\fY.!8\u0002`BQ!fBAe\u0003\u001b\f\t.!6\u0016\u000b\t+&\r\u001b8\u0014\t\u001di3I\u0012\t\u0003]\u0011K!!R\u0018\u0003\u000fA\u0013x\u000eZ;diB\u0011qi\u0014\b\u0003\u00116s!!\u0013'\u000e\u0003)S!a\u0013\u0015\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0014B\u0001(0\u0003\u001d\u0001\u0018mY6bO\u0016L!A\u000f)\u000b\u00059{\u0013A\u0001;2+\u0005\u0019\u0006C\u0001+V\u0019\u0001!QAV\u0004C\u0002]\u0013!\u0001V\u0019\u0012\u0005a[\u0006C\u0001\u0018Z\u0013\tQvFA\u0004O_RD\u0017N\\4\u0011\u00059b\u0016BA/0\u0005\r\te._\u0001\u0004iF\u0002\u0013A\u0001;3+\u0005\t\u0007C\u0001+c\t\u0015\u0019wA1\u0001X\u0005\t!&'A\u0002ue\u0001\n!\u0001^\u001a\u0016\u0003\u001d\u0004\"\u0001\u00165\u0005\u000b%<!\u0019A,\u0003\u0005Q\u001b\u0014a\u0001;4A\u0005\u0011A\u000fN\u000b\u0002[B\u0011AK\u001c\u0003\u0006_\u001e\u0011\ra\u0016\u0002\u0003)R\n1\u0001\u001e\u001b!)\u0015\u00118\u000f^;w!\u0019QsaU1h[\")\u0011\u000b\u0005a\u0001'\")q\f\u0005a\u0001C\")Q\r\u0005a\u0001O\")1\u000e\u0005a\u0001[\u00069Ao\\*dC2\fW#A=\u0011\r9R8+Y4n\u0013\tYs&\u0001\u0005u_N\u001b\u0017\r\\1!\u0003\u0011\u0019w\u000e]=\u0016\u0013y\f\u0019!a\u0002\u0002\f\u0005=A#C@\u0002\u0012\u0005M\u0011QCA\f!)Qs!!\u0001\u0002\u0006\u0005%\u0011Q\u0002\t\u0004)\u0006\rA!\u0002,\u0014\u0005\u00049\u0006c\u0001+\u0002\b\u0011)1m\u0005b\u0001/B\u0019A+a\u0003\u0005\u000b%\u001c\"\u0019A,\u0011\u0007Q\u000by\u0001B\u0003p'\t\u0007q\u000b\u0003\u0005R'A\u0005\t\u0019AA\u0001\u0011!y6\u0003%AA\u0002\u0005\u0015\u0001\u0002C3\u0014!\u0003\u0005\r!!\u0003\t\u0011-\u001c\u0002\u0013!a\u0001\u0003\u001b\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0006\u0002\u001e\u0005M\u0012QGA\u001c\u0003s)\"!a\b+\u0007M\u000b\tc\u000b\u0002\u0002$A!\u0011QEA\u0018\u001b\t\t9C\u0003\u0003\u0002*\u0005-\u0012!C;oG\",7m[3e\u0015\r\ticL\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0019\u0003O\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u00151FC1\u0001X\t\u0015\u0019GC1\u0001X\t\u0015IGC1\u0001X\t\u0015yGC1\u0001X\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"\"a\u0010\u0002D\u0005\u0015\u0013qIA%+\t\t\tEK\u0002b\u0003C!QAV\u000bC\u0002]#QaY\u000bC\u0002]#Q![\u000bC\u0002]#Qa\\\u000bC\u0002]\u000babY8qs\u0012\"WMZ1vYR$3'\u0006\u0006\u0002P\u0005M\u0013QKA,\u00033*\"!!\u0015+\u0007\u001d\f\t\u0003B\u0003W-\t\u0007q\u000bB\u0003d-\t\u0007q\u000bB\u0003j-\t\u0007q\u000bB\u0003p-\t\u0007q+\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0015\u0005}\u00131MA3\u0003O\nI'\u0006\u0002\u0002b)\u001aQ.!\t\u0005\u000bY;\"\u0019A,\u0005\u000b\r<\"\u0019A,\u0005\u000b%<\"\u0019A,\u0005\u000b=<\"\u0019A,\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\u0007\u0005\u0003\u0002r\u0005]TBAA:\u0015\r\t)hN\u0001\u0005Y\u0006tw-\u0003\u0003\u0002z\u0005M$AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002\u0000A\u0019a&!!\n\u0007\u0005\ruFA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002\\\u0003\u0013C\u0011\"a#\u001b\u0003\u0003\u0005\r!a \u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\t\nE\u0003\u0002\u0014\u0006e5,\u0004\u0002\u0002\u0016*\u0019\u0011qS\u0018\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u001c\u0006U%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!)\u0002(B\u0019a&a)\n\u0007\u0005\u0015vFA\u0004C_>dW-\u00198\t\u0011\u0005-E$!AA\u0002m\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qNAW\u0011%\tY)HA\u0001\u0002\u0004\ty(\u0001\u0005iCND7i\u001c3f)\t\ty(\u0001\u0005u_N#(/\u001b8h)\t\ty'\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003C\u000bY\f\u0003\u0005\u0002\f\u0002\n\t\u00111\u0001\\Q\u001d9\u0011qXAc\u0003\u000f\u00042ALAa\u0013\r\t\u0019m\f\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004)\u0006-G!\u0002,\u0004\u0005\u00049\u0006c\u0001+\u0002P\u0012)1m\u0001b\u0001/B\u0019A+a5\u0005\u000b%\u001c!\u0019A,\u0011\u0007Q\u000b9\u000eB\u0003p\u0007\t\u0007q\u000b\u0003\u0004R\u0007\u0001\u0007\u0011\u0011\u001a\u0005\u0007?\u000e\u0001\r!!4\t\r\u0015\u001c\u0001\u0019AAi\u0011\u0019Y7\u00011\u0001\u0002V\u0006)\u0011\r\u001d9msVQ\u0011Q]Av\u0003_\f\u00190a>\u0015\u0015\u0005\u001d\u0018\u0011`A~\u0003{\fy\u0010\u0005\u0006+\u000f\u0005%\u0018Q^Ay\u0003k\u00042\u0001VAv\t\u00151FA1\u0001X!\r!\u0016q\u001e\u0003\u0006G\u0012\u0011\ra\u0016\t\u0004)\u0006MH!B5\u0005\u0005\u00049\u0006c\u0001+\u0002x\u0012)q\u000e\u0002b\u0001/\"1\u0011\u000b\u0002a\u0001\u0003SDaa\u0018\u0003A\u0002\u00055\bBB3\u0005\u0001\u0004\t\t\u0010\u0003\u0004l\t\u0001\u0007\u0011Q_\u0001\bk:\f\u0007\u000f\u001d7z+)\u0011)A!\u0005\u0003\u0016\te!Q\u0004\u000b\u0005\u0005\u000f\u0011y\u0002E\u0003/\u0005\u0013\u0011i!C\u0002\u0003\f=\u0012aa\u00149uS>t\u0007C\u0003\u0018{\u0005\u001f\u0011\u0019Ba\u0006\u0003\u001cA\u0019AK!\u0005\u0005\u000bY+!\u0019A,\u0011\u0007Q\u0013)\u0002B\u0003d\u000b\t\u0007q\u000bE\u0002U\u00053!Q![\u0003C\u0002]\u00032\u0001\u0016B\u000f\t\u0015yWA1\u0001X\u0011%\u0011\t#BA\u0001\u0002\u0004\u0011\u0019#A\u0002yIA\u0002\"BK\u0004\u0003\u0010\tM!q\u0003B\u000e\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011I\u0003\u0005\u0003\u0002r\t-\u0012\u0002\u0002B\u0017\u0003g\u0012aa\u00142kK\u000e$\b")
public final class Tuple4<T1, T2, T3, T4>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final scala.Tuple4<T1, T2, T3, T4> toScala;

    public static <T1, T2, T3, T4> Option<scala.Tuple4<T1, T2, T3, T4>> unapply(Tuple4<T1, T2, T3, T4> x$0) {
        return Tuple4$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> apply(T1 t1, T2 t2, T3 t3, T4 t4) {
        return Tuple4$.MODULE$.apply(t1, t2, t3, t4);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 t1, T2 t2, T3 t3, T4 t4) {
        return Tuple4$.MODULE$.create(t1, t2, t3, t4);
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

    public T4 t4() {
        return this.t4;
    }

    public scala.Tuple4<T1, T2, T3, T4> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> copy(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<T1, T2, T3, T4>(t1, t2, t3, t4);
    }

    public <T1, T2, T3, T4> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4> T4 copy$default$4() {
        return this.t4();
    }

    @Override
    public String productPrefix() {
        return "Tuple4";
    }

    @Override
    public int productArity() {
        return 4;
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
            case 3: {
                return this.t4();
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
        return x$1 instanceof Tuple4;
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
            case 3: {
                return "t4";
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
        if (!(object instanceof Tuple4)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple4 tuple4 = (Tuple4)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple4.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple4.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple4.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple4.t4())) return false;
        return true;
    }

    public Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        Product.$init$(this);
        this.toScala = new scala.Tuple4<T1, T2, T3, T4>(t1, t2, t3, t4);
    }
}

