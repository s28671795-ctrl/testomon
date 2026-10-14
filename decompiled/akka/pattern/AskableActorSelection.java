/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.AskableActorSelection$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Md\u0001B\r\u001b\u0005}A\u0001B\n\u0001\u0003\u0006\u0004%\ta\n\u0005\t]\u0001\u0011\t\u0011)A\u0005Q!)q\u0006\u0001C\u0001a!)A\u0007\u0001C\tk!)A\u0007\u0001C\u0001\u0013\"9!\u000bAI\u0001\n\u0003\u0019\u0006\"B0\u0001\t#\u0001\u0007\"B0\u0001\t\u0003!\u0007bB5\u0001#\u0003%\tA\u001b\u0005\u0007Y\u0002!\tAG7\t\u000fE\u0004\u0011\u0011!C!e\"9a\u000fAA\u0001\n\u0003:xaB?\u001b\u0003\u0003E\tA \u0004\b3i\t\t\u0011#\u0001\u0000\u0011\u0019yc\u0002\"\u0001\u0002\b!9\u0011\u0011\u0002\b\u0005\u0006\u0005-\u0001bBA\u0005\u001d\u0011\u0015\u0011q\u0003\u0005\n\u0003Kq\u0011\u0013!C\u0003\u0003OAq!a\f\u000f\t\u000b\t\t\u0004C\u0004\u000209!)!!\u0010\t\u000f\u0005-c\u0002\"\u0002\u0002N!I\u0011\u0011\f\b\u0012\u0002\u0013\u0015\u00111\f\u0005\n\u0003Gr\u0011\u0011!C\u0003\u0003KB\u0011\"!\u001b\u000f\u0003\u0003%)!a\u001b\u0003+\u0005\u001b8.\u00192mK\u0006\u001bGo\u001c:TK2,7\r^5p]*\u00111\u0004H\u0001\ba\u0006$H/\u001a:o\u0015\u0005i\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001AA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t1\u0011I\\=WC2\f\u0001\"Y2u_J\u001cV\r\\\u000b\u0002QA\u0011\u0011\u0006L\u0007\u0002U)\u00111\u0006H\u0001\u0006C\u000e$xN]\u0005\u0003[)\u0012a\"Q2u_J\u001cV\r\\3di&|g.A\u0005bGR|'oU3mA\u00051A(\u001b8jiz\"\"!M\u001a\u0011\u0005I\u0002Q\"\u0001\u000e\t\u000b\u0019\u001a\u0001\u0019\u0001\u0015\u0002\u0007\u0005\u001c8\u000eF\u00027\u007f\u0005\u00032a\u000e\u001e=\u001b\u0005A$BA\u001d#\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003wa\u0012aAR;ukJ,\u0007CA\u0011>\u0013\tq$EA\u0002B]fDQ\u0001\u0011\u0003A\u0002q\nq!\\3tg\u0006<W\rC\u0003C\t\u0001\u00071)A\u0004uS6,w.\u001e;\u0011\u0005\u0011;U\"A#\u000b\u0005\u0019c\u0012\u0001B;uS2L!\u0001S#\u0003\u000fQKW.Z8viR\u0011!*\u0015\u000b\u0004m-c\u0005\"\u0002\"\u0006\u0001\b\u0019\u0005bB'\u0006!\u0003\u0005\u001dAT\u0001\u0007g\u0016tG-\u001a:\u0011\u0005%z\u0015B\u0001)+\u0005!\t5\r^8s%\u00164\u0007\"\u0002!\u0006\u0001\u0004a\u0014!D1tW\u0012\"WMZ1vYR$3\u0007\u0006\u0002U=*\u0012a*V\u0016\u0002-B\u0011q\u000bX\u0007\u00021*\u0011\u0011LW\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u0017\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002^1\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\t\u000b\u00013\u0001\u0019\u0001\u001f\u0002\r\u0011\nX.\u0019:l)\t\t7\r\u0006\u00027E\")!i\u0002a\u0002\u0007\")\u0001i\u0002a\u0001yQ\u0011Q\r\u001b\u000b\u0004m\u0019<\u0007\"\u0002\"\t\u0001\b\u0019\u0005bB'\t!\u0003\u0005\u001dA\u0014\u0005\u0006\u0001\"\u0001\r\u0001P\u0001\u0011IEl\u0017M]6%I\u00164\u0017-\u001e7uIM\"\"\u0001V6\t\u000b\u0001K\u0001\u0019\u0001\u001f\u0002\u0017%tG/\u001a:oC2\f5o\u001b\u000b\u0005m9|\u0007\u000fC\u0003A\u0015\u0001\u0007A\bC\u0003C\u0015\u0001\u00071\tC\u0003N\u0015\u0001\u0007a*\u0001\u0005iCND7i\u001c3f)\u0005\u0019\bCA\u0011u\u0013\t)(EA\u0002J]R\fa!Z9vC2\u001cHC\u0001=|!\t\t\u00130\u0003\u0002{E\t9!i\\8mK\u0006t\u0007b\u0002?\r\u0003\u0003\u0005\r\u0001P\u0001\u0004q\u0012\n\u0014!F!tW\u0006\u0014G.Z!di>\u00148+\u001a7fGRLwN\u001c\t\u0003e9\u00192ADA\u0001!\r\t\u00131A\u0005\u0004\u0003\u000b\u0011#AB!osJ+g\rF\u0001\u007f\u00035\t7o\u001b\u0013fqR,gn]5p]R!\u0011QBA\n)\u00151\u0014qBA\t\u0011\u0015\u0001\u0005\u00031\u0001=\u0011\u0015\u0011\u0005\u00031\u0001D\u0011\u0019\t)\u0002\u0005a\u0001c\u0005)A\u0005\u001e5jgR!\u0011\u0011DA\u0012)\u0011\tY\"!\t\u0015\u000bY\ni\"a\b\t\u000b\t\u000b\u00029A\"\t\u000f5\u000b\u0002\u0013!a\u0002\u001d\")\u0001)\u0005a\u0001y!1\u0011QC\tA\u0002E\nq#Y:lI\u0011,g-Y;mi\u0012\u001aD%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005%\u0012Q\u0006\u000b\u0004)\u0006-\u0002\"\u0002!\u0013\u0001\u0004a\u0004BBA\u000b%\u0001\u0007\u0011'\u0001\t%c6\f'o\u001b\u0013fqR,gn]5p]R!\u00111GA\u001e)\u0011\t)$!\u000f\u0015\u0007Y\n9\u0004C\u0003C'\u0001\u000f1\tC\u0003A'\u0001\u0007A\b\u0003\u0004\u0002\u0016M\u0001\r!\r\u000b\u0005\u0003\u007f\tI\u0005\u0006\u0003\u0002B\u0005\u001dC#\u0002\u001c\u0002D\u0005\u0015\u0003\"\u0002\"\u0015\u0001\b\u0019\u0005bB'\u0015!\u0003\u0005\u001dA\u0014\u0005\u0006\u0001R\u0001\r\u0001\u0010\u0005\u0007\u0003+!\u0002\u0019A\u0019\u0002+%tG/\u001a:oC2\f5o\u001b\u0013fqR,gn]5p]R!\u0011qJA,)\u001d1\u0014\u0011KA*\u0003+BQ\u0001Q\u000bA\u0002qBQAQ\u000bA\u0002\rCQ!T\u000bA\u00029Ca!!\u0006\u0016\u0001\u0004\t\u0014A\u0007\u0013r[\u0006\u00148\u000e\n3fM\u0006,H\u000e\u001e\u00134I\u0015DH/\u001a8tS>tG\u0003BA/\u0003C\"2\u0001VA0\u0011\u0015\u0001e\u00031\u0001=\u0011\u0019\t)B\u0006a\u0001c\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\r\u0011\u0018q\r\u0005\u0007\u0003+9\u0002\u0019A\u0019\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tG\u0003BA7\u0003c\"2\u0001_A8\u0011\u001da\b$!AA\u0002qBa!!\u0006\u0019\u0001\u0004\t\u0004")
public final class AskableActorSelection {
    private final ActorSelection actorSel;

