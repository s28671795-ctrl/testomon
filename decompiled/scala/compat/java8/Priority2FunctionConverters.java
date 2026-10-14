/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function1;
import scala.Function2;
import scala.compat.java8.Priority3FunctionConverters;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005uaa\u0002\u0005\n!\u0003\r\t\u0001\u0005\u0005\u00063\u0001!\tA\u0007\u0005\u0006=\u0001!\u0019a\b\u0005\u0006}\u0001!\u0019a\u0010\u0005\u0006\u001d\u0002!\u0019a\u0014\u0005\u0006=\u0002!\u0019a\u0018\u0005\u0006]\u0002!\u0019a\u001c\u0005\u0006}\u0002!\u0019a \u0002\u001c!JLwN]5usJ2UO\\2uS>t7i\u001c8wKJ$XM]:\u000b\u0005)Y\u0011!\u00026bm\u0006D$B\u0001\u0007\u000e\u0003\u0019\u0019w.\u001c9bi*\ta\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001\tR\u0003\u0005\u0002\u0013'5\tQ\"\u0003\u0002\u0015\u001b\t1\u0011I\\=SK\u001a\u0004\"AF\f\u000e\u0003%I!\u0001G\u0005\u00037A\u0013\u0018n\u001c:jif\u001cd)\u001e8di&|gnQ8om\u0016\u0014H/\u001a:t\u0003\u0019!\u0013N\\5uIQ\t1\u0004\u0005\u0002\u00139%\u0011Q$\u0004\u0002\u0005+:LG/\u0001\ff]JL7\r[!t\u0015\u00064\u0018MQ5D_:\u001cX/\\3s+\r\u0001\u0013f\r\u000b\u0003CU\u0002BAI\u0013(e5\t1E\u0003\u0002%\u0013\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7/\u0003\u0002'G\tI\"+[2i\rVt7\r^5p]J\n5OQ5D_:\u001cX/\\3s!\tA\u0013\u0006\u0004\u0001\u0005\u000b)\u0012!\u0019A\u0016\u0003\u0003Q\u000b\"\u0001L\u0018\u0011\u0005Ii\u0013B\u0001\u0018\u000e\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0019\n\u0005Ej!aA!osB\u0011\u0001f\r\u0003\u0006i\t\u0011\ra\u000b\u0002\u0002+\")aG\u0001a\u0001o\u0005\u00111O\u001a\t\u0006%a:#gG\u0005\u0003s5\u0011\u0011BR;oGRLwN\u001c\u001a)\u0005\tY\u0004C\u0001\n=\u0013\tiTB\u0001\u0004j]2Lg.Z\u0001\u0018K:\u0014\u0018n\u00195Bg*\u000bg/\u0019\"j!J,G-[2bi\u0016,2\u0001Q#H)\t\t\u0005\n\u0005\u0003#\u0005\u00123\u0015BA\"$\u0005i\u0011\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001b()\u001b)sK\u0012L7-\u0019;f!\tAS\tB\u0003+\u0007\t\u00071\u0006\u0005\u0002)\u000f\u0012)Ag\u0001b\u0001W!)ag\u0001a\u0001\u0013B)!\u0003\u000f#G\u0015B\u0011!cS\u0005\u0003\u00196\u0011qAQ8pY\u0016\fg\u000e\u000b\u0002\u0004w\u0005!RM\u001c:jG\"\f5OS1wC\u001a+hn\u0019;j_:,2\u0001U+X)\t\t\u0016\f\u0005\u0003#%R3\u0016BA*$\u0005]\u0011\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bh)\u001e8di&|g\u000e\u0005\u0002)+\u0012)!\u0006\u0002b\u0001WA\u0011\u0001f\u0016\u0003\u00061\u0012\u0011\ra\u000b\u0002\u0002%\")a\u0007\u0002a\u00015B!!c\u0017+W\u0013\taVBA\u0005Gk:\u001cG/[8oc!\u0012AaO\u0001\u001fK:\u0014\u0018n\u00195Bg*\u000bg/\u0019+p\t>,(\r\\3CS\u001a+hn\u0019;j_:,2\u0001Y3h)\t\t\u0007\u000e\u0005\u0003#E\u00124\u0017BA2$\u0005\u0005\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHk\u001c#pk\ndWMQ5Gk:\u001cG/[8o!\tAS\rB\u0003+\u000b\t\u00071\u0006\u0005\u0002)O\u0012)A'\u0002b\u0001W!)a'\u0002a\u0001SB)!\u0003\u000f3gUB\u0011!c[\u0005\u0003Y6\u0011a\u0001R8vE2,\u0007FA\u0003<\u0003m)gN]5dQ\u0006\u001b(*\u0019<b)>Le\u000e\u001e\"j\rVt7\r^5p]V\u0019\u0001/^<\u0015\u0005ED\b\u0003\u0002\u0012siZL!a]\u0012\u0003=IK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N$v.\u00138u\u0005&4UO\\2uS>t\u0007C\u0001\u0015v\t\u0015QcA1\u0001,!\tAs\u000fB\u00035\r\t\u00071\u0006C\u00037\r\u0001\u0007\u0011\u0010E\u0003\u0013qQ4(\u0010\u0005\u0002\u0013w&\u0011A0\u0004\u0002\u0004\u0013:$\bF\u0001\u0004<\u0003q)gN]5dQ\u0006\u001b(*\u0019<b)>duN\\4CS\u001a+hn\u0019;j_:,b!!\u0001\u0002\f\u0005=A\u0003BA\u0002\u0003#\u0001rAIA\u0003\u0003\u0013\ti!C\u0002\u0002\b\r\u0012qDU5dQ\u001a+hn\u0019;j_:\u0014\u0014i\u001d+p\u0019>twMQ5Gk:\u001cG/[8o!\rA\u00131\u0002\u0003\u0006U\u001d\u0011\ra\u000b\t\u0004Q\u0005=A!\u0002\u001b\b\u0005\u0004Y\u0003B\u0002\u001c\b\u0001\u0004\t\u0019\u0002\u0005\u0005\u0013q\u0005%\u0011QBA\u000b!\r\u0011\u0012qC\u0005\u0004\u00033i!\u0001\u0002'p]\u001eD#aB\u001e")
public interface Priority2FunctionConverters
extends Priority3FunctionConverters {
    public static /* synthetic */ Function2 enrichAsJavaBiConsumer$(Priority2FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaBiConsumer(sf);
    }

    default public <T, U> Function2<T, U, BoxedUnit> enrichAsJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaBiPredicate$(Priority2FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaBiPredicate(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaBiPredicate(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaFunction$(Priority2FunctionConverters $this, Function1 sf) {
        return $this.enrichAsJavaFunction(sf);
    }

    default public <T, R> Function1<T, R> enrichAsJavaFunction(Function1<T, R> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToDoubleBiFunction$(Priority2FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaToDoubleBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToIntBiFunction$(Priority2FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaToIntBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToIntBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToLongBiFunction$(Priority2FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaToLongBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToLongBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static void $init$(Priority2FunctionConverters $this) {
    }
}

