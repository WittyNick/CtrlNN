
import java.awt.event.ActionEvent;

public class ApplyButtonListener extends FrameButtonsListener {

    ApplyButtonListener(final UIFrame frame) {
        super(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frame.pathTextField.getText().equals("")) {

            switch (frame.tabbedPane.getSelectedIndex()) {
                case 0: // Numerate tab picked
                    new NumerateTabAction(frame).applyChanges();
                    break;
                case 1: // DeleteExcessChars tab picked
                    new DeleteTabAction(frame).applyChanges();
                    break;
            }
        }
    }

}

