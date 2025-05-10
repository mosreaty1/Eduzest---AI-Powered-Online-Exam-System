public interface LoginFrameInterface {
    void showLoginSuccess(String username, String role);
    void showLoginFailure(String errorMessage);
    String getUsername();
    String getPassword();
    String getRole();
    void navigateToRegistration();
}
