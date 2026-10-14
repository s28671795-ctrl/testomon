/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tega\u0002\u0011\"!\u0003\r\tA\n\u0005\u0006g\u0001!\t\u0001\u000e\u0005\u0006q\u00011\t!\u000f\u0005\u0006-\u00021\ta\u0016\u0005\u0006W\u0002!\t\u0001\u001c\u0005\u0006{\u00021\tA \u0005\b\u0003?\u0001A1AA\u0011\u000f\u001d\ti$\tE\u0001\u0003\u007f1a\u0001I\u0011\t\u0002\u0005\u0005\u0003bBA)\u0011\u0011\u0005\u00111\u000b\u0005\b\u0003+BA1AA,\r!\t)\t\u0003Q\u0001\n\u0005\u001d\u0005BCA@\u0017\t\u0005\t\u0015!\u0003\u0002&\"Q\u0011\u0011V\u0006\u0003\u0004\u0003\u0006Y!a+\t\u000f\u0005E3\u0002\"\u0001\u0002.\"9\u0011\u0011X\u0006\u0005\u0002\u0005m\u0006BB?\f\t\u0003\t\t\rC\u0004\u0002R\"!\u0019!a5\u0007\r\t\r\u0001\u0002\u0002B\u0003\u0011)\tyH\u0005B\u0001B\u0003%!1\u0005\u0005\u000b\u0005O\u0011\"1!Q\u0001\f\t%\u0002bBA)%\u0011\u0005!1\u0006\u0005\b\u0003s\u0013B\u0011\u0001B\u001b\u0011\u0019i(\u0003\"\u0001\u0003@\u00191!Q\t\u0005\u0001\u0005\u000fB!Ba\u0017\u0019\u0005\u0003\u0005\u000b\u0011\u0002B&\u0011\u001d\t\t\u0006\u0007C\u0001\u0005;Baa\u001b\r\u0005B\t\r\u0004B\u0002,\u0019\t\u0003\u0011y\b\u0003\u000491\u0011\u0005!1\u0014\u0005\u0007{b!\tAa,\t\u0013\t%\u0007\"!A\u0005\n\t-'\u0001E*peR,G-T1q\r\u0006\u001cGo\u001c:z\u0015\t\u00113%\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001J\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t9ShE\u0002\u0001Q1\u0002\"!\u000b\u0016\u000e\u0003\rJ!aK\u0012\u0003\r\u0005s\u0017PU3g!\ti\u0003G\u0004\u0002*]%\u0011qfI\u0001\ba\u0006\u001c7.Y4f\u0013\t\t$G\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00020G\u00051A%\u001b8ji\u0012\"\u0012!\u000e\t\u0003SYJ!aN\u0012\u0003\tUs\u0017\u000e^\u0001\u0006K6\u0004H/_\u000b\u0004u1{ECA\u001eR!\u0011aTh\u0013(\r\u0001\u00111a\b\u0001CC\u0002}\u0012!aQ\"\u0016\u0007\u0001;\u0015*\u0005\u0002B\tB\u0011\u0011FQ\u0005\u0003\u0007\u000e\u0012qAT8uQ&tw\r\u0005\u0002*\u000b&\u0011ai\t\u0002\u0004\u0003:LH!\u0002%>\u0005\u0004\u0001%!B0%IM\u001aD!\u0002&>\u0005\u0004\u0001%!B0%IM\"\u0004C\u0001\u001fM\t\u0015i%A1\u0001A\u0005\u0005Y\u0005C\u0001\u001fP\t\u0015\u0001&A1\u0001A\u0005\u00051\u0006b\u0002*\u0003\u0003\u0003\u0005\u001daU\u0001\fKZLG-\u001a8dK\u0012\u001ad\u0007E\u0002.).K!!\u0016\u001a\u0003\u0011=\u0013H-\u001a:j]\u001e\fAA\u001a:p[V\u0019\u0001\f\u00180\u0015\u0005e\u0013GC\u0001.`!\u0011aThW/\u0011\u0005qbF!B'\u0004\u0005\u0004\u0001\u0005C\u0001\u001f_\t\u0015\u00016A1\u0001A\u0011\u001d\u00017!!AA\u0004\u0005\f1\"\u001a<jI\u0016t7-\u001a\u00134oA\u0019Q\u0006V.\t\u000b\r\u001c\u0001\u0019\u00013\u0002\u0005%$\bcA3gQ6\t\u0011%\u0003\u0002hC\ta\u0011\n^3sC\ndWm\u00148dKB!\u0011&[.^\u0013\tQ7E\u0001\u0004UkBdWMM\u0001\u0006CB\u0004H._\u000b\u0004[F\u001cHC\u00018x)\tyG\u000f\u0005\u0003={A\u0014\bC\u0001\u001fr\t\u0015iEA1\u0001A!\ta4\u000fB\u0003Q\t\t\u0007\u0001\tC\u0004v\t\u0005\u0005\t9\u0001<\u0002\u0017\u00154\u0018\u000eZ3oG\u0016$3\u0007\u000f\t\u0004[Q\u0003\b\"\u0002=\u0005\u0001\u0004I\u0018!B3mK6\u001c\bcA\u0015{y&\u00111p\t\u0002\u000byI,\u0007/Z1uK\u0012t\u0004\u0003B\u0015jaJ\f!B\\3x\u0005VLG\u000eZ3s+\u0015y\u0018\u0011CA\u000b)\u0011\t\t!!\u0007\u0011\u0011\u0005\r\u0011\u0011BA\u0007\u0003/i!!!\u0002\u000b\u0007\u0005\u001d\u0011%A\u0004nkR\f'\r\\3\n\t\u0005-\u0011Q\u0001\u0002\b\u0005VLG\u000eZ3s!\u0019I\u0013.a\u0004\u0002\u0014A\u0019A(!\u0005\u0005\u000b5+!\u0019\u0001!\u0011\u0007q\n)\u0002B\u0003Q\u000b\t\u0007\u0001\t\u0005\u0004={\u0005=\u00111\u0003\u0005\n\u00037)\u0011\u0011!a\u0002\u0003;\t1\"\u001a<jI\u0016t7-\u001a\u00134sA!Q\u0006VA\b\u0003A\u0019xN\u001d;fI6\u000b\u0007OR1di>\u0014\u00180\u0006\u0004\u0002$\u0005=\u00121\u0007\u000b\u0005\u0003K\t9\u0004E\u0004f\u0003O\tY#!\u000e\n\u0007\u0005%\u0012EA\u0004GC\u000e$xN]=\u0011\r%J\u0017QFA\u0019!\ra\u0014q\u0006\u0003\u0006\u001b\u001a\u0011\r\u0001\u0011\t\u0004y\u0005MB!\u0002)\u0007\u0005\u0004\u0001\u0005C\u0002\u001f>\u0003[\t\t\u0004C\u0005\u0002:\u0019\t\t\u0011q\u0001\u0002<\u0005YQM^5eK:\u001cW\r\n\u001b1!\u0011iC+!\f\u0002!M{'\u000f^3e\u001b\u0006\u0004h)Y2u_JL\bCA3\t'\u0011A\u0001&a\u0011\u0011\t\u0005\u0015\u0013qJ\u0007\u0003\u0003\u000fRA!!\u0013\u0002L\u0005\u0011\u0011n\u001c\u0006\u0003\u0003\u001b\nAA[1wC&\u0019\u0011'a\u0012\u0002\rqJg.\u001b;?)\t\ty$A\u0005u_\u001a\u000b7\r^8ssVA\u0011\u0011LA2\u0003O\nY\u0007\u0006\u0003\u0002\\\u0005uD\u0003BA/\u0003o\u0002r!ZA\u0014\u0003?\nI\u0007\u0005\u0004*S\u0006\u0005\u0014Q\r\t\u0004y\u0005\rD!B'\u000b\u0005\u0004\u0001\u0005c\u0001\u001f\u0002h\u0011)\u0001K\u0003b\u0001\u0001B9A(a\u001b\u0002b\u0005\u0015DA\u0002 \u000b\u0005\u0004\ti'F\u0003A\u0003_\n\u0019\bB\u0004\u0002r\u0005-$\u0019\u0001!\u0003\u000b}#CeM\u001b\u0005\u000f\u0005U\u00141\u000eb\u0001\u0001\n)q\f\n\u00134m!I\u0011\u0011\u0010\u0006\u0002\u0002\u0003\u000f\u00111P\u0001\fKZLG-\u001a8dK\u0012\"\u0014\u0007\u0005\u0003.)\u0006\u0005\u0004bBA@\u0015\u0001\u0007\u0011\u0011Q\u0001\bM\u0006\u001cGo\u001c:z!\u0011)\u0007!a!\u0011\u0007q\nYGA\u0005U_\u001a\u000b7\r^8ssVA\u0011\u0011RAI\u0003+\u000bIjE\u0003\fQ\u0005-E\u0006E\u0004f\u0003O\ti)a&\u0011\r%J\u0017qRAJ!\ra\u0014\u0011\u0013\u0003\u0006\u001b.\u0011\r\u0001\u0011\t\u0004y\u0005UE!\u0002)\f\u0005\u0004\u0001\u0005c\u0002\u001f\u0002\u001a\u0006=\u00151\u0013\u0003\u0007}-\u0011\r!a'\u0016\u000b\u0001\u000bi*!)\u0005\u000f\u0005}\u0015\u0011\u0014b\u0001\u0001\n)q\f\n\u00134o\u00119\u00111UAM\u0005\u0004\u0001%!B0%IMB\u0004\u0003B3\u0001\u0003O\u00032\u0001PAM\u0003-)g/\u001b3f]\u000e,G\u0005\u000e\u001a\u0011\t5\"\u0016q\u0012\u000b\u0005\u0003_\u000b9\f\u0006\u0003\u00022\u0006U\u0006#CAZ\u0017\u0005=\u00151SAT\u001b\u0005A\u0001bBAU\u001d\u0001\u000f\u00111\u0016\u0005\b\u0003\u007fr\u0001\u0019AAS\u000311'o\\7Ta\u0016\u001c\u0017NZ5d)\u0011\t9*!0\t\r\r|\u0001\u0019AA`!\u0011)g-!$\u0016\u0005\u0005\r\u0007\u0003CA\u0002\u0003\u0013\ti)a&)\u000f-\t9-!4\u0002PB\u0019\u0011&!3\n\u0007\u0005-7E\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1!A\u0006u_\n+\u0018\u000e\u001c3Ge>lW\u0003CAk\u0003G\f9/a;\u0015\t\u0005]\u0017Q \u000b\u0005\u00033\f9\u0010\u0005\u0005f\u00037$\u0015q\\Au\u0013\r\ti.\t\u0002\n\u0005VLG\u000e\u001a$s_6\u0004b!K5\u0002b\u0006\u0015\bc\u0001\u001f\u0002d\u0012)Q*\u0005b\u0001\u0001B\u0019A(a:\u0005\u000bA\u000b\"\u0019\u0001!\u0011\u000fq\nY/!9\u0002f\u00121a(\u0005b\u0001\u0003[,R\u0001QAx\u0003g$q!!=\u0002l\n\u0007\u0001IA\u0003`I\u0011\u001a\u0014\bB\u0004\u0002v\u0006-(\u0019\u0001!\u0003\u000b}#C\u0005\u000e\u0019\t\u0013\u0005e\u0018#!AA\u0004\u0005m\u0018aC3wS\u0012,gnY3%iM\u0002B!\f+\u0002b\"9\u0011qP\tA\u0002\u0005}\b\u0003B3\u0001\u0005\u0003\u00012\u0001PAv\u0005m\u0019vN\u001d;fI6\u000b\u0007OR1di>\u0014\u0018\u0010V8Ck&dGM\u0012:p[VA!q\u0001B\b\u0005'\u00119b\u0005\u0003\u0013Q\t%\u0001\u0003C3\u0002\\\u0012\u0013YA!\u0006\u0011\r%J'Q\u0002B\t!\ra$q\u0002\u0003\u0006\u001bJ\u0011\r\u0001\u0011\t\u0004y\tMA!\u0002)\u0013\u0005\u0004\u0001\u0005c\u0002\u001f\u0003\u0018\t5!\u0011\u0003\u0003\u0007}I\u0011\rA!\u0007\u0016\u000b\u0001\u0013YBa\b\u0005\u000f\tu!q\u0003b\u0001\u0001\n)q\f\n\u00135c\u00119!\u0011\u0005B\f\u0005\u0004\u0001%!B0%IQ\u0012\u0004\u0003B3\u0001\u0005K\u00012\u0001\u0010B\f\u0003-)g/\u001b3f]\u000e,G\u0005\u000e\u001b\u0011\t5\"&Q\u0002\u000b\u0005\u0005[\u0011\u0019\u0004\u0006\u0003\u00030\tE\u0002#CAZ%\t5!\u0011\u0003B\u0013\u0011\u001d\u00119#\u0006a\u0002\u0005SAq!a \u0016\u0001\u0004\u0011\u0019\u0003\u0006\u0003\u00038\tuB\u0003\u0002B\u000b\u0005sAaa\u0019\fA\u0002\tm\u0002\u0003B3g\u0005\u0017AQA\u0016\fA\u0002\u0011#BA!\u0011\u0003DAA\u00111AA\u0005\u0005\u0017\u0011)\u0002C\u0003W/\u0001\u0007AI\u0001\u0005EK2,w-\u0019;f+\u0011\u0011IEa\u0014\u0014\taA#1\n\t\u0005K\u0002\u0011i\u0005E\u0002=\u0005\u001f\"aA\u0010\rC\u0002\tES#\u0002!\u0003T\t]Ca\u0002B+\u0005\u001f\u0012\r\u0001\u0011\u0002\u0006?\u0012\"Cg\r\u0003\b\u00053\u0012yE1\u0001A\u0005\u0015yF\u0005\n\u001b5\u0003!!W\r\\3hCR,G\u0003\u0002B0\u0005C\u0002R!a-\u0019\u0005\u001bBqAa\u0017\u001b\u0001\u0004\u0011Y%\u0006\u0004\u0003f\t5$\u0011\u000f\u000b\u0005\u0005O\u0012I\b\u0006\u0003\u0003j\tM\u0004c\u0002\u001f\u0003P\t-$q\u000e\t\u0004y\t5D!B'\u001c\u0005\u0004\u0001\u0005c\u0001\u001f\u0003r\u0011)\u0001k\u0007b\u0001\u0001\"I!QO\u000e\u0002\u0002\u0003\u000f!qO\u0001\fKZLG-\u001a8dK\u0012\"T\u0007\u0005\u0003.)\n-\u0004B\u0002=\u001c\u0001\u0004\u0011Y\b\u0005\u0003*u\nu\u0004CB\u0015j\u0005W\u0012y'\u0006\u0004\u0003\u0002\n%%Q\u0012\u000b\u0005\u0005\u0007\u0013)\n\u0006\u0003\u0003\u0006\n=\u0005c\u0002\u001f\u0003P\t\u001d%1\u0012\t\u0004y\t%E!B'\u001d\u0005\u0004\u0001\u0005c\u0001\u001f\u0003\u000e\u0012)\u0001\u000b\bb\u0001\u0001\"I!\u0011\u0013\u000f\u0002\u0002\u0003\u000f!1S\u0001\fKZLG-\u001a8dK\u0012\"d\u0007\u0005\u0003.)\n\u001d\u0005BB2\u001d\u0001\u0004\u00119\n\u0005\u0003fM\ne\u0005CB\u0015j\u0005\u000f\u0013Y)\u0006\u0004\u0003\u001e\n\r&q\u0015\u000b\u0005\u0005?\u0013I\u000bE\u0004=\u0005\u001f\u0012\tK!*\u0011\u0007q\u0012\u0019\u000bB\u0003N;\t\u0007\u0001\tE\u0002=\u0005O#Q\u0001U\u000fC\u0002\u0001C\u0011Ba+\u001e\u0003\u0003\u0005\u001dA!,\u0002\u0017\u00154\u0018\u000eZ3oG\u0016$Cg\u000e\t\u0005[Q\u0013\t+\u0006\u0004\u00032\ne&Q\u0018\u000b\u0005\u0005g\u0013\t\r\u0005\u0005\u0002\u0004\u0005%!Q\u0017B`!\u0019I\u0013Na.\u0003<B\u0019AH!/\u0005\u000b5s\"\u0019\u0001!\u0011\u0007q\u0012i\fB\u0003Q=\t\u0007\u0001\tE\u0004=\u0005\u001f\u00129La/\t\u0013\t\rg$!AA\u0004\t\u0015\u0017aC3wS\u0012,gnY3%ia\u0002B!\f+\u00038\":\u0001$a2\u0002N\u0006=\u0017\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001Bg!\u0011\u0011yM!6\u000e\u0005\tE'\u0002\u0002Bj\u0003\u0017\nA\u0001\\1oO&!!q\u001bBi\u0005\u0019y%M[3di\u0002")
public interface SortedMapFactory<CC>
extends Serializable {
    public static <K, V, CC> BuildFrom<Object, Tuple2<K, V>, CC> toBuildFrom(SortedMapFactory<CC> factory2, Ordering<K> evidence$43) {
        return new SortedMapFactoryToBuildFrom(factory2, evidence$43);
    }

    public static <K, V, CC> Factory<Tuple2<K, V>, CC> toFactory(SortedMapFactory<CC> factory2, Ordering<K> evidence$41) {
        return new ToFactory(factory2, evidence$41);
    }

    public <K, V> CC empty(Ordering<K> var1);

    public <K, V> CC from(IterableOnce<Tuple2<K, V>> var1, Ordering<K> var2);

    public static /* synthetic */ Object apply$(SortedMapFactory $this, Seq elems, Ordering evidence$38) {
        return $this.apply(elems, evidence$38);
    }

    default public <K, V> CC apply(Seq<Tuple2<K, V>> elems, Ordering<K> evidence$38) {
        return this.from(elems, evidence$38);
    }

    public <K, V> Builder<Tuple2<K, V>, CC> newBuilder(Ordering<K> var1);

    default public <K, V> Factory<Tuple2<K, V>, CC> sortedMapFactory(Ordering<K> evidence$40) {
        return new ToFactory(this, evidence$40);
    }

    public static void $init$(SortedMapFactory $this) {
    }

    public static class Delegate<CC>
    implements SortedMapFactory<CC> {
        private static final long serialVersionUID = 3L;
        private final SortedMapFactory<CC> delegate;

        @Override
        public <K, V> Factory<Tuple2<K, V>, CC> sortedMapFactory(Ordering<K> evidence$40) {
            return ((SortedMapFactory)this).sortedMapFactory(evidence$40);
        }

        @Override
        public <K, V> CC apply(Seq<Tuple2<K, V>> elems, Ordering<K> evidence$45) {
            return this.delegate.apply(elems, evidence$45);
        }

        @Override
        public <K, V> CC from(IterableOnce<Tuple2<K, V>> it, Ordering<K> evidence$46) {
            return this.delegate.from(it, evidence$46);
        }

        @Override
        public <K, V> CC empty(Ordering<K> evidence$47) {
            return this.delegate.empty(evidence$47);
        }

        @Override
        public <K, V> Builder<Tuple2<K, V>, CC> newBuilder(Ordering<K> evidence$48) {
            return this.delegate.newBuilder(evidence$48);
        }

        public Delegate(SortedMapFactory<CC> delegate) {
            this.delegate = delegate;
        }
    }

    private static class SortedMapFactoryToBuildFrom<K, V, CC>
    implements BuildFrom<Object, Tuple2<K, V>, CC> {
        private final SortedMapFactory<CC> factory;
        private final Ordering<K> evidence$44;

        @Override
        public Builder<Tuple2<K, V>, CC> apply(Object from) {
            return BuildFrom.apply$(this, from);
        }

        @Override
        public Factory<Tuple2<K, V>, CC> toFactory(Object from) {
            return BuildFrom.toFactory$(this, from);
        }

        @Override
        public CC fromSpecific(Object from, IterableOnce<Tuple2<K, V>> it) {
            return this.factory.from(it, this.evidence$44);
        }

        @Override
        public Builder<Tuple2<K, V>, CC> newBuilder(Object from) {
            return this.factory.newBuilder(this.evidence$44);
        }

        public SortedMapFactoryToBuildFrom(SortedMapFactory<CC> factory2, Ordering<K> evidence$44) {
            this.factory = factory2;
            this.evidence$44 = evidence$44;
        }
    }

    private static class ToFactory<K, V, CC>
    implements Factory<Tuple2<K, V>, CC>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final SortedMapFactory<CC> factory;
        private final Ordering<K> evidence$42;

        @Override
        public CC fromSpecific(IterableOnce<Tuple2<K, V>> it) {
            return this.factory.from(it, this.evidence$42);
        }

        @Override
        public Builder<Tuple2<K, V>, CC> newBuilder() {
            return this.factory.newBuilder(this.evidence$42);
        }

        public ToFactory(SortedMapFactory<CC> factory2, Ordering<K> evidence$42) {
            this.factory = factory2;
            this.evidence$42 = evidence$42;
        }
    }
}

