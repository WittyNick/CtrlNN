
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class FrameButtonsListener implements ActionListener {

    final UIFrame frame;

    FrameButtonsListener(final UIFrame frame) {
        this.frame = frame;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
