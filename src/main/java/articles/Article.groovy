package articles

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.LocalDate;

@Table
public class Article {
	@PrimaryKey
	private final int id;

	private String title;
	private String description;
	private String author;
	private List<String> tags;
	private String createdAt = "";
	private String updatedAt = "";

	public Article(int id, String title, String description,
			  	String author, List<String> tags, String createdAt, 
			  	String updatedAt) {
	    this.id = id;
	    this.title = title;
	    this.description = description;
	    this.author = author;
	    this.tags = tags;
	    this.createdAt = createdAt;
	    this.updatedAt = updatedAt;
	}

	  public int getId() {
		  return id;
	  }

	  public String getTitle() {
		  return title;
	  }
	  
	  public String getDescription() {
		  return description;
	  }
	  
	  public List<String> getTags() {
		  return this.tags;
	  }
	  
	  public String getAuthor() {
		  return this.author
	  }
	  
	  public String setTitle(String title) {
		  this.title = title;
		  return title;
	  }
	  
	  public String setDescription(String description) {
		  this.description = description;
		  return description;
	  }
	  
	  public String setAuthor(String author) {
		  this.author = author;
		  return author;
	  }
	  public String updateArticle() {
		  this.updatedAt = LocalDate.now().toString();
	  }
	  
	  public List<String> addTags(List<String> tags) {
		  tags.each { 
			  if (!this.tags.contains(it)) {
				  this.tags << it;
			  }
		  }
		  
	  }
}
