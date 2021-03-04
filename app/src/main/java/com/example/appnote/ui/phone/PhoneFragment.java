package com.example.appnote.ui.phone;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnote.Prefs;
import com.example.appnote.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {

    private Button btnGo;
    private EditText editText;

    private Button btnVerify;
    private EditText editOTP;

    private String verifyId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editPhone);
        editOTP = view.findViewById(R.id.editOTP);
        btnVerify = view.findViewById(R.id.btnVerify);
        btnGo = view.findViewById(R.id.btnContinue);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requstSms();
                btnGo.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                editOTP.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.VISIBLE);
                btnVerify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm();
                    }
                });

            }
        });
        setCallbacks();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    // метод при нажатии назад приложение закрывается
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void confirm() {
        String codeSms = editOTP.getText().toString().trim();
        if (codeSms.length() == 6 && TextUtils.isDigitsOnly(codeSms)){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyId, codeSms);
            signIn(credential);
        }
    }

    private void setCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            // когда мы отправили запрос на сервер и пришел ответ работает он
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("phone", "onVerificationCompleted: ");
                signIn(phoneAuthCredential);
            }

            @Override
            // когда мы отправили запрос на сервер и какая то ошибка то работает он
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("phone", "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verifyId = s;
            }
        };
    }

    // метод для того что зарегать пользователя на нашем сервере
    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance()
                .signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            close();
                        } else {
                            Toast.makeText(requireContext(), "Error " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void requstSms() { // метод для отправки на номер кода с Firebase
        String phone = editText.getText().toString().trim();
        if (phone.isEmpty()){
            editText.setError("Phone number is required");
            editText.requestFocus();
            return;
        }
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

}