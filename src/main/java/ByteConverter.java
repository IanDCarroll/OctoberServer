public class ByteConverter {
    // accepts a string as a param; returns a byte array
    // accepts a byte array as a param; returns a string
    // implemented as a decorator between the controller and the ClientSocket
    public ByteConverter() {
        System.out.println("byte converter initialized");
    }

    public boolean exists() { return true; }
}
