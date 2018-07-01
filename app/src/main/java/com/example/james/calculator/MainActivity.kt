package com.example.james.calculator


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.R.id.button3
import android.R.id.button2
import android.R.id.button1
import android.annotation.SuppressLint


class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal var valueOne = 0.0
    internal var valueTwo = 0.0
    internal var operatorLast = false
    internal var lastOperator: String? = ""
    internal var mDisplay: TextView? = null
    internal var mFunctionDisplay: TextView? = null

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        super.onSaveInstanceState(savedInstanceState)

        val mDisplay = findViewById<TextView>(R.id.tvNumber)
        val mFunctionDisplay = findViewById<TextView>(R.id.tvResult)

        savedInstanceState.putDouble("VALUE_ONE", valueOne)
        savedInstanceState.putDouble("VALUE_TWO", valueTwo)
        savedInstanceState.putString("M_DISPLAY", mDisplay.text.toString())
        savedInstanceState.putString("M_FUNCTION_DISPLAY", mFunctionDisplay.text.toString())
        savedInstanceState.putString("LAST_OPERATOR", lastOperator)
        savedInstanceState.putBoolean("OPERATOR_LAST", operatorLast)
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mDisplay = findViewById<TextView>(R.id.tvNumber)
        val mFunctionDisplay = findViewById<TextView>(R.id.tvResult)

        if (savedInstanceState != null) {
            valueOne = savedInstanceState.getDouble("VALUE_ONE")
            valueTwo = savedInstanceState.getDouble("VALUE_TWO")
            mDisplay.text = savedInstanceState.getString("M_DISPLAY")
            mFunctionDisplay.text = savedInstanceState.getString("M_FUNCTION_DISPLAY")
            lastOperator = savedInstanceState.getString("LAST_OPERATOR")
            operatorLast = savedInstanceState.getBoolean("OPERATOR_LAST")
        }

        val mZero = findViewById<Button>(R.id.tvZero)
        mZero.setOnClickListener(this)
        val mOne = findViewById<Button>(R.id.tvOne)
        mOne.setOnClickListener(this)
        val mTwo = findViewById<Button>(R.id.tvTwo)
        mTwo.setOnClickListener(this)
        val mThree = findViewById<Button>(R.id.tvThree)
        mThree.setOnClickListener(this)
        val mFour = findViewById<Button>(R.id.tvFour)
        mFour.setOnClickListener(this)
        val mFive = findViewById<Button>(R.id.tvFive)
        mFive.setOnClickListener(this)
        val mSix = findViewById<Button>(R.id.tvSix)
        mSix.setOnClickListener(this)
        val mSeven = findViewById<Button>(R.id.tvSeven)
        mSeven.setOnClickListener(this)
        val mEight = findViewById<Button>(R.id.tvEight)
        mEight.setOnClickListener(this)
        val mNine = findViewById<Button>(R.id.tvNine)
        mNine.setOnClickListener(this)
        val mPlus = findViewById<Button>(R.id.tvPlus)
        mPlus.setOnClickListener(this)
        val mMinus = findViewById<Button>(R.id.tvMinus)
        mMinus.setOnClickListener(this)
        val mMult = findViewById<Button>(R.id.tvMul)
        mMult.setOnClickListener(this)
        val mDiv = findViewById<Button>(R.id.tvDivide)
        mDiv.setOnClickListener(this)
        val mDecimal = findViewById<Button>(R.id.tvDecimal)
        mDecimal.setOnClickListener(this)
        val mClear = findViewById<Button>(R.id.tvDecimal)
        mClear.setOnClickListener(this)
        val mCompleteClear = findViewById<Button>(R.id.tvClear)
        mCompleteClear.setOnClickListener(this)
        val mEquals = findViewById<Button>(R.id.tvEquals)
        mEquals.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        val mDisplay = findViewById<TextView>(R.id.tvNumber)
        val mFunctionDisplay = findViewById<TextView>(R.id.tvResult)

        when (v.id) {
            R.id.tvZero, R.id.tvOne, R.id.tvTwo, R.id.tvThree, R.id.tvFour, R.id.tvFive, R.id.tvSix, R.id.tvSeven, R.id.tvEight, R.id.tvNine, R.id.tvDecimal -> if (mDisplay.text.length != 0 && mDisplay.text.toString().startsWith("E")) {

            } else {
                if (mDisplay.text.toString().startsWith("_")) {
                    mDisplay.text = ""
                }
                if (valueTwo == 0.0) {
                    if (operatorLast) {
                        mDisplay.text = ""
                    }
                    mDisplay.append((v as Button).text.toString())
                }
                operatorLast = false

            }
            R.id.tvPlus, R.id.tvMinus, R.id.tvMul, R.id.tvDivide -> if (mDisplay.text.length != 0 && mDisplay.text.toString().startsWith("E")) {

            } else if (mDisplay.text.length == 0) {
                mDisplay.setText(R.string.ERR)
                mFunctionDisplay.setText(R.string.functionDisplayError)

            } else {
                if (validInput(mDisplay)) {
                    if (lastOperator == "") {
                        valueOne = java.lang.Double.parseDouble(mDisplay.text.toString())
                        lastOperator = (v as Button).text.toString()
                        mDisplay.setText(R.string.underscore)
                        mFunctionDisplay.text = valueOne.toString()
                        mFunctionDisplay.append(" " + lastOperator!!)
                    } else {
                        valueTwo = java.lang.Double.parseDouble(mDisplay.text.toString())
                        evaluate()
                        lastOperator = (v as Button).text.toString()
                        mFunctionDisplay.text = valueOne.toString()
                        mFunctionDisplay.append(" " + lastOperator!!)
                        mDisplay.setText(R.string.underscore)
                    }
                } else {
                    reset(mDisplay, mFunctionDisplay)
                    mDisplay.setText(R.string.ERR)
                    mFunctionDisplay.setText(R.string.functionDisplayError)
                }
                operatorLast = true

            }
            R.id.tvClear -> if (mDisplay.text.length != 0 && mDisplay.text.toString().startsWith("E")) {

            } else if (operatorLast) {
                operatorLast = false
                lastOperator = ""
                var operatorClear = mFunctionDisplay.text.toString()
                operatorClear = operatorClear.substring(0, operatorClear.length - 2)
                mFunctionDisplay.text = ""
                mDisplay.text = operatorClear
            } else if (mDisplay.text.length == 1 && mFunctionDisplay.text.length == 0) {
                reset(mDisplay, mFunctionDisplay)
            } else {
                var displayClear = mDisplay.text.toString()
                displayClear = displayClear.substring(0, displayClear.length - 1)
                mDisplay.text = displayClear
                if (mDisplay.text.length == 0 && valueOne != 0.0) {
                    operatorLast = true
                } else if (mDisplay.text.length == 0 && valueOne == 0.0) {
                    mDisplay.setText(R.string.underscore)
                }
            }
            R.id.tvClose -> reset(mDisplay, mFunctionDisplay)
            R.id.tvEquals -> if (mDisplay.text.length != 0 && mDisplay.text.toString().startsWith("E")) {

            } else {
                if (!validInput(mDisplay)) {
                    Log.d("test", "validInput = " + validInput(mDisplay))
                    reset(mDisplay, mFunctionDisplay)
                    mDisplay.setText(R.string.ERR)
                    mFunctionDisplay.setText(R.string.functionDisplayError)
                } else if (mFunctionDisplay.text.toString().isEmpty()) {

                } else {
                    Log.d("test", "test else")
                    valueTwo = java.lang.Double.parseDouble(mDisplay.text.toString())
                    evaluate()
                    mDisplay.text = valueOne.toString()
                    mFunctionDisplay.text = ""
                    operatorLast = false
                    lastOperator = ""
                }

            }
            else -> {
            }
        }
    }


    fun validInput(view: TextView): Boolean {
        //no two operators in a row
        if (operatorLast) {
            return false
        } else if (view.text.length > 1 && view.text[0] == '0' && view.text[1] != '.') {
            return false
        } else if (view.text[0] == '_') {
            return false
        } else if (view.text.length > 0 && !onlyOneDecimal(view)) {
            return false
        }//valid number, only one decimal
        //if display is still _
        //number not starting with zero unless followed by a decimal
        //first number can't be operator (check if lastoperator = "" && valueOne = 0
        return !(lastOperator != "" && valueOne == 0.0)

    }

    fun onlyOneDecimal(view: TextView): Boolean {
        var count = 0
        for (i in 0 until view.text.length) {
            if (view.text[i] == '.') {
                count++
            }
        }
        return count <= 1

    }

    fun reset(view: TextView, view2: TextView) {
        valueOne = 0.0
        valueTwo = 0.0
        operatorLast = false
        lastOperator = ""
        view.setText(R.string.underscore)
        view2.text = ""
    }

    fun evaluate() {
        if (valueTwo != 0.0) {
            when (lastOperator) {
                "+" -> {
                    valueOne += valueTwo
                    valueTwo = 0.0
                }
                "-" -> {
                    valueOne -= valueTwo
                    valueTwo = 0.0
                }
                "*" -> {
                    valueOne *= valueTwo
                    valueTwo = 0.0
                }
                "/" -> {
                    valueOne /= valueTwo
                    valueTwo = 0.0
                }
                else -> {
                }
            }
            lastOperator = ""
        }
    }
}

