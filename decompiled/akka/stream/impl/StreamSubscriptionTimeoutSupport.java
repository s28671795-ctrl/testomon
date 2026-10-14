/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.annotation.InternalApi;
import akka.stream.StreamSubscriptionTimeoutSettings;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;
import akka.stream.impl.StreamSubscriptionTimeoutSupport$NoopSubscriptionTimeout$;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import scala.MatchError;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\tmqAB\u0014)\u0011\u0003acF\u0002\u00041Q!\u0005A&\r\u0005\u0006q\u0005!\tAO\u0004\u0006w\u0005A\t\t\u0010\u0004\u0006}\u0005A\ti\u0010\u0005\u0006q\u0011!\tA\u0019\u0005\u0006G\u0012!\t\u0005\u001a\u0005\u0006[\u0012!\tE\u001c\u0005\u0006i\u0012!\t%\u001e\u0005\u0006m\u0012!\te\u001e\u0005\bu\u0012\t\t\u0011\"\u0011|\u0011!yH!!A\u0005\u0002\u0005\u0005\u0001\"CA\u0005\t\u0005\u0005I\u0011AA\u0006\u0011%\t\t\u0002BA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002\"\u0011\t\t\u0011\"\u0001\u0002$!I\u0011Q\u0006\u0003\u0002\u0002\u0013\u0005\u0013q\u0006\u0005\n\u0003c!\u0011\u0011!C!\u0003gA\u0011\"!\u000e\u0005\u0003\u0003%I!a\u000e\b\u0011\u0005e\u0012\u0001#!-\u0003w1\u0001\"!\u0010\u0002\u0011\u0003c\u0013q\b\u0005\u0007qM!\t!!\u0014\t\u000f\u0005=3\u0003\"\u0011\u0002R!9\u00111K\n\u0005B\u0005U\u0003b\u0002>\u0014\u0003\u0003%\te\u001f\u0005\t\u007fN\t\t\u0011\"\u0001\u0002\u0002!I\u0011\u0011B\n\u0002\u0002\u0013\u0005\u0011q\u000b\u0005\n\u0003#\u0019\u0012\u0011!C!\u0003'A\u0011\"!\t\u0014\u0003\u0003%\t!a\u0017\t\u0013\u000552#!A\u0005B\u0005=\u0002\"CA\u0019'\u0005\u0005I\u0011IA\u001a\u0011%\t)dEA\u0001\n\u0013\t9DB\u00061QA\u0005\u0019\u0011\u0001\u0017\u0002t\t\u0005\u0001BBA;?\u0011\u0005Q\u000fC\u0004\u0002x}1\t\"!\u001f\t\u000f\u0005\ru\u0004\"\u0005\u0002\u0006\"9\u0011qJ\u0010\u0005\n\u0005M\u0005bBAc?\u0011%\u0011q\u0019\u0005\b\u0003/|B\u0011CAm\u0011\u001d\t9o\bD\t\u0003S\f\u0001e\u0015;sK\u0006l7+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viN+\b\u000f]8si*\u0011\u0011FK\u0001\u0005S6\u0004HN\u0003\u0002,Y\u000511\u000f\u001e:fC6T\u0011!L\u0001\u0005C.\\\u0017\r\u0005\u00020\u00035\t\u0001F\u0001\u0011TiJ,\u0017-\\*vEN\u001c'/\u001b9uS>tG+[7f_V$8+\u001e9q_J$8CA\u00013!\t\u0019d'D\u00015\u0015\u0005)\u0014!B:dC2\f\u0017BA\u001c5\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001/\u0003M\u0019\u0015M\\2fY&twmU;cg\u000e\u0014\u0018NY3s!\tiD!D\u0001\u0002\u0005M\u0019\u0015M\\2fY&twmU;cg\u000e\u0014\u0018NY3s'\u0015!\u0001\tS*W!\t\te)D\u0001C\u0015\t\u0019E)\u0001\u0003mC:<'\"A#\u0002\t)\fg/Y\u0005\u0003\u000f\n\u0013aa\u00142kK\u000e$\bcA%O!6\t!J\u0003\u0002L\u0019\u0006y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001N\u0003\ry'oZ\u0005\u0003\u001f*\u0013!bU;cg\u000e\u0014\u0018NY3s!\t\u0019\u0014+\u0003\u0002Si\t\u0019\u0011I\\=\u0011\u0005M\"\u0016BA+5\u0005\u001d\u0001&o\u001c3vGR\u0004\"aV0\u000f\u0005akfBA-]\u001b\u0005Q&BA.:\u0003\u0019a$o\\8u}%\tQ'\u0003\u0002_i\u00059\u0001/Y2lC\u001e,\u0017B\u00011b\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tqF\u0007F\u0001=\u0003-ygnU;cg\u000e\u0014\u0018NY3\u0015\u0005\u0015D\u0007CA\u001ag\u0013\t9GG\u0001\u0003V]&$\b\"B5\u0007\u0001\u0004Q\u0017!A:\u0011\u0005%[\u0017B\u00017K\u00051\u0019VOY:de&\u0004H/[8o\u0003\u001dyg.\u0012:s_J$\"!Z8\t\u000bA<\u0001\u0019A9\u0002\u0003Q\u0004\"a\u0016:\n\u0005M\f'!\u0003+ie><\u0018M\u00197f\u0003)ygnQ8na2,G/\u001a\u000b\u0002K\u00061qN\u001c(fqR$\"!\u001a=\t\u000beL\u0001\u0019\u0001)\u0002\t\u0015dW-\\\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003q\u0004\"!Q?\n\u0005y\u0014%AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002\u0004A\u00191'!\u0002\n\u0007\u0005\u001dAGA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002Q\u0003\u001bA\u0011\"a\u0004\r\u0003\u0003\u0005\r!a\u0001\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t)\u0002E\u0003\u0002\u0018\u0005u\u0001+\u0004\u0002\u0002\u001a)\u0019\u00111\u0004\u001b\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002 \u0005e!\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\n\u0002,A\u00191'a\n\n\u0007\u0005%BGA\u0004C_>dW-\u00198\t\u0011\u0005=a\"!AA\u0002A\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003\u0007\t\u0001\u0002^8TiJLgn\u001a\u000b\u0002y\u0006aqO]5uKJ+\u0007\u000f\\1dKR\t\u0001)A\fO_>\u00048+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viB\u0011Qh\u0005\u0002\u0018\u001d>|\u0007oU;cg\u000e\u0014\u0018\u000e\u001d;j_:$\u0016.\\3pkR\u001cba\u0005\u001a\u0002BM3\u0006\u0003BA\"\u0003\u0013j!!!\u0012\u000b\u0007\u0005\u001dC&A\u0003bGR|'/\u0003\u0003\u0002L\u0005\u0015#aC\"b]\u000e,G\u000e\\1cY\u0016$\"!a\u000f\u0002\r\r\fgnY3m)\t\t)#A\u0006jg\u000e\u000bgnY3mY\u0016$WCAA\u0013)\r\u0001\u0016\u0011\f\u0005\n\u0003\u001fI\u0012\u0011!a\u0001\u0003\u0007!B!!\n\u0002^!A\u0011qB\u000e\u0002\u0002\u0003\u0007\u0001\u000bK\u0002\u0014\u0003C\u0002B!a\u0019\u0002j5\u0011\u0011Q\r\u0006\u0004\u0003Ob\u0013AC1o]>$\u0018\r^5p]&!\u00111NA3\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0007I\t\t\u0007K\u0002\u0002\u0003CB3\u0001AA1'\ty\"'\u0001\u0004%S:LG\u000fJ\u0001\u001cgV\u00147o\u0019:jaRLwN\u001c+j[\u0016|W\u000f^*fiRLgnZ:\u0016\u0005\u0005m\u0004\u0003BA?\u0003\u007fj\u0011AK\u0005\u0004\u0003\u0003S#!I*ue\u0016\fWnU;cg\u000e\u0014\u0018\u000e\u001d;j_:$\u0016.\\3pkR\u001cV\r\u001e;j]\u001e\u001c\u0018aG:dQ\u0016$W\u000f\\3Tk\n\u001c8M]5qi&|g\u000eV5nK>,H\u000f\u0006\u0004\u0002B\u0005\u001d\u0015q\u0012\u0005\b\u0003\u000f\u0012\u0003\u0019AAE!\u0011\t\u0019%a#\n\t\u00055\u0015Q\t\u0002\t\u0003\u000e$xN\u001d*fM\"1\u0011\u0011\u0013\u0012A\u0002A\u000bq!\\3tg\u0006<W\rF\u0003f\u0003+\u000b\t\fC\u0004\u0002\u0018\u000e\u0002\r!!'\u0002\rQ\f'oZ3ua\u0011\tY*!*\u0011\u000b%\u000bi*!)\n\u0007\u0005}%JA\u0005Qk\nd\u0017n\u001d5feB!\u00111UAS\u0019\u0001!A\"a*\u0002\u0016\u0006\u0005\t\u0011!B\u0001\u0003S\u00131a\u0018\u00132#\r\tY\u000b\u0015\t\u0004g\u00055\u0016bAAXi\t9aj\u001c;iS:<\u0007bBAZG\u0001\u0007\u0011QW\u0001\bi&lWm\\;u!\u0011\t9,!1\u000e\u0005\u0005e&\u0002BA^\u0003{\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0004\u0003\u007f#\u0014AC2p]\u000e,(O]3oi&!\u00111YA]\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\fAa^1s]R)Q-!3\u0002V\"9\u0011q\u0013\u0013A\u0002\u0005-\u0007\u0007BAg\u0003#\u0004R!SAO\u0003\u001f\u0004B!a)\u0002R\u0012a\u00111[Ae\u0003\u0003\u0005\tQ!\u0001\u0002*\n\u0019q\f\n\u001a\t\u000f\u0005MF\u00051\u0001\u00026\u0006!2/\u001e2tGJL\u0007\u000f^5p]RKW.\u001a3PkR$2!ZAn\u0011\u001d\t9*\na\u0001\u0003;\u0004D!a8\u0002dB)\u0011*!(\u0002bB!\u00111UAr\t1\t)/a7\u0002\u0002\u0003\u0005)\u0011AAU\u0005\ryFeM\u0001\u001aQ\u0006tG\r\\3Tk\n\u001c8M]5qi&|g\u000eV5nK>,H\u000fF\u0003f\u0003W\f9\u0010C\u0004\u0002\u0018\u001a\u0002\r!!<1\t\u0005=\u00181\u001f\t\u0006\u0013\u0006u\u0015\u0011\u001f\t\u0005\u0003G\u000b\u0019\u0010\u0002\u0007\u0002v\u0006-\u0018\u0011!A\u0001\u0006\u0003\tIKA\u0002`IQBq!!?'\u0001\u0004\tY0A\u0003dCV\u001cX\rE\u0002X\u0003{L1!a@b\u0005%)\u0005pY3qi&|gN\u0005\u0004\u0003\u0004\t\u001d!\u0011\u0002\u0004\u0007\u0005\u000b\u0001\u0001A!\u0001\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0005=z\"C\u0002B\u0006\u0005\u001b\u0011\u0019B\u0002\u0004\u0003\u0006\u0001\u0001!\u0011\u0002\t\u0005\u0003\u0007\u0012y!\u0003\u0003\u0003\u0012\u0005\u0015#!B!di>\u0014\b\u0003BA\"\u0005+IAAa\u0006\u0002F\ta\u0011i\u0019;pe2{wmZ5oO\"\u001aq$!\u0019")
@InternalApi
public interface StreamSubscriptionTimeoutSupport {
    public StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings();

