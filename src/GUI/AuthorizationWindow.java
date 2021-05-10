package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.SQLException;

public class AuthorizationWindow extends JFrame {
    public AuthorizationWindow(MyConnection conn)  {
        JFrame window = new JFrame("Выбор пользователя");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(300,300);
        window.setLayout(null);
        final String[] role = new String[1];
        JButton adminButton = new JButton("Администратор");
        adminButton.setBounds(50, 30, 200, 50);
        adminButton.addActionListener(e -> {
            try {
                role[0] = "admin";
                MenuWindow menuWindow = new MenuWindow(conn,role[0]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            window.setVisible(false);
        });
        JButton staffGIBDDButton = new JButton("Сотрудник ГИБДД");
        staffGIBDDButton.setBounds(50, 100, 200, 50);
        staffGIBDDButton.addActionListener(e-> {
            try {
                role[0] = "staffGIBDD";
                MenuWindow menuWindow = new MenuWindow(conn, role[0]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            window.setVisible(false);
        });
        JButton staffButton = new JButton("Сотрудник ДПС");
        staffButton.setBounds(50, 170, 200, 50);
        staffButton.addActionListener(e-> {
            try {
                role[0] = "staff";
                MenuWindow menuWindow = new MenuWindow(conn, role[0]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            window.setVisible(false);
        });
        window.add(adminButton);
        window.add(staffGIBDDButton);
        window.add(staffButton);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
