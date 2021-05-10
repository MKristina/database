package GUI;

import DAO.MyConnection;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class UpdateTheftWindow extends JFrame {

    public UpdateTheftWindow(MyConnection conn, int theft_ID, String role){
        addActionListeners(conn,  theft_ID, role);

    }
    private void addActionListeners(MyConnection conn, int theft_ID, String role) {

        JFrame window = new JFrame("Транспорное средтсво найдено");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(300, 200);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel datePanel = new JPanel();
        JLabel txtDate = new JLabel("Дата возвращения ТС: ");
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField DateField = new JFormattedTextField(maskFormatter);
        DateField.setColumns(10);
        datePanel.add(txtDate);
        datePanel.add(DateField);

        JPanel BtnPanel = new JPanel();

        JButton updateButton = new JButton("Обновить данные");
        updateButton.setBounds(100, 150, 50, 50);
        JLabel txtError = new JLabel("Данные введены неверно!");
        txtError.setVisible(false);
        JButton goBackButton = new JButton("Назад");
        goBackButton.setBounds(50, 150, 50, 50);

        BtnPanel.add(updateButton);
        BtnPanel.add(goBackButton);

        updateButton.addActionListener(e ->{
            try {
                String dateOfReturn = DateField.getText();
                List<String> theft = new LinkedList<>();
                theft.add("UPDATE VehicleTheft SET dateOfReturn = TO_DATE ('" + dateOfReturn + "', 'dd.mm.yyyy') where theft_id = " + theft_ID);
                conn.insert(theft);
                window.setVisible(false);
                if(role == "admin"){
                    TheftWindow theftWindow = new TheftWindow(conn, role);
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
                TheftWindow theftWindow = new TheftWindow(conn, role);
            } else if (role == "staff"){
                try {
                    MenuWindow menuWindow = new MenuWindow(conn, role);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        createPanel.add(datePanel);
        createPanel.add(BtnPanel);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
