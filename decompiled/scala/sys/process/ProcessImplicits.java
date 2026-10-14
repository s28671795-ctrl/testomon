/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.net.URL;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.collection.Seq;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.sys.process.Process$;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessBuilder$;
import scala.sys.process.ProcessBuilderImpl;
import scala.sys.process.ProcessCreation;

@ScalaSignature(bytes="\u0006\u0005Q4q\u0001C\u0005\u0011\u0002\u0007\u0005\u0001\u0003C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0003\u001b\u0001\u0011\r1\u0004C\u0003@\u0001\u0011\r\u0001\tC\u0003N\u0001\u0011\ra\nC\u0003X\u0001\u0011\r\u0001\fC\u0003b\u0001\u0011\r!\rC\u0003q\u0001\u0011\r\u0011O\u0001\tQe>\u001cWm]:J[Bd\u0017nY5ug*\u0011!bC\u0001\baJ|7-Z:t\u0015\taQ\"A\u0002tsNT\u0011AD\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0011\u0003\u0005\u0002\u0013'5\tQ\"\u0003\u0002\u0015\u001b\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#A\f\u0011\u0005IA\u0012BA\r\u000e\u0005\u0011)f.\u001b;\u0002#\t,\u0018\u000e\u001c3feN$v\u000e\u0015:pG\u0016\u001c8/\u0006\u0002\u001dgQ\u0011Q\u0004\u0010\u000b\u0003=1\u00022a\b\u0012%\u001b\u0005\u0001#BA\u0011\u000e\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003G\u0001\u00121aU3r!\t)\u0013F\u0004\u0002'O5\t\u0011\"\u0003\u0002)\u0013\u0005q\u0001K]8dKN\u001c()^5mI\u0016\u0014\u0018B\u0001\u0016,\u0005\u0019\u0019v.\u001e:dK*\u0011\u0001&\u0003\u0005\u0006[\t\u0001\u001dAL\u0001\bG>tg/\u001a:u!\u0011\u0011r&\r\u0013\n\u0005Aj!!\u0003$v]\u000e$\u0018n\u001c82!\t\u00114\u0007\u0004\u0001\u0005\u000bQ\u0012!\u0019A\u001b\u0003\u0003Q\u000b\"AN\u001d\u0011\u0005I9\u0014B\u0001\u001d\u000e\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u001e\n\u0005mj!aA!os\")QH\u0001a\u0001}\u0005A!-^5mI\u0016\u00148\u000fE\u0002 EE\n\u0001CY;jY\u0012,'\u000fV8Qe>\u001cWm]:\u0015\u0005\u0005#\u0005C\u0001\u0014C\u0013\t\u0019\u0015B\u0001\bQe>\u001cWm]:Ck&dG-\u001a:\t\u000b\u0015\u001b\u0001\u0019\u0001$\u0002\u000f\t,\u0018\u000e\u001c3feB\u0011qI\u0013\b\u0003M!K!!S\u0005\u0002\u001fA\u0014xnY3tg&sG/\u001a:oC2L!a\u0013'\u0003\u001f)\u0003&o\\2fgN\u0014U/\u001b7eKJT!!S\u0005\u0002\u001b\u0019LG.\u001a+p!J|7-Z:t)\ty%\u000b\u0005\u0002&!&\u0011\u0011k\u000b\u0002\f\r&dWMQ;jY\u0012,'\u000fC\u0003T\t\u0001\u0007A+\u0001\u0003gS2,\u0007CA$V\u0013\t1FJ\u0001\u0003GS2,\u0017\u0001D;sYR{\u0007K]8dKN\u001cHCA-]!\t)#,\u0003\u0002\\W\tQQK\u0015'Ck&dG-\u001a:\t\u000bu+\u0001\u0019\u00010\u0002\u0007U\u0014H\u000e\u0005\u0002H?&\u0011\u0001\r\u0014\u0002\u0004+Jc\u0015aD:ue&tw\rV8Qe>\u001cWm]:\u0015\u0005\u0005\u001b\u0007\"\u00023\u0007\u0001\u0004)\u0017aB2p[6\fg\u000e\u001a\t\u0003M6t!aZ6\u0011\u0005!lQ\"A5\u000b\u0005)|\u0011A\u0002\u001fs_>$h(\u0003\u0002m\u001b\u00051\u0001K]3eK\u001aL!A\\8\u0003\rM#(/\u001b8h\u0015\taW\"\u0001\ntiJLgnZ*fcR{\u0007K]8dKN\u001cHCA!s\u0011\u0015!w\u00011\u0001t!\ry\"%\u001a")
public interface ProcessImplicits {
    public static /* synthetic */ Seq buildersToProcess$(ProcessImplicits $this, Seq builders, Function1 convert) {
        return $this.buildersToProcess(builders, convert);
    }

    default public <T> Seq<ProcessBuilder.Source> buildersToProcess(Seq<T> builders, Function1<T, ProcessBuilder.Source> convert) {
        return (Seq)builders.map(convert);
    }

    public static /* synthetic */ ProcessBuilder builderToProcess$(ProcessImplicits $this, java.lang.ProcessBuilder builder) {
        return $this.builderToProcess(builder);
    }

    default public ProcessBuilder builderToProcess(java.lang.ProcessBuilder builder) {
        return new ProcessBuilderImpl.Simple(ProcessBuilder$.MODULE$, builder);
    }

    public static /* synthetic */ ProcessBuilder.FileBuilder fileToProcess$(ProcessImplicits $this, File file) {
        return $this.fileToProcess(file);
    }

    default public ProcessBuilder.FileBuilder fileToProcess(File file) {
        return new ProcessBuilderImpl.FileImpl(ProcessBuilder$.MODULE$, file);
    }

    public static /* synthetic */ ProcessBuilder.URLBuilder urlToProcess$(ProcessImplicits $this, URL url2) {
        return $this.urlToProcess(url2);
    }

    default public ProcessBuilder.URLBuilder urlToProcess(URL url2) {
        return new ProcessBuilderImpl.URLImpl(ProcessBuilder$.MODULE$, url2);
    }

    public static /* synthetic */ ProcessBuilder stringToProcess$(ProcessImplicits $this, String command) {
        return $this.stringToProcess(command);
    }

    /*
     * WARNING - void declaration
     */
    default public ProcessBuilder stringToProcess(String command) {
        void apply_extraEnv;
        Process$ apply_this = Process$.MODULE$;
        Nil$ nil$ = Nil$.MODULE$;
        None$ apply_cwd = None$.MODULE$;
        return ProcessCreation.apply$((ProcessCreation)apply_this, command, (Option)apply_cwd, (scala.collection.immutable.Seq)apply_extraEnv);
    }

    public static /* synthetic */ ProcessBuilder stringSeqToProcess$(ProcessImplicits $this, Seq command) {
        return $this.stringSeqToProcess(command);
    }

    /*
     * WARNING - void declaration
     */
    default public ProcessBuilder stringSeqToProcess(Seq<String> command) {
        void apply_extraEnv;
        Process$ apply_this = Process$.MODULE$;
        Nil$ nil$ = Nil$.MODULE$;
        None$ apply_cwd = None$.MODULE$;
        return ProcessCreation.apply$((ProcessCreation)apply_this, command, (Option)apply_cwd, (scala.collection.immutable.Seq)apply_extraEnv);
    }

    public static void $init$(ProcessImplicits $this) {
    }
}

