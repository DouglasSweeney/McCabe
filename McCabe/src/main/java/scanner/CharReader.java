//
// File:    CharReader.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney modifications to a file found online.
//
package main.java.scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * A buffered character reader. Abstracts out differences between platforms,
 * mapping all new lineNumbers to '\n'. Also, keeps track of lineNumber numbers where the
 * first lineNumber is numbered 1.
 */

public class CharReader {

    /** A representation of the end of file as a character. */
    public final static char EOFCH = (char) -1;

    /** The underlying reader records lineNumber numbers. */
    private LineNumberReader lineNumberReader;

    /** Name of the file that is being read. */
    private String fileName;

    /**
     * Construct a CharReader from a file name.
     * 
     * @param fileName
     *            the name of the input file.
     * @exception FileNotFoundException
     *                if the file is not found.
     */

    public CharReader(String fileName) throws FileNotFoundException {
        lineNumberReader = new LineNumberReader(new FileReader(fileName));
        this.fileName = fileName;
    }

    /**
     * Scan the next character.
     * 
     * @return the character scanned.
     * @exception IOException
     *                if an I/O error occurs.
     */

    public char nextChar() throws IOException {
        return (char) lineNumberReader.read();
    }

    /**
     * The current lineNumber number in the source file, starting at 1.
     * 
     * @return the current lineNumber number.
     */

    public int lineNumber() {
        // lineNumberNumberReader counts lineNumbers from 0.
        return lineNumberReader.getLineNumber() + 1;
    }

    /**
     * Return the file name.
     * 
     * @return the file name.
     */

    public String fileName() {
        return fileName;
    }

    /**
     * Close the file.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */

    public void close() throws IOException {
        lineNumberReader.close();
    }
}
