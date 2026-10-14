/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.japi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00053Q\u0001B\u0003\u0002\u0002)AQA\t\u0001\u0005\u0002\rBQ!\n\u0001\u0005F\u0019BQ\u0001\f\u0001\u0007\u00025\u0012qAR8sK\u0006\u001c\u0007N\u0003\u0002\u0007\u000f\u0005AA-[:qCR\u001c\u0007NC\u0001\t\u0003\u0011\t7n[1\u0004\u0001U\u00111BF\n\u0003\u00011\u00012!D\t\u0015\u001d\tqq\"D\u0001\u0006\u0013\t\u0001R!\u0001\u0003kCBL\u0017B\u0001\n\u0014\u0005I)f.\u001b;Gk:\u001cG/[8o\u0005JLGmZ3\u000b\u0005A)\u0001CA\u000b\u0017\u0019\u0001!aa\u0006\u0001\t\u0006\u0004A\"!\u0001+\u0012\u0005ey\u0002C\u0001\u000e\u001e\u001b\u0005Y\"\"\u0001\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yY\"a\u0002(pi\"Lgn\u001a\t\u00035\u0001J!!I\u000e\u0003\u0007\u0005s\u00170\u0001\u0004=S:LGO\u0010\u000b\u0002IA\u0019a\u0002\u0001\u000b\u0002\u0011%tG/\u001a:oC2$\"a\n\u0016\u0011\u0005iA\u0013BA\u0015\u001c\u0005\u0011)f.\u001b;\t\u000b-\u0012\u0001\u0019\u0001\u000b\u0002\u0003Q\fA!Z1dQR\u0011qE\f\u0005\u0006_\r\u0001\r\u0001F\u0001\u0007e\u0016\u001cX\u000f\u001c;)\u0007\r\t\u0004\tE\u0002\u001beQJ!aM\u000e\u0003\rQD'o\\<t!\t)TH\u0004\u00027w9\u0011qGO\u0007\u0002q)\u0011\u0011(C\u0001\u0007yI|w\u000e\u001e \n\u0003qI!\u0001P\u000e\u0002\u000fA\f7m[1hK&\u0011ah\u0010\u0002\n)\"\u0014xn^1cY\u0016T!\u0001P\u000e$\u0003Q\u0002")
public abstract class Foreach<T>
extends japi.UnitFunctionBridge<T> {
    @Override
    public final void internal(T t2) {
        this.each(t2);
    }

    public abstract void each(T var1) throws Throwable;
}

