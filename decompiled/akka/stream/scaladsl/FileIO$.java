/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.IOResult;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.io.FileOutputStage;
import akka.stream.impl.io.FileSource;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.util.ByteString;
import java.io.File;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.Future;
import scala.runtime.ScalaRunTime$;

public final class FileIO$ {
    public static final FileIO$ MODULE$ = new FileIO$();

    public Source<ByteString, Future<IOResult>> fromFile(File f, int chunkSize) {
        return this.fromPath(f.toPath(), chunkSize);
    }

    public int fromFile$default$2() {
        return 8192;
    }

    public Source<ByteString, Future<IOResult>> fromPath(Path f, int chunkSize) {
        return this.fromPath(f, chunkSize, 0L);
    }

    public Source<ByteString, Future<IOResult>> fromPath(Path f, int chunkSize, long startPosition) {
        return Source$.MODULE$.fromGraph(new FileSource(f, chunkSize, startPosition)).withAttributes(Stages$DefaultAttributes$.MODULE$.fileSource());
    }

    public int fromPath$default$2() {
        return 8192;
    }

    public Sink<ByteString, Future<IOResult>> toFile(File f, Set<OpenOption> options) {
        return this.toPath(f.toPath(), options);
    }

    public Set<OpenOption> toFile$default$2() {
        return (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE}));
    }

    public Sink<ByteString, Future<IOResult>> toPath(Path f, Set<OpenOption> options) {
        return this.toPath(f, options, 0L);
    }

    public Sink<ByteString, Future<IOResult>> toPath(Path f, Set<OpenOption> options, long startPosition) {
        return Sink$.MODULE$.fromGraph(new FileOutputStage(f, startPosition, options));
    }

    public Set<OpenOption> toPath$default$2() {
        return (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE}));
    }

    private FileIO$() {
    }
}

