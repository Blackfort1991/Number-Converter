package main;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class ConverterApp extends JFrame {
    
    public ConverterApp() {
        // Налаштування головного вікна
        setTitle("Конвертер систем числення");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10)); // 6 рядків з відступами

        // Створюємо компоненти
        JTextField inputField = new JTextField();
        // Створюємо масив для вибору систем (від 2 до 36)
        Integer[] bases = {2, 3, 4, 5, 8, 10, 12, 16, 20, 36};
        JComboBox<Integer> fromBase = new JComboBox<>(bases);
        JComboBox<Integer> toBase = new JComboBox<>(bases);
        fromBase.setSelectedItem(10); // По замовчуванню з 10-ї
        toBase.setSelectedItem(2);    // По замовчуванню в 2-гу

        JButton convertBtn = new JButton("КОНВЕРТУВАТИ");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setFont(new Font("Monospaced", Font.BOLD, 14));

        // Додаємо елементи на вікно
        add(new JLabel(" Введіть ціле число:"));
        add(inputField);
        
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.add(new JLabel(" З системи:"));
        p.add(new JLabel(" В систему:"));
        add(p);

        JPanel comboPanel = new JPanel(new GridLayout(1, 2));
        comboPanel.add(fromBase);
        comboPanel.add(toBase);
        add(comboPanel);

        add(convertBtn);
        add(resultField);

        // Логіка кнопки
        convertBtn.addActionListener(e -> {
            try {
                String input = inputField.getText().trim();
                if (input.isEmpty()) return;

                int b1 = (int) fromBase.getSelectedItem();
                int b2 = (int) toBase.getSelectedItem();
                
                // Основна логіка перетворення
                BigInteger number = new BigInteger(input, b1);
                String result = number.toString(b2).toUpperCase();
                
                resultField.setText(result);
                resultField.setForeground(new Color(0, 100, 0)); // Темно-зелений для успіху
            } catch (Exception ex) {
                resultField.setText("Помилка: невірні дані!");
                resultField.setForeground(Color.RED);
            }
        });

        // Фінальні налаштування вікна
        setSize(400, 450);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Запуск програми
        SwingUtilities.invokeLater(() -> {
            new ConverterApp().setVisible(true);
        });
    }
}