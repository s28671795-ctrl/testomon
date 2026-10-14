/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154a!\u0006\f\u0002\u0002Ya\u0002\"\u0002\u0012\u0001\t\u0003!\u0003b\u0002\u001a\u0001\u0001\u0004%\tb\r\u0005\bo\u0001\u0001\r\u0011\"\u00059\u0011\u0019q\u0004\u0001)Q\u0005i!9q\b\u0001a\u0001\n#\u0019\u0004b\u0002!\u0001\u0001\u0004%\t\"\u0011\u0005\u0007\u0007\u0002\u0001\u000b\u0015\u0002\u001b\t\u0013\u0011\u0003\u0001\u0019!a\u0001\n#)\u0005\"\u0003$\u0001\u0001\u0004\u0005\r\u0011\"\u0005H\u0011%I\u0005\u00011A\u0001B\u0003&q\u0005\u0003\u0004K\u0001\u0001\u0006K\u0001\u000e\u0005\n\u0017\u0002\u0001\r\u0011!Q!\n1C\u0011b\u0014\u0001A\u0002\u0003\u0005\u000b\u0015\u0002)\t\u000bE\u0003A\u0011\u0002*\t\u000b\t\u0002A\u0011A*\t\u000bY\u0003AQB,\t\u000bi\u0003AQB.\t\u000bu\u0003AQ\u0002*\t\u000by\u0003AQB0\t\u000b\r\u0004AQ\u00013\u0003#\rC\u0017-\u001c9CCN,\u0017\n^3sCR|'O\u0003\u0002\u00181\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u00033i\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Y\u0012!B:dC2\fWCA\u000f*'\t\u0001a\u0004\u0005\u0002 A5\t!$\u0003\u0002\"5\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002KA\u0019a\u0005A\u0014\u000e\u0003Y\u0001\"\u0001K\u0015\r\u0001\u0011)!\u0006\u0001b\u0001W\t\tA+\u0005\u0002-_A\u0011q$L\u0005\u0003]i\u0011qAT8uQ&tw\rE\u0002'a\u001dJ!!\r\f\u0003\t9{G-Z\u0001\u0013GV\u0014(/\u001a8u-\u0006dW/Z\"veN|'/F\u00015!\tyR'\u0003\u000275\t\u0019\u0011J\u001c;\u0002-\r,(O]3oiZ\u000bG.^3DkJ\u001cxN]0%KF$\"!\u000f\u001f\u0011\u0005}Q\u0014BA\u001e\u001b\u0005\u0011)f.\u001b;\t\u000fu\u001a\u0011\u0011!a\u0001i\u0005\u0019\u0001\u0010J\u0019\u0002'\r,(O]3oiZ\u000bG.^3DkJ\u001cxN\u001d\u0011\u0002%\r,(O]3oiZ\u000bG.^3MK:<G\u000f[\u0001\u0017GV\u0014(/\u001a8u-\u0006dW/\u001a'f]\u001e$\bn\u0018\u0013fcR\u0011\u0011H\u0011\u0005\b{\u0019\t\t\u00111\u00015\u0003M\u0019WO\u001d:f]R4\u0016\r\\;f\u0019\u0016tw\r\u001e5!\u0003A\u0019WO\u001d:f]R4\u0016\r\\;f\u001d>$W-F\u0001(\u0003Q\u0019WO\u001d:f]R4\u0016\r\\;f\u001d>$Wm\u0018\u0013fcR\u0011\u0011\b\u0013\u0005\b{%\t\t\u00111\u0001(\u0003E\u0019WO\u001d:f]R4\u0016\r\\;f\u001d>$W\rI\u0001\u0012GV\u0014(/\u001a8u'R\f7m\u001b'fm\u0016d\u0017!\u00068pI\u0016\u001cUO]:peN\fe\u000e\u001a'f]\u001e$\bn\u001d\t\u0004?5#\u0014B\u0001(\u001b\u0005\u0015\t%O]1z\u0003\u0015qw\u000eZ3t!\ryRjJ\u0001\nS:LGOT8eKN$\u0012!\u000f\u000b\u0003KQCQ!V\bA\u0002\u001d\n\u0001B]8pi:{G-Z\u0001\u0011g\u0016$X\u000f\u001d)bs2|\u0017\r\u001a(pI\u0016$\"!\u000f-\t\u000be\u0003\u0002\u0019A\u0014\u0002\t9|G-Z\u0001\taV\u001c\bNT8eKR\u0011\u0011\b\u0018\u0005\u00063F\u0001\raJ\u0001\ba>\u0004hj\u001c3f\u0003M\u0019X-\u0019:dQ:+\u0007\u0010\u001e,bYV,gj\u001c3f)\u0005\u0001\u0007CA\u0010b\u0013\t\u0011'DA\u0004C_>dW-\u00198\u0002\u000f!\f7OT3yiV\t\u0001\r")
public abstract class ChampBaseIterator<T extends Node<T>> {
    private int currentValueCursor = 0;
    private int currentValueLength = 0;
    private T currentValueNode;
    private int currentStackLevel = -1;
    private int[] nodeCursorsAndLengths;
    private T[] nodes;

