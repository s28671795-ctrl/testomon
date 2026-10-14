/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.MapOps;
import scala.collection.immutable.TreeSeqMap;
import scala.collection.immutable.TreeSeqMap$;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003T\u0001\u0011\u0005A\u000bC\u0003Y\u0001\u0011E\u0013\fC\u0003a\u0001\u0011E\u0013\rC\u0003i\u0001\u0011\u0005\u0013\u000eC\u0003k\u0001\u0011\u00053N\u0001\nNCB4\u0015m\u0019;pef$UMZ1vYR\u001c(B\u0001\u0005\n\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0015\u0005)1oY1mC\u000e\u0001Q#B\u0007\u0019E\u0015:5\u0003\u0002\u0001\u000f%\u0011\u0003\"a\u0004\t\u000e\u0003%I!!E\u0005\u0003\r\u0005s\u0017PU3g!\u0019\u0019BCF\u0011%s5\tq!\u0003\u0002\u0016\u000f\t1Q*\u00199PaN\u0004\"a\u0006\r\r\u0001\u0011)\u0011\u0004\u0001b\u00015\t\t1*\u0005\u0002\u001c=A\u0011q\u0002H\u0005\u0003;%\u0011qAT8uQ&tw\r\u0005\u0002\u0010?%\u0011\u0001%\u0003\u0002\u0004\u0003:L\bCA\f#\t\u0019\u0019\u0003\u0001\"b\u00015\t\ta\u000b\u0005\u0002\u0018K\u00111a\u0005\u0001CC\u0002\u001d\u0012!aQ\"\u0016\u0007!\u00024'\u0005\u0002\u001cSA)1C\u000b\u00176q%\u00111f\u0002\u0002\f\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0003\u0010[=\u0012\u0014B\u0001\u0018\n\u0005\u0019!V\u000f\u001d7feA\u0011q\u0003\r\u0003\u0006c\u0015\u0012\rA\u0007\u0002\u0002qB\u0011qc\r\u0003\u0006i\u0015\u0012\rA\u0007\u0002\u0002sB\u00111CN\u0005\u0003o\u001d\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0004'Yb\u0003\u0003B\f&-iR#!I\u001e,\u0003q\u0002\"!\u0010\"\u000e\u0003yR!a\u0010!\u0002\u0013Ut7\r[3dW\u0016$'BA!\n\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u0007z\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f!\u0015\u0019\"&\u0012$:!\u0011yQFF\u0011\u0011\u0005]9EA\u0002%\u0001\t\u000b\u0007\u0011J\u0001\u0007XSRDg)\u001b7uKJ\u001c5)\u0006\u0002K!F\u00111d\u0013\n\u0004\u0019:\u0013f\u0001B'\u0001\u0001-\u0013A\u0002\u0010:fM&tW-\\3oiz\u0002Ra\u0005\u0016P\rF\u0003\"a\u0006)\u0005\u000bE:%\u0019\u0001\u000e\u0011\u0007]9u\nE\u0002\u0014m=\u000ba\u0001J5oSR$C#A+\u0011\u0005=1\u0016BA,\n\u0005\u0011)f.\u001b;\u0002\u0019\u0019\u0014x.\\*qK\u000eLg-[2\u0015\u0005eR\u0006\"B.\u0003\u0001\u0004a\u0016\u0001B2pY2\u00042aE/`\u0013\tqvA\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0003\u0010[YQ\u0014A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\u0012A\u0019\t\u0005G\u001a|\u0016(D\u0001e\u0015\t)w!A\u0004nkR\f'\r\\3\n\u0005\u001d$'a\u0002\"vS2$WM]\u0001\u0006K6\u0004H/_\u000b\u0002s\u0005Qq/\u001b;i\r&dG/\u001a:\u0015\u00051\u001c\bCB7q-\u00052EE\u0004\u0002\u0014]&\u0011qnB\u0001\u0007\u001b\u0006\u0004x\n]:\n\u0005E\u0014(AC,ji\"4\u0015\u000e\u001c;fe*\u0011qn\u0002\u0005\u0006i\u0016\u0001\r!^\u0001\u0002aB!qB^#y\u0013\t9\u0018BA\u0005Gk:\u001cG/[8ocA\u0011q\"_\u0005\u0003u&\u0011qAQ8pY\u0016\fg\u000e")
public interface MapFactoryDefaults<K, V, CC extends IterableOps<Tuple2<Object, Object>, Iterable, Iterable<Tuple2<Object, Object>>>, WithFilterCC extends Iterable<Object>>
extends MapOps<K, V, CC, CC> {
    public static /* synthetic */ IterableOps fromSpecific$(MapFactoryDefaults $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public CC fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
        return (CC)((IterableOps)this.mapFactory().from(coll));
    }

    public static /* synthetic */ Builder newSpecificBuilder$(MapFactoryDefaults $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<Tuple2<K, V>, CC> newSpecificBuilder() {
        return this.mapFactory().newBuilder();
    }

    public static /* synthetic */ IterableOps empty$(MapFactoryDefaults $this) {
        return $this.empty();
    }

    @Override
    default public CC empty() {
        if (this instanceof TreeSeqMap) {
            TreeSeqMap treeSeqMap = (TreeSeqMap)this;
            return (CC)TreeSeqMap$.MODULE$.empty(treeSeqMap.orderedBy());
        }
        return (CC)((IterableOps)this.mapFactory().empty());
    }

    public static /* synthetic */ MapOps.WithFilter withFilter$(MapFactoryDefaults $this, Function1 p) {
        return $this.withFilter(p);
    }

    default public MapOps.WithFilter<K, V, WithFilterCC, CC> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return new MapOps.WithFilter(this, p);
    }

    public static void $init$(MapFactoryDefaults $this) {
    }
}

