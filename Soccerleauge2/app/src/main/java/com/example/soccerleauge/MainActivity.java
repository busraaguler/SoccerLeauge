package com.example.soccerleauge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView TextViewResult;
    private Button DrawFixture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextViewResult=findViewById(R.id.text_view_result);
        DrawFixture=findViewById(R.id.draw_fixture);


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.107:3000/")
                .addConverterFactory(GsonConverterFactory.create())     //Json verileri url adresinden çekilir.
                .build();

        TeamsApi teamsApi=retrofit.create(TeamsApi.class);

        Call<List<Team>> call=teamsApi.getTeams();            //interface'deki fonksiyonlar çağrıldı.

        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {   //İsteğimiz başarılı olduğunda ve sunucudan bir cevap döndüğünde çalışıcaktır.

                if(!response.isSuccessful()){                                   //ve dönen değerler response nesnesi üzerinden kontrol edilicektir.
                    TextViewResult.setText("Code:"  + response.code());
                    return;
                }
                List<Team> teams=response.body();
                for(Team team: teams){
                    String content="";
                    //content+= "Userid: " +post.getUserId() +" \n";
                    content+="id: " + team.getId()+"\n";
                    content+="name: " + team.getName() + "\n";
                    //content+="Body: " +post.getBody()+ "\n\n";
                    TextViewResult.append(content);                    //TextViewCevap 'a posts verileri eklenir.
                }
            }
            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {    //İsteğimiz başarılı olmadığında yani sunucudan bir cevap alınmadığında çalışıcaktır.
                TextViewResult.setText(t.getMessage());                        //Throwable objesi üzerinden getMessage()  metodu çağırılır.
            }
        });





    }
}