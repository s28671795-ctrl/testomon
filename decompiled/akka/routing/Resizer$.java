/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.DefaultResizer$;
import akka.routing.OptimalSizeExploringResizer$;
import akka.routing.Resizer;
import akka.routing.ResizerInitializationException;
import com.typesafe.config.Config;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple2$mcZZ$sp;

public final class Resizer$ {
    public static final Resizer$ MODULE$ = new Resizer$();

    public Option<Resizer> fromConfig(Config parentConfig) {
        Config defaultResizerConfig = parentConfig.getConfig("resizer");
        Config metricsBasedResizerConfig = parentConfig.getConfig("optimal-size-exploring-resizer");
        Tuple2$mcZZ$sp tuple2$mcZZ$sp = new Tuple2$mcZZ$sp(defaultResizerConfig.getBoolean("enabled"), metricsBasedResizerConfig.getBoolean("enabled"));
        if (tuple2$mcZZ$sp != null) {
            boolean bl = ((Tuple2)tuple2$mcZZ$sp)._1$mcZ$sp();
            boolean bl2 = ((Tuple2)tuple2$mcZZ$sp)._2$mcZ$sp();
            if (bl && !bl2) {
                return new Some<Resizer>(DefaultResizer$.MODULE$.apply(defaultResizerConfig));
            }
        }
        if (tuple2$mcZZ$sp != null) {
            boolean bl = ((Tuple2)tuple2$mcZZ$sp)._1$mcZ$sp();
            boolean bl3 = ((Tuple2)tuple2$mcZZ$sp)._2$mcZ$sp();
            if (!bl && bl3) {
                return new Some<Resizer>(OptimalSizeExploringResizer$.MODULE$.apply(metricsBasedResizerConfig));
            }
        }
        if (tuple2$mcZZ$sp != null) {
            boolean bl = ((Tuple2)tuple2$mcZZ$sp)._1$mcZ$sp();
            boolean bl4 = ((Tuple2)tuple2$mcZZ$sp)._2$mcZ$sp();
            if (!bl && !bl4) {
                return None$.MODULE$;
            }
        }
        if (tuple2$mcZZ$sp != null) {
            boolean bl = ((Tuple2)tuple2$mcZZ$sp)._1$mcZ$sp();
            boolean bl5 = ((Tuple2)tuple2$mcZZ$sp)._2$mcZ$sp();
            if (bl && bl5) {
                throw new ResizerInitializationException("cannot enable both resizer and optimal-size-exploring-resizer", null);
            }
        }
        throw new MatchError(tuple2$mcZZ$sp);
    }

    private Resizer$() {
    }
}

