package GUI;

import DAO.MyConnection;
import GUI.Creation.CreateOrganizationsWindow;
import GUI.Creation.CreateOwnersWindow;
import GUI.Tables.OwnersWindow;

import javax.swing.*;

public class OwnerTypeSelect extends JFrame {
    public OwnerTypeSelect(MyConnection conn, String role)  {
        JFrame window = new JFrame("Выбетире тип владельца");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(300,300);
        window.setLayout(null);
        JButton indButton = new JButton("Физическое лицо");
        indButton.setBounds(50, 30, 200, 50);
        indButton.addActionListener(e -> {
            CreateOwnersWindow createOwnersWindow = new CreateOwnersWindow(conn,role);
            window.setVisible(false);
        });
        JButton orgButton = new JButton("Юридическое лицо");
        orgButton.setBounds(50, 100, 200, 50);
        orgButton.addActionListener(e-> {
            CreateOrganizationsWindow createOrganizationsWindow = new CreateOrganizationsWindow(conn, role);
            window.setVisible(false);
        });
        JButton goBackButton = new JButton("Назад");
        goBackButton.setBounds(50, 170, 200, 50);
        goBackButton.addActionListener(e-> {
            OwnersWindow ownersWindow = new OwnersWindow(conn, role);
            window.setVisible(false);
        });

        window.add(indButton);
        window.add(orgButton);
        window.add(goBackButton);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
