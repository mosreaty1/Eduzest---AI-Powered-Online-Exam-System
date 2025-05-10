public interface AdminOperations {
    void createExam();
    void editExam(String examName);
    void deleteExam(String examName);
    void addStudent();
    void deleteStudent();
    void updateStudent();
    void logout();
}
