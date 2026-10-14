/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.ParseHandler;
import org.ldaptive.asn1.UniversalDERTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DERParser {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<DERPath, ParseHandler> handlerMap = new HashMap<DERPath, ParseHandler>();
    private final Queue<DERPath> permutations = new ArrayDeque<DERPath>();

    public void registerHandler(DERPath path, ParseHandler handler) {
        this.handlerMap.put(path, handler);
    }

    public void parse(DERBuffer encoded) {
        this.parseTags(encoded);
    }

    public DERTag readTag(DERBuffer encoded) {
        DERTag tag;
        if (encoded.position() >= encoded.limit()) {
            return null;
        }
        byte b = encoded.get();
        int tagNo = b & 0x1F;
        boolean constructed = (b & 0x20) == 32;
        switch (b & 0xC0) {
            case 0: {
                tag = UniversalDERTag.fromTagNo(tagNo);
                break;
            }
            case 64: {
                tag = new ApplicationDERTag(tagNo, constructed);
                break;
            }
            case 128: {
                tag = new ContextDERTag(tagNo, constructed);
                break;
            }
            default: {
                throw new IllegalArgumentException("Private classes not supported.");
            }
        }
        return tag;
    }

    public int readLength(DERBuffer encoded) {
        int length = 0;
        int b = encoded.get();
        if ((b & 0x80) == 128) {
            int len = b & 0x7F;
            if (len > 0) {
                int limit = encoded.limit();
                encoded.limit(encoded.position() + len);
                length = IntegerType.decodeUnsignedPrimitive(encoded);
                encoded.limit(limit);
            }
        } else {
            length = b;
        }
        return length;
    }

    private void parseTags(DERBuffer encoded) {
        int index = 0;
        while (encoded.position() < encoded.limit() && !this.handlerMap.isEmpty()) {
            DERTag tag = this.readTag(encoded);
            if (tag == null) continue;
            this.addTag(tag, index++);
            this.parseTag(tag, encoded);
            this.removeTag();
        }
    }

    private void parseTag(DERTag tag, DERBuffer encoded) {
        int limit = encoded.limit();
        int end = this.readLength(encoded) + encoded.position();
        int start2 = encoded.position();
        for (DERPath p : this.permutations) {
            ParseHandler handler = this.handlerMap.get(p);
            if (handler == null) continue;
            encoded.limit(end).position(start2);
            handler.handle(this, encoded);
        }
        if (tag.isConstructed()) {
            this.parseTags(encoded);
        }
        encoded.limit(limit).position(end);
    }

    private void addTag(DERTag tag, int index) {
        if (this.permutations.isEmpty()) {
            this.permutations.add(new DERPath().pushNode(tag.name()));
            this.permutations.add(new DERPath().pushNode(tag.name(), index));
        } else {
            ArrayDeque<DERPath> generation = new ArrayDeque<DERPath>(this.permutations.size());
            for (DERPath p : this.permutations) {
                generation.add(new DERPath(p).pushNode(tag.name()));
                p.pushNode(tag.name(), index);
            }
            this.permutations.addAll(generation);
        }
    }

    private void removeTag() {
        int half = this.permutations.size() / 2;
        while (this.permutations.size() > half) {
            this.permutations.remove();
        }
        this.permutations.forEach(DERPath::popNode);
    }
}

