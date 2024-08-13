package com.pdp.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aliabbos Ashurov
 * @since 13/August/2024  10:27
 **/
public class QRCodeGenerator {

    public static void generateQRCode(String text, String filePath, int width, int height) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        System.out.println("QR Code generated at: " + filePath);
    }

    public static void main(String[] args) {
        try {
            String text = "localhost:8080/qrcode";
            String filePath = "qrcode.png";
            int width = 300;
            int height = 300;

            generateQRCode(text, filePath, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
