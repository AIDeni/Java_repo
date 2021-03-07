import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

    Socket clientSocket = null;
    Scanner scanner = new Scanner(System.in);
    try (ServerSocket serverSocket = new ServerSocket(5353))  {
        System.out.println("Сервер запущен");
        clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился");
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    out.writeUTF(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        while(true) {
            String str = in.readUTF();
            if (str.equals("/end")) {
                System.out.println("Клиент отключился");
                out.writeUTF("/end");
                break;
            } else {
                String timeStamp = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp + "Клиент: " + str);
            }
        }
    } catch (IOException exception) {
        exception.printStackTrace();
    } finally {
        try {
            clientSocket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
