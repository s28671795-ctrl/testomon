/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mw!\u0002\u000e\u001c\u0011\u0013\u0011c!\u0002\u0013\u001c\u0011\u0013)\u0003\"\u0002\u0016\u0002\t\u0003Y\u0003\"\u0002\u0017\u0002\t\u000bi\u0003\"B#\u0002\t\u000b1\u0005\"B+\u0002\t\u000b1\u0006\"B-\u0002\t\u000bQ\u0006bB1\u0002\u0005\u0004%)A\u0019\u0005\u0007G\u0006\u0001\u000bQ\u0002\u0018\t\u000f\u0011\f!\u0019!C\u0003K\"1\u0011.\u0001Q\u0001\u000e\u0019DqA[\u0001C\u0002\u0013\u00151\u000e\u0003\u0004p\u0003\u0001\u0006i\u0001\u001c\u0005\ba\u0006\u0011\r\u0011\"\u0002r\u0011\u0019)\u0018\u0001)A\u0007e\"9a/\u0001b\u0001\n\u000b9\bBB>\u0002A\u00035\u0001\u0010C\u0004}\u0003\t\u0007IQA?\t\u000f\u0005\r\u0011\u0001)A\u0007}\"9\u0011QA\u0001\u0005\u0006\u0005\u001d\u0001bBA\u001e\u0003\u0011\u0015\u0011Q\b\u0005\b\u0003#\nAQAA*\u0011\u001d\ti'\u0001C\u0003\u0003_Bq!a#\u0002\t\u000b\ti\tC\u0004\u0002,\u0006!)!!,\t\u000f\u0005\u001d\u0017\u0001\"\u0002\u0002J\u0006ia+Z2u_J\u001cF/\u0019;jGNT!\u0001H\u000f\u0002\u0013%lW.\u001e;bE2,'B\u0001\u0010 \u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002A\u0005)1oY1mC\u000e\u0001\u0001CA\u0012\u0002\u001b\u0005Y\"!\u0004,fGR|'o\u0015;bi&\u001c7o\u0005\u0002\u0002MA\u0011q\u0005K\u0007\u0002?%\u0011\u0011f\b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0011\u0013aC2paf\f\u0005\u000f]3oIF\"2A\f A!\ty3H\u0004\u00021s9\u0011\u0011\u0007\u000f\b\u0003e]r!a\r\u001c\u000e\u0003QR!!N\u0011\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0013B\u0001\u0010 \u0013\taR$\u0003\u0002;7\u0005aa+Z2u_JLe\u000e\\5oK&\u0011A(\u0010\u0002\u0005\u0003J\u0014\u0018G\u0003\u0002;7!)qh\u0001a\u0001]\u0005\t\u0011\rC\u0003B\u0007\u0001\u0007!)\u0001\u0003fY\u0016l\u0007CA\u0014D\u0013\t!uDA\u0002B]f\f!bY8qs\u0006\u0003\b/\u001a8e+\t9U\nF\u0002I'R\u00032aJ%L\u0013\tQuDA\u0003BeJ\f\u0017\u0010\u0005\u0002M\u001b2\u0001A!\u0002(\u0005\u0005\u0004y%!\u0001+\u0012\u0005A3\u0003CA\u0014R\u0013\t\u0011vDA\u0004O_RD\u0017N\\4\t\u000b}\"\u0001\u0019\u0001%\t\u000b\u0005#\u0001\u0019A&\u0002\u0019\r|\u0007/\u001f)sKB,g\u000eZ\u0019\u0015\u00079:\u0006\fC\u0003B\u000b\u0001\u0007!\tC\u0003@\u000b\u0001\u0007a&A\u0006d_BL\bK]3qK:$WCA._)\rav\f\u0019\t\u0004O%k\u0006C\u0001'_\t\u0015qeA1\u0001P\u0011\u0015\te\u00011\u0001^\u0011\u0015yd\u00011\u0001]\u0003\u0019)W\u000e\u001d;zcU\ta&A\u0004f[B$\u00180\r\u0011\u0002\r\u0015l\u0007\u000f^=3+\u00051\u0007CA\u0018h\u0013\tAWH\u0001\u0003BeJ\u0014\u0014aB3naRL(\u0007I\u0001\u0007K6\u0004H/_\u001a\u0016\u00031\u0004\"aL7\n\u00059l$\u0001B!seN\nq!Z7qif\u001c\u0004%\u0001\u0004f[B$\u0018\u0010N\u000b\u0002eB\u0011qf]\u0005\u0003iv\u0012A!\u0011:si\u00059Q-\u001c9usR\u0002\u0013AB3naRLX'F\u0001y!\ty\u00130\u0003\u0002{{\t!\u0011I\u001d:6\u0003\u001d)W\u000e\u001d;zk\u0001\na!Z7qif4T#\u0001@\u0011\u0005=z\u0018bAA\u0001{\t!\u0011I\u001d:7\u0003\u001d)W\u000e\u001d;zm\u0001\n!BZ8sK\u0006\u001c\u0007NU3d+!\tI!!\t\u00020\u0005]B\u0003CA\u0006\u0003#\tY\"a\t\u0011\u0007\u001d\ni!C\u0002\u0002\u0010}\u0011A!\u00168ji\"9\u00111C\nA\u0002\u0005U\u0011!\u00027fm\u0016d\u0007cA\u0014\u0002\u0018%\u0019\u0011\u0011D\u0010\u0003\u0007%sG\u000f\u0003\u0004@'\u0001\u0007\u0011Q\u0004\t\u0005O%\u000by\u0002E\u0002M\u0003C!QAT\nC\u0002=Cq!!\n\u0014\u0001\u0004\t9#A\u0001g!\u001d9\u0013\u0011FA\u0017\u0003kI1!a\u000b \u0005%1UO\\2uS>t\u0017\u0007E\u0002M\u0003_!q!!\r\u0014\u0005\u0004\t\u0019DA\u0001B#\t\u0001&\tE\u0002M\u0003o!q!!\u000f\u0014\u0005\u0004\t\u0019DA\u0001V\u0003%i\u0017\r]#mK6\u001c\u0018'\u0006\u0004\u0002@\u0005%\u0013Q\n\u000b\u0006]\u0005\u0005\u00131\t\u0005\u0006\u007fQ\u0001\rA\f\u0005\b\u0003K!\u0002\u0019AA#!\u001d9\u0013\u0011FA$\u0003\u0017\u00022\u0001TA%\t\u001d\t\t\u0004\u0006b\u0001\u0003g\u00012\u0001TA'\t\u001d\ty\u0005\u0006b\u0001\u0003g\u0011\u0011AQ\u0001\u000e[\u0006\u0004X\t\\3ngF\u0012Vm\u001d;\u0016\r\u0005U\u0013qLA2)%q\u0013qKA-\u0003K\nI\u0007C\u0003@+\u0001\u0007a\u0006C\u0004\u0002&U\u0001\r!a\u0017\u0011\u000f\u001d\nI#!\u0018\u0002bA\u0019A*a\u0018\u0005\u000f\u0005ERC1\u0001\u00024A\u0019A*a\u0019\u0005\u000f\u0005=SC1\u0001\u00024!9\u0011qM\u000bA\u0002\u0005U\u0011AA1u\u0011\u0019\tY'\u0006a\u0001M\u0005\u0011aOM\u0001\t[\u0006\u0004X\t\\3ngVA\u0011\u0011OAC\u0003\u0013\u000b9\b\u0006\u0005\u0002t\u0005e\u0014QPA@!\u00119\u0013*!\u001e\u0011\u00071\u000b9\bB\u0003O-\t\u0007q\nC\u0004\u0002|Y\u0001\r!!\u0006\u0002\u00039Daa\u0010\fA\u0002\u0005M\u0004bBA\u0013-\u0001\u0007\u0011\u0011\u0011\t\bO\u0005%\u00121QAD!\ra\u0015Q\u0011\u0003\b\u0003c1\"\u0019AA\u001a!\ra\u0015\u0011\u0012\u0003\b\u0003\u001f2\"\u0019AA\u001a\u00031i\u0017\r]#mK6\u001c(+Z:u+!\ty)!)\u0002&\u0006UE\u0003DAI\u0003/\u000bI*a'\u0002(\u0006%\u0006\u0003B\u0014J\u0003'\u00032\u0001TAK\t\u0015quC1\u0001P\u0011\u001d\tYh\u0006a\u0001\u0003+AaaP\fA\u0002\u0005E\u0005bBA\u0013/\u0001\u0007\u0011Q\u0014\t\bO\u0005%\u0012qTAR!\ra\u0015\u0011\u0015\u0003\b\u0003c9\"\u0019AA\u001a!\ra\u0015Q\u0015\u0003\b\u0003\u001f:\"\u0019AA\u001a\u0011\u001d\t9g\u0006a\u0001\u0003+Aa!a\u001b\u0018\u0001\u00041\u0013a\u00049sKB,g\u000eZ\u0019JMN\u0003\u0018mY3\u0015\u000b9\ny+a-\t\r\u0005E\u0006\u00041\u0001/\u0003\u001d\u0001(/\u001a4jqFBq!!.\u0019\u0001\u0004\t9,\u0001\u0002ygB\"\u0011\u0011XAb!\u0019\tY,!0\u0002B6\tQ$C\u0002\u0002@v\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u00042\u0001TAb\t1\t)-a-\u0002\u0002\u0003\u0005)\u0011AA\u001a\u0005\u0011yFEM\u001c\u0002\u001d\u0005\u0004\b/\u001a8ec%37\u000b]1dKR)a&a3\u0002P\"1\u0011QZ\rA\u00029\nqa];gM&D\u0018\u0007C\u0004\u00026f\u0001\r!!51\t\u0005M\u0017q\u001b\t\u0007\u0003w\u000bi,!6\u0011\u00071\u000b9\u000e\u0002\u0007\u0002Z\u0006=\u0017\u0011!A\u0001\u0006\u0003\t\u0019D\u0001\u0003`IIB\u0004")
public final class VectorStatics {
    public static Object[] append1IfSpace(Object[] suffix1, IterableOnce<?> xs) {
        return VectorStatics$.MODULE$.append1IfSpace(suffix1, xs);
    }

