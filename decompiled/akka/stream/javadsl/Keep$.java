/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.japi.Pair;
import akka.japi.function.Function2;
import scala.Function1;
import scala.Tuple2;

public final class Keep$ {
    public static final Keep$ MODULE$ = new Keep$();
    private static final Function2<Object, Object, Object> _left = new Function2<Object, Object, Object>(){

        public boolean apply$mcZDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcZDD$sp$(this, v1, v2);
        }

        public double apply$mcDDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcDDD$sp$(this, v1, v2);
        }

        public float apply$mcFDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcFDD$sp$(this, v1, v2);
        }

        public int apply$mcIDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcIDD$sp$(this, v1, v2);
        }

        public long apply$mcJDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcJDD$sp$(this, v1, v2);
        }

        public void apply$mcVDD$sp(double v1, double v2) {
            scala.Function2.apply$mcVDD$sp$(this, v1, v2);
        }

        public boolean apply$mcZDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcZDI$sp$(this, v1, v2);
        }

        public double apply$mcDDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcDDI$sp$(this, v1, v2);
        }

        public float apply$mcFDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcFDI$sp$(this, v1, v2);
        }

        public int apply$mcIDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcIDI$sp$(this, v1, v2);
        }

        public long apply$mcJDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcJDI$sp$(this, v1, v2);
        }

        public void apply$mcVDI$sp(double v1, int v2) {
            scala.Function2.apply$mcVDI$sp$(this, v1, v2);
        }

        public boolean apply$mcZDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcZDJ$sp$(this, v1, v2);
        }

        public double apply$mcDDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcDDJ$sp$(this, v1, v2);
        }

        public float apply$mcFDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcFDJ$sp$(this, v1, v2);
        }

        public int apply$mcIDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcIDJ$sp$(this, v1, v2);
        }

        public long apply$mcJDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcJDJ$sp$(this, v1, v2);
        }

        public void apply$mcVDJ$sp(double v1, long v2) {
            scala.Function2.apply$mcVDJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZID$sp(int v1, double v2) {
            return scala.Function2.apply$mcZID$sp$(this, v1, v2);
        }

        public double apply$mcDID$sp(int v1, double v2) {
            return scala.Function2.apply$mcDID$sp$(this, v1, v2);
        }

        public float apply$mcFID$sp(int v1, double v2) {
            return scala.Function2.apply$mcFID$sp$(this, v1, v2);
        }

        public int apply$mcIID$sp(int v1, double v2) {
            return scala.Function2.apply$mcIID$sp$(this, v1, v2);
        }

        public long apply$mcJID$sp(int v1, double v2) {
            return scala.Function2.apply$mcJID$sp$(this, v1, v2);
        }

        public void apply$mcVID$sp(int v1, double v2) {
            scala.Function2.apply$mcVID$sp$(this, v1, v2);
        }

        public boolean apply$mcZII$sp(int v1, int v2) {
            return scala.Function2.apply$mcZII$sp$(this, v1, v2);
        }

        public double apply$mcDII$sp(int v1, int v2) {
            return scala.Function2.apply$mcDII$sp$(this, v1, v2);
        }

        public float apply$mcFII$sp(int v1, int v2) {
            return scala.Function2.apply$mcFII$sp$(this, v1, v2);
        }

        public int apply$mcIII$sp(int v1, int v2) {
            return scala.Function2.apply$mcIII$sp$(this, v1, v2);
        }

        public long apply$mcJII$sp(int v1, int v2) {
            return scala.Function2.apply$mcJII$sp$(this, v1, v2);
        }

        public void apply$mcVII$sp(int v1, int v2) {
            scala.Function2.apply$mcVII$sp$(this, v1, v2);
        }

        public boolean apply$mcZIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcZIJ$sp$(this, v1, v2);
        }

        public double apply$mcDIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcDIJ$sp$(this, v1, v2);
        }

        public float apply$mcFIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcFIJ$sp$(this, v1, v2);
        }

        public int apply$mcIIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcIIJ$sp$(this, v1, v2);
        }

        public long apply$mcJIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcJIJ$sp$(this, v1, v2);
        }

        public void apply$mcVIJ$sp(int v1, long v2) {
            scala.Function2.apply$mcVIJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcZJD$sp$(this, v1, v2);
        }

        public double apply$mcDJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcDJD$sp$(this, v1, v2);
        }

        public float apply$mcFJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcFJD$sp$(this, v1, v2);
        }

        public int apply$mcIJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcIJD$sp$(this, v1, v2);
        }

        public long apply$mcJJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcJJD$sp$(this, v1, v2);
        }

        public void apply$mcVJD$sp(long v1, double v2) {
            scala.Function2.apply$mcVJD$sp$(this, v1, v2);
        }

        public boolean apply$mcZJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcZJI$sp$(this, v1, v2);
        }

        public double apply$mcDJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcDJI$sp$(this, v1, v2);
        }

        public float apply$mcFJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcFJI$sp$(this, v1, v2);
        }

        public int apply$mcIJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcIJI$sp$(this, v1, v2);
        }

        public long apply$mcJJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcJJI$sp$(this, v1, v2);
        }

        public void apply$mcVJI$sp(long v1, int v2) {
            scala.Function2.apply$mcVJI$sp$(this, v1, v2);
        }

        public boolean apply$mcZJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcZJJ$sp$(this, v1, v2);
        }

        public double apply$mcDJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcDJJ$sp$(this, v1, v2);
        }

        public float apply$mcFJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcFJJ$sp$(this, v1, v2);
        }

        public int apply$mcIJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcIJJ$sp$(this, v1, v2);
        }

        public long apply$mcJJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcJJJ$sp$(this, v1, v2);
        }

        public void apply$mcVJJ$sp(long v1, long v2) {
            scala.Function2.apply$mcVJJ$sp$(this, v1, v2);
        }

        public Function1<Object, Function1<Object, Object>> curried() {
            return scala.Function2.curried$(this);
        }

        public Function1<Tuple2<Object, Object>, Object> tupled() {
            return scala.Function2.tupled$(this);
        }

        public String toString() {
            return scala.Function2.toString$(this);
        }

        public Object apply(Object l, Object r) {
            return l;
        }
        {
            scala.Function2.$init$(this);
        }
    };
    private static final Function2<Object, Object, Object> _right = new Function2<Object, Object, Object>(){

        public boolean apply$mcZDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcZDD$sp$(this, v1, v2);
        }

        public double apply$mcDDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcDDD$sp$(this, v1, v2);
        }

        public float apply$mcFDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcFDD$sp$(this, v1, v2);
        }

        public int apply$mcIDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcIDD$sp$(this, v1, v2);
        }

        public long apply$mcJDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcJDD$sp$(this, v1, v2);
        }

        public void apply$mcVDD$sp(double v1, double v2) {
            scala.Function2.apply$mcVDD$sp$(this, v1, v2);
        }

        public boolean apply$mcZDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcZDI$sp$(this, v1, v2);
        }

        public double apply$mcDDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcDDI$sp$(this, v1, v2);
        }

        public float apply$mcFDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcFDI$sp$(this, v1, v2);
        }

        public int apply$mcIDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcIDI$sp$(this, v1, v2);
        }

        public long apply$mcJDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcJDI$sp$(this, v1, v2);
        }

        public void apply$mcVDI$sp(double v1, int v2) {
            scala.Function2.apply$mcVDI$sp$(this, v1, v2);
        }

        public boolean apply$mcZDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcZDJ$sp$(this, v1, v2);
        }

        public double apply$mcDDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcDDJ$sp$(this, v1, v2);
        }

        public float apply$mcFDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcFDJ$sp$(this, v1, v2);
        }

        public int apply$mcIDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcIDJ$sp$(this, v1, v2);
        }

        public long apply$mcJDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcJDJ$sp$(this, v1, v2);
        }

        public void apply$mcVDJ$sp(double v1, long v2) {
            scala.Function2.apply$mcVDJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZID$sp(int v1, double v2) {
            return scala.Function2.apply$mcZID$sp$(this, v1, v2);
        }

        public double apply$mcDID$sp(int v1, double v2) {
            return scala.Function2.apply$mcDID$sp$(this, v1, v2);
        }

        public float apply$mcFID$sp(int v1, double v2) {
            return scala.Function2.apply$mcFID$sp$(this, v1, v2);
        }

        public int apply$mcIID$sp(int v1, double v2) {
            return scala.Function2.apply$mcIID$sp$(this, v1, v2);
        }

        public long apply$mcJID$sp(int v1, double v2) {
            return scala.Function2.apply$mcJID$sp$(this, v1, v2);
        }

        public void apply$mcVID$sp(int v1, double v2) {
            scala.Function2.apply$mcVID$sp$(this, v1, v2);
        }

        public boolean apply$mcZII$sp(int v1, int v2) {
            return scala.Function2.apply$mcZII$sp$(this, v1, v2);
        }

        public double apply$mcDII$sp(int v1, int v2) {
            return scala.Function2.apply$mcDII$sp$(this, v1, v2);
        }

        public float apply$mcFII$sp(int v1, int v2) {
            return scala.Function2.apply$mcFII$sp$(this, v1, v2);
        }

        public int apply$mcIII$sp(int v1, int v2) {
            return scala.Function2.apply$mcIII$sp$(this, v1, v2);
        }

        public long apply$mcJII$sp(int v1, int v2) {
            return scala.Function2.apply$mcJII$sp$(this, v1, v2);
        }

        public void apply$mcVII$sp(int v1, int v2) {
            scala.Function2.apply$mcVII$sp$(this, v1, v2);
        }

        public boolean apply$mcZIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcZIJ$sp$(this, v1, v2);
        }

        public double apply$mcDIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcDIJ$sp$(this, v1, v2);
        }

        public float apply$mcFIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcFIJ$sp$(this, v1, v2);
        }

        public int apply$mcIIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcIIJ$sp$(this, v1, v2);
        }

        public long apply$mcJIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcJIJ$sp$(this, v1, v2);
        }

        public void apply$mcVIJ$sp(int v1, long v2) {
            scala.Function2.apply$mcVIJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcZJD$sp$(this, v1, v2);
        }

        public double apply$mcDJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcDJD$sp$(this, v1, v2);
        }

        public float apply$mcFJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcFJD$sp$(this, v1, v2);
        }

        public int apply$mcIJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcIJD$sp$(this, v1, v2);
        }

        public long apply$mcJJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcJJD$sp$(this, v1, v2);
        }

        public void apply$mcVJD$sp(long v1, double v2) {
            scala.Function2.apply$mcVJD$sp$(this, v1, v2);
        }

        public boolean apply$mcZJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcZJI$sp$(this, v1, v2);
        }

        public double apply$mcDJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcDJI$sp$(this, v1, v2);
        }

        public float apply$mcFJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcFJI$sp$(this, v1, v2);
        }

        public int apply$mcIJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcIJI$sp$(this, v1, v2);
        }

        public long apply$mcJJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcJJI$sp$(this, v1, v2);
        }

        public void apply$mcVJI$sp(long v1, int v2) {
            scala.Function2.apply$mcVJI$sp$(this, v1, v2);
        }

        public boolean apply$mcZJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcZJJ$sp$(this, v1, v2);
        }

        public double apply$mcDJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcDJJ$sp$(this, v1, v2);
        }

        public float apply$mcFJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcFJJ$sp$(this, v1, v2);
        }

        public int apply$mcIJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcIJJ$sp$(this, v1, v2);
        }

        public long apply$mcJJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcJJJ$sp$(this, v1, v2);
        }

        public void apply$mcVJJ$sp(long v1, long v2) {
            scala.Function2.apply$mcVJJ$sp$(this, v1, v2);
        }

        public Function1<Object, Function1<Object, Object>> curried() {
            return scala.Function2.curried$(this);
        }

        public Function1<Tuple2<Object, Object>, Object> tupled() {
            return scala.Function2.tupled$(this);
        }

        public String toString() {
            return scala.Function2.toString$(this);
        }

        public Object apply(Object l, Object r) {
            return r;
        }
        {
            scala.Function2.$init$(this);
        }
    };
    private static final Function2<Object, Object, Object> _both = new Function2<Object, Object, Object>(){

        public boolean apply$mcZDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcZDD$sp$(this, v1, v2);
        }

        public double apply$mcDDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcDDD$sp$(this, v1, v2);
        }

        public float apply$mcFDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcFDD$sp$(this, v1, v2);
        }

        public int apply$mcIDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcIDD$sp$(this, v1, v2);
        }

        public long apply$mcJDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcJDD$sp$(this, v1, v2);
        }

        public void apply$mcVDD$sp(double v1, double v2) {
            scala.Function2.apply$mcVDD$sp$(this, v1, v2);
        }

        public boolean apply$mcZDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcZDI$sp$(this, v1, v2);
        }

        public double apply$mcDDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcDDI$sp$(this, v1, v2);
        }

        public float apply$mcFDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcFDI$sp$(this, v1, v2);
        }

        public int apply$mcIDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcIDI$sp$(this, v1, v2);
        }

        public long apply$mcJDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcJDI$sp$(this, v1, v2);
        }

        public void apply$mcVDI$sp(double v1, int v2) {
            scala.Function2.apply$mcVDI$sp$(this, v1, v2);
        }

        public boolean apply$mcZDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcZDJ$sp$(this, v1, v2);
        }

        public double apply$mcDDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcDDJ$sp$(this, v1, v2);
        }

        public float apply$mcFDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcFDJ$sp$(this, v1, v2);
        }

        public int apply$mcIDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcIDJ$sp$(this, v1, v2);
        }

        public long apply$mcJDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcJDJ$sp$(this, v1, v2);
        }

        public void apply$mcVDJ$sp(double v1, long v2) {
            scala.Function2.apply$mcVDJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZID$sp(int v1, double v2) {
            return scala.Function2.apply$mcZID$sp$(this, v1, v2);
        }

        public double apply$mcDID$sp(int v1, double v2) {
            return scala.Function2.apply$mcDID$sp$(this, v1, v2);
        }

        public float apply$mcFID$sp(int v1, double v2) {
            return scala.Function2.apply$mcFID$sp$(this, v1, v2);
        }

        public int apply$mcIID$sp(int v1, double v2) {
            return scala.Function2.apply$mcIID$sp$(this, v1, v2);
        }

        public long apply$mcJID$sp(int v1, double v2) {
            return scala.Function2.apply$mcJID$sp$(this, v1, v2);
        }

        public void apply$mcVID$sp(int v1, double v2) {
            scala.Function2.apply$mcVID$sp$(this, v1, v2);
        }

        public boolean apply$mcZII$sp(int v1, int v2) {
            return scala.Function2.apply$mcZII$sp$(this, v1, v2);
        }

        public double apply$mcDII$sp(int v1, int v2) {
            return scala.Function2.apply$mcDII$sp$(this, v1, v2);
        }

        public float apply$mcFII$sp(int v1, int v2) {
            return scala.Function2.apply$mcFII$sp$(this, v1, v2);
        }

        public int apply$mcIII$sp(int v1, int v2) {
            return scala.Function2.apply$mcIII$sp$(this, v1, v2);
        }

        public long apply$mcJII$sp(int v1, int v2) {
            return scala.Function2.apply$mcJII$sp$(this, v1, v2);
        }

        public void apply$mcVII$sp(int v1, int v2) {
            scala.Function2.apply$mcVII$sp$(this, v1, v2);
        }

        public boolean apply$mcZIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcZIJ$sp$(this, v1, v2);
        }

        public double apply$mcDIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcDIJ$sp$(this, v1, v2);
        }

        public float apply$mcFIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcFIJ$sp$(this, v1, v2);
        }

        public int apply$mcIIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcIIJ$sp$(this, v1, v2);
        }

        public long apply$mcJIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcJIJ$sp$(this, v1, v2);
        }

        public void apply$mcVIJ$sp(int v1, long v2) {
            scala.Function2.apply$mcVIJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcZJD$sp$(this, v1, v2);
        }

        public double apply$mcDJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcDJD$sp$(this, v1, v2);
        }

        public float apply$mcFJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcFJD$sp$(this, v1, v2);
        }

        public int apply$mcIJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcIJD$sp$(this, v1, v2);
        }

        public long apply$mcJJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcJJD$sp$(this, v1, v2);
        }

        public void apply$mcVJD$sp(long v1, double v2) {
            scala.Function2.apply$mcVJD$sp$(this, v1, v2);
        }

        public boolean apply$mcZJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcZJI$sp$(this, v1, v2);
        }

        public double apply$mcDJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcDJI$sp$(this, v1, v2);
        }

        public float apply$mcFJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcFJI$sp$(this, v1, v2);
        }

        public int apply$mcIJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcIJI$sp$(this, v1, v2);
        }

        public long apply$mcJJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcJJI$sp$(this, v1, v2);
        }

        public void apply$mcVJI$sp(long v1, int v2) {
            scala.Function2.apply$mcVJI$sp$(this, v1, v2);
        }

        public boolean apply$mcZJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcZJJ$sp$(this, v1, v2);
        }

        public double apply$mcDJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcDJJ$sp$(this, v1, v2);
        }

        public float apply$mcFJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcFJJ$sp$(this, v1, v2);
        }

        public int apply$mcIJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcIJJ$sp$(this, v1, v2);
        }

        public long apply$mcJJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcJJJ$sp$(this, v1, v2);
        }

        public void apply$mcVJJ$sp(long v1, long v2) {
            scala.Function2.apply$mcVJJ$sp$(this, v1, v2);
        }

        public Function1<Object, Function1<Object, Object>> curried() {
            return scala.Function2.curried$(this);
        }

        public Function1<Tuple2<Object, Object>, Object> tupled() {
            return scala.Function2.tupled$(this);
        }

        public String toString() {
            return scala.Function2.toString$(this);
        }

        public Pair<Object, Object> apply(Object l, Object r) {
            return new Pair<Object, Object>(l, r);
        }
        {
            scala.Function2.$init$(this);
        }
    };
    private static final Function2<Object, Object, NotUsed> _none = new Function2<Object, Object, NotUsed>(){

        public boolean apply$mcZDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcZDD$sp$(this, v1, v2);
        }

        public double apply$mcDDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcDDD$sp$(this, v1, v2);
        }

        public float apply$mcFDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcFDD$sp$(this, v1, v2);
        }

        public int apply$mcIDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcIDD$sp$(this, v1, v2);
        }

        public long apply$mcJDD$sp(double v1, double v2) {
            return scala.Function2.apply$mcJDD$sp$(this, v1, v2);
        }

        public void apply$mcVDD$sp(double v1, double v2) {
            scala.Function2.apply$mcVDD$sp$(this, v1, v2);
        }

        public boolean apply$mcZDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcZDI$sp$(this, v1, v2);
        }

        public double apply$mcDDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcDDI$sp$(this, v1, v2);
        }

        public float apply$mcFDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcFDI$sp$(this, v1, v2);
        }

        public int apply$mcIDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcIDI$sp$(this, v1, v2);
        }

        public long apply$mcJDI$sp(double v1, int v2) {
            return scala.Function2.apply$mcJDI$sp$(this, v1, v2);
        }

        public void apply$mcVDI$sp(double v1, int v2) {
            scala.Function2.apply$mcVDI$sp$(this, v1, v2);
        }

        public boolean apply$mcZDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcZDJ$sp$(this, v1, v2);
        }

        public double apply$mcDDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcDDJ$sp$(this, v1, v2);
        }

        public float apply$mcFDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcFDJ$sp$(this, v1, v2);
        }

        public int apply$mcIDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcIDJ$sp$(this, v1, v2);
        }

        public long apply$mcJDJ$sp(double v1, long v2) {
            return scala.Function2.apply$mcJDJ$sp$(this, v1, v2);
        }

        public void apply$mcVDJ$sp(double v1, long v2) {
            scala.Function2.apply$mcVDJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZID$sp(int v1, double v2) {
            return scala.Function2.apply$mcZID$sp$(this, v1, v2);
        }

        public double apply$mcDID$sp(int v1, double v2) {
            return scala.Function2.apply$mcDID$sp$(this, v1, v2);
        }

        public float apply$mcFID$sp(int v1, double v2) {
            return scala.Function2.apply$mcFID$sp$(this, v1, v2);
        }

        public int apply$mcIID$sp(int v1, double v2) {
            return scala.Function2.apply$mcIID$sp$(this, v1, v2);
        }

        public long apply$mcJID$sp(int v1, double v2) {
            return scala.Function2.apply$mcJID$sp$(this, v1, v2);
        }

        public void apply$mcVID$sp(int v1, double v2) {
            scala.Function2.apply$mcVID$sp$(this, v1, v2);
        }

        public boolean apply$mcZII$sp(int v1, int v2) {
            return scala.Function2.apply$mcZII$sp$(this, v1, v2);
        }

        public double apply$mcDII$sp(int v1, int v2) {
            return scala.Function2.apply$mcDII$sp$(this, v1, v2);
        }

        public float apply$mcFII$sp(int v1, int v2) {
            return scala.Function2.apply$mcFII$sp$(this, v1, v2);
        }

        public int apply$mcIII$sp(int v1, int v2) {
            return scala.Function2.apply$mcIII$sp$(this, v1, v2);
        }

        public long apply$mcJII$sp(int v1, int v2) {
            return scala.Function2.apply$mcJII$sp$(this, v1, v2);
        }

        public void apply$mcVII$sp(int v1, int v2) {
            scala.Function2.apply$mcVII$sp$(this, v1, v2);
        }

        public boolean apply$mcZIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcZIJ$sp$(this, v1, v2);
        }

        public double apply$mcDIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcDIJ$sp$(this, v1, v2);
        }

        public float apply$mcFIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcFIJ$sp$(this, v1, v2);
        }

        public int apply$mcIIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcIIJ$sp$(this, v1, v2);
        }

        public long apply$mcJIJ$sp(int v1, long v2) {
            return scala.Function2.apply$mcJIJ$sp$(this, v1, v2);
        }

        public void apply$mcVIJ$sp(int v1, long v2) {
            scala.Function2.apply$mcVIJ$sp$(this, v1, v2);
        }

        public boolean apply$mcZJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcZJD$sp$(this, v1, v2);
        }

        public double apply$mcDJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcDJD$sp$(this, v1, v2);
        }

        public float apply$mcFJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcFJD$sp$(this, v1, v2);
        }

        public int apply$mcIJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcIJD$sp$(this, v1, v2);
        }

        public long apply$mcJJD$sp(long v1, double v2) {
            return scala.Function2.apply$mcJJD$sp$(this, v1, v2);
        }

        public void apply$mcVJD$sp(long v1, double v2) {
            scala.Function2.apply$mcVJD$sp$(this, v1, v2);
        }

        public boolean apply$mcZJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcZJI$sp$(this, v1, v2);
        }

        public double apply$mcDJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcDJI$sp$(this, v1, v2);
        }

        public float apply$mcFJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcFJI$sp$(this, v1, v2);
        }

        public int apply$mcIJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcIJI$sp$(this, v1, v2);
        }

        public long apply$mcJJI$sp(long v1, int v2) {
            return scala.Function2.apply$mcJJI$sp$(this, v1, v2);
        }

        public void apply$mcVJI$sp(long v1, int v2) {
            scala.Function2.apply$mcVJI$sp$(this, v1, v2);
        }

        public boolean apply$mcZJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcZJJ$sp$(this, v1, v2);
        }

        public double apply$mcDJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcDJJ$sp$(this, v1, v2);
        }

        public float apply$mcFJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcFJJ$sp$(this, v1, v2);
        }

        public int apply$mcIJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcIJJ$sp$(this, v1, v2);
        }

        public long apply$mcJJJ$sp(long v1, long v2) {
            return scala.Function2.apply$mcJJJ$sp$(this, v1, v2);
        }

        public void apply$mcVJJ$sp(long v1, long v2) {
            scala.Function2.apply$mcVJJ$sp$(this, v1, v2);
        }

        public Function1<Object, Function1<Object, NotUsed>> curried() {
            return scala.Function2.curried$(this);
        }

        public Function1<Tuple2<Object, Object>, NotUsed> tupled() {
            return scala.Function2.tupled$(this);
        }

        public String toString() {
            return scala.Function2.toString$(this);
        }

        public NotUsed$ apply(Object l, Object r) {
            return NotUsed$.MODULE$;
        }
        {
            scala.Function2.$init$(this);
        }
    };

    private Function2<Object, Object, Object> _left() {
        return _left;
    }

    private Function2<Object, Object, Object> _right() {
        return _right;
    }

    private Function2<Object, Object, Object> _both() {
        return _both;
    }

    private Function2<Object, Object, NotUsed> _none() {
        return _none;
    }

    public <L, R> Function2<L, R, L> left() {
        return this._left();
    }

    public <L, R> Function2<L, R, R> right() {
        return this._right();
    }

    public <L, R> Function2<L, R, Pair<L, R>> both() {
        return this._both();
    }

    public <L, R> Function2<L, R, NotUsed> none() {
        return this._none();
    }

    private Keep$() {
    }
}

