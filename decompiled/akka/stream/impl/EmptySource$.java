/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.runtime.Nothing$;

@InternalApi
public final class EmptySource$
extends GraphStage<SourceShape<Nothing$>> {
    public static final EmptySource$ MODULE$ = new EmptySource$();
    private static final Outlet<Nothing$> out = Outlet$.MODULE$.apply("EmptySource.out");
    private static final SourceShape<Nothing$> shape = new SourceShape<Nothing$>(MODULE$.out());

    public Outlet<Nothing$> out() {
        return out;
    }

    @Override
    public SourceShape<Nothing$> shape() {
        return shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.emptySource();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(){

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void preStart() {
                this.completeStage();
            }

            public void onPull() {
                this.completeStage();
            }
            {
                OutHandler.$init$(this);
                this.setHandler(EmptySource$.MODULE$.out(), this);
            }
        };
    }

    public String toString() {
        return "EmptySource";
    }

    private EmptySource$() {
    }
}

