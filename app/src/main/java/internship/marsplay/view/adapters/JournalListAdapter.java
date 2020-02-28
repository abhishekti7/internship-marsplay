package internship.marsplay.view.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import internship.marsplay.R;
import internship.marsplay.service.model.Journal;
import internship.marsplay.view.ui.JournalDetailsFragment;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.ViewHolder> implements Filterable {

    private List<Journal> journalList;
    private List<Journal> tempjournalList;
    private Activity mActivity;

    public JournalListAdapter(List<Journal> journalList, Activity mActivity) {
        this.journalList = journalList;
        this.mActivity = mActivity;
        tempjournalList = new ArrayList<>(journalList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jounal_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.journal_layout.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.recycler_view_anim));
        String title = journalList.get(position).getTitle();
        List<String> author = journalList.get(position).getAuthors();
        String article_type = journalList.get(position).getArticle_type();
        double score = journalList.get(position).getScore();
        List<String> _abstract = journalList.get(position).get_abstract();
        String date = journalList.get(position).getPublication_date();
        String eissn = journalList.get(position).getEissn();
        holder.setData(title, author, article_type, score, _abstract,date, eissn);
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    @Override
    public Filter getFilter() {
        return filterJournalList;
    }

    private Filter filterJournalList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Journal> filteredList = new ArrayList<>();

            if(constraint==null || constraint.length()==0){
                filteredList.addAll(tempjournalList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Journal item : tempjournalList){
                    if(String.valueOf(item.getTitle()).toLowerCase().contains(filterPattern)
                    || item.getAuthors().toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            journalList.clear();
            journalList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        private int counter;
        private TextView tv_score;
        private TextView tv_title;
        private TextView tv_folded_title;
        private TextView tv_author;
        private TextView tv_folded_authors;
        private TextView tv_folded_type;
        private TextView tv_folded_date;
        private TextView tv_abstract;
        private TextView tv_folded_abstract;
        private LinearLayout more_layout;
        private TextView btn_more;
        private ImageView btn_options;
        private ConstraintLayout journal_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            counter = 0;
            tv_score = itemView.findViewById(R.id.tv_score);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_folded_title = itemView.findViewById(R.id.tv_folded_title);
            tv_folded_authors = itemView.findViewById(R.id.tv_folded_authors);
            tv_folded_date = itemView.findViewById(R.id.tv_folded_date);
            tv_folded_type = itemView.findViewById(R.id.tv_folded_type);
            tv_folded_abstract = itemView.findViewById(R.id.tv_folded_abstract);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_abstract = itemView.findViewById(R.id.tv_abstract);
            btn_options = itemView.findViewById(R.id.btn_options);
            btn_more = itemView.findViewById(R.id.btn_more);
            journal_layout = itemView.findViewById(R.id.layout_journals);
        }


        public void setData(String title, List<String> author, String article_type, double score, List<String> anAbstract,String date, String eissn) {

            tv_title.setText(title);
            StringBuffer authors = new StringBuffer();
            StringBuffer _abstract = new StringBuffer();
            for(int i=0; i<author.size(); i++){
                authors.append(author.get(i)+", ");
            }

            if(anAbstract.size()==0 || anAbstract.get(0).length()==0){
                tv_folded_abstract.setText("No abstract Available");
                tv_abstract.setText("No abstract available");
            }else{
                for(int i=0;i<anAbstract.size();i++){
                    _abstract.append(anAbstract.get(i)+" ");
                }
                tv_abstract.setText(_abstract);
                tv_folded_abstract.setText(_abstract);
            }

            tv_author.setText(authors);
            tv_score.setText(String.format("%.2f",score));

            tv_folded_title.setText(title);
            tv_folded_authors.setText(authors.substring(0, authors.length()-1));
            tv_folded_date.setText("Published On: "+date);
            tv_folded_type.setText("Article Type: "+article_type);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    counter++;
                    if(counter%2==0){
                        more_layout.setVisibility(View.GONE);
                        tv_abstract.setVisibility(View.VISIBLE);
                    }else{
                        tv_abstract.setVisibility(View.GONE);
                        more_layout.setVisibility(View.VISIBLE);
                    }
                }
            });

            // get our folding cell
            final FoldingCell fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });

            final Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("authors", authors.substring(0, authors.length()-1));
            args.putString("type", article_type);
            args.putString("abstract", _abstract.toString());
            args.putString("date", date);
            args.putString("eissn", eissn);
            args.putString("score", String.valueOf(score));

            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)itemView.getContext();
                    Fragment fragment = new JournalDetailsFragment();
                    fragment.setArguments(args);//send pid of selected patient
                    appCompatActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.main_framelayout, fragment)
                            .addToBackStack(null).commit();
                }
            });

            btn_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), btn_options);
                    popupMenu.getMenuInflater().inflate(R.menu.dropdown,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId()==R.id.btn_details){
                                AppCompatActivity appCompatActivity = (AppCompatActivity)itemView.getContext();
                                Fragment fragment = new JournalDetailsFragment();
                                fragment.setArguments(args);//send pid of selected patient
                                appCompatActivity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                        .replace(R.id.main_framelayout, fragment)
                                        .addToBackStack(null).commit();
                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                }
            });
        }
    }
}
