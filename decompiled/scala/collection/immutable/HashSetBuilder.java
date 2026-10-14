/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Array$;
import scala.Function1;
import scala.MatchError;
import scala.collection.Hashing$;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.BitmapIndexedSetNode;
import scala.collection.immutable.ChampBaseIterator;
import scala.collection.immutable.HashCollisionSetNode;
import scala.collection.immutable.HashSet;
import scala.collection.immutable.HashSet$;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SetNode;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mb!\u0002\f\u0018\u0005ei\u0002\"B\u001d\u0001\t\u0003Q\u0004\"\u0002\u001f\u0001\t\u0013i\u0004\"C!\u0001\u0001\u0004\u0005\r\u0011\"\u0003C\u0011%\u0019\u0005\u00011AA\u0002\u0013%A\tC\u0005K\u0001\u0001\u0007\t\u0011)Q\u0005k!)1\n\u0001C\u0005\u0019\"9\u0001\u000b\u0001a\u0001\n\u0013i\u0004bB)\u0001\u0001\u0004%IA\u0015\u0005\u0007)\u0002\u0001\u000b\u0015\u0002 \t\u000bU\u0003A\u0011\u0002,\t\u000b\r\u0004A\u0011\u00023\t\u000bM\u0004A\u0011\u0002;\t\u000bi\u0004A\u0011A>\t\u000f\u0005E\u0001\u0001\"\u0003\u0002\u0014!9\u0011Q\u0003\u0001\u0005\n\u0005M\u0001bBA\f\u0001\u0011\u0005\u0013\u0011\u0004\u0005\b\u00037\u0001A\u0011IA\u000f\u0011\u001d\t\u0019\u0003\u0001C!\u0003KAq!a\r\u0001\t\u0003\n\u0019\u0002\u0003\u0005\u00026\u0001!\t!GA\u001c\u0011\u001d\tI\u0004\u0001C!\u0003o\u0011a\u0002S1tQN+GOQ;jY\u0012,'O\u0003\u0002\u00193\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u00035m\t!bY8mY\u0016\u001cG/[8o\u0015\u0005a\u0012!B:dC2\fWC\u0001\u0010,'\r\u0001qd\t\t\u0003A\u0005j\u0011aG\u0005\u0003Em\u0011a!\u00118z%\u00164\u0007\u0003\u0002\u0013(SUj\u0011!\n\u0006\u0003Me\tq!\\;uC\ndW-\u0003\u0002)K\ty!+Z;tC\ndWMQ;jY\u0012,'\u000f\u0005\u0002+W1\u0001A!\u0002\u0017\u0001\u0005\u0004q#!A!\u0004\u0001E\u0011qF\r\t\u0003AAJ!!M\u000e\u0003\u000f9{G\u000f[5oOB\u0011\u0001eM\u0005\u0003im\u00111!\u00118z!\r1t'K\u0007\u0002/%\u0011\u0001h\u0006\u0002\b\u0011\u0006\u001c\bnU3u\u0003\u0019a\u0014N\\5u}Q\t1\bE\u00027\u0001%\n\u0001C\\3x\u000b6\u0004H/\u001f*p_Rtu\u000eZ3\u0016\u0003y\u00022AN *\u0013\t\u0001uC\u0001\u000bCSRl\u0017\r]%oI\u0016DX\rZ*fi:{G-Z\u0001\bC2L\u0017m]3e+\u0005)\u0014aC1mS\u0006\u001cX\rZ0%KF$\"!\u0012%\u0011\u0005\u00012\u0015BA$\u001c\u0005\u0011)f.\u001b;\t\u000f%#\u0011\u0011!a\u0001k\u0005\u0019\u0001\u0010J\u0019\u0002\u0011\u0005d\u0017.Y:fI\u0002\n\u0011\"[:BY&\f7/\u001a3\u0016\u00035\u0003\"\u0001\t(\n\u0005=[\"a\u0002\"p_2,\u0017M\\\u0001\te>|GOT8eK\u0006a!o\\8u\u001d>$Wm\u0018\u0013fcR\u0011Qi\u0015\u0005\b\u0013\"\t\t\u00111\u0001?\u0003%\u0011xn\u001c;O_\u0012,\u0007%A\u0007j]N,'\u000f^#mK6,g\u000e\u001e\u000b\u0005/v{\u0016\rE\u0002!1jK!!W\u000e\u0003\u000b\u0005\u0013(/Y=\u0011\u0005\u0001Z\u0016B\u0001/\u001c\u0005\rIe\u000e\u001e\u0005\u0006=*\u0001\raV\u0001\u0003CNDQ\u0001\u0019\u0006A\u0002i\u000b!!\u001b=\t\u000b\tT\u0001\u0019\u0001.\u0002\t\u0015dW-\\\u0001\fS:\u001cXM\u001d;WC2,X-\u0006\u0002faR1QI\u001a5kY:DQaZ\u0006A\u0002y\n!AY7\t\u000b%\\\u0001\u0019\u0001.\u0002\r\tLG\u000f]8t\u0011\u0015Y7\u00021\u0001*\u0003\rYW-\u001f\u0005\u0006[.\u0001\rAW\u0001\r_JLw-\u001b8bY\"\u000b7\u000f\u001b\u0005\u0006_.\u0001\rAW\u0001\bW\u0016L\b*Y:i\t\u0015\t8B1\u0001s\u0005\t\t\u0015'\u0005\u0002*e\u0005A1/\u001a;WC2,X-\u0006\u0002vsR!QI^<y\u0011\u00159G\u00021\u0001?\u0011\u0015IG\u00021\u0001[\u0011\u0015\u0011G\u00021\u0001*\t\u0015\tHB1\u0001s\u0003\u0019)\b\u000fZ1uKRQQ\t`A\u0002\u0003\u000f\tI!!\u0004\t\u000bul\u0001\u0019\u0001@\u0002\u000fM,GOT8eKB\u0019ag`\u0015\n\u0007\u0005\u0005qCA\u0004TKRtu\u000eZ3\t\r\u0005\u0015Q\u00021\u0001*\u0003\u001d)G.Z7f]RDQ!\\\u0007A\u0002iCa!a\u0003\u000e\u0001\u0004Q\u0016aC3mK6,g\u000e\u001e%bg\"Da!a\u0004\u000e\u0001\u0004Q\u0016!B:iS\u001a$\u0018aD3ogV\u0014X-\u00168bY&\f7/\u001a3\u0015\u0003\u0015\u000b\u0011bY8qs\u0016cW-\\:\u0002\rI,7/\u001e7u)\u0005)\u0014AB1eI>sW\r\u0006\u0003\u0002 \u0005\u0005R\"\u0001\u0001\t\u000b\t\f\u0002\u0019A\u0015\u0002\r\u0005$G-\u00117m)\u0011\ty\"a\n\t\u000f\u0005%\"\u00031\u0001\u0002,\u0005\u0011\u0001p\u001d\t\u0006\u0003[\ty#K\u0007\u00023%\u0019\u0011\u0011G\r\u0003\u0019%#XM]1cY\u0016|enY3\u0002\u000b\rdW-\u0019:\u0002\tML'0Z\u000b\u00025\u0006I1N\\8x]NK'0\u001a")
public final class HashSetBuilder<A>
implements ReusableBuilder<A, HashSet<A>> {
    private HashSet<A> aliased;
    private BitmapIndexedSetNode<A> scala$collection$immutable$HashSetBuilder$$rootNode = this.newEmptyRootNode();

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
    public <NewTo> Builder<A, NewTo> mapResult(Function1<HashSet<A>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable<A> $plus$eq(A elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    private BitmapIndexedSetNode<A> newEmptyRootNode() {
        return new BitmapIndexedSetNode(0, 0, Array$.MODULE$.emptyObjectArray(), Array$.MODULE$.emptyIntArray(), 0, 0);
    }

    private HashSet<A> aliased() {
        return this.aliased;
    }

    private void aliased_$eq(HashSet<A> x$1) {
        this.aliased = x$1;
    }

    private boolean isAliased() {
        return this.aliased() != null;
    }

    public BitmapIndexedSetNode<A> scala$collection$immutable$HashSetBuilder$$rootNode() {
        return this.scala$collection$immutable$HashSetBuilder$$rootNode;
    }

    private void rootNode_$eq(BitmapIndexedSetNode<A> x$1) {
        this.scala$collection$immutable$HashSetBuilder$$rootNode = x$1;
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

    private <A1> void insertValue(BitmapIndexedSetNode<A> bm, int bitpos, A key, int originalHash, int keyHash) {
        int dataIx = bm.dataIndex(bitpos);
        int idx = 1 * dataIx;
        Object[] src = bm.content();
        Object[] dst = new Object[src.length + 1];
        System.arraycopy(src, 0, dst, 0, idx);
        dst[idx] = key;
        System.arraycopy(src, idx, dst, idx + 1, src.length - idx);
        int[] dstHashes = this.insertElement(bm.originalHashes(), dataIx, originalHash);
        bm.dataMap_$eq(bm.dataMap() | bitpos);
        bm.content_$eq(dst);
        bm.originalHashes_$eq(dstHashes);
        bm.size_$eq(bm.size() + 1);
        bm.cachedJavaKeySetHashCode_$eq(bm.cachedJavaKeySetHashCode() + keyHash);
    }

    private <A1> void setValue(BitmapIndexedSetNode<A> bm, int bitpos, A elem) {
        int dataIx = bm.dataIndex(bitpos);
        int idx = 1 * dataIx;
        bm.content()[idx] = elem;
    }

    public void update(SetNode<A> setNode, A element, int originalHash, int elementHash, int shift) {
        if (setNode instanceof BitmapIndexedSetNode) {
            BitmapIndexedSetNode bitmapIndexedSetNode = (BitmapIndexedSetNode)setNode;
            int mask = elementHash >>> shift & 0x1F;
            int bitpos = 1 << mask;
            if ((bitmapIndexedSetNode.dataMap() & bitpos) != 0) {
                int index = Node$.MODULE$.indexFrom(bitmapIndexedSetNode.dataMap(), mask, bitpos);
                Object element0 = bitmapIndexedSetNode.content()[index];
                int element0UnimprovedHash = bitmapIndexedSetNode.originalHashes()[index];
                if (element0UnimprovedHash == originalHash && BoxesRunTime.equals(element0, element)) {
                    this.setValue(bitmapIndexedSetNode, bitpos, element0);
                    return;
                }
                int element0Hash = Hashing$.MODULE$.improve(element0UnimprovedHash);
                SetNode<Object> subNodeNew = bitmapIndexedSetNode.mergeTwoKeyValPairs(element0, element0UnimprovedHash, element0Hash, element, originalHash, elementHash, shift + 5);
                bitmapIndexedSetNode.migrateFromInlineToNodeInPlace(bitpos, element0Hash, subNodeNew);
                return;
            }
            if ((bitmapIndexedSetNode.nodeMap() & bitpos) != 0) {
                int index = Node$.MODULE$.indexFrom(bitmapIndexedSetNode.nodeMap(), mask, bitpos);
                Node subNode = bitmapIndexedSetNode.getNode(index);
                int beforeSize = ((SetNode)subNode).size();
                int beforeHashCode = subNode.cachedJavaKeySetHashCode();
                this.update((SetNode<A>)subNode, element, originalHash, elementHash, shift + 5);
                bitmapIndexedSetNode.size_$eq(bitmapIndexedSetNode.size() + (((SetNode)subNode).size() - beforeSize));
                bitmapIndexedSetNode.cachedJavaKeySetHashCode_$eq(bitmapIndexedSetNode.cachedJavaKeySetHashCode() + (subNode.cachedJavaKeySetHashCode() - beforeHashCode));
                return;
            }
            this.insertValue(bitmapIndexedSetNode, bitpos, element, originalHash, elementHash);
            return;
        }
        if (setNode instanceof HashCollisionSetNode) {
            HashCollisionSetNode hashCollisionSetNode = (HashCollisionSetNode)setNode;
            int index = hashCollisionSetNode.content().indexOf(element);
            if (index < 0) {
                hashCollisionSetNode.content_$eq(hashCollisionSetNode.content().appended(element));
                return;
            }
            hashCollisionSetNode.content_$eq(hashCollisionSetNode.content().updated(index, element));
            return;
        }
        throw new MatchError(setNode);
    }

    private void ensureUnaliased() {
        if (this.isAliased()) {
            this.copyElems();
        }
        this.aliased_$eq(null);
    }

    private void copyElems() {
        this.rootNode_$eq((BitmapIndexedSetNode<A>)this.scala$collection$immutable$HashSetBuilder$$rootNode().copy());
    }

    @Override
    public HashSet<A> result() {
        if (this.scala$collection$immutable$HashSetBuilder$$rootNode().size() == 0) {
            return HashSet$.MODULE$.empty();
        }
        if (this.aliased() != null) {
            return this.aliased();
        }
        this.aliased_$eq(new HashSet<A>(this.scala$collection$immutable$HashSetBuilder$$rootNode()));
        Statics.releaseFence();
        return this.aliased();
    }

    @Override
    public HashSetBuilder<A> addOne(A elem) {
        this.ensureUnaliased();
        int h2 = Statics.anyHash(elem);
        int im = Hashing$.MODULE$.improve(h2);
        this.update(this.scala$collection$immutable$HashSetBuilder$$rootNode(), elem, h2, im, 0);
        return this;
    }

    @Override
    public HashSetBuilder<A> addAll(IterableOnce<A> xs) {
        this.ensureUnaliased();
        if (xs instanceof HashSet) {
            HashSet hashSet = (HashSet)xs;
            new ChampBaseIterator<SetNode<A>>(this, hashSet){
                {
                    while (this.hasNext()) {
                        int originalHash = ((Node)this.currentValueNode()).getHash(this.currentValueCursor());
                        $outer.update($outer.scala$collection$immutable$HashSetBuilder$$rootNode(), ((SetNode)this.currentValueNode()).getPayload(this.currentValueCursor()), originalHash, Hashing$.MODULE$.improve(originalHash), 0);
                        this.currentValueCursor_$eq(this.currentValueCursor() + 1);
                    }
                }
            };
        } else {
            Iterator<A> it = xs.iterator();
            while (it.hasNext()) {
                this.addOne((Object)it.next());
            }
        }
        return this;
    }

    @Override
    public void clear() {
        this.aliased_$eq(null);
        if (this.scala$collection$immutable$HashSetBuilder$$rootNode().size() > 0) {
            this.rootNode_$eq(this.newEmptyRootNode());
            return;
        }
    }

    public int size() {
        return this.scala$collection$immutable$HashSetBuilder$$rootNode().size();
    }

    @Override
    public int knownSize() {
        return this.scala$collection$immutable$HashSetBuilder$$rootNode().size();
    }
}

