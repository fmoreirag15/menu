package com.example.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements pokeballFragment.OnFragmentItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener{
    pokeballFragment pokeballFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView usuario;
    View objView;
    ImageView  imagenPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aplicación Pokemon");
        navigationView = findViewById(R.id.nested);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        pokeballFragment = new pokeballFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, pokeballFragment);
        fragmentTransaction.commit();

        Bundle bundle = this.getIntent().getExtras();
        System.out.println("Datos: "+bundle.getString("inf"));

        String str=bundle.getString("inf");
        try {
            JSONObject jsonObject = new JSONObject(str);
            System.out.println(jsonObject.get("user"));
            objView=navigationView.getHeaderView(0);
            usuario=objView.findViewById(R.id.textView3);
            imagenPerfil=objView.findViewById(R.id.profile_image);
            String imagenes=jsonObject.get("img").toString();
            Picasso.get().load(imagenes).resize(100,100).centerCrop().into(imagenPerfil);
            usuario.setText("Maestro pokemon: "+jsonObject.get("user").toString());
            int tipo=Integer.parseInt(jsonObject.get("type").toString());
            if(tipo==1) {
                Menu nav_menu = navigationView.getMenu();
                MenuItem item = nav_menu.findItem(R.id.Eliminar_registros);
                MenuItem item_2 = nav_menu.findItem(R.id.modificar_usuario);
                MenuItem item_3 = nav_menu.findItem(R.id.lista_pokemon);
                item_2.setVisible(false);
                item_3.setVisible(false);
                item.setVisible(false);
            }else if(tipo==0)
            {
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //txtWelcome.setText(getIntent().getIntExtra("inf",0));
    }
    @Override
    public void onButtonSelected() {
        Toast.makeText(this, "Start New Activity. (Static Fragment are used)", Toast.LENGTH_SHORT).show();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,new squirtleFragment());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        closeDrawer();
        if(menuItem.getItemId() == R.id.inf_pokemon){
            loadFragment(new pokeballFragment());
            getSupportActionBar().setTitle("Menu Pokemon");
        }
        if(menuItem.getItemId() == R.id.lista_pokemon){
            loadFragment(new squirtleFragment());
            getSupportActionBar().setTitle("Lista Pokemon");
        }
        if(menuItem.getItemId() == R.id.modificar_usuario){
            loadFragment(new PikachuFragment());
            getSupportActionBar().setTitle("Modificar Pokemon");
        }
        return true;
    }
    private void loadFragment(Fragment secondFragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,secondFragment);
        fragmentTransaction.commit();
    }
    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }
}
