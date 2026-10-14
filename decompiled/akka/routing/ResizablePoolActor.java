/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorInitializationException$;
import akka.actor.SupervisorStrategy;
import akka.routing.ResizablePoolActor$Resize$;
import akka.routing.ResizablePoolCell;
import akka.routing.RouterPoolActor;
import java.io.Serializable;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005EqA\u0002\u000b\u0016\u0011\u00039\u0012D\u0002\u0004\u001c+!\u0005q\u0003\b\u0005\u0006G\u0005!\t!J\u0004\u0006M\u0005A\ti\n\u0004\u0006S\u0005A\tI\u000b\u0005\u0006G\u0011!\t!\u0010\u0005\b}\u0011\t\t\u0011\"\u0011@\u0011\u001dAE!!A\u0005\u0002%Cq!\u0014\u0003\u0002\u0002\u0013\u0005a\nC\u0004U\t\u0005\u0005I\u0011I+\t\u000fq#\u0011\u0011!C\u0001;\"9!\rBA\u0001\n\u0003\u001a\u0007b\u00023\u0005\u0003\u0003%\t%\u001a\u0005\bM\u0012\t\t\u0011\"\u0003h\r\u0015YR\u0003A\fl\u0011%ygB!A!\u0002\u0013\u0001h\u000fC\u0003$\u001d\u0011\u0005q\u000fC\u0004{\u001d\t\u0007I\u0011A>\t\r}t\u0001\u0015!\u0003}\u0011\u001d\t\tA\u0004C!\u0003\u0007\t!CU3tSj\f'\r\\3Q_>d\u0017i\u0019;pe*\u0011acF\u0001\be>,H/\u001b8h\u0015\u0005A\u0012\u0001B1lW\u0006\u0004\"AG\u0001\u000e\u0003U\u0011!CU3tSj\f'\r\\3Q_>d\u0017i\u0019;peN\u0011\u0011!\b\t\u0003=\u0005j\u0011a\b\u0006\u0002A\u0005)1oY1mC&\u0011!e\b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!G\u0001\u0007%\u0016\u001c\u0018N_3\u0011\u0005!\"Q\"A\u0001\u0003\rI+7/\u001b>f'\u0015!Qd\u000b\u00182!\tQB&\u0003\u0002.+\tA\"k\\;uKJl\u0015M\\1hK6,g\u000e^'fgN\u001c\u0018mZ3\u0011\u0005yy\u0013B\u0001\u0019 \u0005\u001d\u0001&o\u001c3vGR\u0004\"A\r\u001e\u000f\u0005MBdB\u0001\u001b8\u001b\u0005)$B\u0001\u001c%\u0003\u0019a$o\\8u}%\t\u0001%\u0003\u0002:?\u00059\u0001/Y2lC\u001e,\u0017BA\u001e=\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tIt\u0004F\u0001(\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u0001\t\u0005\u0002B\r6\t!I\u0003\u0002D\t\u0006!A.\u00198h\u0015\u0005)\u0015\u0001\u00026bm\u0006L!a\u0012\"\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005Q\u0005C\u0001\u0010L\u0013\tauDA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002P%B\u0011a\u0004U\u0005\u0003#~\u00111!\u00118z\u0011\u001d\u0019\u0006\"!AA\u0002)\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001,\u0011\u0007]Sv*D\u0001Y\u0015\tIv$\u0001\u0006d_2dWm\u0019;j_:L!a\u0017-\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003=\u0006\u0004\"AH0\n\u0005\u0001|\"a\u0002\"p_2,\u0017M\u001c\u0005\b'*\t\t\u00111\u0001P\u0003!A\u0017m\u001d5D_\u0012,G#\u0001&\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001Q\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002QB\u0011\u0011)[\u0005\u0003U\n\u0013aa\u00142kK\u000e$8C\u0001\bm!\tQR.\u0003\u0002o+\ty!k\\;uKJ\u0004vn\u001c7BGR|'/\u0001\ntkB,'O^5t_J\u001cFO]1uK\u001eL\bCA9u\u001b\u0005\u0011(BA:\u0018\u0003\u0015\t7\r^8s\u0013\t)(O\u0001\nTkB,'O^5t_J\u001cFO]1uK\u001eL\u0018BA8n)\tA\u0018\u0010\u0005\u0002\u001b\u001d!)q\u000e\u0005a\u0001a\u0006Y!/Z:ju\u0016\u00148)\u001a7m+\u0005a\bC\u0001\u000e~\u0013\tqXCA\tSKNL'0\u00192mKB{w\u000e\\\"fY2\fAB]3tSj,'oQ3mY\u0002\nqA]3dK&4X-\u0006\u0002\u0002\u0006A1a$a\u0002P\u0003\u0017I1!!\u0003 \u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0007c\u0001\u0010\u0002\u000e%\u0019\u0011qB\u0010\u0003\tUs\u0017\u000e\u001e")
public class ResizablePoolActor
extends RouterPoolActor {
    private final ResizablePoolCell resizerCell;

    public ResizablePoolCell resizerCell() {
        return this.resizerCell;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ ResizablePoolActor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (ResizablePoolActor$Resize$.MODULE$.equals(A1)) {
                    this.$outer.resizerCell().resize(false);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return ResizablePoolActor$Resize$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }.orElse(super.receive());
    }

    public ResizablePoolActor(SupervisorStrategy supervisorStrategy) {
        super(supervisorStrategy);
        ActorContext actorContext = this.context();
        if (!(actorContext instanceof ResizablePoolCell)) {
            throw ActorInitializationException$.MODULE$.apply(new StringBuilder(72).append("Resizable router actor can only be used when resizer is defined, not in ").append(this.context().getClass()).toString());
        }
        ResizablePoolCell resizablePoolCell = (ResizablePoolCell)actorContext;
        this.resizerCell = resizablePoolCell;
    }
}

