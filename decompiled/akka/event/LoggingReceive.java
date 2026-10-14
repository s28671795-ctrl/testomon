/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorContext;
import akka.actor.DiagnosticActorLogging;
import akka.event.LogSource$;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$LogEvent$;
import akka.event.Logging$LogLevel$;
import akka.event.LoggingReceive$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005]r!B\n\u0015\u0011\u0003Ib!B\u000e\u0015\u0011\u0003a\u0002\"B\u0012\u0002\t\u0003!\u0003\"B\u0013\u0002\t\u00031\u0003\"B\u0013\u0002\t\u0003\u0001\u0005\"\u0002)\u0002\t\u0003\t\u0006\"\u0002/\u0002\t\u0003i\u0006\"\u0002/\u0002\t\u0003ig\u0001B\u000e\u0015\u0001MD\u0001\u0002\u001e\u0005\u0003\u0002\u0003\u0006I!\u001e\u0005\t\u007f!\u0011\t\u0011)A\u0005Q!A1\r\u0003B\u0001B\u0003%\u0001\u0010\u0003\u0005G\u0011\t\u0005\t\u0015!\u0003H\u0011!I\u0004B!A!\u0002\u0017Q\u0004\"B\u0012\t\t\u0003I\bBB\u0012\t\t\u0003\t\u0019\u0001\u0003\u0004$\u0011\u0011\u0005\u0011q\u0002\u0005\b\u00033AA\u0011AA\u000e\u0011\u0019)\u0003\u0002\"\u0001\u0002.\u0005qAj\\4hS:<'+Z2fSZ,'BA\u000b\u0017\u0003\u0015)g/\u001a8u\u0015\u00059\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u001b\u00035\tAC\u0001\bM_\u001e<\u0017N\\4SK\u000e,\u0017N^3\u0014\u0005\u0005i\u0002C\u0001\u0010\"\u001b\u0005y\"\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\tz\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u00023\u0005)\u0011\r\u001d9msR\u0011qE\u0010\u000b\u0003Qa\u0002\"!K\u001b\u000f\u0005)\u0012dBA\u00161\u001d\tas&D\u0001.\u0015\tq\u0003$\u0001\u0004=e>|GOP\u0005\u0002/%\u0011\u0011GF\u0001\u0006C\u000e$xN]\u0005\u0003gQ\nQ!Q2u_JT!!\r\f\n\u0005Y:$a\u0002*fG\u0016Lg/\u001a\u0006\u0003gQBQ!O\u0002A\u0004i\nqaY8oi\u0016DH\u000f\u0005\u0002<y5\tA'\u0003\u0002>i\ta\u0011i\u0019;pe\u000e{g\u000e^3yi\")qh\u0001a\u0001Q\u0005\t!\u000f\u0006\u0002B\u000bR\u0011!\t\u0012\u000b\u0003Q\rCQ!\u000f\u0003A\u0004iBQa\u0010\u0003A\u0002!BQA\u0012\u0003A\u0002\u001d\u000b\u0001\u0002\\8h\u0019\u00164X\r\u001c\t\u0003\u00116s!!S&\u000f\u0005-R\u0015BA\u000b\u0017\u0013\taE#A\u0004M_\u001e<\u0017N\\4\n\u00059{%\u0001\u0003'pO2+g/\u001a7\u000b\u00051#\u0012AB2sK\u0006$X\rF\u0002S1f\u0003\"a\u0015,\u000f\u0005m\"\u0016BA+5\u00035\t%m\u001d;sC\u000e$\u0018i\u0019;pe&\u0011ag\u0016\u0006\u0003+RBQaP\u0003A\u0002ICQ!O\u0003A\u0002i\u0003\"aU.\n\u0005u:\u0016!C<ji\"d\u0015MY3m)\rq&\r\u001c\u000b\u0003?\u0006$\"\u0001\u000b1\t\u000be2\u00019\u0001\u001e\t\u000b}2\u0001\u0019\u0001\u0015\t\u000b\r4\u0001\u0019\u00013\u0002\u000b1\f'-\u001a7\u0011\u0005\u0015LgB\u00014h!\tas$\u0003\u0002i?\u00051\u0001K]3eK\u001aL!A[6\u0003\rM#(/\u001b8h\u0015\tAw\u0004C\u0003G\r\u0001\u0007q\t\u0006\u0002oeR\u0011q.\u001d\u000b\u0003QADQ!O\u0004A\u0004iBQaP\u0004A\u0002!BQaY\u0004A\u0002\u0011\u001c2\u0001C\u000f)\u0003\u0019\u0019x.\u001e:dKB\u0019aD^\u000f\n\u0005]|\"AB(qi&|g\u000eE\u0002\u001fm\u0012$bA_?\u007f\u007f\u0006\u0005ACA>}!\tQ\u0002\u0002C\u0003:\u001d\u0001\u000f!\bC\u0003u\u001d\u0001\u0007Q\u000fC\u0003@\u001d\u0001\u0007\u0001\u0006C\u0003d\u001d\u0001\u0007\u0001\u0010C\u0003G\u001d\u0001\u0007q\t\u0006\u0005\u0002\u0006\u0005%\u00111BA\u0007)\rY\u0018q\u0001\u0005\u0006s=\u0001\u001dA\u000f\u0005\u0006i>\u0001\r!\u001e\u0005\u0006\u007f=\u0001\r\u0001\u000b\u0005\u0006G>\u0001\r\u0001\u001f\u000b\u0007\u0003#\t)\"a\u0006\u0015\u0007m\f\u0019\u0002C\u0003:!\u0001\u000f!\bC\u0003u!\u0001\u0007Q\u000fC\u0003@!\u0001\u0007\u0001&A\u0006jg\u0012+g-\u001b8fI\u0006#H\u0003BA\u000f\u0003G\u00012AHA\u0010\u0013\r\t\tc\b\u0002\b\u0005>|G.Z1o\u0011\u001d\t)#\u0005a\u0001\u0003O\t\u0011a\u001c\t\u0004=\u0005%\u0012bAA\u0016?\t\u0019\u0011I\\=\u0015\t\u0005=\u0012Q\u0007\t\u0004=\u0005E\u0012bAA\u001a?\t!QK\\5u\u0011\u001d\t)C\u0005a\u0001\u0003O\u0001")
public class LoggingReceive
implements PartialFunction<Object, BoxedUnit> {
    private final Option<Object> source;
    private final PartialFunction<Object, BoxedUnit> r;
    private final Option<String> label;
    private final int logLevel;
    private final ActorContext context;

    public static PartialFunction<Object, BoxedUnit> withLabel(String label, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        return LoggingReceive$.MODULE$.withLabel(label, r, context);
    }

    public static PartialFunction<Object, BoxedUnit> withLabel(String label, int logLevel, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        return LoggingReceive$.MODULE$.withLabel(label, logLevel, r, context);
    }

    public static AbstractActor.Receive create(AbstractActor.Receive r, AbstractActor.ActorContext context) {
        return LoggingReceive$.MODULE$.create(r, context);
    }

    @Override
    public Option<BoxedUnit> unapply(Object a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(Function1<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C> PartialFunction<Object, C> andThen(PartialFunction<BoxedUnit, C> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, BoxedUnit> compose(PartialFunction<R$, Object> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<Object, Option<BoxedUnit>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public <A1, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        return (B1)PartialFunction.applyOrElse$(this, x, function1);
    }

    @Override
    public <U> Function1<Object, Object> runWith(Function1<BoxedUnit, U> action) {
        return PartialFunction.runWith$(this, action);
    }

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
    }

    @Override
    public double apply$mcDD$sp(double v1) {
        return Function1.apply$mcDD$sp$(this, v1);
    }

    @Override
    public float apply$mcFD$sp(double v1) {
        return Function1.apply$mcFD$sp$(this, v1);
    }

    @Override
    public int apply$mcID$sp(double v1) {
        return Function1.apply$mcID$sp$(this, v1);
    }

    @Override
    public long apply$mcJD$sp(double v1) {
        return Function1.apply$mcJD$sp$(this, v1);
    }

    @Override
    public void apply$mcVD$sp(double v1) {
        Function1.apply$mcVD$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZF$sp(float v1) {
        return Function1.apply$mcZF$sp$(this, v1);
    }

    @Override
    public double apply$mcDF$sp(float v1) {
        return Function1.apply$mcDF$sp$(this, v1);
    }

    @Override
    public float apply$mcFF$sp(float v1) {
        return Function1.apply$mcFF$sp$(this, v1);
    }

    @Override
    public int apply$mcIF$sp(float v1) {
        return Function1.apply$mcIF$sp$(this, v1);
    }

    @Override
    public long apply$mcJF$sp(float v1) {
        return Function1.apply$mcJF$sp$(this, v1);
    }

    @Override
    public void apply$mcVF$sp(float v1) {
        Function1.apply$mcVF$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZI$sp(int v1) {
        return Function1.apply$mcZI$sp$(this, v1);
    }

    @Override
    public double apply$mcDI$sp(int v1) {
        return Function1.apply$mcDI$sp$(this, v1);
    }

    @Override
    public float apply$mcFI$sp(int v1) {
        return Function1.apply$mcFI$sp$(this, v1);
    }

    @Override
    public int apply$mcII$sp(int v1) {
        return Function1.apply$mcII$sp$(this, v1);
    }

    @Override
    public long apply$mcJI$sp(int v1) {
        return Function1.apply$mcJI$sp$(this, v1);
    }

    @Override
    public void apply$mcVI$sp(int v1) {
        Function1.apply$mcVI$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZJ$sp(long v1) {
        return Function1.apply$mcZJ$sp$(this, v1);
    }

    @Override
    public double apply$mcDJ$sp(long v1) {
        return Function1.apply$mcDJ$sp$(this, v1);
    }

    @Override
    public float apply$mcFJ$sp(long v1) {
        return Function1.apply$mcFJ$sp$(this, v1);
    }

    @Override
    public int apply$mcIJ$sp(long v1) {
        return Function1.apply$mcIJ$sp$(this, v1);
    }

    @Override
    public long apply$mcJJ$sp(long v1) {
        return Function1.apply$mcJJ$sp$(this, v1);
    }

    @Override
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A> Function1<A, BoxedUnit> compose(Function1<A, Object> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    @Override
    public boolean isDefinedAt(Object o) {
        boolean handled;
        block5: {
            Logging.LogEvent logEvent;
            String string;
            handled = this.r.isDefinedAt(o);
            if (!Logging$LogLevel$.MODULE$.$greater$eq$extension(this.context.system().eventStream().logLevel(), this.logLevel)) break block5;
            Object src = this.source.getOrElse((Function0<Actor> & Serializable)() -> ((ActorCell)$this.context).actor());
            Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.fromAnyRef(src);
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            String str = tuple2._1();
            Class<?> clazz = tuple2._2();
            Tuple2 tuple22 = new Tuple2(str, clazz);
            String str2 = tuple22._1();
            Class<?> clazz2 = tuple22._2();
            StringBuilder stringBuilder = new StringBuilder(24).append("received ").append((Object)(handled ? "handled" : "unhandled")).append(" message ").append(o).append(" from ").append(this.context.sender());
            Option<String> option = this.label;
            if (option instanceof Some) {
                Some some = (Some)option;
                String l = (String)some.value();
                string = new StringBuilder(10).append(" in state ").append(l).toString();
            } else {
                string = "";
            }
            String message = stringBuilder.append((Object)string).toString();
            Object b = src;
            if (b instanceof DiagnosticActorLogging) {
                DiagnosticActorLogging diagnosticActorLogging = (DiagnosticActorLogging)b;
                logEvent = Logging$LogEvent$.MODULE$.apply(this.logLevel, str2, clazz2, message, diagnosticActorLogging.log().mdc());
            } else {
                logEvent = Logging$LogEvent$.MODULE$.apply(this.logLevel, str2, clazz2, message);
            }
            Logging.LogEvent event = logEvent;
            this.context.system().eventStream().publish(event);
        }
        return handled;
    }

    @Override
    public void apply(Object o) {
        this.r.apply(o);
    }

    public LoggingReceive(Option<Object> source2, PartialFunction<Object, BoxedUnit> r, Option<String> label, int logLevel, ActorContext context) {
        this.source = source2;
        this.r = r;
        this.label = label;
        this.logLevel = logLevel;
        this.context = context;
        Function1.$init$(this);
        PartialFunction.$init$(this);
    }

    public LoggingReceive(Option<Object> source2, PartialFunction<Object, BoxedUnit> r, Option<String> label, ActorContext context) {
        this(source2, r, label, Logging$.MODULE$.DebugLevel(), context);
    }

    public LoggingReceive(Option<Object> source2, PartialFunction<Object, BoxedUnit> r, ActorContext context) {
        this(source2, r, None$.MODULE$, Logging$.MODULE$.DebugLevel(), context);
    }
}

