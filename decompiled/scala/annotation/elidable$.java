/*
 * Decompiled with CFR 0.152.
 */
package scala.annotation;

import scala.Predef$;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.runtime.ScalaRunTime$;

public final class elidable$ {
    public static final elidable$ MODULE$;
    private static final Map<String, Object> byName;

    /*
     * WARNING - void declaration
     */
    static {
        void apply_elems;
        void $minus$greater$extension_y;
        void $minus$greater$extension_y2;
        void $minus$greater$extension_y3;
        void $minus$greater$extension_y4;
        void $minus$greater$extension_y5;
        void $minus$greater$extension_y6;
        void $minus$greater$extension_y7;
        void $minus$greater$extension_y8;
        void $minus$greater$extension_y9;
        void $minus$greater$extension_y10;
        MODULE$ = new elidable$();
        Tuple2[] tuple2Array = new Tuple2[12];
        Integer $minus$greater$extension_y11 = 300;
        String $minus$greater$extension_$this = "FINEST";
        Object var0_1 = null;
        Object var1 = null;
        tuple2Array[0] = new Tuple2<String, Integer>($minus$greater$extension_$this, $minus$greater$extension_y11);
        Integer $minus$greater$extension_y12 = 400;
        String $minus$greater$extension_$this2 = "FINER";
        Object var2_3 = null;
        Object var3_2 = null;
        tuple2Array[1] = new Tuple2<String, Integer>($minus$greater$extension_$this2, $minus$greater$extension_y12);
        Integer n = 500;
        String $minus$greater$extension_$this3 = "FINE";
        Object var4_5 = null;
        n = null;
        tuple2Array[2] = new Tuple2<String, void>($minus$greater$extension_$this3, $minus$greater$extension_y10);
        Integer n2 = 700;
        String $minus$greater$extension_$this4 = "CONFIG";
        Object var6_7 = null;
        n2 = null;
        tuple2Array[3] = new Tuple2<String, void>($minus$greater$extension_$this4, $minus$greater$extension_y9);
        Integer n3 = 800;
        String $minus$greater$extension_$this5 = "INFO";
        Object var8_9 = null;
        n3 = null;
        tuple2Array[4] = new Tuple2<String, void>($minus$greater$extension_$this5, $minus$greater$extension_y8);
        Integer n4 = 900;
        String $minus$greater$extension_$this6 = "WARNING";
        Object var10_11 = null;
        n4 = null;
        tuple2Array[5] = new Tuple2<String, void>($minus$greater$extension_$this6, $minus$greater$extension_y7);
        Integer n5 = 1000;
        String $minus$greater$extension_$this7 = "SEVERE";
        Object var12_13 = null;
        n5 = null;
        tuple2Array[6] = new Tuple2<String, void>($minus$greater$extension_$this7, $minus$greater$extension_y6);
        Integer n6 = 2000;
        String $minus$greater$extension_$this8 = "ASSERTION";
        Object var14_15 = null;
        n6 = null;
        tuple2Array[7] = new Tuple2<String, void>($minus$greater$extension_$this8, $minus$greater$extension_y5);
        Integer n7 = Integer.MIN_VALUE;
        String $minus$greater$extension_$this9 = "ALL";
        Object var16_17 = null;
        n7 = null;
        tuple2Array[8] = new Tuple2<String, void>($minus$greater$extension_$this9, $minus$greater$extension_y4);
        Integer n8 = Integer.MAX_VALUE;
        String $minus$greater$extension_$this10 = "OFF";
        Object var18_19 = null;
        n8 = null;
        tuple2Array[9] = new Tuple2<String, void>($minus$greater$extension_$this10, $minus$greater$extension_y3);
        Integer n9 = Integer.MAX_VALUE;
        String $minus$greater$extension_$this11 = "MAXIMUM";
        Object var20_21 = null;
        n9 = null;
        tuple2Array[10] = new Tuple2<String, void>($minus$greater$extension_$this11, $minus$greater$extension_y2);
        Integer n10 = Integer.MIN_VALUE;
        String $minus$greater$extension_$this12 = "MINIMUM";
        Object var22_23 = null;
        n10 = null;
        tuple2Array[11] = new Tuple2<String, void>($minus$greater$extension_$this12, $minus$greater$extension_y);
        ArraySeq<Tuple2> arraySeq = ScalaRunTime$.MODULE$.wrapRefArray(tuple2Array);
        Map$ apply_this = Predef$.MODULE$.Map();
        if (apply_this == null) {
            throw null;
        }
        byName = apply_this.from((IterableOnce)apply_elems);
    }

    public final int ALL() {
        return Integer.MIN_VALUE;
    }

    public final int FINEST() {
        return 300;
    }

    public final int FINER() {
        return 400;
    }

    public final int FINE() {
        return 500;
    }

    public final int CONFIG() {
        return 700;
    }

    public final int INFO() {
        return 800;
    }

    public final int WARNING() {
        return 900;
    }

    public final int SEVERE() {
        return 1000;
    }

    public final int OFF() {
        return Integer.MAX_VALUE;
    }

    public final int MAXIMUM() {
        return Integer.MAX_VALUE;
    }

    public final int MINIMUM() {
        return Integer.MIN_VALUE;
    }

    public final int ASSERTION() {
        return 2000;
    }

    public Map<String, Object> byName() {
        return byName;
    }

    private elidable$() {
    }
}

