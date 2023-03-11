import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DirectoryDialog extends JDialog implements ActionListener
{
    private final JPanel contentPane;
    private final JList historyList;
    private DefaultListModel listModel = new DefaultListModel<File>();

    public File getDirectory() {
        return (File)historyList.getSelectedValue();
    }

    DirectoryDialog(Frame parent) {
        super(parent, "Select a directory to open", Dialog.ModalityType.DOCUMENT_MODAL);
        setSize(500,500);
        setLocationRelativeTo(null);
        setTitle("Select a directory to open");
        contentPane = new JPanel(new BorderLayout());
        listModel.addElement(new File("Example"));
        historyList = new JList(listModel);
        historyList.setMinimumSize(new Dimension(300,300));
        historyList.setSize(new Dimension(300,300));
        var pane = new JScrollPane(historyList);

        contentPane.add(pane, BorderLayout.CENTER);
        var buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        JButton addDirectory = new JButton("Add directory");
        addDirectory.addActionListener(this);
        JButton selected = new JButton("OK");
        selected.addActionListener(this);
        buttons.add(addDirectory);
        buttons.add(selected);
        contentPane.add(buttons, BorderLayout.SOUTH);
        setContentPane(contentPane);
        pack();
    }

    public static void main(String[] args)
    {
        var dialog = new DirectoryDialog(null);
        dialog.show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add directory")) {
            final JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showOpenDialog(null);
            var dir = fc.getSelectedFile();
            listModel.addElement(dir);
        }
        if (e.getActionCommand().equals("OK")) {
            if (!historyList.isSelectionEmpty()) {
                setVisible(false);
            }
        }
    }
}
