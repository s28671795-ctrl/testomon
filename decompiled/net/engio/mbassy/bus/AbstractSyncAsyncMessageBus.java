/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import net.engio.mbassy.bus.AbstractPubSubSupport;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.common.IMessageBus;
import net.engio.mbassy.bus.config.ConfigurationError;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.InternalPublicationError;
import net.engio.mbassy.bus.publication.ISyncAsyncPublicationCommand;

public abstract class AbstractSyncAsyncMessageBus<T, P extends ISyncAsyncPublicationCommand>
extends AbstractPubSubSupport<T>
implements IMessageBus<T, P> {
    private final ExecutorService executor;
    private final List<Thread> dispatchers;
    private final BlockingQueue<IMessagePublication> pendingMessages;

    protected AbstractSyncAsyncMessageBus(IBusConfiguration configuration) {
        super(configuration);
        Feature.AsynchronousMessageDispatch asyncDispatch = configuration.getFeature(Feature.AsynchronousMessageDispatch.class);
        if (asyncDispatch == null) {
            throw ConfigurationError.MissingFeature(Feature.AsynchronousMessageDispatch.class);
        }
        this.pendingMessages = asyncDispatch.getMessageQueue();
        this.dispatchers = new ArrayList<Thread>(asyncDispatch.getNumberOfMessageDispatchers());
        this.initDispatcherThreads(asyncDispatch);
        Feature.AsynchronousHandlerInvocation asyncInvocation = configuration.getFeature(Feature.AsynchronousHandlerInvocation.class);
        if (asyncInvocation == null) {
            throw ConfigurationError.MissingFeature(Feature.AsynchronousHandlerInvocation.class);
        }
        this.executor = asyncInvocation.getExecutor();
        this.getRuntime().add("bus.handlers.async-executor", this.executor);
    }

    private void initDispatcherThreads(Feature.AsynchronousMessageDispatch configuration) {
        for (int i = 0; i < configuration.getNumberOfMessageDispatchers(); ++i) {
            Thread dispatcher = configuration.getDispatcherThreadFactory().newThread(new Runnable(){

                @Override
                public void run() {
                    while (true) {
                        IMessagePublication publication = null;
                        try {
                            publication = (IMessagePublication)AbstractSyncAsyncMessageBus.this.pendingMessages.take();
                            publication.execute();
                            continue;
                        }
                        catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        catch (Throwable t2) {
                            AbstractSyncAsyncMessageBus.this.handlePublicationError(new InternalPublicationError(t2, "Error in asynchronous dispatch", publication));
                            continue;
                        }
                        break;
                    }
                }
            });
            dispatcher.setName("MsgDispatcher-" + i);
            this.dispatchers.add(dispatcher);
            dispatcher.start();
        }
    }

    protected IMessagePublication addAsynchronousPublication(MessagePublication publication) {
        try {
            this.pendingMessages.put(publication);
            return publication.markScheduled();
        }
        catch (InterruptedException e) {
            this.handlePublicationError(new InternalPublicationError((Throwable)e, "Error while adding an asynchronous message publication", publication));
            return publication;
        }
    }

    protected IMessagePublication addAsynchronousPublication(MessagePublication publication, long timeout2, TimeUnit unit) {
        try {
            return this.pendingMessages.offer(publication, timeout2, unit) ? publication.markScheduled() : publication;
        }
        catch (InterruptedException e) {
            this.handlePublicationError(new InternalPublicationError((Throwable)e, "Error while adding an asynchronous message publication", publication));
            return publication;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.shutdown();
    }

    @Override
    public void shutdown() {
        for (Thread dispatcher : this.dispatchers) {
            dispatcher.interrupt();
        }
        if (this.executor != null) {
            this.executor.shutdown();
        }
    }

    @Override
    public boolean hasPendingMessages() {
        return this.pendingMessages.size() > 0;
    }
}

