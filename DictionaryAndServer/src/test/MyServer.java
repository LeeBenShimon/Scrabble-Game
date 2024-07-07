package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * The MyServer class manages a server that listens on a specified port and handles client connections using a ClientHandler.
 */
public class MyServer {
    private int port; // Port number the server listens on
    private ClientHandler ch; // ClientHandler to handle client requests
    private volatile boolean stop; // Flag to stop the server

    /**
     * Constructor to create a MyServer instance with the specified port and ClientHandler.
     *
     * @param port The port number the server listens on.
     * @param ch   The ClientHandler to handle client requests.
     */
    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        this.stop = false;
    }

    /**
     * Runs the server, listening for client connections and handling them using the ClientHandler.
     *
     * @throws Exception if an error occurs while running the server.
     */
    private void runServer() throws Exception {
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(1000);
            while (!stop) {
                try {
                    Socket aClient = server.accept();
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.close();
                } catch (SocketTimeoutException e) {
                    // Ignore and continue to the next iteration
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the server in a new thread.
     */
    public void start() {
        new Thread(() -> {
            try {
                runServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Stops the server.
     */
    public void close() {
        stop = true;
    }
}