    public static boolean equals$extension(ActorSelection $this, Object x$1) {
        return AskableActorSelection$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(ActorSelection $this) {
        return AskableActorSelection$.MODULE$.hashCode$extension($this);
    }

    public static ActorRef $qmark$default$3$extension(ActorSelection $this, Object message) {
        return AskableActorSelection$.MODULE$.$qmark$default$3$extension($this, message);
    }

    public static Future<Object> internalAsk$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.internalAsk$extension($this, message, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.$qmark$extension($this, message, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorSelection $this, Object message, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.$qmark$extension($this, message, timeout2);
    }

    public static ActorRef ask$default$3$extension(ActorSelection $this, Object message) {
        return AskableActorSelection$.MODULE$.ask$default$3$extension($this, message);
    }

    public static Future<Object> ask$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.ask$extension($this, message, timeout2, sender);
    }

    public static Future<Object> ask$extension(ActorSelection $this, Object message, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.ask$extension($this, message, timeout2);
    }

    public ActorSelection actorSel() {
        return this.actorSel;
    }

    public Future<Object> ask(Object message, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.ask$extension(this.actorSel(), message, timeout2);
    }

    public Future<Object> ask(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.ask$extension(this.actorSel(), message, timeout2, sender);
    }

    public ActorRef ask$default$3(Object message) {
        return AskableActorSelection$.MODULE$.ask$default$3$extension(this.actorSel(), message);
    }

    public Future<Object> $qmark(Object message, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.$qmark$extension(this.actorSel(), message, timeout2);
    }

    public Future<Object> $qmark(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.$qmark$extension(this.actorSel(), message, timeout2, sender);
    }

    public Future<Object> internalAsk(Object message, Timeout timeout2, ActorRef sender) {
        return AskableActorSelection$.MODULE$.internalAsk$extension(this.actorSel(), message, timeout2, sender);
    }

    public ActorRef $qmark$default$3(Object message) {
        return AskableActorSelection$.MODULE$.$qmark$default$3$extension(this.actorSel(), message);
    }

    public int hashCode() {
        return AskableActorSelection$.MODULE$.hashCode$extension(this.actorSel());
    }

    public boolean equals(Object x$1) {
        return AskableActorSelection$.MODULE$.equals$extension(this.actorSel(), x$1);
    }

    public AskableActorSelection(ActorSelection actorSel) {
        this.actorSel = actorSel;
    }
}

