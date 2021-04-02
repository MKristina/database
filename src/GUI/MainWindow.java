package GUI;

import DAO.MyConnection;
import javax.swing.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    public MainWindow() throws SQLException {
        JFrame window = new JFrame("Подключение");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300,300);
        window.setLayout(null);

        JButton nsuConnButton = new JButton("Сервер НГУ");
        nsuConnButton.setBounds(50, 50, 200, 50);
        nsuConnButton.addActionListener(e -> {
            try {
                MyConnection conn = new MyConnection("18204_Meleshko", "meleshko",
                        "jdbc:oracle:thin:@84.237.50.81:1521:XE");
                CreateDBWindow createDBWindow = new CreateDBWindow(conn);
                window.setVisible(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JButton localConnButton = new JButton("Локальный сервер");
        localConnButton.setBounds(50, 150, 200, 50);
        localConnButton.addActionListener(e-> {
            LoggingWindow loggingWindow = new LoggingWindow();
            window.setVisible(false);
        });
        window.add(nsuConnButton);
        window.add(localConnButton);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
