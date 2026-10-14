/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.math.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u001dx!B*U\u0011\u0003If!B.U\u0011\u0003a\u0006\"B1\u0002\t\u0003\u0011\u0007bB2\u0002\u0005\u0004%)\u0001\u001a\u0005\u0007O\u0006\u0001\u000bQB3\t\u000f1\f!\u0019!C\u0003[\"1\u0001/\u0001Q\u0001\u000e9DQA]\u0001\u0005\u0002MDQa^\u0001\u0005\u0002aDQa_\u0001\u0005\u0002qDQA`\u0001\u0005\u0002}Dq!a\u0001\u0002\t\u0003\t)\u0001C\u0004\u0002\n\u0005!\t!a\u0003\t\u000f\u0005=\u0011\u0001\"\u0001\u0002\u0012!9\u0011QC\u0001\u0005\u0002\u0005]\u0001bBA\u000e\u0003\u0011\u0005\u0011Q\u0004\u0005\b\u0003C\tA\u0011AA\u0012\u0011\u001d\tY#\u0001C\u0001\u0003[Aq!a\r\u0002\t\u0003\t)\u0004C\u0004\u0002:\u0005!\t!a\u000f\t\u000f\u0005}\u0012\u0001\"\u0001\u0002B!9\u0011QI\u0001\u0005\u0002\u0005\u001d\u0003bBA#\u0003\u0011\u0005\u0011Q\r\u0005\b\u0003\u000b\nA\u0011AA;\u0011\u001d\tI(\u0001C\u0001\u0003wBq!!\u001f\u0002\t\u0003\ty\bC\u0004\u0002z\u0005!\t!a!\t\u000f\u0005e\u0014\u0001\"\u0001\u0002\b\"9\u00111R\u0001\u0005\u0002\u00055\u0005bBAF\u0003\u0011\u0005\u00111\u0013\u0005\b\u0003\u0017\u000bA\u0011AAM\u0011\u001d\tY)\u0001C\u0001\u0003?Cq!!*\u0002\t\u0003\t9\u000bC\u0004\u0002&\u0006!\t!!,\t\u000f\u0005\u0015\u0016\u0001\"\u0001\u00024\"9\u0011QU\u0001\u0005\u0002\u0005e\u0006bBA`\u0003\u0011\u0005\u0011\u0011\u0019\u0005\b\u0003\u007f\u000bA\u0011AAc\u0011\u001d\ty,\u0001C\u0001\u0003\u0013Dq!a0\u0002\t\u0003\ti\rC\u0004\u0002R\u0006!\t!a5\t\u000f\u0005E\u0017\u0001\"\u0001\u0002Z\"9\u0011q\\\u0001\u0005\u0002\u0005\u0005\bbBAp\u0003\u0011\u0005\u0011q\u001d\u0005\b\u0003[\fA\u0011AAx\u0011\u001d\ti/\u0001C\u0001\u0003sDq!a@\u0002\t\u0003\u0011\t\u0001C\u0004\u0002\u0000\u0006!\tAa\u0003\t\u000f\tE\u0011\u0001\"\u0001\u0003\u0014!9!\u0011C\u0001\u0005\u0002\te\u0001b\u0002B\u0010\u0003\u0011\u0005!\u0011\u0005\u0005\b\u0005?\tA\u0011\u0001B\u0013\u0011\u001d\u0011I#\u0001C\u0001\u0005WAqA!\u000b\u0002\t\u0003\u0011\u0019\u0004C\u0004\u0003:\u0005!\tAa\u000f\t\u000f\t}\u0012\u0001\"\u0001\u0003B!9!QI\u0001\u0005\u0002\t\u001d\u0003b\u0002B'\u0003\u0011\u0005!q\n\u0005\b\u0005'\nA\u0011\u0001B+\u0011\u001d\u0011I&\u0001C\u0001\u00057BqA!\u0017\u0002\t\u0003\u0011y\u0006C\u0004\u0003d\u0005!\tA!\u001a\t\u000f\t%\u0014\u0001\"\u0001\u0003l!9!qN\u0001\u0005\u0002\tE\u0004b\u0002B;\u0003\u0011\u0005!q\u000f\u0005\b\u0005w\nA\u0011\u0001B?\u0011\u001d\u0011\t)\u0001C\u0001\u0005\u0007CqAa\"\u0002\t\u0003\u0011I\tC\u0004\u0003\b\u0006!\tA!$\t\u000f\tE\u0015\u0001\"\u0001\u0003\u0014\"9!\u0011T\u0001\u0005\u0002\tm\u0005b\u0002BM\u0003\u0011\u0005!\u0011\u0015\u0005\b\u0005O\u000bA\u0011\u0001BU\u0011\u001d\u00119+\u0001C\u0001\u0005_CqA!.\u0002\t\u0003\u00119\fC\u0004\u00036\u0006!\tA!0\t\u000f\t\r\u0017\u0001\"\u0001\u0003F\"9!1Y\u0001\u0005\u0002\t%\u0007b\u0002Bg\u0003\u0011\u0005!q\u001a\u0005\b\u0005\u001b\fA\u0011\u0001Bj\u0011\u001d\u00119.\u0001C\u0001\u00053DqAa6\u0002\t\u0003\u0011i\u000eC\u0004\u0003b\u0006!\tAa9\u0002\u000fA\f7m[1hK*\u0011QKV\u0001\u0005[\u0006$\bNC\u0001X\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"AW\u0001\u000e\u0003Q\u0013q\u0001]1dW\u0006<Wm\u0005\u0002\u0002;B\u0011alX\u0007\u0002-&\u0011\u0001M\u0016\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005I\u0016!A#\u0016\u0003\u0015|\u0011A\u001a\u0011\t\u0001\u0016y,b#\u000bXS\u0006\u0011Q\t\t\u0015\u0003\t%\u0004\"A\u00186\n\u0005-4&AB5oY&tW-\u0001\u0002QSV\tanD\u0001pA!\u0001\u0015\"I~U\t6B\u0012a\u0001)jA!\u0012a![\u0001\u0007e\u0006tGm\\7\u0015\u0003Q\u0004\"AX;\n\u0005Y4&A\u0002#pk\ndW-A\u0002tS:$\"\u0001^=\t\u000biD\u0001\u0019\u0001;\u0002\u0003a\f1aY8t)\t!X\u0010C\u0003{\u0013\u0001\u0007A/A\u0002uC:$2\u0001^A\u0001\u0011\u0015Q(\u00021\u0001u\u0003\u0011\t7/\u001b8\u0015\u0007Q\f9\u0001C\u0003{\u0017\u0001\u0007A/\u0001\u0003bG>\u001cHc\u0001;\u0002\u000e!)!\u0010\u0004a\u0001i\u0006!\u0011\r^1o)\r!\u00181\u0003\u0005\u0006u6\u0001\r\u0001^\u0001\ni>\u0014\u0016\rZ5b]N$2\u0001^A\r\u0011\u0015Qh\u00021\u0001u\u0003%!x\u000eR3he\u0016,7\u000fF\u0002u\u0003?AQA_\bA\u0002Q\fQ!\u0019;b]J\"R\u0001^A\u0013\u0003SAa!a\n\u0011\u0001\u0004!\u0018!A=\t\u000bi\u0004\u0002\u0019\u0001;\u0002\u000b!L\bo\u001c;\u0015\u000bQ\fy#!\r\t\u000bi\f\u0002\u0019\u0001;\t\r\u0005\u001d\u0012\u00031\u0001u\u0003\u0011\u0019W-\u001b7\u0015\u0007Q\f9\u0004C\u0003{%\u0001\u0007A/A\u0003gY>|'\u000fF\u0002u\u0003{AQA_\nA\u0002Q\fAA]5oiR\u0019A/a\u0011\t\u000bi$\u0002\u0019\u0001;\u0002\u000bI|WO\u001c3\u0015\t\u0005%\u0013q\n\t\u0004=\u0006-\u0013bAA'-\n!Aj\u001c8h\u0011\u0019QX\u00031\u0001\u0002J!ZQ#a\u0015\u0002Z\u0005m\u0013qLA1!\rq\u0016QK\u0005\u0004\u0003/2&A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAA/\u0003Q$\u0006.[:!SN\u0004\u0013M\u001c\u0011j]R,w-\u001a:!if\u0004Xm\u000f\u0011uQ\u0016\u0014X\rI5tA9|\u0007E]3bg>t\u0007\u0005^8!e>,h\u000e\u001a\u0011ji:\u0002\u0003+\u001a:iCB\u001c\b%_8vA5,\u0017M\u001c;!i>\u00043-\u00197mAQD\u0017n\u001d\u0011xSRD\u0007%\u0019\u0011gY>\fG/\u001b8h[A|\u0017N\u001c;!m\u0006dW/Z \u0002\u000bMLgnY3\"\u0005\u0005\r\u0014A\u0002\u001a/cEr\u0003\u0007\u0006\u0003\u0002h\u00055\u0004c\u00010\u0002j%\u0019\u00111\u000e,\u0003\u0007%sG\u000f\u0003\u0004{-\u0001\u0007\u0011q\u000e\t\u0004=\u0006E\u0014bAA:-\n)a\t\\8biR!\u0011\u0011JA<\u0011\u0015Qx\u00031\u0001u\u0003\r\t'm\u001d\u000b\u0005\u0003O\ni\b\u0003\u0004{1\u0001\u0007\u0011q\r\u000b\u0005\u0003\u0013\n\t\t\u0003\u0004{3\u0001\u0007\u0011\u0011\n\u000b\u0005\u0003_\n)\t\u0003\u0004{5\u0001\u0007\u0011q\u000e\u000b\u0004i\u0006%\u0005\"\u0002>\u001c\u0001\u0004!\u0018aA7bqR1\u0011qMAH\u0003#CaA\u001f\u000fA\u0002\u0005\u001d\u0004bBA\u00149\u0001\u0007\u0011q\r\u000b\u0007\u0003\u0013\n)*a&\t\ril\u0002\u0019AA%\u0011\u001d\t9#\ba\u0001\u0003\u0013\"b!a\u001c\u0002\u001c\u0006u\u0005B\u0002>\u001f\u0001\u0004\ty\u0007C\u0004\u0002(y\u0001\r!a\u001c\u0015\u000bQ\f\t+a)\t\u000bi|\u0002\u0019\u0001;\t\r\u0005\u001dr\u00041\u0001u\u0003\ri\u0017N\u001c\u000b\u0007\u0003O\nI+a+\t\ri\u0004\u0003\u0019AA4\u0011\u001d\t9\u0003\ta\u0001\u0003O\"b!!\u0013\u00020\u0006E\u0006B\u0002>\"\u0001\u0004\tI\u0005C\u0004\u0002(\u0005\u0002\r!!\u0013\u0015\r\u0005=\u0014QWA\\\u0011\u0019Q(\u00051\u0001\u0002p!9\u0011q\u0005\u0012A\u0002\u0005=D#\u0002;\u0002<\u0006u\u0006\"\u0002>$\u0001\u0004!\bBBA\u0014G\u0001\u0007A/\u0001\u0004tS\u001etW/\u001c\u000b\u0005\u0003O\n\u0019\r\u0003\u0004{I\u0001\u0007\u0011q\r\u000b\u0005\u0003\u0013\n9\r\u0003\u0004{K\u0001\u0007\u0011\u0011\n\u000b\u0005\u0003_\nY\r\u0003\u0004{M\u0001\u0007\u0011q\u000e\u000b\u0004i\u0006=\u0007\"\u0002>(\u0001\u0004!\u0018\u0001\u00034m_>\u0014H)\u001b<\u0015\r\u0005\u001d\u0014Q[Al\u0011\u0019Q\b\u00061\u0001\u0002h!9\u0011q\u0005\u0015A\u0002\u0005\u001dDCBA%\u00037\fi\u000e\u0003\u0004{S\u0001\u0007\u0011\u0011\n\u0005\b\u0003OI\u0003\u0019AA%\u0003!1Gn\\8s\u001b>$GCBA4\u0003G\f)\u000f\u0003\u0004{U\u0001\u0007\u0011q\r\u0005\b\u0003OQ\u0003\u0019AA4)\u0019\tI%!;\u0002l\"1!p\u000ba\u0001\u0003\u0013Bq!a\n,\u0001\u0004\tI%\u0001\u0005d_BL8+[4o)\u0015!\u0018\u0011_A{\u0011\u0019\t\u0019\u0010\fa\u0001i\u0006IQ.Y4oSR,H-\u001a\u0005\u0007\u0003od\u0003\u0019\u0001;\u0002\tMLwM\u001c\u000b\u0007\u0003_\nY0!@\t\u000f\u0005MX\u00061\u0001\u0002p!9\u0011q_\u0017A\u0002\u0005=\u0014!\u00038fqR\fe\r^3s)\u0015!(1\u0001B\u0004\u0011\u0019\u0011)A\fa\u0001i\u0006)1\u000f^1si\"1!\u0011\u0002\u0018A\u0002Q\f\u0011\u0002Z5sK\u000e$\u0018n\u001c8\u0015\r\u0005=$Q\u0002B\b\u0011\u001d\u0011)a\fa\u0001\u0003_BaA!\u00030\u0001\u0004!\u0018A\u00028fqR,\u0006\u000fF\u0002u\u0005+AaAa\u00061\u0001\u0004!\u0018!\u00013\u0015\t\u0005=$1\u0004\u0005\b\u0005;\t\u0004\u0019AA8\u0003\u00051\u0017\u0001\u00038fqR$un\u001e8\u0015\u0007Q\u0014\u0019\u0003\u0003\u0004\u0003\u0018I\u0002\r\u0001\u001e\u000b\u0005\u0003_\u00129\u0003C\u0004\u0003\u001eM\u0002\r!a\u001c\u0002\u000bM\u001c\u0017\r\u001c2\u0015\u000bQ\u0014iCa\f\t\r\t]A\u00071\u0001u\u0011\u001d\u0011\t\u0004\u000ea\u0001\u0003O\n1b]2bY\u00164\u0015m\u0019;peR1\u0011q\u000eB\u001b\u0005oAqA!\b6\u0001\u0004\ty\u0007C\u0004\u00032U\u0002\r!a\u001a\u0002\tM\f(\u000f\u001e\u000b\u0004i\nu\u0002\"\u0002>7\u0001\u0004!\u0018\u0001B2ceR$2\u0001\u001eB\"\u0011\u0015Qx\u00071\u0001u\u0003\r\u0001xn\u001e\u000b\u0006i\n%#1\n\u0005\u0006ub\u0002\r\u0001\u001e\u0005\u0007\u0003OA\u0004\u0019\u0001;\u0002\u0007\u0015D\b\u000fF\u0002u\u0005#BQA_\u001dA\u0002Q\fQ!\u001a=q[F\"2\u0001\u001eB,\u0011\u0015Q(\b1\u0001u\u0003-9W\r^#ya>tWM\u001c;\u0015\t\u0005\u001d$Q\f\u0005\b\u0005;Y\u0004\u0019AA8)\u0011\t9G!\u0019\t\r\t]A\b1\u0001u\u0003\rawn\u001a\u000b\u0004i\n\u001d\u0004\"\u0002>>\u0001\u0004!\u0018!\u00027pOF\u0002Hc\u0001;\u0003n!)!P\u0010a\u0001i\u0006)An\\42aQ\u0019AOa\u001d\t\u000bi|\u0004\u0019\u0001;\u0002\tMLg\u000e\u001b\u000b\u0004i\ne\u0004\"\u0002>A\u0001\u0004!\u0018\u0001B2pg\"$2\u0001\u001eB@\u0011\u0015Q\u0018\t1\u0001u\u0003\u0011!\u0018M\u001c5\u0015\u0007Q\u0014)\tC\u0003{\u0005\u0002\u0007A/A\u0002vYB$2\u0001\u001eBF\u0011\u0015Q8\t1\u0001u)\u0011\tyGa$\t\ri$\u0005\u0019AA8\u00035IU)R#sK6\f\u0017N\u001c3feR)AO!&\u0003\u0018\")!0\u0012a\u0001i\"1\u0011qE#A\u0002Q\f\u0001\"\u00193e\u000bb\f7\r\u001e\u000b\u0007\u0003O\u0012iJa(\t\ri4\u0005\u0019AA4\u0011\u001d\t9C\u0012a\u0001\u0003O\"b!!\u0013\u0003$\n\u0015\u0006B\u0002>H\u0001\u0004\tI\u0005C\u0004\u0002(\u001d\u0003\r!!\u0013\u0002\u001bM,(\r\u001e:bGR,\u00050Y2u)\u0019\t9Ga+\u0003.\"1!\u0010\u0013a\u0001\u0003OBq!a\nI\u0001\u0004\t9\u0007\u0006\u0004\u0002J\tE&1\u0017\u0005\u0007u&\u0003\r!!\u0013\t\u000f\u0005\u001d\u0012\n1\u0001\u0002J\u0005iQ.\u001e7uSBd\u00170\u0012=bGR$b!a\u001a\u0003:\nm\u0006B\u0002>K\u0001\u0004\t9\u0007C\u0004\u0002()\u0003\r!a\u001a\u0015\r\u0005%#q\u0018Ba\u0011\u0019Q8\n1\u0001\u0002J!9\u0011qE&A\u0002\u0005%\u0013AD5oGJ,W.\u001a8u\u000bb\f7\r\u001e\u000b\u0005\u0003O\u00129\r\u0003\u0004{\u0019\u0002\u0007\u0011q\r\u000b\u0005\u0003\u0013\u0012Y\r\u0003\u0004{\u001b\u0002\u0007\u0011\u0011J\u0001\u000fI\u0016\u001c'/Z7f]R,\u00050Y2u)\u0011\t9G!5\t\rit\u0005\u0019AA4)\u0011\tIE!6\t\ri|\u0005\u0019AA%\u0003-qWmZ1uK\u0016C\u0018m\u0019;\u0015\t\u0005\u001d$1\u001c\u0005\u0007uB\u0003\r!a\u001a\u0015\t\u0005%#q\u001c\u0005\u0007uF\u0003\r!!\u0013\u0002\u0015Q|\u0017J\u001c;Fq\u0006\u001cG\u000f\u0006\u0003\u0002h\t\u0015\bB\u0002>S\u0001\u0004\tI\u0005")
public final class package {
    public static int toIntExact(long x) {
        return package$.MODULE$.toIntExact(x);
    }

