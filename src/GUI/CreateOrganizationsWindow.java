package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class CreateOrganizationsWindow extends JFrame {
    public CreateOrganizationsWindow(MyConnection conn, String role) {
        JFrame window = new JFrame("Добавление записи");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(400, 300);
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));

        JPanel NamePanel = new JPanel();
        JLabel txtName = new JLabel("Наименование: ");
        JTextField NameField = new JTextField(30);
        NamePanel.add(txtName);
        NamePanel.add(NameField);

        JPanel OwnPanel = new JPanel();
        JLabel txtOwn = new JLabel("ФИО руководителя: ");
        JTextField OwnField = new JTextField(30);
        OwnPanel.add(txtOwn);
        OwnPanel.add(OwnField);

        JPanel regionPanel = new JPanel();
        JLabel txtregion = new JLabel("Регион: ");
        JTextField regionField = new JTextField(20);
        regionPanel.add(txtregion);
        regionPanel.add(regionField);

        JPanel BtnPanel = new JPanel();
        JButton createButton = new JButton("Создать");
        createButton.setBounds(100, 150, 50, 50);
        JLabel txtError = new JLabel("Данные введены неверно!");
        txtError.setVisible(false);
        JButton goBackButton = new JButton("Назад");
        goBackButton.setBounds(50, 150, 50, 50);
        BtnPanel.add(createButton);
        BtnPanel.add(goBackButton);
        createButton.addActionListener(e ->{
            try {
                String name = NameField.getText();
                String own = OwnField.getText();
                String region = regionField.getText();
                List<String> owner = new LinkedList<>();
                owner.add("INSERT INTO Owners(ownType_ID, name) VALUES('" + 2 + "', '" + name  +  "')");
                conn.insert(owner);
                List<String> ind_owner = new LinkedList<>();
                ind_owner.add("INSERT INTO Organizations(name, region, owner) VALUES('"  + name + "', '" + region + "', '" + own  + "')");
                conn.insert(ind_owner);
                window.setVisible(false);
                OwnersWindow ownersWindow = new OwnersWindow(conn, role);
            }
            catch (Exception exep){
                exep.printStackTrace();
                txtError.setVisible(true);
            }
        });

        goBackButton.addActionListener((e)->{
            window.setVisible(false);
            OwnersWindow ownersWindow = new OwnersWindow(conn, role);

        });
        createPanel.add(NamePanel);
        createPanel.add(OwnPanel);
        createPanel.add(regionPanel);
        createPanel.add(BtnPanel);
        createPanel.add(txtError);
        window.add(createPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
