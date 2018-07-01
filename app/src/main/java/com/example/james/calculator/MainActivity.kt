package com.example.james.calculator


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    val operationList: MutableList<String> = arrayListOf() //create a string array that allows me to add and remove elements
    val numberCache: MutableList<String> = arrayListOf()

    fun makeString(list: List<String>,joiner: String = "") : String {

        if (list.isEmpty()) return ""
        return list.reduce { r, s -> r + joiner + s }
    }
    fun updateDisplay(mainDisplayString: String){

        val fullCalculationString = makeString(operationList, " ")
        var fullCalculationTextView = findViewById<TextView>(R.id.tvNumber)
        fullCalculationTextView.text = fullCalculationString

        val mainTextView = findViewById(R.id.tvResult) as TextView
        mainTextView.text = mainDisplayString
    }
    fun numberClick(view: View){
        val button = view as Button
        val numberString = button.text;

        numberCache.add(numberString.toString())
        val text = makeString(numberCache)
        updateDisplay(text)
    }

}
