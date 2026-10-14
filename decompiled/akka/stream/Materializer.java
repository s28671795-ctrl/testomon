/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.ClassicActorContextProvider;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Props;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.MaterializationContext;
import akka.stream.Materializer$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t}a!B\r\u001b\u0003\u0003y\u0002\"\u0002\u0014\u0001\t\u00039\u0003\"\u0002\u0016\u0001\r\u0003Y\u0003\"B\u001d\u0001\r\u0003Q\u0004\"B\u001d\u0001\r\u0003y\u0005\"B:\u0001\r\u0007!\b\"B>\u0001\r\u0003a\bbBA\u0016\u0001\u0019\u0005\u0011Q\u0006\u0005\b\u0003o\u0001a\u0011AA\u001d\u0011\u001d\t\u0019\u0005\u0001D\u0001\u0003\u000bBq!!\u0019\u0001\r\u0003\t\u0019\u0007C\u0004\u0002l\u00011\t!!\u001c\t\u000f\u0005U\u0004A\"\u0001\u0002x!A\u0011q\u0010\u0001\u0007\u0002q\t\t\t\u0003\u0005\u0002\u001e\u00021\t\u0001HAP\u0011!\tI\u000b\u0001D\u00019\u0005-\u0006bBAb\u0001\u0019\u0005\u0011QY\u0004\b\u0003[T\u0002\u0012AAx\r\u0019I\"\u0004#\u0001\u0002r\"1aE\u0005C\u0001\u0003gDq!!>\u0013\t\u0007\t9\u0010\u0003\u0004g%\u0011\u0005!1\u0001\u0005\b\u0005\u001f\u0011B\u0011\u0001B\t\u0011\u00191'\u0003\"\u0001\u0003\u0016!9!q\u0002\n\u0005\u0002\tm!\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014(BA\u000e\u001d\u0003\u0019\u0019HO]3b[*\tQ$\u0001\u0003bW.\f7\u0001A\n\u0003\u0001\u0001\u0002\"!\t\u0013\u000e\u0003\tR\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\t\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001)!\tI\u0003!D\u0001\u001b\u000399\u0018\u000e\u001e5OC6,\u0007K]3gSb$\"\u0001\u000b\u0017\t\u000b5\u0012\u0001\u0019\u0001\u0018\u0002\t9\fW.\u001a\t\u0003_Yr!\u0001\r\u001b\u0011\u0005E\u0012S\"\u0001\u001a\u000b\u0005Mr\u0012A\u0002\u001fs_>$h(\u0003\u00026E\u00051\u0001K]3eK\u001aL!a\u000e\u001d\u0003\rM#(/\u001b8h\u0015\t)$%A\u0006nCR,'/[1mSj,WCA\u001e?)\tat\t\u0005\u0002>}1\u0001A!B \u0004\u0005\u0004\u0001%aA'biF\u0011\u0011\t\u0012\t\u0003C\tK!a\u0011\u0012\u0003\u000f9{G\u000f[5oOB\u0011\u0011%R\u0005\u0003\r\n\u00121!\u00118z\u0011\u0015A5\u00011\u0001J\u0003!\u0011XO\u001c8bE2,\u0007\u0003B\u0015K\u0019rJ!a\u0013\u000e\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\u0005%j\u0015B\u0001(\u001b\u0005-\u0019En\\:fINC\u0017\r]3\u0016\u0005A\u0013FcA)T+B\u0011QH\u0015\u0003\u0006\u007f\u0011\u0011\r\u0001\u0011\u0005\u0006\u0011\u0012\u0001\r\u0001\u0016\t\u0005S)c\u0015\u000bC\u0003W\t\u0001\u0007q+A\teK\u001a\fW\u000f\u001c;BiR\u0014\u0018NY;uKN\u0004\"!\u000b-\n\u0005eS\"AC!uiJL'-\u001e;fg\"\u001aQk\u00170\u0011\u0005\u0005b\u0016BA/#\u00059!W\r\u001d:fG\u0006$X\r\u001a(b[\u0016\fDaH0c_B\u0011\u0011\u0005Y\u0005\u0003C\n\u0012aaU=nE>d\u0017'B\u0012dK&4GCA0e\u0011\u0015ic\u00041\u0001/\u0013\t1w-A\u0003baBd\u0017P\u0003\u0002iE\u000511+_7c_2\fTa\t6n]\"t!a[7\u000f\u0005Eb\u0017\"A\u0012\n\u0005!\u0014\u0013\u0007\u0002\u0013lY\u000e\n4!\n9r\u001f\u0005\t\u0018%\u0001:\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/\u0001\tfq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yiV\tQ\u000f\u0005\u0002ws6\tqO\u0003\u0002yE\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005i<(\u0001G#yK\u000e,H/[8o\u0007>tG/\u001a=u\u000bb,7-\u001e;pe\u0006a1o\u00195fIVdWm\u00148dKR)Q0a\u0002\u0002\u0018A\u0019a0a\u0001\u000e\u0003}T1!!\u0001\u001d\u0003\u0015\t7\r^8s\u0013\r\t)a \u0002\f\u0007\u0006t7-\u001a7mC\ndW\rC\u0004\u0002\n\u0019\u0001\r!a\u0003\u0002\u000b\u0011,G.Y=\u0011\t\u00055\u00111C\u0007\u0003\u0003\u001fQ1!!\u0005x\u0003!!WO]1uS>t\u0017\u0002BA\u000b\u0003\u001f\u0011aBR5oSR,G)\u001e:bi&|g\u000eC\u0004\u0002\u001a\u0019\u0001\r!a\u0007\u0002\tQ\f7o\u001b\t\u0005\u0003;\t9#\u0004\u0002\u0002 )!\u0011\u0011EA\u0012\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\u0015\u0012\u0001\u00026bm\u0006LA!!\u000b\u0002 \tA!+\u001e8oC\ndW-\u0001\ftG\",G-\u001e7f/&$\bNR5yK\u0012$U\r\\1z)\u001di\u0018qFA\u001a\u0003kAq!!\r\b\u0001\u0004\tY!\u0001\u0007j]&$\u0018.\u00197EK2\f\u0017\u0010C\u0004\u0002\n\u001d\u0001\r!a\u0003\t\u000f\u0005eq\u00011\u0001\u0002\u001c\u0005\u00192o\u00195fIVdW-\u0011;GSb,GMU1uKR9Q0a\u000f\u0002>\u0005\u0005\u0003bBA\u0019\u0011\u0001\u0007\u00111\u0002\u0005\b\u0003\u007fA\u0001\u0019AA\u0006\u0003!Ig\u000e^3sm\u0006d\u0007bBA\r\u0011\u0001\u0007\u00111D\u0001\u0015g\u000eDW\rZ;mKB+'/[8eS\u000e\fG\u000e\\=\u0015\u000fu\f9%!\u0013\u0002L!9\u0011\u0011G\u0005A\u0002\u0005-\u0001bBA \u0013\u0001\u0007\u00111\u0002\u0005\b\u00033I\u0001\u0019AA\u000eQ-I\u0011qJA+\u0003/\nY&!\u0018\u0011\u0007\u0005\n\t&C\u0002\u0002T\t\u0012!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#!!\u0017\u0002\u0003w)6/\u001a\u0011tG\",G-\u001e7f/&$\bNR5yK\u0012$U\r\\1zA=\u0014\be]2iK\u0012,H.Z!u\r&DX\r\u001a*bi\u0016\u0004\u0013N\\:uK\u0006$g\u0006\t+iSN\u0004\u0003.Y:!i\",\u0007e]1nK\u0002\u001aX-\\1oi&\u001c7\u000fI1tAM\u001c\u0007.\u001a3vY\u0016\fEOR5yK\u0012\u0014\u0016\r^3-A\t,H\u000fI:dQ\u0016$W\u000f\\3XSRDg)\u001b=fI\u0012+G.Y=!SN\u0004sN\u001a;f]\u0002\u0002(/\u001a4feJ,GML\u0001\u0006g&t7-Z\u0011\u0003\u0003?\nQA\r\u00187]A\n\u0001b\u001d5vi\u0012|wO\u001c\u000b\u0003\u0003K\u00022!IA4\u0013\r\tIG\t\u0002\u0005+:LG/\u0001\u0006jgNCW\u000f\u001e3po:,\"!a\u001c\u0011\u0007\u0005\n\t(C\u0002\u0002t\t\u0012qAQ8pY\u0016\fg.\u0001\u0004tsN$X-\\\u000b\u0003\u0003s\u00022A`A>\u0013\r\tih \u0002\f\u0003\u000e$xN]*zgR,W.\u0001\u0004m_\u001e<WM]\u000b\u0003\u0003\u0007\u0003B!!\"\u0002\f6\u0011\u0011q\u0011\u0006\u0004\u0003\u0013c\u0012!B3wK:$\u0018\u0002BAG\u0003\u000f\u0013a\u0002T8hO&tw-\u00113baR,'\u000fK\u0002\u000e\u0003#\u0003B!a%\u0002\u001a6\u0011\u0011Q\u0013\u0006\u0004\u0003/c\u0012AC1o]>$\u0018\r^5p]&!\u00111TAK\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u0015M,\b/\u001a:wSN|'/\u0006\u0002\u0002\"B\u0019a0a)\n\u0007\u0005\u0015vP\u0001\u0005BGR|'OU3gQ\rq\u0011\u0011S\u0001\bC\u000e$xN](g)\u0019\t\t+!,\u00028\"9\u0011qV\bA\u0002\u0005E\u0016aB2p]R,\u0007\u0010\u001e\t\u0004S\u0005M\u0016bAA[5\t1R*\u0019;fe&\fG.\u001b>bi&|gnQ8oi\u0016DH\u000fC\u0004\u0002:>\u0001\r!a/\u0002\u000bA\u0014x\u000e]:\u0011\u0007y\fi,C\u0002\u0002@~\u0014Q\u0001\u0015:paND3aDAI\u0003!\u0019X\r\u001e;j]\u001e\u001cXCAAd!\rI\u0013\u0011Z\u0005\u0004\u0003\u0017T\"!G!di>\u0014X*\u0019;fe&\fG.\u001b>feN+G\u000f^5oOND3\u0002EA(\u0003+\ny-a\u0017\u0002^\u0005\u0012\u0011\u0011[\u0001.+N,\u0007%\u0019;ue&\u0014W\u000f^3tAQ|\u0007%Y2dKN\u001c\be]3ui&twm\u001d\u0011ge>l\u0007e\u001d;bO\u0016\u001c\bf\u0001\u0001\u0002VB!\u00111SAl\u0013\u0011\tI.!&\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;)\u000f\u0001\ti.a:\u0002jB!\u0011q\\Ar\u001b\t\t\tOC\u0002\u0002\u0018\nJA!!:\u0002b\n\u0001\u0012.\u001c9mS\u000eLGOT8u\r>,h\u000eZ\u0001\u0004[N<\u0017EAAv\u0003M\u000b\u0005%T1uKJL\u0017\r\\5{KJ\u0004\u0013n\u001d\u0011sKF,\u0018N]3e]\u0001\u0002\u0013l\\;![\u0006L\be^1oi\u0002\"x\u000e\t5bm\u0016\u0004C\u000f[3!\u0003\u000e$xN]*zgR,W\u000eI5oA%l\u0007\u000f\\5dSR\u00043oY8qK\u0006aQ*\u0019;fe&\fG.\u001b>feB\u0011\u0011FE\n\u0003%\u0001\"\"!a<\u0002\u001b5\fGO\u0012:p[NK8\u000f^3n)\rA\u0013\u0011 \u0005\b\u0003w$\u00029AA\u007f\u0003!\u0001(o\u001c<jI\u0016\u0014\bc\u0001@\u0002\u0000&\u0019!\u0011A@\u00035\rc\u0017m]:jG\u0006\u001bGo\u001c:TsN$X-\u001c)s_ZLG-\u001a:\u0015\u0007!\u0012)\u0001C\u0004\u0003\bU\u0001\rA!\u0003\u0002\u001f\r|g\u000e^3yiB\u0013xN^5eKJ\u00042A B\u0006\u0013\r\u0011ia \u0002\u001c\u00072\f7o]5d\u0003\u000e$xN]\"p]R,\u0007\u0010\u001e)s_ZLG-\u001a:\u0002%\r\u0014X-\u0019;f\u001b\u0006$XM]5bY&TXM\u001d\u000b\u0004Q\tM\u0001b\u0002B\u0004-\u0001\u0007!\u0011\u0002\u000b\u0004Q\t]\u0001b\u0002B\r/\u0001\u0007\u0011Q`\u0001\u000fgf\u001cH/Z7Qe>4\u0018\u000eZ3s)\rA#Q\u0004\u0005\b\u00053A\u0002\u0019AA\u007f\u0001")
@DoNotInherit
public abstract class Materializer {
    public static Materializer createMaterializer(ClassicActorSystemProvider systemProvider) {
        return Materializer$.MODULE$.createMaterializer(systemProvider);
    }

