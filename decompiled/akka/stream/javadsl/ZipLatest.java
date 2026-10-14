/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.FanInShape2;
import akka.stream.Graph;
import akka.stream.javadsl.ZipLatest$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAaaO\u0001!\u0002\u001ba\u0014!\u0003.ja2\u000bG/Z:u\u0015\t9\u0001\"A\u0004kCZ\fGm\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001A\u0011a\"A\u0007\u0002\r\tI!,\u001b9MCR,7\u000f^\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003\u0019\u0019'/Z1uKV\u00191$J\u0018\u0016\u0003q\u0001B!\b\u0010!o5\t\u0001\"\u0003\u0002 \u0011\t)qI]1qQB)Q$I\u0012/c%\u0011!\u0005\u0003\u0002\f\r\u0006t\u0017J\\*iCB,'\u0007\u0005\u0002%K1\u0001A!\u0002\u0014\u0004\u0005\u00049#!A!\u0012\u0005!Z\u0003C\u0001\n*\u0013\tQ3CA\u0004O_RD\u0017N\\4\u0011\u0005Ia\u0013BA\u0017\u0014\u0005\r\te.\u001f\t\u0003I=\"Q\u0001M\u0002C\u0002\u001d\u0012\u0011A\u0011\t\u0005eU\u001ac&D\u00014\u0015\t!$\"\u0001\u0003kCBL\u0017B\u0001\u001c4\u0005\u0011\u0001\u0016-\u001b:\u0011\u0005aJT\"\u0001\u0006\n\u0005iR!a\u0002(piV\u001bX\rZ\u0001\b?R|\u0007+Y5s!\u0015i\u0004iK\u0016C\u001b\u0005q$BA 4\u0003!1WO\\2uS>t\u0017BA!?\u0005%1UO\\2uS>t'\u0007\u0005\u00033k-Z\u0003")
public final class ZipLatest {
    public static <A, B> Graph<FanInShape2<A, B, Pair<A, B>>, NotUsed> create() {
        return ZipLatest$.MODULE$.create();
    }
}

