/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorContext;
import akka.actor.ActorInterruptedException;
import akka.actor.ActorRef;
import akka.actor.ActorRefScope;
import akka.actor.ChildRestartStats;
import akka.actor.InternalActorRef;
import akka.actor.PostRestartException$;
import akka.actor.PreRestartException;
import akka.actor.PreRestartException$;
import akka.actor.dungeon.Children;
import akka.actor.dungeon.ChildrenContainer;
import akka.actor.dungeon.ChildrenContainer$Termination$;
import akka.actor.dungeon.DeathWatch;
import akka.actor.dungeon.Dispatch;
import akka.actor.dungeon.FaultHandling$FailedFatally$;
import akka.actor.dungeon.FaultHandling$NoFailedInfo$;
import akka.actor.dungeon.ReceiveTimeout;
import akka.annotation.InternalStableApi;
import akka.dispatch.Envelope$;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.Failed;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$Error$;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t%vA\u0002%J\u0011\u0003iuJ\u0002\u0004R\u0013\"\u0005QJ\u0015\u0005\u00063\u0006!\ta\u0017\u0004\b9\u0006\u0001\n1%\t^\u000f\u001d\ti*\u0001EE\u0003'3q!!$\u0002\u0011\u0013\u000by\t\u0003\u0004Z\u000b\u0011\u0005\u0011\u0011\u0013\u0005\bi\u0016\t\t\u0011\"\u0011v\u0011\u001dqX!!A\u0005\u0002}D\u0011\"a\u0002\u0006\u0003\u0003%\t!!&\t\u0013\u0005UQ!!A\u0005B\u0005]\u0001\"CA\u0013\u000b\u0005\u0005I\u0011AAM\u0011%\t\t$BA\u0001\n\u0003\n\u0019\u0004C\u0005\u00026\u0015\t\t\u0011\"\u0011\u00028!I\u0011\u0011H\u0003\u0002\u0002\u0013%\u00111\b\u0004\u0007\u0003\u0007\na)!\u0012\t\u0015\u0005\u001dsB!f\u0001\n\u0003\tI\u0005\u0003\u0006\u0002T=\u0011\t\u0012)A\u0005\u0003\u0017Ba!W\b\u0005\u0002\u0005U\u0003\"CA.\u001f\u0005\u0005I\u0011AA/\u0011%\t\tgDI\u0001\n\u0003\t\u0019\u0007C\u0004u\u001f\u0005\u0005I\u0011I;\t\u000fy|\u0011\u0011!C\u0001\u007f\"I\u0011qA\b\u0002\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003+y\u0011\u0011!C!\u0003/A\u0011\"!\n\u0010\u0003\u0003%\t!! \t\u0013\u0005\u0005u\"!A\u0005B\u0005\r\u0005\"CA\u0019\u001f\u0005\u0005I\u0011IA\u001a\u0011%\t)dDA\u0001\n\u0003\n9\u0004C\u0005\u0002\b>\t\t\u0011\"\u0011\u0002\n\u001eI\u0011qT\u0001\u0002\u0002#%\u0011\u0011\u0015\u0004\n\u0003\u0007\n\u0011\u0011!E\u0005\u0003GCa!W\u0010\u0005\u0002\u0005m\u0006\"CA\u001b?\u0005\u0005IQIA\u001c\u0011%\tilHA\u0001\n\u0003\u000by\fC\u0005\u0002D~\t\t\u0011\"!\u0002F\"I\u0011\u0011H\u0010\u0002\u0002\u0013%\u00111H\u0004\u0007\u0003#\f\u0001\u0012R:\u0007\u000b}\u000b\u0001\u0012\u00121\t\u000be3C\u0011\u0001:\t\u000fQ4\u0013\u0011!C!k\"9aPJA\u0001\n\u0003y\b\"CA\u0004M\u0005\u0005I\u0011AA\u0005\u0011%\t)BJA\u0001\n\u0003\n9\u0002C\u0005\u0002&\u0019\n\t\u0011\"\u0001\u0002(!I\u0011\u0011\u0007\u0014\u0002\u0002\u0013\u0005\u00131\u0007\u0005\n\u0003k1\u0013\u0011!C!\u0003oA\u0011\"!\u000f'\u0003\u0003%I!a\u000f\u0007\u0017EK\u0005\u0013aA\u0001\u001b\u0006\u0005(1\u0015\u0005\b\u0003G\u0004D\u0011AAs\u0011\u001d\ti\u000f\rC\u0005\u0003KDq!a<1\t\u0013\t)\u000fC\u0005\u0002rB\u0002\r\u0011\"\u0003\u0002t\"I\u0011\u0011 \u0019A\u0002\u0013%\u00111 \u0005\b\u0003\u007f\u0004D\u0011\u0002B\u0001\u0011\u001d\u0011\u0019\u0001\rC\u0005\u0005\u0003AqA!\u00021\t\u0013\tI\u0005C\u0004\u0003\bA\"IA!\u0003\t\u000f\t5\u0001\u0007\"\u0003\u0002f\"9!q\u0002\u0019\u0005\u0012\u0005\u0015\bb\u0002B\ta\u0011E!1\u0003\u0005\b\u0005?\u0001D\u0011CAs\u0011\u001d\u0011\t\u0003\rC\t\u0005GAqA!\u000b1\t#\t)\u000fC\u0004\u0003,A\"I!!:\t\u000f\t5\u0002\u0007\"\u0005\u0002f\"9!q\u0006\u0019\u0005\u0006\tE\u0002b\u0002B(a\u0011%\u0011Q\u001d\u0005\b\u0005#\u0002D\u0011\u0002B*\u0011\u001d\u00119\u0006\rC\u000b\u00053BqAa\u001c1\t+\u0011\t\bC\u0004\u0003xA\")B!\u001f\u0002\u001b\u0019\u000bW\u000f\u001c;IC:$G.\u001b8h\u0015\tQ5*A\u0004ek:<Wm\u001c8\u000b\u00051k\u0015!B1di>\u0014(\"\u0001(\u0002\t\u0005\\7.\u0019\t\u0003!\u0006i\u0011!\u0013\u0002\u000e\r\u0006,H\u000e\u001e%b]\u0012d\u0017N\\4\u0014\u0005\u0005\u0019\u0006C\u0001+X\u001b\u0005)&\"\u0001,\u0002\u000bM\u001c\u0017\r\\1\n\u0005a+&AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005y%A\u0003$bS2,G-\u00138g_N\u00111aU\u0015\u0005\u0007\u0019zQAA\u0007GC&dW\r\u001a$bi\u0006dG._\n\u0006MM\u000b7M\u001a\t\u0003E\u000ei\u0011!\u0001\t\u0003)\u0012L!!Z+\u0003\u000fA\u0013x\u000eZ;diB\u0011qm\u001c\b\u0003Q6t!!\u001b7\u000e\u0003)T!a\u001b.\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0016B\u00018V\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001]9\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00059,F#A:\u0011\u0005\t4\u0013!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001w!\t9H0D\u0001y\u0015\tI(0\u0001\u0003mC:<'\"A>\u0002\t)\fg/Y\u0005\u0003{b\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA\u0001!\r!\u00161A\u0005\u0004\u0003\u000b)&aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\u0006\u0003#\u00012\u0001VA\u0007\u0013\r\ty!\u0016\u0002\u0004\u0003:L\b\"CA\nU\u0005\u0005\t\u0019AA\u0001\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u0004\t\u0007\u00037\t\t#a\u0003\u000e\u0005\u0005u!bAA\u0010+\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\r\u0012Q\u0004\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002*\u0005=\u0002c\u0001+\u0002,%\u0019\u0011QF+\u0003\u000f\t{w\u000e\\3b]\"I\u00111\u0003\u0017\u0002\u0002\u0003\u0007\u00111B\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011A\u0001\ti>\u001cFO]5oOR\ta/\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002>A\u0019q/a\u0010\n\u0007\u0005\u0005\u0003P\u0001\u0004PE*,7\r\u001e\u0002\n\r\u0006LG.\u001a3SK\u001a\u001cRaD*bG\u001a\f1A]3g+\t\tY\u0005\u0005\u0003\u0002N\u0005=S\"A&\n\u0007\u0005E3J\u0001\u0005BGR|'OU3g\u0003\u0011\u0011XM\u001a\u0011\u0015\t\u0005]\u0013\u0011\f\t\u0003E>Aq!a\u0012\u0013\u0001\u0004\tY%\u0001\u0003d_BLH\u0003BA,\u0003?B\u0011\"a\u0012\u0014!\u0003\u0005\r!a\u0013\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\r\u0016\u0005\u0003\u0017\n9g\u000b\u0002\u0002jA!\u00111NA;\u001b\t\tiG\u0003\u0003\u0002p\u0005E\u0014!C;oG\",7m[3e\u0015\r\t\u0019(V\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA<\u0003[\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f)\u0011\tY!a\u001f\t\u0013\u0005Mq#!AA\u0002\u0005\u0005A\u0003BA\u0015\u0003\u007fB\u0011\"a\u0005\u001a\u0003\u0003\u0005\r!a\u0003\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004m\u0006\u0015\u0005\"CA\n5\u0005\u0005\t\u0019AA\u0001\u0003\u0019)\u0017/^1mgR!\u0011\u0011FAF\u0011%\t\u0019\"HA\u0001\u0002\u0004\tYA\u0001\u0007O_\u001a\u000b\u0017\u000e\\3e\u0013:4wnE\u0003\u0006'\u0006\u001cg\r\u0006\u0002\u0002\u0014B\u0011!-\u0002\u000b\u0005\u0003\u0017\t9\nC\u0005\u0002\u0014%\t\t\u00111\u0001\u0002\u0002Q!\u0011\u0011FAN\u0011%\t\u0019bCA\u0001\u0002\u0004\tY!\u0001\u0007O_\u001a\u000b\u0017\u000e\\3e\u0013:4w.A\u0005GC&dW\r\u001a*fMB\u0011!mH\n\u0006?\u0005\u0015\u0016\u0011\u0017\t\t\u0003O\u000bi+a\u0013\u0002X5\u0011\u0011\u0011\u0016\u0006\u0004\u0003W+\u0016a\u0002:v]RLW.Z\u0005\u0005\u0003_\u000bIKA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a-\u0002:6\u0011\u0011Q\u0017\u0006\u0004\u0003oS\u0018AA5p\u0013\r\u0001\u0018Q\u0017\u000b\u0003\u0003C\u000bQ!\u00199qYf$B!a\u0016\u0002B\"9\u0011q\t\u0012A\u0002\u0005-\u0013aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u000f\fi\rE\u0003U\u0003\u0013\fY%C\u0002\u0002LV\u0013aa\u00149uS>t\u0007\"CAhG\u0005\u0005\t\u0019AA,\u0003\rAH\u0005M\u0001\u000e\r\u0006LG.\u001a3GCR\fG\u000e\\=)\u0007\u0005\t)\u000e\u0005\u0003\u0002X\u0006mWBAAm\u0015\r\t\u0019(T\u0005\u0005\u0003;\fINA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0002VN\u0011\u0001gU\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0005\u0005\u001d\bc\u0001+\u0002j&\u0019\u00111^+\u0003\tUs\u0017\u000e^\u0001\u0014gV\u001c\b/\u001a8e\u001d>t'+Z2veNLg/Z\u0001\u0013e\u0016\u001cX/\\3O_:\u0014VmY;sg&4X-A\u0004`M\u0006LG.\u001a3\u0016\u0005\u0005U\bcAA|\u00079\u0011\u0001\u000bA\u0001\f?\u001a\f\u0017\u000e\\3e?\u0012*\u0017\u000f\u0006\u0003\u0002h\u0006u\b\"CA\nk\u0005\u0005\t\u0019AA{\u0003!I7OR1jY\u0016$WCAA\u0015\u0003=I7OR1jY\u0016$g)\u0019;bY2L\u0018a\u00039feB,GO]1u_J\f\u0011b]3u\r\u0006LG.\u001a3\u0015\t\u0005\u001d(1\u0002\u0005\b\u0005\u000bI\u0004\u0019AA&\u0003-\u0019G.Z1s\r\u0006LG.\u001a3\u0002!M,GOR1jY\u0016$g)\u0019;bY2L\u0018!\u00044bk2$(+Z2sK\u0006$X\r\u0006\u0003\u0002h\nU\u0001b\u0002B\fy\u0001\u0007!\u0011D\u0001\u0006G\u0006,8/\u001a\t\u0004O\nm\u0011b\u0001B\u000fc\nIA\u000b\u001b:po\u0006\u0014G.Z\u0001\rM\u0006,H\u000e^*vgB,g\u000eZ\u0001\fM\u0006,H\u000e\u001e*fgVlW\r\u0006\u0003\u0002h\n\u0015\u0002b\u0002B\u0014}\u0001\u0007!\u0011D\u0001\u0010G\u0006,8/\u001a3Cs\u001a\u000b\u0017\u000e\\;sK\u0006Ya-Y;mi\u000e\u0013X-\u0019;f\u000311\u0017N\\5tQ\u000e\u0013X-\u0019;f\u0003%!XM]7j]\u0006$X-A\niC:$G.Z%om>\\WMR1jYV\u0014X\r\u0006\u0004\u0002h\nM\"1\t\u0005\b\u0005k\u0011\u0005\u0019\u0001B\u001c\u0003Q\u0019\u0007.\u001b7ee\u0016tgj\u001c;U_N+8\u000f]3oIB1!\u0011\bB \u0003\u0017j!Aa\u000f\u000b\t\tu\u0012QD\u0001\nS6lW\u000f^1cY\u0016LAA!\u0011\u0003<\tA\u0011\n^3sC\ndW\rC\u0004\u0003F\t\u0003\rA!\u0007\u0002\u0003QD3A\u0011B%!\u0011\t9Na\u0013\n\t\t5\u0013\u0011\u001c\u0002\u0012\u0013:$XM\u001d8bYN#\u0018M\u00197f\u0003BL\u0017a\u00044j]&\u001c\b\u000eV3s[&t\u0017\r^3\u0002\u001d\u0019Lg.[:i%\u0016\u001c'/Z1uKR!\u0011q\u001dB+\u0011\u001d\u00119\u0002\u0012a\u0001\u00053\tQ\u0002[1oI2,g)Y5mkJ,G\u0003BAt\u00057BqA!\u0018F\u0001\u0004\u0011y&A\u0001g!\u0011\u0011\tGa\u001b\u000e\u0005\t\r$\u0002\u0002B3\u0005O\naa]=t[N<'b\u0001B5\u001b\u0006AA-[:qCR\u001c\u0007.\u0003\u0003\u0003n\t\r$A\u0002$bS2,G-A\u000biC:$G.Z\"iS2$G+\u001a:nS:\fG/\u001a3\u0015\t\u0005\u001d(1\u000f\u0005\b\u0005k2\u0005\u0019AA&\u0003\u0015\u0019\u0007.\u001b7e\u0003\u0011B\u0017M\u001c3mK:{gNR1uC2|%/\u00138uKJ\u0014X\u000f\u001d;fI\u0016C8-\u001a9uS>tG\u0003\u0002B>\u00053\u0003bA! \u0003\u0014\u0006\u001dh\u0002\u0002B@\u0005\u001bsAA!!\u0003\b:\u0019\u0001Na!\n\u0007\t\u0015U+\u0001\u0003vi&d\u0017\u0002\u0002BE\u0005\u0017\u000bqaY8oiJ|GNC\u0002\u0003\u0006VKAAa$\u0003\u0012\u0006IQ\t_2faRLwN\u001c\u0006\u0005\u0005\u0013\u0013Y)\u0003\u0003\u0003\u0016\n]%aB\"bi\u000eDWM\u001d\u0006\u0005\u0005\u001f\u0013\t\nC\u0004\u0003\u001c\u001e\u0003\rA!(\u0002\u000bQDWO\\6\u0011\u000fQ\u0013yJ!\u0007\u0002h&\u0019!\u0011U+\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003BA'\u0005KK1Aa*L\u0005%\t5\r^8s\u0007\u0016dG\u000e")
public interface FaultHandling {
    private void suspendNonRecursive() {
        ((ActorCell)this).dispatcher().suspend((ActorCell)this);
    }

