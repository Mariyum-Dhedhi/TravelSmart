package io.shiftai.travelsmart.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.shiftai.travelsmart.R

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
){
    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail = view.findViewById<EditText>(R.id.etResetPassword)
    val buttonSend = view.findViewById<Button>(R.id.sendButton)
    val buttonCancel = view.findViewById<Button>(R.id.cancelButton)

    buttonSend.setOnClickListener {
        val email = edEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}