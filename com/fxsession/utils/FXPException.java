package com.fxsession.utils;


/**
 * @author Dmitry Vulf
 * 
 * Basic exception class
 *
 */
public class FXPException extends Exception {
    private static final long serialVersionUID = 1L;
    public FXPException(Throwable t) {
        super(t);
    }

    public FXPException(String message) {
        super(message);
    }

}
