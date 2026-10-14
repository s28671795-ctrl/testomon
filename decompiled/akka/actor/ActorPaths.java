/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorPaths$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQaK\u0001\u0005\u00061BQaK\u0001\u0005\u0006IBQAN\u0001\u0005\u0006]\n!\"Q2u_J\u0004\u0016\r\u001e5t\u0015\tI!\"A\u0003bGR|'OC\u0001\f\u0003\u0011\t7n[1\u0004\u0001A\u0011a\"A\u0007\u0002\u0011\tQ\u0011i\u0019;peB\u000bG\u000f[:\u0014\u0005\u0005\t\u0002C\u0001\n\u0016\u001b\u0005\u0019\"\"\u0001\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Y\u0019\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u001b\u0005QaM]8n'R\u0014\u0018N\\4\u0015\u0005mq\u0002C\u0001\b\u001d\u0013\ti\u0002BA\u0005BGR|'\u000fU1uQ\")qd\u0001a\u0001A\u0005\t1\u000f\u0005\u0002\"Q9\u0011!E\n\t\u0003GMi\u0011\u0001\n\u0006\u0003K1\ta\u0001\u0010:p_Rt\u0014BA\u0014\u0014\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011F\u000b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001d\u001a\u0012a\u0005<bY&$\u0017\r^3QCRDW\t\\3nK:$HCA\u00171!\t\u0011b&\u0003\u00020'\t!QK\\5u\u0011\u0015\tD\u00011\u0001!\u0003\u001d)G.Z7f]R$2!L\u001a5\u0011\u0015\tT\u00011\u0001!\u0011\u0015)T\u00011\u0001!\u0003!1W\u000f\u001c7QCRD\u0017AE5t-\u0006d\u0017\u000e\u001a)bi\",E.Z7f]R$\"\u0001O\u001e\u0011\u0005II\u0014B\u0001\u001e\u0014\u0005\u001d\u0011un\u001c7fC:DQa\b\u0004A\u0002\u0001\u0002")
public final class ActorPaths {
    public static boolean isValidPathElement(String s2) {
        return ActorPaths$.MODULE$.isValidPathElement(s2);
    }

    public static void validatePathElement(String element, String fullPath) {
        ActorPaths$.MODULE$.validatePathElement(element, fullPath);
    }

    public static void validatePathElement(String element) {
        ActorPaths$.MODULE$.validatePathElement(element);
    }

    public static ActorPath fromString(String s2) {
        return ActorPaths$.MODULE$.fromString(s2);
    }
}

