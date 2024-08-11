package com.example.reforaccion;

import static android.content.ContentValues.TAG;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TextView;
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
    TableLayout statsTable;
    TextView allCostAvg;
    TextView minPlantsText;
    TextView maxPlantsText;
    Double averageCost;
    Integer minPlanted;
    Integer maxPlanted;

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
        statsTable = findViewById(R.id.statsTableLayout);
        allCostAvg = findViewById(R.id.totalAverageText);
        minPlantsText = findViewById(R.id.minPlantedText);
        maxPlantsText = findViewById(R.id.maxPlantedText);
        averageCost = 0.0;
        minPlanted = 0;
        maxPlanted = 0;
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
                        Map<String, List<Plant>> monthlyGroups = new HashMap<>();
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
                        for (Map.Entry<String, List<Plant>> entry : monthlyGroups.entrySet()) {

                            String month = entry.getKey();
                            List<Plant> plantsInMonth = entry.getValue();
                            //Variables for each month stats
                            Double averagePrice = 0.0;
                            Integer totalPlants = 0;

                            for (Plant plant : plantsInMonth) {
                                //Calculates every statistic
                                averagePrice += plant.getIndividualPrice();
                                totalPlants +=  plant.getQuantity();
                            }
                            averagePrice = averagePrice / plantsInMonth.size();

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
                        Map<String, List<Area>> monthlyGroups = new HashMap<>();
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

                        //Access every Plant element in the list
                        for (Map.Entry<String, List<Area>> entry : monthlyGroups.entrySet()) {
                            String month = entry.getKey();
                            List<Area> areasInMonth = entry.getValue();
                            Double areaPlanted = 0.0;
                            for (Area area : areasInMonth) {
                                areaPlanted += area.getAreaPlanted();
                                maxZone = area.getZonePlanted();
                            }
                            //It the current month zone holds the greater area, saves it as such
                            if(areaPlanted > maxArea)
                                maxArea = areaPlanted;

                            //Updates the hashmap
                            Stats monthStats = stats.get(month);
                            if (monthStats != null) monthStats.setMostPlantedZone(maxZone);
                        }

                    }
                    //Adding the data to the table
                    populateTableLayout(stats);

                }else
                    Toast.makeText(getApplicationContext(), "No pudo obtenerse la información", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No pudo obtenerse la información", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Stats got failed with ", task.getException());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void populateTableLayout(Map<String, Stats> statsMap) {

        Integer minPlanted = 0;
        Integer maxPlanted = 0;
        String minMonth = "";
        String maxMonth = "";

        // Iterate over the hashmap
        for (Map.Entry<String, Stats> entry : statsMap.entrySet()) {

            String key = entry.getKey();
            Stats stats = entry.getValue();
            //New row to add to the table
            TableRow tableRow = new TableRow(this);

            // Textview for each col on the table
            TextView keyTextView = new TextView(this);
            keyTextView.setTextColor(getResources().getColor(R.color.darkBlueTextColor));
            keyTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            keyTextView.setText(key); //Month col
            tableRow.addView(keyTextView);

            TextView plantedTextView = new TextView(this);
            plantedTextView.setText(String.valueOf(stats.getTotalPlanted())); //Total planted col
            plantedTextView.setTextColor(getResources().getColor(R.color.darkBlueTextColor));
            plantedTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(plantedTextView);

            TextView avgTextView = new TextView(this);
            avgTextView.setText("$" + stats.getAveragePriceMonth()); //Avg per month
            avgTextView.setTextColor(getResources().getColor(R.color.darkBlueTextColor));
            avgTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(avgTextView);

            TextView zoneTextView = new TextView(this);
            zoneTextView.setText(String.valueOf(stats.getMostPlantedZone())); //Zone
            zoneTextView.setTextColor(getResources().getColor(R.color.darkBlueTextColor));
            zoneTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(zoneTextView);

            //Adding the row
            statsTable.addView(tableRow);
            //Calculation for other stats
            averageCost += stats.getAveragePriceMonth();
            if(stats.getTotalPlanted() > maxPlanted){
                maxPlanted = stats.getTotalPlanted();
                if(minPlanted == 0) {
                    minPlanted = maxPlanted;
                    minMonth = key;
                };
                maxMonth = key;
            }
            if(stats.getTotalPlanted() < minPlanted){
                minPlanted = stats.getTotalPlanted();
                minMonth = key;
            }
        }

        //Sets the all-over stats
        averageCost = averageCost / statsMap.size();
        allCostAvg.setText(getResources().getString(R.string.totalAverageLabel) + " $" + averageCost);
        maxPlantsText.setText(getResources().getString(R.string.maxPlantedLabel) + " " + maxMonth);
        minPlantsText.setText(getResources().getString(R.string.minPlantedLabel) + " " + minMonth);
    }

    private void returnStatsButtonListener(){
        ImageButton returnUserButton = findViewById(R.id.returnStatsButton);
        returnUserButton.setOnClickListener(v -> {
            Intent i;
            if(currentUser.user != null)
                i = new Intent(StatsActivity.this, MainActivity.class);
            else
                i = new Intent(StatsActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}