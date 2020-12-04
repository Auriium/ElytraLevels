package com.elytraforce.elytracore.exception;

public class NegativeException extends Exception{
    public NegativeException(int num) {
        super("Error: " + num + " cannot be negative in a PlayerController environment!");
    }
}
