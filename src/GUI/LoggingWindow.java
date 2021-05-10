package GUI;
import DAO.MyConnection;
import javax.swing.*;
import java.sql.SQLException;

public class LoggingWindow extends JFrame {
    public LoggingWindow(){
        JFrame window = new JFrame("Авторизация");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(400, 200);
     //   window.setLayout(null);
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
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Войти");
        loginButton.setBounds(50, 120, 50, 50);
        JButton goBackButton = new JButton("Назад");
        goBackButton.setBounds(100, 120, 50, 50);
        buttonPanel.add(loginButton);
        buttonPanel.add(goBackButton);

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
        goBackButton.addActionListener(e -> {
            window.setVisible(false);
            try {
                MainWindow mainWindow = new MainWindow();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(txtError);
        loginPanel.add(buttonPanel);
        window.add(loginPanel);
    //    window.add(buttonPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
