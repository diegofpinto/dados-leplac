import javax.swing.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
//        App app = new App();
//        app.setVisible(true);
//        app.setLocationRelativeTo(null);

        FileSearch fileSearch = new FileSearch();
        fileSearch.searchDirectory(new File("\\\\10.5.193.218\\fitoteca\\DADOS_LEPLAC\\LEPLAC-XVI-FASE2\\GRAVIMETRIA_FASE2"), "B20_A");
        System.out.println(fileSearch.getResult());
    }

}
