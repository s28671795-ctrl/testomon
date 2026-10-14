/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Inputs;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%2\u0001\u0002B\u0003\u0011\u0002\u0007\u0005\u0011b\u0003\u0005\u0006-\u0001!\t\u0001\u0007\u0005\b9\u0001\u0011\r\u0011\"\u0011\u001e\u0011\u001d\t\u0003A1A\u0005Bu\u0011!\u0004R3gCVdG/\u00138qkR$&/\u00198tM\u0016\u00148\u000b^1uKNT!AB\u0004\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u0011%\taa\u001d;sK\u0006l'\"\u0001\u0006\u0002\t\u0005\\7.Y\n\u0004\u00011\u0011\u0002CA\u0007\u0011\u001b\u0005q!\"A\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Eq!AB!osJ+g\r\u0005\u0002\u0014)5\tQ!\u0003\u0002\u0016\u000b\t1\u0011J\u001c9viN\fa\u0001J5oSR$3\u0001\u0001\u000b\u00023A\u0011QBG\u0005\u000379\u0011A!\u00168ji\u0006Qa*Z3eg&s\u0007/\u001e;\u0016\u0003y\u0001\"aE\u0010\n\u0005\u0001*!!\u0004+sC:\u001ch-\u001a:Ti\u0006$X-\u0001\u000bOK\u0016$7/\u00138qkR|%oQ8na2,G/\u001a\u0015\u0003\u0001\r\u0002\"\u0001J\u0014\u000e\u0003\u0015R!AJ\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002)K\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public interface DefaultInputTransferStates
extends Inputs {
    public void akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInput_$eq(TransferState var1);

    public void akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInputOrComplete_$eq(TransferState var1);

    @Override
    public TransferState NeedsInput();

    @Override
    public TransferState NeedsInputOrComplete();

    public static void $init$(DefaultInputTransferStates $this) {
        $this.akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInput_$eq(new TransferState($this){
            private final /* synthetic */ DefaultInputTransferStates $outer;

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
                return this.$outer.inputsAvailable();
            }

            public boolean isCompleted() {
                return this.$outer.inputsDepleted();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        });
        $this.akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInputOrComplete_$eq(new TransferState($this){
            private final /* synthetic */ DefaultInputTransferStates $outer;

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
                return this.$outer.inputsAvailable() || this.$outer.inputsDepleted();
            }

            public boolean isCompleted() {
                return false;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        });
    }
}

