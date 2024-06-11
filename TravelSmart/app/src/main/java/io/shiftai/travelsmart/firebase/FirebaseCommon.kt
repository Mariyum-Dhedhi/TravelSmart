package io.shiftai.travelsmart.firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val cartCollection =
        firestore.collection("user").document(auth.uid!!).collection("cart")

}