/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.control.ControlThrowable;

@ScalaSignature(bytes="\u0006\u0005E4Aa\u0002\u0005\u0001\u001b!Aq\u0003\u0001BC\u0002\u0013\u0005\u0001\u0004\u0003\u0005\u001e\u0001\t\u0005\t\u0015!\u0003\u001a\u0011!q\u0002A!b\u0001\n\u0003y\u0002\u0002C1\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0011\t\u000b\t\u0004A\u0011A2\t\u000b!\u0004AQI5\u0003+9{g\u000eT8dC2\u0014V\r^;s]\u000e{g\u000e\u001e:pY*\u0011\u0011BC\u0001\beVtG/[7f\u0015\u0005Y\u0011!B:dC2\f7\u0001A\u000b\u0003\u001d\t\u001a\"\u0001A\b\u0011\u0005A)R\"A\t\u000b\u0005I\u0019\u0012aB2p]R\u0014x\u000e\u001c\u0006\u0003))\tA!\u001e;jY&\u0011a#\u0005\u0002\u0011\u0007>tGO]8m)\"\u0014xn^1cY\u0016\f1a[3z+\u0005I\u0002C\u0001\u000e\u001c\u001b\u0005Q\u0011B\u0001\u000f\u000b\u0005\u0019\te.\u001f*fM\u0006!1.Z=!\u0003\u00151\u0018\r\\;f+\u0005\u0001\u0003CA\u0011#\u0019\u0001!\u0011b\t\u0001!\u0002\u0003\u0005)\u0019\u0001\u0013\u0003\u0003Q\u000b\"!\n\u0015\u0011\u0005i1\u0013BA\u0014\u000b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AG\u0015\n\u0005)R!aA!os\"Z!\u0005L\u0018:}\rCUJU,]!\tQR&\u0003\u0002/\u0015\tY1\u000f]3dS\u0006d\u0017N_3ec\u0015\u0019\u0003'M\u001a3\u001d\tQ\u0012'\u0003\u00023\u0015\u0005!!)\u001f;fc\u0011!C\u0007O\u0006\u000f\u0005UBT\"\u0001\u001c\u000b\u0005]b\u0011A\u0002\u001fs_>$h(C\u0001\fc\u0015\u0019#hO\u001f=\u001d\tQ2(\u0003\u0002=\u0015\u0005)1\u000b[8siF\"A\u0005\u000e\u001d\fc\u0015\u0019s\b\u0011\"B\u001d\tQ\u0002)\u0003\u0002B\u0015\u0005\u0019\u0011J\u001c;2\t\u0011\"\u0004hC\u0019\u0006G\u0011+uI\u0012\b\u00035\u0015K!A\u0012\u0006\u0002\t1{gnZ\u0019\u0005IQB4\"M\u0003$\u0013*c5J\u0004\u0002\u001b\u0015&\u00111JC\u0001\u0005\u0007\"\f'/\r\u0003%iaZ\u0011'B\u0012O\u001fF\u0003fB\u0001\u000eP\u0013\t\u0001&\"A\u0003GY>\fG/\r\u0003%iaZ\u0011'B\u0012T)Z+fB\u0001\u000eU\u0013\t)&\"\u0001\u0004E_V\u0014G.Z\u0019\u0005IQB4\"M\u0003$1f[&L\u0004\u0002\u001b3&\u0011!LC\u0001\b\u0005>|G.Z1oc\u0011!C\u0007O\u00062\u000b\rjf\fY0\u000f\u0005iq\u0016BA0\u000b\u0003\u0011)f.\u001b;2\t\u0011\"\u0004hC\u0001\u0007m\u0006dW/\u001a\u0011\u0002\rqJg.\u001b;?)\r!gm\u001a\t\u0004K\u0002\u0001S\"\u0001\u0005\t\u000b])\u0001\u0019A\r\t\u000by)\u0001\u0019\u0001\u0011\u0002!\u0019LG\u000e\\%o'R\f7m\u001b+sC\u000e,G#\u00016\u0011\u0005-tgB\u0001\u001bm\u0013\ti'\"A\u0004qC\u000e\\\u0017mZ3\n\u0005=\u0004(!\u0003+ie><\u0018M\u00197f\u0015\ti'\u0002")
public class NonLocalReturnControl<T>
extends ControlThrowable {
    private final Object key;
    public final T value;

    public Object key() {
        return this.key;
    }

    public T value() {
        return this.value;
    }

    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }

    public boolean value$mcZ$sp() {
        return BoxesRunTime.unboxToBoolean(this.value());
    }

    public byte value$mcB$sp() {
        return BoxesRunTime.unboxToByte(this.value());
    }

    public char value$mcC$sp() {
        return BoxesRunTime.unboxToChar(this.value());
    }

    public double value$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.value());
    }

    public float value$mcF$sp() {
        return BoxesRunTime.unboxToFloat(this.value());
    }

    public int value$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.value());
    }

    public long value$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.value());
    }

    public short value$mcS$sp() {
        return BoxesRunTime.unboxToShort(this.value());
    }

    public void value$mcV$sp() {
        this.value();
    }

    public boolean specInstance$() {
        return false;
    }

    public NonLocalReturnControl(Object key, T value) {
        this.key = key;
        this.value = value;
    }
}

