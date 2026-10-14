/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function0;
import scala.Function1;
import scala.runtime.BoxedUnit;

public final class SetupStage$ {
    public static final SetupStage$ MODULE$ = new SetupStage$();

    public <T> InHandler delegateToSubOutlet(Function0<T> grab, GraphStageLogic.SubSourceOutlet<T> subOutlet) {
        return new InHandler(subOutlet, grab){
            private final GraphStageLogic.SubSourceOutlet subOutlet$1;
            private final Function0 grab$1;

            public void onPush() {
                this.subOutlet$1.push(this.grab$1.apply());
            }

            public void onUpstreamFinish() {
                this.subOutlet$1.complete();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.subOutlet$1.fail(ex);
            }
            {
                this.subOutlet$1 = subOutlet$1;
                this.grab$1 = grab$1;
                InHandler.$init$(this);
            }
        };
    }

    public <T> InHandler delegateToOutlet(Function1<T, BoxedUnit> push, Function0<BoxedUnit> complete, Function1<Throwable, BoxedUnit> fail, GraphStageLogic.SubSinkInlet<T> subInlet) {
        return new InHandler(push, subInlet, complete, fail){
            private final Function1 push$1;
            private final GraphStageLogic.SubSinkInlet subInlet$1;
            private final Function0 complete$1;
            private final Function1 fail$1;

            public void onPush() {
                this.push$1.apply(this.subInlet$1.grab());
            }

            public void onUpstreamFinish() {
                this.complete$1.apply$mcV$sp();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.fail$1.apply(ex);
            }
            {
                this.push$1 = push$1;
                this.subInlet$1 = subInlet$1;
                this.complete$1 = complete$1;
                this.fail$1 = fail$1;
                InHandler.$init$(this);
            }
        };
    }

    public <T> OutHandler delegateToSubInlet(GraphStageLogic.SubSinkInlet<T> subInlet) {
        return new OutHandler(subInlet){
            private final GraphStageLogic.SubSinkInlet subInlet$2;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onPull() {
                this.subInlet$2.pull();
            }

            public void onDownstreamFinish(Throwable cause) {
                this.subInlet$2.cancel(cause);
            }
            {
                this.subInlet$2 = subInlet$2;
                OutHandler.$init$(this);
            }
        };
    }

    public OutHandler delegateToInlet(Function0<BoxedUnit> pull, Function1<Throwable, BoxedUnit> cancel) {
        return new OutHandler(pull, cancel){
            private final Function0 pull$1;
            private final Function1 cancel$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onPull() {
                this.pull$1.apply$mcV$sp();
            }

            public void onDownstreamFinish(Throwable cause) {
                this.cancel$1.apply(cause);
            }
            {
                this.pull$1 = pull$1;
                this.cancel$1 = cancel$1;
                OutHandler.$init$(this);
            }
        };
    }

    private SetupStage$() {
    }
}

