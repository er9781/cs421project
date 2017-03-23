package interactions;

import java.util.Scanner;

import main.DbConnection;


/**
 * UserInteraction which deletes a chosen comment.
 * @author Mario
 *
 */
public class DeleteComment extends UserInteraction {
    private int pageId, ownerId, pageOwner;
    private String comment;

    public DeleteComment(int pageId, String comment, int ownerId, int pageOwner) {
        this.pageId = pageId;
        this.comment = comment;
        this.ownerId = ownerId;
        this.pageOwner = pageOwner;
    }

    @Override
    public String getDisplayTitle() {
        return "\"" + this.comment +"\" by User: " + this.ownerId +" on Page: " + pageId;
    }

    @Override
    public Context execute(Scanner in, DbConnection con) {
        //will delete this comment
        String sql = "DELETE FROM COMMENT WHERE PAGE_ID = " + pageId + " AND OWNER_ID = " + ownerId + " AND TEXT = '" + comment + "'";
        con.executeUpdate(sql);
        return new ViewComments(this.pageOwner).execute(in, con);
    }

    @Override
    public String getDescription() {
        return "This will delete a comment.";
    }
}
