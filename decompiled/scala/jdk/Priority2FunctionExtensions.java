/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import scala.Function1;
import scala.Function2;
import scala.jdk.Priority3FunctionExtensions;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005maa\u0002\u0005\n!\u0003\r\tA\u0004\u0005\u0006/\u0001!\t\u0001\u0007\u0005\u00069\u0001!\u0019!\b\u0005\u0006{\u0001!\u0019A\u0010\u0005\u0006\u001b\u0002!\u0019A\u0014\u0005\u0006;\u0002!\u0019A\u0018\u0005\u0006[\u0002!\u0019A\u001c\u0005\u0006{\u0002!\u0019A \u0002\u001c!JLwN]5usJ2UO\\2uS>tW\t\u001f;f]NLwN\\:\u000b\u0005)Y\u0011a\u00016eW*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001y1\u0003\u0005\u0002\u0011#5\t1\"\u0003\u0002\u0013\u0017\t1\u0011I\\=SK\u001a\u0004\"\u0001F\u000b\u000e\u0003%I!AF\u0005\u00037A\u0013\u0018n\u001c:jif\u001cd)\u001e8di&|g.\u0012=uK:\u001c\u0018n\u001c8t\u0003\u0019!\u0013N\\5uIQ\t\u0011\u0004\u0005\u0002\u00115%\u00111d\u0003\u0002\u0005+:LG/\u0001\ff]JL7\r[!t\u0015\u00064\u0018MQ5D_:\u001cX/\\3s+\rq\u0002F\r\u000b\u0003?Q\u0002B\u0001I\u0012'c9\u0011A#I\u0005\u0003E%\t\u0001CR;oGRLwN\\,sCB\u0004XM]:\n\u0005\u0011*#!\u0007*jG\"4UO\\2uS>t''Q:CS\u000e{gn];nKJT!AI\u0005\u0011\u0005\u001dBC\u0002\u0001\u0003\u0006S\t\u0011\rA\u000b\u0002\u0002)F\u00111F\f\t\u0003!1J!!L\u0006\u0003\u000f9{G\u000f[5oOB\u0011\u0001cL\u0005\u0003a-\u00111!\u00118z!\t9#\u0007B\u00034\u0005\t\u0007!FA\u0001V\u0011\u0015)$\u00011\u00017\u0003\t\u0019h\rE\u0003\u0011o\u0019\n\u0014$\u0003\u00029\u0017\tIa)\u001e8di&|gN\r\u0015\u0003\u0005i\u0002\"\u0001E\u001e\n\u0005qZ!AB5oY&tW-A\ff]JL7\r[!t\u0015\u00064\u0018MQ5Qe\u0016$\u0017nY1uKV\u0019q\b\u0012$\u0015\u0005\u0001;\u0005\u0003\u0002\u0011B\u0007\u0016K!AQ\u0013\u00035IK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N\u0014\u0015\u000e\u0015:fI&\u001c\u0017\r^3\u0011\u0005\u001d\"E!B\u0015\u0004\u0005\u0004Q\u0003CA\u0014G\t\u0015\u00194A1\u0001+\u0011\u0015)4\u00011\u0001I!\u0015\u0001rgQ#J!\t\u0001\"*\u0003\u0002L\u0017\t9!i\\8mK\u0006t\u0007FA\u0002;\u0003Q)gN]5dQ\u0006\u001b(*\u0019<b\rVt7\r^5p]V\u0019q\n\u0016,\u0015\u0005AC\u0006\u0003\u0002\u0011R'VK!AU\u0013\u0003/IK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N4UO\\2uS>t\u0007CA\u0014U\t\u0015ICA1\u0001+!\t9c\u000bB\u0003X\t\t\u0007!FA\u0001S\u0011\u0015)D\u00011\u0001Z!\u0011\u0001\"lU+\n\u0005m[!!\u0003$v]\u000e$\u0018n\u001c82Q\t!!(\u0001\u0010f]JL7\r[!t\u0015\u00064\u0018\rV8E_V\u0014G.\u001a\"j\rVt7\r^5p]V\u0019q\f\u001a4\u0015\u0005\u0001<\u0007\u0003\u0002\u0011bG\u0016L!AY\u0013\u0003CIK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N$v\u000eR8vE2,')\u001b$v]\u000e$\u0018n\u001c8\u0011\u0005\u001d\"G!B\u0015\u0006\u0005\u0004Q\u0003CA\u0014g\t\u0015\u0019TA1\u0001+\u0011\u0015)T\u00011\u0001i!\u0015\u0001rgY3j!\t\u0001\".\u0003\u0002l\u0017\t1Ai\\;cY\u0016D#!\u0002\u001e\u00027\u0015t'/[2i\u0003NT\u0015M^1U_&sGOQ5Gk:\u001cG/[8o+\ryGO\u001e\u000b\u0003a^\u0004B\u0001I9tk&\u0011!/\n\u0002\u001f%&\u001c\u0007NR;oGRLwN\u001c\u001aBgR{\u0017J\u001c;CS\u001a+hn\u0019;j_:\u0004\"a\n;\u0005\u000b%2!\u0019\u0001\u0016\u0011\u0005\u001d2H!B\u001a\u0007\u0005\u0004Q\u0003\"B\u001b\u0007\u0001\u0004A\b#\u0002\t8gVL\bC\u0001\t{\u0013\tY8BA\u0002J]RD#A\u0002\u001e\u00029\u0015t'/[2i\u0003NT\u0015M^1U_2{gn\u001a\"j\rVt7\r^5p]V)q0!\u0003\u0002\u000eQ!\u0011\u0011AA\b!\u001d\u0001\u00131AA\u0004\u0003\u0017I1!!\u0002&\u0005}\u0011\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHk\u001c'p]\u001e\u0014\u0015NR;oGRLwN\u001c\t\u0004O\u0005%A!B\u0015\b\u0005\u0004Q\u0003cA\u0014\u0002\u000e\u0011)1g\u0002b\u0001U!1Qg\u0002a\u0001\u0003#\u0001\u0002\u0002E\u001c\u0002\b\u0005-\u00111\u0003\t\u0004!\u0005U\u0011bAA\f\u0017\t!Aj\u001c8hQ\t9!\b")
public interface Priority2FunctionExtensions
extends Priority3FunctionExtensions {
    public static /* synthetic */ Function2 enrichAsJavaBiConsumer$(Priority2FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaBiConsumer(sf);
    }

    default public <T, U> Function2<T, U, BoxedUnit> enrichAsJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaBiPredicate$(Priority2FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaBiPredicate(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaBiPredicate(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function1 enrichAsJavaFunction$(Priority2FunctionExtensions $this, Function1 sf) {
        return $this.enrichAsJavaFunction(sf);
    }

    default public <T, R> Function1<T, R> enrichAsJavaFunction(Function1<T, R> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToDoubleBiFunction$(Priority2FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaToDoubleBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToIntBiFunction$(Priority2FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaToIntBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToIntBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static /* synthetic */ Function2 enrichAsJavaToLongBiFunction$(Priority2FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaToLongBiFunction(sf);
    }

    default public <T, U> Function2<T, U, Object> enrichAsJavaToLongBiFunction(Function2<T, U, Object> sf) {
        return sf;
    }

    public static void $init$(Priority2FunctionExtensions $this) {
    }
}

