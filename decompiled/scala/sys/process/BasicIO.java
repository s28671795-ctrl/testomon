/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.collection.immutable.LazyList;
import scala.collection.immutable.Stream;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.sys.process.BasicIO$;
import scala.sys.process.ProcessIO;
import scala.sys.process.ProcessLogger;

@ScalaSignature(bytes="\u0006\u0005\t-v!\u0002!B\u0011\u0003Ae!\u0002&B\u0011\u0003Y\u0005\"\u0002)\u0002\t\u0003\t\u0006b\u0002*\u0002\u0005\u0004%)a\u0015\u0005\u0007-\u0006\u0001\u000bQ\u0002+\t\u000f]\u000b!\u0019!C\u00031\"1\u0011-\u0001Q\u0001\u000ee3QAY\u0001\u0003\u0003\u000eD\u0001BQ\u0004\u0003\u0006\u0004%\t!\u001a\u0005\to\u001e\u0011\t\u0011)A\u0005M\"A\u0001p\u0002BC\u0002\u0013\u0005\u0011\u0010\u0003\u0005\u007f\u000f\t\u0005\t\u0015!\u0003{\u0011%yxA!b\u0001\n\u0003\t\t\u0001\u0003\u0006\u0002\u0012\u001d\u0011\t\u0011)A\u0005\u0003\u0007Aa\u0001U\u0004\u0005\u0002\u0005Mq\u0001CA\u0010\u0003!\u0005\u0011)!\t\u0007\u000f\t\f\u0001\u0012A!\u0002$!1\u0001\u000b\u0005C\u0001\u0003KAq!a\n\u0011\t\u0003\tICB\u0004\u0002H\u0005\u0011\u0011)!\u0013\t\u0013\t\u001b\"Q1A\u0005\u0002\u00055\u0003\"C<\u0014\u0005\u0003\u0005\u000b\u0011BA(\u0011!A8C!b\u0001\n\u0003I\b\u0002\u0003@\u0014\u0005\u0003\u0005\u000b\u0011\u0002>\t\u0015\u0005U3C!b\u0001\n\u0003\t9\u0006\u0003\u0006\u0002fM\u0011\t\u0011)A\u0005\u00033Ba\u0001U\n\u0005\u0002\u0005\u001dt\u0001CAC\u0003!\u0005\u0011)a\"\u0007\u0011\u0005\u001d\u0013\u0001#\u0001B\u0003\u0013Ca\u0001\u0015\u000f\u0005\u0002\u0005-\u0005bBA\u00149\u0011\u0005\u0011Q\u0012\u0004\u000b\u0003?\u000b\u0001\u0013aA\u0001\u0003\u0006\u0005\u0006bBA\\?\u0011\u0005\u0011\u0011\u0018\u0005\b\u0003w{BQIA]\u000f!\ti,\u0001E\u0001\u0003\u0006}f\u0001CAP\u0003!\u0005\u0011)!1\t\rA\u001bC\u0011AAb\u0011\u001d\t9c\tC\u0001\u0003\u000bDq!a\n$\t\u0003\t\t\u000eC\u0004\u0002^\u000e\"\t!a8\t\u000f\u0005u7\u0005\"\u0001\u0002d\"9\u0011qE\u0001\u0005\u0002\u0005\u001d\bbBA\u0014\u0003\u0011\u0005!Q\u0004\u0005\b\u0003O\tA\u0011\u0001B\u0017\u0011\u001d\u0011\u0019$\u0001C\u0001\u0005kAqAa\u000f\u0002\t\u0013\u0011i\u0004C\u0004\u0003B\u0005!IAa\u0011\t\u000f\u0005m\u0016\u0001\"\u0001\u0003H!9!QJ\u0001\u0005\u0002\t=\u0003b\u0002B'\u0003\u0011\u0005!1\u000b\u0005\b\u00053\nA\u0011\u0001B.\u0011\u001d\u00119'\u0001C\u0001\u0005SBqAa\u001c\u0002\t\u0003\u0011\t\b\u0003\u0006\u0003z\u0005\u0011\r\u0011\"\u0001B\u0005wB\u0001B! \u0002A\u0003%!1\u000f\u0005\u000b\u0005\u007f\n!\u0019!C\u0001\u0003\nm\u0004\u0002\u0003BA\u0003\u0001\u0006IAa\u001d\t\u000f\t\r\u0015\u0001\"\u0001\u0003\u0006\"9!1Q\u0001\u0005\u0002\t-\u0005b\u0002BH\u0003\u0011\u0005!\u0011\u0013\u0005\b\u0005'\u000bA\u0011\u0001BI\u0011\u001d\u0011)*\u0001C\u0001\u0005/C\u0001B!(\u0002A\u0013%!q\u0014\u0005\t\u0005G\u000b\u0001\u0015\"\u0003\u0003&\u00069!)Y:jG&{%B\u0001\"D\u0003\u001d\u0001(o\\2fgNT!\u0001R#\u0002\u0007ML8OC\u0001G\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"!S\u0001\u000e\u0003\u0005\u0013qAQ1tS\u000eLuj\u0005\u0002\u0002\u0019B\u0011QJT\u0007\u0002\u000b&\u0011q*\u0012\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005A\u0015A\u0003\"vM\u001a,'oU5{KV\tAkD\u0001V;\t\u0001\u0003!A\u0006Ck\u001a4WM]*ju\u0016\u0004\u0013a\u0002(fo2Lg.Z\u000b\u00023B\u0011!lX\u0007\u00027*\u0011A,X\u0001\u0005Y\u0006twMC\u0001_\u0003\u0011Q\u0017M^1\n\u0005\u0001\\&AB*ue&tw-\u0001\u0005OK^d\u0017N\\3!\u00051a\u0015M_5ms2K7\u000f^3e+\t!7n\u0005\u0002\b\u0019V\ta\r\u0005\u0003NO&$\u0018B\u00015F\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002kW2\u0001A!\u00027\b\u0005\u0004i'!\u0001+\u0012\u00059\f\bCA'p\u0013\t\u0001XIA\u0004O_RD\u0017N\\4\u0011\u00055\u0013\u0018BA:F\u0005\r\te.\u001f\t\u0003\u001bVL!A^#\u0003\tUs\u0017\u000e^\u0001\taJ|7-Z:tA\u0005!Am\u001c8f+\u0005Q\b\u0003B'hwR\u0004\"!\u0014?\n\u0005u,%aA%oi\u0006)Am\u001c8fA\u0005AA.\u0019>z\u0019&\u001cH/\u0006\u0002\u0002\u0004A)\u0011QAA\u0006S:\u0019Q*a\u0002\n\u0007\u0005%Q)A\u0004qC\u000e\\\u0017mZ3\n\t\u00055\u0011q\u0002\u0002\t\u0019\u0006T\u0018\u0010T5ti*\u0019\u0011\u0011B#\u0002\u00131\f'0\u001f'jgR\u0004C\u0003CA\u000b\u00033\tY\"!\b\u0011\t\u0005]q![\u0007\u0002\u0003!)!I\u0004a\u0001M\")\u0001P\u0004a\u0001u\"1qP\u0004a\u0001\u0003\u0007\tA\u0002T1{S2LH*[:uK\u0012\u00042!a\u0006\u0011'\t\u0001B\n\u0006\u0002\u0002\"\u0005)\u0011\r\u001d9msV!\u00111FA\u0019)\u0019\ti#a\r\u0002>A)\u0011qC\u0004\u00020A\u0019!.!\r\u0005\u000b1\u0014\"\u0019A7\t\u000f\u0005U\"\u00031\u0001\u00028\u0005\u0001bn\u001c8{KJ|W\t_2faRLwN\u001c\t\u0004\u001b\u0006e\u0012bAA\u001e\u000b\n9!i\\8mK\u0006t\u0007bBA %\u0001\u0007\u0011\u0011I\u0001\tG\u0006\u0004\u0018mY5usB\u0019!,a\u0011\n\u0007\u0005\u00153LA\u0004J]R,w-\u001a:\u0003\u0011M#(/Z1nK\u0012,B!a\u0013\u0002TM\u00111\u0003T\u000b\u0003\u0003\u001f\u0002R!T4\u0002RQ\u00042A[A*\t\u0015a7C1\u0001n\u0003\u0019\u0019HO]3b[V\u0011\u0011\u0011\f\t\u0006\u001b\u0006m\u0013qL\u0005\u0004\u0003;*%!\u0003$v]\u000e$\u0018n\u001c81!\u0019\t)!!\u0019\u0002R%!\u00111MA\b\u0005\u0019\u0019FO]3b[\u000691\u000f\u001e:fC6\u0004C\u0003CA5\u0003W\ni'a\u001c\u0011\u000b\u0005]1#!\u0015\t\r\tS\u0002\u0019AA(\u0011\u0015A(\u00041\u0001{\u0011\u001d\t)F\u0007a\u0001\u00033B3bEA:\u0003s\nY(a \u0002\u0002B\u0019Q*!\u001e\n\u0007\u0005]TI\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0002~\u0005A\u0011N\u001c;fe:\fG.A\u0003tS:\u001cW-\t\u0002\u0002\u0004\u00061!GL\u00194]Q\n\u0001b\u0015;sK\u0006lW\r\u001a\t\u0004\u0003/a2C\u0001\u000fM)\t\t9)\u0006\u0003\u0002\u0010\u0006UECBAI\u0003/\u000bI\nE\u0003\u0002\u0018M\t\u0019\nE\u0002k\u0003+#Q\u0001\u001c\u0010C\u00025Dq!!\u000e\u001f\u0001\u0004\t9\u0004C\u0004\u0002@y\u0001\r!!\u0011)\u0017q\t\u0019(!\u001f\u0002|\u0005}\u0014\u0011\u0011\u0015\f7\u0005M\u0014\u0011PA>\u0003\u007f\n\tIA\u0006V]\u000edwn]3bE2,7#B\u0010\u0002$\u0006%\u0006c\u0001.\u0002&&\u0019\u0011qU.\u0003\r=\u0013'.Z2u!\u0011\tY+!-\u000f\u0007%\u000bi+C\u0002\u00020\u0006\u000bq\u0002\u001d:pG\u0016\u001c8/\u00138uKJt\u0017\r\\\u0005\u0005\u0003g\u000b)LA\u0005DY>\u001cX-\u00192mK*\u0019\u0011qV!\u0002\r\u0011Jg.\u001b;%)\u0005!\u0018!B2m_N,\u0017aC+oG2|7/Z1cY\u0016\u00042!a\u0006$'\t\u0019C\n\u0006\u0002\u0002@R!\u0011qYAg!\u0011\tY+!3\n\t\u0005-\u0017Q\u0017\u0002\f\u0013:\u0004X\u000f^*ue\u0016\fW\u000eC\u0004\u0002P\u0016\u0002\r!a2\u0002\u0005%tG\u0003BAj\u00033\u0004B!a+\u0002V&!\u0011q[A[\u00051yU\u000f\u001e9viN#(/Z1n\u0011\u001d\tYN\na\u0001\u0003'\f1a\\;u\u0003\u001d\u0001(o\u001c;fGR$B!a2\u0002b\"9\u0011qZ\u0014A\u0002\u0005\u001dG\u0003BAj\u0003KDq!a7)\u0001\u0004\t\u0019\u000e\u0006\u0005\u0002j\u0006=\u00181\u001fB\u0007!\rI\u00151^\u0005\u0004\u0003[\f%!\u0003)s_\u000e,7o]%P\u0011\u001d\t\t0\u000ba\u0001\u0003o\taa^5uQ&s\u0007bBA{S\u0001\u0007\u0011q_\u0001\u0007_V$\b/\u001e;\u0011\u000b5;\u0017\u0011 ;\u0011\t\u0005m(\u0011\u0002\b\u0005\u0003{\u0014)\u0001E\u0002\u0002\u0000\u0016k!A!\u0001\u000b\u0007\t\rq)\u0001\u0004=e>|GOP\u0005\u0004\u0005\u000f)\u0015A\u0002)sK\u0012,g-C\u0002a\u0005\u0017Q1Aa\u0002F\u0011\u001d\u0011y!\u000ba\u0001\u0005#\t1\u0001\\8h!\u0015i%1\u0003B\f\u0013\r\u0011)\"\u0012\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0007%\u0013I\"C\u0002\u0003\u001c\u0005\u0013Q\u0002\u0015:pG\u0016\u001c8\u000fT8hO\u0016\u0014H\u0003CAu\u0005?\u0011\tCa\u000b\t\u000f\u0005E(\u00061\u0001\u00028!9!1\u0005\u0016A\u0002\t\u0015\u0012A\u00022vM\u001a,'\u000fE\u0002[\u0005OI1A!\u000b\\\u0005)\t\u0005\u000f]3oI\u0006\u0014G.\u001a\u0005\b\u0005\u001fQ\u0003\u0019\u0001B\t)\u0019\tIOa\f\u00032!9\u0011\u0011_\u0016A\u0002\u0005]\u0002b\u0002B\bW\u0001\u0007!qC\u0001\u0007O\u0016$XI\u001d:\u0015\t\t]\"\u0011\b\t\u0006\u001b\u001e\f9\r\u001e\u0005\b\u0005\u001fa\u0003\u0019\u0001B\t\u0003=\u0001(o\\2fgN,%O\u001d$vY2LH\u0003\u0002B\u001c\u0005\u007fAqAa\u0004.\u0001\u0004\u00119\"A\bqe>\u001cWm]:PkR4U\u000f\u001c7z)\u0011\u00119D!\u0012\t\u000f\t=a\u00061\u0001\u0003\u0018Q\u0019AO!\u0013\t\u000f\t-s\u00061\u0001\u0002*\u0006\t1-\u0001\u0007qe>\u001cWm]:Gk2d\u0017\u0010\u0006\u0003\u00038\tE\u0003b\u0002B\u0012a\u0001\u0007!Q\u0005\u000b\u0005\u0005o\u0011)\u0006C\u0004\u0003XE\u0002\r!a>\u0002\u0017A\u0014xnY3tg2Kg.Z\u0001\u0012aJ|7-Z:t\u0019&tWm\u001d$vY2LH\u0003\u0002B/\u0005K\"2\u0001\u001eB0\u0011\u001d\u0011\tG\ra\u0001\u0005G\n\u0001B]3bI2Kg.\u001a\t\u0006\u001b\u0006m\u0013\u0011 \u0005\b\u0005/\u0012\u0004\u0019AA|\u0003-\u0019wN\u001c8fGR$v.\u00138\u0015\u0007Q\u0014Y\u0007C\u0004\u0003nM\u0002\r!a5\u0002\u0003=\fQ!\u001b8qkR$BAa\u001d\u0003vA)QjZAji\"9!q\u000f\u001bA\u0002\u0005]\u0012aB2p]:,7\r^\u0001\u000fG>tg.Z2u)>\u001cF\u000fZ%o+\t\u0011\u0019(A\bd_:tWm\u0019;U_N#H-\u00138!\u0003-\u0019wN\u001c8fGRtun\u00149\u0002\u0019\r|gN\\3di:{w\n\u001d\u0011\u0002\u0011M$\u0018M\u001c3be\u0012$B!!;\u0003\b\"9!\u0011R\u001dA\u0002\u0005]\u0012\u0001D2p]:,7\r^%oaV$H\u0003BAu\u0005\u001bCq!a4;\u0001\u0004\u0011\u0019(\u0001\u0005u_N#H-\u0012:s+\t\u00119$\u0001\u0005u_N#HmT;u\u00035!(/\u00198tM\u0016\u0014h)\u001e7msR)AO!'\u0003\u001c\"9\u0011qZ\u001fA\u0002\u0005\u001d\u0007bBAn{\u0001\u0007\u00111[\u0001\u000bCB\u0004XM\u001c3MS:,G\u0003BA|\u0005CCqAa\t?\u0001\u0004\u0011)#A\tue\u0006t7OZ3s\rVdG._%na2$R\u0001\u001eBT\u0005SCq!a4@\u0001\u0004\t9\rC\u0004\u0002\\~\u0002\r!a5")
public final class BasicIO {
    public static void transferFully(InputStream in, OutputStream out) {
        BasicIO$.MODULE$.transferFully(in, out);
    }

