/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ElementDescriptor;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.descriptor.ElementDescriptorImpl;

public class ClassDescriptorImpl
extends ElementDescriptorImpl
implements ElementDescriptor {
    public ClassDescriptorImpl(Type beanType, Set<ConstraintDescriptorImpl<?>> constraints, boolean defaultGroupSequenceRedefined, List<Class<?>> defaultGroupSequence) {
        super(beanType, constraints, defaultGroupSequenceRedefined, defaultGroupSequence);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append("{beanType=").append(this.getElementClass());
        sb.append('}');
        return sb.toString();
    }
}

