package Database;

public class DatabaseCRUD {
    protected static DbAccess mydb;
    public DatabaseCRUD() {
        mydb=DbAccess.getInstance();
    }

}
