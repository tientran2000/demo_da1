package com.example.demo_da1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.demo_da1.Account.Login;
import com.example.demo_da1.Action.History;
import com.example.demo_da1.R;
import com.example.demo_da1.Action.SaveNews;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

import javax.annotation.Nullable;


public class AccountFragment extends Fragment {
RelativeLayout relativeLayout;
View view;
TableRow rlichsu,ryeuthich,rthongtin,rdangxuat;
ImageView imvtk;
TextView tvtentk;
StorageReference storageReference;
FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    public AccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_account, container, false);
Init();Events();

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void Init(){
        rdangxuat=(TableRow) view.findViewById(R.id.rowdangxuat);
        rlichsu=(TableRow) view.findViewById(R.id.rowlichsu);
        rthongtin=(TableRow) view.findViewById(R.id.rowthongtin);
        ryeuthich=(TableRow) view.findViewById(R.id.rowyeuthich);
        imvtk=(ImageView) view.findViewById(R.id.imvtk);
        tvtentk=(TextView) view.findViewById(R.id.tvtentk);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
//        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(getContext()).load(uri).into(imvtk);
//            }
//        });
//        DocumentReference documentReference = fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if(documentSnapshot.exists()){
//                    tvtentk.setText(documentSnapshot.getString("fName"));
//                }else {
//                    Log.d("tag", "onEvent: Document do not exists");
//                }
//            }
//        });
    }
    public void Events(){
        ryeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), SaveNews.class);
                startActivity(it);
            }
        });
        rlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), History.class);
                startActivity(it);
            }
        });
        rdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
    }
}