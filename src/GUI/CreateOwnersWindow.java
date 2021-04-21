package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CreateOwnersWindow extends JFrame {
    public CreateOwnersWindow(MyConnection conn, String role) {
        JFrame window = new JFrame("Добавление записи");
     //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel LastNamePanel = new JPanel();
        JLabel txtLastName = new JLabel("Фамилия: ");
        JTextField LastNameField = new JTextField(20);
        LastNamePanel.add(txtLastName);
        LastNamePanel.add(LastNameField);

        JPanel FirstNamePanel = new JPanel();
        JLabel txtFirstName = new JLabel("Имя: ");
        JTextField FirstNameField = new JTextField(20);
        FirstNamePanel.add(txtFirstName);
        FirstNamePanel.add(FirstNameField);

        JPanel patronymicPanel = new JPanel();
        JLabel txtpatronymic = new JLabel("Отчество: ");
        JTextField patronymicField = new JTextField(20);
        patronymicPanel.add(txtpatronymic);
        patronymicPanel.add(patronymicField);

        JPanel AddressPanel = new JPanel();
        JLabel txtAddressName = new JLabel("Адрес: ");
        JTextField AddressField = new JTextField(20);
        AddressPanel.add(txtAddressName);
        AddressPanel.add(AddressField);

        JPanel TypePanel = new JPanel();
        JLabel txtTypeName = new JLabel("Тип владельца: ");
        String[] items = {"физическое лицо", "юридическое лицо" };
        JComboBox typeBox = new JComboBox(items);
        TypePanel.add(txtTypeName);
        TypePanel.add(typeBox);
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
                String lastName = LastNameField.getText();
                String firstName = FirstNameField.getText();
                String patronymic = patronymicField.getText();
                String address = AddressField.getText();
                int ownType_ID;
                int ownType = typeBox.getSelectedIndex();
                if (ownType == 0 ) ownType_ID=1;
                else ownType_ID=2;
                System.out.println(ownType_ID);
                List<String> owner = new LinkedList<>();
                owner.add("INSERT INTO Owners(ownType_ID, lastName, firstName, patronymic,address) VALUES('" + ownType_ID + "', '" + lastName + "', '" + firstName + "', '" + patronymic + "', '" + address + "')");
                conn.insert(owner);
                window.setVisible(false);
                OwnersWindow ownersWindow = new OwnersWindow(conn, role);
            }
            catch (Exception exep){
                exep.printStackTrace();
                txtError.setVisible(true);
            }
        });

        goBackButton.addActionListener((e)->{
            setVisible(false);
                OwnersWindow ownersWindow = new OwnersWindow(conn, role);

        });
        createPanel.add(LastNamePanel);
        createPanel.add(FirstNamePanel);
        createPanel.add(patronymicPanel);
        createPanel.add(AddressPanel);
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
