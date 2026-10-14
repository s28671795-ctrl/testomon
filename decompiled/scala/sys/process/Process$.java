/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.net.URL;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.collection.Seq;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessCreation;
import scala.sys.process.ProcessImpl;
import scala.sys.process.ProcessImpl$Future$;
import scala.sys.process.ProcessImpl$Spawn$;

public final class Process$
implements ProcessImpl,
ProcessCreation {
    public static final Process$ MODULE$ = new Process$();
    private static volatile ProcessImpl$Spawn$ Spawn$module;
    private static volatile ProcessImpl$Future$ Future$module;

    @Override
    public ProcessBuilder apply(String command) {
        return ProcessCreation.apply$((ProcessCreation)this, command);
    }

    @Override
    public ProcessBuilder apply(Seq<String> command) {
        return ProcessCreation.apply$((ProcessCreation)this, command);
    }

    @Override
    public ProcessBuilder apply(String command, Seq<String> arguments) {
        return ProcessCreation.apply$((ProcessCreation)this, command, arguments);
    }

    @Override
    public ProcessBuilder apply(String command, File cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return ProcessCreation.apply$((ProcessCreation)this, command, cwd, extraEnv);
    }

    @Override
    public ProcessBuilder apply(Seq<String> command, File cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return ProcessCreation.apply$((ProcessCreation)this, command, cwd, extraEnv);
    }

    @Override
    public ProcessBuilder apply(String command, Option<File> cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return ProcessCreation.apply$((ProcessCreation)this, command, cwd, extraEnv);
    }

    @Override
    public ProcessBuilder apply(Seq<String> command, Option<File> cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return ProcessCreation.apply$((ProcessCreation)this, command, cwd, extraEnv);
    }

    @Override
    public ProcessBuilder apply(java.lang.ProcessBuilder builder) {
        return ProcessCreation.apply$((ProcessCreation)this, builder);
    }

    @Override
    public ProcessBuilder.FileBuilder apply(File file) {
        return ProcessCreation.apply$((ProcessCreation)this, file);
    }

    @Override
    public ProcessBuilder.URLBuilder apply(URL url2) {
        return ProcessCreation.apply$((ProcessCreation)this, url2);
    }

    @Override
    public ProcessBuilder apply(boolean value) {
        return ProcessCreation.apply$((ProcessCreation)this, value);
    }

    @Override
    public ProcessBuilder apply(String name, Function0<Object> exitValue) {
        return ProcessCreation.apply$((ProcessCreation)this, name, exitValue);
    }

    @Override
    public <T> Seq<ProcessBuilder.Source> applySeq(Seq<T> builders, Function1<T, ProcessBuilder.Source> convert) {
        return ProcessCreation.applySeq$(this, builders, convert);
    }

    @Override
    public ProcessBuilder cat(ProcessBuilder.Source file, scala.collection.immutable.Seq<ProcessBuilder.Source> files) {
        return ProcessCreation.cat$(this, file, files);
    }

    @Override
    public ProcessBuilder cat(Seq<ProcessBuilder.Source> files) {
        return ProcessCreation.cat$(this, files);
    }

    @Override
    public ProcessImpl$Spawn$ Spawn() {
        if (Spawn$module == null) {
            this.Spawn$lzycompute$1();
        }
        return Spawn$module;
    }

    @Override
    public ProcessImpl$Future$ Future() {
        if (Future$module == null) {
            this.Future$lzycompute$1();
        }
        return Future$module;
    }

    private final void Spawn$lzycompute$1() {
        synchronized (this) {
            if (Spawn$module == null) {
                Spawn$module = new ProcessImpl$Spawn$(this);
            }
            return;
        }
    }

    private final void Future$lzycompute$1() {
        synchronized (this) {
            if (Future$module == null) {
                Future$module = new ProcessImpl$Future$(this);
            }
            return;
        }
    }

    private Process$() {
    }
}

