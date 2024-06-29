package core.windows;

import javax.swing.*;

public class MainFrame {
    JFrame frame;

    public MainFrame(JPanel panel) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
