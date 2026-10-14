/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import scala.Function1;
import scala.collection.Seq;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessImplicits;

public final class package$
implements ProcessImplicits {
    public static final package$ MODULE$ = new package$();

    @Override
    public <T> Seq<ProcessBuilder.Source> buildersToProcess(Seq<T> builders, Function1<T, ProcessBuilder.Source> convert) {
        return ProcessImplicits.buildersToProcess$(this, builders, convert);
    }

    @Override
    public ProcessBuilder builderToProcess(java.lang.ProcessBuilder builder) {
        return ProcessImplicits.builderToProcess$(this, builder);
    }

    @Override
    public ProcessBuilder.FileBuilder fileToProcess(File file) {
        return ProcessImplicits.fileToProcess$(this, file);
    }

    @Override
    public ProcessBuilder.URLBuilder urlToProcess(URL url2) {
        return ProcessImplicits.urlToProcess$(this, url2);
    }

    @Override
    public ProcessBuilder stringToProcess(String command) {
        return ProcessImplicits.stringToProcess$(this, command);
    }

    @Override
    public ProcessBuilder stringSeqToProcess(Seq<String> command) {
        return ProcessImplicits.stringSeqToProcess$(this, command);
    }

    public InputStream stdin() {
        return System.in;
    }

    public PrintStream stdout() {
        return System.out;
    }

    public PrintStream stderr() {
        return System.err;
    }

    private package$() {
    }
}

