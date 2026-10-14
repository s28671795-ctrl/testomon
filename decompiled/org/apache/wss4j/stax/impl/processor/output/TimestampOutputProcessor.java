/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.UsernameTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public class TimestampOutputProcessor
extends AbstractOutputProcessor {
    public TimestampOutputProcessor() throws XMLSecurityException {
        this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
        this.addBeforeProcessor(EncryptOutputProcessor.class.getName());
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        outputProcessorChain.processEvent(xmlSecEvent);
        if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
            QName headerElementName = WSSConstants.TAG_WSU_TIMESTAMP;
            OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
            Instant created = Instant.now();
            int ttl = ((WSSSecurityProperties)this.getSecurityProperties()).getTimestampTTL();
            Instant expires = created.plusSeconds(ttl);
            OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
            this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, true, null);
            this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_CREATED, false, null);
            DateTimeFormatter formatter = DateUtil.getDateTimeFormatter(true);
            this.createCharactersAndOutputAsEvent(subOutputProcessorChain, created.atZone(ZoneOffset.UTC).format(formatter));
            this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_CREATED);
            this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_EXPIRES, false, null);
            this.createCharactersAndOutputAsEvent(subOutputProcessorChain, expires.atZone(ZoneOffset.UTC).format(formatter));
            this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_EXPIRES);
            this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
            outputProcessorChain.removeProcessor(this);
        }
    }
}

