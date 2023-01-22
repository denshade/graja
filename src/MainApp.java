import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainApp extends JFrame implements ActionListener
{

    private FileTree fileTree;
    private final JPanel contentPane;

    public static void main(String[] args)
    {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        var dir = fc.getSelectedFile().toString();
        SwingUtilities.invokeLater(() -> new MainApp(dir).setVisible(true));
    }
    private File directory;
    public MainApp(String directory)
    {
        contentPane = new JPanel(new BorderLayout());
        var mb = new JMenuBar();
        var x = new JMenu("File");

        var m1 = new JMenuItem("Open directory");
        var m3 = new JMenuItem("Exit");
        m1.addActionListener(this);
        m3.addActionListener(this);
        x.add(m1);
        x.add(m3);
        mb.add(x);
        setJMenuBar(mb);
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        contentPane.add(sp, BorderLayout.CENTER);
        fileTree = new FileTree(new File(directory));
        contentPane.add(fileTree, BorderLayout.WEST);

        setContentPane(contentPane);
        setTitle("Text Editor Demo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if("Exit".equals(e.getActionCommand())){

            int dialogButton = JOptionPane.YES_NO_OPTION;
            dialogButton = JOptionPane.showConfirmDialog (null, "Would You Like to Save your Previous Note First?","Warning",dialogButton);

            if(dialogButton == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
        if("Open directory".equals(e.getActionCommand())){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                directory = file;
                contentPane.remove(fileTree);
                fileTree = new FileTree(directory);
                contentPane.add(fileTree, BorderLayout.WEST);
                pack();
            }
        }

    }
}
