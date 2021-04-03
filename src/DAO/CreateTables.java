package DAO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateTables {

    private MyConnection connection;
    private List<String> tablesName;

    public CreateTables(MyConnection connection)
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

    private String writeScriptFromFile(String relativePath) {
        InputStreamReader is = null;
        relativePath = "resources/" + relativePath;
        System.out.printf( relativePath + "\n");
        is = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(relativePath)));

            BufferedReader reader = new BufferedReader(is);
            return reader.lines().collect(Collectors.joining());
    }

    private List<String> deleteTables() {
        List<String> autoIncrements = new LinkedList<>();
        for(String name : tablesName)
        {
            ((LinkedList<String>) autoIncrements).addFirst(writeScriptFromFile("drop/" + name));
        }
        return autoIncrements;
    }
    private List<String> addAutoincrement() {
        List<String> autoIncrements = new LinkedList<>();
        for(String name : tablesName)
        {
            autoIncrements.add(writeScriptFromFile("triggers/" + name));
        }
        return autoIncrements;
    }

    private List<String> getSequences() {
        List<String> sequences = new LinkedList<>();
        for(String name: tablesName) {
            sequences.add(writeScriptFromFile("sequences/" + name));
        }
        return sequences;
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
    private List<String> getSequencesDrops() {
        List<String> autoIncrements = new LinkedList<>();
        for(String tableName: tablesName) {
            autoIncrements.add(writeScriptFromFile("dropSequences/" + tableName));
        }
        return autoIncrements;
    }
    public void create() {
        List<String> createList = new LinkedList<>();
        for(String name : tablesName)
        {
            createList.add(writeScriptFromFile("create/" + name));
        }
    /*    try {
            connection.executeQuery(writeScriptFromFile("createIfNotExists.sql"));
        }
        catch (SQLSyntaxErrorException e) {
            e.printStackTrace();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

     */
        // execForAll(deleteSequences());
        execForAll(deleteTables());
        execForAll(createList);
        execForAll(getSequencesDrops());
        execForAll(getSequences());
        execForAll(addAutoincrement());
    }
}