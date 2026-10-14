/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import java.io.Serializable;

public class AddressStringParameters
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_EMPTY = true;
    public static final boolean DEFAULT_ALLOW_ALL = true;
    public static final boolean DEFAULT_ALLOW_SINGLE_SEGMENT = true;
    public final boolean allowEmpty;
    public final boolean allowAll;
    public final boolean allowSingleSegment;

    public BuilderBase toBuilder(BuilderBase builder) {
        builder.allowAll = this.allowAll;
        builder.allowEmpty = this.allowEmpty;
        builder.allowSingleSegment = this.allowSingleSegment;
        return builder;
    }

    public AddressStringParameters(boolean allowEmpty, boolean allowAll, boolean allowSingleSegment) {
        this.allowEmpty = allowEmpty;
        this.allowAll = allowAll;
        this.allowSingleSegment = allowSingleSegment;
    }

    public AddressStringParameters clone() {
        try {
            AddressStringParameters result = (AddressStringParameters)super.clone();
            return result;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public int compareTo(AddressStringParameters o) {
        int result = Boolean.compare(this.allowAll, o.allowAll);
        if (result == 0 && (result = Boolean.compare(this.allowEmpty, o.allowEmpty)) == 0) {
            result = Boolean.compare(this.allowSingleSegment, o.allowSingleSegment);
        }
        return result;
    }

    public boolean equals(Object o) {
        if (o instanceof AddressStringParameters) {
            AddressStringParameters other = (AddressStringParameters)o;
            return this.allowEmpty == other.allowEmpty && this.allowAll == other.allowAll && this.allowSingleSegment == other.allowSingleSegment;
        }
        return false;
    }

    public static class AddressStringFormatParameters
    implements Cloneable,
    Serializable {
        private static final long serialVersionUID = 4L;
        public static final boolean DEFAULT_ALLOW_LEADING_ZEROS = true;
        public static final boolean DEFAULT_ALLOW_UNLIMITED_LEADING_ZEROS = true;
        public static final boolean DEFAULT_ALLOW_WILDCARDED_SEPARATOR = true;
        public static final RangeParameters DEFAULT_RANGE_OPTIONS = RangeParameters.WILDCARD_AND_RANGE;
        public final RangeParameters rangeOptions;
        public final boolean allowWildcardedSeparator;
        public final boolean allowLeadingZeros;
        public final boolean allowUnlimitedLeadingZeros;

        public AddressStringFormatParameters(boolean allowLeadingZeros, boolean allowUnlimitedLeadingZeros, RangeParameters rangeOptions, boolean allowWildcardedSeparator) {
            this.rangeOptions = rangeOptions;
            if (rangeOptions == null) {
                throw new NullPointerException();
            }
            this.allowWildcardedSeparator = allowWildcardedSeparator;
            this.allowLeadingZeros = allowLeadingZeros;
            this.allowUnlimitedLeadingZeros = allowUnlimitedLeadingZeros;
        }

        protected BuilderBase toBuilder(BuilderBase builder) {
            builder.allowUnlimitedLeadingZeros = this.allowUnlimitedLeadingZeros;
            builder.rangeOptions = this.rangeOptions;
            builder.allowWildcardedSeparator = this.allowWildcardedSeparator;
            builder.allowLeadingZeros = this.allowLeadingZeros;
            return builder;
        }

        protected int compareTo(AddressStringFormatParameters o) {
            int result = this.rangeOptions.compareTo(o.rangeOptions);
            if (result == 0 && (result = Boolean.compare(this.allowWildcardedSeparator, o.allowWildcardedSeparator)) == 0) {
                result = Boolean.compare(this.allowLeadingZeros, o.allowLeadingZeros);
            }
            return result;
        }

        public boolean equals(Object o) {
            if (o instanceof AddressStringFormatParameters) {
                AddressStringFormatParameters other = (AddressStringFormatParameters)o;
                return this.rangeOptions.equals(other.rangeOptions) && this.allowUnlimitedLeadingZeros == other.allowUnlimitedLeadingZeros && this.allowWildcardedSeparator == other.allowWildcardedSeparator && this.allowLeadingZeros == other.allowLeadingZeros;
            }
            return false;
        }

        public int hashCode() {
            int hash = this.rangeOptions.hashCode();
            if (this.allowUnlimitedLeadingZeros) {
                hash |= 8;
            }
            if (this.allowWildcardedSeparator) {
                hash |= 0x10;
            }
            if (this.allowLeadingZeros) {
                hash |= 0x20;
            }
            return hash;
        }

        protected static class BuilderBase {
            protected RangeParameters rangeOptions = DEFAULT_RANGE_OPTIONS;
            protected boolean allowWildcardedSeparator = true;
            protected boolean allowLeadingZeros = true;
            protected boolean allowUnlimitedLeadingZeros = true;

            protected BuilderBase() {
            }

            public BuilderBase setRangeOptions(RangeParameters rangeOptions) {
                this.rangeOptions = rangeOptions;
                return this;
            }

            public BuilderBase allowWildcardedSeparator(boolean allow) {
                this.allowWildcardedSeparator = allow;
                return this;
            }

            public BuilderBase allowLeadingZeros(boolean allow) {
                this.allowLeadingZeros = allow;
                if (!allow) {
                    this.allowUnlimitedLeadingZeros = allow;
                }
                return this;
            }

            public BuilderBase allowUnlimitedLeadingZeros(boolean allow) {
                this.allowUnlimitedLeadingZeros = allow;
                if (allow) {
                    this.allowLeadingZeros = allow;
                }
                return this;
            }
        }
    }

    public static class BuilderBase {
        protected boolean allowEmpty = true;
        protected boolean allowAll = true;
        protected boolean allowSingleSegment = true;

        public BuilderBase allowEmpty(boolean allow) {
            this.allowEmpty = allow;
            return this;
        }

        public BuilderBase allowSingleSegment(boolean allow) {
            this.allowSingleSegment = allow;
            return this;
        }

        public BuilderBase allowAll(boolean allow) {
            this.allowAll = allow;
            return this;
        }
    }

    public static class RangeParameters
    implements Comparable<RangeParameters>,
    Cloneable,
    Serializable {
        private static final long serialVersionUID = 4L;
        private final boolean wildcard;
        private final boolean range;
        private final boolean allowReverse;
        private final boolean singleWildcard;
        private final boolean allowInferredBoundary;
        public static final RangeParameters NO_RANGE = new RangeParameters(false, false, false, false, false);
        public static final RangeParameters WILDCARD_ONLY = new RangeParameters(true, false, false, false, true);
        public static final RangeParameters WILDCARD_AND_RANGE = new RangeParameters(true, true, true, true, true);

        public RangeParameters(boolean wildcard, boolean range, boolean reverseAllowed, boolean allowInferred, boolean singleWildcard) {
            this.wildcard = wildcard;
            this.range = range;
            this.allowReverse = reverseAllowed;
            this.allowInferredBoundary = allowInferred;
            this.singleWildcard = singleWildcard;
        }

        public boolean isNoRange() {
            return !this.wildcard && !this.range && !this.singleWildcard;
        }

        public boolean allowsWildcard() {
            return this.wildcard;
        }

        public boolean allowsRangeSeparator() {
            return this.range;
        }

        public boolean allowsReverseRange() {
            return this.allowReverse;
        }

        public boolean allowsInferredBoundary() {
            return this.allowInferredBoundary;
        }

        public boolean allowsSingleWildcard() {
            return this.singleWildcard;
        }

        public RangeParameters clone() {
            try {
                return (RangeParameters)super.clone();
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
        }

        public int hashCode() {
            int result = 0;
            if (this.wildcard) {
                result = 1;
            }
            if (this.range) {
                result |= 2;
            }
            if (this.singleWildcard) {
                result |= 4;
            }
            return result;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof RangeParameters) {
                RangeParameters other = (RangeParameters)o;
                return this.wildcard == other.wildcard && this.range == other.range && this.allowReverse == other.allowReverse && this.allowInferredBoundary == other.allowInferredBoundary && this.singleWildcard == other.singleWildcard;
            }
            return false;
        }

        @Override
        public int compareTo(RangeParameters o) {
            int val = Boolean.compare(this.wildcard, o.wildcard);
            if (val == 0 && (val = Boolean.compare(this.range, o.range)) == 0 && (val = Boolean.compare(this.singleWildcard, o.singleWildcard)) == 0 && (val = Boolean.compare(this.allowReverse, o.allowReverse)) == 0) {
                val = Boolean.compare(this.allowInferredBoundary, o.allowInferredBoundary);
            }
            return val;
        }
    }
}

