package GUI.Requests;

import DAO.MyConnection;
import GUI.Creation.CreateTheftWindow;
import GUI.MenuWindow;
import GUI.UpdateTheftWindow;

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

public class TheftDateStatistics extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    JButton goBack;
    JButton createNew;
    JButton deleteNew;
    JButton updateButton;
    JButton returnVehicle;
    private JTable table;
    DefaultTableModel model;
    JTextField resultCountField;
    JTextField percentField;
    public TheftDateStatistics(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
        JFrame window = new JFrame("Статистика угонов");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(600, 400);

        JPanel DatePanel = new JPanel();
        JLabel datePeriodLabel = new JLabel("Введите период:");
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField FirstDateField = new JFormattedTextField(maskFormatter);
        FirstDateField.setColumns(10);
        JFormattedTextField SecondDateField = new JFormattedTextField(maskFormatter);
        SecondDateField.setColumns(10);
        DatePanel.add(datePeriodLabel);
        DatePanel.add(FirstDateField);
        DatePanel.add(SecondDateField);

        goBack = new JButton("Назад");
        createNew = new JButton("Добавить");
        deleteNew = new JButton("Удалить");
        returnVehicle = new JButton("Отметить возврат");
        updateButton = new JButton("Обновить");
        DatePanel.add(updateButton);
        columnNames = new Vector();
        columnNames.add("ID угона");
        columnNames.add("ТС");
        columnNames.add("Дата угона");
        columnNames.add("Дата возвращения");
        strings = new Vector();
        String select = "select theft_ID, brand || ' ' || model||' '||series || ' ' || num, to_char(dateOfTheft, 'dd.mm.yyyy'), to_char(dateOfReturn, 'dd.mm.yyyy') from vehicleTheft join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id)";
        createTable(conn, select);
        updateButton.addActionListener((e)->{
            strings.clear();
            String firstDate = FirstDateField.getText();
            String secondDate = SecondDateField.getText();
            String selectNew = "select theft_ID, brand || ' ' || model||' '||series || ' ' || num, to_char(dateOfTheft, 'dd.mm.yyyy'), to_char(dateOfReturn, 'dd.mm.yyyy')  from vehicleTheft join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id) where dateOfTheft>=TO_DATE('" + firstDate + "','dd.mm.yyyy') AND dateOfTheft<=TO_DATE('" + secondDate + "','dd.mm.yyyy')";
            createTable(conn, selectNew);
            model = new DefaultTableModel(strings, columnNames);
            table.setModel(model);
            ResultSet resultSet = null;
            String resultCount ="";
            String selectCount = "select COUNT(theft_ID) from vehicleTheft where dateOfTheft>=TO_DATE('" + firstDate + "','dd.mm.yyyy') AND dateOfTheft<=TO_DATE('" + secondDate + "','dd.mm.yyyy')";
            try {
                PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    resultCount = resultSet.getString(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resultCountField.setText(resultCount);

        });

        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        ResultSet resultSet = null;
        String resultCount ="";
        String selectCount = "select COUNT(theft_ID) from vehicleTheft";
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                resultCount = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ResultSet resultSet2 = null;
        String resultCount2 ="";
        String selectCount2 = "with t1 as(select COUNT(*) as SUM from VehicleTheft where dateOfReturn IS NOT NULL), t2 as (select COUNT(*) as SUM2 from VehicleTheft) select (SUM/SUM2)*100 from t1 ,t2 ";
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount2);
            resultSet2 = preparedStatement.executeQuery();
            while(resultSet2.next()){
                resultCount2 = resultSet2.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        JPanel resultPanel = new JPanel();
        JLabel resultCountLabel = new JLabel("Общее количество");
        resultCountField = new JTextField();
        resultCountField.setColumns(5);
        resultCountField.setEditable(false);
        resultCountField.setText(resultCount);
        resultPanel.add(resultCountLabel);
        resultPanel.add(resultCountField);

        JLabel percentLabel = new JLabel("Процент найденных ТС:");
        percentField = new JTextField();
        percentField.setColumns(5);
        percentField.setEditable(false);
        percentField.setText(resultCount2+"%");
        resultPanel.add(percentLabel);
        resultPanel.add(percentField);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        goBackPanel.add(createNew);
        goBackPanel.add(deleteNew);
        goBackPanel.add(returnVehicle);
        mainPanel.add(DatePanel, BorderLayout.NORTH);
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
        createNew.addActionListener((e)->{
            setVisible(false);
                try {
                    new CreateTheftWindow(conn, role);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
        });
        returnVehicle.addActionListener((e)-> {
            int selectedRow = table.getSelectedRow();
            int theft_id = 0;
            if(selectedRow != -1){
                theft_id = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
                setVisible(false);
                new UpdateTheftWindow(conn, theft_id, role);
            } else{
                JOptionPane.showMessageDialog(mainPanel, "Запись не выбрана!");
            }
        });
        deleteNew.addActionListener((e)->{
            int selectedRow = table.getSelectedRow();
            String del = "";
            if(selectedRow != -1){
                int del_ID = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
                del= "DELETE FROM VehicleTheft WHERE theft_ID = " + del_ID;
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = conn.conn.prepareStatement(del);
                    preparedStatement.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                model.removeRow(selectedRow);
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

