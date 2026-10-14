/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.impl.fusing.GraphInterpreter;
import scala.runtime.Null$;

@InternalApi
public final class GraphInterpreter$ {
    public static final GraphInterpreter$ MODULE$ = new GraphInterpreter$();
    private static final Attributes[] singleNoAttribute = (Attributes[])((Object[])new Attributes[]{Attributes$.MODULE$.none()});
    private static final ThreadLocal<Object[]> akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter = new ThreadLocal<Object[]>(){

        public Object[] initialValue() {
            return new Object[1];
        }
    };

    public final boolean Debug() {
        return false;
    }

    public final Null$ NoEvent() {
        return null;
    }

    public final int InReady() {
        return 1;
    }

    public final int Pulling() {
        return 2;
    }

    public final int Pushing() {
        return 4;
    }

    public final int OutReady() {
        return 8;
    }

    public final int InClosed() {
        return 16;
    }

    public final int OutClosed() {
        return 32;
    }

    public final int InFailed() {
        return 64;
    }

    public final int PullStartFlip() {
        return 3;
    }

    public final int PullEndFlip() {
        return 10;
    }

    public final int PushStartFlip() {
        return 12;
    }

    public final int PushEndFlip() {
        return 5;
    }

    public final int KeepGoingFlag() {
        return 0x4000000;
    }

    public final int KeepGoingMask() {
        return 0x3FFFFFF;
    }

    public Attributes[] singleNoAttribute() {
        return singleNoAttribute;
    }

    public ThreadLocal<Object[]> akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter() {
        return akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter;
    }

    public GraphInterpreter currentInterpreter() {
        return ((GraphInterpreter)this.akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter().get()[0]).nonNull();
    }

    public GraphInterpreter currentInterpreterOrNull() {
        return (GraphInterpreter)this.akka$stream$impl$fusing$GraphInterpreter$$_currentInterpreter().get()[0];
    }

    private GraphInterpreter$() {
    }
}

