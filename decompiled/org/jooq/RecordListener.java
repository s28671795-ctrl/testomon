/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.EventListener;
import java.util.function.Consumer;
import org.jooq.RecordContext;
import org.jooq.impl.CallbackRecordListener;

public interface RecordListener
extends EventListener {
    default public void storeStart(RecordContext ctx) {
    }

    default public void storeEnd(RecordContext ctx) {
    }

    default public void insertStart(RecordContext ctx) {
    }

    default public void insertEnd(RecordContext ctx) {
    }

    default public void updateStart(RecordContext ctx) {
    }

    default public void updateEnd(RecordContext ctx) {
    }

    default public void mergeStart(RecordContext ctx) {
    }

    default public void mergeEnd(RecordContext ctx) {
    }

    default public void deleteStart(RecordContext ctx) {
    }

    default public void deleteEnd(RecordContext ctx) {
    }

    default public void loadStart(RecordContext ctx) {
    }

    default public void loadEnd(RecordContext ctx) {
    }

    default public void refreshStart(RecordContext ctx) {
    }

    default public void refreshEnd(RecordContext ctx) {
    }

    default public void exception(RecordContext ctx) {
    }

    public static CallbackRecordListener onStoreStart(Consumer<? super RecordContext> onStoreStart) {
        return new CallbackRecordListener().onStoreStart(onStoreStart);
    }

    public static CallbackRecordListener onStoreEnd(Consumer<? super RecordContext> onStoreEnd) {
        return new CallbackRecordListener().onStoreEnd(onStoreEnd);
    }

    public static CallbackRecordListener onInsertStart(Consumer<? super RecordContext> onInsertStart) {
        return new CallbackRecordListener().onInsertStart(onInsertStart);
    }

    public static CallbackRecordListener onInsertEnd(Consumer<? super RecordContext> onInsertEnd) {
        return new CallbackRecordListener().onInsertEnd(onInsertEnd);
    }

    public static CallbackRecordListener onUpdateStart(Consumer<? super RecordContext> onUpdateStart) {
        return new CallbackRecordListener().onUpdateStart(onUpdateStart);
    }

    public static CallbackRecordListener onUpdateEnd(Consumer<? super RecordContext> onUpdateEnd) {
        return new CallbackRecordListener().onUpdateEnd(onUpdateEnd);
    }

    public static CallbackRecordListener onMergeStart(Consumer<? super RecordContext> onMergeStart) {
        return new CallbackRecordListener().onMergeStart(onMergeStart);
    }

    public static CallbackRecordListener onMergeEnd(Consumer<? super RecordContext> onMergeEnd) {
        return new CallbackRecordListener().onMergeEnd(onMergeEnd);
    }

    public static CallbackRecordListener onDeleteStart(Consumer<? super RecordContext> onDeleteStart) {
        return new CallbackRecordListener().onDeleteStart(onDeleteStart);
    }

    public static CallbackRecordListener onDeleteEnd(Consumer<? super RecordContext> onDeleteEnd) {
        return new CallbackRecordListener().onDeleteEnd(onDeleteEnd);
    }

    public static CallbackRecordListener onLoadStart(Consumer<? super RecordContext> onLoadStart) {
        return new CallbackRecordListener().onLoadStart(onLoadStart);
    }

    public static CallbackRecordListener onLoadEnd(Consumer<? super RecordContext> onLoadEnd) {
        return new CallbackRecordListener().onLoadEnd(onLoadEnd);
    }

    public static CallbackRecordListener onRefreshStart(Consumer<? super RecordContext> onRefreshStart) {
        return new CallbackRecordListener().onRefreshStart(onRefreshStart);
    }

    public static CallbackRecordListener onRefreshEnd(Consumer<? super RecordContext> onRefreshEnd) {
        return new CallbackRecordListener().onRefreshEnd(onRefreshEnd);
    }

    public static CallbackRecordListener onException(Consumer<? super RecordContext> onException) {
        return new CallbackRecordListener().onException(onException);
    }
}

