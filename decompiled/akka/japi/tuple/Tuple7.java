/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple7$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\u001dq!\u0002\u0016,\u0011\u0003\u0011d!\u0002\u001b,\u0011\u0003)\u0004\"\u0002#\u0002\t\u0003)\u0005\"\u0002$\u0002\t\u00039\u0005\"\u0003BN\u0003\u0005\u0005I\u0011\u0011BO\u0011%\u0011i-AA\u0001\n\u0003\u0013y\rC\u0005\u0003~\u0006\t\t\u0011\"\u0003\u0003\u0000\u001a!Ag\u000b\"K\u0011!QvA!f\u0001\n\u0003Y\u0006\u0002C4\b\u0005#\u0005\u000b\u0011\u0002/\t\u0011!<!Q3A\u0005\u0002%D\u0001\"\\\u0004\u0003\u0012\u0003\u0006IA\u001b\u0005\t]\u001e\u0011)\u001a!C\u0001_\"A1o\u0002B\tB\u0003%\u0001\u000f\u0003\u0005u\u000f\tU\r\u0011\"\u0001v\u0011!IxA!E!\u0002\u00131\b\u0002\u0003>\b\u0005+\u0007I\u0011A>\t\u0011}<!\u0011#Q\u0001\nqD!\"!\u0001\b\u0005+\u0007I\u0011AA\u0002\u0011)\tYa\u0002B\tB\u0003%\u0011Q\u0001\u0005\u000b\u0003\u001b9!Q3A\u0005\u0002\u0005=\u0001BCA\f\u000f\tE\t\u0015!\u0003\u0002\u0012!1Ai\u0002C\u0001\u00033A\u0011\"a\u000b\b\u0005\u0004%\t!!\f\t\u0011\u0005Mr\u0001)A\u0005\u0003_A\u0011\"!\u000e\b\u0003\u0003%\t!a\u000e\t\u0013\u0005\u001dt!%A\u0005\u0002\u0005%\u0004\"CAH\u000fE\u0005I\u0011AAI\u0011%\t)kBI\u0001\n\u0003\t9\u000bC\u0005\u0002<\u001e\t\n\u0011\"\u0001\u0002>\"I\u0011\u0011[\u0004\u0012\u0002\u0013\u0005\u00111\u001b\u0005\n\u0003O<\u0011\u0013!C\u0001\u0003SD\u0011\"!@\b#\u0003%\t!a@\t\u0013\tMq!!A\u0005B\tU\u0001\"\u0003B\u0012\u000f\u0005\u0005I\u0011\u0001B\u0013\u0011%\u0011icBA\u0001\n\u0003\u0011y\u0003C\u0005\u00036\u001d\t\t\u0011\"\u0011\u00038!I!QI\u0004\u0002\u0002\u0013\u0005!q\t\u0005\n\u0005#:\u0011\u0011!C!\u0005'B\u0011Ba\u0016\b\u0003\u0003%\tE!\u0017\t\u0013\tms!!A\u0005B\tu\u0003\"\u0003B0\u000f\u0005\u0005I\u0011\tB1\u0003\u0019!V\u000f\u001d7fo)\u0011A&L\u0001\u0006iV\u0004H.\u001a\u0006\u0003]=\nAA[1qS*\t\u0001'\u0001\u0003bW.\f7\u0001\u0001\t\u0003g\u0005i\u0011a\u000b\u0002\u0007)V\u0004H.Z\u001c\u0014\u0007\u00051D\b\u0005\u00028u5\t\u0001HC\u0001:\u0003\u0015\u00198-\u00197b\u0013\tY\u0004H\u0001\u0004B]f\u0014VM\u001a\t\u0003{\tk\u0011A\u0010\u0006\u0003\u007f\u0001\u000b!![8\u000b\u0003\u0005\u000bAA[1wC&\u00111I\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003I\naa\u0019:fCR,Wc\u0004%\u0003t\t]$1\u0010B@\u0005\u0007\u00139Ia#\u0015\u001f%\u0013iIa$\u0003\u0012\nM%Q\u0013BL\u00053\u0003\u0002cM\u0004\u0003r\tU$\u0011\u0010B?\u0005\u0003\u0013)I!#\u0016\u0015-s6.]<~\u0003\u000f\t\u0019b\u0005\u0003\bm1{\u0005CA\u001cN\u0013\tq\u0005HA\u0004Qe>$Wo\u0019;\u0011\u0005ACfBA)W\u001d\t\u0011V+D\u0001T\u0015\t!\u0016'\u0001\u0004=e>|GOP\u0005\u0002s%\u0011q\u000bO\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0019\u0015L\u0003\u0002Xq\u0005\u0011A/M\u000b\u00029B\u0011QL\u0018\u0007\u0001\t\u0015yvA1\u0001a\u0005\t!\u0016'\u0005\u0002bIB\u0011qGY\u0005\u0003Gb\u0012qAT8uQ&tw\r\u0005\u00028K&\u0011a\r\u000f\u0002\u0004\u0003:L\u0018a\u0001;2A\u0005\u0011AOM\u000b\u0002UB\u0011Ql\u001b\u0003\u0006Y\u001e\u0011\r\u0001\u0019\u0002\u0003)J\n1\u0001\u001e\u001a!\u0003\t!8'F\u0001q!\ti\u0016\u000fB\u0003s\u000f\t\u0007\u0001M\u0001\u0002Ug\u0005\u0019Ao\r\u0011\u0002\u0005Q$T#\u0001<\u0011\u0005u;H!\u0002=\b\u0005\u0004\u0001'A\u0001+5\u0003\r!H\u0007I\u0001\u0003iV*\u0012\u0001 \t\u0003;v$QA`\u0004C\u0002\u0001\u0014!\u0001V\u001b\u0002\u0007Q,\u0004%\u0001\u0002umU\u0011\u0011Q\u0001\t\u0004;\u0006\u001dAABA\u0005\u000f\t\u0007\u0001M\u0001\u0002Um\u0005\u0019AO\u000e\u0011\u0002\u0005Q<TCAA\t!\ri\u00161\u0003\u0003\u0007\u0003+9!\u0019\u00011\u0003\u0005Q;\u0014a\u0001;8AQ\u0001\u00121DA\u000f\u0003?\t\t#a\t\u0002&\u0005\u001d\u0012\u0011\u0006\t\fg\u001da&\u000e\u001d<}\u0003\u000b\t\t\u0002C\u0003[-\u0001\u0007A\fC\u0003i-\u0001\u0007!\u000eC\u0003o-\u0001\u0007\u0001\u000fC\u0003u-\u0001\u0007a\u000fC\u0003{-\u0001\u0007A\u0010C\u0004\u0002\u0002Y\u0001\r!!\u0002\t\u000f\u00055a\u00031\u0001\u0002\u0012\u00059Ao\\*dC2\fWCAA\u0018!19\u0014\u0011\u0007/kaZd\u0018QAA\t\u0013\t!\u0004(\u0001\u0005u_N\u001b\u0017\r\\1!\u0003\u0011\u0019w\u000e]=\u0016!\u0005e\u0012qHA\"\u0003\u000f\nY%a\u0014\u0002T\u0005]C\u0003EA\u001e\u00033\nY&!\u0018\u0002`\u0005\u0005\u00141MA3!A\u0019t!!\u0010\u0002B\u0005\u0015\u0013\u0011JA'\u0003#\n)\u0006E\u0002^\u0003\u007f!QaX\rC\u0002\u0001\u00042!XA\"\t\u0015a\u0017D1\u0001a!\ri\u0016q\t\u0003\u0006ef\u0011\r\u0001\u0019\t\u0004;\u0006-C!\u0002=\u001a\u0005\u0004\u0001\u0007cA/\u0002P\u0011)a0\u0007b\u0001AB\u0019Q,a\u0015\u0005\r\u0005%\u0011D1\u0001a!\ri\u0016q\u000b\u0003\u0007\u0003+I\"\u0019\u00011\t\u0011iK\u0002\u0013!a\u0001\u0003{A\u0001\u0002[\r\u0011\u0002\u0003\u0007\u0011\u0011\t\u0005\t]f\u0001\n\u00111\u0001\u0002F!AA/\u0007I\u0001\u0002\u0004\tI\u0005\u0003\u0005{3A\u0005\t\u0019AA'\u0011%\t\t!\u0007I\u0001\u0002\u0004\t\t\u0006C\u0005\u0002\u000ee\u0001\n\u00111\u0001\u0002V\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003EA6\u0003\u0003\u000b\u0019)!\"\u0002\b\u0006%\u00151RAG+\t\tiGK\u0002]\u0003_Z#!!\u001d\u0011\t\u0005M\u0014QP\u0007\u0003\u0003kRA!a\u001e\u0002z\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003wB\u0014AC1o]>$\u0018\r^5p]&!\u0011qPA;\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006?j\u0011\r\u0001\u0019\u0003\u0006Yj\u0011\r\u0001\u0019\u0003\u0006ej\u0011\r\u0001\u0019\u0003\u0006qj\u0011\r\u0001\u0019\u0003\u0006}j\u0011\r\u0001\u0019\u0003\u0007\u0003\u0013Q\"\u0019\u00011\u0005\r\u0005U!D1\u0001a\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0002#a%\u0002\u0018\u0006e\u00151TAO\u0003?\u000b\t+a)\u0016\u0005\u0005U%f\u00016\u0002p\u0011)ql\u0007b\u0001A\u0012)An\u0007b\u0001A\u0012)!o\u0007b\u0001A\u0012)\u0001p\u0007b\u0001A\u0012)ap\u0007b\u0001A\u00121\u0011\u0011B\u000eC\u0002\u0001$a!!\u0006\u001c\u0005\u0004\u0001\u0017AD2paf$C-\u001a4bk2$HeM\u000b\u0011\u0003S\u000bi+a,\u00022\u0006M\u0016QWA\\\u0003s+\"!a++\u0007A\fy\u0007B\u0003`9\t\u0007\u0001\rB\u0003m9\t\u0007\u0001\rB\u0003s9\t\u0007\u0001\rB\u0003y9\t\u0007\u0001\rB\u0003\u007f9\t\u0007\u0001\r\u0002\u0004\u0002\nq\u0011\r\u0001\u0019\u0003\u0007\u0003+a\"\u0019\u00011\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0001\u0012qXAb\u0003\u000b\f9-!3\u0002L\u00065\u0017qZ\u000b\u0003\u0003\u0003T3A^A8\t\u0015yVD1\u0001a\t\u0015aWD1\u0001a\t\u0015\u0011XD1\u0001a\t\u0015AXD1\u0001a\t\u0015qXD1\u0001a\t\u0019\tI!\bb\u0001A\u00121\u0011QC\u000fC\u0002\u0001\fabY8qs\u0012\"WMZ1vYR$S'\u0006\t\u0002V\u0006e\u00171\\Ao\u0003?\f\t/a9\u0002fV\u0011\u0011q\u001b\u0016\u0004y\u0006=D!B0\u001f\u0005\u0004\u0001G!\u00027\u001f\u0005\u0004\u0001G!\u0002:\u001f\u0005\u0004\u0001G!\u0002=\u001f\u0005\u0004\u0001G!\u0002@\u001f\u0005\u0004\u0001GABA\u0005=\t\u0007\u0001\r\u0002\u0004\u0002\u0016y\u0011\r\u0001Y\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+A\tY/a<\u0002r\u0006M\u0018Q_A|\u0003s\fY0\u0006\u0002\u0002n*\"\u0011QAA8\t\u0015yvD1\u0001a\t\u0015awD1\u0001a\t\u0015\u0011xD1\u0001a\t\u0015AxD1\u0001a\t\u0015qxD1\u0001a\t\u0019\tIa\bb\u0001A\u00121\u0011QC\u0010C\u0002\u0001\fabY8qs\u0012\"WMZ1vYR$s'\u0006\t\u0003\u0002\t\u0015!q\u0001B\u0005\u0005\u0017\u0011iAa\u0004\u0003\u0012U\u0011!1\u0001\u0016\u0005\u0003#\ty\u0007B\u0003`A\t\u0007\u0001\rB\u0003mA\t\u0007\u0001\rB\u0003sA\t\u0007\u0001\rB\u0003yA\t\u0007\u0001\rB\u0003\u007fA\t\u0007\u0001\r\u0002\u0004\u0002\n\u0001\u0012\r\u0001\u0019\u0003\u0007\u0003+\u0001#\u0019\u00011\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u00119\u0002\u0005\u0003\u0003\u001a\t}QB\u0001B\u000e\u0015\r\u0011i\u0002Q\u0001\u0005Y\u0006tw-\u0003\u0003\u0003\"\tm!AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0003(A\u0019qG!\u000b\n\u0007\t-\u0002HA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002e\u0005cA\u0011Ba\r$\u0003\u0003\u0005\rAa\n\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011I\u0004E\u0003\u0003<\t\u0005C-\u0004\u0002\u0003>)\u0019!q\b\u001d\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0003D\tu\"\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$BA!\u0013\u0003PA\u0019qGa\u0013\n\u0007\t5\u0003HA\u0004C_>dW-\u00198\t\u0011\tMR%!AA\u0002\u0011\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!!q\u0003B+\u0011%\u0011\u0019DJA\u0001\u0002\u0004\u00119#\u0001\u0005iCND7i\u001c3f)\t\u00119#\u0001\u0005u_N#(/\u001b8h)\t\u00119\"\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0005\u0013\u0012\u0019\u0007\u0003\u0005\u00034%\n\t\u00111\u0001eQ\u001d9!q\rB7\u0005_\u00022a\u000eB5\u0013\r\u0011Y\u0007\u000f\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004;\nMD!B0\u0004\u0005\u0004\u0001\u0007cA/\u0003x\u0011)An\u0001b\u0001AB\u0019QLa\u001f\u0005\u000bI\u001c!\u0019\u00011\u0011\u0007u\u0013y\bB\u0003y\u0007\t\u0007\u0001\rE\u0002^\u0005\u0007#QA`\u0002C\u0002\u0001\u00042!\u0018BD\t\u0019\tIa\u0001b\u0001AB\u0019QLa#\u0005\r\u0005U1A1\u0001a\u0011\u0019Q6\u00011\u0001\u0003r!1\u0001n\u0001a\u0001\u0005kBaA\\\u0002A\u0002\te\u0004B\u0002;\u0004\u0001\u0004\u0011i\b\u0003\u0004{\u0007\u0001\u0007!\u0011\u0011\u0005\b\u0003\u0003\u0019\u0001\u0019\u0001BC\u0011\u001d\tia\u0001a\u0001\u0005\u0013\u000bQ!\u00199qYf,\u0002Ca(\u0003&\n%&Q\u0016BY\u0005k\u0013IL!0\u0015!\t\u0005&q\u0018Ba\u0005\u0007\u0014)Ma2\u0003J\n-\u0007\u0003E\u001a\b\u0005G\u00139Ka+\u00030\nM&q\u0017B^!\ri&Q\u0015\u0003\u0006?\u0012\u0011\r\u0001\u0019\t\u0004;\n%F!\u00027\u0005\u0005\u0004\u0001\u0007cA/\u0003.\u0012)!\u000f\u0002b\u0001AB\u0019QL!-\u0005\u000ba$!\u0019\u00011\u0011\u0007u\u0013)\fB\u0003\u007f\t\t\u0007\u0001\rE\u0002^\u0005s#a!!\u0003\u0005\u0005\u0004\u0001\u0007cA/\u0003>\u00121\u0011Q\u0003\u0003C\u0002\u0001DaA\u0017\u0003A\u0002\t\r\u0006B\u00025\u0005\u0001\u0004\u00119\u000b\u0003\u0004o\t\u0001\u0007!1\u0016\u0005\u0007i\u0012\u0001\rAa,\t\ri$\u0001\u0019\u0001BZ\u0011\u001d\t\t\u0001\u0002a\u0001\u0005oCq!!\u0004\u0005\u0001\u0004\u0011Y,A\u0004v]\u0006\u0004\b\u000f\\=\u0016!\tE'Q\u001cBq\u0005K\u0014IO!<\u0003r\nUH\u0003\u0002Bj\u0005o\u0004Ra\u000eBk\u00053L1Aa69\u0005\u0019y\u0005\u000f^5p]B\tr'!\r\u0003\\\n}'1\u001dBt\u0005W\u0014yOa=\u0011\u0007u\u0013i\u000eB\u0003`\u000b\t\u0007\u0001\rE\u0002^\u0005C$Q\u0001\\\u0003C\u0002\u0001\u00042!\u0018Bs\t\u0015\u0011XA1\u0001a!\ri&\u0011\u001e\u0003\u0006q\u0016\u0011\r\u0001\u0019\t\u0004;\n5H!\u0002@\u0006\u0005\u0004\u0001\u0007cA/\u0003r\u00121\u0011\u0011B\u0003C\u0002\u0001\u00042!\u0018B{\t\u0019\t)\"\u0002b\u0001A\"I!\u0011`\u0003\u0002\u0002\u0003\u0007!1`\u0001\u0004q\u0012\u0002\u0004\u0003E\u001a\b\u00057\u0014yNa9\u0003h\n-(q\u001eBz\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0019\t\u0001\u0005\u0003\u0003\u001a\r\r\u0011\u0002BB\u0003\u00057\u0011aa\u00142kK\u000e$\b")
public final class Tuple7<T1, T2, T3, T4, T5, T6, T7>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final T5 t5;
    private final T6 t6;
    private final T7 t7;
    private final scala.Tuple7<T1, T2, T3, T4, T5, T6, T7> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7> Option<scala.Tuple7<T1, T2, T3, T4, T5, T6, T7>> unapply(Tuple7<T1, T2, T3, T4, T5, T6, T7> x$0) {
        return Tuple7$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return Tuple7$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return Tuple7$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7);
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

    public T7 t7() {
        return this.t7;
    }

    public scala.Tuple7<T1, T2, T3, T4, T5, T6, T7> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(t1, t2, t3, t4, t5, t6, t7);
    }

    public <T1, T2, T3, T4, T5, T6, T7> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T7 copy$default$7() {
        return this.t7();
    }

    @Override
    public String productPrefix() {
        return "Tuple7";
    }

    @Override
    public int productArity() {
        return 7;
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
            case 6: {
                return this.t7();
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
        return x$1 instanceof Tuple7;
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
            case 6: {
                return "t7";
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
        if (!(object instanceof Tuple7)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple7 tuple7 = (Tuple7)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple7.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple7.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple7.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple7.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple7.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple7.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple7.t7())) return false;
        return true;
    }

    public Tuple7(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        Product.$init$(this);
        this.toScala = new scala.Tuple7<T1, T2, T3, T4, T5, T6, T7>(t1, t2, t3, t4, t5, t6, t7);
    }
}

