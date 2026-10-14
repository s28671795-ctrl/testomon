/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.scaladsl.RestartSource$;
import akka.stream.scaladsl.Source;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mv!B\u0005\u000b\u0011\u0003\tb!B\n\u000b\u0011\u0003!\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u000f\u0002\t\u00031\u0007BB\u000f\u0002\t\u0003\t)\u0001C\u0004\u00026\u0005!\t!a\u000e\t\u000f\u0005U\u0012\u0001\"\u0001\u0002f!9\u0011QG\u0001\u0005\u0002\u0005U\u0015!\u0004*fgR\f'\u000f^*pkJ\u001cWM\u0003\u0002\f\u0019\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u000e\u001d\u000511\u000f\u001e:fC6T\u0011aD\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005I\tQ\"\u0001\u0006\u0003\u001bI+7\u000f^1siN{WO]2f'\t\tQ\u0003\u0005\u0002\u001735\tqCC\u0001\u0019\u0003\u0015\u00198-\u00197b\u0013\tQrC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\t1b^5uQ\n\u000b7m[8gMV\u0011qD\n\u000b\u0005A\tce\n\u0006\u0002\"gA!!C\t\u00130\u0013\t\u0019#B\u0001\u0004T_V\u00148-\u001a\t\u0003K\u0019b\u0001\u0001B\u0003(\u0007\t\u0007\u0001FA\u0001U#\tIC\u0006\u0005\u0002\u0017U%\u00111f\u0006\u0002\b\u001d>$\b.\u001b8h!\t1R&\u0003\u0002//\t\u0019\u0011I\\=\u0011\u0005A\nT\"\u0001\b\n\u0005Ir!a\u0002(piV\u001bX\r\u001a\u0005\u0006i\r\u0001\r!N\u0001\u000eg>,(oY3GC\u000e$xN]=\u0011\u0007Y1\u0004(\u0003\u00028/\tIa)\u001e8di&|g\u000e\r\u0019\u0003sm\u0002BA\u0005\u0012%uA\u0011Qe\u000f\u0003\nyu\n\t\u0011!A\u0003\u0002!\u00121a\u0018\u00132\u0011\u0015!4\u00011\u0001?!\r1bg\u0010\u0019\u0003\u0001n\u0002BA\u0005\u0012BuA\u0011QE\n\u0005\u0006\u0007\u000e\u0001\r\u0001R\u0001\u000b[&t')Y2l_\u001a4\u0007CA#K\u001b\u00051%BA$I\u0003!!WO]1uS>t'BA%\u0018\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003\u0017\u001a\u0013aBR5oSR,G)\u001e:bi&|g\u000eC\u0003N\u0007\u0001\u0007A)\u0001\u0006nCb\u0014\u0015mY6pM\u001aDQaT\u0002A\u0002A\u000bAB]1oI>lg)Y2u_J\u0004\"AF)\n\u0005I;\"A\u0002#pk\ndW\r\u000b\u0004\u0004)^C&l\u0017\t\u0003-UK!AV\f\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003e\u000bA*V:fAQDW\rI8wKJdw.\u00193fI\u0002jW\r\u001e5pI\u0002:\b.[2iA\u0005\u001c7-\u001a9ug\u0002\n7n[1/gR\u0014X-Y7/%\u0016\u001cH/\u0019:u'\u0016$H/\u001b8hg\u0002Jgn\u001d;fC\u0012t\u0013!B:j]\u000e,\u0017%\u0001/\u0002\rIrcGL\u00191Q\t\u0019a\f\u0005\u0002`I6\t\u0001M\u0003\u0002bE\u0006!A.\u00198h\u0015\u0005\u0019\u0017\u0001\u00026bm\u0006L!!\u001a1\u0003\u0015\u0011+\u0007O]3dCR,G-\u0006\u0002hWR)\u0001\u000e_={wR\u0011\u0011\u000e\u001c\t\u0005%\tRw\u0006\u0005\u0002&W\u0012)q\u0005\u0002b\u0001Q!)A\u0007\u0002a\u0001[B\u0019aC\u000e81\u0005=\f\b\u0003\u0002\n#UB\u0004\"!J9\u0005\u0013I\u001c\u0018\u0011!A\u0001\u0006\u0003A#aA0%e!)A\u0007\u0002a\u0001iB\u0019aCN;1\u0005Y\f\b\u0003\u0002\n#oB\u0004\"!J6\t\u000b\r#\u0001\u0019\u0001#\t\u000b5#\u0001\u0019\u0001#\t\u000b=#\u0001\u0019\u0001)\t\u000bq$\u0001\u0019A?\u0002\u00175\f\u0007PU3ti\u0006\u0014Ho\u001d\t\u0003-yL!a`\f\u0003\u0007%sG\u000f\u000b\u0004\u0005)^C&l\u0017\u0015\u0003\ty+B!a\u0002\u0002\u0010Q!\u0011\u0011BA\u0015)\u0011\tY!!\u0005\u0011\u000bI\u0011\u0013QB\u0018\u0011\u0007\u0015\ny\u0001B\u0003(\u000b\t\u0007\u0001\u0006\u0003\u00045\u000b\u0001\u0007\u00111\u0003\t\u0005-Y\n)\u0002\r\u0003\u0002\u0018\u0005m\u0001C\u0002\n#\u0003\u001b\tI\u0002E\u0002&\u00037!1\"!\b\u0002 \u0005\u0005\t\u0011!B\u0001Q\t\u0019q\fJ\u001a\t\rQ*\u0001\u0019AA\u0011!\u00111b'a\t1\t\u0005\u0015\u00121\u0004\t\u0007%\t\n9#!\u0007\u0011\u0007\u0015\ny\u0001C\u0004\u0002,\u0015\u0001\r!!\f\u0002\u0011M,G\u000f^5oON\u0004B!a\f\u000225\tA\"C\u0002\u000241\u0011qBU3ti\u0006\u0014HoU3ui&twm]\u0001\u0016_:4\u0015-\u001b7ve\u0016\u001cx+\u001b;i\u0005\u0006\u001c7n\u001c4g+\u0011\tI$!\u0011\u0015\u0011\u0005m\u00121LA/\u0003?\"B!!\u0010\u0002DA)!CIA _A\u0019Q%!\u0011\u0005\u000b\u001d2!\u0019\u0001\u0015\t\rQ2\u0001\u0019AA#!\u00111b'a\u00121\t\u0005%\u0013Q\n\t\u0007%\t\ny$a\u0013\u0011\u0007\u0015\ni\u0005B\u0006\u0002P\u0005E\u0013\u0011!A\u0001\u0006\u0003A#aA0%i!1AG\u0002a\u0001\u0003'\u0002BA\u0006\u001c\u0002VA\"\u0011qKA'!\u0019\u0011\"%!\u0017\u0002LA\u0019Q%!\u0011\t\u000b\r3\u0001\u0019\u0001#\t\u000b53\u0001\u0019\u0001#\t\u000b=3\u0001\u0019\u0001))\r\u0019!v\u000b\u0017.\\Q\t1a,\u0006\u0003\u0002h\u0005=DCCA5\u0003\u0013\u000bY)!$\u0002\u0010R!\u00111NA9!\u0015\u0011\"%!\u001c0!\r)\u0013q\u000e\u0003\u0006O\u001d\u0011\r\u0001\u000b\u0005\u0007i\u001d\u0001\r!a\u001d\u0011\tY1\u0014Q\u000f\u0019\u0005\u0003o\nY\b\u0005\u0004\u0013E\u00055\u0014\u0011\u0010\t\u0004K\u0005mDaCA?\u0003\u007f\n\t\u0011!A\u0003\u0002!\u00121a\u0018\u00136\u0011\u0019!t\u00011\u0001\u0002\u0002B!aCNABa\u0011\t))a\u001f\u0011\rI\u0011\u0013qQA=!\r)\u0013q\u000e\u0005\u0006\u0007\u001e\u0001\r\u0001\u0012\u0005\u0006\u001b\u001e\u0001\r\u0001\u0012\u0005\u0006\u001f\u001e\u0001\r\u0001\u0015\u0005\u0006y\u001e\u0001\r! \u0015\u0007\u000fQ;\u0006LW.)\u0005\u001dqV\u0003BAL\u0003?#B!!'\u0002:R!\u00111TAQ!\u0015\u0011\"%!(0!\r)\u0013q\u0014\u0003\u0006O!\u0011\r\u0001\u000b\u0005\u0007i!\u0001\r!a)\u0011\tY1\u0014Q\u0015\u0019\u0005\u0003O\u000bY\u000b\u0005\u0004\u0013E\u0005u\u0015\u0011\u0016\t\u0004K\u0005-FaCAW\u0003_\u000b\t\u0011!A\u0003\u0002!\u00121a\u0018\u00137\u0011\u0019!\u0004\u00021\u0001\u00022B!aCNAZa\u0011\t),a+\u0011\rI\u0011\u0013qWAU!\r)\u0013q\u0014\u0005\b\u0003WA\u0001\u0019AA\u0017\u0001")
public final class RestartSource {
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(RestartSettings settings, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(settings, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, sourceFactory);
    }

    public static <T> Source<T, NotUsed> withBackoff(RestartSettings settings, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(settings, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sourceFactory);
    }

    @Deprecated
    public static <T> Source<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Source<T, ?>> sourceFactory) {
        return RestartSource$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, sourceFactory);
    }
}

