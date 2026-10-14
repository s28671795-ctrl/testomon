/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.utils;

import org.onvif.ver10.schema.PTZPreset;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.PTZVector;

public class OnvifUtils {
    public static String format(PTZVector vector) {
        String out = "";
        if (vector != null) {
            out = out + "[" + vector.getPanTilt().getX() + "," + vector.getPanTilt().getY();
            if (vector.getZoom() != null) {
                out = out + "," + vector.getZoom().getX();
            }
            out = out + "]";
        }
        return out;
    }

    public static String format(PTZPreset preset) {
        String out = "";
        if (preset != null) {
            out = out + preset.getToken() + "/" + preset.getName() + ":" + OnvifUtils.format(preset.getPTZPosition());
        }
        return out;
    }

    public static String format(PTZStatus status) {
        String out = "";
        if (status != null) {
            out = out + "moveStatus=" + OnvifUtils.format(status.getMoveStatus()) + " position=" + OnvifUtils.format(status.getPosition()) + " time=" + status.getUtcTime();
        }
        return out;
    }

    public static String format(Object o) {
        String out = "";
        if (o != null) {
            int ch;
            out = o.toString();
            while ((ch = out.indexOf("org.onvif.ver")) != -1) {
                int end = out.indexOf("[", ch);
                if (end == -1) {
                    assert (false);
                    break;
                }
                int at = out.indexOf("@", ch);
                if (at == -1 || at > end) {
                    assert (false);
                    break;
                }
                out = out.substring(0, ch) + out.substring(end);
            }
            out = out.replaceAll("<null>", "");
        }
        return out;
    }
}

