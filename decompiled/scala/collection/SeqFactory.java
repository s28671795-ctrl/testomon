/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Seq;
import scala.collection.SeqFactory$UnapplySeqWrapper$;
import scala.collection.SeqOps;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tedaB\u0013'!\u0003\r\ta\u000b\u0005\u0006\u0019\u0002!\t!\u0014\u0005\u0006#\u0002!)AU\u0004\u0006-\u001aB\ta\u0016\u0004\u0006K\u0019B\t\u0001\u0017\u0005\u0006C\u0012!\tA\u0019\u0004\u0005G\u0012\u0001A\r\u0003\u0005p\r\t\u0005\t\u0015!\u0003g\u0011\u0015\tg\u0001\"\u0001q\u0011\u0015!h\u0001\"\u0011v\u0011\u0019yh\u0001\"\u0001\u0002\u0002!9\u00111\u0002\u0004\u0005\u0002\u00055\u0001bBA\u0012\r\u0011\u0005\u0011Q\u0005\u0004\u0007\u0003\u000f\"!!!\u0013\t\u001d\u0005MS\u0002\"A\u0001\u0006\u000b\u0015\r\u0011\"\u0003\u0002V!Y\u0011qL\u0007\u0003\u0006\u0003\u0005\u000b\u0011BA,\u0011\u0019\tW\u0002\"\u0001\u0002b!9\u0011\u0011N\u0007\u0005\u0002\u0005-\u0004bBA9\u001b\u0011\u0005\u00111\u000f\u0005\b\u0003kjA\u0011AA<\u0011\u0019!X\u0002\"\u0001\u0002\u0004\"9\u0011\u0011R\u0007\u0005\u0002\u0005-\u0005bBAT\u001b\u0011\u0005\u0011\u0011\u0016\u0005\n\u0003Wk\u0011\u0011!C!\u0003[C\u0011\"a,\u000e\u0003\u0003%\t%!-\b\u0013\u0005uF!!A\t\u0002\u0005}f!CA$\t\u0005\u0005\t\u0012AAa\u0011\u0019\t'\u0004\"\u0001\u0002D\"9\u0011Q\u0019\u000e\u0005\u0006\u0005\u001d\u0007bBAk5\u0011\u0015\u0011q\u001b\u0005\b\u0003GTBQAAs\u0011\u001d\t)P\u0007C\u0003\u0003oDqAa\u0002\u001b\t\u000b\u0011I\u0001C\u0004\u0003\u001ci!)A!\b\t\u0013\t-\"$!A\u0005\u0006\t5\u0002\"\u0003B\u001d5\u0005\u0005IQ\u0001B\u001e\u0011%\u0011Y\u0005BA\u0001\n\u0013\u0011iE\u0001\u0006TKF4\u0015m\u0019;pefT!a\n\u0015\u0002\u0015\r|G\u000e\\3di&|gNC\u0001*\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001L\u001c\u0014\u0007\u0001i\u0013\u0007\u0005\u0002/_5\t\u0001&\u0003\u00021Q\t1\u0011I\\=SK\u001a\u00042AM\u001a6\u001b\u00051\u0013B\u0001\u001b'\u0005=IE/\u001a:bE2,g)Y2u_JL\bC\u0001\u001c8\u0019\u0001!a\u0001\u000f\u0001\u0005\u0006\u0004I$AA\"D+\tQ$)\u0005\u0002<}A\u0011a\u0006P\u0005\u0003{!\u0012qAT8uQ&tw\rE\u00033\u007f\u0005C5*\u0003\u0002AM\t11+Z9PaN\u0004\"A\u000e\"\u0005\u000b\r;$\u0019\u0001#\u0003\u0003\u0005\u000b\"aO#\u0011\u000592\u0015BA$)\u0005\r\te.\u001f\t\u0003e%K!A\u0013\u0014\u0003\u0007M+\u0017\u000fE\u00023\u0013\u0006\u000ba\u0001J5oSR$C#\u0001(\u0011\u00059z\u0015B\u0001))\u0005\u0011)f.\u001b;\u0002\u0015Ut\u0017\r\u001d9msN+\u0017/F\u0002T\u0005;\"2\u0001\u0016B0!\u0011)VBa\u0017\u000f\u0005I\u001a\u0011AC*fc\u001a\u000b7\r^8ssB\u0011!\u0007B\n\u0004\t5J\u0006C\u0001.`\u001b\u0005Y&B\u0001/^\u0003\tIwNC\u0001_\u0003\u0011Q\u0017M^1\n\u0005\u0001\\&\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u0001X\u0005!!U\r\\3hCR,WCA3i'\r1QF\u001a\t\u0004e\u00019\u0007C\u0001\u001ci\t\u0015AdA1\u0001j+\tQW.\u0005\u0002<WB)!g\u00107I]B\u0011a'\u001c\u0003\u0006\u0007\"\u0014\r\u0001\u0012\t\u0004e%c\u0017\u0001\u00033fY\u0016<\u0017\r^3\u0015\u0005E\u001c\bc\u0001:\u0007O6\tA\u0001C\u0003p\u0011\u0001\u0007a-A\u0003baBd\u00170\u0006\u0002wsR\u0011qO\u001f\t\u0004m!D\bC\u0001\u001cz\t\u0015\u0019\u0015B1\u0001E\u0011\u0015Y\u0018\u00021\u0001}\u0003\u0015)G.Z7t!\rqS\u0010_\u0005\u0003}\"\u0012!\u0002\u0010:fa\u0016\fG/\u001a3?\u0003\u0015)W\u000e\u001d;z+\u0011\t\u0019!!\u0003\u0016\u0005\u0005\u0015\u0001\u0003\u0002\u001ci\u0003\u000f\u00012ANA\u0005\t\u0015\u0019%B1\u0001E\u0003\u00111'o\\7\u0016\t\u0005=\u0011Q\u0003\u000b\u0005\u0003#\tI\u0002\u0005\u00037Q\u0006M\u0001c\u0001\u001c\u0002\u0016\u00111\u0011qC\u0006C\u0002\u0011\u0013\u0011!\u0012\u0005\b\u00037Y\u0001\u0019AA\u000f\u0003\tIG\u000fE\u00033\u0003?\t\u0019\"C\u0002\u0002\"\u0019\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!B\\3x\u0005VLG\u000eZ3s+\u0011\t9#a\u000e\u0016\u0005\u0005%\u0002\u0003CA\u0016\u0003c\t)$!\u000f\u000e\u0005\u00055\"bAA\u0018M\u00059Q.\u001e;bE2,\u0017\u0002BA\u001a\u0003[\u0011qAQ;jY\u0012,'\u000fE\u00027\u0003o!Qa\u0011\u0007C\u0002\u0011\u0003BA\u000e5\u00026!:a!!\u0010\u0002D\u0005\u0015\u0003c\u0001\u0018\u0002@%\u0019\u0011\u0011\t\u0015\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002\u0003#Us\u0017\r\u001d9msN+\u0017o\u0016:baB,'/\u0006\u0003\u0002L\u0005m3cA\u0007\u0002NA\u0019a&a\u0014\n\u0007\u0005E\u0003F\u0001\u0004B]f4\u0016\r\\\u00011g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$3+Z9GC\u000e$xN]=%+:\f\u0007\u000f\u001d7z'\u0016\fxK]1qa\u0016\u0014H\u0005J2\u0016\u0005\u0005]\u0003c\u0002\u001a@\u00033B\u0015Q\f\t\u0004m\u0005mC!B\"\u000e\u0005\u0004!\u0005\u0003\u0002\u001aJ\u00033\n\u0011g]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%'\u0016\fh)Y2u_JLH%\u00168baBd\u0017pU3r/J\f\u0007\u000f]3sI\u0011\u001a\u0007\u0005\u0006\u0003\u0002d\u0005\u0015\u0004\u0003\u0002:\u000e\u00033Bq!a\u001a\u0011\u0001\u0004\t9&A\u0001d\u0003\u001dI7/R7qif,\"!!\u001c\u0010\u0005\u0005=\u0014$\u0001\u0001\u0002\u0007\u001d,G/\u0006\u0002\u0002d\u0005iA.\u001a8hi\"\u001cu.\u001c9be\u0016$B!!\u001f\u0002\u0000A\u0019a&a\u001f\n\u0007\u0005u\u0004FA\u0002J]RDq!!!\u0014\u0001\u0004\tI(A\u0002mK:$B!!\u0017\u0002\u0006\"9\u0011q\u0011\u000bA\u0002\u0005e\u0014!A5\u0002\t\u0011\u0014x\u000e\u001d\u000b\u0005\u0003\u001b\u000b\u0019\u000b\u0005\u0004\u0002\u0010\u0006}\u0015\u0011\f\b\u0005\u0003#\u000bYJ\u0004\u0003\u0002\u0014\u0006eUBAAK\u0015\r\t9JK\u0001\u0007yI|w\u000e\u001e \n\u0003%J1!!()\u0003\u001d\u0001\u0018mY6bO\u0016L1ASAQ\u0015\r\ti\n\u000b\u0005\b\u0003K+\u0002\u0019AA=\u0003\u0005q\u0017!\u0002;p'\u0016\fXCAAG\u0003!A\u0017m\u001d5D_\u0012,GCAA=\u0003\u0019)\u0017/^1mgR!\u00111WA]!\rq\u0013QW\u0005\u0004\u0003oC#a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003wC\u0012\u0011!a\u0001\u000b\u0006\u0019\u0001\u0010J\u0019\u0002#Us\u0017\r\u001d9msN+\u0017o\u0016:baB,'\u000f\u0005\u0002s5M\u0011!$\f\u000b\u0003\u0003\u007f\u000b\u0011#[:F[B$\u0018\u0010J3yi\u0016t7/[8o+\u0011\tI-a5\u0015\t\u00055\u00141\u001a\u0005\b\u0003\u001bd\u0002\u0019AAh\u0003\u0015!C\u000f[5t!\u0011\u0011X\"!5\u0011\u0007Y\n\u0019\u000eB\u0003D9\t\u0007A)A\u0007hKR$S\r\u001f;f]NLwN\\\u000b\u0005\u00033\fy\u000e\u0006\u0003\u0002\\\u0006\u0005\b\u0003\u0002:\u000e\u0003;\u00042ANAp\t\u0015\u0019UD1\u0001E\u0011\u001d\ti-\ba\u0001\u00037\fq\u0003\\3oORD7i\\7qCJ,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\u0005\u001d\u00181\u001f\u000b\u0005\u0003S\fi\u000f\u0006\u0003\u0002z\u0005-\bbBAA=\u0001\u0007\u0011\u0011\u0010\u0005\b\u0003\u001bt\u0002\u0019AAx!\u0011\u0011X\"!=\u0011\u0007Y\n\u0019\u0010B\u0003D=\t\u0007A)A\bbaBd\u0017\u0010J3yi\u0016t7/[8o+\u0011\tI0a@\u0015\t\u0005m(1\u0001\u000b\u0005\u0003{\u0014\t\u0001E\u00027\u0003\u007f$QaQ\u0010C\u0002\u0011Cq!a\" \u0001\u0004\tI\bC\u0004\u0002N~\u0001\rA!\u0002\u0011\tIl\u0011Q`\u0001\u000fIJ|\u0007\u000fJ3yi\u0016t7/[8o+\u0011\u0011YAa\u0005\u0015\t\t5!q\u0003\u000b\u0005\u0005\u001f\u0011)\u0002\u0005\u0004\u0002\u0010\u0006}%\u0011\u0003\t\u0004m\tMA!B\"!\u0005\u0004!\u0005bBASA\u0001\u0007\u0011\u0011\u0010\u0005\b\u0003\u001b\u0004\u0003\u0019\u0001B\r!\u0011\u0011XB!\u0005\u0002\u001fQ|7+Z9%Kb$XM\\:j_:,BAa\b\u0003&Q!!\u0011\u0005B\u0014!\u0019\ty)a(\u0003$A\u0019aG!\n\u0005\u000b\r\u000b#\u0019\u0001#\t\u000f\u00055\u0017\u00051\u0001\u0003*A!!/\u0004B\u0012\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\t=\"q\u0007\u000b\u0005\u0003[\u0013\t\u0004C\u0004\u0002N\n\u0002\rAa\r\u0011\tIl!Q\u0007\t\u0004m\t]B!B\"#\u0005\u0004!\u0015\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0011\u0011iD!\u0013\u0015\t\t}\"1\t\u000b\u0005\u0003g\u0013\t\u0005\u0003\u0005\u0002<\u000e\n\t\u00111\u0001F\u0011\u001d\tim\ta\u0001\u0005\u000b\u0002BA]\u0007\u0003HA\u0019aG!\u0013\u0005\u000b\r\u001b#\u0019\u0001#\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t=\u0003\u0003\u0002B)\u0005/j!Aa\u0015\u000b\u0007\tUS,\u0001\u0003mC:<\u0017\u0002\u0002B-\u0005'\u0012aa\u00142kK\u000e$\bc\u0001\u001c\u0003^\u0011)1I\u0001b\u0001\t\"9!\u0011\r\u0002A\u0002\t\r\u0014!\u0001=+\t\t\u0015$q\r\t\u0005m]\u0012Yf\u000b\u0002\u0003jA!!1\u000eB;\u001b\t\u0011iG\u0003\u0003\u0003p\tE\u0014!C;oG\",7m[3e\u0015\r\u0011\u0019\bK\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B<\u0005[\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0001")
public interface SeqFactory<CC extends SeqOps<Object, Seq, Seq<Object>>>
extends IterableFactory<CC> {
    default public <A> SeqOps<A, Seq, Seq<A>> unapplySeq(CC x) {
        return x;
    }

    public static void $init$(SeqFactory $this) {
    }

    public static class Delegate<CC extends SeqOps<Object, Seq, Seq<Object>>>
    implements SeqFactory<CC> {
        private static final long serialVersionUID = 3L;
        private final SeqFactory<CC> delegate;

        @Override
        public final <A> SeqOps<A, Seq, Seq<A>> unapplySeq(CC x) {
            return ((SeqFactory)this).unapplySeq(x);
        }

        @Override
        public Object iterate(Object start2, int len, Function1 f) {
            return IterableFactory.iterate$(this, start2, len, f);
        }

        @Override
        public Object unfold(Object init2, Function1 f) {
            return IterableFactory.unfold$(this, init2, f);
        }

        @Override
        public Object range(Object start2, Object end, Integral evidence$3) {
            return IterableFactory.range$(this, start2, end, evidence$3);
        }

        @Override
        public Object range(Object start2, Object end, Object step, Integral evidence$4) {
            return IterableFactory.range$(this, start2, end, step, evidence$4);
        }

        @Override
        public Object fill(int n, Function0 elem) {
            return IterableFactory.fill$(this, n, elem);
        }

        @Override
        public Object fill(int n1, int n2, Function0 elem) {
            return IterableFactory.fill$(this, n1, n2, elem);
        }

        @Override
        public Object fill(int n1, int n2, int n3, Function0 elem) {
            return IterableFactory.fill$(this, n1, n2, n3, elem);
        }

        @Override
        public Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
            return IterableFactory.fill$(this, n1, n2, n3, n4, elem);
        }

        @Override
        public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
            return IterableFactory.fill$(this, n1, n2, n3, n4, n5, elem);
        }

        @Override
        public Object tabulate(int n, Function1 f) {
            return IterableFactory.tabulate$(this, n, f);
        }

        @Override
        public Object tabulate(int n1, int n2, Function2 f) {
            return IterableFactory.tabulate$(this, n1, n2, f);
        }

        @Override
        public Object tabulate(int n1, int n2, int n3, Function3 f) {
            return IterableFactory.tabulate$(this, n1, n2, n3, f);
        }

        @Override
        public Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
            return IterableFactory.tabulate$(this, n1, n2, n3, n4, f);
        }

        @Override
        public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
            return IterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f);
        }

        @Override
        public Object concat(scala.collection.immutable.Seq xss) {
            return IterableFactory.concat$(this, xss);
        }

        @Override
        public <A> Factory<A, CC> iterableFactory() {
            return IterableFactory.iterableFactory$(this);
        }

        @Override
        public <A> CC apply(scala.collection.immutable.Seq<A> elems) {
            return (CC)((SeqOps)this.delegate.apply(elems));
        }

        @Override
        public <A> CC empty() {
            return (CC)((SeqOps)this.delegate.empty());
        }

        @Override
        public <E> CC from(IterableOnce<E> it) {
            return (CC)((SeqOps)this.delegate.from(it));
        }

        @Override
        public <A> Builder<A, CC> newBuilder() {
            return this.delegate.newBuilder();
        }

        public Delegate(SeqFactory<CC> delegate) {
            this.delegate = delegate;
        }
    }

    public static final class UnapplySeqWrapper<A> {
        private final SeqOps<A, Seq, Seq<A>> scala$collection$SeqFactory$UnapplySeqWrapper$$c;

        public SeqOps<A, Seq, Seq<A>> scala$collection$SeqFactory$UnapplySeqWrapper$$c() {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c;
        }

        public boolean isEmpty() {
            this.scala$collection$SeqFactory$UnapplySeqWrapper$$c();
            return false;
        }

        public SeqOps<A, Seq, Seq<A>> get() {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c();
        }

        public int lengthCompare(int len) {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c().lengthCompare(len);
        }

        public A apply(int i) {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c().apply(i);
        }

        public scala.collection.immutable.Seq<A> drop(int n) {
            return SeqFactory$UnapplySeqWrapper$.MODULE$.drop$extension(this.scala$collection$SeqFactory$UnapplySeqWrapper$$c(), n);
        }

        public scala.collection.immutable.Seq<A> toSeq() {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c().toSeq();
        }

        public int hashCode() {
            return this.scala$collection$SeqFactory$UnapplySeqWrapper$$c().hashCode();
        }

        public boolean equals(Object x$1) {
            return SeqFactory$UnapplySeqWrapper$.MODULE$.equals$extension(this.scala$collection$SeqFactory$UnapplySeqWrapper$$c(), x$1);
        }

        public UnapplySeqWrapper(SeqOps<A, Seq, Seq<A>> c) {
            this.scala$collection$SeqFactory$UnapplySeqWrapper$$c = c;
        }
    }
}

