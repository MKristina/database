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
            ButtonMethods.selectFromDb(conn, vehiclesButton.getText(), "SELECT VehicleTypes.name, brand, model, dateOfIssue,engineNumber, engineVolume, enginePower, bodyNumber, color, bodyType FROM Vehicles INNER JOIN VehicleTypes USING(type_ID) ",
                    (resultSet -> {
                        try {
                            List<String> res = new LinkedList<>();
                            res.add(resultSet.getString(1));
                            res.add(resultSet.getString(2));
                            res.add(resultSet.getString(3));
                            res.add(resultSet.getString(4));
                            res.add(resultSet.getString(5));
                            res.add(resultSet.getString(6));
                            res.add(resultSet.getString(7));
                            res.add(resultSet.getString(8));
                            res.add(resultSet.getString(9));
                            res.add(resultSet.getString(10));
                            return res;
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        return null;
                    }));
        });
        JButton ownersButton = new JButton("Владельцы ТС");
        ownersButton.setBounds(20, 60, 200, 40);
        ownersButton.addActionListener(e -> {
           // try {
                ButtonMethods.selectFromDb(conn, ownersButton.getText(), "SELECT LastName, FirstName, patronymic, address, OwnerTypes.name FROM Owners INNER JOIN OwnerTypes USING(ownType_ID)",
                        (resultSet -> {
                            try {
                                List<String> res = new LinkedList<>();
                                res.add(resultSet.getString(1));
                                res.add(resultSet.getString(2));
                                res.add(resultSet.getString(3));
                                res.add(resultSet.getString(4));
                                res.add(resultSet.getString(5));
                                return res;
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            return null;
                        }));
        });
        JButton vehicleTypesButton = new JButton("Типы ТС");
        vehicleTypesButton.setBounds(20, 110, 200, 40);

        vehicleTypesButton.addActionListener(e-> {
            ButtonMethods.selectFromDb(conn, vehiclesButton.getText(), "SELECT VehicleTypes.name FROM VehicleTypes");
        });

        JButton registrationButton = new JButton("Регистрационный журнал");
        registrationButton.setBounds(20, 160, 200, 40);

        registrationButton.addActionListener(e-> {
            ButtonMethods.selectFromDb(conn, vehiclesButton.getText(), "select LastName, FirstName, brand, model, series, num, dateReg from registration join owners using(owner_id) join vehicles using (vehicle_id) join freenumbers using (num_id)",
                    (resultSet -> {
                        try {
                            List<String> res = new LinkedList<>();
                            res.add(resultSet.getString(1));
                            res.add(resultSet.getString(2));
                            res.add(resultSet.getString(3));
                            res.add(resultSet.getString(4));
                            res.add(resultSet.getString(5));
                            res.add(resultSet.getString(6));
                            res.add(resultSet.getString(7));
                            return res;
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        return null;
                    }));
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
