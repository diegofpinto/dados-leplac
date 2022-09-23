import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {

    public enum Actions {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton jRadioButton = (JRadioButton) e.getSource();
        if (jRadioButton.isSelected()) {
            switch (Actions.valueOf(e.getActionCommand())) {
                case BATIMETRIA_FASE_1:
                    break;
                case GRAVIMETRIA_FASE_1:
                    System.out.println("");
                    break;
                default:
                    System.out.println("");
            }
        }

    }
}
