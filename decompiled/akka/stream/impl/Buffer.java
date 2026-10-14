/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.stream.Attributes;
import akka.stream.impl.Buffer$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y4\u0001\"\u0006\f\u0011\u0002G\u0005!\u0004\b\u0005\u0006I\u00011\tA\n\u0005\u0006U\u00011\tA\n\u0005\u0006W\u00011\t\u0001\f\u0005\u0006a\u00011\t\u0001\f\u0005\u0006c\u00011\t\u0001\f\u0005\u0006e\u00011\ta\r\u0005\u0006\t\u00021\t!\u0012\u0005\u0006\r\u00021\t!\u0012\u0005\u0006\u000f\u00021\t\u0001\u0013\u0005\u0006\u0013\u00021\t\u0001\u0013\u0005\u0006\u0015\u00021\t\u0001S\u0004\u0007%ZA\tAG*\u0007\rU1\u0002\u0012\u0001\u000eV\u0011\u00151V\u0002\"\u0001X\u0011\u001dAVB1A\u0005\u0002\u0019Ba!W\u0007!\u0002\u00139\u0003b\u0002.\u000e\u0005\u0004%\tA\n\u0005\u000776\u0001\u000b\u0011B\u0014\t\u000bqkA\u0011A/\t\u000bqkA\u0011\u00016\u0003\r\t+hMZ3s\u0015\t9\u0002$\u0001\u0003j[Bd'BA\r\u001b\u0003\u0019\u0019HO]3b[*\t1$\u0001\u0003bW.\fWCA\u000f<'\t\u0001a\u0004\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VMZ\u0001\tG\u0006\u0004\u0018mY5us\u000e\u0001Q#A\u0014\u0011\u0005}A\u0013BA\u0015!\u0005\rIe\u000e^\u0001\u0005kN,G-\u0001\u0004jg\u001a+H\u000e\\\u000b\u0002[A\u0011qDL\u0005\u0003_\u0001\u0012qAQ8pY\u0016\fg.A\u0004jg\u0016k\u0007\u000f^=\u0002\u00119|g.R7qif\fq!\u001a8rk\u0016,X\r\u0006\u00025oA\u0011q$N\u0005\u0003m\u0001\u0012A!\u00168ji\")\u0001H\u0002a\u0001s\u0005!Q\r\\3n!\tQ4\b\u0004\u0001\u0005\u000bq\u0002!\u0019A\u001f\u0003\u0003Q\u000b\"AP!\u0011\u0005}y\u0014B\u0001!!\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\b\"\n\u0005\r\u0003#aA!os\u00069A-Z9vKV,G#A\u001d\u0002\tA,Wm[\u0001\u0006G2,\u0017M\u001d\u000b\u0002i\u0005AAM]8q\u0011\u0016\fG-\u0001\u0005ee>\u0004H+Y5mQ\t\u0001A\n\u0005\u0002N!6\taJ\u0003\u0002P5\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Es%aC%oi\u0016\u0014h.\u00197Ba&\faAQ;gM\u0016\u0014\bC\u0001+\u000e\u001b\u000512CA\u0007\u001f\u0003\u0019a\u0014N\\5u}Q\t1+\u0001\bGSb,G-U;fk\u0016\u001c\u0016N_3\u0002\u001f\u0019K\u00070\u001a3Rk\u0016,XmU5{K\u0002\naBR5yK\u0012\fV/Z;f\u001b\u0006\u001c8.A\bGSb,G-U;fk\u0016l\u0015m]6!\u0003\u0015\t\u0007\u000f\u001d7z+\tq\u0016\rF\u0002`E\u0012\u00042\u0001\u0016\u0001a!\tQ\u0014\rB\u0003='\t\u0007Q\bC\u0003d'\u0001\u0007q%\u0001\u0003tSj,\u0007\"B3\u0014\u0001\u00041\u0017aE3gM\u0016\u001cG/\u001b<f\u0003R$(/\u001b2vi\u0016\u001c\bCA4i\u001b\u0005A\u0012BA5\u0019\u0005)\tE\u000f\u001e:jEV$Xm]\u000b\u0003W:$2\u0001\\8q!\r!\u0006!\u001c\t\u0003u9$Q\u0001\u0010\u000bC\u0002uBQa\u0019\u000bA\u0002\u001dBQ!\u001d\u000bA\u0002\u001d\n1!\\1yQ\t!2\u000f\u0005\u0002Ni&\u0011QO\u0014\u0002\u0012\u0013:$XM\u001d8bYN#\u0018M\u00197f\u0003BL\u0007")
@InternalApi
public interface Buffer<T> {
    @InternalStableApi
    public static <T> Buffer<T> apply(int size, int max) {
        return Buffer$.MODULE$.apply(size, max);
    }

    public static <T> Buffer<T> apply(int size, Attributes effectiveAttributes) {
        return Buffer$.MODULE$.apply(size, effectiveAttributes);
    }

    public static int FixedQueueMask() {
        return Buffer$.MODULE$.FixedQueueMask();
    }

    public static int FixedQueueSize() {
        return Buffer$.MODULE$.FixedQueueSize();
    }

    public int capacity();

    public int used();

    public boolean isFull();

    public boolean isEmpty();

    public boolean nonEmpty();

    public void enqueue(T var1);

    public T dequeue();

    public T peek();

    public void clear();

    public void dropHead();

    public void dropTail();
}

