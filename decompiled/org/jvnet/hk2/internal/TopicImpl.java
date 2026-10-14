/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.hk2.api.messaging.TopicDistributionService;
import org.glassfish.hk2.utilities.NamedImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

public class TopicImpl<T>
implements Topic<T> {
    private final ServiceLocatorImpl locator;
    private final Type topicType;
    private final Set<Annotation> requiredQualifiers;

    TopicImpl(ServiceLocatorImpl locator, Type topicType, Set<Annotation> requiredQualifiers) {
        this.locator = locator;
        this.topicType = topicType;
        this.requiredQualifiers = Collections.unmodifiableSet(requiredQualifiers);
    }

    @Override
    public void publish(T message) {
        if (message == null) {
            throw new IllegalArgumentException();
        }
        TopicDistributionService distributor = this.locator.getService(TopicDistributionService.class, new Annotation[0]);
        if (distributor == null) {
            throw new IllegalStateException("There is no implementation of the TopicDistributionService to distribute the message");
        }
        distributor.distributeMessage(this, message);
    }

    @Override
    public Topic<T> named(String name) {
        return this.qualifiedWith(new NamedImpl(name));
    }

    @Override
    public <U> Topic<U> ofType(Type type) {
        return new TopicImpl<T>(this.locator, type, this.requiredQualifiers);
    }

    @Override
    public Topic<T> qualifiedWith(Annotation ... qualifiers) {
        HashSet<Annotation> moreAnnotations = new HashSet<Annotation>(this.requiredQualifiers);
        for (Annotation qualifier : qualifiers) {
            moreAnnotations.add(qualifier);
        }
        return new TopicImpl<T>(this.locator, this.topicType, moreAnnotations);
    }

    @Override
    public Type getTopicType() {
        return this.topicType;
    }

    @Override
    public Set<Annotation> getTopicQualifiers() {
        return this.requiredQualifiers;
    }
}

