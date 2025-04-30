import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class GPACalculator extends JFrame {
    private JTextField nameField, idField, courseField, creditHourField;
    private JComboBox<String> gradeBox, saveOptionBox;
    private JTextArea resultArea;
    private java.util.List<Course> courseList = new ArrayList<>();

    // Grade-to-point mapping
    private final Map<String, Double> gradePointMap = Map.of(
        "A", 4.0, "B+", 3.5, "B", 3.0, "C+", 2.5,
        "C", 2.0, "D", 1.0, "F", 0.0
    );

    public GPACalculator() {
        setTitle("Academic City GPA Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 580);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Theme colors
        Color acityRed = new Color(158, 27, 50);
        Color softGray = new Color(245, 245, 245);
        Font baseFont = new Font("Segoe UI", Font.PLAIN, 14);

        // App title
        JLabel titleLabel = new JLabel("JFrame GPA Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(acityRed);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(softGray);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        nameField = new JTextField(16);
        idField = new JTextField(16);
        courseField = new JTextField(16);
        creditHourField = new JTextField(6);

        gradeBox = new JComboBox<>(new String[]{"A", "B+", "B", "C+", "C", "D", "F"});
        saveOptionBox = new JComboBox<>(new String[]{"File", "Database"});

        String[] fieldLabels = {"Name:", "Student ID:", "Course:", "Grade:", "Credit Hours:", "Save Option:"};
        JComponent[] inputs = {nameField, idField, courseField, gradeBox, creditHourField, saveOptionBox};

        for (int i = 0; i < fieldLabels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            JLabel lbl = new JLabel(fieldLabels[i]);
            lbl.setFont(baseFont);
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            formPanel.add(inputs[i], gbc);
        }

        // Buttons
        JButton addButton = new JButton("Add Course");
        JButton calcButton = new JButton("Calculate GPA");

        addButton.setBackground(acityRed);
        addButton.setForeground(Color.WHITE);
        calcButton.setBackground(acityRed);
        calcButton.setForeground(Color.WHITE);

        addButton.setFocusPainted(false);
        calcButton.setFocusPainted(false);

        addButton.addActionListener(e -> addCourse());
        calcButton.addActionListener(e -> calculateGPA());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(softGray);
        buttonPanel.add(addButton);
        buttonPanel.add(calcButton);

        gbc.gridx = 0;
        gbc.gridy = fieldLabels.length;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Text output
        resultArea = new JTextArea(9, 40);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(acityRed), "Results"));
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addCourse() {
        try {
            String courseName = courseField.getText().trim();
            String selectedGrade = (String) gradeBox.getSelectedItem();
            int creditHrs = Integer.parseInt(creditHourField.getText().trim());

            if (courseName.isEmpty() || selectedGrade == null || creditHrs <= 0) {
                JOptionPane.showMessageDialog(this, "Fill in all fields with valid values.");
                return;
            }

            courseList.add(new Course(courseName, selectedGrade, creditHrs));
            resultArea.append("✔ Added: " + courseName + " (" + selectedGrade + ", " + creditHrs + " hrs)\n");

            courseField.setText("");
            creditHourField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Credit hours must be a number.");
        }
    }

    private void calculateGPA() {
        if (courseList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one course.");
            return;
        }

        String studentName = nameField.getText().trim();
        String studentID = idField.getText().trim();
        String option = (String) saveOptionBox.getSelectedItem();

        if (studentName.isEmpty() || studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your name and ID.");
            return;
        }

        double totalPoints = 0;
        int totalHours = 0;

        for (Course course : courseList) {
            totalPoints += gradePointMap.get(course.grade) * course.creditHours;
            totalHours += course.creditHours;
        }

        double gpa = totalPoints / totalHours;

        StringBuilder output = new StringBuilder();
        output.append("\n🎓 GPA Report\n");
        output.append("Name: ").append(studentName).append("\n");
        output.append("Student ID: ").append(studentID).append("\n");

        for (Course course : courseList) {
            output.append(" - ").append(course.courseName).append(": ")
                  .append(course.grade).append(" (").append(course.creditHours).append(" hrs)\n");
        }

        output.append(String.format("GPA: %.2f\n", gpa));
        resultArea.append(output.toString());

        if ("File".equals(option)) {
            try (FileWriter writer = new FileWriter("gpa_records.txt", true)) {
                writer.write(output.toString() + "\n");
                JOptionPane.showMessageDialog(this, "Saved to gpa_records.txt");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving to file.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Database feature not yet implemented.");
        }

        courseList.clear();
    }

    private static class Course {
        String courseName;
        String grade;
        int creditHours;

        Course(String name, String grade, int hours) {
            this.courseName = name;
            this.grade = grade;
            this.creditHours = hours;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignored
        }

        SwingUtilities.invokeLater(() -> new GPACalculator());
    }
}
