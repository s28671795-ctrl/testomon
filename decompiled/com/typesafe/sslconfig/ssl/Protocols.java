/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.Protocols$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Q\u0001C\u0005\t\u0002I1Q\u0001F\u0005\t\u0002UAQ\u0001H\u0001\u0005\u0002uAqAH\u0001C\u0002\u0013\u0005q\u0004\u0003\u00041\u0003\u0001\u0006I\u0001\t\u0005\bc\u0005\u0011\r\u0011\"\u00013\u0011\u00191\u0014\u0001)A\u0005g!)q'\u0001C\u0001q\u0005I\u0001K]8u_\u000e|Gn\u001d\u0006\u0003\u0015-\t1a]:m\u0015\taQ\"A\u0005tg2\u001cwN\u001c4jO*\u0011abD\u0001\tif\u0004Xm]1gK*\t\u0001#A\u0002d_6\u001c\u0001\u0001\u0005\u0002\u0014\u00035\t\u0011BA\u0005Qe>$xnY8mgN\u0011\u0011A\u0006\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0011\u0012a\u00053faJ,7-\u0019;fIB\u0013x\u000e^8d_2\u001cX#\u0001\u0011\u0011\u0007\u00052\u0003&D\u0001#\u0015\t\u0019C%A\u0005j[6,H/\u00192mK*\u0011Q\u0005G\u0001\u000bG>dG.Z2uS>t\u0017BA\u0014#\u0005\r\u0019V\r\u001e\t\u0003S9j\u0011A\u000b\u0006\u0003W1\nA\u0001\\1oO*\tQ&\u0001\u0003kCZ\f\u0017BA\u0018+\u0005\u0019\u0019FO]5oO\u0006!B-\u001a9sK\u000e\fG/\u001a3Qe>$xnY8mg\u0002\nAC]3d_6lWM\u001c3fIB\u0013x\u000e^8d_2\u001cX#A\u001a\u0011\u0007]!\u0004&\u0003\u000261\t)\u0011I\u001d:bs\u0006)\"/Z2p[6,g\u000eZ3e!J|Go\\2pYN\u0004\u0013a\u0005:fG>lW.\u001a8eK\u0012\u0004&o\u001c;pG>dW#\u0001\u0015")
public final class Protocols {
    public static String recommendedProtocol() {
        return Protocols$.MODULE$.recommendedProtocol();
    }

    public static String[] recommendedProtocols() {
        return Protocols$.MODULE$.recommendedProtocols();
    }

    public static Set<String> deprecatedProtocols() {
        return Protocols$.MODULE$.deprecatedProtocols();
    }
}

