package com.dimar.map2saveme.viewModel;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.repository.Repository;


import java.util.List;

public class FindListViewModel extends ViewModel {

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(Repository.getDatabaseReference());

    private final MutableLiveData<Photo> selected = new MutableLiveData<>();

    @NonNull
    public LiveData<List<Photo>> getDataSnapshotLiveData() {
        return liveData;
    }

    public void select(Photo item) {
        selected.setValue(item);
    }

    public LiveData<Photo> getSelected() {
        return selected;
    }
}
