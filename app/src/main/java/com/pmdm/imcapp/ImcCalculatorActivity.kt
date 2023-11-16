package com.pmdm.imcapp

import android.content.Intent
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class ImcCalculatorActivity : AppCompatActivity() {
    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView

    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

    private lateinit var tvPeso: TextView
    private lateinit var tvEdad: TextView
    private lateinit var btMenosPeso: FloatingActionButton
    private lateinit var btMasPeso: FloatingActionButton
    private lateinit var btMenosEdad: FloatingActionButton
    private lateinit var btMasEdad: FloatingActionButton

    private var peso: Double = 60.0
    private var edad: Double = 26.0

    private lateinit var acbCalcular: AppCompatButton

    private var altura: Double = 120.0

    private var isMaleSelected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)

        initComponents()
        initListeners()
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            isMaleSelected = true
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            isMaleSelected = false
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            //tvHeight.text = value.toString()
            altura = value.toDouble()
            tvHeight.text = DecimalFormat("#.##").format(value) + " cm"
        }

        btMenosPeso.setOnClickListener {
            peso = peso - 1
            setPeso()
        }

        btMasPeso.setOnClickListener {
            peso = peso + 1
            setPeso()
        }

        btMenosEdad.setOnClickListener {
            edad = edad - 1
            setEdad()
        }

        btMasEdad.setOnClickListener {
            edad = edad + 1
            setEdad()
        }

        acbCalcular.setOnClickListener {
            navigate2result(calculateIMC())
        }
    }

    private fun navigate2result(resultado: Double) {

        var texto: String
        var titulo: String

        when {
            resultado < 18.5 -> {
                titulo = getString(R.string.pesoiferior)
                texto = getString(R.string.pesoiferiordesc)
            }
            resultado in 18.5..24.9 -> {
                titulo = getString(R.string.pesonormal)
                texto = getString(R.string.pesonormaldesc)
            }
            resultado in 25.0..29.9 -> {
                titulo = getString(R.string.sobrepeso)
                texto = getString(R.string.sobrepesodesc)
            }
            resultado > 30.0 -> {
                titulo = getString(R.string.obesidad)
                texto = getString(R.string.obesidaddesc)
            }
            else -> {
                titulo = ""
                texto = ""
            }
        }

        val intent = Intent(this, ImcResultActivity::class.java)

        intent.putExtra("RESULTADO_EXTRA", resultado)
        intent.putExtra("TITULO_EXTRA", titulo)
        intent.putExtra("TEXTO_EXTRA", texto)

        startActivity(intent)
    }

    private fun calculateIMC(): Double {
        return  peso / ((altura / 100) * (altura / 100))
    }

    private fun setPeso() {
        tvPeso.setText(peso.toString())
    }

    private fun setEdad() {
        tvEdad.setText(edad.toString())
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }

    private fun getBackgroundColor(isCompSelected:Boolean):Int{
        val colorReference = if(isCompSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this,colorReference)
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)

        tvPeso = findViewById(R.id.tvPeso)
        tvEdad = findViewById(R.id.tvEdad)
        btMenosPeso = findViewById(R.id.btMenosPeso)
        btMasPeso = findViewById(R.id.btMasPeso)
        btMenosEdad = findViewById(R.id.btMenosEdad)
        btMasEdad = findViewById(R.id.btMasEdad)
        acbCalcular = findViewById(R.id.acbCalcular)
    }

    companion object {
        const val IMC_KEY = "RESULT"
    }
}