    private void resumeNonRecursive() {
        ((ActorCell)this).dispatcher().resume((ActorCell)this);
    }

    public FailedInfo akka$actor$dungeon$FaultHandling$$_failed();

    public void akka$actor$dungeon$FaultHandling$$_failed_$eq(FailedInfo var1);

    private boolean isFailed() {
        return this.akka$actor$dungeon$FaultHandling$$_failed() instanceof FailedRef;
    }

    private boolean isFailedFatally() {
        return this.akka$actor$dungeon$FaultHandling$$_failed() == FaultHandling$FailedFatally$.MODULE$;
    }

    private ActorRef perpetrator() {
        FailedInfo failedInfo = this.akka$actor$dungeon$FaultHandling$$_failed();
        if (failedInfo instanceof FailedRef) {
            FailedRef failedRef = (FailedRef)failedInfo;
            ActorRef ref = failedRef.ref();
            return ref;
        }
        return null;
    }

    private void setFailed(ActorRef perpetrator) {
        FailedInfo failedInfo = this.akka$actor$dungeon$FaultHandling$$_failed();
        this.akka$actor$dungeon$FaultHandling$$_failed_$eq((FailedInfo)((Object)(FaultHandling$FailedFatally$.MODULE$.equals(failedInfo) ? FaultHandling$FailedFatally$.MODULE$ : new FailedRef(perpetrator))));
    }

