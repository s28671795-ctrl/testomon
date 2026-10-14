/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.pattern.AskableActorRef$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u001ds!B\u0013'\u0011\u0003Yc!B\u0017'\u0011\u0003q\u0003\"B\u001b\u0002\t\u00031\u0004\"B\u001c\u0002\t\u0013A\u0004BB)\u0002\t\u0003A#\u000b\u0003\u0004h\u0003\u0011\u0005\u0001\u0006\u001b\u0005\u0007a\u0006!\t\u0001K9\t\u000bY\fAQA<\t\rY\fAQAA[\u0011%\t\u0019-AI\u0001\n\u000b\t)\rC\u0004\u0002N\u0006!)!a4\t\u0013\u0005u\u0017!%A\u0005\u0006\u0005}\u0007bBAt\u0003\u0011\u0015\u0011\u0011\u001e\u0005\b\u0003s\fAQAA~\u0011\u001d\tI0\u0001C\u0003\u0005\u000fAqA!\u0006\u0002\t\u000b\u00119\u0002C\u0005\u0003$\u0005\t\n\u0011\"\u0002\u0003&!I!QF\u0001\u0012\u0002\u0013\u0015!q\u0006\u0005\n\u0005o\t\u0011\u0011!C\u0003\u0005sA\u0011B!\u0010\u0002\u0003\u0003%)Aa\u0010\u0007\u000b52#!a\u0006\t\u0015\u0005}AC!b\u0001\n\u0003\t\t\u0003C\u0005\u0002$Q\u0011\t\u0011)A\u0005\u0017\"1Q\u0007\u0006C\u0001\u0003KAq!!\u000b\u0015\t#\tY\u0003C\u0004\u0002*Q!\t!!\r\t\u0013\u0005mB#%A\u0005\u0002\u0005u\u0002bBA*)\u0011\u0005\u0011Q\u000b\u0005\n\u0003?\"\u0012\u0013!C\u0001\u0003CB\u0001\"!\u001a\u0015\t\u00031\u0013q\r\u0005\u000b\u0003g\"\u0012\u0013!C\u0001M\u0005U\u0004bBA=)\u0011E\u00111\u0010\u0005\b\u0003s\"B\u0011AAB\u0011%\ti\tFI\u0001\n\u0003\ty\t\u0003\u0005\u0002\u0014R!\tAJAK\u0011%\ti\nFA\u0001\n\u0003\ny\nC\u0005\u0002(R\t\t\u0011\"\u0011\u0002*\u0006y\u0011i]6bE2,\u0017i\u0019;peJ+gM\u0003\u0002(Q\u00059\u0001/\u0019;uKJt'\"A\u0015\u0002\t\u0005\\7.Y\u0002\u0001!\ta\u0013!D\u0001'\u0005=\t5o[1cY\u0016\f5\r^8s%\u001647CA\u00010!\t\u00014'D\u00012\u0015\u0005\u0011\u0014!B:dC2\f\u0017B\u0001\u001b2\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aK\u0001\u0017[\u0016\u001c8/Y4f!\u0006\u0014Ho\u00144Fq\u000e,\u0007\u000f^5p]R\u0019\u0011\bR%\u0011\u0005i\neBA\u001e@!\ta\u0014'D\u0001>\u0015\tq$&\u0001\u0004=e>|GOP\u0005\u0003\u0001F\na\u0001\u0015:fI\u00164\u0017B\u0001\"D\u0005\u0019\u0019FO]5oO*\u0011\u0001)\r\u0005\u0006\u000b\u000e\u0001\rAR\u0001\b[\u0016\u001c8/Y4f!\t\u0001t)\u0003\u0002Ic\t\u0019\u0011I\\=\t\u000b)\u001b\u0001\u0019A&\u0002\rM,g\u000eZ3s!\tau*D\u0001N\u0015\tq\u0005&A\u0003bGR|'/\u0003\u0002Q\u001b\nA\u0011i\u0019;peJ+g-\u0001\roK\u001e\fG/\u001b<f)&lWm\\;u\u000bb\u001cW\r\u001d;j_:$Ba\u0015/_?B\u0011A+\u0017\b\u0003+^s!\u0001\u0010,\n\u0003IJ!\u0001W\u0019\u0002\u000fA\f7m[1hK&\u0011!l\u0017\u0002\u0019\u00132dWmZ1m\u0003J<W/\\3oi\u0016C8-\u001a9uS>t'B\u0001-2\u0011\u0015iF\u00011\u0001G\u0003%\u0011XmY5qS\u0016tG\u000fC\u0003F\t\u0001\u0007a\tC\u0003K\t\u0001\u00071\n\u000b\u0002\u0005CB\u0011!-Z\u0007\u0002G*\u0011A\rK\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00014d\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u00029I,7-\u001b9jK:$H+\u001a:nS:\fG/\u001a3Fq\u000e,\u0007\u000f^5p]R!\u0011\u000e\\7o!\ta#.\u0003\u0002lM\t\u0019\u0012i]6US6,w.\u001e;Fq\u000e,\u0007\u000f^5p]\")Q,\u0002a\u0001\r\")Q)\u0002a\u0001\r\")!*\u0002a\u0001\u0017\"\u0012Q!Y\u0001\u0019k:\u001cX\u000f\u001d9peR,GMU3dSBLWM\u001c;UsB,G\u0003B*sgRDQ!\u0018\u0004A\u0002\u0019CQ!\u0012\u0004A\u0002\u0019CQA\u0013\u0004A\u0002-C#AB1\u0002\u001b\u0005\u001c8\u000eJ3yi\u0016t7/[8o)\rA\u0018\u0011\u0003\u000b\u0005s~\f\t\u0001E\u0002{{\u001ak\u0011a\u001f\u0006\u0003yF\n!bY8oGV\u0014(/\u001a8u\u0013\tq8P\u0001\u0004GkR,(/\u001a\u0005\u0006\u000b\u001e\u0001\rA\u0012\u0005\b\u0003\u00079\u0001\u0019AA\u0003\u0003\u001d!\u0018.\\3pkR\u0004B!a\u0002\u0002\u000e5\u0011\u0011\u0011\u0002\u0006\u0004\u0003\u0017A\u0013\u0001B;uS2LA!a\u0004\u0002\n\t9A+[7f_V$\bbBA\n\u000f\u0001\u0007\u0011QC\u0001\u0006IQD\u0017n\u001d\t\u0003YQ\u00192\u0001FA\r!\r\u0001\u00141D\u0005\u0004\u0003;\t$AB!osZ\u000bG.\u0001\u0005bGR|'OU3g+\u0005Y\u0015!C1di>\u0014(+\u001a4!)\u0011\t)\"a\n\t\r\u0005}q\u00031\u0001L\u0003\r\t7o\u001b\u000b\u0006s\u00065\u0012q\u0006\u0005\u0006\u000bb\u0001\rA\u0012\u0005\b\u0003\u0007A\u0002\u0019AA\u0003)\u0011\t\u0019$!\u000f\u0015\u000be\f)$a\u000e\t\u000f\u0005\r\u0011\u0004q\u0001\u0002\u0006!9!*\u0007I\u0001\u0002\bY\u0005\"B#\u001a\u0001\u00041\u0015!D1tW\u0012\"WMZ1vYR$3\u0007\u0006\u0003\u0002@\u0005E#fA&\u0002B-\u0012\u00111\t\t\u0005\u0003\u000b\ni%\u0004\u0002\u0002H)!\u0011\u0011JA&\u0003%)hn\u00195fG.,GM\u0003\u0002ec%!\u0011qJA$\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0005\u0006\u000bj\u0001\rAR\u0001\u000eCN\\w+\u001b;i'R\fG/^:\u0015\t\u0005]\u0013Q\f\u000b\u0006s\u0006e\u00131\f\u0005\b\u0003\u0007Y\u00029AA\u0003\u0011\u001dQ5\u0004%AA\u0004-CQ!R\u000eA\u0002\u0019\u000bq#Y:l/&$\bn\u0015;biV\u001cH\u0005Z3gCVdG\u000fJ\u001a\u0015\t\u0005}\u00121\r\u0005\u0006\u000br\u0001\rAR\u0001\u0016S:$XM\u001d8bY\u0006\u001b8nV5uQN#\u0018\r^;t)\u0011\tI'a\u001c\u0015\u000be\fY'!\u001c\t\u000f\u0005\rQ\u0004q\u0001\u0002\u0006!9!*\bI\u0001\u0002\bY\u0005\"B#\u001e\u0001\u00041\u0005FA\u000fb\u0003}Ig\u000e^3s]\u0006d\u0017i]6XSRD7\u000b^1ukN$C-\u001a4bk2$He\r\u000b\u0005\u0003\u007f\t9\bC\u0003F=\u0001\u0007a)\u0001\u0004%c6\f'o\u001b\u000b\u0005\u0003{\n\t\tF\u0002z\u0003\u007fBq!a\u0001 \u0001\b\t)\u0001C\u0003F?\u0001\u0007a\t\u0006\u0003\u0002\u0006\u0006-E#B=\u0002\b\u0006%\u0005bBA\u0002A\u0001\u000f\u0011Q\u0001\u0005\b\u0015\u0002\u0002\n\u0011q\u0001L\u0011\u0015)\u0005\u00051\u0001G\u0003A!\u0013/\\1sW\u0012\"WMZ1vYR$3\u0007\u0006\u0003\u0002@\u0005E\u0005\"B#\"\u0001\u00041\u0015aC5oi\u0016\u0014h.\u00197Bg.$r!_AL\u00033\u000bY\nC\u0003FE\u0001\u0007a\tC\u0004\u0002\u0004\t\u0002\r!!\u0002\t\u000b)\u0013\u0003\u0019A&\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!)\u0011\u0007A\n\u0019+C\u0002\u0002&F\u00121!\u00138u\u0003\u0019)\u0017/^1mgR!\u00111VAY!\r\u0001\u0014QV\u0005\u0004\u0003_\u000b$a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003g#\u0013\u0011!a\u0001\r\u0006\u0019\u0001\u0010J\u0019\u0015\t\u0005]\u0016\u0011\u0019\u000b\u0005\u0003s\u000by\fF\u0003z\u0003w\u000bi\fC\u0004\u0002\u0004!\u0001\u001d!!\u0002\t\u000f)C\u0001\u0013!a\u0002\u0017\")Q\t\u0003a\u0001\r\"9\u00111\u0003\u0005A\u0002\u0005U\u0011aF1tW\u0012\"WMZ1vYR$3\u0007J3yi\u0016t7/[8o)\u0011\t9-a3\u0015\t\u0005}\u0012\u0011\u001a\u0005\u0006\u000b&\u0001\rA\u0012\u0005\b\u0003'I\u0001\u0019AA\u000b\u0003]\t7o[,ji\"\u001cF/\u0019;vg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002R\u0006mG\u0003BAj\u00033$R!_Ak\u0003/Dq!a\u0001\u000b\u0001\b\t)\u0001C\u0004K\u0015A\u0005\t9A&\t\u000b\u0015S\u0001\u0019\u0001$\t\u000f\u0005M!\u00021\u0001\u0002\u0016\u0005\t\u0013m]6XSRD7\u000b^1ukN$C-\u001a4bk2$He\r\u0013fqR,gn]5p]R!\u0011\u0011]As)\u0011\ty$a9\t\u000b\u0015[\u0001\u0019\u0001$\t\u000f\u0005M1\u00021\u0001\u0002\u0016\u0005y\u0012N\u001c;fe:\fG.Q:l/&$\bn\u0015;biV\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005-\u0018Q\u001f\u000b\u0005\u0003[\f\u0019\u0010F\u0003z\u0003_\f\t\u0010C\u0004\u0002\u00041\u0001\u001d!!\u0002\t\u000f)c\u0001\u0013!a\u0002\u0017\")Q\t\u0004a\u0001\r\"9\u00111\u0003\u0007A\u0002\u0005U\u0001F\u0001\u0007b\u0003A!\u0013/\\1sW\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002~\n\u0015A\u0003BA\u0000\u0005\u0007!2!\u001fB\u0001\u0011\u001d\t\u0019!\u0004a\u0002\u0003\u000bAQ!R\u0007A\u0002\u0019Cq!a\u0005\u000e\u0001\u0004\t)\u0002\u0006\u0003\u0003\n\tMA\u0003\u0002B\u0006\u0005#!R!\u001fB\u0007\u0005\u001fAq!a\u0001\u000f\u0001\b\t)\u0001C\u0004K\u001dA\u0005\t9A&\t\u000b\u0015s\u0001\u0019\u0001$\t\u000f\u0005Ma\u00021\u0001\u0002\u0016\u0005)\u0012N\u001c;fe:\fG.Q:lI\u0015DH/\u001a8tS>tG\u0003\u0002B\r\u0005C!r!\u001fB\u000e\u0005;\u0011y\u0002C\u0003F\u001f\u0001\u0007a\tC\u0004\u0002\u0004=\u0001\r!!\u0002\t\u000b){\u0001\u0019A&\t\u000f\u0005Mq\u00021\u0001\u0002\u0016\u0005I\u0013N\u001c;fe:\fG.Q:l/&$\bn\u0015;biV\u001cH\u0005Z3gCVdG\u000fJ\u001a%Kb$XM\\:j_:$BAa\n\u0003,Q!\u0011q\bB\u0015\u0011\u0015)\u0005\u00031\u0001G\u0011\u001d\t\u0019\u0002\u0005a\u0001\u0003+\t!\u0004J9nCJ\\G\u0005Z3gCVdG\u000fJ\u001a%Kb$XM\\:j_:$BA!\r\u00036Q!\u0011q\bB\u001a\u0011\u0015)\u0015\u00031\u0001G\u0011\u001d\t\u0019\"\u0005a\u0001\u0003+\t!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R!\u0011q\u0014B\u001e\u0011\u001d\t\u0019B\u0005a\u0001\u0003+\t\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\t\u0005#Q\t\u000b\u0005\u0003W\u0013\u0019\u0005\u0003\u0005\u00024N\t\t\u00111\u0001G\u0011\u001d\t\u0019b\u0005a\u0001\u0003+\u0001")
public final class AskableActorRef {
    private final ActorRef actorRef;

