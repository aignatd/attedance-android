package com.faceplugin.facerecognition.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faceplugin.facerecognition.R
import com.faceplugin.facerecognition.common.validateEmail
import androidx.appcompat.app.AlertDialog.Builder
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.faceplugin.facerecognition.api.ApiClient
import com.faceplugin.facerecognition.common.parseError
import com.faceplugin.facerecognition.databinding.ActivityLoginBinding
import com.faceplugin.facerecognition.model.LoginRequest
import com.faceplugin.facerecognition.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    private var dialog: Dialog? = null
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val builder: AlertDialog.Builder = Builder(this)
        builder.setView(R.layout.progress_dialog)
        dialog = builder.create()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_compat)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun login(v: View) {
        /* validate if empty */
        if ( TextUtils.isEmpty(binding.etEmail.text) && TextUtils.isEmpty(binding.etPassword.text) ){
            binding.tlEmail.error = "Enter email"
            binding.tlPassword.error = "Enter password"
            return
        }

        if (TextUtils.isEmpty(binding.etEmail.text)) {
            binding.tlEmail.error = "Enter email"
            return
        }

        if (TextUtils.isEmpty(binding.etPassword.text)) {
            binding.tlPassword.error = "Enter password"
            return
        }

        /* validate email */
        if (!validateEmail(binding.etEmail.text.toString())) {
            binding.tlEmail.error = "Enter valid email"
            return
        }

        /* request */
        val request = LoginRequest(binding.etEmail.text.toString(), binding.etPassword.text.toString())

        /* make api call */
        val call = ApiClient().service?.login(request)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                /* dismiss progress dialog */
                dialog?.dismiss()

                /* check if request was a successs */
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.status == true) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            loginResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    /* request not success - parse error */
                    Toast.makeText(
                        this@LoginActivity,
                        parseError(response.errorBody()?.string()!!),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.localizedMessage?.let { Log.e("--->", it) }
                dialog?.dismiss()
                Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setDialog(show: Boolean) {
        if (show) dialog?.show()
        else dialog?.dismiss()
    }
}