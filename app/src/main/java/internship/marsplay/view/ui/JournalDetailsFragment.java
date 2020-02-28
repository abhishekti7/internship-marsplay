package internship.marsplay.view.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import internship.marsplay.R;


public class JournalDetailsFragment extends Fragment {

    private String title;
    private String type;
    private String _abstract;
    private String authors;
    private String date;
    private String eissn;
    private String score;

    private TextView tv_title;
    private TextView tv_authors;
    private TextView tv_abstract;
    private TextView tv_date;
    private TextView tv_eissn;
    private TextView tv_score;
    private TextView tv_type;

    public JournalDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal_details, container, false);

        title = getArguments().getString("title");
        type = getArguments().getString("type");
        authors = getArguments().getString("authors");
        _abstract = getArguments().getString("abstract");
        date = getArguments().getString("date");
        eissn = getArguments().getString("eissn");
        score = getArguments().getString("score");

        tv_title = view.findViewById(R.id.title);
        tv_authors = view.findViewById(R.id.authors);
        tv_date= view.findViewById(R.id.published_on);
        tv_eissn = view.findViewById(R.id.eissn);
        tv_score = view.findViewById(R.id.score);
        tv_type = view.findViewById(R.id.article_type);
        tv_abstract = view.findViewById(R.id.full_abtract);

        tv_title.setText(title);
        tv_authors.setText(authors);
        tv_date.setText(date);
        tv_type.setText(type);
        tv_eissn.setText(eissn);
        tv_score.setText(score);
        tv_abstract.setText(_abstract);

        tv_abstract.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

}
