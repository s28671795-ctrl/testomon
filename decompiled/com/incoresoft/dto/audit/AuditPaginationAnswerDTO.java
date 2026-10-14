/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit;

import com.incoresoft.dto.audit.AuditEventDTO;
import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="AuditPaginationAnswerDTO", description="Audit logs pagination answer class description")
public class AuditPaginationAnswerDTO
extends PaginationAnswer<AuditEventDTO> {
    public AuditPaginationAnswerDTO(List<AuditEventDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

