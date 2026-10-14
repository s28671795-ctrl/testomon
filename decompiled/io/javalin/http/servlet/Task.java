/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.servlet.TaskHandler;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2={"Lio/javalin/http/servlet/Task;", "", "skipIfExceptionOccurred", "", "handler", "Lio/javalin/http/servlet/TaskHandler;", "", "(ZLio/javalin/http/servlet/TaskHandler;)V", "getHandler", "()Lio/javalin/http/servlet/TaskHandler;", "getSkipIfExceptionOccurred", "()Z", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "javalin"})
public final class Task {
    private final boolean skipIfExceptionOccurred;
    @NotNull
    private final TaskHandler<Unit> handler;

    public Task(boolean skipIfExceptionOccurred, @NotNull TaskHandler<Unit> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.skipIfExceptionOccurred = skipIfExceptionOccurred;
        this.handler = handler;
    }

    public /* synthetic */ Task(boolean bl, TaskHandler taskHandler, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = true;
        }
        this(bl, taskHandler);
    }

    public final boolean getSkipIfExceptionOccurred() {
        return this.skipIfExceptionOccurred;
    }

    @NotNull
    public final TaskHandler<Unit> getHandler() {
        return this.handler;
    }

    public final boolean component1() {
        return this.skipIfExceptionOccurred;
    }

    @NotNull
    public final TaskHandler<Unit> component2() {
        return this.handler;
    }

    @NotNull
    public final Task copy(boolean skipIfExceptionOccurred, @NotNull TaskHandler<Unit> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        return new Task(skipIfExceptionOccurred, handler);
    }

    public static /* synthetic */ Task copy$default(Task task, boolean bl, TaskHandler taskHandler, int n, Object object) {
        if ((n & 1) != 0) {
            bl = task.skipIfExceptionOccurred;
        }
        if ((n & 2) != 0) {
            taskHandler = task.handler;
        }
        return task.copy(bl, taskHandler);
    }

    @NotNull
    public String toString() {
        return "Task(skipIfExceptionOccurred=" + this.skipIfExceptionOccurred + ", handler=" + this.handler + ")";
    }

    public int hashCode() {
        int n = this.skipIfExceptionOccurred ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        int result = n;
        result = result * 31 + this.handler.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }
        Task task = (Task)other;
        if (this.skipIfExceptionOccurred != task.skipIfExceptionOccurred) {
            return false;
        }
        return Intrinsics.areEqual(this.handler, task.handler);
    }
}

