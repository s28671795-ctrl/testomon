/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.validation;

import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.service.model.OperationInfo;

public interface SchemaValidationTypeProvider {
    public SchemaValidation.SchemaValidationType getSchemaValidationType(OperationInfo var1);
}

