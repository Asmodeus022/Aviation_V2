package com.barcode.aviation.tool.inventory.exception;

public class FileExistsException extends RuntimeException{
    public FileExistsException(String message){
        super(message);
    }
}
