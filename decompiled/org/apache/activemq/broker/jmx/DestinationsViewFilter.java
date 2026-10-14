/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.management.ObjectName;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.broker.jmx.TopicView;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.util.IntrospectionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationsViewFilter
implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DestinationsViewFilter.class);
    private static final long serialVersionUID = 1L;
    String name;
    String filter;
    String sortColumn = "name";
    String sortOrder = "asc";
    Map<ObjectName, DestinationView> destinations;

    public static DestinationsViewFilter create(String json) throws IOException {
        ObjectMapper mapper2 = new ObjectMapper();
        if (json == null) {
            return new DestinationsViewFilter();
        }
        if ((json = json.trim()).length() == 0 || json.equals("{}")) {
            return new DestinationsViewFilter();
        }
        return (DestinationsViewFilter)mapper2.readerFor(DestinationsViewFilter.class).readValue(json);
    }

    public DestinationsViewFilter setDestinations(Map<ObjectName, DestinationView> destinations) {
        this.destinations = destinations;
        return this;
    }

    String filter(int page, int pageSize) throws IOException {
        ObjectMapper mapper2 = new ObjectMapper();
        this.destinations = Maps.filterValues(this.destinations, this.getPredicate());
        Map<ObjectName, DestinationView> pagedDestinations = this.getPagedDestinations(page, pageSize);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("data", pagedDestinations);
        result.put("count", this.destinations.size());
        StringWriter writer = new StringWriter();
        mapper2.writeValue(writer, result);
        return writer.toString();
    }

    Map<ObjectName, DestinationView> getPagedDestinations(int page, int pageSize) {
        ImmutableMap.Builder<ObjectName, DestinationView> builder = ImmutableMap.builder();
        int start2 = (page - 1) * pageSize;
        int end = Math.min(page * pageSize, this.destinations.size());
        int i = 0;
        for (Map.Entry<ObjectName, DestinationView> entry : this.getOrdering().sortedCopy(this.destinations.entrySet())) {
            if (i >= start2 && i < end) {
                builder.put(entry.getKey(), entry.getValue());
            }
            ++i;
        }
        return builder.build();
    }

    Predicate<DestinationView> getPredicate() {
        return new Predicate<DestinationView>(){

            @Override
            public boolean apply(DestinationView input) {
                boolean match = true;
                if (DestinationsViewFilter.this.getName() != null && !DestinationsViewFilter.this.getName().isEmpty()) {
                    match = input.getName().contains(DestinationsViewFilter.this.getName());
                }
                if (match) {
                    if (DestinationsViewFilter.this.getFilter().equals("empty")) {
                        boolean bl = match = input.getQueueSize() == 0L;
                    }
                    if (DestinationsViewFilter.this.getFilter().equals("nonEmpty")) {
                        boolean bl = match = input.getQueueSize() != 0L;
                    }
                    if (DestinationsViewFilter.this.getFilter().equals("noConsumer")) {
                        boolean bl = match = input.getConsumerCount() == 0L;
                    }
                    if (DestinationsViewFilter.this.getFilter().equals("nonAdvisory")) {
                        return !(input instanceof TopicView) || !AdvisorySupport.isAdvisoryTopic(new ActiveMQTopic(input.getName()));
                    }
                }
                return match;
            }
        };
    }

    Ordering<Map.Entry<ObjectName, DestinationView>> getOrdering() {
        return new Ordering<Map.Entry<ObjectName, DestinationView>>(){
            Method getter;
            {
                this.getter = IntrospectionSupport.findGetterMethod(DestinationView.class, DestinationsViewFilter.this.getSortColumn());
            }

            @Override
            public int compare(Map.Entry<ObjectName, DestinationView> left, Map.Entry<ObjectName, DestinationView> right) {
                try {
                    if (this.getter != null) {
                        Object leftValue = this.getter.invoke((Object)left.getValue(), new Object[0]);
                        Object rightValue = this.getter.invoke((Object)right.getValue(), new Object[0]);
                        if (leftValue instanceof Comparable && rightValue instanceof Comparable) {
                            if (DestinationsViewFilter.this.getSortOrder().toLowerCase().equals("desc")) {
                                return ((Comparable)rightValue).compareTo(leftValue);
                            }
                            return ((Comparable)leftValue).compareTo(rightValue);
                        }
                    }
                    return 0;
                }
                catch (Exception e) {
                    LOG.info("Exception sorting destinations", e);
                    return 0;
                }
            }
        };
    }

    public Map<ObjectName, DestinationView> getDestinations() {
        return this.destinations;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter2) {
        this.filter = filter2;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortColumn() {
        return this.sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }
}

