/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.actor.ActorSystem;
import akka.annotation.ApiMayChange;
import akka.stream.Materializer;
import akka.stream.snapshot.MaterializerState$;
import akka.stream.snapshot.StreamSnapshot;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q;Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002uBa!R\u0001\u0005\u000211\u0005B\u0002,\u0002\t\u0003aq+A\tNCR,'/[1mSj,'o\u0015;bi\u0016T!!\u0003\u0006\u0002\u0011Mt\u0017\r]:i_RT!a\u0003\u0007\u0002\rM$(/Z1n\u0015\u0005i\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0011\u00035\t\u0001BA\tNCR,'/[1mSj,'o\u0015;bi\u0016\u001c\"!A\n\u0011\u0005Q9R\"A\u000b\u000b\u0003Y\tQa]2bY\u0006L!\u0001G\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tq\"A\btiJ,\u0017-\\*oCB\u001c\bn\u001c;t)\tib\u0006E\u0002\u001fC\rj\u0011a\b\u0006\u0003AU\t!bY8oGV\u0014(/\u001a8u\u0013\t\u0011sD\u0001\u0004GkR,(/\u001a\t\u0004I%ZS\"A\u0013\u000b\u0005\u0019:\u0013!C5n[V$\u0018M\u00197f\u0015\tAS#\u0001\u0006d_2dWm\u0019;j_:L!AK\u0013\u0003\u0007M+\u0017\u000f\u0005\u0002\u0011Y%\u0011Q\u0006\u0003\u0002\u000f'R\u0014X-Y7T]\u0006\u00048\u000f[8u\u0011\u0015y3\u00011\u00011\u0003\u0019\u0019\u0018p\u001d;f[B\u0011\u0011\u0007N\u0007\u0002e)\u00111\u0007D\u0001\u0006C\u000e$xN]\u0005\u0003kI\u00121\"Q2u_J\u001c\u0016p\u001d;f[\"\u00121a\u000e\t\u0003qmj\u0011!\u000f\u0006\u0003u1\t!\"\u00198o_R\fG/[8o\u0013\ta\u0014H\u0001\u0007Ba&l\u0015-_\"iC:<W\r\u0006\u0002\u001e}!)q\b\u0002a\u0001\u0001\u0006\u0019Q.\u0019;\u0011\u0005\u0005\u0013U\"\u0001\u0006\n\u0005\rS!\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\bF\u0001\u00038\u0003U\u0011X-];fgR4%o\\7TkB,'O^5t_J$\"aR'\u0015\u0005uA\u0005\"B%\u0006\u0001\bQ\u0015AA3d!\tq2*\u0003\u0002M?\t\u0001R\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0005\u0006\u001d\u0016\u0001\raT\u0001\u000bgV\u0004XM\u001d<jg>\u0014\bCA\u0019Q\u0013\t\t&G\u0001\u0005BGR|'OU3gQ\t)1\u000b\u0005\u00029)&\u0011Q+\u000f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\tsKF,Xm\u001d;Ge>l7\t[5mIR\u0011\u0001,\u0017\t\u0004=\u0005Z\u0003\"\u0002.\u0007\u0001\u0004y\u0015!B2iS2$\u0007F\u0001\u0004T\u0001")
public final class MaterializerState {
    @ApiMayChange
    public static Future<Seq<StreamSnapshot>> streamSnapshots(Materializer mat) {
        return MaterializerState$.MODULE$.streamSnapshots(mat);
    }

    @ApiMayChange
    public static Future<Seq<StreamSnapshot>> streamSnapshots(ActorSystem system) {
        return MaterializerState$.MODULE$.streamSnapshots(system);
    }
}

