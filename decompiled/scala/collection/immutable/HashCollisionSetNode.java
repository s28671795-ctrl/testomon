/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Predef$;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.BitmapIndexedSetNode;
import scala.collection.immutable.SetNode;
import scala.collection.immutable.SetNode$;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.VectorBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005ec\u0001B\u0010!\r\u001dB\u0001\"\u000f\u0001\u0003\u0006\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0011)A\u0005w!Aq\b\u0001BC\u0002\u0013\u0005!\b\u0003\u0005A\u0001\t\u0005\t\u0015!\u0003<\u0011!\t\u0005A!a\u0001\n\u0003\u0011\u0005\u0002\u0003$\u0001\u0005\u0003\u0007I\u0011A$\t\u00115\u0003!\u0011!Q!\n\rCQA\u0014\u0001\u0005\u0002=CQ\u0001\u0016\u0001\u0005\u0002UCQa\u0018\u0001\u0005\u0002\u0001DQ!\u001a\u0001\u0005\u0002\u0019DQa\u001b\u0001\u0005\u00021DQ!\u001c\u0001\u0005\u0002iBQA\u001c\u0001\u0005\u0002=DQA\u001d\u0001\u0005\u00021DQa\u001d\u0001\u0005\u0002iBQ\u0001\u001e\u0001\u0005\u0002UDQa\u001e\u0001\u0005BaDQA\u001f\u0001\u0005\u0002iBQa\u001f\u0001\u0005\u0002qDa!!\u0004\u0001\t\u0003R\u0004bBA\b\u0001\u0011\u0005\u0011\u0011\u0003\u0005\b\u00033\u0001A\u0011IA\u000e\u0011\u001d\t9\u0003\u0001C!\u0003SAq!a\f\u0001\t\u0003\n\t\u0004C\u0004\u00026\u0001!\t%a\u000e\t\u000f\u0005e\u0002\u0001\"\u0011\u0002<!9\u0011Q\b\u0001\u0005B\u0005}\u0002bBA#\u0001\u0011\u0005\u0013q\t\u0005\b\u0003#\u0002A\u0011IA*\u0005QA\u0015m\u001d5D_2d\u0017n]5p]N+GOT8eK*\u0011\u0011EI\u0001\nS6lW\u000f^1cY\u0016T!a\t\u0013\u0002\u0015\r|G\u000e\\3di&|gNC\u0001&\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001K\u0018\u0014\u0005\u0001I\u0003c\u0001\u0016,[5\t\u0001%\u0003\u0002-A\t91+\u001a;O_\u0012,\u0007C\u0001\u00180\u0019\u0001!Q\u0001\r\u0001C\u0002E\u0012\u0011!Q\t\u0003eY\u0002\"a\r\u001b\u000e\u0003\u0011J!!\u000e\u0013\u0003\u000f9{G\u000f[5oOB\u00111gN\u0005\u0003q\u0011\u00121!\u00118z\u00031y'/[4j]\u0006d\u0007*Y:i+\u0005Y\u0004CA\u001a=\u0013\tiDEA\u0002J]R\fQb\u001c:jO&t\u0017\r\u001c%bg\"\u0004\u0013\u0001\u00025bg\"\fQ\u0001[1tQ\u0002\nqaY8oi\u0016tG/F\u0001D!\rQC)L\u0005\u0003\u000b\u0002\u0012aAV3di>\u0014\u0018aC2p]R,g\u000e^0%KF$\"\u0001S&\u0011\u0005MJ\u0015B\u0001&%\u0005\u0011)f.\u001b;\t\u000f13\u0011\u0011!a\u0001\u0007\u0006\u0019\u0001\u0010J\u0019\u0002\u0011\r|g\u000e^3oi\u0002\na\u0001P5oSRtD\u0003\u0002)R%N\u00032A\u000b\u0001.\u0011\u0015I\u0004\u00021\u0001<\u0011\u0015y\u0004\u00021\u0001<\u0011\u0015\t\u0005\u00021\u0001D\u0003!\u0019wN\u001c;bS:\u001cH#\u0002,Z7rk\u0006CA\u001aX\u0013\tAFEA\u0004C_>dW-\u00198\t\u000biK\u0001\u0019A\u0017\u0002\u000f\u0015dW-\\3oi\")\u0011(\u0003a\u0001w!)q(\u0003a\u0001w!)a,\u0003a\u0001w\u0005)1\u000f[5gi\u00069Q\u000f\u001d3bi\u0016$G#B\u0015bE\u000e$\u0007\"\u0002.\u000b\u0001\u0004i\u0003\"B\u001d\u000b\u0001\u0004Y\u0004\"B \u000b\u0001\u0004Y\u0004\"\u00020\u000b\u0001\u0004Y\u0014a\u0002:f[>4X\r\u001a\u000b\u0006S\u001dD\u0017N\u001b\u0005\u00065.\u0001\r!\f\u0005\u0006s-\u0001\ra\u000f\u0005\u0006\u007f-\u0001\ra\u000f\u0005\u0006=.\u0001\raO\u0001\tQ\u0006\u001chj\u001c3fgV\ta+A\u0005o_\u0012,\u0017I]5us\u00069q-\u001a;O_\u0012,GCA\u0015q\u0011\u0015\th\u00021\u0001<\u0003\u0015Ig\u000eZ3y\u0003)A\u0017m\u001d)bs2|\u0017\rZ\u0001\ra\u0006LHn\\1e\u0003JLG/_\u0001\u000bO\u0016$\b+Y=m_\u0006$GCA\u0017w\u0011\u0015\t\u0018\u00031\u0001<\u0003\u001d9W\r\u001e%bg\"$\"aO=\t\u000bE\u0014\u0002\u0019A\u001e\u0002\tML'0Z\u0001\bM>\u0014X-Y2i+\ri\u0018\u0011\u0002\u000b\u0003\u0011zDaa \u000bA\u0002\u0005\u0005\u0011!\u00014\u0011\rM\n\u0019!LA\u0004\u0013\r\t)\u0001\n\u0002\n\rVt7\r^5p]F\u00022ALA\u0005\t\u0019\tY\u0001\u0006b\u0001c\t\tQ+\u0001\rdC\u000eDW\r\u001a&bm\u0006\\U-_*fi\"\u000b7\u000f[\"pI\u0016\f\u0001b];cg\u0016$xJ\u001a\u000b\u0006-\u0006M\u0011q\u0003\u0005\u0007\u0003+1\u0002\u0019A\u0015\u0002\tQD\u0017\r\u001e\u0005\u0006=Z\u0001\raO\u0001\u000bM&dG/\u001a:J[BdG#B\u0015\u0002\u001e\u0005\r\u0002bBA\u0010/\u0001\u0007\u0011\u0011E\u0001\u0005aJ,G\rE\u00034\u0003\u0007ic\u000b\u0003\u0004\u0002&]\u0001\rAV\u0001\bM2L\u0007\u000f]3e\u0003\u0011!\u0017N\u001a4\u0015\u000b%\nY#!\f\t\r\u0005U\u0001\u00041\u0001*\u0011\u0015q\u0006\u00041\u0001<\u0003\u0019)\u0017/^1mgR\u0019a+a\r\t\r\u0005U\u0011\u00041\u00017\u0003!A\u0017m\u001d5D_\u0012,G#A\u001e\u0002\t\r|\u0007/\u001f\u000b\u0002!\u000611m\u001c8dCR$R!KA!\u0003\u0007Ba!!\u0006\u001d\u0001\u0004I\u0003\"\u00020\u001d\u0001\u0004Y\u0014a\u00044pe\u0016\f7\r[,ji\"D\u0015m\u001d5\u0015\u0007!\u000bI\u0005\u0003\u0004\u0000;\u0001\u0007\u00111\n\t\u0007g\u00055Sf\u000f%\n\u0007\u0005=CEA\u0005Gk:\u001cG/[8oe\u0005!bm\u001c:fC\u000eDw+\u001b;i\u0011\u0006\u001c\bn\u00165jY\u0016$2AVA+\u0011\u0019yh\u00041\u0001\u0002XA11'!\u0014.wY\u0003")
public final class HashCollisionSetNode<A>
extends SetNode<A> {
    private final int originalHash;
    private final int hash;
    private Vector<A> content;

    public int originalHash() {
        return this.originalHash;
    }

    public int hash() {
        return this.hash;
    }

    public Vector<A> content() {
        return this.content;
    }

    public void content_$eq(Vector<A> x$1) {
        this.content = x$1;
    }

    @Override
    public boolean contains(A element, int originalHash, int hash, int shift) {
        return this.hash() == hash && this.content().contains(element);
    }

    @Override
    public SetNode<A> updated(A element, int originalHash, int hash, int shift) {
        if (this.contains(element, originalHash, hash, shift)) {
            return this;
        }
        return new HashCollisionSetNode<A>(originalHash, hash, this.content().appended(element));
    }

    @Override
    public SetNode<A> removed(A element, int originalHash, int hash, int shift) {
        if (!this.contains(element, originalHash, hash, shift)) {
            return this;
        }
        Vector updatedContent = (Vector)this.content().filterNot((Function1<Object, Object> & Serializable)element0 -> BoxesRunTime.boxToBoolean(HashCollisionSetNode.$anonfun$removed$1(element, element0)));
        if (updatedContent == null) {
            throw null;
        }
        switch (updatedContent.length()) {
            case 1: {
                int maskFrom_shift = 0;
                int bitposFrom_mask = hash >>> maskFrom_shift & 0x1F;
                return new BitmapIndexedSetNode(1 << bitposFrom_mask, 0, new Object[]{updatedContent.apply(false)}, new int[]{originalHash}, 1, hash);
            }
        }
        return new HashCollisionSetNode<A>(originalHash, hash, updatedContent);
    }

    @Override
    public boolean hasNodes() {
        return false;
    }

    @Override
    public int nodeArity() {
        return 0;
    }

    @Override
    public SetNode<A> getNode(int index) {
        throw new IndexOutOfBoundsException("No sub-nodes present in hash-collision leaf node.");
    }

    @Override
    public boolean hasPayload() {
        return true;
    }

    @Override
    public int payloadArity() {
        return this.content().length();
    }

    @Override
    public A getPayload(int index) {
        return (A)this.content().apply(index);
    }

    @Override
    public int getHash(int index) {
        return this.originalHash();
    }

    @Override
    public int size() {
        return this.content().length();
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        Iterator<A> iter = this.content().iterator();
        while (iter.hasNext()) {
            f.apply(iter.next());
        }
    }

    @Override
    public int cachedJavaKeySetHashCode() {
        return this.size() * this.hash();
    }

    @Override
    public boolean subsetOf(SetNode<A> that, int shift) {
        if (this == that) {
            return true;
        }
        if (that instanceof HashCollisionSetNode) {
            HashCollisionSetNode hashCollisionSetNode = (HashCollisionSetNode)that;
            return this.payloadArity() <= hashCollisionSetNode.payloadArity() && this.content().forall(arg_0 -> HashCollisionSetNode.$anonfun$subsetOf$1$adapted(hashCollisionSetNode.content(), arg_0));
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public SetNode<A> filterImpl(Function1<A, Object> pred, boolean flipped) {
        Object var27_31;
        Object var32_34;
        Object[] objectArray;
        Function1<Object, Object> & Serializable intersect;
        Object object;
        Object var16_12;
        Object var21_16;
        Object[] objectArray2;
        Function1<Object, Object> & Serializable intersect2;
        block34: {
            Vector0$ vector0$ = this.content();
            if (vector0$ == null) {
                throw null;
            }
            Vector0$ filterImpl_this = vector0$;
            int filterImpl_len = filterImpl_this.prefix1().length;
            for (int filterImpl_i = 0; filterImpl_i != filterImpl_len; ++filterImpl_i) {
                if (BoxesRunTime.unboxToBoolean(pred.apply(filterImpl_this.prefix1()[filterImpl_i])) != flipped) continue;
                int filterImpl_bitmap = 0;
                for (int filterImpl_j = filterImpl_i + 1; filterImpl_j < filterImpl_len; ++filterImpl_j) {
                    if (BoxesRunTime.unboxToBoolean(pred.apply(filterImpl_this.prefix1()[filterImpl_j])) == flipped) continue;
                    filterImpl_bitmap |= 1 << filterImpl_j;
                }
                int filterImpl_newLen = filterImpl_i + Integer.bitCount(filterImpl_bitmap);
                if (filterImpl_this instanceof BigVector) {
                    int filterImpl_k;
                    VectorBuilder filterImpl_b = new VectorBuilder();
                    for (filterImpl_k = 0; filterImpl_k < filterImpl_i; ++filterImpl_k) {
                        filterImpl_b.addOne(filterImpl_this.prefix1()[filterImpl_k]);
                    }
                    filterImpl_k = filterImpl_i + 1;
                    while (filterImpl_i != filterImpl_newLen) {
                        if ((1 << filterImpl_k & filterImpl_bitmap) != 0) {
                            filterImpl_b.addOne(filterImpl_this.prefix1()[filterImpl_k]);
                            ++filterImpl_i;
                        }
                        ++filterImpl_k;
                    }
                    intersect2 = arg_0 -> Vector.$anonfun$filterImpl$1(pred, flipped, filterImpl_b, arg_0);
                    BigVector filterImpl_foreachRest_this = filterImpl_this;
                    int filterImpl_foreachRest_c = filterImpl_foreachRest_this.vectorSliceCount();
                    for (int filterImpl_foreachRest_i = 1; filterImpl_foreachRest_i < filterImpl_foreachRest_c; ++filterImpl_foreachRest_i) {
                        Object object2;
                        int filterImpl_foreachRest_foreachRec_i;
                        void filterImpl_foreachRest_foreachRec_level;
                        void filterImpl_foreachRest_foreachRec_a;
                        int filterImpl_foreachRest_vectorSliceDim_c = filterImpl_foreachRest_c / 2;
                        objectArray2 = filterImpl_foreachRest_this.vectorSlice(filterImpl_foreachRest_i);
                        int n = filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_foreachRest_i - filterImpl_foreachRest_vectorSliceDim_c) - 1;
                        VectorStatics$ filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                        int filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_foreachRest_foreachRec_a).length;
                        if (filterImpl_foreachRest_foreachRec_level == false) {
                            for (filterImpl_foreachRest_foreachRec_i = 0; filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len; ++filterImpl_foreachRest_foreachRec_i) {
                                object2 = filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                                if (BoxesRunTime.unboxToBoolean(pred.apply(object2)) == flipped) continue;
                                filterImpl_b.addOne(object2);
                            }
                        } else {
                            void filterImpl_foreachRest_foreachRec_l = filterImpl_foreachRest_foreachRec_level - true;
                            while (filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len) {
                                int foreachRec_i;
                                Object[] foreachRec_a = (Object[])filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                                int foreachRec_len = foreachRec_a.length;
                                if (filterImpl_foreachRest_foreachRec_l == false) {
                                    for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                        object2 = foreachRec_a[foreachRec_i];
                                        if (BoxesRunTime.unboxToBoolean(pred.apply(object2)) == flipped) continue;
                                        filterImpl_b.addOne(object2);
                                    }
                                } else {
                                    void foreachRec_l = filterImpl_foreachRest_foreachRec_l - true;
                                    while (foreachRec_i < foreachRec_len) {
                                        void filterImpl_foreachRest_f;
                                        filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_foreachRest_f);
                                        ++foreachRec_i;
                                    }
                                }
                                Object var39_22 = null;
                                ++filterImpl_foreachRest_foreachRec_i;
                            }
                        }
                        var21_16 = null;
                        objectArray2 = null;
                    }
                    var16_12 = null;
                    intersect2 = null;
                    var21_16 = null;
                    objectArray2 = null;
                    object = filterImpl_b.result();
                    break block34;
                }
                if (filterImpl_newLen == 0) {
                    object = Vector0$.MODULE$;
                } else {
                    Object[] filterImpl_newData = new Object[filterImpl_newLen];
                    System.arraycopy(filterImpl_this.prefix1(), 0, filterImpl_newData, 0, filterImpl_i);
                    int filterImpl_k = filterImpl_i + 1;
                    while (filterImpl_i != filterImpl_newLen) {
                        if ((1 << filterImpl_k & filterImpl_bitmap) != 0) {
                            filterImpl_newData[filterImpl_i] = filterImpl_this.prefix1()[filterImpl_k];
                            ++filterImpl_i;
                        }
                        ++filterImpl_k;
                    }
                    object = new Vector1(filterImpl_newData);
                }
                break block34;
            }
            if (filterImpl_this instanceof BigVector) {
                VectorBuilder filterImpl_b = new VectorBuilder();
                filterImpl_b.initFrom(filterImpl_this.prefix1());
                intersect = arg_0 -> Vector.$anonfun$filterImpl$2(pred, flipped, filterImpl_b, arg_0);
                BigVector filterImpl_foreachRest_this = filterImpl_this;
                int filterImpl_foreachRest_c = filterImpl_foreachRest_this.vectorSliceCount();
                for (int filterImpl_foreachRest_i = 1; filterImpl_foreachRest_i < filterImpl_foreachRest_c; ++filterImpl_foreachRest_i) {
                    Object object3;
                    int filterImpl_foreachRest_foreachRec_i;
                    void filterImpl_foreachRest_foreachRec_level;
                    void filterImpl_foreachRest_foreachRec_a;
                    int filterImpl_foreachRest_vectorSliceDim_c = filterImpl_foreachRest_c / 2;
                    objectArray = filterImpl_foreachRest_this.vectorSlice(filterImpl_foreachRest_i);
                    int n = filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_foreachRest_i - filterImpl_foreachRest_vectorSliceDim_c) - 1;
                    VectorStatics$ filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                    int filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_foreachRest_foreachRec_a).length;
                    if (filterImpl_foreachRest_foreachRec_level == false) {
                        for (filterImpl_foreachRest_foreachRec_i = 0; filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len; ++filterImpl_foreachRest_foreachRec_i) {
                            object3 = filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                            if (BoxesRunTime.unboxToBoolean(pred.apply(object3)) == flipped) continue;
                            filterImpl_b.addOne(object3);
                        }
                    } else {
                        void filterImpl_foreachRest_foreachRec_l = filterImpl_foreachRest_foreachRec_level - true;
                        while (filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len) {
                            int foreachRec_i;
                            Object[] foreachRec_a = (Object[])filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                            int foreachRec_len = foreachRec_a.length;
                            if (filterImpl_foreachRest_foreachRec_l == false) {
                                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                    object3 = foreachRec_a[foreachRec_i];
                                    if (BoxesRunTime.unboxToBoolean(pred.apply(object3)) == flipped) continue;
                                    filterImpl_b.addOne(object3);
                                }
                            } else {
                                void foreachRec_l = filterImpl_foreachRest_foreachRec_l - true;
                                while (foreachRec_i < foreachRec_len) {
                                    void filterImpl_foreachRest_f;
                                    filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_foreachRest_f);
                                    ++foreachRec_i;
                                }
                            }
                            Object var43_40 = null;
                            ++filterImpl_foreachRest_foreachRec_i;
                        }
                    }
                    var32_34 = null;
                    objectArray = null;
                }
                var27_31 = null;
                intersect = null;
                var32_34 = null;
                objectArray = null;
                object = filterImpl_b.result();
            } else {
                object = filterImpl_this;
            }
        }
        Object var5_3 = null;
        Object var11_9 = null;
        Object var13_27 = null;
        Object var15_29 = null;
        var16_12 = null;
        intersect2 = null;
        var21_16 = null;
        objectArray2 = null;
        var27_31 = null;
        intersect = null;
        var32_34 = null;
        objectArray = null;
        Vector0$ newContent = object;
        int newContentLength = newContent.length();
        if (newContentLength == 0) {
            return SetNode$.MODULE$.empty();
        }
        if (newContentLength == 1) {
            int bitposFrom_mask = this.hash() >>> 0 & 0x1F;
            return new BitmapIndexedSetNode(1 << bitposFrom_mask, 0, new Object[]{newContent.head()}, new int[]{this.originalHash()}, 1, this.hash());
        }
        if (newContent.length() == this.content().length()) {
            return this;
        }
        return new HashCollisionSetNode<Nothing$>(this.originalHash(), this.hash(), newContent);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public SetNode<A> diff(SetNode<A> that, int shift) {
        Object var29_34;
        Object var34_38;
        Object[] objectArray;
        Function1<Object, Object> & Serializable intersect;
        Object object;
        Object var18_15;
        Object var23_19;
        Object[] objectArray2;
        Function1<Object, Object> & Serializable intersect2;
        block34: {
            void filterImpl_flipped;
            boolean bl = true;
            Function1<Object, Object> & Serializable filterImpl_pred = (Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(that.contains(x$1, this.originalHash(), this.hash(), shift));
            Vector0$ vector0$ = this.content();
            if (vector0$ == null) {
                throw null;
            }
            Vector0$ filterImpl_filterImpl_this = vector0$;
            int filterImpl_filterImpl_len = filterImpl_filterImpl_this.prefix1().length;
            for (int filterImpl_filterImpl_i = 0; filterImpl_filterImpl_i != filterImpl_filterImpl_len; ++filterImpl_filterImpl_i) {
                Object object2 = filterImpl_filterImpl_this.prefix1()[filterImpl_filterImpl_i];
                if (that.contains(object2, this.originalHash(), this.hash(), shift) != filterImpl_flipped) continue;
                int filterImpl_filterImpl_bitmap = 0;
                for (int filterImpl_filterImpl_j = filterImpl_filterImpl_i + 1; filterImpl_filterImpl_j < filterImpl_filterImpl_len; ++filterImpl_filterImpl_j) {
                    object2 = filterImpl_filterImpl_this.prefix1()[filterImpl_filterImpl_j];
                    if (that.contains(object2, this.originalHash(), this.hash(), shift) == filterImpl_flipped) continue;
                    filterImpl_filterImpl_bitmap |= 1 << filterImpl_filterImpl_j;
                }
                int filterImpl_filterImpl_newLen = filterImpl_filterImpl_i + Integer.bitCount(filterImpl_filterImpl_bitmap);
                if (filterImpl_filterImpl_this instanceof BigVector) {
                    int filterImpl_filterImpl_k;
                    VectorBuilder filterImpl_filterImpl_b = new VectorBuilder();
                    for (filterImpl_filterImpl_k = 0; filterImpl_filterImpl_k < filterImpl_filterImpl_i; ++filterImpl_filterImpl_k) {
                        filterImpl_filterImpl_b.addOne(filterImpl_filterImpl_this.prefix1()[filterImpl_filterImpl_k]);
                    }
                    filterImpl_filterImpl_k = filterImpl_filterImpl_i + 1;
                    while (filterImpl_filterImpl_i != filterImpl_filterImpl_newLen) {
                        if ((1 << filterImpl_filterImpl_k & filterImpl_filterImpl_bitmap) != 0) {
                            filterImpl_filterImpl_b.addOne(filterImpl_filterImpl_this.prefix1()[filterImpl_filterImpl_k]);
                            ++filterImpl_filterImpl_i;
                        }
                        ++filterImpl_filterImpl_k;
                    }
                    intersect2 = arg_0 -> Vector.$anonfun$filterImpl$1(filterImpl_pred, (boolean)filterImpl_flipped, filterImpl_filterImpl_b, arg_0);
                    BigVector filterImpl_filterImpl_foreachRest_this = filterImpl_filterImpl_this;
                    int filterImpl_filterImpl_foreachRest_c = filterImpl_filterImpl_foreachRest_this.vectorSliceCount();
                    for (int filterImpl_filterImpl_foreachRest_i = 1; filterImpl_filterImpl_foreachRest_i < filterImpl_filterImpl_foreachRest_c; ++filterImpl_filterImpl_foreachRest_i) {
                        Object object3;
                        int filterImpl_filterImpl_foreachRest_foreachRec_i;
                        void filterImpl_filterImpl_foreachRest_foreachRec_level;
                        void filterImpl_filterImpl_foreachRest_foreachRec_a;
                        int filterImpl_filterImpl_foreachRest_vectorSliceDim_c = filterImpl_filterImpl_foreachRest_c / 2;
                        objectArray2 = filterImpl_filterImpl_foreachRest_this.vectorSlice(filterImpl_filterImpl_foreachRest_i);
                        int n = filterImpl_filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_filterImpl_foreachRest_i - filterImpl_filterImpl_foreachRest_vectorSliceDim_c) - 1;
                        VectorStatics$ filterImpl_filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                        int filterImpl_filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_filterImpl_foreachRest_foreachRec_a).length;
                        if (filterImpl_filterImpl_foreachRest_foreachRec_level == false) {
                            for (filterImpl_filterImpl_foreachRest_foreachRec_i = 0; filterImpl_filterImpl_foreachRest_foreachRec_i < filterImpl_filterImpl_foreachRest_foreachRec_len; ++filterImpl_filterImpl_foreachRest_foreachRec_i) {
                                object3 = filterImpl_filterImpl_foreachRest_foreachRec_a[filterImpl_filterImpl_foreachRest_foreachRec_i];
                                if (that.contains(object3, this.originalHash(), this.hash(), shift) == filterImpl_flipped) continue;
                                filterImpl_filterImpl_b.addOne(object3);
                            }
                        } else {
                            void filterImpl_filterImpl_foreachRest_foreachRec_l = filterImpl_filterImpl_foreachRest_foreachRec_level - true;
                            while (filterImpl_filterImpl_foreachRest_foreachRec_i < filterImpl_filterImpl_foreachRest_foreachRec_len) {
                                int foreachRec_i;
                                Object[] foreachRec_a = (Object[])filterImpl_filterImpl_foreachRest_foreachRec_a[filterImpl_filterImpl_foreachRest_foreachRec_i];
                                int foreachRec_len = foreachRec_a.length;
                                if (filterImpl_filterImpl_foreachRest_foreachRec_l == false) {
                                    for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                        object3 = foreachRec_a[foreachRec_i];
                                        if (that.contains(object3, this.originalHash(), this.hash(), shift) == filterImpl_flipped) continue;
                                        filterImpl_filterImpl_b.addOne(object3);
                                    }
                                } else {
                                    void foreachRec_l = filterImpl_filterImpl_foreachRest_foreachRec_l - true;
                                    while (foreachRec_i < foreachRec_len) {
                                        void filterImpl_filterImpl_foreachRest_f;
                                        filterImpl_filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_filterImpl_foreachRest_f);
                                        ++foreachRec_i;
                                    }
                                }
                                Object var41_25 = null;
                                ++filterImpl_filterImpl_foreachRest_foreachRec_i;
                            }
                        }
                        var23_19 = null;
                        objectArray2 = null;
                    }
                    var18_15 = null;
                    intersect2 = null;
                    var23_19 = null;
                    objectArray2 = null;
                    object = filterImpl_filterImpl_b.result();
                    break block34;
                }
                if (filterImpl_filterImpl_newLen == 0) {
                    object = Vector0$.MODULE$;
                } else {
                    Object[] filterImpl_filterImpl_newData = new Object[filterImpl_filterImpl_newLen];
                    System.arraycopy(filterImpl_filterImpl_this.prefix1(), 0, filterImpl_filterImpl_newData, 0, filterImpl_filterImpl_i);
                    int filterImpl_filterImpl_k = filterImpl_filterImpl_i + 1;
                    while (filterImpl_filterImpl_i != filterImpl_filterImpl_newLen) {
                        if ((1 << filterImpl_filterImpl_k & filterImpl_filterImpl_bitmap) != 0) {
                            filterImpl_filterImpl_newData[filterImpl_filterImpl_i] = filterImpl_filterImpl_this.prefix1()[filterImpl_filterImpl_k];
                            ++filterImpl_filterImpl_i;
                        }
                        ++filterImpl_filterImpl_k;
                    }
                    object = new Vector1(filterImpl_filterImpl_newData);
                }
                break block34;
            }
            if (filterImpl_filterImpl_this instanceof BigVector) {
                VectorBuilder filterImpl_filterImpl_b = new VectorBuilder();
                filterImpl_filterImpl_b.initFrom(filterImpl_filterImpl_this.prefix1());
                intersect = arg_0 -> Vector.$anonfun$filterImpl$2(filterImpl_pred, (boolean)filterImpl_flipped, filterImpl_filterImpl_b, arg_0);
                BigVector filterImpl_filterImpl_foreachRest_this = filterImpl_filterImpl_this;
                int filterImpl_filterImpl_foreachRest_c = filterImpl_filterImpl_foreachRest_this.vectorSliceCount();
                for (int filterImpl_filterImpl_foreachRest_i = 1; filterImpl_filterImpl_foreachRest_i < filterImpl_filterImpl_foreachRest_c; ++filterImpl_filterImpl_foreachRest_i) {
                    Object object4;
                    int filterImpl_filterImpl_foreachRest_foreachRec_i;
                    void filterImpl_filterImpl_foreachRest_foreachRec_level;
                    void filterImpl_filterImpl_foreachRest_foreachRec_a;
                    int filterImpl_filterImpl_foreachRest_vectorSliceDim_c = filterImpl_filterImpl_foreachRest_c / 2;
                    objectArray = filterImpl_filterImpl_foreachRest_this.vectorSlice(filterImpl_filterImpl_foreachRest_i);
                    int n = filterImpl_filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_filterImpl_foreachRest_i - filterImpl_filterImpl_foreachRest_vectorSliceDim_c) - 1;
                    VectorStatics$ filterImpl_filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                    int filterImpl_filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_filterImpl_foreachRest_foreachRec_a).length;
                    if (filterImpl_filterImpl_foreachRest_foreachRec_level == false) {
                        for (filterImpl_filterImpl_foreachRest_foreachRec_i = 0; filterImpl_filterImpl_foreachRest_foreachRec_i < filterImpl_filterImpl_foreachRest_foreachRec_len; ++filterImpl_filterImpl_foreachRest_foreachRec_i) {
                            object4 = filterImpl_filterImpl_foreachRest_foreachRec_a[filterImpl_filterImpl_foreachRest_foreachRec_i];
                            if (that.contains(object4, this.originalHash(), this.hash(), shift) == filterImpl_flipped) continue;
                            filterImpl_filterImpl_b.addOne(object4);
                        }
                    } else {
                        void filterImpl_filterImpl_foreachRest_foreachRec_l = filterImpl_filterImpl_foreachRest_foreachRec_level - true;
                        while (filterImpl_filterImpl_foreachRest_foreachRec_i < filterImpl_filterImpl_foreachRest_foreachRec_len) {
                            int foreachRec_i;
                            Object[] foreachRec_a = (Object[])filterImpl_filterImpl_foreachRest_foreachRec_a[filterImpl_filterImpl_foreachRest_foreachRec_i];
                            int foreachRec_len = foreachRec_a.length;
                            if (filterImpl_filterImpl_foreachRest_foreachRec_l == false) {
                                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                    object4 = foreachRec_a[foreachRec_i];
                                    if (that.contains(object4, this.originalHash(), this.hash(), shift) == filterImpl_flipped) continue;
                                    filterImpl_filterImpl_b.addOne(object4);
                                }
                            } else {
                                void foreachRec_l = filterImpl_filterImpl_foreachRest_foreachRec_l - true;
                                while (foreachRec_i < foreachRec_len) {
                                    void filterImpl_filterImpl_foreachRest_f;
                                    filterImpl_filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_filterImpl_foreachRest_f);
                                    ++foreachRec_i;
                                }
                            }
                            Object var45_44 = null;
                            ++filterImpl_filterImpl_foreachRest_foreachRec_i;
                        }
                    }
                    var34_38 = null;
                    objectArray = null;
                }
                var29_34 = null;
                intersect = null;
                var34_38 = null;
                objectArray = null;
                object = filterImpl_filterImpl_b.result();
            } else {
                object = filterImpl_filterImpl_this;
            }
        }
        Object var7_5 = null;
        Object var13_12 = null;
        Object var15_30 = null;
        Object var17_32 = null;
        var18_15 = null;
        intersect2 = null;
        var23_19 = null;
        objectArray2 = null;
        var29_34 = null;
        intersect = null;
        var34_38 = null;
        objectArray = null;
        Vector0$ filterImpl_newContent = object;
        int filterImpl_newContentLength = filterImpl_newContent.length();
        if (filterImpl_newContentLength == 0) {
            return SetNode$.MODULE$.empty();
        }
        if (filterImpl_newContentLength == 1) {
            int filterImpl_bitposFrom_mask = this.hash() >>> 0 & 0x1F;
            return new BitmapIndexedSetNode(1 << filterImpl_bitposFrom_mask, 0, new Object[]{filterImpl_newContent.head()}, new int[]{this.originalHash()}, 1, this.hash());
        }
        if (filterImpl_newContent.length() == this.content().length()) {
            return this;
        }
        return new HashCollisionSetNode<Nothing$>(this.originalHash(), this.hash(), filterImpl_newContent);
    }

    public boolean equals(Object that) {
        block5: {
            block7: {
                block6: {
                    if (!(that instanceof HashCollisionSetNode)) break block5;
                    HashCollisionSetNode hashCollisionSetNode = (HashCollisionSetNode)that;
                    if (this == hashCollisionSetNode) break block6;
                    if (this.hash() != hashCollisionSetNode.hash()) break block7;
                    Vector<A> vector = this.content();
                    if (vector == null) {
                        throw null;
                    }
                    Vector<A> vector2 = hashCollisionSetNode.content();
                    if (vector2 == null) {
                        throw null;
                    }
                    if (vector.length() != vector2.length() || !this.content().forall(arg_0 -> HashCollisionSetNode.$anonfun$equals$1$adapted(hashCollisionSetNode.content(), arg_0))) break block7;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Trie nodes do not support hashing.");
    }

    @Override
    public HashCollisionSetNode<A> copy() {
        return new HashCollisionSetNode<A>(this.originalHash(), this.hash(), this.content());
    }

    @Override
    public SetNode<A> concat(SetNode<A> that, int shift) {
        if (that instanceof HashCollisionSetNode) {
            HashCollisionSetNode hashCollisionSetNode = (HashCollisionSetNode)that;
            if (hashCollisionSetNode == this) {
                return this;
            }
            VectorBuilder newContent = null;
            Iterator<A> iter = hashCollisionSetNode.content().iterator();
            while (iter.hasNext()) {
                A nextPayload = iter.next();
                if (this.content().contains(nextPayload)) continue;
                if (newContent == null) {
                    newContent = new VectorBuilder();
                    newContent.addAll((IterableOnce)this.content());
                }
                newContent.addOne((Object)nextPayload);
            }
            if (newContent == null) {
                return this;
            }
            return new HashCollisionSetNode<A>(this.originalHash(), this.hash(), newContent.result());
        }
        if (that instanceof BitmapIndexedSetNode) {
            throw new UnsupportedOperationException("Cannot concatenate a HashCollisionSetNode with a BitmapIndexedSetNode");
        }
        throw new MatchError(that);
    }

    @Override
    public void foreachWithHash(Function2<A, Object, BoxedUnit> f) {
        Iterator<A> iter = this.content().iterator();
        while (iter.hasNext()) {
            A next = iter.next();
            f.apply(next, this.originalHash());
        }
    }

    @Override
    public boolean foreachWithHashWhile(Function2<A, Object, Object> f) {
        boolean stillGoing = true;
        Iterator<A> iter = this.content().iterator();
        while (iter.hasNext() && stillGoing) {
            A next = iter.next();
            stillGoing = stillGoing && BoxesRunTime.unboxToBoolean(f.apply(next, this.originalHash()));
        }
        return stillGoing;
    }

    public static final /* synthetic */ boolean $anonfun$removed$1(Object element$1, Object element0) {
        return BoxesRunTime.equals(element0, element$1);
    }

    public HashCollisionSetNode(int originalHash, int hash, Vector<A> content) {
        this.originalHash = originalHash;
        this.hash = hash;
        this.content = content;
        Predef$.MODULE$.require(this.content().length() >= 2);
    }

    public static final /* synthetic */ Object $anonfun$subsetOf$1$adapted(Vector eta$0$1$1, Object elem) {
        return BoxesRunTime.boxToBoolean(eta$0$1$1.contains(elem));
    }

    public static final /* synthetic */ Object $anonfun$equals$1$adapted(Vector eta$0$1$2, Object elem) {
        return BoxesRunTime.boxToBoolean(eta$0$1$2.contains(elem));
    }
}

