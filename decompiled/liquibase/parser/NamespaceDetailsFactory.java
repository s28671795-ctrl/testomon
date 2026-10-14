/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.LiquibaseParser;
import liquibase.parser.NamespaceDetails;
import liquibase.serializer.LiquibaseSerializer;

public class NamespaceDetailsFactory {
    private static NamespaceDetailsFactory instance;
    private final List<NamespaceDetails> namespaceDetails = new ArrayList<NamespaceDetails>();

    public static synchronized void reset() {
        instance = null;
    }

    public static synchronized NamespaceDetailsFactory getInstance() {
        if (instance == null) {
            instance = new NamespaceDetailsFactory();
        }
        return instance;
    }

    private NamespaceDetailsFactory() {
        try {
            for (NamespaceDetails details : Scope.getCurrentScope().getServiceLocator().findInstances(NamespaceDetails.class)) {
                this.register(details);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Collection<NamespaceDetails> getNamespaceDetails() {
        return Collections.unmodifiableCollection(this.namespaceDetails);
    }

    public NamespaceDetails getNamespaceDetails(LiquibaseParser parser, String namespace) {
        TreeSet<NamespaceDetails> validNamespaceDetails = new TreeSet<NamespaceDetails>(new SerializerNamespaceDetailsComparator());
        for (NamespaceDetails details : this.namespaceDetails) {
            if (!details.supports(parser, namespace)) continue;
            validNamespaceDetails.add(details);
        }
        if (validNamespaceDetails.isEmpty()) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("No parser namespace details associated with namespace '" + namespace + "' and parser " + parser.getClass().getName());
        }
        return (NamespaceDetails)validNamespaceDetails.iterator().next();
    }

    public NamespaceDetails getNamespaceDetails(LiquibaseSerializer serializer, String namespace) {
        TreeSet<NamespaceDetails> validNamespaceDetails = new TreeSet<NamespaceDetails>(new SerializerNamespaceDetailsComparator());
        for (NamespaceDetails details : this.namespaceDetails) {
            if (!details.supports(serializer, namespace)) continue;
            validNamespaceDetails.add(details);
        }
        if (validNamespaceDetails.isEmpty()) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("No serializer namespace details associated with namespace '" + namespace + "' and serializer " + serializer.getClass().getName());
        }
        return (NamespaceDetails)validNamespaceDetails.iterator().next();
    }

    public void register(NamespaceDetails namespaceDetails) {
        this.namespaceDetails.add(namespaceDetails);
    }

    public void unregister(NamespaceDetails namespaceDetails) {
        this.namespaceDetails.remove(namespaceDetails);
    }

    private class SerializerNamespaceDetailsComparator
    implements Comparator<NamespaceDetails> {
        private SerializerNamespaceDetailsComparator() {
        }

        @Override
        public int compare(NamespaceDetails o1, NamespaceDetails o2) {
            return Integer.compare(o2.getPriority(), o1.getPriority());
        }
    }
}

