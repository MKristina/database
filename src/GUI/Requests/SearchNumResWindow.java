package GUI.Requests;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class SearchNumResWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public SearchNumResWindow(MyConnection conn, String role, String id){
        addActionListeners(conn, role, id);
    }
    private void addActionListeners(MyConnection conn, String role,String id){
        columnNames = new Vector();
        columnNames.add("ID владельца");
        columnNames.add("Тип владельца");
        columnNames.add("Имя");

        strings = new Vector();
        String select = "SELECT owner_id, ot.name, o.name from owners o join ownerTypes ot using(ownType_id) join registration using(owner_id) join freenumbers using(num_id) where num_ID = " + Integer.parseInt(id) ;
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
            new TablesView(conn, "Информация о владельце", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
