package internship.marsplay;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String JSONURL = "https://api.plos.org";

    @GET("/search?q=title:DNA")
    Call<RetrofitDataModel> getString();

}
