/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.IOException;
import scala.Function0;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.sys.process.processInternal$;

@ScalaSignature(bytes="\u0006\u0005\u0005MtAB\u000b\u0017\u0011\u00031BD\u0002\u0004\u001f-!\u0005ac\b\u0005\u0006I\u0005!\tA\n\u0005\bO\u0005\u0011\r\u0011\"\u0002)\u0011\u0019a\u0013\u0001)A\u0007S\u0015!Q&\u0001\u0001/\u000b\u0011\u0001\u0015\u0001A!\u0006\t!\u000b\u0001!S\u0003\u0005\u0017\u0006\u0001A*\u0002\u0003O\u0003\u0001yU\u0001B)\u0002\u0001I+A\u0001V\u0001\u0001+\u0016!1,\u0001\u0001]\u000b\u0011y\u0016\u0001\u00011\u0006\t\t\f\u0001aY\u0003\u0005Q\u0006\u0001\u0011\u000eC\u0003}\u0003\u0011\u0005Q\u0010C\u0004\u0002*\u0005!\t!a\u000b\t\u000f\u0005u\u0012\u0001\"\u0001\u0002@!9\u0011QJ\u0001\u0005\u0002\u0005=\u0003bBA0\u0003\u0011\u0005\u0011\u0011M\u0001\u0010aJ|7-Z:t\u0013:$XM\u001d8bY*\u0011q\u0003G\u0001\baJ|7-Z:t\u0015\tI\"$A\u0002tsNT\u0011aG\u0001\u0006g\u000e\fG.\u0019\t\u0003;\u0005i\u0011A\u0006\u0002\u0010aJ|7-Z:t\u0013:$XM\u001d8bYN\u0011\u0011\u0001\t\t\u0003C\tj\u0011AG\u0005\u0003Gi\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003q\tA\u0002\u001d:pG\u0016\u001c8\u000fR3ck\u001e,\u0012!\u000b\t\u0003C)J!a\u000b\u000e\u0003\u000f\t{w\u000e\\3b]\u0006i\u0001O]8dKN\u001cH)\u001a2vO\u0002\u0012\u0011\u0003J3rIEl\u0017M]6%OJ,\u0017\r^3s+\ryCG\u0010\t\u0005CA\u0012T(\u0003\u000225\ty\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u00024i1\u0001AAB\u001b\u0006\u0011\u000b\u0007aGA\u0001B#\t9$\b\u0005\u0002\"q%\u0011\u0011H\u0007\u0002\b\u001d>$\b.\u001b8h!\t\t3(\u0003\u0002=5\t\u0019\u0011I\\=\u0011\u0005MrDAB \u0006\t\u000b\u0007aGA\u0001C\u0005%\u0019En\\:fC\ndW\r\u0005\u0002C\u000f6\t1I\u0003\u0002E\u000b\u0006\u0011\u0011n\u001c\u0006\u0002\r\u0006!!.\u0019<b\u0013\t\u00015I\u0001\u0003GS2,\u0007C\u0001\"K\u0013\tA5IA\u0006J\u001f\u0016C8-\u001a9uS>t\u0007C\u0001\"N\u0013\tY5I\u0001\fJ]R,'O];qi\u0016$\u0017jT#yG\u0016\u0004H/[8o!\t\u0011\u0005+\u0003\u0002O\u0007\nY\u0011J\u001c9viN#(/Z1n!\t\u00115+\u0003\u0002R\u0007\nA!\n\u0015:pG\u0016\u001c8\u000f\u0005\u0002W36\tqK\u0003\u0002Y\u000b\u0006!A.\u00198h\u0013\tQvKA\u0004Qe>\u001cWm]:\u0003\u001f)\u0003&o\\2fgN\u0014U/\u001b7eKJ\u0004\"AV/\n\u0005y;&A\u0004)s_\u000e,7o\u001d\"vS2$WM\u001d\u0002\r\u001fV$\b/\u001e;TiJ,\u0017-\u001c\t\u0003\u0005\u0006L!aX\"\u0003\u0007U\u0013F\n\u0005\u0002eO6\tQM\u0003\u0002g\u000b\u0006\u0019a.\u001a;\n\u0005\t,'aB*z]\u000e4\u0016M]\u000b\u0003UB\u00042a\u001b8p\u001b\u0005a'BA7\u001b\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003Q2\u0004\"a\r9\u0005\u000bE|!\u0019\u0001\u001c\u0003\u0003QCcaD:wofT\bCA\u0011u\u0013\t)(D\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001y\u0003\u001d+6/\u001a\u0011aU\u00064\u0018ML;uS2t3m\u001c8dkJ\u0014XM\u001c;/\u0019&t7.\u001a3CY>\u001c7.\u001b8h#V,W/\u001a\u0011xSRD\u0007eY1qC\u000eLG/\u001f\u00112A\u0002Jgn\u001d;fC\u0012t\u0013!B:j]\u000e,\u0017%A>\u0002\rIr\u0013g\r\u00185\u0003\u001dyg.\u0012:s_J,2A`A\u000f)\ry\u0018q\u0004\t\b\u0003\u0003)\u00111AA\u000e\u001b\u0005\t\u0001\u0003BA\u0003\u0003+qA!a\u0002\u0002\u00129!\u0011\u0011BA\b\u001b\t\tYAC\u0002\u0002\u000e\u0015\na\u0001\u0010:p_Rt\u0014\"A\u000e\n\u0007\u0005M!$A\u0004qC\u000e\\\u0017mZ3\n\t\u0005]\u0011\u0011\u0004\u0002\n)\"\u0014xn^1cY\u0016T1!a\u0005\u001b!\r\u0019\u0014Q\u0004\u0003\u0006cB\u0011\rA\u000e\u0005\b\u0003C\u0001\u0002\u0019AA\u0012\u0003\u001dA\u0017M\u001c3mKJ\u0004r!IA\u0013\u0003\u0007\tY\"C\u0002\u0002(i\u0011\u0011BR;oGRLwN\\\u0019\u0002\u001b=t\u0017jT%oi\u0016\u0014(/\u001e9u+\u0011\ti#a\r\u0015\t\u0005=\u0012Q\u0007\t\b\u0003\u0003)\u00111AA\u0019!\r\u0019\u00141\u0007\u0003\u0006cF\u0011\rA\u000e\u0005\t\u0003C\tB\u00111\u0001\u00028A)\u0011%!\u000f\u00022%\u0019\u00111\b\u000e\u0003\u0011q\u0012\u0017P\\1nKz\n1b\u001c8J]R,'O];qiV!\u0011\u0011IA$)\u0011\t\u0019%!\u0013\u0011\u000f\u0005\u0005Q!a\u0001\u0002FA\u00191'a\u0012\u0005\u000bE\u0014\"\u0019\u0001\u001c\t\u0011\u0005\u0005\"\u0003\"a\u0001\u0003\u0017\u0002R!IA\u001d\u0003\u000b\n\u0011\"[8GC&dWO]3\u0016\t\u0005E\u0013q\u000b\u000b\u0005\u0003'\nI\u0006E\u0004\u0002\u0002\u0015\t\u0019!!\u0016\u0011\u0007M\n9\u0006B\u0003r'\t\u0007a\u0007C\u0004\u0002\"M\u0001\r!a\u0017\u0011\u000f\u0005\n)#!\u0018\u0002VA\u0019\u0011\u0011\u0001\u0005\u0002\u0007\u0011\u0014w\r\u0006\u0003\u0002d\u0005%\u0004cA\u0011\u0002f%\u0019\u0011q\r\u000e\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003W\"\u0002\u0019AA7\u0003\u0011i7oZ:\u0011\t\u0005\nyGO\u0005\u0004\u0003cR\"A\u0003\u001fsKB,\u0017\r^3e}\u0001")
public final class processInternal {
    public static void dbg(Seq<Object> msgs) {
        processInternal$.MODULE$.dbg(msgs);
    }

    public static <T> PartialFunction<Throwable, T> ioFailure(Function1<IOException, T> handler) {
        return processInternal$.MODULE$.ioFailure(handler);
    }

    public static <T> PartialFunction<Throwable, T> onInterrupt(Function0<T> handler) {
        return processInternal$.MODULE$.onInterrupt(handler);
    }

    public static <T> PartialFunction<Throwable, T> onIOInterrupt(Function0<T> handler) {
        return processInternal$.MODULE$.onIOInterrupt(handler);
    }

    public static <T> PartialFunction<Throwable, T> onError(Function1<Throwable, T> handler) {
        return processInternal$.MODULE$.onError(handler);
    }

    public static boolean processDebug() {
        return processInternal$.MODULE$.processDebug();
    }
}

