/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.util.ccompat.package$JavaConverters$;
import java.util.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3Q\u0001C\u0005\u0002\u00029AQa\u0005\u0001\u0005\u0002QAQA\u0006\u0001\u0007\u0002]AQA\r\u0001\u0007\u0002MB\u0001\u0002\u0010\u0001\t\u0006\u0004%)%\u0010\u0005\t\u0017\u0002A)\u0019!C#\u0019\")1\u000b\u0001C#/!)A\u000b\u0001C#g\ti\u0011IY:ue\u0006\u001cGo\u00155ba\u0016T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u0013%\u0011!#\u0003\u0002\u0006'\"\f\u0007/Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003U\u0001\"\u0001\u0005\u0001\u0002\u0013\u0005dG.\u00138mKR\u001cX#\u0001\r\u0011\u0007eq\u0002%D\u0001\u001b\u0015\tYB$\u0001\u0003vi&d'\"A\u000f\u0002\t)\fg/Y\u0005\u0003?i\u0011A\u0001T5tiB\u0012\u0011E\n\t\u0004!\t\"\u0013BA\u0012\n\u0005\u0015Ie\u000e\\3u!\t)c\u0005\u0004\u0001\u0005\u0013\u001d\u0012\u0011\u0011!A\u0001\u0006\u0003A#\u0001B0%cA\n\"!K\u0018\u0011\u0005)jS\"A\u0016\u000b\u00031\nQa]2bY\u0006L!AL\u0016\u0003\u000f9{G\u000f[5oOB\u0011!\u0006M\u0005\u0003c-\u00121!\u00118z\u0003)\tG\u000e\\(vi2,Go]\u000b\u0002iA\u0019\u0011DH\u001b1\u0005YR\u0004c\u0001\t8s%\u0011\u0001(\u0003\u0002\u0007\u001fV$H.\u001a;\u0011\u0005\u0015RD!C\u001e\u0004\u0003\u0003\u0005\tQ!\u0001)\u0005\u0011yF%M\u0019\u0002\r%tG.\u001a;t+\u0005q\u0004cA E\r6\t\u0001I\u0003\u0002B\u0005\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0007.\n!bY8mY\u0016\u001cG/[8o\u0013\t)\u0005IA\u0002TKF\u0004$aR%\u0011\u0007A\u0011\u0003\n\u0005\u0002&\u0013\u0012I!\nBA\u0001\u0002\u0003\u0015\t\u0001\u000b\u0002\u0005?\u0012\n$'A\u0004pkRdW\r^:\u0016\u00035\u00032a\u0010#Oa\ty\u0015\u000bE\u0002\u0011oA\u0003\"!J)\u0005\u0013I+\u0011\u0011!A\u0001\u0006\u0003A#\u0001B0%cM\n\u0011bZ3u\u0013:dW\r^:\u0002\u0015\u001d,GoT;uY\u0016$8\u000f")
public abstract class AbstractShape
extends Shape {
    private Seq<Inlet<?>> inlets;
    private Seq<Outlet<?>> outlets;
    private volatile byte bitmap$0;

    public abstract List<Inlet<?>> allInlets();

    public abstract List<Outlet<?>> allOutlets();

    private Seq<Inlet<?>> inlets$lzycompute() {
        AbstractShape abstractShape = this;
        synchronized (abstractShape) {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                this.inlets = package$JavaConverters$.MODULE$.ListHasAsScala(this.allInlets()).asScala().toList();
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.inlets;
    }

    @Override
    public final Seq<Inlet<?>> inlets() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.inlets$lzycompute();
        }
        return this.inlets;
    }

    private Seq<Outlet<?>> outlets$lzycompute() {
        AbstractShape abstractShape = this;
        synchronized (abstractShape) {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                this.outlets = package$JavaConverters$.MODULE$.ListHasAsScala(this.allOutlets()).asScala().toList();
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.outlets;
    }

    @Override
    public final Seq<Outlet<?>> outlets() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.outlets$lzycompute();
        }
        return this.outlets;
    }

    @Override
    public final List<Inlet<?>> getInlets() {
        return this.allInlets();
    }

    @Override
    public final List<Outlet<?>> getOutlets() {
        return this.allOutlets();
    }
}

