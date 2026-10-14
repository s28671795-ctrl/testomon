/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Resizer$;
import akka.routing.Routee;
import com.typesafe.config.Config;
import scala.Option;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3qa\u0002\u0005\u0011\u0002G\u0005Q\u0002C\u0003\u0015\u0001\u0019\u0005Q\u0003C\u0003\u001f\u0001\u0019\u0005qdB\u00032\u0011!\u0005!GB\u0003\b\u0011!\u00051\u0007C\u00035\t\u0011\u0005Q\u0007C\u00037\t\u0011\u0005qGA\u0004SKNL'0\u001a:\u000b\u0005%Q\u0011a\u0002:pkRLgn\u001a\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\b\u0011\u0005=\u0011R\"\u0001\t\u000b\u0003E\tQa]2bY\u0006L!a\u0005\t\u0003\r\u0005s\u0017PU3g\u0003=I7\u000fV5nK\u001a{'OU3tSj,GC\u0001\f\u001a!\tyq#\u0003\u0002\u0019!\t9!i\\8mK\u0006t\u0007\"\u0002\u000e\u0002\u0001\u0004Y\u0012AD7fgN\fw-Z\"pk:$XM\u001d\t\u0003\u001fqI!!\b\t\u0003\t1{gnZ\u0001\u0007e\u0016\u001c\u0018N_3\u0015\u0005\u0001\u001a\u0003CA\b\"\u0013\t\u0011\u0003CA\u0002J]RDQ\u0001\n\u0002A\u0002\u0015\nabY;se\u0016tGOU8vi\u0016,7\u000fE\u0002'W5j\u0011a\n\u0006\u0003Q%\n\u0011\"[7nkR\f'\r\\3\u000b\u0005)\u0002\u0012AC2pY2,7\r^5p]&\u0011Af\n\u0002\u000b\u0013:$W\r_3e'\u0016\f\bC\u0001\u00180\u001b\u0005A\u0011B\u0001\u0019\t\u0005\u0019\u0011v.\u001e;fK\u00069!+Z:ju\u0016\u0014\bC\u0001\u0018\u0005'\t!a\"\u0001\u0004=S:LGO\u0010\u000b\u0002e\u0005QaM]8n\u0007>tg-[4\u0015\u0005ab\u0004cA\b:w%\u0011!\b\u0005\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u00059\u0002\u0001\"B\u001f\u0007\u0001\u0004q\u0014\u0001\u00049be\u0016tGoQ8oM&<\u0007CA G\u001b\u0005\u0001%BA!C\u0003\u0019\u0019wN\u001c4jO*\u00111\tR\u0001\tif\u0004Xm]1gK*\tQ)A\u0002d_6L!a\u0012!\u0003\r\r{gNZ5h\u0001")
public interface Resizer {
    public static Option<Resizer> fromConfig(Config parentConfig) {
        return Resizer$.MODULE$.fromConfig(parentConfig);
    }

    public boolean isTimeForResize(long var1);

    public int resize(IndexedSeq<Routee> var1);
}

