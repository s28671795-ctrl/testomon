/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ClassicActorSystemProvider;
import akka.annotation.InternalApi;
import akka.util.FlightRecorderLoader$;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00051;a\u0001B\u0003\t\u0002\u001dIaAB\u0006\u0006\u0011\u00039A\u0002C\u0003\u0014\u0003\u0011\u0005Q\u0003C\u0003\u0017\u0003\u0011\u0005q#\u0001\u000bGY&<\u0007\u000e\u001e*fG>\u0014H-\u001a:M_\u0006$WM\u001d\u0006\u0003\r\u001d\tA!\u001e;jY*\t\u0001\"\u0001\u0003bW.\f\u0007C\u0001\u0006\u0002\u001b\u0005)!\u0001\u0006$mS\u001eDGOU3d_J$WM\u001d'pC\u0012,'o\u0005\u0002\u0002\u001bA\u0011a\"E\u0007\u0002\u001f)\t\u0001#A\u0003tG\u0006d\u0017-\u0003\u0002\u0013\u001f\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\u0013\u0005!An\\1e+\tAB\u0004\u0006\u0003\u001a[U\u0012EC\u0001\u000e&!\tYB\u0004\u0004\u0001\u0005\u000bu\u0019!\u0019\u0001\u0010\u0003\u0003Q\u000b\"a\b\u0012\u0011\u00059\u0001\u0013BA\u0011\u0010\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AD\u0012\n\u0005\u0011z!aA!os\"9aeAA\u0001\u0002\b9\u0013AC3wS\u0012,gnY3%cA\u0019\u0001f\u000b\u000e\u000e\u0003%R!AK\b\u0002\u000fI,g\r\\3di&\u0011A&\u000b\u0002\t\u00072\f7o\u001d+bO\")af\u0001a\u0001_\u0005!1-Y:q!\t\u00014'D\u00012\u0015\t\u0011t!A\u0003bGR|'/\u0003\u00025c\tQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\")ag\u0001a\u0001o\u0005!a-]2o!\tAtH\u0004\u0002:{A\u0011!hD\u0007\u0002w)\u0011A\bF\u0001\u0007yI|w\u000e\u001e \n\u0005yz\u0011A\u0002)sK\u0012,g-\u0003\u0002A\u0003\n11\u000b\u001e:j]\u001eT!AP\b\t\u000b\r\u001b\u0001\u0019\u0001\u000e\u0002\u0011\u0019\fG\u000e\u001c2bG.D#!A#\u0011\u0005\u0019KU\"A$\u000b\u0005!;\u0011AC1o]>$\u0018\r^5p]&\u0011!j\u0012\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001\u000b\u0002")
@InternalApi
public final class FlightRecorderLoader {
    public static <T> T load(ClassicActorSystemProvider casp, String fqcn, T fallback, ClassTag<T> evidence$1) {
        return FlightRecorderLoader$.MODULE$.load(casp, fqcn, fallback, evidence$1);
    }
}

