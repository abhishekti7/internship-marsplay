package internship.marsplay.service.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import internship.marsplay.service.model.Journal;
import internship.marsplay.service.model.RetrofitDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalRepository {

    private static JournalRepository journalRepository;

    public static JournalRepository getInstance(){
        if(journalRepository==null){
            journalRepository = new JournalRepository();
        }
        return journalRepository;
    }

    private ApiInterface api;

    public JournalRepository() {
        api = internship.marsplay.service.networking.RetrofitService.createService(ApiInterface.class);
    }

    public MutableLiveData<List<Journal>> getData(){
        final MutableLiveData<List<Journal>> data = new MutableLiveData<>();

        api.getString().enqueue(new Callback<RetrofitDataModel>() {
            @Override
            public void onResponse(Call<RetrofitDataModel> call, Response<RetrofitDataModel> response) {
                Log.i("MSG", response.body().toString());
                if(response.isSuccessful()){
                    if (response.body()!=null){
                        RetrofitDataModel resp = response.body();
                        internship.marsplay.service.model.Response result = resp.getResponse();
                        List<Journal> journals = result.getJournal();
                        data.setValue(journals);
                    }else{
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<RetrofitDataModel> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
