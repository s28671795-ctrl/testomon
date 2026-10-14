/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.annotation.DoNotInherit;
import akka.japi.Pair;
import akka.stream.javadsl.MergeHub$;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.MergeHub;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Er!B\u0007\u000f\u0011\u0003)b!B\f\u000f\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001caB\u0011\u0002!\u0003\r\nC\t\u0005\u0006G\r1\t\u0001\n\u0004\u0005S\u00051!\u0006\u0003\u0005.\u000b\t\u0005\t\u0015!\u0003/\u0011\u0015yR\u0001\"\u0001?\u0011\u0015\u0019S\u0001\"\u0011%\u0011\u0015A\u0015\u0001\"\u0001J\u0011\u00151\u0018\u0001\"\u0001x\u0011\u0019A\u0015\u0001\"\u0001\u0002\u0010!1a/\u0001C\u0001\u0003?\t\u0001\"T3sO\u0016DUO\u0019\u0006\u0003\u001fA\tqA[1wC\u0012\u001cHN\u0003\u0002\u0012%\u000511\u000f\u001e:fC6T\u0011aE\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005Y\tQ\"\u0001\b\u0003\u00115+'oZ3Ik\n\u001c\"!A\r\u0011\u0005iiR\"A\u000e\u000b\u0003q\tQa]2bY\u0006L!AH\u000e\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQCA\bEe\u0006Lg.\u001b8h\u0007>tGO]8m'\t\u0019\u0011$\u0001\tee\u0006Lg.\u00118e\u0007>l\u0007\u000f\\3uKR\tQ\u0005\u0005\u0002\u001bM%\u0011qe\u0007\u0002\u0005+:LG/\u000b\u0002\u0004\u000b\t\u0019BI]1j]&twmQ8oiJ|G.S7qYN\u0019Q!G\u0016\u0011\u00051\u001aQ\"A\u0001\u0002\u0003\r\u0004\"a\f\u001f\u000f\u0005ARdBA\u00199\u001d\t\u0011tG\u0004\u00024m5\tAG\u0003\u00026)\u00051AH]8pizJ\u0011aE\u0005\u0003#II!!\u000f\t\u0002\u0011M\u001c\u0017\r\\1eg2L!!D\u001e\u000b\u0005e\u0002\u0012BA\u0011>\u0015\ti1\b\u0006\u0002@\u0001B\u0011A&\u0002\u0005\u0006[\u001d\u0001\rA\f\u0015\u0003\u0007\t\u0003\"a\u0011$\u000e\u0003\u0011S!!\u0012\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002H\t\naAi\u001c(pi&s\u0007.\u001a:ji\u0006\u0011qNZ\u000b\u0003\u0015B#2a\u00131r!\u00111BJT-\n\u00055s!AB*pkJ\u001cW\r\u0005\u0002P!2\u0001A!B)\n\u0005\u0004\u0011&!\u0001+\u0012\u0005M3\u0006C\u0001\u000eU\u0013\t)6DA\u0004O_RD\u0017N\\4\u0011\u0005i9\u0016B\u0001-\u001c\u0005\r\te.\u001f\t\u0005-isE,\u0003\u0002\\\u001d\t!1+\u001b8l!\tif,D\u0001\u0013\u0013\ty&CA\u0004O_R,6/\u001a3\t\u000b\u0005L\u0001\u0019\u00012\u0002\u000b\rd\u0017M\u001f>\u0011\u0007\r<gJ\u0004\u0002eKB\u00111gG\u0005\u0003Mn\ta\u0001\u0015:fI\u00164\u0017B\u00015j\u0005\u0015\u0019E.Y:t\u0015\t17\u0004\u000b\u0002aWB\u0011An\\\u0007\u0002[*\u0011aNE\u0001\u0005kRLG.\u0003\u0002q[\n1QO\\;tK\u0012DQA]\u0005A\u0002M\fQ\u0003]3s!J|G-^2fe\n+hMZ3s'&TX\r\u0005\u0002\u001bi&\u0011Qo\u0007\u0002\u0004\u0013:$\u0018\u0001D<ji\"$%/Y5oS:<WC\u0001=|)\u0015I\u0018qAA\u0007!\u00111BJ\u001f?\u0011\u0005=[H!B)\u000b\u0005\u0004\u0011\u0006CB?\u0002\u0002\u0005\u00151&D\u0001\u007f\u0015\ty(#\u0001\u0003kCBL\u0017bAA\u0002}\n!\u0001+Y5s!\u00111\"L\u001f/\t\r\u0005T\u0001\u0019AA\u0005!\r\u0019wM\u001f\u0015\u0004\u0003\u000fY\u0007\"\u0002:\u000b\u0001\u0004\u0019X\u0003BA\t\u0003/!B!a\u0005\u0002\u001cA1a\u0003TA\u000b\u00033\u00012aTA\f\t\u0015\t6B1\u0001S!\u00151\",!\u0006]\u0011\u0019\t7\u00021\u0001\u0002\u001eA!1mZA\u000b+\u0011\t\t#a\n\u0015\t\u0005\r\u0012Q\u0006\t\u0007-1\u000b)#!\u000b\u0011\u0007=\u000b9\u0003B\u0003R\u0019\t\u0007!\u000b\u0005\u0004~\u0003\u0003\tYc\u000b\t\u0006-i\u000b)\u0003\u0018\u0005\u0007C2\u0001\r!a\f\u0011\t\r<\u0017Q\u0005")
public final class MergeHub {
    public static <T> Source<T, Pair<Sink<T, NotUsed>, DrainingControl>> withDraining(Class<T> clazz) {
        return MergeHub$.MODULE$.withDraining(clazz);
    }

    public static <T> Source<T, Sink<T, NotUsed>> of(Class<T> clazz) {
        return MergeHub$.MODULE$.of(clazz);
    }

    public static <T> Source<T, Pair<Sink<T, NotUsed>, DrainingControl>> withDraining(Class<T> clazz, int perProducerBufferSize) {
        return MergeHub$.MODULE$.withDraining(clazz, perProducerBufferSize);
    }

    public static <T> Source<T, Sink<T, NotUsed>> of(Class<T> clazz, int perProducerBufferSize) {
        return MergeHub$.MODULE$.of(clazz, perProducerBufferSize);
    }

    @DoNotInherit
    public static interface DrainingControl {
        public void drainAndComplete();
    }

    private static final class DrainingControlImpl
    implements DrainingControl {
        private final MergeHub.DrainingControl c;

        @Override
        public void drainAndComplete() {
            this.c.drainAndComplete();
        }

        public DrainingControlImpl(MergeHub.DrainingControl c) {
            this.c = c;
        }
    }
}

