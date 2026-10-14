/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.DefaultHeaders;
import io.grpc.netty.shaded.io.netty.handler.codec.Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.ValueConverter;
import io.grpc.netty.shaded.io.netty.handler.codec.http.DefaultHttpHeaders;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderNames;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.HashingStrategy;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CombinedHttpHeaders
extends DefaultHttpHeaders {
    public CombinedHttpHeaders(boolean validate) {
        super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, CombinedHttpHeaders.valueConverter(), CombinedHttpHeaders.nameValidator(validate), CombinedHttpHeaders.valueValidator(validate)));
    }

    @Override
    public boolean containsValue(CharSequence name, CharSequence value, boolean ignoreCase) {
        return super.containsValue(name, StringUtil.trimOws(value), ignoreCase);
    }

    private static final class CombinedHttpHeadersImpl
    extends DefaultHeaders<CharSequence, CharSequence, CombinedHttpHeadersImpl> {
        private static final int VALUE_LENGTH_ESTIMATE = 10;
        private CsvValueEscaper<Object> objectEscaper;
        private CsvValueEscaper<CharSequence> charSequenceEscaper;

        private CsvValueEscaper<Object> objectEscaper() {
            if (this.objectEscaper == null) {
                this.objectEscaper = new CsvValueEscaper<Object>(){

                    @Override
                    public CharSequence escape(CharSequence name, Object value) {
                        CharSequence converted;
                        try {
                            converted = (CharSequence)CombinedHttpHeadersImpl.this.valueConverter().convertObject(value);
                        }
                        catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Failed to convert object value for header '" + name + '\'', e);
                        }
                        return StringUtil.escapeCsv(converted, true);
                    }
                };
            }
            return this.objectEscaper;
        }

        private CsvValueEscaper<CharSequence> charSequenceEscaper() {
            if (this.charSequenceEscaper == null) {
                this.charSequenceEscaper = new CsvValueEscaper<CharSequence>(){

                    @Override
                    public CharSequence escape(CharSequence name, CharSequence value) {
                        return StringUtil.escapeCsv(value, true);
                    }
                };
            }
            return this.charSequenceEscaper;
        }

        CombinedHttpHeadersImpl(HashingStrategy<CharSequence> nameHashingStrategy, ValueConverter<CharSequence> valueConverter, DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator) {
            super(nameHashingStrategy, valueConverter, nameValidator, 16, valueValidator);
        }

        @Override
        public Iterator<CharSequence> valueIterator(CharSequence name) {
            Iterator<CharSequence> itr = super.valueIterator(name);
            if (!itr.hasNext() || CombinedHttpHeadersImpl.cannotBeCombined(name)) {
                return itr;
            }
            Iterator<CharSequence> unescapedItr = StringUtil.unescapeCsvFields(itr.next()).iterator();
            if (itr.hasNext()) {
                throw new IllegalStateException("CombinedHttpHeaders should only have one value");
            }
            return unescapedItr;
        }

        @Override
        public List<CharSequence> getAll(CharSequence name) {
            List<CharSequence> values2 = super.getAll(name);
            if (values2.isEmpty() || CombinedHttpHeadersImpl.cannotBeCombined(name)) {
                return values2;
            }
            if (values2.size() != 1) {
                throw new IllegalStateException("CombinedHttpHeaders should only have one value");
            }
            return StringUtil.unescapeCsvFields(values2.get(0));
        }

        @Override
        public CombinedHttpHeadersImpl add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                throw new IllegalArgumentException("can't add to itself.");
            }
            if (headers instanceof CombinedHttpHeadersImpl) {
                if (this.isEmpty()) {
                    this.addImpl(headers);
                } else {
                    for (Map.Entry<CharSequence, CharSequence> entry : headers) {
                        this.addEscapedValue(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                for (Map.Entry<CharSequence, CharSequence> entry : headers) {
                    this.add(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            this.clear();
            return this.add((Headers)headers);
        }

        @Override
        public CombinedHttpHeadersImpl setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            for (CharSequence charSequence : headers.names()) {
                this.remove(charSequence);
            }
            return this.add(headers);
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence name, CharSequence value) {
            return this.addEscapedValue(name, this.charSequenceEscaper().escape(name, value));
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence name, CharSequence ... values2) {
            return this.addEscapedValue(name, CombinedHttpHeadersImpl.commaSeparate(name, this.charSequenceEscaper(), values2));
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence name, Iterable<? extends CharSequence> values2) {
            return this.addEscapedValue(name, CombinedHttpHeadersImpl.commaSeparate(name, this.charSequenceEscaper(), values2));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence name, Object value) {
            return this.addEscapedValue(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), value));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence name, Iterable<?> values2) {
            return this.addEscapedValue(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), values2));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence name, Object ... values2) {
            return this.addEscapedValue(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), values2));
        }

        @Override
        public CombinedHttpHeadersImpl set(CharSequence name, CharSequence ... values2) {
            this.set(name, CombinedHttpHeadersImpl.commaSeparate(name, this.charSequenceEscaper(), values2));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl set(CharSequence name, Iterable<? extends CharSequence> values2) {
            this.set(name, CombinedHttpHeadersImpl.commaSeparate(name, this.charSequenceEscaper(), values2));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence name, Object value) {
            this.set(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), value));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence name, Object ... values2) {
            this.set(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), values2));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence name, Iterable<?> values2) {
            this.set(name, CombinedHttpHeadersImpl.commaSeparate(name, this.objectEscaper(), values2));
            return this;
        }

        private static boolean cannotBeCombined(CharSequence name) {
            return HttpHeaderNames.SET_COOKIE.contentEqualsIgnoreCase(name);
        }

        private CombinedHttpHeadersImpl addEscapedValue(CharSequence name, CharSequence escapedValue) {
            CharSequence currentValue = (CharSequence)this.get(name);
            if (currentValue == null || CombinedHttpHeadersImpl.cannotBeCombined(name)) {
                super.add(name, escapedValue);
            } else {
                this.set(name, CombinedHttpHeadersImpl.commaSeparateEscapedValues(currentValue, escapedValue));
            }
            return this;
        }

        private static <T> CharSequence commaSeparate(CharSequence name, CsvValueEscaper<T> escaper, T ... values2) {
            StringBuilder sb = new StringBuilder(values2.length * 10);
            if (values2.length > 0) {
                int end = values2.length - 1;
                for (int i = 0; i < end; ++i) {
                    sb.append(escaper.escape(name, values2[i])).append(',');
                }
                sb.append(escaper.escape(name, values2[end]));
            }
            return sb;
        }

        private static <T> CharSequence commaSeparate(CharSequence name, CsvValueEscaper<T> escaper, Iterable<? extends T> values2) {
            StringBuilder sb = values2 instanceof Collection ? new StringBuilder(((Collection)values2).size() * 10) : new StringBuilder();
            Iterator<T> iterator2 = values2.iterator();
            if (iterator2.hasNext()) {
                T next = iterator2.next();
                while (iterator2.hasNext()) {
                    sb.append(escaper.escape(name, next)).append(',');
                    next = iterator2.next();
                }
                sb.append(escaper.escape(name, next));
            }
            return sb;
        }

        private static CharSequence commaSeparateEscapedValues(CharSequence currentValue, CharSequence value) {
            return new StringBuilder(currentValue.length() + 1 + value.length()).append(currentValue).append(',').append(value);
        }

        private static interface CsvValueEscaper<T> {
            public CharSequence escape(CharSequence var1, T var2);
        }
    }
}

