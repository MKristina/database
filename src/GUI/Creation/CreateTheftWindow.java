package GUI.Creation;

import DAO.MyConnection;
import GUI.MenuWindow;
import GUI.Requests.TheftDateStatistics;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class CreateTheftWindow extends JFrame {
    public CreateTheftWindow(MyConnection conn, String role) throws ParseException {
        Map<String, String> vehicleMap = new HashMap<>();
        JFrame window = new JFrame("Добавление записи");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(500, 250);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));


        JPanel vehiclePanel = new JPanel();
        JLabel txtVehicle = new JLabel("Транспортное средство: ");
        JComboBox vehicleBox = new JComboBox();
        Vector resultVector2 = new Vector();
        resultVector2 = conn.select("select reg_id, brand || ' ' || model||' '|| series || ' ' || num from registration join vehicles using (vehicle_id) join freenumbers using (num_id)", 2);
        for(int i =0; i< resultVector2.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector2.get(i);
            vehicleMap.put(vec.get(1), vec.get(0));
            vehicleBox.addItem(vec.get(1));
        }
        vehiclePanel.add(txtVehicle);
        vehiclePanel.add(vehicleBox);

        JPanel DatePanel = new JPanel();
        JLabel txtDate = new JLabel("Дата угона: ");
        MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
        JFormattedTextField DateField = new JFormattedTextField(maskFormatter);
        DateField.setColumns(10);
        DatePanel.add(txtDate);
        DatePanel.add(DateField);

        JPanel BtnPanel = new JPanel();
        JButton createButton = new JButton("Создать");
        createButton.setBounds(100, 150, 50, 50);
        JLabel txtError = new JLabel("Данные введены неверно!");
        txtError.setVisible(false);
        JButton goBackButton = new JButton("Назад");
        createButton.setBounds(50, 150, 50, 50);
        BtnPanel.add(createButton);
        BtnPanel.add(goBackButton);
        createButton.addActionListener(e ->{
            try {
                int reg_id = Integer.parseInt(vehicleMap.get(vehicleBox.getSelectedItem()));
                String dateOfTheft = DateField.getText();
                List<String> theft = new LinkedList<>();

                theft.add("INSERT INTO VehicleTheft(reg_ID, dateOfTheft) VALUES('" + reg_id +  "',TO_DATE ('" + dateOfTheft + "', 'dd.mm.yyyy'))");
                conn.insert(theft);
                window.setVisible(false);
                if(role == "admin"){
                    TheftDateStatistics theftWindow = new TheftDateStatistics(conn, role);
                } else if (role == "staff"){
                    try {
                        MenuWindow menuWindow = new MenuWindow(conn, role);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            catch (Exception exep){
                exep.printStackTrace();
                txtError.setVisible(true);
            }
        });

        goBackButton.addActionListener((e)->{
            window.setVisible(false);
            if(role == "admin"){
                TheftDateStatistics theftDateStatistics = new TheftDateStatistics(conn, role);
            } else if (role == "staff"){
                try {
                    MenuWindow menuWindow = new MenuWindow(conn, role);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        createPanel.add(vehiclePanel);
        createPanel.add(DatePanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
