/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Deploy$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.pattern.AskableActorRef$;
import akka.pattern.package$;
import akka.stream.ActorMaterializerSettings;
import akka.stream.ActorMaterializerSettings$;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer$;
import akka.stream.impl.MaterializerGuardian;
import akka.stream.impl.MaterializerGuardian$;
import akka.stream.impl.MaterializerGuardian$StartMaterializer$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.Timeout;
import akka.util.Timeout$;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.Await$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005mq!\u0002\f\u0018\u0011\u0003ab!\u0002\u0010\u0018\u0011\u0003y\u0002\"B\u001b\u0002\t\u0003a\b\"B?\u0002\t\u0003r\bBB?\u0002\t\u0003\n9\u0001C\u0004\u0002\u0012\u0005!\t%a\u0005\t\u000f\u0005U\u0011\u0001\"\u0011\u0002\u0018\u0019!ad\u0006\u0002.\u0011!\ttA!A!\u0002\u0013\u0011\u0004\"B\u001b\b\t\u00031\u0004b\u0002\u001d\b\u0005\u0004%I!\u000f\u0005\u0007\u0007\u001e\u0001\u000b\u0011\u0002\u001e\t\u0011\u0011;!\u0019!C\u00013\u0015Ca!S\u0004!\u0002\u00131\u0005bB)\b\u0005\u0004%YA\u0015\u0005\u00073\u001e\u0001\u000b\u0011B*\t\u000fi;!\u0019!C\u00057\"1ql\u0002Q\u0001\nqCa!Y\u0004\u0005\u0002e\u0011\u0007B\u00023\b\t\u0003IR\rC\u0004w\u000f\t\u0007I\u0011A<\t\ra<\u0001\u0015!\u0003A\u0003I\u0019\u0016p\u001d;f[6\u000bG/\u001a:jC2L'0\u001a:\u000b\u0005aI\u0012AB:ue\u0016\fWNC\u0001\u001b\u0003\u0011\t7n[1\u0004\u0001A\u0011Q$A\u0007\u0002/\t\u00112+_:uK6l\u0015\r^3sS\u0006d\u0017N_3s'\u0011\t\u0001EJ=\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0003\r\nQa]2bY\u0006L!!\n\u0012\u0003\r\u0005s\u0017PU3g!\r9#\u0006L\u0007\u0002Q)\u0011\u0011&G\u0001\u0006C\u000e$xN]\u0005\u0003W!\u00121\"\u0012=uK:\u001c\u0018n\u001c8JIB\u0011QdB\n\u0004\u000f\u0001r\u0003CA\u00140\u0013\t\u0001\u0004FA\u0005FqR,gn]5p]\u000611/_:uK6\u0004\"aJ\u001a\n\u0005QB#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017A\u0002\u001fj]&$h\b\u0006\u0002-o!)\u0011'\u0003a\u0001e\u0005I2/_:uK6l\u0015\r^3sS\u0006d\u0017N_3s!J|W.[:f+\u0005Q\u0004cA\u001e?\u00016\tAH\u0003\u0002>E\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005}b$a\u0002)s_6L7/\u001a\t\u0003;\u0005K!AQ\f\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u00025ML8\u000f^3n\u001b\u0006$XM]5bY&TXM\u001d)s_6L7/\u001a\u0011\u0002)5\fG/\u001a:jC2L'0\u001a:TKR$\u0018N\\4t+\u00051\u0005CA\u000fH\u0013\tAuCA\rBGR|'/T1uKJL\u0017\r\\5{KJ\u001cV\r\u001e;j]\u001e\u001c\u0018!F7bi\u0016\u0014\u0018.\u00197ju\u0016\u00148+\u001a;uS:<7\u000f\t\u0015\u0003\u001b-\u0003\"\u0001T(\u000e\u00035S!AT\r\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002Q\u001b\nY\u0011J\u001c;fe:\fG.\u00119j\u0003Mi\u0017\r^3sS\u0006d\u0017N_3s)&lWm\\;u+\u0005\u0019\u0006C\u0001+X\u001b\u0005)&B\u0001,\u001a\u0003\u0011)H/\u001b7\n\u0005a+&a\u0002+j[\u0016|W\u000f^\u0001\u0015[\u0006$XM]5bY&TXM\u001d+j[\u0016|W\u000f\u001e\u0011\u0002)5\fG/\u001a:jC2L'0\u001a:Hk\u0006\u0014H-[1o+\u0005a\u0006CA\u0014^\u0013\tq\u0006F\u0001\u0005BGR|'OU3g\u0003Ui\u0017\r^3sS\u0006d\u0017N_3s\u000fV\f'\u000fZ5b]\u0002B#!E&\u0002E\r\u0014X-\u0019;f\u0003\u0012$\u0017\u000e^5p]\u0006d7+_:uK6l\u0015\r^3sS\u0006d\u0017N_3s)\u0005\u0001\u0005F\u0001\nL\u0003!\u001a'/Z1uK\u0006#G-\u001b;j_:\fG\u000eT3hC\u000eL8+_:uK6l\u0015\r^3sS\u0006d\u0017N_3s)\r\u0001em\u001d\u0005\u0006ON\u0001\r\u0001[\u0001\u000b]\u0006lW\r\u0015:fM&D\bCA5q\u001d\tQg\u000e\u0005\u0002lE5\tAN\u0003\u0002n7\u00051AH]8pizJ!a\u001c\u0012\u0002\rA\u0013X\rZ3g\u0013\t\t(O\u0001\u0004TiJLgn\u001a\u0006\u0003_\nBQ\u0001^\nA\u0002\u0019\u000b\u0001b]3ui&twm\u001d\u0015\u0003'-\u000bA\"\\1uKJL\u0017\r\\5{KJ,\u0012\u0001Q\u0001\u000e[\u0006$XM]5bY&TXM\u001d\u0011\u0011\u0005\u001dR\u0018BA>)\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s)\u0005a\u0012aA4fiR\u0011Af \u0005\u0007c\r\u0001\r!!\u0001\u0011\u0007\u001d\n\u0019!C\u0002\u0002\u0006!\u00121\"Q2u_J\u001c\u0016p\u001d;f[R\u0019A&!\u0003\t\rE\"\u0001\u0019AA\u0006!\r9\u0013QB\u0005\u0004\u0003\u001fA#AG\"mCN\u001c\u0018nY!di>\u00148+_:uK6\u0004&o\u001c<jI\u0016\u0014\u0018A\u00027p_.,\b/F\u0001\u001d\u0003=\u0019'/Z1uK\u0016CH/\u001a8tS>tGc\u0001\u0017\u0002\u001a!)\u0011G\u0002a\u0001e\u0001")
public final class SystemMaterializer
implements Extension {
    private final Promise<Materializer> systemMaterializerPromise = Promise$.MODULE$.apply();
    @InternalApi
    private final ActorMaterializerSettings materializerSettings;
    private final Timeout materializerTimeout;
    @InternalApi
    private final ActorRef materializerGuardian;
    private final Materializer materializer;

    public static SystemMaterializer createExtension(ExtendedActorSystem system) {
        return SystemMaterializer$.MODULE$.createExtension(system);
    }

    public static SystemMaterializer$ lookup() {
        return SystemMaterializer$.MODULE$.lookup();
    }

    public static SystemMaterializer get(ClassicActorSystemProvider system) {
        return SystemMaterializer$.MODULE$.get(system);
    }

    public static SystemMaterializer get(ActorSystem system) {
        return SystemMaterializer$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return SystemMaterializer$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return SystemMaterializer$.MODULE$.apply(system);
    }

    private Promise<Materializer> systemMaterializerPromise() {
        return this.systemMaterializerPromise;
    }

    public ActorMaterializerSettings materializerSettings() {
        return this.materializerSettings;
    }

    private Timeout materializerTimeout() {
        return this.materializerTimeout;
    }

    private ActorRef materializerGuardian() {
        return this.materializerGuardian;
    }

    @InternalApi
    public Materializer createAdditionalSystemMaterializer() {
        ActorRef qual$1 = package$.MODULE$.ask(this.materializerGuardian());
        MaterializerGuardian$StartMaterializer$ x$1 = MaterializerGuardian$StartMaterializer$.MODULE$;
        Timeout x$2 = this.materializerTimeout();
        ActorRef x$3 = AskableActorRef$.MODULE$.$qmark$default$3$extension(qual$1, x$1);
        Future started = AskableActorRef$.MODULE$.$qmark$extension(qual$1, x$1, x$2, x$3).mapTo(ClassTag$.MODULE$.apply(MaterializerGuardian.MaterializerStarted.class));
        return ((MaterializerGuardian.MaterializerStarted)Await$.MODULE$.result(started, this.materializerTimeout().duration())).materializer();
    }

    @InternalApi
    public Materializer createAdditionalLegacySystemMaterializer(String namePrefix, ActorMaterializerSettings settings) {
        ActorRef qual$1 = package$.MODULE$.ask(this.materializerGuardian());
        MaterializerGuardian.LegacyStartMaterializer x$1 = new MaterializerGuardian.LegacyStartMaterializer(namePrefix, settings);
        Timeout x$2 = this.materializerTimeout();
        ActorRef x$3 = AskableActorRef$.MODULE$.$qmark$default$3$extension(qual$1, x$1);
        Future started = AskableActorRef$.MODULE$.$qmark$extension(qual$1, x$1, x$2, x$3).mapTo(ClassTag$.MODULE$.apply(MaterializerGuardian.MaterializerStarted.class));
        return ((MaterializerGuardian.MaterializerStarted)Await$.MODULE$.result(started, this.materializerTimeout().duration())).materializer();
    }

    public Materializer materializer() {
        return this.materializer;
    }

    public SystemMaterializer(ExtendedActorSystem system) {
        Materializer materializer;
        this.materializerSettings = ActorMaterializerSettings$.MODULE$.apply(system);
        this.materializerTimeout = Timeout$.MODULE$.durationToTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(system.settings().config().getDuration("akka.stream.materializer.creation-timeout"))));
        this.materializerGuardian = system.systemActorOf(MaterializerGuardian$.MODULE$.props(this.systemMaterializerPromise(), this.materializerSettings()).withDispatcher("akka.actor.internal-dispatcher").withDeploy(Deploy$.MODULE$.local()), "Materializers");
        Future<Materializer> systemMaterializerFuture = this.systemMaterializerPromise().future();
        Option<Try<Materializer>> option = systemMaterializerFuture.value();
        if (option instanceof Some) {
            Some some = (Some)option;
            Try tryMaterializer = (Try)some.value();
            materializer = (Materializer)tryMaterializer.get();
        } else if (None$.MODULE$.equals(option)) {
            materializer = Await$.MODULE$.result(systemMaterializerFuture, this.materializerTimeout().duration());
        } else {
            throw new MatchError(option);
        }
        this.materializer = materializer;
    }
}

