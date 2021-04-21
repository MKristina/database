package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class OwnersWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public OwnersWindow(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
            columnNames = new Vector();
            columnNames.add("ID владельца");
            columnNames.add("Фамилия");
            columnNames.add("Имя");
            columnNames.add("Отчество");
            columnNames.add("Адрес");
            columnNames.add("Тип владельца");
            strings = new Vector();
            String select = "SELECT owner_ID, LastName, FirstName, patronymic, address, OwnerTypes.name FROM Owners INNER JOIN OwnerTypes USING(ownType_ID)";
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
                new TablesView(conn, "Owners", columnNames, strings, role);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }
}
