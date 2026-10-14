/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;
import scala.jdk.javaapi.FutureConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}:Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\u0002aAQAN\u0001\u0005\u0002]\n\u0001CR;ukJ,7i\u001c8wKJ$XM]:\u000b\u0005\u001dA\u0011a\u00026bm\u0006\f\u0007/\u001b\u0006\u0003\u0013)\t1A\u001b3l\u0015\u0005Y\u0011!B:dC2\f7\u0001\u0001\t\u0003\u001d\u0005i\u0011A\u0002\u0002\u0011\rV$XO]3D_:4XM\u001d;feN\u001c\"!A\t\u0011\u0005I\u0019R\"\u0001\u0006\n\u0005QQ!AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u001b\u00051\u0011m\u001d&bm\u0006,\"!\u0007\u0014\u0015\u0005iy\u0003cA\u000e#I5\tAD\u0003\u0002\u001e=\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005}\u0001\u0013\u0001B;uS2T\u0011!I\u0001\u0005U\u00064\u0018-\u0003\u0002$9\ty1i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W\r\u0005\u0002&M1\u0001A!B\u0014\u0004\u0005\u0004A#!\u0001+\u0012\u0005%b\u0003C\u0001\n+\u0013\tY#BA\u0004O_RD\u0017N\\4\u0011\u0005Ii\u0013B\u0001\u0018\u000b\u0005\r\te.\u001f\u0005\u0006a\r\u0001\r!M\u0001\u0002MB\u0019!\u0007\u000e\u0013\u000e\u0003MR!!\b\u0006\n\u0005U\u001a$A\u0002$viV\u0014X-A\u0004bgN\u001b\u0017\r\\1\u0016\u0005aZDCA\u001d=!\r\u0011DG\u000f\t\u0003Km\"Qa\n\u0003C\u0002!BQ!\u0010\u0003A\u0002y\n!aY:\u0011\u0007m\u0011#\b")
public final class FutureConverters {
    public static <T> Future<T> asScala(CompletionStage<T> cs) {
        return FutureConverters$.MODULE$.asScala(cs);
    }

    public static <T> CompletionStage<T> asJava(Future<T> f) {
        return FutureConverters$.MODULE$.asJava(f);
    }
}

