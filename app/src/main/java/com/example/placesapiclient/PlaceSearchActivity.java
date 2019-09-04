package com.example.placesapiclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class PlaceSearchActivity extends AppCompatActivity implements PlacesAAdapter.ClickMaanger {
    private RecyclerView recyclerView;
    private EditText editText;
    private PlacesAAdapter placesAAdapter;
    private List<PlaceItem> placeItemList = new ArrayList<PlaceItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
        //Please add your api key here
        Places.initialize(getApplicationContext(), "");
        final PlacesClient placesClient = Places.createClient(this);
        final RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(13.051577, 80.216374), new LatLng(13.051577, 80.216374));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editText = (EditText) findViewById(R.id.edt_search);

        placesAAdapter = new PlacesAAdapter(this,this);
        recyclerView.setAdapter(placesAAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                FindAutocompletePredictionsRequest.Builder requestBuilder =
                        FindAutocompletePredictionsRequest.builder()
                                .setQuery(charSequence.toString())
                                .setCountry("IN")
//                                .setLocationRestriction(bounds);
                                .setLocationBias(bounds);

//                if (isUseSessionTokenChecked()) {
//                    requestBuilder.setSessionToken(AutocompleteSessionToken.newInstance());
//                }

                Task<FindAutocompletePredictionsResponse> task =
                        placesClient.findAutocompletePredictions(requestBuilder.build());

                task.addOnSuccessListener(
                        (response) -> {

                            for (AutocompletePrediction autocompletePrediction : response.getAutocompletePredictions()) {
                                placeItemList.add(new PlaceItem(autocompletePrediction.getPlaceId(), autocompletePrediction.getPrimaryText(null).toString(), autocompletePrediction.getSecondaryText(null).toString()));
                            }
                            placesAAdapter.setList(placeItemList);
                            Toast.makeText(PlaceSearchActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        });

                task.addOnFailureListener(
                        (exception) -> {
                            exception.printStackTrace();
                        });

                task.addOnCompleteListener(response -> Toast.makeText(PlaceSearchActivity.this, "Completed", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onItemClick(int positon) {
        Intent intent=new Intent();
        intent.putExtra("key",placeItemList.get(positon).getPrimaryTxt());
        setResult(1234,intent);
        finish();
    }
}
