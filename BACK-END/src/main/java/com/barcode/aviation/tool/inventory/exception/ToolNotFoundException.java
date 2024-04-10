package com.barcode.aviation.tool.inventory.exception;

public class ToolNotFoundException extends RuntimeException {
    public ToolNotFoundException(String message){
        super(message);
    }
}
