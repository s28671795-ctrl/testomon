/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.criteria.entity.impl;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.metadata.criteria.entity.EvaluableEntityDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EvaluableEntityRoleEntityDescriptorCriterion
implements EvaluableEntityDescriptorCriterion {
    private QName role;

    public EvaluableEntityRoleEntityDescriptorCriterion(EntityRoleCriterion criterion) {
        Constraint.isNotNull(criterion, "EntityRoleCriterion was null");
        this.role = Constraint.isNotNull(criterion.getRole(), "Criterion role QName was null");
    }

    public EvaluableEntityRoleEntityDescriptorCriterion(QName entityRole) {
        this.role = Constraint.isNotNull(entityRole, "Entity Role QName was null");
    }

    @Override
    public boolean apply(EntityDescriptor entityDescriptor) {
        if (entityDescriptor == null) {
            return false;
        }
        return !entityDescriptor.getRoleDescriptors(this.role).isEmpty();
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof EvaluableEntityRoleEntityDescriptorCriterion) {
            return Objects.equals(this.role, ((EvaluableEntityRoleEntityDescriptorCriterion)other).role);
        }
        return false;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("role", this.role).toString();
    }
}

