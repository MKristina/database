import GUI.MainWindow;
import java.util.Locale;
import java.sql.SQLException;

public class Main {
    public static void main(String[] agrs) throws SQLException {
        Locale.setDefault(new Locale("ru"));
        MainWindow mainWindow = new MainWindow();
    }
}
