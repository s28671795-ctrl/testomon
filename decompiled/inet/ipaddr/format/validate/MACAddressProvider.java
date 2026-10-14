/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.MACAddressStringParameters;
import inet.ipaddr.format.validate.ParsedMACAddress;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.io.Serializable;
import java.util.Arrays;

public interface MACAddressProvider
extends Serializable {
    public static final MACAddressProvider EMPTY_PROVIDER = new MACAddressProvider(){

        @Override
        public MACAddress getAddress() {
            return null;
        }

        public String toString() {
            return "null";
        }
    };

    public MACAddress getAddress();

    public static MACAddressProvider getAllProvider(MACAddressStringParameters validationOptions) {
        MACAddressNetwork network = validationOptions.getNetwork();
        MACAddressStringParameters.AddressSize allAddresses = validationOptions.addressSize;
        final MACAddressNetwork.MACAddressCreator creator = network.getAddressCreator();
        MACAddressSegment allRangeSegment = creator.createRangeSegment(0, 255);
        Object[] segments = creator.createSegmentArray(allAddresses == MACAddressStringParameters.AddressSize.EUI64 ? 8 : 6);
        Arrays.fill(segments, allRangeSegment);
        return new MACAddressProvider((MACAddressSegment[])segments){
            private static final long serialVersionUID = 4L;
            final /* synthetic */ MACAddressSegment[] val$segments;
            {
                this.val$segments = mACAddressSegmentArray;
            }

            @Override
            public MACAddress getAddress() {
                MACAddressNetwork.MACAddressCreator parsedCreator = creator;
                MACAddressSection section = (MACAddressSection)parsedCreator.createSectionInternal(this.val$segments);
                return creator.createAddress(section);
            }

            public String toString() {
                return String.valueOf(this.getAddress());
            }
        };
    }

    public static final class ParsedMACAddressProvider
    implements MACAddressProvider {
        private static final long serialVersionUID = 4L;
        private ParsedMACAddress parsedAddress;
        private MACAddress address;

        public ParsedMACAddressProvider(MACAddress address) {
            this.address = address;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public MACAddress getAddress() {
            if (this.parsedAddress != null) {
                ParsedMACAddressProvider parsedMACAddressProvider = this;
                synchronized (parsedMACAddressProvider) {
                    if (this.parsedAddress != null) {
                        this.address = this.parsedAddress.createAddress();
                        this.parsedAddress = null;
                    }
                }
            }
            return this.address;
        }

        public String toString() {
            return String.valueOf(this.getAddress());
        }
    }
}

