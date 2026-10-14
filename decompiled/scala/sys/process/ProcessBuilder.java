/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import scala.Function0;
import scala.collection.immutable.LazyList;
import scala.collection.immutable.Stream;
import scala.reflect.ScalaSignature;
import scala.sys.process.Process;
import scala.sys.process.ProcessBuilder$;
import scala.sys.process.ProcessBuilderImpl;
import scala.sys.process.ProcessIO;
import scala.sys.process.ProcessLogger;

@ScalaSignature(bytes="\u0006\u0005\t-daB\u001f?!\u0003\r\n!\u0012\u0005\b\u0003+\u0002a\u0011AA,\u0011\u001d\t)\u0006\u0001D\u0001\u0003_Bq!a\u001f\u0001\r\u0003\t9\u0006C\u0004\u0002|\u00011\t!! \t\u000f\u0005\u0005\u0005A\"\u0001\u0002\u0004\"9\u0011\u0011\u0011\u0001\u0007\u0002\u0005M\u0005bBAA\u0001\u0019\u0005\u0011\u0011\u0016\u0005\b\u0003\u0003\u0003a\u0011AAW\u0011\u001d\t\u0019\f\u0001D\u0001\u0003\u0007Cq!a-\u0001\r\u0003\t)\fC\u0004\u00024\u00021\t!!/\t\u000f\u0005M\u0006A\"\u0001\u0002>\"9\u00111\u0019\u0001\u0007\u0002\u0005\u0015\u0007bBAb\u0001\u0019\u0005\u0011\u0011\u001d\u0005\b\u0003\u0007\u0004a\u0011AAt\u0011\u001d\t\u0019\r\u0001D\u0001\u0003[Dq!!>\u0001\r\u0003\t)\rC\u0004\u0002v\u00021\t!!@\t\u000f\u0005U\bA\"\u0001\u0003\u0004!9\u0011Q\u001f\u0001\u0007\u0002\t%\u0001b\u0002B\t\u0001\u0019\u0005!1\u0003\u0005\b\u0005#\u0001a\u0011\u0001B\u000e\u0011\u001d\u0011y\u0002\u0001D\u0001\u0005'AqAa\b\u0001\r\u0003\u0011\t\u0003C\u0004\u0003&\u00011\tAa\n\t\u000f\t\u0015\u0002A\"\u0001\u00030!9!Q\u0005\u0001\u0007\u0002\tM\u0002b\u0002B\u0013\u0001\u0019\u0005!q\b\u0005\b\u0005K\u0001a\u0011\u0001B#\u0011\u001d\u0011Y\u0005\u0001D\u0001\u0005\u001bBqAa\u0015\u0001\r\u0003\u0011)\u0006C\u0004\u0003Z\u00011\tAa\u0017\t\u000f\t}\u0003A\"\u0001\u0003b!9!Q\r\u0001\u0007\u0002\t\u001d\u0004b\u0002B5\u0001\u0019\u0005!qM\u0004\u0006\u001bzB\tA\u0014\u0004\u0006{yB\ta\u0014\u0005\u0006'\u0016\"\t\u0001\u0016\u0004\b+\u0016\u0002\n1%\u0001W\r%\ti!\nI\u0001$\u0003\ty\u0001C\u0004\u0002<!2\t!!\u0010\t\u000f\u0005m\u0002F\"\u0001\u0002B!9\u00111\b\u0015\u0007\u0002\u0005\u001d\u0003bBA\u001eQ\u0019\u0005\u0011Q\n\u0004\b3\u0016\u0002\n1!\u0001[\u0011\u0015YV\u0006\"\u0001]\u0011\u0015\u0001WF\"\u0005b\u0011\u0015\u0019W\u0006\"\u0001e\u0011\u0015qW\u0006\"\u0001p\u0011\u0015\u0019W\u0006\"\u0001r\u0011\u0015\u0019W\u0006\"\u0001{\u0011\u0015iX\u0006\"\u0001b\u0011\u0015qX\u0006\"\u0003\u0000\r%\t\u0019\"\nI\u0001\u0004\u0003\t)\u0002C\u0003\\m\u0011\u0005A\f\u0003\u0004\u0002\u0018Y2\t\"\u0019\u0005\b\u000331D\u0011AA\u000e\u0011\u001d\tIB\u000eC\u0001\u0003?Aq!!\u00077\t\u0003\tI\u0003C\u0004\u0002\u001aY\"\t!a\u000e\u0003\u001dA\u0013xnY3tg\n+\u0018\u000e\u001c3fe*\u0011q\bQ\u0001\baJ|7-Z:t\u0015\t\t%)A\u0002tsNT\u0011aQ\u0001\u0006g\u000e\fG.Y\u0002\u0001'\u0015\u0001aISA*!\t9\u0005*D\u0001C\u0013\tI%I\u0001\u0004B]f\u0014VM\u001a\t\u0003\u00176r!\u0001\u0014\u0013\u000e\u0003y\na\u0002\u0015:pG\u0016\u001c8OQ;jY\u0012,'\u000f\u0005\u0002MKM\u0019QE\u0012)\u0011\u00051\u000b\u0016B\u0001*?\u0005I\u0001&o\\2fgN\u0014U/\u001b7eKJLU\u000e\u001d7\u0002\rqJg.\u001b;?)\u0005q%AC+S\u0019\n+\u0018\u000e\u001c3feN\u0019qER,\u0011\u0005akS\"A\u0013\u0003\rM{WO]2f'\tic)\u0001\u0004%S:LG\u000f\n\u000b\u0002;B\u0011qIX\u0005\u0003?\n\u0013A!\u00168ji\u0006AAo\\*pkJ\u001cW-F\u0001c!\ta\u0005!A\u0007%Q\u0006\u001c\b\u000eJ4sK\u0006$XM\u001d\u000b\u0003E\u0016DQA\u001a\u0019A\u0002\u001d\f\u0011A\u001a\t\u0003Q.t!\u0001T5\n\u0005)t\u0014a\u00049s_\u000e,7o]%oi\u0016\u0014h.\u00197\n\u00051l'\u0001\u0002$jY\u0016T!A\u001b \u0002+\u0011B\u0017m\u001d5%OJ,\u0017\r^3sI\u001d\u0014X-\u0019;feR\u0011!\r\u001d\u0005\u0006MF\u0002\ra\u001a\u000b\u0003EJDaa\u001d\u001a\u0005\u0002\u0004!\u0018aA8viB\u0019q)^<\n\u0005Y\u0014%\u0001\u0003\u001fcs:\fW.\u001a \u0011\u0005!D\u0018BA=n\u00051yU\u000f\u001e9viN#(/Z1n)\t\u00117\u0010C\u0003}g\u0001\u0007!-A\u0001c\u0003\r\u0019\u0017\r^\u0001\u0007i>4\u0015\u000e\\3\u0015\u000b\t\f\t!a\u0001\t\u000b\u0019,\u0004\u0019A4\t\u000f\u0005\u0015Q\u00071\u0001\u0002\b\u00051\u0011\r\u001d9f]\u0012\u00042aRA\u0005\u0013\r\tYA\u0011\u0002\b\u0005>|G.Z1o\u0005-1\u0015\u000e\\3Ck&dG-\u001a:\u0014\u000b!2\u0015\u0011C,\u0011\u0005a3$\u0001B*j].\u001c\"A\u000e$\u0002\rQ|7+\u001b8l\u0003)!\u0003.Y:iI1,7o\u001d\u000b\u0004E\u0006u\u0001\"\u00024:\u0001\u00049Gc\u00012\u0002\"!1aM\u000fa\u0001\u0003G\u00012\u0001[A\u0013\u0013\r\t9#\u001c\u0002\u0004+JcEc\u00012\u0002,!A\u0011QF\u001e\u0005\u0002\u0004\ty#\u0001\u0002j]B!q)^A\u0019!\rA\u00171G\u0005\u0004\u0003ki'aC%oaV$8\u000b\u001e:fC6$2AYA\u001d\u0011\u0015aH\b1\u0001c\u0003=!\u0003.Y:iI1,7o\u001d\u0013mKN\u001cHc\u00012\u0002@!)a-\u000ba\u0001OR\u0019!-a\u0011\t\u000f\u0005\u0015#\u00061\u0001\u0002$\u0005\tQ\u000fF\u0002c\u0003\u0013B\u0001\"a\u0013,\t\u0003\u0007\u0011qF\u0001\u0002SR\u0019!-a\u0014\t\r\u0005EC\u00061\u0001c\u0003\u0005\u0001\bCA&7\u0003)!#-\u00198hI\t\fgnZ\u000b\u0003\u00033\u0002B!a\u0017\u0002j9!\u0011QLA3!\r\tyFQ\u0007\u0003\u0003CR1!a\u0019E\u0003\u0019a$o\\8u}%\u0019\u0011q\r\"\u0002\rA\u0013X\rZ3g\u0013\u0011\tY'!\u001c\u0003\rM#(/\u001b8h\u0015\r\t9G\u0011\u000b\u0005\u00033\n\t\bC\u0004\u0002t\t\u0001\r!!\u001e\u0002\u00071|w\rE\u0002M\u0003oJ1!!\u001f?\u00055\u0001&o\\2fgNdunZ4fe\u0006yAEY1oO\u0012\u0012\u0017M\\4%Y\u0016\u001c8\u000f\u0006\u0003\u0002Z\u0005}\u0004bBA:\t\u0001\u0007\u0011QO\u0001\nY\u0006T\u0018\u0010T5oKN,\"!!\"\u0011\r\u0005\u001d\u0015QRA-\u001d\r9\u0015\u0011R\u0005\u0004\u0003\u0017\u0013\u0015a\u00029bG.\fw-Z\u0005\u0005\u0003\u001f\u000b\tJ\u0001\u0005MCjLH*[:u\u0015\r\tYI\u0011\u000b\u0005\u0003\u000b\u000b)\nC\u0004\u0002\u0018\u001a\u0001\r!!'\u0002\u0011\r\f\u0007/Y2jif\u0004B!a'\u0002&6\u0011\u0011Q\u0014\u0006\u0005\u0003?\u000b\t+\u0001\u0003mC:<'BAAR\u0003\u0011Q\u0017M^1\n\t\u0005\u001d\u0016Q\u0014\u0002\b\u0013:$XmZ3s)\u0011\t))a+\t\u000f\u0005Mt\u00011\u0001\u0002vQ1\u0011QQAX\u0003cCq!a\u001d\t\u0001\u0004\t)\bC\u0004\u0002\u0018\"\u0001\r!!'\u0002\u001f1\f'0\u001f'j]\u0016\u001cx\f\n2b]\u001e$B!!\"\u00028\"9\u0011q\u0013\u0006A\u0002\u0005eE\u0003BAC\u0003wCq!a\u001d\f\u0001\u0004\t)\b\u0006\u0004\u0002\u0006\u0006}\u0016\u0011\u0019\u0005\b\u0003gb\u0001\u0019AA;\u0011\u001d\t9\n\u0004a\u0001\u00033\u000b!\u0002\\5oKN#(/Z1n+\t\t9\r\u0005\u0004\u0002\b\u0006%\u0017\u0011L\u0005\u0005\u0003\u0017\f\tJ\u0001\u0004TiJ,\u0017-\u001c\u0015\f\u001b\u0005=\u0017Q[Al\u00037\fi\u000eE\u0002H\u0003#L1!a5C\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\tI.A\u0007vg\u0016\u0004C.\u0019>z\u0019&tWm]\u0001\u0006g&t7-Z\u0011\u0003\u0003?\faA\r\u00182g9\u0002D\u0003BAd\u0003GDq!a&\u000f\u0001\u0004\tI\nK\u0006\u000f\u0003\u001f\f).a6\u0002\\\u0006uG\u0003BAd\u0003SDq!a\u001d\u0010\u0001\u0004\t)\bK\u0006\u0010\u0003\u001f\f).a6\u0002\\\u0006uGCBAd\u0003_\f\t\u0010C\u0004\u0002tA\u0001\r!!\u001e\t\u000f\u0005]\u0005\u00031\u0001\u0002\u001a\"Z\u0001#a4\u0002V\u0006]\u00171\\Ao\u0003Aa\u0017N\\3TiJ,\u0017-\\0%E\u0006tw\rK\u0006\u0012\u0003\u001f\f).!?\u0002\\\u0006u\u0017EAA~\u0003=)8/\u001a\u0011mCjLH*\u001b8fg~\u000bC\u0003BAd\u0003\u007fDq!a&\u0013\u0001\u0004\tI\nK\u0006\u0013\u0003\u001f\f).!?\u0002\\\u0006uG\u0003BAd\u0005\u000bAq!a\u001d\u0014\u0001\u0004\t)\bK\u0006\u0014\u0003\u001f\f).!?\u0002\\\u0006uGCBAd\u0005\u0017\u0011i\u0001C\u0004\u0002tQ\u0001\r!!\u001e\t\u000f\u0005]E\u00031\u0001\u0002\u001a\"ZA#a4\u0002V\u0006e\u00181\\Ao\u0003\u0015!#-\u00198h+\t\u0011)\u0002E\u0002H\u0005/I1A!\u0007C\u0005\rIe\u000e\u001e\u000b\u0005\u0005+\u0011i\u0002C\u0004\u0002tY\u0001\r!!\u001e\u0002\u0015\u0011\u0012\u0017M\\4%Y\u0016\u001c8\u000f\u0006\u0003\u0003\u0016\t\r\u0002bBA:1\u0001\u0007\u0011QO\u0001\u0004eVtGC\u0001B\u0015!\ra%1F\u0005\u0004\u0005[q$a\u0002)s_\u000e,7o\u001d\u000b\u0005\u0005S\u0011\t\u0004C\u0004\u0002ti\u0001\r!!\u001e\u0015\t\t%\"Q\u0007\u0005\b\u0005oY\u0002\u0019\u0001B\u001d\u0003\tIw\u000eE\u0002M\u0005wI1A!\u0010?\u0005%\u0001&o\\2fgNLu\n\u0006\u0003\u0003*\t\u0005\u0003b\u0002B\"9\u0001\u0007\u0011qA\u0001\rG>tg.Z2u\u0013:\u0004X\u000f\u001e\u000b\u0007\u0005S\u00119E!\u0013\t\u000f\u0005MT\u00041\u0001\u0002v!9!1I\u000fA\u0002\u0005\u001d\u0011!\u0004\u0013iCNDG%Y7qI\u0005l\u0007\u000fF\u0002c\u0005\u001fBaA!\u0015\u001f\u0001\u0004\u0011\u0017!B8uQ\u0016\u0014\u0018!\u0004\u0013iCNDGEY1sI\t\f'\u000fF\u0002c\u0005/BaA!\u0015 \u0001\u0004\u0011\u0017!\u0003\u0013iCNDGEY1s)\r\u0011'Q\f\u0005\u0007\u0005#\u0002\u0003\u0019\u00012\u0002\u001f\u0011B\u0017m\u001d5%Q\u0006\u001c\b\u000e\n5bg\"$2A\u0019B2\u0011\u0019\u0011\t&\ta\u0001E\u0006I1-\u00198QSB,Gk\\\u000b\u0003\u0003\u000f\tA\u0002[1t\u000bbLGOV1mk\u0016\u0004")
public interface ProcessBuilder
extends Source,
Sink {
    public String $bang$bang();

    public String $bang$bang(ProcessLogger var1);

    public String $bang$bang$less();

    public String $bang$bang$less(ProcessLogger var1);

    public LazyList<String> lazyLines();

    public LazyList<String> lazyLines(Integer var1);

    public LazyList<String> lazyLines(ProcessLogger var1);

    public LazyList<String> lazyLines(ProcessLogger var1, Integer var2);

    public LazyList<String> lazyLines_$bang();

    public LazyList<String> lazyLines_$bang(Integer var1);

    public LazyList<String> lazyLines_$bang(ProcessLogger var1);

    public LazyList<String> lazyLines_$bang(ProcessLogger var1, Integer var2);

    public Stream<String> lineStream();

    public Stream<String> lineStream(Integer var1);

    public Stream<String> lineStream(ProcessLogger var1);

    public Stream<String> lineStream(ProcessLogger var1, Integer var2);

    public Stream<String> lineStream_$bang();

    public Stream<String> lineStream_$bang(Integer var1);

    public Stream<String> lineStream_$bang(ProcessLogger var1);

    public Stream<String> lineStream_$bang(ProcessLogger var1, Integer var2);

    public int $bang();

    public int $bang(ProcessLogger var1);

    public int $bang$less();

    public int $bang$less(ProcessLogger var1);

    public Process run();

    public Process run(ProcessLogger var1);

    public Process run(ProcessIO var1);

    public Process run(boolean var1);

    public Process run(ProcessLogger var1, boolean var2);

    public ProcessBuilder $hash$amp$amp(ProcessBuilder var1);

    public ProcessBuilder $hash$bar$bar(ProcessBuilder var1);

    public ProcessBuilder $hash$bar(ProcessBuilder var1);

    public ProcessBuilder $hash$hash$hash(ProcessBuilder var1);

    public boolean canPipeTo();

    public boolean hasExitValue();

    public static interface FileBuilder
    extends Sink,
    Source {
        public ProcessBuilder $hash$less$less(File var1);

        public ProcessBuilder $hash$less$less(URL var1);

        public ProcessBuilder $hash$less$less(Function0<InputStream> var1);

        public ProcessBuilder $hash$less$less(ProcessBuilder var1);
    }

    public static interface Sink {
        public ProcessBuilder toSink();

        public static /* synthetic */ ProcessBuilder $hash$less$(Sink $this, File f) {
            return $this.$hash$less(f);
        }

        default public ProcessBuilder $hash$less(File f) {
            return this.$hash$less(new ProcessBuilderImpl.FileInput(ProcessBuilder$.MODULE$, f));
        }

        public static /* synthetic */ ProcessBuilder $hash$less$(Sink $this, URL f) {
            return $this.$hash$less(f);
        }

        default public ProcessBuilder $hash$less(URL f) {
            return this.$hash$less(new ProcessBuilderImpl.URLInput(ProcessBuilder$.MODULE$, f));
        }

        public static /* synthetic */ ProcessBuilder $hash$less$(Sink $this, Function0 in) {
            return $this.$hash$less(in);
        }

        default public ProcessBuilder $hash$less(Function0<InputStream> in) {
            return this.$hash$less(new ProcessBuilderImpl.IStreamBuilder(ProcessBuilder$.MODULE$, in, "<input stream>"));
        }

        public static /* synthetic */ ProcessBuilder $hash$less$(Sink $this, ProcessBuilder b) {
            return $this.$hash$less(b);
        }

        default public ProcessBuilder $hash$less(ProcessBuilder b) {
            return new ProcessBuilderImpl.PipedBuilder(ProcessBuilder$.MODULE$, b, this.toSink(), false);
        }

        public static void $init$(Sink $this) {
        }
    }

    public static interface Source {
        public ProcessBuilder toSource();

        public static /* synthetic */ ProcessBuilder $hash$greater$(Source $this, File f) {
            return $this.$hash$greater(f);
        }

        default public ProcessBuilder $hash$greater(File f) {
            return this.toFile(f, false);
        }

        public static /* synthetic */ ProcessBuilder $hash$greater$greater$(Source $this, File f) {
            return $this.$hash$greater$greater(f);
        }

        default public ProcessBuilder $hash$greater$greater(File f) {
            return this.toFile(f, true);
        }

        public static /* synthetic */ ProcessBuilder $hash$greater$(Source $this, Function0 out) {
            return $this.$hash$greater(out);
        }

        default public ProcessBuilder $hash$greater(Function0<OutputStream> out) {
            return this.$hash$greater(new ProcessBuilderImpl.OStreamBuilder(ProcessBuilder$.MODULE$, out, "<output stream>"));
        }

        public static /* synthetic */ ProcessBuilder $hash$greater$(Source $this, ProcessBuilder b) {
            return $this.$hash$greater(b);
        }

        default public ProcessBuilder $hash$greater(ProcessBuilder b) {
            return new ProcessBuilderImpl.PipedBuilder(ProcessBuilder$.MODULE$, this.toSource(), b, false);
        }

        public static /* synthetic */ ProcessBuilder cat$(Source $this) {
            return $this.cat();
        }

        default public ProcessBuilder cat() {
            return this.toSource();
        }

        private ProcessBuilder toFile(File f, boolean append) {
            return this.$hash$greater(new ProcessBuilderImpl.FileOutput(ProcessBuilder$.MODULE$, f, append));
        }

        public static void $init$(Source $this) {
        }
    }

    public static interface URLBuilder
    extends Source {
    }
}

