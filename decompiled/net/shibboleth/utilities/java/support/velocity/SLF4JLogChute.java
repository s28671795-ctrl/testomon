/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.velocity.runtime.RuntimeServices
 *  org.apache.velocity.runtime.log.LogChute
 */
package net.shibboleth.utilities.java.support.velocity;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogChute
implements LogChute {
    public static final String LOGCHUTE_SLF4J_NAME = "runtime.log.logsystem.slf4j.name";
    public static final String DEFAULT_LOG_NAME = "org.apache.velocity";
    private Logger log;

    public void init(RuntimeServices rs) throws Exception {
        String name = (String)rs.getProperty(LOGCHUTE_SLF4J_NAME);
        if (name == null) {
            name = DEFAULT_LOG_NAME;
        }
        this.log = LoggerFactory.getLogger(name);
        this.log(0, "SLF4JLogChute name is '" + name + "'");
    }

    public boolean isLevelEnabled(int level) {
        switch (level) {
            case 0: {
                return this.log.isDebugEnabled();
            }
            case 1: {
                return this.log.isInfoEnabled();
            }
            case -1: {
                return this.log.isTraceEnabled();
            }
            case 2: {
                return this.log.isWarnEnabled();
            }
            case 3: {
                return this.log.isErrorEnabled();
            }
        }
        return true;
    }

    public void log(int level, String message) {
        switch (level) {
            case 3: {
                this.log.error(message);
                break;
            }
            case 2: {
                this.log.warn(message);
                break;
            }
            case 1: {
                this.log.info(message);
                break;
            }
            case -1: {
                this.log.trace(message);
                break;
            }
            default: {
                this.log.debug(message);
            }
        }
    }

    public void log(int level, String message, Throwable t2) {
        switch (level) {
            case 3: {
                this.log.error(message, t2);
                break;
            }
            case 2: {
                this.log.warn(message, t2);
                break;
            }
            case 1: {
                this.log.info(message, t2);
                break;
            }
            case -1: {
                this.log.trace(message, t2);
                break;
            }
            default: {
                this.log.debug(message, t2);
            }
        }
    }
}

