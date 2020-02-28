package internship.marsplay;

import com.google.gson.annotations.SerializedName;

public class RetrofitDataModel {

    @SerializedName("response")
    private Response response;

    public RetrofitDataModel(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