    public static Function1<InputStream, BoxedUnit> toStdOut() {
        return BasicIO$.MODULE$.toStdOut();
    }

    public static Function1<InputStream, BoxedUnit> toStdErr() {
        return BasicIO$.MODULE$.toStdErr();
    }

    public static ProcessIO standard(Function1<OutputStream, BoxedUnit> in) {
        return BasicIO$.MODULE$.standard(in);
    }

    public static ProcessIO standard(boolean connectInput) {
        return BasicIO$.MODULE$.standard(connectInput);
    }

    public static Function1<OutputStream, BoxedUnit> input(boolean connect2) {
        return BasicIO$.MODULE$.input(connect2);
    }

    public static void connectToIn(OutputStream o) {
        BasicIO$.MODULE$.connectToIn(o);
    }

    public static void processLinesFully(Function1<String, BoxedUnit> processLine, Function0<String> readLine) {
        BasicIO$.MODULE$.processLinesFully(processLine, readLine);
    }

    public static Function1<InputStream, BoxedUnit> processFully(Function1<String, BoxedUnit> processLine) {
        return BasicIO$.MODULE$.processFully(processLine);
    }

    public static Function1<InputStream, BoxedUnit> processFully(Appendable buffer) {
        return BasicIO$.MODULE$.processFully(buffer);
    }

