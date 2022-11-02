package com.telkom.capex.etc

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreImplementator {
    const val TAG = "FIRESTORE IMPL"
    fun ref() = Firebase.firestore
}