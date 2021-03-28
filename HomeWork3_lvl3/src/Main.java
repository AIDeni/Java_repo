import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
  //          fileToArray();
  //          mergeFiles();
            pageByPage();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void fileToArray() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("files/forTask1.txt"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
            int x;
            while ((x = in.read()) != -1) {
                out.write(x);
            }
        byte[] b = out.toByteArray();
        System.out.println(Arrays.toString(b));
            in.close();
            out.close();
    }

    public static void mergeFiles() throws IOException {
        ArrayList<InputStream> al = new ArrayList<>();

        al.add(new FileInputStream("files/1.txt"));
        al.add(new FileInputStream("files/2.txt"));
        al.add(new FileInputStream("files/3.txt"));
        al.add(new FileInputStream("files/4.txt"));
        al.add(new FileInputStream("files/5.txt"));

        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(al)));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("files/result.txt"));
           int x;
           while ((x = in.read()) != -1) {
            out.write(x);
            System.out.print((char) x);
            }
        in.close();
        out.close();
    }

public static void pageByPage() throws IOException {
    final int PAGE_SIZE = 1800;
    long startLoading = System.currentTimeMillis();
    RandomAccessFile otp = new RandomAccessFile("files/forTask3.txt", "r");
    System.out.println("Время загрузки: " + (System.currentTimeMillis() - startLoading) + " мс");
    Scanner sc = new Scanner(System.in);
    System.out.println("Введите номер страницы: ");

    int page = sc.nextInt() - 1;
    otp.seek(page * PAGE_SIZE);
    byte[] barr = new byte[1800];
    long startReading = System.currentTimeMillis();
    otp.read(barr);
    System.out.println("Время на чтение: " + (System.currentTimeMillis() - startReading) + " мс");

    System.out.println(new String(barr));

    otp.close();
}

}
