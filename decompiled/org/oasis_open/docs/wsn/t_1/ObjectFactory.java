/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.t_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wsn.t_1.Documentation;
import org.oasis_open.docs.wsn.t_1.QueryExpressionType;
import org.oasis_open.docs.wsn.t_1.TopicNamespaceType;
import org.oasis_open.docs.wsn.t_1.TopicSetType;
import org.oasis_open.docs.wsn.t_1.TopicType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _TopicNamespace_QNAME = new QName("http://docs.oasis-open.org/wsn/t-1", "TopicNamespace");
    private static final QName _TopicSet_QNAME = new QName("http://docs.oasis-open.org/wsn/t-1", "TopicSet");

    public TopicNamespaceType createTopicNamespaceType() {
        return new TopicNamespaceType();
    }

    public TopicSetType createTopicSetType() {
        return new TopicSetType();
    }

    public Documentation createDocumentation() {
        return new Documentation();
    }

    public QueryExpressionType createQueryExpressionType() {
        return new QueryExpressionType();
    }

    public TopicType createTopicType() {
        return new TopicType();
    }

    public TopicNamespaceType.Topic createTopicNamespaceTypeTopic() {
        return new TopicNamespaceType.Topic();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/t-1", name="TopicNamespace")
    public JAXBElement<TopicNamespaceType> createTopicNamespace(TopicNamespaceType value) {
        return new JAXBElement<TopicNamespaceType>(_TopicNamespace_QNAME, TopicNamespaceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/t-1", name="TopicSet")
    public JAXBElement<TopicSetType> createTopicSet(TopicSetType value) {
        return new JAXBElement<TopicSetType>(_TopicSet_QNAME, TopicSetType.class, null, value);
    }
}

