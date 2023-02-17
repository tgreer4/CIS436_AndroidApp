package com.example.a2dicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.a2dicegame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    //variables to keep track of turn, rounds and the winner
    var whoseturn = 0
    var numrounds = 0
    var winner = 0

    //total_sum in array -> had hard accessing it using player 1/2 so used the indexes instead
    var total_sums = arrayOf(0,0)
    var turn_sum = 0 //sum of dice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtaining various buttons/text boxes to use later one
        var hold_Bttn = findViewById(R.id.hold_Bttn) as Button
        var rollDiceBttn = findViewById(R.id.rollDice_bttn) as Button
        var whoseTurnbttn = findViewById(R.id.whoseturn_label) as TextView
        var player1 = findViewById(R.id.player1Total) as TextView
        var player2 = findViewById(R.id.player2Total) as TextView
        var numberRounds = findViewById(R.id.num_rounds) as TextView

        rollDiceBttn.setOnClickListener {

            //rolling and displaying dice info
            var diceVal1 = Random.nextInt(1, 6)
            var diceVal2 = Random.nextInt(1, 6)

            var imageName1 = "@drawable/dice" + diceVal1
            var imageName2 = "@drawable/dice" + diceVal2

            var resourceID1 = resources.getIdentifier(imageName1, "drawable", getPackageName())
            var resourceID2 = resources.getIdentifier(imageName2, "drawable", getPackageName())

            //calulations and output statement
            whoseTurnbttn.setText("Player " + (whoseturn) .toString() + "\'s turn.")
            numberRounds.setText("Round Number: " + (numrounds) .toString())
            calculate_total(diceVal1, diceVal2, whoseturn)
            gameEnd()

            binding.ivDie1.setImageResource(resourceID1)
            binding.ivDie2.setImageResource(resourceID2)
        }//end event handler

        binding.holdBttn.setOnClickListener {
            total_sums[whoseturn] = total_sums[whoseturn] + turn_sum
            player1.setText(total_sums[0].toString()) //outputs each players score
            player2.setText(total_sums[1].toString())
            turn_sum = 0
            change_player(whoseturn)

            binding.turnTotal.text = turn_sum.toString()
            whoseTurnbttn.setText("Player " + (whoseturn) .toString() + "\'s turn.") //denotes which player's turn

            //ensures that both buttons are enbaled in case priorly disabled
            hold_Bttn.isEnabled = true
            rollDiceBttn.isEnabled = true
        }
    }

    private fun calculate_total(num1 :Int, num2 : Int, turn : Int){ //calculates the total of a dice roll, void function
        var hold_Bttn2 = findViewById(R.id.hold_Bttn) as Button
        var rollBttn = findViewById(R.id.rollDice_bttn) as Button
        if ((num1 == 1) && (num2 ==1)){
                turn_sum = 0
                total_sums[turn] = 0
                binding.turnTotal.text = turn_sum.toString()
                rollBttn.isEnabled = false
            }

        else if (num1 == 1 || num2 == 1){
            turn_sum = turn_sum + num1 + num2
            binding.turnTotal.text = turn_sum.toString()
            hold_Bttn2.isEnabled = true //ensures that hold is enbaled in case priorly disabled
            rollBttn.isEnabled=false
        }

        else if(num1 == num2){
            turn_sum = turn_sum + num1 + num2
            hold_Bttn2.isEnabled = false
            binding.turnTotal.text = turn_sum.toString()
        }


        else {
            turn_sum = turn_sum + num1 + num2
            binding.turnTotal.text = turn_sum.toString()
            hold_Bttn2.isEnabled = true
        }
    } //end of calculate function

    private fun change_player(player : Int){ //void function, changes whoseturn variable
        if(player == 1) whoseturn = 0 else whoseturn = 1
        numrounds ++
    } //end of change_player

    private fun gameEnd() { //checks if 40 rounds has occurred or a player has reached 50 points, void function

        //get button variables by ID to diasble them when game ends
        var hold_Bttn = findViewById(R.id.hold_Bttn) as Button
        var rollBttn = findViewById(R.id.rollDice_bttn) as Button
        var whoseTurnbttn = findViewById(R.id.whoseturn_label) as TextView

        if (numrounds > 41 || total_sums[0] > 51 || total_sums[1] > 51) { //conditions to end game
            rollBttn.isEnabled = false
            hold_Bttn.isEnabled = false
            if (total_sums[0] > total_sums[1]) winner = 0 else winner = 1
            whoseTurnbttn.setText("Game has ended. Player " + (whoseturn).toString() + "\'s won.")
        }
    } //end of gameEnd function
}