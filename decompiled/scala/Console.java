/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import scala.Console$;
import scala.Function0;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055t!B\f\u0019\u0011\u0003Yb!B\u000f\u0019\u0011\u0003q\u0002\"\u0002\u0015\u0002\t\u0003I\u0003B\u0002\u0016\u0002A\u0003%1\u0006\u0003\u00049\u0003\u0001\u0006Ia\u000b\u0005\u0007s\u0005\u0001\u000b\u0011\u0002\u001e\t\u000by\nA\u0011C \t\u000b\u0015\u000bA\u0011\u0003$\t\u000b%\u000bA\u0011\u0003&\t\u000b\u0011\u000bA\u0011A'\t\u000b!\u000bA\u0011A'\t\u000b1\u000bA\u0011\u0001(\t\u000b=\u000bA\u0011\u0001)\t\u000b=\u000bA\u0011\u00013\t\u000b=\fA\u0011\u00019\t\u000b=\fA\u0011\u0001=\t\u000f\u0005\u0005\u0011\u0001\"\u0001\u0002\u0004!9\u0011\u0011A\u0001\u0005\u0002\u0005m\u0001bBA\u0019\u0003\u0011\u0005\u00111\u0007\u0005\b\u0003s\tA\u0011AA\u001e\u0011\u001d\ti$\u0001C\u0001\u0003wAq!!\u0010\u0002\t\u0003\ty\u0004C\u0004\u0002F\u0005!\t!a\u0012\u0002\u000f\r{gn]8mK*\t\u0011$A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005q\tQ\"\u0001\r\u0003\u000f\r{gn]8mKN\u0019\u0011a\b\u0012\u0011\u0005q\u0001\u0013BA\u0011\u0019\u0005\u0019\te.\u001f*fMB\u00111EJ\u0007\u0002I)\u0011Q\u0005G\u0001\u0003S>L!a\n\u0013\u0003\u0013\u0005s7/[\"pY>\u0014\u0018A\u0002\u001fj]&$h\bF\u0001\u001c\u0003\u0019yW\u000f\u001e,beB\u0019AfL\u0019\u000e\u00035R!A\f\r\u0002\tU$\u0018\u000e\\\u0005\u0003a5\u0012q\u0002R=oC6L7MV1sS\u0006\u0014G.\u001a\t\u0003eYj\u0011a\r\u0006\u0003KQR\u0011!N\u0001\u0005U\u00064\u0018-\u0003\u00028g\tY\u0001K]5oiN#(/Z1n\u0003\u0019)'O\u001d,be\u0006)\u0011N\u001c,beB\u0019AfL\u001e\u0011\u0005Ib\u0014BA\u001f4\u00059\u0011UO\u001a4fe\u0016$'+Z1eKJ\fAb]3u\u001fV$H)\u001b:fGR$\"\u0001Q\"\u0011\u0005q\t\u0015B\u0001\"\u0019\u0005\u0011)f.\u001b;\t\u000b\u00113\u0001\u0019A\u0019\u0002\u0007=,H/\u0001\u0007tKR,%O\u001d#je\u0016\u001cG\u000f\u0006\u0002A\u000f\")\u0001j\u0002a\u0001c\u0005\u0019QM\u001d:\u0002\u0017M,G/\u00138ESJ,7\r\u001e\u000b\u0003\u0001.CQ\u0001\u0014\u0005A\u0002m\n!!\u001b8\u0016\u0003E*\u0012aO\u0001\bo&$\bnT;u+\t\tV\u000b\u0006\u0002SGR\u00111K\u0018\t\u0003)Vc\u0001\u0001B\u0003W\u0019\t\u0007qKA\u0001U#\tA6\f\u0005\u0002\u001d3&\u0011!\f\u0007\u0002\b\u001d>$\b.\u001b8h!\taB,\u0003\u0002^1\t\u0019\u0011I\\=\t\r}cA\u00111\u0001a\u0003\u0015!\b.\u001e8l!\ra\u0012mU\u0005\u0003Eb\u0011\u0001\u0002\u00102z]\u0006lWM\u0010\u0005\u0006\t2\u0001\r!M\u000b\u0003K\"$\"AZ6\u0015\u0005\u001dL\u0007C\u0001+i\t\u00151VB1\u0001X\u0011\u0019yV\u0002\"a\u0001UB\u0019A$Y4\t\u000b\u0011k\u0001\u0019\u00017\u0011\u0005Ij\u0017B\u000184\u00051yU\u000f\u001e9viN#(/Z1n\u0003\u001d9\u0018\u000e\u001e5FeJ,\"!\u001d;\u0015\u0005I<HCA:v!\t!F\u000fB\u0003W\u001d\t\u0007q\u000b\u0003\u0004`\u001d\u0011\u0005\rA\u001e\t\u00049\u0005\u001c\b\"\u0002%\u000f\u0001\u0004\tTCA=})\tQx\u0010\u0006\u0002|{B\u0011A\u000b \u0003\u0006->\u0011\ra\u0016\u0005\u0007?>!\t\u0019\u0001@\u0011\u0007q\t7\u0010C\u0003I\u001f\u0001\u0007A.\u0001\u0004xSRD\u0017J\\\u000b\u0005\u0003\u000b\tY\u0001\u0006\u0003\u0002\b\u0005EA\u0003BA\u0005\u0003\u001b\u00012\u0001VA\u0006\t\u00151\u0006C1\u0001X\u0011\u001dy\u0006\u0003\"a\u0001\u0003\u001f\u0001B\u0001H1\u0002\n!9\u00111\u0003\tA\u0002\u0005U\u0011A\u0002:fC\u0012,'\u000fE\u00023\u0003/I1!!\u00074\u0005\u0019\u0011V-\u00193feV!\u0011QDA\u0012)\u0011\ty\"!\u000b\u0015\t\u0005\u0005\u0012Q\u0005\t\u0004)\u0006\rB!\u0002,\u0012\u0005\u00049\u0006bB0\u0012\t\u0003\u0007\u0011q\u0005\t\u00059\u0005\f\t\u0003\u0003\u0004M#\u0001\u0007\u00111\u0006\t\u0004e\u00055\u0012bAA\u0018g\tY\u0011J\u001c9viN#(/Z1n\u0003\u0015\u0001(/\u001b8u)\r\u0001\u0015Q\u0007\u0005\u0007\u0003o\u0011\u0002\u0019A.\u0002\u0007=\u0014'.A\u0003gYV\u001c\b\u000eF\u0001A\u0003\u001d\u0001(/\u001b8uY:$2\u0001QA!\u0011\u0019\t\u0019%\u0006a\u00017\u0006\t\u00010\u0001\u0004qe&tGO\u001a\u000b\u0006\u0001\u0006%\u00131\r\u0005\b\u0003\u00172\u0002\u0019AA'\u0003\u0011!X\r\u001f;\u0011\t\u0005=\u0013Q\f\b\u0005\u0003#\nI\u0006E\u0002\u0002Tai!!!\u0016\u000b\u0007\u0005]#$\u0001\u0004=e>|GOP\u0005\u0004\u00037B\u0012A\u0002)sK\u0012,g-\u0003\u0003\u0002`\u0005\u0005$AB*ue&twMC\u0002\u0002\\aAq!!\u001a\u0017\u0001\u0004\t9'\u0001\u0003be\u001e\u001c\b\u0003\u0002\u000f\u0002jmK1!a\u001b\u0019\u0005)a$/\u001a9fCR,GM\u0010")
public final class Console {
    public static void printf(String text, Seq<Object> args2) {
        Console$.MODULE$.printf(text, args2);
    }

