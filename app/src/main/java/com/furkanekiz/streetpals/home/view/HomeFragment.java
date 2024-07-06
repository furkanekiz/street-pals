
package com.furkanekiz.streetpals.home.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.furkanekiz.streetpals.utils.Utils;
import com.furkanekiz.streetpals.databinding.HomeFragmentBinding;
import com.furkanekiz.streetpals.home.adapter.HomeAnimalAdapter;
import com.furkanekiz.streetpals.home.adapter.HomeOrganizationAdapter;
import com.furkanekiz.streetpals.home.model.AnimalCard;
import com.furkanekiz.streetpals.home.model.OrganizationCard;
import com.furkanekiz.streetpals.home.slider.SliderAdapter;
import com.furkanekiz.streetpals.home.slider.model.ListData;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    HomeFragmentBinding fragmentHomeBinding;
    private ListData listData = new ListData();

    private static AnimalCard addHomeCardAnimal(JSONObject cardJObject) {
        AnimalCard animalCard = new AnimalCard();
        try {
            String cardTypeString = (String) cardJObject.get("type");
            String cardTitleString = (String) cardJObject.get("title");
            String cardDescString = (String) cardJObject.get("description");
            String cardExtraString = (String) cardJObject.get("extraText");
            String localImageResource = (String) cardJObject.get("localImageResource");
            String urlLink = (String) cardJObject.get("urlToSite");

            animalCard.setType(AnimalCard.Type.ANIMAL);
            animalCard.setTitle(cardTitleString);
            animalCard.setDescription(cardDescString);
            animalCard.setLocalImageResource(localImageResource);
            animalCard.setUrlToSite(urlLink);
            animalCard.setExtraText(cardExtraString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return animalCard;
    }

    private static OrganizationCard addHomeCardOrganization(JSONObject cardJObject) {
        OrganizationCard organizationCard = new OrganizationCard();

        try {
            int id = cardJObject.getInt("id");
            String title = cardJObject.getString("title");
            String poster = cardJObject.getString("poster");
            String url = cardJObject.getString("url");
            String description = cardJObject.getString("description");
            String director = cardJObject.getString("director");
            String year = cardJObject.getString("year");

            organizationCard.setType(OrganizationCard.Type.ORGANIZATION);
            organizationCard.setTitle(title);
            organizationCard.setDesc(description);
            organizationCard.setDirector(director);
            organizationCard.setPoster(poster);
            organizationCard.setUrl(url);
            organizationCard.setYear(year);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return organizationCard;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View generalView = fragmentHomeBinding.getRoot();

        Context ctx = generalView.getContext();
        Activity act = getActivity();

        loadData();


        LinearLayoutManager linearLayoutManagerAnimal = new LinearLayoutManager(ctx);
        linearLayoutManagerAnimal.setOrientation(RecyclerView.HORIZONTAL);
        fragmentHomeBinding.recyclerViewAnimal.setLayoutManager(linearLayoutManagerAnimal);

        LinearLayoutManager linearLayoutManagerMovie = new LinearLayoutManager(ctx);
        linearLayoutManagerMovie.setOrientation(RecyclerView.HORIZONTAL);
        fragmentHomeBinding.recyclerViewOrganization.setLayoutManager(linearLayoutManagerMovie);

        SliderAdapter sliderAdapter = new SliderAdapter(listData.sliderCard);
        fragmentHomeBinding.homeSlider.setSliderAdapter(sliderAdapter);
        fragmentHomeBinding.homeSlider.setIndicatorAnimation(IndicatorAnimationType.COLOR);
        fragmentHomeBinding.homeSlider.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        fragmentHomeBinding.homeSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        fragmentHomeBinding.homeSlider.setIndicatorVisibility(true);
        fragmentHomeBinding.homeSlider.setIndicatorSelectedColor(Color.YELLOW);
        fragmentHomeBinding.homeSlider.setIndicatorUnselectedColor(Color.GRAY);
        fragmentHomeBinding.homeSlider.startAutoCycle();

        return generalView;
    }

    private void loadData() {
        if (isAdded()) {

            List<AnimalCard> animalCardModels = new ArrayList<>();
            List<OrganizationCard> organizationCards = new ArrayList<>();

            String json = Utils.inputStreamToString(requireActivity().getResources().openRawResource(R.raw.home_screen));

            try {
                JSONArray fileJson = new JSONArray(json);
                for (int y = 0; y < fileJson.length(); y++) {
                    JSONObject rowObj = fileJson.getJSONObject(y);
                    JSONArray cardsJsonArray = rowObj.getJSONArray("cards");

                    for (int z = 0; z < cardsJsonArray.length(); z++) {
                        JSONObject cardJsonObj = cardsJsonArray.getJSONObject(z);
                        AnimalCard cardAnimal;
                        OrganizationCard cardOrganization;

                        if (cardJsonObj.get("type").equals("ANIMAL")) {
                            cardAnimal = addHomeCardAnimal(cardJsonObj);
                            animalCardModels.add(cardAnimal);
                        } else if (cardJsonObj.get("type").equals("ORGANIZATION")) {
                            cardOrganization = addHomeCardOrganization(cardJsonObj);
                            organizationCards.add(cardOrganization);
                        }
                    }
                    //Animal
                    HomeAnimalAdapter homeAnimalAdapter = new HomeAnimalAdapter(animalCardModels, R.layout.item_animal_card, getContext());
                    fragmentHomeBinding.recyclerViewAnimal.setAdapter(homeAnimalAdapter);

                    //Organization
                    HomeOrganizationAdapter homeOrganizationAdapter = new HomeOrganizationAdapter(organizationCards, R.layout.item_organization_card, getContext());
                    fragmentHomeBinding.recyclerViewOrganization.setAdapter(homeOrganizationAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