    public static long negateExact(long x) {
        return package$.MODULE$.negateExact(x);
    }

    public static int negateExact(int x) {
        return package$.MODULE$.negateExact(x);
    }

    public static long decrementExact(long x) {
        return package$.MODULE$.decrementExact(x);
    }

    public static int decrementExact(int x) {
        return package$.MODULE$.decrementExact(x);
    }

    public static long incrementExact(long x) {
        return package$.MODULE$.incrementExact(x);
    }

    public static int incrementExact(int x) {
        return package$.MODULE$.incrementExact(x);
    }

    public static long multiplyExact(long x, long y) {
        return package$.MODULE$.multiplyExact(x, y);
    }

    public static int multiplyExact(int x, int y) {
        return package$.MODULE$.multiplyExact(x, y);
    }

    public static long subtractExact(long x, long y) {
        return package$.MODULE$.subtractExact(x, y);
    }

    public static int subtractExact(int x, int y) {
        return package$.MODULE$.subtractExact(x, y);
    }

    public static long addExact(long x, long y) {
        return package$.MODULE$.addExact(x, y);
    }

    public static int addExact(int x, int y) {
        return package$.MODULE$.addExact(x, y);
    }

    public static double IEEEremainder(double x, double y) {
        return package$.MODULE$.IEEEremainder(x, y);
    }

