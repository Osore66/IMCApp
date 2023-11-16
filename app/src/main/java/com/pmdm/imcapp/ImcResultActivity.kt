package com.pmdm.imcapp

import android.content.Intent
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class ImcResultActivity : AppCompatActivity() {

    private lateinit var recalcular: AppCompatButton

    private lateinit var IMC: TextView
    private lateinit var textoIMC: TextView
    private lateinit var pesoIMC: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_result)
        initComponents()
        initListeners()

        var initialResult = intent.getDoubleExtra("RESULTADO_EXTRA", 0.0)
        var initialTitle = intent.getStringExtra("TITULO_EXTRA") ?: ""
        var initialTextResult = intent.getStringExtra("TEXTO_EXTRA") ?: ""

        IMC.text = DecimalFormat("#.##").format(initialResult)
        pesoIMC.text = initialTitle
        textoIMC.text = initialTextResult
    }

    private fun initComponents(){
        recalcular = findViewById(R.id.recalcular)
        IMC = findViewById(R.id.IMC)
        textoIMC = findViewById(R.id.textoIMC)
        pesoIMC = findViewById(R.id.pesoIMC)

    }

    private fun initListeners(){
        recalcular.setOnClickListener {
            cambioPantallaDosaUno()
        }
    }

    private fun cambioPantallaDosaUno(){
        intent = Intent(this,ImcCalculatorActivity::class.java)
        startActivity(intent)
    }
}