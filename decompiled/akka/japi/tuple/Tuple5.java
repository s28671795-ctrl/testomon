/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple5$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMt!\u0002\u0013&\u0011\u0003ac!\u0002\u0018&\u0011\u0003y\u0003\"\u0002 \u0002\t\u0003y\u0004\"\u0002!\u0002\t\u0003\t\u0005\"\u0003B\u000e\u0003\u0005\u0005I\u0011\u0011B\u000f\u0011%\u0011\t%AA\u0001\n\u0003\u0013\u0019\u0005C\u0005\u0003j\u0005\t\t\u0011\"\u0003\u0003l\u0019!a&\n\"E\u0011!!vA!f\u0001\n\u0003)\u0006\u0002C1\b\u0005#\u0005\u000b\u0011\u0002,\t\u0011\t<!Q3A\u0005\u0002\rD\u0001bZ\u0004\u0003\u0012\u0003\u0006I\u0001\u001a\u0005\tQ\u001e\u0011)\u001a!C\u0001S\"AQn\u0002B\tB\u0003%!\u000e\u0003\u0005o\u000f\tU\r\u0011\"\u0001p\u0011!\u0019xA!E!\u0002\u0013\u0001\b\u0002\u0003;\b\u0005+\u0007I\u0011A;\t\u0011e<!\u0011#Q\u0001\nYDQAP\u0004\u0005\u0002iD\u0011\"a\u0001\b\u0005\u0004%\t!!\u0002\t\u0011\u0005-q\u0001)A\u0005\u0003\u000fA\u0011\"!\u0004\b\u0003\u0003%\t!a\u0004\t\u0013\u0005Mr!%A\u0005\u0002\u0005U\u0002\"CA,\u000fE\u0005I\u0011AA-\u0011%\tIgBI\u0001\n\u0003\tY\u0007C\u0005\u0002|\u001d\t\n\u0011\"\u0001\u0002~!I\u0011QR\u0004\u0012\u0002\u0013\u0005\u0011q\u0012\u0005\n\u0003?;\u0011\u0011!C!\u0003CC\u0011\"a,\b\u0003\u0003%\t!!-\t\u0013\u0005ev!!A\u0005\u0002\u0005m\u0006\"CAa\u000f\u0005\u0005I\u0011IAb\u0011%\t\tnBA\u0001\n\u0003\t\u0019\u000eC\u0005\u0002^\u001e\t\t\u0011\"\u0011\u0002`\"I\u00111]\u0004\u0002\u0002\u0013\u0005\u0013Q\u001d\u0005\n\u0003O<\u0011\u0011!C!\u0003SD\u0011\"a;\b\u0003\u0003%\t%!<\u0002\rQ+\b\u000f\\36\u0015\t1s%A\u0003ukBdWM\u0003\u0002)S\u0005!!.\u00199j\u0015\u0005Q\u0013\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002.\u00035\tQE\u0001\u0004UkBdW-N\n\u0004\u0003A2\u0004CA\u00195\u001b\u0005\u0011$\"A\u001a\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0012$AB!osJ+g\r\u0005\u00028y5\t\u0001H\u0003\u0002:u\u0005\u0011\u0011n\u001c\u0006\u0002w\u0005!!.\u0019<b\u0013\ti\u0004H\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002Y\u000511M]3bi\u0016,2BQA\u0000\u0005\u0007\u00119Aa\u0003\u0003\u0010QY1I!\u0005\u0003\u0014\tU!q\u0003B\r!1is!!@\u0003\u0002\t\u0015!\u0011\u0002B\u0007+\u0019)\u0005,Z6roN!q\u0001\r$J!\t\tt)\u0003\u0002Ie\t9\u0001K]8ek\u000e$\bC\u0001&S\u001d\tY\u0005K\u0004\u0002M\u001f6\tQJ\u0003\u0002OW\u00051AH]8pizJ\u0011aM\u0005\u0003#J\nq\u0001]1dW\u0006<W-\u0003\u0002>'*\u0011\u0011KM\u0001\u0003iF*\u0012A\u0016\t\u0003/bc\u0001\u0001B\u0003Z\u000f\t\u0007!L\u0001\u0002UcE\u00111L\u0018\t\u0003cqK!!\u0018\u001a\u0003\u000f9{G\u000f[5oOB\u0011\u0011gX\u0005\u0003AJ\u00121!\u00118z\u0003\r!\u0018\u0007I\u0001\u0003iJ*\u0012\u0001\u001a\t\u0003/\u0016$QAZ\u0004C\u0002i\u0013!\u0001\u0016\u001a\u0002\u0007Q\u0014\u0004%\u0001\u0002ugU\t!\u000e\u0005\u0002XW\u0012)An\u0002b\u00015\n\u0011AkM\u0001\u0004iN\u0002\u0013A\u0001;5+\u0005\u0001\bCA,r\t\u0015\u0011xA1\u0001[\u0005\t!F'A\u0002ui\u0001\n!\u0001^\u001b\u0016\u0003Y\u0004\"aV<\u0005\u000ba<!\u0019\u0001.\u0003\u0005Q+\u0014a\u0001;6AQ91\u0010`?\u007f\u007f\u0006\u0005\u0001cB\u0017\b-\u0012T\u0007O\u001e\u0005\u0006)J\u0001\rA\u0016\u0005\u0006EJ\u0001\r\u0001\u001a\u0005\u0006QJ\u0001\rA\u001b\u0005\u0006]J\u0001\r\u0001\u001d\u0005\u0006iJ\u0001\rA^\u0001\bi>\u001c6-\u00197b+\t\t9\u0001\u0005\u00052\u0003\u00131FM\u001b9w\u0013\tq#'\u0001\u0005u_N\u001b\u0017\r\\1!\u0003\u0011\u0019w\u000e]=\u0016\u0019\u0005E\u0011qCA\u000e\u0003?\t\u0019#a\n\u0015\u0019\u0005M\u0011\u0011FA\u0016\u0003[\ty#!\r\u0011\u00195:\u0011QCA\r\u0003;\t\t#!\n\u0011\u0007]\u000b9\u0002B\u0003Z+\t\u0007!\fE\u0002X\u00037!QAZ\u000bC\u0002i\u00032aVA\u0010\t\u0015aWC1\u0001[!\r9\u00161\u0005\u0003\u0006eV\u0011\rA\u0017\t\u0004/\u0006\u001dB!\u0002=\u0016\u0005\u0004Q\u0006\u0002\u0003+\u0016!\u0003\u0005\r!!\u0006\t\u0011\t,\u0002\u0013!a\u0001\u00033A\u0001\u0002[\u000b\u0011\u0002\u0003\u0007\u0011Q\u0004\u0005\t]V\u0001\n\u00111\u0001\u0002\"!AA/\u0006I\u0001\u0002\u0004\t)#\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0019\u0005]\u0012QJA(\u0003#\n\u0019&!\u0016\u0016\u0005\u0005e\"f\u0001,\u0002<-\u0012\u0011Q\b\t\u0005\u0003\u007f\tI%\u0004\u0002\u0002B)!\u00111IA#\u0003%)hn\u00195fG.,GMC\u0002\u0002HI\n!\"\u00198o_R\fG/[8o\u0013\u0011\tY%!\u0011\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003Z-\t\u0007!\fB\u0003g-\t\u0007!\fB\u0003m-\t\u0007!\fB\u0003s-\t\u0007!\fB\u0003y-\t\u0007!,\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0019\u0005m\u0013qLA1\u0003G\n)'a\u001a\u0016\u0005\u0005u#f\u00013\u0002<\u0011)\u0011l\u0006b\u00015\u0012)am\u0006b\u00015\u0012)An\u0006b\u00015\u0012)!o\u0006b\u00015\u0012)\u0001p\u0006b\u00015\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aT\u0003DA7\u0003c\n\u0019(!\u001e\u0002x\u0005eTCAA8U\rQ\u00171\b\u0003\u00063b\u0011\rA\u0017\u0003\u0006Mb\u0011\rA\u0017\u0003\u0006Yb\u0011\rA\u0017\u0003\u0006eb\u0011\rA\u0017\u0003\u0006qb\u0011\rAW\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+1\ty(a!\u0002\u0006\u0006\u001d\u0015\u0011RAF+\t\t\tIK\u0002q\u0003w!Q!W\rC\u0002i#QAZ\rC\u0002i#Q\u0001\\\rC\u0002i#QA]\rC\u0002i#Q\u0001_\rC\u0002i\u000babY8qs\u0012\"WMZ1vYR$S'\u0006\u0007\u0002\u0012\u0006U\u0015qSAM\u00037\u000bi*\u0006\u0002\u0002\u0014*\u001aa/a\u000f\u0005\u000beS\"\u0019\u0001.\u0005\u000b\u0019T\"\u0019\u0001.\u0005\u000b1T\"\u0019\u0001.\u0005\u000bIT\"\u0019\u0001.\u0005\u000baT\"\u0019\u0001.\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t\u0019\u000b\u0005\u0003\u0002&\u0006-VBAAT\u0015\r\tIKO\u0001\u0005Y\u0006tw-\u0003\u0003\u0002.\u0006\u001d&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u00024B\u0019\u0011'!.\n\u0007\u0005]&GA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002_\u0003{C\u0011\"a0\u001e\u0003\u0003\u0005\r!a-\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t)\rE\u0003\u0002H\u00065g,\u0004\u0002\u0002J*\u0019\u00111\u001a\u001a\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002P\u0006%'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!6\u0002\\B\u0019\u0011'a6\n\u0007\u0005e'GA\u0004C_>dW-\u00198\t\u0011\u0005}v$!AA\u0002y\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111UAq\u0011%\ty\fIA\u0001\u0002\u0004\t\u0019,\u0001\u0005iCND7i\u001c3f)\t\t\u0019,\u0001\u0005u_N#(/\u001b8h)\t\t\u0019+\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003+\fy\u000f\u0003\u0005\u0002@\u000e\n\t\u00111\u0001_Q\u001d9\u00111_A}\u0003w\u00042!MA{\u0013\r\t9P\r\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004/\u0006}H!B-\u0004\u0005\u0004Q\u0006cA,\u0003\u0004\u0011)am\u0001b\u00015B\u0019qKa\u0002\u0005\u000b1\u001c!\u0019\u0001.\u0011\u0007]\u0013Y\u0001B\u0003s\u0007\t\u0007!\fE\u0002X\u0005\u001f!Q\u0001_\u0002C\u0002iCa\u0001V\u0002A\u0002\u0005u\bB\u00022\u0004\u0001\u0004\u0011\t\u0001\u0003\u0004i\u0007\u0001\u0007!Q\u0001\u0005\u0007]\u000e\u0001\rA!\u0003\t\rQ\u001c\u0001\u0019\u0001B\u0007\u0003\u0015\t\u0007\u000f\u001d7z+1\u0011yB!\n\u0003*\t5\"\u0011\u0007B\u001b)1\u0011\tCa\u000e\u0003:\tm\"Q\bB !1isAa\t\u0003(\t-\"q\u0006B\u001a!\r9&Q\u0005\u0003\u00063\u0012\u0011\rA\u0017\t\u0004/\n%B!\u00024\u0005\u0005\u0004Q\u0006cA,\u0003.\u0011)A\u000e\u0002b\u00015B\u0019qK!\r\u0005\u000bI$!\u0019\u0001.\u0011\u0007]\u0013)\u0004B\u0003y\t\t\u0007!\f\u0003\u0004U\t\u0001\u0007!1\u0005\u0005\u0007E\u0012\u0001\rAa\n\t\r!$\u0001\u0019\u0001B\u0016\u0011\u0019qG\u00011\u0001\u00030!1A\u000f\u0002a\u0001\u0005g\tq!\u001e8baBd\u00170\u0006\u0007\u0003F\tE#Q\u000bB-\u0005;\u0012\t\u0007\u0006\u0003\u0003H\t\r\u0004#B\u0019\u0003J\t5\u0013b\u0001B&e\t1q\n\u001d;j_:\u0004R\"MA\u0005\u0005\u001f\u0012\u0019Fa\u0016\u0003\\\t}\u0003cA,\u0003R\u0011)\u0011,\u0002b\u00015B\u0019qK!\u0016\u0005\u000b\u0019,!\u0019\u0001.\u0011\u0007]\u0013I\u0006B\u0003m\u000b\t\u0007!\fE\u0002X\u0005;\"QA]\u0003C\u0002i\u00032a\u0016B1\t\u0015AXA1\u0001[\u0011%\u0011)'BA\u0001\u0002\u0004\u00119'A\u0002yIA\u0002B\"L\u0004\u0003P\tM#q\u000bB.\u0005?\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!\u001c\u0011\t\u0005\u0015&qN\u0005\u0005\u0005c\n9K\u0001\u0004PE*,7\r\u001e")
public final class Tuple5<T1, T2, T3, T4, T5>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final T5 t5;
    private final scala.Tuple5<T1, T2, T3, T4, T5> toScala;

    public static <T1, T2, T3, T4, T5> Option<scala.Tuple5<T1, T2, T3, T4, T5>> unapply(Tuple5<T1, T2, T3, T4, T5> x$0) {
        return Tuple5$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return Tuple5$.MODULE$.apply(t1, t2, t3, t4, t5);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return Tuple5$.MODULE$.create(t1, t2, t3, t4, t5);
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

    public T5 t5() {
        return this.t5;
    }

    public scala.Tuple5<T1, T2, T3, T4, T5> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<T1, T2, T3, T4, T5>(t1, t2, t3, t4, t5);
    }

    public <T1, T2, T3, T4, T5> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5> T5 copy$default$5() {
        return this.t5();
    }

    @Override
    public String productPrefix() {
        return "Tuple5";
    }

    @Override
    public int productArity() {
        return 5;
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
            case 4: {
                return this.t5();
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
        return x$1 instanceof Tuple5;
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
            case 4: {
                return "t5";
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
        if (!(object instanceof Tuple5)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple5 tuple5 = (Tuple5)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple5.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple5.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple5.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple5.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple5.t5())) return false;
        return true;
    }

    public Tuple5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        Product.$init$(this);
        this.toScala = new scala.Tuple5<T1, T2, T3, T4, T5>(t1, t2, t3, t4, t5);
    }
}

