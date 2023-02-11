import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

public class EditorWithHeader extends JPanel implements FileTree.FileTreeListener
{
    private RSyntaxTextArea javaTextEditor;
    private JLabel fileheader;
    public EditorWithHeader()
    {
        setLayout(new BorderLayout());
        javaTextEditor = new RSyntaxTextArea(20, 60);
        javaTextEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        javaTextEditor.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(javaTextEditor);
        fileheader = new JLabel("new file");
        add(fileheader, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }

    @Override
    public void fileChanged(File file) {
        if (!file.isFile()) return;
        java.util.List<String> lines = Collections.emptyList();
        try
        {
            lines =
                    Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            fileheader.setText(file.getAbsolutePath());
        }
        catch (IOException e)
        {
            // do something
            e.printStackTrace();
        }
        javaTextEditor.setText(String.join("\n", lines));
    }
}
