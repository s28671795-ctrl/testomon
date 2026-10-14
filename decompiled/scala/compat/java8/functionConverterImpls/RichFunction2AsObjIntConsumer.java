/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjIntConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjIntConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bQBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'!\b*jG\"4UO\\2uS>t''Q:PE*Le\u000e^\"p]N,X.\u001a:\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005a!3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\u001c\u001aBg>\u0013'.\u00138u\u0007>t7/^7fe\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001RA\u0007\u0011#[AJ!!\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u0012%\u0019\u0001!Q!\n\u0001C\u0002\u0019\u0012\u0011\u0001V\t\u0003O)\u0002\"A\u0007\u0015\n\u0005%\"\"a\u0002(pi\"Lgn\u001a\t\u00035-J!\u0001\f\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001b]%\u0011q\u0006\u0006\u0002\u0004\u0013:$\bC\u0001\u000e2\u0013\t\u0011DC\u0001\u0003V]&$\u0018\u0001V:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t''Q:PE*Le\u000e^\"p]N,X.\u001a:%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005YB\u0004cA\u001c\u0001E5\ta\u0002C\u0003:\u0007\u0001\u0007q$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fa!Y:KCZ\fW#\u0001\u001f\u0011\u0007u\"%%D\u0001?\u0015\ty\u0004)\u0001\u0005gk:\u001cG/[8o\u0015\t\t%)\u0001\u0003vi&d'\"A\"\u0002\t)\fg/Y\u0005\u0003\u000bz\u0012ab\u00142k\u0013:$8i\u001c8tk6,'\u000f\u000b\u0002\u0005\u000fB\u0011!\u0004S\u0005\u0003\u0013R\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u00035\na!Z9vC2\u001cHC\u0001(R!\tQr*\u0003\u0002Q)\t9!i\\8mK\u0006t\u0007b\u0002*\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014!\b*jG\"4UO\\2uS>t''Q:PE*Le\u000e^\"p]N,X.\u001a:\u0011\u0005]B1C\u0001\u0005W!\tQr+\u0003\u0002Y)\t1\u0011I\\=SK\u001a$\u0012\u0001V\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:,\"\u0001X0\u0015\u0005u\u0003\u0007cA\u001fE=B\u00111e\u0018\u0003\u0006K)\u0011\rA\n\u0005\u0006C*\u0001\rAY\u0001\u0006IQD\u0017n\u001d\t\u0004o\u0001q\u0006F\u0001\u0006H\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005\u0019TGCA&h\u0011\u0015\t7\u00021\u0001i!\r9\u0004!\u001b\t\u0003G)$Q!J\u0006C\u0002\u0019\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u00055\u001cHC\u00018q)\tqu\u000eC\u0004S\u0019\u0005\u0005\t\u0019\u0001\u0016\t\u000b\u0005d\u0001\u0019A9\u0011\u0007]\u0002!\u000f\u0005\u0002$g\u0012)Q\u0005\u0004b\u0001M\u0001")
public final class RichFunction2AsObjIntConsumer<T> {
    private final Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying;

    public static <T> boolean equals$extension(Function2<T, Object, BoxedUnit> function2, Object object) {
        return RichFunction2AsObjIntConsumer$.MODULE$.equals$extension(function2, object);
    }

    public static <T> int hashCode$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjIntConsumer$.MODULE$.hashCode$extension(function2);
    }

    public static <T> ObjIntConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjIntConsumer$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying;
    }

    public ObjIntConsumer<T> asJava() {
        return RichFunction2AsObjIntConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsObjIntConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsObjIntConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying(), x$1);
    }

    public RichFunction2AsObjIntConsumer(Function2<T, Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjIntConsumer$$underlying = underlying;
    }
}

