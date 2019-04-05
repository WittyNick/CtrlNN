
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Image; // to set Frame icon
import java.awt.Toolkit; //

public final class UIFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    static final String APP_TITLE = "Нумерация кадров УП";

    JTextField pathTextField = new JTextField();

    JButton fileButton = new JButton("Файл...");
    JButton applyButton = new JButton("Применить");

    JCheckBox skipCheckBox = new JCheckBox("После: ~");
    JCheckBox m2CheckBox = new JCheckBox("До: M2");
    JCheckBox spaceCheckBox = new JCheckBox("Пробел");
    JCheckBox bakCheckBox = new JCheckBox(".bak");

    JCheckBox deleteNumerationCheckBox = new JCheckBox("Удалить номера кадров");
    JCheckBox deleteEmptyLinesCheckBox = new JCheckBox("Удалить пустые строки");
    JCheckBox deleteSpacesCheckBox = new JCheckBox("Удалить пробелы");
    JCheckBox deleteCaretsCheckBox = new JCheckBox("Удалить символы ^");

    Integer [] comboBoxItems = { 1, 5, 10 };
    JComboBox<Integer> stepComboBox = new JComboBox<Integer>(comboBoxItems);

    JTabbedPane tabbedPane = new JTabbedPane();

    JFileChooser fileChooser = new JFileChooser();
    JPanel contentPanel = new JPanel();


    JPanel numerationPanel = new JPanel();
    JPanel deleteCharsPanel = new JPanel();

    JLabel stepLabel = new JLabel("Шаг:");

    FileButtonListener fileButtonAction = new FileButtonListener(this);


    UIFrame() {
        super(APP_TITLE);

        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        setIconImage(image);
    }

    void create() {
        addFileChooserFilters();

        tabbedPane.addTab("Нумерация кадров", numerationPanel);
        tabbedPane.addTab("Удаление лишних символов", deleteCharsPanel);

        stepComboBox.setSelectedIndex(1);

        stepLabel.setToolTipText("Шаг нумерации");

        skipCheckBox.setSelected(true);
        skipCheckBox.setToolTipText("Применить только для строк ниже символа ~");

        m2CheckBox.setSelected(true);
        m2CheckBox.setToolTipText("Применить до M2/M02");

        stepComboBox.setToolTipText("Шаг нумерации");
        spaceCheckBox.setToolTipText("Вставлять пробел после номера кадра");

        bakCheckBox.setSelected(true);
        bakCheckBox.setToolTipText("Создать резервную копию файла");

        fileButton.setFocusable(false);
        fileButton.setToolTipText("Выбрать файл");

        addButtonActionListeners();

        buildUIElements();
        initUIFrame();
    }

    private void addButtonActionListeners() {
        fileButton.addActionListener(fileButtonAction);
        applyButton.addActionListener(new ApplyButtonListener(this));
    }

    private void addFileChooserFilters() {
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Текстовые (*.prj;*.prg;*.bak;*.txt)", "prj",
                "prg", "bak", "txt");
        FileNameExtensionFilter sinumerikFilter = new FileNameExtensionFilter("Sinumerik (*.mpf;*.spf;*.arc)", "mpf",
                "spf", "arc");
        FileNameExtensionFilter fagorFilter = new FileNameExtensionFilter("Fagor (*.pit;*.pim)", "pit", "pim");

        fileChooser.addChoosableFileFilter(textFilter);
        fileChooser.addChoosableFileFilter(sinumerikFilter);
        fileChooser.addChoosableFileFilter(fagorFilter);
    }

    private void initUIFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(applyButton);
        setContentPane(contentPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUIElements() {
        GridBagLayout gbl = new GridBagLayout();
        contentPanel.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();

        // pathTextField
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 4);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(pathTextField, c);
        contentPanel.add(pathTextField);

        // fileButton
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(fileButton, c);
        contentPanel.add(fileButton);

        // skipCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 0, 5, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(skipCheckBox, c);
        contentPanel.add(skipCheckBox);

        // m2CheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5, 15, 5, 125);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(m2CheckBox, c);
        contentPanel.add(m2CheckBox);

        // bakCheckBox
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(5, 0, 5, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(bakCheckBox, c);
        contentPanel.add(bakCheckBox);

        // tabbedPane
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(tabbedPane, c);
        contentPanel.add(tabbedPane);

        numerationPanel.setLayout(gbl);

        // stepLabel
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(stepLabel, c);
        numerationPanel.add(stepLabel);

        // stepComboBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 5, 0, 10);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(stepComboBox, c);
        numerationPanel.add(stepComboBox);

        // spaceCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(spaceCheckBox, c);
        numerationPanel.add(spaceCheckBox);

        // applyButton
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(applyButton, c);
        contentPanel.add(applyButton);

        deleteCharsPanel.setLayout(gbl);

        // deleteEmptyLinesCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(deleteEmptyLinesCheckBox, c);
        deleteCharsPanel.add(deleteEmptyLinesCheckBox);

        // deleteSpacesCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(deleteSpacesCheckBox, c);
        deleteCharsPanel.add(deleteSpacesCheckBox);

        // deleteNumerationCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 30, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(deleteNumerationCheckBox, c);
        deleteCharsPanel.add(deleteNumerationCheckBox);

        // deleteNumerationCheckBox
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 30, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(deleteCaretsCheckBox, c);
        deleteCharsPanel.add(deleteCaretsCheckBox);
    }
}