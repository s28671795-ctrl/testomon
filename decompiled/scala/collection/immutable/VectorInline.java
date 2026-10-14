/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.VectorInline$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tutA\u0002\u001d:\u0011\u0003ItH\u0002\u0004Bs!\u0005\u0011H\u0011\u0005\u0006\u000f\u0006!\t!\u0013\u0005\b\u0015\u0006\u0011\r\u0011\"\u0002L\u0011\u0019q\u0015\u0001)A\u0007\u0019\"9q*\u0001b\u0001\n\u000b\u0001\u0006BB*\u0002A\u00035\u0011\u000bC\u0004U\u0003\t\u0007IQA+\t\ra\u000b\u0001\u0015!\u0004W\u0011\u001dI\u0016A1A\u0005\u0006iCa!X\u0001!\u0002\u001bY\u0006b\u00020\u0002\u0005\u0004%)a\u0018\u0005\u0007E\u0006\u0001\u000bQ\u00021\t\u000f\r\f!\u0019!C\u0003I\"1q-\u0001Q\u0001\u000e\u0015Dq\u0001[\u0001C\u0002\u0013\u0015\u0011\u000e\u0003\u0004m\u0003\u0001\u0006iA\u001b\u0005\b[\u0006\u0011\r\u0011\"\u0002o\u0011\u0019\t\u0018\u0001)A\u0007_\"9!/\u0001b\u0001\n\u000b\u0019\bB\u0002<\u0002A\u00035A\u000fC\u0004x\u0003\t\u0007IQ\u0001=\t\rm\f\u0001\u0015!\u0004z\u0011\u001da\u0018A1A\u0005\u0006uDq!!\u0001\u0002A\u00035a\u0010C\u0005\u0002\u0004\u0005\u0011\r\u0011\"\u0002\u0002\u0006!A\u00111B\u0001!\u0002\u001b\t9\u0001C\u0005\u0002\u000e\u0005\u0011\r\u0011\"\u0002\u0002\u0010!A\u0011QC\u0001!\u0002\u001b\t\t\u0002C\u0005\u0002\u0018\u0005\u0011\r\u0011\"\u0002\u0002\u001a!A\u0011qD\u0001!\u0002\u001b\tY\u0002\u0003\u0005\u0002\"\u0005\u0011\r\u0011\"\u0002L\u0011\u001d\t\u0019#\u0001Q\u0001\u000e1+a!!\n\u0002\u0001\u0005\u001dRABA\u0017\u0003\u0001\ty#\u0002\u0004\u00022\u0005\u0001\u00111G\u0003\u0007\u0003k\t\u0001!a\u000e\u0006\r\u0005e\u0012\u0001AA\u001e\u000b\u0019\ti$\u0001\u0001\u0002@!9\u0011\u0011I\u0001\u0005\u0002\u0005\r\u0003bBA.\u0003\u0011\u0005\u0011Q\f\u0005\b\u0003\u0003\u000bAQAAB\u0011\u001d\t\t*\u0001C\u0003\u0003'Cq!!)\u0002\t\u000b\t\u0019\u000bC\u0004\u00026\u0006!)!a.\t\u000f\u0005%\u0017\u0001\"\u0002\u0002L\"9\u00111[\u0001\u0005\u0006\u0005U\u0007bBAo\u0003\u0011\u0015\u0011q\u001c\u0005\b\u0003O\fAQAAu\u0011\u001d\t\t0\u0001C\u0003\u0003gDq!!=\u0002\t\u000b\u0011\u0019\u0001C\u0004\u0002r\u0006!)Aa\u0005\t\u000f\u0005E\u0018\u0001\"\u0002\u0003&!9\u0011\u0011_\u0001\u0005\u0006\te\u0002bBAy\u0003\u0011\u0015!q\n\u0005\b\u0005S\nAQ\u0001B6\u000311Vm\u0019;pe&sG.\u001b8f\u0015\tQ4(A\u0005j[6,H/\u00192mK*\u0011A(P\u0001\u000bG>dG.Z2uS>t'\"\u0001 \u0002\u000bM\u001c\u0017\r\\1\u0011\u0005\u0001\u000bQ\"A\u001d\u0003\u0019Y+7\r^8s\u0013:d\u0017N\\3\u0014\u0005\u0005\u0019\u0005C\u0001#F\u001b\u0005i\u0014B\u0001$>\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001@\u0003\u0011\u0011\u0015\nV*\u0016\u00031{\u0011!T\u000f\u0002\u000b\u0005)!)\u0013+TA\u0005)q+\u0013#U\u0011V\t\u0011kD\u0001S;\u0005\u0001\u0013AB,J\tRC\u0005%\u0001\u0003N\u0003N[U#\u0001,\u0010\u0003]k\u0012aH\u0001\u0006\u001b\u0006\u001b6\nI\u0001\u0006\u0005&#6KM\u000b\u00027>\tA,H\u0001\u000b\u0003\u0019\u0011\u0015\nV*3A\u00051q+\u0013#U\u0011J*\u0012\u0001Y\b\u0002Cv\u0011A\u0001A\u0001\b/&#E\u000b\u0013\u001a!\u0003\u0015\u0011\u0015\nV*4+\u0005)w\"\u00014\u001e\u0003=\taAQ%U'N\u0002\u0013AB,J\tRC5'F\u0001k\u001f\u0005YWd\u0001\u0001\u0001\u0002\u00059q+\u0013#U\u0011N\u0002\u0013!\u0002\"J)N#T#A8\u0010\u0003Al\u0012\u0001F\u0001\u0007\u0005&#6\u000b\u000e\u0011\u0002\r]KE\t\u0016%5+\u0005!x\"A;\u001e\u0007A\u0001\u0001!A\u0004X\u0013\u0012#\u0006\n\u000e\u0011\u0002\u000b\tKEkU\u001b\u0016\u0003e|\u0011A_\u000f\u00023\u00051!)\u0013+Tk\u0001\naaV%E)\"+T#\u0001@\u0010\u0003}lBA\u0001\u0001\u0001\u0001\u00059q+\u0013#U\u0011V\u0002\u0013!\u0002\"J)N3TCAA\u0004\u001f\t\tI!H\u0001\u001f\u0003\u0019\u0011\u0015\nV*7A\u00051q+\u0013#U\u0011Z*\"!!\u0005\u0010\u0005\u0005MQ\u0004\u0002!\u0001\u0001\u0001\tqaV%E)\"3\u0004%A\u0005M\u0003N#v+\u0013#U\u0011V\u0011\u00111D\b\u0003\u0003;i\u0012\u0001Q\u0001\u000b\u0019\u0006\u001bFkV%E)\"\u0003\u0013\u0001\u0005'pOJ\u001auN\\2bi\u001a\u000b7\u000f^3s\u0003Eaun\u001a\u001aD_:\u001c\u0017\r\u001e$bgR,'\u000f\t\u0002\u0005\u0003J\u0014\u0018\u0007\u0005\u0003E\u0003S\u0019\u0015bAA\u0016{\t)\u0011I\u001d:bs\n!\u0011I\u001d:3!\u0015!\u0015\u0011FA\u0014\u0005\u0011\t%O]\u001a\u0011\u000b\u0011\u000bI#a\f\u0003\t\u0005\u0013(\u000f\u000e\t\u0006\t\u0006%\u00121\u0007\u0002\u0005\u0003J\u0014X\u0007E\u0003E\u0003S\t9D\u0001\u0003BeJ4\u0004#\u0002#\u0002*\u0005m\u0012A\u0004<fGR|'o\u00157jG\u0016$\u0015.\u001c\u000b\u0007\u0003\u000b\nY%a\u0014\u0011\u0007\u0011\u000b9%C\u0002\u0002Ju\u00121!\u00138u\u0011\u001d\tie\na\u0001\u0003\u000b\nQaY8v]RDq!!\u0015(\u0001\u0004\t)%A\u0002jIbD3aJA+!\r!\u0015qK\u0005\u0004\u00033j$AB5oY&tW-A\u0005d_BLxJ]+tKV!\u0011qLA4)!\t\t'a\u001d\u0002x\u0005m\u0004#\u0002#\u0002*\u0005\r\u0004\u0003BA3\u0003Ob\u0001\u0001B\u0004\u0002j!\u0012\r!a\u001b\u0003\u0003Q\u000b2!!\u001cD!\r!\u0015qN\u0005\u0004\u0003cj$a\u0002(pi\"Lgn\u001a\u0005\b\u0003kB\u0003\u0019AA1\u0003\u0005\t\u0007bBA=Q\u0001\u0007\u0011QI\u0001\u0006gR\f'\u000f\u001e\u0005\b\u0003{B\u0003\u0019AA#\u0003\r)g\u000e\u001a\u0015\u0004Q\u0005U\u0013\u0001C2paf$\u0016-\u001b7\u0016\t\u0005\u0015\u00151\u0012\u000b\u0005\u0003\u000f\u000bi\tE\u0003E\u0003S\tI\t\u0005\u0003\u0002f\u0005-EaBA5S\t\u0007\u00111\u000e\u0005\b\u0003kJ\u0003\u0019AADQ\rI\u0013QK\u0001\tG>\u0004\u00180\u00138jiV!\u0011QSAN)\u0011\t9*!(\u0011\u000b\u0011\u000bI#!'\u0011\t\u0005\u0015\u00141\u0014\u0003\b\u0003SR#\u0019AA6\u0011\u001d\t)H\u000ba\u0001\u0003/C3AKA+\u0003M\u0019w\u000e]=JM\u0012KgMZ3sK:$8+\u001b>f+\u0011\t)+a+\u0015\r\u0005\u001d\u0016QVAX!\u0015!\u0015\u0011FAU!\u0011\t)'a+\u0005\u000f\u0005%4F1\u0001\u0002l!9\u0011QO\u0016A\u0002\u0005\u001d\u0006bBAYW\u0001\u0007\u0011QI\u0001\u0004Y\u0016t\u0007fA\u0016\u0002V\u0005)qO]1qcQ!\u0011\u0011XA_!\r\tY,I\u0007\u0002\u0003!9\u0011q\u0018\u0017A\u0002\u0005\u0005\u0017!\u0001=\u0011\u0007\u0011\u000b\u0019-C\u0002\u0002Fv\u00121!\u00118zQ\ra\u0013QK\u0001\u0006oJ\f\u0007O\r\u000b\u0005\u0003\u001b\fy\rE\u0002\u0002<\nBq!a0.\u0001\u0004\tI\fK\u0002.\u0003+\nQa\u001e:baN\"B!a6\u0002ZB\u0019\u00111X\u0012\t\u000f\u0005}f\u00061\u0001\u0002N\"\u001aa&!\u0016\u0002\u000b]\u0014\u0018\r\u001d\u001b\u0015\t\u0005\u0005\u00181\u001d\t\u0004\u0003w#\u0003bBA`_\u0001\u0007\u0011q\u001b\u0015\u0004_\u0005U\u0013!B<sCB,D\u0003BAv\u0003[\u00042!a/&\u0011\u001d\ty\f\ra\u0001\u0003CD3\u0001MA+\u0003)\u0019w\u000e]=Va\u0012\fG/\u001a\u000b\t\u0003s\u000b)0!?\u0002~\"9\u0011q_\u0019A\u0002\u0005e\u0016AA12\u0011\u001d\tY0\ra\u0001\u0003\u000b\nA!\u001b3yc!9\u0011q`\u0019A\u0002\u0005\u0005\u0017\u0001B3mK6D3!MA+))\tiM!\u0002\u0003\n\t5!q\u0002\u0005\b\u0005\u000f\u0011\u0004\u0019AAg\u0003\t\t'\u0007C\u0004\u0003\fI\u0002\r!!\u0012\u0002\t%$\u0007P\r\u0005\b\u0003w\u0014\u0004\u0019AA#\u0011\u001d\tyP\ra\u0001\u0003\u0003D3AMA+)1\t9N!\u0006\u0003\u001a\tu!q\u0004B\u0011\u0011\u001d\u00119b\ra\u0001\u0003/\f!!Y\u001a\t\u000f\tm1\u00071\u0001\u0002F\u0005!\u0011\u000e\u001a=4\u0011\u001d\u0011Ya\ra\u0001\u0003\u000bBq!a?4\u0001\u0004\t)\u0005C\u0004\u0002\u0000N\u0002\r!!1)\u0007M\n)\u0006\u0006\b\u0002b\n\u001d\"1\u0006B\u0018\u0005c\u0011\u0019D!\u000e\t\u000f\t%B\u00071\u0001\u0002b\u0006\u0011\u0011\r\u000e\u0005\b\u0005[!\u0004\u0019AA#\u0003\u0011IG\r\u001f\u001b\t\u000f\tmA\u00071\u0001\u0002F!9!1\u0002\u001bA\u0002\u0005\u0015\u0003bBA~i\u0001\u0007\u0011Q\t\u0005\b\u0003\u007f$\u0004\u0019AAaQ\r!\u0014Q\u000b\u000b\u0011\u0003W\u0014YDa\u0010\u0003D\t\u0015#q\tB%\u0005\u0017BqA!\u00106\u0001\u0004\tY/\u0001\u0002bk!9!\u0011I\u001bA\u0002\u0005\u0015\u0013\u0001B5eqVBqA!\f6\u0001\u0004\t)\u0005C\u0004\u0003\u001cU\u0002\r!!\u0012\t\u000f\t-Q\u00071\u0001\u0002F!9\u00111`\u001bA\u0002\u0005\u0015\u0003bBA\u0000k\u0001\u0007\u0011\u0011\u0019\u0015\u0004k\u0005UCC\u0005B)\u0005'\u00129Fa\u0017\u0003^\t}#\u0011\rB2\u0005K\u00022!a/'\u0011\u001d\u0011)F\u000ea\u0001\u0005#\n!!\u0019\u001c\t\u000f\tec\u00071\u0001\u0002F\u0005!\u0011\u000e\u001a=7\u0011\u001d\u0011\tE\u000ea\u0001\u0003\u000bBqA!\f7\u0001\u0004\t)\u0005C\u0004\u0003\u001cY\u0002\r!!\u0012\t\u000f\t-a\u00071\u0001\u0002F!9\u00111 \u001cA\u0002\u0005\u0015\u0003bBA\u0000m\u0001\u0007\u0011\u0011\u0019\u0015\u0004m\u0005U\u0013\u0001D2p]\u000e\fG/\u0011:sCf\u001cX\u0003\u0002B7\u0005g\"bAa\u001c\u0003v\t]\u0004#\u0002#\u0002*\tE\u0004\u0003BA3\u0005g\"q!!\u001b8\u0005\u0004\tY\u0007C\u0004\u0002v]\u0002\rAa\u001c\t\u000f\tet\u00071\u0001\u0003p\u0005\t!\rK\u00028\u0003+\u0002")
public final class VectorInline {
    public static <T> T[] concatArrays(T[] a, T[] b) {
        return VectorInline$.MODULE$.concatArrays(a, b);
    }

