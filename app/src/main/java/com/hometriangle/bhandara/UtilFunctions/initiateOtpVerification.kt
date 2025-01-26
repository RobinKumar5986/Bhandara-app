package com.hometriangle.bhandara.UtilFunctions

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

fun initiateOtpVerification(
    phoneNumber: String,
    context: Context,
    onOtpResult: (OtpStatus, String?) -> Unit
) {
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("FirebaseAuth", "Verification Completed: $credential")
            onOtpResult(OtpStatus.OTP_VERIFIED, null)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Verification failed
            Log.e("FirebaseAuth", "Verification Failed: ${e.message}")
            Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
            onOtpResult(OtpStatus.OTP_FAILURE, null)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // Code sent successfully
            Log.d("FirebaseAuth", "Code Sent: Verification ID: $verificationId")
            Toast.makeText(context, "Code sent to +91$phoneNumber", Toast.LENGTH_SHORT).show()
            onOtpResult(OtpStatus.OTP_SENT, verificationId)
        }
    }
    FirebaseUtils.sendOtp(phoneNumber = phoneNumber, context = context, callbacks = callbacks)
}


enum class OtpStatus {
    OTP_VERIFIED,
    OTP_FAILURE,
    OTP_SENT
}