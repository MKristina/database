package GUI.Creation;

import DAO.MyConnection;
import GUI.MenuWindow;
import GUI.Tables.AccidentsWindow;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateDTPWindow extends JFrame {
    public CreateDTPWindow(MyConnection conn, String role) {
        Map<String, String> accTypeMap = new HashMap<>();
        Map<String, String> accResMap = new HashMap<>();
        JFrame window = new JFrame("Добавление записи");
           window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(500, 500);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel AccTypePanel = new JPanel();
        JLabel txtTypeName = new JLabel("Тип ДТП: ");
        JComboBox typeBox = new JComboBox();
        Vector resultVector = new Vector();
        resultVector = conn.select("SELECT acctype_ID, name from AccidentTypes", 2);

        for(int i =0; i< resultVector.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector.get(i);
            accTypeMap.put(vec.get(1), vec.get(0));
            typeBox.addItem(vec.get(1));
        }

        AccTypePanel.add(txtTypeName);
        AccTypePanel.add(typeBox);

        JPanel AccReasonPanel = new JPanel();
        JLabel txtReason = new JLabel("Причина ДТП: ");
        JComboBox reasonBox = new JComboBox();
        Vector resultVector2 = new Vector();
        resultVector2 = conn.select("SELECT accReason_ID, accDescription from AccidentReasons", 2);

        for(int i =0; i< resultVector2.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector2.get(i);
            accResMap.put(vec.get(1), vec.get(0));
            reasonBox.addItem(vec.get(1));
        }

        AccReasonPanel.add(txtReason);
        AccReasonPanel.add(reasonBox);

        JPanel DatePanel = new JPanel();
        JLabel txtDate = new JLabel("Дата ДТП: ");
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        DateFormatter df = new DateFormatter(format);
        JFormattedTextField DateField = new JFormattedTextField(df);
        DateField.setColumns(10);
        DatePanel.add(txtDate);
        DatePanel.add(DateField);

        JPanel PlacePanel = new JPanel();
        JLabel txtPlace = new JLabel("Место: ");
        JTextField PlaceField = new JTextField(30);
        PlacePanel.add(txtPlace);
        PlacePanel.add(PlaceField);

        JPanel DescrPanel = new JPanel();
        JLabel txtDescr = new JLabel("Описание: ");
        JTextField DescrField = new JTextField(30);
        DescrPanel.add(txtDescr);
        DescrPanel.add(DescrField);

        JPanel RoadPanel = new JPanel();
        JLabel txtRoad = new JLabel("Состояние дороги: ");
        JTextField RoadField = new JTextField(30);
        RoadPanel.add(txtRoad);
        RoadPanel.add(RoadField);

        JPanel VictimPanel = new JPanel();
        JLabel txtVictim = new JLabel("Число пострадавших: ");
        //  JTextField PowerField = new JTextField(10);
        JFormattedTextField VictimField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        VictimField.setColumns(4);
        VictimPanel.add(txtVictim);
        VictimPanel.add(VictimField);

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
                int acctype_id = Integer.parseInt(accTypeMap.get(typeBox.getSelectedItem()));
                int accReason_ID  = Integer.parseInt(accResMap.get(reasonBox.getSelectedItem()));
                String accDate = DateField.getText();
                String place = PlaceField.getText();
                String description = DescrField.getText();
                String road = RoadField.getText();
                String victims = VictimField.getText();
                List<String> accident = new LinkedList<>();

                accident.add("INSERT INTO RoadAccidents(acctype_ID, accReason_ID, accDate, place, description, roadCondition, numOfVictims) VALUES('" + acctype_id + "', '" + accReason_ID +  "',TO_DATE ('" + accDate + "', 'dd.mm.yyyy'),'" + place + "', '" + description + "', '" + road + "', '" + victims +  "')");
                conn.insert(accident);
                window.setVisible(false);
                if(role == "admin"){
                    AccidentsWindow accidentsWindow = new AccidentsWindow(conn, role);
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
                AccidentsWindow accidentsWindow = new AccidentsWindow(conn, role);
            } else if (role == "staff"){
                try {
                    MenuWindow menuWindow = new MenuWindow(conn, role);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });
        createPanel.add(AccTypePanel);
        createPanel.add(AccReasonPanel);
        createPanel.add(DatePanel);
        createPanel.add(PlacePanel);
        createPanel.add(DescrPanel);
        createPanel.add(RoadPanel);
        createPanel.add(VictimPanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
