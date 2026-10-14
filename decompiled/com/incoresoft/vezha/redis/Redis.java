/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.redis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Redis {
    private final Encoder writer;
    private final Parser reader;

    public Redis(Socket socket) throws IOException {
        this(socket, 65536, 65536);
    }

    public Redis(Socket socket, int inputBufferSize, int outputBufferSize) throws IOException {
        this(new BufferedInputStream(socket.getInputStream(), inputBufferSize), new BufferedOutputStream(socket.getOutputStream(), outputBufferSize));
    }

    public Redis(InputStream inputStream2, OutputStream outputStream2) {
        this.reader = new Parser(inputStream2);
        this.writer = new Encoder(outputStream2);
    }

    public <T> T call(Object ... args2) throws IOException {
        this.writer.write(Arrays.asList(args2));
        this.writer.flush();
        return this.read();
    }

    public <T> T read() throws IOException {
        return (T)this.reader.parse();
    }

    public Pipeline pipeline() {
        return new Pipeline(){
            private int n = 0;

            @Override
            public Pipeline call(String ... args2) throws IOException {
                Redis.this.writer.write(Arrays.asList((Object[])args2));
                Redis.this.writer.flush();
                ++this.n;
                return this;
            }

            @Override
            public List<Object> read() throws IOException {
                LinkedList<Object> ret = new LinkedList<Object>();
                while (this.n-- > 0) {
                    ret.add(Redis.this.reader.parse());
                }
                return ret;
            }
        };
    }

    public static void run(FailableConsumer<Redis, IOException> callback, String addr, int port) throws IOException {
        try (Socket s2 = new Socket(addr, port);){
            Redis.run(callback, s2);
        }
    }

    public static void run(FailableConsumer<Redis, IOException> callback, Socket s2) throws IOException {
        callback.accept(new Redis(s2));
    }

    static class Parser {
        private final InputStream input;

        Parser(InputStream input) {
            this.input = input;
        }

        Object parse() throws IOException, ProtocolException {
            Object ret;
            int read = this.input.read();
            switch (read) {
                case 43: {
                    ret = this.parseSimpleString();
                    break;
                }
                case 45: {
                    throw new ServerError(new String(this.parseSimpleString()));
                }
                case 58: {
                    ret = this.parseNumber();
                    break;
                }
                case 36: {
                    ret = this.parseBulkString();
                    break;
                }
                case 42: {
                    long len = this.parseNumber();
                    if (len == -1L) {
                        ret = null;
                        break;
                    }
                    LinkedList<Object> arr = new LinkedList<Object>();
                    for (long i = 0L; i < len; ++i) {
                        arr.add(this.parse());
                    }
                    ret = arr;
                    break;
                }
                case -1: {
                    return null;
                }
                default: {
                    throw new ProtocolException("Unexpected input: " + (byte)read);
                }
            }
            return ret;
        }

        private byte[] parseBulkString() throws IOException {
            long expectedLength = this.parseNumber();
            if (expectedLength == -1L) {
                return null;
            }
            if (expectedLength > Integer.MAX_VALUE) {
                throw new ProtocolException("Unsupported value length for bulk string");
            }
            int numBytes = (int)expectedLength;
            byte[] buffer = new byte[numBytes];
            int read = 0;
            while ((long)read < expectedLength) {
                read += this.input.read(buffer, read, numBytes - read);
            }
            if (this.input.read() != 13) {
                throw new ProtocolException("Expected CR");
            }
            if (this.input.read() != 10) {
                throw new ProtocolException("Expected LF");
            }
            return buffer;
        }

        private byte[] parseSimpleString() throws IOException {
            return this.scanCr(1024);
        }

        private long parseNumber() throws IOException {
            return Long.parseLong(new String(this.scanCr(1024)));
        }

        private byte[] scanCr(int size) throws IOException {
            int ch;
            int idx = 0;
            byte[] buffer = new byte[size];
            while ((ch = this.input.read()) != 13) {
                buffer[idx++] = (byte)ch;
                if (idx != size) continue;
                buffer = Arrays.copyOf(buffer, size *= 2);
            }
            if (this.input.read() != 10) {
                throw new ProtocolException("Expected LF");
            }
            return Arrays.copyOfRange(buffer, 0, idx);
        }

        static class ServerError
        extends IOException {
            ServerError(String msg) {
                super(msg);
            }
        }

        static class ProtocolException
        extends IOException {
            ProtocolException(String msg) {
                super(msg);
            }
        }
    }

    static class Encoder {
        private static final byte[] CRLF = new byte[]{13, 10};
        private final OutputStream out;

        Encoder(OutputStream out) {
            this.out = out;
        }

        void write(byte[] value) throws IOException {
            this.out.write(36);
            this.out.write(Long.toString(value.length).getBytes());
            this.out.write(CRLF);
            this.out.write(value);
            this.out.write(CRLF);
        }

        void write(long val) throws IOException {
            this.out.write(58);
            this.out.write(Long.toString(val).getBytes());
            this.out.write(CRLF);
        }

        void write(List<?> list) throws IOException, IllegalArgumentException {
            this.out.write(42);
            this.out.write(Long.toString(list.size()).getBytes());
            this.out.write(CRLF);
            for (Object o : list) {
                if (o instanceof byte[]) {
                    this.write((byte[])o);
                    continue;
                }
                if (o instanceof String) {
                    this.write(((String)o).getBytes());
                    continue;
                }
                if (o instanceof Long) {
                    this.write((Long)o);
                    continue;
                }
                if (o instanceof Integer) {
                    this.write(((Integer)o).longValue());
                    continue;
                }
                if (o instanceof List) {
                    this.write((List)o);
                    continue;
                }
                throw new IllegalArgumentException("Unexpected type " + o.getClass().getCanonicalName());
            }
        }

        void flush() throws IOException {
            this.out.flush();
        }
    }

    @FunctionalInterface
    public static interface FailableConsumer<T, E extends Throwable> {
        public void accept(T var1) throws E;
    }

    public static abstract class Pipeline {
        public abstract Pipeline call(String ... var1) throws IOException;

        public abstract List<Object> read() throws IOException;
    }
}

