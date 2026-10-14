/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.annotation.DoNotInherit;
import akka.stream.StreamRefSettings$;
import com.typesafe.config.Config;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y<Q\u0001E\t\t\u0002Y1Q\u0001G\t\t\u0002eAQ\u0001I\u0001\u0005\u0002\u0005BQAI\u0001\u0005\u0002\rBQ![\u0001\u0005\u0002)DQAI\u0001\u0005\u00025DQ![\u0001\u0005\u0002m4q\u0001G\t\u0011\u0002G\u0005Q\u0005C\u0003'\u000f\u0019\u0005q\u0005C\u00036\u000f\u0019\u0005a\u0007C\u0003C\u000f\u0019\u0005a\u0007C\u0003G\u000f\u0019\u0005a\u0007C\u0003K\u000f\u0019\u00051\nC\u0003O\u000f\u0019\u0005q\nC\u0003R\u000f\u0019\u0005!\u000bC\u0003U\u000f\u0019\u0005Q+A\tTiJ,\u0017-\u001c*fMN+G\u000f^5oONT!AE\n\u0002\rM$(/Z1n\u0015\u0005!\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0018\u00035\t\u0011CA\tTiJ,\u0017-\u001c*fMN+G\u000f^5oON\u001c\"!\u0001\u000e\u0011\u0005mqR\"\u0001\u000f\u000b\u0003u\tQa]2bY\u0006L!a\b\u000f\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta#\u0001\u0004de\u0016\fG/\u001a\u000b\u0003Iy\u0003\"aF\u0004\u0014\u0005\u001dQ\u0012A\u00042vM\u001a,'oQ1qC\u000eLG/_\u000b\u0002QA\u00111$K\u0005\u0003Uq\u00111!\u00138uQ\u0019AAf\f\u00193gA\u00111$L\u0005\u0003]q\u0011!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013!M\u0001V+N,\u0007%\u0019;ue&\u0014W\u000f^3!OM#(/Z1n%\u00164\u0017\t\u001e;sS\n,H/Z:/\u0005V4g-\u001a:DCB\f7-\u001b;zO\u0001\"x\u000e\t:fC\u0012\u0004C\u000f[3!G>t7M]3uK\u0002\u001aX\r\u001e;j]\u001e\u0004c/\u00197vK\u0006)1/\u001b8dK\u0006\nA'A\u00033]Yr\u0003'\u0001\reK6\fg\u000e\u001a*fI\u0016d\u0017N^3ss&sG/\u001a:wC2,\u0012a\u000e\t\u0003quj\u0011!\u000f\u0006\u0003um\n\u0001\u0002Z;sCRLwN\u001c\u0006\u0003yq\t!bY8oGV\u0014(/\u001a8u\u0013\tq\u0014H\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8)\r%as\u0006\u0011\u001a4C\u0005\t\u0015aX+tK\u0002\nG\u000f\u001e:jEV$X\rI\u0014TiJ,\u0017-\u001c*fM\u0006#HO]5ckR,7O\f#f[\u0006tGMU3eK2Lg/\u001a:z\u0013:$XM\u001d<bY\u001e\u0002Co\u001c\u0011sK\u0006$\u0007\u0005\u001e5fA\r|gn\u0019:fi\u0016\u00043/\u001a;uS:<\u0007E^1mk\u0016\f1c];cg\u000e\u0014\u0018\u000e\u001d;j_:$\u0016.\\3pkRDcA\u0003\u00170\tJ\u001a\u0014%A#\u00025V\u001bX\rI1uiJL'-\u001e;fA\u001d\u001aFO]3b[J+g-\u0011;ue&\u0014W\u000f^3t]M+(m]2sSB$\u0018n\u001c8US6,w.\u001e;(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003y1\u0017N\\1m)\u0016\u0014X.\u001b8bi&|gnU5h]\u0006dG)Z1eY&tW\r\u000b\u0004\fY=B%gM\u0011\u0002\u0013\u0006)Wk]3!CR$(/\u001b2vi\u0016\u0004se\u0015;sK\u0006l'+\u001a4BiR\u0014\u0018NY;uKNtc)\u001b8bYR+'/\\5oCRLwN\\*jO:\fG\u000eR3bI2Lg.Z\u0014!i>\u0004#/Z1eAQDW\rI2p]\u000e\u0014X\r^3!g\u0016$H/\u001b8hAY\fG.^3\u0002%]LG\u000f\u001b\"vM\u001a,'oQ1qC\u000eLG/\u001f\u000b\u0003I1CQ!\u0014\u0007A\u0002!\nQA^1mk\u0016\fAd^5uQ\u0012+W.\u00198e%\u0016$W\r\\5wKJL\u0018J\u001c;feZ\fG\u000e\u0006\u0002%!\")Q*\u0004a\u0001o\u00059r/\u001b;i'V\u00147o\u0019:jaRLwN\u001c+j[\u0016|W\u000f\u001e\u000b\u0003IMCQ!\u0014\bA\u0002]\nQf^5uQR+'/\\5oCRLwN\u001c*fG\u0016Lg/\u001a3CK\u001a|'/Z\"p[BdW\r^5p]2+Wm^1z)\t!c\u000bC\u0003N\u001f\u0001\u0007q\u0007\u000b\u0002\b1B\u0011\u0011\fX\u0007\u00025*\u00111lE\u0001\u000bC:tw\u000e^1uS>t\u0017BA/[\u00051!uNT8u\u0013:DWM]5u\u0011\u0015y6\u00011\u0001a\u0003\u0019\u0019\u0018p\u001d;f[B\u0011\u0011\rZ\u0007\u0002E*\u00111mE\u0001\u0006C\u000e$xN]\u0005\u0003K\n\u00141\"Q2u_J\u001c\u0016p\u001d;f[\"21\u0001L\u0018heM\n\u0013\u0001[\u0001\u0002rU\u001bX\rI1uiJL'-\u001e;fg\u0002zg\u000e\t;iK\u0002\u0012VO\u001c8bE2,\u0007e\u001a:ba\"\u0004sN\u001d\u0011dQ\u0006tw-\u001a\u0011uQ\u0016\u0004C-\u001a4bk2$8\u000fI5oA\r|gNZ5hkJ\fG/[8oY\u0001\u001aX-\u001a\u0011nS\u001e\u0014\u0018\r^5p]\u0002:W/\u001b3fA\u0019|'\u000f\t3fi\u0006LGn\u001d\u0011iiR\u00048OO\u00180I>\u001cg&Y6lC:Jwn\f3pGN|\u0013m[6b_Ircg\f9s_*,7\r^\u0018nS\u001e\u0014\u0018\r^5p]6:W/\u001b3f[IrSG\f=.e92d\u0006\u001f\u0018ii6d\u0017!B1qa2LHC\u0001\u0013l\u0011\u0015yF\u00011\u0001aQ\u0019!AfL43gQ\u0011AE\u001c\u0005\u0006_\u0016\u0001\r\u0001]\u0001\u0002GB\u0011\u0011\u000f_\u0007\u0002e*\u00111\u000f^\u0001\u0007G>tg-[4\u000b\u0005U4\u0018\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003]\f1aY8n\u0013\tI(O\u0001\u0004D_:4\u0017n\u001a\u0015\u0007\u000b1zsMM\u001a\u0015\u0005\u0011b\b\"B8\u0007\u0001\u0004\u0001\bF\u0002\u0004-_\u001d\u00144\u0007")
@DoNotInherit
public interface StreamRefSettings {
    public static StreamRefSettings apply(Config c) {
        return StreamRefSettings$.MODULE$.apply(c);
    }

    public static StreamRefSettings create(Config c) {
        return StreamRefSettings$.MODULE$.create(c);
    }

    public static StreamRefSettings apply(ActorSystem system) {
        return StreamRefSettings$.MODULE$.apply(system);
    }

    public static StreamRefSettings create(ActorSystem system) {
        return StreamRefSettings$.MODULE$.create(system);
    }

    public int bufferCapacity();

    public FiniteDuration demandRedeliveryInterval();

    public FiniteDuration subscriptionTimeout();

    public FiniteDuration finalTerminationSignalDeadline();

    public StreamRefSettings withBufferCapacity(int var1);

    public StreamRefSettings withDemandRedeliveryInterval(FiniteDuration var1);

    public StreamRefSettings withSubscriptionTimeout(FiniteDuration var1);

    public StreamRefSettings withTerminationReceivedBeforeCompletionLeeway(FiniteDuration var1);
}

