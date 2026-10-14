/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichBiConsumerAsFunction2$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000baBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(!\u0007*jG\"\u0014\u0015nQ8ogVlWM]!t\rVt7\r^5p]JR!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001)2\u0001G\u00166'\t\u0001\u0011\u0004\u0005\u0002\u001b75\tA#\u0003\u0002\u001d)\t1\u0011I\\=WC2\fqj]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\nK7i\u001c8tk6,'/Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001B\u0001I\u0014*i5\t\u0011E\u0003\u0002#G\u0005Aa-\u001e8di&|gN\u0003\u0002%K\u0005!Q\u000f^5m\u0015\u00051\u0013\u0001\u00026bm\u0006L!\u0001K\u0011\u0003\u0015\tK7i\u001c8tk6,'\u000f\u0005\u0002+W1\u0001A!\u0002\u0017\u0001\u0005\u0004i#!\u0001+\u0012\u00059\n\u0004C\u0001\u000e0\u0013\t\u0001DCA\u0004O_RD\u0017N\\4\u0011\u0005i\u0011\u0014BA\u001a\u0015\u0005\r\te.\u001f\t\u0003UU\"QA\u000e\u0001C\u00025\u0012\u0011!V\u0001Qg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NQ5D_:\u001cX/\\3s\u0003N4UO\\2uS>t'\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002;yA!1\bA\u00155\u001b\u0005q\u0001\"B\u001f\u0004\u0001\u0004y\u0012AC;oI\u0016\u0014H._5oO\u00069\u0011m]*dC2\fW#\u0001!\u0011\u000bi\t\u0015\u0006N\"\n\u0005\t#\"!\u0003$v]\u000e$\u0018n\u001c83!\tQB)\u0003\u0002F)\t!QK\\5uQ\t!q\t\u0005\u0002\u001b\u0011&\u0011\u0011\n\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u0014\t\u000355K!A\u0014\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003#R\u0003\"A\u0007*\n\u0005M#\"a\u0002\"p_2,\u0017M\u001c\u0005\b+\u001a\t\t\u00111\u00012\u0003\rAH%M\u0001\u001a%&\u001c\u0007NQ5D_:\u001cX/\\3s\u0003N4UO\\2uS>t'\u0007\u0005\u0002<\u0011M\u0011\u0001\"\u0017\t\u00035iK!a\u0017\u000b\u0003\r\u0005s\u0017PU3g)\u00059\u0016!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]V\u0019qL\u00193\u0015\u0005\u0001,\u0007#\u0002\u000eBC\u000e\u001c\u0005C\u0001\u0016c\t\u0015a#B1\u0001.!\tQC\rB\u00037\u0015\t\u0007Q\u0006C\u0003g\u0015\u0001\u0007q-A\u0003%i\"L7\u000f\u0005\u0003<\u0001\u0005\u001c\u0007F\u0001\u0006H\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007-|\u0017\u000f\u0006\u0002LY\")am\u0003a\u0001[B!1\b\u00018q!\tQs\u000eB\u0003-\u0017\t\u0007Q\u0006\u0005\u0002+c\u0012)ag\u0003b\u0001[\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0004ijdHCA;x)\t\tf\u000fC\u0004V\u0019\u0005\u0005\t\u0019A\u0019\t\u000b\u0019d\u0001\u0019\u0001=\u0011\tm\u0002\u0011p\u001f\t\u0003Ui$Q\u0001\f\u0007C\u00025\u0002\"A\u000b?\u0005\u000bYb!\u0019A\u0017")
public final class RichBiConsumerAsFunction2<T, U> {
    private final BiConsumer<T, U> scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying;

    public static <T, U> boolean equals$extension(BiConsumer<T, U> biConsumer, Object object) {
        return RichBiConsumerAsFunction2$.MODULE$.equals$extension(biConsumer, object);
    }

    public static <T, U> int hashCode$extension(BiConsumer<T, U> biConsumer) {
        return RichBiConsumerAsFunction2$.MODULE$.hashCode$extension(biConsumer);
    }

    public static <T, U> Function2<T, U, BoxedUnit> asScala$extension(BiConsumer<T, U> biConsumer) {
        return RichBiConsumerAsFunction2$.MODULE$.asScala$extension(biConsumer);
    }

    public BiConsumer<T, U> scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying;
    }

    public Function2<T, U, BoxedUnit> asScala() {
        return RichBiConsumerAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichBiConsumerAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichBiConsumerAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying(), x$1);
    }

    public RichBiConsumerAsFunction2(BiConsumer<T, U> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichBiConsumerAsFunction2$$underlying = underlying;
    }
}

