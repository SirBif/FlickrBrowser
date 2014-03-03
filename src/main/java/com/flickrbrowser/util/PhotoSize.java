package com.flickrbrowser.util;

/**
Image sizes supported by the Flickr API
 */
public enum PhotoSize {
    SMALL_SQUARE("s"),
    LARGE_SQUARE("q"),
    THUMBNAIL("t"),
    SMALL_240("m"),
    SMALL_320("n"),
    MEDIUM_640("z"),
    MEDIUM_800("c")
    ;

    private PhotoSize(final String text) {
        this.text = text;
    }
    private final String text;

    @Override
    public String toString() {
        return text;
    }
}