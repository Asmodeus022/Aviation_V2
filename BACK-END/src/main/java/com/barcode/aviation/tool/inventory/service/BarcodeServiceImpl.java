package com.barcode.aviation.tool.inventory.service;

import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

@Service
public class BarcodeServiceImpl implements BarcodeService {
    public byte[] generateBarcode(String barcodeData, int width, int height) throws WriterException, IOException {
        // Encode data into barcode using Code128 format
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0); // Adjust margin as needed
        BitMatrix bitMatrix = new Code128Writer().encode(barcodeData, BarcodeFormat.CODE_128, width, height, hints);

        // Convert BitMatrix to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream, new MatrixToImageConfig());

        return byteArrayOutputStream.toByteArray();
    }

    public String decodeBarcode(byte[] barcodeImageBytes) {
        try {
            // Convert byte array to BufferedImage
            ByteArrayInputStream bis = new ByteArrayInputStream(barcodeImageBytes);
            BufferedImage image = ImageIO.read(bis);

            // Convert BufferedImage to BinaryBitmap
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            // Set decoding hints if needed
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.CODE_128); // Adjust format if needed

            // Decode the barcode
            Result result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
