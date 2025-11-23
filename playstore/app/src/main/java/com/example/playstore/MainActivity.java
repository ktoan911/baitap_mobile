package com.example.playstore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private RecyclerView suggestedRecyclerView;
        private RecyclerView recommendedRecyclerView;
        private SuggestedAdapter suggestedAdapter;
        private RecommendedAdapter recommendedAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // setup suggested apps - horizontal scroll with 3 rows
                suggestedRecyclerView = findViewById(R.id.suggestedRecyclerView);
                GridLayoutManager suggestedLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL,
                                false);
                suggestedRecyclerView.setLayoutManager(suggestedLayoutManager);
                suggestedAdapter = new SuggestedAdapter(getSuggestedApps());
                suggestedRecyclerView.setAdapter(suggestedAdapter);

                // setup recommended apps - horizontal scroll
                recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
                LinearLayoutManager recommendedLayoutManager = new LinearLayoutManager(this,
                                LinearLayoutManager.HORIZONTAL, false);
                recommendedRecyclerView.setLayoutManager(recommendedLayoutManager);
                recommendedAdapter = new RecommendedAdapter(getRecommendedApps());
                recommendedRecyclerView.setAdapter(recommendedAdapter);
        }

        // dummy data cho suggested apps
        private List<AppItem> getSuggestedApps() {
                List<AppItem> apps = new ArrayList<>();
                apps.add(new AppItem("Mech Assembler: Zombie Swarm", "Action • Role Playing • Roguelike • Zombie", 4.8f,
                                624, R.drawable.app_icon_red));
                apps.add(new AppItem("MU: Hồng Hoá Đạo", "Role Playing", 4.8f, 339, R.drawable.app_icon_purple));
                apps.add(new AppItem("War Inc: Rising", "Strategy • Tower defense", 4.9f, 231,
                                R.drawable.app_icon_blue));
                apps.add(new AppItem("Mobile Legends: Bang Bang", "Action • MOBA • Multiplayer", 4.3f, 512,
                                R.drawable.app_icon_orange));
                apps.add(new AppItem("PUBG Mobile", "Action • Battle Royale • Shooter", 4.4f, 722,
                                R.drawable.app_icon_teal));
                apps.add(new AppItem("Genshin Impact", "Adventure • RPG • Open World", 4.5f, 1850,
                                R.drawable.app_icon_pink));
                apps.add(new AppItem("Clash of Clans", "Strategy • Multiplayer", 4.6f, 156, R.drawable.app_icon_red));
                apps.add(new AppItem("Candy Crush Saga", "Puzzle • Casual", 4.5f, 89, R.drawable.app_icon_purple));
                apps.add(new AppItem("Free Fire MAX", "Action • Battle Royale", 4.2f, 895, R.drawable.app_icon_blue));
                apps.add(new AppItem("Call of Duty Mobile", "Action • Shooter • Multiplayer", 4.5f, 1200,
                                R.drawable.app_icon_orange));
                apps.add(new AppItem("Among Us", "Social • Multiplayer • Strategy", 4.2f, 77,
                                R.drawable.app_icon_teal));
                apps.add(new AppItem("Roblox", "Adventure • Sandbox • Multiplayer", 4.4f, 138,
                                R.drawable.app_icon_pink));
                return apps;
        }

        // dummy data cho recommended apps
        private List<AppItem> getRecommendedApps() {
                List<AppItem> apps = new ArrayList<>();
                apps.add(new AppItem("Suno - AI Music & Song", "", 0f, 0, R.drawable.app_icon_orange));
                apps.add(new AppItem("Claude by Anthropic", "", 0f, 0, R.drawable.app_icon_teal));
                apps.add(new AppItem("DramaBox - Watch TV", "", 0f, 0, R.drawable.app_icon_pink));
                apps.add(new AppItem("Photo Editor Pro", "", 0f, 0, R.drawable.app_icon_blue));
                apps.add(new AppItem("Music Player", "", 0f, 0, R.drawable.app_icon_red));
                apps.add(new AppItem("TikTok", "", 0f, 0, R.drawable.app_icon_purple));
                apps.add(new AppItem("Instagram", "", 0f, 0, R.drawable.app_icon_orange));
                apps.add(new AppItem("Facebook", "", 0f, 0, R.drawable.app_icon_blue));
                apps.add(new AppItem("WhatsApp", "", 0f, 0, R.drawable.app_icon_teal));
                apps.add(new AppItem("Telegram", "", 0f, 0, R.drawable.app_icon_pink));
                apps.add(new AppItem("Spotify", "", 0f, 0, R.drawable.app_icon_red));
                apps.add(new AppItem("YouTube Music", "", 0f, 0, R.drawable.app_icon_purple));
                apps.add(new AppItem("Netflix", "", 0f, 0, R.drawable.app_icon_red));
                apps.add(new AppItem("Zalo", "", 0f, 0, R.drawable.app_icon_blue));
                return apps;
        }
}
