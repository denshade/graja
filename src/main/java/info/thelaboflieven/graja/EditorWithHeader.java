package info.thelaboflieven.graja;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

public class EditorWithHeader extends JPanel implements FileTree.FileTreeListener, DocumentListener
{
    private RSyntaxTextArea javaTextEditor;
    private JLabel fileheader;
    private File activeFile;
    private boolean fileChanged = false;
    public EditorWithHeader()
    {
        setLayout(new BorderLayout());
        javaTextEditor = new RSyntaxTextArea(20, 60);
        javaTextEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        javaTextEditor.setCodeFoldingEnabled(true);
        javaTextEditor.getDocument().addDocumentListener(this);
        RTextScrollPane sp = new RTextScrollPane(javaTextEditor);
        fileheader = new JLabel("new file");
        add(fileheader, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }

    @Override
    public void loadNewFile(File file) {
        if (!file.isFile()) return;
        activeFile = file;
        java.util.List<String> lines = Collections.emptyList();
        try
        {
            lines =
                    Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            fileheader.setText(file.getAbsolutePath());
        }
        catch (IOException e)
        {
            JOptionPane.showConfirmDialog(this, "Failed to load file "+ file+ " with "+ e.getMessage());
            e.printStackTrace();
        }
        javaTextEditor.setText(String.join("\n", lines));
        fileChanged = false;
        fileheader.setText(file.getAbsolutePath());
    }

    public void triggerSave() {
        try {
            FileWriter fileWriter = new FileWriter(activeFile);
            fileWriter.write(javaTextEditor.getText());
            fileWriter.close();
            fileChanged = false;
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(this, "Failed to save file " + activeFile+ " with "+ e.getMessage());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        toggleFileChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        toggleFileChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        toggleFileChanged();
    }

    private void toggleFileChanged() {
        fileChanged = true;
        fileheader.setText(activeFile.getAbsolutePath() + " +");
    }
}
