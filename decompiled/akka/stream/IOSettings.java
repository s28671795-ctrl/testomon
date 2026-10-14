/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.stream.IOSettings$;
import com.typesafe.config.Config;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ur!\u0002\r\u001a\u0011\u0003qb!\u0002\u0011\u001a\u0011\u0003\t\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"\u0002\u0016\u0002\t\u0003Y\u0003B\u0002\u0016\u0002\t\u0003\t\t\u0001\u0003\u0004+\u0003\u0011\u0005\u00111\u0004\u0005\b\u0003C\tA\u0011AA\u0012\u0011\u001d\t\t#\u0001C\u0001\u0003SAq!!\t\u0002\t\u0003\tyC\u0002\u0003!3\ti\u0003\u0002\u0003\u0018\n\u0005\u000b\u0007I\u0011A\u0018\t\u0011uJ!\u0011!Q\u0001\nAB\u0001bP\u0005\u0003\u0006\u0004%\ta\f\u0005\t\u0001&\u0011\t\u0011)A\u0005a!)\u0001&\u0003C\u0005\u0003\")\u0001&\u0003C\u0001\t\")q)\u0003C\u0001\u0011\")1*\u0003C\u0001\u0019\")a*\u0003C\u0005\u001f\"9!+CI\u0001\n\u0013\u0019\u0006b\u00020\n#\u0003%Ia\u0015\u0005\u0006?&!\t\u0005\u0019\u0005\u0006S&!\tE\u001b\u0005\u0006W&!\t\u0005\\\u0001\u000b\u0013>\u001bV\r\u001e;j]\u001e\u001c(B\u0001\u000e\u001c\u0003\u0019\u0019HO]3b[*\tA$\u0001\u0003bW.\f7\u0001\u0001\t\u0003?\u0005i\u0011!\u0007\u0002\u000b\u0013>\u001bV\r\u001e;j]\u001e\u001c8CA\u0001#!\t\u0019c%D\u0001%\u0015\u0005)\u0013!B:dC2\f\u0017BA\u0014%\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AH\u0001\u0006CB\u0004H.\u001f\u000b\u0003YU\u0004\"aH\u0005\u0014\u0005%\u0011\u0013A\u0005;da^\u0013\u0018\u000e^3Ck\u001a4WM]*ju\u0016,\u0012\u0001\r\t\u0003GEJ!A\r\u0013\u0003\u0007%sG\u000f\u000b\u0004\u000bi]B$h\u000f\t\u0003GUJ!A\u000e\u0013\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003e\n1+V:fA\u0005$HO]5ckR,\u0007e\n+da\u0006#HO]5ckR,7O\f+da^\u0013\u0018\u000e^3Ck\u001a4WM]*ju\u0016<\u0003\u0005^8!e\u0016\fG\r\t;iK\u0002\u001awN\\2sKR,\u0007e]3ui&tw\r\t<bYV,\u0017!B:j]\u000e,\u0017%\u0001\u001f\u0002\u000bIrcG\f\u0019\u0002'Q\u001c\u0007o\u0016:ji\u0016\u0014UO\u001a4feNK'0\u001a\u0011)\r-!t\u0007\u000f\u001e<\u00039\u0019w.\u00197fg\u000e,wK]5uKN\fqbY8bY\u0016\u001c8-Z,sSR,7\u000f\t\u000b\u0004Y\t\u001b\u0005\"\u0002\u0018\u000f\u0001\u0004\u0001\u0004\"B \u000f\u0001\u0004\u0001DC\u0001\u0017F\u0011\u0015qs\u00021\u00011Q\u0019yAg\u000e\u001d;w\u00051r/\u001b;i)\u000e\u0004xK]5uK\n+hMZ3s'&TX\r\u0006\u0002-\u0013\")!\n\u0005a\u0001a\u0005)a/\u00197vK\u0006\u0011r/\u001b;i\u0007>\fG.Z:dK^\u0013\u0018\u000e^3t)\taS\nC\u0003K#\u0001\u0007\u0001'\u0001\u0003d_BLHc\u0001\u0017Q#\"9aF\u0005I\u0001\u0002\u0004\u0001\u0004bB \u0013!\u0003\u0005\r\u0001M\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005!&F\u0001\u0019VW\u00051\u0006CA,]\u001b\u0005A&BA-[\u0003%)hn\u00195fG.,GM\u0003\u0002\\I\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005uC&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014AB3rk\u0006d7\u000f\u0006\u0002bIB\u00111EY\u0005\u0003G\u0012\u0012qAQ8pY\u0016\fg\u000eC\u0003f+\u0001\u0007a-A\u0003pi\",'\u000f\u0005\u0002$O&\u0011\u0001\u000e\n\u0002\u0004\u0003:L\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003A\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002[B\u0011an]\u0007\u0002_*\u0011\u0001/]\u0001\u0005Y\u0006twMC\u0001s\u0003\u0011Q\u0017M^1\n\u0005Q|'AB*ue&tw\rC\u0003w\u0007\u0001\u0007q/\u0001\u0004tsN$X-\u001c\t\u0003qnl\u0011!\u001f\u0006\u0003un\tQ!Y2u_JL!\u0001`=\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\u001c\u0015\u0007\u0007Q:dPO\u001e\"\u0003}\f!/V:fAM,G\u000f^5oO\u0002:\u0013m[6b]M$(/Z1n]5\fG/\u001a:jC2L'0\u001a:/S>tCo\u00199/oJLG/Z\u0017ck\u001a4WM]\u0017tSj,w\u0005I8sA\u0005$HO]5ckR,\u0007\u0005V2q\u0003R$(/\u001b2vi\u0016\u001chf\u001e:ji\u0016\u0014UO\u001a4feNK'0\u001a\u0011j]N$X-\u00193\u0015\u00071\n\u0019\u0001C\u0004\u0002\u0006\u0011\u0001\r!a\u0002\u0002\r\r|gNZ5h!\u0011\tI!!\u0006\u000e\u0005\u0005-!\u0002BA\u0003\u0003\u001bQA!a\u0004\u0002\u0012\u0005AA/\u001f9fg\u00064WM\u0003\u0002\u0002\u0014\u0005\u00191m\\7\n\t\u0005]\u00111\u0002\u0002\u0007\u0007>tg-[4)\r\u0011!tG \u001e<)\ra\u0013Q\u0004\u0005\u0006]\u0015\u0001\r\u0001\r\u0015\u0007\u000bQ:dPO\u001e\u0002\r\r\u0014X-\u0019;f)\ra\u0013Q\u0005\u0005\b\u0003\u000b1\u0001\u0019AA\u0004Q\u00191Ag\u000e@;wQ\u0019A&a\u000b\t\u000bY<\u0001\u0019A<)\r\u001d!tG \u001e<)\ra\u0013\u0011\u0007\u0005\u0006]!\u0001\r\u0001\r\u0015\u0007\u0011Q:dPO\u001e")
public final class IOSettings {
    private final int tcpWriteBufferSize;
    private final int coalesceWrites;