    public static Materializer apply(ClassicActorSystemProvider systemProvider) {
        return Materializer$.MODULE$.apply(systemProvider);
    }

    public static Materializer createMaterializer(ClassicActorContextProvider contextProvider) {
        return Materializer$.MODULE$.createMaterializer(contextProvider);
    }

    public static Materializer apply(ClassicActorContextProvider contextProvider) {
        return Materializer$.MODULE$.apply(contextProvider);
    }

    public static Materializer matFromSystem(ClassicActorSystemProvider provider) {
        return Materializer$.MODULE$.matFromSystem(provider);
    }

    public abstract Materializer withNamePrefix(String var1);

    public abstract <Mat> Mat materialize(Graph<ClosedShape, Mat> var1);

    public abstract <Mat> Mat materialize(Graph<ClosedShape, Mat> var1, Attributes var2);

    public abstract ExecutionContextExecutor executionContext();

    public abstract Cancellable scheduleOnce(FiniteDuration var1, Runnable var2);

    public abstract Cancellable scheduleWithFixedDelay(FiniteDuration var1, FiniteDuration var2, Runnable var3);

    public abstract Cancellable scheduleAtFixedRate(FiniteDuration var1, FiniteDuration var2, Runnable var3);

    public abstract Cancellable schedulePeriodically(FiniteDuration var1, FiniteDuration var2, Runnable var3);

    public abstract void shutdown();

    public abstract boolean isShutdown();

    public abstract ActorSystem system();

    @InternalApi
    public abstract LoggingAdapter logger();

    @InternalApi
    public abstract ActorRef supervisor();

    @InternalApi
    public abstract ActorRef actorOf(MaterializationContext var1, Props var2);

    public abstract ActorMaterializerSettings settings();
}

