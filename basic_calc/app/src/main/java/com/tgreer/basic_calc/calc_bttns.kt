package com.tgreer.basic_calc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tgreer.basic_calc.databinding.FragmentCalcBttnsBinding
import kotlin.math.sqrt

class calc_bttns : Fragment() {
    private lateinit var binding : FragmentCalcBttnsBinding

    var activityCallback: calc_bttnsListener? = null
    var operator = " "
    var calc_occur = false
    var tempString = "0" //used to store equation
    var holdtemp = " "
    val math_ops = listOf('+', '-', '*', '/', '√', '%')
    var button_value = " "

    interface calc_bttnsListener {
        fun onButtonClick(value: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallback = context as calc_bttnsListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement calc_bttnsListener.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalcBttnsBinding.inflate(inflater, container, false)

        binding.zeroBttn.setOnClickListener { v: View -> onClick(v) }
        binding.oneBttn.setOnClickListener { v: View -> onClick(v) }
        binding.twoBttn.setOnClickListener { v: View -> onClick(v) }
        binding.threeBttn.setOnClickListener { v: View -> onClick(v) }
        binding.fourBttn.setOnClickListener { v: View -> onClick(v) }
        binding.fiveBttn.setOnClickListener { v: View -> onClick(v) }
        binding.sixBttn.setOnClickListener { v: View -> onClick(v) }
        binding.sevenBttn.setOnClickListener { v: View -> onClick(v) }
        binding.eightBttn.setOnClickListener { v: View -> onClick(v) }
        binding.nineBttn.setOnClickListener { v: View -> onClick(v) }

        binding.addBttn.setOnClickListener { v: View -> onClick(v) }
        binding.subBttn.setOnClickListener { v: View -> onClick(v) }
        binding.multBttn.setOnClickListener { v: View -> onClick(v) }
        binding.divideBttn.setOnClickListener { v: View -> onClick(v) }
        binding.sqrtBttn.setOnClickListener { v: View -> onClick(v) }
        binding.modBttn.setOnClickListener { v: View -> onClick(v) }
        binding.equalsBttn.setOnClickListener { v: View -> onClick(v) }

        binding.decimalBttn.setOnClickListener { v: View -> onClick(v) }
        binding.cBttn.setOnClickListener { v: View -> onClick(v)}
        binding.clearEBttn.setOnClickListener { v: View -> onClick(v)}
        binding.negateBttn.setOnClickListener { v: View -> onClick(v)}

        return binding.root
    }

    private fun onClick(view: View){
        calc_occur = false
        button_value = deter_button(view)
        val partialExpr = check_string() //full is denoted by not having an x,y, and operator

        if(partialExpr && (button_value != "√")) { //if expression isn't full then add to tempString
            System.out.println("Inside if loop in onClick")
            when (button_value) {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "%"
                -> addToString(button_value)

                "+/-" -> math_calc()

                "." ->addToString(button_value)

                "=" -> return //b/c expression isn't full

                "c" -> tempString = "0"

                "ce"-> {
                    if(tempString.length == 1) tempString = "0" //account for deleting only 1 value
                    else tempString = tempString.substring(0, tempString.length - 1)
                }
            }
        }

        else {
            math_calc()
        }
        buttonClicked()
    }

    private fun deter_button(view: View): String { //determine value of button
        when (view.getId()) {
            R.id.zero_bttn -> return ("0")
            R.id.one_bttn -> return ("1")
            R.id.two_bttn -> return ("2")
            R.id.three_bttn -> return ("3")
            R.id.four_bttn -> return ("4")
            R.id.five_bttn -> return ("5")
            R.id.six_bttn -> return ("6")
            R.id.seven_bttn -> return ("7")
            R.id.eight_bttn -> return ("8")
            R.id.nine_bttn -> return ("9")
            R.id.add_bttn -> return ("+")
            R.id.sub_bttn -> return ("-")
            R.id.mult_bttn -> return ("*")
            R.id.divide_bttn -> return ("/")
            R.id.mod_bttn -> return ("%")
            R.id.clearE_bttn -> return ("ce")
            R.id.negate_bttn -> return ("+/-")
            R.id.decimal_bttn -> return (".")
            R.id.equals_bttn -> return ("=")
            R.id.c_bttn -> return ("c")
            R.id.sqrt_bttn -> return ("√")
            else -> return(" ")
        }
    }

    private fun buttonClicked() {
        activityCallback?.onButtonClick(tempString)
        if(calc_occur) tempString = "0"
    }

    private fun addToString(value: String) {
        //if tempString is empty & val is operand, then concantenate
        if (math_ops.contains(value.first())) tempString += value
        else {
            //replaces standard val of tempString to value
            if (tempString == "-0" || tempString == "0"){
                //takes only the (-) and then adds value
                when(tempString){
                "-0"-> tempString = tempString.substring(0, tempString.length - 1) + value
                "0" -> tempString = value
                }
            }
            else {
                tempString += value
            }
        }
    }

    private fun check_string(): Boolean { //check if a full expression with 2 operands
        var operatorPresent : Int
        for (i in math_ops) { //checks if any operands in tempString
            operatorPresent = tempString.indexOf(i)
            if (operatorPresent > 0) {
                operator = i.toString()
                return(operatorPresent == tempString.length - 1)//checks if operand is at last index
            }
        }
        return(true)
    }

    private fun math_calc() {
        val splitExpr = tempString.split(operator).toTypedArray()
        calc_occur = true

        when (operator) {
            "+" -> tempString = (splitExpr[0].toFloat() + splitExpr[1].toFloat()).toString()

            "-" -> tempString = (splitExpr[0].toFloat() - splitExpr[1].toFloat()).toString()

            "*" -> tempString = (splitExpr[0].toFloat() * splitExpr[1].toFloat()).toString()

            "/" -> tempString = (splitExpr[0].toFloat() / splitExpr[1].toFloat()).toString()

            "%" -> tempString = (splitExpr[0].toFloat() % splitExpr[1].toFloat()).toString()

            "√" -> { //sqrt function
                calc_occur = false
                if (tempString.toFloat() < 0) { //add a try and except statement
                    return
                } else {
                    splitExpr[splitExpr.size - 1] =
                        (sqrt(splitExpr[splitExpr.size - 1].toFloat())).toString()

                    if (tempString.length == 1) tempString = splitExpr[splitExpr.size - 1]
                    else for (a in splitExpr) tempString += a //ensures that sqrting last value in full expression will update last value
                }
            }

            "+/-" -> { //negate function
                calc_occur = false
                if (tempString.toFloat() == 0.0f) {
                    tempString = "-0"
                } else {
                    splitExpr[splitExpr.size - 1] =
                        ((splitExpr[splitExpr.size - 1].toFloat())*-1.0f).toString()
                    if (tempString.length == 1) tempString = splitExpr[splitExpr.size - 1]
                    else for (a in splitExpr) tempString += a
                }
            }
        }

        holdtemp = tempString //stores in case tempstring val will be used later
    }
}

