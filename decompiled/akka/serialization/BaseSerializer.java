/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.BaseSerializer$;
import akka.serialization.Serializer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d4q!\u0004\b\u0011\u0002\u0007\u00051\u0003C\u0003\u001f\u0001\u0011\u0005q\u0004C\u0003$\u0001\u0019\u0005A\u0005C\u0004,\u0001\t\u0007IQ\u0001\u0017\t\u000fA\u0002!\u0019!C!c!1Q\u0007\u0001C\u0001!E:Q!\u0010\b\t\u0002y2Q!\u0004\b\t\u0002}BQ\u0001Q\u0004\u0005\u0002\u0005CqaK\u0004C\u0002\u0013\u0015A\u0006\u0003\u0004C\u000f\u0001\u0006i!\f\u0005\u0007k\u001d!\t\u0001E\"\t\rU:A\u0011\u0001\t`\u00059\u0011\u0015m]3TKJL\u0017\r\\5{KJT!a\u0004\t\u0002\u001bM,'/[1mSj\fG/[8o\u0015\u0005\t\u0012\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001)i\u0001\"!\u0006\r\u000e\u0003YQ\u0011aF\u0001\u0006g\u000e\fG.Y\u0005\u00033Y\u0011a!\u00118z%\u00164\u0007CA\u000e\u001d\u001b\u0005q\u0011BA\u000f\u000f\u0005)\u0019VM]5bY&TXM]\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\u0001\u0002\"!F\u0011\n\u0005\t2\"\u0001B+oSR\faa]=ti\u0016lW#A\u0013\u0011\u0005\u0019JS\"A\u0014\u000b\u0005!\u0002\u0012!B1di>\u0014\u0018B\u0001\u0016(\u0005M)\u0005\u0010^3oI\u0016$\u0017i\u0019;peNK8\u000f^3n\u0003a\u0019VM]5bY&T\u0018\r^5p]&#WM\u001c;jM&,'o]\u000b\u0002[=\ta&I\u00010\u0003\u0011\n7n[1/C\u000e$xN\u001d\u0018tKJL\u0017\r\\5{CRLwN\\\u0017jI\u0016tG/\u001b4jKJ\u001c\u0018AC5eK:$\u0018NZ5feV\t!\u0007\u0005\u0002\u0016g%\u0011AG\u0006\u0002\u0004\u0013:$\u0018\u0001F5eK:$\u0018NZ5fe\u001a\u0013x.\\\"p]\u001aLw\r\u000b\u0002\u0006oA\u0011\u0001hO\u0007\u0002s)\u0011!\bE\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u001f:\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u001d\t\u000b7/Z*fe&\fG.\u001b>feB\u00111dB\n\u0003\u000fQ\ta\u0001P5oSRtD#\u0001 \u00023M+'/[1mSj\fG/[8o\u0013\u0012,g\u000e^5gS\u0016\u00148\u000f\t\u000b\u0004e\u0011k\u0006\"B#\f\u0001\u00041\u0015!B2mCjT\bGA$U!\rAuJ\u0015\b\u0003\u00136\u0003\"A\u0013\f\u000e\u0003-S!\u0001\u0014\n\u0002\rq\u0012xn\u001c;?\u0013\tqe#\u0001\u0004Qe\u0016$WMZ\u0005\u0003!F\u0013Qa\u00117bgNT!A\u0014\f\u0011\u0005M#F\u0002\u0001\u0003\n+\u0012\u000b\t\u0011!A\u0003\u0002Y\u00131a\u0018\u00135#\t9&\f\u0005\u0002\u00161&\u0011\u0011L\u0006\u0002\b\u001d>$\b.\u001b8h!\t)2,\u0003\u0002]-\t\u0019\u0011I\\=\t\u000b\rZ\u0001\u0019A\u0013)\u0005-9Dc\u0001\u001aaK\")\u0011\r\u0004a\u0001E\u0006Y!-\u001b8eS:<g*Y7f!\tA5-\u0003\u0002e#\n11\u000b\u001e:j]\u001eDQa\t\u0007A\u0002\u0015B#\u0001D\u001c")
public interface BaseSerializer
extends Serializer {
    public void akka$serialization$BaseSerializer$_setter_$identifier_$eq(int var1);

    public ExtendedActorSystem system();

    public static /* synthetic */ String SerializationIdentifiers$(BaseSerializer $this) {
        return $this.SerializationIdentifiers();
    }

    default public String SerializationIdentifiers() {
        return "akka.actor.serialization-identifiers";
    }

    @Override
    public int identifier();

    public static /* synthetic */ int identifierFromConfig$(BaseSerializer $this) {
        return $this.identifierFromConfig();
    }

    @InternalApi
    default public int identifierFromConfig() {
        return BaseSerializer$.MODULE$.identifierFromConfig(this.getClass(), this.system());
    }

    public static void $init$(BaseSerializer $this) {
        $this.akka$serialization$BaseSerializer$_setter_$identifier_$eq($this.identifierFromConfig());
    }
}

