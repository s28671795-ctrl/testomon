/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2qa\u0001\u0003\u0011\u0002G\u0005\u0011\u0002C\u0003\u0011\u0001\u0019\u0005\u0011\u0003C\u0003!\u0001\u0019\u0005\u0011EA\bBgft7mU3sS\u0006d\u0017N_3s\u0015\t)a!A\u0007tKJL\u0017\r\\5{CRLwN\u001c\u0006\u0002\u000f\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0006\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g\u00035!xNQ5oCJL\u0018i]=oGR\u0011!C\b\t\u0004'YAR\"\u0001\u000b\u000b\u0005Ua\u0011AC2p]\u000e,(O]3oi&\u0011q\u0003\u0006\u0002\u0007\rV$XO]3\u0011\u0007-I2$\u0003\u0002\u001b\u0019\t)\u0011I\u001d:bsB\u00111\u0002H\u0005\u0003;1\u0011AAQ=uK\")q$\u0001a\u0001\u0015\u0005\tq.A\bge>l')\u001b8bef\f5/\u001f8d)\r\u00113%\n\t\u0004'YQ\u0001\"\u0002\u0013\u0003\u0001\u0004A\u0012!\u00022zi\u0016\u001c\b\"\u0002\u0014\u0003\u0001\u00049\u0013\u0001C7b]&4Wm\u001d;\u0011\u0005!zcBA\u0015.!\tQC\"D\u0001,\u0015\ta\u0003\"\u0001\u0004=e>|GOP\u0005\u0003]1\ta\u0001\u0015:fI\u00164\u0017B\u0001\u00192\u0005\u0019\u0019FO]5oO*\u0011a\u0006\u0004")
public interface AsyncSerializer {
    public Future<byte[]> toBinaryAsync(Object var1);

    public Future<Object> fromBinaryAsync(byte[] var1, String var2);
}

