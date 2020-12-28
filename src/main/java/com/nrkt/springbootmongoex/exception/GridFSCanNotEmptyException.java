package com.nrkt.springbootmongoex.exception;

public class GridFSCanNotEmptyException extends RuntimeException {
    public GridFSCanNotEmptyException(String message) {
        super(message);
    }
}