    public static void println(Object x) {
        Console$.MODULE$.println(x);
    }

    public static void println() {
        Console$.MODULE$.println();
    }

    public static void flush() {
        Console$.MODULE$.flush();
    }

    public static void print(Object obj) {
        Console$.MODULE$.print(obj);
    }

    public static <T> T withIn(InputStream in, Function0<T> thunk) {
        return Console$.MODULE$.withIn(in, thunk);
    }

    public static <T> T withIn(Reader reader, Function0<T> thunk) {
        return Console$.MODULE$.withIn(reader, thunk);
    }

    public static <T> T withErr(OutputStream err, Function0<T> thunk) {
        return Console$.MODULE$.withErr(err, thunk);
    }

    public static <T> T withErr(PrintStream err, Function0<T> thunk) {
        return Console$.MODULE$.withErr(err, thunk);
    }

    public static <T> T withOut(OutputStream out, Function0<T> thunk) {
        return Console$.MODULE$.withOut(out, thunk);
    }

    public static <T> T withOut(PrintStream out, Function0<T> thunk) {
        return Console$.MODULE$.withOut(out, thunk);
    }

    public static BufferedReader in() {
        return Console$.MODULE$.in();
    }

    public static PrintStream err() {
        return Console$.MODULE$.err();
    }

    public static PrintStream out() {
        return Console$.MODULE$.out();
    }

    public static String INVISIBLE() {
        return Console$.MODULE$.INVISIBLE();
    }

    public static String REVERSED() {
        return Console$.MODULE$.REVERSED();
    }

    public static String BLINK() {
        return Console$.MODULE$.BLINK();
    }

    public static String UNDERLINED() {
        return Console$.MODULE$.UNDERLINED();
    }

    public static String BOLD() {
        return Console$.MODULE$.BOLD();
    }

    public static String RESET() {
        return Console$.MODULE$.RESET();
    }

    public static String WHITE_B() {
        return Console$.MODULE$.WHITE_B();
    }

    public static String CYAN_B() {
        return Console$.MODULE$.CYAN_B();
    }

    public static String MAGENTA_B() {
        return Console$.MODULE$.MAGENTA_B();
    }

    public static String BLUE_B() {
        return Console$.MODULE$.BLUE_B();
    }

    public static String YELLOW_B() {
        return Console$.MODULE$.YELLOW_B();
    }

    public static String GREEN_B() {
        return Console$.MODULE$.GREEN_B();
    }

    public static String RED_B() {
        return Console$.MODULE$.RED_B();
    }

    public static String BLACK_B() {
        return Console$.MODULE$.BLACK_B();
    }

    public static String WHITE() {
        return Console$.MODULE$.WHITE();
    }

    public static String CYAN() {
        return Console$.MODULE$.CYAN();
    }

    public static String MAGENTA() {
        return Console$.MODULE$.MAGENTA();
    }

    public static String BLUE() {
        return Console$.MODULE$.BLUE();
    }

    public static String YELLOW() {
        return Console$.MODULE$.YELLOW();
    }

    public static String GREEN() {
        return Console$.MODULE$.GREEN();
    }

    public static String RED() {
        return Console$.MODULE$.RED();
    }

    public static String BLACK() {
        return Console$.MODULE$.BLACK();
    }
}

