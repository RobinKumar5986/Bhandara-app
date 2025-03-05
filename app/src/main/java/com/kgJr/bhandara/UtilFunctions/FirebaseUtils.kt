package com.kgJr.bhandara.UtilFunctions

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

object FirebaseUtils {
    fun sendOtp(
        phoneNumber: String,
        context: Context,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val auth = FirebaseAuth.getInstance()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")  // Always use +91 as prefix
            .setTimeout(60L, TimeUnit.SECONDS)  // Timeout duration
            .setActivity(context as Activity)   // Context cast to Activity
            .setCallbacks(callbacks)           // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}