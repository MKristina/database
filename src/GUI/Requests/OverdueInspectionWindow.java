package GUI.Requests;

import DAO.MyConnection;
import GUI.MenuWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class OverdueInspectionWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    JButton goBack;
    private JTable table;
    DefaultTableModel model;
    public OverdueInspectionWindow(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
        JFrame window = new JFrame("ТС, не прошедшие ТО");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(600, 400);

        goBack = new JButton("Назад");

        columnNames = new Vector();
        columnNames.add("ID ТО");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Квитанция");
        columnNames.add("Дата прохождения");
        columnNames.add("Следующий ТО");

        strings = new Vector();
        String select = "select insp_id, brand || ' ' || model, series || ' ' || num,receipt, dateOfInsp, to_char(nextYear, 'yyyy')  from inspection join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id) where passed = 0";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaData = null;
        try {
            resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        resultSet = null;
        String resultCount ="";
        String selectCount = "SELECT COUNT(insp_id) from Inspection where passed = 0";
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                resultCount = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JPanel resultPanel = new JPanel();
        JLabel resultCountLabel = new JLabel("Общее количество");
        JTextField resultCountField = new JTextField();
        resultCountField.setColumns(5);
        resultCountField.setText(resultCount);
        resultPanel.add(resultCountLabel);
        resultPanel.add(resultCountField);

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
        mainPanel.add(resultPanel);
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
}
