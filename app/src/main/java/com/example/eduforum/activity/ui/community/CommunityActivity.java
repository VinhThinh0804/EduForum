package com.example.eduforum.activity.ui.community;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eduforum.R;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.ui.community.adapter.PostAdapter;
import com.example.eduforum.activity.ui.community.adapter.TagsAdapter;
import com.example.eduforum.activity.ui.community.viewstate.FilterViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.NewsFeedViewModel;
import com.example.eduforum.databinding.ActivityCommunityBinding;
import com.example.eduforum.databinding.CommunityFilterBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    ActivityCommunityBinding binding;
    NewsFeedViewModel viewModel;

    PostAdapter postAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(NewsFeedViewModel.class);
        binding.setLifecycleOwner(this);

        CreateCommunityViewState currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if (currentCommunity != null) {
            viewModel.setCurrentCommunity(currentCommunity);
        } else {
            //finish();
        }
        // setup postRecyclerView
        postAdapter = new PostAdapter(this, viewModel.getPostList().getValue());
        binding.postRecyclerView.setAdapter(postAdapter);
        binding.postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.createPostEditTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            intent.putExtra("communityId", currentCommunity.getCommunityID());
            startActivity(intent);
        });
        viewModel.getCurrentCommunity().observe(this, community -> {
            if (community != null) {
                binding.communityName.setText(community.getName());
                binding.descriptionContentTextview.setText(community.getDescription());
            }
        });
        viewModel.getPostList().observe(this, postList -> {
            postAdapter.setPostList(postList);
        });
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        //setup toolbar
        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.community_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            //event  when user submit the search query
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            //event when user change the search query
//            @Override
//            public boolean onQueryTextChange(String newText) {

//               postAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
        if (id == R.id.filter) {
            Dialog filterDialog = new Dialog(this);
            CommunityFilterBinding filterBinding = CommunityFilterBinding.inflate(getLayoutInflater());
            filterDialog.setContentView(filterBinding.getRoot());
            filterBinding.setLifecycleOwner(this);
            TagsAdapter tagsAdapter = new TagsAdapter( viewModel.getAllCategories().getValue());
            filterBinding.categoryRecyclerView.setAdapter(tagsAdapter);
            filterBinding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            filterBinding.applyButton.setOnClickListener(v -> {
                // for testing
                List<Category> sampleCategories = new ArrayList<>();
                sampleCategories.add(new Category("1", "Hỏi đáp", false));
                viewModel.setFilter(new FilterViewState(sampleCategories));
                filterDialog.dismiss();
            });


            filterDialog.show();
            return true;
        }

        if (id == R.id.setting) {
           Intent intent = new Intent(this, SettingCommunityActivity.class);
              startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}