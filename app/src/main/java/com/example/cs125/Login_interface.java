package com.example.cs125;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
 * Use the {@link Login_interface#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_interface extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_interface() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_interface.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_interface newInstance(String param1, String param2) {
        Login_interface fragment = new Login_interface();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_interface, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button;
        Button SignUpButton;

        ProgressBar progressBar;
        EditText Email, password;
        FirebaseAuth firebase;

        firebase = FirebaseAuth.getInstance();
        Email = getView().findViewById(R.id.editTextTextEmailAddress2);
        password =  getView().findViewById(R.id.editTextTextPassword2);
        progressBar = getView().findViewById(R.id.progressBar2);




        button = getView().findViewById(R.id.LogninButton);
        SignUpButton = getView().findViewById(R.id.SignUpbutton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* change here


                 */
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_login_interface_to_afterLog);
                //controller.navigate(R.id.action_login_interface_to_locations);


                String username = Email.getText().toString().trim();
                String mima = password.getText().toString().trim();
                if(TextUtils.isEmpty(username)  ){

                    Email.setError("ID cannot be empty");

                    return;

                }
                if(TextUtils.isEmpty(mima)){
                    password.setError("\"cannot be empty\"");

                    return;

                }


                if(mima.length() < 8){
                    password.setError("password must be at least 8 characters");
                    return;

                }


                progressBar.setVisibility(View.VISIBLE);

                firebase.signInWithEmailAndPassword(username, mima).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "login suessuflly!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getContext(), MainActivity2.class));
                            NavController controller = Navigation.findNavController(v);
                            controller.navigate(R.id.action_login_interface_to_afterLog);
                        }
                        else{
                            Toast.makeText(getContext(), "incorrect email/password" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });


            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_login_interface_to_signUpFragment6);

            }
        });
    }
}