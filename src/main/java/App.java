//import org.graalvm.compiler.phases.common.NodeCounterPhase;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class App extends JFrame {
    private JTextField sourceTextField;

    private File src, dest;
    private JTextField targetTextField;
    private JButton okButton;
    private JButton cleanButton;
    private JPanel mainPanel;

    private int qtFiles;
    private JTextArea fileTextArea;
    private JProgressBar progressBar;
    private JRadioButton fase1RadioButton;
    private JRadioButton fase2RadioButton;
    private JRadioButton batimetriaRadioButton;
    private JRadioButton sismicaRadioButton;
    private JRadioButton magnetometriaRadioButton;
    private JRadioButton gravimetriaRadioButton;
    private JRadioButton miniAirGunRadioButton;
    private JRadioButton a35KHzRadioButton;
    private JButton fileButton;
    private JRadioButton[] jRadioButtons;

    public JTextArea getFileTextArea() {
        return fileTextArea;
    }
    public App() {
        jRadioButtons = new JRadioButton[]{batimetriaRadioButton, sismicaRadioButton, magnetometriaRadioButton,
                gravimetriaRadioButton, miniAirGunRadioButton, a35KHzRadioButton};
        JMenuItem config = new JMenuItem("Configurações");
        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config configFrame = new Config();
                configFrame.setLocationRelativeTo(null);
                configFrame.setVisible(true);
            }
        });
        JMenuItem exit = new JMenuItem("Fechar");
        JMenu programMenu = new JMenu("Programa");

        JMenuBar menuBar = new JMenuBar();

        setTitle("Ficha de Controle para Cessão de Dados do LEPAC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        add(mainPanel);

        setSize(800, 600);
        setVisible(true);

        programMenu.add(config);
        programMenu.add(exit);
        menuBar.add(programMenu);


        progressBar.setValue(0);
//        sourceTextField.setText("\\\\10.5.193.218\\fitoteca\\DADOS_LEPLAC\\LEPLAC-FASE1\\BATIMETRIA_FASE I\\LP09-BAT\\");
//        targetTextField.setText("C:\\Users\\86768646\\Desktop\\Sistemas\\teste\\");
//        fileTextArea.setText("5000688.xyz,5000688A.xyz,5000691A.xyz,5000691B.xyz,5000692.xyz,5000692C.xyz,5000693.xyz,5000694.xyz,5000695.xyz,5000696.xyz,50006967.xyz,5000698.xyz,5000699.xyz,5000700.xyz,5000700C.xyz,5000701.xyz,5000701A.xyz,5000701B.xyz,5000702A.xyz,5000702B.xyz,5000703.xyz,5000704.xyz,5000705.xyz,5000705A.xyz,5000706.xyz,5000707.xyz,5000708.xyz,5000709.xyz,5000710.xyz,5000710A.xz,5000711.xyz,5000712.xyz,5000713.xyz,5000713B.xyz,5000713dup.xyz,5000714.xyz,5000715.xyz,5000716.xyz,5000717.xyz,5000718.xyz,5000719.xyz,5000720.xyz,5000721.xyz,5000722.xyz,5000722B.xyz,5000722C.xyz,5000723.xyz,5000724.xyz,5000725.xyz,5000726.xyz,5000727.xyz,5000728.xyz,5000728A.xyz,5000729.xyz,5000730.xyz,5000731.xyz,5000732.xyz,5000733.xyz,5000734.xyz,5000735.xyz,500736.xyz,5000737.xyz,5000738.xyz,5000739.xyz,5000740.xyz,5000741.xyz,5000742.xyz,5000743.xyz,5000744.xyz,5000745.xyz,5000746.xyz,5000747.xyz,5000748.xyz,5000749.xyz,5000750.xyz,5000751xyz,5000752.xyz,5000753.xyz,5000753A.xyz,5000754.xyz,5000755.xyz");
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanInputFields();
                progressBar.setValue(0);
                sourceTextField.setEnabled(true);
                targetTextField.setEnabled(true);
                fileTextArea.setEnabled(true);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceTextField.setEnabled(false);
                targetTextField.setEnabled(false);
                fileTextArea.setEnabled(false);
                copyFile(sourceTextField.getText(),
                        targetTextField.getText(),
                        fileTextArea.getText());


                src = new File(sourceTextField.getText());
                dest = new File(targetTextField.getText());

//                System.out.println(fileTextArea.getText());
                System.out.println("SRC: " + src.getPath());
                System.out.println("FILE ?" + src.isFile());

                //Qt of files
                qtFiles = src.listFiles().length;

                // If fileText == "*" => Copy all ??
                // If fileText != "*" => Copy recursively a folder or just what want (passed)??
//                try {
//                    copyFolder(src.toPath(), dest.toPath(), fileTextArea.getText());
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }

                sourceTextField.setEnabled(true);
                targetTextField.setEnabled(true);
                fileTextArea.setEnabled(true);
                progressBar.setValue(0);
//                JOptionPane.showMessageDialog(null, "Cópia Finalizada");
            }
        });

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooserDemo fileChooser = new JFileChooserDemo(targetTextField);
                targetTextField.setText(fileChooser.getPath());
                //  fileChooser.setDialogTitle("Selecione um diretório de destino");


            }
        });
        fase1RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fase1RadioButton.isSelected()) {
                    sismicaRadioButton.setText("Sísmica");
                    fase2RadioButton.setSelected(false);
                    miniAirGunRadioButton.setEnabled(false);
                    a35KHzRadioButton.setEnabled(false);
                    setAllRadioFalse();
                    cleanInputFields();
                }

            }
        });
        fase2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fase2RadioButton.isSelected()) {
                    sismicaRadioButton.setText("Sísmica Multicanal");
                    fase1RadioButton.setSelected(false);
                    miniAirGunRadioButton.setEnabled(true);
                    a35KHzRadioButton.setEnabled(true);
                    setAllRadioFalse();
                    cleanInputFields();

                }
            }
        });

