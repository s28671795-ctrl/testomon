/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueuePad1;
import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

abstract class BaseLinkedAtomicQueueConsumerNodeRef<E>
extends BaseLinkedAtomicQueuePad1<E> {
    private static final AtomicReferenceFieldUpdater<BaseLinkedAtomicQueueConsumerNodeRef, LinkedQueueAtomicNode> C_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(BaseLinkedAtomicQueueConsumerNodeRef.class, LinkedQueueAtomicNode.class, "consumerNode");
    private volatile LinkedQueueAtomicNode<E> consumerNode;

    BaseLinkedAtomicQueueConsumerNodeRef() {
    }

    final void spConsumerNode(LinkedQueueAtomicNode<E> newValue) {
        C_NODE_UPDATER.lazySet(this, newValue);
    }

    final LinkedQueueAtomicNode<E> lvConsumerNode() {
        return this.consumerNode;
    }

    final LinkedQueueAtomicNode<E> lpConsumerNode() {
        return this.consumerNode;
    }
}

