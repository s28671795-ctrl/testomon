/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

public final class OSFCodeSetRegistry {
    public static final int ISO_8859_1_VALUE = 65537;
    public static final int UTF_16_VALUE = 65801;
    public static final int UTF_8_VALUE = 0x5010001;
    public static final int UCS_2_VALUE = 65792;
    public static final int ISO_646_VALUE = 65568;
    public static final Entry ISO_8859_1 = new Entry("ISO-8859-1", 65537, true, 1);
    static final Entry UTF_16BE = new Entry("UTF-16BE", -1, true, 2);
    static final Entry UTF_16LE = new Entry("UTF-16LE", -2, true, 2);
    public static final Entry UTF_16 = new Entry("UTF-16", 65801, true, 4);
    public static final Entry UTF_8 = new Entry("UTF-8", 0x5010001, false, 6);
    public static final Entry UCS_2 = new Entry("UCS-2", 65792, true, 2);
    public static final Entry ISO_646 = new Entry("US-ASCII", 65568, true, 1);

    private OSFCodeSetRegistry() {
    }

    public static Entry lookupEntry(int encodingValue) {
        switch (encodingValue) {
            case 65537: {
                return ISO_8859_1;
            }
            case 65801: {
                return UTF_16;
            }
            case 0x5010001: {
                return UTF_8;
            }
            case 65568: {
                return ISO_646;
            }
            case 65792: {
                return UCS_2;
            }
        }
        return null;
    }

    public static final class Entry {
        private String javaName;
        private int encodingNum;
        private boolean isFixedWidth;
        private int maxBytesPerChar;

        private Entry(String javaName, int encodingNum, boolean isFixedWidth, int maxBytesPerChar) {
            this.javaName = javaName;
            this.encodingNum = encodingNum;
            this.isFixedWidth = isFixedWidth;
            this.maxBytesPerChar = maxBytesPerChar;
        }

        public String getName() {
            return this.javaName;
        }

        public int getNumber() {
            return this.encodingNum;
        }

        public boolean isFixedWidth() {
            return this.isFixedWidth;
        }

        public int getMaxBytesPerChar() {
            return this.maxBytesPerChar;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry other = (Entry)obj;
            return this.javaName.equals(other.javaName) && this.encodingNum == other.encodingNum && this.isFixedWidth == other.isFixedWidth && this.maxBytesPerChar == other.maxBytesPerChar;
        }

        public int hashCode() {
            return this.encodingNum;
        }
    }
}

