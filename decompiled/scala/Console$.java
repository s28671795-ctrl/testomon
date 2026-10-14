/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import scala.Function0;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.io.AnsiColor;
import scala.util.DynamicVariable;

public final class Console$
implements AnsiColor {
    public static final Console$ MODULE$ = new Console$();
    private static final DynamicVariable<PrintStream> outVar = new DynamicVariable<PrintStream>(System.out);
    private static final DynamicVariable<PrintStream> errVar = new DynamicVariable<PrintStream>(System.err);
    private static final DynamicVariable<BufferedReader> inVar = new DynamicVariable<BufferedReader>(new BufferedReader(new InputStreamReader(System.in)));

    public void setOutDirect(PrintStream out) {
        outVar.value_$eq(out);
    }

    public void setErrDirect(PrintStream err) {
        errVar.value_$eq(err);
    }

    public void setInDirect(BufferedReader in) {
        inVar.value_$eq(in);
    }

    public PrintStream out() {
        return outVar.value();
    }

    public PrintStream err() {
        return errVar.value();
    }

    public BufferedReader in() {
        return inVar.value();
    }

    public <T> T withOut(PrintStream out, Function0<T> thunk) {
        return outVar.withValue(out, thunk);
    }

    public <T> T withOut(OutputStream out, Function0<T> thunk) {
        PrintStream withOut_out = new PrintStream(out);
        return outVar.withValue(withOut_out, thunk);
    }

    public <T> T withErr(PrintStream err, Function0<T> thunk) {
        return errVar.withValue(err, thunk);
    }

    public <T> T withErr(OutputStream err, Function0<T> thunk) {
        PrintStream withErr_err = new PrintStream(err);
        return errVar.withValue(withErr_err, thunk);
    }

    public <T> T withIn(Reader reader, Function0<T> thunk) {
        return inVar.withValue(new BufferedReader(reader), thunk);
    }

    public <T> T withIn(InputStream in, Function0<T> thunk) {
        InputStreamReader withIn_reader = new InputStreamReader(in);
        return inVar.withValue(new BufferedReader(withIn_reader), thunk);
    }

    public void print(Object obj) {
        this.out().print(obj == null ? "null" : obj.toString());
    }

    public void flush() {
        this.out().flush();
    }

    public void println() {
        this.out().println();
    }

    public void println(Object x) {
        this.out().println(x);
    }

    public void printf(String text, Seq<Object> args2) {
        this.out().print(StringOps$.MODULE$.format$extension(text, args2));
    }

    private Console$() {
    }
}

