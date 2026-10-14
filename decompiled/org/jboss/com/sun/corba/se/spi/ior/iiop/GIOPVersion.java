/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class GIOPVersion {
    public static final GIOPVersion V1_0 = new GIOPVersion(1, 0);
    public static final GIOPVersion V1_1 = new GIOPVersion(1, 1);
    public static final GIOPVersion V1_2 = new GIOPVersion(1, 2);
    public static final GIOPVersion V1_3 = new GIOPVersion(1, 3);
    public static final GIOPVersion V13_XX = new GIOPVersion(13, 1);
    public static final GIOPVersion DEFAULT_VERSION = V1_2;
    public static final int VERSION_1_0 = 256;
    public static final int VERSION_1_1 = 257;
    public static final int VERSION_1_2 = 258;
    public static final int VERSION_1_3 = 259;
    public static final int VERSION_13_XX = 3329;
    private byte major = 0;
    private byte minor = 0;

    public GIOPVersion() {
    }

    public GIOPVersion(byte majorB, byte minorB) {
        this.major = majorB;
        this.minor = minorB;
    }

    public GIOPVersion(int major, int minor) {
        this.major = (byte)major;
        this.minor = (byte)minor;
    }

    public byte getMajor() {
        return this.major;
    }

    public byte getMinor() {
        return this.minor;
    }

    public boolean equals(GIOPVersion gv) {
        return gv.major == this.major && gv.minor == this.minor;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof GIOPVersion) {
            return this.equals((GIOPVersion)obj);
        }
        return false;
    }

    public int hashCode() {
        return 37 * this.major + this.minor;
    }

    public boolean lessThan(GIOPVersion gv) {
        if (this.major < gv.major) {
            return true;
        }
        return this.major == gv.major && this.minor < gv.minor;
    }

    public int intValue() {
        return this.major << 8 | this.minor;
    }

    public String toString() {
        return this.major + "." + this.minor;
    }

    public static GIOPVersion getInstance(byte major, byte minor) {
        switch (major << 8 | minor) {
            case 256: {
                return V1_0;
            }
            case 257: {
                return V1_1;
            }
            case 258: {
                return V1_2;
            }
            case 259: {
                return V1_3;
            }
            case 3329: {
                return V13_XX;
            }
        }
        return new GIOPVersion(major, minor);
    }

    public static GIOPVersion parseVersion(String s2) {
        int dotIdx = s2.indexOf(46);
        if (dotIdx < 1 || dotIdx == s2.length() - 1) {
            throw new NumberFormatException("GIOP major, minor, and decimal point required: " + s2);
        }
        int major = Integer.parseInt(s2.substring(0, dotIdx));
        int minor = Integer.parseInt(s2.substring(dotIdx + 1, s2.length()));
        return GIOPVersion.getInstance((byte)major, (byte)minor);
    }

    public static GIOPVersion chooseRequestVersion(ORB orb, IOR ior) {
        GIOPVersion orbVersion = orb.getORBData().getGIOPVersion();
        IIOPProfile prof = ior.getProfile();
        GIOPVersion profVersion = prof.getGIOPVersion();
        ORBVersion targetOrbVersion = prof.getORBVersion();
        if (!targetOrbVersion.equals(ORBVersionFactory.getFOREIGN()) && targetOrbVersion.lessThan(ORBVersionFactory.getNEWER())) {
            return V1_0;
        }
        byte prof_major = profVersion.getMajor();
        byte prof_minor = profVersion.getMinor();
        byte orb_major = orbVersion.getMajor();
        byte orb_minor = orbVersion.getMinor();
        if (orb_major < prof_major) {
            return orbVersion;
        }
        if (orb_major > prof_major) {
            return profVersion;
        }
        if (orb_minor <= prof_minor) {
            return orbVersion;
        }
        return profVersion;
    }

    public boolean supportsIORIIOPProfileComponents() {
        return this.getMinor() > 0 || this.getMajor() > 1;
    }

    public void read(InputStream istream) {
        this.major = istream.read_octet();
        this.minor = istream.read_octet();
    }

    public void write(OutputStream ostream) {
        ostream.write_octet(this.major);
        ostream.write_octet(this.minor);
    }
}

