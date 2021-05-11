package GUI.Creation;

import DAO.MyConnection;
import GUI.MenuWindow;
import GUI.Tables.RegistrationView;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class CreateRegistrationWindow extends JFrame {
    public CreateRegistrationWindow(MyConnection conn, String role) throws ParseException {
        Map<String, String> vehicleMap = new HashMap<>();
        Map<String, String> ownerMap = new HashMap<>();
        Map<String, String> numMap = new HashMap<>();
        JFrame window = new JFrame("Добавление записи");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(500, 250);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel ownerPanel = new JPanel();
        JLabel txtOwner = new JLabel("Владелец: ");
        JComboBox ownerBox = new JComboBox();
        Vector resultVector = new Vector();
        resultVector = conn.select("select owner_id, o.name, ot.name from owners o join ownertypes ot using(ownType_id)", 2);
        for(int i =0; i< resultVector.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector.get(i);
            ownerMap.put(vec.get(1), vec.get(0));
            ownerBox.addItem(vec.get(1));
        }
        ownerPanel.add(txtOwner);
        ownerPanel.add(ownerBox);

        JPanel vehiclePanel = new JPanel();
        JLabel txtVehicle = new JLabel("Транспортное средство: ");
        JComboBox vehicleBox = new JComboBox();
        Vector resultVector2 = new Vector();
        resultVector2 = conn.select("SELECT vehicle_ID, brand||' ' ||model||' '||dateOfIssue from Vehicles", 2);
        for(int i =0; i< resultVector2.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector2.get(i);
            vehicleMap.put(vec.get(1), vec.get(0));
            vehicleBox.addItem(vec.get(1));
        }
        vehiclePanel.add(txtVehicle);
        vehiclePanel.add(vehicleBox);


        JPanel numPanel = new JPanel();
        JLabel txtNum = new JLabel("Гос. номер: ");
        JComboBox numBox = new JComboBox();
        Vector resultVector3 = new Vector();
        resultVector3 = conn.select("SELECT num_ID, series ||' '|| num from FreeNumbers where flag = 0", 2);

        for(int i =0; i< resultVector3.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector3.get(i);
            numMap.put(vec.get(1), vec.get(0));
            numBox.addItem(vec.get(1));
        }
        numPanel.add(txtNum);
        numPanel.add(numBox);


        JPanel DatePanel = new JPanel();
        JLabel txtDate = new JLabel("Дата регистрации: ");
        //DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        //DateFormatter df = new DateFormatter(format);
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
                int owner_id = Integer.parseInt(ownerMap.get(ownerBox.getSelectedItem()));
                int vehicle_id = Integer.parseInt(vehicleMap.get(vehicleBox.getSelectedItem()));
                int num_id = Integer.parseInt(numMap.get(numBox.getSelectedItem()));
                String dateOfIssue = DateField.getText();
                conn.executeQuery("BEGIN INSERT INTO Registration(num_ID, vehicle_ID, owner_ID, dateReg) VALUES('" + num_id + "', '" + owner_id + "', '" + vehicle_id + "',TO_DATE ('" + dateOfIssue + "', 'dd.mm.yyyy'));" + "UPDATE FreeNumbers SET flag = 1 WHERE num_ID = " + num_id + "; END;");
                window.setVisible(false);
                if(role == "admin"){
                    RegistrationView registrationView = new RegistrationView(conn, role);
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
                RegistrationView registrationView = new RegistrationView(conn, role);
            } else if (role == "staffGIBDD"){
                try {
                    MenuWindow menuWindow = new MenuWindow(conn, role);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        createPanel.add(ownerPanel);
        createPanel.add(vehiclePanel);
        createPanel.add(numPanel);
        createPanel.add(DatePanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