    public static void close(Closeable c) {
        BasicIO$.MODULE$.close(c);
    }

    public static Function1<InputStream, BoxedUnit> getErr(Option<ProcessLogger> log) {
        return BasicIO$.MODULE$.getErr(log);
    }

    public static ProcessIO apply(boolean withIn, ProcessLogger log) {
        return BasicIO$.MODULE$.apply(withIn, log);
    }

    public static ProcessIO apply(boolean withIn, Appendable buffer, Option<ProcessLogger> log) {
        return BasicIO$.MODULE$.apply(withIn, buffer, log);
    }

    public static ProcessIO apply(boolean withIn, Function1<String, BoxedUnit> output, Option<ProcessLogger> log) {
        return BasicIO$.MODULE$.apply(withIn, output, log);
    }

    public static String Newline() {
        return BasicIO$.MODULE$.Newline();
    }

    public static int BufferSize() {
        return BasicIO$.MODULE$.BufferSize();
    }

    public static final class LazilyListed<T> {
        private final Function1<T, BoxedUnit> process;
        private final Function1<Object, BoxedUnit> done;
        private final LazyList<T> lazyList;

        public Function1<T, BoxedUnit> process() {
            return this.process;
        }

        public Function1<Object, BoxedUnit> done() {
            return this.done;
        }

        public LazyList<T> lazyList() {
            return this.lazyList;
        }

        public LazilyListed(Function1<T, BoxedUnit> process, Function1<Object, BoxedUnit> done, LazyList<T> lazyList) {
            this.process = process;
            this.done = done;
            this.lazyList = lazyList;
        }
    }

    public static final class Streamed<T> {
        private final Function1<T, BoxedUnit> process;
        private final Function1<Object, BoxedUnit> done;
        private final Function0<Stream<T>> stream;

        public Function1<T, BoxedUnit> process() {
            return this.process;
        }

        public Function1<Object, BoxedUnit> done() {
            return this.done;
        }

        public Function0<Stream<T>> stream() {
            return this.stream;
        }

        public Streamed(Function1<T, BoxedUnit> process, Function1<Object, BoxedUnit> done, Function0<Stream<T>> stream) {
            this.process = process;
            this.done = done;
            this.stream = stream;
        }
    }

    public static interface Uncloseable
    extends Closeable {
        public static /* synthetic */ void close$(Uncloseable $this) {
            $this.close();
        }

        @Override
        default public void close() {
        }

        public static void $init$(Uncloseable $this) {
        }
    }
}

