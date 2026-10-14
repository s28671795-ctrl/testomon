/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.xml;

import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.core.ParsedNode;
import liquibase.resource.ResourceAccessor;

public abstract class AbstractChangeLogParser
implements ChangeLogParser {
    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        ParsedNode parsedNode = this.parseToNode(physicalChangeLogLocation, changeLogParameters, resourceAccessor);
        if (parsedNode == null) {
            return null;
        }
        DatabaseChangeLog changeLog = new DatabaseChangeLog(DatabaseChangeLog.normalizePath(physicalChangeLogLocation));
        changeLog.setChangeLogParameters(changeLogParameters);
        try {
            changeLog.load(parsedNode, resourceAccessor);
        }
        catch (Exception e) {
            throw new ChangeLogParseException(e);
        }
        return changeLog;
    }

    protected abstract ParsedNode parseToNode(String var1, ChangeLogParameters var2, ResourceAccessor var3) throws ChangeLogParseException;
}

