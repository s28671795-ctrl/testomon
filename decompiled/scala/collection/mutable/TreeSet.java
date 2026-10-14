/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSetOps;
import scala.collection.StrictOptimizedSortedSetOps;
import scala.collection.View;
import scala.collection.convert.impl.AnyBinaryTreeStepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.DoubleBinaryTreeStepper;
import scala.collection.convert.impl.IntBinaryTreeStepper;
import scala.collection.convert.impl.LongBinaryTreeStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.RedBlackTree;
import scala.collection.mutable.RedBlackTree$;
import scala.collection.mutable.RedBlackTree$Tree$;
import scala.collection.mutable.ReusableBuilder;
import scala.collection.mutable.SetOps;
import scala.collection.mutable.SortedSet;
import scala.collection.mutable.TreeSet$;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\t\rg\u0001B\u001b7!uB\u0001B\u001b\u0001\u0003\u0006\u0004%Ia\u001b\u0005\tm\u0002\u0011\t\u0011)A\u0005Y\"Aq\u000f\u0001BC\u0002\u0013\r\u0001\u0010C\u0005\u0002\u0002\u0001\u0011\t\u0011)A\u0005s\"9\u00111\u0001\u0001\u0005\n\u0005\u0015\u0001bBA\u0002\u0001\u0011\u0005\u0011Q\u0002\u0005\b\u0003+\u0001A\u0011IA\f\u0011\u001d\ty\u0002\u0001C\u0001\u0003CAq!!\u000b\u0001\t\u0003\tY\u0003C\u0004\u00022\u0001!\t%a\r\t\u000f\u0005e\u0004\u0001\"\u0001\u0002|!9\u00111\u0011\u0001\u0005\u0002\u0005\u0015\u0005bBAE\u0001\u0011\u0005\u00111\u0012\u0005\b\u0003'\u0003A\u0011AAK\u0011\u001d\ty\n\u0001C\u0001\u0003CCq!a*\u0001\t\u0003\tI\u000b\u0003\u0005\u0002:\u0002\u0001K\u0011KA^\u0011\u001d\ti\r\u0001C!\u0003\u001fDq!a6\u0001\t\u0003\ny\rC\u0004\u0002Z\u0002!\t%a7\t\u000f\u0005u\u0007\u0001\"\u0011\u0002`\"9\u0011\u0011\u001d\u0001\u0005B\u0005}\u0007bBAr\u0001\u0011\u0005\u0013Q\u001d\u0005\b\u0003W\u0004A\u0011IAw\u0011\u001d\t\t\u0010\u0001C!\u0003g4\u0001Ba\u0002\u0001A\u00035!\u0011\u0002\u0005\u000b\u0003[S\"\u0011!Q\u0001\n\u0005=\u0006BCA\\5\t\u0005\t\u0015!\u0003\u00020\"9\u00111\u0001\u000e\u0005\u0002\t-\u0001\u0002\u0003B\n5\u0001&IA!\u0006\t\u0011\tm!\u0004)C\u0005\u0005;A\u0001Ba\t\u001bA\u0013%!Q\u0005\u0005\b\u0003OSB\u0011\tB\u0015\u0011\u001d\t\u0019J\u0007C!\u0005_Aq!a\b\u001b\t\u0003\n\t\u0003C\u0004\u0002*i!\tEa\r\t\u000f\u00055'\u0004\"\u0011\u0002P\"9\u0011q\u001b\u000e\u0005B\u0005=\u0007bBAm5\u0011\u0005\u00131\u001c\u0005\b\u0003;TB\u0011IAp\u0011\u001d\u00119D\u0007C!\u0005sAq!!9\u001b\t\u0003\ny\u000eC\u0004\u0003<i!\tE!\u000f\t\u000f\u0005E(\u0004\"\u0011\u0003>!9!\u0011\n\u000e\u0005B\t-sa\u0002B(m!\u0005!\u0011\u000b\u0004\u0007kYB\tAa\u0015\t\u000f\u0005\rq\u0006\"\u0001\u0003\\!9!QL\u0018\u0005\u0002\t}\u0003bBAW_\u0011\u0005!q\u000e\u0005\b\u0005\u0017{C\u0011\u0001BG\u0011%\u0011\tkLA\u0001\n\u0013\u0011\u0019KA\u0004Ue\u0016,7+\u001a;\u000b\u0005]B\u0014aB7vi\u0006\u0014G.\u001a\u0006\u0003si\n!bY8mY\u0016\u001cG/[8o\u0015\u0005Y\u0014!B:dC2\f7\u0001A\u000b\u0003}\u0015\u001b\u0002\u0002A P%^s\u0016\r\u001a\t\u0004\u0001\u0006\u001bU\"\u0001\u001c\n\u0005\t3$aC!cgR\u0014\u0018m\u0019;TKR\u0004\"\u0001R#\r\u0001\u0011)a\t\u0001b\u0001\u000f\n\t\u0011)\u0005\u0002I\u0019B\u0011\u0011JS\u0007\u0002u%\u00111J\u000f\u0002\b\u001d>$\b.\u001b8h!\tIU*\u0003\u0002Ou\t\u0019\u0011I\\=\u0011\u0007\u0001\u00036)\u0003\u0002Rm\tI1k\u001c:uK\u0012\u001cV\r\u001e\t\u0006\u0001N\u001bUKV\u0005\u0003)Z\u0012AbU8si\u0016$7+\u001a;PaN\u0004\"\u0001\u0011\u0001\u0011\u0007\u0001\u00031\tE\u0003Y3\u000e[f+D\u00019\u0013\tQ\u0006H\u0001\u000eTiJL7\r^(qi&l\u0017N_3e\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0002A9&\u0011QL\u000e\u0002\u0004'\u0016$\b#\u0002-`\u0007V3\u0016B\u000119\u0005m\u0019FO]5di>\u0003H/[7ju\u0016$7k\u001c:uK\u0012\u001cV\r^(qgB)\u0001LY\"V7&\u00111\r\u000f\u0002\u0019'>\u0014H/\u001a3TKR4\u0015m\u0019;pef$UMZ1vYR\u001c\bCA3i\u001b\u00051'BA49\u0003\u001d9WM\\3sS\u000eL!!\u001b4\u0003'\u0011+g-Y;miN+'/[1mSj\f'\r\\3\u0002\tQ\u0014X-Z\u000b\u0002YB!Q\u000e]\"t\u001d\t\u0001e.\u0003\u0002pm\u0005a!+\u001a3CY\u0006\u001c7\u000e\u0016:fK&\u0011\u0011O\u001d\u0002\u0005)J,WM\u0003\u0002pmA\u0011\u0011\n^\u0005\u0003kj\u0012AAT;mY\u0006)AO]3fA\u0005AqN\u001d3fe&tw-F\u0001z!\rQXp\u0011\b\u0003\u0013nL!\u0001 \u001e\u0002\u000fA\f7m[1hK&\u0011ap \u0002\t\u001fJ$WM]5oO*\u0011APO\u0001\n_J$WM]5oO\u0002\na\u0001P5oSRtD\u0003BA\u0004\u0003\u0017!2AVA\u0005\u0011\u00159X\u0001q\u0001z\u0011\u0015QW\u00011\u0001m)\t\ty\u0001F\u0002W\u0003#Aa!a\u0005\u0007\u0001\bI\u0018aA8sI\u0006)2o\u001c:uK\u0012LE/\u001a:bE2,g)Y2u_JLXCAA\r!\u0011A\u00161D+\n\u0007\u0005u\u0001HA\u000bT_J$X\rZ%uKJ\f'\r\\3GC\u000e$xN]=\u0002\u0011%$XM]1u_J,\"!a\t\u0011\ta\u000b)cQ\u0005\u0004\u0003OA$\u0001C%uKJ\fGo\u001c:\u0002\u0019%$XM]1u_J4%o\\7\u0015\t\u0005\r\u0012Q\u0006\u0005\u0007\u0003_I\u0001\u0019A\"\u0002\u000bM$\u0018M\u001d;\u0002\u000fM$X\r\u001d9feV!\u0011QGA )\u0011\t9$a\u001c\u0013\r\u0005e\u0012QHA*\r\u0019\tY\u0004\u0001\u0001\u00028\taAH]3gS:,W.\u001a8u}A\u0019A)a\u0010\u0005\u000f\u0005\u0005#B1\u0001\u0002D\t\t1+E\u0002I\u0003\u000b\u0002D!a\u0012\u0002PA)\u0001,!\u0013\u0002N%\u0019\u00111\n\u001d\u0003\u000fM#X\r\u001d9feB\u0019A)a\u0014\u0005\u0017\u0005E\u0013qHA\u0001\u0002\u0003\u0015\ta\u0012\u0002\u0004?\u0012\n\u0004\u0003BA+\u0003SrA!a\u0016\u0002f9!\u0011\u0011LA2\u001d\u0011\tY&!\u0019\u000e\u0005\u0005u#bAA0y\u00051AH]8pizJ\u0011aO\u0005\u0003siJ1!a\u001a9\u0003\u001d\u0019F/\u001a9qKJLA!a\u001b\u0002n\tqQI\u001a4jG&,g\u000e^*qY&$(bAA4q!9\u0011\u0011\u000f\u0006A\u0004\u0005M\u0014!B:iCB,\u0007C\u0002-\u0002v\r\u000bi$C\u0002\u0002xa\u0012Ab\u0015;faB,'o\u00155ba\u0016\fa!\u00193e\u001f:,G\u0003BA?\u0003\u007fj\u0011\u0001\u0001\u0005\u0007\u0003\u0003[\u0001\u0019A\"\u0002\t\u0015dW-\\\u0001\fgV\u0014GO]1di>sW\r\u0006\u0003\u0002~\u0005\u001d\u0005BBAA\u0019\u0001\u00071)A\u0003dY\u0016\f'\u000f\u0006\u0002\u0002\u000eB\u0019\u0011*a$\n\u0007\u0005E%H\u0001\u0003V]&$\u0018\u0001C2p]R\f\u0017N\\:\u0015\t\u0005]\u0015Q\u0014\t\u0004\u0013\u0006e\u0015bAANu\t9!i\\8mK\u0006t\u0007BBAA\u001d\u0001\u00071)A\u0007v]\u000e|gn\u001d;sC&tW\rZ\u000b\u0003\u0003G\u0003B\u0001WAS\u0007&\u0011Q\fO\u0001\ne\u0006tw-Z%na2$RAVAV\u0003kCq!!,\u0011\u0001\u0004\ty+\u0001\u0003ge>l\u0007\u0003B%\u00022\u000eK1!a-;\u0005\u0019y\u0005\u000f^5p]\"9\u0011q\u0017\tA\u0002\u0005=\u0016!B;oi&d\u0017!C2mCN\u001ch*Y7f+\t\ti\f\u0005\u0003\u0002@\u0006\u001dg\u0002BAa\u0003\u0007\u00042!a\u0017;\u0013\r\t)MO\u0001\u0007!J,G-\u001a4\n\t\u0005%\u00171\u001a\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005\u0015'(\u0001\u0003tSj,WCAAi!\rI\u00151[\u0005\u0004\u0003+T$aA%oi\u0006I1N\\8x]NK'0Z\u0001\bSN,U\u000e\u001d;z+\t\t9*\u0001\u0003iK\u0006$W#A\"\u0002\t1\f7\u000f^\u0001\t[&t\u0017I\u001a;feR!\u0011qVAt\u0011\u0019\tIo\u0006a\u0001\u0007\u0006\u00191.Z=\u0002\u00135\f\u0007PQ3g_J,G\u0003BAX\u0003_Da!!;\u0019\u0001\u0004\u0019\u0015a\u00024pe\u0016\f7\r[\u000b\u0005\u0003k\u0014\u0019\u0001\u0006\u0003\u0002\u000e\u0006]\bbBA}3\u0001\u0007\u00111`\u0001\u0002MB1\u0011*!@D\u0005\u0003I1!a@;\u0005%1UO\\2uS>t\u0017\u0007E\u0002E\u0005\u0007!aA!\u0002\u001a\u0005\u00049%!A+\u0003#Q\u0013X-Z*fiB\u0013xN[3di&|gn\u0005\u0002\u001b-R1!Q\u0002B\b\u0005#\u00012!! \u001b\u0011\u001d\ti+\ba\u0001\u0003_Cq!a.\u001e\u0001\u0004\ty+\u0001\bqS\u000e\\Gj\\<fe\n{WO\u001c3\u0015\t\u0005=&q\u0003\u0005\b\u00053q\u0002\u0019AAX\u0003\u001dqWm\u001e$s_6\fa\u0002]5dWV\u0003\b/\u001a:C_VtG\r\u0006\u0003\u00020\n}\u0001b\u0002B\u0011?\u0001\u0007\u0011qV\u0001\t]\u0016<XK\u001c;jY\u0006\u0011\u0012n]%og&$WMV5fo\n{WO\u001c3t)\u0011\t9Ja\n\t\r\u0005%\b\u00051\u0001D)\u00151&1\u0006B\u0017\u0011\u001d\ti+\ta\u0001\u0003_Cq!a.\"\u0001\u0004\ty\u000b\u0006\u0003\u0002\u0018\nE\u0002BBAuE\u0001\u00071\t\u0006\u0003\u0002$\tU\u0002BBA\u0018I\u0001\u00071)\u0001\u0006iK\u0006$w\n\u001d;j_:,\"!a,\u0002\u00151\f7\u000f^(qi&|g.\u0006\u0003\u0003@\t\u001dC\u0003BAG\u0005\u0003Bq!!?-\u0001\u0004\u0011\u0019\u0005\u0005\u0004J\u0003{\u001c%Q\t\t\u0004\t\n\u001dCA\u0002B\u0003Y\t\u0007q)A\u0003dY>tW\rF\u0001WS\t\u0001!$A\u0004Ue\u0016,7+\u001a;\u0011\u0005\u0001{3#B\u0018\u0003V\u0005e\u0001cA%\u0003X%\u0019!\u0011\f\u001e\u0003\r\u0005s\u0017PU3g)\t\u0011\t&A\u0003f[B$\u00180\u0006\u0003\u0003b\t\u001dD\u0003\u0002B2\u0005S\u0002B\u0001\u0011\u0001\u0003fA\u0019AIa\u001a\u0005\u000b\u0019\u000b$\u0019A$\t\u0013\t-\u0014'!AA\u0004\t5\u0014AC3wS\u0012,gnY3%cA!!0 B3+\u0011\u0011\tH!\u001f\u0015\t\tM$\u0011\u0011\u000b\u0005\u0005k\u0012i\b\u0005\u0003A\u0001\t]\u0004c\u0001#\u0003z\u00111!1\u0010\u001aC\u0002\u001d\u0013\u0011!\u0012\u0005\u0007oJ\u0002\u001dAa \u0011\til(q\u000f\u0005\b\u0005\u0007\u0013\u0004\u0019\u0001BC\u0003\tIG\u000fE\u0003{\u0005\u000f\u00139(C\u0002\u0003\n~\u0014A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!B\\3x\u0005VLG\u000eZ3s+\u0011\u0011yI!'\u0015\t\tE%Q\u0014\t\b\u0001\nM%q\u0013BN\u0013\r\u0011)J\u000e\u0002\b\u0005VLG\u000eZ3s!\r!%\u0011\u0014\u0003\u0006\rN\u0012\ra\u0012\t\u0005\u0001\u0002\u00119\n\u0003\u0004xg\u0001\u000f!q\u0014\t\u0005uv\u00149*\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003&B!!q\u0015BY\u001b\t\u0011IK\u0003\u0003\u0003,\n5\u0016\u0001\u00027b]\u001eT!Aa,\u0002\t)\fg/Y\u0005\u0005\u0005g\u0013IK\u0001\u0004PE*,7\r\u001e\u0015\b_\t]&Q\u0018B`!\rI%\u0011X\u0005\u0004\u0005wS$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001f\u0002\u0018\u00038\nu&q\u0018")
public class TreeSet<A>
extends AbstractSet<A>
implements SortedSet<A>,
StrictOptimizedSortedSetOps<A, TreeSet, TreeSet<A>>,
DefaultSerializable {
    private final RedBlackTree.Tree<A, Null$> scala$collection$mutable$TreeSet$$tree;
    private final Ordering<A> ordering;

    public static <A> Builder<A, TreeSet<A>> newBuilder(Ordering<A> ordering) {
        return new ReusableBuilder<A, TreeSet<A>>(ordering){
            private RedBlackTree.Tree<A, Null$> tree;
            private final Ordering ordering$1;

            public void sizeHint(int size) {
                Builder.sizeHint$(this, size);
            }

            public final void sizeHint(IterableOnce<?> coll, int delta) {
                Builder.sizeHint$(this, coll, delta);
            }

            public final int sizeHint$default$2() {
                return Builder.sizeHint$default$2$(this);
            }

            public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
                Builder.sizeHintBounded$(this, size, boundingColl);
            }

            public <NewTo> Builder<A, NewTo> mapResult(Function1<TreeSet<A>, NewTo> f) {
                return Builder.mapResult$(this, f);
            }

            public final Growable<A> $plus$eq(A elem) {
                return Growable.$plus$eq$(this, elem);
            }

            public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
                return Growable.$plus$eq$(this, elem1, elem2, elems);
            }

            public Growable<A> addAll(IterableOnce<A> xs) {
                return Growable.addAll$(this, xs);
            }

            public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
                return Growable.$plus$plus$eq$(this, xs);
            }

            public int knownSize() {
                return Growable.knownSize$(this);
            }

            public $anon$1 addOne(A elem) {
                RedBlackTree$.MODULE$.insert(this.tree, elem, null, this.ordering$1);
                return this;
            }

            public TreeSet<A> result() {
                return new TreeSet<A>(this.tree, this.ordering$1);
            }

            public void clear() {
                this.tree = RedBlackTree$Tree$.MODULE$.empty();
            }
            {
                this.ordering$1 = ordering$1;
                this.tree = RedBlackTree$Tree$.MODULE$.empty();
            }
        };
    }

    public static Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(TreeSet$.MODULE$, evidence$13);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        TreeSet$ unfold_this = TreeSet$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from(from_it, (Ordering)evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        TreeSet$ iterate_this = TreeSet$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from(from_it, (Ordering)evidence$10);
    }

    public static Object tabulate(int n, Function1 f, Object evidence$9) {
        TreeSet$ tabulate_this = TreeSet$.MODULE$;
        View.Tabulate from_it = new View.Tabulate(n, f);
        return tabulate_this.from(from_it, (Ordering)evidence$9);
    }

    public static Object fill(int n, Function0 elem, Object evidence$8) {
        TreeSet$ fill_this = TreeSet$.MODULE$;
        View.Fill from_it = new View.Fill(n, elem);
        return fill_this.from(from_it, (Ordering)evidence$8);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public scala.collection.SortedSet map(Function1 f, Ordering ev) {
        return StrictOptimizedSortedSetOps.map$(this, f, ev);
    }

    @Override
    public scala.collection.SortedSet flatMap(Function1 f, Ordering ev) {
        return StrictOptimizedSortedSetOps.flatMap$(this, f, ev);
    }

    @Override
    public scala.collection.SortedSet zip(IterableOnce that, Ordering ev) {
        return StrictOptimizedSortedSetOps.zip$(this, that, ev);
    }

    @Override
    public scala.collection.SortedSet collect(PartialFunction pf, Ordering ev) {
        return StrictOptimizedSortedSetOps.collect$(this, pf, ev);
    }

    @Override
    public scala.collection.SetOps concat(IterableOnce that) {
        return StrictOptimizedSetOps.concat$(this, that);
    }

    @Override
    public Tuple2<TreeSet<A>, TreeSet<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<TreeSet<A>, TreeSet<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>, scala.collection.mutable.Set<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public scala.collection.mutable.Set<A> unsorted() {
        return SortedSet.unsorted$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
        return Set.equals$(this, that);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.SortedSet.stringPrefix$(this);
    }

    @Override
    public boolean equals(Object that) {
        return scala.collection.SortedSet.equals$(this, that);
    }

    @Override
    public scala.collection.SortedSet fromSpecific(IterableOnce coll) {
        return SortedSetFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<A, TreeSet<A>> newSpecificBuilder() {
        return SortedSetFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public scala.collection.SortedSet empty() {
        return SortedSetFactoryDefaults.empty$(this);
    }

    @Override
    public SortedSetOps.WithFilter<A, ?, ?> withFilter(Function1<A, Object> p) {
        return SortedSetFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering ord) {
        return IterableOnceOps.min$(this, ord);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public Iterator<A> keysIteratorFrom(A start2) {
        return SortedSetOps.keysIteratorFrom$(this, start2);
    }

    @Override
    public A firstKey() {
        return (A)SortedSetOps.firstKey$(this);
    }

    @Override
    public A lastKey() {
        return (A)SortedSetOps.lastKey$(this);
    }

    @Override
    public <B> A min(Ordering<B> ord) {
        return (A)SortedSetOps.min$(this, ord);
    }

    @Override
    public <B> A max(Ordering<B> ord) {
        return (A)SortedSetOps.max$(this, ord);
    }

    @Override
    public SortedSetOps rangeTo(Object to) {
        return SortedSetOps.rangeTo$(this, to);
    }

    @Override
    public int compare(A k0, A k1) {
        return SortedOps.compare$(this, k0, k1);
    }

    @Override
    public Object range(Object from, Object until) {
        return SortedOps.range$(this, from, until);
    }

    @Override
    public final Object from(Object from) {
        return SortedOps.from$(this, from);
    }

    @Override
    public Object rangeFrom(Object from) {
        return SortedOps.rangeFrom$(this, from);
    }

    @Override
    public final Object until(Object until) {
        return SortedOps.until$(this, until);
    }

    @Override
    public Object rangeUntil(Object until) {
        return SortedOps.rangeUntil$(this, until);
    }

    @Override
    public final Object to(Object to) {
        return SortedOps.to$(this, to);
    }

    public RedBlackTree.Tree<A, Null$> scala$collection$mutable$TreeSet$$tree() {
        return this.scala$collection$mutable$TreeSet$$tree;
    }

    @Override
    public Ordering<A> ordering() {
        return this.ordering;
    }

    @Override
    public SortedIterableFactory<TreeSet> sortedIterableFactory() {
        return TreeSet$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<A> iterator() {
        void keysIterator_evidence$4;
        void keysIterator_end;
        Ordering<A> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        None$ keysIterator_start = None$.MODULE$;
        RedBlackTree.Tree<A, Null$> keysIterator_tree = this.scala$collection$mutable$TreeSet$$tree();
        return new RedBlackTree.KeysIterator<Nothing$, Null$>((RedBlackTree.Tree<Nothing$, Null$>)keysIterator_tree, keysIterator_start, (Option<Nothing$>)keysIterator_end, (Ordering<Nothing$>)keysIterator_evidence$4);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<A> iteratorFrom(A start2) {
        void keysIterator_evidence$4;
        void keysIterator_end;
        Ordering<A> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        Some<A> keysIterator_start = new Some<A>(start2);
        RedBlackTree.Tree<A, Null$> keysIterator_tree = this.scala$collection$mutable$TreeSet$$tree();
        return new RedBlackTree.KeysIterator<A, Null$>(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        void from_root;
        void from_extract;
        void from_right;
        void from_left;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            void from_right2;
            void from_left2;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$3 -> BoxesRunTime.boxToInteger(TreeSet.$anonfun$stepper$3(x$3));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect2 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$2 -> x$2.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect3 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$1 -> x$1.left();
            RedBlackTree.Node<A, Null$> node = this.scala$collection$mutable$TreeSet$$tree().root();
            int from_maxLength = this.size();
            IntBinaryTreeStepper<Object> from_ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left2, (Function1<Object, Object>)from_right2, (Function1<Object, Object>)from_extract2);
            from_ans.initialize(from_root2, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            void from_right3;
            void from_left3;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$6 -> BoxesRunTime.boxToLong(TreeSet.$anonfun$stepper$6(x$6));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect4 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$5 -> x$5.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect5 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$4 -> x$4.left();
            RedBlackTree.Node<A, Null$> node = this.scala$collection$mutable$TreeSet$$tree().root();
            int from_maxLength = this.size();
            LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left3, (Function1<Object, Object>)from_right3, (Function1<Object, Object>)from_extract3);
            from_ans.initialize(from_root3, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            void from_right4;
            void from_left4;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$9 -> BoxesRunTime.boxToDouble(TreeSet.$anonfun$stepper$9(x$9));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect6 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$8 -> x$8.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect7 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$7 -> x$7.left();
            RedBlackTree.Node<A, Null$> node = this.scala$collection$mutable$TreeSet$$tree().root();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$12 -> x$12.key();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect8 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$11 -> x$11.right();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect9 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$10 -> x$10.left();
        RedBlackTree.Node<A, Null$> node = this.scala$collection$mutable$TreeSet$$tree().root();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        node = null;
        intersect9 = null;
        intersect8 = null;
        intersect = null;
        Object var26_26 = null;
        return shape.parUnbox(from_ans);
    }

    @Override
    public TreeSet<A> addOne(A elem) {
        RedBlackTree$.MODULE$.insert(this.scala$collection$mutable$TreeSet$$tree(), elem, null, this.ordering());
        return this;
    }

    @Override
    public TreeSet<A> subtractOne(A elem) {
        RedBlackTree$.MODULE$.delete(this.scala$collection$mutable$TreeSet$$tree(), elem, this.ordering());
        return this;
    }

    @Override
    public void clear() {
        RedBlackTree$.MODULE$.clear(this.scala$collection$mutable$TreeSet$$tree());
    }

    @Override
    public boolean contains(A elem) {
        return RedBlackTree$.MODULE$.contains(this.scala$collection$mutable$TreeSet$$tree(), elem, this.ordering());
    }

    public Set<A> unconstrained() {
        return this;
    }

    @Override
    public TreeSet<A> rangeImpl(Option<A> from, Option<A> until) {
        return new TreeSetProjection(this, from, until);
    }

    @Override
    public String className() {
        return "TreeSet";
    }

    @Override
    public int size() {
        return this.scala$collection$mutable$TreeSet$$tree().size();
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return RedBlackTree$.MODULE$.isEmpty(this.scala$collection$mutable$TreeSet$$tree());
    }

    @Override
    public A head() {
        return RedBlackTree$.MODULE$.minKey(this.scala$collection$mutable$TreeSet$$tree()).get();
    }

    @Override
    public A last() {
        return RedBlackTree$.MODULE$.maxKey(this.scala$collection$mutable$TreeSet$$tree()).get();
    }

    @Override
    public Option<A> minAfter(A key) {
        return RedBlackTree$.MODULE$.minKeyAfter(this.scala$collection$mutable$TreeSet$$tree(), key, this.ordering());
    }

    @Override
    public Option<A> maxBefore(A key) {
        return RedBlackTree$.MODULE$.maxKeyBefore(this.scala$collection$mutable$TreeSet$$tree(), key, this.ordering());
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        RedBlackTree$.MODULE$.foreachKey(this.scala$collection$mutable$TreeSet$$tree(), f);
    }

    public static final /* synthetic */ int $anonfun$stepper$3(RedBlackTree.Node x$3) {
        return BoxesRunTime.unboxToInt(x$3.key());
    }

    public static final /* synthetic */ long $anonfun$stepper$6(RedBlackTree.Node x$6) {
        return BoxesRunTime.unboxToLong(x$6.key());
    }

    public static final /* synthetic */ double $anonfun$stepper$9(RedBlackTree.Node x$9) {
        return BoxesRunTime.unboxToDouble(x$9.key());
    }

    public TreeSet(RedBlackTree.Tree<A, Null$> tree, Ordering<A> ordering) {
        this.scala$collection$mutable$TreeSet$$tree = tree;
        this.ordering = ordering;
        if (ordering == null) {
            throw new NullPointerException("ordering must not be null");
        }
    }

    public TreeSet(Ordering<A> ord) {
        this(RedBlackTree$Tree$.MODULE$.empty(), ord);
    }

    private final class TreeSetProjection
    extends TreeSet<A> {
        private final Option<A> from;
        private final Option<A> until;
        private final /* synthetic */ TreeSet $outer;

        private Option<A> pickLowerBound(Option<A> newFrom) {
            Option option = this.from;
            if (option instanceof Some) {
                Object fr = ((Some)option).value();
                if (newFrom instanceof Some) {
                    Object newFr = ((Some)newFrom).value();
                    return new Some(this.ordering().max(fr, newFr));
                }
            }
            if (None$.MODULE$.equals(option)) {
                return newFrom;
            }
            return this.from;
        }

        private Option<A> pickUpperBound(Option<A> newUntil) {
            Option option = this.until;
            if (option instanceof Some) {
                Object unt = ((Some)option).value();
                if (newUntil instanceof Some) {
                    Object newUnt = ((Some)newUntil).value();
                    return new Some(this.ordering().min(unt, newUnt));
                }
            }
            if (None$.MODULE$.equals(option)) {
                return newUntil;
            }
            return this.until;
        }

        private boolean isInsideViewBounds(A key) {
            boolean beforeUntil;
            boolean afterFrom = this.from.isEmpty() || this.ordering().compare(this.from.get(), key) <= 0;
            boolean bl = beforeUntil = this.until.isEmpty() || this.ordering().compare(key, this.until.get()) < 0;
            return afterFrom && beforeUntil;
        }

        @Override
        public TreeSet<A> rangeImpl(Option<A> from, Option<A> until) {
            return new TreeSetProjection(this.$outer, this.pickLowerBound(from), this.pickUpperBound(until));
        }

        @Override
        public boolean contains(A key) {
            return this.isInsideViewBounds(key) && RedBlackTree$.MODULE$.contains(this.$outer.scala$collection$mutable$TreeSet$$tree(), key, this.ordering());
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<A> iterator() {
            void keysIterator_evidence$4;
            void keysIterator_end;
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option keysIterator_start = this.from;
            RedBlackTree.Tree keysIterator_tree = this.$outer.scala$collection$mutable$TreeSet$$tree();
            return new RedBlackTree.KeysIterator(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<A> iteratorFrom(A start2) {
            void keysIterator_evidence$4;
            void keysIterator_end;
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option keysIterator_start = this.pickLowerBound(new Some(start2));
            RedBlackTree.Tree keysIterator_tree = this.$outer.scala$collection$mutable$TreeSet$$tree();
            return new RedBlackTree.KeysIterator(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
        }

        @Override
        public int size() {
            if (this.$outer.scala$collection$mutable$TreeSet$$tree().size() == 0) {
                return 0;
            }
            Iterator iterator2 = this.iterator();
            if (iterator2 == null) {
                throw null;
            }
            return iterator2.size();
        }

        @Override
        public int knownSize() {
            if (this.$outer.scala$collection$mutable$TreeSet$$tree().size() == 0) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.$outer.scala$collection$mutable$TreeSet$$tree().size() == 0 || !this.iterator().hasNext();
        }

        @Override
        public A head() {
            return this.headOption().get();
        }

        @Override
        public Option<A> headOption() {
            Option elem = this.from.isDefined() ? RedBlackTree$.MODULE$.minKeyAfter(this.$outer.scala$collection$mutable$TreeSet$$tree(), this.from.get(), this.ordering()) : RedBlackTree$.MODULE$.minKey(this.$outer.scala$collection$mutable$TreeSet$$tree());
            Option option = this.until;
            if (elem instanceof Some) {
                Object e = ((Some)elem).value();
                if (option instanceof Some) {
                    Object unt = ((Some)option).value();
                    if (this.ordering().compare(e, unt) >= 0) {
                        return None$.MODULE$;
                    }
                }
            }
            return elem;
        }

        @Override
        public A last() {
            return this.lastOption().get();
        }

        @Override
        public Option<A> lastOption() {
            Option elem = this.until.isDefined() ? RedBlackTree$.MODULE$.maxKeyBefore(this.$outer.scala$collection$mutable$TreeSet$$tree(), this.until.get(), this.ordering()) : RedBlackTree$.MODULE$.maxKey(this.$outer.scala$collection$mutable$TreeSet$$tree());
            Option option = this.from;
            if (elem instanceof Some) {
                Object e = ((Some)elem).value();
                if (option instanceof Some) {
                    Object fr = ((Some)option).value();
                    if (this.ordering().compare(e, fr) < 0) {
                        return None$.MODULE$;
                    }
                }
            }
            return elem;
        }

        @Override
        public <U> void foreach(Function1<A, U> f) {
            this.iterator().foreach(f);
        }

        @Override
        public TreeSet<A> clone() {
            return ((TreeSet)SetOps.clone$(this)).rangeImpl(this.from, this.until);
        }

        public TreeSetProjection(TreeSet $outer, Option<A> from, Option<A> until) {
            this.from = from;
            this.until = until;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            super($outer.scala$collection$mutable$TreeSet$$tree(), $outer.ordering());
        }
    }
}