    private void clearFailed() {
        FailedInfo failedInfo = this.akka$actor$dungeon$FaultHandling$$_failed();
        this.akka$actor$dungeon$FaultHandling$$_failed_$eq(failedInfo instanceof FailedRef ? FaultHandling$NoFailedInfo$.MODULE$ : failedInfo);
    }

    public static /* synthetic */ void setFailedFatally$(FaultHandling $this) {
        $this.setFailedFatally();
    }

    default public void setFailedFatally() {
        this.akka$actor$dungeon$FaultHandling$$_failed_$eq(FaultHandling$FailedFatally$.MODULE$);
    }

    public static /* synthetic */ void faultRecreate$(FaultHandling $this, Throwable cause) {
        $this.faultRecreate(cause);
    }

    default public void faultRecreate(Throwable cause) {
        if (((ActorCell)this).actor() == null) {
            ((ActorCell)this).system().eventStream().publish(Logging$Error$.MODULE$.apply(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(36).append("changing Recreate into Create after ").append(cause).toString()));
            this.faultCreate();
            return;
        }
        if (((Children)((Object)this)).isNormal()) {
            BoxedUnit boxedUnit;
            Actor failedActor = ((ActorCell)this).actor();
            if (((ActorCell)this).system().settings().DebugLifecycle()) {
                ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(failedActor), "restarting"));
            }
            if (failedActor != null) {
                Option optionalMessage = ((ActorCell)this).currentMessage() != null ? new Some<Object>(((ActorCell)this).currentMessage().message()) : None$.MODULE$;
                try {
                    try {
                        if (!this.isFailedFatally()) {
                            failedActor.aroundPreRestart(cause, optionalMessage);
                        }
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    catch (Throwable x$1) {
                        PartialFunction<Throwable, BoxedUnit> catchExpr$1 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                            FaultHandling.$anonfun$faultRecreate$1(this, cause, optionalMessage, failedActor, e);
                            return BoxedUnit.UNIT;
                        });
                        if (catchExpr$1.isDefinedAt(x$1)) {
                            boxedUnit = catchExpr$1.apply(x$1);
                        }
                        throw x$1;
                    }
                }
                finally {
                    ((ActorCell)this).clearActorFields(failedActor, true);
                }
            } else {
                boxedUnit = BoxedUnit.UNIT;
            }
            Predef$.MODULE$.assert(((Dispatch)((Object)this)).mailbox().isSuspended(), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(49).append("mailbox must be suspended during restart, status=").append(((Dispatch)((Object)this)).mailbox().currentStatus()).toString());
            if (!((Children)((Object)this)).setChildrenTerminationReason(new ChildrenContainer.Recreation(cause))) {
                this.finishRecreate(cause);
                return;
            }
            return;
        }
        this.faultResume(null);
    }

    public static /* synthetic */ void faultSuspend$(FaultHandling $this) {
        $this.faultSuspend();
    }

    default public void faultSuspend() {
        this.suspendNonRecursive();
        ((Children)((Object)this)).suspendChildren(((Children)((Object)this)).suspendChildren$default$1());
    }

    public static /* synthetic */ void faultResume$(FaultHandling $this, Throwable causedByFailure) {
        $this.faultResume(causedByFailure);
    }

    default public void faultResume(Throwable causedByFailure) {
        if (((ActorCell)this).actor() == null) {
            ((ActorCell)this).system().eventStream().publish(Logging$Error$.MODULE$.apply(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(34).append("changing Resume into Create after ").append(causedByFailure).toString()));
            this.faultCreate();
            return;
        }
        if (this.isFailedFatally() && causedByFailure != null) {
            ((ActorCell)this).system().eventStream().publish(Logging$Error$.MODULE$.apply(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(35).append("changing Resume into Restart after ").append(causedByFailure).toString()));
            this.faultRecreate(causedByFailure);
            return;
        }
        ActorRef perp = this.perpetrator();
        try {
            this.resumeNonRecursive();
        }
        finally {
            if (causedByFailure != null) {
                this.clearFailed();
            }
        }
        ((Children)((Object)this)).resumeChildren(causedByFailure, perp);
    }

    public static /* synthetic */ void faultCreate$(FaultHandling $this) {
        $this.faultCreate();
    }

    default public void faultCreate() {
        Predef$.MODULE$.assert(((Dispatch)((Object)this)).mailbox().isSuspended(), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(57).append("mailbox must be suspended during failed creation, status=").append(((Dispatch)((Object)this)).mailbox().currentStatus()).toString());
        ActorRef actorRef2 = this.perpetrator();
        InternalActorRef internalActorRef = ((ActorCell)this).self();
        Predef$.MODULE$.assert(!(actorRef2 != null ? !((Object)actorRef2).equals(internalActorRef) : internalActorRef != null));
        ((AbstractActor.ActorContext)((Object)this)).cancelReceiveTimeout();
        ((ReceiveTimeout)((Object)this)).cancelReceiveTimeoutTask();
        ((Children)((Object)this)).children().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
            FaultHandling.$anonfun$faultCreate$2(this, actor);
            return BoxedUnit.UNIT;
        });
        if (!((Children)((Object)this)).setChildrenTerminationReason(new ChildrenContainer.Creation())) {
            this.finishCreate();
            return;
        }
    }

    private void finishCreate() {
        try {
            this.resumeNonRecursive();
        }
        finally {
            this.clearFailed();
        }
        try {
            ((ActorCell)this).create(None$.MODULE$);
        }
        catch (Throwable x$2) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$2 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                this.handleInvokeFailure(Nil$.MODULE$, e);
                return BoxedUnit.UNIT;
            });
            if (catchExpr$2.isDefinedAt(x$2)) {
                catchExpr$2.apply(x$2);
            }
            throw x$2;
        }
    }

    public static /* synthetic */ void terminate$(FaultHandling $this) {
        $this.terminate();
    }

    default public void terminate() {
        ((AbstractActor.ActorContext)((Object)this)).cancelReceiveTimeout();
        ((ReceiveTimeout)((Object)this)).cancelReceiveTimeoutTask();
        ((DeathWatch)((Object)this)).unwatchWatchedActors(((ActorCell)this).actor());
        ((Children)((Object)this)).children().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
            FaultHandling.$anonfun$terminate$1(this, actor);
            return BoxedUnit.UNIT;
        });
        if (((ActorCell)this).systemImpl().aborting()) {
            ((Children)((Object)this)).children().foreach((Function1<ActorRef, Object> & Serializable)x0$1 -> {
                FaultHandling.$anonfun$terminate$2(this, x0$1);
                return BoxedUnit.UNIT;
            });
        }
        boolean wasTerminating = ((Children)((Object)this)).isTerminating();
        if (((Children)((Object)this)).setChildrenTerminationReason(ChildrenContainer$Termination$.MODULE$)) {
            if (!wasTerminating) {
                this.suspendNonRecursive();
                this.setFailed(((ActorCell)this).self());
                if (((ActorCell)this).system().settings().DebugLifecycle()) {
                    ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), "stopping"));
                    return;
                }
                return;
            }
            return;
        }
        ((Children)((Object)this)).setTerminated();
        this.finishTerminate();
    }

    public static /* synthetic */ void handleInvokeFailure$(FaultHandling $this, Iterable childrenNotToSuspend, Throwable t2) {
        $this.handleInvokeFailure(childrenNotToSuspend, t2);
    }

    /*
     * Unable to fully structure code
     */
    @InternalStableApi
    default public void handleInvokeFailure(Iterable<ActorRef> childrenNotToSuspend, Throwable t) {
        block7: {
            if (this.isFailed()) break block7;
            try {
                this.suspendNonRecursive();
                var6_3 = ((ActorCell)this).currentMessage();
                if (var6_3 == null) ** GOTO lbl-1000
                var7_4 = var6_3.message();
                child = var6_3.sender();
                if (var7_4 instanceof Failed) {
                    this.setFailed(child);
                    v0 = (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{child}));
                } else lbl-1000:
                // 2 sources

                {
                    this.setFailed(((ActorCell)this).self());
                    v0 = Predef$.MODULE$.Set().empty();
                }
                skip = v0;
                ((Children)this).suspendChildren((Set)skip.$plus$plus(childrenNotToSuspend));
                var9_7 = t;
                if (var9_7 instanceof InterruptedException) {
                    ((ActorCell)this).parent().sendSystemMessage(new Failed(((ActorCell)this).self(), new ActorInterruptedException(t), ((ActorCell)this).uid()));
                } else {
                    ((ActorCell)this).parent().sendSystemMessage(new Failed(((ActorCell)this).self(), t, ((ActorCell)this).uid()));
                }
            }
            catch (Throwable x$3) {
                catchExpr$3 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$handleInvokeFailure$1$adapted(akka.actor.dungeon.FaultHandling java.lang.Throwable java.lang.Throwable ), (Ljava/lang/Throwable;)Ljava/lang/Object;)((FaultHandling)this, (Throwable)t));
                if (catchExpr$3.isDefinedAt(x$3)) {
                    catchExpr$3.apply(x$3);
                }
                throw x$3;
            }
            return;
        }
    }

    private void finishTerminate() {
        block137: {
            Actor a = ((ActorCell)this).actor();
            try {
                try {
                    if (a != null) {
                        a.aroundPostStop();
                    }
                }
                catch (Throwable x$4) {
                    PartialFunction<Throwable, BoxedUnit> catchExpr$4 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                        FaultHandling.$anonfun$finishTerminate$1(this, a, e);
                        return BoxedUnit.UNIT;
                    });
                    if (catchExpr$4.isDefinedAt(x$4)) {
                        catchExpr$4.apply(x$4);
                        break block137;
                    }
                    throw x$4;
                }
            }
            finally {
                try {
                    ((Children)((Object)this)).stopFunctionRefs();
                }
                finally {
                    try {
                        ((ActorCell)this).dispatcher().detach((ActorCell)this);
                    }
                    finally {
                        try {
                            ((ActorCell)this).parent().sendSystemMessage(new DeathWatchNotification(((ActorCell)this).self(), true, false));
                        }
                        finally {
                            try {
                                ((DeathWatch)((Object)this)).tellWatchersWeDied();
                            }
                            finally {
                                try {
                                    ((DeathWatch)((Object)this)).unwatchWatchedActors(a);
                                }
                                finally {
                                    if (((ActorCell)this).system().settings().DebugLifecycle()) {
                                        ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(a), "stopped"));
                                    }
                                    ((ActorCell)this).clearActorFields(a, false);
                                    ((ActorCell)this).clearFieldsForTermination();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void finishRecreate(Throwable cause) {
        Iterable<ActorRef> survivors = ((Children)((Object)this)).children();
        try {
            try {
                this.resumeNonRecursive();
            }
            finally {
                this.clearFailed();
            }
            Actor freshActor = ((ActorCell)this).newActor();
            freshActor.aroundPostRestart(cause);
            ((ReceiveTimeout)((Object)this)).checkReceiveTimeout(true);
            if (((ActorCell)this).system().settings().DebugLifecycle()) {
                ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(freshActor), "restarted"));
            }
            survivors.foreach((Function1<ActorRef, Object> & Serializable)child -> {
                FaultHandling.$anonfun$finishRecreate$1(this, cause, freshActor, child);
                return BoxedUnit.UNIT;
            });
        }
        catch (Throwable x$6) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$6 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                FaultHandling.$anonfun$finishRecreate$3(this, survivors, cause, e);
                return BoxedUnit.UNIT;
            });
            if (catchExpr$6.isDefinedAt(x$6)) {
                catchExpr$6.apply(x$6);
            }
            throw x$6;
        }
    }

    public static /* synthetic */ void handleFailure$(FaultHandling $this, Failed f) {
        $this.handleFailure(f);
    }

    default public void handleFailure(Failed f) {
        ((ActorCell)this).currentMessage_$eq(Envelope$.MODULE$.apply(f, f.child(), ((ActorCell)this).system()));
        boolean bl = false;
        Some some = null;
        Option<ChildRestartStats> option = ((Children)((Object)this)).getChildByRef(f.child());
        if (option instanceof Some) {
            bl = true;
            some = (Some)option;
            ChildRestartStats stats = (ChildRestartStats)some.value();
            if (stats.uid() == f.uid()) {
                if (!((ActorCell)this).actor().supervisorStrategy().handleFailure((ActorContext)((Object)this), f.child(), f.cause(), stats, ((Children)((Object)this)).getAllChildStats())) {
                    throw f.cause();
                }
                return;
            }
        }
        if (bl) {
            ChildRestartStats stats = (ChildRestartStats)some.value();
            ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(44).append("dropping Failed(").append(f.cause()).append(") from old child ").append(f.child()).append(" (uid=").append(stats.uid()).append(" != ").append(f.uid()).append(")").toString()));
            return;
        }
        if (None$.MODULE$.equals(option)) {
            ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(37).append("dropping Failed(").append(f.cause()).append(") from unknown child ").append(f.child()).toString()));
            return;
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ void handleChildTerminated$(FaultHandling $this, ActorRef child) {
        $this.handleChildTerminated(child);
    }

    default public void handleChildTerminated(ActorRef child) {
        ChildrenContainer.SuspendReason suspendReason;
        ChildrenContainer.SuspendReason suspendReason2;
        BoxedUnit boxedUnit;
        Option<ChildrenContainer.SuspendReason> status = ((Children)((Object)this)).removeChildAndGetStateChange(child);
        if (((ActorCell)this).actor() != null) {
            try {
                ((ActorCell)this).actor().supervisorStrategy().handleChildTerminated((ActorContext)((Object)this), child, ((Children)((Object)this)).children());
                boxedUnit = BoxedUnit.UNIT;
            }
            catch (Throwable x$7) {
                PartialFunction<Throwable, BoxedUnit> catchExpr$7 = this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                    FaultHandling.$anonfun$handleChildTerminated$1(this, e);
                    return BoxedUnit.UNIT;
                });
                if (catchExpr$7.isDefinedAt(x$7)) {
                    boxedUnit = catchExpr$7.apply(x$7);
                }
                throw x$7;
            }
        } else {
            boxedUnit = BoxedUnit.UNIT;
        }
        boolean bl = false;
        Some some = null;
        Option<ChildrenContainer.SuspendReason> option = status;
        if (option instanceof Some) {
            bl = true;
            some = (Some)option;
            ChildrenContainer.SuspendReason suspendReason3 = (ChildrenContainer.SuspendReason)some.value();
            if (suspendReason3 instanceof ChildrenContainer.Recreation) {
                ChildrenContainer.Recreation recreation = (ChildrenContainer.Recreation)suspendReason3;
                Throwable cause = recreation.cause();
                this.finishRecreate(cause);
                return;
            }
        }
        if (bl && (suspendReason2 = (ChildrenContainer.SuspendReason)some.value()) instanceof ChildrenContainer.Creation) {
            this.finishCreate();
            return;
        }
        if (bl && ChildrenContainer$Termination$.MODULE$.equals(suspendReason = (ChildrenContainer.SuspendReason)some.value())) {
            this.finishTerminate();
            return;
        }
    }

    public static /* synthetic */ PartialFunction handleNonFatalOrInterruptedException$(FaultHandling $this, Function1 thunk) {
        return $this.handleNonFatalOrInterruptedException(thunk);
    }

    default public PartialFunction<Throwable, BoxedUnit> handleNonFatalOrInterruptedException(Function1<Throwable, BoxedUnit> thunk) {
        return new Serializable((ActorCell)this, thunk){
            private static final long serialVersionUID = 0L;
            private final Function1 thunk$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                Option<Throwable> option;
                A1 A1 = x1;
                if (A1 instanceof InterruptedException) {
                    InterruptedException interruptedException = (InterruptedException)A1;
                    this.thunk$1.apply(interruptedException);
                    Thread.currentThread().interrupt();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                    Throwable e = option.get();
                    return (B1)this.thunk$1.apply(e);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Option<Throwable> option;
                Throwable throwable = x1;
                if (throwable instanceof InterruptedException) {
                    return true;
                }
                return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
            }
            {
                this.thunk$1 = thunk$1;
            }
        };
    }

    public static /* synthetic */ void $anonfun$faultRecreate$1(FaultHandling $this, Throwable cause$1, Option optionalMessage$1, Actor failedActor$1, Throwable e) {
        PreRestartException ex = PreRestartException$.MODULE$.apply(((ActorCell)$this).self(), e, cause$1, optionalMessage$1);
        ((ActorCell)$this).publish(new Logging.Error(ex, ((ActorCell)$this).self().path().toString(), ((ActorCell)$this).clazz(failedActor$1), e.getMessage()));
    }

    public static /* synthetic */ void $anonfun$faultCreate$2(FaultHandling $this, ActorRef actor) {
        ((Children)((Object)$this)).stop(actor);
    }

    public static /* synthetic */ void $anonfun$terminate$1(FaultHandling $this, ActorRef actor) {
        ((Children)((Object)$this)).stop(actor);
    }

    public static /* synthetic */ void $anonfun$terminate$2(FaultHandling $this, ActorRef x0$1) {
        ActorRef actorRef2;
        ActorRef actorRef3 = x0$1;
        if (actorRef3 instanceof ActorRefScope && !((ActorRefScope)((Object)(actorRef2 = actorRef3))).isLocal()) {
            ((ActorCell)$this).self().sendSystemMessage(new DeathWatchNotification(actorRef2, true, false));
            return;
        }
    }

    public static /* synthetic */ void $anonfun$handleInvokeFailure$2(FaultHandling $this, ActorRef actor) {
        ((Children)((Object)$this)).stop(actor);
    }

    public static /* synthetic */ void $anonfun$handleInvokeFailure$1(FaultHandling $this, Throwable t$1, Throwable e) {
        ((ActorCell)$this).publish(new Logging.Error(e, ((ActorCell)$this).self().path().toString(), ((ActorCell)$this).clazz(((ActorCell)$this).actor()), new StringBuilder(50).append("emergency stop: exception in failure handling for ").append(t$1.getClass()).append(Logging$.MODULE$.stackTraceFor(t$1)).toString()));
        try {
            ((Children)((Object)$this)).children().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
                FaultHandling.$anonfun$handleInvokeFailure$2($this, actor);
                return BoxedUnit.UNIT;
            });
        }
        finally {
            $this.finishTerminate();
        }
    }

    public static /* synthetic */ void $anonfun$finishTerminate$1(FaultHandling $this, Actor a$1, Throwable e) {
        ((ActorCell)$this).publish(new Logging.Error(e, ((ActorCell)$this).self().path().toString(), ((ActorCell)$this).clazz(a$1), e.getMessage()));
    }

    public static /* synthetic */ void $anonfun$finishRecreate$2(FaultHandling $this, Actor freshActor$1, ActorRef child$1, Throwable e) {
        ((ActorCell)$this).publish(new Logging.Error(e, ((ActorCell)$this).self().path().toString(), ((ActorCell)$this).clazz(freshActor$1), new StringBuilder(11).append("restarting ").append(child$1).toString()));
    }

    public static /* synthetic */ void $anonfun$finishRecreate$1(FaultHandling $this, Throwable cause$2, Actor freshActor$1, ActorRef child) {
        try {
            ((InternalActorRef)child).restart(cause$2);
        }
        catch (Throwable x$5) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$5 = $this.handleNonFatalOrInterruptedException((Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)e -> {
                FaultHandling.$anonfun$finishRecreate$2($this, freshActor$1, child, e);
                return BoxedUnit.UNIT;
            });
            if (catchExpr$5.isDefinedAt(x$5)) {
                catchExpr$5.apply(x$5);
            }
            throw x$5;
        }
    }

    public static /* synthetic */ void $anonfun$finishRecreate$3(FaultHandling $this, Iterable survivors$1, Throwable cause$2, Throwable e) {
        $this.setFailedFatally();
        ((ActorCell)$this).clearActorFields(((ActorCell)$this).actor(), false);
        $this.handleInvokeFailure(survivors$1, PostRestartException$.MODULE$.apply(((ActorCell)$this).self(), e, cause$2));
    }

    public static /* synthetic */ void $anonfun$handleChildTerminated$1(FaultHandling $this, Throwable e) {
        ((ActorCell)$this).publish(new Logging.Error(e, ((ActorCell)$this).self().path().toString(), ((ActorCell)$this).clazz(((ActorCell)$this).actor()), "handleChildTerminated failed"));
        $this.handleInvokeFailure(Nil$.MODULE$, e);
    }

    public static void $init$(FaultHandling $this) {
        $this.akka$actor$dungeon$FaultHandling$$_failed_$eq(FaultHandling$NoFailedInfo$.MODULE$);
    }

    public static /* synthetic */ Object $anonfun$handleInvokeFailure$1$adapted(FaultHandling $this, Throwable t$1, Throwable e) {
        FaultHandling.$anonfun$handleInvokeFailure$1($this, t$1, e);
        return BoxedUnit.UNIT;
    }

    public static interface FailedInfo {
    }

    private static final class FailedRef
    implements FailedInfo,
    Product,
    Serializable {
        private final ActorRef ref;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef ref() {
            return this.ref;
        }

        public FailedRef copy(ActorRef ref) {
            return new FailedRef(ref);
        }

        public ActorRef copy$default$1() {
            return this.ref();
        }

        @Override
        public String productPrefix() {
            return "FailedRef";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.ref();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof FailedRef;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ref";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof FailedRef)) return false;
            boolean bl = true;
            if (!bl) return false;
            FailedRef failedRef = (FailedRef)x$1;
            ActorRef actorRef2 = this.ref();
            ActorRef actorRef3 = failedRef.ref();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public FailedRef(ActorRef ref) {
            this.ref = ref;
            Product.$init$(this);
        }
    }
}

