/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorInitializationException;
import akka.actor.ActorInitializationException$;
import akka.actor.Cell;
import akka.actor.DeadLetter;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.dungeon.AbstractActorCell;
import akka.actor.dungeon.SerializationCheckFailedException$;
import akka.annotation.InternalApi;
import akka.dispatch.Envelope;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.UnboundedMailbox;
import akka.dispatch.sysmsg.Create;
import akka.dispatch.sysmsg.Recreate;
import akka.dispatch.sysmsg.Resume;
import akka.dispatch.sysmsg.Supervise;
import akka.dispatch.sysmsg.Suspend;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Terminate;
import akka.event.Logging;
import akka.serialization.DisabledJavaSerializer;
import akka.serialization.Serialization;
import akka.serialization.Serialization$;
import akka.serialization.SerializationExtension$;
import akka.serialization.Serializer;
import akka.serialization.Serializers$;
import akka.util.Unsafe;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NoStackTrace;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005dA\u0003\f\u0018!\u0003\r\taG\u000f\u0002N!)A\u0005\u0001C\u0001M!I!\u0006\u0001a\u0001\u0002\u0004%Ia\u000b\u0005\nm\u0001\u0001\r\u00111A\u0005\n]BQA\u000f\u0001\u0005\n-BQa\u000f\u0001\u0005\u0006-BQ\u0001\u0011\u0001\u0005\u0006\u0005CQa\u0013\u0001\u0005\u00061CQ\u0001\u0015\u0001\u0005\u0006ECQ!\u0016\u0001\u0005\u00061CQA\u0016\u0001\u0005\u0006]CQ\u0001\u0019\u0001\u0005\u0006\u0005DQ\u0001\u001d\u0001\u0005\u0002EDQA\u001d\u0001\u0005\nMDa!a\u0002\u0001\t\u000b1\u0003bBA\u0005\u0001\u0011\u0015\u00111\u0002\u0005\b\u0003#\u0001AQAA\n\u0011\u0019\tI\u0002\u0001C\u0003M!9\u00111\u0004\u0001\u0005\u0002\u0005u\u0001bBA\u0015\u0001\u0011%\u00111\u0006\u0005\b\u0003c\u0001A\u0011BA\u001a\u0011\u001d\tI\u0004\u0001C!\u0003w\u0011\u0001\u0002R5ta\u0006$8\r\u001b\u0006\u00031e\tq\u0001Z;oO\u0016|gN\u0003\u0002\u001b7\u0005)\u0011m\u0019;pe*\tA$\u0001\u0003bW.\f7C\u0001\u0001\u001f!\ty\"%D\u0001!\u0015\u0005\t\u0013!B:dC2\f\u0017BA\u0012!\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\u001a\u0001\u0001F\u0001(!\ty\u0002&\u0003\u0002*A\t!QK\\5u\u0003myV.Y5mE>DHi\u001c(pi\u000e\u000bG\u000e\\'f\t&\u0014Xm\u0019;msV\tA\u0006\u0005\u0002.a5\taF\u0003\u000207\u0005AA-[:qCR\u001c\u0007.\u0003\u00022]\t9Q*Y5mE>D\bF\u0001\u00024!\tyB'\u0003\u00026A\tAao\u001c7bi&dW-A\u0010`[\u0006LGNY8y\t>tu\u000e^\"bY2lU\rR5sK\u000e$H._0%KF$\"a\n\u001d\t\u000fe\u001a\u0011\u0011!a\u0001Y\u0005\u0019\u0001\u0010J\u0019\u00029}\u0003(/\u001a<f]R\u0004&/\u001b<bi\u0016,f.^:fI\u0016\u0013\u0018m];sK\u00069Q.Y5mE>D\bFA\u0003>!\tyb(\u0003\u0002@A\t1\u0011N\u001c7j]\u0016\f1b]<ba6\u000b\u0017\u000e\u001c2pqR\u0011AF\u0011\u0005\u0006\u0007\u001a\u0001\r\u0001L\u0001\u000b]\u0016<X*Y5mE>D\bF\u0001\u0004F!\t1\u0015*D\u0001H\u0015\tA\u0005%\u0001\u0006b]:|G/\u0019;j_:L!AS$\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006Y\u0001.Y:NKN\u001c\u0018mZ3t+\u0005i\u0005CA\u0010O\u0013\ty\u0005EA\u0004C_>dW-\u00198\u0002!9,XNY3s\u001f\u001alUm]:bO\u0016\u001cX#\u0001*\u0011\u0005}\u0019\u0016B\u0001+!\u0005\rIe\u000e^\u0001\rSN$VM]7j]\u0006$X\rZ\u0001\u0005S:LG\u000fF\u0002Y3nk\u0011\u0001\u0001\u0005\u00065*\u0001\r!T\u0001\u000eg\u0016tGmU;qKJ4\u0018n]3\t\u000bqS\u0001\u0019A/\u0002\u00175\f\u0017\u000e\u001c2pqRK\b/\u001a\t\u0003[yK!a\u0018\u0018\u0003\u00175\u000b\u0017\u000e\u001c2pqRK\b/Z\u0001\u0010S:LGoV5uQ\u001a\u000b\u0017\u000e\\;sKR\u0011\u0001L\u0019\u0005\u0006G.\u0001\r\u0001Z\u0001\bM\u0006LG.\u001e:f!\t)WN\u0004\u0002gW:\u0011qM[\u0007\u0002Q*\u0011\u0011.J\u0001\u0007yI|w\u000e\u001e \n\u0003\u0005J!\u0001\u001c\u0011\u0002\u000fA\f7m[1hK&\u0011an\u001c\u0002\n)\"\u0014xn^1cY\u0016T!\u0001\u001c\u0011\u0002\u000bM$\u0018M\u001d;\u0015\u0003a\u000bq\u0002[1oI2,W\t_2faRLwN\\\u000b\u0002iB!Q/!\u0001(\u001d\t1XP\u0004\u0002xu:\u0011a\r_\u0005\u0003s\u0002\nA!\u001e;jY&\u00111\u0010`\u0001\bG>tGO]8m\u0015\tI\b%\u0003\u0002\u007f\u007f\u0006IQ\t_2faRLwN\u001c\u0006\u0003wrLA!a\u0001\u0002\u0006\t91)\u0019;dQ\u0016\u0014(B\u0001@\u0000\u0003\u001d\u0019Xo\u001d9f]\u0012\faA]3tk6,GcA\u0014\u0002\u000e!1\u0011qB\bA\u0002\u0011\fqbY1vg\u0016$')\u001f$bS2,(/Z\u0001\be\u0016\u001cH/\u0019:u)\r9\u0013Q\u0003\u0005\u0007\u0003/\u0001\u0002\u0019\u00013\u0002\u000b\r\fWo]3\u0002\tM$x\u000e]\u0001\fg\u0016tG-T3tg\u0006<W\rF\u0002(\u0003?Aq!!\t\u0013\u0001\u0004\t\u0019#A\u0002ng\u001e\u00042!LA\u0013\u0013\r\t9C\f\u0002\t\u000b:4X\r\\8qK\u000692/\u001a:jC2L'0Z!oI\u0012+7/\u001a:jC2L'0\u001a\u000b\u0005\u0003G\ti\u0003C\u0004\u00020M\u0001\r!a\t\u0002\u0011\u0015tg/\u001a7pa\u0016\fad]3sS\u0006d\u0017N_3B]\u0012$Um]3sS\u0006d\u0017N_3QCfdw.\u00193\u0015\u0007y\t)\u0004\u0003\u0004\u00028Q\u0001\rAH\u0001\u0004_\nT\u0017!E:f]\u0012\u001c\u0016p\u001d;f[6+7o]1hKR\u0019q%!\u0010\t\u000f\u0005}R\u00031\u0001\u0002B\u00059Q.Z:tC\u001e,\u0007\u0003BA\"\u0003\u0013j!!!\u0012\u000b\u0007\u0005\u001dc&\u0001\u0004tsNl7oZ\u0005\u0005\u0003\u0017\n)EA\u0007TsN$X-\\'fgN\fw-\u001a\t\u0005\u0003\u001f\n\t&D\u0001\u001a\u0013\r\t\u0019&\u0007\u0002\n\u0003\u000e$xN]\"fY2D3\u0001AA,!\u0011\tI&!\u0018\u000e\u0005\u0005m#B\u0001%\u001c\u0013\u0011\ty&a\u0017\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public interface Dispatch {
    public Mailbox akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly();

    public void akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly_$eq(Mailbox var1);

    private Mailbox _preventPrivateUnusedErasure() {
        return this.akka$actor$dungeon$Dispatch$$_mailboxDoNotCallMeDirectly();
    }

    public static /* synthetic */ Mailbox mailbox$(Dispatch $this) {
        return $this.mailbox();
    }

    default public Mailbox mailbox() {
        return (Mailbox)Unsafe.instance.getObjectVolatile(this, AbstractActorCell.mailboxOffset);
    }

    public static /* synthetic */ Mailbox swapMailbox$(Dispatch $this, Mailbox newMailbox) {
        return $this.swapMailbox(newMailbox);
    }

    default public Mailbox swapMailbox(Mailbox newMailbox) {
        Mailbox oldMailbox;
        while (!Unsafe.instance.compareAndSwapObject(this_, AbstractActorCell.mailboxOffset, oldMailbox = this_.mailbox(), newMailbox)) {
            Dispatch this_ = (ActorCell)this_;
        }
        return oldMailbox;
    }

    public static /* synthetic */ boolean hasMessages$(Dispatch $this) {
        return $this.hasMessages();
    }

    default public boolean hasMessages() {
        return this.mailbox().hasMessages();
    }

    public static /* synthetic */ int numberOfMessages$(Dispatch $this) {
        return $this.numberOfMessages();
    }

    default public int numberOfMessages() {
        return this.mailbox().numberOfMessages();
    }

    public static /* synthetic */ boolean isTerminated$(Dispatch $this) {
        return $this.isTerminated();
    }

    default public boolean isTerminated() {
        return this.mailbox().isClosed();
    }

    public static /* synthetic */ ActorCell init$(Dispatch $this, boolean sendSupervise, MailboxType mailboxType) {
        return $this.init(sendSupervise, mailboxType);
    }

    default public ActorCell init(boolean sendSupervise, MailboxType mailboxType) {
        block4: {
            Create create;
            Mailbox mbox = ((ActorCell)this).dispatcher().createMailbox((Cell)((Object)this), mailboxType);
            Class<? extends Actor> actorClass = ((ActorCell)this).props().actorClass();
            MailboxType mailboxType2 = mailboxType;
            if (mailboxType2 instanceof ProducesMessageQueue && ((ActorCell)this).system().mailboxes().hasRequiredType(actorClass)) {
                Class<?> req = ((ActorCell)this).system().mailboxes().getRequiredType(actorClass);
                if (req.isInstance(mbox.messageQueue())) {
                    create = new Create(None$.MODULE$);
                } else {
                    String gotType = mbox.messageQueue() == null ? "null" : mbox.messageQueue().getClass().getName();
                    create = new Create(new Some<ActorInitializationException>(ActorInitializationException$.MODULE$.apply(((ActorCell)this).self(), new StringBuilder(40).append("Actor [").append(((ActorCell)this).self()).append("] requires mailbox type [").append(req).append("] got [").append(gotType).append("]").toString(), ActorInitializationException$.MODULE$.apply$default$3())));
                }
            } else {
                create = new Create(None$.MODULE$);
            }
            Create createMessage = create;
            this.swapMailbox(mbox);
            this.mailbox().setActor((ActorCell)this);
            this.mailbox().systemEnqueue(((ActorCell)this).self(), createMessage);
            if (!sendSupervise) break block4;
            ((ActorCell)this).parent().sendSystemMessage(new Supervise(((ActorCell)this).self(), false));
        }
        return (ActorCell)this;
    }

    public static /* synthetic */ ActorCell initWithFailure$(Dispatch $this, Throwable failure) {
        return $this.initWithFailure(failure);
    }

    default public ActorCell initWithFailure(Throwable failure) {
        Mailbox mbox = ((ActorCell)this).dispatcher().createMailbox((Cell)((Object)this), new UnboundedMailbox());
        this.swapMailbox(mbox);
        this.mailbox().setActor((ActorCell)this);
        Create createMessage = new Create(new Some<ActorInitializationException>(ActorInitializationException$.MODULE$.apply(((ActorCell)this).self(), "failure while creating ActorCell", failure)));
        this.mailbox().systemEnqueue(((ActorCell)this).self(), createMessage);
        return (ActorCell)this;
    }

    public static /* synthetic */ ActorCell start$(Dispatch $this) {
        return $this.start();
    }

    default public ActorCell start() {
        ((ActorCell)this).dispatcher().attach((ActorCell)this);
        return (ActorCell)this;
    }

    private PartialFunction<Throwable, BoxedUnit> handleException() {
        return new Serializable((ActorCell)this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ ActorCell $outer;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                Option<Throwable> option;
                A1 A1 = x1;
                if (A1 instanceof InterruptedException) {
                    InterruptedException interruptedException = (InterruptedException)A1;
                    this.$outer.system().eventStream().publish(new Logging.Error(interruptedException, this.$outer.self().path().toString(), this.$outer.clazz(this.$outer.actor()), "interrupted during message send"));
                    Thread.currentThread().interrupt();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                    String string;
                    Throwable e = option.get();
                    Throwable throwable = e;
                    if (throwable instanceof NoStackTrace) {
                        NoStackTrace noStackTrace = (NoStackTrace)((Object)throwable);
                        string = new StringBuilder(42).append("swallowing exception during message send: ").append(((Throwable)((Object)noStackTrace)).getMessage()).toString();
                    } else {
                        string = "swallowing exception during message send";
                    }
                    String message = string;
                    this.$outer.system().eventStream().publish(new Logging.Error(e, this.$outer.self().path().toString(), this.$outer.clazz(this.$outer.actor()), message));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Option<Throwable> option;
                Throwable throwable = x1;
                if (throwable instanceof InterruptedException) {
                    return true;
                }
                return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static /* synthetic */ void suspend$(Dispatch $this) {
        $this.suspend();
    }

    default public void suspend() {
        try {
            ((ActorCell)this).dispatcher().systemDispatch((ActorCell)this, new Suspend());
        }
        catch (Throwable x$2) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$1 = this.handleException();
            if (catchExpr$1.isDefinedAt(x$2)) {
                catchExpr$1.apply(x$2);
            }
            throw x$2;
        }
    }

    public static /* synthetic */ void resume$(Dispatch $this, Throwable causedByFailure) {
        $this.resume(causedByFailure);
    }

    default public void resume(Throwable causedByFailure) {
        try {
            ((ActorCell)this).dispatcher().systemDispatch((ActorCell)this, new Resume(causedByFailure));
        }
        catch (Throwable x$3) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$2 = this.handleException();
            if (catchExpr$2.isDefinedAt(x$3)) {
                catchExpr$2.apply(x$3);
            }
            throw x$3;
        }
    }

    public static /* synthetic */ void restart$(Dispatch $this, Throwable cause) {
        $this.restart(cause);
    }

    default public void restart(Throwable cause) {
        try {
            ((ActorCell)this).dispatcher().systemDispatch((ActorCell)this, new Recreate(cause));
        }
        catch (Throwable x$4) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$3 = this.handleException();
            if (catchExpr$3.isDefinedAt(x$4)) {
                catchExpr$3.apply(x$4);
            }
            throw x$4;
        }
    }

    public static /* synthetic */ void stop$(Dispatch $this) {
        $this.stop();
    }

    default public void stop() {
        try {
            ((ActorCell)this).dispatcher().systemDispatch((ActorCell)this, new Terminate());
        }
        catch (Throwable x$5) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$4 = this.handleException();
            if (catchExpr$4.isDefinedAt(x$5)) {
                catchExpr$4.apply(x$5);
            }
            throw x$5;
        }
    }

    public static /* synthetic */ void sendMessage$(Dispatch $this, Envelope msg) {
        $this.sendMessage(msg);
    }

    default public void sendMessage(Envelope msg) {
        try {
            Envelope msgToDispatch = ((ActorCell)this).system().settings().SerializeAllMessages() ? this.serializeAndDeserialize(msg) : msg;
            ((ActorCell)this).dispatcher().dispatch((ActorCell)this, msgToDispatch);
        }
        catch (Throwable x$6) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$5 = this.handleException();
            if (catchExpr$5.isDefinedAt(x$6)) {
                catchExpr$5.apply(x$6);
            }
            throw x$6;
        }
    }

    private Envelope serializeAndDeserialize(Envelope envelope) {
        Object object;
        Object unwrappedMessage;
        Object object2;
        Object object3 = envelope.message();
        if (object3 instanceof DeadLetter) {
            Object wrapped;
            DeadLetter deadLetter = (DeadLetter)object3;
            object2 = wrapped = deadLetter.message();
        } else {
            object2 = object3;
        }
        Object object4 = unwrappedMessage = object2;
        if (object4 instanceof NoSerializationVerificationNeeded) {
            return envelope;
        }
        String string = object4.getClass().getName();
        if (((ActorCell)this).system().settings().NoSerializationVerificationNeededClassPrefix().exists((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(string.startsWith(x$1)))) {
            return envelope;
        }
        try {
            object = this.serializeAndDeserializePayload(object4);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                throw SerializationCheckFailedException$.MODULE$.apply(object4, e);
            }
            throw throwable;
        }
        Object deserializedMsg = object;
        Object object5 = envelope.message();
        if (object5 instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter)object5;
            return envelope.copy(deadLetter.copy(deserializedMsg, deadLetter.copy$default$2(), deadLetter.copy$default$3()), envelope.copy$default$2());
        }
        return envelope.copy(deserializedMsg, envelope.copy$default$2());
    }

    private Object serializeAndDeserializePayload(Object obj) {
        Object object;
        Serialization s2 = (Serialization)SerializationExtension$.MODULE$.apply(((ActorCell)this).system());
        Serializer serializer = s2.findSerializerFor(obj);
        if (serializer instanceof DisabledJavaSerializer && !s2.shouldWarnAboutJavaSerializer(obj.getClass(), serializer)) {
            return obj;
        }
        Serialization.Information oldInfo = Serialization$.MODULE$.currentTransportInformation().value();
        try {
            if (oldInfo == null) {
                Serialization$.MODULE$.currentTransportInformation().value_$eq(((ActorCell)this).system().provider().serializationInformation());
            }
            byte[] bytes = serializer.toBinary(obj);
            String ms = Serializers$.MODULE$.manifestFor(serializer, obj);
            object = s2.deserialize(bytes, serializer.identifier(), ms).get();
        }
        finally {
            Serialization$.MODULE$.currentTransportInformation().value_$eq(oldInfo);
        }
        return object;
    }

    public static /* synthetic */ void sendSystemMessage$(Dispatch $this, SystemMessage message) {
        $this.sendSystemMessage(message);
    }

    default public void sendSystemMessage(SystemMessage message) {
        try {
            ((ActorCell)this).dispatcher().systemDispatch((ActorCell)this, message);
        }
        catch (Throwable x$7) {
            PartialFunction<Throwable, BoxedUnit> catchExpr$6 = this.handleException();
            if (catchExpr$6.isDefinedAt(x$7)) {
                catchExpr$6.apply(x$7);
            }
            throw x$7;
        }
    }

    public static void $init$(Dispatch $this) {
    }
}