    public static Object[] prepend1IfSpace(Object[] prefix1, IterableOnce<?> xs) {
        return VectorStatics$.MODULE$.prepend1IfSpace(prefix1, xs);
    }

    public static <A, B, T> T[] mapElemsRest(int n, T[] a, Function1<A, B> f, int at, Object v2) {
        return VectorStatics$.MODULE$.mapElemsRest(n, a, f, at, v2);
    }

    public static <A, B, T> T[] mapElems(int n, T[] a, Function1<A, B> f) {
        return VectorStatics$.MODULE$.mapElems(n, a, f);
    }

    public static <A, B> Object[] mapElems1Rest(Object[] a, Function1<A, B> f, int at, Object v2) {
        return VectorStatics$.MODULE$.mapElems1Rest(a, f, at, v2);
    }

    public static <A, B> Object[] mapElems1(Object[] a, Function1<A, B> f) {
        return VectorStatics$.MODULE$.mapElems1(a, f);
    }

    public static <T, A, U> void foreachRec(int level, T[] a, Function1<A, U> f) {
        VectorStatics$.MODULE$.foreachRec(level, a, f);
    }

    public static Object[][][][][][] empty6() {
        return VectorStatics$.MODULE$.empty6();
    }

    public static Object[][][][][] empty5() {
        return VectorStatics$.MODULE$.empty5();
    }

    public static Object[][][][] empty4() {
        return VectorStatics$.MODULE$.empty4();
    }

    public static Object[][][] empty3() {
        return VectorStatics$.MODULE$.empty3();
    }

    public static Object[][] empty2() {
        return VectorStatics$.MODULE$.empty2();
    }

    public static Object[] empty1() {
        return VectorStatics$.MODULE$.empty1();
    }

    public static <T> T[] copyPrepend(T elem, T[] a) {
        return VectorStatics$.MODULE$.copyPrepend(elem, a);
    }

    public static Object[] copyPrepend1(Object elem, Object[] a) {
        return VectorStatics$.MODULE$.copyPrepend1(elem, a);
    }

    public static <T> T[] copyAppend(T[] a, T elem) {
        return VectorStatics$.MODULE$.copyAppend(a, elem);
    }

    public static Object[] copyAppend1(Object[] a, Object elem) {
        return VectorStatics$.MODULE$.copyAppend1(a, elem);
    }
}

