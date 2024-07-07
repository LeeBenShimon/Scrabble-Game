package test;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The ClientHandler interface defines methods for handling client communication.
 * Implementations of this interface process client requests and manage client connections.
 */
public interface ClientHandler 
{
	/**
     * Handles communication with a client.
     * Reads input from the client and writes output to the client.
     *
     * @param inFromClient The input stream from the client.
     * @param outToClient  The output stream to the client.
     */
	void handleClient(InputStream inFromclient, OutputStream outToClient);
	
	/**
     * Closes the client handler.
     * This method can be used to perform any necessary cleanup when the client handler is no longer needed.
     */
	void close();
}
