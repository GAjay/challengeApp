package com.cambium.challenge.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cambium.challenge.R;
import com.cambium.challenge.adapters.ProjectListingAdapter;
import com.cambium.challenge.models.ProjectDetails;
import com.cambium.challenge.network.ApiService;
import com.cambium.challenge.utils.DialogUtils;
import com.cambium.challenge.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A listing fragment class for project list.
 *
 * @author Ajay Kumar Maheshwari
 */

public class ListingFragment extends Fragment {

    ProjectListingAdapter projectListingAdapter;
    int sortingId = -1;
    String backers = null;
    int filterId = -1;
    ArrayList<ProjectDetails> projectDetailsFilter = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        ApiService service = new ApiService();
        service.shareProjectDetails(getActivity(), new ApiService.ApiCallback() {
            @Override
            public void onSuccess(ArrayList<ProjectDetails> result) {
                setData(result, rootView);
            }
        });

        return rootView;
    }

    /**
     * A method for set data in fragment ui components.
     *
     * @param projectDetails : List of data.
     * @param rootView       : Main View
     */
    private void setData(final ArrayList<ProjectDetails> projectDetails, View rootView) {

        projectDetailsFilter = projectDetails;
        final ListView listView = rootView.findViewById(R.id.lv_project_listing);

        if (null !=projectDetails  && projectDetails.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            //enables filtering for the contents of the given ListView
            listView.setTextFilterEnabled(true);
            Collections.sort(projectDetailsFilter, ProjectDetails.aTozCompare);
            backers = projectDetails.get(0).getNumBackers();
            final ArrayList<String> filter = new ArrayList<>();
            filter.add("0-" + String.valueOf((Integer.parseInt(backers) / 2)));
            filter.add(String.valueOf((Integer.parseInt(backers) / 2)) + "-" + backers);
            final TextView filterText = rootView.findViewById(R.id.tv_filter);
            filterText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.getDropDown(getActivity(), filter, filterId, new DialogUtils.
                            choiceSelectedCallback() {
                        @Override
                        public void onChoiceSelected(int pos) {
                            filterId = pos;
                            filterText.setCompoundDrawablesWithIntrinsicBounds(getResources().
                                    getDrawable(R.drawable.ic_filter_active), null, null, null);
                            filterText.setTextColor(getResources().getColor(R.color.colorAccent));
                            Log.d("Total filter results",
                                    String.valueOf(filtersData(pos, backers, projectDetailsFilter).size()));
                            projectDetailsFilter = filtersData(pos, backers, projectDetailsFilter);
                            projectListingAdapter = new ProjectListingAdapter(projectDetailsFilter, getContext());
                            listView.setAdapter(projectListingAdapter);

                        }
                    });
                }
            });
            final TextView sortText = rootView.findViewById(R.id.tv_sorting);
            projectListingAdapter = new ProjectListingAdapter(
                    projectDetails, getActivity());
            listView.setAdapter(projectListingAdapter);
            //making sorting list.
            final ArrayList<String> sorting = new ArrayList<>();
            sorting.add(getResources().getString(R.string.label_sort_a_z));
            sorting.add(getString(R.string.label_sort_z_a));
            sorting.add(getString(R.string.label_sort_time_desc));
            sorting.add(getString(R.string.label_sort_time_asc));
            sortText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.getDropDown(getActivity(), sorting, sortingId, new DialogUtils.
                            choiceSelectedCallback() {
                        @Override
                        public void onChoiceSelected(int pos) {
                            sort(pos, sortText, projectDetailsFilter);
                        }
                    });
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    callingFragment(i, projectDetailsFilter);
                }
            });
            final EditText searchText = rootView.findViewById(R.id.etv_search);
            searchText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s,
                                              int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {
                    if (searchText.getText().length() > 0) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(R.drawable.ic_search_active), null);
                    } else {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(R.drawable.ic_search), null);
                    }
                    projectListingAdapter.getFilter().filter(charSequence);
                }
            });
        } else {

            (rootView.findViewById(R.id.tv_no_record)).setVisibility(View.VISIBLE);
            (rootView.findViewById(R.id.ll_filter_sorting)).setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
    }

    /**
     * A method for filter the data as per user selection
     *
     * @param pos            : Selected position
     * @param backers        : No Backers.
     * @param projectDetails : ArrayList
     * @return : Filtered result
     */
    private ArrayList<ProjectDetails> filtersData(int pos, String backers,
                                                  ArrayList<ProjectDetails> projectDetails) {
        int compareValue = (pos == 0) ? (Integer.parseInt(backers) / 2) : Integer.parseInt(backers);
        ArrayList<ProjectDetails> projectDetailsFilter = new ArrayList<>();
        for (int i = 0; i <= (projectDetails.size() - 1); i++) {
            try {
                if (Integer.parseInt(projectDetails.get(i).getNumBackers()) <= compareValue) {
                    projectDetailsFilter.add(projectDetails.get(i));
                }
            } catch (NumberFormatException e) {
                Log.d("Number exception", projectDetails.get(i).getNumBackers());

            }
        }
        return projectDetailsFilter;
    }

    /**
     * A method for call detail fragment.
     *
     * @param i              : position of item where user clicked.
     * @param projectDetails : Array list class.
     */
    public void callingFragment(int i, ArrayList<ProjectDetails> projectDetails) {
        filterId=-1;
        sortingId=-1;
        Fragment fragment = new DetailFragment();
        String tag = Utils.DETAIL_FRAGMENT;
        Bundle bundle = new Bundle();
        bundle.putString("fragmentTag", tag);
        bundle.putInt("resourceId", R.id.frame_container);
        bundle.putParcelable("data", projectDetails.get(i));
        Utils.replaceFragmentWithTag(getActivity(), fragment, bundle);
    }

    /**
     * A method for call sorting dialog and sort data as per user selection.
     *
     * @param pos            : selected position.
     * @param sortText       : textview reference.
     * @param projectDetails : Arraylist of data.
     */
    private void sort(int pos, TextView sortText, ArrayList<ProjectDetails> projectDetails) {
        sortingId = pos;
        sortText.setCompoundDrawablesWithIntrinsicBounds(getResources().
                getDrawable(R.drawable.ic_sort_active), null, null, null);
        sortText.setTextColor(getResources().getColor(R.color.colorAccent));

        switch (pos) {
            case 0:
                Collections.sort(projectDetails, ProjectDetails.aTozCompare);
                projectListingAdapter.notifyDataSetChanged();

                break;
            case 1:
                Collections.sort(projectDetails, ProjectDetails.zToaCompare);
                projectListingAdapter.notifyDataSetChanged();
                break;
            case 2:
                Collections.sort(projectDetails, ProjectDetails.timeDesc);
                projectListingAdapter.notifyDataSetChanged();
                break;
            case 3:
                Collections.sort(projectDetails, ProjectDetails.timeAsc);
                projectListingAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}

