/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPathExtractor$;
import akka.actor.Address;
import scala.Option;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002i\t!#Q2u_J\u0004\u0016\r\u001e5FqR\u0014\u0018m\u0019;pe*\u0011aaB\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u0001\"aC\u0001\u000e\u0003\u0015\u0011!#Q2u_J\u0004\u0016\r\u001e5FqR\u0014\u0018m\u0019;peN\u0019\u0011A\u0004\u000b\u0011\u0005=\u0011R\"\u0001\t\u000b\u0003E\tQa]2bY\u0006L!a\u0005\t\u0003\r\u0005s\u0017PU3g!\tYQ#\u0003\u0002\u0017\u000b\tI\u0001+\u0019;i+RLGn]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003)\tq!\u001e8baBd\u0017\u0010\u0006\u0002\u001coA\u0019q\u0002\b\u0010\n\u0005u\u0001\"AB(qi&|g\u000e\u0005\u0003\u0010?\u0005\"\u0013B\u0001\u0011\u0011\u0005\u0019!V\u000f\u001d7feA\u00111BI\u0005\u0003G\u0015\u0011q!\u00113ee\u0016\u001c8\u000fE\u0002&U1j\u0011A\n\u0006\u0003O!\n\u0011\"[7nkR\f'\r\\3\u000b\u0005%\u0002\u0012AC2pY2,7\r^5p]&\u00111F\n\u0002\t\u0013R,'/\u00192mKB\u0011Q\u0006\u000e\b\u0003]I\u0002\"a\f\t\u000e\u0003AR!!M\u0005\u0002\rq\u0012xn\u001c;?\u0013\t\u0019\u0004#\u0001\u0004Qe\u0016$WMZ\u0005\u0003kY\u0012aa\u0015;sS:<'BA\u001a\u0011\u0011\u0015A4\u00011\u0001-\u0003\u0011\tG\r\u001a:")
public final class ActorPathExtractor {
    public static Option<Tuple2<Address, Iterable<String>>> unapply(String addr) {
        return ActorPathExtractor$.MODULE$.unapply(addr);
    }
}

