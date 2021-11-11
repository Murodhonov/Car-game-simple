package uz.umarxon.race

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import uz.umarxon.race.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var pos = 1
    private var currentPos = 1
    private var score = 0
    private var highScore = 0
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        invisibleEnemy()
        binding.car2.visibility = View.INVISIBLE

        highScore = getSharedPreferences("db", Context.MODE_PRIVATE).getInt("highScore",0)

        binding.highScore.text = "High Score: ${highScore}"

        binding.start.visibility = View.VISIBLE

        binding.left.setOnClickListener {
            currentPos = 1
            binding.car1.visibility = View.VISIBLE
            binding.car2.visibility = View.INVISIBLE
        }

        binding.right.setOnClickListener {
            currentPos = 2
            binding.car1.visibility = View.INVISIBLE
            binding.car2.visibility = View.VISIBLE
        }

        binding.start.setOnClickListener {
            it.visibility = View.GONE
            binding.txt.visibility = View.GONE
            startGame()
        }

    }

    private fun startGame() {
        invisibleEnemy()
        continueGame()
    }

    private fun continueGame(){
        when(getRandomPos()){
            1->{
                binding.enemy1.visibility = View.VISIBLE
                binding.enemy2.visibility = View.INVISIBLE
                val anim = AnimationUtils.loadAnimation(this,R.anim.road1_anim)
                binding.enemy1.startAnimation(anim)

                anim.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        checkPos()
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })

            }
            2->{
                binding.enemy2.visibility = View.VISIBLE
                binding.enemy1.visibility = View.INVISIBLE
                val anim = AnimationUtils.loadAnimation(this,R.anim.road2_anim)
                binding.enemy2.startAnimation(anim)

                anim.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        checkPos()
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })
            }
        }
    }

    private fun checkPos(){
        if (pos == currentPos){
            invisibleEnemy()
            Toast.makeText(this, "Game Over !", Toast.LENGTH_SHORT).show()
            binding.txt.visibility = View.VISIBLE
            binding.start.visibility = View.VISIBLE
        }else{
            continueGame()

            if (score > highScore){
                score++
                highScore = score
                getSharedPreferences("db", Context.MODE_PRIVATE).edit().putInt("highScore",highScore).apply()
                binding.score.text = "Score: $score"
                binding.highScore.text = "High Score: $highScore"
            }else{
                score++
                binding.score.text = "Score: $score"
            }
        }
    }

    private fun getRandomPos():Int{
        val rand = Random.nextInt(1,3)
        pos = rand
        return rand
    }

    private fun invisibleEnemy(){
        binding.enemy1.visibility = View.INVISIBLE
        binding.enemy2.visibility = View.INVISIBLE
    }


}