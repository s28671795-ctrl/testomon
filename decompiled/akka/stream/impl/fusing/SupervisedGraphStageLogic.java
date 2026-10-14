/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.DoNotInherit;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Shape;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.stage.GraphStageLogic;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u000594a!\u0003\u0006\u0002\u0002A\u0011\u0002\u0002C\r\u0001\u0005\u0003\u0005\u000b\u0011B\u000e\t\u0011}\u0001!\u0011!Q\u0001\n\u0001BQa\t\u0001\u0005\u0002\u0011B\u0001\"\u000b\u0001\t\u0006\u0004%IA\u000b\u0005\u0006e\u0001!\ta\r\u0005\u0006\u001d\u00021\ta\u0014\u0005\u0006C\u0002!\tA\u0019\u0005\u0006I\u0002!\t!\u001a\u0002\u001a'V\u0004XM\u001d<jg\u0016$wI]1qQN#\u0018mZ3M_\u001eL7M\u0003\u0002\f\u0019\u00051a-^:j]\u001eT!!\u0004\b\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001fA\taa\u001d;sK\u0006l'\"A\t\u0002\t\u0005\\7.Y\n\u0003\u0001M\u0001\"\u0001F\f\u000e\u0003UQ!A\u0006\b\u0002\u000bM$\u0018mZ3\n\u0005a)\"aD$sCBD7\u000b^1hK2{w-[2\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0004\u0001A\u0011A$H\u0007\u0002\u001d%\u0011aD\u0004\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,\u0007C\u0001\u000f\"\u0013\t\u0011cBA\u0003TQ\u0006\u0004X-\u0001\u0004=S:LGO\u0010\u000b\u0004K\u001dB\u0003C\u0001\u0014\u0001\u001b\u0005Q\u0001\"B\r\u0004\u0001\u0004Y\u0002\"B\u0010\u0004\u0001\u0004\u0001\u0013a\u00023fG&$WM]\u000b\u0002WA\u0011Af\f\b\u000395J!A\f\b\u0002\u0017M+\b/\u001a:wSNLwN\\\u0005\u0003aE\u0012q\u0001R3dS\u0012,'O\u0003\u0002/\u001d\u0005yq/\u001b;i'V\u0004XM\u001d<jg&|g.\u0006\u00025{Q\u0011Q'\u0013\t\u0004meZT\"A\u001c\u000b\u0005a\u0002\u0012\u0001B;uS2L!AO\u001c\u0003\u0013=\u0003H/[8o-\u0006d\u0007C\u0001\u001f>\u0019\u0001!QAP\u0003C\u0002}\u0012\u0011\u0001V\t\u0003\u0001\u001a\u0003\"!\u0011#\u000e\u0003\tS\u0011aQ\u0001\u0006g\u000e\fG.Y\u0005\u0003\u000b\n\u0013qAT8uQ&tw\r\u0005\u0002B\u000f&\u0011\u0001J\u0011\u0002\u0004\u0003:L\b\"\u0002&\u0006\u0001\u0004Y\u0015!\u00014\u0011\u0007\u0005c5(\u0003\u0002N\u0005\nIa)\u001e8di&|g\u000eM\u0001\t_:\u0014Vm];nKR\u0011\u0001k\u0015\t\u0003\u0003FK!A\u0015\"\u0003\tUs\u0017\u000e\u001e\u0005\u0006)\u001a\u0001\r!V\u0001\u0002iB\u0011aK\u0018\b\u0003/rs!\u0001W.\u000e\u0003eS!A\u0017\u000e\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0015BA/C\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u00181\u0003\u0013QC'o\\<bE2,'BA/C\u0003\u0019ygn\u0015;paR\u0011\u0001k\u0019\u0005\u0006)\u001e\u0001\r!V\u0001\n_:\u0014Vm\u001d;beR$\"\u0001\u00154\t\u000bQC\u0001\u0019A+)\u0005\u0001A\u0007CA5m\u001b\u0005Q'BA6\u0011\u0003)\tgN\\8uCRLwN\\\u0005\u0003[*\u0014A\u0002R8O_RLe\u000e[3sSR\u0004")
@DoNotInherit
public abstract class SupervisedGraphStageLogic
extends GraphStageLogic {
    private Function1<Throwable, Supervision.Directive> decider;
    private Attributes inheritedAttributes;
    private volatile boolean bitmap$0;

    private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
        SupervisedGraphStageLogic supervisedGraphStageLogic = this;
        synchronized (supervisedGraphStageLogic) {
            if (!this.bitmap$0) {
                this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                this.bitmap$0 = true;
            }
        }
        this.inheritedAttributes = null;
        return this.decider;
    }

    private Function1<Throwable, Supervision.Directive> decider() {
        if (!this.bitmap$0) {
            return this.decider$lzycompute();
        }
        return this.decider;
    }

    public <T> T withSupervision(Function0<T> f) {
        Object object;
        try {
            object = OptionVal$Some$.MODULE$.apply(f.apply());
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable ex = option.get();
                Supervision.Directive directive = this.decider().apply(ex);
                if (Supervision$Stop$.MODULE$.equals(directive)) {
                    this.onStop(ex);
                } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                    this.onResume(ex);
                } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                    this.onRestart(ex);
                } else {
                    throw new MatchError(directive);
                }
                object = OptionVal$.MODULE$.none();
            }
            throw throwable;
        }
        return object;
    }

    public abstract void onResume(Throwable var1);

    public void onStop(Throwable t2) {
        this.failStage(t2);
    }

    public void onRestart(Throwable t2) {
        this.onResume(t2);
    }

    public SupervisedGraphStageLogic(Attributes inheritedAttributes, Shape shape) {
        this.inheritedAttributes = inheritedAttributes;
        super(shape);
    }
}

