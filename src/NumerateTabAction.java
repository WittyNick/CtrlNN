
public class NumerateTabAction extends TabAction {

    private boolean addSpaces;

    NumerateTabAction(final UIFrame frame) {
        super(frame);
        addSpaces = frame.spaceCheckBox.isSelected();
    }

    /**
     * Numerate lines
     */
    private void numerate() {
        StringBuffer resultText = new StringBuffer();
        int startIndex = getStartIndex();
        int endOfLine;

        int selectedStep = (Integer) frame.stepComboBox.getSelectedItem();
        int stepNo = selectedStep;

        if (0 != startIndex) {
            startIndex = startIndex + lsLength;
            for (int i = 0; i < startIndex; i++) {
                resultText.append(textBuffer.charAt(i));
            }
        }

        endOfLine = textBuffer.indexOf(ls, startIndex);

        if (endOfLine != -1) {
            boolean flag = false;

            endOfLine = endOfLine + lsLength;

            while (endOfLine <= textBuffer.length()) {

                if (m2CheckBoxIsSelected) {

                    int indexOfM2 = getEndIndex();

                    if (indexOfM2 != -1 && startIndex > indexOfM2) {
                        for (int i = startIndex; i < textBuffer.length(); i++) {
                            resultText.append(textBuffer.charAt(i));
                        }
                        break;
                    }
                }

                // add lines numeration
                // if <- defines two ls running together
                if (textBuffer.indexOf(ls, startIndex) != startIndex) {
                    char c = textBuffer.charAt(startIndex);

                    if (c == '/') {
                        resultText.append(c);
                        startIndex++;
                        c = textBuffer.charAt(startIndex);
                    }

                    if (c != '%' && c != ':' && c != ';' && c != '(' && c != '!') {

                        // for old GF O at the begin of the program don't numerate
                        if (!(c == 'O' && Character.isDigit(textBuffer.charAt(startIndex + 1)))) {
                            resultText.append("N" + stepNo);
                            if (addSpaces) {
                                resultText.append(' ');
                            }
                            stepNo = stepNo + selectedStep;
                        }
                    }
                }

                for (int i = startIndex; i < endOfLine; i++) {
                    resultText.append(textBuffer.charAt(i));
                }

                if (flag)
                    break;

                startIndex = endOfLine;
                endOfLine = textBuffer.indexOf(ls, startIndex) + lsLength;

                if (endOfLine >= textBuffer.length()) {
                    endOfLine = textBuffer.length();
                    flag = true; // when the second iteration
                }
            }
        }

        textBuffer.delete(0, textBuffer.length());
        textBuffer.append(resultText);
    }

    @Override
    void applyChanges() {
        if (readFile()) {
            deleteNumeration();
            numerate();

            if (!bakCheckBoxIsSelected || makeBackUpFile()) {
                write();
            }
        }
    }

}