    public static /* synthetic */ Cancellable scheduleSubscriptionTimeout$(StreamSubscriptionTimeoutSupport $this, ActorRef actor, Object message) {
        return $this.scheduleSubscriptionTimeout(actor, message);
    }

    default public Cancellable scheduleSubscriptionTimeout(ActorRef actor, Object message) {
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = this.subscriptionTimeoutSettings().mode();
        if (StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
            return StreamSubscriptionTimeoutSupport$NoopSubscriptionTimeout$.MODULE$;
        }
        Cancellable cancellable = ((Actor)((Object)this)).context().system().scheduler().scheduleOnce(this.subscriptionTimeoutSettings().timeout(), actor, message, (ExecutionContext)((Actor)((Object)this)).context().dispatcher(), ((Actor)((Object)this)).self());
        return cancellable;
    }

    private void cancel(Publisher<?> target, FiniteDuration timeout2) {
        long millis = timeout2.toMillis();
        Publisher<?> publisher = target;
        if (publisher instanceof Processor) {
            Processor processor = (Processor)publisher;
            ((ActorLogging)((Object)this)).log().debug("Cancelling {} Processor's publisher and subscriber sides (after {} ms)", processor, BoxesRunTime.boxToLong(millis));
            this.handleSubscriptionTimeout(target, (Exception)((Object)new NoStackTrace(null, millis){

                public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                    return super.fillInStackTrace();
                }

                public Throwable fillInStackTrace() {
                    return NoStackTrace.fillInStackTrace$(this);
                }
                {
                    NoStackTrace.$init$(this);
                }
            }));
            return;
        }
        if (publisher != null) {
            Publisher<?> publisher2 = publisher;
            ((ActorLogging)((Object)this)).log().debug("Cancelling {} (after: {} ms)", publisher2, BoxesRunTime.boxToLong(millis));
            this.handleSubscriptionTimeout(target, (Exception)((Object)new NoStackTrace(null, publisher2){

                public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                    return super.fillInStackTrace();
                }

                public Throwable fillInStackTrace() {
                    return NoStackTrace.fillInStackTrace$(this);
                }
                {
                    NoStackTrace.$init$(this);
                }
            }));
            return;
        }
        throw new MatchError(publisher);
    }

    private void warn(Publisher<?> target, FiniteDuration timeout2) {
        ((ActorLogging)((Object)this)).log().warning("Timed out {} detected (after {} ms)! You should investigate if you either cancel or consume all {} instances", target, (Object)BoxesRunTime.boxToLong(timeout2.toMillis()), (Object)target.getClass().getCanonicalName());
    }

    public static /* synthetic */ void subscriptionTimedOut$(StreamSubscriptionTimeoutSupport $this, Publisher target) {
        $this.subscriptionTimedOut(target);
    }

    default public void subscriptionTimedOut(Publisher<?> target) {
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = this.subscriptionTimeoutSettings().mode();
        if (StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
            return;
        }
        if (StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
            this.warn(target, this.subscriptionTimeoutSettings().timeout());
            return;
        }
        if (StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
            this.cancel(target, this.subscriptionTimeoutSettings().timeout());
            return;
        }
        throw new MatchError(streamSubscriptionTimeoutTerminationMode);
    }

    public void handleSubscriptionTimeout(Publisher<?> var1, Exception var2);

    public static void $init$(StreamSubscriptionTimeoutSupport $this) {
    }
}

