import java.net.*;
import java.io.*;
import java.util.Scanner;

public class WizardClient {
    private static final int SERVER_PORT = 8010;

    public static void main(String[] args) throws IOException {
        // Передаем null в getByName(), получая
        // специальный IP адрес "локальной заглушки"
        // для тестирования на машине без сети:
        InetAddress addr = InetAddress.getByName(null);
        // Альтернативно, вы можете использовать
        // адрес или имя:
        // InetAddress addr =
        // InetAddress.getByName("127.0.0.1");
        // InetAddress addr =
        // InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, SERVER_PORT);

        // Помещаем все в блок try-finally, чтобы
        // быть уверенным, что сокет закроется:
        try {
            System.out.println("socket = " + socket);
            Scanner scanner = new Scanner(System.in);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));

            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);

            String msg;
            while (true) {

                msg = in.readLine();

                while (!"ENDOFMESSAGE".equals(msg)) {

                        System.out.println(msg);
                        msg = in.readLine();
                }

                String command = scanner.next();

                out.println(command);

            }

/*
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);

            for (int i = 0; i < 100000; i++) {
                out.println("test " + i);
                String str = in.readLine();
                System.out.println(str);
            }
			*/

            //out.println("END");
        } finally {
            System.out.println("closing...");
            socket.close();
        }
    }
}
