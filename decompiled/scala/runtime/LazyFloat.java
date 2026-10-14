/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00153A\u0001C\u0005\u0001\u001d!)q\u0004\u0001C\u0001A!I1\u0005\u0001a\u0001\u0002\u0003\u0006K\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0005\n[\u0001\u0001\r\u0011!Q!\n9BQ!\r\u0001\u0005\u0002IBQa\r\u0001\u0005\u0002QBQA\u000e\u0001\u0005B]\u0012\u0011\u0002T1{s\u001acw.\u0019;\u000b\u0005)Y\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010'A\u0011\u0001#E\u0007\u0002\u0017%\u0011!c\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0005QabBA\u000b\u001b\u001d\t1\u0012$D\u0001\u0018\u0015\tAR\"\u0001\u0004=e>|GOP\u0005\u0002\u0019%\u00111dC\u0001\ba\u0006\u001c7.Y4f\u0013\tibD\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002\u001c\u0017\u00051A(\u001b8jiz\"\u0012!\t\t\u0003E\u0001i\u0011!C\u0001\r?&t\u0017\u000e^5bY&TX\r\u001a\t\u0003!\u0015J!AJ\u0006\u0003\u000f\t{w\u000e\\3b]\"\u0012!\u0001\u000b\t\u0003!%J!AK\u0006\u0003\u0011Y|G.\u0019;jY\u0016\f1\"\u001b8ji&\fG.\u001b>fIV\tA%\u0001\u0004`m\u0006dW/\u001a\t\u0003!=J!\u0001M\u0006\u0003\u000b\u0019cw.\u0019;\u0002\u000bY\fG.^3\u0016\u00039\n!\"\u001b8ji&\fG.\u001b>f)\tqS\u0007C\u00032\r\u0001\u0007a&\u0001\u0005u_N#(/\u001b8h)\u0005A\u0004CA\u001d?\u001b\u0005Q$BA\u001e=\u0003\u0011a\u0017M\\4\u000b\u0003u\nAA[1wC&\u0011qH\u000f\u0002\u0007'R\u0014\u0018N\\4)\t\u0001\t\u0015\u0007\u0012\t\u0003!\tK!aQ\u0006\u0003!M+'/[1m-\u0016\u00148/[8o+&#e$A\u0001")
public class LazyFloat
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private float _value;

    public boolean initialized() {
        return this._initialized;
    }

    public float value() {
        return this._value;
    }

    public float initialize(float value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(10).append("LazyFloat ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

