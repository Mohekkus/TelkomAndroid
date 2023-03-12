package com.telkom.capex.ui.menu.dashboard.helper

import android.util.Log
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.telkom.capex.ui.menu.dashboard.helper.interfaces.DashboardInterface
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardModel
import javax.inject.Inject

class DashboardFirestore @Inject constructor() {

    fun getDoc(dashInterface: DashboardInterface) {
        val doc = Firebase.firestore.collection("Apps").document("21xKsNAMnLhEbTWbTnmD")

        doc.get(Source.DEFAULT).addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result


                Log.e("BANGST", "${result.data}")
                val pojo = result.toObject(DashboardModel::class.java)
                println(pojo?.gridMenu)
                if (pojo != null)
                    dashInterface.doWithData(pojo)
            }
        }
    }
}