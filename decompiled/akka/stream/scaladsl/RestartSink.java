/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.scaladsl.RestartSink$;
import akka.stream.scaladsl.Sink;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=r!\u0002\u0004\b\u0011\u0003qa!\u0002\t\b\u0011\u0003\t\u0002\"\u0002\r\u0002\t\u0003I\u0002\"\u0002\u000e\u0002\t\u0003Y\u0002\"\u0002\u000e\u0002\t\u0003\u0019\u0007\"\u0002\u000e\u0002\t\u0003y\u0018a\u0003*fgR\f'\u000f^*j].T!\u0001C\u0005\u0002\u0011M\u001c\u0017\r\\1eg2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tqAA\u0006SKN$\u0018M\u001d;TS:\\7CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AD\u0001\fo&$\bNQ1dW>4g-\u0006\u0002\u001dGQ!QdP%L)\tq\u0002\u0007\u0005\u0003\u0010?\u0005b\u0013B\u0001\u0011\b\u0005\u0011\u0019\u0016N\\6\u0011\u0005\t\u001aC\u0002\u0001\u0003\u0006I\r\u0011\r!\n\u0002\u0002)F\u0011a%\u000b\t\u0003'\u001dJ!\u0001\u000b\u000b\u0003\u000f9{G\u000f[5oOB\u00111CK\u0005\u0003WQ\u00111!\u00118z!\tic&D\u0001\f\u0013\ty3BA\u0004O_R,6/\u001a3\t\u000bE\u001a\u0001\u0019\u0001\u001a\u0002\u0017MLgn\u001b$bGR|'/\u001f\t\u0004'M*\u0014B\u0001\u001b\u0015\u0005%1UO\\2uS>t\u0007\u0007\r\u00027qA!qbH\u00118!\t\u0011\u0003\bB\u0005:u\u0005\u0005\t\u0011!B\u0001K\t\u0019q\fJ\u0019\t\u000bE\u001a\u0001\u0019A\u001e\u0011\u0007M\u0019D\b\r\u0002>qA!qb\b 8!\t\u00113\u0005C\u0003A\u0007\u0001\u0007\u0011)\u0001\u0006nS:\u0014\u0015mY6pM\u001a\u0004\"AQ$\u000e\u0003\rS!\u0001R#\u0002\u0011\u0011,(/\u0019;j_:T!A\u0012\u000b\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002I\u0007\nqa)\u001b8ji\u0016$UO]1uS>t\u0007\"\u0002&\u0004\u0001\u0004\t\u0015AC7bq\n\u000b7m[8gM\")Aj\u0001a\u0001\u001b\u0006a!/\u00198e_64\u0015m\u0019;peB\u00111CT\u0005\u0003\u001fR\u0011a\u0001R8vE2,\u0007FB\u0002R)V;\u0006\f\u0005\u0002\u0014%&\u00111\u000b\u0006\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002-\u0006aUk]3!i\",\u0007e\u001c<fe2|\u0017\rZ3eA5,G\u000f[8eA]D\u0017n\u00195!C\u000e\u001cW\r\u001d;tA\u0005\\7.\u0019\u0018tiJ,\u0017-\u001c\u0018SKN$\u0018M\u001d;TKR$\u0018N\\4tA%t7\u000f^3bI:\nQa]5oG\u0016\f\u0013!W\u0001\u0007e92d&\r\u0019)\u0005\rY\u0006C\u0001/b\u001b\u0005i&B\u00010`\u0003\u0011a\u0017M\\4\u000b\u0003\u0001\fAA[1wC&\u0011!-\u0018\u0002\u000b\t\u0016\u0004(/Z2bi\u0016$WC\u00013i)\u0015)WO^<y)\t1\u0017\u000e\u0005\u0003\u0010?\u001dd\u0003C\u0001\u0012i\t\u0015!CA1\u0001&\u0011\u0015\tD\u00011\u0001k!\r\u00192g\u001b\u0019\u0003Y:\u0004BaD\u0010h[B\u0011!E\u001c\u0003\n_B\f\t\u0011!A\u0003\u0002\u0015\u00121a\u0018\u00133\u0011\u0015\tD\u00011\u0001r!\r\u00192G\u001d\u0019\u0003g:\u0004BaD\u0010u[B\u0011!\u0005\u001b\u0005\u0006\u0001\u0012\u0001\r!\u0011\u0005\u0006\u0015\u0012\u0001\r!\u0011\u0005\u0006\u0019\u0012\u0001\r!\u0014\u0005\u0006s\u0012\u0001\rA_\u0001\f[\u0006D(+Z:uCJ$8\u000f\u0005\u0002\u0014w&\u0011A\u0010\u0006\u0002\u0004\u0013:$\bF\u0002\u0003R)V;\u0006\f\u000b\u0002\u00057V!\u0011\u0011AA\u0005)\u0011\t\u0019!a\t\u0015\t\u0005\u0015\u00111\u0002\t\u0006\u001f}\t9\u0001\f\t\u0004E\u0005%A!\u0002\u0013\u0006\u0005\u0004)\u0003BB\u0019\u0006\u0001\u0004\ti\u0001\u0005\u0003\u0014g\u0005=\u0001\u0007BA\t\u0003+\u0001baD\u0010\u0002\b\u0005M\u0001c\u0001\u0012\u0002\u0016\u0011Y\u0011qCA\r\u0003\u0003\u0005\tQ!\u0001&\u0005\ryFe\r\u0005\u0007c\u0015\u0001\r!a\u0007\u0011\tM\u0019\u0014Q\u0004\u0019\u0005\u0003?\t)\u0002\u0005\u0004\u0010?\u0005\u0005\u00121\u0003\t\u0004E\u0005%\u0001bBA\u0013\u000b\u0001\u0007\u0011qE\u0001\tg\u0016$H/\u001b8hgB!\u0011\u0011FA\u0016\u001b\u0005I\u0011bAA\u0017\u0013\ty!+Z:uCJ$8+\u001a;uS:<7\u000f")
public final class RestartSink {
    public static <T> Sink<T, NotUsed> withBackoff(RestartSettings settings, Function0<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(settings, sinkFactory);
    }

    @Deprecated
    public static <T> Sink<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sinkFactory);
    }

    @Deprecated
    public static <T> Sink<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, sinkFactory);
    }
}

