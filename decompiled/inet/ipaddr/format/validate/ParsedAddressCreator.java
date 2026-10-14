/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.format.validate.Validator;
import java.io.Serializable;

public abstract class ParsedAddressCreator<T extends Address, R extends AddressSection, E extends AddressSection, S extends AddressSegment>
implements Serializable {
    private static final long serialVersionUID = 4L;

    public void clearCaches() {
        for (int i = 0; i < Validator.MASK_CACHE.length; ++i) {
            Validator.MASK_CACHE[i] = null;
        }
    }

    public void setSegmentCaching(boolean enable) {
    }

    public abstract S[] createSegmentArray(int var1);

    public abstract S createSegment(int var1, int var2, Integer var3);

    protected abstract S createSegmentInternal(int var1, Integer var2, CharSequence var3, int var4, boolean var5, int var6, int var7);

    protected abstract S createRangeSegmentInternal(int var1, int var2, Integer var3, CharSequence var4, int var5, int var6, boolean var7, boolean var8, int var9, int var10, int var11);

    protected abstract R createSectionInternal(S[] var1);

    protected abstract R createPrefixedSectionInternal(S[] var1, Integer var2);

    protected abstract R createPrefixedSectionInternal(S[] var1, Integer var2, boolean var3);

    protected R createSectionInternal(S[] segments, E embeddedSection, Integer prefix, boolean singleOnly) {
        return (R)this.createPrefixedSectionInternal((AddressSegment[])segments, prefix, singleOnly);
    }

    protected R createSectionInternal(S[] segments, E embeddedSection) {
        return (R)this.createSectionInternal((AddressSegment[])segments);
    }

    protected R createSectionInternal(S[] segments, E embeddedSection, Integer prefix) {
        return (R)this.createPrefixedSectionInternal((AddressSegment[])segments, prefix);
    }

    protected abstract T createAddressInternal(byte[] var1, CharSequence var2);

    protected abstract T createAddressInternal(R var1, HostIdentifierString var2);

    protected abstract T createAddressInternal(R var1, CharSequence var2, HostIdentifierString var3);

    protected T createAddressInternal(S[] segments, HostIdentifierString from, Integer prefix) {
        return this.createAddressInternal(this.createPrefixedSectionInternal((AddressSegment[])segments, prefix), from);
    }

    protected T createAddressInternal(S[] segments, CharSequence zone, HostIdentifierString from, Integer prefix) {
        return this.createAddressInternal(this.createPrefixedSectionInternal((AddressSegment[])segments, prefix), zone, from);
    }

    protected abstract T createAddressInternal(R var1, CharSequence var2, HostIdentifierString var3, T var4, T var5);

    protected abstract int getMaxValuePerSegment();
}

