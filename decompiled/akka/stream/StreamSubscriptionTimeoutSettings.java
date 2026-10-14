/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamSubscriptionTimeoutSettings$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import com.typesafe.config.Config;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M<Qa\u0004\t\t\u0002U1Qa\u0006\t\t\u0002aAQaH\u0001\u0005\u0002\u0001BQ!I\u0001\u0005\u0002\tBQ!Y\u0001\u0005\u0002\tDQ!I\u0001\u0005\u0002\u0015DQ!Y\u0001\u0005\u0002E4Aa\u0006\t\u0003I!AQe\u0002BC\u0002\u0013\u0005a\u0005\u0003\u00055\u000f\t\u0005\t\u0015!\u0003(\u0011!1tA!b\u0001\n\u00039\u0004\u0002C\"\b\u0005\u0003\u0005\u000b\u0011\u0002\u001d\t\u000b}9A\u0011A#\t\u000b!;A\u0011I%\t\u000bI;A\u0011I*\u0002CM#(/Z1n'V\u00147o\u0019:jaRLwN\u001c+j[\u0016|W\u000f^*fiRLgnZ:\u000b\u0005E\u0011\u0012AB:ue\u0016\fWNC\u0001\u0014\u0003\u0011\t7n[1\u0004\u0001A\u0011a#A\u0007\u0002!\t\t3\u000b\u001e:fC6\u001cVOY:de&\u0004H/[8o)&lWm\\;u'\u0016$H/\u001b8hgN\u0011\u0011!\u0007\t\u00035ui\u0011a\u0007\u0006\u00029\u0005)1oY1mC&\u0011ad\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005)\u0012AB2sK\u0006$X\rF\u0002$?\u0002\u0004\"AF\u0004\u0014\u0005\u001dI\u0012\u0001B7pI\u0016,\u0012a\n\t\u0003-!J!!\u000b\t\u0003QM#(/Z1n'V\u00147o\u0019:jaRLwN\u001c+j[\u0016|W\u000f\u001e+fe6Lg.\u0019;j_:lu\u000eZ3)\r!YcfL\u00193!\tQB&\u0003\u0002.7\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n\u0001'\u00011Vg\u0016\u0004\u0013\r\u001e;sS\n,H/\u001a\u0011(\u0003\u000e$xN]!uiJL'-\u001e;fg:\u001aFO]3b[N+(m]2sSB$\u0018n\u001c8US6,w.\u001e;N_\u0012,w\u0005\t;pAI,\u0017\r\u001a\u0011uQ\u0016\u00043m\u001c8de\u0016$X\rI:fiRLgn\u001a\u0011wC2,X-A\u0003tS:\u001cW-I\u00014\u0003\u0015\u0011dF\u000e\u00181\u0003\u0015iw\u000eZ3!Q\u0019I1FL\u00182e\u00059A/[7f_V$X#\u0001\u001d\u0011\u0005erT\"\u0001\u001e\u000b\u0005mb\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005uZ\u0012AC2p]\u000e,(O]3oi&\u0011qH\u000f\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8oQ\u0019Q1FL!2e\u0005\n!)\u0001/Vg\u0016\u0004\u0013\r\u001e;sS\n,H/\u001a\u0011(\u0003\u000e$xN]!uiJL'-\u001e;fg:\u001aFO]3b[N+(m]2sSB$\u0018n\u001c8US6,w.\u001e;(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003!!\u0018.\\3pkR\u0004\u0003FB\u0006,]\u0005\u000b$\u0007F\u0002$\r\u001eCQ!\n\u0007A\u0002\u001dBQA\u000e\u0007A\u0002a\na!Z9vC2\u001cHC\u0001&N!\tQ2*\u0003\u0002M7\t9!i\\8mK\u0006t\u0007\"\u0002(\u000e\u0001\u0004y\u0015!B8uQ\u0016\u0014\bC\u0001\u000eQ\u0013\t\t6DA\u0002B]f\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002)B\u0011Q\u000b\u0018\b\u0003-j\u0003\"aV\u000e\u000e\u0003aS!!\u0017\u000b\u0002\rq\u0012xn\u001c;?\u0013\tY6$\u0001\u0004Qe\u0016$WMZ\u0005\u0003;z\u0013aa\u0015;sS:<'BA.\u001c\u0011\u0015)3\u00011\u0001(\u0011\u001514\u00011\u00019\u0003\u0015\t\u0007\u000f\u001d7z)\r\u00193\r\u001a\u0005\u0006K\u0011\u0001\ra\n\u0005\u0006m\u0011\u0001\r\u0001\u000f\u000b\u0003G\u0019DQaZ\u0003A\u0002!\faaY8oM&<\u0007CA5p\u001b\u0005Q'BA4l\u0015\taW.\u0001\u0005usB,7/\u00194f\u0015\u0005q\u0017aA2p[&\u0011\u0001O\u001b\u0002\u0007\u0007>tg-[4\u0015\u0005\r\u0012\b\"B4\u0007\u0001\u0004A\u0007")
public final class StreamSubscriptionTimeoutSettings {
    private final StreamSubscriptionTimeoutTerminationMode mode;
    private final FiniteDuration timeout;

    public static StreamSubscriptionTimeoutSettings apply(Config config) {
        return StreamSubscriptionTimeoutSettings$.MODULE$.apply(config);
    }

    public static StreamSubscriptionTimeoutSettings create(Config config) {
        return StreamSubscriptionTimeoutSettings$.MODULE$.create(config);
    }

    public static StreamSubscriptionTimeoutSettings apply(StreamSubscriptionTimeoutTerminationMode mode, FiniteDuration timeout2) {
        return StreamSubscriptionTimeoutSettings$.MODULE$.apply(mode, timeout2);
    }

    public static StreamSubscriptionTimeoutSettings create(StreamSubscriptionTimeoutTerminationMode mode, FiniteDuration timeout2) {
        return StreamSubscriptionTimeoutSettings$.MODULE$.create(mode, timeout2);
    }

    public StreamSubscriptionTimeoutTerminationMode mode() {
        return this.mode;
    }

    public FiniteDuration timeout() {
        return this.timeout;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object other) {
        Object object = other;
        if (!(object instanceof StreamSubscriptionTimeoutSettings)) return false;
        StreamSubscriptionTimeoutSettings streamSubscriptionTimeoutSettings = (StreamSubscriptionTimeoutSettings)object;
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = streamSubscriptionTimeoutSettings.mode();
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode2 = this.mode();
        if (streamSubscriptionTimeoutTerminationMode == null) {
            if (streamSubscriptionTimeoutTerminationMode2 != null) {
                return false;
            }
        } else if (!streamSubscriptionTimeoutTerminationMode.equals(streamSubscriptionTimeoutTerminationMode2)) return false;
        FiniteDuration finiteDuration = streamSubscriptionTimeoutSettings.timeout();
        FiniteDuration finiteDuration2 = this.timeout();
        if (finiteDuration == null) {
            if (finiteDuration2 == null) return true;
            return false;
        } else {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
    }

    public String toString() {
        return new StringBuilder(36).append("StreamSubscriptionTimeoutSettings(").append(this.mode()).append(",").append(this.timeout()).append(")").toString();
    }

    public StreamSubscriptionTimeoutSettings(StreamSubscriptionTimeoutTerminationMode mode, FiniteDuration timeout2) {
        this.mode = mode;
        this.timeout = timeout2;
    }
}

