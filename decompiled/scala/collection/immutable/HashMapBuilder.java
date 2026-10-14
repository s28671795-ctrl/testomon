/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.Hashing$;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.BitmapIndexedMapNode;
import scala.collection.immutable.ChampBaseIterator;
import scala.collection.immutable.HashCollisionMapNode;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.Map;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Md!B\r\u001b\u0005i\u0001\u0003\"\u0002\"\u0001\t\u0003\u0019\u0005\"B#\u0001\t\u00131\u0005\"\u0003&\u0001\u0001\u0004\u0005\r\u0011\"\u0003L\u0011%a\u0005\u00011AA\u0002\u0013%Q\nC\u0005T\u0001\u0001\u0007\t\u0011)Q\u0005}!)A\u000b\u0001C\u0005+\"9\u0011\f\u0001a\u0001\n\u00131\u0005b\u0002.\u0001\u0001\u0004%Ia\u0017\u0005\u0007;\u0002\u0001\u000b\u0015B$\t\ry\u0003A\u0011\u0001\u000e`\u0011\u0019I\u0007\u0001)C\u0005U\"1q\u000f\u0001Q\u0005\naD\u0001\"a\u0004\u0001\t\u0003Q\u0012\u0011\u0003\u0005\t\u0003S\u0001\u0001\u0015\"\u0003\u0002,!A\u0011Q\u0006\u0001!\n\u0013\tY\u0003C\u0004\u00020\u0001!\t%!\r\t\u000f\u0005M\u0002\u0001\"\u0011\u00026!9\u00111\u0007\u0001\u0005\u0002\u0005m\u0002bBA\u001a\u0001\u0011\u0005\u0011\u0011\t\u0005\b\u0003g\u0001A\u0011AA%\u0011\u001d\t)\u0006\u0001C!\u0003/Bq!a\u001b\u0001\t\u0003\nY\u0003\u0003\u0005\u0002n\u0001!\t\u0001HA8\u0011\u001d\t\t\b\u0001C!\u0003_\u0012a\u0002S1tQ6\u000b\u0007OQ;jY\u0012,'O\u0003\u0002\u001c9\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003;y\t!bY8mY\u0016\u001cG/[8o\u0015\u0005y\u0012!B:dC2\fWcA\u00112yM\u0019\u0001A\t\u0014\u0011\u0005\r\"S\"\u0001\u0010\n\u0005\u0015r\"AB!osJ+g\r\u0005\u0003(U1rT\"\u0001\u0015\u000b\u0005%b\u0012aB7vi\u0006\u0014G.Z\u0005\u0003W!\u0012qBU3vg\u0006\u0014G.\u001a\"vS2$WM\u001d\t\u0005G5z3(\u0003\u0002/=\t1A+\u001e9mKJ\u0002\"\u0001M\u0019\r\u0001\u0011)!\u0007\u0001b\u0001i\t\t1j\u0001\u0001\u0012\u0005UB\u0004CA\u00127\u0013\t9dDA\u0004O_RD\u0017N\\4\u0011\u0005\rJ\u0014B\u0001\u001e\u001f\u0005\r\te.\u001f\t\u0003aq\"Q!\u0010\u0001C\u0002Q\u0012\u0011A\u0016\t\u0005\u007f\u0001{3(D\u0001\u001b\u0013\t\t%DA\u0004ICNDW*\u00199\u0002\rqJg.\u001b;?)\u0005!\u0005\u0003B \u0001_m\n\u0001C\\3x\u000b6\u0004H/\u001f*p_Rtu\u000eZ3\u0016\u0003\u001d\u0003Ba\u0010%0w%\u0011\u0011J\u0007\u0002\u0015\u0005&$X.\u00199J]\u0012,\u00070\u001a3NCBtu\u000eZ3\u0002\u000f\u0005d\u0017.Y:fIV\ta(A\u0006bY&\f7/\u001a3`I\u0015\fHC\u0001(R!\t\u0019s*\u0003\u0002Q=\t!QK\\5u\u0011\u001d\u0011F!!AA\u0002y\n1\u0001\u001f\u00132\u0003!\tG.[1tK\u0012\u0004\u0013!C5t\u00032L\u0017m]3e+\u00051\u0006CA\u0012X\u0013\tAfDA\u0004C_>dW-\u00198\u0002\u0011I|w\u000e\u001e(pI\u0016\fAB]8pi:{G-Z0%KF$\"A\u0014/\t\u000fIC\u0011\u0011!a\u0001\u000f\u0006I!o\\8u\u001d>$W\rI\u0001\nO\u0016$xJ]#mg\u0016,\"\u0001\u00192\u0015\u0007\u0005,w\r\u0005\u00021E\u0012)1M\u0003b\u0001I\n\u0011a\u000bM\t\u0003waBQA\u001a\u0006A\u0002=\n1a[3z\u0011\u0015A'\u00021\u0001b\u0003\u00151\u0018\r\\;f\u00035Ign]3si\u0016cW-\\3oiR!1.]:v!\r\u0019CN\\\u0005\u0003[z\u0011Q!\u0011:sCf\u0004\"aI8\n\u0005At\"aA%oi\")!o\u0003a\u0001W\u0006\u0011\u0011m\u001d\u0005\u0006i.\u0001\rA\\\u0001\u0003SbDQA^\u0006A\u00029\fA!\u001a7f[\u0006Y\u0011N\\:feR4\u0016\r\\;f+\rI\u00181\u0002\u000b\n\u001djdhp`A\u0002\u0003\u000fAQa\u001f\u0007A\u0002\u001d\u000b!AY7\t\u000bud\u0001\u0019\u00018\u0002\r\tLG\u000f]8t\u0011\u00151G\u00021\u00010\u0011\u0019\t\t\u0001\u0004a\u0001]\u0006aqN]5hS:\fG\u000eS1tQ\"1\u0011Q\u0001\u0007A\u00029\fqa[3z\u0011\u0006\u001c\b\u000e\u0003\u0004i\u0019\u0001\u0007\u0011\u0011\u0002\t\u0004a\u0005-AABA\u0007\u0019\t\u0007AM\u0001\u0002Wc\u00051Q\u000f\u001d3bi\u0016$RBTA\n\u0003;\ty\"!\t\u0002$\u0005\u0015\u0002bBA\u000b\u001b\u0001\u0007\u0011qC\u0001\b[\u0006\u0004hj\u001c3f!\u0015y\u0014\u0011D\u0018<\u0013\r\tYB\u0007\u0002\b\u001b\u0006\u0004hj\u001c3f\u0011\u00151W\u00021\u00010\u0011\u0015AW\u00021\u0001<\u0011\u0019\t\t!\u0004a\u0001]\"1\u0011QA\u0007A\u00029Da!a\n\u000e\u0001\u0004q\u0017!B:iS\u001a$\u0018aD3ogV\u0014X-\u00168bY&\f7/\u001a3\u0015\u00039\u000b\u0011bY8qs\u0016cW-\\:\u0002\rI,7/\u001e7u)\u0005q\u0014AB1eI>sW\r\u0006\u0003\u00028\u0005eR\"\u0001\u0001\t\u000bY\f\u0002\u0019\u0001\u0017\u0015\r\u0005]\u0012QHA \u0011\u00151'\u00031\u00010\u0011\u0015A'\u00031\u0001<)!\t9$a\u0011\u0002F\u0005\u001d\u0003\"\u00024\u0014\u0001\u0004y\u0003\"\u00025\u0014\u0001\u0004Y\u0004BBA\u0001'\u0001\u0007a\u000e\u0006\u0006\u00028\u0005-\u0013QJA(\u0003#BQA\u001a\u000bA\u0002=BQ\u0001\u001b\u000bA\u0002mBa!!\u0001\u0015\u0001\u0004q\u0007BBA*)\u0001\u0007a.\u0001\u0003iCND\u0017AB1eI\u0006cG\u000e\u0006\u0003\u00028\u0005e\u0003bBA.+\u0001\u0007\u0011QL\u0001\u0003qN\u0004R!a\u0018\u0002f1r1aIA1\u0013\r\t\u0019GH\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t9'!\u001b\u0003\u0019%#XM]1cY\u0016|enY3\u000b\u0007\u0005\rd$A\u0003dY\u0016\f'/\u0001\u0003tSj,W#\u00018\u0002\u0013-twn\u001e8TSj,\u0007")
public final class HashMapBuilder<K, V>
implements ReusableBuilder<Tuple2<K, V>, HashMap<K, V>> {
    private HashMap<K, V> aliased;
    private BitmapIndexedMapNode<K, V> scala$collection$immutable$HashMapBuilder$$rootNode = this.newEmptyRootNode();

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<HashMap<K, V>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable $plus$eq(Object elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    private BitmapIndexedMapNode<K, V> newEmptyRootNode() {
        return new BitmapIndexedMapNode(0, 0, Array$.MODULE$.emptyObjectArray(), Array$.MODULE$.emptyIntArray(), 0, 0);
    }

    private HashMap<K, V> aliased() {
        return this.aliased;
    }

    private void aliased_$eq(HashMap<K, V> x$1) {
        this.aliased = x$1;
    }

    private boolean isAliased() {
        return this.aliased() != null;
    }

    public BitmapIndexedMapNode<K, V> scala$collection$immutable$HashMapBuilder$$rootNode() {
        return this.scala$collection$immutable$HashMapBuilder$$rootNode;
    }

    private void rootNode_$eq(BitmapIndexedMapNode<K, V> x$1) {
        this.scala$collection$immutable$HashMapBuilder$$rootNode = x$1;
    }

    /*
     * WARNING - void declaration
     */
    public <V0> V0 getOrElse(K key, V0 value) {
        void getOrElse_shift;
        void getOrElse_keyHash;
        if (this.scala$collection$immutable$HashMapBuilder$$rootNode().size() == 0) {
            return value;
        }
        int originalHash = Statics.anyHash(key);
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.scala$collection$immutable$HashMapBuilder$$rootNode();
        Function0<Object> & Serializable intersect = (Function0<Object> & Serializable)() -> value;
        boolean bl = false;
        int n = Hashing$.MODULE$.improve(originalHash);
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode<K, V> getOrElse_this = bitmapIndexedMapNode;
        int getOrElse_mask = getOrElse_keyHash >>> getOrElse_shift & 0x1F;
        int getOrElse_bitpos = 1 << getOrElse_mask;
        if ((getOrElse_this.dataMap() & getOrElse_bitpos) != 0) {
            int getOrElse_index = Node$.MODULE$.indexFrom(getOrElse_this.dataMap(), getOrElse_mask, getOrElse_bitpos);
            Object getOrElse_key0 = getOrElse_this.content()[2 * getOrElse_index];
            if (BoxesRunTime.equals(key, getOrElse_key0)) {
                return (V0)getOrElse_this.content()[2 * getOrElse_index + 1];
            }
            return value;
        }
        if ((getOrElse_this.nodeMap() & getOrElse_bitpos) != 0) {
            void getOrElse_f;
            int getOrElse_index = Node$.MODULE$.indexFrom(getOrElse_this.nodeMap(), getOrElse_mask, getOrElse_bitpos);
            return (V0)((MapNode)getOrElse_this.getNode(getOrElse_index)).getOrElse(key, originalHash, (int)getOrElse_keyHash, (int)(getOrElse_shift + 5), getOrElse_f);
        }
        return value;
    }

    private int[] insertElement(int[] as, int ix, int elem) {
        if (ix < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (ix > as.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int[] result = new int[as.length + 1];
        System.arraycopy(as, 0, result, 0, ix);
        result[ix] = elem;
        System.arraycopy(as, ix, result, ix + 1, as.length - ix);
        return result;
    }

    private <V1> void insertValue(BitmapIndexedMapNode<K, V> bm, int bitpos, K key, int originalHash, int keyHash, V1 value) {
        int dataIx = bm.dataIndex(bitpos);
        int idx = 2 * dataIx;
        Object[] src = bm.content();
        Object[] dst = new Object[src.length + 2];
        System.arraycopy(src, 0, dst, 0, idx);
        dst[idx] = key;
        dst[idx + 1] = value;
        System.arraycopy(src, idx, dst, idx + 2, src.length - idx);
        int[] dstHashes = this.insertElement(bm.originalHashes(), dataIx, originalHash);
        bm.dataMap_$eq(bm.dataMap() | bitpos);
        bm.content_$eq(dst);
        bm.originalHashes_$eq(dstHashes);
        bm.size_$eq(bm.size() + 1);
        bm.cachedJavaKeySetHashCode_$eq(bm.cachedJavaKeySetHashCode() + keyHash);
    }

    public void update(MapNode<K, V> mapNode, K key, V value, int originalHash, int keyHash, int shift) {
        if (mapNode instanceof BitmapIndexedMapNode) {
            BitmapIndexedMapNode bitmapIndexedMapNode = (BitmapIndexedMapNode)mapNode;
            int mask = keyHash >>> shift & 0x1F;
            int bitpos = 1 << mask;
            if ((bitmapIndexedMapNode.dataMap() & bitpos) != 0) {
                int index = Node$.MODULE$.indexFrom(bitmapIndexedMapNode.dataMap(), mask, bitpos);
                Object key0 = bitmapIndexedMapNode.content()[2 * index];
                int key0UnimprovedHash = bitmapIndexedMapNode.originalHashes()[index];
                if (key0UnimprovedHash == originalHash && BoxesRunTime.equals(key0, key)) {
                    bitmapIndexedMapNode.content()[2 * index + 1] = value;
                    return;
                }
                Object value0 = bitmapIndexedMapNode.content()[2 * index + 1];
                int key0Hash = Hashing$.MODULE$.improve(key0UnimprovedHash);
                MapNode<Object, Object> subNodeNew = bitmapIndexedMapNode.mergeTwoKeyValPairs(key0, value0, key0UnimprovedHash, key0Hash, key, value, originalHash, keyHash, shift + 5);
                bitmapIndexedMapNode.migrateFromInlineToNodeInPlace(bitpos, key0Hash, subNodeNew);
                return;
            }
            if ((bitmapIndexedMapNode.nodeMap() & bitpos) != 0) {
                int index = Node$.MODULE$.indexFrom(bitmapIndexedMapNode.nodeMap(), mask, bitpos);
                Node subNode = bitmapIndexedMapNode.getNode(index);
                int beforeSize = ((MapNode)subNode).size();
                int beforeHash = subNode.cachedJavaKeySetHashCode();
                this.update((MapNode<K, V>)subNode, key, value, originalHash, keyHash, shift + 5);
                bitmapIndexedMapNode.size_$eq(bitmapIndexedMapNode.size() + (((MapNode)subNode).size() - beforeSize));
                bitmapIndexedMapNode.cachedJavaKeySetHashCode_$eq(bitmapIndexedMapNode.cachedJavaKeySetHashCode() + (subNode.cachedJavaKeySetHashCode() - beforeHash));
                return;
            }
            this.insertValue(bitmapIndexedMapNode, bitpos, key, originalHash, keyHash, value);
            return;
        }
        if (mapNode instanceof HashCollisionMapNode) {
            HashCollisionMapNode hashCollisionMapNode = (HashCollisionMapNode)mapNode;
            int index = hashCollisionMapNode.indexOf(key);
            if (index < 0) {
                hashCollisionMapNode.content_$eq(hashCollisionMapNode.content().appended(new Tuple2<K, V>(key, value)));
                return;
            }
            hashCollisionMapNode.content_$eq(hashCollisionMapNode.content().updated(index, new Tuple2<K, V>(key, value)));
            return;
        }
        throw new MatchError(mapNode);
    }

    private void ensureUnaliased() {
        if (this.isAliased()) {
            this.copyElems();
        }
        this.aliased_$eq(null);
    }

    private void copyElems() {
        this.rootNode_$eq((BitmapIndexedMapNode<K, V>)this.scala$collection$immutable$HashMapBuilder$$rootNode().copy());
    }

    @Override
    public HashMap<K, V> result() {
        if (this.scala$collection$immutable$HashMapBuilder$$rootNode().size() == 0) {
            return HashMap$.MODULE$.empty();
        }
        if (this.aliased() != null) {
            return this.aliased();
        }
        this.aliased_$eq(new HashMap<K, V>(this.scala$collection$immutable$HashMapBuilder$$rootNode()));
        Statics.releaseFence();
        return this.aliased();
    }

    public HashMapBuilder<K, V> addOne(Tuple2<K, V> elem) {
        this.ensureUnaliased();
        int h2 = Statics.anyHash(elem._1());
        int im = Hashing$.MODULE$.improve(h2);
        this.update(this.scala$collection$immutable$HashMapBuilder$$rootNode(), elem._1(), elem._2(), h2, im, 0);
        return this;
    }

    public HashMapBuilder<K, V> addOne(K key, V value) {
        this.ensureUnaliased();
        int originalHash = Statics.anyHash(key);
        this.update(this.scala$collection$immutable$HashMapBuilder$$rootNode(), key, value, originalHash, Hashing$.MODULE$.improve(originalHash), 0);
        return this;
    }

    public HashMapBuilder<K, V> addOne(K key, V value, int originalHash) {
        this.ensureUnaliased();
        this.update(this.scala$collection$immutable$HashMapBuilder$$rootNode(), key, value, originalHash, Hashing$.MODULE$.improve(originalHash), 0);
        return this;
    }

    public HashMapBuilder<K, V> addOne(K key, V value, int originalHash, int hash) {
        this.ensureUnaliased();
        this.update(this.scala$collection$immutable$HashMapBuilder$$rootNode(), key, value, originalHash, hash, 0);
        return this;
    }

    public HashMapBuilder<K, V> addAll(IterableOnce<Tuple2<K, V>> xs) {
        this.ensureUnaliased();
        if (xs instanceof HashMap) {
            HashMap hashMap = (HashMap)xs;
            new ChampBaseIterator<MapNode<K, V>>(this, hashMap){
                {
                    while (this.hasNext()) {
                        int originalHash = ((Node)this.currentValueNode()).getHash(this.currentValueCursor());
                        $outer.update($outer.scala$collection$immutable$HashMapBuilder$$rootNode(), ((MapNode)this.currentValueNode()).getKey(this.currentValueCursor()), ((MapNode)this.currentValueNode()).getValue(this.currentValueCursor()), originalHash, Hashing$.MODULE$.improve(originalHash), 0);
                        this.currentValueCursor_$eq(this.currentValueCursor() + 1);
                    }
                }
            };
        } else if (xs instanceof scala.collection.mutable.HashMap) {
            scala.collection.mutable.HashMap hashMap = (scala.collection.mutable.HashMap)xs;
            Iterator iter = hashMap.nodeIterator();
            while (iter.hasNext()) {
                HashMap.Node next = iter.next();
                int originalHash = hashMap.unimproveHash(next.hash());
                int hash = Hashing$.MODULE$.improve(originalHash);
                this.update(this.scala$collection$immutable$HashMapBuilder$$rootNode(), next.key(), next.value(), originalHash, hash, 0);
            }
        } else if (xs instanceof Map) {
            ((Map)xs).foreachEntry((Function2<Object, Object, HashMapBuilder> & Serializable)(key, value) -> this.addOne(key, value));
        } else {
            Iterator<Tuple2<K, V>> it = xs.iterator();
            while (it.hasNext()) {
                this.addOne(it.next());
            }
        }
        return this;
    }

    @Override
    public void clear() {
        this.aliased_$eq(null);
        if (this.scala$collection$immutable$HashMapBuilder$$rootNode().size() > 0) {
            this.rootNode_$eq(this.newEmptyRootNode());
            return;
        }
    }

    public int size() {
        return this.scala$collection$immutable$HashMapBuilder$$rootNode().size();
    }

    @Override
    public int knownSize() {
        return this.scala$collection$immutable$HashMapBuilder$$rootNode().size();
    }
}

