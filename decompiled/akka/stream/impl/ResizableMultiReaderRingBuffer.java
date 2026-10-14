/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ResizableMultiReaderRingBuffer$NothingToReadException$;
import java.io.Serializable;
import scala.Function0;
import scala.Predef$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.math.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-e!B\u0013'\u0001)b\u0003\u0002\u0003\u001b\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001c\t\u0011e\u0002!\u0011!Q\u0001\nYB\u0001B\u000f\u0001\u0003\u0006\u0004%\ta\u000f\u0005\n\u0003\u000b\u0001!\u0011!Q\u0001\nqBaA\u0011\u0001\u0005\u0002\u0005\u001d\u0001bBA\u0014\u0001\u0001\u0006IA\u000e\u0005\t\u0003S\u0001\u0001\u0015)\u0003\u0002,!9\u0011\u0011\u0007\u0001!B\u00131\u0004bBA\u001a\u0001\u0001\u0006KA\u000e\u0005\u0007\u0003k\u0001A\u0011\u0002:\t\r\u0005]\u0002\u0001\"\u0003s\u0011\u0019\tI\u0004\u0001C\u0001e\"9\u00111\b\u0001\u0005\u0002\u0005u\u0002bBA#\u0001\u0011\u0005\u0011Q\b\u0005\u0007\u0003\u000f\u0002A\u0011\u0001:\t\r\u0005%\u0003\u0001\"\u0001s\u0011\u001d\tY\u0005\u0001C\u0001\u0003\u001bBq!a\u0015\u0001\t\u0003\t)\u0006C\u0004\u0002Z\u0001!\t!a\u0017\t\u000f\u0005\u0005\u0004\u0001\"\u0001\u0002d!9\u0011q\r\u0001\u0005\u0002\u0005%\u0004bBA7\u0001\u0011%\u0011q\u000e\u0005\b\u0003c\u0002A\u0011CA:\u0011\u001d\t)\b\u0001C!\u0003o:aa\u0010\u0014\t\u0002)\u0002eAB\u0013'\u0011\u0003Q\u0013\tC\u0003C5\u0011\u00051iB\u0003E5!\u0005QIB\u0003H5!\u0005\u0001\nC\u0003C;\u0011\u0005Q\fC\u0004_;\u0005\u0005I\u0011B0\u0007\u000f!T\u0002\u0013aI\u0001S\")!\b\tD\u0001U\u001a9qN\u0007I\u0001$\u0003\u0001\b\"B9#\r\u0003\u0011\b\"B:#\r\u0003!(A\b*fg&T\u0018M\u00197f\u001bVdG/\u001b*fC\u0012,'OU5oO\n+hMZ3s\u0015\t9\u0003&\u0001\u0003j[Bd'BA\u0015+\u0003\u0019\u0019HO]3b[*\t1&\u0001\u0003bW.\fWcA\u0017\u0002\u0010M\u0011\u0001A\f\t\u0003_Ij\u0011\u0001\r\u0006\u0002c\u0005)1oY1mC&\u00111\u0007\r\u0002\u0007\u0003:L(+\u001a4\u0002\u0017%t\u0017\u000e^5bYNK'0Z\u0002\u0001!\tys'\u0003\u00029a\t\u0019\u0011J\u001c;\u0002\u000f5\f\u0007pU5{K\u000691-\u001e:t_J\u001cX#\u0001\u001f\u0011\u0005u\u0002cB\u0001 \u001a\u001b\u00051\u0013A\b*fg&T\u0018M\u00197f\u001bVdG/\u001b*fC\u0012,'OU5oO\n+hMZ3s!\tq$d\u0005\u0002\u001b]\u00051A(\u001b8jiz\"\u0012\u0001Q\u0001\u0017\u001d>$\b.\u001b8h)>\u0014V-\u00193Fq\u000e,\u0007\u000f^5p]B\u0011a)H\u0007\u00025\t1bj\u001c;iS:<Gk\u001c*fC\u0012,\u0005pY3qi&|gnE\u0002\u001e\u0013V\u0003\"A\u0013*\u000f\u0005-\u0003fB\u0001'P\u001b\u0005i%B\u0001(6\u0003\u0019a$o\\8u}%\t\u0011'\u0003\u0002Ra\u00059\u0001/Y2lC\u001e,\u0017BA*U\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|gN\u0003\u0002RaA\u0011akW\u0007\u0002/*\u0011\u0001,W\u0001\bG>tGO]8m\u0015\tQ\u0006'\u0001\u0003vi&d\u0017B\u0001/X\u00051qun\u0015;bG.$&/Y2f)\u0005)\u0015\u0001D<sSR,'+\u001a9mC\u000e,G#\u00011\u0011\u0005\u00054W\"\u00012\u000b\u0005\r$\u0017\u0001\u00027b]\u001eT\u0011!Z\u0001\u0005U\u00064\u0018-\u0003\u0002hE\n1qJ\u00196fGR\u0014qaQ;sg>\u00148o\u0005\u0002!]U\t1\u000eE\u0002KY:L!!\u001c+\u0003\t1K7\u000f\u001e\t\u0003\r\n\u0012aaQ;sg>\u00148C\u0001\u0012/\u0003\u0019\u0019WO]:peV\ta'\u0001\u0006dkJ\u001cxN]0%KF$\"!\u001e=\u0011\u0005=2\u0018BA<1\u0005\u0011)f.\u001b;\t\u000be$\u0003\u0019\u0001\u001c\u0002\u0005%D\bF\u0001\u000e|!\tax0D\u0001~\u0015\tq(&\u0001\u0006b]:|G/\u0019;j_:L1!!\u0001~\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005eY\u0018\u0001C2veN|'o\u001d\u0011\u0015\u0011\u0005%\u0011\u0011EA\u0012\u0003K\u0001BA\u0010\u0001\u0002\fA!\u0011QBA\b\u0019\u0001!q!!\u0005\u0001\u0005\u0004\t\u0019BA\u0001U#\u0011\t)\"a\u0007\u0011\u0007=\n9\"C\u0002\u0002\u001aA\u0012qAT8uQ&tw\rE\u00020\u0003;I1!a\b1\u0005\r\te.\u001f\u0005\u0006i\u0015\u0001\rA\u000e\u0005\u0006s\u0015\u0001\rA\u000e\u0005\u0006u\u0015\u0001\r\u0001P\u0001\u000b[\u0006D8+\u001b>f\u0005&$\u0018!B1se\u0006L\b#B\u0018\u0002.\u0005m\u0011bAA\u0018a\t)\u0011I\u001d:bs\u00069qO]5uK&C\u0018A\u0002:fC\u0012L\u00050\u0001\u0004mK:\u0014\u0015\u000e^\u0001\u0005[\u0006\u001c8.\u0001\u0003tSj,\u0017aB5t\u000b6\u0004H/_\u000b\u0003\u0003\u007f\u00012aLA!\u0013\r\t\u0019\u0005\r\u0002\b\u0005>|G.Z1o\u0003!qwN\\#naRL\u0018\u0001F5n[\u0016$\u0017.\u0019;fYf\fe/Y5mC\ndW-\u0001\u0007nCb\fe/Y5mC\ndW-A\u0003d_VtG\u000fF\u00027\u0003\u001fBa!]\tA\u0002\u0005E\u0003CA\u001f#\u0003)Ig.\u001b;DkJ\u001cxN\u001d\u000b\u0004k\u0006]\u0003BB9\u0013\u0001\u0004\t\t&A\u0003xe&$X\r\u0006\u0003\u0002@\u0005u\u0003bBA0'\u0001\u0007\u00111B\u0001\u0006m\u0006dW/Z\u0001\u0005e\u0016\fG\r\u0006\u0003\u0002\f\u0005\u0015\u0004BB9\u0015\u0001\u0004\t\t&A\bp]\u000e+(o]8s%\u0016lwN^3e)\r)\u00181\u000e\u0005\u0007cV\u0001\r!!\u0015\u0002\u0019U\u0004H-\u0019;f%\u0016\fG-\u0013=\u0015\u0003U\fq\"\u001e8eKJd\u00170\u001b8h\u0003J\u0014\u0018-_\u000b\u0003\u0003W\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003s\u0002B!a\u001f\u0002\u0004:!\u0011QPA@!\ta\u0005'C\u0002\u0002\u0002B\na\u0001\u0015:fI\u00164\u0017\u0002BAC\u0003\u000f\u0013aa\u0015;sS:<'bAAAa!\u0012\u0001a\u001f")
@InternalApi
public class ResizableMultiReaderRingBuffer<T> {
    private final Cursors cursors;
    private final int maxSizeBit;
    private Object[] array;
    private int writeIx;
    private int readIx;

