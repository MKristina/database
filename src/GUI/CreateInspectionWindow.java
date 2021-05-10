package GUI;

import DAO.MyConnection;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class CreateInspectionWindow extends JFrame {
    public CreateInspectionWindow(MyConnection conn, String role) throws ParseException {
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
        resultVector2 = conn.select("SELECT reg_id, brand||' ' ||model||' '||series||''||num from Registration join Vehicles Using(vehicle_id) join FreeNumbers using(num_id)", 2);
        for(int i =0; i< resultVector2.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector2.get(i);
            vehicleMap.put(vec.get(1), vec.get(0));
            vehicleBox.addItem(vec.get(1));
        }
        vehiclePanel.add(txtVehicle);
        vehiclePanel.add(vehicleBox);


        JPanel RecPanel = new JPanel();
        JCheckBox receiptBox = new JCheckBox("Квитанция об оплате");
        RecPanel.add(receiptBox);

        JPanel DatePanel = new JPanel();
        JLabel txtDate = new JLabel("Дата прохождения ТО: ");
        MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
        JFormattedTextField DateField = new JFormattedTextField(maskFormatter);
        DateField.setColumns(10);
        DatePanel.add(txtDate);
        DatePanel.add(DateField);

        JPanel PassedPanel = new JPanel();
        JCheckBox passedBox = new JCheckBox("ТО пройден");
        PassedPanel.add(passedBox);

        JPanel NextDatePanel = new JPanel();
        JLabel txtNextDate = new JLabel("Следующее прохождение ТО: ");
        MaskFormatter maskFormatter2 = new MaskFormatter("####");
        JFormattedTextField NextDateField = new JFormattedTextField(maskFormatter2);
        NextDateField.setColumns(5);
        NextDatePanel.add(txtNextDate);
        NextDatePanel.add(NextDateField);


        JPanel BtnPanel = new JPanel();

        JButton createButton = new JButton("Создать");
        createButton.setBounds(100, 150, 50, 50);

        JButton goBackButton = new JButton("Назад");
        createButton.setBounds(50, 150, 50, 50);
        BtnPanel.add(createButton);
        BtnPanel.add(goBackButton);
        createButton.addActionListener(e ->{
            try {
                int receipt = 0;
                int passed = 0;
                String nextYear = NextDateField.getText();
                int reg_id = Integer.parseInt(vehicleMap.get(vehicleBox.getSelectedItem()));
                String dateOfInsp = DateField.getText();
                if (passedBox.isSelected() && !receiptBox.isSelected()){
                    JOptionPane.showMessageDialog(createPanel, "Отсутствует квитанция об оплате");
                } else {
                    if (receiptBox.isSelected()) receipt = 1;
                    if (passedBox.isSelected()) passed = 1;
                    List<String> reg = new LinkedList<>();

                    reg.add("INSERT INTO Inspection(reg_ID, receipt, dateOfInsp, passed, nextYear) VALUES('" + reg_id + "', '" + receipt + "',TO_DATE ('" + dateOfInsp + "', 'dd.mm.yyyy'), '" + passed +"',TO_DATE ('" + nextYear + "', 'yyyy'))"  );
                    conn.insert(reg);

                    window.setVisible(false);
                    if(role == "admin"){
                        InspectionWindow inspectionWindow = new InspectionWindow(conn, role);
                    } else if (role == "staff"){
                        try {
                            MenuWindow menuWindow = new MenuWindow(conn, role);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

            }
            catch (Exception exep){
                exep.printStackTrace();

            }
        });

        goBackButton.addActionListener((e)->{
            window.setVisible(false);
            if(role == "admin"){
                InspectionWindow inspectionWindow = new InspectionWindow(conn, role);
            } else if (role == "staffGIBDD"){
                try {
                    MenuWindow menuWindow = new MenuWindow(conn, role);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        createPanel.add(vehiclePanel);
        createPanel.add(DatePanel);
        createPanel.add(RecPanel);
        createPanel.add(PassedPanel);
        createPanel.add(NextDatePanel);
        createPanel.add(BtnPanel);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
