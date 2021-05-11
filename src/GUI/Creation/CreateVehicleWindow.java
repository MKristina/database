package GUI.Creation;

import DAO.MyConnection;
import GUI.Tables.VehiclesWindow;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.*;

public class CreateVehicleWindow extends JFrame {
    public CreateVehicleWindow(MyConnection conn, String role) {
         Map<String, String> vehicletype = new HashMap<>();
        JFrame window = new JFrame("Добавление записи");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(500, 600);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel VehTypePanel = new JPanel();
        JLabel txtTypeName = new JLabel("Тип ТС: ");
        JComboBox typeBox = new JComboBox();
        Vector resultVector = new Vector();
        resultVector = conn.select("SELECT type_ID, name from VehicleTypes", 2);

        for(int i =0; i< resultVector.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector.get(i);
            vehicletype.put(vec.get(1), vec.get(0));
            typeBox.addItem(vec.get(1));
        }

        VehTypePanel.add(txtTypeName);
        VehTypePanel.add(typeBox);

        JPanel BrandPanel = new JPanel();
        JLabel txtBrand = new JLabel("Бренд: ");
        JTextField BrandField = new JTextField(15);
        BrandPanel.add(txtBrand);
        BrandPanel.add(BrandField);

        JPanel ModelPanel = new JPanel();
        JLabel txtModel = new JLabel("Модель: ");
        JTextField ModelField = new JTextField(15);
        BrandPanel.add(txtModel);
        BrandPanel.add(ModelField);

        JPanel DatePanel = new JPanel();
        JLabel txtDate = new JLabel("Год выпуска: ");
     //   JTextField DateField = new JTextField(10);
  //      JFormattedTextField DateField = new JFormattedTextField(NumberFormat.getInstance());
    //    DateField.setColumns(4);
        SpinnerNumberModel datemodel = new SpinnerNumberModel(2021, 1900, 2021, 1);
        JSpinner DateField = new JSpinner(datemodel);
        DatePanel.add(txtDate);
        DatePanel.add(DateField);

        JPanel VolumePanel = new JPanel();
        JLabel txtVolume = new JLabel("Объем двигателя: ");
     //   JTextField VolumeField = new JTextField(10);
        JFormattedTextField VolumeField = new JFormattedTextField(NumberFormat.getNumberInstance());
        VolumeField.setColumns(4);
        VolumePanel.add(txtVolume);
        VolumePanel.add(VolumeField);

        JPanel PowerPanel = new JPanel();
        JLabel txtPower = new JLabel("Мощность двигателя: ");
      //  JTextField PowerField = new JTextField(10);
        JFormattedTextField PowerField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        PowerField.setColumns(4);
        VolumePanel.add(txtPower);
        VolumePanel.add(PowerField);

        JPanel EnginePanel = new JPanel();
        JLabel txtEngine = new JLabel("Номер двигателя: ");
        JTextField EngineField = new JTextField(20);
        EnginePanel.add(txtEngine);
        EnginePanel.add(EngineField);

        JPanel chassisPanel = new JPanel();
        JLabel txtchassis = new JLabel("Номер шасси: ");
        JTextField chassisField = new JTextField(20);
        chassisPanel.add(txtchassis);
        chassisPanel.add(chassisField);

        JPanel BodyPanel = new JPanel();
        JLabel txtBody = new JLabel("Номер кузова: ");
        JTextField BodyField = new JTextField(20);
        BodyPanel.add(txtBody);
        BodyPanel.add(BodyField);

        JPanel ColorPanel = new JPanel();
        JLabel txtColor = new JLabel("Цвет: ");
        JTextField ColorField = new JTextField(20);
        ColorPanel.add(txtColor);
        ColorPanel.add(ColorField);

        JPanel TypePanel = new JPanel();
        JLabel txtType = new JLabel("Тип кузова: ");
        JTextField TypeField = new JTextField(20);
        TypePanel.add(txtType);
        TypePanel.add(TypeField);



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
                int type_id = Integer.parseInt(vehicletype.get(typeBox.getSelectedItem()));
                String brand = BrandField.getText();
                String model = ModelField.getText();
                String dateOfIssue = (String) DateField.getValue();
                float engineVolume = Float.parseFloat(VolumeField.getText());
                String engineNumber = EngineField.getText();
                String chassisNumber = chassisField.getText();
                String bodyNumber = BodyField.getText();
                String color = ColorField.getText();
                String bodyType = TypeField.getText();
                float enginePower = Float.parseFloat(PowerField.getText());
                System.out.println(type_id);
                List<String> vehicle = new LinkedList<>();

                vehicle.add("INSERT INTO Vehicles(type_ID, brand, model, dateOfIssue, engineVolume, engineNumber, chassisNumber, bodyNumber,color, bodyType, enginePower) VALUES('" + type_id + "', '" + brand + "', '" + model + "',TO_DATE ('" + dateOfIssue + "', 'yyyy'), '" + engineVolume + "', '" + engineNumber + "', '" + chassisNumber + "', '" + bodyNumber + "', '" + color + "', '" + bodyType + "', '" + enginePower + "')");
                conn.insert(vehicle);
                window.setVisible(false);
                VehiclesWindow vehiclesWindow = new VehiclesWindow(conn, role);
            }
            catch (Exception exep){
                exep.printStackTrace();
                txtError.setVisible(true);
            }
        });

        goBackButton.addActionListener((e)->{
            window.setVisible(false);
            VehiclesWindow vehiclesWindow = new VehiclesWindow(conn, role);


        });
        createPanel.add(VehTypePanel);
        createPanel.add(BrandPanel);
   //     createPanel.add(ModelPanel);
        createPanel.add(DatePanel);
        createPanel.add(VolumePanel);
   //     createPanel.add(PowerPanel);
        createPanel.add(EnginePanel);
        createPanel.add(chassisPanel);
        createPanel.add(BodyPanel);
        createPanel.add(ColorPanel);
        createPanel.add(TypePanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        //  createPanel.add(createButton);
        //  createPanel.add(goBackButton);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
