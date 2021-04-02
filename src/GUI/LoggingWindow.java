package GUI;
import DAO.MyConnection;
import javax.swing.*;
import java.sql.SQLException;

public class LoggingWindow {
    public LoggingWindow(){
        JFrame window = new JFrame("Авторизация");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 200);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));

        JPanel usernamePanel = new JPanel();
        JLabel txtUser = new JLabel("Логин: ");
        JTextField usernameField = new JTextField(20);
        usernamePanel.add(txtUser);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        JLabel txtPassword = new JLabel("Пароль: ");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(txtPassword);
        passwordPanel.add(passwordField);
        JButton loginButton = new JButton("Войти");
        loginButton.setBounds(100, 150, 50, 50);
        JLabel txtError = new JLabel("Неверный логин или пароль!") ;
        txtError.setVisible(false);
        loginButton.addActionListener(e ->{
            try {
                String username = usernameField.getText();
                String password = new String (passwordField.getPassword());
                MyConnection conn = new MyConnection(username, password, "jdbc:oracle:thin:@localhost:1521:XE");
                CreateDBWindow createDBWindow = new CreateDBWindow(conn);
                window.setVisible(false);
            } catch (SQLException throwables) {
                txtError.setVisible(true);
                throwables.printStackTrace();

            }
        });

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(txtError);
        loginPanel.add(loginButton);

        window.add(loginPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
