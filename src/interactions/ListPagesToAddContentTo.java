package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Control;
import main.DbConnection;


/**
 * UserInteraction which lists the available pages you can add content to.
 * @author Mario
 *
 */
public class ListPagesToAddContentTo extends UserInteraction {
    private int ownerId;

    public ListPagesToAddContentTo(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getDisplayTitle() {
        return "Add Content To Page";
    }

    @Override
    public Context execute(Scanner in, DbConnection con) {
        //get the pages that belong to this owner
        String sql = "SELECT PAGE_ID, TITLE FROM PAGE WHERE OWNER_ID = " + this.ownerId;
        ResultSet res = con.executeQuery(sql);
        ArrayList<Integer> PageIds = new ArrayList<Integer>(10);
        ArrayList<String> Titles = new ArrayList<String>(10);
        try {
            while(res.next()){
                PageIds.add(res.getInt(1));
                Titles.add(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserInteraction[] options = new UserInteraction[PageIds.size() + 1];
        for(int i = 0; i < PageIds.size(); i++){
            options[i] = new AddContent(PageIds.get(i), Titles.get(i));
        }
        options[PageIds.size()] = Control.getReturnToMainMenuOption();
        Context pagesList = new Context(in, con, options, "Select a page to add content to.");

        return pagesList;

    }

    @Override
    public String getDescription() {
        return "This will show all the given user's pages that you can add content to.";
    }
}