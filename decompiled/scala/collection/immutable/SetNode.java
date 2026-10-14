/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function1;
import scala.Function2;
import scala.collection.immutable.BitmapIndexedSetNode;
import scala.collection.immutable.Node;
import scala.collection.immutable.SetNode$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005]sA\u0002\u000f\u001e\u0011\u0003i2E\u0002\u0004&;!\u0005QD\n\u0005\u0006W\u0005!\t!\f\u0005\b]\u0005\u0011\r\u0011\"\u00040\u0011\u00191\u0014\u0001)A\u0007a!)q'\u0001C\u0001q!91)\u0001b\u0001\n\u000b!\u0005BB$\u0002A\u00035QI\u0002\u0004&;\u0005\u0005R\u0004\u0013\u0005\u0006W!!\t\u0001\u0015\u0005\u0006#\"1\tA\u0015\u0005\u0006C\"1\tA\u0019\u0005\u0006O\"1\t\u0001\u001b\u0005\u0006[\"1\tA\u001c\u0005\u0006_\"1\t\u0001\u001d\u0005\u0006c\"1\tA\u001d\u0005\u0006k\"1\tA\u001c\u0005\u0006m\"1\t\u0001\u001d\u0005\u0006o\"1\t\u0001\u001f\u0005\u0006u\"1\t\u0001\u001d\u0005\u0006w\"1\t\u0001 \u0005\b\u0003'Aa\u0011AA\u000b\u0011\u0019\ti\u0002\u0003D\u0001!\"9\u0011q\u0004\u0005\u0007\u0002\u0005\u0005\u0002bBA\u0017\u0011\u0019\u0005\u0011q\u0006\u0005\b\u0003kAa\u0011AA\u001c\u0011\u001d\ti\u0004\u0003D\u0001\u0003\u007fAq!!\u0013\t\r\u0003\tY%A\u0004TKRtu\u000eZ3\u000b\u0005yy\u0012!C5n[V$\u0018M\u00197f\u0015\t\u0001\u0013%\u0001\u0006d_2dWm\u0019;j_:T\u0011AI\u0001\u0006g\u000e\fG.\u0019\t\u0003I\u0005i\u0011!\b\u0002\b'\u0016$hj\u001c3f'\t\tq\u0005\u0005\u0002)S5\t\u0011%\u0003\u0002+C\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002G\u0005aQ)\u001c9usN+GOT8eKV\t\u0001\u0007E\u0002%cMJ!AM\u000f\u0003)\tKG/\\1q\u0013:$W\r_3e'\u0016$hj\u001c3f!\tAC'\u0003\u00026C\t9aj\u001c;iS:<\u0017!D#naRL8+\u001a;O_\u0012,\u0007%A\u0003f[B$\u00180\u0006\u0002:{U\t!\bE\u0002%cm\u0002\"\u0001P\u001f\r\u0001\u0011)a(\u0002b\u0001\u007f\t\t\u0011)\u0005\u00024\u0001B\u0011\u0001&Q\u0005\u0003\u0005\u0006\u00121!\u00118z\u0003-!V\u000f\u001d7f\u0019\u0016tw\r\u001e5\u0016\u0003\u0015{\u0011AR\u000f\u0002\u0003\u0005aA+\u001e9mK2+gn\u001a;iAU\u0011\u0011jT\n\u0003\u0011)\u00032\u0001J&N\u0013\taUD\u0001\u0003O_\u0012,\u0007c\u0001\u0013\t\u001dB\u0011Ah\u0014\u0003\u0006}!\u0011\ra\u0010\u000b\u0002\u001b\u0006A1m\u001c8uC&t7\u000fF\u0003T-bkv\f\u0005\u0002))&\u0011Q+\t\u0002\b\u0005>|G.Z1o\u0011\u00159&\u00021\u0001O\u0003\u001d)G.Z7f]RDQ!\u0017\u0006A\u0002i\u000bAb\u001c:jO&t\u0017\r\u001c%bg\"\u0004\"\u0001K.\n\u0005q\u000b#aA%oi\")aL\u0003a\u00015\u0006!\u0001.Y:i\u0011\u0015\u0001'\u00021\u0001[\u0003\u0015\u0019\b.\u001b4u\u0003\u001d)\b\u000fZ1uK\u0012$R!T2eK\u001aDQaV\u0006A\u00029CQ!W\u0006A\u0002iCQAX\u0006A\u0002iCQ\u0001Y\u0006A\u0002i\u000bqA]3n_Z,G\rF\u0003NS*\\G\u000eC\u0003X\u0019\u0001\u0007a\nC\u0003Z\u0019\u0001\u0007!\fC\u0003_\u0019\u0001\u0007!\fC\u0003a\u0019\u0001\u0007!,\u0001\u0005iCNtu\u000eZ3t+\u0005\u0019\u0016!\u00038pI\u0016\f%/\u001b;z+\u0005Q\u0016aB4fi:{G-\u001a\u000b\u0003\u001bNDQ\u0001^\bA\u0002i\u000bQ!\u001b8eKb\f!\u0002[1t!\u0006LHn\\1e\u00031\u0001\u0018-\u001f7pC\u0012\f%/\u001b;z\u0003)9W\r\u001e)bs2|\u0017\r\u001a\u000b\u0003\u001dfDQ\u0001\u001e\nA\u0002i\u000bAa]5{K\u00069am\u001c:fC\u000eDWcA?\u0002\u0010Q\u0019a0a\u0001\u0011\u0005!z\u0018bAA\u0001C\t!QK\\5u\u0011\u001d\t)\u0001\u0006a\u0001\u0003\u000f\t\u0011A\u001a\t\u0007Q\u0005%a*!\u0004\n\u0007\u0005-\u0011EA\u0005Gk:\u001cG/[8ocA\u0019A(a\u0004\u0005\r\u0005EAC1\u0001@\u0005\u0005)\u0016\u0001C:vEN,Go\u00144\u0015\u000bM\u000b9\"a\u0007\t\r\u0005eQ\u00031\u0001N\u0003\u0011!\b.\u0019;\t\u000b\u0001,\u0002\u0019\u0001.\u0002\t\r|\u0007/_\u0001\u000bM&dG/\u001a:J[BdG#B'\u0002$\u0005%\u0002bBA\u0013/\u0001\u0007\u0011qE\u0001\u0005aJ,G\rE\u0003)\u0003\u0013q5\u000b\u0003\u0004\u0002,]\u0001\raU\u0001\bM2L\u0007\u000f]3e\u0003\u0011!\u0017N\u001a4\u0015\u000b5\u000b\t$a\r\t\r\u0005e\u0001\u00041\u0001N\u0011\u0015\u0001\u0007\u00041\u0001[\u0003\u0019\u0019wN\\2biR)Q*!\u000f\u0002<!1\u0011\u0011D\rA\u00025CQ\u0001Y\rA\u0002i\u000bqBZ8sK\u0006\u001c\u0007nV5uQ\"\u000b7\u000f\u001b\u000b\u0004}\u0006\u0005\u0003bBA\u00035\u0001\u0007\u00111\t\t\u0007Q\u0005\u0015cJ\u0017@\n\u0007\u0005\u001d\u0013EA\u0005Gk:\u001cG/[8oe\u0005!bm\u001c:fC\u000eDw+\u001b;i\u0011\u0006\u001c\bn\u00165jY\u0016$2aUA'\u0011\u001d\t)a\u0007a\u0001\u0003\u001f\u0002b\u0001KA#\u001dj\u001b\u0016\u0006\u0002\u00052\u0003'J1!!\u0016\u001e\u0005QA\u0015m\u001d5D_2d\u0017n]5p]N+GOT8eK\u0002")
public abstract class SetNode<A>
extends Node<SetNode<A>> {
    public static int TupleLength() {
        return 1;
    }

    public static <A> BitmapIndexedSetNode<A> empty() {
        return SetNode$.MODULE$.empty();
    }

    public abstract boolean contains(A var1, int var2, int var3, int var4);

    public abstract SetNode<A> updated(A var1, int var2, int var3, int var4);

    public abstract SetNode<A> removed(A var1, int var2, int var3, int var4);

    @Override
    public abstract boolean hasNodes();

    @Override
    public abstract int nodeArity();

    @Override
    public abstract SetNode<A> getNode(int var1);

    @Override
    public abstract boolean hasPayload();

    @Override
    public abstract int payloadArity();

    @Override
    public abstract A getPayload(int var1);

    public abstract int size();

    public abstract <U> void foreach(Function1<A, U> var1);

    public abstract boolean subsetOf(SetNode<A> var1, int var2);

    public abstract SetNode<A> copy();

    public abstract SetNode<A> filterImpl(Function1<A, Object> var1, boolean var2);

    public abstract SetNode<A> diff(SetNode<A> var1, int var2);

    public abstract SetNode<A> concat(SetNode<A> var1, int var2);

    public abstract void foreachWithHash(Function2<A, Object, BoxedUnit> var1);

    public abstract boolean foreachWithHashWhile(Function2<A, Object, Object> var1);
}

