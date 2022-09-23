import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config extends JFrame {

    private JPanel configPanel;
    private JTextField delimitadorTextField;
    private JTextField fase1BatimetriaTextField;
    private JTextField fase1GravimetriaTextField;
    private JTextField fase1MagnetometriaTextField;
    private JTextField fase1SegyTextField;
    private JTextField fase2BatimetriaTextField;
    private JTextField fase2GravimetriaTextField;
    private JTextField fase2MagnetometriaTextField;
    private JTextField fase2MulticanalTextField;
    private JTextField fase2AirGunTextField;
    private JTextField a35KHzTextField2;
    private JButton saveButton;
    //    private InputStream input = null;
    private static Properties prop = new Properties();
    private static final String configFilePath = "config.properties";

    public enum Props {
        DELIMITER,
        BATIMETRIA_FASE_1,
        GRAVIMETRIA_FASE_1,
        MAGNETOMETRIA_FASE_1,
        SEGY_FASE_1,
        BATIMETRIA_FASE_2,
        GRAVIMETRIA_FASE_2,
        MAGNETOMETRIA_FASE_2,
        SISMICA_FASE_2_MULTICANAL,
        SISMICA_FASE_2_AIR_GUN,
        _35KHZ_FASE_2,
    }

    public static String getPropValue(Props propName) {
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(Config.configFilePath)) {
            prop = new Properties();
            prop.load(input);

        } catch (IOException io) {
            io.printStackTrace();
        }
        return prop.getProperty(propName.name());
    }


    public Config() {
        add(configPanel);
        setSize(800, 600);
        setVisible(true);


        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(configFilePath)) {
//            input = ;
            prop.load(input);
            delimitadorTextField.setText(prop.getProperty(Props.DELIMITER.name()));
            fase1BatimetriaTextField.setText(prop.getProperty(Props.BATIMETRIA_FASE_1.name()));
            fase1GravimetriaTextField.setText(prop.getProperty(Props.GRAVIMETRIA_FASE_1.name()));
            fase1MagnetometriaTextField.setText(prop.getProperty(Props.MAGNETOMETRIA_FASE_1.name()));
            fase1SegyTextField.setText(prop.getProperty(Props.SEGY_FASE_1.name()));
            fase2BatimetriaTextField.setText(prop.getProperty(Props.BATIMETRIA_FASE_2.name()));
            fase2GravimetriaTextField.setText(prop.getProperty(Props.GRAVIMETRIA_FASE_2.name()));
            fase2MagnetometriaTextField.setText(prop.getProperty(Props.MAGNETOMETRIA_FASE_2.name()));
            fase2MulticanalTextField.setText(prop.getProperty(Props.SISMICA_FASE_2_MULTICANAL.name()));
            fase2AirGunTextField.setText(prop.getProperty(Props.SISMICA_FASE_2_AIR_GUN.name()));
            a35KHzTextField2.setText(prop.getProperty(Props._35KHZ_FASE_2.name()));

        } catch (IOException io) {
            io.printStackTrace();
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertiesCache cache = PropertiesCache.getInstance();
                cache.setProperty("country", "INDIA");
                try {
                    PropertiesCache.getInstance().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }
}
