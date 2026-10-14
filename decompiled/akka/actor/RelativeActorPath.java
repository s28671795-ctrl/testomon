/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.RelativeActorPath$;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002i\t\u0011CU3mCRLg/Z!di>\u0014\b+\u0019;i\u0015\t1q!A\u0003bGR|'OC\u0001\t\u0003\u0011\t7n[1\u0004\u0001A\u00111\"A\u0007\u0002\u000b\t\t\"+\u001a7bi&4X-Q2u_J\u0004\u0016\r\u001e5\u0014\u0007\u0005qA\u0003\u0005\u0002\u0010%5\t\u0001CC\u0001\u0012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0002C\u0001\u0004B]f\u0014VM\u001a\t\u0003\u0017UI!AF\u0003\u0003\u0013A\u000bG\u000f[+uS2\u001c\u0018A\u0002\u001fj]&$h\bF\u0001\u000b\u0003\u001d)h.\u00199qYf$\"aG\u0019\u0011\u0007=ab$\u0003\u0002\u001e!\t1q\n\u001d;j_:\u00042a\b\u0013'\u001b\u0005\u0001#BA\u0011#\u0003%IW.\\;uC\ndWM\u0003\u0002$!\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005\u0015\u0002#aA*fcB\u0011qE\f\b\u0003Q1\u0002\"!\u000b\t\u000e\u0003)R!aK\u0005\u0002\rq\u0012xn\u001c;?\u0013\ti\u0003#\u0001\u0004Qe\u0016$WMZ\u0005\u0003_A\u0012aa\u0015;sS:<'BA\u0017\u0011\u0011\u0015\u00114\u00011\u0001'\u0003\u0011\tG\r\u001a:")
public final class RelativeActorPath {
    public static Option<Seq<String>> unapply(String addr) {
        return RelativeActorPath$.MODULE$.unapply(addr);
    }
}

