package com.kgJr.bhandara.ui.layouts.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openUPIPayment(
    context: Context,
    upiId: String = "",
    payeeName: String = "Unknown",
    amount: String = "100",
    transactionNote: String = "Payment"
) {
    val upiUri = Uri.parse(
        "upi://pay?pa=$upiId&pn=$payeeName&am=$amount&cu=INR&tn=$transactionNote"
    )
    val intent = Intent(Intent.ACTION_VIEW, upiUri)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No UPI app found", Toast.LENGTH_SHORT).show()
    }
}
fun openUPIPayment2(context: Context, upiId: String = "", name: String = "Robin Kumar", amount: String = "100.00", transactionNote: String = "Payment") {
    val uniqueTr = System.currentTimeMillis().toString()
    val formattedAmount = String.format("%.2f", amount.toDouble())

    val upiUri = Uri.parse(
        "upi://pay?pa=$upiId&pn=$name&am=$formattedAmount&cu=INR&tn=$transactionNote&tr=$uniqueTr"
    )

    val intent = Intent(Intent.ACTION_VIEW, upiUri)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No UPI app found", Toast.LENGTH_SHORT).show()
    }
}
fun payUsingUpi(context: Context, amount: String = "1", upiId: String = "") {
    val uri = Uri.parse("upi://pay").buildUpon()
        .appendQueryParameter("pa", upiId)          // Your UPI ID
        .appendQueryParameter("pn", upiId)          // Your UPI ID (same as pa)
        .appendQueryParameter("mc", "1234")         // Merchant Code (optional)
        .appendQueryParameter("am", amount)         // Amount to be paid
        .appendQueryParameter("mam", amount)        // Amount (again)
        .appendQueryParameter("cu", "INR")          // Currency
        .build()

    val upiPayIntent = Intent(Intent.ACTION_VIEW).apply {
        data = uri
    }

    // Create chooser intent for selecting UPI app
    val chooser = Intent.createChooser(upiPayIntent, "Pay with")

    // Check if there is any UPI app installed and resolve activity
    if (chooser.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, "No UPI app found. Please install one to continue.", Toast.LENGTH_SHORT).show()
    }
}