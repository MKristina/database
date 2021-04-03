package DAO;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InsertData {

    private MyConnection connection;
    private List<String> tablesName;

    public InsertData(MyConnection connection)
    {
        this.connection = connection;
        tablesName = new LinkedList<>();
        tablesName.add("VehicleTypes");
        tablesName.add("Vehicles");
        tablesName.add("OwnerTypes");
        tablesName.add("Owners");
        tablesName.add("Organizations");
        tablesName.add("FreeNumbers");
        tablesName.add("Registration");
    }

    private List<String> writeScriptFromFile(String relativePath) {
        InputStreamReader is = null;
        relativePath = "resources/" + relativePath;
        System.out.printf( relativePath+ "\n");
        is = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(relativePath)));
        BufferedReader reader = new BufferedReader(is);
        List<String> queries = new LinkedList<>();
        Object[] lines = reader.lines().toArray();

        for(Object line: lines) {
            queries.add(line.toString());
        }
        return queries;


    }

    private void execForAll(List<String> queries) {
        for (String q : queries) {
            try {
                connection.executeQuery(q);
            }
            catch (java.sql.SQLIntegrityConstraintViolationException ignored) {
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void insert() {
        for(String tableName: tablesName) {
            List<String> list = writeScriptFromFile("insert/" + tableName);
            if(!list.isEmpty()) {
                execForAll(list);
            }
        }
    }

    public void insertVehicles(int type_ID, String brand, String model, String dateOfIssue, float engineVolume, String engineNumber, String chassisNumber, String bodyNumber, String color, String bodyType, float enginePower){
        List<String> vehicle = new LinkedList<>();
        vehicle.add("INSERT INTO Vehicles(type_ID, brand, model, dateOfIssue, engineVolume, engineNumber, chassisNumber, bodyNumber,color, bodyType, enginePower) VALUES('" + type_ID + "', '" + brand + "', '" + model + "', TO_DATE('" + dateOfIssue + "', 'yyyy'), " +"', '" + engineVolume + "', '"+ engineNumber + "', '"+ chassisNumber + "', '"+ bodyNumber + "', '"+ color + "', '"+ bodyType + "', '"+ enginePower +")");
        connection.insert(vehicle);
    }

}