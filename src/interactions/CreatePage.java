package interactions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import main.DbConnection;


/**
 * UserInteraction which creates a page for a user and prints out the resulting line in table.
 * @author Mario
 *
 */
public class CreatePage extends UserInteraction {
    private int isPublic, ownerId;
    private String title;

    public CreatePage(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getDisplayTitle() {
        return "Create Page";
    }

    @Override
    public Context execute(Scanner in, DbConnection con) {
        String input;
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Would you like this page to be public? Enter 1 if yes, 0 if no.");
            input = in.nextLine();
            isValid = Context.validateInteger(input);
            if (!isValid) {
                System.out.println("Your input was not of the correct format");
                continue;
            } else {
                isPublic = Integer.parseInt(input);
                if(isPublic != 0 && isPublic != 1) {
                    System.out.println("Your input must be either a 0 or a 1");
                    isValid = false;
                }
            }
        }

        System.out.println("Please enter a title for this page. ");
        title = in.nextLine();

        //Insert page with given credentials into Page table
        String sql = "INSERT INTO PAGE (TITLE, IS_PUBLIC, OWNER_ID) VALUES ('" + this.title + "', " + this.isPublic +
                ", " + this.ownerId + ")";
        //Get said page from table
        String sql2 = "SELECT * FROM PAGE WHERE TITLE = '" + this.title + "' AND IS_PUBLIC = " +
                this.isPublic + " AND OWNER_ID = " + this.ownerId;

        con.executeUpdate(sql);
        ResultSet rs = con.executeQuery(sql2);
        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println("Created page: ");
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
            System.out.println();
        } catch (SQLException e) {}

        return null;
    }

    @Override
    public String getDescription() {
        return "This will create a new page for a given user and get you the resulting table entry.";
    }
}
