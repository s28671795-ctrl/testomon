/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.TopicMessageStore;

public class PersistenceAdapterSupport {
    private static final DestinationMatcher MATCH_ALL = new AlwaysMatches();

    public static List<SubscriptionInfo> listSubscriptions(PersistenceAdapter adapter, String clientId) throws IOException {
        ArrayList<SubscriptionInfo> rc = new ArrayList<SubscriptionInfo>();
        for (ActiveMQDestination destination : adapter.getDestinations()) {
            if (!destination.isTopic()) continue;
            TopicMessageStore store = adapter.createTopicMessageStore((ActiveMQTopic)destination);
            for (SubscriptionInfo sub : store.getAllSubscriptions()) {
                if (clientId != sub.getClientId() && !clientId.equals(sub.getClientId())) continue;
                rc.add(sub);
            }
        }
        return rc;
    }

    public static List<ActiveMQQueue> listQueues(PersistenceAdapter adapter) throws IOException {
        return PersistenceAdapterSupport.listQueues(adapter, MATCH_ALL);
    }

    public static List<ActiveMQQueue> listQueues(PersistenceAdapter adapter, DestinationMatcher matcher) throws IOException {
        ArrayList<ActiveMQQueue> rc = new ArrayList<ActiveMQQueue>();
        for (ActiveMQDestination destination : adapter.getDestinations()) {
            if (!destination.isQueue() || !matcher.matches(destination)) continue;
            rc.add((ActiveMQQueue)destination);
        }
        return rc;
    }

    public static List<ActiveMQTopic> listTopics(PersistenceAdapter adapter) throws IOException {
        return PersistenceAdapterSupport.listTopics(adapter, MATCH_ALL);
    }

    public static List<ActiveMQTopic> listTopics(PersistenceAdapter adapter, DestinationMatcher matcher) throws IOException {
        ArrayList<ActiveMQTopic> rc = new ArrayList<ActiveMQTopic>();
        for (ActiveMQDestination destination : adapter.getDestinations()) {
            if (!destination.isTopic() || !matcher.matches(destination)) continue;
            rc.add((ActiveMQTopic)destination);
        }
        return rc;
    }

    private static class AlwaysMatches
    implements DestinationMatcher {
        private AlwaysMatches() {
        }

        @Override
        public boolean matches(ActiveMQDestination destination) {
            return true;
        }
    }

    public static interface DestinationMatcher {
        public boolean matches(ActiveMQDestination var1);
    }
}

