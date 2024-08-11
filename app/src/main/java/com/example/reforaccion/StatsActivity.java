package com.example.reforaccion;

import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reforaccion.models.Area;
import com.example.reforaccion.models.Plant;
import com.example.reforaccion.models.Stats;
import com.example.reforaccion.models.UserApplication;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StatsActivity extends AppCompatActivity {

    UserApplication currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Stats> stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.statistics_activity);
        initiateVars();
        fetchAndGroupDataByMonth(currentUser.user.getCellphone());
        returnStatsButtonListener();
    }

    private void initiateVars(){
        currentUser = (UserApplication) getApplicationContext();
        stats = new HashMap<>();
    }

    private void fetchAndGroupDataByMonth(String documentId) {

        DocumentReference docRef = db.collection("users").document(documentId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    //Get the Plants list from the user
                    List<Map<String, Object>> plants = (List<Map<String, Object>>) document.get("plants");
                    //If the list is not empty
                    if (!Objects.requireNonNull(plants).isEmpty()) {

                        //Create a HashMap to save the grouped data
                        Map<String, List<Plant>> monthlyGroups = new HashMap<>(); //A
                        for (Map<String, Object> plantMap : plants) {

                            //Creates a plant object for each Plant array element
                            Integer quantity = Integer.parseInt(Objects.requireNonNull(plantMap.get("quantity")).toString());
                            Double individualPrice = Double.parseDouble(Objects.requireNonNull(plantMap.get("individualPrice")).toString());
                            String datePlanted = Objects.requireNonNull(plantMap.get("datePlanted")).toString();
                            Plant plant = new Plant(quantity, individualPrice, datePlanted);
                            //Adds the object to the HashMap
                            monthlyGroups.putIfAbsent(datePlanted, new ArrayList<>());
                            Objects.requireNonNull(monthlyGroups.get(datePlanted)).add(plant);

                        }

                        //Access every Plant element in the list
                        for (Map.Entry<String, List<Plant>> entry : monthlyGroups.entrySet()) { //A_i

                            String month = entry.getKey();
                            List<Plant> plantsInMonth = entry.getValue();
                            //Variables for each month stats
                            Double averagePrice = 0.0;
                            Integer totalPlants = 0;
                            int plantCounter = 0;

                            for (Plant plant : plantsInMonth) { //A_ij
                                //Calculates every statistic
                                averagePrice += plant.getIndividualPrice();
                                totalPlants +=  plant.getQuantity();
                                plantCounter++;
                                if(plantCounter == plantsInMonth.size()) averagePrice = averagePrice / plantCounter;
                            }

                            //Creates a stats object and adds it to the global list
                            Stats newMonthStat = new Stats(month, totalPlants, averagePrice, "");
                            stats.putIfAbsent(month, newMonthStat);
                        }
                    }


                    //Get the Areas list from the user
                    List<Map<String, Object>> areas = (List<Map<String, Object>>) document.get("areas");
                    //If the list is not empty
                    if (!Objects.requireNonNull(areas).isEmpty()) {

                        //Create a HashMap to save the grouped data
                        Map<String, List<Area>> monthlyGroups = new HashMap<>(); //B
                        for (Map<String, Object> areaMap : areas) {

                            //Creates an area object for each Area array element
                            String zone = Objects.requireNonNull(areaMap.get("zonePlanted")).toString();
                            Double areaPlanted = Double.parseDouble(Objects.requireNonNull(areaMap.get("areaPlanted")).toString());
                            String datePlanted = Objects.requireNonNull(areaMap.get("datePlanted")).toString();
                            Area area = new Area(zone,areaPlanted, datePlanted);
                            //Adds the object to the HashMap
                            monthlyGroups.putIfAbsent(datePlanted, new ArrayList<>());
                            Objects.requireNonNull(monthlyGroups.get(datePlanted)).add(area);

                        }

                        //Variables for area stats
                        double maxArea = 0.0;
                        String maxZone = "";
                        String qrMonth = "";

                        //Access every Plant element in the list
                        for (Map.Entry<String, List<Area>> entry : monthlyGroups.entrySet()) { //A_i

                            String month = entry.getKey();
                            List<Area> areasInMonth = entry.getValue();
                            Double areaPlanted = 0.0;
                            for (Area area : areasInMonth) { //A_ij
                                areaPlanted += area.getAreaPlanted();
                                maxZone = area.getZonePlanted();
                            }
                            //It the current month zone holds the greater area, saves it as such
                            if(areaPlanted > maxArea)
                                maxArea = areaPlanted;

                            qrMonth = month;
                        }

                        //Updates the hashmap
                        Stats monthStats = stats.get(qrMonth);
                        if (monthStats != null) monthStats.setMostPlantedZone(maxZone);
                        Log.d(TAG,"Second " + monthStats);

                    }

                    Log.d(TAG,"Third " + stats.toString());

                }else
                    Toast.makeText(getApplicationContext(), "No pudo obtenerse la infromación", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No pudo obtenerse la infromación", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Stats got failed with ", task.getException());
            }
        });

    }

    private void returnStatsButtonListener(){
        ImageButton returnUserButton = findViewById(R.id.returnStatsButton);
        returnUserButton.setOnClickListener(v -> finish());
    }
}