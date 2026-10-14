/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.Arrays;
import scala.collection.Stepper;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\reA\u0002\u0012$\u0003\u000393\u0006\u0003\u0005A\u0001\t\u0005\r\u0011\"\u0005B\u0011!)\u0005A!a\u0001\n#1\u0005\u0002\u0003'\u0001\u0005\u0003\u0005\u000b\u0015\u0002\"\t\u000b5\u0003A\u0011\u0001(\t\u000fu\u0004\u0001\u0019!C\t\u0003\"9a\u0010\u0001a\u0001\n#y\bbBA\u0002\u0001\u0001\u0006KA\u0011\u0005\t\u0003\u000b\u0001\u0001\u0019!C\t\u0003\"I\u0011q\u0001\u0001A\u0002\u0013E\u0011\u0011\u0002\u0005\b\u0003\u001b\u0001\u0001\u0015)\u0003C\u0011-\ty\u0001\u0001a\u0001\u0002\u0004%\t\"!\u0005\t\u0017\u0005M\u0001\u00011AA\u0002\u0013E\u0011Q\u0003\u0005\u000b\u00033\u0001\u0001\u0019!A!B\u0013a\u0006\u0002CA\u000e\u0001\u0001\u0007I\u0011B!\t\u0013\u0005u\u0001\u00011A\u0005\n\u0005}\u0001bBA\u0012\u0001\u0001\u0006KA\u0011\u0005\f\u0003K\u0001\u0001\u0019!a\u0001\n\u0013\t9\u0003C\u0006\u00020\u0001\u0001\r\u00111A\u0005\n\u0005E\u0002bCA\u001b\u0001\u0001\u0007\t\u0011)Q\u0005\u0003SA1\"a\u000e\u0001\u0001\u0004\u0005\r\u0011\"\u0003\u0002:!Y\u0011Q\b\u0001A\u0002\u0003\u0007I\u0011BA \u0011-\t\u0019\u0005\u0001a\u0001\u0002\u0003\u0006K!a\u000f\t\u000f\u0005\u0015\u0003\u0001\"\u0003\u0002H!9\u0011\u0011\n\u0001\u0005\u0002\u0005-\u0003bBA)\u0001\u00115\u00111\u000b\u0005\b\u00033\u0002AQBA.\u0011\u001d\ty\u0006\u0001C\u0007\u0003\u000fBq!!\u0019\u0001\t\u001b\t\u0019\u0007\u0003\u0004\u0002l\u0001!\t!\u0011\u0005\b\u0003[\u0002A\u0011AA8\u0011\u001d\t9\b\u0001D\u0001\u0003sBq!a\u001f\u0001\t\u000b\ti\bC\u0004\u0002\u0000\u0001!)!!!\u0003!\rC\u0017-\u001c9Ti\u0016\u0004\b/\u001a:CCN,'B\u0001\u0013&\u0003\u0011IW\u000e\u001d7\u000b\u0005\u0019:\u0013aB2p]Z,'\u000f\u001e\u0006\u0003Q%\n!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0013!B:dC2\fW#\u0002\u0017T;\u001et7c\u0001\u0001.cA\u0011afL\u0007\u0002S%\u0011\u0001'\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005IjdBA\u001a<\u001d\t!$H\u0004\u00026s5\taG\u0003\u00028q\u00051AH]8piz\u001a\u0001!C\u0001+\u0013\tA\u0013&\u0003\u0002=O\u000591\u000b^3qa\u0016\u0014\u0018B\u0001 @\u00059)eMZ5dS\u0016tGo\u00159mSRT!\u0001P\u0014\u0002\u000f5\f\u0007pU5{KV\t!\t\u0005\u0002/\u0007&\u0011A)\u000b\u0002\u0004\u0013:$\u0018aC7bqNK'0Z0%KF$\"a\u0012&\u0011\u00059B\u0015BA%*\u0005\u0011)f.\u001b;\t\u000f-\u0013\u0011\u0011!a\u0001\u0005\u0006\u0019\u0001\u0010J\u0019\u0002\u00115\f\u0007pU5{K\u0002\na\u0001P5oSRtDCA(}!\u0019\u0001\u0006!\u0015/g[6\t1\u0005\u0005\u0002S'2\u0001A!\u0002+\u0001\u0005\u0004)&!A!\u0012\u0005YK\u0006C\u0001\u0018X\u0013\tA\u0016FA\u0004O_RD\u0017N\\4\u0011\u00059R\u0016BA.*\u0005\r\te.\u001f\t\u0003%v#QA\u0018\u0001C\u0002}\u0013\u0011\u0001V\t\u0003-\u0002\u00042!\u00193]\u001b\u0005\u0011'BA2(\u0003%IW.\\;uC\ndW-\u0003\u0002fE\n!aj\u001c3f!\t\u0011v\rB\u0003i\u0001\t\u0007\u0011NA\u0002Tk\n\f\"A[-\u0011\u00059Z\u0017B\u00017*\u0005\u0011qU\u000f\u001c7\u0011\u0005IsG!B8\u0001\u0005\u0004\u0001(\u0001B*f[&\f\"AV9\u0013\u0007I4GO\u0002\u0003t\u0001\u0001\t(\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004gA;xuB1\u0001\u000bA)]mf\u0004\"AU<\u0005\u0013at\u0017\u0011!A\u0001\u0006\u0003)&aA0%cA\u0011!K\u001f\u0003\nw:\f\t\u0011!A\u0003\u0002U\u00131a\u0018\u00133\u0011\u0015\u0001E\u00011\u0001C\u0003I\u0019WO\u001d:f]R4\u0016\r\\;f\u0007V\u00148o\u001c:\u0002-\r,(O]3oiZ\u000bG.^3DkJ\u001cxN]0%KF$2aRA\u0001\u0011\u001dYe!!AA\u0002\t\u000b1cY;se\u0016tGOV1mk\u0016\u001cUO]:pe\u0002\n!cY;se\u0016tGOV1mk\u0016dUM\\4uQ\u000612-\u001e:sK:$h+\u00197vK2+gn\u001a;i?\u0012*\u0017\u000fF\u0002H\u0003\u0017AqaS\u0005\u0002\u0002\u0003\u0007!)A\ndkJ\u0014XM\u001c;WC2,X\rT3oORD\u0007%\u0001\tdkJ\u0014XM\u001c;WC2,XMT8eKV\tA,\u0001\u000bdkJ\u0014XM\u001c;WC2,XMT8eK~#S-\u001d\u000b\u0004\u000f\u0006]\u0001bB&\r\u0003\u0003\u0005\r\u0001X\u0001\u0012GV\u0014(/\u001a8u-\u0006dW/\u001a(pI\u0016\u0004\u0013!E2veJ,g\u000e^*uC\u000e\\G*\u001a<fY\u0006)2-\u001e:sK:$8\u000b^1dW2+g/\u001a7`I\u0015\fHcA$\u0002\"!91jDA\u0001\u0002\u0004\u0011\u0015AE2veJ,g\u000e^*uC\u000e\\G*\u001a<fY\u0002\nQC\\8eK\u000e+(o]8sg\u0006sG\rT3oORD7/\u0006\u0002\u0002*A!a&a\u000bC\u0013\r\ti#\u000b\u0002\u0006\u0003J\u0014\u0018-_\u0001\u001a]>$WmQ;sg>\u00148/\u00118e\u0019\u0016tw\r\u001e5t?\u0012*\u0017\u000fF\u0002H\u0003gA\u0001b\u0013\n\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\u0017]>$WmQ;sg>\u00148/\u00118e\u0019\u0016tw\r\u001e5tA\u0005)an\u001c3fgV\u0011\u00111\b\t\u0005]\u0005-B,A\u0005o_\u0012,7o\u0018\u0013fcR\u0019q)!\u0011\t\u0011-+\u0012\u0011!a\u0001\u0003w\taA\\8eKN\u0004\u0013!C5oSRtu\u000eZ3t)\u00059\u0015\u0001C5oSR\u0014vn\u001c;\u0015\u0007\u001d\u000bi\u0005\u0003\u0004\u0002Pa\u0001\r\u0001X\u0001\te>|GOT8eK\u0006\u00012/\u001a;vaB\u000b\u0017\u0010\\8bI:{G-\u001a\u000b\u0004\u000f\u0006U\u0003BBA,3\u0001\u0007A,\u0001\u0003o_\u0012,\u0017\u0001\u00039vg\"tu\u000eZ3\u0015\u0007\u001d\u000bi\u0006\u0003\u0004\u0002Xi\u0001\r\u0001X\u0001\ba>\u0004hj\u001c3f\u0003M\u0019X-\u0019:dQ:+\u0007\u0010\u001e,bYV,gj\u001c3f)\t\t)\u0007E\u0002/\u0003OJ1!!\u001b*\u0005\u001d\u0011un\u001c7fC:\fqb\u00195be\u0006\u001cG/\u001a:jgRL7m]\u0001\rKN$\u0018.\\1uKNK'0Z\u000b\u0003\u0003c\u00022ALA:\u0013\r\t)(\u000b\u0002\u0005\u0019>tw-A\u0005tK6L7\r\\8oKR\tQ.A\u0004iCN\u001cF/\u001a9\u0016\u0005\u0005\u0015\u0014\u0001\u0003;ssN\u0003H.\u001b;\u0015\u0003\u0019\u0004")
public abstract class ChampStepperBase<A, T extends Node<T>, Sub, Semi extends Sub>
implements Stepper.EfficientSplit {
    private int maxSize;
    private int currentValueCursor;
    private int currentValueLength;
    private T currentValueNode;
    private int currentStackLevel;
    private int[] nodeCursorsAndLengths;
    private T[] nodes;

    public int maxSize() {
        return this.maxSize;
    }

    public void maxSize_$eq(int x$1) {
        this.maxSize = x$1;
    }

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

    private int currentStackLevel() {
        return this.currentStackLevel;
    }

    private void currentStackLevel_$eq(int x$1) {
        this.currentStackLevel = x$1;
    }

    private int[] nodeCursorsAndLengths() {
        return this.nodeCursorsAndLengths;
    }

    private void nodeCursorsAndLengths_$eq(int[] x$1) {
        this.nodeCursorsAndLengths = x$1;
    }

    private T[] nodes() {
        return this.nodes;
    }

    private void nodes_$eq(T[] x$1) {
        this.nodes = x$1;
    }

    private void initNodes() {
        if (this.nodeCursorsAndLengths() == null) {
            this.nodeCursorsAndLengths_$eq(new int[Node$.MODULE$.MaxDepth() * 2]);
            this.nodes_$eq(new Node[Node$.MODULE$.MaxDepth()]);
            return;
        }
    }

    public void initRoot(T rootNode) {
        if (((Node)rootNode).hasNodes()) {
            this.pushNode(rootNode);
        }
        if (((Node)rootNode).hasPayload()) {
            this.setupPayloadNode(rootNode);
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
        this.currentStackLevel_$eq(this.currentStackLevel() + 1);
        int cursorIndex = this.currentStackLevel() * 2;
        int lengthIndex = this.currentStackLevel() * 2 + 1;
        this.nodes()[this.currentStackLevel()] = node;
        this.nodeCursorsAndLengths()[cursorIndex] = 0;
        this.nodeCursorsAndLengths()[lengthIndex] = ((Node)node).nodeArity();
    }

    private final void popNode() {
        this.currentStackLevel_$eq(this.currentStackLevel() - 1);
    }

    private final boolean searchNextValueNode() {
        while (this.currentStackLevel() >= 0) {
            int nodeLength;
            int cursorIndex = this.currentStackLevel() * 2;
            int lengthIndex = this.currentStackLevel() * 2 + 1;
            int nodeCursor = this.nodeCursorsAndLengths()[cursorIndex];
            if (nodeCursor < (nodeLength = this.nodeCursorsAndLengths()[lengthIndex])) {
                int[] nArray = this.nodeCursorsAndLengths();
                nArray[cursorIndex] = nArray[cursorIndex] + 1;
                Object nextNode = this.nodes()[this.currentStackLevel()].getNode(nodeCursor);
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

    public int characteristics() {
        return 0;
    }

    public long estimateSize() {
        if (this.hasStep()) {
            return this.maxSize();
        }
        return 0L;
    }

    public abstract Semi semiclone();

    public final boolean hasStep() {
        if (this.maxSize() > 0) {
            boolean ans;
            boolean bl = ans = this.currentValueCursor() < this.currentValueLength() || this.searchNextValueNode();
            if (!ans) {
                this.maxSize_$eq(0);
            }
            if (ans) {
                return true;
            }
        }
        return false;
    }

    public final Sub trySplit() {
        int fork;
        if (!this.hasStep()) {
            return null;
        }
        for (fork = 0; fork <= this.currentStackLevel() && this.nodeCursorsAndLengths()[2 * fork] >= this.nodeCursorsAndLengths()[2 * fork + 1]; ++fork) {
        }
        if (fork > this.currentStackLevel() && this.currentValueCursor() > this.currentValueLength() - 2) {
            return null;
        }
        Semi semi = this.semiclone();
        ((ChampStepperBase)semi).maxSize_$eq(this.maxSize());
        ((ChampStepperBase)semi).currentValueCursor_$eq(this.currentValueCursor());
        ((ChampStepperBase)semi).currentValueNode_$eq(this.currentValueNode());
        if (fork > this.currentStackLevel()) {
            super.currentStackLevel_$eq(-1);
            int i = this.currentValueCursor() + this.currentValueLength() >>> 1;
            ((ChampStepperBase)semi).currentValueLength_$eq(i);
            this.currentValueCursor_$eq(i);
        } else {
            int i;
            super.nodeCursorsAndLengths_$eq(Arrays.copyOf(this.nodeCursorsAndLengths(), this.nodeCursorsAndLengths().length));
            super.nodes_$eq(Arrays.copyOf(this.nodes(), this.nodes().length));
            super.currentStackLevel_$eq(this.currentStackLevel());
            ((ChampStepperBase)semi).currentValueLength_$eq(this.currentValueLength());
            super.nodeCursorsAndLengths()[2 * fork + 1] = i = this.nodeCursorsAndLengths()[2 * fork] + this.nodeCursorsAndLengths()[2 * fork + 1] >>> 1;
            for (int j = this.currentStackLevel(); j > fork; --j) {
                this.nodeCursorsAndLengths()[2 * j] = this.nodeCursorsAndLengths()[2 * j + 1];
            }
            this.nodeCursorsAndLengths()[2 * fork] = i;
            this.searchNextValueNode();
        }
        return (Sub)semi;
    }

    public ChampStepperBase(int maxSize) {
        this.maxSize = maxSize;
        this.currentValueCursor = 0;
        this.currentValueLength = 0;
        this.currentStackLevel = -1;
    }
}

