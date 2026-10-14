/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.UnknownFormatException;
import liquibase.parser.SnapshotParser;
import liquibase.resource.ResourceAccessor;

public class SnapshotParserFactory {
    private static SnapshotParserFactory instance;
    private final List<SnapshotParser> parsers;
    private final Comparator<SnapshotParser> snapshotParserComparator = (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority());

    public static synchronized void reset() {
        instance = new SnapshotParserFactory();
    }

    public static synchronized SnapshotParserFactory getInstance() {
        if (instance == null) {
            instance = new SnapshotParserFactory();
        }
        return instance;
    }

    public static synchronized void setInstance(SnapshotParserFactory instance) {
        SnapshotParserFactory.instance = instance;
    }

    private SnapshotParserFactory() {
        this.parsers = new ArrayList<SnapshotParser>();
        try {
            for (SnapshotParser parser : Scope.getCurrentScope().getServiceLocator().findInstances(SnapshotParser.class)) {
                this.register(parser);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public List<SnapshotParser> getParsers() {
        return this.parsers;
    }

    public SnapshotParser getParser(String fileNameOrExtension, ResourceAccessor resourceAccessor) throws LiquibaseException {
        for (SnapshotParser parser : this.parsers) {
            if (!parser.supports(fileNameOrExtension, resourceAccessor)) continue;
            return parser;
        }
        throw new UnknownFormatException("Cannot find parser that supports " + fileNameOrExtension);
    }

    public void register(SnapshotParser snapshotParser) {
        this.parsers.add(snapshotParser);
        this.parsers.sort(this.snapshotParserComparator);
    }

    public void unregister(SnapshotParser snapshotParser) {
        this.parsers.remove(snapshotParser);
    }
}

