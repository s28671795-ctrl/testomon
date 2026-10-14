/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.InputStream;
import java.io.OutputStream;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Y3Aa\u0004\t\u0003/!AA\u0004\u0001BC\u0002\u0013\u0005Q\u0004\u0003\u0005-\u0001\t\u0005\t\u0015!\u0003\u001f\u0011!i\u0003A!b\u0001\n\u0003q\u0003\u0002C\u001a\u0001\u0005\u0003\u0005\u000b\u0011B\u0018\t\u0011Q\u0002!Q1A\u0005\u00029B\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\tm\u0001\u0011)\u0019!C\u0001o!A1\b\u0001B\u0001B\u0003%\u0001\bC\u0003=\u0001\u0011\u0005Q\bC\u0003=\u0001\u0011\u00051\tC\u0003K\u0001\u0011\u00051\nC\u0003O\u0001\u0011\u0005q\nC\u0003R\u0001\u0011\u0005!\u000bC\u0003U\u0001\u0011\u0005QKA\u0005Qe>\u001cWm]:J\u001f*\u0011\u0011CE\u0001\baJ|7-Z:t\u0015\t\u0019B#A\u0002tsNT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0001\u0004\u0005\u0002\u001a55\tA#\u0003\u0002\u001c)\t1\u0011I\\=SK\u001a\f!b\u001e:ji\u0016Le\u000e];u+\u0005q\u0002\u0003B\r C%J!\u0001\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001\u0012'\u001d\t\u0019C%D\u0001\u0011\u0013\t)\u0003#A\bqe>\u001cWm]:J]R,'O\\1m\u0013\t9\u0003F\u0001\u0007PkR\u0004X\u000f^*ue\u0016\fWN\u0003\u0002&!A\u0011\u0011DK\u0005\u0003WQ\u0011A!\u00168ji\u0006YqO]5uK&s\u0007/\u001e;!\u00035\u0001(o\\2fgN|U\u000f\u001e9viV\tq\u0006\u0005\u0003\u001a?AJ\u0003C\u0001\u00122\u0013\t\u0011\u0004FA\u0006J]B,Ho\u0015;sK\u0006l\u0017A\u00049s_\u000e,7o](viB,H\u000fI\u0001\raJ|7-Z:t\u000bJ\u0014xN]\u0001\u000eaJ|7-Z:t\u000bJ\u0014xN\u001d\u0011\u0002!\u0011\fW-\\8oSj,G\u000b\u001b:fC\u0012\u001cX#\u0001\u001d\u0011\u0005eI\u0014B\u0001\u001e\u0015\u0005\u001d\u0011un\u001c7fC:\f\u0011\u0003Z1f[>t\u0017N_3UQJ,\u0017\rZ:!\u0003\u0019a\u0014N\\5u}Q)ah\u0010!B\u0005B\u00111\u0005\u0001\u0005\u00069%\u0001\rA\b\u0005\u0006[%\u0001\ra\f\u0005\u0006i%\u0001\ra\f\u0005\u0006m%\u0001\r\u0001\u000f\u000b\u0005}\u00113\u0005\nC\u0003F\u0015\u0001\u0007a$\u0001\u0002j]\")qI\u0003a\u0001_\u0005\u0019q.\u001e;\t\u000b%S\u0001\u0019A\u0018\u0002\u0007\u0015\u0014(/A\u0005xSRD\u0017J\u001c9viR\u0011a\b\u0014\u0005\u0006\u001b.\u0001\rAH\u0001\u0006oJLG/Z\u0001\u000bo&$\bnT;uaV$HC\u0001 Q\u0011\u0015\tB\u00021\u00010\u0003%9\u0018\u000e\u001e5FeJ|'\u000f\u0006\u0002?'\")\u0011#\u0004a\u0001_\u0005QA-Y3n_:L'0\u001a3\u0015\u0003y\u0002")
public final class ProcessIO {
    private final Function1<OutputStream, BoxedUnit> writeInput;
    private final Function1<InputStream, BoxedUnit> processOutput;
    private final Function1<InputStream, BoxedUnit> processError;
    private final boolean daemonizeThreads;

    public Function1<OutputStream, BoxedUnit> writeInput() {
        return this.writeInput;
    }

    public Function1<InputStream, BoxedUnit> processOutput() {
        return this.processOutput;
    }

    public Function1<InputStream, BoxedUnit> processError() {
        return this.processError;
    }

    public boolean daemonizeThreads() {
        return this.daemonizeThreads;
    }

    public ProcessIO withInput(Function1<OutputStream, BoxedUnit> write) {
        return new ProcessIO(write, this.processOutput(), this.processError(), this.daemonizeThreads());
    }

    public ProcessIO withOutput(Function1<InputStream, BoxedUnit> process) {
        return new ProcessIO(this.writeInput(), process, this.processError(), this.daemonizeThreads());
    }

    public ProcessIO withError(Function1<InputStream, BoxedUnit> process) {
        return new ProcessIO(this.writeInput(), this.processOutput(), process, this.daemonizeThreads());
    }

    public ProcessIO daemonized() {
        return new ProcessIO(this.writeInput(), this.processOutput(), this.processError(), true);
    }

    public ProcessIO(Function1<OutputStream, BoxedUnit> writeInput, Function1<InputStream, BoxedUnit> processOutput, Function1<InputStream, BoxedUnit> processError, boolean daemonizeThreads) {
        this.writeInput = writeInput;
        this.processOutput = processOutput;
        this.processError = processError;
        this.daemonizeThreads = daemonizeThreads;
    }

    public ProcessIO(Function1<OutputStream, BoxedUnit> in, Function1<InputStream, BoxedUnit> out, Function1<InputStream, BoxedUnit> err) {
        this(in, out, err, false);
    }
}

