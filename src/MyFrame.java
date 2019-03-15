import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.jar.JarEntry;

public class MyFrame extends JFrame implements ActionListener {
    private static int N = 3;
    private String select_word;
    JPanel Upanel;
    JButton[] button = new JButton[26];
    JPanel[] panels = new JPanel[N];
    JPanel[] vpanels = new JPanel[N];
    JPanel panelhangman;
    JButton buttonnext;
    JButton buttonend;
    JButton buttonrestart;
    WordManage WM;
    Hangman HM;

    MyFrame() {
        initComponent();
        creatUI();
        this.setTitle("Hangman");
        this.setLocation(0, 0);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setVisible(true);
    }

    public void deleteAll() {
        vpanels[0].removeAll();
        vpanels[1].removeAll();
        WM.jPanel.removeAll();
        panels[0].remove(vpanels[0]);
        panels[0].remove(vpanels[1]);
        panels[2].removeAll();

        return;
    }

    public void creatUI() {
        paint_letter();
        paint_word();
        Upanel.setLayout(new GridLayout(4, 1, 10, 10));
        Upanel.add(panels[0]);
        Upanel.add(panels[2]);
        Upanel.add(panels[1]);
        HM.paintHangMan(0, Upanel);
        Upanel.add(HM.labelImage);
        this.setContentPane(Upanel);
        return;
    }

    public void paint_word() {
        select_word = WM.ChooseWord();
        if (select_word == null) {
            showGameOver();
            return;
        }
        WM.initialLable(select_word.length());
        panels[2].setLayout(new GridLayout(1, 1, 10, 10));
        panels[2].add(WM.jPanel);
        panels[1].add(vpanels[2]);
        return;
    }

    public void paint_letter() {
        for (int i = 0; i < 26; i++) {
            button[i] = new JButton(String.valueOf((char) (97 + i)).toUpperCase());
            button[i].addActionListener(this);
            if (String.valueOf((char) (97 + i)).toUpperCase().equals("A") ||
                    String.valueOf((char) (97 + i)).toUpperCase().equals("E") ||
                    String.valueOf((char) (97 + i)).toUpperCase().equals("I") ||
                    String.valueOf((char) (97 + i)).toUpperCase().equals("O") ||
                    String.valueOf((char) (97 + i)).toUpperCase().equals("U")) {
                vpanels[0].add(button[i]);   //元音，副面板0
            } else {
                vpanels[1].add(button[i]);   //辅音，副面板1
            }
            button[i].setPreferredSize(new Dimension(80, 30));
        }
        //副面板1
        vpanels[1].setLayout(new GridLayout(3, 7, 10, 10));
        panels[0].setLayout(new GridLayout(2, 1));
        panels[0].add(vpanels[0]);
        panels[0].add(vpanels[1]);
    }

    public void initComponent() {
        for (int i = 0; i < N; i++) {
            vpanels[i] = new JPanel();
            panels[i] = new JPanel();
        }
        buttonnext = new JButton("next");
        buttonend = new JButton("end");
        buttonrestart = new JButton("restart");
        panelhangman = new JPanel();
        WM = new WordManage();
        HM = new Hangman();
//        panelhangman.add(HM.labelImage);
        vpanels[2].setLayout(new GridLayout(1, 2));
        buttonnext.setPreferredSize(new Dimension(150, 30));
        buttonend.setPreferredSize(new Dimension(150, 30));
        vpanels[2].add(buttonrestart);
        vpanels[2].add(buttonnext);
        vpanels[2].add(buttonend);
        buttonrestart.addActionListener(this);
        buttonnext.addActionListener(this);
        buttonend.addActionListener(this);
        Upanel = new JPanel();

    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        for (int i = 0; i < 26; i++) {
            if (source == button[i]) {
                button[i].setVisible(false);
                int cas = WM.Judge(select_word, i);
                HM.paintHangMan(WM.times, Upanel);
                switch (cas) {
                    case 1:
                        this.repaint();
                        showFailure(this);
                        break;
                    case 2:
                        showWinDialog(this);
                        break;
                    default:
                        HM.paintHangMan(WM.times, Upanel);
                        this.repaint();
                        break;
                }
                return;
            }
        }
        if (source == buttonnext) {
            deleteAll();
            creatUI();
            this.repaint();
            return;
        }
        if (source == buttonend) {
            this.dispose();
            return;
        }
        if (source == buttonrestart) {
            WM.hashMap.clear();
            deleteAll();
            creatUI();
//            System.out.println(WM.hashMap.size());
            this.repaint();
        }
    }

    public void showWinDialog(JFrame f) {
        final JDialog dialog = new JDialog(this, "Win", true);
        dialog.setSize(250, 250);
        dialog.setLocationRelativeTo(this);
        JLabel messagewin = new JLabel("答对单词" + select_word);
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                deleteAll();
                creatUI();
                f.repaint();
            }
        });

        JPanel panel = new JPanel();
        panel.add(messagewin);
        panel.add(okBtn);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    public void showFailure(JFrame f) {
        final JDialog dialog = new JDialog(this, "Failure", true);
        dialog.setSize(250, 250);
        dialog.setLocationRelativeTo(this);
        JLabel messagewin = new JLabel("答错单词,正确单词是" + select_word);
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                deleteAll();
                creatUI();
//                HM.paintHangMan(0,Upanel);
                f.repaint();
            }
        });
        JPanel panel = new JPanel();
        panel.add(messagewin);
        panel.add(okBtn);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    public void showGameOver() {
        final JDialog dialog = new JDialog(this, "Gameover", true);
        dialog.setSize(250, 250);
        dialog.setLocationRelativeTo(this);
        JLabel messagewin = new JLabel("完成所有单词");
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(messagewin);
        panel.add(okBtn);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
