package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SearchNumber extends JFrame{
    private Map<String, String> vehiclenum = new HashMap<>();
    public SearchNumber(MyConnection conn, String role){
        JFrame window = new JFrame("Поиск по номеру");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 200);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));

        JPanel seriesPanel = new JPanel();
        JLabel txtSeries = new JLabel("Гос. номер: ");
        JComboBox seriesBox = new JComboBox();
        Vector resultVector = new Vector();
        resultVector = conn.select("SELECT num_ID, series ||' '|| num from FreeNumbers", 2);

        for(int i =0; i< resultVector.size(); i++){
            Vector<String> vec = (Vector<String>) resultVector.get(i);
            vehiclenum.put(vec.get(1), vec.get(0));
            seriesBox.addItem(vec.get(1));
        }
        pack();
        seriesPanel.add(txtSeries);
        seriesPanel.add(seriesBox);

        JButton searchButton = new JButton("Поиск");
        searchButton.setBounds(100, 150, 50, 50);
        JLabel txtError = new JLabel("Данные введены неверно!") ;
        txtError.setVisible(false);
        searchButton.addActionListener(e ->{
                window.setVisible(false);
               SearchNumResWindow searchNumResWindow= new SearchNumResWindow(conn, role,vehiclenum.get(seriesBox.getSelectedItem()) );
        });

        loginPanel.add(seriesPanel);
        loginPanel.add(txtError);
        loginPanel.add(searchButton);
        window.add(loginPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}