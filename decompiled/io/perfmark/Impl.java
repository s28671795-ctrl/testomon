/*
 * Decompiled with CFR 0.152.
 */
package io.perfmark;

import io.perfmark.Link;
import io.perfmark.StringFunction;
import io.perfmark.Tag;
import javax.annotation.Nullable;

public class Impl {
    static final String NO_TAG_NAME = "";
    static final long NO_TAG_ID = Long.MIN_VALUE;
    private static final long NO_LINK_ID = Long.MIN_VALUE;
    static final Tag NO_TAG = new Tag("", Long.MIN_VALUE);
    static final Link NO_LINK = new Link(Long.MIN_VALUE);

    protected Impl(Tag key) {
        if (key != NO_TAG) {
            throw new AssertionError((Object)"nope");
        }
    }

    protected void setEnabled(boolean value) {
    }

    protected boolean setEnabled(boolean value, boolean overload) {
        return false;
    }

    protected <T> void startTask(T taskNameObject, StringFunction<? super T> taskNameFunc) {
    }

    protected void startTask(String taskName, Tag tag) {
    }

    protected void startTask(String taskName) {
    }

    protected void startTask(String taskName, String subTaskName) {
    }

    protected void event(String eventName, Tag tag) {
    }

    protected void event(String eventName) {
    }

    protected void event(String eventName, String subEventName) {
    }

    protected void stopTask() {
    }

    protected void stopTask(String taskName, Tag tag) {
    }

    protected void stopTask(String taskName) {
    }

    protected void stopTask(String taskName, String subTaskName) {
    }

    protected Link linkOut() {
        return NO_LINK;
    }

    protected void linkIn(Link link) {
    }

    protected void attachTag(Tag tag) {
    }

    protected void attachTag(String tagName, String tagValue) {
    }

    protected void attachTag(String tagName, long tagValue) {
    }

    protected void attachTag(String tagName, long tagValue0, long tagValue1) {
    }

    protected <T> void attachTag(String tagName, T tagObject, StringFunction<? super T> stringFunction) {
    }

    protected Tag createTag(@Nullable String tagName, long tagId) {
        return NO_TAG;
    }

    @Nullable
    protected static String unpackTagName(Tag tag) {
        return tag.tagName;
    }

    protected static long unpackTagId(Tag tag) {
        return tag.tagId;
    }

    protected static long unpackLinkId(Link link) {
        return link.linkId;
    }

    protected static Tag packTag(@Nullable String tagName, long tagId) {
        return new Tag(tagName, tagId);
    }

    protected static Link packLink(long linkId) {
        return new Link(linkId);
    }
}

