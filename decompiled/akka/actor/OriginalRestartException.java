/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.OriginalRestartException$;
import akka.actor.PostRestartException;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\t\u0001d\u0014:jO&t\u0017\r\u001c*fgR\f'\u000f^#yG\u0016\u0004H/[8o\u0015\t1q!A\u0003bGR|'OC\u0001\t\u0003\u0011\t7n[1\u0004\u0001A\u00111\"A\u0007\u0002\u000b\tArJ]5hS:\fGNU3ti\u0006\u0014H/\u0012=dKB$\u0018n\u001c8\u0014\u0005\u0005q\u0001CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u0015\u00059QO\\1qa2LHC\u0001\r(!\ry\u0011dG\u0005\u00035A\u0011aa\u00149uS>t\u0007C\u0001\u000f%\u001d\ti\"E\u0004\u0002\u001fC5\tqD\u0003\u0002!\u0013\u00051AH]8pizJ\u0011!E\u0005\u0003GA\tq\u0001]1dW\u0006<W-\u0003\u0002&M\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003GAAQ\u0001K\u0002A\u0002%\n!!\u001a=\u0011\u0005-Q\u0013BA\u0016\u0006\u0005Q\u0001vn\u001d;SKN$\u0018M\u001d;Fq\u000e,\u0007\u000f^5p]\"\"\u0011!\f\u00192!\tya&\u0003\u00020!\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003!\"\u0001!\f\u00192\u0001")
public final class OriginalRestartException {
    public static Option<Throwable> unapply(PostRestartException ex) {
        return OriginalRestartException$.MODULE$.unapply(ex);
    }
}

