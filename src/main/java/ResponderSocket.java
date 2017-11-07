import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponderSocket {
    // on init accepts a controller as a parameter
    // accepts a byte[] from a client
    // returns a response byte[] to the client by means of the Controller

    public Parser parser;
    public Controller controller;
    public Socket clientConnection;
    public BufferedInputStream listener;
    public BufferedOutputStream responder;
    public final int MAX_REQUEST_SIZE = 1000;


    public ResponderSocket(Parser parser, Controller controller) {
        this.parser = parser;
        this.controller = controller;
    }

    public void respondTo(Socket clientConnection) {
        this.clientConnection = clientConnection;
        try {
           toSendAResponse();
        } catch (IOException e) {
            System.out.println("IOException caught while trying to send a response to the client connection");
        }
        thenCloseTheConnection();
    }
     private void toSendAResponse() throws IOException {
         this.listener = new BufferedInputStream(clientConnection.getInputStream());
         this.responder = new BufferedOutputStream(clientConnection.getOutputStream());
         try {
             toComposeSomethingMoreThanNothing();
         } catch (IOException e) {
             System.out.println("IOException caught while trying compose something more than nothing"); }
     }

     private void toComposeSomethingMoreThanNothing() throws IOException {
        try {
            toWriteAResponse();
        } catch (NullPointerException e) {
            System.out.println("Null Pointer encountered while trying to compose something more than nothing");
        }
     }

     private void toWriteAResponse() throws NullPointerException, IOException {
        byte[] request = readRequest();
        System.out.println(new String(request));
        byte[] responsePayload = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes(); // This is the gateway to the functional core
        responder.write(responsePayload);
        responder.flush();
     }

     private byte[] readRequest() throws NullPointerException, IOException {
        byte[] request = new byte[MAX_REQUEST_SIZE];
        listener.read(request);
        return request;
     }

     private void thenCloseTheConnection() {
        try {
            clientConnection.close();
        } catch (IOException e) {
            System.out.println("IOException caught while trying to close the client connection");
        }
     }
}
