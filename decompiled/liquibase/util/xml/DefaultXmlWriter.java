/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import liquibase.GlobalConfiguration;
import liquibase.util.xml.XmlWriter;
import org.w3c.dom.Document;

public class DefaultXmlWriter
implements XmlWriter {
    @Override
    public void write(Document doc, OutputStream outputStream2) throws IOException {
        try {
            TransformerFactory factory2 = TransformerFactory.newInstance();
            try {
                factory2.setAttribute("indent-number", 4);
            }
            catch (Exception exception) {
                // empty catch block
            }
            Transformer transformer = factory2.newTransformer();
            transformer.setOutputProperty("method", "xml");
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
            OutputStreamWriter writer = new OutputStreamWriter(outputStream2, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            writer.flush();
            writer.close();
        }
        catch (TransformerException e) {
            throw new IOException(e.getMessage());
        }
    }
}

