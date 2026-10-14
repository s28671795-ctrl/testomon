/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2\u0001b\u0002\u0005\u0011\u0002\u0007\u0005AB\u0004\u0005\u0006+\u0001!\ta\u0006\u0005\u00067\u00011\t\u0001\b\u0005\u0006A\u00011\t\u0001\b\u0005\u0006C\u0001!\t\u0001\b\u0005\u0006E\u0001!\ta\t\u0005\u0006Q\u0001!\t!\u000b\u0002\u000e)J\fgn\u001d4feN#\u0018\r^3\u000b\u0005%Q\u0011\u0001B5na2T!a\u0003\u0007\u0002\rM$(/Z1n\u0015\u0005i\u0011\u0001B1lW\u0006\u001c\"\u0001A\b\u0011\u0005A\u0019R\"A\t\u000b\u0003I\tQa]2bY\u0006L!\u0001F\t\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uI\r\u0001A#\u0001\r\u0011\u0005AI\u0012B\u0001\u000e\u0012\u0005\u0011)f.\u001b;\u0002\u000f%\u001c(+Z1esV\tQ\u0004\u0005\u0002\u0011=%\u0011q$\u0005\u0002\b\u0005>|G.Z1o\u0003-I7oQ8na2,G/\u001a3\u0002\u0019%\u001cX\t_3dkR\f'\r\\3\u0002\u0011\u0011\u0012\u0017M\u001d\u0013cCJ$\"\u0001\n\u0014\u0011\u0005\u0015\u0002Q\"\u0001\u0005\t\u000b\u001d*\u0001\u0019\u0001\u0013\u0002\u000b=$\b.\u001a:\u0002\u0011\u0011\nW\u000e\u001d\u0013b[B$\"\u0001\n\u0016\t\u000b\u001d2\u0001\u0019\u0001\u0013)\u0005\u0001a\u0003CA\u00171\u001b\u0005q#BA\u0018\r\u0003)\tgN\\8uCRLwN\\\u0005\u0003c9\u00121\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public interface TransferState {
    public boolean isReady();

    public boolean isCompleted();

    public static /* synthetic */ boolean isExecutable$(TransferState $this) {
        return $this.isExecutable();
    }

    default public boolean isExecutable() {
        return this.isReady() && !this.isCompleted();
    }

    public static /* synthetic */ TransferState $bar$bar$(TransferState $this, TransferState other) {
        return $this.$bar$bar(other);
    }

    default public TransferState $bar$bar(TransferState other) {
        return new TransferState(this, other){
            private final /* synthetic */ TransferState $outer;
            private final TransferState other$1;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            public boolean isReady() {
                return this.$outer.isReady() || this.other$1.isReady();
            }

            public boolean isCompleted() {
                return this.$outer.isCompleted() && this.other$1.isCompleted();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.other$1 = other$1;
                TransferState.$init$(this);
            }
        };
    }

    public static /* synthetic */ TransferState $amp$amp$(TransferState $this, TransferState other) {
        return $this.$amp$amp(other);
    }

    default public TransferState $amp$amp(TransferState other) {
        return new TransferState(this, other){
            private final /* synthetic */ TransferState $outer;
            private final TransferState other$2;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            public boolean isReady() {
                return this.$outer.isReady() && this.other$2.isReady();
            }

            public boolean isCompleted() {
                return this.$outer.isCompleted() || this.other$2.isCompleted();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.other$2 = other$2;
                TransferState.$init$(this);
            }
        };
    }

    public static void $init$(TransferState $this) {
    }
}

