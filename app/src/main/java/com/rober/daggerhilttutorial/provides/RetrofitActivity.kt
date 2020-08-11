package com.rober.daggerhilttutorial.provides

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rober.daggerhilttutorial.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

@AndroidEntryPoint
class RetrofitActivity : AppCompatActivity() {

    @Inject lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val call = userService.listUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.i("RetrofitActivity", "Users Response: ${response.body()}")
                text_retrofit_provides_users.text = response.body().toString()
            }
        })
    }
}

data class User(val userId: Long, val id: Long, val title: String, val completed:Boolean){}

interface UserService{
    @GET("todos")
    fun listUsers(): Call<List<User>>
}

@InstallIn(ActivityComponent::class)
@Module
object RetrofitModule{

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
    }
}
