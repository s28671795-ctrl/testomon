/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.BroadcastHub$;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!G\u0001\u0005\u0002)\u000bAB\u0011:pC\u0012\u001c\u0017m\u001d;Ik\nT!a\u0002\u0005\u0002\u000f)\fg/\u00193tY*\u0011\u0011BC\u0001\u0007gR\u0014X-Y7\u000b\u0003-\tA!Y6lC\u000e\u0001\u0001C\u0001\b\u0002\u001b\u00051!\u0001\u0004\"s_\u0006$7-Y:u\u0011V\u00147CA\u0001\u0012!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!D\u0001\u0003_\u001a,\"aG\u0011\u0015\u0007q\tT\t\u0005\u0003\u000f;}Q\u0013B\u0001\u0010\u0007\u0005\u0011\u0019\u0016N\\6\u0011\u0005\u0001\nC\u0002\u0001\u0003\u0006E\r\u0011\ra\t\u0002\u0002)F\u0011Ae\n\t\u0003%\u0015J!AJ\n\u0003\u000f9{G\u000f[5oOB\u0011!\u0003K\u0005\u0003SM\u00111!\u00118z!\u0011q1fH\u0017\n\u000512!AB*pkJ\u001cW\r\u0005\u0002/_5\t!\"\u0003\u00021\u0015\t9aj\u001c;Vg\u0016$\u0007\"\u0002\u001a\u0004\u0001\u0004\u0019\u0014!B2mCjT\bc\u0001\u001b<?9\u0011Q'\u000f\t\u0003mMi\u0011a\u000e\u0006\u0003q1\ta\u0001\u0010:p_Rt\u0014B\u0001\u001e\u0014\u0003\u0019\u0001&/\u001a3fM&\u0011A(\u0010\u0002\u0006\u00072\f7o\u001d\u0006\u0003uMA#!M \u0011\u0005\u0001\u001bU\"A!\u000b\u0005\tS\u0011\u0001B;uS2L!\u0001R!\u0003\rUtWo]3e\u0011\u001515\u00011\u0001H\u0003)\u0011WO\u001a4feNK'0\u001a\t\u0003%!K!!S\n\u0003\u0007%sG/\u0006\u0002L\u001dR\u0011A\n\u0015\t\u0005\u001duiu\n\u0005\u0002!\u001d\u0012)!\u0005\u0002b\u0001GA!abK'.\u0011\u0015\u0011D\u00011\u0001R!\r!4(\u0014")
public final class BroadcastHub {
    public static <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz) {
        return BroadcastHub$.MODULE$.of(clazz);
    }

    public static <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, int bufferSize) {
        return BroadcastHub$.MODULE$.of(clazz, bufferSize);
    }
}

