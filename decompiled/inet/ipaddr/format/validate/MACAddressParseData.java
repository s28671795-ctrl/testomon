/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.format.validate.AddressParseData;

class MACAddressParseData
extends AddressParseData {
    private static final long serialVersionUID = 4L;
    private boolean isDoubleSegment;
    private boolean isExtended;
    private MACFormat format;

    MACAddressParseData(CharSequence str) {
        super(str);
    }

    AddressParseData getAddressParseData() {
        return this;
    }

    MACFormat getFormat() {
        return this.format;
    }

    void setFormat(MACFormat val) {
        this.format = val;
    }

    void setDoubleSegment(boolean val) {
        this.isDoubleSegment = val;
    }

    boolean isDoubleSegment() {
        return this.isDoubleSegment;
    }

    void setExtended(boolean val) {
        this.isExtended = val;
    }

    boolean isExtended() {
        return this.isExtended;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.toString(builder);
        if (this.isDoubleSegment()) {
            builder.append("is double segment").append('\n');
        }
        builder.append("bit length:").append(this.isExtended() ? 64 : 48).append('\n');
        MACFormat format = this.getFormat();
        if (format != null) {
            builder.append((Object)format);
        }
        return builder.toString();
    }

    static enum MACFormat {
        DASHED('-'),
        COLON_DELIMITED(':'),
        DOTTED('.'),
        SPACE_DELIMITED(' ');

        private char separator;

        private MACFormat(char separator) {
            this.separator = separator;
        }

        char getSeparator() {
            return this.separator;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("mac format:").append(super.toString()).append('\n');
            builder.append("segment separator:").append(this.separator).append('\n');
            return builder.toString();
        }
    }
}