    public static boolean equals$extension(ActorRef $this, Object x$1) {
        return AskableActorRef$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(ActorRef $this) {
        return AskableActorRef$.MODULE$.hashCode$extension($this);
    }

    public static ActorRef $qmark$default$3$extension(ActorRef $this, Object message) {
        return AskableActorRef$.MODULE$.$qmark$default$3$extension($this, message);
    }

    public static ActorRef internalAskWithStatus$default$3$extension(ActorRef $this, Object message) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$default$3$extension($this, message);
    }

    public static Future<Object> internalAsk$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.internalAsk$extension($this, message, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.$qmark$extension($this, message, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorRef $this, Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.$qmark$extension($this, message, timeout2);
    }

    @InternalApi
    public static Future<Object> internalAskWithStatus$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$extension($this, message, timeout2, sender);
    }

    public static ActorRef askWithStatus$default$3$extension(ActorRef $this, Object message) {
        return AskableActorRef$.MODULE$.askWithStatus$default$3$extension($this, message);
    }

    public static Future<Object> askWithStatus$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.askWithStatus$extension($this, message, timeout2, sender);
    }

    public static ActorRef ask$default$3$extension(ActorRef $this, Object message) {
        return AskableActorRef$.MODULE$.ask$default$3$extension($this, message);
    }

    public static Future<Object> ask$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.ask$extension($this, message, timeout2, sender);
    }

    public static Future<Object> ask$extension(ActorRef $this, Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.ask$extension($this, message, timeout2);
    }

    public ActorRef actorRef() {
        return this.actorRef;
    }

    public Future<Object> ask(Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.ask$extension(this.actorRef(), message, timeout2);
    }

    public Future<Object> ask(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.ask$extension(this.actorRef(), message, timeout2, sender);
    }

    public ActorRef ask$default$3(Object message) {
        return AskableActorRef$.MODULE$.ask$default$3$extension(this.actorRef(), message);
    }

    public Future<Object> askWithStatus(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.askWithStatus$extension(this.actorRef(), message, timeout2, sender);
    }

    public ActorRef askWithStatus$default$3(Object message) {
        return AskableActorRef$.MODULE$.askWithStatus$default$3$extension(this.actorRef(), message);
    }

    @InternalApi
    public Future<Object> internalAskWithStatus(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$extension(this.actorRef(), message, timeout2, sender);
    }

    public Future<Object> $qmark(Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.$qmark$extension(this.actorRef(), message, timeout2);
    }

    public Future<Object> $qmark(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.$qmark$extension(this.actorRef(), message, timeout2, sender);
    }

    public Future<Object> internalAsk(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorRef$.MODULE$.internalAsk$extension(this.actorRef(), message, timeout2, sender);
    }

    public ActorRef internalAskWithStatus$default$3(Object message) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$default$3$extension(this.actorRef(), message);
    }

    public ActorRef $qmark$default$3(Object message) {
        return AskableActorRef$.MODULE$.$qmark$default$3$extension(this.actorRef(), message);
    }

    public int hashCode() {
        return AskableActorRef$.MODULE$.hashCode$extension(this.actorRef());
    }

    public boolean equals(Object x$1) {
        return AskableActorRef$.MODULE$.equals$extension(this.actorRef(), x$1);
    }

    public AskableActorRef(ActorRef actorRef2) {
        this.actorRef = actorRef2;
    }
}