    public static float ulp(float x) {
        return package$.MODULE$.ulp(x);
    }

    public static double ulp(double x) {
        return package$.MODULE$.ulp(x);
    }

    public static double tanh(double x) {
        return package$.MODULE$.tanh(x);
    }

    public static double cosh(double x) {
        return package$.MODULE$.cosh(x);
    }

    public static double sinh(double x) {
        return package$.MODULE$.sinh(x);
    }

    public static double log10(double x) {
        return package$.MODULE$.log10(x);
    }

    public static double log1p(double x) {
        return package$.MODULE$.log1p(x);
    }

    public static double log(double x) {
        return package$.MODULE$.log(x);
    }

    public static int getExponent(double d) {
        return package$.MODULE$.getExponent(d);
    }

    public static int getExponent(float f) {
        return package$.MODULE$.getExponent(f);
    }

    public static double expm1(double x) {
        return package$.MODULE$.expm1(x);
    }

    public static double exp(double x) {
        return package$.MODULE$.exp(x);
    }

    public static double pow(double x, double y) {
        return package$.MODULE$.pow(x, y);
    }

    public static double cbrt(double x) {
        return package$.MODULE$.cbrt(x);
    }

    public static double sqrt(double x) {
        return package$.MODULE$.sqrt(x);
    }

