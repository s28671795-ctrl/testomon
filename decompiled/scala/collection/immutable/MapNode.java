/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Option;
import scala.Tuple2;
import scala.collection.immutable.BitmapIndexedMapNode;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.MapNode$;
import scala.collection.immutable.Node;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\turAB\u0012%\u0011\u0003!#F\u0002\u0004-I!\u0005A%\f\u0005\u0006e\u0005!\t\u0001\u000e\u0005\bk\u0005\u0011\r\u0011\"\u00047\u0011\u0019i\u0014\u0001)A\u0007o!)a(\u0001C\u0001\u007f!9Q*\u0001b\u0001\n\u000bq\u0005BB)\u0002A\u00035qJ\u0002\u0004-I\u0005\u0005BE\u0015\u0005\u0006e!!\tA\u001a\u0005\u0006Q\"1\t!\u001b\u0005\u0006k\"1\tA\u001e\u0005\u0006}\"1\ta \u0005\b\u0003;Aa\u0011AA\u0010\u0011\u001d\ty\u0003\u0003D\u0001\u0003cAq!a\u0013\t\r\u0003\ti\u0005C\u0004\u0002`!1\t!!\u0019\t\u000f\u0005\r\u0004B\"\u0001\u0002f!9\u0011q\r\u0005\u0007\u0002\u0005%\u0004bBA8\u0011\u0019\u0005\u0011\u0011\r\u0005\b\u0003cBa\u0011AA3\u0011\u001d\t\u0019\b\u0003D\u0001\u0003kBq!!\u001f\t\r\u0003\tY\bC\u0004\u0002\u0000!1\t!!!\t\u000f\u0005-\u0005B\"\u0001\u0002f!9\u0011Q\u0012\u0005\u0007\u0002\u0005=\u0005bBAT\u0011\u0019\u0005\u0011\u0011\u0016\u0005\b\u0003sCa\u0011AA^\u0011\u001d\t)\r\u0003D\u0001\u0003\u000fDa!a6\t\r\u00031\u0007bBAm\u0011\u0019\u0005\u00111\u001c\u0005\b\u0003WDa\u0011AAw\u0011\u001d\tI\u0010\u0003D\u0001\u0003wDqA!\b\t\r\u0003\u0011y\u0002C\u0004\u0003*!1\tAa\u000b\u0002\u000f5\u000b\u0007OT8eK*\u0011QEJ\u0001\nS6lW\u000f^1cY\u0016T!a\n\u0015\u0002\u0015\r|G\u000e\\3di&|gNC\u0001*\u0003\u0015\u00198-\u00197b!\tY\u0013!D\u0001%\u0005\u001di\u0015\r\u001d(pI\u0016\u001c\"!\u0001\u0018\u0011\u0005=\u0002T\"\u0001\u0015\n\u0005EB#AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005Q\u0013\u0001D#naRLX*\u00199O_\u0012,W#A\u001c\u0011\t-B$HO\u0005\u0003s\u0011\u0012ACQ5u[\u0006\u0004\u0018J\u001c3fq\u0016$W*\u00199O_\u0012,\u0007CA\u0018<\u0013\ta\u0004FA\u0004O_RD\u0017N\\4\u0002\u001b\u0015k\u0007\u000f^=NCBtu\u000eZ3!\u0003\u0015)W\u000e\u001d;z+\r\u0001EiS\u000b\u0002\u0003B!1\u0006\u000f\"K!\t\u0019E\t\u0004\u0001\u0005\u000b\u0015+!\u0019\u0001$\u0003\u0003-\u000b\"AO$\u0011\u0005=B\u0015BA%)\u0005\r\te.\u001f\t\u0003\u0007.#Q\u0001T\u0003C\u0002\u0019\u0013\u0011AV\u0001\f)V\u0004H.\u001a'f]\u001e$\b.F\u0001P\u001f\u0005\u0001V$\u0001\u0002\u0002\u0019Q+\b\u000f\\3MK:<G\u000f\u001b\u0011\u0016\u0007MKFl\u0005\u0002\t)B\u00191&V,\n\u0005Y##\u0001\u0002(pI\u0016\u0004Ba\u000b\u0005Y5B\u00111)\u0017\u0003\u0006\u000b\"\u0011\rA\u0012\u0016\u00037v\u0003\"a\u0011/\u0005\r1CAQ1\u0001GW\u0005q\u0006CA0e\u001b\u0005\u0001'BA1c\u0003%)hn\u00195fG.,GM\u0003\u0002dQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0015\u0004'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dKR\tq\r\u0005\u0003,\u0011a[\u0016!B1qa2LH#B.kYF\u001c\b\"B6\u000b\u0001\u0004A\u0016aA6fs\")QN\u0003a\u0001]\u0006aqN]5hS:\fG\u000eS1tQB\u0011qf\\\u0005\u0003a\"\u00121!\u00138u\u0011\u0015\u0011(\u00021\u0001o\u0003\u0011A\u0017m\u001d5\t\u000bQT\u0001\u0019\u00018\u0002\u000bMD\u0017N\u001a;\u0002\u0007\u001d,G\u000fF\u0003xundX\u0010E\u00020qnK!!\u001f\u0015\u0003\r=\u0003H/[8o\u0011\u0015Y7\u00021\u0001Y\u0011\u0015i7\u00021\u0001o\u0011\u0015\u00118\u00021\u0001o\u0011\u0015!8\u00021\u0001o\u0003%9W\r^(s\u000b2\u001cX-\u0006\u0003\u0002\u0002\u0005\u0015A\u0003DA\u0002\u0003\u0017\ti!a\u0004\u0002\u0012\u0005M\u0001cA\"\u0002\u0006\u00119\u0011q\u0001\u0007C\u0002\u0005%!A\u0001,2#\tYv\tC\u0003l\u0019\u0001\u0007\u0001\fC\u0003n\u0019\u0001\u0007a\u000eC\u0003s\u0019\u0001\u0007a\u000eC\u0003u\u0019\u0001\u0007a\u000e\u0003\u0005\u0002\u00161!\t\u0019AA\f\u0003\u00051\u0007#B\u0018\u0002\u001a\u0005\r\u0011bAA\u000eQ\tAAHY=oC6,g(A\u0006d_:$\u0018-\u001b8t\u0017\u0016LHCCA\u0011\u0003O\tI#a\u000b\u0002.A\u0019q&a\t\n\u0007\u0005\u0015\u0002FA\u0004C_>dW-\u00198\t\u000b-l\u0001\u0019\u0001-\t\u000b5l\u0001\u0019\u00018\t\u000bIl\u0001\u0019\u00018\t\u000bQl\u0001\u0019\u00018\u0002\u000fU\u0004H-\u0019;fIV!\u00111GA\u001d)9\t)$a\u000f\u0002>\u0005\u0005\u00131IA#\u0003\u000f\u0002Ra\u000b\u0005Y\u0003o\u00012aQA\u001d\t\u001d\t9A\u0004b\u0001\u0003\u0013AQa\u001b\bA\u0002aCq!a\u0010\u000f\u0001\u0004\t9$A\u0003wC2,X\rC\u0003n\u001d\u0001\u0007a\u000eC\u0003s\u001d\u0001\u0007a\u000eC\u0003u\u001d\u0001\u0007a\u000eC\u0004\u0002J9\u0001\r!!\t\u0002\u0019I,\u0007\u000f\\1dKZ\u000bG.^3\u0002\u000fI,Wn\u001c<fIV!\u0011qJA+))\t\t&a\u0016\u0002Z\u0005m\u0013Q\f\t\u0006W!A\u00161\u000b\t\u0004\u0007\u0006UCaBA\u0004\u001f\t\u0007\u0011\u0011\u0002\u0005\u0006W>\u0001\r\u0001\u0017\u0005\u0006[>\u0001\rA\u001c\u0005\u0006e>\u0001\rA\u001c\u0005\u0006i>\u0001\rA\\\u0001\tQ\u0006\u001chj\u001c3fgV\u0011\u0011\u0011E\u0001\n]>$W-\u0011:jif,\u0012A\\\u0001\bO\u0016$hj\u001c3f)\r9\u00171\u000e\u0005\u0007\u0003[\u0012\u0002\u0019\u00018\u0002\u000b%tG-\u001a=\u0002\u0015!\f7\u000fU1zY>\fG-\u0001\u0007qCfdw.\u00193Be&$\u00180\u0001\u0004hKR\\U-\u001f\u000b\u00041\u0006]\u0004BBA7+\u0001\u0007a.\u0001\u0005hKR4\u0016\r\\;f)\rY\u0016Q\u0010\u0005\u0007\u0003[2\u0002\u0019\u00018\u0002\u0015\u001d,G\u000fU1zY>\fG\r\u0006\u0003\u0002\u0004\u0006%\u0005#B\u0018\u0002\u0006b[\u0016bAADQ\t1A+\u001e9mKJBa!!\u001c\u0018\u0001\u0004q\u0017\u0001B:ju\u0016\fqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0002\u0012\u0006\rF\u0003BAJ\u00033\u00032aLAK\u0013\r\t9\n\u000b\u0002\u0005+:LG\u000fC\u0004\u0002\u0016e\u0001\r!a'\u0011\u000f=\ni*a!\u0002\"&\u0019\u0011q\u0014\u0015\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\"\u0002$\u00121\u0011QU\rC\u0002\u0019\u0013\u0011!V\u0001\rM>\u0014X-Y2i\u000b:$(/_\u000b\u0005\u0003W\u000b9\f\u0006\u0003\u0002\u0014\u00065\u0006bBA\u000b5\u0001\u0007\u0011q\u0016\t\b_\u0005E\u0006lWA[\u0013\r\t\u0019\f\u000b\u0002\n\rVt7\r^5p]J\u00022aQA\\\t\u0019\t)K\u0007b\u0001\r\u0006yam\u001c:fC\u000eDw+\u001b;i\u0011\u0006\u001c\b\u000e\u0006\u0003\u0002\u0014\u0006u\u0006bBA\u000b7\u0001\u0007\u0011q\u0018\t\t_\u0005\u0005\u0007l\u00178\u0002\u0014&\u0019\u00111\u0019\u0015\u0003\u0013\u0019+hn\u0019;j_:\u001c\u0014!\u0003;sC:\u001chm\u001c:n+\u0011\tI-a4\u0015\t\u0005-\u00171\u001b\t\u0006W!A\u0016Q\u001a\t\u0004\u0007\u0006=GABAi9\t\u0007aIA\u0001X\u0011\u001d\t)\u0002\ba\u0001\u0003+\u0004raLAY1n\u000bi-\u0001\u0003d_BL\u0018AB2p]\u000e\fG/\u0006\u0003\u0002^\u0006\rHCBAp\u0003K\fI\u000fE\u0003,\u0011a\u000b\t\u000fE\u0002D\u0003G$q!a\u0002\u001f\u0005\u0004\tI\u0001C\u0004\u0002hz\u0001\r!a8\u0002\tQD\u0017\r\u001e\u0005\u0006iz\u0001\rA\\\u0001\u000bM&dG/\u001a:J[BdG#B4\u0002p\u0006U\bbBAy?\u0001\u0007\u00111_\u0001\u0005aJ,G\rE\u00040\u0003;\u000b\u0019)!\t\t\u000f\u0005]x\u00041\u0001\u0002\"\u0005I\u0011n\u001d$mSB\u0004X\rZ\u0001\n[\u0016\u0014x-Z%oi>,B!!@\u0003\fQA\u0011q B\u0007\u0005#\u0011Y\u0002\u0006\u0003\u0002\u0014\n\u0005\u0001b\u0002B\u0002A\u0001\u0007!QA\u0001\u0007[\u0016\u0014x-\u001a4\u0011\u0013=\n\t,a!\u0003\b\t\u001d\u0001CB\u0018\u0002\u0006b\u0013I\u0001E\u0002D\u0005\u0017!q!a\u0002!\u0005\u0004\tI\u0001C\u0004\u0002h\u0002\u0002\rAa\u0004\u0011\u000b-B\u0001L!\u0003\t\u000f\tM\u0001\u00051\u0001\u0003\u0016\u00059!-^5mI\u0016\u0014\bCB\u0016\u0003\u0018a\u0013I!C\u0002\u0003\u001a\u0011\u0012a\u0002S1tQ6\u000b\u0007OQ;jY\u0012,'\u000fC\u0003uA\u0001\u0007a.\u0001\u0005hKR$V\u000f\u001d7f))\t\u0019I!\t\u0003$\t\u0015\"q\u0005\u0005\u0006W\u0006\u0002\r\u0001\u0017\u0005\u0006[\u0006\u0002\rA\u001c\u0005\u0006e\u0006\u0002\rA\u001c\u0005\u0006i\u0006\u0002\rA\\\u0001\bEVLG\u000e\u001a+p+\u0011\u0011iC!\u000e\u0015\t\u0005M%q\u0006\u0005\b\u0005'\u0011\u0003\u0019\u0001B\u0019!\u0019Y#q\u0003-\u00034A\u00191I!\u000e\u0005\u000f\u0005\u001d!E1\u0001\u0002\n%\"\u0001\u0002\u000fB\u001d\u0013\r\u0011Y\u0004\n\u0002\u0015\u0011\u0006\u001c\bnQ8mY&\u001c\u0018n\u001c8NCBtu\u000eZ3")
public abstract class MapNode<K, V>
extends Node<MapNode<K, V>> {
    public static int TupleLength() {
        return 2;
    }

    public static <K, V> BitmapIndexedMapNode<K, V> empty() {
        return MapNode$.MODULE$.empty();
    }

    public abstract V apply(K var1, int var2, int var3, int var4);

    public abstract Option<V> get(K var1, int var2, int var3, int var4);

    public abstract <V1> V1 getOrElse(K var1, int var2, int var3, int var4, Function0<V1> var5);

    public abstract boolean containsKey(K var1, int var2, int var3, int var4);

    public abstract <V1> MapNode<K, V1> updated(K var1, V1 var2, int var3, int var4, int var5, boolean var6);

    public abstract <V1> MapNode<K, V1> removed(K var1, int var2, int var3, int var4);

    @Override
    public abstract boolean hasNodes();

    @Override
    public abstract int nodeArity();

    @Override
    public abstract MapNode<K, V> getNode(int var1);

    @Override
    public abstract boolean hasPayload();

    @Override
    public abstract int payloadArity();

    public abstract K getKey(int var1);

    public abstract V getValue(int var1);

    @Override
    public abstract Tuple2<K, V> getPayload(int var1);

    public abstract int size();

    public abstract <U> void foreach(Function1<Tuple2<K, V>, U> var1);

    public abstract <U> void foreachEntry(Function2<K, V, U> var1);

    public abstract void foreachWithHash(Function3<K, V, Object, BoxedUnit> var1);

    public abstract <W> MapNode<K, W> transform(Function2<K, V, W> var1);

    public abstract MapNode<K, V> copy();

    public abstract <V1> MapNode<K, V1> concat(MapNode<K, V1> var1, int var2);

    public abstract MapNode<K, V> filterImpl(Function1<Tuple2<K, V>, Object> var1, boolean var2);

    public abstract <V1> void mergeInto(MapNode<K, V1> var1, HashMapBuilder<K, V1> var2, int var3, Function2<Tuple2<K, V>, Tuple2<K, V1>, Tuple2<K, V1>> var4);

    public abstract Tuple2<K, V> getTuple(K var1, int var2, int var3, int var4);

    public abstract <V1> void buildTo(HashMapBuilder<K, V1> var1);
}

