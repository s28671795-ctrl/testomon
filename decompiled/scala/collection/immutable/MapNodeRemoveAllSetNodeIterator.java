/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.Hashing$;
import scala.collection.immutable.BitmapIndexedMapNode;
import scala.collection.immutable.ChampBaseIterator;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.Node;
import scala.collection.immutable.SetNode;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2A\u0001B\u0003\u0007\u0019!A\u0011\u0005\u0001B\u0001B\u0003%!\u0003C\u0003#\u0001\u0011\u00051\u0005C\u0003'\u0001\u0011\u0005qEA\u0010NCBtu\u000eZ3SK6|g/Z!mYN+GOT8eK&#XM]1u_JT!AB\u0004\u0002\u0013%lW.\u001e;bE2,'B\u0001\u0005\n\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0015\u0005)1oY1mC\u000e\u0001QCA\u0007\u0018'\t\u0001a\u0002E\u0002\u0010!Ii\u0011!B\u0005\u0003#\u0015\u0011\u0011c\u00115b[B\u0014\u0015m]3Ji\u0016\u0014\u0018\r^8s!\ry1#F\u0005\u0003)\u0015\u0011qaU3u\u001d>$W\r\u0005\u0002\u0017/1\u0001A!\u0002\r\u0001\u0005\u0004I\"!A&\u0012\u0005iq\u0002CA\u000e\u001d\u001b\u0005I\u0011BA\u000f\n\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aG\u0010\n\u0005\u0001J!aA!os\u0006Y!o\\8u'\u0016$hj\u001c3f\u0003\u0019a\u0014N\\5u}Q\u0011A%\n\t\u0004\u001f\u0001)\u0002\"B\u0011\u0003\u0001\u0004\u0011\u0012!\u0003:f[>4X-\u00117m+\tAS\u0006\u0006\u0002*_A!qBK\u000b-\u0013\tYSA\u0001\u000bCSRl\u0017\r]%oI\u0016DX\rZ'ba:{G-\u001a\t\u0003-5\"QAL\u0002C\u0002e\u0011\u0011A\u0016\u0005\u0006a\r\u0001\r!K\u0001\fe>|G/T1q\u001d>$W\r")
public final class MapNodeRemoveAllSetNodeIterator<K>
extends ChampBaseIterator<SetNode<K>> {
    public <V> BitmapIndexedMapNode<K, V> removeAll(BitmapIndexedMapNode<K, V> rootMapNode) {
        MapNode curr = rootMapNode;
        while (curr.size() > 0 && this.hasNext()) {
            int originalHash = ((Node)this.currentValueNode()).getHash(this.currentValueCursor());
            Object x$1 = ((SetNode)this.currentValueNode()).getPayload(this.currentValueCursor());
            int x$2 = Hashing$.MODULE$.improve(originalHash);
            curr = curr.removed(x$1, originalHash, x$2, 0);
            this.currentValueCursor_$eq(this.currentValueCursor() + 1);
        }
        return curr;
    }

    public MapNodeRemoveAllSetNodeIterator(SetNode<K> rootSetNode) {
        super(rootSetNode);
    }
}

