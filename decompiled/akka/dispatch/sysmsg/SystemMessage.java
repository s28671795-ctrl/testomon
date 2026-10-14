/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.PossiblyHarmful;
import akka.annotation.InternalStableApi;
import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3\u0001BB\u0004\u0011\u0002\u0007\u00052\"\u0004\u0005\u0006O\u0001!\t\u0001\u000b\u0005\u000bY\u0001\u0001\r\u00111A\u0005\u0002\u001di\u0003B\u0003\u001b\u0001\u0001\u0004\u0005\r\u0011\"\u0001\bk!)\u0001\b\u0001C\u0001Q!)\u0011\b\u0001C\u0001u\ti1+_:uK6lUm]:bO\u0016T!\u0001C\u0005\u0002\rML8/\\:h\u0015\tQ1\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0011\u0001B1lW\u0006\u001cB\u0001\u0001\b\u00155A\u0011qBE\u0007\u0002!)\t\u0011#A\u0003tG\u0006d\u0017-\u0003\u0002\u0014!\t1\u0011I\\=SK\u001a\u0004\"!\u0006\r\u000e\u0003YQ!aF\u0006\u0002\u000b\u0005\u001cGo\u001c:\n\u0005e1\"a\u0004)pgNL'\r\\=ICJlg-\u001e7\u0011\u0005m!cB\u0001\u000f#\u001d\ti\u0012%D\u0001\u001f\u0015\ty\u0002%\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\t\u0012BA\u0012\u0011\u0003\u001d\u0001\u0018mY6bO\u0016L!!\n\u0014\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\r\u0002\u0012A\u0002\u0013j]&$H\u0005F\u0001*!\ty!&\u0003\u0002,!\t!QK\\5u\u0003\u0011qW\r\u001f;\u0016\u00039\u0002\"a\f\u0001\u000e\u0003\u001dA#AA\u0019\u0011\u0005=\u0011\u0014BA\u001a\u0011\u0005%!(/\u00198tS\u0016tG/\u0001\u0005oKb$x\fJ3r)\tIc\u0007C\u00048\u0007\u0005\u0005\t\u0019\u0001\u0018\u0002\u0007a$\u0013'\u0001\u0004v]2Lgn[\u0001\tk:d\u0017N\\6fIV\t1\b\u0005\u0002\u0010y%\u0011Q\b\u0005\u0002\b\u0005>|G.Z1oS1\u0001q(Q\"F\u000f&[UjT)T\u0013\t\u0001uA\u0001\u0004De\u0016\fG/Z\u0005\u0003\u0005\u001e\u0011a\u0003R3bi\"<\u0016\r^2i\u001d>$\u0018NZ5dCRLwN\\\u0005\u0003\t\u001e\u0011aAR1jY\u0016$'B\u0001$\b\u0003%qu.T3tg\u0006<W-\u0003\u0002I\u000f\tA!+Z2sK\u0006$X-\u0003\u0002K\u000f\t1!+Z:v[\u0016L!\u0001T\u0004\u0003\u0013M+\b/\u001a:wSN,\u0017B\u0001(\b\u0005\u001d\u0019Vo\u001d9f]\u0012L!\u0001U\u0004\u0003\u0013Q+'/\\5oCR,\u0017B\u0001*\b\u0005\u001d)fn^1uG\"L!\u0001V\u0004\u0003\u000b]\u000bGo\u00195)\u0005\u00011\u0006CA,[\u001b\u0005A&BA-\f\u0003)\tgN\\8uCRLwN\\\u0005\u00037b\u0013\u0011#\u00138uKJt\u0017\r\\*uC\ndW-\u00119j\u0001")
@InternalStableApi
public interface SystemMessage
extends PossiblyHarmful,
Serializable {
    public SystemMessage next();

    public void next_$eq(SystemMessage var1);

    public static /* synthetic */ void unlink$(SystemMessage $this) {
        $this.unlink();
    }

    default public void unlink() {
        this.next_$eq(null);
    }

    public static /* synthetic */ boolean unlinked$(SystemMessage $this) {
        return $this.unlinked();
    }

    default public boolean unlinked() {
        return this.next() == null;
    }

    public static void $init$(SystemMessage $this) {
    }
}

