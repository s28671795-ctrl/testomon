/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Iterator;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.apache.cxf.helpers.IOUtils;

public class ImageDataContentHandler
implements DataContentHandler {
    private static final DataFlavor[] FLAVORS;

    @Override
    public Object getContent(DataSource ds) throws IOException {
        return ImageIO.read(ds.getInputStream());
    }

    @Override
    public Object getTransferData(DataFlavor df, DataSource ds) throws UnsupportedFlavorException, IOException {
        for (DataFlavor f : FLAVORS) {
            if (!f.equals(df)) continue;
            return this.getContent(ds);
        }
        return null;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return FLAVORS;
    }

    @Override
    public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
        if (obj instanceof Image) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType(mimeType);
            if (writers.hasNext()) {
                ImageWriter writer = writers.next();
                BufferedImage bimg = ImageDataContentHandler.convertToBufferedImage((Image)obj);
                ImageOutputStream out = ImageIO.createImageOutputStream(os);
                writer.setOutput(out);
                writer.write(bimg);
                writer.dispose();
                out.flush();
                out.close();
                return;
            }
        } else if (obj instanceof byte[]) {
            os.write((byte[])obj);
        } else if (obj instanceof InputStream) {
            IOUtils.copyAndCloseInput((InputStream)obj, os);
        } else if (obj instanceof File) {
            InputStream file = Files.newInputStream(((File)obj).toPath(), new OpenOption[0]);
            IOUtils.copyAndCloseInput(file, os);
        } else {
            throw new IOException("Attachment type not spported " + obj.getClass());
        }
    }

    private static BufferedImage convertToBufferedImage(Image image) throws IOException {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        MediaTracker tracker = new MediaTracker(new Component(){
            private static final long serialVersionUID = 977142547536262901L;
        });
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        }
        catch (InterruptedException e) {
            throw new IOException(e.getMessage(), e);
        }
        BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        Graphics2D g2 = bufImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufImage;
    }

    static {
        String[] types = ImageIO.getReaderMIMETypes();
        FLAVORS = new DataFlavor[types.length];
        int i = 0;
        for (String type : types) {
            ImageDataContentHandler.FLAVORS[i++] = new ActivationDataFlavor(Image.class, type, "Image");
        }
    }
}

