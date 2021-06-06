package com.example.pristencare

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import com.example.pristencare.databinding.ActivityNextBinding
import kotlin.math.max
import kotlin.math.min

class NextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextBinding
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.image.loadUrl(intent.getStringExtra("url"))


        scaleGestureDetector =
            ScaleGestureDetector(this, object : ScaleGestureDetector.OnScaleGestureListener {
                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                    return true
                }

                override fun onScaleEnd(detector: ScaleGestureDetector?) {}

                override fun onScale(detector: ScaleGestureDetector?): Boolean {

                    mScaleFactor *= scaleGestureDetector.scaleFactor
                    mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
                    binding.image.scaleX = mScaleFactor
                    binding.image.scaleY = mScaleFactor
                    return true
                }

            })

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }
}