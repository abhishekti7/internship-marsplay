package internship.marsplay;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("numFound")
    private Integer numFound;

    @SerializedName("start")
    private Integer start;

    @SerializedName("maxScore")
    private Double maxScore;

    @SerializedName("docs")
    private List<Journal> journal;

    public Response(Integer numFound, Integer start, Double maxScore, List<Journal> journal) {
        this.numFound = numFound;
        this.start = start;
        this.maxScore = maxScore;
        this.journal = journal;
    }

    public Integer getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public List<Journal> getJournal() {
        return journal;
    }

    public void setJournal(List<Journal> journal) {
        this.journal = journal;
    }
}