    public static IOSettings create(int tcpWriteBufferSize) {
        return IOSettings$.MODULE$.create(tcpWriteBufferSize);
    }

    public static IOSettings create(ActorSystem system) {
        return IOSettings$.MODULE$.create(system);
    }

    public static IOSettings create(Config config) {
        return IOSettings$.MODULE$.create(config);
    }

    public static IOSettings apply(int tcpWriteBufferSize) {
        return IOSettings$.MODULE$.apply(tcpWriteBufferSize);
    }

    public static IOSettings apply(Config config) {
        return IOSettings$.MODULE$.apply(config);
    }

    public static IOSettings apply(ActorSystem system) {
        return IOSettings$.MODULE$.apply(system);
    }

    public int tcpWriteBufferSize() {
        return this.tcpWriteBufferSize;
    }

    public int coalesceWrites() {
        return this.coalesceWrites;
    }

    public IOSettings withTcpWriteBufferSize(int value) {
        return this.copy(value, this.copy$default$2());
    }

    public IOSettings withCoalesceWrites(int value) {
        int x$1 = value;
        int x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private IOSettings copy(int tcpWriteBufferSize, int coalesceWrites) {
        return new IOSettings(tcpWriteBufferSize, coalesceWrites);
    }

    private int copy$default$1() {
        return this.tcpWriteBufferSize();
    }

    private int copy$default$2() {
        return this.coalesceWrites();
    }

    public boolean equals(Object other) {
        Object object = other;
        if (object instanceof IOSettings) {
            IOSettings iOSettings = (IOSettings)object;
            return iOSettings.tcpWriteBufferSize() == this.tcpWriteBufferSize() && iOSettings.coalesceWrites() == this.coalesceWrites();
        }
        return false;
    }

    public int hashCode() {
        return 31 * this.tcpWriteBufferSize() + this.coalesceWrites();
    }

    public String toString() {
        return new StringBuilder(13).append("IoSettings(").append(this.tcpWriteBufferSize()).append(",").append(this.coalesceWrites()).append(")").toString();
    }

    public IOSettings(int tcpWriteBufferSize, int coalesceWrites) {
        this.tcpWriteBufferSize = tcpWriteBufferSize;
        this.coalesceWrites = coalesceWrites;
    }

    public IOSettings(int tcpWriteBufferSize) {
        this(tcpWriteBufferSize, 10);
    }
}

