/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.InputSource;

public final class InputLocation
implements Serializable,
Cloneable,
InputLocationTracker {
    private int lineNumber = -1;
    private int columnNumber = -1;
    private InputSource source;
    private Map<Object, InputLocation> locations;
    private InputLocation location;

    public InputLocation(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public InputLocation(int lineNumber, int columnNumber, InputSource source2) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.source = source2;
    }

    public InputLocation clone() {
        try {
            InputLocation copy = (InputLocation)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public Map<Object, InputLocation> getLocations() {
        return this.locations;
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
            }
            this.setOtherLocation(key, location);
            return;
        }
        this.setOtherLocation(key, location);
    }

    public void setOtherLocation(Object key, InputLocation location) {
        if (location != null) {
            if (this.locations == null) {
                this.locations = new LinkedHashMap<Object, InputLocation>();
            }
            this.locations.put(key, location);
        }
    }

    private InputLocation getOtherLocation(Object key) {
        return this.locations != null ? this.locations.get(key) : null;
    }

    public InputSource getSource() {
        return this.source;
    }

    public static InputLocation merge(InputLocation target, InputLocation source2, boolean sourceDominant) {
        Map<Object, InputLocation> locations;
        if (source2 == null) {
            return target;
        }
        if (target == null) {
            return source2;
        }
        InputLocation result = new InputLocation(target.getLineNumber(), target.getColumnNumber(), target.getSource());
        Map<Object, InputLocation> sourceLocations = source2.getLocations();
        Map<Object, InputLocation> targetLocations = target.getLocations();
        if (sourceLocations == null) {
            locations = targetLocations;
        } else if (targetLocations == null) {
            locations = sourceLocations;
        } else {
            locations = new LinkedHashMap<Object, InputLocation>();
            locations.putAll(sourceDominant ? targetLocations : sourceLocations);
            locations.putAll(sourceDominant ? sourceLocations : targetLocations);
        }
        result.setLocations(locations);
        return result;
    }

    public static InputLocation merge(InputLocation target, InputLocation source2, Collection<Integer> indices) {
        Map<Object, InputLocation> locations;
        if (source2 == null) {
            return target;
        }
        if (target == null) {
            return source2;
        }
        InputLocation result = new InputLocation(target.getLineNumber(), target.getColumnNumber(), target.getSource());
        Map<Object, InputLocation> sourceLocations = source2.getLocations();
        Map<Object, InputLocation> targetLocations = target.getLocations();
        if (sourceLocations == null) {
            locations = targetLocations;
        } else if (targetLocations == null) {
            locations = sourceLocations;
        } else {
            locations = new LinkedHashMap<Object, InputLocation>();
            for (Integer index : indices) {
                InputLocation location = index < 0 ? sourceLocations.get(~index.intValue()) : targetLocations.get(index);
                locations.put(locations.size(), location);
            }
        }
        result.setLocations(locations);
        return result;
    }

    public void setLocations(Map<Object, InputLocation> locations) {
        this.locations = locations;
    }

    public String toString() {
        return this.getLineNumber() + " : " + this.getColumnNumber() + ", " + this.getSource();
    }

    public static abstract class StringFormatter {
        public abstract String toString(InputLocation var1);
    }
}

