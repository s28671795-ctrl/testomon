/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.filter.DestinationMapNode;
import org.apache.activemq.filter.DestinationNode;

public class DestinationMap {
    protected static final String ANY_DESCENDENT = ">";
    protected static final String ANY_CHILD = "*";
    private DestinationMapNode queueRootNode = new DestinationMapNode(null);
    private DestinationMapNode tempQueueRootNode = new DestinationMapNode(null);
    private DestinationMapNode topicRootNode = new DestinationMapNode(null);
    private DestinationMapNode tempTopicRootNode = new DestinationMapNode(null);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Set get(ActiveMQDestination key) {
        DestinationMap destinationMap = this;
        synchronized (destinationMap) {
            return this.unsynchronizedGet(key);
        }
    }

    public Set unsynchronizedGet(ActiveMQDestination key) {
        if (key.isComposite()) {
            ActiveMQDestination[] destinations = key.getCompositeDestinations();
            HashSet<Set> answer = new HashSet<Set>(destinations.length);
            for (int i = 0; i < destinations.length; ++i) {
                ActiveMQDestination childDestination = destinations[i];
                Set value = this.unsynchronizedGet(childDestination);
                if (value instanceof Set) {
                    answer.addAll(value);
                    continue;
                }
                if (value == null) continue;
                answer.add(value);
            }
            return answer;
        }
        return this.findWildcardMatches(key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void put(ActiveMQDestination key, Object value) {
        DestinationMap destinationMap = this;
        synchronized (destinationMap) {
            this.unsynchronizedPut(key, value);
        }
    }

    public void unsynchronizedPut(ActiveMQDestination key, Object value) {
        if (key.isComposite()) {
            ActiveMQDestination[] destinations = key.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                ActiveMQDestination childDestination = destinations[i];
                this.put(childDestination, value);
            }
            return;
        }
        String[] paths = key.getDestinationPaths();
        this.getRootNode(key).add(paths, 0, value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void remove(ActiveMQDestination key, Object value) {
        DestinationMap destinationMap = this;
        synchronized (destinationMap) {
            this.unsynchronizedRemove(key, value);
        }
    }

    public void unsynchronizedRemove(ActiveMQDestination key, Object value) {
        if (key.isComposite()) {
            ActiveMQDestination[] destinations = key.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                ActiveMQDestination childDestination = destinations[i];
                this.remove(childDestination, value);
            }
            return;
        }
        String[] paths = key.getDestinationPaths();
        this.getRootNode(key).remove(paths, 0, value);
    }

    public int getTopicRootChildCount() {
        return this.topicRootNode.getChildCount();
    }

    public int getQueueRootChildCount() {
        return this.queueRootNode.getChildCount();
    }

    public DestinationMapNode getQueueRootNode() {
        return this.queueRootNode;
    }

    public DestinationMapNode getTopicRootNode() {
        return this.topicRootNode;
    }

    public DestinationMapNode getTempQueueRootNode() {
        return this.tempQueueRootNode;
    }

    public DestinationMapNode getTempTopicRootNode() {
        return this.tempTopicRootNode;
    }

    protected void setEntries(List<DestinationMapEntry> entries) {
        for (DestinationMapEntry element : entries) {
            Class<? extends DestinationMapEntry> type = this.getEntryClass();
            if (type.isInstance(element)) {
                DestinationMapEntry entry = element;
                this.put(entry.getDestination(), entry.getValue());
                continue;
            }
            throw new IllegalArgumentException("Each entry must be an instance of type: " + type.getName() + " but was: " + element);
        }
    }

    protected Class<? extends DestinationMapEntry> getEntryClass() {
        return DestinationMapEntry.class;
    }

    protected Set findWildcardMatches(ActiveMQDestination key) {
        return this.findWildcardMatches(key, true);
    }

    protected Set findWildcardMatches(ActiveMQDestination key, boolean deep) {
        String[] paths = key.getDestinationPaths();
        HashSet<DestinationNode> answer = new HashSet<DestinationNode>();
        this.getRootNode(key).appendMatchingValues(answer, paths, 0, deep);
        return answer;
    }

    public Set removeAll(ActiveMQDestination key) {
        HashSet<DestinationNode> rc = new HashSet<DestinationNode>();
        if (key.isComposite()) {
            ActiveMQDestination[] destinations = key.getCompositeDestinations();
            for (int i = 0; i < destinations.length; ++i) {
                rc.add((DestinationNode)((Object)this.removeAll(destinations[i])));
            }
            return rc;
        }
        String[] paths = key.getDestinationPaths();
        this.getRootNode(key).removeAll(rc, paths, 0);
        return rc;
    }

    public DestinationMapEntry chooseValue(final ActiveMQDestination destination) {
        Set set = this.get(destination);
        if (set == null || set.isEmpty()) {
            return null;
        }
        Comparator<DestinationMapEntry> comparator = new Comparator<DestinationMapEntry>(){

            @Override
            public int compare(DestinationMapEntry entry1, DestinationMapEntry entry2) {
                return destination.equals(entry1.destination) ? -1 : (destination.equals(entry2.destination) ? 1 : entry1.compareTo(entry2));
            }
        };
        SortedSet sortedSet = set.stream().filter(entry -> this.isMatchOrParent(destination, (DestinationMapEntry)entry)).collect(Collectors.toCollection(() -> new TreeSet(comparator)));
        return sortedSet.size() > 0 ? (DestinationMapEntry)sortedSet.first() : null;
    }

    private boolean isMatchOrParent(ActiveMQDestination destination, DestinationMapEntry entry) {
        if (entry.getDestination() == null) {
            return true;
        }
        DestinationFilter filter2 = DestinationFilter.parseFilter(entry.getDestination());
        return destination.equals(entry.getDestination()) || filter2.matches(destination);
    }

    protected DestinationMapNode getRootNode(ActiveMQDestination key) {
        if (key.isTemporary()) {
            if (key.isQueue()) {
                return this.tempQueueRootNode;
            }
            return this.tempTopicRootNode;
        }
        if (key.isQueue()) {
            return this.queueRootNode;
        }
        return this.topicRootNode;
    }

    public void reset() {
        this.queueRootNode = new DestinationMapNode(null);
        this.tempQueueRootNode = new DestinationMapNode(null);
        this.topicRootNode = new DestinationMapNode(null);
        this.tempTopicRootNode = new DestinationMapNode(null);
    }

    public boolean isEmpty() {
        return this.queueRootNode.isEmpty() && this.topicRootNode.isEmpty() && this.tempQueueRootNode.isEmpty() && this.tempTopicRootNode.isEmpty();
    }

    public static Set union(Set existing, Set candidates) {
        if (candidates != null) {
            if (existing != null) {
                Iterator iterator2 = existing.iterator();
                while (iterator2.hasNext()) {
                    Object toMatch = iterator2.next();
                    if (candidates.contains(toMatch)) continue;
                    iterator2.remove();
                }
            } else {
                existing = candidates;
            }
        } else if (existing != null) {
            existing.clear();
        }
        return existing;
    }
}

