import java.awt.event.ActionEvent;
import java.io.File;

public class FileButtonListener extends FrameButtonsListener {

    FileButtonListener(UIFrame frame) {
        super(frame);
    }

    File lastFile = new File("./");

    @Override
    public void actionPerformed(ActionEvent e) {

        frame.fileChooser.setCurrentDirectory(lastFile);

        // 0 when file was chosen (else 1)
        if (0 == frame.fileChooser.showOpenDialog(frame)) {
            lastFile = frame.fileChooser.getSelectedFile();
            frame.pathTextField.setText(lastFile.getAbsolutePath());
        }
    }
}
