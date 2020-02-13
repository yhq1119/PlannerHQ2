package HQ.Planner.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class PlannerViewModel extends AndroidViewModel {

 static Bundle savedInstance;
 static PlannerViewModel plannerViewModel;

    public PlannerViewModel(@NonNull Application application, Bundle savedInstance) {
        super(application);
        this.savedInstance = savedInstance;
    }



    public Bundle getSavedInstance(){
        return savedInstance;
    }
}
