/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.StashSupport;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A3qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003\u001b\u0001\u0011\u00051\u0004C\u0003 \u0001\u0011\u0005\u0003\u0005C\u0003@\u0001\u0011\u00053\u0004C\u0006B\u0001A\u0005\u0019\u0011!A\u0005\n\tk\u0005b\u0003(\u0001!\u0003\r\t\u0011!C\u00057=\u0013\u0011#\u00168sKN$(/[2uK\u0012\u001cF/Y:i\u0015\tA\u0011\"A\u0003bGR|'OC\u0001\u000b\u0003\u0011\t7n[1\u0004\u0001M!\u0001!D\n\u0018!\tq\u0011#D\u0001\u0010\u0015\u0005\u0001\u0012!B:dC2\f\u0017B\u0001\n\u0010\u0005\u0019\te.\u001f*fMB\u0011A#F\u0007\u0002\u000f%\u0011ac\u0002\u0002\u0006\u0003\u000e$xN\u001d\t\u0003)aI!!G\u0004\u0003\u0019M#\u0018m\u001d5TkB\u0004xN\u001d;\u0002\r\u0011Jg.\u001b;%)\u0005a\u0002C\u0001\b\u001e\u0013\tqrB\u0001\u0003V]&$\u0018A\u00039sKJ+7\u000f^1siR\u0019A$I\u0018\t\u000b\t\u0012\u0001\u0019A\u0012\u0002\rI,\u0017m]8o!\t!CF\u0004\u0002&U9\u0011a%K\u0007\u0002O)\u0011\u0001fC\u0001\u0007yI|w\u000e\u001e \n\u0003AI!aK\b\u0002\u000fA\f7m[1hK&\u0011QF\f\u0002\n)\"\u0014xn^1cY\u0016T!aK\b\t\u000bA\u0012\u0001\u0019A\u0019\u0002\u000f5,7o]1hKB\u0019aB\r\u001b\n\u0005Mz!AB(qi&|g\u000e\u0005\u0002\u000fk%\u0011ag\u0004\u0002\u0004\u0003:L\bf\u0001\u00029}A\u0019a\"O\u001e\n\u0005iz!A\u0002;ie><8\u000f\u0005\u0002%y%\u0011QH\f\u0002\n\u000bb\u001cW\r\u001d;j_:\u001c\u0013aO\u0001\ta>\u001cHo\u0015;pa\"\u001a1\u0001\u000f \u0002!M,\b/\u001a:%aJ,'+Z:uCJ$Hc\u0001\u000fD\u0017\")!\u0005\u0002a\u0001G!\u00121)\u0012\t\u0003\r&k\u0011a\u0012\u0006\u0003\u0011&\tA!\u001e;jY&\u0011!j\u0012\u0002\u0007k:,8/\u001a3\t\u000bA\"\u0001\u0019A\u0019)\u0005-+\u0015BA\u0010\u0016\u00039\u0019X\u000f]3sIA|7\u000f^*u_BL!aP\u000b")
public interface UnrestrictedStash
extends Actor,
StashSupport {
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable var1, Option var2);

    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop();

    public static /* synthetic */ void preRestart$(UnrestrictedStash $this, Throwable reason, Option message) {
        $this.preRestart(reason, message);
    }

    @Override
    default public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        try {
            this.unstashAll();
        }
        finally {
            this.akka$actor$UnrestrictedStash$$super$preRestart(reason, message);
        }
    }

    public static /* synthetic */ void postStop$(UnrestrictedStash $this) {
        $this.postStop();
    }

    @Override
    default public void postStop() throws Exception {
        try {
            this.unstashAll();
        }
        finally {
            this.akka$actor$UnrestrictedStash$$super$postStop();
        }
    }

    public static void $init$(UnrestrictedStash $this) {
    }
}

