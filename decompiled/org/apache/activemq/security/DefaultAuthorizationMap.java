/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.security.AuthorizationEntry;
import org.apache.activemq.security.AuthorizationMap;
import org.apache.activemq.security.TempDestinationAuthorizationEntry;

public class DefaultAuthorizationMap
extends DestinationMap
implements AuthorizationMap {
    public static final String DEFAULT_GROUP_CLASS = "org.apache.activemq.jaas.GroupPrincipal";
    private AuthorizationEntry defaultEntry;
    private TempDestinationAuthorizationEntry tempDestinationAuthorizationEntry;
    protected String groupClass = "org.apache.activemq.jaas.GroupPrincipal";
    static final String WILDCARD = "*";

    public DefaultAuthorizationMap() {
    }

    public DefaultAuthorizationMap(List<DestinationMapEntry> authorizationEntries) {
        this.setAuthorizationEntries(authorizationEntries);
    }

    public void setTempDestinationAuthorizationEntry(TempDestinationAuthorizationEntry tempDestinationAuthorizationEntry) {
        this.tempDestinationAuthorizationEntry = tempDestinationAuthorizationEntry;
    }

    public TempDestinationAuthorizationEntry getTempDestinationAuthorizationEntry() {
        return this.tempDestinationAuthorizationEntry;
    }

    public Set<Object> getTempDestinationAdminACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
            answer.addAll(this.tempDestinationAuthorizationEntry.getAdminACLs());
            return answer;
        }
        return null;
    }

    public Set<Object> getTempDestinationReadACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
            answer.addAll(this.tempDestinationAuthorizationEntry.getReadACLs());
            return answer;
        }
        return null;
    }

    public Set<Object> getTempDestinationWriteACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
            answer.addAll(this.tempDestinationAuthorizationEntry.getWriteACLs());
            return answer;
        }
        return null;
    }

    public Set<Object> getAdminACLs(ActiveMQDestination destination) {
        Set<AuthorizationEntry> entries = this.getAllEntries(destination);
        WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
        for (AuthorizationEntry entry : entries) {
            answer.addAll(entry.getAdminACLs());
        }
        return answer;
    }

    public Set<Object> getReadACLs(ActiveMQDestination destination) {
        Set<AuthorizationEntry> entries = this.getAllEntries(destination);
        WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
        for (AuthorizationEntry entry : entries) {
            answer.addAll(entry.getReadACLs());
        }
        return answer;
    }

    public Set<Object> getWriteACLs(ActiveMQDestination destination) {
        Set<AuthorizationEntry> entries = this.getAllEntries(destination);
        WildcardAwareSet<Object> answer = new WildcardAwareSet<Object>();
        for (AuthorizationEntry entry : entries) {
            answer.addAll(entry.getWriteACLs());
        }
        return answer;
    }

    public AuthorizationEntry getEntryFor(ActiveMQDestination destination) {
        AuthorizationEntry answer = (AuthorizationEntry)this.chooseValue(destination);
        if (answer == null) {
            answer = this.getDefaultEntry();
        }
        return answer;
    }

    @Override
    public synchronized Set get(ActiveMQDestination key) {
        if (key.isComposite()) {
            ActiveMQDestination childDestination;
            ActiveMQDestination[] destinations = key.getCompositeDestinations();
            Set answer = null;
            for (int i = 0; i < destinations.length && (answer = DefaultAuthorizationMap.union(answer, this.get(childDestination = destinations[i]))) != null && !answer.isEmpty(); ++i) {
            }
            return answer;
        }
        return this.findWildcardMatches(key, false);
    }

    public void setAuthorizationEntries(List<DestinationMapEntry> entries) {
        super.setEntries(entries);
    }

    public AuthorizationEntry getDefaultEntry() {
        return this.defaultEntry;
    }

    public void setDefaultEntry(AuthorizationEntry defaultEntry) {
        this.defaultEntry = defaultEntry;
    }

    @Override
    protected Class<? extends DestinationMapEntry> getEntryClass() {
        return AuthorizationEntry.class;
    }

    protected Set<AuthorizationEntry> getAllEntries(ActiveMQDestination destination) {
        Set entries = this.get(destination);
        if (this.defaultEntry != null) {
            entries.add(this.defaultEntry);
        }
        return entries;
    }

    public String getGroupClass() {
        return this.groupClass;
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
    }

    public static Object createGroupPrincipal(String name, String groupClass) throws Exception {
        Object instance;
        Class<?>[] paramTypes;
        int i;
        if (WILDCARD.equals(name)) {
            return new Principal(){

                @Override
                public String getName() {
                    return DefaultAuthorizationMap.WILDCARD;
                }

                @Override
                public boolean equals(Object other) {
                    return true;
                }

                @Override
                public int hashCode() {
                    return DefaultAuthorizationMap.WILDCARD.hashCode();
                }
            };
        }
        Object[] param2 = new Object[]{name};
        Class<?> cls = Class.forName(groupClass);
        Constructor<?>[] constructors = cls.getConstructors();
        for (i = 0; !(i >= constructors.length || (paramTypes = constructors[i].getParameterTypes()).length == 1 && paramTypes[0].equals(String.class)); ++i) {
        }
        if (i < constructors.length) {
            instance = constructors[i].newInstance(param2);
        } else {
            Class<?>[] paramTypes2;
            instance = cls.newInstance();
            Method[] methods = cls.getMethods();
            i = 0;
            for (i = 0; !(i >= methods.length || (paramTypes2 = methods[i].getParameterTypes()).length == 1 && methods[i].getName().equals("setName") && paramTypes2[0].equals(String.class)); ++i) {
            }
            if (i < methods.length) {
                methods[i].invoke(instance, param2);
            } else {
                throw new NoSuchMethodException();
            }
        }
        return instance;
    }

    class WildcardAwareSet<T>
    extends HashSet<T> {
        boolean hasWildcard = false;

        WildcardAwareSet() {
        }

        @Override
        public boolean contains(Object e) {
            if (this.hasWildcard) {
                return true;
            }
            return super.contains(e);
        }

        @Override
        public boolean addAll(Collection<? extends T> collection) {
            boolean modified = false;
            for (T item : collection) {
                if (this.isWildcard(item)) {
                    this.hasWildcard = true;
                }
                if (!this.add(item)) continue;
                modified = true;
            }
            return modified;
        }

        private boolean isWildcard(T item) {
            try {
                if (item.getClass().getMethod("getName", new Class[0]).invoke(item, new Object[0]).equals(DefaultAuthorizationMap.WILDCARD)) {
                    return true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return false;
        }
    }
}

