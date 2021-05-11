package GUI.Tables;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class AccidentReasonsWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public AccidentReasonsWindow(MyConnection conn, String role){
        addActionListeners(conn, role);

    }
    private void addActionListeners(MyConnection conn, String role){

        columnNames = new Vector();
        columnNames.add("ID причины");
        columnNames.add("Описание");
        strings = new Vector();
        String select = "SELECT * FROM AccidentReasons";
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
            new TablesView(conn, "AccidentReasons", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
