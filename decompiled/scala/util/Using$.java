/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Option;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Using;
import scala.util.control.ControlThrowable;
import scala.util.control.NonFatal$;

public final class Using$ {
    public static final Using$ MODULE$ = new Using$();

    public <R, A> Try<A> apply(Function0<R> resource, Function1<R, A> f, Using.Releasable<R> evidence$1) {
        try {
            return new Success<Object>(Using$.$anonfun$apply$1(resource, f, evidence$1));
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable apply_e = option.get();
                return new Failure(apply_e);
            }
            throw throwable;
        }
    }

    public Throwable scala$util$Using$$preferentiallySuppress(Throwable primary, Throwable secondary) {
        if (Using$.score$1(secondary) > Using$.score$1(primary)) {
            secondary.addSuppressed(primary);
            return secondary;
        }
        primary.addSuppressed(secondary);
        return primary;
    }

    public <R, A> A resource(R resource, Function1<R, A> body2, Using.Releasable<R> releasable) {
        A a;
        if (resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable toThrow2 = null;
        try {
            try {
                a = body2.apply(resource);
            }
            catch (Throwable toThrow2) {
                a = null;
            }
        }
        catch (Throwable throwable) {
            if (toThrow2 != null) {
                try {
                    try {
                        releasable.release(resource);
                    }
                    catch (Throwable other) {
                        toThrow2 = this.scala$util$Using$$preferentiallySuppress(toThrow2, other);
                    }
                }
                catch (Throwable throwable2) {
                    throw toThrow2;
                }
                throw toThrow2;
            }
            releasable.release(resource);
            throw throwable;
        }
        A a2 = a;
        if (toThrow2 != null) {
            try {
                try {
                    releasable.release(resource);
                }
                catch (Throwable other) {
                    toThrow2 = this.scala$util$Using$$preferentiallySuppress(toThrow2, other);
                }
            }
            catch (Throwable throwable) {
                throw toThrow2;
            }
            throw toThrow2;
        }
        releasable.release(resource);
        return a2;
    }

    /*
     * WARNING - void declaration
     */
    public <R1, R2, A> A resources(R1 resource1, Function0<R2> resource2, Function2<R1, R2, A> body2, Using.Releasable<R1> evidence$4, Using.Releasable<R2> evidence$5) {
        Object v3;
        if (resource1 == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object v0;
                void $anonfun$resources$1_resource_resource;
                R2 R2 = resource2.apply();
                Using$ $anonfun$resources$1_resource_this = MODULE$;
                if ($anonfun$resources$1_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$1_resource_toThrow2 = null;
                try {
                    try {
                        v0 = body2.apply(resource1, $anonfun$resources$1_resource_resource);
                    }
                    catch (Throwable $anonfun$resources$1_resource_toThrow2) {
                        v0 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$1_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$5.release($anonfun$resources$1_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$1_resource_other) {
                                $anonfun$resources$1_resource_toThrow2 = $anonfun$resources$1_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$1_resource_toThrow2, $anonfun$resources$1_resource_other);
                            }
                        }
                        catch (Throwable throwable2) {
                            throw $anonfun$resources$1_resource_toThrow2;
                        }
                        throw $anonfun$resources$1_resource_toThrow2;
                    }
                    evidence$5.release($anonfun$resources$1_resource_resource);
                    throw throwable;
                }
                Object var13_15 = v0;
                if ($anonfun$resources$1_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$5.release($anonfun$resources$1_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$1_resource_other) {
                            $anonfun$resources$1_resource_toThrow2 = $anonfun$resources$1_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$1_resource_toThrow2, $anonfun$resources$1_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$1_resource_toThrow2;
                    }
                    throw $anonfun$resources$1_resource_toThrow2;
                }
                evidence$5.release($anonfun$resources$1_resource_resource);
                v3 = var13_15;
                Object var10_9 = null;
                R2 = null;
                var13_15 = null;
            }
            catch (Throwable resource_toThrow2) {
                v3 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$4.release(resource1);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable3) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$4.release(resource1);
            throw throwable;
        }
        Object var7_19 = v3;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$4.release(resource1);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$4.release(resource1);
        return var7_19;
    }

    /*
     * WARNING - void declaration
     */
    public <R1, R2, R3, A> A resources(R1 resource1, Function0<R2> resource2, Function0<R3> resource3, Function3<R1, R2, R3, A> body2, Using.Releasable<R1> evidence$6, Using.Releasable<R2> evidence$7, Using.Releasable<R3> evidence$8) {
        Object v6;
        if (resource1 == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object var18_15;
                Object v3;
                Object var21_23;
                R3 R3;
                void $anonfun$resources$3_resource_resource;
                R2 R2 = resource2.apply();
                Using$ $anonfun$resources$3_resource_this = MODULE$;
                if ($anonfun$resources$3_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$3_resource_toThrow2 = null;
                try {
                    try {
                        Object v0;
                        void $anonfun$resources$3_$anonfun$resources$4_resource_resource;
                        R3 = resource3.apply();
                        Using$ $anonfun$resources$3_$anonfun$resources$4_resource_this = MODULE$;
                        if ($anonfun$resources$3_$anonfun$resources$4_resource_resource == null) {
                            throw new NullPointerException("null resource");
                        }
                        Throwable $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2 = null;
                        try {
                            try {
                                v0 = body2.apply(resource1, $anonfun$resources$3_resource_resource, $anonfun$resources$3_$anonfun$resources$4_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2) {
                                v0 = null;
                            }
                        }
                        catch (Throwable throwable) {
                            if ($anonfun$resources$3_$anonfun$resources$4_resource_toThrow2 != null) {
                                try {
                                    try {
                                        evidence$8.release($anonfun$resources$3_$anonfun$resources$4_resource_resource);
                                    }
                                    catch (Throwable $anonfun$resources$3_$anonfun$resources$4_resource_other) {
                                        $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2 = $anonfun$resources$3_$anonfun$resources$4_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$3_$anonfun$resources$4_resource_toThrow2, $anonfun$resources$3_$anonfun$resources$4_resource_other);
                                    }
                                }
                                catch (Throwable throwable2) {
                                    throw $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2;
                                }
                                throw $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2;
                            }
                            evidence$8.release($anonfun$resources$3_$anonfun$resources$4_resource_resource);
                            throw throwable;
                        }
                        var21_23 = v0;
                        if ($anonfun$resources$3_$anonfun$resources$4_resource_toThrow2 != null) {
                            try {
                                try {
                                    evidence$8.release($anonfun$resources$3_$anonfun$resources$4_resource_resource);
                                }
                                catch (Throwable $anonfun$resources$3_$anonfun$resources$4_resource_other) {
                                    $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2 = $anonfun$resources$3_$anonfun$resources$4_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$3_$anonfun$resources$4_resource_toThrow2, $anonfun$resources$3_$anonfun$resources$4_resource_other);
                                }
                            }
                            catch (Throwable throwable) {
                                throw $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2;
                            }
                            throw $anonfun$resources$3_$anonfun$resources$4_resource_toThrow2;
                        }
                        evidence$8.release($anonfun$resources$3_$anonfun$resources$4_resource_resource);
                        v3 = var21_23;
                        var18_15 = null;
                        R3 = null;
                        var21_23 = null;
                    }
                    catch (Throwable $anonfun$resources$3_resource_toThrow2) {
                        v3 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$3_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$7.release($anonfun$resources$3_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$3_resource_other) {
                                $anonfun$resources$3_resource_toThrow2 = $anonfun$resources$3_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$3_resource_toThrow2, $anonfun$resources$3_resource_other);
                            }
                        }
                        catch (Throwable throwable3) {
                            throw $anonfun$resources$3_resource_toThrow2;
                        }
                        throw $anonfun$resources$3_resource_toThrow2;
                    }
                    evidence$7.release($anonfun$resources$3_resource_resource);
                    throw throwable;
                }
                Object var15_27 = v3;
                if ($anonfun$resources$3_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$7.release($anonfun$resources$3_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$3_resource_other) {
                            $anonfun$resources$3_resource_toThrow2 = $anonfun$resources$3_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$3_resource_toThrow2, $anonfun$resources$3_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$3_resource_toThrow2;
                    }
                    throw $anonfun$resources$3_resource_toThrow2;
                }
                evidence$7.release($anonfun$resources$3_resource_resource);
                v6 = var15_27;
                Object var12_11 = null;
                R2 = null;
                var15_27 = null;
                var18_15 = null;
                R3 = null;
                Object var20_16 = null;
                var21_23 = null;
                Object var22_19 = null;
                Object var23_22 = null;
            }
            catch (Throwable resource_toThrow2) {
                v6 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$6.release(resource1);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable4) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$6.release(resource1);
            throw throwable;
        }
        Object var9_31 = v6;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$6.release(resource1);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$6.release(resource1);
        return var9_31;
    }

    /*
     * WARNING - void declaration
     */
    public <R1, R2, R3, R4, A> A resources(R1 resource1, Function0<R2> resource2, Function0<R3> resource3, Function0<R4> resource4, Function4<R1, R2, R3, R4, A> body2, Using.Releasable<R1> evidence$9, Using.Releasable<R2> evidence$10, Using.Releasable<R3> evidence$11, Using.Releasable<R4> evidence$12) {
        Object v9;
        if (resource1 == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object var20_17;
                Object v6;
                Object var23_33;
                R3 R3;
                void $anonfun$resources$6_resource_resource;
                R2 R2 = resource2.apply();
                Using$ $anonfun$resources$6_resource_this = MODULE$;
                if ($anonfun$resources$6_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$6_resource_toThrow2 = null;
                try {
                    try {
                        Object v3;
                        void $anonfun$resources$6_$anonfun$resources$7_resource_resource;
                        R3 = resource3.apply();
                        Using$ $anonfun$resources$6_$anonfun$resources$7_resource_this = MODULE$;
                        if ($anonfun$resources$6_$anonfun$resources$7_resource_resource == null) {
                            throw new NullPointerException("null resource");
                        }
                        Throwable $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2 = null;
                        try {
                            try {
                                Object v0;
                                void $anonfun$resources$8_resource_resource;
                                R4 R4 = resource4.apply();
                                Using$ $anonfun$resources$8_resource_this = MODULE$;
                                if ($anonfun$resources$8_resource_resource == null) {
                                    throw new NullPointerException("null resource");
                                }
                                Throwable $anonfun$resources$8_resource_toThrow2 = null;
                                try {
                                    try {
                                        v0 = body2.apply(resource1, $anonfun$resources$6_resource_resource, $anonfun$resources$6_$anonfun$resources$7_resource_resource, $anonfun$resources$8_resource_resource);
                                    }
                                    catch (Throwable $anonfun$resources$8_resource_toThrow2) {
                                        v0 = null;
                                    }
                                }
                                catch (Throwable throwable) {
                                    if ($anonfun$resources$8_resource_toThrow2 != null) {
                                        try {
                                            try {
                                                evidence$12.release($anonfun$resources$8_resource_resource);
                                            }
                                            catch (Throwable $anonfun$resources$8_resource_other) {
                                                $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                                            }
                                        }
                                        catch (Throwable throwable2) {
                                            throw $anonfun$resources$8_resource_toThrow2;
                                        }
                                        throw $anonfun$resources$8_resource_toThrow2;
                                    }
                                    evidence$12.release($anonfun$resources$8_resource_resource);
                                    throw throwable;
                                }
                                Object var29_27 = v0;
                                if ($anonfun$resources$8_resource_toThrow2 != null) {
                                    try {
                                        try {
                                            evidence$12.release($anonfun$resources$8_resource_resource);
                                        }
                                        catch (Throwable $anonfun$resources$8_resource_other) {
                                            $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        throw $anonfun$resources$8_resource_toThrow2;
                                    }
                                    throw $anonfun$resources$8_resource_toThrow2;
                                }
                                evidence$12.release($anonfun$resources$8_resource_resource);
                                v3 = var29_27;
                                Object var26_21 = null;
                                R4 = null;
                                var29_27 = null;
                            }
                            catch (Throwable $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2) {
                                v3 = null;
                            }
                        }
                        catch (Throwable throwable) {
                            if ($anonfun$resources$6_$anonfun$resources$7_resource_toThrow2 != null) {
                                try {
                                    try {
                                        evidence$11.release($anonfun$resources$6_$anonfun$resources$7_resource_resource);
                                    }
                                    catch (Throwable $anonfun$resources$6_$anonfun$resources$7_resource_other) {
                                        $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2 = $anonfun$resources$6_$anonfun$resources$7_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$6_$anonfun$resources$7_resource_toThrow2, $anonfun$resources$6_$anonfun$resources$7_resource_other);
                                    }
                                }
                                catch (Throwable throwable3) {
                                    throw $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2;
                                }
                                throw $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2;
                            }
                            evidence$11.release($anonfun$resources$6_$anonfun$resources$7_resource_resource);
                            throw throwable;
                        }
                        var23_33 = v3;
                        if ($anonfun$resources$6_$anonfun$resources$7_resource_toThrow2 != null) {
                            try {
                                try {
                                    evidence$11.release($anonfun$resources$6_$anonfun$resources$7_resource_resource);
                                }
                                catch (Throwable $anonfun$resources$6_$anonfun$resources$7_resource_other) {
                                    $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2 = $anonfun$resources$6_$anonfun$resources$7_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$6_$anonfun$resources$7_resource_toThrow2, $anonfun$resources$6_$anonfun$resources$7_resource_other);
                                }
                            }
                            catch (Throwable throwable) {
                                throw $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2;
                            }
                            throw $anonfun$resources$6_$anonfun$resources$7_resource_toThrow2;
                        }
                        evidence$11.release($anonfun$resources$6_$anonfun$resources$7_resource_resource);
                        v6 = var23_33;
                        var20_17 = null;
                        R3 = null;
                        var23_33 = null;
                    }
                    catch (Throwable $anonfun$resources$6_resource_toThrow2) {
                        v6 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$6_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$10.release($anonfun$resources$6_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$6_resource_other) {
                                $anonfun$resources$6_resource_toThrow2 = $anonfun$resources$6_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$6_resource_toThrow2, $anonfun$resources$6_resource_other);
                            }
                        }
                        catch (Throwable throwable4) {
                            throw $anonfun$resources$6_resource_toThrow2;
                        }
                        throw $anonfun$resources$6_resource_toThrow2;
                    }
                    evidence$10.release($anonfun$resources$6_resource_resource);
                    throw throwable;
                }
                Object var17_37 = v6;
                if ($anonfun$resources$6_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$10.release($anonfun$resources$6_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$6_resource_other) {
                            $anonfun$resources$6_resource_toThrow2 = $anonfun$resources$6_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$6_resource_toThrow2, $anonfun$resources$6_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$6_resource_toThrow2;
                    }
                    throw $anonfun$resources$6_resource_toThrow2;
                }
                evidence$10.release($anonfun$resources$6_resource_resource);
                v9 = var17_37;
                Object var14_13 = null;
                R2 = null;
                var17_37 = null;
                var20_17 = null;
                R3 = null;
                Object var22_18 = null;
                var23_33 = null;
                Object var24_29 = null;
                Object var25_32 = null;
            }
            catch (Throwable resource_toThrow2) {
                v9 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$9.release(resource1);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable5) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$9.release(resource1);
            throw throwable;
        }
        Object var11_41 = v9;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$9.release(resource1);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$9.release(resource1);
        return var11_41;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$apply$1(Function0 resource$1, Function1 f$1, Using.Releasable evidence$1$1) {
        Object r;
        void resource_resource;
        Object r2 = resource$1.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                r = f$1.apply(resource_resource);
            }
            catch (Throwable resource_toThrow2) {
                r = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$1$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable2) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$1$1.release(resource_resource);
            throw throwable;
        }
        Object r3 = r;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$1$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$1$1.release(resource_resource);
        return r3;
    }

    private static final int score$1(Throwable t2) {
        if (t2 instanceof VirtualMachineError) {
            return 4;
        }
        if (t2 instanceof LinkageError) {
            return 3;
        }
        if (t2 instanceof InterruptedException ? true : t2 instanceof ThreadDeath) {
            return 2;
        }
        if (t2 instanceof ControlThrowable) {
            return 0;
        }
        if (!NonFatal$.MODULE$.apply(t2)) {
            return 1;
        }
        return -1;
    }

    private static final Throwable suppress$1(Throwable t2, Throwable suppressed) {
        t2.addSuppressed(suppressed);
        return t2;
    }

    public static final /* synthetic */ Object $anonfun$resources$2(Function2 body$1, Object r1$1, Object r2) {
        return body$1.apply(r1$1, r2);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$1(Function0 resource2$1, Function2 body$1, Using.Releasable evidence$5$1, Object r1) {
        Object v0;
        void resource_resource;
        Object r = resource2$1.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                v0 = body$1.apply(r1, resource_resource);
            }
            catch (Throwable resource_toThrow2) {
                v0 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$5$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable2) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$5$1.release(resource_resource);
            throw throwable;
        }
        Object var7_11 = v0;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$5$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$5$1.release(resource_resource);
        return var7_11;
    }

    public static final /* synthetic */ Object $anonfun$resources$5(Function3 body$2, Object r1$2, Object r2$1, Object r3) {
        return body$2.apply(r1$2, r2$1, r3);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$4(Function0 resource3$1, Function3 body$2, Object r1$2, Using.Releasable evidence$8$1, Object r2) {
        Object v0;
        void resource_resource;
        Object r = resource3$1.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                v0 = body$2.apply(r1$2, r2, resource_resource);
            }
            catch (Throwable resource_toThrow2) {
                v0 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$8$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable2) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$8$1.release(resource_resource);
            throw throwable;
        }
        Object var8_12 = v0;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$8$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$8$1.release(resource_resource);
        return var8_12;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$3(Function0 resource2$2, Function0 resource3$1, Function3 body$2, Using.Releasable evidence$8$1, Using.Releasable evidence$7$1, Object r1) {
        Object v3;
        void resource_resource;
        Object r = resource2$2.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object v0;
                void $anonfun$resources$4_resource_resource;
                Object r2 = resource3$1.apply();
                Using$ $anonfun$resources$4_resource_this = MODULE$;
                if ($anonfun$resources$4_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$4_resource_toThrow2 = null;
                try {
                    try {
                        v0 = body$2.apply(r1, resource_resource, $anonfun$resources$4_resource_resource);
                    }
                    catch (Throwable $anonfun$resources$4_resource_toThrow2) {
                        v0 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$4_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$8$1.release($anonfun$resources$4_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$4_resource_other) {
                                $anonfun$resources$4_resource_toThrow2 = $anonfun$resources$4_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$4_resource_toThrow2, $anonfun$resources$4_resource_other);
                            }
                        }
                        catch (Throwable throwable2) {
                            throw $anonfun$resources$4_resource_toThrow2;
                        }
                        throw $anonfun$resources$4_resource_toThrow2;
                    }
                    evidence$8$1.release($anonfun$resources$4_resource_resource);
                    throw throwable;
                }
                Object var15_17 = v0;
                if ($anonfun$resources$4_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$8$1.release($anonfun$resources$4_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$4_resource_other) {
                            $anonfun$resources$4_resource_toThrow2 = $anonfun$resources$4_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$4_resource_toThrow2, $anonfun$resources$4_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$4_resource_toThrow2;
                    }
                    throw $anonfun$resources$4_resource_toThrow2;
                }
                evidence$8$1.release($anonfun$resources$4_resource_resource);
                v3 = var15_17;
                Object var12_11 = null;
                r2 = null;
                var15_17 = null;
            }
            catch (Throwable resource_toThrow2) {
                v3 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$7$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable3) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$7$1.release(resource_resource);
            throw throwable;
        }
        Object var9_21 = v3;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$7$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$7$1.release(resource_resource);
        return var9_21;
    }

    public static final /* synthetic */ Object $anonfun$resources$9(Function4 body$3, Object r1$3, Object r2$2, Object r3$1, Object r4) {
        return body$3.apply(r1$3, r2$2, r3$1, r4);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$8(Function0 resource4$1, Function4 body$3, Object r1$3, Object r2$2, Using.Releasable evidence$12$1, Object r3) {
        Object v0;
        void resource_resource;
        Object r = resource4$1.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                v0 = body$3.apply(r1$3, r2$2, r3, resource_resource);
            }
            catch (Throwable resource_toThrow2) {
                v0 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$12$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable2) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$12$1.release(resource_resource);
            throw throwable;
        }
        Object var9_13 = v0;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$12$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$12$1.release(resource_resource);
        return var9_13;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$7(Function0 resource3$2, Function0 resource4$1, Function4 body$3, Object r1$3, Using.Releasable evidence$12$1, Using.Releasable evidence$11$1, Object r2) {
        Object v3;
        void resource_resource;
        Object r = resource3$2.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object v0;
                void $anonfun$resources$8_resource_resource;
                Object r3 = resource4$1.apply();
                Using$ $anonfun$resources$8_resource_this = MODULE$;
                if ($anonfun$resources$8_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$8_resource_toThrow2 = null;
                try {
                    try {
                        v0 = body$3.apply(r1$3, r2, resource_resource, $anonfun$resources$8_resource_resource);
                    }
                    catch (Throwable $anonfun$resources$8_resource_toThrow2) {
                        v0 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$8_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$12$1.release($anonfun$resources$8_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$8_resource_other) {
                                $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                            }
                        }
                        catch (Throwable throwable2) {
                            throw $anonfun$resources$8_resource_toThrow2;
                        }
                        throw $anonfun$resources$8_resource_toThrow2;
                    }
                    evidence$12$1.release($anonfun$resources$8_resource_resource);
                    throw throwable;
                }
                Object var16_18 = v0;
                if ($anonfun$resources$8_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$12$1.release($anonfun$resources$8_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$8_resource_other) {
                            $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$8_resource_toThrow2;
                    }
                    throw $anonfun$resources$8_resource_toThrow2;
                }
                evidence$12$1.release($anonfun$resources$8_resource_resource);
                v3 = var16_18;
                Object var13_12 = null;
                r3 = null;
                var16_18 = null;
            }
            catch (Throwable resource_toThrow2) {
                v3 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$11$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable3) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$11$1.release(resource_resource);
            throw throwable;
        }
        Object var10_22 = v3;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$11$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$11$1.release(resource_resource);
        return var10_22;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$resources$6(Function0 resource2$3, Function0 resource3$2, Function0 resource4$1, Function4 body$3, Using.Releasable evidence$12$1, Using.Releasable evidence$11$1, Using.Releasable evidence$10$1, Object r1) {
        Object v6;
        void resource_resource;
        Object r = resource2$3.apply();
        Using$ resource_this = MODULE$;
        if (resource_resource == null) {
            throw new NullPointerException("null resource");
        }
        Throwable resource_toThrow2 = null;
        try {
            try {
                Object v3;
                void $anonfun$resources$7_resource_resource;
                Object r2 = resource3$2.apply();
                Using$ $anonfun$resources$7_resource_this = MODULE$;
                if ($anonfun$resources$7_resource_resource == null) {
                    throw new NullPointerException("null resource");
                }
                Throwable $anonfun$resources$7_resource_toThrow2 = null;
                try {
                    try {
                        Object v0;
                        void $anonfun$resources$8_resource_resource;
                        Object r3 = resource4$1.apply();
                        Using$ $anonfun$resources$8_resource_this = MODULE$;
                        if ($anonfun$resources$8_resource_resource == null) {
                            throw new NullPointerException("null resource");
                        }
                        Throwable $anonfun$resources$8_resource_toThrow2 = null;
                        try {
                            try {
                                v0 = body$3.apply(r1, resource_resource, $anonfun$resources$7_resource_resource, $anonfun$resources$8_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$8_resource_toThrow2) {
                                v0 = null;
                            }
                        }
                        catch (Throwable throwable) {
                            if ($anonfun$resources$8_resource_toThrow2 != null) {
                                try {
                                    try {
                                        evidence$12$1.release($anonfun$resources$8_resource_resource);
                                    }
                                    catch (Throwable $anonfun$resources$8_resource_other) {
                                        $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                                    }
                                }
                                catch (Throwable throwable2) {
                                    throw $anonfun$resources$8_resource_toThrow2;
                                }
                                throw $anonfun$resources$8_resource_toThrow2;
                            }
                            evidence$12$1.release($anonfun$resources$8_resource_resource);
                            throw throwable;
                        }
                        Object var23_23 = v0;
                        if ($anonfun$resources$8_resource_toThrow2 != null) {
                            try {
                                try {
                                    evidence$12$1.release($anonfun$resources$8_resource_resource);
                                }
                                catch (Throwable $anonfun$resources$8_resource_other) {
                                    $anonfun$resources$8_resource_toThrow2 = $anonfun$resources$8_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$8_resource_toThrow2, $anonfun$resources$8_resource_other);
                                }
                            }
                            catch (Throwable throwable) {
                                throw $anonfun$resources$8_resource_toThrow2;
                            }
                            throw $anonfun$resources$8_resource_toThrow2;
                        }
                        evidence$12$1.release($anonfun$resources$8_resource_resource);
                        v3 = var23_23;
                        Object var20_17 = null;
                        r3 = null;
                        var23_23 = null;
                    }
                    catch (Throwable $anonfun$resources$7_resource_toThrow2) {
                        v3 = null;
                    }
                }
                catch (Throwable throwable) {
                    if ($anonfun$resources$7_resource_toThrow2 != null) {
                        try {
                            try {
                                evidence$11$1.release($anonfun$resources$7_resource_resource);
                            }
                            catch (Throwable $anonfun$resources$7_resource_other) {
                                $anonfun$resources$7_resource_toThrow2 = $anonfun$resources$7_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$7_resource_toThrow2, $anonfun$resources$7_resource_other);
                            }
                        }
                        catch (Throwable throwable3) {
                            throw $anonfun$resources$7_resource_toThrow2;
                        }
                        throw $anonfun$resources$7_resource_toThrow2;
                    }
                    evidence$11$1.release($anonfun$resources$7_resource_resource);
                    throw throwable;
                }
                Object var17_27 = v3;
                if ($anonfun$resources$7_resource_toThrow2 != null) {
                    try {
                        try {
                            evidence$11$1.release($anonfun$resources$7_resource_resource);
                        }
                        catch (Throwable $anonfun$resources$7_resource_other) {
                            $anonfun$resources$7_resource_toThrow2 = $anonfun$resources$7_resource_this.scala$util$Using$$preferentiallySuppress($anonfun$resources$7_resource_toThrow2, $anonfun$resources$7_resource_other);
                        }
                    }
                    catch (Throwable throwable) {
                        throw $anonfun$resources$7_resource_toThrow2;
                    }
                    throw $anonfun$resources$7_resource_toThrow2;
                }
                evidence$11$1.release($anonfun$resources$7_resource_resource);
                v6 = var17_27;
                Object var14_13 = null;
                r2 = null;
                var17_27 = null;
            }
            catch (Throwable resource_toThrow2) {
                v6 = null;
            }
        }
        catch (Throwable throwable) {
            if (resource_toThrow2 != null) {
                try {
                    try {
                        evidence$10$1.release(resource_resource);
                    }
                    catch (Throwable resource_other) {
                        resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                    }
                }
                catch (Throwable throwable4) {
                    throw resource_toThrow2;
                }
                throw resource_toThrow2;
            }
            evidence$10$1.release(resource_resource);
            throw throwable;
        }
        Object var11_31 = v6;
        if (resource_toThrow2 != null) {
            try {
                try {
                    evidence$10$1.release(resource_resource);
                }
                catch (Throwable resource_other) {
                    resource_toThrow2 = resource_this.scala$util$Using$$preferentiallySuppress(resource_toThrow2, resource_other);
                }
            }
            catch (Throwable throwable) {
                throw resource_toThrow2;
            }
            throw resource_toThrow2;
        }
        evidence$10$1.release(resource_resource);
        return var11_31;
    }

    private Using$() {
    }
}

