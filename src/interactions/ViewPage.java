package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import main.DbConnection;

/**
 * UserInteraction to view a general page and its content.
 * @author Simon
 *
 */
public class ViewPage extends UserInteraction {

	private int pageId;
	private String pageTitle;
	
	public ViewPage(int pageId, String pageTitle){
		this.pageId= pageId;
		this.pageTitle = pageTitle;
	}
	
	@Override
	public String getDisplayTitle() {
		return "View page: " + this.pageTitle;
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		System.out.println("Connect to DB and get page content for \"" + this.pageTitle + "\" and present it.\n");
		String sql = "SELECT index, content FROM content WHERE page_id=" + this.pageId;
		ResultSet res = con.executeQuery(sql);

		ArrayList<Paragraph> paragraphs = new ArrayList<Paragraph>(10);
		try {
			while(res.next()){
				
				paragraphs.add(new Paragraph(res.getInt(1), res.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(paragraphs);
		
		Paragraph p;
		for(int i = 0; i < paragraphs.size(); i++){
			p = paragraphs.get(i);
			System.out.println(p.content);
			System.out.println();
		}
		
		
		
		return null;
	}

	@Override
	public String getDescription() {
		return "This will display the contents of the page " + this.pageTitle;
	}
	
	
	private class Paragraph implements Comparable<Paragraph>{

		public int index;
		public String content;
		
		public Paragraph(int index, String content){
			this.index = index;
			this.content = content;
		}
		
		@Override
		public int compareTo(Paragraph p2) {
			return this.index - p2.index;
		}
		
	}

}
