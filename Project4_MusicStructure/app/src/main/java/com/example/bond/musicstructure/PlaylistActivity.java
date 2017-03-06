package com.example.bond.musicstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.title_playlist);
        Button btn_artist = (Button) findViewById(R.id.artist);
        Button btn_genre = (Button) findViewById(R.id.genre);

        btn_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, ArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, GenreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
