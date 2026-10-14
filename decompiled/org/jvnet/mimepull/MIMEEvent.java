/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.nio.ByteBuffer;
import org.jvnet.mimepull.InternetHeaders;

abstract class MIMEEvent {
    static final StartMessage START_MESSAGE = new StartMessage();
    static final StartPart START_PART = new StartPart();
    static final EndPart END_PART = new EndPart();
    static final EndMessage END_MESSAGE = new EndMessage();

    MIMEEvent() {
    }

    abstract EVENT_TYPE getEventType();

    static final class EndMessage
    extends MIMEEvent {
        EndMessage() {
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.END_MESSAGE;
        }
    }

    static final class Content
    extends MIMEEvent {
        private final ByteBuffer buf;

        Content(ByteBuffer buf) {
            this.buf = buf;
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.CONTENT;
        }

        ByteBuffer getData() {
            return this.buf;
        }
    }

    static final class Headers
    extends MIMEEvent {
        InternetHeaders ih;

        Headers(InternetHeaders ih) {
            this.ih = ih;
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.HEADERS;
        }

        InternetHeaders getHeaders() {
            return this.ih;
        }
    }

    static final class EndPart
    extends MIMEEvent {
        EndPart() {
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.END_PART;
        }
    }

    static final class StartPart
    extends MIMEEvent {
        StartPart() {
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.START_PART;
        }
    }

    static final class StartMessage
    extends MIMEEvent {
        StartMessage() {
        }

        @Override
        EVENT_TYPE getEventType() {
            return EVENT_TYPE.START_MESSAGE;
        }
    }

    static enum EVENT_TYPE {
        START_MESSAGE,
        START_PART,
        HEADERS,
        CONTENT,
        END_PART,
        END_MESSAGE;

    }
}

