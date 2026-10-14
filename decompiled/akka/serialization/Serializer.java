/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import java.io.NotSerializableException;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d4q\u0001C\u0005\u0011\u0002\u0007\u0005a\u0002C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0003\u001b\u0001\u0019\u00051\u0004C\u0003 \u0001\u0019\u0005\u0001\u0005C\u0003*\u0001\u0019\u0005!\u0006C\u0003/\u0001\u0019\u0005q\u0006C\u0003/\u0001\u0011\u00151\fC\u0003/\u0001\u0011\u0015QL\u0001\u0006TKJL\u0017\r\\5{KJT!AC\u0006\u0002\u001bM,'/[1mSj\fG/[8o\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001cE\u0007\u0002#)\t!#A\u0003tG\u0006d\u0017-\u0003\u0002\u0015#\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#A\f\u0011\u0005AA\u0012BA\r\u0012\u0005\u0011)f.\u001b;\u0002\u0015%$WM\u001c;jM&,'/F\u0001\u001d!\t\u0001R$\u0003\u0002\u001f#\t\u0019\u0011J\u001c;\u0002\u0011Q|')\u001b8bef$\"!I\u0014\u0011\u0007A\u0011C%\u0003\u0002$#\t)\u0011I\u001d:bsB\u0011\u0001#J\u0005\u0003ME\u0011AAQ=uK\")\u0001f\u0001a\u0001\u001f\u0005\tq.A\bj]\u000edW\u000fZ3NC:Lg-Z:u+\u0005Y\u0003C\u0001\t-\u0013\ti\u0013CA\u0004C_>dW-\u00198\u0002\u0015\u0019\u0014x.\u001c\"j]\u0006\u0014\u0018\u0010F\u0002\u0010aIBQ!M\u0003A\u0002\u0005\nQAY=uKNDQaM\u0003A\u0002Q\n\u0001\"\\1oS\u001a,7\u000f\u001e\t\u0004!U:\u0014B\u0001\u001c\u0012\u0005\u0019y\u0005\u000f^5p]B\u0012\u0001(\u0012\t\u0004s\u0001\u001beB\u0001\u001e?!\tY\u0014#D\u0001=\u0015\tiT\"\u0001\u0004=e>|GOP\u0005\u0003\u007fE\ta\u0001\u0015:fI\u00164\u0017BA!C\u0005\u0015\u0019E.Y:t\u0015\ty\u0014\u0003\u0005\u0002E\u000b2\u0001A!\u0003$3\u0003\u0003\u0005\tQ!\u0001H\u0005\ryF%M\t\u0003\u0011.\u0003\"\u0001E%\n\u0005)\u000b\"a\u0002(pi\"Lgn\u001a\t\u0003!1K!!T\t\u0003\u0007\u0005s\u0017\u0010K\u0002\u0006\u001fj\u00032\u0001\u0005)S\u0013\t\t\u0016C\u0001\u0004uQJ|wo\u001d\t\u0003'bk\u0011\u0001\u0016\u0006\u0003+Z\u000b!![8\u000b\u0003]\u000bAA[1wC&\u0011\u0011\f\u0016\u0002\u0019\u001d>$8+\u001a:jC2L'0\u00192mK\u0016C8-\u001a9uS>t7%\u0001*\u0015\u0005=a\u0006\"B\u0019\u0007\u0001\u0004\tCcA\b_?\")\u0011g\u0002a\u0001C!)\u0001m\u0002a\u0001C\u0006)1\r\\1{uB\u0012!\r\u001a\t\u0004s\u0001\u001b\u0007C\u0001#e\t%)w,!A\u0001\u0002\u000b\u0005qIA\u0002`IIB3aB([\u0001")
public interface Serializer {
    public int identifier();

    public byte[] toBinary(Object var1);

    public boolean includeManifest();

    public Object fromBinary(byte[] var1, Option<Class<?>> var2) throws NotSerializableException;

    public static /* synthetic */ Object fromBinary$(Serializer $this, byte[] bytes) {
        return $this.fromBinary(bytes);
    }

    default public Object fromBinary(byte[] bytes) {
        return this.fromBinary(bytes, None$.MODULE$);
    }

    public static /* synthetic */ Object fromBinary$(Serializer $this, byte[] bytes, Class clazz) {
        return $this.fromBinary(bytes, clazz);
    }

    default public Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return this.fromBinary(bytes, Option$.MODULE$.apply(clazz));
    }

    public static void $init$(Serializer $this) {
    }
}

