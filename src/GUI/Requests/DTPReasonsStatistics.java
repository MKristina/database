package GUI.Requests;

import DAO.MyConnection;
import GUI.MenuWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

public class DTPReasonsStatistics extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    JButton goBack;
    JButton updateButton;
    private JTable table;
    DefaultTableModel model;
    public DTPReasonsStatistics(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
        JFrame window = new JFrame("Статистика причин ДТП");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(600, 400);

        goBack = new JButton("Назад");
        columnNames = new Vector();
        columnNames.add("Причина ДТП");
        columnNames.add("Количество");
        columnNames.add("Процент");
        strings = new Vector();
        String select = "SELECT accDescription, COUNT(acc_id), TRUNC(COUNT(acc_id)/(SELECT COUNT(*)from RoadAccidents)*100, 1) from RoadAccidents join AccidentReasons using (accReason_ID) group by accDescription";
        createTable(conn, select);
        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        window.add(mainPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        goBack.addActionListener((e)->{
            window.setVisible(false);
            try {
                new MenuWindow(conn, role);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void createTable(MyConnection conn, String selectNew) {
        ResultSet resultSetNew = null;
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectNew);
            resultSetNew = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaDataNew = null;
        try {
            resultSetMetaDataNew = resultSetNew.getMetaData();
            while (resultSetNew.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaDataNew.getColumnCount(); i++)
                    tmp.add(resultSetNew.getString(i));
                strings.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

