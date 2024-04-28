package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import com.example.eduforum.activity.viewmodel.auth.SignUpViewModel;
import com.example.eduforum.activity.viewmodel.auth.VerificationWaitingViewModel;
import com.example.eduforum.databinding.ActivityVerificationWaitingBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class VerificationWaitingActivity extends AppCompatActivity {

    private ActivityVerificationWaitingBinding binding;
    private VerificationWaitingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationWaitingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(VerificationWaitingViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getBackToLogin().observe(this, backToLogin -> {
            if (backToLogin) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}