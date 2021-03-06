package com.tpjad.project.photoalbumapi.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageBytesUtils {
    private final String filename;

    public ImageBytesUtils(String filename) {
        this.filename = filename;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public byte[] extractBytes() {
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(getFileFromResourceAsStream(this.filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            boolean jpg = ImageIO.write(originalImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void convertToJpg(byte[] bytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