    public static float scalb(float f, int scaleFactor) {
        return package$.MODULE$.scalb(f, scaleFactor);
    }

    public static double scalb(double d, int scaleFactor) {
        return package$.MODULE$.scalb(d, scaleFactor);
    }

    public static float nextDown(float f) {
        return package$.MODULE$.nextDown(f);
    }

    public static double nextDown(double d) {
        return package$.MODULE$.nextDown(d);
    }

    public static float nextUp(float f) {
        return package$.MODULE$.nextUp(f);
    }

    public static double nextUp(double d) {
        return package$.MODULE$.nextUp(d);
    }

    public static float nextAfter(float start2, double direction) {
        return package$.MODULE$.nextAfter(start2, direction);
    }

    public static double nextAfter(double start2, double direction) {
        return package$.MODULE$.nextAfter(start2, direction);
    }

    public static float copySign(float magnitude, float sign) {
        return package$.MODULE$.copySign(magnitude, sign);
    }

    public static double copySign(double magnitude, double sign) {
        return package$.MODULE$.copySign(magnitude, sign);
    }

    public static long floorMod(long x, long y) {
        return package$.MODULE$.floorMod(x, y);
    }

    public static int floorMod(int x, int y) {
        return package$.MODULE$.floorMod(x, y);
    }

