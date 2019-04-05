
import java.io.*;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

public abstract class TabAction {

    protected final UIFrame frame;
    protected boolean skipCheckBoxIsSelected;
    protected boolean m2CheckBoxIsSelected;
    protected boolean bakCheckBoxIsSelected;

    TabAction(final UIFrame frame) {
        this.frame = frame;
        skipCheckBoxIsSelected = frame.skipCheckBox.isSelected();
        m2CheckBoxIsSelected = frame.m2CheckBox.isSelected();
        bakCheckBoxIsSelected = frame.bakCheckBox.isSelected();
    }

    String path;

    StringBuffer textBuffer = new StringBuffer();

    String ls = System.getProperty("line.separator");

    int lsLength = ls.length();

    abstract void applyChanges();

    /**
     * Read from selected file to textBuffer
     *
     * @return true when method was successfully complied
     */
    boolean readFile() {
        path = frame.pathTextField.getText().trim();
        frame.pathTextField.setText(path);

        try (BufferedReader buffReader = new BufferedReader(new FileReader(path))) {

            while (true) {
                String line = buffReader.readLine();

                if (line == null)
                    break;

                textBuffer.append(line + ls);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Ошибка. Файл не найден.", UIFrame.APP_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Ошибка. Невозможно прочитать файл.", UIFrame.APP_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Неизвестная ошибка при чтении файла.", UIFrame.APP_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    int getStartIndex() {
        int startIndex = 0;
        int endOfLine;

        if (skipCheckBoxIsSelected) {

            startIndex = textBuffer.indexOf("~");

            if (startIndex != -1) {
                endOfLine = textBuffer.indexOf(ls, startIndex);
                if (endOfLine != -1) {
                    startIndex = endOfLine;
                }
            } else {
                startIndex = 0;
            }
        }
        return startIndex;
    }

    /**
     *
     * @return
     */
    int getEndIndex() {
        int endIndex;

        int hat = getStartIndex();

        int a = textBuffer.indexOf("M02", hat);
        int b = textBuffer.indexOf("M2", hat);

        while (true) {

            // when M20
            if (-1 != b && Character.isDigit(textBuffer.charAt(b + 2))) {
                b = b + 2;
                b = textBuffer.indexOf("M2", b);
            } else {
                break;
            }
        }

        if (a == -1) {
            endIndex = b;
        } else if (b == -1) {
            endIndex = a;
        } else {
            endIndex = (a < b) ? a : b;
        }
        return endIndex;
    }

    /**
     * Delete lines numeration
     */
    void deleteNumeration() {
        int startIndex = getStartIndex();
        int endOfLine;

        if (0 != startIndex) {
            startIndex = startIndex + lsLength;
        }

        endOfLine = textBuffer.indexOf(ls, startIndex);
        if (-1 != endOfLine) {
            endOfLine = endOfLine + lsLength;

            boolean flag = false;

            while (endOfLine <= textBuffer.length()) {

                if (m2CheckBoxIsSelected) {

                    int indexOfM2 = getEndIndex();

                    if (-1 != indexOfM2 && startIndex > indexOfM2) {
                        break;
                    }
                }

                // when line is not empty
                if (textBuffer.indexOf(ls, startIndex) != startIndex) {
                    char firstSymbol = textBuffer.charAt(startIndex);

                    if (firstSymbol == '/') {
                        startIndex++;
                        firstSymbol = textBuffer.charAt(startIndex);
                    }

                    if (firstSymbol == 'N') {
                        int counter = 1;
                        boolean isNomber = false;

                        while (Character.isDigit(textBuffer.charAt(startIndex + counter))
                                || textBuffer.charAt(startIndex + counter) == ' ') {
                            isNomber = true;
                            counter++;
                        }

                        if (isNomber) {
                            textBuffer.delete(startIndex, startIndex + counter);

                            // after deleting substring endOfLine was shifted
                            endOfLine = endOfLine - counter;
                        }
                    }
                }

                if (flag) {
                    break;
                }

                startIndex = endOfLine;
                endOfLine = textBuffer.indexOf(ls, startIndex) + lsLength;
                if (endOfLine >= textBuffer.length()) {
                    endOfLine = textBuffer.length();
                    flag = true; // when the second iteration
                }

            }
        }
    }

    /**
     * Writes changed file
     *
     * @return true when method was successfully completed
     */
    void write() {

        // System.out.println(textBuffer.toString());
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(path))) {
            buffWriter.write(textBuffer.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Ошибка записи.", UIFrame.APP_TITLE, JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(frame, "Готово!", UIFrame.APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Create backup file *.bak
     *
     * @return true when it was successfully completed
     */
    boolean makeBackUpFile() {
        File sourceFile = new File(path);
        String bakName = path;

        if (path.lastIndexOf(".") == path.length() - 4) {
            bakName = path.substring(0, path.length() - 4);
        }

        File backupFile = new File(bakName + ".bak");

        return copyFile(sourceFile, backupFile);
    }

    /**
     * Make file copy
     *
     * @param source
     *            - source file
     * @param dest
     *            - destination file
     */
    private boolean copyFile(final File source, final File dest) {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try (FileInputStream fileInputStream = new FileInputStream(source);
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {

            sourceChannel = fileInputStream.getChannel();
            destChannel = fileOutputStream.getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Ошибка. Не удаётся создать .bak файл.", UIFrame.APP_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                sourceChannel.close();
                destChannel.close();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Неизвестная ошибка при создании .bak файла.", UIFrame.APP_TITLE,
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
