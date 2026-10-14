/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.IOResult;
import akka.stream.javadsl.FileIO$;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import java.io.File;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mr!B\u0007\u000f\u0011\u0003)b!B\f\u000f\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u0011\u0002\t\u0003\u0011\u0003\"B&\u0002\t\u0003a\u0005\"B\u0011\u0002\t\u00031\u0006\"B&\u0002\t\u0003Y\u0007\"B&\u0002\t\u0003\u0011\b\"\u0002@\u0002\t\u0003y\bbBA\b\u0003\u0011\u0005\u0011\u0011\u0003\u0005\u0007}\u0006!\t!!\u0006\t\u000f\u0005=\u0011\u0001\"\u0001\u0002&!9\u0011qB\u0001\u0005\u0002\u0005-\u0012A\u0002$jY\u0016LuJ\u0003\u0002\u0010!\u00059!.\u0019<bINd'BA\t\u0013\u0003\u0019\u0019HO]3b[*\t1#\u0001\u0003bW.\f7\u0001\u0001\t\u0003-\u0005i\u0011A\u0004\u0002\u0007\r&dW-S(\u0014\u0005\u0005I\u0002C\u0001\u000e\u001e\u001b\u0005Y\"\"\u0001\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yY\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002+\u00051Ao\u001c$jY\u0016$\"aI\u001d\u0011\tY!c\u0005L\u0005\u0003K9\u0011AaU5oWB\u0011qEK\u0007\u0002Q)\u0011\u0011FE\u0001\u0005kRLG.\u0003\u0002,Q\tQ!)\u001f;f'R\u0014\u0018N\\4\u0011\u00075\u001aT'D\u0001/\u0015\ty\u0003'\u0001\u0006d_:\u001cWO\u001d:f]RT!!K\u0019\u000b\u0003I\nAA[1wC&\u0011AG\f\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKB\u0011agN\u0007\u0002!%\u0011\u0001\b\u0005\u0002\t\u0013>\u0013Vm];mi\")!h\u0001a\u0001w\u0005\ta\r\u0005\u0002=\u007f5\tQH\u0003\u0002?c\u0005\u0011\u0011n\\\u0005\u0003\u0001v\u0012AAR5mK\"21AQ#G\u0011&\u0003\"AG\"\n\u0005\u0011[\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A$\u0002+U\u001bX\r\t1u_B\u000bG\u000f\u001b1!S:\u001cH/Z1e]\u0005)1/\u001b8dK\u0006\n!*A\u00033]QrS'\u0001\u0004u_B\u000bG\u000f\u001b\u000b\u0003G5CQA\u000f\u0003A\u00029\u0003\"a\u0014+\u000e\u0003AS!!\u0015*\u0002\t\u0019LG.\u001a\u0006\u0003'F\n1A\\5p\u0013\t)\u0006K\u0001\u0003QCRDWCA,b)\r\u0019\u0003,\u0017\u0005\u0006u\u0015\u0001\ra\u000f\u0005\u00065\u0016\u0001\raW\u0001\b_B$\u0018n\u001c8t!\raVlX\u0007\u0002a%\u0011a\f\r\u0002\u0004'\u0016$\bC\u00011b\u0019\u0001!QAY\u0003C\u0002\r\u00141a\u00149u#\t!w\r\u0005\u0002\u001bK&\u0011am\u0007\u0002\b\u001d>$\b.\u001b8h!\ty\u0005.\u0003\u0002j!\nQq\n]3o\u001fB$\u0018n\u001c8)\r\u0015\u0011UI\u0012%J+\ta\u0017\u000fF\u0002$[:DQA\u000f\u0004A\u00029CQA\u0017\u0004A\u0002=\u00042\u0001X/q!\t\u0001\u0017\u000fB\u0003c\r\t\u00071-\u0006\u0002tqR!1\u0005^;z\u0011\u0015Qt\u00011\u0001O\u0011\u0015Qv\u00011\u0001w!\raVl\u001e\t\u0003Ab$QAY\u0004C\u0002\rDQA_\u0004A\u0002m\fQb\u001d;beR\u0004vn]5uS>t\u0007C\u0001\u000e}\u0013\ti8D\u0001\u0003M_:<\u0017\u0001\u00034s_64\u0015\u000e\\3\u0015\t\u0005\u0005\u0011q\u0001\t\u0006-\u0005\ra\u0005L\u0005\u0004\u0003\u000bq!AB*pkJ\u001cW\rC\u0003;\u0011\u0001\u00071\bK\u0004\t\u0005\u0016\u000bY\u0001S%\"\u0005\u00055\u0011aF+tK\u0002\u0002gM]8n!\u0006$\b\u000e\u0019\u0011j]N$X-\u00193/\u0003!1'o\\7QCRDG\u0003BA\u0001\u0003'AQAO\u0005A\u00029#b!!\u0001\u0002\u0018\u0005e\u0001\"\u0002\u001e\u000b\u0001\u0004Y\u0004bBA\u000e\u0015\u0001\u0007\u0011QD\u0001\nG\",hn[*ju\u0016\u00042AGA\u0010\u0013\r\t\tc\u0007\u0002\u0004\u0013:$\bf\u0002\u0006C\u000b\u0006-\u0001*\u0013\u000b\u0007\u0003\u0003\t9#!\u000b\t\u000biZ\u0001\u0019\u0001(\t\u000f\u0005m1\u00021\u0001\u0002\u001eQA\u0011\u0011AA\u0017\u0003_\t\t\u0004C\u0003;\u0019\u0001\u0007a\nC\u0004\u0002\u001c1\u0001\r!!\b\t\u000bid\u0001\u0019A>")
public final class FileIO {
    public static Source<ByteString, CompletionStage<IOResult>> fromPath(Path f, int chunkSize, long startPosition) {
        return FileIO$.MODULE$.fromPath(f, chunkSize, startPosition);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromPath(Path f, int chunkSize) {
        return FileIO$.MODULE$.fromPath(f, chunkSize);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromFile(File f, int chunkSize) {
        return FileIO$.MODULE$.fromFile(f, chunkSize);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromPath(Path f) {
        return FileIO$.MODULE$.fromPath(f);
    }

    public static Source<ByteString, CompletionStage<IOResult>> fromFile(File f) {
        return FileIO$.MODULE$.fromFile(f);
    }

    public static <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toPath(Path f, Set<Opt> options, long startPosition) {
        return FileIO$.MODULE$.toPath(f, options, startPosition);
    }

    public static <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toPath(Path f, Set<Opt> options) {
        return FileIO$.MODULE$.toPath(f, options);
    }

    public static <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toFile(File f, Set<Opt> options) {
        return FileIO$.MODULE$.toFile(f, options);
    }

    public static Sink<ByteString, CompletionStage<IOResult>> toPath(Path f) {
        return FileIO$.MODULE$.toPath(f);
    }

    public static Sink<ByteString, CompletionStage<IOResult>> toFile(File f) {
        return FileIO$.MODULE$.toFile(f);
    }
}

