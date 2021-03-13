package com.example.cs125;
/*

contributes by: Yang Lu
User can choose to sign up, if they don't have an account.

It's linked with firebase  authentication
 */
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_sign_up, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button RigsterButton;
        RigsterButton = getView().findViewById(R.id.ReigsterButton);

        EditText ID, passcode;
        FirebaseAuth fAuth;

        ID = getView().findViewById(R.id.editTextTextEmailAddress2);
        passcode = getView().findViewById(R.id.editTextTextPassword2);
        fAuth = FirebaseAuth.getInstance();

        RigsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ID.getText().toString().trim();
                String mima = passcode.getText().toString().trim();
                if(TextUtils.isEmpty(username)  ){

                    ID.setError("ID cannot be empty");

                    return;

                }
                if(TextUtils.isEmpty(mima)){
                    passcode.setError("\"cannot be empty\"");

                    return;

                }


                if(mima.length() < 8){
                    passcode.setError("password must be at least 8 characters");
                    return;

                }

                fAuth.createUserWithEmailAndPassword(username, mima).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "account suessuflly created!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getContext(), MainActivity2.class));
                            NavController controller = Navigation.findNavController(v);
                            controller.navigate(R.id.action_signUpFragment6_to_login_interface);
                        }
                        else{
                            Toast.makeText(getContext(), "account failed to created!", Toast.LENGTH_LONG).show();

                        }
                    }

                });




            }
        });
    }
}