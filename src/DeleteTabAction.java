
public class DeleteTabAction extends TabAction {


    private boolean deleteEmptyLines;
    private boolean deleteSpaces;
    private boolean deleteNumeration;
    private boolean deleteCarets;

    DeleteTabAction(final UIFrame frame) {
        super(frame);

        deleteEmptyLines = frame.deleteEmptyLinesCheckBox.isSelected();
        deleteSpaces = frame.deleteSpacesCheckBox.isSelected();
        deleteNumeration = frame.deleteNumerationCheckBox.isSelected();
        deleteCarets = frame.deleteCaretsCheckBox.isSelected();;
    }

    /**
     * Delete charset
     *
     * @param c
     *            - symbol to delete
     */
    private void deleteChar(final char c) {

        int startIndex = getStartIndex();
        String s = String.valueOf(c);

        while (true) {
            int index = textBuffer.indexOf(s, startIndex);

            if (index == -1)
                break;

            if (m2CheckBoxIsSelected) {
                int lineAfterM02 = -1;

                int indexOfM2 = getEndIndex();

                if (indexOfM2 != -1) {
                    lineAfterM02 = textBuffer.indexOf(ls, indexOfM2);
                }

                if (lineAfterM02 != -1 && index > lineAfterM02)
                    break;
            }

            textBuffer.deleteCharAt(index);
            startIndex = index;
        }
    }

    /**
     * Delete empty lines
     */
    private void deleteEmptyLines() {

        int startIndex = getStartIndex();

        String emptyLine = ls + ls;

        while (true) {
            int index = textBuffer.indexOf(emptyLine, startIndex);
            if (index == -1) {
                break;
            }

            if (m2CheckBoxIsSelected) {
                int indexOfM2 = getEndIndex();

                if (-1 != indexOfM2 && index > indexOfM2) {
                    break;
                }
            }

            textBuffer.delete(index, index + lsLength);
            startIndex = index;
        }

    }

    /**
     * Delete checked symbols
     */
    @Override
    void applyChanges() {
        boolean wasChanged = false;

        if (readFile()) {
            if (deleteSpaces) {
                deleteChar(' ');
                wasChanged = true;
            }
            if (deleteCarets) {
                deleteChar('^');
                wasChanged = true;
            }
            if (deleteNumeration) {
                deleteNumeration();
                wasChanged = true;
            }
            if (deleteEmptyLines) {
                deleteEmptyLines();
                wasChanged = true;
            }
            if (wasChanged) {
                if (!bakCheckBoxIsSelected || makeBackUpFile()) {
                    write();
                }
            }
        }
    }
}
