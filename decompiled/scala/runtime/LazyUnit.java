/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013AAB\u0004\u0001\u0019!)Q\u0004\u0001C\u0001=!I\u0011\u0005\u0001a\u0001\u0002\u0003\u0006KA\t\u0005\u0006S\u0001!\tA\u000b\u0005\u0006W\u0001!\t\u0001\f\u0005\u0006a\u0001!\t%\r\u0002\t\u0019\u0006T\u00180\u00168ji*\u0011\u0001\"C\u0001\beVtG/[7f\u0015\u0005Q\u0011!B:dC2\f7\u0001A\n\u0004\u00015\t\u0002C\u0001\b\u0010\u001b\u0005I\u0011B\u0001\t\n\u0005\u0019\te.\u001f*fMB\u0011!C\u0007\b\u0003'aq!\u0001F\f\u000e\u0003UQ!AF\u0006\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0011BA\r\n\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0007\u000f\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005eI\u0011A\u0002\u001fj]&$h\bF\u0001 !\t\u0001\u0003!D\u0001\b\u00031y\u0016N\\5uS\u0006d\u0017N_3e!\tq1%\u0003\u0002%\u0013\t9!i\\8mK\u0006t\u0007F\u0001\u0002'!\tqq%\u0003\u0002)\u0013\tAao\u001c7bi&dW-A\u0006j]&$\u0018.\u00197ju\u0016$W#\u0001\u0012\u0002\u0015%t\u0017\u000e^5bY&TX\rF\u0001.!\tqa&\u0003\u00020\u0013\t!QK\\5u\u0003!!xn\u0015;sS:<G#\u0001\u001a\u0011\u0005MBT\"\u0001\u001b\u000b\u0005U2\u0014\u0001\u00027b]\u001eT\u0011aN\u0001\u0005U\u00064\u0018-\u0003\u0002:i\t11\u000b\u001e:j]\u001eDC\u0001A\u001e?\u007fA\u0011a\u0002P\u0005\u0003{%\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\u0001")
public class LazyUnit
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;

    public boolean initialized() {
        return this._initialized;
    }

    public void initialize() {
        this._initialized = true;
    }

    public String toString() {
        return new StringBuilder(8).append("LazyUnit").append((Object)(this._initialized ? "" : " thunk")).toString();
    }
}

