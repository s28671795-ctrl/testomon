/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.ApiMayChange;
import akka.event.Logging$;
import akka.stream.Attributes;
import akka.stream.Attributes$AsyncBoundary$;
import akka.stream.Attributes$CancellationStrategy$CompleteStage$;
import akka.stream.Attributes$CancellationStrategy$FailStage$;
import akka.stream.Attributes$CancellationStrategy$PropagateFailure$;
import akka.stream.Attributes$LogLevels$;
import akka.stream.Attributes$NestedMaterializationCancellationPolicy$;
import akka.stream.impl.TraversalBuilder;
import java.io.Serializable;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;

public final class Attributes$
implements Serializable {
    public static final Attributes$ MODULE$ = new Attributes$();
    private static final Attributes none = MODULE$.apply();
    private static final Attributes asyncBoundary = MODULE$.apply(Attributes$AsyncBoundary$.MODULE$);

    public List<Attributes.Attribute> $lessinit$greater$default$1() {
        return Nil$.MODULE$;
    }

    public Attributes apply() {
        return new Attributes(Nil$.MODULE$, (Map<Class<Object>, Attributes.MandatoryAttribute>)Predef$.MODULE$.Map().empty());
    }

    public Attributes apply(List<Attributes.Attribute> attributeList) {
        return new Attributes(attributeList);
    }

    public Option<List<Attributes.Attribute>> unapply(Attributes attrs) {
        return new Some<List<Attributes.Attribute>>(attrs.attributeList());
    }

    @ApiMayChange
    public Attributes.CancellationStrategy.Strategy cancellationStrategyCompleteState() {
        return Attributes$CancellationStrategy$CompleteStage$.MODULE$;
    }

    @ApiMayChange
    public Attributes.CancellationStrategy.Strategy cancellationStrategyFailStage() {
        return Attributes$CancellationStrategy$FailStage$.MODULE$;
    }

    @ApiMayChange
    public Attributes.CancellationStrategy.Strategy cancellationStrategyPropagateFailure() {
        return Attributes$CancellationStrategy$PropagateFailure$.MODULE$;
    }

    @ApiMayChange
    public Attributes.CancellationStrategy.Strategy cancellationStrategyAfterDelay(FiniteDuration delay, Attributes.CancellationStrategy.Strategy strategy) {
        return new Attributes.CancellationStrategy.AfterDelay(delay, strategy);
    }

    @ApiMayChange
    public Attributes.NestedMaterializationCancellationPolicy nestedMaterializationCancellationPolicyEagerCancellation() {
        return Attributes$NestedMaterializationCancellationPolicy$.MODULE$.EagerCancellation();
    }

    @ApiMayChange
    public Attributes.NestedMaterializationCancellationPolicy nestedMaterializationCancellationPolicyPropagateToNested() {
        return Attributes$NestedMaterializationCancellationPolicy$.MODULE$.PropagateToNested();
    }

    public Attributes.NestedMaterializationCancellationPolicy nestedMaterializationCancellationPolicyDefault() {
        return Attributes$NestedMaterializationCancellationPolicy$.MODULE$.Default();
    }

    public int logLevelOff() {
        return Attributes$LogLevels$.MODULE$.Off();
    }

    public int logLevelError() {
        return Attributes$LogLevels$.MODULE$.Error();
    }

    public int logLevelWarning() {
        return Attributes$LogLevels$.MODULE$.Warning();
    }

    public int logLevelInfo() {
        return Attributes$LogLevels$.MODULE$.Info();
    }

    public int logLevelDebug() {
        return Attributes$LogLevels$.MODULE$.Debug();
    }

    public Attributes apply(Attributes.Attribute attribute) {
        Attributes.Attribute attribute2 = attribute;
        return this.apply(Nil$.MODULE$.$colon$colon(attribute2));
    }

    public List<Attributes.Attribute> apply$default$1() {
        return Nil$.MODULE$;
    }

    public Attributes none() {
        return none;
    }

    public Attributes asyncBoundary() {
        return asyncBoundary;
    }

    public Attributes name(String name) {
        if (name == null || name.isEmpty()) {
            return this.none();
        }
        return this.apply(new Attributes.Name(name));
    }

    public Attributes inputBuffer(int initial, int max) {
        return this.apply(new Attributes.InputBuffer(initial, max));
    }

    public Attributes createLogLevels(int onElement, int onFinish, int onFailure) {
        return this.logLevels(onElement, onFinish, onFailure);
    }

    public Attributes createLogLevels(int onElement) {
        return this.logLevels(onElement, this.logLevels$default$2(), this.logLevels$default$3());
    }

    public Attributes logLevels(int onElement, int onFinish, int onFailure) {
        return this.apply(new Attributes.LogLevels(onElement, onFinish, onFailure));
    }

    public int logLevels$default$1() {
        return Logging$.MODULE$.DebugLevel();
    }

    public int logLevels$default$2() {
        return Logging$.MODULE$.DebugLevel();
    }

    public int logLevels$default$3() {
        return Logging$.MODULE$.ErrorLevel();
    }

    public String extractName(TraversalBuilder builder, String string) {
        return builder.attributes().nameOrDefault(string);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Attributes$.class);
    }

    private Attributes$() {
    }
}

