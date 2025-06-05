package com.practice.BookingService.exception;


public class FileProcessingException extends Throwable {
    public FileProcessingException(String msg, Throwable cause) { super(msg, cause); }
    public FileProcessingException(String msg){ super(msg);}

}
