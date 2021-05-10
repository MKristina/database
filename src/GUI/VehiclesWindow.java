package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class VehiclesWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;

    public VehiclesWindow(MyConnection conn, String role){

        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){

            columnNames = new Vector();
            columnNames.add("ID ТС");
            columnNames.add("Тип ТС");
            columnNames.add("Бренд");
            columnNames.add("Модель");
            columnNames.add("Год выпуска");
            columnNames.add("Номер двигателя");
            columnNames.add("Объем двигателя");
            columnNames.add("Мощность двигателя");
            columnNames.add("Номер шасси");
            columnNames.add("Номер кузова");
            columnNames.add("Цвет");
            columnNames.add("Тип кузова");
            strings = new Vector();
            String select = "SELECT vehicle_ID, VehicleTypes.name, brand, model, to_char(dateOfIssue, 'yyyy'),engineNumber, engineVolume, enginePower,chassisNumber, bodyNumber, color, bodyType FROM Vehicles INNER JOIN VehicleTypes USING(type_ID)";
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
                new TablesView(conn, "Vehicles", columnNames, strings, role);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }
}
