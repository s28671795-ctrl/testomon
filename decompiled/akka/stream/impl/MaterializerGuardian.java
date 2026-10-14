/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.Materializer;
import akka.stream.impl.MaterializerGuardian$;
import akka.stream.impl.MaterializerGuardian$StartMaterializer$;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t}uA\u0002&L\u0011\u0003y\u0015K\u0002\u0004T\u0017\"\u0005q\n\u0016\u0005\u00067\u0006!\t!X\u0004\u0006=\u0006A\ti\u0018\u0004\u0006C\u0006A\tI\u0019\u0005\u00067\u0012!\tA\u001d\u0005\bg\u0012\t\t\u0011\"\u0011u\u0011\u001diH!!A\u0005\u0002yD\u0011\"!\u0002\u0005\u0003\u0003%\t!a\u0002\t\u0013\u0005MA!!A\u0005B\u0005U\u0001\"CA\u0012\t\u0005\u0005I\u0011AA\u0013\u0011%\ty\u0003BA\u0001\n\u0003\n\t\u0004C\u0005\u00024\u0011\t\t\u0011\"\u0011\u00026!I\u0011q\u0007\u0003\u0002\u0002\u0013%\u0011\u0011\b\u0004\u0007\u0003\u0003\n!)a\u0011\t\u0015\u0005\u0015cB!f\u0001\n\u0003\t9\u0005\u0003\u0006\u0002R9\u0011\t\u0012)A\u0005\u0003\u0013Baa\u0017\b\u0005\u0002\u0005M\u0003\"CA-\u001d\u0005\u0005I\u0011AA.\u0011%\tyFDI\u0001\n\u0003\t\t\u0007C\u0004t\u001d\u0005\u0005I\u0011\t;\t\u000fut\u0011\u0011!C\u0001}\"I\u0011Q\u0001\b\u0002\u0002\u0013\u0005\u0011q\u000f\u0005\n\u0003'q\u0011\u0011!C!\u0003+A\u0011\"a\t\u000f\u0003\u0003%\t!a\u001f\t\u0013\u0005}d\"!A\u0005B\u0005\u0005\u0005\"CA\u0018\u001d\u0005\u0005I\u0011IA\u0019\u0011%\t\u0019DDA\u0001\n\u0003\n)\u0004C\u0005\u0002\u0006:\t\t\u0011\"\u0011\u0002\b\u001eI\u00111R\u0001\u0002\u0002#\u0005\u0011Q\u0012\u0004\n\u0003\u0003\n\u0011\u0011!E\u0001\u0003\u001fCaa\u0017\u0010\u0005\u0002\u0005\u001d\u0006\"CA\u001a=\u0005\u0005IQIA\u001b\u0011%\tIKHA\u0001\n\u0003\u000bY\u000bC\u0005\u00020z\t\t\u0011\"!\u00022\"I\u0011q\u0007\u0010\u0002\u0002\u0013%\u0011\u0011\b\u0004\u0007\u0003{\u000b!)a0\t\u0015\u0005\u0005GE!f\u0001\n\u0003\t\u0019\r\u0003\u0006\u0002T\u0012\u0012\t\u0012)A\u0005\u0003\u000bD!\"!6%\u0005+\u0007I\u0011AAl\u0011)\ty\u000e\nB\tB\u0003%\u0011\u0011\u001c\u0005\u00077\u0012\"\t!!9\t\u0013\u0005eC%!A\u0005\u0002\u0005%\b\"CA0IE\u0005I\u0011AAx\u0011%\t\u0019\u0010JI\u0001\n\u0003\t)\u0010C\u0004tI\u0005\u0005I\u0011\t;\t\u000fu$\u0013\u0011!C\u0001}\"I\u0011Q\u0001\u0013\u0002\u0002\u0013\u0005\u0011\u0011 \u0005\n\u0003'!\u0013\u0011!C!\u0003+A\u0011\"a\t%\u0003\u0003%\t!!@\t\u0013\u0005}D%!A\u0005B\t\u0005\u0001\"CA\u0018I\u0005\u0005I\u0011IA\u0019\u0011%\t\u0019\u0004JA\u0001\n\u0003\n)\u0004C\u0005\u0002\u0006\u0012\n\t\u0011\"\u0011\u0003\u0006\u001dI!\u0011B\u0001\u0002\u0002#\u0005!1\u0002\u0004\n\u0003{\u000b\u0011\u0011!E\u0001\u0005\u001bAaaW\u001c\u0005\u0002\tU\u0001\"CA\u001ao\u0005\u0005IQIA\u001b\u0011%\tIkNA\u0001\n\u0003\u00139\u0002C\u0005\u00020^\n\t\u0011\"!\u0003\u001e!I\u0011qG\u001c\u0002\u0002\u0013%\u0011\u0011\b\u0005\b\u0005S\tA\u0011\u0001B\u0016\r\u0019\u00196JA(\u0003\\!Q!1\r \u0003\u0002\u0003\u0006IA!\u0010\t\u0015\t-cH!A!\u0002\u0013\tI\u000e\u0003\u0004\\}\u0011\u0005!Q\r\u0005\n\u0005[r$\u0019!C\u0005\u0005_B\u0001Ba\u001e?A\u0003%!\u0011\u000f\u0005\t\u0005sr$\u0019!C\u0005i\"9!1\u0010 !\u0002\u0013)\b\"\u0003B\u001e}\t\u0007I\u0011\u0002B?\u0011!\u0011)I\u0010Q\u0001\n\t}\u0004b\u0002BD}\u0011\u0005#\u0011\u0012\u0005\b\u0005'sD\u0011\u0002BK\u0003Qi\u0015\r^3sS\u0006d\u0017N_3s\u000fV\f'\u000fZ5b]*\u0011A*T\u0001\u0005S6\u0004HN\u0003\u0002O\u001f\u000611\u000f\u001e:fC6T\u0011\u0001U\u0001\u0005C.\\\u0017\r\u0005\u0002S\u00035\t1J\u0001\u000bNCR,'/[1mSj,'oR;be\u0012L\u0017M\\\n\u0003\u0003U\u0003\"AV-\u000e\u0003]S\u0011\u0001W\u0001\u0006g\u000e\fG.Y\u0005\u00035^\u0013a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003E\u000b\u0011c\u0015;beRl\u0015\r^3sS\u0006d\u0017N_3s!\t\u0001G!D\u0001\u0002\u0005E\u0019F/\u0019:u\u001b\u0006$XM]5bY&TXM]\n\u0005\tU\u001bg\r\u0005\u0002WI&\u0011Qm\u0016\u0002\b!J|G-^2u!\t9wN\u0004\u0002i[:\u0011\u0011\u000e\\\u0007\u0002U*\u00111\u000eX\u0001\u0007yI|w\u000e\u001e \n\u0003aK!A\\,\u0002\u000fA\f7m[1hK&\u0011\u0001/\u001d\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003]^#\u0012aX\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003U\u0004\"A^>\u000e\u0003]T!\u0001_=\u0002\t1\fgn\u001a\u0006\u0002u\u0006!!.\u0019<b\u0013\taxO\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002\u007fB\u0019a+!\u0001\n\u0007\u0005\rqKA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002\n\u0005=\u0001c\u0001,\u0002\f%\u0019\u0011QB,\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002\u0012!\t\t\u00111\u0001\u0000\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0007\u00033\ty\"!\u0003\u000e\u0005\u0005m!bAA\u000f/\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\u0005\u00121\u0004\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002(\u00055\u0002c\u0001,\u0002*%\u0019\u00111F,\u0003\u000f\t{w\u000e\\3b]\"I\u0011\u0011\u0003\u0006\u0002\u0002\u0003\u0007\u0011\u0011B\u0001\tQ\u0006\u001c\bnQ8eKR\tq0\u0001\u0005u_N#(/\u001b8h)\u0005)\u0018\u0001D<sSR,'+\u001a9mC\u000e,GCAA\u001e!\r1\u0018QH\u0005\u0004\u0003\u007f9(AB(cU\u0016\u001cGOA\nNCR,'/[1mSj,'o\u0015;beR,Gm\u0005\u0003\u000f+\u000e4\u0017\u0001D7bi\u0016\u0014\u0018.\u00197ju\u0016\u0014XCAA%!\u0011\tY%!\u0014\u000e\u00035K1!a\u0014N\u00051i\u0015\r^3sS\u0006d\u0017N_3s\u00035i\u0017\r^3sS\u0006d\u0017N_3sAQ!\u0011QKA,!\t\u0001g\u0002C\u0004\u0002FE\u0001\r!!\u0013\u0002\t\r|\u0007/\u001f\u000b\u0005\u0003+\ni\u0006C\u0005\u0002FI\u0001\n\u00111\u0001\u0002J\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA2U\u0011\tI%!\u001a,\u0005\u0005\u001d\u0004\u0003BA5\u0003gj!!a\u001b\u000b\t\u00055\u0014qN\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u001dX\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003k\nYGA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$B!!\u0003\u0002z!A\u0011\u0011\u0003\f\u0002\u0002\u0003\u0007q\u0010\u0006\u0003\u0002(\u0005u\u0004\"CA\t1\u0005\u0005\t\u0019AA\u0005\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007U\f\u0019\t\u0003\u0005\u0002\u0012e\t\t\u00111\u0001\u0000\u0003\u0019)\u0017/^1mgR!\u0011qEAE\u0011%\t\t\u0002HA\u0001\u0002\u0004\tI!A\nNCR,'/[1mSj,'o\u0015;beR,G\r\u0005\u0002a=M)a$!%\u0002\u001eBA\u00111SAM\u0003\u0013\n)&\u0004\u0002\u0002\u0016*\u0019\u0011qS,\u0002\u000fI,h\u000e^5nK&!\u00111TAK\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003?\u000b)+\u0004\u0002\u0002\"*\u0019\u00111U=\u0002\u0005%|\u0017b\u00019\u0002\"R\u0011\u0011QR\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0003+\ni\u000bC\u0004\u0002F\u0005\u0002\r!!\u0013\u0002\u000fUt\u0017\r\u001d9msR!\u00111WA]!\u00151\u0016QWA%\u0013\r\t9l\u0016\u0002\u0007\u001fB$\u0018n\u001c8\t\u0013\u0005m&%!AA\u0002\u0005U\u0013a\u0001=%a\t9B*Z4bGf\u001cF/\u0019:u\u001b\u0006$XM]5bY&TXM]\n\u0005IU\u001bg-\u0001\u0006oC6,\u0007K]3gSb,\"!!2\u0011\t\u0005\u001d\u0017q\u001a\b\u0005\u0003\u0013\fY\r\u0005\u0002j/&\u0019\u0011QZ,\u0002\rA\u0013X\rZ3g\u0013\ra\u0018\u0011\u001b\u0006\u0004\u0003\u001b<\u0016a\u00038b[\u0016\u0004&/\u001a4jq\u0002\n\u0001b]3ui&twm]\u000b\u0003\u00033\u0004B!a\u0013\u0002\\&\u0019\u0011Q\\'\u00033\u0005\u001bGo\u001c:NCR,'/[1mSj,'oU3ui&twm]\u0001\ng\u0016$H/\u001b8hg\u0002\"b!a9\u0002f\u0006\u001d\bC\u00011%\u0011\u001d\t\t-\u000ba\u0001\u0003\u000bDq!!6*\u0001\u0004\tI\u000e\u0006\u0004\u0002d\u0006-\u0018Q\u001e\u0005\n\u0003\u0003T\u0003\u0013!a\u0001\u0003\u000bD\u0011\"!6+!\u0003\u0005\r!!7\u0016\u0005\u0005E(\u0006BAc\u0003K\nabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002x*\"\u0011\u0011\\A3)\u0011\tI!a?\t\u0011\u0005Eq&!AA\u0002}$B!a\n\u0002\u0000\"I\u0011\u0011C\u0019\u0002\u0002\u0003\u0007\u0011\u0011\u0002\u000b\u0004k\n\r\u0001\u0002CA\te\u0005\u0005\t\u0019A@\u0015\t\u0005\u001d\"q\u0001\u0005\n\u0003#)\u0014\u0011!a\u0001\u0003\u0013\tq\u0003T3hC\u000eL8\u000b^1si6\u000bG/\u001a:jC2L'0\u001a:\u0011\u0005\u0001<4#B\u001c\u0003\u0010\u0005u\u0005CCAJ\u0005#\t)-!7\u0002d&!!1CAK\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\u000b\u0003\u0005\u0017!b!a9\u0003\u001a\tm\u0001bBAau\u0001\u0007\u0011Q\u0019\u0005\b\u0003+T\u0004\u0019AAm)\u0011\u0011yBa\n\u0011\u000bY\u000b)L!\t\u0011\u000fY\u0013\u0019#!2\u0002Z&\u0019!QE,\u0003\rQ+\b\u000f\\33\u0011%\tYlOA\u0001\u0002\u0004\t\u0019/A\u0003qe>\u00048\u000f\u0006\u0004\u0003.\te\"\u0011\n\t\u0005\u0005_\u0011)$\u0004\u0002\u00032)\u0019!1G(\u0002\u000b\u0005\u001cGo\u001c:\n\t\t]\"\u0011\u0007\u0002\u0006!J|\u0007o\u001d\u0005\b\u0005wi\u0004\u0019\u0001B\u001f\u0003I\u0019\u0018p\u001d;f[6\u000bG/\u001a:jC2L'0\u001a:\u0011\r\t}\"QIA%\u001b\t\u0011\tEC\u0002\u0003D]\u000b!bY8oGV\u0014(/\u001a8u\u0013\u0011\u00119E!\u0011\u0003\u000fA\u0013x.\\5tK\"9!1J\u001fA\u0002\u0005e\u0017\u0001F7bi\u0016\u0014\u0018.\u00197ju\u0016\u00148+\u001a;uS:<7\u000fK\u0002\u0002\u0005\u001f\u0002BA!\u0015\u0003V5\u0011!1\u000b\u0006\u0004\u0003cz\u0015\u0002\u0002B,\u0005'\u00121\"\u00138uKJt\u0017\r\\!qS\"\u001a\u0001Aa\u0014\u0014\ty*&Q\f\t\u0005\u0005_\u0011y&\u0003\u0003\u0003b\tE\"!B!di>\u0014\u0018!G:zgR,W.T1uKJL\u0017\r\\5{KJ\u0004&o\\7jg\u0016$bAa\u001a\u0003j\t-\u0004C\u0001*?\u0011\u001d\u0011\u0019'\u0011a\u0001\u0005{AqAa\u0013B\u0001\u0004\tI.A\teK\u001a\fW\u000f\u001c;BiR\u0014\u0018NY;uKN,\"A!\u001d\u0011\t\u0005-#1O\u0005\u0004\u0005kj%AC!uiJL'-\u001e;fg\u0006\u0011B-\u001a4bk2$\u0018\t\u001e;sS\n,H/Z:!\u0003E!WMZ1vYRt\u0015-\\3Qe\u00164\u0017\u000e_\u0001\u0013I\u00164\u0017-\u001e7u\u001d\u0006lW\r\u0015:fM&D\b%\u0006\u0002\u0003\u0000A\u0019!K!!\n\u0007\t\r5JA\u000fQQ\u0006\u001cX\r\u001a$vg&tw-Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s\u0003M\u0019\u0018p\u001d;f[6\u000bG/\u001a:jC2L'0\u001a:!\u0003\u001d\u0011XmY3jm\u0016,\"Aa#\u0011\t\t5%qR\u0007\u0002}%!!\u0011\u0013B0\u0005\u001d\u0011VmY3jm\u0016\f\u0011c\u001d;beRl\u0015\r^3sS\u0006d\u0017N_3s)\u0019\u0011yHa&\u0003\u001a\"9\u0011\u0011Y%A\u0002\u0005\u0015\u0007bBAk\u0013\u0002\u0007!1\u0014\t\u0006-\u0006U\u0016\u0011\u001c\u0015\u0004}\t=\u0003")
@InternalApi
public final class MaterializerGuardian
implements Actor {
    private final ActorMaterializerSettings materializerSettings;
    private final Attributes defaultAttributes;
    private final String akka$stream$impl$MaterializerGuardian$$defaultNamePrefix;
    private final PhasedFusingActorMaterializer systemMaterializer;
    private ActorContext context;
    private ActorRef self;

    public static Props props(Promise<Materializer> systemMaterializer, ActorMaterializerSettings materializerSettings) {
        return MaterializerGuardian$.MODULE$.props(systemMaterializer, materializerSettings);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    private Attributes defaultAttributes() {
        return this.defaultAttributes;
    }

    public String akka$stream$impl$MaterializerGuardian$$defaultNamePrefix() {
        return this.akka$stream$impl$MaterializerGuardian$$defaultNamePrefix;
    }

    private PhasedFusingActorMaterializer systemMaterializer() {
        return this.systemMaterializer;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ MaterializerGuardian $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (MaterializerGuardian$StartMaterializer$.MODULE$.equals(A1)) {
                    this.$outer.sender().$bang(new MaterializerStarted(this.$outer.akka$stream$impl$MaterializerGuardian$$startMaterializer(this.$outer.akka$stream$impl$MaterializerGuardian$$defaultNamePrefix(), None$.MODULE$)), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof LegacyStartMaterializer) {
                    LegacyStartMaterializer legacyStartMaterializer = (LegacyStartMaterializer)A1;
                    String namePrefix = legacyStartMaterializer.namePrefix();
                    ActorMaterializerSettings settings = legacyStartMaterializer.settings();
                    this.$outer.sender().$bang(new MaterializerStarted(this.$outer.akka$stream$impl$MaterializerGuardian$$startMaterializer(namePrefix, new Some<ActorMaterializerSettings>(settings))), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (MaterializerGuardian$StartMaterializer$.MODULE$.equals(object)) {
                    return true;
                }
                return object instanceof LegacyStartMaterializer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    /*
     * Unable to fully structure code
     */
    public PhasedFusingActorMaterializer akka$stream$impl$MaterializerGuardian$$startMaterializer(String namePrefix, Option<ActorMaterializerSettings> settings) {
        block5: {
            block4: {
                var5_3 = false;
                var6_4 = null;
                var7_5 = settings;
                if (!None$.MODULE$.equals(var7_5)) break block4;
                v0 = this.defaultAttributes();
                break block5;
            }
            if (!(var7_5 instanceof Some)) ** GOTO lbl-1000
            var5_3 = true;
            var6_4 = (Some)var7_5;
            var8_6 = (ActorMaterializerSettings)var6_4.value();
            v1 = this.materializerSettings;
            var9_7 = var8_6;
            if (!(v1 != null ? v1.equals(var9_7) == false : var9_7 != null)) {
                v0 = this.defaultAttributes();
            } else if (var5_3) {
                settings = (ActorMaterializerSettings)var6_4.value();
                v0 = settings.toAttributes();
            } else {
                throw new MatchError(var7_5);
            }
        }
        attributes = v0;
        return PhasedFusingActorMaterializer$.MODULE$.apply(this.context(), namePrefix, (ActorMaterializerSettings)settings.getOrElse((Function0<ActorMaterializerSettings> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, ()Ljava/lang/Object;, $anonfun$startMaterializer$1(akka.stream.impl.MaterializerGuardian ), ()Lakka/stream/ActorMaterializerSettings;)((MaterializerGuardian)this)), attributes);
    }

    public static final /* synthetic */ ActorMaterializerSettings $anonfun$startMaterializer$1(MaterializerGuardian $this) {
        return $this.materializerSettings;
    }

    public MaterializerGuardian(Promise<Materializer> systemMaterializerPromise, ActorMaterializerSettings materializerSettings) {
        this.materializerSettings = materializerSettings;
        Actor.$init$(this);
        this.defaultAttributes = materializerSettings.toAttributes();
        this.akka$stream$impl$MaterializerGuardian$$defaultNamePrefix = "flow";
        this.systemMaterializer = this.akka$stream$impl$MaterializerGuardian$$startMaterializer(this.akka$stream$impl$MaterializerGuardian$$defaultNamePrefix(), None$.MODULE$);
        systemMaterializerPromise.success(this.systemMaterializer());
        Statics.releaseFence();
    }

    public static final class LegacyStartMaterializer
    implements Product,
    Serializable {
        private final String namePrefix;
        private final ActorMaterializerSettings settings;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String namePrefix() {
            return this.namePrefix;
        }

        public ActorMaterializerSettings settings() {
            return this.settings;
        }

        public LegacyStartMaterializer copy(String namePrefix, ActorMaterializerSettings settings) {
            return new LegacyStartMaterializer(namePrefix, settings);
        }

        public String copy$default$1() {
            return this.namePrefix();
        }

        public ActorMaterializerSettings copy$default$2() {
            return this.settings();
        }

        @Override
        public String productPrefix() {
            return "LegacyStartMaterializer";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.namePrefix();
                }
                case 1: {
                    return this.settings();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof LegacyStartMaterializer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "namePrefix";
                }
                case 1: {
                    return "settings";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof LegacyStartMaterializer)) return false;
            boolean bl = true;
            if (!bl) return false;
            LegacyStartMaterializer legacyStartMaterializer = (LegacyStartMaterializer)x$1;
            String string = this.namePrefix();
            String string2 = legacyStartMaterializer.namePrefix();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            ActorMaterializerSettings actorMaterializerSettings = this.settings();
            ActorMaterializerSettings actorMaterializerSettings2 = legacyStartMaterializer.settings();
            if (actorMaterializerSettings == null) {
                if (actorMaterializerSettings2 == null) return true;
                return false;
            } else {
                if (!((Object)actorMaterializerSettings).equals(actorMaterializerSettings2)) return false;
                return true;
            }
        }

        public LegacyStartMaterializer(String namePrefix, ActorMaterializerSettings settings) {
            this.namePrefix = namePrefix;
            this.settings = settings;
            Product.$init$(this);
        }
    }

    public static final class MaterializerStarted
    implements Product,
    Serializable {
        private final Materializer materializer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Materializer materializer() {
            return this.materializer;
        }

        public MaterializerStarted copy(Materializer materializer) {
            return new MaterializerStarted(materializer);
        }

        public Materializer copy$default$1() {
            return this.materializer();
        }

        @Override
        public String productPrefix() {
            return "MaterializerStarted";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.materializer();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof MaterializerStarted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "materializer";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof MaterializerStarted)) return false;
            boolean bl = true;
            if (!bl) return false;
            MaterializerStarted materializerStarted = (MaterializerStarted)x$1;
            Materializer materializer = this.materializer();
            Materializer materializer2 = materializerStarted.materializer();
            if (materializer != null) {
                if (!materializer.equals(materializer2)) return false;
                return true;
            }
            if (materializer2 == null) return true;
            return false;
        }

        public MaterializerStarted(Materializer materializer) {
            this.materializer = materializer;
            Product.$init$(this);
        }
    }
}

