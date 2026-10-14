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
import scala.reflect.ScalaSignature;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.package$;

@ScalaSignature(bytes="\u0006\u0005-:QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQ!J\u0001\u0005\u0002\u0019BQAK\u0001\u0005\u0002\u0019\nq\u0001]1dW\u0006<WM\u0003\u0002\t\u0013\u00059\u0001O]8dKN\u001c(B\u0001\u0006\f\u0003\r\u0019\u0018p\u001d\u0006\u0002\u0019\u0005)1oY1mC\u000e\u0001\u0001CA\b\u0002\u001b\u00059!a\u00029bG.\fw-Z\n\u0004\u0003I1\u0002CA\n\u0015\u001b\u0005Y\u0011BA\u000b\f\u0005\u0019\te.\u001f*fMB\u0011qbF\u0005\u00031\u001d\u0011\u0001\u0003\u0015:pG\u0016\u001c8/S7qY&\u001c\u0017\u000e^:\u0002\rqJg.\u001b;?)\u0005q\u0011!B:uI&tW#A\u000f\u0011\u0005y\u0019S\"A\u0010\u000b\u0005\u0001\n\u0013AA5p\u0015\u0005\u0011\u0013\u0001\u00026bm\u0006L!\u0001J\u0010\u0003\u0017%s\u0007/\u001e;TiJ,\u0017-\\\u0001\u0007gR$w.\u001e;\u0016\u0003\u001d\u0002\"A\b\u0015\n\u0005%z\"a\u0003)sS:$8\u000b\u001e:fC6\faa\u001d;eKJ\u0014\b")
public final class package {
    public static PrintStream stderr() {
        return package$.MODULE$.stderr();
    }

    public static PrintStream stdout() {
        return package$.MODULE$.stdout();
    }

    public static InputStream stdin() {
        return package$.MODULE$.stdin();
    }

    public static ProcessBuilder stringSeqToProcess(Seq<String> command) {
        return package$.MODULE$.stringSeqToProcess(command);
    }

    public static ProcessBuilder stringToProcess(String command) {
        return package$.MODULE$.stringToProcess(command);
    }

    public static ProcessBuilder.URLBuilder urlToProcess(URL url2) {
        return package$.MODULE$.urlToProcess(url2);
    }

    public static ProcessBuilder.FileBuilder fileToProcess(File file) {
        return package$.MODULE$.fileToProcess(file);
    }

    public static ProcessBuilder builderToProcess(java.lang.ProcessBuilder builder) {
        return package$.MODULE$.builderToProcess(builder);
    }

    public static <T> Seq<ProcessBuilder.Source> buildersToProcess(Seq<T> builders, Function1<T, ProcessBuilder.Source> convert) {
        return package$.MODULE$.buildersToProcess(builders, convert);
    }
}

