package interactions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import main.DbConnection;


/**
 * UserInteraction which adds content to a given page.
 * @author Mario
 *
 */
public class AddContent extends UserInteraction {
    private int pageId, index;
    private String title, styling, type, content;

    public AddContent(int pageId, String title) {
        this.pageId = pageId;
        this.title = title;
    }

    @Override
    public String getDisplayTitle() {
        return "Add Content To Page " + this.pageId +", Titled: " + title;
    }

    @Override
    public Context execute(Scanner in, DbConnection con) {

        //will get the size of the table so as to find what the next Index will be
        String sql = "SELECT COUNT(*) FROM CONTENT";
        ResultSet rs = con.executeQuery(sql);

        try {
            rs.next();
            index = rs.getInt(1) + 1;
        } catch (SQLException e) {}

        String input;
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Select your Styling? Enter \"airy\" or \"compact\"");
            input = in.nextLine();
            isValid = Context.validate(input, "compact") || Context.validate(input, "airy");
            if (!isValid) {
                System.out.println("Your input was not of the correct format");
                continue;
            } else {
                styling = input;
            }
        }

        isValid = false;

        while (!isValid) {
            System.out.println("Select your type? Enter \"text\" or \"img\"");
            input = in.nextLine();
            isValid = Context.validate(input, "text") || Context.validate(input, "img");
            if (!isValid) {
                System.out.println("Your input was not of the correct format");
                continue;
            } else {
                type = input;
            }
        }

        System.out.println("Enter your Content.");
        content = in.nextLine();

        //add new row to content table
        String sql2 = "INSERT INTO CONTENT (PAGE_ID, STYLING, INDEX, TYPE, CONTENT) VALUES (" + this.pageId + ", '" + this.styling + "', " + this.index + ", '" + this.type + "', '" + this.content + "')";

        //get the row that was just added
        String sql3 = "SELECT * FROM CONTENT WHERE INDEX = " + this.index;

        con.executeUpdate(sql2);
        rs = con.executeQuery(sql3);
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
        return "This will add content to a given page.";
    }
}
