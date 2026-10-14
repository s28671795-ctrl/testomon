/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00153A\u0001C\u0005\u0001\u001d!)q\u0004\u0001C\u0001A!I1\u0005\u0001a\u0001\u0002\u0003\u0006K\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0005\n[\u0001\u0001\r\u0011!Q!\n9BQ!\r\u0001\u0005\u0002IBQa\r\u0001\u0005\u0002QBQA\u000e\u0001\u0005B]\u0012q\u0001T1{s&sGO\u0003\u0002\u000b\u0017\u00059!/\u001e8uS6,'\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\n\u0011\u0005A\tR\"A\u0006\n\u0005IY!AB!osJ+g\r\u0005\u0002\u001599\u0011QC\u0007\b\u0003-ei\u0011a\u0006\u0006\u000315\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0007\n\u0005mY\u0011a\u00029bG.\fw-Z\u0005\u0003;y\u0011AbU3sS\u0006d\u0017N_1cY\u0016T!aG\u0006\u0002\rqJg.\u001b;?)\u0005\t\u0003C\u0001\u0012\u0001\u001b\u0005I\u0011\u0001D0j]&$\u0018.\u00197ju\u0016$\u0007C\u0001\t&\u0013\t13BA\u0004C_>dW-\u00198)\u0005\tA\u0003C\u0001\t*\u0013\tQ3B\u0001\u0005w_2\fG/\u001b7f\u0003-Ig.\u001b;jC2L'0\u001a3\u0016\u0003\u0011\naa\u0018<bYV,\u0007C\u0001\t0\u0013\t\u00014BA\u0002J]R\fQA^1mk\u0016,\u0012AL\u0001\u000bS:LG/[1mSj,GC\u0001\u00186\u0011\u0015\td\u00011\u0001/\u0003!!xn\u0015;sS:<G#\u0001\u001d\u0011\u0005erT\"\u0001\u001e\u000b\u0005mb\u0014\u0001\u00027b]\u001eT\u0011!P\u0001\u0005U\u00064\u0018-\u0003\u0002@u\t11\u000b\u001e:j]\u001eDC\u0001A!2\tB\u0011\u0001CQ\u0005\u0003\u0007.\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\u0005\u0001")
public class LazyInt
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private int _value;

    public boolean initialized() {
        return this._initialized;
    }

    public int value() {
        return this._value;
    }

    public int initialize(int value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(8).append("LazyInt ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

