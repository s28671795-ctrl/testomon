/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.h2.store.fs.FileBaseDefault;
import org.h2.store.fs.FileUtils;

class FileAsync
extends FileBaseDefault {
    private final String name;
    private final AsynchronousFileChannel channel;

    /*
     * Loose catch block
     */
    private static <T> T complete(Future<T> future) throws IOException {
        boolean bl = false;
        while (true) {
            try {
                T t2 = future.get();
                if (bl) {
                    Thread.currentThread().interrupt();
                }
                return t2;
            }
            catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        catch (ExecutionException executionException) {
            throw new IOException(executionException.getCause());
        }
    }

    FileAsync(String string, String string2) throws IOException {
        this.name = string;
        this.channel = AsynchronousFileChannel.open(Paths.get(string, new String[0]), FileUtils.modeToOptions(string2), null, FileUtils.NO_ATTRIBUTES);
    }

    @Override
    public void implCloseChannel() throws IOException {
        this.channel.close();
    }

    @Override
    public long size() throws IOException {
        return this.channel.size();
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        return FileAsync.complete(this.channel.read(byteBuffer, l));
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        return FileAsync.complete(this.channel.write(byteBuffer, l));
    }

    @Override
    protected void implTruncate(long l) throws IOException {
        this.channel.truncate(l);
    }

    @Override
    public void force(boolean bl) throws IOException {
        this.channel.force(bl);
    }

    @Override
    public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        return this.channel.tryLock(l, l2, bl);
    }

    public String toString() {
        return "async:" + this.name;
    }
}

