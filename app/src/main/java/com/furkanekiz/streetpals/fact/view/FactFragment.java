
package com.furkanekiz.streetpals.fact.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.databinding.FactFragmentBinding;
import com.furkanekiz.streetpals.fact.adapter.FactAdapter;
import com.furkanekiz.streetpals.fact.model.FactCard;
import com.furkanekiz.streetpals.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FactFragment extends Fragment {
    List<FactCard> factsCardModels = new ArrayList<>();
    private FactAdapter factAdapter;
    private Activity act;
    FactFragmentBinding factFragmentBinding;
    private SQLiteDatabase database;
    private Boolean flagDog = false;
    private Boolean flagCat = false;
    private Boolean flagBird = false;
    private final String DOG = "DOG";
    private final String CAT = "CAT";
    private final String BIRD = "BIRD";

    private static FactCard addCardFact(JSONObject cardJObject) {
        FactCard factCard = new FactCard();
        try {
            String cardTypeString = (String) cardJObject.get("type");
            String cardInfoString = (String) cardJObject.get("info");
            factCard.setType(cardTypeString);
            factCard.setInfo(cardInfoString);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return factCard;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        factFragmentBinding = FactFragmentBinding.inflate(inflater, container, false);
        View generalView = factFragmentBinding.getRoot();

        Context ctx = generalView.getContext();
        act = getActivity();

        factFragmentBinding.cardDog.setOnClickListener(v -> {
            if (!flagDog) {
                loadDataFromJson(DOG);
                flagDog = true;
            }
            loadRecyclerViewData(DOG);
        });
        factFragmentBinding.cardCat.setOnClickListener(v -> {
            if (!flagCat) {
                loadDataFromJson(CAT);
                flagCat = true;
            }
            loadRecyclerViewData(CAT);
        });
        factFragmentBinding.cardBird.setOnClickListener(v -> {
            if (!flagBird) {
                loadDataFromJson(BIRD);
                flagBird = true;
            }
            loadRecyclerViewData(BIRD);
        });


        LinearLayoutManager linearLayoutManagerFact = new LinearLayoutManager(ctx);
        linearLayoutManagerFact.setOrientation(RecyclerView.VERTICAL);
        factFragmentBinding.recyclerViewFact.setLayoutManager(linearLayoutManagerFact);

        factAdapter = new FactAdapter(factsCardModels, R.layout.item_fact_card, getContext());
        factFragmentBinding.recyclerViewFact.setAdapter(factAdapter);

        return generalView;
    }

    public void saveToSqlite(List<FactCard> factCardList) {
        try {
            database = act.openOrCreateDatabase("Facts", Context.MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS facts (id INTEGER PRIMARY KEY, type VARCHAR, info VARCHAR)");

            for (FactCard factCard : factCardList) {
                String type = factCard.getType();
                String info = factCard.getInfo();

                SQLiteStatement statement = database.compileStatement("INSERT INTO facts (type, info) VALUES (?, ?)");
                statement.bindString(1, type);
                statement.bindString(2, info);
                statement.execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecyclerViewData(String type) {

        List<FactCard> factsFromDatabase = getFactsFromDatabase(type);

        factAdapter = new FactAdapter(factsFromDatabase, R.layout.item_fact_card, getContext());
        factFragmentBinding.recyclerViewFact.setAdapter(factAdapter);
        factAdapter.notifyDataSetChanged();
    }

    private List<FactCard> getFactsFromDatabase(String type) {
        List<FactCard> factsFromDatabase = new ArrayList<>();

        try {
            String[] selectionArgs = {type};
            Cursor cursor = database.rawQuery("SELECT * FROM facts WHERE type=?", selectionArgs);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String retrievedType = cursor.getString(cursor.getColumnIndex("type"));
                    @SuppressLint("Range") String info = cursor.getString(cursor.getColumnIndex("info"));

                    FactCard factCard = new FactCard();
                    factCard.setType(retrievedType);
                    factCard.setInfo(info);

                    factsFromDatabase.add(factCard);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return factsFromDatabase;
    }

    private void loadDataFromJson(String type) {
        if (isAdded()) {

            List<FactCard> factsCardModels = new ArrayList<>();

            String json = Utils.inputStreamToString(requireActivity().getResources().openRawResource(R.raw.animal_facts));

            try {
                JSONArray fileJson = new JSONArray(json);
                for (int y = 0; y < fileJson.length(); y++) {
                    JSONObject rowObj = fileJson.getJSONObject(y);
                    JSONArray cardsJsonArray = rowObj.getJSONArray("facts");

                    for (int z = 0; z < cardsJsonArray.length(); z++) {
                        JSONObject cardJsonObj = cardsJsonArray.getJSONObject(z);
                        FactCard factCard;

                        if (type.equalsIgnoreCase(DOG)) {
                            if (cardJsonObj.get("type").equals(DOG)) {
                                factCard = addCardFact(cardJsonObj);
                                factsCardModels.add(factCard);
                            }
                        } else if (type.equalsIgnoreCase(CAT)) {
                            if (cardJsonObj.get("type").equals(CAT)) {
                                factCard = addCardFact(cardJsonObj);
                                factsCardModels.add(factCard);
                            }
                        } else if (type.equalsIgnoreCase(BIRD)) {
                            if (cardJsonObj.get("type").equals(BIRD)) {
                                factCard = addCardFact(cardJsonObj);
                                factsCardModels.add(factCard);
                            }
                        }
                    }

                }

                saveToSqlite(factsCardModels);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


