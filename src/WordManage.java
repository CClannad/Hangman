import javax.swing.*;
import java.awt.*;
import java.beans.beancontext.BeanContext;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Boolean.TRUE;

public class WordManage {
    private String word[] = new String[]{
            "hello", "world", "java", "nice", "to", "meet", "you"
    };
    HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
    JLabel[] label = new JLabel[10];
    JPanel jPanel;
    Font f;
    int times;

    WordManage() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 3, 10, 10));
        f = new Font("隶书", Font.PLAIN, 20);
        for (int i = 0; i < 10; i++) {
            label[i] = new JLabel();
        }
    }

    public String ChooseWord() {
        if (hashMap.size() == word.length) {
            return null;
        }
        int rand = (int) (Math.random() * (word.length));
        while (hashMap.get(rand) == TRUE) {
            rand = (int) (Math.random() * (word.length));
        }
        hashMap.put(rand, true);
        return word[rand];
    }

    public void initialLable(int size) {
        times = 0;
        for (int i = 0; i < size; i++) {
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            label[i].setFont(f);
            label[i].setText("-");
            jPanel.add(label[i]);
        }
    }

    //判断返回
    //0 正常返回继续输入单词
    //1 7次错误，失败
    //2 答案正确
    public int Judge(String str, int ascii) {
        char selected = (char) (97 + ascii);
        Boolean iswritedown = false;
        Boolean iswin = true;
        for (int i = 0; i < str.length(); i++) {
            if (selected == str.charAt(i)) {
                writeDown(str, i);
                iswritedown = true;
            }
        }
        if (!iswritedown) {
            times += 1;
            if (times == 7) {
                return 1;
            }
        }

        for (int i = 0; i < str.length(); i++) {
            if (label[i].getText().equals("-")) {
                iswin = false;
            }
        }
        if (iswin) {
            return 2;
        }
        return 0;
    }

    public void writeDown(String str, int index) {
        label[index].setText(String.valueOf(str.charAt(index)).toUpperCase());
    }
}
