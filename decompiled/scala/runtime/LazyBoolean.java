/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001C\u0005\u0001\u001d!)q\u0004\u0001C\u0001A!I1\u0005\u0001a\u0001\u0002\u0003\u0006K\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0005\n[\u0001\u0001\r\u0011!Q!\n\u0011BQA\f\u0001\u0005\u00021BQa\f\u0001\u0005\u0002ABQA\r\u0001\u0005BM\u00121\u0002T1{s\n{w\u000e\\3b]*\u0011!bC\u0001\beVtG/[7f\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=\u0019\u0002C\u0001\t\u0012\u001b\u0005Y\u0011B\u0001\n\f\u0005\u0019\te.\u001f*fMB\u0011A\u0003\b\b\u0003+iq!AF\r\u000e\u0003]Q!\u0001G\u0007\u0002\rq\u0012xn\u001c;?\u0013\u0005a\u0011BA\u000e\f\u0003\u001d\u0001\u0018mY6bO\u0016L!!\b\u0010\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005mY\u0011A\u0002\u001fj]&$h\bF\u0001\"!\t\u0011\u0003!D\u0001\n\u00031y\u0016N\\5uS\u0006d\u0017N_3e!\t\u0001R%\u0003\u0002'\u0017\t9!i\\8mK\u0006t\u0007F\u0001\u0002)!\t\u0001\u0012&\u0003\u0002+\u0017\tAao\u001c7bi&dW-A\u0006j]&$\u0018.\u00197ju\u0016$W#\u0001\u0013\u0002\r}3\u0018\r\\;f\u0003\u00151\u0018\r\\;f\u0003)Ig.\u001b;jC2L'0\u001a\u000b\u0003IEBQA\f\u0004A\u0002\u0011\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002iA\u0011QGO\u0007\u0002m)\u0011q\u0007O\u0001\u0005Y\u0006twMC\u0001:\u0003\u0011Q\u0017M^1\n\u0005m2$AB*ue&tw\r\u000b\u0003\u0001{9\u0002\u0005C\u0001\t?\u0013\ty4B\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\tz\t\u0011\u0001")
public class LazyBoolean
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private boolean _value;

    public boolean initialized() {
        return this._initialized;
    }

    public boolean value() {
        return this._value;
    }

    public boolean initialize(boolean value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(12).append("LazyBoolean ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

