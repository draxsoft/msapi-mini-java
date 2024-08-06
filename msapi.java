import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("What would you like your script to be? ");
        String userInput = scanner.nextLine();

        String script = userInput; // Set the input as the script

        try (Socket socket = new Socket("127.0.0.1", 5553);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            // Prepare the length of the script
            int length = script.length() + 1; // +1 for the null terminator
            byte[] header = new byte[16];
            header[8] = (byte) (length & 0xFF);
            header[9] = (byte) ((length >> 8) & 0xFF);
            header[10] = (byte) ((length >> 16) & 0xFF);
            header[11] = (byte) ((length >> 24) & 0xFF);

            // Send header
            out.write(header);

            // Send script
            out.write(script.getBytes());
            out.write(0); // Null terminator

            System.out.println("F12 in Roblox to see script activity.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
