/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxedUnit;
import scala.runtime.Null$;
import scala.runtime.java8.JFunction0$mcV$sp;
import scala.sys.process.BasicIO$Uncloseable$;
import scala.sys.process.ProcessIO;
import scala.sys.process.ProcessLogger;

public final class BasicIO$ {
    public static final BasicIO$ MODULE$ = new BasicIO$();
    private static final String Newline = System.lineSeparator();
    private static final Function1<OutputStream, BoxedUnit> connectToStdIn = (Function1<OutputStream, Object> & Serializable)x$3 -> {
        BasicIO$.$anonfun$connectToStdIn$1(x$3);
        return BoxedUnit.UNIT;
    };
    private static final Function1<OutputStream, BoxedUnit> connectNoOp = (Function1<OutputStream, Object> & Serializable)x$4 -> {
        BasicIO$.$anonfun$connectNoOp$1(x$4);
        return BoxedUnit.UNIT;
    };

    public final int BufferSize() {
        return 8192;
    }

    public final String Newline() {
        return Newline;
    }

    public ProcessIO apply(boolean withIn, Function1<String, BoxedUnit> output, Option<ProcessLogger> log) {
        return new ProcessIO(this.input(withIn), (Function1<InputStream, BoxedUnit>)(Function1<InputStream, Object> & Serializable)in -> {
            BasicIO$.$anonfun$processFully$1(output, in);
            return BoxedUnit.UNIT;
        }, this.getErr(log));
    }

    public ProcessIO apply(boolean withIn, Appendable buffer, Option<ProcessLogger> log) {
        return new ProcessIO(this.input(withIn), this.processFully(buffer), this.getErr(log));
    }

    public ProcessIO apply(boolean withIn, ProcessLogger log) {
        return new ProcessIO(this.input(withIn), this.processOutFully(log), this.processErrFully(log));
    }

    public Function1<InputStream, BoxedUnit> getErr(Option<ProcessLogger> log) {
        if (log instanceof Some) {
            ProcessLogger lg = (ProcessLogger)((Some)log).value();
            return this.processErrFully(lg);
        }
        if (None$.MODULE$.equals(log)) {
            return this.toStdErr();
        }
        throw new MatchError(log);
    }

    private Function1<InputStream, BoxedUnit> processErrFully(ProcessLogger log) {
        return arg_0 -> BasicIO$.$anonfun$processFully$1$adapted((Function1<String, Object> & Serializable)x$1 -> {
            log.err((Function0<String> & Serializable)() -> x$1);
            return BoxedUnit.UNIT;
        }, arg_0);
    }

    private Function1<InputStream, BoxedUnit> processOutFully(ProcessLogger log) {
        return arg_0 -> BasicIO$.$anonfun$processFully$1$adapted((Function1<String, Object> & Serializable)x$2 -> {
            log.out((Function0<String> & Serializable)() -> x$2);
            return BoxedUnit.UNIT;
        }, arg_0);
    }

