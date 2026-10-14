/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class LinkedNode {
    protected LinkedNode next = this;
    protected LinkedNode prev = this;
    protected boolean tail = true;

    public LinkedNode getHeadNode() {
        if (this.isHeadNode()) {
            return this;
        }
        if (this.isTailNode()) {
            return this.next;
        }
        LinkedNode rc = this.prev;
        while (!rc.isHeadNode()) {
            rc = rc.prev;
        }
        return rc;
    }

    public LinkedNode getTailNode() {
        if (this.isTailNode()) {
            return this;
        }
        if (this.isHeadNode()) {
            return this.prev;
        }
        LinkedNode rc = this.next;
        while (!rc.isTailNode()) {
            rc = rc.next;
        }
        return rc;
    }

    public LinkedNode getNext() {
        return this.tail ? null : this.next;
    }

    public LinkedNode getPrevious() {
        return this.prev.tail ? null : this.prev;
    }

    public boolean isHeadNode() {
        return this.prev.isTailNode();
    }

    public boolean isTailNode() {
        return this.tail;
    }

    public LinkedNode linkAfter(LinkedNode rightHead) {
        if (rightHead == this) {
            throw new IllegalArgumentException("You cannot link to yourself");
        }
        if (!rightHead.isHeadNode()) {
            throw new IllegalArgumentException("You only insert nodes that are the first in a list");
        }
        LinkedNode rightTail = rightHead.prev;
        if (this.tail) {
            this.tail = false;
        } else {
            rightTail.tail = false;
        }
        rightHead.prev = this;
        rightTail.next = this.next;
        this.next.prev = rightTail;
        this.next = rightHead;
        return this;
    }

    public LinkedNode linkBefore(LinkedNode leftHead) {
        if (leftHead == this) {
            throw new IllegalArgumentException("You cannot link to yourself");
        }
        if (!leftHead.isHeadNode()) {
            throw new IllegalArgumentException("You only insert nodes that are the first in a list");
        }
        LinkedNode leftTail = leftHead.prev;
        leftTail.tail = false;
        leftTail.next = this;
        leftHead.prev = this.prev;
        this.prev.next = leftHead;
        this.prev = leftTail;
        return leftHead;
    }

    public void unlink() {
        if (this.prev == this) {
            this.reset();
            return;
        }
        if (this.tail) {
            this.prev.tail = true;
        }
        this.next.prev = this.prev;
        this.prev.next = this.next;
        this.reset();
    }

    public void reset() {
        this.next = this;
        this.prev = this;
        this.tail = true;
    }
}

