package com.example.quizzapp.services;

import androidx.lifecycle.MutableLiveData;

import com.example.quizzapp.models.Friend;
import com.example.quizzapp.models.Quizz;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DbService {

    private FirebaseFirestore db;

    public DbService() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Friend> getFriend(String id) {
        MutableLiveData<Friend> friend = new MutableLiveData(new Friend());
        db.collection("friend").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> friends = queryDocumentSnapshots.getDocuments();

                    ArrayList<String> dbFriendsIds = new ArrayList<String>();
                    for (DocumentSnapshot doc : friends) {
                        if (doc.getId().equals(id)) {
                            String dbEmail = doc.getData().get("email").toString();
                            String dbPassword = doc.getData().get("password").toString();
                            String dbName = doc.getData().get("name").toString();
                            int dbScore = Integer.parseInt(doc.getData().get("score").toString());
                            String dbAvatar = doc.getData().get("avatar").toString();

                            dbFriendsIds = (ArrayList) doc.getData().get("friends");
                            friend.setValue(new Friend(doc.getId(), dbEmail, dbPassword, dbName, dbScore, dbAvatar));
                        }
                    }

                    for (String id : dbFriendsIds) {
                        for(DocumentSnapshot doc : friends) {
                            if(doc.getId().equals(id)) {
                                String dbName = doc.getData().get("name").toString();
                                int dbScore = Integer.parseInt(doc.getData().get("score").toString());
                                String dbAvatar = doc.getData().get("avatar").toString();

                                friend.getValue().getFriends().add(new Friend(dbName, dbScore, dbAvatar));
                            }
                        }
                    }

                    friend.setValue(friend.getValue());
                }
            }
        });

        return friend;
    }

    public MutableLiveData<ArrayList<Quizz>> getQuizz(int nbQuestion) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<Quizz>> questions = new MutableLiveData(new ArrayList<Quizz>());

        database.collection("quizz").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> dbQuestions = queryDocumentSnapshots.getDocuments();

                        Random r = new Random();
                        ArrayList<Integer> indexList = new ArrayList<>();

                        while (indexList.size() < nbQuestion) {
                            Integer rIndex = r.nextInt(dbQuestions.size());

                            if (!indexList.contains(rIndex)) {
                                indexList.add(rIndex);
                            }
                        }


                        for (int i = 0; i < indexList.size(); i++) {
                            Map<String, Object> data = dbQuestions.get(indexList.get(i)).getData();
                            questions.getValue().add(new Quizz(
                                data.get("question").toString(),
                                data.get("answer").toString().equals("true"),
                                data.get("image").toString()
                            ));
                            System.out.println(questions.getValue().size());
                        }

                        questions.setValue(questions.getValue());
                    }
                }
            });

        return questions;
    }

    public MutableLiveData<Boolean> updateScore(Friend friend) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Boolean> success = new MutableLiveData(false);

        database.collection("friend").document(friend.getId()).update("score", friend.getScore())
            .addOnSuccessListener(new OnSuccessListener() {

                @Override
                public void onSuccess(Object o) {
                    success.setValue(true);
                }
            });

        return success;
    }

    public MutableLiveData<String> signIn(String email, String password) {
        MutableLiveData<String> friendId = new MutableLiveData("");
        db.collection("friend").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> friends = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : friends) {
                        String dbEmail = doc.getData().get("email").toString();
                        String dbPassword = doc.getData().get("password").toString();

                        if (dbEmail.equals(email) && dbPassword.equals(password)) {
                            friendId.setValue(doc.getId());
                        }
                    }
                }
            }
        });

        return friendId;
    }
}
