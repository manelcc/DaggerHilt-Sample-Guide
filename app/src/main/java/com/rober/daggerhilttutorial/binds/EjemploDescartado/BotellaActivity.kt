package com.rober.daggerhilttutorial.binds.EjemploDescartado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rober.daggerhilttutorial.R
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.android.synthetic.main.activity_botella.*
import javax.inject.Inject

@AndroidEntryPoint
class BotellaActivity : AppCompatActivity() {

    @Inject lateinit var botella: Botella

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_botella)

        println(botella.enroscarBotella())
        textbind.text = botella.enroscarBotella()
    }
}

class Botella @Inject constructor(
    private val botellaTaponImpl: Tapon
){
    fun enroscarBotella(): String{
        return botellaTaponImpl.enroscarBotella()
    }
}

class BotellaTaponImpl @Inject constructor():
    Tapon {
    override fun enroscarBotella(): String {
        return "Enroscando botella, gracias por proporcionarme la interfaz Dagger!"
    }
}

interface Tapon{
    fun enroscarBotella(): String
}

@InstallIn(ActivityComponent::class)
@Module
abstract class BotellaModule {

    @Binds
    abstract fun bindDependendiaBotella(
        botellaTaponImpl: BotellaTaponImpl
    ): Tapon
}