package test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The BookScrabbleHandler class implements the ClientHandler interface
 * to handle client requests for the BookScrabble game.
 */
public class BookScrabbleHandler implements ClientHandler{
    private final DictionaryManager dictionaryManager; // Manager for dictionary operations

    /**
     * Constructor to initialize the BookScrabbleHandler.
     */
    public BookScrabbleHandler() 
    {
        this.dictionaryManager = DictionaryManager.get(); // Get the singleton instance of DictionaryManager
    }

    /**
     * Handles client requests by reading from the input stream and writing to the output stream.
     *
     * @param inFromClient The input stream from the client.
     * @param outToClient  The output stream to the client.
     */
    @SuppressWarnings("resource")
    public void handleClient(InputStream inFromclient, OutputStream outToClient)
    {
        Scanner myScanner = new Scanner(new BufferedInputStream(inFromclient)); // Scanner for reading client input
        PrintWriter out = new PrintWriter(outToClient); // Writer for sending output to the client
        if (myScanner.hasNextLine()) // Check if there is input from the client
        {
            String input = myScanner.nextLine();
            String[] booksNames = input.split(","); // Split the input into an array of strings

            int length = booksNames.length;
            if (length<3) // Check if the input is valid
            {
                out.flush();
                return;
            }
            String action=booksNames[0];
            boolean result;
            if("Q".equals(action)) // If action is query
            {
                result = dictionaryManager.query(Arrays.copyOfRange(booksNames,1,length)); // Perform query
            }
            else if ("C".equals(action)) // If action is challenge
            {
                result = dictionaryManager.challenge(Arrays.copyOfRange(booksNames,1,length)); // Perform challenge
            }
            else // If action is invalid
            {
                out.flush();
                return;
            }
            out.println(result); // Send the result to the client
            out.flush();
        }
        
        myScanner.close();
        out.close();

    }

    public void close(){}
}
