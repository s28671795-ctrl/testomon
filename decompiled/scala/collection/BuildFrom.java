/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Tuple2;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.BuildFrom$;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Map;
import scala.collection.SortedMap;
import scala.collection.SortedSet;
import scala.collection.View;
import scala.collection.View$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.WrappedString;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\tmba\u0002\n\u0014!\u0003\r\t\u0001\u0007\u0005\u0006=\u0001!\ta\b\u0005\u0006G\u00011\t\u0001\n\u0005\u0006y\u00011\t!\u0010\u0005\u0006\u000b\u0002!\tA\u0012\u0005\u0006-\u0002!\taV\u0004\u0006MNA\ta\u001a\u0004\u0006%MA\t\u0001\u001b\u0005\u0006_\u001e!\t\u0001\u001d\u0005\u0006c\u001e!\u0019A\u001d\u0005\b\u0003{9A1AA \u0011\u001d\tyj\u0002C\u0002\u0003CC\u0011\"a1\b\u0005\u0004%\u0019!!2\t\u0011\u0005}w\u0001)A\u0005\u0003\u000fD\u0011\"!9\b\u0005\u0004%\u0019!a9\t\u0011\u0005Mx\u0001)A\u0005\u0003KDq!!>\b\t\u0007\t9\u0010C\u0004\u0003\"\u001d!\u0019Aa\t\u0003\u0013\t+\u0018\u000e\u001c3Ge>l'B\u0001\u000b\u0016\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002-\u0005)1oY1mC\u000e\u0001Q\u0003B\r;k!\u001a\"\u0001\u0001\u000e\u0011\u0005maR\"A\u000b\n\u0005u)\"aA!os\u00061A%\u001b8ji\u0012\"\u0012\u0001\t\t\u00037\u0005J!AI\u000b\u0003\tUs\u0017\u000e^\u0001\rMJ|Wn\u00159fG&4\u0017n\u0019\u000b\u0003K]\"\"A\n\u0018\u0011\u0005\u001dBC\u0002\u0001\u0003\u0007S\u0001!)\u0019\u0001\u0016\u0003\u0003\r\u000b\"a\u000b\u000e\u0011\u0005ma\u0013BA\u0017\u0016\u0005\u001dqu\u000e\u001e5j]\u001eDQa\f\u0002A\u0002A\n!!\u001b;\u0011\u0007E\u0012D'D\u0001\u0014\u0013\t\u00194C\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002(k\u00111a\u0007\u0001EC\u0002)\u0012\u0011!\u0011\u0005\u0006q\t\u0001\r!O\u0001\u0005MJ|W\u000e\u0005\u0002(u\u001111\b\u0001EC\u0002)\u0012AA\u0012:p[\u0006Qa.Z<Ck&dG-\u001a:\u0015\u0005y\"\u0005\u0003B Ci\u0019j\u0011\u0001\u0011\u0006\u0003\u0003N\tq!\\;uC\ndW-\u0003\u0002D\u0001\n9!)^5mI\u0016\u0014\b\"\u0002\u001d\u0004\u0001\u0004I\u0014!B1qa2LHC\u0001 H\u0011\u0015AD\u00011\u0001:Q\t!\u0011\n\u0005\u0002\u001c\u0015&\u00111*\u0006\u0002\u0007S:d\u0017N\\3)\r\u0011i\u0005+U*U!\tYb*\u0003\u0002P+\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n!+A\u0012Vg\u0016\u0004c.Z<Ck&dG-\u001a:)S\u0001Jgn\u001d;fC\u0012\u0004sN\u001a\u0011baBd\u0017\u0010K\u0015\u0002\u000bMLgnY3\"\u0003U\u000baA\r\u00182g9\u0002\u0014!\u0003;p\r\u0006\u001cGo\u001c:z)\tA6\f\u0005\u000323R2\u0013B\u0001.\u0014\u0005\u001d1\u0015m\u0019;pefDQ\u0001O\u0003A\u0002eBC\u0001A/dIB\u0011a,Y\u0007\u0002?*\u0011\u0001-F\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00012`\u0005AIW\u000e\u001d7jG&$hj\u001c;G_VtG-A\u0002ng\u001e\f\u0013!Z\u0001m\u0007\u0006tgn\u001c;!G>t7\u000f\u001e:vGR\u0004\u0013\rI2pY2,7\r^5p]\u0002zg\r\t;za\u0016\u0004Ce_\"~A]LG\u000f\u001b\u0011fY\u0016lWM\u001c;tA=4\u0007\u0005^=qK\u0002\"30Q?!E\u0006\u001cX\r\u001a\u0011p]\u0002\n\u0007eY8mY\u0016\u001cG/[8oA=4\u0007\u0005^=qK\u0002\"3P\u0012:p[vt\u0013!\u0003\"vS2$gI]8n!\t\ttaE\u0002\bS2\u0004\"a\u00076\n\u0005-,\"AB!osJ+g\r\u0005\u00022[&\u0011an\u0005\u0002\u0016\u0005VLG\u000e\u001a$s_6dun\u001e)sS>\u0014\u0018\u000e^=2\u0003\u0019a\u0014N\\5u}Q\tq-A\bck&dGM\u0012:p[6\u000b\u0007o\u00149t+)\u0019h/a\b\u0002&\u0005E\u0012qG\u000b\u0002iB9\u0011\u0007A;\u0002*\u0005m\u0002CB\u0014w\u0003;\t\u0019\u0003B\u0003x\u0013\t\u0007\u0001P\u0001\u0002D\u0007V)\u00110a\u0001\u0002\nE\u00111F\u001f\n\u0005wv\fiA\u0002\u0003}\u000f\u0001Q(\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004CB\u0019\u007f\u0003\u0003\t9!\u0003\u0002\u0000'\t\u0019Q*\u00199\u0011\u0007\u001d\n\u0019\u0001\u0002\u0004\u0002\u0006Y\u0014\rA\u000b\u0002\u00021B\u0019q%!\u0003\u0005\r\u0005-aO1\u0001+\u0005\u0005I\u0006\u0007BA\b\u00033\u00012\"MA\t\u0003\u0003\t9!!\u0006\u0002\u0018%\u0019\u00111C\n\u0003\r5\u000b\u0007o\u00149t!\t9c\u000fE\u0002(\u00033!!\"a\u0007w\u0003\u0003\u0005\tQ!\u0001+\u0005\ryF%\r\t\u0004O\u0005}AABA\u0011\u0013\t\u0007!F\u0001\u0002LaA\u0019q%!\n\u0005\r\u0005\u001d\u0012B1\u0001+\u0005\t1\u0006\u0007E\u0004\u001c\u0003W\ty#!\u000e\n\u0007\u00055RC\u0001\u0004UkBdWM\r\t\u0004O\u0005EBABA\u001a\u0013\t\u0007!FA\u0001L!\r9\u0013q\u0007\u0003\u0007\u0003sI!\u0019\u0001\u0016\u0003\u0003Y\u0003ba\n<\u00020\u0005U\u0012!\u00062vS2$gI]8n'>\u0014H/\u001a3NCB|\u0005o]\u000b\r\u0003\u0003\n9%!\u001d\u0002v\u0005m\u0014q\u0010\u000b\u0005\u0003\u0007\n\u0019\t\u0005\u00052\u0001\u0005\u0015\u0013qOAA!\u001d9\u0013qIA8\u0003g\"aa\u001e\u0006C\u0002\u0005%SCBA&\u00033\ni&E\u0002,\u0003\u001b\u0012b!a\u0014\u0002R\u0005}c!\u0002?\b\u0001\u00055\u0003cB\u0019\u0002T\u0005]\u00131L\u0005\u0004\u0003+\u001a\"!C*peR,G-T1q!\r9\u0013\u0011\f\u0003\b\u0003\u000b\t9E1\u0001+!\r9\u0013Q\f\u0003\b\u0003\u0017\t9E1\u0001+a\u0011\t\t'a\u001b\u0011\u0017E\n\u0019'a\u0016\u0002\\\u0005\u001d\u0014\u0011N\u0005\u0004\u0003K\u001a\"\u0001D*peR,G-T1q\u001fB\u001c\bcA\u0014\u0002HA\u0019q%a\u001b\u0005\u0017\u00055\u0014qIA\u0001\u0002\u0003\u0015\tA\u000b\u0002\u0004?\u0012\"\u0004cA\u0014\u0002r\u00111\u0011\u0011\u0005\u0006C\u0002)\u00022aJA;\t\u0019\t9C\u0003b\u0001UA91$a\u000b\u0002z\u0005u\u0004cA\u0014\u0002|\u00111\u00111\u0007\u0006C\u0002)\u00022aJA@\t\u0019\tID\u0003b\u0001UA9q%a\u0012\u0002z\u0005u\u0004\"CAC\u0015\u0005\u0005\t9AAD\u0003))g/\u001b3f]\u000e,G%\r\t\u0007\u0003\u0013\u000bI*!\u001f\u000f\t\u0005-\u0015Q\u0013\b\u0005\u0003\u001b\u000b\u0019*\u0004\u0002\u0002\u0010*\u0019\u0011\u0011S\f\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0012bAAL+\u00059\u0001/Y2lC\u001e,\u0017\u0002BAN\u0003;\u0013\u0001b\u0014:eKJLgn\u001a\u0006\u0004\u0003/+\u0012a\u00042vS2$gI]8n\u0005&$8+\u001a;\u0016\t\u0005\r\u0016\u0011V\u000b\u0003\u0003K\u0003\u0002\"\r\u0001\u0002(\u0006u\u0016q\u0015\t\u0004O\u0005%FAB\u0015\f\u0005\u0004\tY+E\u0002,\u0003[\u0013b!a,\u00022\u0006]f!\u0002?\b\u0001\u00055\u0006cA\u0019\u00024&\u0019\u0011QW\n\u0003\r\tKGoU3u!\u0015\t\u0014\u0011XAT\u0013\r\tYl\u0005\u0002\n\u0005&$8+\u001a;PaN\u00042aGA`\u0013\r\t\t-\u0006\u0002\u0004\u0013:$\u0018a\u00042vS2$gI]8n'R\u0014\u0018N\\4\u0016\u0005\u0005\u001d\u0007\u0003C\u0019\u0001\u0003\u0013\fI.!3\u0011\t\u0005-\u00171\u001b\b\u0005\u0003\u001b\fy\rE\u0002\u0002\u000eVI1!!5\u0016\u0003\u0019\u0001&/\u001a3fM&!\u0011Q[Al\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011[\u000b\u0011\u0007m\tY.C\u0002\u0002^V\u0011Aa\u00115be\u0006\u0001\"-^5mI\u001a\u0013x.\\*ue&tw\rI\u0001\u0017EVLG\u000e\u001a$s_6<&/\u00199qK\u0012\u001cFO]5oOV\u0011\u0011Q\u001d\t\tc\u0001\t9/!7\u0002hB!\u0011\u0011^Ax\u001b\t\tYOC\u0002\u0002nN\t\u0011\"[7nkR\f'\r\\3\n\t\u0005E\u00181\u001e\u0002\u000e/J\f\u0007\u000f]3e'R\u0014\u0018N\\4\u0002/\t,\u0018\u000e\u001c3Ge>lwK]1qa\u0016$7\u000b\u001e:j]\u001e\u0004\u0013A\u00042vS2$gI]8n\u0003J\u0014\u0018-_\u000b\u0005\u0003s\u0014i\u0001\u0006\u0003\u0002|\nE\u0001\u0003C\u0019\u0001\u0003{\u0014YAa\u00041\t\u0005}(q\u0001\t\u00067\t\u0005!QA\u0005\u0004\u0005\u0007)\"!B!se\u0006L\bcA\u0014\u0003\b\u0011Q!\u0011\u0002\t\u0002\u0002\u0003\u0005)\u0011\u0001\u0016\u0003\u0007}#s\u0007E\u0002(\u0005\u001b!QA\u000e\tC\u0002)\u0002Ra\u0007B\u0001\u0005\u0017A\u0011Ba\u0005\u0011\u0003\u0003\u0005\u001dA!\u0006\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$#\u0007\u0005\u0004\u0003\u0018\tu!1B\u0007\u0003\u00053Q1Aa\u0007\u0016\u0003\u001d\u0011XM\u001a7fGRLAAa\b\u0003\u001a\tA1\t\\1tgR\u000bw-A\u0007ck&dGM\u0012:p[ZKWm^\u000b\u0007\u0005K\u0011\tD!\u000e\u0016\u0005\t\u001d\u0002\u0003C\u0019\u0001\u0005S\u0011\u0019D!\u000f\u0011\u000bE\u0012YCa\f\n\u0007\t52C\u0001\u0003WS\u0016<\bcA\u0014\u00032\u0011)a'\u0005b\u0001UA\u0019qE!\u000e\u0005\r\t]\u0012C1\u0001+\u0005\u0005\u0011\u0005#B\u0019\u0003,\tM\u0002")
public interface BuildFrom<From, A, C> {
    public static <A, B> BuildFrom<View<A>, B, View<B>> buildFromView() {
        return new BuildFrom<View<A>, B, View<B>>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public View<B> fromSpecific(View<A> from, IterableOnce<B> it) {
                return View$.MODULE$.from((IterableOnce)it);
            }

            public Builder<B, View<B>> newBuilder(View<A> from) {
                return View$.MODULE$.newBuilder();
            }
        };
    }

    public static <A> BuildFrom<Object, A, Object> buildFromArray(ClassTag<A> evidence$2) {
        return new BuildFrom<Object, A, Object>(evidence$2){
            private final ClassTag evidence$2$1;

            public Builder<A, Object> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, Object> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Object fromSpecific(Object from, IterableOnce<A> it) {
                ClassTag arrayFactory_evidence$1 = this.evidence$2$1;
                Object var3_3 = null;
                return new Factory.ArrayFactory<A>(arrayFactory_evidence$1).fromSpecific(it);
            }

            public Builder<A, Object> newBuilder(Object from) {
                ClassTag arrayFactory_evidence$1 = this.evidence$2$1;
                Object var2_2 = null;
                return new Factory.ArrayFactory<A>(arrayFactory_evidence$1).newBuilder();
            }
            {
                this.evidence$2$1 = evidence$2$1;
            }
        };
    }

    public static BuildFrom<WrappedString, Object, WrappedString> buildFromWrappedString() {
        return BuildFrom$.MODULE$.buildFromWrappedString();
    }

    public static BuildFrom<String, Object, String> buildFromString() {
        return BuildFrom$.MODULE$.buildFromString();
    }

    public static <C extends BitSet & BitSetOps<C>> BuildFrom<C, Object, C> buildFromBitSet() {
        return new BuildFrom<C, Object, C>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public C fromSpecific(C from, IterableOnce<Object> it) {
                return (C)((BitSet)from.bitSetFactory().fromSpecific(it));
            }

            public Builder<Object, C> newBuilder(C from) {
                return from.bitSetFactory().newBuilder();
            }
        };
    }

    public static <CC extends SortedMap<Object, Object>, K0, V0, K, V> BuildFrom<CC, Tuple2<K, V>, CC> buildFromSortedMapOps(Ordering<K> evidence$1) {
        return new BuildFrom<CC, Tuple2<K, V>, CC>(evidence$1){
            private final Ordering evidence$1$1;

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(CC from) {
                return from.sortedMapFactory().newBuilder(this.evidence$1$1);
            }

            public CC fromSpecific(CC from, IterableOnce<Tuple2<K, V>> it) {
                return (CC)((SortedMap)from.sortedMapFactory().from(it, this.evidence$1$1));
            }
            {
                this.evidence$1$1 = evidence$1$1;
            }
        };
    }

    public static <CC extends Map<Object, Object>, K0, V0, K, V> BuildFrom<CC, Tuple2<K, V>, CC> buildFromMapOps() {
        return new BuildFrom<CC, Tuple2<K, V>, CC>(){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(CC from) {
                return from.mapFactory().newBuilder();
            }

            public CC fromSpecific(CC from, IterableOnce<Tuple2<K, V>> it) {
                return (CC)((Map)from.mapFactory().from(it));
            }
        };
    }

    public static <A> BuildFrom<String, A, IndexedSeq<A>> fallbackStringCanBuildFrom() {
        return new BuildFrom<String, A, IndexedSeq<A>>(null){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public IndexedSeq<A> fromSpecific(String from, IterableOnce<A> it) {
                return IndexedSeq$.MODULE$.from((IterableOnce)it);
            }

            public Builder<A, IndexedSeq<A>> newBuilder(String from) {
                return IndexedSeq$.MODULE$.newBuilder();
            }
        };
    }

    public static <CC extends SortedSet<Object>, A0, A> BuildFrom<CC, A, CC> buildFromSortedSetOps(Ordering<A> evidence$3) {
        return new BuildFrom<CC, A, CC>(null, evidence$3){
            private final Ordering evidence$3$1;

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<A, CC> newBuilder(CC from) {
                return from.sortedIterableFactory().newBuilder(this.evidence$3$1);
            }

            public CC fromSpecific(CC from, IterableOnce<A> it) {
                return (CC)((SortedSet)from.sortedIterableFactory().from(it, this.evidence$3$1));
            }
            {
                this.evidence$3$1 = evidence$3$1;
            }
        };
    }

    public static <A> BuildFrom<Iterator<?>, A, Iterator<A>> buildFromIterator() {
        return new BuildFrom<Iterator<?>, A, Iterator<A>>(null){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<A, Iterator<A>> newBuilder(Iterator<?> from) {
                return new ImmutableBuilder<A, Iterator<A>>(){

                    /*
                     * WARNING - void declaration
                     */
                    public Iterator$.anon.21 addOne(A elem) {
                        void $plus$plus_xs;
                        Iterator iterator2 = (Iterator)this.elems();
                        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> Iterator$.MODULE$.single(elem);
                        if (iterator2 == null) {
                            throw null;
                        }
                        intersect = null;
                        this.elems_$eq(iterator2.concat($plus$plus_xs));
                        return this;
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$addOne$1(java.lang.Object )}, serializedLambda);
                    }
                };
            }

            public Iterator<A> fromSpecific(Iterator<?> from, IterableOnce<A> it) {
                return it.iterator();
            }
        };
    }

    public static <CC extends Iterable<Object>, A0, A> BuildFrom<CC, A, CC> buildFromIterableOps() {
        return new BuildFrom<CC, A, CC>(null){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<A, CC> newBuilder(CC from) {
                return from.iterableFactory().newBuilder();
            }

            public CC fromSpecific(CC from, IterableOnce<A> it) {
                return (CC)((Iterable)from.iterableFactory().from(it));
            }
        };
    }

    public C fromSpecific(From var1, IterableOnce<A> var2);

    public Builder<A, C> newBuilder(From var1);

    public static /* synthetic */ Builder apply$(BuildFrom $this, Object from) {
        return $this.apply(from);
    }

    default public Builder<A, C> apply(From from) {
        return this.newBuilder(from);
    }

    public static /* synthetic */ Factory toFactory$(BuildFrom $this, Object from) {
        return $this.toFactory(from);
    }

    default public Factory<A, C> toFactory(From from) {
        return new Factory<A, C>(this, from){
            private final /* synthetic */ BuildFrom $outer;
            private final Object from$1;

            public C fromSpecific(IterableOnce<A> it) {
                return this.$outer.fromSpecific(this.from$1, it);
            }

            public Builder<A, C> newBuilder() {
                return this.$outer.newBuilder(this.from$1);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.from$1 = from$1;
            }
        };
    }

    public static void $init$(BuildFrom $this) {
    }
}

