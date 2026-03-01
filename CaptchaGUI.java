import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGUI extends JFrame {

    private String captchaText;
    private JLabel captchaLabel;
    private JTextField inputField;

    public CaptchaGUI() {
        setTitle("CAPTCHA Generator");
        setSize(400, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        captchaLabel = new JLabel();
        generateCaptchaImage();

        inputField = new JTextField(15);

        JButton verifyButton = new JButton("Verify");
        JButton refreshButton = new JButton("Refresh");

        add(captchaLabel);
        add(new JLabel("Enter CAPTCHA:"));
        add(inputField);
        add(verifyButton);
        add(refreshButton);

        verifyButton.addActionListener(e -> {
            if (inputField.getText().equals(captchaText)) {
                JOptionPane.showMessageDialog(this, "CAPTCHA Verified!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect CAPTCHA!");
            }
        });

        refreshButton.addActionListener(e -> {
            generateCaptchaImage();
            inputField.setText("");
        });

        setVisible(true);
    }

    private void generateCaptchaImage() {
        captchaText = generateCaptchaText(6);

        int width = 200, height = 70;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString(captchaText, 40, 45);

        // Add noise
        Random rand = new Random();
        g.setColor(Color.GRAY);
        for (int i = 0; i < 10; i++) {
            int x1 = rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int x2 = rand.nextInt(width);
            int y2 = rand.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();
        captchaLabel.setIcon(new ImageIcon(image));
    }

    private String generateCaptchaText(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new CaptchaGUI();
    }
}