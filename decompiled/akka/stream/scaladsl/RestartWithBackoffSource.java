/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.RestartSettings;
import akka.stream.SourceShape;
import akka.stream.scaladsl.RestartWithBackoffLogic;
import akka.stream.scaladsl.Source;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$EagerTerminateOutput$;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3A!\u0003\u0006\u0007#!A1\u0006\u0001B\u0001B\u0003%A\u0006\u0003\u00058\u0001\t\u0005\t\u0015!\u00039\u0011!Y\u0004A!A!\u0002\u0013a\u0004\"B \u0001\t\u0003\u0001\u0005b\u0002&\u0001\u0005\u0004%\ta\u0013\u0005\u0007\u001f\u0002\u0001\u000b\u0011\u0002'\t\u000bA\u0003A\u0011I)\t\u000bI\u0003A\u0011I*\u00031I+7\u000f^1si^KG\u000f\u001b\"bG.|gMZ*pkJ\u001cWM\u0003\u0002\f\u0019\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u000e\u001d\u000511\u000f\u001e:fC6T\u0011aD\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0005Iy2C\u0001\u0001\u0014!\r!r#G\u0007\u0002+)\u0011a\u0003D\u0001\u0006gR\fw-Z\u0005\u00031U\u0011!b\u0012:ba\"\u001cF/Y4f!\rQ2$H\u0007\u0002\u0019%\u0011A\u0004\u0004\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0002\u001f?1\u0001A!\u0002\u0011\u0001\u0005\u0004\t#!\u0001+\u0012\u0005\tB\u0003CA\u0012'\u001b\u0005!#\"A\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d\"#a\u0002(pi\"Lgn\u001a\t\u0003G%J!A\u000b\u0013\u0003\u0007\u0005s\u00170A\u0007t_V\u00148-\u001a$bGR|'/\u001f\t\u0004G5z\u0013B\u0001\u0018%\u0005%1UO\\2uS>t\u0007\u0007\r\u00021kA!\u0011GM\u000f5\u001b\u0005Q\u0011BA\u001a\u000b\u0005\u0019\u0019v.\u001e:dKB\u0011a$\u000e\u0003\nm\u0005\t\t\u0011!A\u0003\u0002\u0005\u00121a\u0018\u00138\u0003!\u0019X\r\u001e;j]\u001e\u001c\bC\u0001\u000e:\u0013\tQDBA\bSKN$\u0018M\u001d;TKR$\u0018N\\4t\u00039yg\u000e\\=P]\u001a\u000b\u0017\u000e\\;sKN\u0004\"aI\u001f\n\u0005y\"#a\u0002\"p_2,\u0017M\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\t\u0005\u0013\u0005*\u0013\t\u0004c\u0001i\u0002\"B\u0016\u0005\u0001\u0004\u0019\u0005cA\u0012.\tB\u0012Qi\u0012\t\u0005cIjb\t\u0005\u0002\u001f\u000f\u0012IaGQA\u0001\u0002\u0003\u0015\t!\t\u0005\u0006o\u0011\u0001\r\u0001\u000f\u0005\u0006w\u0011\u0001\r\u0001P\u0001\u0004_V$X#\u0001'\u0011\u0007iiU$\u0003\u0002O\u0019\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\t\u0011$A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u0001+X!\r\tT+G\u0005\u0003-*\u0011qCU3ti\u0006\u0014HoV5uQ\n\u000b7m[8gM2{w-[2\t\u000baC\u0001\u0019A-\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0005iQ\u0016BA.\r\u0005)\tE\u000f\u001e:jEV$Xm\u001d")
public final class RestartWithBackoffSource<T>
extends GraphStage<SourceShape<T>> {
    public final Function0<Source<T, ?>> akka$stream$scaladsl$RestartWithBackoffSource$$sourceFactory;
    public final RestartSettings akka$stream$scaladsl$RestartWithBackoffSource$$settings;
    public final boolean akka$stream$scaladsl$RestartWithBackoffSource$$onlyOnFailures;
    private final Outlet<T> out;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return new SourceShape<T>(this.out());
    }

    @Override
    public RestartWithBackoffLogic<SourceShape<T>> createLogic(Attributes inheritedAttributes) {
        return new RestartWithBackoffLogic<SourceShape<T>>(this, inheritedAttributes){
            private final /* synthetic */ RestartWithBackoffSource $outer;
            private final Attributes inheritedAttributes$1;

            public Class<?> logSource() {
                return this.$outer.getClass();
            }

            public void startGraph() {
                GraphStageLogic.SubSinkInlet<T> sinkIn = this.createSubInlet(this.$outer.out());
                this.subFusingMaterializer().materialize(this.$outer.akka$stream$scaladsl$RestartWithBackoffSource$$sourceFactory.apply().to(sinkIn.sink()), this.inheritedAttributes$1);
                if (this.isAvailable(this.$outer.out())) {
                    sinkIn.pull();
                    return;
                }
            }

            public void backoff() {
                this.setHandler(this.$outer.out(), GraphStageLogic$EagerTerminateOutput$.MODULE$);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super("Source", $outer.shape(), inheritedAttributes$1, $outer.akka$stream$scaladsl$RestartWithBackoffSource$$settings, $outer.akka$stream$scaladsl$RestartWithBackoffSource$$onlyOnFailures);
                this.backoff();
            }
        };
    }

    public RestartWithBackoffSource(Function0<Source<T, ?>> sourceFactory, RestartSettings settings, boolean onlyOnFailures) {
        this.akka$stream$scaladsl$RestartWithBackoffSource$$sourceFactory = sourceFactory;
        this.akka$stream$scaladsl$RestartWithBackoffSource$$settings = settings;
        this.akka$stream$scaladsl$RestartWithBackoffSource$$onlyOnFailures = onlyOnFailures;
        this.out = Outlet$.MODULE$.apply("RestartWithBackoffSource.out");
    }
}

