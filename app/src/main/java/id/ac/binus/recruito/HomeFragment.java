package id.ac.binus.recruito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.adapter.ThreadAdapter;
import id.ac.binus.recruito.databinding.FragmentHomeBinding;
import id.ac.binus.recruito.models.JobThread;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ThreadAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View rootView= inflater.inflate(R.layout.fragment_home,container,false);
        RecyclerView recyclerview= rootView.findViewById(R.id.recycler_view_thread);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ThreadAdapter(getActivity(), getAllThread());
        adapter.notifyDataSetChanged();
        recyclerview.setAdapter(adapter);
        return rootView;

    }


    private ArrayList<JobThread> getAllThread(){

        // Contoh isi arraylist
        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        jobThreadArrayList.add(new JobThread(1, "Jojo", null, null, "Butuh Koki", null, "2020-02-14", "Jakarta Barat", null, 8, 3));
        jobThreadArrayList.add(new JobThread(2, "Lili", null, null, "Butuh Drummer", null, "2020-02-15", "Jakarta Utara", null, 4, 4));
        jobThreadArrayList.add(new JobThread(3, "Bibi", null, null, "Butuh Accountant", null, "2020-02-16", "Jakarta Timur", null, 2, 0));
        jobThreadArrayList.add(new JobThread(4, "Budi", null, null, "Butuh IT", null, "2020-02-17", "Jakarta Selatan", null, 3, 2));

        return jobThreadArrayList;
    }

}