    public static long floorDiv(long x, long y) {
        return package$.MODULE$.floorDiv(x, y);
    }

    public static int floorDiv(int x, int y) {
        return package$.MODULE$.floorDiv(x, y);
    }

    public static double signum(double x) {
        return package$.MODULE$.signum(x);
    }

    public static float signum(float x) {
        return package$.MODULE$.signum(x);
    }

    public static long signum(long x) {
        return package$.MODULE$.signum(x);
    }

    public static int signum(int x) {
        return package$.MODULE$.signum(x);
    }

    public static double min(double x, double y) {
        return package$.MODULE$.min(x, y);
    }

    public static float min(float x, float y) {
        return package$.MODULE$.min(x, y);
    }

    public static long min(long x, long y) {
        return package$.MODULE$.min(x, y);
    }

    public static int min(int x, int y) {
        return package$.MODULE$.min(x, y);
    }

    public static double max(double x, double y) {
        return package$.MODULE$.max(x, y);
    }

    public static float max(float x, float y) {
        return package$.MODULE$.max(x, y);
    }

    public static long max(long x, long y) {
        return package$.MODULE$.max(x, y);
    }

    public static int max(int x, int y) {
        return package$.MODULE$.max(x, y);
    }

    public static double abs(double x) {
        return package$.MODULE$.abs(x);
    }

