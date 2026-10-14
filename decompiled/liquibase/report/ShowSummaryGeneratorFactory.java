/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import liquibase.plugin.AbstractPluginFactory;
import liquibase.report.ShowSummaryGenerator;

public class ShowSummaryGeneratorFactory
extends AbstractPluginFactory<ShowSummaryGenerator> {
    private ShowSummaryGeneratorFactory() {
    }

    @Override
    protected Class<ShowSummaryGenerator> getPluginClass() {
        return ShowSummaryGenerator.class;
    }

    @Override
    protected int getPriority(ShowSummaryGenerator obj, Object ... args2) {
        return obj.getPriority();
    }

    public ShowSummaryGenerator getShowSummaryGenerator() {
        return (ShowSummaryGenerator)this.getPlugin(new Object[0]);
    }
}

