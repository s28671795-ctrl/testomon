/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.BufferPool;
import akka.io.DirectByteBufferPool$;
import java.nio.ByteBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3QAE\n\u0001+]A\u0001B\t\u0001\u0003\u0002\u0003\u0006I\u0001\n\u0005\tO\u0001\u0011\t\u0011)A\u0005I!)\u0001\u0006\u0001C\u0001S!1Q\u0006\u0001Q\u0001\n9Ba!\u000f\u0001!B\u0013!\u0003\"\u0002\u001e\u0001\t\u0003Y\u0004\"\u0002\u001f\u0001\t\u0003i\u0004\"B\"\u0001\t\u0013!\u0005\"B$\u0001\t\u001bY\u0004\"\u0002%\u0001\t\u001bI\u0005\"B&\u0001\t\u001bauAB(\u0014\u0011\u0003)\u0002K\u0002\u0004\u0013'!\u0005Q#\u0015\u0005\u0006Q5!\tA\u0015\u0005\b'6\u0011\r\u0011\"\u0003U\u0011\u0019AV\u0002)A\u0005+\")1*\u0004C\u00013\n!B)\u001b:fGR\u0014\u0015\u0010^3Ck\u001a4WM\u001d)p_2T!\u0001F\u000b\u0002\u0005%|'\"\u0001\f\u0002\t\u0005\\7.Y\n\u0004\u0001aq\u0002CA\r\u001d\u001b\u0005Q\"\"A\u000e\u0002\u000bM\u001c\u0017\r\\1\n\u0005uQ\"AB!osJ+g\r\u0005\u0002 A5\t1#\u0003\u0002\"'\tQ!)\u001e4gKJ\u0004vn\u001c7\u0002#\u0011,g-Y;mi\n+hMZ3s'&TXm\u0001\u0001\u0011\u0005e)\u0013B\u0001\u0014\u001b\u0005\rIe\u000e^\u0001\u000f[\u0006D\bk\\8m\u000b:$(/[3t\u0003\u0019a\u0014N\\5u}Q\u0019!f\u000b\u0017\u0011\u0005}\u0001\u0001\"\u0002\u0012\u0004\u0001\u0004!\u0003\"B\u0014\u0004\u0001\u0004!\u0013\u0001\u00029p_2\u00042!G\u00182\u0013\t\u0001$DA\u0003BeJ\f\u0017\u0010\u0005\u00023o5\t1G\u0003\u00025k\u0005\u0019a.[8\u000b\u0003Y\nAA[1wC&\u0011\u0001h\r\u0002\u000b\u0005f$XMQ;gM\u0016\u0014\u0018!\u00042vM\u001a,'o]%o!>|G.A\u0004bGF,\u0018N]3\u0015\u0003E\nqA]3mK\u0006\u001cX\r\u0006\u0002?\u0003B\u0011\u0011dP\u0005\u0003\u0001j\u0011A!\u00168ji\")!i\u0002a\u0001c\u0005\u0019!-\u001e4\u0002\u0011\u0005dGn\\2bi\u0016$\"!M#\t\u000b\u0019C\u0001\u0019\u0001\u0013\u0002\tML'0Z\u0001\u0013i\u0006\\WMQ;gM\u0016\u0014hI]8n!>|G.A\tpM\u001a,'OQ;gM\u0016\u0014Hk\u001c)p_2$\"A\u0010&\t\u000b\tS\u0001\u0019A\u0019\u00021Q\u0014\u0018p\u00117fC:$\u0015N]3di\nKH/\u001a\"vM\u001a,'\u000f\u0006\u0002?\u001b\")aj\u0003a\u0001c\u0005iAo\u001c\"f\t\u0016\u001cHO]8zK\u0012\fA\u0003R5sK\u000e$()\u001f;f\u0005V4g-\u001a:Q_>d\u0007CA\u0010\u000e'\ti\u0001\u0004F\u0001Q\u0003E\u0019E.Z1o\t&\u0014Xm\u0019;Ck\u001a4WM]\u000b\u0002+B!\u0011DV\u0019?\u0013\t9&DA\u0005Gk:\u001cG/[8oc\u0005\u00112\t\\3b]\u0012K'/Z2u\u0005V4g-\u001a:!)\tq$\fC\u0003\\#\u0001\u0007\u0011'\u0001\u0006csR,')\u001e4gKJ\u0004")
public class DirectByteBufferPool
implements BufferPool {
    private final int defaultBufferSize;
    private final int maxPoolEntries;
    private final ByteBuffer[] pool;
    private int buffersInPool;

    @Override
    public ByteBuffer acquire() {
        return this.takeBufferFromPool();
    }

    @Override
    public void release(ByteBuffer buf) {
        this.offerBufferToPool(buf);
    }

    private ByteBuffer allocate(int size) {
        return ByteBuffer.allocateDirect(size);
    }

    private final ByteBuffer takeBufferFromPool() {
        ByteBuffer[] byteBufferArray = this.pool;
        synchronized (this.pool) {
            ByteBuffer byteBuffer;
            if (this.buffersInPool > 0) {
                --this.buffersInPool;
                byteBuffer = this.pool[this.buffersInPool];
            } else {
                byteBuffer = null;
            }
            ByteBuffer byteBuffer2 = byteBuffer;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            ByteBuffer buffer = byteBuffer2;
            if (buffer == null) {
                return this.allocate(this.defaultBufferSize);
            }
            buffer.clear();
            return buffer;
        }
    }

    private final void offerBufferToPool(ByteBuffer buf) {
        ByteBuffer[] byteBufferArray = this.pool;
        synchronized (this.pool) {
            boolean bl;
            if (this.buffersInPool < this.maxPoolEntries) {
                this.pool[this.buffersInPool] = buf;
                ++this.buffersInPool;
                bl = false;
            } else {
                bl = true;
            }
            boolean bl2 = bl;
            // ** MonitorExit[var3_2] (shouldn't be in output)
            boolean clean = bl2;
            if (clean) {
                this.tryCleanDirectByteBuffer(buf);
                return;
            }
            return;
        }
    }

    private final void tryCleanDirectByteBuffer(ByteBuffer toBeDestroyed) {
        DirectByteBufferPool$.MODULE$.tryCleanDirectByteBuffer(toBeDestroyed);
    }

    public DirectByteBufferPool(int defaultBufferSize, int maxPoolEntries) {
        this.defaultBufferSize = defaultBufferSize;
        this.maxPoolEntries = maxPoolEntries;
        this.pool = new ByteBuffer[maxPoolEntries];
        this.buffersInPool = 0;
    }
}

