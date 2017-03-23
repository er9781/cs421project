package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Control;
import main.DbConnection;


/**
 * UserInteraction which lists the comments on pages belonging to this owner
 * @author Mario
 *
 */
public class ViewComments extends UserInteraction {
    private int ownerId;

    public ViewComments(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getDisplayTitle() {
        return "View Comments on your Pages";
    }

    @Override
    public Context execute(Scanner in, DbConnection con) {
        //get the comments on this user's pages
        String sql = "SELECT TEXT, PAGE_ID, OWNER_ID FROM COMMENT WHERE PAGE_ID IN (SELECT PAGE_ID FROM PAGE WHERE OWNER_ID = " + this.ownerId + ")";
        ResultSet res = con.executeQuery(sql);
        ArrayList<String> Texts = new ArrayList<String>(10);
        ArrayList<Integer> PageIds = new ArrayList<Integer>(10);
        ArrayList<Integer> OwnerIds = new ArrayList<Integer>(10);
        try {
            while(res.next()){
                Texts.add(res.getString(1));
                PageIds.add(res.getInt(2));
                OwnerIds.add(res.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserInteraction[] options = new UserInteraction[Texts.size() + 1];
        for(int i = 0; i < Texts.size(); i++){
            options[i] = new DeleteComment(PageIds.get(i), Texts.get(i), OwnerIds.get(i), ownerId);
        }
        options[PageIds.size()] = Control.getReturnToMainMenuOption();
        Context pagesList = new Context(in, con, options, "Select a comment to delete.");

        return pagesList;

    }

    @Override
    public String getDescription() {
        return "This will show all the given user's page comments";
    }
}