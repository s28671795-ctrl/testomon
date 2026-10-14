/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Seq;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.sys.process.Parser$;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessBuilder$;
import scala.sys.process.ProcessBuilderImpl;

@ScalaSignature(bytes="\u0006\u0005\u0005\rdaB\t\u0013!\u0003\r\t!\u0007\u0005\u0006=\u0001!\ta\b\u0005\u0006G\u0001!\t\u0001\n\u0005\u0006G\u0001!\tA\u000e\u0005\u0006G\u0001!\tA\u0010\u0005\u0006G\u0001!\tA\u0011\u0005\u0006G\u0001!\t!\u0016\u0005\u0006G\u0001!\t!\u0017\u0005\u0006G\u0001!\t\u0001\u0019\u0005\u0006G\u0001!\t\u0001\u001a\u0005\u0006G\u0001!\tA\u001b\u0005\u0006G\u0001!\t\u0001\u001e\u0005\u0006G\u0001!\t! \u0005\u0007G\u0001!\t!a\u0002\t\u000f\u0005u\u0001\u0001\"\u0001\u0002 !9\u00111\u000b\u0001\u0005\u0002\u0005U\u0003bBA*\u0001\u0011\u0005\u0011q\f\u0002\u0010!J|7-Z:t\u0007J,\u0017\r^5p]*\u00111\u0003F\u0001\baJ|7-Z:t\u0015\t)b#A\u0002tsNT\u0011aF\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001!\u0004\u0005\u0002\u001c95\ta#\u0003\u0002\u001e-\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001\u0011\u0011\u0005m\t\u0013B\u0001\u0012\u0017\u0005\u0011)f.\u001b;\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005\u0015J\u0003C\u0001\u0014(\u001b\u0005\u0011\u0012B\u0001\u0015\u0013\u00059\u0001&o\\2fgN\u0014U/\u001b7eKJDQA\u000b\u0002A\u0002-\nqaY8n[\u0006tG\r\u0005\u0002-g9\u0011Q&\r\t\u0003]Yi\u0011a\f\u0006\u0003aa\ta\u0001\u0010:p_Rt\u0014B\u0001\u001a\u0017\u0003\u0019\u0001&/\u001a3fM&\u0011A'\u000e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005I2BCA\u00138\u0011\u0015Q3\u00011\u00019!\rIDhK\u0007\u0002u)\u00111HF\u0001\u000bG>dG.Z2uS>t\u0017BA\u001f;\u0005\r\u0019V-\u001d\u000b\u0004K}\u0002\u0005\"\u0002\u0016\u0005\u0001\u0004Y\u0003\"B!\u0005\u0001\u0004A\u0014!C1sOVlWM\u001c;t)\u0011)3\tR'\t\u000b)*\u0001\u0019A\u0016\t\u000b\u0015+\u0001\u0019\u0001$\u0002\u0007\r<H\r\u0005\u0002H\u0015:\u0011a\u0005S\u0005\u0003\u0013J\tq\u0002\u001d:pG\u0016\u001c8/\u00138uKJt\u0017\r\\\u0005\u0003\u00172\u0013AAR5mK*\u0011\u0011J\u0005\u0005\u0006\u001d\u0016\u0001\raT\u0001\tKb$(/Y#omB\u00191\u0004\u0015*\n\u0005E3\"A\u0003\u001fsKB,\u0017\r^3e}A!1dU\u0016,\u0013\t!fC\u0001\u0004UkBdWM\r\u000b\u0005KY;\u0006\fC\u0003+\r\u0001\u0007\u0001\bC\u0003F\r\u0001\u0007a\tC\u0003O\r\u0001\u0007q\n\u0006\u0003&5n{\u0006\"\u0002\u0016\b\u0001\u0004Y\u0003\"B#\b\u0001\u0004a\u0006cA\u000e^\r&\u0011aL\u0006\u0002\u0007\u001fB$\u0018n\u001c8\t\u000b9;\u0001\u0019A(\u0015\t\u0015\n'm\u0019\u0005\u0006U!\u0001\r\u0001\u000f\u0005\u0006\u000b\"\u0001\r\u0001\u0018\u0005\u0006\u001d\"\u0001\ra\u0014\u000b\u0003K\u0015DQAZ\u0005A\u0002\u001d\fqAY;jY\u0012,'\u000f\u0005\u0002HQ&\u0011\u0011\u000e\u0014\u0002\u0010\u0015B\u0013xnY3tg\n+\u0018\u000e\u001c3feR\u00111N\u001d\t\u0003Y>t!AJ7\n\u00059\u0014\u0012A\u0004)s_\u000e,7o\u001d\"vS2$WM]\u0005\u0003aF\u00141BR5mK\n+\u0018\u000e\u001c3fe*\u0011aN\u0005\u0005\u0006g*\u0001\rAR\u0001\u0005M&dW\r\u0006\u0002vqB\u0011AN^\u0005\u0003oF\u0014!\"\u0016*M\u0005VLG\u000eZ3s\u0011\u0015I8\u00021\u0001{\u0003\r)(\u000f\u001c\t\u0003\u000fnL!\u0001 '\u0003\u0007U\u0013F\n\u0006\u0002&}\"1q\u0010\u0004a\u0001\u0003\u0003\tQA^1mk\u0016\u00042aGA\u0002\u0013\r\t)A\u0006\u0002\b\u0005>|G.Z1o)\u0015)\u0013\u0011BA\u0007\u0011\u0019\tY!\u0004a\u0001W\u0005!a.Y7f\u0011!\ty!\u0004CA\u0002\u0005E\u0011!C3ySR4\u0016\r\\;f!\u0015Y\u00121CA\f\u0013\r\t)B\u0006\u0002\ty\tLh.Y7f}A\u00191$!\u0007\n\u0007\u0005maCA\u0002J]R\f\u0001\"\u00199qYf\u001cV-]\u000b\u0005\u0003C\tY\u0004\u0006\u0003\u0002$\u00055C\u0003BA\u0013\u0003[\u0001B!\u000f\u001f\u0002(A\u0019A.!\u000b\n\u0007\u0005-\u0012O\u0001\u0004T_V\u00148-\u001a\u0005\b\u0003_q\u00019AA\u0019\u0003\u001d\u0019wN\u001c<feR\u0004raGA\u001a\u0003o\t9#C\u0002\u00026Y\u0011\u0011BR;oGRLwN\\\u0019\u0011\t\u0005e\u00121\b\u0007\u0001\t\u001d\tiD\u0004b\u0001\u0003\u007f\u0011\u0011\u0001V\t\u0005\u0003\u0003\n9\u0005E\u0002\u001c\u0003\u0007J1!!\u0012\u0017\u0005\u001dqu\u000e\u001e5j]\u001e\u00042aGA%\u0013\r\tYE\u0006\u0002\u0004\u0003:L\bbBA(\u001d\u0001\u0007\u0011\u0011K\u0001\tEVLG\u000eZ3sgB!\u0011\bPA\u001c\u0003\r\u0019\u0017\r\u001e\u000b\u0006K\u0005]\u0013\u0011\f\u0005\u0007g>\u0001\r!a\n\t\u000f\u0005ms\u00021\u0001\u0002^\u0005)a-\u001b7fgB!1\u0004UA\u0014)\r)\u0013\u0011\r\u0005\b\u00037\u0002\u0002\u0019AA\u0013\u0001")
public interface ProcessCreation {
    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, String command) {
        return $this.apply(command);
    }

    default public ProcessBuilder apply(String command) {
        return this.apply(command, (Option<File>)None$.MODULE$, (scala.collection.immutable.Seq<Tuple2<String, String>>)Nil$.MODULE$);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, Seq command) {
        return $this.apply(command);
    }

    default public ProcessBuilder apply(Seq<String> command) {
        return this.apply(command, (Option<File>)None$.MODULE$, (scala.collection.immutable.Seq<Tuple2<String, String>>)Nil$.MODULE$);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, String command, Seq arguments) {
        return $this.apply(command, arguments);
    }

    default public ProcessBuilder apply(String command, Seq<String> arguments) {
        if (arguments == null) {
            throw null;
        }
        return this.apply((Seq<String>)((Seq)arguments.prepended(command)), (Option<File>)None$.MODULE$, (scala.collection.immutable.Seq<Tuple2<String, String>>)Nil$.MODULE$);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, String command, File cwd, scala.collection.immutable.Seq extraEnv) {
        return $this.apply(command, cwd, (scala.collection.immutable.Seq<Tuple2<String, String>>)extraEnv);
    }

    default public ProcessBuilder apply(String command, File cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return this.apply(command, new Some<File>(cwd), extraEnv);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, Seq command, File cwd, scala.collection.immutable.Seq extraEnv) {
        return $this.apply((Seq<String>)command, cwd, (scala.collection.immutable.Seq<Tuple2<String, String>>)extraEnv);
    }

    default public ProcessBuilder apply(Seq<String> command, File cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return this.apply(command, new Some<File>(cwd), extraEnv);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, String command, Option cwd, scala.collection.immutable.Seq extraEnv) {
        return $this.apply(command, (Option<File>)cwd, (scala.collection.immutable.Seq<Tuple2<String, String>>)extraEnv);
    }

    default public ProcessBuilder apply(String command, Option<File> cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        return this.apply(Parser$.MODULE$.tokenize(command), cwd, extraEnv);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, Seq command, Option cwd, scala.collection.immutable.Seq extraEnv) {
        return $this.apply((Seq<String>)command, (Option<File>)cwd, (scala.collection.immutable.Seq<Tuple2<String, String>>)extraEnv);
    }

    default public ProcessBuilder apply(Seq<String> command, Option<File> cwd, scala.collection.immutable.Seq<Tuple2<String, String>> extraEnv) {
        java.lang.ProcessBuilder jpb = new java.lang.ProcessBuilder((String[])command.toArray(ClassTag$.MODULE$.apply(String.class)));
        if (cwd == null) {
            throw null;
        }
        if (!cwd.isEmpty()) {
            File file = cwd.get();
            jpb.directory(file);
        }
        extraEnv.foreach((Function1<Tuple2, String> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                String k = (String)x0$1._1();
                String v = (String)x0$1._2();
                return jpb.environment().put(k, v);
            }
            throw new MatchError((Object)null);
        });
        return this.apply(jpb);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, java.lang.ProcessBuilder builder) {
        return $this.apply(builder);
    }

    default public ProcessBuilder apply(java.lang.ProcessBuilder builder) {
        return new ProcessBuilderImpl.Simple(ProcessBuilder$.MODULE$, builder);
    }

    public static /* synthetic */ ProcessBuilder.FileBuilder apply$(ProcessCreation $this, File file) {
        return $this.apply(file);
    }

    default public ProcessBuilder.FileBuilder apply(File file) {
        return new ProcessBuilderImpl.FileImpl(ProcessBuilder$.MODULE$, file);
    }

    public static /* synthetic */ ProcessBuilder.URLBuilder apply$(ProcessCreation $this, URL url2) {
        return $this.apply(url2);
    }

    default public ProcessBuilder.URLBuilder apply(URL url2) {
        return new ProcessBuilderImpl.URLImpl(ProcessBuilder$.MODULE$, url2);
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, boolean value) {
        return $this.apply(value);
    }

    default public ProcessBuilder apply(boolean value) {
        return this.apply(Boolean.toString(value), () -> {
            if (value) {
                return 0;
            }
            return 1;
        });
    }

    public static /* synthetic */ ProcessBuilder apply$(ProcessCreation $this, String name, Function0 exitValue) {
        return $this.apply(name, exitValue);
    }

    default public ProcessBuilder apply(String name, Function0<Object> exitValue) {
        return new ProcessBuilderImpl.Dummy(ProcessBuilder$.MODULE$, name, exitValue);
    }

    public static /* synthetic */ Seq applySeq$(ProcessCreation $this, Seq builders, Function1 convert) {
        return $this.applySeq(builders, convert);
    }

    default public <T> Seq<ProcessBuilder.Source> applySeq(Seq<T> builders, Function1<T, ProcessBuilder.Source> convert) {
        return (Seq)builders.map(convert);
    }

    public static /* synthetic */ ProcessBuilder cat$(ProcessCreation $this, ProcessBuilder.Source file, scala.collection.immutable.Seq files) {
        return $this.cat(file, files);
    }

    default public ProcessBuilder cat(ProcessBuilder.Source file, scala.collection.immutable.Seq<ProcessBuilder.Source> files) {
        if (files == null) {
            throw null;
        }
        return this.cat((Seq)files.prepended(file));
    }

    public static /* synthetic */ ProcessBuilder cat$(ProcessCreation $this, Seq files) {
        return $this.cat(files);
    }

    default public ProcessBuilder cat(Seq<ProcessBuilder.Source> files) {
        Predef$.MODULE$.require(files.nonEmpty());
        return (ProcessBuilder)((IterableOnceOps)files.map((Function1<ProcessBuilder.Source, ProcessBuilder> & Serializable)x$2 -> x$2.cat())).reduceLeft((Function2<ProcessBuilder, ProcessBuilder, ProcessBuilder> & Serializable)(x$3, x$4) -> x$3.$hash$amp$amp((ProcessBuilder)x$4));
    }

    public static /* synthetic */ java.lang.ProcessBuilder $anonfun$apply$1(java.lang.ProcessBuilder jpb$1, File x$1) {
        return jpb$1.directory(x$1);
    }

    public static void $init$(ProcessCreation $this) {
    }
}

