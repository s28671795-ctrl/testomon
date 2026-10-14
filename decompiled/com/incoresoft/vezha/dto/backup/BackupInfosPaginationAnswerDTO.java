/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.backup;

import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import com.incoresoft.vezha.dto.backup.BackupInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="BackupInfosPaginationAnswerDTO", description="Pagination answer for backup infos")
public class BackupInfosPaginationAnswerDTO
extends PaginationAnswer<BackupInfoDTO> {
    public BackupInfosPaginationAnswerDTO(List<BackupInfoDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

