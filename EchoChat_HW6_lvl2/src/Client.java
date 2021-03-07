import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Client {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 5353;

    public static void main(String[] args) {

        Socket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Клиент подключился");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Thread threadReader = new Thread(() -> {
                try {
                    while (true) {
                        out.writeUTF(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        threadReader.setDaemon(true);
        threadReader.start();

        while(true) {
            String str = in.readUTF();
            if (str.equals("/end")) {
                System.out.println("Потеряно соединение с сервером");
                out.writeUTF("/end");
                break;
            } else {
                String timeStamp = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp + "Сервер: " + str);
            }
        }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
