/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3Aa\u0004\t\u0001+!)1\u0004\u0001C\u00019\u0019!!\u0006\u0001\u0003,\u0011\u0015Y\"\u0001\"\u0001-\u0011%y#\u00011AA\u0002\u0013\u0005\u0001\u0007C\u00052\u0005\u0001\u0007\t\u0019!C\u0001e!I\u0001H\u0001a\u0001\u0002\u0003\u0006Ka\b\u0005\ns\t\u0001\r\u00111A\u0005\u0002iB\u0011b\u000f\u0002A\u0002\u0003\u0007I\u0011\u0001\u001f\t\u0013y\u0012\u0001\u0019!A!B\u0013i\u0003BB \u0001A\u0003&Q\u0006\u0003\u0004A\u0001\u0001\u0006K!\f\u0005\u0007\u0003\u0002\u0001\u000b\u0015\u0002\"\t\u000b\u0015\u0003A\u0011\u0001$\t\u000b%\u0003A\u0011\u0001\u0019\u0003\u000f\rC\u0017M\u001c8fY*\u0011\u0011CE\u0001\u000bG>t7-\u001e:sK:$(\"A\n\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011a#I\n\u0003\u0001]\u0001\"\u0001G\r\u000e\u0003II!A\u0007\n\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQ\u0004E\u0002\u001f\u0001}i\u0011\u0001\u0005\t\u0003A\u0005b\u0001\u0001B\u0003#\u0001\t\u00071EA\u0001B#\t!s\u0005\u0005\u0002\u0019K%\u0011aE\u0005\u0002\b\u001d>$\b.\u001b8h!\tA\u0002&\u0003\u0002*%\t\u0019\u0011I\\=\u0003\u00151Kgn[3e\u0019&\u001cHo\u0005\u0002\u0003/Q\tQ\u0006\u0005\u0002/\u00055\t\u0001!\u0001\u0003fY\u0016lW#A\u0010\u0002\u0011\u0015dW-\\0%KF$\"a\r\u001c\u0011\u0005a!\u0014BA\u001b\u0013\u0005\u0011)f.\u001b;\t\u000f]*\u0011\u0011!a\u0001?\u0005\u0019\u0001\u0010J\u0019\u0002\u000b\u0015dW-\u001c\u0011\u0002\t9,\u0007\u0010^\u000b\u0002[\u0005Aa.\u001a=u?\u0012*\u0017\u000f\u0006\u00024{!9q\u0007CA\u0001\u0002\u0004i\u0013!\u00028fqR\u0004\u0013aB<sSR$XM\\\u0001\fY\u0006\u001cHo\u0016:jiR,g.\u0001\u0005oe\u0016\fG-\u001a:t!\tA2)\u0003\u0002E%\t\u0019\u0011J\u001c;\u0002\u000b]\u0014\u0018\u000e^3\u0015\u0005M:\u0005\"\u0002%\u000e\u0001\u0004y\u0012!\u0001=\u0002\tI,\u0017\r\u001a\u0015\u0007\u0001-su*\u0015*\u0011\u0005aa\u0015BA'\u0013\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005\u0001\u0016aN+tK\u0002\u0002'.\u0019<b]U$\u0018\u000e\u001c\u0018d_:\u001cWO\u001d:f]RtC*\u001b8lK\u0012$&/\u00198tM\u0016\u0014\u0018+^3vK\u0002\u0004\u0013N\\:uK\u0006$g&A\u0003tS:\u001cW-I\u0001T\u0003\u0019\u0011d&M\u001a/a\u0001")
public class Channel<A> {
    private LinkedList written;
    private LinkedList lastWritten;
    private int nreaders;

    public synchronized void write(A x) {
        this.lastWritten.elem_$eq(x);
        this.lastWritten.next_$eq(new LinkedList());
        this.lastWritten = this.lastWritten.next();
        if (this.nreaders > 0) {
            this.notify();
            return;
        }
    }

    public synchronized A read() {
        while (this.written.next() == null) {
            try {
                ++this.nreaders;
                this.wait();
            }
            finally {
                --this.nreaders;
            }
        }
        Object x = this.written.elem();
        this.written = this.written.next();
        return x;
    }

    public Channel() {
        this.lastWritten = this.written = new LinkedList();
        this.nreaders = 0;
    }

    private class LinkedList {
        private A elem;
        private LinkedList next;

        public A elem() {
            return this.elem;
        }

        public void elem_$eq(A x$1) {
            this.elem = x$1;
        }

        public LinkedList next() {
            return this.next;
        }

        public void next_$eq(LinkedList x$1) {
            this.next = x$1;
        }

        public /* synthetic */ Channel scala$concurrent$Channel$LinkedList$$$outer() {
            return Channel.this;
        }

        public LinkedList() {
            if (Channel.this == null) {
                throw null;
            }
        }
    }
}

