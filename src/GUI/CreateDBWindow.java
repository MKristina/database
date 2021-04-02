package GUI;

import DAO.CreateTables;
import DAO.InsertData;
import DAO.MyConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateDBWindow extends JFrame {
    public CreateDBWindow(MyConnection conn) throws SQLException {
        JFrame window = new JFrame("ГИБДД");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400,200);
        window.setLayout(null);
        JLabel msg = new JLabel("Перед началом работы создайте БД");
        msg.setVisible(true);
        JButton createButton = new JButton("Создать БД");
        createButton.setBounds(50, 50, 200, 50);
        createButton.addActionListener(e -> {
            CreateTables createTables = new CreateTables(conn);
            createTables.create();
           InsertData insertData = new InsertData(conn);
           insertData.insert();
        });
        window.add(msg);
        window.add(createButton);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
