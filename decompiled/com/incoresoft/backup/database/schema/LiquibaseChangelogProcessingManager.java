/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.schema;

import com.google.inject.Inject;
import com.incoresoft.backup.database.exceptions.LiquibaseChangelogProcessingException;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jooq.SQLDialect;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LiquibaseChangelogProcessingManager {
    private final SQLDialect dialect;

    public void process(File rawChangelogFile, File processedChangelogFile) {
        if (processedChangelogFile.exists() || !rawChangelogFile.exists()) {
            throw new LiquibaseChangelogProcessingException();
        }
        try {
            Document document = this.getDocument(rawChangelogFile);
            if (this.dialect == SQLDialect.SQLSERVER) {
                this.processSqlServerChangelog(document);
            }
            this.saveDocument(document, processedChangelogFile);
        }
        catch (Exception e) {
            throw new LiquibaseChangelogProcessingException(e);
        }
    }

    private void processSqlServerChangelog(Document document) {
        NodeList columns = document.getElementsByTagName("column");
        int size = columns.getLength();
        for (int i = 0; i < size; ++i) {
            Node current = columns.item(i);
            String parentNodeName = current.getParentNode().getNodeName();
            if (!parentNodeName.equals("createIndex") && !parentNodeName.equals("createTable") || current.getNodeType() != 1) continue;
            Element element = (Element)current;
            element.removeAttribute("defaultValueComputed");
            element.removeAttribute("defaultValueConstraintName");
        }
    }

    private Document getDocument(File rawChangelogFile) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(rawChangelogFile);
        document.getDocumentElement().normalize();
        return document;
    }

    private void saveDocument(Document document, File processedChangelogFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source2 = new DOMSource(document);
        StreamResult result = new StreamResult(processedChangelogFile);
        transformer.transform(source2, result);
    }

    @Inject
    public LiquibaseChangelogProcessingManager(SQLDialect dialect) {
        this.dialect = dialect;
    }
}

