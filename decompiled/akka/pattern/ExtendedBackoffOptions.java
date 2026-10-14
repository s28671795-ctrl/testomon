/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.annotation.DoNotInherit;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3\u0001\u0002C\u0005\u0011\u0002G\u00052\"\u0004\u0005\u0006+\u00011\ta\u0006\u0005\u0006Y\u00011\t!\f\u0005\u0006]\u00011\ta\f\u0005\u0006q\u00011\t!\u000f\u0005\u0006\u007f\u00011\t\u0001\u0011\u0005\u0006\r\u00021\ta\u0012\u0005\u0007\u001b\u00021\ta\u0003(\u0003-\u0015CH/\u001a8eK\u0012\u0014\u0015mY6pM\u001a|\u0005\u000f^5p]NT!AC\u0006\u0002\u000fA\fG\u000f^3s]*\tA\"\u0001\u0003bW.\fWC\u0001\b\u001b'\t\u0001q\u0002\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VMZ\u0001\u000eo&$\b.Q;u_J+7/\u001a;\u0004\u0001Q\u0011\u0001D\t\t\u00033ia\u0001\u0001B\u0003\u001c\u0001\t\u0007ADA\u0001U#\ti\u0002\u0005\u0005\u0002\u0011=%\u0011q$\u0005\u0002\b\u001d>$\b.\u001b8h!\r\t\u0003\u0001G\u0007\u0002\u0013!)1%\u0001a\u0001I\u0005a!/Z:fi\n\u000b7m[8gMB\u0011QEK\u0007\u0002M)\u0011q\u0005K\u0001\tIV\u0014\u0018\r^5p]*\u0011\u0011&E\u0001\u000bG>t7-\u001e:sK:$\u0018BA\u0016'\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\fqb^5uQ6\u000bg.^1m%\u0016\u001cX\r^\u000b\u00021\u00051r/\u001b;i'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017\u0010\u0006\u0002\u0019a!)\u0011g\u0001a\u0001e\u0005\u00112/\u001e9feZL7o\u001c:TiJ\fG/Z4z!\t\u0019d'D\u00015\u0015\t)4\"A\u0003bGR|'/\u0003\u00028i\t\trJ\\3G_J|e.Z*ue\u0006$XmZ=\u0002%]LG\u000f['bq:\u0013xJ\u001a*fiJLWm\u001d\u000b\u00031iBQa\u000f\u0003A\u0002q\na\"\\1y\u001dJ|eMU3ue&,7\u000f\u0005\u0002\u0011{%\u0011a(\u0005\u0002\u0004\u0013:$\u0018!F<ji\"\u0014V\r\u001d7z/\"LG.Z*u_B\u0004X\r\u001a\u000b\u00031\u0005CQAQ\u0003A\u0002\r\u000b\u0011C]3qYf<\u0006.\u001b7f'R|\u0007\u000f]3e!\t\u0001B)\u0003\u0002F#\t\u0019\u0011I\\=\u0002/]LG\u000f\u001b%b]\u0012dWM],iS2,7\u000b^8qa\u0016$GC\u0001\rI\u0011\u0015Ie\u00011\u0001K\u0003\u001dA\u0017M\u001c3mKJ\u0004\"aM&\n\u00051#$\u0001C!di>\u0014(+\u001a4\u0002\u000bA\u0014x\u000e]:\u0016\u0003=\u0003\"a\r)\n\u0005E#$!\u0002)s_B\u001c\u0018f\u0001\u0001T+&\u0011A+\u0003\u0002\u0018\u0005\u0006\u001c7n\u001c4g\u001f:4\u0015-\u001b7ve\u0016|\u0005\u000f^5p]NL!AV\u0005\u0003)\t\u000b7m[8gM>s7\u000b^8q\u001fB$\u0018n\u001c8tQ\t\u0001\u0001\f\u0005\u0002Z96\t!L\u0003\u0002\\\u0017\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005uS&\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
public interface ExtendedBackoffOptions<T extends ExtendedBackoffOptions<T>> {
    public T withAutoReset(FiniteDuration var1);

    public T withManualReset();

    public T withSupervisorStrategy(OneForOneStrategy var1);

    public T withMaxNrOfRetries(int var1);

    public T withReplyWhileStopped(Object var1);

    public T withHandlerWhileStopped(ActorRef var1);

    public Props props();
}

