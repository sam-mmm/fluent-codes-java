package io.github.sammmm.fluentcodes;

/**
 * Will throw if min length is larger than max length
 *
 * @author sam-mmm
 */
public class LengthMismatchException extends Exception {
    public LengthMismatchException(String message) {
        super(message);
    }
}
