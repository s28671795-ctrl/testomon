/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593A\u0001C\u0005\u0001\u001d!)\u0001\u0005\u0001C\u0001C!Iq\u0006\u0001a\u0001\u0002\u0003\u0006K\u0001\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\ns\u0001\u0001\r\u0011!Q!\n\u0011BQA\u000f\u0001\u0005\u0002mBQ\u0001\u0010\u0001\u0005\u0002uBQa\u0010\u0001\u0005B\u0001\u0013q\u0001T1{sJ+gM\u0003\u0002\u000b\u0017\u00059!/\u001e8uS6,'\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011qBJ\n\u0004\u0001A!\u0002CA\t\u0013\u001b\u0005Y\u0011BA\n\f\u0005\u0019\te.\u001f*fMB\u0011Q#\b\b\u0003-mq!a\u0006\u000e\u000e\u0003aQ!!G\u0007\u0002\rq\u0012xn\u001c;?\u0013\u0005a\u0011B\u0001\u000f\f\u0003\u001d\u0001\u0018mY6bO\u0016L!AH\u0010\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005qY\u0011A\u0002\u001fj]&$h\bF\u0001#!\r\u0019\u0003\u0001J\u0007\u0002\u0013A\u0011QE\n\u0007\u0001\t\u00159\u0003A1\u0001)\u0005\u0005!\u0016CA\u0015-!\t\t\"&\u0003\u0002,\u0017\t9aj\u001c;iS:<\u0007CA\t.\u0013\tq3BA\u0002B]f\fAbX5oSRL\u0017\r\\5{K\u0012\u0004\"!E\u0019\n\u0005IZ!a\u0002\"p_2,\u0017M\u001c\u0015\u0003\u0005Q\u0002\"!E\u001b\n\u0005YZ!\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\u0017%t\u0017\u000e^5bY&TX\rZ\u000b\u0002a\u00051qL^1mk\u0016\fQA^1mk\u0016,\u0012\u0001J\u0001\u000bS:LG/[1mSj,GC\u0001\u0013?\u0011\u0015Qd\u00011\u0001%\u0003!!xn\u0015;sS:<G#A!\u0011\u0005\t;U\"A\"\u000b\u0005\u0011+\u0015\u0001\u00027b]\u001eT\u0011AR\u0001\u0005U\u00064\u0018-\u0003\u0002I\u0007\n11\u000b\u001e:j]\u001eDC\u0001\u0001&;\u001bB\u0011\u0011cS\u0005\u0003\u0019.\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\u0005\u0001")
public class LazyRef<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private T _value;

    public boolean initialized() {
        return this._initialized;
    }

    public T value() {
        return this._value;
    }

    public T initialize(T value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(8).append("LazyRef ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

