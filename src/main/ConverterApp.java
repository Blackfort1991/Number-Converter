package main;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigInteger;

public class ConverterApp extends JFrame {

    private boolean isDarkTheme = true;

    public ConverterApp() {
        setTitle("Конвертер систем числення 3000");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/icon.png"));
            setIconImage(img.getImage());
        } catch (Exception e) {
            System.out.println("Іконку не знайдено!");
        }
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 12, 12));
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
        setContentPane(mainPanel);

        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton themeButton = new JButton("Light Theme");
        themeButton.putClientProperty("JButton.buttonType", "roundRect");
        themeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        themeButton.setPreferredSize(new Dimension(180, 38)); 
        themeButton.setToolTipText("Перемкнути тему");

        themeButton.addActionListener(e -> toggleTheme(themeButton));

        themePanel.add(themeButton);
        mainPanel.add(themePanel);   

        mainPanel.add(new JLabel(""));   

        JLabel inputLabel = new JLabel("Вхідні дані:");
        inputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField inputField = new JTextField();
        inputField.putClientProperty("JTextField.placeholderText", "Введіть число тут...");

        Integer[] bases = {2, 3, 4, 5, 8, 10, 12, 16, 20, 36};
        JComboBox<Integer> fromBase = new JComboBox<>(bases);
        JComboBox<Integer> toBase = new JComboBox<>(bases);
        fromBase.setSelectedItem(10);
        toBase.setSelectedItem(2);

        JButton convertBtn = new JButton("КОНВЕРТУВАТИ");
        convertBtn.putClientProperty("JButton.buttonType", "roundRect");
        convertBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setFont(new Font("JetBrains Mono", Font.BOLD, 17));
        resultField.setHorizontalAlignment(JTextField.CENTER);

        mainPanel.add(inputLabel);
        mainPanel.add(inputField);

        JPanel labelsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        labelsPanel.add(new JLabel("З системи:"));
        labelsPanel.add(new JLabel("В систему:"));
        mainPanel.add(labelsPanel);

        JPanel comboPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        comboPanel.add(fromBase);
        comboPanel.add(toBase);
        mainPanel.add(comboPanel);

        mainPanel.add(convertBtn);
        mainPanel.add(resultField);

        convertBtn.addActionListener(e -> {
            try {
                String input = inputField.getText().trim();
                if (input.isEmpty()) return;

                int b1 = (int) fromBase.getSelectedItem();
                int b2 = (int) toBase.getSelectedItem();

                BigInteger number = new BigInteger(input, b1);
                String result = number.toString(b2).toUpperCase();

                resultField.setText(result);
                resultField.setForeground(isDarkTheme ?
                        new Color(144, 238, 144) :
                        new Color(0, 130, 0));

            } catch (Exception ex) {
                resultField.setText("Помилка даних!");
                resultField.setForeground(new Color(220, 80, 80));
            }
        });

        pack();
        setSize(430, 560);           
        setLocationRelativeTo(null);

        applyTheme();
    }

    private void toggleTheme(JButton themeButton) {
        isDarkTheme = !isDarkTheme;
        applyTheme();

        if (isDarkTheme) {
            themeButton.setText("Light Theme");
        } else {
            themeButton.setText("Dark Theme");
        }
    }

    private void applyTheme() {
        try {
            if (isDarkTheme) {
                FlatDarkLaf.setup();
            } else {
                FlatLightLaf.setup();
            }

            SwingUtilities.updateComponentTreeUI(this);

            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);

        } catch (Exception ex) {
            System.err.println("Не вдалося змінити тему: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();

            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);

        } catch (Exception ex) {
            System.err.println("Не вдалося запустити FlatLaf");
        }

        SwingUtilities.invokeLater(() -> new ConverterApp().setVisible(true));
    }
}