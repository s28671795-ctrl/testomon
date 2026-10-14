/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.EventListener;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteEventHandler;
import org.jooq.Pro;
import org.jooq.impl.CallbackExecuteListener;

public interface ExecuteListener
extends EventListener,
Serializable {
    default public void start(ExecuteContext ctx) {
    }

    default public void renderStart(ExecuteContext ctx) {
    }

    default public void renderEnd(ExecuteContext ctx) {
    }

    @Pro
    default public void transformStart(ExecuteContext ctx) {
    }

    @Pro
    default public void transformEnd(ExecuteContext ctx) {
    }

    default public void prepareStart(ExecuteContext ctx) {
    }

    default public void prepareEnd(ExecuteContext ctx) {
    }

    default public void bindStart(ExecuteContext ctx) {
    }

    default public void bindEnd(ExecuteContext ctx) {
    }

    default public void executeStart(ExecuteContext ctx) {
    }

    default public void executeEnd(ExecuteContext ctx) {
    }

    default public void outStart(ExecuteContext ctx) {
    }

    default public void outEnd(ExecuteContext ctx) {
    }

    default public void fetchStart(ExecuteContext ctx) {
    }

    default public void resultStart(ExecuteContext ctx) {
    }

    default public void recordStart(ExecuteContext ctx) {
    }

    default public void recordEnd(ExecuteContext ctx) {
    }

    default public void resultEnd(ExecuteContext ctx) {
    }

    default public void fetchEnd(ExecuteContext ctx) {
    }

    default public void end(ExecuteContext ctx) {
    }

    default public void exception(ExecuteContext ctx) {
    }

    default public void warning(ExecuteContext ctx) {
    }

    public static CallbackExecuteListener onStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onStart(handler);
    }

    public static CallbackExecuteListener onEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onEnd(handler);
    }

    public static CallbackExecuteListener onTransformStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onTransformStart(handler);
    }

    public static CallbackExecuteListener onTransformEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onTransformEnd(handler);
    }

    public static CallbackExecuteListener onRenderStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onRenderStart(handler);
    }

    public static CallbackExecuteListener onRenderEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onRenderEnd(handler);
    }

    public static CallbackExecuteListener onPrepareStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onPrepareStart(handler);
    }

    public static CallbackExecuteListener onPrepareEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onPrepareEnd(handler);
    }

    public static CallbackExecuteListener onBindStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onBindStart(handler);
    }

    public static CallbackExecuteListener onBindEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onBindEnd(handler);
    }

    public static CallbackExecuteListener onExecuteStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onExecuteStart(handler);
    }

    public static CallbackExecuteListener onExecuteEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onExecuteEnd(handler);
    }

    public static CallbackExecuteListener onOutStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onOutStart(handler);
    }

    public static CallbackExecuteListener onOutEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onOutEnd(handler);
    }

    public static CallbackExecuteListener onFetchStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onFetchStart(handler);
    }

    public static CallbackExecuteListener onFetchEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onFetchEnd(handler);
    }

    public static CallbackExecuteListener onResultStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onResultStart(handler);
    }

    public static CallbackExecuteListener onResultEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onResultEnd(handler);
    }

    public static CallbackExecuteListener onRecordStart(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onRecordStart(handler);
    }

    public static CallbackExecuteListener onRecordEnd(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onRecordEnd(handler);
    }

    public static CallbackExecuteListener onException(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onException(handler);
    }

    public static CallbackExecuteListener onWarning(ExecuteEventHandler handler) {
        return new CallbackExecuteListener().onWarning(handler);
    }
}

