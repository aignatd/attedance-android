package com.faceplugin.facerecognition.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.faceplugin.facerecognition.R
import com.ocp.facesdk.FaceSDK

class HomeActivity : ComponentActivity() {
    companion object {
        private val SELECT_PHOTO_REQUEST_CODE = 1
        private val SELECT_ATTRIBUTE_REQUEST_CODE = 2
    }

    private lateinit var textWarning: TextView
    private lateinit var textEnrolledFace: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        textWarning = findViewById<TextView>(R.id.textWarning)
        textEnrolledFace = findViewById<TextView>(R.id.tv_enrolledface)
        textEnrolledFace.setVisibility(View.INVISIBLE)

        var ret =
            FaceSDK.setActivation(
                "fqRL05BKupwTX4/Q2fE8c5wn+HVz7eh7f7pXGxnQbXo8Bxy4OhbcW0NRYQXJpJ+p6fVYxdB6OU6K\n" +
                        "RCDsWhqcImUQ9+fXdD7314NBFOg7tVh0T4GsKdrVS6989gjDSwDQKxhhyZ7RXbV2a0GmHx6eyfLs\n" +
                        "bugMfje6bXfUA8G9qs0yyOomahS/0x2PUIrSPINbk/JhDeRtFzfUvORBjte1lsxAR5SB/h68veUW\n" +
                        "M7jhfT6Gl/uk/ekK7VXvcZeGcWYW9Ig22+y51OPQNBS/vfo8ENj9xJjFG1AXEHCzYxK9EwC2ZZSi\n" +
                        "6gTif7XYJMGwuFej1TyQ4wnZsLSlx5pdJDuRtw==",
            )

        if (ret == FaceSDK.SDK_SUCCESS) {
            ret = FaceSDK.init(assets)
        }

        if (ret != FaceSDK.SDK_SUCCESS) {
            textWarning.visibility = View.VISIBLE
            if (ret == FaceSDK.SDK_LICENSE_KEY_ERROR) {
                textWarning.text = "Invalid license!"
            } else if (ret == FaceSDK.SDK_LICENSE_APPID_ERROR) {
                textWarning.text = "Invalid error!"
            } else if (ret == FaceSDK.SDK_LICENSE_EXPIRED) {
                textWarning.text = "License expired!"
            } else if (ret == FaceSDK.SDK_NO_ACTIVATED) {
                textWarning.text = "No activated!"
            } else if (ret == FaceSDK.SDK_INIT_ERROR) {
                textWarning.text = "Init error!"
            }
        }

        findViewById<LinearLayout>(R.id.ll_identify).setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.ll_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.ll_capture).setOnClickListener {
            startActivity(Intent(this, CaptureActivity::class.java))
        }
    }
}
