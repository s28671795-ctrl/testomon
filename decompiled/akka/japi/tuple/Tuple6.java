/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple6$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tmv!B\u0014)\u0011\u0003yc!B\u0019)\u0011\u0003\u0011\u0004\"B!\u0002\t\u0003\u0011\u0005\"B\"\u0002\t\u0003!\u0005\"\u0003B-\u0003\u0005\u0005I\u0011\u0011B.\u0011%\u0011))AA\u0001\n\u0003\u00139\tC\u0005\u00032\u0006\t\t\u0011\"\u0003\u00034\u001a!\u0011\u0007\u000b\"H\u0011!9vA!f\u0001\n\u0003A\u0006\u0002\u00033\b\u0005#\u0005\u000b\u0011B-\t\u0011\u0015<!Q3A\u0005\u0002\u0019D\u0001B[\u0004\u0003\u0012\u0003\u0006Ia\u001a\u0005\tW\u001e\u0011)\u001a!C\u0001Y\"A\u0001o\u0002B\tB\u0003%Q\u000e\u0003\u0005r\u000f\tU\r\u0011\"\u0001s\u0011!1xA!E!\u0002\u0013\u0019\b\u0002C<\b\u0005+\u0007I\u0011\u0001=\t\u0011q<!\u0011#Q\u0001\neD\u0001\"`\u0004\u0003\u0016\u0004%\tA \u0005\n\u0003\u000b9!\u0011#Q\u0001\n}Da!Q\u0004\u0005\u0002\u0005\u001d\u0001\"CA\f\u000f\t\u0007I\u0011AA\r\u0011!\tyb\u0002Q\u0001\n\u0005m\u0001\"CA\u0011\u000f\u0005\u0005I\u0011AA\u0012\u0011%\tieBI\u0001\n\u0003\ty\u0005C\u0005\u0002t\u001d\t\n\u0011\"\u0001\u0002v!I\u0011qQ\u0004\u0012\u0002\u0013\u0005\u0011\u0011\u0012\u0005\n\u00037;\u0011\u0013!C\u0001\u0003;C\u0011\"a,\b#\u0003%\t!!-\t\u0013\u0005\rw!%A\u0005\u0002\u0005\u0015\u0007\"CAl\u000f\u0005\u0005I\u0011IAm\u0011%\t9oBA\u0001\n\u0003\tI\u000fC\u0005\u0002r\u001e\t\t\u0011\"\u0001\u0002t\"I\u0011\u0011`\u0004\u0002\u0002\u0013\u0005\u00131 \u0005\n\u0005\u00139\u0011\u0011!C\u0001\u0005\u0017A\u0011B!\u0006\b\u0003\u0003%\tEa\u0006\t\u0013\tmq!!A\u0005B\tu\u0001\"\u0003B\u0010\u000f\u0005\u0005I\u0011\tB\u0011\u0011%\u0011\u0019cBA\u0001\n\u0003\u0012)#\u0001\u0004UkBdWM\u000e\u0006\u0003S)\nQ\u0001^;qY\u0016T!a\u000b\u0017\u0002\t)\f\u0007/\u001b\u0006\u0002[\u0005!\u0011m[6b\u0007\u0001\u0001\"\u0001M\u0001\u000e\u0003!\u0012a\u0001V;qY\u001644cA\u00014sA\u0011AgN\u0007\u0002k)\ta'A\u0003tG\u0006d\u0017-\u0003\u00029k\t1\u0011I\\=SK\u001a\u0004\"AO \u000e\u0003mR!\u0001P\u001f\u0002\u0005%|'\"\u0001 \u0002\t)\fg/Y\u0005\u0003\u0001n\u0012AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#A\u0018\u0002\r\r\u0014X-\u0019;f+5)%q\u0007B\u001e\u0005\u007f\u0011\u0019Ea\u0012\u0003LQiaI!\u0014\u0003P\tE#1\u000bB+\u0005/\u0002b\u0002M\u0004\u00036\te\"Q\bB!\u0005\u000b\u0012I%\u0006\u0005I7\"tGO_A\u0001'\u001191'\u0013'\u0011\u0005QR\u0015BA&6\u0005\u001d\u0001&o\u001c3vGR\u0004\"!T+\u000f\u00059\u001bfBA(S\u001b\u0005\u0001&BA)/\u0003\u0019a$o\\8u}%\ta'\u0003\u0002Uk\u00059\u0001/Y2lC\u001e,\u0017B\u0001!W\u0015\t!V'\u0001\u0002ucU\t\u0011\f\u0005\u0002[72\u0001A!\u0002/\b\u0005\u0004i&A\u0001+2#\tq\u0016\r\u0005\u00025?&\u0011\u0001-\u000e\u0002\b\u001d>$\b.\u001b8h!\t!$-\u0003\u0002dk\t\u0019\u0011I\\=\u0002\u0007Q\f\u0004%\u0001\u0002ueU\tq\r\u0005\u0002[Q\u0012)\u0011n\u0002b\u0001;\n\u0011AKM\u0001\u0004iJ\u0002\u0013A\u0001;4+\u0005i\u0007C\u0001.o\t\u0015ywA1\u0001^\u0005\t!6'A\u0002ug\u0001\n!\u0001\u001e\u001b\u0016\u0003M\u0004\"A\u0017;\u0005\u000bU<!\u0019A/\u0003\u0005Q#\u0014a\u0001;5A\u0005\u0011A/N\u000b\u0002sB\u0011!L\u001f\u0003\u0006w\u001e\u0011\r!\u0018\u0002\u0003)V\n1\u0001^\u001b!\u0003\t!h'F\u0001\u0000!\rQ\u0016\u0011\u0001\u0003\u0007\u0003\u00079!\u0019A/\u0003\u0005Q3\u0014a\u0001;7AQq\u0011\u0011BA\u0006\u0003\u001b\ty!!\u0005\u0002\u0014\u0005U\u0001\u0003\u0003\u0019\b3\u001el7/_@\t\u000b]#\u0002\u0019A-\t\u000b\u0015$\u0002\u0019A4\t\u000b-$\u0002\u0019A7\t\u000bE$\u0002\u0019A:\t\u000b]$\u0002\u0019A=\t\u000bu$\u0002\u0019A@\u0002\u000fQ|7kY1mCV\u0011\u00111\u0004\t\ni\u0005u\u0011lZ7ts~L!!M\u001b\u0002\u0011Q|7kY1mC\u0002\nAaY8qsVq\u0011QEA\u0016\u0003_\t\u0019$a\u000e\u0002<\u0005}BCDA\u0014\u0003\u0003\n\u0019%!\u0012\u0002H\u0005%\u00131\n\t\u000fa\u001d\tI#!\f\u00022\u0005U\u0012\u0011HA\u001f!\rQ\u00161\u0006\u0003\u00069^\u0011\r!\u0018\t\u00045\u0006=B!B5\u0018\u0005\u0004i\u0006c\u0001.\u00024\u0011)qn\u0006b\u0001;B\u0019!,a\u000e\u0005\u000bU<\"\u0019A/\u0011\u0007i\u000bY\u0004B\u0003|/\t\u0007Q\fE\u0002[\u0003\u007f!a!a\u0001\u0018\u0005\u0004i\u0006\u0002C,\u0018!\u0003\u0005\r!!\u000b\t\u0011\u0015<\u0002\u0013!a\u0001\u0003[A\u0001b[\f\u0011\u0002\u0003\u0007\u0011\u0011\u0007\u0005\tc^\u0001\n\u00111\u0001\u00026!Aqo\u0006I\u0001\u0002\u0004\tI\u0004\u0003\u0005~/A\u0005\t\u0019AA\u001f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*b\"!\u0015\u0002h\u0005%\u00141NA7\u0003_\n\t(\u0006\u0002\u0002T)\u001a\u0011,!\u0016,\u0005\u0005]\u0003\u0003BA-\u0003Gj!!a\u0017\u000b\t\u0005u\u0013qL\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u00196\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003K\nYFA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q\u0001\u0018\rC\u0002u#Q!\u001b\rC\u0002u#Qa\u001c\rC\u0002u#Q!\u001e\rC\u0002u#Qa\u001f\rC\u0002u#a!a\u0001\u0019\u0005\u0004i\u0016AD2paf$C-\u001a4bk2$HEM\u000b\u000f\u0003o\nY(! \u0002\u0000\u0005\u0005\u00151QAC+\t\tIHK\u0002h\u0003+\"Q\u0001X\rC\u0002u#Q![\rC\u0002u#Qa\\\rC\u0002u#Q!^\rC\u0002u#Qa_\rC\u0002u#a!a\u0001\u001a\u0005\u0004i\u0016AD2paf$C-\u001a4bk2$HeM\u000b\u000f\u0003\u0017\u000by)!%\u0002\u0014\u0006U\u0015qSAM+\t\tiIK\u0002n\u0003+\"Q\u0001\u0018\u000eC\u0002u#Q!\u001b\u000eC\u0002u#Qa\u001c\u000eC\u0002u#Q!\u001e\u000eC\u0002u#Qa\u001f\u000eC\u0002u#a!a\u0001\u001b\u0005\u0004i\u0016AD2paf$C-\u001a4bk2$H\u0005N\u000b\u000f\u0003?\u000b\u0019+!*\u0002(\u0006%\u00161VAW+\t\t\tKK\u0002t\u0003+\"Q\u0001X\u000eC\u0002u#Q![\u000eC\u0002u#Qa\\\u000eC\u0002u#Q!^\u000eC\u0002u#Qa_\u000eC\u0002u#a!a\u0001\u001c\u0005\u0004i\u0016AD2paf$C-\u001a4bk2$H%N\u000b\u000f\u0003g\u000b9,!/\u0002<\u0006u\u0016qXAa+\t\t)LK\u0002z\u0003+\"Q\u0001\u0018\u000fC\u0002u#Q!\u001b\u000fC\u0002u#Qa\u001c\u000fC\u0002u#Q!\u001e\u000fC\u0002u#Qa\u001f\u000fC\u0002u#a!a\u0001\u001d\u0005\u0004i\u0016AD2paf$C-\u001a4bk2$HEN\u000b\u000f\u0003\u000f\fY-!4\u0002P\u0006E\u00171[Ak+\t\tIMK\u0002\u0000\u0003+\"Q\u0001X\u000fC\u0002u#Q![\u000fC\u0002u#Qa\\\u000fC\u0002u#Q!^\u000fC\u0002u#Qa_\u000fC\u0002u#a!a\u0001\u001e\u0005\u0004i\u0016!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002\\B!\u0011Q\\Ar\u001b\t\tyNC\u0002\u0002bv\nA\u0001\\1oO&!\u0011Q]Ap\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u001e\t\u0004i\u00055\u0018bAAxk\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019\u0011-!>\t\u0013\u0005]\b%!AA\u0002\u0005-\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002~B)\u0011q B\u0003C6\u0011!\u0011\u0001\u0006\u0004\u0005\u0007)\u0014AC2pY2,7\r^5p]&!!q\u0001B\u0001\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\t5!1\u0003\t\u0004i\t=\u0011b\u0001B\tk\t9!i\\8mK\u0006t\u0007\u0002CA|E\u0005\u0005\t\u0019A1\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u00037\u0014I\u0002C\u0005\u0002x\u000e\n\t\u00111\u0001\u0002l\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002l\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\\\u00061Q-];bYN$BA!\u0004\u0003(!A\u0011q\u001f\u0014\u0002\u0002\u0003\u0007\u0011\rK\u0004\b\u0005W\u0011\tDa\r\u0011\u0007Q\u0012i#C\u0002\u00030U\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\u00012A\u0017B\u001c\t\u0015a6A1\u0001^!\rQ&1\b\u0003\u0006S\u000e\u0011\r!\u0018\t\u00045\n}B!B8\u0004\u0005\u0004i\u0006c\u0001.\u0003D\u0011)Qo\u0001b\u0001;B\u0019!La\u0012\u0005\u000bm\u001c!\u0019A/\u0011\u0007i\u0013Y\u0005\u0002\u0004\u0002\u0004\r\u0011\r!\u0018\u0005\u0007/\u000e\u0001\rA!\u000e\t\r\u0015\u001c\u0001\u0019\u0001B\u001d\u0011\u0019Y7\u00011\u0001\u0003>!1\u0011o\u0001a\u0001\u0005\u0003Baa^\u0002A\u0002\t\u0015\u0003BB?\u0004\u0001\u0004\u0011I%A\u0003baBd\u00170\u0006\b\u0003^\t\r$q\rB6\u0005_\u0012\u0019Ha\u001e\u0015\u001d\t}#\u0011\u0010B>\u0005{\u0012yH!!\u0003\u0004Bq\u0001g\u0002B1\u0005K\u0012IG!\u001c\u0003r\tU\u0004c\u0001.\u0003d\u0011)A\f\u0002b\u0001;B\u0019!La\u001a\u0005\u000b%$!\u0019A/\u0011\u0007i\u0013Y\u0007B\u0003p\t\t\u0007Q\fE\u0002[\u0005_\"Q!\u001e\u0003C\u0002u\u00032A\u0017B:\t\u0015YHA1\u0001^!\rQ&q\u000f\u0003\u0007\u0003\u0007!!\u0019A/\t\r]#\u0001\u0019\u0001B1\u0011\u0019)G\u00011\u0001\u0003f!11\u000e\u0002a\u0001\u0005SBa!\u001d\u0003A\u0002\t5\u0004BB<\u0005\u0001\u0004\u0011\t\b\u0003\u0004~\t\u0001\u0007!QO\u0001\bk:\f\u0007\u000f\u001d7z+9\u0011II!&\u0003\u001a\nu%\u0011\u0015BS\u0005S#BAa#\u0003,B)AG!$\u0003\u0012&\u0019!qR\u001b\u0003\r=\u0003H/[8o!=!\u0014Q\u0004BJ\u0005/\u0013YJa(\u0003$\n\u001d\u0006c\u0001.\u0003\u0016\u0012)A,\u0002b\u0001;B\u0019!L!'\u0005\u000b%,!\u0019A/\u0011\u0007i\u0013i\nB\u0003p\u000b\t\u0007Q\fE\u0002[\u0005C#Q!^\u0003C\u0002u\u00032A\u0017BS\t\u0015YXA1\u0001^!\rQ&\u0011\u0016\u0003\u0007\u0003\u0007)!\u0019A/\t\u0013\t5V!!AA\u0002\t=\u0016a\u0001=%aAq\u0001g\u0002BJ\u0005/\u0013YJa(\u0003$\n\u001d\u0016\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B[!\u0011\tiNa.\n\t\te\u0016q\u001c\u0002\u0007\u001f\nTWm\u0019;")
public final class Tuple6<T1, T2, T3, T4, T5, T6>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final T5 t5;
    private final T6 t6;
    private final scala.Tuple6<T1, T2, T3, T4, T5, T6> toScala;

    public static <T1, T2, T3, T4, T5, T6> Option<scala.Tuple6<T1, T2, T3, T4, T5, T6>> unapply(Tuple6<T1, T2, T3, T4, T5, T6> x$0) {
        return Tuple6$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return Tuple6$.MODULE$.apply(t1, t2, t3, t4, t5, t6);
    }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return Tuple6$.MODULE$.create(t1, t2, t3, t4, t5, t6);
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

    public T6 t6() {
        return this.t6;
    }

    public scala.Tuple6<T1, T2, T3, T4, T5, T6> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(t1, t2, t3, t4, t5, t6);
    }

    public <T1, T2, T3, T4, T5, T6> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6> T6 copy$default$6() {
        return this.t6();
    }

    @Override
    public String productPrefix() {
        return "Tuple6";
    }

    @Override
    public int productArity() {
        return 6;
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
            case 5: {
                return this.t6();
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
        return x$1 instanceof Tuple6;
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
            case 5: {
                return "t6";
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
        if (!(object instanceof Tuple6)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple6 tuple6 = (Tuple6)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple6.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple6.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple6.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple6.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple6.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple6.t6())) return false;
        return true;
    }

    public Tuple6(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        Product.$init$(this);
        this.toScala = new scala.Tuple6<T1, T2, T3, T4, T5, T6>(t1, t2, t3, t4, t5, t6);
    }
}

