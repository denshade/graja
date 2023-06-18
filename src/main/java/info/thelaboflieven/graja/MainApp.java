package info.thelaboflieven.graja;

import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class MainApp extends JFrame implements ActionListener
{

    EditorWithHeader editor;
    private FileTree fileTree;
    private final JPanel contentPane;
    private File directory;


    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();
        DirectoryDialog directoryDialog = new DirectoryDialog(null);
        directoryDialog.setVisible(true);
        var dir = directoryDialog.getDirectory();
        SwingUtilities.invokeLater(() -> {
            try {
                new MainApp(dir).setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public MainApp(File directory) throws IOException {
        contentPane = new JPanel(new BorderLayout());
        var mb = new JMenuBar();
        mb.add(createFileMenu(directory));
        mb.add(createGradleMenu());
        setJMenuBar(mb);
        editor = new EditorWithHeader();
        contentPane.add(editor, BorderLayout.CENTER);
        fileTree = new FileTree(directory);
        fileTree.addListener(editor);
        contentPane.add(fileTree, BorderLayout.WEST);

        setContentPane(contentPane);
        setTitle("Graja");
        setIconImage(ImageIO.read(new File("res/graja.png")));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    private JMenu createGradleMenu() {
        var gradleMenu = new JMenu("Gradle");

        var gradleBuild = new JMenuItem("Gradle build");
        gradleMenu.add(gradleBuild);
        gradleBuild.addActionListener(this);
        return gradleMenu;
    }

    private JMenu createFileMenu(File directory) {
        var fileMenu = new JMenu("File");
        this.directory = directory;
        var openDirectoryItem = new JMenuItem("Open directory");
        var saveCurrentFile = new JMenuItem("Save file");
        var exitMenuItem = new JMenuItem("Exit");
        openDirectoryItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        saveCurrentFile.addActionListener(this);
        fileMenu.add(openDirectoryItem);
        fileMenu.add(saveCurrentFile);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit" -> exitAction();
            case "Open directory" -> openDirectoryAction();
            case "Save file" -> saveAction();
            case "Gradle build" -> Executors.newSingleThreadExecutor().execute(() -> new GradleRunner().run(directory));
            default -> throw new RuntimeException("Unknown option");
        }
    }

    private void saveAction() {
        editor.triggerSave();
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
            fileTree.addListener(editor);
            contentPane.add(fileTree, BorderLayout.WEST);
            pack();
        }
    }

    private void exitAction() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);

        if(dialogButton == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

}
