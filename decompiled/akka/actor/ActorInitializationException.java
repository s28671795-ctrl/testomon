/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.ActorInitializationException$;
import akka.actor.ActorRef;
import scala.Option;
import scala.Tuple3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4Aa\u0004\t\u0001+!A\u0011\u0003\u0001B\u0001B\u0003%!\u0004\u0003\u0005\u001f\u0001\t\u0005\t\u0015!\u0003 \u0011!a\u0003A!A!\u0002\u0013i\u0003\"\u0002\u001c\u0001\t#9\u0004\"\u0002\u001f\u0001\t\u0003it!B#\u0011\u0011\u00031e!B\b\u0011\u0011\u00039\u0005\"\u0002\u001c\b\t\u0003\u0019\u0006\"\u0002+\b\t\u0013)\u0006B\u0002-\b\t\u0003\u0011\u0012\f\u0003\u0005^\u000fE\u0005I\u0011\u0001\n_\u0011\u0019Av\u0001\"\u0001\u0013S\")1n\u0002C\u0001Y\"9QoBA\u0001\n\u00131(\u0001H!di>\u0014\u0018J\\5uS\u0006d\u0017N_1uS>tW\t_2faRLwN\u001c\u0006\u0003#I\tQ!Y2u_JT\u0011aE\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u00011\u0002CA\f\u0019\u001b\u0005\u0011\u0012BA\r\u0013\u00055\t5n[1Fq\u000e,\u0007\u000f^5p]B\u00111\u0004H\u0007\u0002!%\u0011Q\u0004\u0005\u0002\t\u0003\u000e$xN\u001d*fM\u00069Q.Z:tC\u001e,\u0007C\u0001\u0011*\u001d\t\ts\u0005\u0005\u0002#K5\t1E\u0003\u0002%)\u00051AH]8pizR\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\na\u0001\u0015:fI\u00164\u0017B\u0001\u0016,\u0005\u0019\u0019FO]5oO*\u0011\u0001&J\u0001\u0006G\u0006,8/\u001a\t\u0003]Mr!aL\u0019\u000f\u0005\t\u0002\u0014\"\u0001\u0014\n\u0005I*\u0013a\u00029bG.\fw-Z\u0005\u0003iU\u0012\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0005I*\u0013A\u0002\u001fj]&$h\b\u0006\u00039siZ\u0004CA\u000e\u0001\u0011\u0015\tB\u00011\u0001\u001b\u0011\u0015qB\u00011\u0001 \u0011\u0015aC\u00011\u0001.\u0003!9W\r^!di>\u0014X#\u0001\u000e)\t\u0001y4\t\u0012\t\u0003\u0001\u0006k\u0011!J\u0005\u0003\u0005\u0016\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\tA$Q2u_JLe.\u001b;jC2L'0\u0019;j_:,\u0005pY3qi&|g\u000e\u0005\u0002\u001c\u000fM\u0019q\u0001S&\u0011\u0005\u0001K\u0015B\u0001&&\u0005\u0019\te.\u001f*fMB\u0011A*U\u0007\u0002\u001b*\u0011ajT\u0001\u0003S>T\u0011\u0001U\u0001\u0005U\u00064\u0018-\u0003\u0002S\u001b\na1+\u001a:jC2L'0\u00192mKR\ta)A\bf]JL7\r[3e\u001b\u0016\u001c8/Y4f)\rybk\u0016\u0005\u0006#%\u0001\rA\u0007\u0005\u0006=%\u0001\raH\u0001\u0006CB\u0004H.\u001f\u000b\u0005qi[F\fC\u0003\u0012\u0015\u0001\u0007!\u0004C\u0003\u001f\u0015\u0001\u0007q\u0004C\u0004-\u0015A\u0005\t\u0019A\u0017\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIM*\u0012a\u0018\u0016\u0003[\u0001\\\u0013!\u0019\t\u0003E\u001el\u0011a\u0019\u0006\u0003I\u0016\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0019,\u0013AC1o]>$\u0018\r^5p]&\u0011\u0001n\u0019\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,GC\u0001\u001dk\u0011\u0015qB\u00021\u0001 \u0003\u001d)h.\u00199qYf$\"!\\:\u0011\u0007\u0001s\u0007/\u0003\u0002pK\t1q\n\u001d;j_:\u0004R\u0001Q9\u001b?5J!A]\u0013\u0003\rQ+\b\u000f\\34\u0011\u0015!X\u00021\u00019\u0003\t)\u00070\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001x!\tA80D\u0001z\u0015\tQx*\u0001\u0003mC:<\u0017B\u0001?z\u0005\u0019y%M[3di\u0002")
public class ActorInitializationException
extends AkkaException {
    private static final long serialVersionUID = 1L;
    private final ActorRef actor;

    public static Option<Tuple3<ActorRef, String, Throwable>> unapply(ActorInitializationException ex) {
        return ActorInitializationException$.MODULE$.unapply(ex);
    }

    public ActorRef getActor() {
        return this.actor;
    }

    public ActorInitializationException(ActorRef actor, String message, Throwable cause) {
        this.actor = actor;
        super(ActorInitializationException$.MODULE$.akka$actor$ActorInitializationException$$enrichedMessage(actor, message), cause);
    }
}

