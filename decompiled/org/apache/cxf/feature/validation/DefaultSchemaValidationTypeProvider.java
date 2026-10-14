/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.validation;

import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.feature.validation.SchemaValidationTypeProvider;
import org.apache.cxf.service.model.OperationInfo;

public class DefaultSchemaValidationTypeProvider
implements SchemaValidationTypeProvider {
    private final Map<String, SchemaValidation.SchemaValidationType> operationMap = new HashMap<String, SchemaValidation.SchemaValidationType>();

    public DefaultSchemaValidationTypeProvider(Map<String, SchemaValidation.SchemaValidationType> operationMap) {
        this.operationMap.putAll(operationMap);
    }

    @Override
    public SchemaValidation.SchemaValidationType getSchemaValidationType(OperationInfo info) {
        SchemaValidation.SchemaValidationType t2 = this.operationMap.get(info.getName().getLocalPart());
        if (t2 == null) {
            t2 = this.operationMap.get("*");
        }
        return t2;
    }
}

