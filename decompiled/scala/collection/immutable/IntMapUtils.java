/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMapUtils$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q;aAB\u0004\t\u0002\u001diaAB\b\b\u0011\u00039\u0001\u0003C\u0003 \u0003\u0011\u0005\u0011\u0005C\u0003#\u0003\u0011\u00051\u0005C\u0003.\u0003\u0011\u0005a\u0006C\u0003G\u0003\u0011\u0005q)A\u0006J]Rl\u0015\r]+uS2\u001c(B\u0001\u0005\n\u0003%IW.\\;uC\ndWM\u0003\u0002\u000b\u0017\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u00031\tQa]2bY\u0006\u0004\"AD\u0001\u000e\u0003\u001d\u00111\"\u00138u\u001b\u0006\u0004X\u000b^5mgN\u0019\u0011!E\u000b\u0011\u0005I\u0019R\"A\u0006\n\u0005QY!AB!osJ+g\r\u0005\u0002\u001799\u0011qCG\u0007\u00021)\u0011\u0011$C\u0001\bO\u0016tWM]5d\u0013\tY\u0002$A\u0007CSR|\u0005/\u001a:bi&|gn]\u0005\u0003;y\u00111!\u00138u\u0015\tY\u0002$\u0001\u0004=S:LGOP\u0002\u0001)\u0005i\u0011A\u00032sC:\u001c\u0007.T1tWR\u0019AEJ\u0016\u0011\u0005I)\u0013BA\u000f\f\u0011\u001593\u00011\u0001)\u0003\u0005I\u0007CA\u0015+\u001b\u0005\t\u0011BA\u000f\u001d\u0011\u0015a3\u00011\u0001)\u0003\u0005Q\u0017\u0001\u00026pS:,\"aL\u001b\u0015\u000bAr\u0004I\u0011#\u0011\u00079\t4'\u0003\u00023\u000f\t1\u0011J\u001c;NCB\u0004\"\u0001N\u001b\r\u0001\u0011)a\u0007\u0002b\u0001o\t\tA+\u0005\u00029wA\u0011!#O\u0005\u0003u-\u0011qAT8uQ&tw\r\u0005\u0002\u0013y%\u0011Qh\u0003\u0002\u0004\u0003:L\b\"B \u0005\u0001\u0004A\u0013A\u000192\u0011\u0015\tE\u00011\u00011\u0003\t!\u0018\u0007C\u0003D\t\u0001\u0007\u0001&\u0001\u0002qe!)Q\t\u0002a\u0001a\u0005\u0011AOM\u0001\u0004E&tWC\u0001%L)\u0015IEJ\u0014)S!\rq\u0011G\u0013\t\u0003i-#QAN\u0003C\u0002]BQ!T\u0003A\u0002!\na\u0001\u001d:fM&D\b\"B(\u0006\u0001\u0004A\u0013\u0001B7bg.DQ!U\u0003A\u0002%\u000bA\u0001\\3gi\")1+\u0002a\u0001\u0013\u0006)!/[4ii\u0002")
public final class IntMapUtils {
    public static <T> IntMap<T> bin(int prefix, int mask, IntMap<T> left, IntMap<T> right) {
        return IntMapUtils$.MODULE$.bin(prefix, mask, left, right);
    }

    public static <T> IntMap<T> join(int p1, IntMap<T> t1, int p2, IntMap<T> t2) {
        return IntMapUtils$.MODULE$.join(p1, t1, p2, t2);
    }

    public static int branchMask(int i, int j) {
        return IntMapUtils$.MODULE$.branchMask(i, j);
    }

    public static int highestOneBit(int j) {
        return IntMapUtils$.MODULE$.highestOneBit(j);
    }

    public static String bitString$default$2() {
        return IntMapUtils$.MODULE$.bitString$default$2();
    }

    public static String bitString(int num, String sep) {
        return IntMapUtils$.MODULE$.bitString(num, sep);
    }

    public static IndexedSeq<Object> bits(int num) {
        return IntMapUtils$.MODULE$.bits(num);
    }

    public static int complement(int i) {
        return IntMapUtils$.MODULE$.complement(i);
    }

    public static boolean shorter(int m1, int m22) {
        return IntMapUtils$.MODULE$.shorter(m1, m22);
    }

    public static boolean unsignedCompare(int i, int j) {
        return IntMapUtils$.MODULE$.unsignedCompare(i, j);
    }

    public static boolean hasMatch(int key, int prefix, int m4) {
        return IntMapUtils$.MODULE$.hasMatch(key, prefix, m4);
    }

    public static int mask(int i, int mask) {
        return IntMapUtils$.MODULE$.mask(i, mask);
    }

    public static boolean zero(int i, int mask) {
        return IntMapUtils$.MODULE$.zero(i, mask);
    }
}

