package com.cambium.challenge.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cambium.challenge.R;
import com.cambium.challenge.activities.MainActivity;
import com.cambium.challenge.fragments.DetailFragment;
import com.cambium.challenge.models.ProjectDetails;
import com.cambium.challenge.utils.Utils;

import java.util.ArrayList;

/**
 * A adapter class for set data in ui.
 *
 * @author Ajay Kumar Maheshwari
 */

public class ProjectListingAdapter extends BaseAdapter implements Filterable {
    private ArrayList<ProjectDetails> itemlist;
    private ArrayList<ProjectDetails> filterList;
    private Context context;
    private listFilter filter;
    private Animation scaleUp;

    public ProjectListingAdapter(ArrayList<ProjectDetails> itemlist, Context context) {
        this.itemlist = itemlist;
        this.filterList = itemlist;
        this.context = context;
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up_fast);


    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ProjectListingAdapter.ViewHolder holder;
        View view;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_listing, null);
            holder = new ProjectListingAdapter.ViewHolder();
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.tv_amtpledged = (TextView) view.findViewById(R.id.tv_amtpledged);
            holder.tv_backers = (TextView) view.findViewById(R.id.tv_backers);
            holder.tv_daytogo = (TextView) view.findViewById(R.id.tv_daytogo);
            holder.rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ProjectListingAdapter.ViewHolder) view.getTag();
        }
        holder.rl_item.startAnimation(scaleUp);
        holder.tv_title.setText(filterList.get(position).getTitle());
        String amtPledged = (filterList.get(position).getCurrency().equalsIgnoreCase
                        (context.getString(R.string.compare_currency)) ?
                        context.getString(R.string.label_symbol) : filterList.get(position).getCurrency()) +
                        filterList.get(position).getAmtPledged();
        holder.tv_amtpledged.setText(amtPledged);
        String backers = filterList.get(position).getNumBackers();
        holder.tv_backers.setText(backers);
        holder.tv_daytogo.setText(Utils.getDateWithFormat(filterList.get(position).getEndTime()));
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Fragment fragment = new DetailFragment();
                                                   String tag = Utils.DETAIL_FRAGMENT;
                                                   Bundle bundle = new Bundle();
                                                   bundle.putString("fragmentTag", tag);
                                                   bundle.putInt("resourceId", R.id.frame_container);
                                                   bundle.putParcelable("data", filterList.get(position));
                                                   Utils.replaceFragmentWithTag((MainActivity) context, fragment, bundle);
                                               }
                                           }
        );
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new listFilter();
        }
        return filter;
    }

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_amtpledged;
        private TextView tv_backers;
        private TextView tv_daytogo;
        private RelativeLayout rl_item;
    }

    private class listFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final ArrayList<ProjectDetails> referenceList = itemlist;
            final ArrayList<ProjectDetails> newList = new ArrayList<>(referenceList.size());
            ProjectDetails filterableString;
            for (int i = 0; i < referenceList.size(); i++) {
                filterableString = referenceList.get(i);
                if (filterableString.getTitle().toLowerCase().contains(filterString)) {
                    newList.add(filterableString);
                }
            }
            results.values = newList;
            results.count = newList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (ArrayList<ProjectDetails>) results.values;
            notifyDataSetChanged();
        }

    }


}


