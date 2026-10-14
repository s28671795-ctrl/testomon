/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.rtree;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.rtree.DefaultSpatial;
import org.h2.mvstore.rtree.Spatial;
import org.h2.mvstore.type.BasicDataType;

public class SpatialDataType
extends BasicDataType<Spatial> {
    private final int dimensions;

    public SpatialDataType(int n) {
        DataUtils.checkArgument(n >= 1 && n < 32, "Dimensions must be between 1 and 31, is {0}", n);
        this.dimensions = n;
    }

    protected Spatial create(long l, float ... fArray) {
        return new DefaultSpatial(l, fArray);
    }

    public Spatial[] createStorage(int n) {
        return new Spatial[n];
    }

    @Override
    public int compare(Spatial spatial, Spatial spatial2) {
        if (spatial == spatial2) {
            return 0;
        }
        if (spatial == null) {
            return -1;
        }
        if (spatial2 == null) {
            return 1;
        }
        long l = spatial.getId();
        long l2 = spatial2.getId();
        return Long.compare(l, l2);
    }

    public boolean equals(Spatial spatial, Spatial spatial2) {
        if (spatial == spatial2) {
            return true;
        }
        if (spatial == null || spatial2 == null) {
            return false;
        }
        return spatial.getId() == spatial2.getId();
    }

    @Override
    public int getMemory(Spatial spatial) {
        return 40 + this.dimensions * 4;
    }

    @Override
    public void write(WriteBuffer writeBuffer, Spatial spatial) {
        int n;
        if (spatial.isNull()) {
            writeBuffer.putVarInt(-1);
            writeBuffer.putVarLong(spatial.getId());
            return;
        }
        int n2 = 0;
        for (n = 0; n < this.dimensions; ++n) {
            if (spatial.min(n) != spatial.max(n)) continue;
            n2 |= 1 << n;
        }
        writeBuffer.putVarInt(n2);
        for (n = 0; n < this.dimensions; ++n) {
            writeBuffer.putFloat(spatial.min(n));
            if ((n2 & 1 << n) != 0) continue;
            writeBuffer.putFloat(spatial.max(n));
        }
        writeBuffer.putVarLong(spatial.getId());
    }

    @Override
    public Spatial read(ByteBuffer byteBuffer) {
        int n = DataUtils.readVarInt(byteBuffer);
        if (n == -1) {
            long l = DataUtils.readVarLong(byteBuffer);
            return this.create(l, new float[0]);
        }
        float[] fArray = new float[this.dimensions * 2];
        for (int i = 0; i < this.dimensions; ++i) {
            float f = byteBuffer.getFloat();
            float f2 = (n & 1 << i) != 0 ? f : byteBuffer.getFloat();
            fArray[i + i] = f;
            fArray[i + i + 1] = f2;
        }
        long l = DataUtils.readVarLong(byteBuffer);
        return this.create(l, fArray);
    }

    public boolean isOverlap(Spatial spatial, Spatial spatial2) {
        if (spatial.isNull() || spatial2.isNull()) {
            return false;
        }
        for (int i = 0; i < this.dimensions; ++i) {
            if (!(spatial.max(i) < spatial2.min(i)) && !(spatial.min(i) > spatial2.max(i))) continue;
            return false;
        }
        return true;
    }

    public void increaseBounds(Spatial spatial, Spatial spatial2) {
        if (spatial2.isNull() || spatial.isNull()) {
            return;
        }
        for (int i = 0; i < this.dimensions; ++i) {
            float f = spatial2.min(i);
            if (f < spatial.min(i)) {
                spatial.setMin(i, f);
            }
            if (!((f = spatial2.max(i)) > spatial.max(i))) continue;
            spatial.setMax(i, f);
        }
    }

    public float getAreaIncrease(Spatial spatial, Spatial spatial2) {
        if (spatial.isNull() || spatial2.isNull()) {
            return 0.0f;
        }
        float f = spatial.min(0);
        float f2 = spatial.max(0);
        float f3 = f2 - f;
        f = Math.min(f, spatial2.min(0));
        f2 = Math.max(f2, spatial2.max(0));
        float f4 = f2 - f;
        for (int i = 1; i < this.dimensions; ++i) {
            f = spatial.min(i);
            f2 = spatial.max(i);
            f3 *= f2 - f;
            f = Math.min(f, spatial2.min(i));
            f2 = Math.max(f2, spatial2.max(i));
            f4 *= f2 - f;
        }
        return f4 - f3;
    }

    float getCombinedArea(Spatial spatial, Spatial spatial2) {
        if (spatial.isNull()) {
            return this.getArea(spatial2);
        }
        if (spatial2.isNull()) {
            return this.getArea(spatial);
        }
        float f = 1.0f;
        for (int i = 0; i < this.dimensions; ++i) {
            float f2 = Math.min(spatial.min(i), spatial2.min(i));
            float f3 = Math.max(spatial.max(i), spatial2.max(i));
            f *= f3 - f2;
        }
        return f;
    }

    private float getArea(Spatial spatial) {
        if (spatial.isNull()) {
            return 0.0f;
        }
        float f = 1.0f;
        for (int i = 0; i < this.dimensions; ++i) {
            f *= spatial.max(i) - spatial.min(i);
        }
        return f;
    }

    public boolean contains(Spatial spatial, Spatial spatial2) {
        if (spatial.isNull() || spatial2.isNull()) {
            return false;
        }
        for (int i = 0; i < this.dimensions; ++i) {
            if (!(spatial.min(i) > spatial2.min(i)) && !(spatial.max(i) < spatial2.max(i))) continue;
            return false;
        }
        return true;
    }

    public boolean isInside(Spatial spatial, Spatial spatial2) {
        if (spatial.isNull() || spatial2.isNull()) {
            return false;
        }
        for (int i = 0; i < this.dimensions; ++i) {
            if (!(spatial.min(i) <= spatial2.min(i)) && !(spatial.max(i) >= spatial2.max(i))) continue;
            return false;
        }
        return true;
    }

    Spatial createBoundingBox(Spatial spatial) {
        if (spatial.isNull()) {
            return spatial;
        }
        return spatial.clone(0L);
    }

    public int[] getExtremes(ArrayList<Spatial> arrayList) {
        float f;
        if ((arrayList = SpatialDataType.getNotNull(arrayList)).isEmpty()) {
            return null;
        }
        Spatial spatial = this.createBoundingBox(arrayList.get(0));
        Spatial spatial2 = this.createBoundingBox(spatial);
        for (int i = 0; i < this.dimensions; ++i) {
            float f2 = spatial2.min(i);
            spatial2.setMin(i, spatial2.max(i));
            spatial2.setMax(i, f2);
        }
        for (Spatial spatial3 : arrayList) {
            this.increaseBounds(spatial, spatial3);
            this.increaseMaxInnerBounds(spatial2, spatial3);
        }
        double d = 0.0;
        int n = 0;
        for (int i = 0; i < this.dimensions; ++i) {
            float f3;
            float f4;
            f = spatial2.max(i) - spatial2.min(i);
            if (f < 0.0f || !((double)(f4 = f / (f3 = spatial.max(i) - spatial.min(i))) > d)) continue;
            d = f4;
            n = i;
        }
        if (d <= 0.0) {
            return null;
        }
        float f5 = spatial2.min(n);
        f = spatial2.max(n);
        int n2 = -1;
        int n3 = -1;
        for (int i = 0; i < arrayList.size() && (n2 < 0 || n3 < 0); ++i) {
            Spatial spatial4 = arrayList.get(i);
            if (n2 < 0 && spatial4.max(n) == f5) {
                n2 = i;
                continue;
            }
            if (n3 >= 0 || spatial4.min(n) != f) continue;
            n3 = i;
        }
        return new int[]{n2, n3};
    }

    private static ArrayList<Spatial> getNotNull(ArrayList<Spatial> arrayList) {
        boolean bl = false;
        for (Spatial object : arrayList) {
            if (!object.isNull()) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        for (Spatial spatial : arrayList) {
            if (spatial.isNull()) continue;
            arrayList2.add(spatial);
        }
        return arrayList2;
    }

    private void increaseMaxInnerBounds(Spatial spatial, Spatial spatial2) {
        for (int i = 0; i < this.dimensions; ++i) {
            spatial.setMin(i, Math.min(spatial.min(i), spatial2.max(i)));
            spatial.setMax(i, Math.max(spatial.max(i), spatial2.min(i)));
        }
    }
}