    public int currentValueCursor() {
        return this.currentValueCursor;
    }

    public void currentValueCursor_$eq(int x$1) {
        this.currentValueCursor = x$1;
    }

    public int currentValueLength() {
        return this.currentValueLength;
    }

    public void currentValueLength_$eq(int x$1) {
        this.currentValueLength = x$1;
    }

    public T currentValueNode() {
        return this.currentValueNode;
    }

    public void currentValueNode_$eq(T x$1) {
        this.currentValueNode = x$1;
    }

    private void initNodes() {
        if (this.nodeCursorsAndLengths == null) {
            this.nodeCursorsAndLengths = new int[Node$.MODULE$.MaxDepth() * 2];
            this.nodes = new Node[Node$.MODULE$.MaxDepth()];
            return;
        }
    }

    private final void setupPayloadNode(T node) {
        this.currentValueNode_$eq(node);
        this.currentValueCursor_$eq(0);
        this.currentValueLength_$eq(((Node)node).payloadArity());
    }

    private final void pushNode(T node) {
        this.initNodes();
        ++this.currentStackLevel;
        int cursorIndex = this.currentStackLevel * 2;
        int lengthIndex = this.currentStackLevel * 2 + 1;
        this.nodes[this.currentStackLevel] = node;
        this.nodeCursorsAndLengths[cursorIndex] = 0;
        this.nodeCursorsAndLengths[lengthIndex] = ((Node)node).nodeArity();
    }

    private final void popNode() {
        --this.currentStackLevel;
    }

    private final boolean searchNextValueNode() {
        while (this.currentStackLevel >= 0) {
            int cursorIndex = this.currentStackLevel * 2;
            int nodeCursor = this.nodeCursorsAndLengths[cursorIndex];
            int lengthIndex = this.currentStackLevel * 2 + 1;
            int nodeLength = this.nodeCursorsAndLengths[lengthIndex];
            if (nodeCursor < nodeLength) {
                int[] nArray = this.nodeCursorsAndLengths;
                nArray[cursorIndex] = nArray[cursorIndex] + 1;
                Object nextNode = ((Node)this.nodes[this.currentStackLevel]).getNode(nodeCursor);
                if (((Node)nextNode).hasNodes()) {
                    this.pushNode(nextNode);
                }
                if (!((Node)nextNode).hasPayload()) continue;
                this.setupPayloadNode(nextNode);
                return true;
            }
            this.popNode();
        }
        return false;
    }

    public final boolean hasNext() {
        return this.currentValueCursor() < this.currentValueLength() || this.searchNextValueNode();
    }

    public ChampBaseIterator() {
    }

    public ChampBaseIterator(T rootNode) {
        this();
        if (((Node)rootNode).hasNodes()) {
            super.pushNode(rootNode);
        }
        if (((Node)rootNode).hasPayload()) {
            super.setupPayloadNode(rootNode);
        }
    }
}

