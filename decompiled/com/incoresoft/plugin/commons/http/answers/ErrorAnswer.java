/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.middleware.http.errors.Error;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.http.answers.Answer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@Schema(name="ErrorAnswer", description="Error answer")
public class ErrorAnswer
extends Answer {
    @JSONField(name="errors")
    @ArraySchema(schema=@Schema(implementation=ErrorDTO.class))
    private final List<ErrorDTO> errors;

    public ErrorAnswer(@NotNull List<ErrorDTO> errors2) {
        super("error");
        this.errors = Objects.requireNonNull(errors2);
    }

    public ErrorAnswer(ValidationException error) {
        this(error.getViolations().stream().map(it -> new ErrorDTO(it.getType(), it.getField(), it.getArgs())).collect(Collectors.toList()));
    }

    public ErrorAnswer(ErrorException e) {
        this(e.getError(), e.getArgs());
    }

    public ErrorAnswer(Error error, Object ... args2) {
        this(List.of(new ErrorDTO(error.getLocaleKey(), args2)));
    }

    public ErrorAnswer(Error error, String field2, Object ... args2) {
        this(List.of(new ErrorDTO(error.getLocaleKey(), field2, args2)));
    }

    public List<ErrorDTO> getErrors() {
        return this.errors;
    }

    public static class Serializer
    implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            if (object instanceof ErrorAnswer) {
                serializer.write(((ErrorAnswer)object).getErrors());
            } else {
                serializer.writeNull();
            }
        }
    }
}

