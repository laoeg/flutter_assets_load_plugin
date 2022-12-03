import com.example.flutter_asssts_load_plugin.VFlowLayout;

import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        VFlowLayout vFlowLayout = new VFlowLayout();

        JPanel op = new JPanel(vFlowLayout);
        op.setLayout(null);
        op.setBackground(Color.BLUE);
        JButton open = new JButton("查看");
        op.add(open,BorderLayout.LINE_START);
        open.setBounds(0,0,100,100);//设置按钮大小
//        frame.setLayout(null);
        frame.setBounds(400,400,500,500);//设置窗口大小
        frame.add(op);
        frame.setVisible(true);
    }
}