    public void close(Closeable c) {
        try {
            c.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public Function1<InputStream, BoxedUnit> processFully(Appendable buffer) {
        return this.processFully(this.appendLine(buffer));
    }

    public Function1<InputStream, BoxedUnit> processFully(Function1<String, BoxedUnit> processLine) {
        return (Function1<InputStream, Object> & Serializable)in -> {
            BasicIO$.$anonfun$processFully$1(output, in);
            return BoxedUnit.UNIT;
        };
    }

    public void processLinesFully(Function1<String, BoxedUnit> processLine, Function0<String> readLine) {
        while (BasicIO$.working$1()) {
            String string;
            try {
                string = readLine.apply();
            }
            catch (Throwable throwable) {
                if (throwable instanceof InterruptedException) {
                    BasicIO$.halting$1();
                    string = null;
                }
                if (throwable instanceof IOException && !BasicIO$.working$1()) {
                    BasicIO$.halting$1();
                    string = null;
                }
                throw throwable;
            }
            String readFully$1_line = string;
            if (readFully$1_line == null) break;
            processLine.apply(readFully$1_line);
        }
    }

    public void connectToIn(OutputStream o) {
        this.transferFully(BasicIO$Uncloseable$.MODULE$.protect(System.in), o);
    }

    public Function1<OutputStream, BoxedUnit> input(boolean connect2) {
        if (connect2) {
            return this.connectToStdIn();
        }
        return this.connectNoOp();
    }

    public Function1<OutputStream, BoxedUnit> connectToStdIn() {
        return connectToStdIn;
    }

    public Function1<OutputStream, BoxedUnit> connectNoOp() {
        return connectNoOp;
    }

    public ProcessIO standard(boolean connectInput) {
        return this.standard(this.input(connectInput));
    }

    public ProcessIO standard(Function1<OutputStream, BoxedUnit> in) {
        return new ProcessIO(in, this.toStdOut(), this.toStdErr());
    }

    public Function1<InputStream, BoxedUnit> toStdErr() {
        return (Function1<InputStream, Object> & Serializable)in -> {
            BasicIO$.MODULE$.transferFully(in, System.err);
            return BoxedUnit.UNIT;
        };
    }

    public Function1<InputStream, BoxedUnit> toStdOut() {
        return (Function1<InputStream, Object> & Serializable)in -> {
            BasicIO$.MODULE$.transferFully(in, System.out);
            return BoxedUnit.UNIT;
        };
    }

    public void transferFully(InputStream in, OutputStream out) {
        try {
            this.transferFullyImpl(in, out);
            return;
        }
        catch (Throwable x$5) {
            JFunction0$mcV$sp onIOInterrupt_handler = () -> {};
            Object var5_4 = null;
            Serializable catchExpr$1 = new Serializable(onIOInterrupt_handler){
                private static final long serialVersionUID = 0L;
                private final Function0 handler$2;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    if (x1 instanceof InterruptedIOException) {
                        return (B1)this.handler$2.apply();
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Throwable x1) {
                    return x1 instanceof InterruptedIOException;
                }
                {
                    this.handler$2 = handler$2;
                }
            };
            if (catchExpr$1.isDefinedAt(x$5)) {
                catchExpr$1.apply(x$5);
                return;
            }
            throw x$5;
        }
    }

    private Function1<String, BoxedUnit> appendLine(Appendable buffer) {
        return (Function1<String, Object> & Serializable)line -> {
            BasicIO$.$anonfun$appendLine$1(buffer, line);
            return BoxedUnit.UNIT;
        };
    }

    private void transferFullyImpl(InputStream in, OutputStream out) {
        byte[] buffer = new byte[8192];
        this.loop$1(in, buffer, out);
        in.close();
    }

    public static final /* synthetic */ String $anonfun$processFully$2(BufferedReader reader$1) {
        return reader$1.readLine();
    }

    public static final /* synthetic */ void $anonfun$processFully$1(Function1 processLine$1, InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));){
            while (MODULE$.working$1()) {
                String string;
                try {
                    string = reader.readLine();
                }
                catch (Throwable throwable) {
                    if (throwable instanceof InterruptedException) {
                        BasicIO$.halting$1();
                        string = null;
                    }
                    if (throwable instanceof IOException && !BasicIO$.working$1()) {
                        BasicIO$.halting$1();
                        string = null;
                    }
                    throw throwable;
                }
                String processLinesFully_readFully$1_line = string;
                if (processLinesFully_readFully$1_line == null) break;
                processLine$1.apply(processLinesFully_readFully$1_line);
            }
            Object var4_3 = null;
            Object var5_5 = null;
        }
    }

    private static final boolean working$1() {
        return !Thread.currentThread().isInterrupted();
    }

    private static final Null$ halting$1() {
        Thread.currentThread().interrupt();
        return null;
    }

    private final void readFully$1(Function0 readLine$1, Function1 processLine$2) {
        while (BasicIO$.working$1()) {
            String string;
            try {
                string = (String)readLine$1.apply();
            }
            catch (Throwable throwable) {
                if (throwable instanceof InterruptedException) {
                    BasicIO$.halting$1();
                    string = null;
                }
                if (throwable instanceof IOException && !BasicIO$.working$1()) {
                    BasicIO$.halting$1();
                    string = null;
                }
                throw throwable;
            }
            String line = string;
            if (line == null) break;
            processLine$2.apply(line);
        }
    }

    public static final /* synthetic */ void $anonfun$connectToStdIn$1(OutputStream x$3) {
    }

    public static final /* synthetic */ void $anonfun$connectNoOp$1(OutputStream x$4) {
    }

    public static final /* synthetic */ void $anonfun$appendLine$1(Appendable buffer$1, String line) {
        buffer$1.append(line);
        buffer$1.append(MODULE$.Newline());
    }

    private final void loop$1(InputStream in$2, byte[] buffer$2, OutputStream out$2) {
        int byteCount;
        while ((byteCount = in$2.read(buffer$2)) > 0) {
            boolean bl;
            out$2.write(buffer$2, 0, byteCount);
            try {
                out$2.flush();
                bl = true;
            }
            catch (IOException iOException) {
                bl = false;
            }
            if (bl) continue;
        }
    }

    private BasicIO$() {
    }
}

