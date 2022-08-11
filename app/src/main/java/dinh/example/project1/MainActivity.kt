package dinh.example.project1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import java.util.*


class MainActivity : AppCompatActivity() {
    private var colorLayout : ConstraintLayout? = null
    private var colorMainLayout : ConstraintLayout? = null
    private var colorMainAnim : ValueAnimator? = null
    private var ANIMATIONBACKGROUND_DURATION = 3000
    private var handler: Handler? = null
    private var mediaPlayer: MediaPlayer?= null
    private var anRotate: Animation? = null
    private var anMove: Animation? = null
    private var anMove1: Animation? = null
    private var anMove2: Animation? = null
    private var timer: Timer? = null
    private var imageView: ImageView? = null

    private var springFadein: Animation? = null
    private var springFadeout: Animation? = null
    private var summerFadein: Animation? = null
    private var summerFadeout: Animation? = null
    private var autumnFadein: Animation? = null
    private var autumnFadeout: Animation? = null
    private var winterFadein: Animation? = null

    lateinit var textViewTime: TextView
    lateinit var imageViewWheel: ImageView
    lateinit var imageViewSun: ImageView
    lateinit var imageViewCloud: ImageView
    lateinit var imageViewBird: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        colorLayout = findViewById<View>(R.id.frameLayout) as ConstraintLayout


        colorMainLayout = findViewById<View>(R.id.MainLayout) as ConstraintLayout
        colorMainAnim = ObjectAnimator.ofInt(
            colorMainLayout, "backgroundColor",
            Color.parseColor("#ADD8E6"), //lightblue
            Color.parseColor("#191970"),  //MidnightBlue
        )

        imageView = findViewById(R.id.imageViewBackground) as ImageView


        textViewTime = findViewById(R.id.textViewTime)
        handler = object: Handler() {
            override fun handleMessage(msg: Message) {
                val bundle = msg.data
                val string1 = bundle.getString("myKey")
                textViewTime.text = string1
            }
        }

        imageViewWheel = findViewById(R.id.imageViewWheel)
        imageViewBird = findViewById(R.id.imageViewBird)
        imageViewCloud = findViewById(R.id.imageViewCloud)
        imageViewSun =  findViewById(R.id.imageViewSun)

        anRotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        anMove = AnimationUtils.loadAnimation(this,R.anim.move)
        anMove1 = AnimationUtils.loadAnimation(this,R.anim.move1)
        anMove2 = AnimationUtils.loadAnimation(this,R.anim.move2)

        springFadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        springFadeout = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)
        summerFadein = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
        summerFadeout = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)
        autumnFadein = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
        autumnFadeout = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)
        winterFadein = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
    }



    fun startMainBackgroundColor() {
        colorMainAnim?.duration = ANIMATIONBACKGROUND_DURATION.toLong()
        colorMainAnim?.repeatCount = ValueAnimator.INFINITE
        colorMainAnim?.repeatMode = ValueAnimator.REVERSE
        colorMainAnim?.setEvaluator(ArgbEvaluator())
        colorMainAnim?.start()
    }
    fun stopMainBackgroundColor(){
        colorMainAnim?.pause()
    }

    fun runTime(){
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                showTime()
            }
        }, 0, 1000)
    }


    fun showTime(){
        val runable = Runnable{
            val msg = handler?.obtainMessage()
            val bundle = Bundle()
            val dateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val dateString = dateformat.format(Date())

            bundle.putString("myKey", dateString)
            msg?.data =bundle
            if (msg != null) {
                handler?.sendMessage(msg)
            }
        }

        val myThread = Thread(runable)
        myThread.start()
    }


    fun startObjectAnimation(){
        imageViewSun.startAnimation(anMove)
        imageViewCloud.startAnimation(anMove1)
        imageViewBird.startAnimation(anMove2)
        imageViewWheel.startAnimation(anRotate)

    }
    fun stopObjectAnimation(){
        imageViewSun.clearAnimation()
        imageViewCloud.clearAnimation()
        imageViewBird.clearAnimation()
        imageViewWheel.clearAnimation()
    }



    fun changeImageAndMusic(){
        imageView?.setImageResource(R.drawable.spring)
        colorLayout?.setBackgroundColor(Color.parseColor("#FF4500"))

        springFadein?.setAnimationListener(MyAnimationListener1())
        imageView?.startAnimation(springFadein)
    }

    inner class MyAnimationListener1 : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            stopMusic()

            imageView?.startAnimation(springFadeout)
            imageView?.setImageResource(R.drawable.summer)
            colorLayout?.setBackgroundColor(Color.parseColor("#8FBC8F"))

            summerFadein?.setAnimationListener(MyAnimationListener2())
            imageView?.startAnimation(summerFadein)
        }
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {
            playMusicSpring()
        }
    }
    inner class MyAnimationListener2 : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            stopMusic()

            imageView?.startAnimation(summerFadeout)
            imageView?.setImageResource(R.drawable.autumn)
            colorLayout?.setBackgroundColor(Color.parseColor("#FFFF00"))

            autumnFadein?.setAnimationListener(MyAnimationListener3())
            imageView?.startAnimation(autumnFadein)
        }
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {
            playMusicSummer()
        }
    }
    inner class MyAnimationListener3 : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            stopMusic()

            imageView?.startAnimation(autumnFadeout)
            imageView?.setImageResource(R.drawable.winter)
            colorLayout?.setBackgroundColor(Color.parseColor("#FFFFFF"))

            winterFadein?.setAnimationListener(MyAnimationListener4())
            imageView?.startAnimation(winterFadein)
        }
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {
            playMusicAutumn()
        }
    }
    inner class MyAnimationListener4 : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            stopMusic()
            changeImageAndMusic()
        }
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {
            playMusicWinter()
        }
    }

    fun stopImageAnimationAndMusic(){
        springFadein?.setAnimationListener(null)
        springFadeout?.setAnimationListener(null)
        summerFadein?.setAnimationListener(null)
        summerFadeout?.setAnimationListener(null)
        autumnFadein?.setAnimationListener(null)
        autumnFadeout?.setAnimationListener(null)
        winterFadein?.setAnimationListener(null)

        stopMusic()
    }

    fun stopMusic(){
        if(mediaPlayer?.isPlaying() == true){
            mediaPlayer?.pause()
        }
    }

    fun playMusicAutumn(){
        mediaPlayer = MediaPlayer.create(this,R.raw.autumn_song)

        if(mediaPlayer?.isPlaying() == false){
            println("autumn")
            mediaPlayer?.start()
        }
    }
    fun playMusicSpring(){
        mediaPlayer = MediaPlayer.create(this, R.raw.spring_song)

        if(mediaPlayer?.isPlaying() == false) {
            println("spring")
            mediaPlayer?.start()
        }
    }
    fun playMusicSummer(){
        mediaPlayer = MediaPlayer.create(this, R.raw.summer_song)

        if(mediaPlayer?.isPlaying() == false) {
            println("summer")
            mediaPlayer?.start()
        }
    }
    fun playMusicWinter(){
        mediaPlayer = MediaPlayer.create(this, R.raw.winter_song)

        if(mediaPlayer?.isPlaying() == false) {
            println("winter")
            mediaPlayer?.start()
        }
    }



    fun btnStart(view: View) {
        changeImageAndMusic()
        runTime()
        showTime()
        startMainBackgroundColor()
        startObjectAnimation()
    }
    fun btnStop(view: View) {
        stopImageAnimationAndMusic()
        stopMainBackgroundColor()
        stopObjectAnimation()
    }
}
