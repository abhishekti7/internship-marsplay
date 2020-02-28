package internship.marsplay;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Journal {

    @SerializedName("id")
    private String id;

    @SerializedName("journal")
    private String journal;

    @SerializedName("eissn")
    private String eissn;

    @SerializedName("publication_date")
    private String publication_date;

    @SerializedName("article_type")
    private String article_type;

    @SerializedName("author_display")
    private List<String> authors;

    @SerializedName("abstract")
    private List<String> _abstract;

    @SerializedName("title_display")
    private String title;

    @SerializedName("score")
    private Double score;

    public Journal(String id, String journal, String eissn, String publication_date, String article_type, List<String> authors, List<String> _abstract, String title, double score) {
        this.id = id;
        this.journal = journal;
        this.eissn = eissn;
        this.publication_date = publication_date;
        this.article_type = article_type;
        this.authors = authors;
        this._abstract = _abstract;
        this.title = title;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getEissn() {
        return eissn;
    }

    public void setEissn(String eissn) {
        this.eissn = eissn;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> get_abstract() {
        return _abstract;
    }

    public void set_abstract(List<String> _abstract) {
        this._abstract = _abstract;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
