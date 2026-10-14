/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3a!\u0005\n\u0002\u0002IA\u0002\"\u0002\u0010\u0001\t\u0003\u0001\u0003b\u0002\u0018\u0001\u0001\u0004%\tb\f\u0005\bg\u0001\u0001\r\u0011\"\u00055\u0011\u0019Q\u0004\u0001)Q\u0005a!I1\b\u0001a\u0001\u0002\u0004%\t\u0002\u0010\u0005\n{\u0001\u0001\r\u00111A\u0005\u0012yB\u0011\u0002\u0011\u0001A\u0002\u0003\u0005\u000b\u0015B\u0012\t\r\u0005\u0003\u0001\u0015)\u00031\u0011\u0019\u0011\u0005\u0001)A\u0005\u0007\"1a\t\u0001Q\u0001\n\u001dCQA\b\u0001\u0005\u0002!CQa\u0013\u0001\u0005\u000e1CQa\u0014\u0001\u0005\u000eACQA\u0015\u0001\u0005\u000eMCQ\u0001\u0016\u0001\u0005\u000eUCQ!\u0017\u0001\u0005\u0006i\u0013\u0001d\u00115b[B\u0014\u0015m]3SKZ,'o]3Ji\u0016\u0014\u0018\r^8s\u0015\t\u0019B#A\u0005j[6,H/\u00192mK*\u0011QCF\u0001\u000bG>dG.Z2uS>t'\"A\f\u0002\u000bM\u001c\u0017\r\\1\u0016\u0005e)3C\u0001\u0001\u001b!\tYB$D\u0001\u0017\u0013\tibC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0011\u0005E\u0002#\u0001\rj\u0011A\u0005\t\u0003I\u0015b\u0001\u0001B\u0003'\u0001\t\u0007qEA\u0001U#\tA3\u0006\u0005\u0002\u001cS%\u0011!F\u0006\u0002\b\u001d>$\b.\u001b8h!\r\u0011CfI\u0005\u0003[I\u0011AAT8eK\u0006\u00112-\u001e:sK:$h+\u00197vK\u000e+(o]8s+\u0005\u0001\u0004CA\u000e2\u0013\t\u0011dCA\u0002J]R\facY;se\u0016tGOV1mk\u0016\u001cUO]:pe~#S-\u001d\u000b\u0003ka\u0002\"a\u0007\u001c\n\u0005]2\"\u0001B+oSRDq!O\u0002\u0002\u0002\u0003\u0007\u0001'A\u0002yIE\n1cY;se\u0016tGOV1mk\u0016\u001cUO]:pe\u0002\n\u0001cY;se\u0016tGOV1mk\u0016tu\u000eZ3\u0016\u0003\r\nAcY;se\u0016tGOV1mk\u0016tu\u000eZ3`I\u0015\fHCA\u001b@\u0011\u001dId!!AA\u0002\r\n\u0011cY;se\u0016tGOV1mk\u0016tu\u000eZ3!\u0003E\u0019WO\u001d:f]R\u001cF/Y2l\u0019\u00164X\r\\\u0001\n]>$W-\u00138eKb\u00042a\u0007#1\u0013\t)eCA\u0003BeJ\f\u00170A\u0005o_\u0012,7\u000b^1dWB\u00191\u0004R\u0012\u0015\u0005\u0005J\u0005\"\u0002&\f\u0001\u0004\u0019\u0013\u0001\u0003:p_Rtu\u000eZ3\u0002!M,G/\u001e9QCfdw.\u00193O_\u0012,GCA\u001bN\u0011\u0015qE\u00021\u0001$\u0003\u0011qw\u000eZ3\u0002\u0011A,8\u000f\u001b(pI\u0016$\"!N)\t\u000b9k\u0001\u0019A\u0012\u0002\u000fA|\u0007OT8eKR\tQ'A\ntK\u0006\u00148\r\u001b(fqR4\u0016\r\\;f\u001d>$W\rF\u0001W!\tYr+\u0003\u0002Y-\t9!i\\8mK\u0006t\u0017a\u00025bg:+\u0007\u0010^\u000b\u0002-\u0002")
public abstract class ChampBaseReverseIterator<T extends Node<T>> {
    private int currentValueCursor = -1;
    private T currentValueNode;
    private int currentStackLevel = -1;
    private final int[] nodeIndex = new int[Node$.MODULE$.MaxDepth() + 1];
    private final T[] nodeStack = new Node[Node$.MODULE$.MaxDepth() + 1];

    public int currentValueCursor() {
        return this.currentValueCursor;
    }

    public void currentValueCursor_$eq(int x$1) {
        this.currentValueCursor = x$1;
    }

    public T currentValueNode() {
        return this.currentValueNode;
    }

    public void currentValueNode_$eq(T x$1) {
        this.currentValueNode = x$1;
    }

    private final void setupPayloadNode(T node) {
        this.currentValueNode_$eq(node);
        this.currentValueCursor_$eq(((Node)node).payloadArity() - 1);
    }

    private final void pushNode(T node) {
        ++this.currentStackLevel;
        this.nodeStack[this.currentStackLevel] = node;
        this.nodeIndex[this.currentStackLevel] = ((Node)node).nodeArity() - 1;
    }

    private final void popNode() {
        --this.currentStackLevel;
    }

    private final boolean searchNextValueNode() {
        while (this.currentStackLevel >= 0) {
            int nodeCursor = this.nodeIndex[this.currentStackLevel];
            this.nodeIndex[this.currentStackLevel] = nodeCursor - 1;
            if (nodeCursor >= 0) {
                Object nextNode = ((Node)this.nodeStack[this.currentStackLevel]).getNode(nodeCursor);
                this.pushNode(nextNode);
                continue;
            }
            T currNode = this.nodeStack[this.currentStackLevel];
            this.popNode();
            if (!((Node)currNode).hasPayload()) continue;
            this.setupPayloadNode(currNode);
            return true;
        }
        return false;
    }

    public final boolean hasNext() {
        return this.currentValueCursor() >= 0 || this.searchNextValueNode();
    }

    public ChampBaseReverseIterator() {
    }

    public ChampBaseReverseIterator(T rootNode) {
        this();
        super.pushNode(rootNode);
        super.searchNextValueNode();
    }
}

