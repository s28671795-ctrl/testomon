/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

public final class FI {
    private FI() {
    }

    @FunctionalInterface
    static interface Predicate {
        public boolean defined(Object var1);
    }

    @FunctionalInterface
    public static interface UnitApplyVoid {
        public void apply() throws Exception;
    }

    @FunctionalInterface
    public static interface UnitApply4<I1, I2, I3, I4> {
        public void apply(I1 var1, I2 var2, I3 var3, I4 var4) throws Exception;
    }

    @FunctionalInterface
    public static interface UnitApply3<I1, I2, I3> {
        public void apply(I1 var1, I2 var2, I3 var3) throws Exception;
    }

    @FunctionalInterface
    public static interface UnitApply2<I1, I2> {
        public void apply(I1 var1, I2 var2) throws Exception;
    }

    @FunctionalInterface
    public static interface UnitApply<I> {
        public void apply(I var1) throws Exception;
    }

    @FunctionalInterface
    public static interface TypedPredicate2<T, U> {
        public boolean defined(T var1, U var2);
    }

    @FunctionalInterface
    public static interface TypedPredicate<T> {
        public boolean defined(T var1);
    }

    @FunctionalInterface
    public static interface Apply2<I1, I2, R> {
        public R apply(I1 var1, I2 var2) throws Exception;
    }

    @FunctionalInterface
    public static interface Apply<I, R> {
        public R apply(I var1) throws Exception;
    }
}