//        batimetriaRadioButton.setActionCommand(Listener.Actions.BATIMETRIA_FASE_1.name());
//        batimetriaRadioButton.addActionListener(new Listener());
        batimetriaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                batimetriaRadioButton.setSelected(true);
                if (fase1RadioButton.isSelected()) {
                    if (batimetriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.BATIMETRIA_FASE_1));
                    }
                } else {
                    if (batimetriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.BATIMETRIA_FASE_2));
                    }
                }
            }
        });

        gravimetriaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                gravimetriaRadioButton.setSelected(true);
                if (fase1RadioButton.isSelected()) {
                    if (gravimetriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.GRAVIMETRIA_FASE_1));
                    }
                } else {
                    if (gravimetriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.GRAVIMETRIA_FASE_2));
                    }
                }
            }
        });

        magnetometriaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                magnetometriaRadioButton.setSelected(true);
                if (fase1RadioButton.isSelected()) {
                    if (magnetometriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.MAGNETOMETRIA_FASE_1));
                    }
                } else {
                    if (magnetometriaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.MAGNETOMETRIA_FASE_2));
                    }
                }
            }
        });

        sismicaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                sismicaRadioButton.setSelected(true);
                if (fase1RadioButton.isSelected()) {
                    if (sismicaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.SEGY_FASE_1));
                    }
                } else {
                    if (sismicaRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.SISMICA_FASE_2_MULTICANAL));
                    }
                }
            }
        });

        miniAirGunRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                miniAirGunRadioButton.setSelected(true);
                if (fase2RadioButton.isSelected()) {
                    if (miniAirGunRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props.SISMICA_FASE_2_AIR_GUN));
                    }
                }
            }
        });

        a35KHzRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllRadioFalse();
                a35KHzRadioButton.setSelected(true);
                if (fase2RadioButton.isSelected()) {
                    if (a35KHzRadioButton.isSelected()) {
                        sourceTextField.setText(Config.getPropValue(Config.Props._35KHZ_FASE_2));
                    }
                }
            }
        });

    }
    private void setAllRadioFalse() {
        for (JRadioButton jRadioButton : jRadioButtons) {
            jRadioButton.setSelected(false);
        }
    }

    private void cleanInputFields() {
        sourceTextField.setText("");
        targetTextField.setText("");
        fileTextArea.setText("");
    }
    //====================================================
    //Testing create/copy folder recursively - Diego
    public void copyFolder(Path src, Path dest, String files) throws IOException {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source)), files));
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage(), ex);
        }

    }
    //====================================================

    private void copy(Path source, Path dest, String files) {
        try {
            String path = source.toString();
            String target = dest.toString();
            File srcFile = new File(path);
            File destFile = new File(target);
            if(srcFile.isDirectory()){
                destFile.mkdir();
            }else{
                if(srcFile.isFile()){
                    copyFile(path, target, files);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // ====================================================
//    public void copyFile(String sourcePath, String destPath, String files) {
    public void copyFile(String sourcePath, String destPath, String files) {
        String fileList[] = files.split(",");
        Double progressInc = (double) 100 / qtFiles;
//        System.out.println("QTD FILES ==> " + qtFiles);
//        System.out.println(progressInc);
        Double counter = progressInc;
        String text = "Arquivos com erro ao copiar: \n";
        String errorsCapture ="";
        ArrayList<String> errors = new ArrayList<>();
        for (String file : fileList) {
            try {
//                System.out.println("TRIM FILE => *" + sourcePath + file.trim() + "* <= ");
//                File source = new File(sourcePath + file.trim());
                //File dest = new File(destPath + file.trim());
                /************************************
                * MODIFIED - DIEGO
                * File source = new File(sourcePath);
                *************************************/
                File source = new File(sourcePath);


                if (!(file.isEmpty())) {
                    source = new File(sourcePath + file.trim());
                    File dest = new File(destPath + file.trim());
                    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }else {
                    if (source.isDirectory()){
                        source.mkdir();
                    }else{
                        System.out.println("Else mkdir ´error´ ");
                    }
                }

            } catch (Exception exception) {
                System.out.println(exception);
                for (String arq : errors) {
                    errorsCapture += arq + "\n";
                }
//                JOptionPane.showMessageDialog(null, "Erro ao copiar arquivo: " + file);
                errors.add(file);
            }
            if (counter >= 1) {
                if (counter >= 2) {
                    progressBar.setValue(progressBar.getValue() + counter.intValue());
                } else {
                    progressBar.setValue(progressBar.getValue() + 1);
                }
                counter = 0.0;
            } else {
                counter += progressInc;
            }
            //System.out.println(progressBar.getValue());
        }
        JTextArea errorFiles = new JTextArea(10, 10);

//        for (String file : errors) {
//            text += file + "\n";
//        }
        errorFiles.setText(text);
        errorFiles.setWrapStyleWord(true);
        errorFiles.setLineWrap(true);
        errorFiles.setCaretPosition(0);
        errorFiles.setEditable(false);

//        System.out.println(fileList.length);
//        if(fileList.length){
//            JOptionPane.showMessageDialog(null, new JScrollPane(errorFiles), "Cópia Finalizada", JOptionPane.INFORMATION_MESSAGE);
//        }
    }

}
