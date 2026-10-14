/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.Function1;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.impl.ExecutionContextImpl$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005Eda\u0002\u000e\u001c!\u0003\r\t\u0001\t\u0005\u0006K\u0001!\tA\n\u0005\u0006U\u00011\ta\u000b\u0005\u0006m\u00011\ta\u000e\u0005\u0006=\u0002!\taX\u0004\u0006mnA\ta\u001e\u0004\u00065mA\t\u0001\u001f\u0005\u0006s\u001a!\tA\u001f\u0005\tw\u001aA)\u0019!C\u0003y\u001e9\u0011\u0011\u0001\u0004\t\u0002\u0005\raaBA\u0004\r!\u0005\u0011\u0011\u0002\u0005\u0007s*!\t!!\u0005\t\u000f\u0005M!\u0002\"\u0012\u0002\u0016!1!F\u0003C#\u00033AaA\u000e\u0006\u0005F\u0005u\u0001BCA\u0011\r!\u0015\r\u0011\"\u0001\u001ey\u001e9\u00111\u0005\u0004\t\u0002\u0005\u0015baBA\u0014\r!\u0005\u0011\u0011\u0006\u0005\u0007sF!\t!a\u000b\t\rm\fBqAA\u0017\u0011\u001d\tyC\u0002C\u0001\u0003cAq!a\f\u0007\t\u0003\t)\u0006C\u0004\u0002Z\u0019!\t!a\u0017\t\u000f\u0005ec\u0001\"\u0001\u0002h!I\u00111\u000e\u0004C\u0002\u0013\u0015\u0011Q\u000e\u0005\t\u0003_2\u0001\u0015!\u0004\u0002P\t\u0001R\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0006\u00039u\t!bY8oGV\u0014(/\u001a8u\u0015\u0005q\u0012!B:dC2\f7\u0001A\n\u0003\u0001\u0005\u0002\"AI\u0012\u000e\u0003uI!\u0001J\u000f\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\tq\u0005\u0005\u0002#Q%\u0011\u0011&\b\u0002\u0005+:LG/A\u0004fq\u0016\u001cW\u000f^3\u0015\u0005\u001db\u0003\"B\u0017\u0003\u0001\u0004q\u0013\u0001\u0003:v]:\f'\r\\3\u0011\u0005=\"T\"\u0001\u0019\u000b\u0005E\u0012\u0014\u0001\u00027b]\u001eT\u0011aM\u0001\u0005U\u00064\u0018-\u0003\u00026a\tA!+\u001e8oC\ndW-A\u0007sKB|'\u000f\u001e$bS2,(/\u001a\u000b\u0003OaBQ!O\u0002A\u0002i\nQaY1vg\u0016\u0004\"aO\"\u000f\u0005q\neBA\u001fA\u001b\u0005q$BA  \u0003\u0019a$o\\8u}%\ta$\u0003\u0002C;\u00059\u0001/Y2lC\u001e,\u0017B\u0001#F\u0005%!\u0006N]8xC\ndWM\u0003\u0002C;!\"\u0001h\u0012&M!\t\u0011\u0003*\u0003\u0002J;\tqA-\u001a9sK\u000e\fG/\u001a3OC6,\u0017%A&\u0002\u0003Q\fTaI'V3Z\u0003\"A\u0014*\u000f\u0005=\u0003\u0006CA\u001f\u001e\u0013\t\tV$\u0001\u0004Qe\u0016$WMZ\u0005\u0003'R\u0013aa\u0015;sS:<'BA)\u001e\u0013\t1v+A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HE\r\u0006\u00031v\ta\u0002Z3qe\u0016\u001c\u0017\r^3e\u001d\u0006lW-M\u0003$5nc\u0006L\u0004\u0002#7&\u0011\u0001,H\u0019\u0005E\tjRLA\u0003tG\u0006d\u0017-A\u0004qe\u0016\u0004\u0018M]3\u0015\u0003\u0001\u0004\"!\u0019\u0001\u000e\u0003mAc\u0001B2gO&T\u0007C\u0001\u0012e\u0013\t)WD\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001i\u0003A\u0002(/\u001a9be\u0006$\u0018n\u001c8!_\u001a\u0004S\t_3dkRLwN\\\"p]R,\u0007\u0010^:!o&dG\u000e\t2fAI,Wn\u001c<fI\u0006)1/\u001b8dK\u0006\n1.\u0001\u00043]E\u0012d\u0006\r\u0015\u0005\u00015\u001cH\u000f\u0005\u0002oc6\tqN\u0003\u0002q;\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005I|'\u0001E5na2L7-\u001b;O_R4u.\u001e8e\u0003\ri7oZ\u0011\u0002k\u0006!\u0019dQ1o]>$\bEZ5oI\u0002\ng\u000eI5na2L7-\u001b;!\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRt\u0003%W8vA5Lw\r\u001b;!C\u0012$'\"\u00198!Q%l\u0007\u000f\\5dSR\u0004Sm\u0019\u001e!\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRL\u0003\u0005]1sC6,G/\u001a:!i>\u0004\u0013p\\;sA5,G\u000f[8e])QA\u000b[3!\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR\u0004\u0013n\u001d\u0011vg\u0016$\u0007\u0005^8!G>tg-[4ve\u0016\u0004\u0003n\\<!C:$\u0007e\u001c8!o\"L7\r\u001b\u0006uQJ,\u0017\r\u001a\u0011q_>d7\u000fI1ts:\u001c\u0007N]8o_V\u001c\b\u0005^1tWN\u0004\u0003f];dQ\u0002\n7\u000f\t$viV\u0014Xm]\u0015!o&dG\u000e\t:v]2R1o\u001c\u0011uQ\u0016\u00043\u000f]3dS\u001aL7\rI#yK\u000e,H/[8o\u0007>tG/\u001a=uAQD\u0017\r\u001e\u0011jg\u0002\u001aX\r\\3di\u0016$\u0007%[:!S6\u0004xN\u001d;b]Rt#BC%gAe|WO\u001d\u0011baBd\u0017nY1uS>t\u0007\u0005Z8fg\u0002rw\u000e\u001e\u0011eK\u001aLg.\u001a\u0011b]\u0002*\u00050Z2vi&|gnQ8oi\u0016DH\u000fI3mg\u0016<\b.\u001a:fY)\u0019wN\\:jI\u0016\u0014\b%^:j]\u001e\u00043kY1mC\u001e\u001a\be\u001a7pE\u0006d\u0007%\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;!Ef\u0004C-\u001a4j]&twM\u0003;iK\u00022w\u000e\u001c7po&twM\u000f\u0006\u000bS6\u0004H.[2ji\u00022\u0018\r\u001c\u0011fGj\u00023oY1mC:\u001awN\\2veJ,g\u000e\u001e\u0018Fq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0002j\u0004e]2bY\u0006t3m\u001c8dkJ\u0014XM\u001c;/\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRts\r\\8cC2\f\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0011\u0005\u000541C\u0001\u0004\"\u0003\u0019a\u0014N\\5u}Q\tq/\u0001\u0004hY>\u0014\u0017\r\\\u000b\u0002{B\u0011\u0011M`\u0005\u0003\u007fn\u0011\u0001$\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;Fq\u0016\u001cW\u000f^8s\u0003%\u0001\u0018M]1tSRL7\rE\u0002\u0002\u0006)i\u0011A\u0002\u0002\na\u0006\u0014\u0018m]5uS\u000e\u001cRAC\u0011~\u0003\u0017\u00012!YA\u0007\u0013\r\tya\u0007\u0002\u0011\u0005\u0006$8\r[5oO\u0016CXmY;u_J$\"!a\u0001\u0002%M,(-\\5u\r>\u0014X\t_3dkRLwN\u001c\u000b\u0004O\u0005]\u0001\"B\u0017\r\u0001\u0004qCcA\u0014\u0002\u001c!)Q&\u0004a\u0001]Q\u0019q%a\b\t\u000b-s\u0001\u0019\u0001\u001e\u0002\u001b=\u0004\bo\u001c:uk:L7\u000f^5d\u0003%IU\u000e\u001d7jG&$8\u000fE\u0002\u0002\u0006E\u0011\u0011\"S7qY&\u001c\u0017\u000e^:\u0014\u0005E\tCCAA\u0013+\u0005\u0001\u0017a\u00054s_6,\u00050Z2vi>\u00148+\u001a:wS\u000e,GCBA\u001a\u0003s\tY\u0005E\u0002b\u0003kI1!a\u000e\u001c\u0005})\u00050Z2vi&|gnQ8oi\u0016DH/\u0012=fGV$xN]*feZL7-\u001a\u0005\b\u0003w!\u0002\u0019AA\u001f\u0003\u0005)\u0007\u0003BA \u0003\u000fj!!!\u0011\u000b\u0007q\t\u0019EC\u0002\u0002FI\nA!\u001e;jY&!\u0011\u0011JA!\u0005=)\u00050Z2vi>\u00148+\u001a:wS\u000e,\u0007bBA')\u0001\u0007\u0011qJ\u0001\te\u0016\u0004xN\u001d;feB)!%!\u0015;O%\u0019\u00111K\u000f\u0003\u0013\u0019+hn\u0019;j_:\fD\u0003BA\u001a\u0003/Bq!a\u000f\u0016\u0001\u0004\ti$\u0001\u0007ge>lW\t_3dkR|'\u000fF\u0003~\u0003;\n)\u0007C\u0004\u0002<Y\u0001\r!a\u0018\u0011\t\u0005}\u0012\u0011M\u0005\u0005\u0003G\n\tE\u0001\u0005Fq\u0016\u001cW\u000f^8s\u0011\u001d\tiE\u0006a\u0001\u0003\u001f\"2!`A5\u0011\u001d\tYd\u0006a\u0001\u0003?\nq\u0002Z3gCVdGOU3q_J$XM]\u000b\u0003\u0003\u001f\n\u0001\u0003Z3gCVdGOU3q_J$XM\u001d\u0011")
public interface ExecutionContext {
    public static Function1<Throwable, BoxedUnit> defaultReporter() {
        return ExecutionContext$.MODULE$.defaultReporter();
    }

    public static ExecutionContextExecutor fromExecutor(Executor e) {
        return ExecutionContext$.MODULE$.fromExecutor(e);
    }

    public static ExecutionContextExecutor fromExecutor(Executor e, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutor(e, reporter);
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService e) {
        return ExecutionContext$.MODULE$.fromExecutorService(e);
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService e, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutorService(e, reporter);
    }

    public static ExecutionContextExecutor global() {
        return ExecutionContext$.MODULE$.global();
    }

    public void execute(Runnable var1);

    public void reportFailure(Throwable var1);

    public static /* synthetic */ ExecutionContext prepare$(ExecutionContext $this) {
        return $this.prepare();
    }

    default public ExecutionContext prepare() {
        return this;
    }

    public static void $init$(ExecutionContext $this) {
    }
}

