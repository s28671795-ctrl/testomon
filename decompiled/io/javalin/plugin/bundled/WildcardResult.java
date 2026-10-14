/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2={"Lio/javalin/plugin/bundled/WildcardResult;", "", "()V", "ErrorState", "NoWildcardDetected", "WildcardOkay", "Lio/javalin/plugin/bundled/WildcardResult$ErrorState;", "Lio/javalin/plugin/bundled/WildcardResult$NoWildcardDetected;", "Lio/javalin/plugin/bundled/WildcardResult$WildcardOkay;", "javalin"})
public abstract class WildcardResult {
    private WildcardResult() {
    }

    public /* synthetic */ WildcardResult(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/plugin/bundled/WildcardResult$ErrorState;", "Lio/javalin/plugin/bundled/WildcardResult;", "()V", "TooManyWildcards", "WildcardNotAtTheStartOfTheHost", "Lio/javalin/plugin/bundled/WildcardResult$ErrorState$TooManyWildcards;", "Lio/javalin/plugin/bundled/WildcardResult$ErrorState$WildcardNotAtTheStartOfTheHost;", "javalin"})
    public static abstract class ErrorState
    extends WildcardResult {
        private ErrorState() {
            super(null);
        }

        public /* synthetic */ ErrorState(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/WildcardResult$ErrorState$TooManyWildcards;", "Lio/javalin/plugin/bundled/WildcardResult$ErrorState;", "()V", "javalin"})
        public static final class TooManyWildcards
        extends ErrorState {
            @NotNull
            public static final TooManyWildcards INSTANCE = new TooManyWildcards();

            private TooManyWildcards() {
                super(null);
            }
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/WildcardResult$ErrorState$WildcardNotAtTheStartOfTheHost;", "Lio/javalin/plugin/bundled/WildcardResult$ErrorState;", "()V", "javalin"})
        public static final class WildcardNotAtTheStartOfTheHost
        extends ErrorState {
            @NotNull
            public static final WildcardNotAtTheStartOfTheHost INSTANCE = new WildcardNotAtTheStartOfTheHost();

            private WildcardNotAtTheStartOfTheHost() {
                super(null);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/WildcardResult$NoWildcardDetected;", "Lio/javalin/plugin/bundled/WildcardResult;", "()V", "javalin"})
    public static final class NoWildcardDetected
    extends WildcardResult {
        @NotNull
        public static final NoWildcardDetected INSTANCE = new NoWildcardDetected();

        private NoWildcardDetected() {
            super(null);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/plugin/bundled/WildcardResult$WildcardOkay;", "Lio/javalin/plugin/bundled/WildcardResult;", "()V", "javalin"})
    public static final class WildcardOkay
    extends WildcardResult {
        @NotNull
        public static final WildcardOkay INSTANCE = new WildcardOkay();

        private WildcardOkay() {
            super(null);
        }
    }
}

