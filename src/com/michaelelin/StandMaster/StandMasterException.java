package com.michaelelin.StandMaster;

/**
 * An exception thrown by StandMaster9000.
 */
public class StandMasterException extends RuntimeException {
    private static final long serialVersionUID = 5466999442586414193L;

    /**
     * Constructs a generic {@code StandMasterException}.
     */
    public StandMasterException() {
        super();
    }

    /**
     * Constructs a {@code StandMasterException} with the given message.
     *
     * @param message the message
     */
    public StandMasterException(String message) {
        super(message);
    }

}