    public static Object[][][][][][] copyUpdate(Object[][][][][][] a6, int idx6, int idx5, int idx4, int idx3, int idx2, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a6, idx6, idx5, idx4, idx3, idx2, idx1, elem);
    }

    public static Object[][][][][] copyUpdate(Object[][][][][] a5, int idx5, int idx4, int idx3, int idx2, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a5, idx5, idx4, idx3, idx2, idx1, elem);
    }

    public static Object[][][][] copyUpdate(Object[][][][] a4, int idx4, int idx3, int idx2, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a4, idx4, idx3, idx2, idx1, elem);
    }

    public static Object[][][] copyUpdate(Object[][][] a3, int idx3, int idx2, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a3, idx3, idx2, idx1, elem);
    }

    public static Object[][] copyUpdate(Object[][] a2, int idx2, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a2, idx2, idx1, elem);
    }

    public static Object[] copyUpdate(Object[] a1, int idx1, Object elem) {
        return VectorInline$.MODULE$.copyUpdate(a1, idx1, elem);
    }

    public static Object[][][][][] wrap5(Object[][][][] x) {
        return VectorInline$.MODULE$.wrap5(x);
    }

    public static Object[][][][] wrap4(Object[][][] x) {
        return VectorInline$.MODULE$.wrap4(x);
    }

    public static Object[][][] wrap3(Object[][] x) {
        return VectorInline$.MODULE$.wrap3(x);
    }

    public static Object[][] wrap2(Object[] x) {
        return VectorInline$.MODULE$.wrap2(x);
    }

    public static Object[] wrap1(Object x) {
        return VectorInline$.MODULE$.wrap1(x);
    }

    public static <T> T[] copyIfDifferentSize(T[] a, int len) {
        return VectorInline$.MODULE$.copyIfDifferentSize(a, len);
    }

    public static <T> T[] copyInit(T[] a) {
        return VectorInline$.MODULE$.copyInit(a);
    }

    public static <T> T[] copyTail(T[] a) {
        return VectorInline$.MODULE$.copyTail(a);
    }

    public static <T> T[] copyOrUse(T[] a, int start2, int end) {
        return VectorInline$.MODULE$.copyOrUse(a, start2, end);
    }

    public static int vectorSliceDim(int count, int idx) {
        return VectorInline$.MODULE$.vectorSliceDim(count, idx);
    }

    public static int Log2ConcatFaster() {
        return VectorInline$.MODULE$.Log2ConcatFaster();
    }

    public static int LASTWIDTH() {
        return VectorInline$.MODULE$.LASTWIDTH();
    }

    public static int WIDTH6() {
        return VectorInline$.MODULE$.WIDTH6();
    }

    public static int BITS6() {
        return VectorInline$.MODULE$.BITS6();
    }

    public static int WIDTH5() {
        return VectorInline$.MODULE$.WIDTH5();
    }

    public static int BITS5() {
        return VectorInline$.MODULE$.BITS5();
    }

    public static int WIDTH4() {
        return VectorInline$.MODULE$.WIDTH4();
    }

    public static int BITS4() {
        return VectorInline$.MODULE$.BITS4();
    }

    public static int WIDTH3() {
        return VectorInline$.MODULE$.WIDTH3();
    }

    public static int BITS3() {
        return VectorInline$.MODULE$.BITS3();
    }

    public static int WIDTH2() {
        return VectorInline$.MODULE$.WIDTH2();
    }

    public static int BITS2() {
        return VectorInline$.MODULE$.BITS2();
    }

    public static int MASK() {
        return VectorInline$.MODULE$.MASK();
    }

    public static int WIDTH() {
        return VectorInline$.MODULE$.WIDTH();
    }

    public static int BITS() {
        return VectorInline$.MODULE$.BITS();
    }
}

