/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.IgnoreActorRef$;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.SerializedIgnore$;
import akka.annotation.InternalApi;
import akka.dispatch.sysmsg.SystemMessage;
import java.io.ObjectStreamException;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a4Qa\u0005\u000b\u0003-aA\u0001\u0002\t\u0001\u0003\u0006\u0004%\tE\t\u0005\tM\u0001\u0011\t\u0011)A\u0005G!)q\u0005\u0001C\u0001Q!91\u0006\u0001b\u0001\n\u0003b\u0003B\u0002\u0019\u0001A\u0003%Q\u0006C\u00032\u0001\u0011E#g\u0002\u0004N)!\u0005aC\u0014\u0004\u0007'QA\tAF(\t\u000b\u001dBA\u0011A*\t\u000fQC!\u0019!C\u0005+\"1A\f\u0003Q\u0001\nYCqa\u000b\u0005C\u0002\u0013\u0005A\u0006\u0003\u00041\u0011\u0001\u0006I!\f\u0005\b;\"\u0011\r\u0011\"\u0003V\u0011\u0019q\u0006\u0002)A\u0005-\")q\f\u0003C\u0001A\")q\f\u0003C\u0001a\"9\u0011\u0007CA\u0001\n\u0013\u0011(AD%h]>\u0014X-Q2u_J\u0014VM\u001a\u0006\u0003+Y\tQ!Y2u_JT\u0011aF\u0001\u0005C.\\\u0017mE\u0002\u00013u\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0007C\u0001\u000e\u001f\u0013\tyBCA\bNS:LW.\u00197BGR|'OU3g\u0003!\u0001(o\u001c<jI\u0016\u00148\u0001A\u000b\u0002GA\u0011!\u0004J\u0005\u0003KQ\u0011\u0001#Q2u_J\u0014VM\u001a)s_ZLG-\u001a:\u0002\u0013A\u0014xN^5eKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002*UA\u0011!\u0004\u0001\u0005\u0006A\r\u0001\raI\u0001\u0005a\u0006$\b.F\u0001.!\tQb&\u0003\u00020)\tI\u0011i\u0019;peB\u000bG\u000f[\u0001\u0006a\u0006$\b\u000eI\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002gA\u0011AgN\u0007\u0002k)\ta'A\u0003tG\u0006d\u0017-\u0003\u00029k\t1\u0011I\\=SK\u001aD3A\u0002\u001eF!\r!4(P\u0005\u0003yU\u0012a\u0001\u001e5s_^\u001c\bC\u0001 D\u001b\u0005y$B\u0001!B\u0003\tIwNC\u0001C\u0003\u0011Q\u0017M^1\n\u0005\u0011{$!F(cU\u0016\u001cGo\u0015;sK\u0006lW\t_2faRLwN\\\u0012\u0002{!\u0012\u0001a\u0012\t\u0003\u0011.k\u0011!\u0013\u0006\u0003\u0015Z\t!\"\u00198o_R\fG/[8o\u0013\ta\u0015JA\u0006J]R,'O\\1m\u0003BL\u0017AD%h]>\u0014X-Q2u_J\u0014VM\u001a\t\u00035!\u00192\u0001C\u001aQ!\tq\u0014+\u0003\u0002S\u007f\ta1+\u001a:jC2L'0\u00192mKR\ta*\u0001\bgC.,7+_:uK6t\u0015-\\3\u0016\u0003Y\u0003\"a\u0016.\u000e\u0003aS!!W!\u0002\t1\fgnZ\u0005\u00037b\u0013aa\u0015;sS:<\u0017a\u00044bW\u0016\u001c\u0016p\u001d;f[:\u000bW.\u001a\u0011\u0002\u0015A\fG\u000f[*ue&tw-A\u0006qCRD7\u000b\u001e:j]\u001e\u0004\u0013aD5t\u0013\u001etwN]3SK\u001a\u0004\u0016\r\u001e5\u0015\u0005\u0005$\u0007C\u0001\u001bc\u0013\t\u0019WGA\u0004C_>dW-\u00198\t\u000b\u0015\u0004\u0002\u0019\u00014\u0002\u0013=$\b.\u001a:QCRD\u0007CA4o\u001d\tAG\u000e\u0005\u0002jk5\t!N\u0003\u0002lC\u00051AH]8pizJ!!\\\u001b\u0002\rA\u0013X\rZ3g\u0013\tYvN\u0003\u0002nkQ\u0011\u0011-\u001d\u0005\u0006KF\u0001\r!\f\u000b\u0002gB\u0011q\u000b^\u0005\u0003kb\u0013aa\u00142kK\u000e$\bF\u0001\u0005HQ\t9q\t")
@InternalApi
public final class IgnoreActorRef
extends InternalActorRef
implements MinimalActorRef {
    private final ActorRefProvider provider;
    private final ActorPath path;

    public static boolean isIgnoreRefPath(ActorPath otherPath) {
        return IgnoreActorRef$.MODULE$.isIgnoreRefPath(otherPath);
    }

    public static boolean isIgnoreRefPath(String otherPath) {
        return IgnoreActorRef$.MODULE$.isIgnoreRefPath(otherPath);
    }

    @Override
    public InternalActorRef getParent() {
        return MinimalActorRef.getParent$(this);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        return MinimalActorRef.getChild$(this, names);
    }

    @Override
    public void start() {
        MinimalActorRef.start$(this);
    }

    @Override
    public void suspend() {
        MinimalActorRef.suspend$(this);
    }

    @Override
    public void resume(Throwable causedByFailure) {
        MinimalActorRef.resume$(this, causedByFailure);
    }

    @Override
    public void stop() {
        MinimalActorRef.stop$(this);
    }

    @Override
    public boolean isTerminated() {
        return MinimalActorRef.isTerminated$(this);
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        MinimalActorRef.$bang$(this, message, sender);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return MinimalActorRef.$bang$default$2$(this, message);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        MinimalActorRef.sendSystemMessage$(this, message);
    }

    @Override
    public void restart(Throwable cause) {
        MinimalActorRef.restart$(this, cause);
    }

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public ActorRefProvider provider() {
        return this.provider;
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return SerializedIgnore$.MODULE$;
    }

    public IgnoreActorRef(ActorRefProvider provider) {
        this.provider = provider;
        LocalRef.$init$(this);
        MinimalActorRef.$init$(this);
        this.path = IgnoreActorRef$.MODULE$.path();
    }
}

