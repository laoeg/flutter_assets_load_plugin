package com.example.flutter_asssts_load_plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.intellij.util.PsiNavigateUtil;
import com.intellij.util.ui.JBImageIcon;
import org.apache.velocity.runtime.log.Log;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.Objects;

public class AssetsLoad extends AnAction {
    private String TAG = AssetsLoad.class.getSimpleName();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e)  {
//        Messages.showMessageDialog(e.getProject(), "aaaa", "this message title", Messages.getInformationIcon());

        String selectedText = getEditor(e).getSelectionModel().getSelectedText();
        String imagePath = e.getProject().getBasePath() + "/" + selectedText;
        File file = new File(imagePath);
        if(!file.exists()){
            imagePath = imagePath.replace("/images/", "/images_src/");
            file = new File(imagePath);
        }
        if(!file.exists()){
            System.out.println(TAG+" actionPerformed-> "+"assets/images and assets/images_src not hava: "+imagePath.substring(imagePath.lastIndexOf("/")));
            VFlowLayout vFlowLayout = new VFlowLayout();

            JPanel panel = new JPanel(vFlowLayout);
            JLabel error = new JLabel("<html>error : assets/images and assets/images_src not exits: "+imagePath.substring(imagePath.lastIndexOf("/"))+"</html>");
            error.setForeground(JBColor.RED);
            error.setMaximumSize(new Dimension(220,200));
//            error.setPreferredSize(new Dimension(220,30));
            error.setBorder(new EmptyBorder(5,20,5,20));
            panel.add(error);
            JBPopup jbPopup = JBPopupFactory.getInstance().createComponentPopupBuilder(panel, null).createPopup();
            jbPopup.showCenteredInCurrentWindow(Objects.requireNonNull(e.getProject()));

            return;
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(imagePath));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        VFlowLayout vFlowLayout = new VFlowLayout();

        JPanel panel = new JPanel(vFlowLayout);
        panel.setMinimumSize(new Dimension(200,0));


//        ImageIcon icon = new ImageIcon(image);
//        int iconWidth = icon.getIconWidth();
//        int iconHeight = icon.getIconHeight();
        JLabel label = new JLabel(file.getAbsolutePath().replace(e.getProject().getBasePath()+"/",""));
//        label.setPreferredSize(new Dimension(220,30));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel label2 = new JLabel();
//        label.setPreferredSize(new Dimension(220,20));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setText("w*h: "+image.getWidth()+"*"+image.getHeight()+"       Size:"+String.format("%.2f", Double.parseDouble(file.length()+"")/1024)+"kB");
        ZPanel zPanel = new ZPanel();
        int max = Math.max(image.getWidth(), image.getHeight());
        int min = Math.min(max, 200);
        int t = max / min;
        int realWidth =  image.getWidth() / t ;
        zPanel.setImgWidth(realWidth);
        int realHeight =  image.getHeight() / t ;
        zPanel.setImgHeight(realHeight);
        zPanel.setImagePath(imagePath);
        zPanel.setPreferredSize(new Dimension(realWidth,realHeight));

//        label.setIcon(icon);
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder());
        btnPanel.setLayout(null);
        btnPanel.setPreferredSize(new Dimension(100,30));
        JButton openBtn = new JButton("查看");
        openBtn.setBounds(0,0,70,30);
        openBtn.setBorder(BorderFactory.createEmptyBorder());

        panel.add(label);
        panel.add(label2);
        panel.add(zPanel);
        btnPanel.add(openBtn);
        panel.add(btnPanel);
        JBPopup jbPopup = JBPopupFactory.getInstance().createComponentPopupBuilder(panel, null).createPopup();
        jbPopup.showCenteredInCurrentWindow(Objects.requireNonNull(e.getProject()));
        File finalFile = file;
        openBtn.addActionListener((b)->{
            openFileInNewWindow(e, finalFile);
            jbPopup.cancel();
        });
    }

    private static void openFileInNewWindow(@NotNull AnActionEvent e, File file) {
        VirtualFile fileByIoFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        FileEditorManager.getInstance(e.getProject()).openFile(fileByIoFile, true, true);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        String selectedText = getEditor(e).getSelectionModel().getSelectedText();
        if(selectedText == null){
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        if(!selectedText.startsWith("assets")){
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        e.getPresentation().setEnabledAndVisible(true);
    }
    Editor getEditor(AnActionEvent e){
        return e.getRequiredData(CommonDataKeys.EDITOR);
    }
}
