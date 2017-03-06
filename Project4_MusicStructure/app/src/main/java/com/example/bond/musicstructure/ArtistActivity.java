package com.example.bond.musicstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        setTitle(R.string.title_artist);

        Button btn_genre = (Button) findViewById(R.id.genre);
        Button btn_playlist = (Button) findViewById(R.id.playlist);

        btn_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistActivity.this, GenreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistActivity.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
