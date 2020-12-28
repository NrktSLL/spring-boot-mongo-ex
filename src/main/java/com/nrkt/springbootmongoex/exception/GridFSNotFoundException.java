package com.nrkt.springbootmongoex.exception;

public class GridFSNotFoundException extends RuntimeException {
    public GridFSNotFoundException(String message) {
        super(message);
    }
}
