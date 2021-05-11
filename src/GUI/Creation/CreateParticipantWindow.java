package GUI.Creation;

import DAO.MyConnection;
import GUI.Tables.AccidentParticipantsWindow;
import GUI.Tables.AccidentsWindow;

import javax.swing.*;
import java.util.*;

public class CreateParticipantWindow extends JFrame {
    public CreateParticipantWindow(MyConnection conn, String role) {
        Map<String, String> accidentMap = new HashMap<>();
        Map<String, String> regMap = new HashMap<>();
        JFrame window = new JFrame("Добавление записи");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(480, 250);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel accidentPanel = new JPanel();
        JLabel txtaccident = new JLabel("ДТП: ");
        JComboBox accidentBox = new JComboBox();
        Vector resultVector = new Vector();
        resultVector = conn.select("select acc_id, acc_id||': '||accDate||' '||name||' '||place from RoadAccidents JOIN AccidentTypes USING(accType_ID)", 2);
        for(int i =0; i< resultVector.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector.get(i);
            accidentMap.put(vec.get(1), vec.get(0));
            accidentBox.addItem(vec.get(1));
        }
        accidentPanel.add(txtaccident);
        accidentPanel.add(accidentBox);

        JPanel partPanel = new JPanel();
        JLabel txtpart = new JLabel("Участник: ");
        JComboBox partBox = new JComboBox();
        Vector resultVector2 = new Vector();
        resultVector2 = conn.select("SELECT reg_id, lastname||' ' ||firstname||' '|| brand||' ' ||model||' '||dateOfIssue from Registration join Vehicles USING(vehicle_ID) join Owners USING(owner_ID)", 2);
        for(int i =0; i< resultVector2.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector2.get(i);
            regMap.put(vec.get(1), vec.get(0));
            partBox.addItem(vec.get(1));
        }
        partPanel.add(txtpart);
        partPanel.add(partBox);

        JPanel DamagePanel = new JPanel();
        JLabel txtDamage = new JLabel("Сумма ущерба: ");
        JTextField DamageField = new JTextField(15);
        DamagePanel.add(txtDamage);
        DamagePanel.add(DamageField);

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
                int acc_id = Integer.parseInt(accidentMap.get(accidentBox.getSelectedItem()));

                int reg_id = Integer.parseInt(regMap.get(partBox.getSelectedItem()));
                int damage = Integer.parseInt(DamageField.getText());
                List<String> reg = new LinkedList<>();

                reg.add("INSERT INTO AccidentParticipants(acc_ID, reg_ID, damage) VALUES(" + acc_id + ", " + reg_id + ", " + damage + ")");
                conn.insert(reg);
                window.setVisible(false);
                    AccidentParticipantsWindow accidentParticipantsWindow = new AccidentParticipantsWindow(conn,acc_id, role);
            }
            catch (Exception exep){
                exep.printStackTrace();
                txtError.setVisible(true);
            }
        });

        goBackButton.addActionListener((e)->{
            window.setVisible(false);
            AccidentsWindow accidentsWindow = new AccidentsWindow(conn, role);

        });
        createPanel.add(accidentPanel);
        createPanel.add(partPanel);
        createPanel.add(DamagePanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
