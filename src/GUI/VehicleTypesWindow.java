package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class VehicleTypesWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public VehicleTypesWindow(MyConnection conn){
        addActionListeners(conn);

    }
    private void addActionListeners(MyConnection conn){

            columnNames = new Vector();
            columnNames.add("ID типа");
            columnNames.add("Название");
            strings = new Vector();
            String select = "SELECT * FROM VehicleTypes";
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
               new TablesView(conn, "VehicleTypes", columnNames, strings);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }
}
