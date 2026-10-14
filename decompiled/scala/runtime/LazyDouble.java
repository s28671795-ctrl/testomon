/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00153A\u0001C\u0005\u0001\u001d!)q\u0004\u0001C\u0001A!I1\u0005\u0001a\u0001\u0002\u0003\u0006K\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0005\n[\u0001\u0001\r\u0011!Q!\n9BQ!\r\u0001\u0005\u0002IBQa\r\u0001\u0005\u0002QBQA\u000e\u0001\u0005B]\u0012!\u0002T1{s\u0012{WO\u00197f\u0015\tQ1\"A\u0004sk:$\u0018.\\3\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001fM\u0001\"\u0001E\t\u000e\u0003-I!AE\u0006\u0003\r\u0005s\u0017PU3g!\t!BD\u0004\u0002\u001659\u0011a#G\u0007\u0002/)\u0011\u0001$D\u0001\u0007yI|w\u000e\u001e \n\u00031I!aG\u0006\u0002\u000fA\f7m[1hK&\u0011QD\b\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u00037-\ta\u0001P5oSRtD#A\u0011\u0011\u0005\t\u0002Q\"A\u0005\u0002\u0019}Kg.\u001b;jC2L'0\u001a3\u0011\u0005A)\u0013B\u0001\u0014\f\u0005\u001d\u0011un\u001c7fC:D#A\u0001\u0015\u0011\u0005AI\u0013B\u0001\u0016\f\u0005!1x\u000e\\1uS2,\u0017aC5oSRL\u0017\r\\5{K\u0012,\u0012\u0001J\u0001\u0007?Z\fG.^3\u0011\u0005Ay\u0013B\u0001\u0019\f\u0005\u0019!u.\u001e2mK\u0006)a/\u00197vKV\ta&\u0001\u0006j]&$\u0018.\u00197ju\u0016$\"AL\u001b\t\u000bE2\u0001\u0019\u0001\u0018\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\u000f\t\u0003syj\u0011A\u000f\u0006\u0003wq\nA\u0001\\1oO*\tQ(\u0001\u0003kCZ\f\u0017BA ;\u0005\u0019\u0019FO]5oO\"\"\u0001!Q\u0019E!\t\u0001\")\u0003\u0002D\u0017\t\u00012+\u001a:jC24VM]:j_:,\u0016\n\u0012\u0010\u0002\u0003\u0001")
public class LazyDouble
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private double _value;

    public boolean initialized() {
        return this._initialized;
    }

    public double value() {
        return this._value;
    }

    public double initialize(double value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(11).append("LazyDouble ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

