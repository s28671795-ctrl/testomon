/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.javadsl.RestartSource$;
import akka.stream.javadsl.Source;
import java.time.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Uv!B\u0005\u000b\u0011\u0003\tb!B\n\u000b\u0011\u0003!\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u000f\u0002\t\u0003A\u0007BB\u000f\u0002\t\u0003\t9\u0001C\u0004\u00026\u0005!\t!a\u000e\t\u000f\u0005U\u0012\u0001\"\u0001\u0002d!9\u0011QG\u0001\u0005\u0002\u0005E\u0015!\u0004*fgR\f'\u000f^*pkJ\u001cWM\u0003\u0002\f\u0019\u00059!.\u0019<bINd'BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003%\u0005i\u0011A\u0003\u0002\u000e%\u0016\u001cH/\u0019:u'>,(oY3\u0014\u0005\u0005)\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002#\u0005Yq/\u001b;i\u0005\u0006\u001c7n\u001c4g+\tyR\u0005F\u0003!eqr4\t\u0005\u0003\u0013C\rr\u0013B\u0001\u0012\u000b\u0005\u0019\u0019v.\u001e:dKB\u0011A%\n\u0007\u0001\t\u001513A1\u0001(\u0005\u0005!\u0016C\u0001\u0015,!\t1\u0012&\u0003\u0002+/\t9aj\u001c;iS:<\u0007C\u0001\f-\u0013\tisCA\u0002B]f\u0004\"a\f\u0019\u000e\u00039I!!\r\b\u0003\u000f9{G/V:fI\")1g\u0001a\u0001i\u0005QQ.\u001b8CC\u000e\\wN\u001a4\u0011\u0005URT\"\u0001\u001c\u000b\u0005]B\u0014\u0001\u0002;j[\u0016T\u0011!O\u0001\u0005U\u00064\u0018-\u0003\u0002<m\tAA)\u001e:bi&|g\u000eC\u0003>\u0007\u0001\u0007A'\u0001\u0006nCb\u0014\u0015mY6pM\u001aDQaP\u0002A\u0002\u0001\u000bAB]1oI>lg)Y2u_J\u0004\"AF!\n\u0005\t;\"A\u0002#pk\ndW\rC\u0003E\u0007\u0001\u0007Q)A\u0007t_V\u00148-\u001a$bGR|'/\u001f\t\u0004\r.kU\"A$\u000b\u0005!K\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005)s\u0011\u0001\u00026ba&L!\u0001T$\u0003\u000f\r\u0013X-\u0019;peB\u0012a\n\u0015\t\u0005%\u0005\u001as\n\u0005\u0002%!\u0012I\u0011KUA\u0001\u0002\u0003\u0015\ta\n\u0002\u0004?\u0012\n\u0004\"\u0002#\u0004\u0001\u0004\u0019\u0006c\u0001$L)B\u0012Q\u000b\u0015\t\u0005%\u00052v\n\u0005\u0002%K!21\u0001W.]=~\u0003\"AF-\n\u0005i;\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A/\u0002\u0019V\u001bX\r\t;iK\u0002zg/\u001a:m_\u0006$W\r\u001a\u0011nKRDw\u000e\u001a\u0011xQ&\u001c\u0007\u000eI1dG\u0016\u0004Ho\u001d\u0011bW.\fgf\u001d;sK\u0006lgFU3ti\u0006\u0014HoU3ui&twm\u001d\u0011j]N$X-\u00193/\u0003\u0015\u0019\u0018N\\2fC\u0005\u0001\u0017A\u0002\u001a/m9\n\u0004\u0007\u000b\u0002\u0004EB\u00111MZ\u0007\u0002I*\u0011Q\rO\u0001\u0005Y\u0006tw-\u0003\u0002hI\nQA)\u001a9sK\u000e\fG/\u001a3\u0016\u0005%dGC\u00026n]>\u0004X\u000f\u0005\u0003\u0013C-t\u0003C\u0001\u0013m\t\u00151CA1\u0001(\u0011\u0015\u0019D\u00011\u00015\u0011\u0015iD\u00011\u00015\u0011\u0015yD\u00011\u0001A\u0011\u0015\tH\u00011\u0001s\u0003-i\u0017\r\u001f*fgR\f'\u000f^:\u0011\u0005Y\u0019\u0018B\u0001;\u0018\u0005\rIe\u000e\u001e\u0005\u0006\t\u0012\u0001\rA\u001e\t\u0004\r.;\bG\u0001={!\u0011\u0011\u0012e[=\u0011\u0005\u0011RH!C>}\u0003\u0003\u0005\tQ!\u0001(\u0005\ryFE\r\u0005\u0006\t\u0012\u0001\r! \t\u0004\r.s\bGA@{!\u0015\u0011\u0012%!\u0001z!\t!C\u000e\u000b\u0004\u00051ncfl\u0018\u0015\u0003\t\t,B!!\u0003\u0002\u0010Q1\u00111BA\t\u0003;\u0001RAE\u0011\u0002\u000e9\u00022\u0001JA\b\t\u00151SA1\u0001(\u0011\u001d\t\u0019\"\u0002a\u0001\u0003+\t\u0001b]3ui&twm\u001d\t\u0005\u0003/\tI\"D\u0001\r\u0013\r\tY\u0002\u0004\u0002\u0010%\u0016\u001cH/\u0019:u'\u0016$H/\u001b8hg\"1A)\u0002a\u0001\u0003?\u0001BAR&\u0002\"A\"\u00111EA\u0014!\u0019\u0011\u0012%!\u0004\u0002&A\u0019A%a\n\u0005\u0017\u0005%\u00121FA\u0001\u0002\u0003\u0015\ta\n\u0002\u0004?\u0012\u001a\u0004B\u0002#\u0006\u0001\u0004\ti\u0003\u0005\u0003G\u0017\u0006=\u0002\u0007BA\u0019\u0003O\u0001bAE\u0011\u00024\u0005\u0015\u0002c\u0001\u0013\u0002\u0010\u0005)rN\u001c$bS2,(/Z:XSRD')Y2l_\u001a4W\u0003BA\u001d\u0003\u007f!\"\"a\u000f\u0002B\u0005\r\u0013QIA$!\u0015\u0011\u0012%!\u0010/!\r!\u0013q\b\u0003\u0006M\u0019\u0011\ra\n\u0005\u0006g\u0019\u0001\r\u0001\u000e\u0005\u0006{\u0019\u0001\r\u0001\u000e\u0005\u0006\u007f\u0019\u0001\r\u0001\u0011\u0005\u0007\t\u001a\u0001\r!!\u0013\u0011\t\u0019[\u00151\n\u0019\u0005\u0003\u001b\n\t\u0006\u0005\u0004\u0013C\u0005u\u0012q\n\t\u0004I\u0005ECaCA*\u0003+\n\t\u0011!A\u0003\u0002\u001d\u00121a\u0018\u00135\u0011\u0019!e\u00011\u0001\u0002XA!aiSA-a\u0011\tY&!\u0015\u0011\rI\t\u0013QLA(!\r!\u0013q\b\u0015\u0007\ra[FLX0)\u0005\u0019\u0011W\u0003BA3\u0003W\"B\"a\u001a\u0002n\u0005=\u0014\u0011OA:\u0003k\u0002RAE\u0011\u0002j9\u00022\u0001JA6\t\u00151sA1\u0001(\u0011\u0015\u0019t\u00011\u00015\u0011\u0015it\u00011\u00015\u0011\u0015yt\u00011\u0001A\u0011\u0015\tx\u00011\u0001s\u0011\u0019!u\u00011\u0001\u0002xA!aiSA=a\u0011\tY(a \u0011\rI\t\u0013\u0011NA?!\r!\u0013q\u0010\u0003\f\u0003\u0003\u000b\u0019)!A\u0001\u0002\u000b\u0005qEA\u0002`IUBa\u0001R\u0004A\u0002\u0005\u0015\u0005\u0003\u0002$L\u0003\u000f\u0003D!!#\u0002\u0000A1!#IAF\u0003{\u00022\u0001JA6Q\u00199\u0001l\u0017/_?\"\u0012qAY\u000b\u0005\u0003'\u000bI\n\u0006\u0004\u0002\u0016\u0006m\u0015Q\u0014\t\u0006%\u0005\n9J\f\t\u0004I\u0005eE!\u0002\u0014\t\u0005\u00049\u0003bBA\n\u0011\u0001\u0007\u0011Q\u0003\u0005\u0007\t\"\u0001\r!a(\u0011\t\u0019[\u0015\u0011\u0015\u0019\u0005\u0003G\u000b9\u000b\u0005\u0004\u0013C\u0005]\u0015Q\u0015\t\u0004I\u0005\u001dFaCAU\u0003W\u000b\t\u0011!A\u0003\u0002\u001d\u00121a\u0018\u00137\u0011\u0019!\u0005\u00021\u0001\u0002.B!aiSAXa\u0011\t\t,a*\u0011\rI\t\u00131WAS!\r!\u0013\u0011\u0014")
public final class RestartSource {
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(RestartSettings settings, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(settings, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, sourceFactory);
    }

    public static <T> Source<T, NotUsed> withBackoff(RestartSettings settings, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(settings, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, sourceFactory);
    }
}