    public static float abs(float x) {
        return package$.MODULE$.abs(x);
    }

    public static long abs(long x) {
        return package$.MODULE$.abs(x);
    }

    public static int abs(int x) {
        return package$.MODULE$.abs(x);
    }

    public static long round(double x) {
        return package$.MODULE$.round(x);
    }

    public static int round(float x) {
        return package$.MODULE$.round(x);
    }

    public static long round(long x) {
        return package$.MODULE$.round(x);
    }

    public static double rint(double x) {
        return package$.MODULE$.rint(x);
    }

    public static double floor(double x) {
        return package$.MODULE$.floor(x);
    }

    public static double ceil(double x) {
        return package$.MODULE$.ceil(x);
    }

    public static double hypot(double x, double y) {
        return package$.MODULE$.hypot(x, y);
    }

    public static double atan2(double y, double x) {
        return package$.MODULE$.atan2(y, x);
    }

    public static double toDegrees(double x) {
        return package$.MODULE$.toDegrees(x);
    }

    public static double toRadians(double x) {
        return package$.MODULE$.toRadians(x);
    }

    public static double atan(double x) {
        return package$.MODULE$.atan(x);
    }

    public static double acos(double x) {
        return package$.MODULE$.acos(x);
    }

    public static double asin(double x) {
        return package$.MODULE$.asin(x);
    }

    public static double tan(double x) {
        return package$.MODULE$.tan(x);
    }

    public static double cos(double x) {
        return package$.MODULE$.cos(x);
    }

    public static double sin(double x) {
        return package$.MODULE$.sin(x);
    }

    public static double random() {
        return package$.MODULE$.random();
    }

    public static double Pi() {
        return package$.MODULE$.Pi();
    }

    public static double E() {
        return package$.MODULE$.E();
    }
}

