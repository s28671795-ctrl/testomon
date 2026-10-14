/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.file;

import java.math.BigInteger;
import java.util.Objects;

public class Counters {
    public static Counter bigIntegerCounter() {
        return new BigIntegerCounter();
    }

    public static PathCounters bigIntegerPathCounters() {
        return new BigIntegerPathCounters();
    }

    public static Counter longCounter() {
        return new LongCounter();
    }

    public static PathCounters longPathCounters() {
        return new LongPathCounters();
    }

    public static interface PathCounters {
        public Counter getByteCounter();

        public Counter getDirectoryCounter();

        public Counter getFileCounter();
    }

    private static class LongPathCounters
    extends AbstractPathCounters {
        protected LongPathCounters() {
            super(Counters.longCounter(), Counters.longCounter(), Counters.longCounter());
        }
    }

    private static class LongCounter
    implements Counter {
        private long value;

        private LongCounter() {
        }

        @Override
        public void add(long add) {
            this.value += add;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Counter)) {
                return false;
            }
            Counter other = (Counter)obj;
            return this.value == other.get();
        }

        @Override
        public long get() {
            return this.value;
        }

        @Override
        public BigInteger getBigInteger() {
            return BigInteger.valueOf(this.value);
        }

        @Override
        public Long getLong() {
            return this.value;
        }

        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public void increment() {
            ++this.value;
        }

        public String toString() {
            return Long.toString(this.value);
        }
    }

    public static interface Counter {
        public void add(long var1);

        public long get();

        public BigInteger getBigInteger();

        public Long getLong();

        public void increment();
    }

    private static class BigIntegerPathCounters
    extends AbstractPathCounters {
        protected BigIntegerPathCounters() {
            super(Counters.bigIntegerCounter(), Counters.bigIntegerCounter(), Counters.bigIntegerCounter());
        }
    }

    private static class BigIntegerCounter
    implements Counter {
        private BigInteger value = BigInteger.ZERO;

        private BigIntegerCounter() {
        }

        @Override
        public void add(long val) {
            this.value = this.value.add(BigInteger.valueOf(val));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Counter)) {
                return false;
            }
            Counter other = (Counter)obj;
            return Objects.equals(this.value, other.getBigInteger());
        }

        @Override
        public long get() {
            return this.value.longValueExact();
        }

        @Override
        public BigInteger getBigInteger() {
            return this.value;
        }

        @Override
        public Long getLong() {
            return this.value.longValueExact();
        }

        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public void increment() {
            this.value = this.value.add(BigInteger.ONE);
        }

        public String toString() {
            return this.value.toString();
        }
    }

    private static class AbstractPathCounters
    implements PathCounters {
        private final Counter byteCounter;
        private final Counter directoryCounter;
        private final Counter fileCounter;

        protected AbstractPathCounters(Counter byteCounter, Counter directoryCounter, Counter fileCounter) {
            this.byteCounter = byteCounter;
            this.directoryCounter = directoryCounter;
            this.fileCounter = fileCounter;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AbstractPathCounters)) {
                return false;
            }
            AbstractPathCounters other = (AbstractPathCounters)obj;
            return Objects.equals(this.byteCounter, other.byteCounter) && Objects.equals(this.directoryCounter, other.directoryCounter) && Objects.equals(this.fileCounter, other.fileCounter);
        }

        @Override
        public Counter getByteCounter() {
            return this.byteCounter;
        }

        @Override
        public Counter getDirectoryCounter() {
            return this.directoryCounter;
        }

        @Override
        public Counter getFileCounter() {
            return this.fileCounter;
        }

        public int hashCode() {
            return Objects.hash(this.byteCounter, this.directoryCounter, this.fileCounter);
        }

        public String toString() {
            return String.format("%,d files, %,d directories, %,d bytes", this.fileCounter.get(), this.directoryCounter.get(), this.byteCounter.get());
        }
    }
}

