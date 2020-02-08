package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
    private boolean isHistory;
    private ClickHandler handler;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;

    public HomeFragment(boolean isHistory) {
        this.isHistory = isHistory;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        View rootView= binding.getRoot();
        RecyclerView recyclerview= rootView.findViewById(R.id.recycler_view_thread);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        relativeLayout = rootView.findViewById(R.id.relative_layout_title);
        linearLayout = rootView.findViewById(R.id.linear_layout_header);

        handler = new ClickHandler(getActivity());
        binding.setClickHandler(handler);


        if (isHistory){
            adapter = new ThreadAdapter(getActivity(), getAllHistory());
            linearLayout.setVisibility(View.GONE);
        }
        else{
            adapter = new ThreadAdapter(getActivity(), getAllThread());
            relativeLayout.setVisibility(View.GONE);
        }
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



    private ArrayList<JobThread> getAllHistory(){

        // Contoh isi arraylist
        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        jobThreadArrayList.add(new JobThread(1, "Jojo", null, null, "Butuh Dosen", null, "2019-02-14", "Jakarta Barat", null, 8, 3));
        jobThreadArrayList.add(new JobThread(2, "Lili", null, null, "Product Manager", null, "2019-02-15", "Jakarta Utara", null, 4, 4));
        jobThreadArrayList.add(new JobThread(3, "Bibi", null, null, "Bos required", null, "2019-02-16", "Jakarta Timur", null, 2, 0));
        jobThreadArrayList.add(new JobThread(4, "Budi", null, null, "Help wanted", null, "2019-02-17", "Jakarta Selatan", null, 3, 2));

        return jobThreadArrayList;
    }

    public class ClickHandler{
        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void notifButtonClick(View view){
            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToWhichFragment", "notification");
            startActivity(intent);
        }

        public void filterButtonClick(View view){
            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToWhichFragment", "filter");
            startActivity(intent);
        }

    }

}
