/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.nio.ByteBuffer;
import org.conscrypt.Preconditions;

final class BufferUtils {
    private BufferUtils() {
    }

    static void checkNotNull(ByteBuffer[] buffers) {
        for (ByteBuffer buffer : buffers) {
            if (buffer != null) continue;
            throw new IllegalArgumentException("Null buffer in array");
        }
    }

    static long remaining(ByteBuffer[] buffers) {
        long size = 0L;
        for (ByteBuffer buffer : buffers) {
            size += (long)buffer.remaining();
        }
        return size;
    }

    static void consume(ByteBuffer[] sourceBuffers, int toConsume) {
        for (ByteBuffer sourceBuffer : sourceBuffers) {
            int amount = Math.min(sourceBuffer.remaining(), toConsume);
            if (amount <= 0) continue;
            sourceBuffer.position(sourceBuffer.position() + amount);
            if ((toConsume -= amount) == 0) break;
        }
        if (toConsume > 0) {
            throw new IllegalArgumentException("toConsume > data size");
        }
    }

    static ByteBuffer getBufferLargerThan(ByteBuffer[] buffers, int minSize) {
        int length = buffers.length;
        for (int i = 0; i < length; ++i) {
            ByteBuffer buffer = buffers[i];
            int remaining = buffer.remaining();
            if (remaining <= 0) continue;
            if (remaining >= minSize) {
                return buffer;
            }
            for (int j = i + 1; j < length; ++j) {
                if (buffers[j].remaining() <= 0) continue;
                return null;
            }
            return buffer;
        }
        return null;
    }

    static ByteBuffer copyNoConsume(ByteBuffer[] buffers, ByteBuffer destination, int maxAmount) {
        Preconditions.checkArgument(destination.remaining() >= maxAmount, "Destination buffer too small");
        int needed = maxAmount;
        for (ByteBuffer buffer : buffers) {
            int remaining = buffer.remaining();
            if (remaining <= 0) continue;
            int oldPosition = buffer.position();
            if (remaining <= needed) {
                destination.put(buffer);
                needed -= remaining;
            } else {
                int oldLimit = buffer.limit();
                buffer.limit(buffer.position() + needed);
                destination.put(buffer);
                buffer.limit(oldLimit);
                needed = 0;
            }
            buffer.position(oldPosition);
            if (needed == 0) break;
        }
        destination.flip();
        return destination;
    }
}

