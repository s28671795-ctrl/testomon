/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.error;

import net.engio.mbassy.bus.error.PublicationError;

public interface IPublicationErrorHandler {
    public void handleError(PublicationError var1);

    public static final class ConsoleLogger
    implements IPublicationErrorHandler {
        private final boolean printStackTrace;

        public ConsoleLogger() {
            this(false);
        }

        public ConsoleLogger(boolean printStackTrace) {
            this.printStackTrace = printStackTrace;
        }

        @Override
        public void handleError(PublicationError error) {
            System.out.println(error);
            if (this.printStackTrace && error.getCause() != null) {
                error.getCause().printStackTrace();
            }
        }
    }
}

