/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2={"Lio/javalin/plugin/bundled/PortResult;", "", "()V", "ErrorState", "NoPortSpecified", "PortSpecified", "Lio/javalin/plugin/bundled/PortResult$ErrorState;", "Lio/javalin/plugin/bundled/PortResult$NoPortSpecified;", "Lio/javalin/plugin/bundled/PortResult$PortSpecified;", "javalin"})
public abstract class PortResult {
    private PortResult() {
    }

    public /* synthetic */ PortResult(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/plugin/bundled/PortResult$ErrorState;", "Lio/javalin/plugin/bundled/PortResult;", "()V", "InvalidOrigin", "InvalidPort", "Lio/javalin/plugin/bundled/PortResult$ErrorState$InvalidOrigin;", "Lio/javalin/plugin/bundled/PortResult$ErrorState$InvalidPort;", "javalin"})
    public static abstract class ErrorState
    extends PortResult {
        private ErrorState() {
            super(null);
        }

        public /* synthetic */ ErrorState(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/PortResult$ErrorState$InvalidOrigin;", "Lio/javalin/plugin/bundled/PortResult$ErrorState;", "()V", "javalin"})
        public static final class InvalidOrigin
        extends ErrorState {
            @NotNull
            public static final InvalidOrigin INSTANCE = new InvalidOrigin();

            private InvalidOrigin() {
                super(null);
            }
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/PortResult$ErrorState$InvalidPort;", "Lio/javalin/plugin/bundled/PortResult$ErrorState;", "()V", "javalin"})
        public static final class InvalidPort
        extends ErrorState {
            @NotNull
            public static final InvalidPort INSTANCE = new InvalidPort();

            private InvalidPort() {
                super(null);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/PortResult$NoPortSpecified;", "Lio/javalin/plugin/bundled/PortResult;", "()V", "javalin"})
    public static final class NoPortSpecified
    extends PortResult {
        @NotNull
        public static final NoPortSpecified INSTANCE = new NoPortSpecified();

        private NoPortSpecified() {
            super(null);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2={"Lio/javalin/plugin/bundled/PortResult$PortSpecified;", "Lio/javalin/plugin/bundled/PortResult;", "port", "", "fromSchemeDefault", "", "(IZ)V", "getFromSchemeDefault", "()Z", "getPort", "()I", "component1", "component2", "copy", "equals", "other", "", "hashCode", "toString", "", "javalin"})
    public static final class PortSpecified
    extends PortResult {
        private final int port;
        private final boolean fromSchemeDefault;

        public PortSpecified(int port, boolean fromSchemeDefault) {
            super(null);
            this.port = port;
            this.fromSchemeDefault = fromSchemeDefault;
        }

        public /* synthetic */ PortSpecified(int n, boolean bl, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 2) != 0) {
                bl = false;
            }
            this(n, bl);
        }

        public final int getPort() {
            return this.port;
        }

        public final boolean getFromSchemeDefault() {
            return this.fromSchemeDefault;
        }

        public final int component1() {
            return this.port;
        }

        public final boolean component2() {
            return this.fromSchemeDefault;
        }

        @NotNull
        public final PortSpecified copy(int port, boolean fromSchemeDefault) {
            return new PortSpecified(port, fromSchemeDefault);
        }

        public static /* synthetic */ PortSpecified copy$default(PortSpecified portSpecified, int n, boolean bl, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = portSpecified.port;
            }
            if ((n2 & 2) != 0) {
                bl = portSpecified.fromSchemeDefault;
            }
            return portSpecified.copy(n, bl);
        }

        @NotNull
        public String toString() {
            return "PortSpecified(port=" + this.port + ", fromSchemeDefault=" + this.fromSchemeDefault + ")";
        }

        public int hashCode() {
            int result = Integer.hashCode(this.port);
            int n = this.fromSchemeDefault ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result = result * 31 + n;
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PortSpecified)) {
                return false;
            }
            PortSpecified portSpecified = (PortSpecified)other;
            if (this.port != portSpecified.port) {
                return false;
            }
            return this.fromSchemeDefault == portSpecified.fromSchemeDefault;
        }
    }
}

