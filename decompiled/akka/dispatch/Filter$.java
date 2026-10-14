/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.japi.Function;
import scala.Function1;

public final class Filter$ {
    public static final Filter$ MODULE$ = new Filter$();

    public <T> Function1<T, Object> filterOf(Function<T, Boolean> f) {
        return new Function1<T, Object>(f){
            private final Function f$1;

            public boolean apply$mcZD$sp(double v1) {
                return Function1.apply$mcZD$sp$(this, v1);
            }

            public double apply$mcDD$sp(double v1) {
                return Function1.apply$mcDD$sp$(this, v1);
            }

            public float apply$mcFD$sp(double v1) {
                return Function1.apply$mcFD$sp$(this, v1);
            }

            public int apply$mcID$sp(double v1) {
                return Function1.apply$mcID$sp$(this, v1);
            }

            public long apply$mcJD$sp(double v1) {
                return Function1.apply$mcJD$sp$(this, v1);
            }

            public void apply$mcVD$sp(double v1) {
                Function1.apply$mcVD$sp$(this, v1);
            }

            public boolean apply$mcZF$sp(float v1) {
                return Function1.apply$mcZF$sp$(this, v1);
            }

            public double apply$mcDF$sp(float v1) {
                return Function1.apply$mcDF$sp$(this, v1);
            }

            public float apply$mcFF$sp(float v1) {
                return Function1.apply$mcFF$sp$(this, v1);
            }

            public int apply$mcIF$sp(float v1) {
                return Function1.apply$mcIF$sp$(this, v1);
            }

            public long apply$mcJF$sp(float v1) {
                return Function1.apply$mcJF$sp$(this, v1);
            }

            public void apply$mcVF$sp(float v1) {
                Function1.apply$mcVF$sp$(this, v1);
            }

            public boolean apply$mcZI$sp(int v1) {
                return Function1.apply$mcZI$sp$(this, v1);
            }

            public double apply$mcDI$sp(int v1) {
                return Function1.apply$mcDI$sp$(this, v1);
            }

            public float apply$mcFI$sp(int v1) {
                return Function1.apply$mcFI$sp$(this, v1);
            }

            public int apply$mcII$sp(int v1) {
                return Function1.apply$mcII$sp$(this, v1);
            }

            public long apply$mcJI$sp(int v1) {
                return Function1.apply$mcJI$sp$(this, v1);
            }

            public void apply$mcVI$sp(int v1) {
                Function1.apply$mcVI$sp$(this, v1);
            }

            public boolean apply$mcZJ$sp(long v1) {
                return Function1.apply$mcZJ$sp$(this, v1);
            }

            public double apply$mcDJ$sp(long v1) {
                return Function1.apply$mcDJ$sp$(this, v1);
            }

            public float apply$mcFJ$sp(long v1) {
                return Function1.apply$mcFJ$sp$(this, v1);
            }

            public int apply$mcIJ$sp(long v1) {
                return Function1.apply$mcIJ$sp$(this, v1);
            }

            public long apply$mcJJ$sp(long v1) {
                return Function1.apply$mcJJ$sp$(this, v1);
            }

            public void apply$mcVJ$sp(long v1) {
                Function1.apply$mcVJ$sp$(this, v1);
            }

            public <A> Function1<A, Object> compose(Function1<A, T> g2) {
                return Function1.compose$(this, g2);
            }

            public <A> Function1<T, A> andThen(Function1<Object, A> g2) {
                return Function1.andThen$(this, g2);
            }

            public String toString() {
                return Function1.toString$(this);
            }

            public boolean apply(T result) {
                return (Boolean)this.f$1.apply(result);
            }
            {
                this.f$1 = f$1;
                Function1.$init$(this);
            }
        };
    }

    private Filter$() {
    }
}

