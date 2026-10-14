/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jvnet.mimepull.InternetHeaders;
import org.jvnet.mimepull.MIMEConfig;
import org.jvnet.mimepull.MIMEEvent;
import org.jvnet.mimepull.MIMEParser;
import org.jvnet.mimepull.MIMEParsingException;
import org.jvnet.mimepull.MIMEPart;

public class MIMEMessage
implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(MIMEMessage.class.getName());
    MIMEConfig config;
    private final InputStream in;
    private final Iterator<MIMEEvent> it;
    private boolean parsed;
    private MIMEPart currentPart;
    private int currentIndex;
    private final List<MIMEPart> partsList = new ArrayList<MIMEPart>();
    private final Map<String, MIMEPart> partsMap = new HashMap<String, MIMEPart>();

    public MIMEMessage(InputStream in, String boundary) {
        this(in, boundary, new MIMEConfig());
    }

    public MIMEMessage(InputStream in, String boundary, MIMEConfig config) {
        this.in = in;
        this.config = config;
        MIMEParser parser = new MIMEParser(in, boundary, config);
        this.it = parser.iterator();
        if (config.isParseEagerly()) {
            this.parseAll();
        }
    }

    public List<MIMEPart> getAttachments() {
        if (!this.parsed) {
            this.parseAll();
        }
        return this.partsList;
    }

    public MIMEPart getPart(int index) {
        MIMEPart part;
        LOGGER.log(Level.FINE, "index={0}", index);
        MIMEPart mIMEPart = part = index < this.partsList.size() ? this.partsList.get(index) : null;
        if (this.parsed && part == null) {
            throw new MIMEParsingException("There is no " + index + " attachment part ");
        }
        if (part == null) {
            part = new MIMEPart(this);
            this.partsList.add(index, part);
        }
        LOGGER.log(Level.FINE, "Got attachment at index={0} attachment={1}", new Object[]{index, part});
        return part;
    }

    public MIMEPart getPart(String contentId) {
        LOGGER.log(Level.FINE, "Content-ID={0}", contentId);
        MIMEPart part = this.getDecodedCidPart(contentId);
        if (this.parsed && part == null) {
            throw new MIMEParsingException("There is no attachment part with Content-ID = " + contentId);
        }
        if (part == null) {
            part = new MIMEPart(this, contentId);
            this.partsMap.put(contentId, part);
        }
        LOGGER.log(Level.FINE, "Got attachment for Content-ID={0} attachment={1}", new Object[]{contentId, part});
        return part;
    }

    private MIMEPart getDecodedCidPart(String cid) {
        MIMEPart part = this.partsMap.get(cid);
        if (part == null && cid.indexOf(37) != -1) {
            try {
                String tempCid = URLDecoder.decode(cid, "utf-8");
                part = this.partsMap.get(tempCid);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return part;
    }

    public final void parseAll() {
        while (this.makeProgress()) {
        }
    }

    @Override
    public void close() {
        this.close(this.partsList);
        this.close(this.partsMap.values());
    }

    private void close(Collection<MIMEPart> parts) {
        for (MIMEPart part : parts) {
            try {
                part.close();
            }
            catch (MIMEParsingException closeError) {
                LOGGER.log(Level.FINE, "Exception during closing MIME part", closeError);
            }
        }
    }

    public synchronized boolean makeProgress() {
        if (!this.it.hasNext()) {
            return false;
        }
        MIMEEvent event = this.it.next();
        switch (event.getEventType()) {
            case START_MESSAGE: {
                LOGGER.log(Level.FINE, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.START_MESSAGE);
                break;
            }
            case START_PART: {
                LOGGER.log(Level.FINE, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.START_PART);
                break;
            }
            case HEADERS: {
                String cid;
                LOGGER.log(Level.FINE, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.HEADERS);
                MIMEEvent.Headers headers = (MIMEEvent.Headers)event;
                InternetHeaders ih = headers.getHeaders();
                List<String> cids = ih.getHeader("content-id");
                String string = cid = cids != null ? cids.get(0) : this.currentIndex + "";
                if (cid.length() > 2 && cid.charAt(0) == '<') {
                    cid = cid.substring(1, cid.length() - 1);
                }
                MIMEPart listPart = this.currentIndex < this.partsList.size() ? this.partsList.get(this.currentIndex) : null;
                MIMEPart mapPart = this.getDecodedCidPart(cid);
                if (listPart == null && mapPart == null) {
                    this.currentPart = this.getPart(cid);
                    this.partsList.add(this.currentIndex, this.currentPart);
                } else if (listPart == null) {
                    this.currentPart = mapPart;
                    this.partsList.add(this.currentIndex, mapPart);
                } else if (mapPart == null) {
                    this.currentPart = listPart;
                    this.currentPart.setContentId(cid);
                    this.partsMap.put(cid, this.currentPart);
                } else if (listPart != mapPart) {
                    throw new MIMEParsingException("Created two different attachments using Content-ID and index");
                }
                this.currentPart.setHeaders(ih);
                break;
            }
            case CONTENT: {
                LOGGER.log(Level.FINER, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.CONTENT);
                MIMEEvent.Content content = (MIMEEvent.Content)event;
                ByteBuffer buf = content.getData();
                this.currentPart.addBody(buf);
                break;
            }
            case END_PART: {
                LOGGER.log(Level.FINE, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.END_PART);
                this.currentPart.doneParsing();
                ++this.currentIndex;
                break;
            }
            case END_MESSAGE: {
                LOGGER.log(Level.FINE, "MIMEEvent={0}", (Object)MIMEEvent.EVENT_TYPE.END_MESSAGE);
                this.parsed = true;
                try {
                    this.in.close();
                    break;
                }
                catch (IOException ioe) {
                    throw new MIMEParsingException(ioe);
                }
            }
            default: {
                throw new MIMEParsingException("Unknown Parser state = " + (Object)((Object)event.getEventType()));
            }
        }
        return true;
    }
}

