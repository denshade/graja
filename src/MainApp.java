import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class MainApp extends JFrame implements ActionListener, FileTree.FileTreeListener
{

    private final RSyntaxTextArea javaTextEditor;
    private FileTree fileTree;
    private final JPanel contentPane;
    private File directory;


    public static void main(String[] args)
    {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        var dir = fc.getSelectedFile();
        SwingUtilities.invokeLater(() -> new MainApp(dir).setVisible(true));
    }
    public MainApp(File directory)
    {
        contentPane = new JPanel(new BorderLayout());
        var mb = new JMenuBar();
        var x = new JMenu("File");
        this.directory = directory;

        var m1 = new JMenuItem("Open directory");
        var m3 = new JMenuItem("Exit");
        m1.addActionListener(this);
        m3.addActionListener(this);
        x.add(m1);
        x.add(m3);
        mb.add(x);
        setJMenuBar(mb);
        javaTextEditor = new RSyntaxTextArea(20, 60);
        javaTextEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        javaTextEditor.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(javaTextEditor);
        contentPane.add(sp, BorderLayout.CENTER);
        fileTree = new FileTree(directory);
        fileTree.addListener(this);
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
            exitAction();
        }
        if("Open directory".equals(e.getActionCommand())){
            openDirectoryAction();
        }

    }

    private void openDirectoryAction() {
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

    private void exitAction() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        dialogButton = JOptionPane.showConfirmDialog (null, "Would You Like to Save your Previous Note First?","Warning",dialogButton);

        if(dialogButton == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    @Override
    public void fileChanged(File file) {
        java.util.List<String> lines = Collections.emptyList();
        try
        {
            lines =
                    Files.readAllLines(new File(directory + File.separator + file.toString()).toPath(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            // do something
            e.printStackTrace();
        }
        javaTextEditor.setText(String.join("\n", lines));
    }
}
