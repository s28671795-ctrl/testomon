/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.msv.grammar.ExpressionPool
 *  com.sun.msv.grammar.xmlschema.XMLSchemaGrammar
 *  com.sun.msv.grammar.xmlschema.XMLSchemaSchema
 *  com.sun.msv.reader.GrammarReaderController
 *  com.sun.msv.reader.State
 *  com.sun.msv.reader.xmlschema.MultiSchemaReader
 *  com.sun.msv.reader.xmlschema.SchemaState
 *  com.sun.msv.reader.xmlschema.XMLSchemaReader
 *  com.sun.msv.reader.xmlschema.XMLSchemaReader$StateFactory
 */
package org.apache.cxf.staxutils.validation;

import com.ctc.wstx.msv.BaseSchemaFactory;
import com.ctc.wstx.msv.W3CSchema;
import com.sun.msv.grammar.ExpressionPool;
import com.sun.msv.grammar.xmlschema.XMLSchemaGrammar;
import com.sun.msv.grammar.xmlschema.XMLSchemaSchema;
import com.sun.msv.reader.GrammarReaderController;
import com.sun.msv.reader.State;
import com.sun.msv.reader.xmlschema.MultiSchemaReader;
import com.sun.msv.reader.xmlschema.SchemaState;
import com.sun.msv.reader.xmlschema.XMLSchemaReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.staxutils.validation.EmbeddedSchema;
import org.apache.cxf.staxutils.validation.ResolvingGrammarReaderController;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;

public class W3CMultiSchemaFactory
extends BaseSchemaFactory {
    private MultiSchemaReader multiSchemaReader;
    private SAXParserFactory parserFactory;
    private RecursiveAllowedXMLSchemaReader xmlSchemaReader;

    public W3CMultiSchemaFactory() {
        super("http://www.w3.org/2001/XMLSchema");
    }

    public XMLValidationSchema loadSchemas(String baseURI, Map<String, EmbeddedSchema> sources) throws XMLStreamException {
        this.parserFactory = W3CMultiSchemaFactory.getSaxFactory();
        ResolvingGrammarReaderController ctrl = new ResolvingGrammarReaderController(baseURI, sources);
        this.xmlSchemaReader = new RecursiveAllowedXMLSchemaReader((GrammarReaderController)ctrl, this.parserFactory);
        this.multiSchemaReader = new MultiSchemaReader((XMLSchemaReader)this.xmlSchemaReader);
        for (EmbeddedSchema source2 : sources.values()) {
            DOMSource domSource = new DOMSource(source2.getSchemaElement());
            domSource.setSystemId(source2.getSystemId());
            this.multiSchemaReader.parse((Source)domSource);
        }
        XMLSchemaGrammar grammar = this.multiSchemaReader.getResult();
        if (grammar == null) {
            throw new XMLStreamException("Failed to load schemas");
        }
        return new W3CSchema(grammar);
    }

    @Override
    protected XMLValidationSchema loadSchema(InputSource src, Object sysRef) throws XMLStreamException {
        throw new XMLStreamException("W3CMultiSchemaFactory does not support the provider API.");
    }

    static class RecursiveAllowedXMLSchemaReader
    extends XMLSchemaReader {
        Set<String> sysIds = new TreeSet<String>();

        RecursiveAllowedXMLSchemaReader(GrammarReaderController controller, SAXParserFactory parserFactory) {
            super(controller, parserFactory, new XMLSchemaReader.StateFactory(){

                public State schemaHead(String expectedNamespace) {
                    return new SchemaState(expectedNamespace){
                        private XMLSchemaSchema old;

                        protected void endSelf() {
                            super.endSelf();
                            RecursiveAllowedXMLSchemaReader r = (RecursiveAllowedXMLSchemaReader)this.reader;
                            r.currentSchema = this.old;
                        }

                        protected void onTargetNamespaceResolved(String targetNs, boolean ignoreContents) {
                            RecursiveAllowedXMLSchemaReader r = (RecursiveAllowedXMLSchemaReader)this.reader;
                            this.old = r.currentSchema;
                            r.currentSchema = r.getOrCreateSchema(targetNs);
                            if (ignoreContents) {
                                return;
                            }
                            if (!r.isSchemaDefined(r.currentSchema)) {
                                r.markSchemaAsDefined(r.currentSchema);
                            }
                        }
                    };
                }
            }, new ExpressionPool());
        }

        public void setLocator(Locator locator) {
            if (locator == null && this.getLocator() != null && this.getLocator().getSystemId() != null) {
                this.sysIds.add(this.getLocator().getSystemId());
            }
            super.setLocator(locator);
        }

        public void switchSource(Source source2, State newState) {
            String url2 = source2.getSystemId();
            if (url2 != null && this.sysIds.contains(url2)) {
                return;
            }
            super.switchSource(source2, newState);
        }
    }
}

