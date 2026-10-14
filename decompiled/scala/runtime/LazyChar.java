/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00153A\u0001C\u0005\u0001\u001d!)q\u0004\u0001C\u0001A!I1\u0005\u0001a\u0001\u0002\u0003\u0006K\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0005\n[\u0001\u0001\r\u0011!Q!\n9BQ!\r\u0001\u0005\u0002IBQa\r\u0001\u0005\u0002QBQA\u000e\u0001\u0005B]\u0012\u0001\u0002T1{s\u000eC\u0017M\u001d\u0006\u0003\u0015-\tqA];oi&lWMC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\b\u0014!\t\u0001\u0012#D\u0001\f\u0013\t\u00112B\u0001\u0004B]f\u0014VM\u001a\t\u0003)qq!!\u0006\u000e\u000f\u0005YIR\"A\f\u000b\u0005ai\u0011A\u0002\u001fs_>$h(C\u0001\r\u0013\tY2\"A\u0004qC\u000e\\\u0017mZ3\n\u0005uq\"\u0001D*fe&\fG.\u001b>bE2,'BA\u000e\f\u0003\u0019a\u0014N\\5u}Q\t\u0011\u0005\u0005\u0002#\u00015\t\u0011\"\u0001\u0007`S:LG/[1mSj,G\r\u0005\u0002\u0011K%\u0011ae\u0003\u0002\b\u0005>|G.Z1oQ\t\u0011\u0001\u0006\u0005\u0002\u0011S%\u0011!f\u0003\u0002\tm>d\u0017\r^5mK\u0006Y\u0011N\\5uS\u0006d\u0017N_3e+\u0005!\u0013AB0wC2,X\r\u0005\u0002\u0011_%\u0011\u0001g\u0003\u0002\u0005\u0007\"\f'/A\u0003wC2,X-F\u0001/\u0003)Ig.\u001b;jC2L'0\u001a\u000b\u0003]UBQ!\r\u0004A\u00029\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002qA\u0011\u0011HP\u0007\u0002u)\u00111\bP\u0001\u0005Y\u0006twMC\u0001>\u0003\u0011Q\u0017M^1\n\u0005}R$AB*ue&tw\r\u000b\u0003\u0001\u0003F\"\u0005C\u0001\tC\u0013\t\u00195B\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\tz\t\u0011\u0001")
public class LazyChar
implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile boolean _initialized;
    private char _value;

    public boolean initialized() {
        return this._initialized;
    }

    public char value() {
        return this._value;
    }

    public char initialize(char value) {
        this._value = value;
        this._initialized = true;
        return value;
    }

    public String toString() {
        return new StringBuilder(9).append("LazyChar ").append((Object)(this._initialized ? new StringBuilder(4).append("of: ").append(this._value).toString() : "thunk")).toString();
    }
}

