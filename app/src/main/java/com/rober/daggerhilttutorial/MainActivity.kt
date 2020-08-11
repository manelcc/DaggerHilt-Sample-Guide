package com.rober.daggerhilttutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rober.daggerhilttutorial.binds.EjemploDescartado.BotellaActivity
import com.rober.daggerhilttutorial.binds.EjemploUserService.UserServiceActivity
import com.rober.daggerhilttutorial.provides.RetrofitActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var ordenador: Ordenador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textid.text = ordenador.tengoOrdenador()
        println(ordenador.tengoOrdenador())


        button_bind_user_service.setOnClickListener {
            startActivity(Intent(this, UserServiceActivity::class.java))
        }

        button_bind_discarded_example.setOnClickListener {
            startActivity(Intent(this, BotellaActivity::class.java))
        }

        button_provide.setOnClickListener {
            startActivity(Intent(this, RetrofitActivity::class.java))
        }
    }
}

class Luz @Inject constructor(){
    fun tengoLuz(): String{
        return "Tengo luz"
    }
}

class CableEnchufe @Inject constructor(private val luz: Luz){
    fun tengoElectricidad(): String{
        return "${luz.tengoLuz()}, electricidad"
    }
}

class Ordenador @Inject constructor(private val cableEnchufe: CableEnchufe){
    fun tengoOrdenador(): String{
        return " ${cableEnchufe.tengoElectricidad()} y mi ordenador funciona!"
    }
}