    public Cursors cursors() {
        return this.cursors;
    }

    private int lenBit() {
        return Integer.numberOfTrailingZeros(this.array.length);
    }

    private int mask() {
        return Integer.MAX_VALUE >> 31 - this.lenBit();
    }

    public int size() {
        return this.writeIx - this.readIx;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean nonEmpty() {
        return !this.isEmpty();
    }

    public int immediatelyAvailable() {
        return this.array.length - this.size();
    }

    public int maxAvailable() {
        return (1 << this.maxSizeBit) - this.size();
    }

    public int count(Cursor cursor) {
        return this.writeIx - cursor.cursor();
    }

    public void initCursor(Cursor cursor) {
        cursor.cursor_$eq(this.readIx);
    }

    public boolean write(T value) {
        if (this.size() < this.array.length) {
            this.array[this.writeIx & this.mask()] = value;
            ++this.writeIx;
            return true;
        }
        if (this.lenBit() < this.maxSizeBit) {
            int r = this.readIx & this.mask();
            Object[] newArray = new Object[this.array.length << 1];
            System.arraycopy(this.array, r, newArray, 0, this.array.length - r);
            System.arraycopy(this.array, 0, newArray, this.array.length - r, r);
            this.rebaseCursors$1(this.cursors().cursors());
            this.array = newArray;
            int w = this.size();
            this.array[w & this.mask()] = value;
            this.writeIx = w + 1;
            this.readIx = 0;
            return true;
        }
        return false;
    }

    public T read(Cursor cursor) {
        int c = cursor.cursor();
        if (c - this.writeIx < 0) {
            cursor.cursor_$eq(cursor.cursor() + 1);
            Object ret = this.array[c & this.mask()];
            if (c == this.readIx) {
                this.updateReadIx();
            }
            return (T)ret;
        }
        throw ResizableMultiReaderRingBuffer$NothingToReadException$.MODULE$;
    }

    public void onCursorRemoved(Cursor cursor) {
        if (cursor.cursor() == this.readIx) {
            this.updateReadIx();
            return;
        }
    }

    private void updateReadIx() {
        int newReadIx = this.writeIx + this.minCursor$1(this.cursors().cursors(), 0);
        while (this.readIx != newReadIx) {
            this.array[this.readIx & this.mask()] = null;
            ++this.readIx;
        }
    }

    public Object[] underlyingArray() {
        return this.array;
    }

    public String toString() {
        return new StringBuilder(66).append("ResizableMultiReaderRingBuffer(size=").append(this.size()).append(", writeIx=").append(this.writeIx).append(", readIx=").append(this.readIx).append(", cursors=").append(this.cursors().cursors().size()).append(")").toString();
    }

    private final void rebaseCursors$1(List remaining) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            Cursor head = (Cursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            head.cursor_$eq(head.cursor() - this.readIx);
            remaining = tail;
        }
    }

