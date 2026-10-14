/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.format.standard.AddressCreator;
import java.io.Serializable;
import java.util.Map;

public abstract class AddressNetwork<S extends AddressSegment>
implements Serializable {
    private static final long serialVersionUID = 4L;
    private static PrefixConfiguration defaultPrefixConfiguration = PrefixConfiguration.PREFIXED_ZERO_HOSTS_ARE_SUBNETS;

    public abstract AddressCreator<?, ?, ?, S> getAddressCreator();

    public void clearCaches() {
        this.getAddressCreator().clearCaches();
    }

    public void setSegmentCaching(boolean enable) {
        this.getAddressCreator().setSegmentCaching(enable);
    }

    public abstract PrefixConfiguration getPrefixConfiguration();

    public static PrefixConfiguration getDefaultPrefixConfiguration() {
        return defaultPrefixConfiguration;
    }

    protected boolean isCompatible(AddressNetwork<?> other) {
        return IPAddressSection.isCompatibleNetworks(this, other);
    }

    public static abstract class HostIdentifierStringGenerator<T extends HostIdentifierString>
    implements Serializable {
        private static final long serialVersionUID = 4L;
        protected final Map<String, T> backingMap;

        public HostIdentifierStringGenerator() {
            this(null);
        }

        public HostIdentifierStringGenerator(Map<String, T> backingMap) {
            this.backingMap = backingMap;
        }

        public Map<String, T> getBackingMap() {
            return this.backingMap;
        }

        protected void added(T added) {
        }

        public boolean contains(T value) {
            return this.backingMap.containsValue(value);
        }

        public T get(String key) {
            if (this.backingMap == null) {
                return this.create(key);
            }
            HostIdentifierString result = (HostIdentifierString)this.backingMap.get(key);
            if (result == null) {
                result = this.create(key);
                String normalizedKey = result.toNormalizedString();
                HostIdentifierString existing = this.backingMap.putIfAbsent(normalizedKey, result = this.create(normalizedKey));
                if (existing == null) {
                    this.added(result);
                } else {
                    result = existing;
                }
                if (!normalizedKey.equals(key)) {
                    this.backingMap.put(key, result);
                }
            }
            return (T)result;
        }

        public abstract T get(byte[] var1);

        public abstract T get(Address.AddressValueProvider var1);

        protected abstract T create(String var1);
    }

    public static enum PrefixConfiguration {
        ALL_PREFIXED_ADDRESSES_ARE_SUBNETS,
        PREFIXED_ZERO_HOSTS_ARE_SUBNETS,
        EXPLICIT_SUBNETS;


        public boolean allPrefixedAddressesAreSubnets() {
            return this == ALL_PREFIXED_ADDRESSES_ARE_SUBNETS;
        }

        public boolean zeroHostsAreSubnets() {
            return this == PREFIXED_ZERO_HOSTS_ARE_SUBNETS;
        }

        public boolean prefixedSubnetsAreExplicit() {
            return this == EXPLICIT_SUBNETS;
        }
    }

    public static interface AddressSegmentCreator<S extends AddressSegment> {
        public S[] createSegmentArray(int var1);

        public S createSegment(int var1);

        public S createSegment(int var1, Integer var2);

        public S createSegment(int var1, int var2, Integer var3);
    }
}

