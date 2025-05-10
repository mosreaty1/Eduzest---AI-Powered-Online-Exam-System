import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Utility {
    private static final String USERS_FILE = "users.txt";
    private static final String CATEGORIES_FILE = "categories.txt";
    private static final String QUESTIONS_FILE = "questions.txt";

    // Authentication Methods
    public static boolean authenticateUser(String username, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password) && parts[2].equals(role)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveUser(String username, String password, String role) {
        try (FileWriter writer = new FileWriter(USERS_FILE, true)) {
            writer.write(username + "," + password + "," + role + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Grade Management Methods
    public static void saveGrade(String username, String examName, int grade) {
        File gradesFile = new File(username + "_grades.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradesFile, true))) {
            writer.write(examName + ", " + grade + "\n");
            System.out.println("Grade saved: " + examName + ", " + grade);  // Debugging line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadPreviousGrades(String username) {
        File gradesFile = new File(username + "_grades.txt");
        StringBuilder grades = new StringBuilder();

        if (gradesFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(gradesFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    grades.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return grades.toString();
    }






}