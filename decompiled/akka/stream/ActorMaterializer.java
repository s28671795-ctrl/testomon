/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.LoggingAdapter;
import akka.stream.ActorMaterializer$;
import akka.stream.ActorMaterializerSettings;
import akka.stream.MaterializationContext;
import akka.stream.Materializer;
import akka.stream.MaterializerLoggingProvider;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mt!\u0002\f\u0018\u0011\u0003ab!\u0002\u0010\u0018\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"CA\f\u0003E\u0005I\u0011AA\r\u0011%\ty#AI\u0001\n\u0003\t\t\u0004\u0003\u0004)\u0003\u0011\u0005\u0011Q\u0007\u0005\u0007Q\u0005!\t!!\u0011\t\u000f\u0005=\u0013\u0001\"\u0001\u0002R!9\u0011qJ\u0001\u0005\u0002\u0005m\u0003bBA(\u0003\u0011\u0005\u00111\r\u0005\b\u0003\u001f\nA\u0011AA6\u0011\u001d\t)(\u0001C\u0005\u0003o2QAH\f\u0002\u00021BQAJ\u0007\u0005\u0002MBQ\u0001N\u0007\u0007\u0002UBQaQ\u0007\u0007\u0002\u0011CQ\u0001S\u0007\u0007\u0002%Ca!T\u0007\u0007\u0002eq\u0005\"B0\u000e\r\u0003\u0001\u0007B\u00023\u000e\r\u0003IR\r\u0003\u0004m\u001b\u0019\u0005\u0011$\\\u0001\u0012\u0003\u000e$xN]'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014(B\u0001\r\u001a\u0003\u0019\u0019HO]3b[*\t!$\u0001\u0003bW.\f7\u0001\u0001\t\u0003;\u0005i\u0011a\u0006\u0002\u0012\u0003\u000e$xN]'bi\u0016\u0014\u0018.\u00197ju\u0016\u00148CA\u0001!!\t\tC%D\u0001#\u0015\u0005\u0019\u0013!B:dC2\f\u0017BA\u0013#\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001H\u0001\u0006CB\u0004H.\u001f\u000b\u0004UUTHCA\u0016r!\tiRbE\u0002\u000e[A\u0002\"!\b\u0018\n\u0005=:\"\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\bCA\u000f2\u0013\t\u0011tCA\u000eNCR,'/[1mSj,'\u000fT8hO&tw\r\u0015:pm&$WM\u001d\u000b\u0002W\u0005A1/\u001a;uS:<7/F\u00017!\tir'\u0003\u00029/\tI\u0012i\u0019;pe6\u000bG/\u001a:jC2L'0\u001a:TKR$\u0018N\\4tQ\u0019y!(\u0010 A\u0003B\u0011\u0011eO\u0005\u0003y\t\u0012!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013aP\u0001~+N,\u0007%\u0019;ue&\u0014W\u000f^3tAQ|\u0007%Y2dKN\u001c\be]3ui&twm\u001d\u0011ge>l\u0007e\u001d;bO\u0016\u001cH\u0006I:fK\u0002BG\u000f\u001e9tu=zCm\\2/C.\\\u0017ML5p_\u0011|7m]\u0018bW.\fwF\r\u00187_A\u0014xN[3di>j\u0017n\u001a:bi&|g.L4vS\u0012,WF\r\u00186]al#G\f\u001c/q:BG/\u001c7\u0002\u000bMLgnY3\"\u0003\t\u000bQA\r\u00187]A\n\u0001b\u001d5vi\u0012|wO\u001c\u000b\u0002\u000bB\u0011\u0011ER\u0005\u0003\u000f\n\u0012A!\u00168ji\u0006Q\u0011n]*ikR$wn\u001e8\u0016\u0003)\u0003\"!I&\n\u00051\u0013#a\u0002\"p_2,\u0017M\\\u0001\bC\u000e$xN](g)\ryUK\u0017\t\u0003!Nk\u0011!\u0015\u0006\u0003%f\tQ!Y2u_JL!\u0001V)\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQA\u0016\nA\u0002]\u000bqaY8oi\u0016DH\u000f\u0005\u0002\u001e1&\u0011\u0011l\u0006\u0002\u0017\u001b\u0006$XM]5bY&T\u0018\r^5p]\u000e{g\u000e^3yi\")1L\u0005a\u00019\u0006)\u0001O]8qgB\u0011\u0001+X\u0005\u0003=F\u0013Q\u0001\u0015:paN\faa]=ti\u0016lW#A1\u0011\u0005A\u0013\u0017BA2R\u0005-\t5\r^8s'f\u001cH/Z7\u0002\r1|wmZ3s+\u00051\u0007CA4k\u001b\u0005A'BA5\u001a\u0003\u0015)g/\u001a8u\u0013\tY\u0007N\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u0002\u0015M,\b/\u001a:wSN|'/F\u0001PQ\u0019i!(P8A\u0003\u0006\n\u0001/A$UQ\u0016\u0004S*\u0019;fe&\fG.\u001b>fe\u0002rwn\u001e\u0011iCN\u0004\u0013\r\u001c7![\u0016$\bn\u001c3tAQDW\rI!di>\u0014X*\u0019;fe&\fG.\u001b>fe\u0002*8/\u001a3!i>\u0004\u0003.\u0019<f\u0011\u001516\u0001q\u0001s!\t\u00016/\u0003\u0002u#\ny\u0011i\u0019;peJ+gMR1di>\u0014\u0018\u0010C\u0004w\u0007A\u0005\t\u0019A<\u0002)5\fG/\u001a:jC2L'0\u001a:TKR$\u0018N\\4t!\r\t\u0003PN\u0005\u0003s\n\u0012aa\u00149uS>t\u0007bB>\u0004!\u0003\u0005\r\u0001`\u0001\u000b]\u0006lW\r\u0015:fM&D\bcA\u0011y{B\u0019a0a\u0003\u000f\u0007}\f9\u0001E\u0002\u0002\u0002\tj!!a\u0001\u000b\u0007\u0005\u00151$\u0001\u0004=e>|GOP\u0005\u0004\u0003\u0013\u0011\u0013A\u0002)sK\u0012,g-\u0003\u0003\u0002\u000e\u0005=!AB*ue&twMC\u0002\u0002\n\tBsa\u0001\u001e>\u0003'\u0001\u0015)\t\u0002\u0002\u0016\u0005!Wk]3!i\",\u0007e]=ti\u0016l\u0007e^5eK\u0002j\u0017\r^3sS\u0006d\u0017N_3sA]LG\u000f\u001b\u0011tiJ,\u0017-\u001c\u0011biR\u0014\u0018NY;uKN\u0004sN\u001d\u0011d_:4\u0017nZ;sCRLwN\u001c\u0011tKR$\u0018N\\4tAQ|\u0007e\u00195b]\u001e,\u0007\u0005Z3gCVdGo]\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\u0004\u0016\u0004o\u0006u1FAA\u0010!\u0011\t\t#a\u000b\u000e\u0005\u0005\r\"\u0002BA\u0013\u0003O\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005%\"%\u0001\u0006b]:|G/\u0019;j_:LA!!\f\u0002$\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII*\"!a\r+\u0007q\fi\u0002\u0006\u0004\u00028\u0005m\u0012Q\b\u000b\u0004W\u0005e\u0002\"\u0002,\u0007\u0001\b\u0011\b\"\u0002<\u0007\u0001\u00041\u0004\"B>\u0007\u0001\u0004i\bf\u0002\u0004;{\u0005M\u0001)\u0011\u000b\u0005\u0003\u0007\n9\u0005F\u0002,\u0003\u000bBQAV\u0004A\u0004IDQA^\u0004A\u0002YBsa\u0002\u001e>\u0003\u0017\u0002\u0015)\t\u0002\u0002N\u0005\t\t\"V:fAQDW\rI:zgR,W\u000eI<jI\u0016\u0004S.\u0019;fe&\fG.\u001b>fe\u0002z'\u000fI'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014h&\u00199qYfD\u0013m\u0019;pe\u000e{g\u000e^3yi&\u0002s/\u001b;iAM$(/Z1nA\u0005$HO]5ckR,7\u000fI8sA\r|gNZ5hkJ\fG/[8oAM,G\u000f^5oON\u0004Co\u001c\u0011dQ\u0006tw-\u001a\u0011eK\u001a\fW\u000f\u001c;t\u0003\u0019\u0019'/Z1uKR\u00191&a\u0015\t\u000bYC\u0001\u0019\u0001:)\u000f!QT(a\u0016A\u0003\u0006\u0012\u0011\u0011L\u0001\u0002\u0014U\u001bX\r\t;iK\u0002\u001a\u0018p\u001d;f[\u0002:\u0018\u000eZ3![\u0006$XM]5bY&TXM\u001d\u0011pe\u0002j\u0015\r^3sS\u0006d\u0017N_3s]\r\u0014X-\u0019;fQ\u0005\u001cGo\u001c:D_:$X\r\u001f;*A]LG\u000f\u001b\u0011tiJ,\u0017-\u001c\u0011biR\u0014\u0018NY;uKN\u0004sN\u001d\u0011d_:4\u0017nZ;sCRLwN\u001c\u0011tKR$\u0018N\\4tAQ|\u0007e\u00195b]\u001e,\u0007\u0005Z3gCVdGo\u001d\u000b\u0006W\u0005u\u0013q\f\u0005\u0006-&\u0001\rA\u001d\u0005\u0006w&\u0001\r! \u0015\b\u0013ij\u0014q\u000b!B)\u0015Y\u0013QMA4\u0011\u0015!$\u00021\u00017\u0011\u00151&\u00021\u0001sQ\u001dQ!(PA,\u0001\u0006#raKA7\u0003_\n\t\bC\u00035\u0017\u0001\u0007a\u0007C\u0003W\u0017\u0001\u0007!\u000fC\u0003|\u0017\u0001\u0007Q\u0010K\u0004\fuu\n9\u0006Q!\u0002\u001b\u0005\u001cGo\u001c:TsN$X-\\(g)\r\t\u0017\u0011\u0010\u0005\u0006-2\u0001\rA\u001d")
public abstract class ActorMaterializer
extends Materializer
implements MaterializerLoggingProvider {
    public static ActorMaterializer create(ActorMaterializerSettings settings, ActorRefFactory context, String namePrefix) {
        return ActorMaterializer$.MODULE$.create(settings, context, namePrefix);
    }

    public static ActorMaterializer create(ActorMaterializerSettings settings, ActorRefFactory context) {
        return ActorMaterializer$.MODULE$.create(settings, context);
    }

    public static ActorMaterializer create(ActorRefFactory context, String namePrefix) {
        return ActorMaterializer$.MODULE$.create(context, namePrefix);
    }

    public static ActorMaterializer create(ActorRefFactory context) {
        return ActorMaterializer$.MODULE$.create(context);
    }

    public static ActorMaterializer apply(ActorMaterializerSettings materializerSettings, ActorRefFactory context) {
        return ActorMaterializer$.MODULE$.apply(materializerSettings, context);
    }

    public static ActorMaterializer apply(ActorMaterializerSettings materializerSettings, String namePrefix, ActorRefFactory context) {
        return ActorMaterializer$.MODULE$.apply(materializerSettings, namePrefix, context);
    }

    public static Option<String> apply$default$2() {
        return ActorMaterializer$.MODULE$.apply$default$2();
    }

    public static Option<ActorMaterializerSettings> apply$default$1() {
        return ActorMaterializer$.MODULE$.apply$default$1();
    }

    public static ActorMaterializer apply(Option<ActorMaterializerSettings> materializerSettings, Option<String> namePrefix, ActorRefFactory context) {
        return ActorMaterializer$.MODULE$.apply(materializerSettings, namePrefix, context);
    }

    @Override
    public abstract ActorMaterializerSettings settings();

    @Override
    public abstract void shutdown();

    @Override
    public abstract boolean isShutdown();

    @Override
    public abstract ActorRef actorOf(MaterializationContext var1, Props var2);

    @Override
    public abstract ActorSystem system();

    @Override
    public abstract LoggingAdapter logger();

    @Override
    public abstract ActorRef supervisor();
}

