/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.IOResult;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.package$;
import akka.stream.scaladsl.package$SinkToCompletionStage$;
import akka.stream.scaladsl.package$SourceToCompletionStage$;
import akka.util.ByteString;
import akka.util.ccompat.package$JavaConverters$;
import java.io.File;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletionStage;

public final class FileIO$ {
    public static final FileIO$ MODULE$ = new FileIO$();

    public Sink<ByteString, CompletionStage<IOResult>> toFile(File f) {
        return this.toPath(f.toPath());
    }

    public Sink<ByteString, CompletionStage<IOResult>> toPath(Path f) {
        return new Sink<ByteString, CompletionStage<IOResult>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.FileIO$.MODULE$.toPath(f, akka.stream.scaladsl.FileIO$.MODULE$.toPath$default$2()))));
    }

    public <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toFile(File f, Set<Opt> options) {
        return this.toPath(f.toPath());
    }

    public <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toPath(Path f, Set<Opt> options) {
        return new Sink<ByteString, CompletionStage<IOResult>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.FileIO$.MODULE$.toPath(f, package$JavaConverters$.MODULE$.SetHasAsScala(options).asScala().toSet()))));
    }

    public <Opt extends OpenOption> Sink<ByteString, CompletionStage<IOResult>> toPath(Path f, Set<Opt> options, long startPosition) {
        return new Sink<ByteString, CompletionStage<IOResult>>(package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SinkToCompletionStage(akka.stream.scaladsl.FileIO$.MODULE$.toPath(f, package$JavaConverters$.MODULE$.SetHasAsScala(options).asScala().toSet(), startPosition))));
    }

    public Source<ByteString, CompletionStage<IOResult>> fromFile(File f) {
        return this.fromPath(f.toPath());
    }

    public Source<ByteString, CompletionStage<IOResult>> fromPath(Path f) {
        return this.fromPath(f, 8192);
    }

    public Source<ByteString, CompletionStage<IOResult>> fromFile(File f, int chunkSize) {
        return this.fromPath(f.toPath(), chunkSize);
    }

    public Source<ByteString, CompletionStage<IOResult>> fromPath(Path f, int chunkSize) {
        return new Source<ByteString, CompletionStage<IOResult>>(package$SourceToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SourceToCompletionStage(akka.stream.scaladsl.FileIO$.MODULE$.fromPath(f, chunkSize))));
    }

    public Source<ByteString, CompletionStage<IOResult>> fromPath(Path f, int chunkSize, long startPosition) {
        return new Source<ByteString, CompletionStage<IOResult>>(package$SourceToCompletionStage$.MODULE$.toCompletionStage$extension(package$.MODULE$.SourceToCompletionStage(akka.stream.scaladsl.FileIO$.MODULE$.fromPath(f, chunkSize, startPosition))));
    }

    private FileIO$() {
    }
}

