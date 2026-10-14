/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.changelog.visitor.ValidatingVisitorGenerator;
import liquibase.plugin.AbstractPluginFactory;

public class ValidatingVisitorGeneratorFactory
extends AbstractPluginFactory<ValidatingVisitorGenerator> {
    @Override
    protected Class<ValidatingVisitorGenerator> getPluginClass() {
        return ValidatingVisitorGenerator.class;
    }

    @Override
    protected int getPriority(ValidatingVisitorGenerator obj, Object ... args2) {
        return obj.getPriority();
    }

    public ValidatingVisitorGenerator getValidatingVisitorGenerator() {
        return (ValidatingVisitorGenerator)this.getPlugin(new Object[0]);
    }

    private ValidatingVisitorGeneratorFactory() {
    }
}

