/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.ClientAuth;
import com.typesafe.sslconfig.ssl.SSLParametersConfig$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4Aa\u0005\u000b\u0003;!AA\u0005\u0001BC\u0002\u0013\u0005Q\u0005\u0003\u0005+\u0001\t\u0005\t\u0015!\u0003'\u0011!Y\u0003A!b\u0001\n\u0003a\u0003\u0002\u0003!\u0001\u0005\u0003\u0005\u000b\u0011B\u0017\t\r\u0005\u0003A\u0011\u0001\fC\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u0015i\u0005\u0001\"\u0003O\u0011\u001d\t\u0006!%A\u0005\nICq!\u0018\u0001\u0012\u0002\u0013%a\fC\u0003a\u0001\u0011\u0005\u0013mB\u0003c)!\u00051MB\u0003\u0014)!\u0005A\rC\u0003B\u001b\u0011\u0005Q\rC\u0003g\u001b\u0011\u0005q\rC\u0003i\u001b\u0011\u0005q\r\u0003\u0005j\u001bE\u0005I\u0011\u0001\fS\u0011!QW\"%A\u0005\u0002Yq&aE*T\u0019B\u000b'/Y7fi\u0016\u00148oQ8oM&<'BA\u000b\u0017\u0003\r\u00198\u000f\u001c\u0006\u0003/a\t\u0011b]:mG>tg-[4\u000b\u0005eQ\u0012\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003m\t1aY8n\u0007\u0001\u0019\"\u0001\u0001\u0010\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0003\u0005\nQa]2bY\u0006L!a\t\u0011\u0003\r\u0005s\u0017PU3g\u0003)\u0019G.[3oi\u0006+H\u000f[\u000b\u0002MA\u0011q\u0005K\u0007\u0002)%\u0011\u0011\u0006\u0006\u0002\u000b\u00072LWM\u001c;BkRD\u0017aC2mS\u0016tG/Q;uQ\u0002\n\u0011\u0002\u001d:pi>\u001cw\u000e\\:\u0016\u00035\u00022AL\u001a6\u001b\u0005y#B\u0001\u00192\u0003%IW.\\;uC\ndWM\u0003\u00023A\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Qz#aA*fcB\u0011a'\u0010\b\u0003om\u0002\"\u0001\u000f\u0011\u000e\u0003eR!A\u000f\u000f\u0002\rq\u0012xn\u001c;?\u0013\ta\u0004%\u0001\u0004Qe\u0016$WMZ\u0005\u0003}}\u0012aa\u0015;sS:<'B\u0001\u001f!\u0003)\u0001(o\u001c;pG>d7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\r#U\t\u0005\u0002(\u0001!9A%\u0002I\u0001\u0002\u00041\u0003bB\u0016\u0006!\u0003\u0005\r!L\u0001\u000fo&$\bn\u00117jK:$\u0018)\u001e;i)\t\u0019\u0005\nC\u0003J\r\u0001\u0007a%A\u0003wC2,X-A\u0007xSRD\u0007K]8u_\u000e|Gn\u001d\u000b\u0003\u00072CQ!S\u0004A\u00025\nAaY8qsR\u00191i\u0014)\t\u000f\u0011B\u0001\u0013!a\u0001M!91\u0006\u0003I\u0001\u0002\u0004i\u0013AD2paf$C-\u001a4bk2$H%M\u000b\u0002'*\u0012a\u0005V\u0016\u0002+B\u0011akW\u0007\u0002/*\u0011\u0001,W\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0017\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002]/\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\tqL\u000b\u0002.)\u0006AAo\\*ue&tw\rF\u00016\u0003M\u00196\u000b\u0014)be\u0006lW\r^3sg\u000e{gNZ5h!\t9Sb\u0005\u0002\u000e=Q\t1-A\u0003baBd\u0017\u0010F\u0001D\u0003-9W\r^%ogR\fgnY3\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0001")
public final class SSLParametersConfig {
    private final ClientAuth clientAuth;
    private final Seq<String> protocols;

    public static SSLParametersConfig getInstance() {
        return SSLParametersConfig$.MODULE$.getInstance();
    }

    public static SSLParametersConfig apply() {
        return SSLParametersConfig$.MODULE$.apply();
    }

    public ClientAuth clientAuth() {
        return this.clientAuth;
    }

    public Seq<String> protocols() {
        return this.protocols;
    }

    public SSLParametersConfig withClientAuth(ClientAuth value) {
        return this.copy(value, this.copy$default$2());
    }

    public SSLParametersConfig withProtocols(Seq<String> value) {
        Seq<String> x$1 = value;
        ClientAuth x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private SSLParametersConfig copy(ClientAuth clientAuth, Seq<String> protocols) {
        return new SSLParametersConfig(clientAuth, protocols);
    }

    private ClientAuth copy$default$1() {
        return this.clientAuth();
    }

    private Seq<String> copy$default$2() {
        return this.protocols();
    }

    public String toString() {
        return new StringBuilder(22).append("SSLParametersConfig(").append(this.clientAuth()).append(",").append(this.protocols()).append(")").toString();
    }

    public SSLParametersConfig(ClientAuth clientAuth, Seq<String> protocols) {
        this.clientAuth = clientAuth;
        this.protocols = protocols;
    }
}

