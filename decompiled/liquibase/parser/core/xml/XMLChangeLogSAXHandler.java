/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.xml;

import java.util.Stack;
import liquibase.Scope;
import liquibase.change.ChangeFactory;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.logging.Logger;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.precondition.PreconditionFactory;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.visitor.SqlVisitorFactory;
import liquibase.util.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XMLChangeLogSAXHandler
extends DefaultHandler {
    private final ChangeFactory changeFactory;
    private final PreconditionFactory preconditionFactory;
    private final SqlVisitorFactory sqlVisitorFactory;
    private final ChangeLogParserFactory changeLogParserFactory;
    protected Logger log;
    private final DatabaseChangeLog databaseChangeLog;
    private final ResourceAccessor resourceAccessor;
    private final ChangeLogParameters changeLogParameters;
    private final Stack<ParsedNode> nodeStack = new Stack();
    private final Stack<StringBuilder> textStack = new Stack();
    private ParsedNode databaseChangeLogTree;

    protected XMLChangeLogSAXHandler(String physicalChangeLogLocation, ResourceAccessor resourceAccessor, ChangeLogParameters changeLogParameters) {
        this.log = Scope.getCurrentScope().getLog(this.getClass());
        this.resourceAccessor = resourceAccessor;
        this.databaseChangeLog = new DatabaseChangeLog();
        this.databaseChangeLog.setPhysicalFilePath(physicalChangeLogLocation);
        this.databaseChangeLog.setChangeLogParameters(changeLogParameters);
        this.changeLogParameters = changeLogParameters == null ? new ChangeLogParameters() : changeLogParameters;
        this.changeFactory = Scope.getCurrentScope().getSingleton(ChangeFactory.class);
        this.preconditionFactory = PreconditionFactory.getInstance();
        this.sqlVisitorFactory = SqlVisitorFactory.getInstance();
        this.changeLogParserFactory = ChangeLogParserFactory.getInstance();
    }

    public DatabaseChangeLog getDatabaseChangeLog() {
        return this.databaseChangeLog;
    }

    public ParsedNode getDatabaseChangeLogTree() {
        return this.databaseChangeLogTree;
    }

    @Override
    public void characters(char[] ch, int start2, int length) {
        this.textStack.peek().append(new String(ch, start2, length));
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {
        ParsedNode node = new ParsedNode(null, localName);
        try {
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); ++i) {
                    node.addChild(null, attributes.getLocalName(i), attributes.getValue(i));
                    continue;
                }
            }
            if (!this.nodeStack.isEmpty()) {
                this.nodeStack.peek().addChild(node);
            }
            if (this.nodeStack.isEmpty()) {
                this.databaseChangeLogTree = node;
            }
            this.nodeStack.push(node);
            this.textStack.push(new StringBuilder());
        }
        catch (ParsedNodeException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        ParsedNode node = this.nodeStack.pop();
        try {
            String seenText = this.textStack.pop().toString();
            if (!"".equals(StringUtil.trimToEmpty(seenText))) {
                node.setValue(seenText.trim());
            }
        }
        catch (ParsedNodeException e) {
            throw new SAXException(e);
        }
    }
}

