/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.InputSerialization;
import io.minio.messages.OutputSerialization;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;

@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public abstract class SelectObjectContentRequestBase {
    @Element(name="Expression")
    private String expression;
    @Element(name="ExpressionType")
    private String expressionType = "SQL";
    @Element(name="InputSerialization")
    private InputSerialization inputSerialization;
    @Element(name="OutputSerialization")
    private OutputSerialization outputSerialization;

    public SelectObjectContentRequestBase(@Nonnull String expression, @Nonnull InputSerialization is, @Nonnull OutputSerialization os) {
        this.expression = Objects.requireNonNull(expression, "Expression must not be null");
        this.inputSerialization = Objects.requireNonNull(is, "InputSerialization must not be null");
        this.outputSerialization = Objects.requireNonNull(os, "OutputSerialization must not be null");
    }
}

