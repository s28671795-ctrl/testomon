/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Parameter;
import io.r2dbc.spi.Type;

public final class Parameters {
    private Parameters() {
    }

    public static Parameter in(Type type) {
        Assert.requireNonNull(type, "Type must not be null");
        return Parameters.in(type, null);
    }

    public static Parameter in(Class<?> type) {
        Assert.requireNonNull(type, "Type must not be null");
        return Parameters.in(new DefaultInferredType(type), null);
    }

    public static Parameter in(Object value) {
        Assert.requireNonNull(value, "Value must not be null");
        return Parameters.in(new DefaultInferredType(value.getClass()), value);
    }

    public static Parameter in(Type type, @Nullable Object value) {
        Assert.requireNonNull(type, "Type must not be null");
        return new InParameter(type, value);
    }

    public static Parameter out(Type type) {
        Assert.requireNonNull(type, "Type must not be null");
        return new OutParameter(type);
    }

    public static Parameter out(Class<?> type) {
        Assert.requireNonNull(type, "Type must not be null");
        return Parameters.out(new DefaultInferredType(type));
    }

    public static Parameter inOut(Type type) {
        Assert.requireNonNull(type, "Type must not be null");
        return Parameters.inOut(type, null);
    }

    public static Parameter inOut(Class<?> type) {
        Assert.requireNonNull(type, "Type must not be null");
        return Parameters.inOut(new DefaultInferredType(type), null);
    }

    public static Parameter inOut(Object value) {
        Assert.requireNonNull(value, "Value must not be null");
        return Parameters.inOut(new DefaultInferredType(value.getClass()), value);
    }

    public static Parameter inOut(Type type, @Nullable Object value) {
        Assert.requireNonNull(type, "Type must not be null");
        return new InOutParameter(type, value);
    }

    private static class DefaultInferredType
    implements Type.InferredType,
    Type {
        private final Class<?> javaType;

        DefaultInferredType(Class<?> javaType) {
            this.javaType = javaType;
        }

        @Override
        public Class<?> getJavaType() {
            return this.javaType;
        }

        @Override
        public String getName() {
            return "(inferred)";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Type.InferredType)) {
                return false;
            }
            Type.InferredType that = (Type.InferredType)o;
            return this.getJavaType().equals(that.getJavaType());
        }

        public int hashCode() {
            return this.getJavaType().hashCode();
        }

        public String toString() {
            return "Inferred: " + this.getJavaType().getName();
        }
    }

    private static class InOutParameter
    extends DefaultParameter
    implements Parameter.In,
    Parameter.Out {
        public InOutParameter(Type type, @Nullable Object value) {
            super(type, value);
        }

        public String toString() {
            return "InOut{" + this.getType() + '}';
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Parameter.In && o instanceof Parameter.Out && super.equals(o);
        }
    }

    private static class OutParameter
    extends DefaultParameter
    implements Parameter.Out {
        public OutParameter(Type type) {
            super(type, null);
        }

        public String toString() {
            return "Out{" + this.getType() + '}';
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Parameter.Out && !(o instanceof Parameter.In) && super.equals(o);
        }
    }

    private static class InParameter
    extends DefaultParameter
    implements Parameter.In {
        public InParameter(Type type, @Nullable Object value) {
            super(type, value);
        }

        public String toString() {
            return "In{" + this.getType() + '}';
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Parameter.In && !(o instanceof Parameter.Out) && super.equals(o);
        }
    }

    private static class DefaultParameter
    implements Parameter {
        private final Type type;
        @Nullable
        private final Object value;

        public DefaultParameter(Type type, @Nullable Object value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public Type getType() {
            return this.type;
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Parameter)) {
                return false;
            }
            Parameter that = (Parameter)o;
            if (!this.getType().equals(that.getType())) {
                return false;
            }
            return this.getValue() != null ? this.getValue().equals(that.getValue()) : that.getValue() == null;
        }

        public int hashCode() {
            int result = this.getType().hashCode();
            result = 31 * result + (this.getValue() != null ? this.getValue().hashCode() : 0);
            return result;
        }
    }
}

