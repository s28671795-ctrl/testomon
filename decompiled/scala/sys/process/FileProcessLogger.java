/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.sys.process.ProcessLogger;

@ScalaSignature(bytes="\u0006\u0005u3A!\u0003\u0006\u0001#!AQ\u0005\u0001B\u0001B\u0003%a\u0005C\u0003*\u0001\u0011\u0005!\u0006\u0003\u0004.\u0001\u0001\u0006IA\f\u0005\u0006c\u0001!\tA\r\u0005\u0006\r\u0002!\ta\u0012\u0005\u0006\u0013\u0002!\tA\u0013\u0005\u00065\u0002!\ta\u0017\u0005\u00069\u0002!\ta\u0017\u0002\u0012\r&dW\r\u0015:pG\u0016\u001c8\u000fT8hO\u0016\u0014(BA\u0006\r\u0003\u001d\u0001(o\\2fgNT!!\u0004\b\u0002\u0007ML8OC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019R\u0001\u0001\n\u00175\t\u0002\"a\u0005\u000b\u000e\u00039I!!\u0006\b\u0003\r\u0005s\u0017PU3g!\t9\u0002$D\u0001\u000b\u0013\tI\"BA\u0007Qe>\u001cWm]:M_\u001e<WM\u001d\t\u00037\u0001j\u0011\u0001\b\u0006\u0003;y\t!![8\u000b\u0003}\tAA[1wC&\u0011\u0011\u0005\b\u0002\n\u00072|7/Z1cY\u0016\u0004\"aG\u0012\n\u0005\u0011b\"!\u0003$mkND\u0017M\u00197f\u0003\u00111\u0017\u000e\\3\u0011\u0005m9\u0013B\u0001\u0015\u001d\u0005\u00111\u0015\u000e\\3\u0002\rqJg.\u001b;?)\tYC\u0006\u0005\u0002\u0018\u0001!)QE\u0001a\u0001M\u00051qO]5uKJ\u0004\"aG\u0018\n\u0005Ab\"a\u0003)sS:$xK]5uKJ\f1a\\;u)\t\u0019d\u0007\u0005\u0002\u0014i%\u0011QG\u0004\u0002\u0005+:LG\u000f\u0003\u00048\t\u0011\u0005\r\u0001O\u0001\u0002gB\u00191#O\u001e\n\u0005ir!\u0001\u0003\u001fcs:\fW.\u001a \u0011\u0005q\u001aeBA\u001fB!\tqd\"D\u0001@\u0015\t\u0001\u0005#\u0001\u0004=e>|GOP\u0005\u0003\u0005:\ta\u0001\u0015:fI\u00164\u0017B\u0001#F\u0005\u0019\u0019FO]5oO*\u0011!ID\u0001\u0004KJ\u0014HCA\u001aI\u0011\u00199T\u0001\"a\u0001q\u00051!-\u001e4gKJ,\"a\u0013(\u0015\u00051;\u0006CA'O\u0019\u0001!Qa\u0014\u0004C\u0002A\u0013\u0011\u0001V\t\u0003#R\u0003\"a\u0005*\n\u0005Ms!a\u0002(pi\"Lgn\u001a\t\u0003'UK!A\u0016\b\u0003\u0007\u0005s\u0017\u0010\u0003\u0004Y\r\u0011\u0005\r!W\u0001\u0002MB\u00191#\u000f'\u0002\u000b\rdwn]3\u0015\u0003M\nQA\u001a7vg\"\u0004")
public class FileProcessLogger
implements ProcessLogger,
Closeable,
Flushable {
    private final PrintWriter writer;

    @Override
    public void out(Function0<String> s2) {
        this.writer.println(s2.apply());
    }

    @Override
    public void err(Function0<String> s2) {
        this.writer.println(s2.apply());
    }

    @Override
    public <T> T buffer(Function0<T> f) {
        return f.apply();
    }

    @Override
    public void close() {
        this.writer.close();
    }

    @Override
    public void flush() {
        this.writer.flush();
    }

    public FileProcessLogger(File file) {
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true))));
    }
}

