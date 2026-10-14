/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi.internal;

import java.util.function.Function;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ValueParamProvider {
    public Function<ContainerRequest, ?> getValueProvider(Parameter var1);

    public PriorityType getPriority();

    public static enum Priority implements PriorityType
    {
        LOW(100),
        NORMAL(200),
        HIGH(300);

        private final int weight;

        private Priority(int weight) {
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    public static interface PriorityType {
        public int getWeight();
    }
}

