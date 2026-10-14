/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.IOResult;
import akka.stream.scaladsl.FileIO$;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.util.ByteString;
import java.io.File;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import scala.collection.immutable.Set;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-r!B\u0007\u000f\u0011\u0003)b!B\f\u000f\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u0011\u0002\t\u0003\u0011\u0003bB(\u0002#\u0003%\t\u0001\u0015\u0005\u00067\u0006!\t\u0001\u0018\u0005\bO\u0006\t\n\u0011\"\u0001Q\u0011\u0015Y\u0016\u0001\"\u0001i\u0011\u0015\u0001\u0018\u0001\"\u0001r\u0011%\t\u0019\"AI\u0001\n\u0003\t)\u0002C\u0004\u0002\u001a\u0005!\t!a\u0007\t\u0013\u0005\u0005\u0012!%A\u0005\u0002\u0005U\u0001bBA\r\u0003\u0011\u0005\u00111E\u0001\u0007\r&dW-S(\u000b\u0005=\u0001\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005E\u0011\u0012AB:ue\u0016\fWNC\u0001\u0014\u0003\u0011\t7n[1\u0004\u0001A\u0011a#A\u0007\u0002\u001d\t1a)\u001b7f\u0013>\u001b\"!A\r\u0011\u0005iiR\"A\u000e\u000b\u0003q\tQa]2bY\u0006L!AH\u000e\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQ#\u0001\u0005ge>lg)\u001b7f)\r\u0019c\u0007\u0011\t\u0005-\u00112C&\u0003\u0002&\u001d\t11k\\;sG\u0016\u0004\"a\n\u0016\u000e\u0003!R!!\u000b\n\u0002\tU$\u0018\u000e\\\u0005\u0003W!\u0012!BQ=uKN#(/\u001b8h!\ri\u0003GM\u0007\u0002])\u0011qfG\u0001\u000bG>t7-\u001e:sK:$\u0018BA\u0019/\u0005\u00191U\u000f^;sKB\u00111\u0007N\u0007\u0002!%\u0011Q\u0007\u0005\u0002\t\u0013>\u0013Vm];mi\")qg\u0001a\u0001q\u0005\ta\r\u0005\u0002:}5\t!H\u0003\u0002<y\u0005\u0011\u0011n\u001c\u0006\u0002{\u0005!!.\u0019<b\u0013\ty$H\u0001\u0003GS2,\u0007bB!\u0004!\u0003\u0005\rAQ\u0001\nG\",hn[*ju\u0016\u0004\"AG\"\n\u0005\u0011[\"aA%oi\"21AR%K\u00196\u0003\"AG$\n\u0005![\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A&\u0002-U\u001bX\r\t1ge>l\u0007+\u0019;iA\u0002Jgn\u001d;fC\u0012\fQa]5oG\u0016\f\u0013AT\u0001\u0006e9\"d&N\u0001\u0013MJ|WNR5mK\u0012\"WMZ1vYR$#'F\u0001RU\t\u0011%kK\u0001T!\t!\u0016,D\u0001V\u0015\t1v+A\u0005v]\u000eDWmY6fI*\u0011\u0001lG\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001.V\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\tMJ|W\u000eU1uQR\u00191%\u00184\t\u000b]*\u0001\u0019\u00010\u0011\u0005}#W\"\u00011\u000b\u0005\u0005\u0014\u0017\u0001\u00024jY\u0016T!a\u0019\u001f\u0002\u00079Lw.\u0003\u0002fA\n!\u0001+\u0019;i\u0011\u001d\tU\u0001%AA\u0002\t\u000b!C\u001a:p[B\u000bG\u000f\u001b\u0013eK\u001a\fW\u000f\u001c;%eQ!1%\u001b6l\u0011\u00159t\u00011\u0001_\u0011\u0015\tu\u00011\u0001C\u0011\u0015aw\u00011\u0001n\u00035\u0019H/\u0019:u!>\u001c\u0018\u000e^5p]B\u0011!D\\\u0005\u0003_n\u0011A\u0001T8oO\u00061Ao\u001c$jY\u0016$2A];w!\u001112O\n\u0017\n\u0005Qt!\u0001B*j].DQa\u000e\u0005A\u0002aBqa\u001e\u0005\u0011\u0002\u0003\u0007\u00010A\u0004paRLwN\\:\u0011\u000be\f\t!a\u0002\u000f\u0005it\bCA>\u001c\u001b\u0005a(BA?\u0015\u0003\u0019a$o\\8u}%\u0011qpG\u0001\u0007!J,G-\u001a4\n\t\u0005\r\u0011Q\u0001\u0002\u0004'\u0016$(BA@\u001c!\ry\u0016\u0011B\u0005\u0004\u0003\u0017\u0001'AC(qK:|\u0005\u000f^5p]\":\u0001BR%\u0002\u00101k\u0015EAA\t\u0003Q)6/\u001a\u0011ai>\u0004\u0016\r\u001e5aA%t7\u000f^3bI\u0006\u0001Bo\u001c$jY\u0016$C-\u001a4bk2$HEM\u000b\u0003\u0003/Q#\u0001\u001f*\u0002\rQ|\u0007+\u0019;i)\u0015\u0011\u0018QDA\u0010\u0011\u00159$\u00021\u0001_\u0011\u001d9(\u0002%AA\u0002a\f\u0001\u0003^8QCRDG\u0005Z3gCVdG\u000f\n\u001a\u0015\u000fI\f)#a\n\u0002*!)q\u0007\u0004a\u0001=\")q\u000f\u0004a\u0001q\")A\u000e\u0004a\u0001[\u0002")
public final class FileIO {
    public static Sink<ByteString, Future<IOResult>> toPath(Path f, Set<OpenOption> options, long startPosition) {
        return FileIO$.MODULE$.toPath(f, options, startPosition);
    }

    public static Set<OpenOption> toPath$default$2() {
        return FileIO$.MODULE$.toPath$default$2();
    }

    public static Sink<ByteString, Future<IOResult>> toPath(Path f, Set<OpenOption> options) {
        return FileIO$.MODULE$.toPath(f, options);
    }

    public static Set<OpenOption> toFile$default$2() {
        return FileIO$.MODULE$.toFile$default$2();
    }

    public static Sink<ByteString, Future<IOResult>> toFile(File f, Set<OpenOption> options) {
        return FileIO$.MODULE$.toFile(f, options);
    }

    public static Source<ByteString, Future<IOResult>> fromPath(Path f, int chunkSize, long startPosition) {
        return FileIO$.MODULE$.fromPath(f, chunkSize, startPosition);
    }

    public static int fromPath$default$2() {
        return FileIO$.MODULE$.fromPath$default$2();
    }

    public static Source<ByteString, Future<IOResult>> fromPath(Path f, int chunkSize) {
        return FileIO$.MODULE$.fromPath(f, chunkSize);
    }

    public static int fromFile$default$2() {
        return FileIO$.MODULE$.fromFile$default$2();
    }

    public static Source<ByteString, Future<IOResult>> fromFile(File f, int chunkSize) {
        return FileIO$.MODULE$.fromFile(f, chunkSize);
    }
}

