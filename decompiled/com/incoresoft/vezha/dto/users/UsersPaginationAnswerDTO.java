/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.users;

import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import com.incoresoft.vezha.dto.users.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="UsersPaginationAnswerDTO", description="Users pagination answer")
public class UsersPaginationAnswerDTO
extends PaginationAnswer<UserDTO> {
    public UsersPaginationAnswerDTO(List<UserDTO> data, long total, int pageSize) {
        super(data, total, pageSize);
    }
}

