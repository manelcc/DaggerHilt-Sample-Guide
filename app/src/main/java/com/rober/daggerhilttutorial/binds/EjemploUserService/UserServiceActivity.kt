package com.rober.daggerhilttutorial.binds.EjemploUserService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rober.daggerhilttutorial.R
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.activity_user_service.*
import javax.inject.Inject
import javax.inject.Qualifier

@AndroidEntryPoint
class UserServiceActivity : AppCompatActivity() {

    @UserServiceUno
    @Inject lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_service)

        user_service_bind_text.text = userService.helloWorld()
        println(userService.helloWorld())
    }
}

interface UserService {
    fun helloWorld(): String
}

class UserServiceUnoImpl @Inject constructor(): UserService{
    override fun helloWorld(): String {
        return "Hello World from UserServiceUnoImpl"
    }
}

class UserServiceDosImpl @Inject constructor(): UserService{
    override fun helloWorld(): String {
        return "Hello World from UserServiceDosImpl"
    }
}

@InstallIn(ActivityComponent::class)
@Module
abstract class UserServiceModule {

    @UserServiceUno
    @Binds
    @ActivityScoped
    abstract fun bindUserServiceUno(userServiceImpl: UserServiceUnoImpl):UserService

    @UserServiceDos
    @Binds
    @ActivityScoped
    abstract fun bindUserServiceDos(userServiceImpl: UserServiceDosImpl):UserService
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserServiceUno

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserServiceDos