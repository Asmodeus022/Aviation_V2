package com.barcode.aviation.tool.inventory.service;

import java.io.IOException;

import com.google.zxing.WriterException;

public interface BarcodeService {
    byte[] generateBarcode(String barcodeData, int width, int height) throws WriterException, IOException;

    String decodeBarcode(byte[] barcodeImageBytes);
 
}
