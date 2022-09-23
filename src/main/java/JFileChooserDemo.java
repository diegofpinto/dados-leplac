import javax.swing.*;


public class JFileChooserDemo extends JFrame {
    private JPanel panel1;
    private String path;
    private JFileChooser fileChooser = new JFileChooser();
    private JButton openButton = new JButton("Selecione...");

    public enum Props {
        TARGET_TEXT,
    }
    public String getPath(){
        return this.path;
    }
    public JFileChooserDemo(JTextField textField) {

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setApproveButtonText("Selecionar");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile()+"/";
        }

    }

}
