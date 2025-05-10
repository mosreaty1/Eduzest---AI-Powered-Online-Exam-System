public interface QuestionManagement {
    void addQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer);
    void finishExam();
}
