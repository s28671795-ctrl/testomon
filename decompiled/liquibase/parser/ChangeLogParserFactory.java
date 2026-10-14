/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.UnknownChangelogFormatException;
import liquibase.parser.ChangeLogParser;
import liquibase.resource.ResourceAccessor;

public class ChangeLogParserFactory {
    private static ChangeLogParserFactory instance;
    private final List<ChangeLogParser> parsers = new ArrayList<ChangeLogParser>();

    public static synchronized void reset() {
        instance = new ChangeLogParserFactory();
    }

    public static synchronized ChangeLogParserFactory getInstance() {
        if (instance == null) {
            instance = new ChangeLogParserFactory();
        }
        return instance;
    }

    public static synchronized void setInstance(ChangeLogParserFactory instance) {
        ChangeLogParserFactory.instance = instance;
    }

    private ChangeLogParserFactory() {
        try {
            List<ChangeLogParser> parser = Scope.getCurrentScope().getServiceLocator().findInstances(ChangeLogParser.class);
            this.register(parser);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public List<ChangeLogParser> getParsers() {
        return new ArrayList<ChangeLogParser>(this.parsers);
    }

    public ChangeLogParser getParser(String fileNameOrExtension, ResourceAccessor resourceAccessor) throws LiquibaseException {
        for (ChangeLogParser parser : this.parsers) {
            if (!parser.supports(fileNameOrExtension, resourceAccessor)) continue;
            Scope.getCurrentScope().getLog(ChangeLogParserFactory.class).fine(String.format("Matched file '%s' to parser '%s'", fileNameOrExtension, parser.getClass().getSimpleName()));
            return parser;
        }
        throw new UnknownChangelogFormatException("Cannot find parser that supports " + fileNameOrExtension);
    }

    public void register(ChangeLogParser changeLogParsers) {
        this.register(Collections.singletonList(changeLogParsers));
    }

    private void register(List<ChangeLogParser> changeLogParsers) {
        this.parsers.addAll(changeLogParsers);
        this.parsers.sort(ChangeLogParser.COMPARATOR);
    }

    public void unregister(ChangeLogParser changeLogParser) {
        this.parsers.remove(changeLogParser);
    }

    public void unregisterAllParsers() {
        this.parsers.clear();
    }
}

