/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.ISubscriptionManagerProvider;
import net.engio.mbassy.subscription.SubscriptionFactory;
import net.engio.mbassy.subscription.SubscriptionManagerProvider;

public interface Feature {

    public static class AsynchronousMessageDispatch
    implements Feature {
        protected static final ThreadFactory MessageDispatchThreadFactory = new ThreadFactory(){
            private final AtomicInteger threadID = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread2 = Executors.defaultThreadFactory().newThread(r);
                thread2.setDaemon(true);
                thread2.setName("Dispatcher-" + this.threadID.getAndIncrement());
                return thread2;
            }
        };
        private int numberOfMessageDispatchers;
        private BlockingQueue<IMessagePublication> messageQueue;
        private ThreadFactory dispatcherThreadFactory;

        public static final AsynchronousMessageDispatch Default() {
            return new AsynchronousMessageDispatch().setNumberOfMessageDispatchers(2).setDispatcherThreadFactory(MessageDispatchThreadFactory).setMessageQueue(new LinkedBlockingQueue<IMessagePublication>(Integer.MAX_VALUE));
        }

        public int getNumberOfMessageDispatchers() {
            return this.numberOfMessageDispatchers;
        }

        public AsynchronousMessageDispatch setNumberOfMessageDispatchers(int numberOfMessageDispatchers) {
            this.numberOfMessageDispatchers = numberOfMessageDispatchers;
            return this;
        }

        public BlockingQueue<IMessagePublication> getMessageQueue() {
            return this.messageQueue;
        }

        public AsynchronousMessageDispatch setMessageQueue(BlockingQueue<IMessagePublication> pendingMessages) {
            this.messageQueue = pendingMessages;
            return this;
        }

        public ThreadFactory getDispatcherThreadFactory() {
            return this.dispatcherThreadFactory;
        }

        public AsynchronousMessageDispatch setDispatcherThreadFactory(ThreadFactory dispatcherThreadFactory) {
            this.dispatcherThreadFactory = dispatcherThreadFactory;
            return this;
        }
    }

    public static class AsynchronousHandlerInvocation
    implements Feature {
        protected static final ThreadFactory MessageHandlerThreadFactory = new ThreadFactory(){
            private final AtomicInteger threadID = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread2 = Executors.defaultThreadFactory().newThread(r);
                thread2.setName("AsyncHandler-" + this.threadID.getAndIncrement());
                thread2.setDaemon(true);
                return thread2;
            }
        };
        private ExecutorService executor;

        public static final AsynchronousHandlerInvocation Default() {
            int numberOfCores = Runtime.getRuntime().availableProcessors();
            return AsynchronousHandlerInvocation.Default(numberOfCores, numberOfCores * 2);
        }

        public static final AsynchronousHandlerInvocation Default(int minThreadCount, int maxThreadCount) {
            return new AsynchronousHandlerInvocation().setExecutor(new ThreadPoolExecutor(minThreadCount, maxThreadCount, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), MessageHandlerThreadFactory));
        }

        public ExecutorService getExecutor() {
            return this.executor;
        }

        public AsynchronousHandlerInvocation setExecutor(ExecutorService executor2) {
            this.executor = executor2;
            return this;
        }
    }

    public static class SyncPubSub
    implements Feature {
        private MessagePublication.Factory publicationFactory;
        private MetadataReader metadataReader;
        private SubscriptionFactory subscriptionFactory;
        private ISubscriptionManagerProvider subscriptionManagerProvider;

        public static final SyncPubSub Default() {
            return new SyncPubSub().setMetadataReader(new MetadataReader()).setPublicationFactory(new MessagePublication.Factory()).setSubscriptionFactory(new SubscriptionFactory()).setSubscriptionManagerProvider(new SubscriptionManagerProvider());
        }

        public ISubscriptionManagerProvider getSubscriptionManagerProvider() {
            return this.subscriptionManagerProvider;
        }

        public SyncPubSub setSubscriptionManagerProvider(ISubscriptionManagerProvider subscriptionManagerProvider) {
            this.subscriptionManagerProvider = subscriptionManagerProvider;
            return this;
        }

        public SubscriptionFactory getSubscriptionFactory() {
            return this.subscriptionFactory;
        }

        public SyncPubSub setSubscriptionFactory(SubscriptionFactory subscriptionFactory) {
            this.subscriptionFactory = subscriptionFactory;
            return this;
        }

        public MetadataReader getMetadataReader() {
            return this.metadataReader;
        }

        public SyncPubSub setMetadataReader(MetadataReader metadataReader) {
            this.metadataReader = metadataReader;
            return this;
        }

        public MessagePublication.Factory getPublicationFactory() {
            return this.publicationFactory;
        }

        public SyncPubSub setPublicationFactory(MessagePublication.Factory publicationFactory) {
            this.publicationFactory = publicationFactory;
            return this;
        }
    }
}

