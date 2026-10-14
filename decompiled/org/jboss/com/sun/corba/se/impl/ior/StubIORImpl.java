/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class StubIORImpl {
    private int hashCode;
    private byte[] typeData;
    private int[] profileTags;
    private byte[][] profileData;

    public StubIORImpl() {
        this.hashCode = 0;
        this.typeData = null;
        this.profileTags = null;
        this.profileData = null;
    }

    public String getRepositoryId() {
        if (this.typeData == null) {
            return null;
        }
        return new String(this.typeData);
    }

    public StubIORImpl(Object obj) {
        OutputStream ostr = StubAdapter.getORB(obj).create_output_stream();
        ostr.write_Object(obj);
        InputStream istr = ostr.create_input_stream();
        int typeLength = istr.read_long();
        this.typeData = new byte[typeLength];
        istr.read_octet_array(this.typeData, 0, typeLength);
        int numProfiles = istr.read_long();
        this.profileTags = new int[numProfiles];
        this.profileData = new byte[numProfiles][];
        for (int i = 0; i < numProfiles; ++i) {
            this.profileTags[i] = istr.read_long();
            this.profileData[i] = new byte[istr.read_long()];
            istr.read_octet_array(this.profileData[i], 0, this.profileData[i].length);
        }
    }

    public Delegate getDelegate(ORB orb) {
        OutputStream ostr = orb.create_output_stream();
        ostr.write_long(this.typeData.length);
        ostr.write_octet_array(this.typeData, 0, this.typeData.length);
        ostr.write_long(this.profileTags.length);
        for (int i = 0; i < this.profileTags.length; ++i) {
            ostr.write_long(this.profileTags[i]);
            ostr.write_long(this.profileData[i].length);
            ostr.write_octet_array(this.profileData[i], 0, this.profileData[i].length);
        }
        InputStream istr = ostr.create_input_stream();
        Object obj = istr.read_Object();
        return StubAdapter.getDelegate(obj);
    }

    public void doRead(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        int typeLength = stream.readInt();
        this.typeData = new byte[typeLength];
        stream.readFully(this.typeData);
        int numProfiles = stream.readInt();
        this.profileTags = new int[numProfiles];
        this.profileData = new byte[numProfiles][];
        for (int i = 0; i < numProfiles; ++i) {
            this.profileTags[i] = stream.readInt();
            this.profileData[i] = new byte[stream.readInt()];
            stream.readFully(this.profileData[i]);
        }
    }

    public void doWrite(ObjectOutputStream stream) throws IOException {
        stream.writeInt(this.typeData.length);
        stream.write(this.typeData);
        stream.writeInt(this.profileTags.length);
        for (int i = 0; i < this.profileTags.length; ++i) {
            stream.writeInt(this.profileTags[i]);
            stream.writeInt(this.profileData[i].length);
            stream.write(this.profileData[i]);
        }
    }

    public synchronized int hashCode() {
        if (this.hashCode == 0) {
            int i;
            for (i = 0; i < this.typeData.length; ++i) {
                this.hashCode = this.hashCode * 37 + this.typeData[i];
            }
            for (i = 0; i < this.profileTags.length; ++i) {
                this.hashCode = this.hashCode * 37 + this.profileTags[i];
                for (int j = 0; j < this.profileData[i].length; ++j) {
                    this.hashCode = this.hashCode * 37 + this.profileData[i][j];
                }
            }
        }
        return this.hashCode;
    }

    private boolean equalArrays(int[] data1, int[] data2) {
        if (data1.length != data2.length) {
            return false;
        }
        for (int ctr = 0; ctr < data1.length; ++ctr) {
            if (data1[ctr] == data2[ctr]) continue;
            return false;
        }
        return true;
    }

    private boolean equalArrays(byte[] data1, byte[] data2) {
        if (data1.length != data2.length) {
            return false;
        }
        for (int ctr = 0; ctr < data1.length; ++ctr) {
            if (data1[ctr] == data2[ctr]) continue;
            return false;
        }
        return true;
    }

    private boolean equalArrays(byte[][] data1, byte[][] data2) {
        if (data1.length != data2.length) {
            return false;
        }
        for (int ctr = 0; ctr < data1.length; ++ctr) {
            if (this.equalArrays(data1[ctr], data2[ctr])) continue;
            return false;
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StubIORImpl)) {
            return false;
        }
        StubIORImpl other = (StubIORImpl)obj;
        if (other.hashCode() != this.hashCode()) {
            return false;
        }
        return this.equalArrays(this.typeData, other.typeData) && this.equalArrays(this.profileTags, other.profileTags) && this.equalArrays(this.profileData, other.profileData);
    }

    private void appendByteArray(StringBuffer result, byte[] data) {
        for (int ctr = 0; ctr < data.length; ++ctr) {
            result.append(Integer.toHexString(data[ctr]));
        }
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("SimpleIORImpl[");
        String repositoryId = new String(this.typeData);
        result.append(repositoryId);
        for (int ctr = 0; ctr < this.profileTags.length; ++ctr) {
            result.append(",(");
            result.append(this.profileTags[ctr]);
            result.append(")");
            this.appendByteArray(result, this.profileData[ctr]);
        }
        result.append("]");
        return result.toString();
    }
}