    private final int minCursor$1(List remaining, int result) {
        List list;
        while ((list = remaining) instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            Cursor head = (Cursor)$colon$colon.head();
            List tail = $colon$colon.next$access$1();
            result = package$.MODULE$.min(head.cursor() - this.writeIx, result);
            remaining = tail;
        }
        return result;
    }

    public ResizableMultiReaderRingBuffer(int initialSize, int maxSize, Cursors cursors) {
        this.cursors = cursors;
        Predef$.MODULE$.require(Integer.lowestOneBit(maxSize) == maxSize && 0 < maxSize && maxSize <= 0x3FFFFFFF, (Function0<Object>)(Function0<String> & Serializable)() -> "maxSize must be a power of 2 that is > 0 and < Int.MaxValue/2");
        Predef$.MODULE$.require(Integer.lowestOneBit(initialSize) == initialSize && 0 < initialSize && initialSize <= maxSize, (Function0<Object>)(Function0<String> & Serializable)() -> "initialSize must be a power of 2 that is > 0 and <= maxSize");
        this.maxSizeBit = Integer.numberOfTrailingZeros(maxSize);
        this.array = new Object[initialSize];
        this.writeIx = 0;
        this.readIx = 0;
    }

    public static interface Cursor {
        public int cursor();

        public void cursor_$eq(int var1);
    }

    public static interface Cursors {
        public List<Cursor> cursors();
    }
}

