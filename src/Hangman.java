import javax.swing.*;
import java.awt.*;

public class Hangman {
    ImageIcon imageIcon;
    JLabel labelImage;

    Hangman() {
        imageIcon = new ImageIcon("D:\\IDEA\\hangman\\src\\Image\\0.png");
        imageIcon.setImage(
                imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(), Image.SCALE_DEFAULT)
        );
        labelImage = new JLabel();
        labelImage.setIcon(imageIcon);
        labelImage.setBounds(400, 200, 190, 190);
    }

    public void paintHangMan(int index, JPanel panel) {
        String url = "D:\\IDEA\\hangman\\src\\Image\\" + toString().valueOf(index) + ".png";
        panel.remove(labelImage);
        imageIcon = new ImageIcon(url);
        imageIcon.setImage(
                imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(), Image.SCALE_DEFAULT)
        );
        labelImage.setIcon(imageIcon);
        panel.add(labelImage);

    }
}
