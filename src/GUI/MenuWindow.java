package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.sql.SQLException;
import java.util.LinkedList;

public class MenuWindow extends JFrame {


    public MenuWindow(MyConnection conn) throws SQLException {
        JFrame window = new JFrame("ГИБДД");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setLayout(null);
       // JLabel msg = new JLabel("Информационная система ГИБДД");
      //  msg.setVisible(true);
        JButton vehiclesButton = new JButton("Транспортные средства");
        vehiclesButton.setBounds(20, 10, 200, 40);

        vehiclesButton.addActionListener(e-> {
            VehiclesWindow vehiclesWindow = new VehiclesWindow(conn);
            window.setVisible(false);
        });
        JButton ownersButton = new JButton("Владельцы ТС");
        ownersButton.setBounds(20, 60, 200, 40);
        ownersButton.addActionListener(e -> {
            OwnersWindow ownersWindow = new OwnersWindow(conn);
            window.setVisible(false);
        });
        JButton vehicleTypesButton = new JButton("Типы ТС");
        vehicleTypesButton.setBounds(20, 110, 200, 40);

        vehicleTypesButton.addActionListener(e-> {
        VehicleTypesWindow vehicleTypesWindow = new VehicleTypesWindow(conn);
        window.setVisible(false);
        });

        JButton registrationButton = new JButton("Регистрационный журнал");
        registrationButton.setBounds(20, 160, 200, 40);

        registrationButton.addActionListener(e-> {
            RegistrationView registrationView = new RegistrationView(conn);
            window.setVisible(false);
        });

     // window.add(msg);
      window.add(ownersButton);
      window.add(vehiclesButton);
      window.add(vehicleTypesButton);
      window.add(registrationButton);
      window.setVisible(true);
      window.setLocationRelativeTo(null);
    }


}
