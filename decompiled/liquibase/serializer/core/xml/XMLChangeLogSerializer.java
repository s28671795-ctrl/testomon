/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import liquibase.GlobalConfiguration;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.NamespaceDetails;
import liquibase.parser.NamespaceDetailsFactory;
import liquibase.parser.core.xml.LiquibaseEntityResolver;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.util.ISODateFormat;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;
import liquibase.util.XMLUtil;
import liquibase.util.xml.DefaultXmlWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLChangeLogSerializer
implements ChangeLogSerializer {
    public static final String INVALID_STRING_ENCODING_MESSAGE = "Invalid string encoding";
    private Document currentChangeLogFileDOM;
    private static final String XML_VERSION = "1.1";
    private final LiquibaseEntityResolver resolver = new LiquibaseEntityResolver();

    public XMLChangeLogSerializer() {
        try {
            this.currentChangeLogFileDOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            this.currentChangeLogFileDOM.setXmlVersion(XML_VERSION);
        }
        catch (ParserConfigurationException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    protected XMLChangeLogSerializer(Document currentChangeLogFileDOM) {
        this.currentChangeLogFileDOM = currentChangeLogFileDOM;
    }

    public void setCurrentChangeLogFileDOM(Document currentChangeLogFileDOM) {
        this.currentChangeLogFileDOM = currentChangeLogFileDOM;
    }

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"xml"};
    }

    public String serialize(DatabaseChangeLog databaseChangeLog) {
        return null;
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        StringBuilder buffer = new StringBuilder();
        int indent = -1;
        if (pretty) {
            indent = 0;
        }
        this.nodeToStringBuilder(this.createNode(object), buffer, indent);
        return buffer.toString();
    }

    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        DocumentBuilder documentBuilder;
        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
        factory2.setNamespaceAware(true);
        try {
            documentBuilder = factory2.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        documentBuilder.setEntityResolver(this.resolver);
        Document doc = documentBuilder.newDocument();
        doc.setXmlVersion(XML_VERSION);
        Element changeLogElement = doc.createElementNS("http://www.liquibase.org/xml/ns/dbchangelog", "databaseChangeLog");
        changeLogElement.setAttribute("xmlns", "http://www.liquibase.org/xml/ns/dbchangelog");
        changeLogElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        HashMap<String, String> shortNameByNamespace = new HashMap<String, String>();
        HashMap<String, String> urlByNamespace = new HashMap<String, String>();
        for (NamespaceDetails namespaceDetails : NamespaceDetailsFactory.getInstance().getNamespaceDetails()) {
            for (String namespace : namespaceDetails.getNamespaces()) {
                if (namespaceDetails.getPriority() <= 0 || !namespaceDetails.supports(this, namespace)) continue;
                String shortName = namespaceDetails.getShortName(namespace);
                String url2 = namespaceDetails.getSchemaUrl(namespace);
                if (shortName != null) {
                    shortNameByNamespace.put(namespace, shortName);
                }
                if (url2 == null) continue;
                urlByNamespace.put(namespace, url2);
            }
        }
        for (Map.Entry entry : shortNameByNamespace.entrySet()) {
            if ("".equals(entry.getValue())) continue;
            changeLogElement.setAttribute("xmlns:" + (String)entry.getValue(), (String)entry.getKey());
        }
        StringBuilder schemaLocationAttribute = new StringBuilder();
        for (Map.Entry entry : urlByNamespace.entrySet()) {
            if ("".equals(entry.getValue())) continue;
            schemaLocationAttribute.append((String)entry.getKey()).append(" ").append((String)entry.getValue()).append(" ");
        }
        changeLogElement.setAttribute("xsi:schemaLocation", schemaLocationAttribute.toString().trim());
        doc.appendChild(changeLogElement);
        this.setCurrentChangeLogFileDOM(doc);
        for (ChangeLogChild child : children) {
            doc.getDocumentElement().appendChild(this.createNode(child));
        }
        new DefaultXmlWriter().write(doc, out);
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
        String existingChangeLog;
        try (InputStream in = Files.newInputStream(changeLogFile.toPath(), new OpenOption[0]);){
            existingChangeLog = StreamUtil.readStreamAsString(in);
        }
        try (OutputStream out = Files.newOutputStream(changeLogFile.toPath(), new OpenOption[0]);){
            if (!existingChangeLog.contains("</databaseChangeLog>")) {
                this.write(Collections.singletonList(changeSet), out);
            } else {
                existingChangeLog = existingChangeLog.replaceFirst("</databaseChangeLog>", this.serialize(changeSet, true) + "\n</databaseChangeLog>");
                StreamUtil.copy(new ByteArrayInputStream(existingChangeLog.getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())), out);
            }
            out.flush();
        }
    }

    public Element createNode(LiquibaseSerializable object) {
        String namespace = object.getSerializedObjectNamespace();
        String nodeName = object.getSerializedObjectName();
        NamespaceDetails details = NamespaceDetailsFactory.getInstance().getNamespaceDetails(this, namespace);
        if (details != null && !"".equals(details.getShortName(namespace))) {
            nodeName = details.getShortName(namespace) + ":" + nodeName;
        }
        Element node = this.currentChangeLogFileDOM.createElementNS(namespace, nodeName);
        try {
            for (String field2 : object.getSerializableFields()) {
                this.setValueOnNode(node, object.getSerializableFieldNamespace(field2), field2, object.getSerializableFieldValue(field2), object.getSerializableFieldType(field2), namespace);
            }
        }
        catch (UnexpectedLiquibaseException e) {
            if (object instanceof ChangeSet && e.getMessage().startsWith(INVALID_STRING_ENCODING_MESSAGE)) {
                throw new UnexpectedLiquibaseException(e.getMessage() + " in changeSet " + ((ChangeSet)object).toString(false) + ". To resolve, remove the invalid character on the database and try again");
            }
            throw e;
        }
        return node;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setValueOnNode(Element node, String objectNamespace, String objectName, Object value, LiquibaseSerializable.SerializationType serializationType, String parentNamespace) {
        if (value == null) {
            return;
        }
        if (value instanceof Collection) {
            for (Object child : (Collection)value) {
                this.setValueOnNode(node, objectNamespace, objectName, child, serializationType, parentNamespace);
            }
            return;
        } else if (value instanceof Map) {
            for (Map.Entry entry : ((Map)value).entrySet()) {
                Element mapNode = this.currentChangeLogFileDOM.createElementNS("http://www.liquibase.org/xml/ns/dbchangelog", this.qualifyName(objectName, objectNamespace, parentNamespace));
                if (serializationType == LiquibaseSerializable.SerializationType.NESTED_OBJECT) {
                    this.setValueOnNode(mapNode, objectNamespace, (String)entry.getKey(), entry.getValue(), serializationType, objectNamespace);
                } else {
                    this.setValueOnNode(mapNode, objectNamespace, "name", entry.getKey(), LiquibaseSerializable.SerializationType.NAMED_FIELD, objectNamespace);
                    this.setValueOnNode(mapNode, objectNamespace, "value", entry.getValue(), serializationType, objectNamespace);
                }
                node.appendChild(mapNode);
            }
            return;
        } else if (value instanceof LiquibaseSerializable) {
            node.appendChild(this.createNode((LiquibaseSerializable)value));
            return;
        } else if (value instanceof Object[]) {
            if (serializationType.equals((Object)LiquibaseSerializable.SerializationType.NESTED_OBJECT)) {
                String namespace = "http://www.liquibase.org/xml/ns/dbchangelog";
                Element newNode = this.createNode(namespace, objectName, "");
                for (Object child : (Object[])value) {
                    this.setValueOnNode(newNode, namespace, objectName, child, serializationType, parentNamespace);
                }
                node.appendChild(newNode);
                return;
            } else {
                for (Object child : (Object[])value) {
                    this.setValueOnNode(node, objectNamespace, objectName, child, serializationType, parentNamespace);
                }
            }
            return;
        } else if (serializationType.equals((Object)LiquibaseSerializable.SerializationType.NESTED_OBJECT)) {
            String namespace = "http://www.liquibase.org/xml/ns/dbchangelog";
            node.appendChild(this.createNode(namespace, objectName, value.toString()));
            return;
        } else if (serializationType.equals((Object)LiquibaseSerializable.SerializationType.DIRECT_VALUE)) {
            try {
                node.setTextContent(this.checkString(value.toString()));
                return;
            }
            catch (UnexpectedLiquibaseException e) {
                if (!e.getMessage().startsWith(INVALID_STRING_ENCODING_MESSAGE)) return;
                throw new UnexpectedLiquibaseException(e.getMessage() + " in text of " + node.getTagName() + ". To resolve, remove the invalid character on the database and try again");
            }
        } else {
            String attributeName = this.qualifyName(objectName, objectNamespace, parentNamespace);
            try {
                node.setAttribute(attributeName, this.checkString(value.toString()));
                return;
            }
            catch (UnexpectedLiquibaseException e) {
                if (!e.getMessage().startsWith(INVALID_STRING_ENCODING_MESSAGE)) return;
                throw new UnexpectedLiquibaseException(e.getMessage() + " on " + node.getTagName() + "." + attributeName + ". To resolve, remove the invalid character on the database and try again");
            }
        }
    }

    protected String checkString(String text) throws UnexpectedLiquibaseException {
        if (null == text || text.isEmpty()) {
            return text;
        }
        int len = text.length();
        for (int i = 0; i < len; ++i) {
            int current = text.charAt(i);
            int codePoint = Character.isHighSurrogate((char)current) && i + 1 < len && Character.isLowSurrogate(text.charAt(i + 1)) ? text.codePointAt(i++) : current;
            if (codePoint == 10 || codePoint == 13 || codePoint == 9 || codePoint == 11 || codePoint == 12 || codePoint >= 32 && codePoint <= 126 || codePoint >= 160 && codePoint <= 55295 || codePoint >= 57344 && codePoint <= 65533 || codePoint >= 65536 && codePoint <= 0x10FFFF) continue;
            throw new UnexpectedLiquibaseException(INVALID_STRING_ENCODING_MESSAGE);
        }
        return text;
    }

    private String qualifyName(String objectName, String objectNamespace, String parentNamespace) {
        if (objectNamespace != null && !objectNamespace.equals("http://www.liquibase.org/xml/ns/dbchangelog") && !objectNamespace.equals(parentNamespace)) {
            NamespaceDetails details = NamespaceDetailsFactory.getInstance().getNamespaceDetails(this, objectNamespace);
            return details.getShortName(objectNamespace) + ":" + objectName;
        }
        return objectName;
    }

    public Element createNode(String nodeNamespace, String nodeName, String nodeContent) {
        Element element = this.currentChangeLogFileDOM.createElementNS(nodeNamespace, nodeName);
        element.setTextContent(nodeContent);
        return element;
    }

    public Element createNode(ColumnConfig columnConfig) {
        ConstraintsConfig constraints;
        Element element = this.currentChangeLogFileDOM.createElementNS(columnConfig.getSerializedObjectNamespace(), "column");
        if (columnConfig.getName() != null) {
            element.setAttribute("name", columnConfig.getName());
        }
        if (columnConfig.getType() != null) {
            element.setAttribute("type", columnConfig.getType());
        }
        if (columnConfig.getDefaultValue() != null) {
            element.setAttribute("defaultValue", columnConfig.getDefaultValue());
        }
        if (columnConfig.getDefaultValueNumeric() != null) {
            element.setAttribute("defaultValueNumeric", columnConfig.getDefaultValueNumeric().toString());
        }
        if (columnConfig.getDefaultValueDate() != null) {
            element.setAttribute("defaultValueDate", new ISODateFormat().format(columnConfig.getDefaultValueDate()));
        }
        if (columnConfig.getDefaultValueBoolean() != null) {
            element.setAttribute("defaultValueBoolean", columnConfig.getDefaultValueBoolean().toString());
        }
        if (columnConfig.getDefaultValueComputed() != null) {
            element.setAttribute("defaultValueComputed", columnConfig.getDefaultValueComputed().toString());
        }
        if (columnConfig.getDefaultValueSequenceNext() != null) {
            element.setAttribute("defaultValueSequenceNext", columnConfig.getDefaultValueSequenceNext().toString());
        }
        if (columnConfig.getValue() != null) {
            element.setAttribute("value", columnConfig.getValue());
        }
        if (columnConfig.getValueNumeric() != null) {
            element.setAttribute("valueNumeric", columnConfig.getValueNumeric().toString());
        }
        if (columnConfig.getValueBoolean() != null) {
            element.setAttribute("valueBoolean", columnConfig.getValueBoolean().toString());
        }
        if (columnConfig.getValueDate() != null) {
            element.setAttribute("valueDate", new ISODateFormat().format(columnConfig.getValueDate()));
        }
        if (columnConfig.getValueComputed() != null) {
            element.setAttribute("valueComputed", columnConfig.getValueComputed().toString());
        }
        if (columnConfig.getValueSequenceNext() != null) {
            element.setAttribute("valueSequenceNext", columnConfig.getValueSequenceNext().toString());
        }
        if (columnConfig.getValueSequenceCurrent() != null) {
            element.setAttribute("valueSequenceNext", columnConfig.getValueSequenceCurrent().toString());
        }
        if (StringUtil.trimToNull(columnConfig.getRemarks()) != null) {
            element.setAttribute("remarks", columnConfig.getRemarks());
        }
        if (columnConfig.isAutoIncrement() != null && columnConfig.isAutoIncrement().booleanValue()) {
            element.setAttribute("autoIncrement", "true");
        }
        if ((constraints = columnConfig.getConstraints()) != null) {
            Element constraintsElement = this.currentChangeLogFileDOM.createElementNS(columnConfig.getSerializedObjectNamespace(), "constraints");
            if (constraints.getCheckConstraint() != null) {
                constraintsElement.setAttribute("checkConstraint", constraints.getCheckConstraint());
            }
            if (constraints.getForeignKeyName() != null) {
                constraintsElement.setAttribute("foreignKeyName", constraints.getForeignKeyName());
            }
            if (constraints.getReferences() != null) {
                constraintsElement.setAttribute("references", constraints.getReferences());
            }
            if (constraints.getReferencedTableName() != null) {
                constraintsElement.setAttribute("referencedTableName", constraints.getReferencedTableName());
            }
            if (constraints.getReferencedColumnNames() != null) {
                constraintsElement.setAttribute("referencedColumnNames", constraints.getReferencedColumnNames());
            }
            if (constraints.isDeferrable() != null) {
                constraintsElement.setAttribute("deferrable", constraints.isDeferrable().toString());
            }
            if (constraints.getValidateNullable() != null) {
                constraintsElement.setAttribute("validateNullable", constraints.getValidateNullable().toString());
            }
            if (constraints.getValidateUnique() != null) {
                constraintsElement.setAttribute("validateUnique", constraints.getValidateUnique().toString());
            }
            if (constraints.getValidatePrimaryKey() != null) {
                constraintsElement.setAttribute("validatePrimaryKey", constraints.getValidatePrimaryKey().toString());
            }
            if (constraints.getValidateForeignKey() != null) {
                constraintsElement.setAttribute("validateForeignKey", constraints.getValidateForeignKey().toString());
            }
            if (constraints.isDeleteCascade() != null) {
                constraintsElement.setAttribute("deleteCascade", constraints.isDeleteCascade().toString());
            }
            if (constraints.isInitiallyDeferred() != null) {
                constraintsElement.setAttribute("initiallyDeferred", constraints.isInitiallyDeferred().toString());
            }
            if (constraints.isNullable() != null) {
                constraintsElement.setAttribute("nullable", constraints.isNullable().toString());
            }
            if (constraints.isPrimaryKey() != null) {
                constraintsElement.setAttribute("primaryKey", constraints.isPrimaryKey().toString());
            }
            if (constraints.isUnique() != null) {
                constraintsElement.setAttribute("unique", constraints.isUnique().toString());
            }
            if (constraints.getUniqueConstraintName() != null) {
                constraintsElement.setAttribute("uniqueConstraintName", constraints.getUniqueConstraintName());
            }
            if (constraints.getPrimaryKeyName() != null) {
                constraintsElement.setAttribute("primaryKeyName", constraints.getPrimaryKeyName());
            }
            if (constraints.getPrimaryKeyTablespace() != null) {
                constraintsElement.setAttribute("primaryKeyTablespace", constraints.getPrimaryKeyTablespace());
            }
            if (constraints.getNotNullConstraintName() != null) {
                constraintsElement.setAttribute("notNullConstraintName", constraints.getNotNullConstraintName());
            }
            if (constraints.getReferencedTableCatalogName() != null) {
                constraintsElement.setAttribute("referencedTableCatalogName", constraints.getReferencedTableCatalogName());
            }
            if (constraints.getReferencedTableSchemaName() != null) {
                constraintsElement.setAttribute("referencedTableSchemaName", constraints.getReferencedTableSchemaName());
            }
            element.appendChild(constraintsElement);
        }
        return element;
    }

    private void nodeToStringBuilder(Node node, StringBuilder buffer, int indent) {
        boolean bl;
        if (indent >= 0) {
            if (indent > 0) {
                buffer.append("\n");
            }
            buffer.append(StringUtil.repeat(" ", indent));
        }
        buffer.append("<").append(node.getNodeName());
        TreeMap<String, String> attributeMap = new TreeMap<String, String>();
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attribute = attributes.item(i);
            attributeMap.put(attribute.getNodeName(), attribute.getNodeValue());
        }
        boolean firstAttribute = true;
        for (Map.Entry entry : attributeMap.entrySet()) {
            String value = (String)entry.getValue();
            if (value == null) continue;
            if (indent >= 0 && !firstAttribute && attributeMap.size() > 2) {
                buffer.append("\n").append(StringUtil.repeat(" ", indent)).append("        ");
            } else {
                buffer.append(" ");
            }
            buffer.append(entry.getKey()).append("=\"").append(value).append("\"");
            firstAttribute = false;
        }
        String textContent = StringUtil.trimToEmpty(XMLUtil.getTextContent(node));
        textContent = this.escapeXml(textContent);
        buffer.append(">").append(textContent);
        boolean bl2 = false;
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node childNode = childNodes.item(i);
            if (!(childNode instanceof Element)) continue;
            int newIndent = indent;
            if (newIndent >= 0) {
                newIndent += 4;
            }
            this.nodeToStringBuilder(childNode, buffer, newIndent);
            bl = true;
        }
        if (indent >= 0 && bl) {
            buffer.append("\n").append(StringUtil.repeat(" ", indent));
        }
        if (!bl && "".equals(textContent)) {
            buffer.replace(buffer.length() - 1, buffer.length(), "/>");
        } else {
            buffer.append("</").append(node.getNodeName()).append(">");
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public String escapeXml(String valueToEscape) {
        return valueToEscape;
    }
}

