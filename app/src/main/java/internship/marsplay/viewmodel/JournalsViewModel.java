package internship.marsplay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import internship.marsplay.service.model.Journal;
import internship.marsplay.service.networking.JournalRepository;


public class JournalsViewModel extends ViewModel {

    private MutableLiveData<List<Journal>> mutableLiveData;
    private JournalRepository journalRepository;

    public void init(){
        if(mutableLiveData!=null){
            return;
        }
        journalRepository = JournalRepository.getInstance();
        mutableLiveData = journalRepository.getData();
    }

    public LiveData<List<Journal>> getJournalRepository(){
        return mutableLiveData;
    }
}
