/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Outputs;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%2\u0001\u0002B\u0003\u0011\u0002\u0007\u0005\u0011b\u0003\u0005\u0006-\u0001!\t\u0001\u0007\u0005\b9\u0001\u0011\r\u0011\"\u0011\u001e\u0011\u0015\t\u0003\u0001\"\u0011\u001e\u0005m!UMZ1vYR|U\u000f\u001e9viR\u0013\u0018M\\:gKJ\u001cF/\u0019;fg*\u0011aaB\u0001\u0005S6\u0004HN\u0003\u0002\t\u0013\u000511\u000f\u001e:fC6T\u0011AC\u0001\u0005C.\\\u0017mE\u0002\u0001\u0019I\u0001\"!\u0004\t\u000e\u00039Q\u0011aD\u0001\u0006g\u000e\fG.Y\u0005\u0003#9\u0011a!\u00118z%\u00164\u0007CA\n\u0015\u001b\u0005)\u0011BA\u000b\u0006\u0005\u001dyU\u000f\u001e9viN\fa\u0001J5oSR$3\u0001\u0001\u000b\u00023A\u0011QBG\u0005\u000379\u0011A!\u00168ji\u0006Ya*Z3eg\u0012+W.\u00198e+\u0005q\u0002CA\n \u0013\t\u0001SAA\u0007Ue\u0006t7OZ3s'R\fG/Z\u0001\u0014\u001d\u0016,Gm\u001d#f[\u0006tGm\u0014:DC:\u001cW\r\u001c\u0015\u0003\u0001\r\u0002\"\u0001J\u0014\u000e\u0003\u0015R!AJ\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002)K\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public interface DefaultOutputTransferStates
extends Outputs {
    public void akka$stream$impl$DefaultOutputTransferStates$_setter_$NeedsDemand_$eq(TransferState var1);

    @Override
    public TransferState NeedsDemand();

    public static /* synthetic */ TransferState NeedsDemandOrCancel$(DefaultOutputTransferStates $this) {
        return $this.NeedsDemandOrCancel();
    }

    @Override
    default public TransferState NeedsDemandOrCancel() {
        return new TransferState(this){
            private final /* synthetic */ DefaultOutputTransferStates $outer;

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
                return this.$outer.demandAvailable() || this.$outer.isClosed();
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
        };
    }

    public static void $init$(DefaultOutputTransferStates $this) {
        $this.akka$stream$impl$DefaultOutputTransferStates$_setter_$NeedsDemand_$eq(new TransferState($this){
            private final /* synthetic */ DefaultOutputTransferStates $outer;

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
                return this.$outer.demandAvailable();
            }

            public boolean isCompleted() {
                return this.$outer.isClosed();
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

