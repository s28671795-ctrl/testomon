/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectReadArgs;
import io.minio.messages.InputSerialization;
import io.minio.messages.OutputSerialization;
import java.util.Objects;

public class SelectObjectContentArgs
extends ObjectReadArgs {
    private String sqlExpression;
    private InputSerialization inputSerialization;
    private OutputSerialization outputSerialization;
    private Boolean requestProgress;
    private Long scanStartRange;
    private Long scanEndRange;

    public Long scanEndRange() {
        return this.scanEndRange;
    }

    public Long scanStartRange() {
        return this.scanStartRange;
    }

    public Boolean requestProgress() {
        return this.requestProgress;
    }

    public OutputSerialization outputSerialization() {
        return this.outputSerialization;
    }

    public InputSerialization inputSerialization() {
        return this.inputSerialization;
    }

    public String sqlExpression() {
        return this.sqlExpression;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SelectObjectContentArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SelectObjectContentArgs that = (SelectObjectContentArgs)o;
        return Objects.equals(this.sqlExpression, that.sqlExpression) && Objects.equals(this.inputSerialization, that.inputSerialization) && Objects.equals(this.outputSerialization, that.outputSerialization) && Objects.equals(this.requestProgress, that.requestProgress) && Objects.equals(this.scanStartRange, that.scanStartRange) && Objects.equals(this.scanEndRange, that.scanEndRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.sqlExpression, this.inputSerialization, this.outputSerialization, this.requestProgress, this.scanStartRange, this.scanEndRange);
    }

    public static final class Builder
    extends ObjectReadArgs.Builder<Builder, SelectObjectContentArgs> {
        private void validateSqlExpression(String se) {
            this.validateNotEmptyString(se, "sqlExpression");
        }

        public Builder sqlExpression(String sqlExpression) {
            this.validateSqlExpression(sqlExpression);
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).sqlExpression = sqlExpression);
            return this;
        }

        private void validateInputSerialization(InputSerialization is) {
            this.validateNotNull(is, "inputSerialization");
        }

        public Builder inputSerialization(InputSerialization inputSerialization) {
            this.validateInputSerialization(inputSerialization);
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).inputSerialization = inputSerialization);
            return this;
        }

        private void validateOutputSerialization(OutputSerialization os) {
            this.validateNotNull(os, "outputSerialization");
        }

        public Builder outputSerialization(OutputSerialization outputSerialization) {
            this.validateOutputSerialization(outputSerialization);
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).outputSerialization = outputSerialization);
            return this;
        }

        public Builder requestProgress(Boolean requestProgress) {
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).requestProgress = requestProgress);
            return this;
        }

        public Builder scanStartRange(Long scanStartRange) {
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).scanStartRange = scanStartRange);
            return this;
        }

        public Builder scanEndRange(Long scanEndRange) {
            this.operations.add(args2 -> ((SelectObjectContentArgs)args2).scanEndRange = scanEndRange);
            return this;
        }

        @Override
        protected void validate(SelectObjectContentArgs args2) {
            super.validate(args2);
            this.validateSqlExpression(args2.sqlExpression());
            this.validateInputSerialization(args2.inputSerialization());
            this.validateOutputSerialization(args2.outputSerialization());
        }
    }
}

