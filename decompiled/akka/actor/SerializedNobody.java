/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Nobody$;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U2Qa\u0001\u0003\u0001\r!AQ\u0001\b\u0001\u0005\u0002uAQ\u0001\t\u0001\u0005\n\u0005\u0012\u0001cU3sS\u0006d\u0017N_3e\u001d>\u0014w\u000eZ=\u000b\u0005\u00151\u0011!B1di>\u0014(\"A\u0004\u0002\t\u0005\\7.Y\n\u0004\u0001%y\u0001C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g\r\u0005\u0002\u001139\u0011\u0011c\u0006\b\u0003%Yi\u0011a\u0005\u0006\u0003)U\ta\u0001\u0010:p_Rt4\u0001A\u0005\u0002\u0019%\u0011\u0001dC\u0001\ba\u0006\u001c7.Y4f\u0013\tQ2D\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002\u0019\u0017\u00051A(\u001b8jiz\"\u0012A\b\t\u0003?\u0001i\u0011\u0001B\u0001\fe\u0016\fGMU3t_24X\rF\u0001\nQ\r\u00111E\f\t\u0004\u0015\u00112\u0013BA\u0013\f\u0005\u0019!\bN]8xgB\u0011q\u0005L\u0007\u0002Q)\u0011\u0011FK\u0001\u0003S>T\u0011aK\u0001\u0005U\u00064\u0018-\u0003\u0002.Q\t)rJ\u00196fGR\u001cFO]3b[\u0016C8-\u001a9uS>t7%\u0001\u0014)\t\u0001\u00014\u0007\u000e\t\u0003\u0015EJ!AM\u0006\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001")
public class SerializedNobody
implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object readResolve() throws ObjectStreamException {
        return Nobody$.MODULE$;
    }
}

