package com.michaelelin.standmaster;

/**
 * An exception thrown by StandMaster9000.
 */
public class StandMasterException extends RuntimeException {
    private static final long serialVersionUID = 5388139123055335421L;

    /**
     * Constructs a {@code StandMasterException} with the given message.
     *
     * @param message the message
     */
    public StandMasterException(String message) {
        super(message);
    }

}
