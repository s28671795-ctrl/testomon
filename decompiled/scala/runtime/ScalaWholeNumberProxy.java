/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.reflect.ScalaSignature;
import scala.runtime.ScalaNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\u00152qa\u0001\u0003\u0011\u0002\u0007\u0005\u0011\u0002C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0011\u0005\u0011EA\u000bTG\u0006d\u0017m\u00165pY\u0016tU/\u001c2feB\u0013x\u000e_=\u000b\u0005\u00151\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u000f\u0005)1oY1mC\u000e\u0001QC\u0001\u0006\u0016'\r\u00011b\u0004\t\u0003\u00195i\u0011AB\u0005\u0003\u001d\u0019\u00111!\u00118z!\r\u0001\u0012cE\u0007\u0002\t%\u0011!\u0003\u0002\u0002\u0011'\u000e\fG.\u0019(v[\n,'\u000f\u0015:pqf\u0004\"\u0001F\u000b\r\u0001\u0011)a\u0003\u0001b\u0001/\t\tA+\u0005\u0002\u0019\u0017A\u0011A\"G\u0005\u00035\u0019\u0011qAT8uQ&tw-\u0001\u0004%S:LG\u000f\n\u000b\u0002;A\u0011ABH\u0005\u0003?\u0019\u0011A!\u00168ji\u00069\u0011n],i_2,W#\u0001\u0012\u0011\u00051\u0019\u0013B\u0001\u0013\u0007\u0005\u001d\u0011un\u001c7fC:\u0004")
public interface ScalaWholeNumberProxy<T>
extends ScalaNumberProxy<T> {
    public static /* synthetic */ boolean isWhole$(ScalaWholeNumberProxy $this) {
        return $this.isWhole();
    }

    @Override
    default public boolean isWhole() {
        return true;
    }

    public static void $init$(ScalaWholeNumberProxy $this) {
    }
}

