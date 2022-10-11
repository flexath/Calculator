package com.flexath.calculator.ui.menu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flexath.calculator.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.feedback_bottom_dialog.*
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedbackSetting()
        checkForUpdatesSetting()
        privacyOfUs()
    }

    private fun feedbackSetting() {
        btnFeedback.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity(),R.style.FeedbackBottomDialogStyle)
            val bottomView = layoutInflater.inflate(R.layout.feedback_bottom_dialog,null,true)
            dialog.setContentView(bottomView)

            val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm)
            val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
            val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)

            btnConfirm?.setOnClickListener {
                val stars = ratingBar?.rating.toString()
                Toast.makeText(requireContext(),"Thank you for your feedback: $stars",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            btnCancel?.setOnClickListener {
                Toast.makeText(requireContext(),"Please Rate our app",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun checkForUpdatesSetting() {
        btnCheckUpdate.setOnClickListener {
            Intent(Intent.ACTION_VIEW).also {intent ->
                intent.setData(Uri.parse("https://github.com/flexath/Calculator"))
                startActivity(intent)
            }
        }
    }

    private fun privacyOfUs() {
        btnPrivacy.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setMessage("There is not privacy , just kidding!")
                .create()
                .show()
        }
    }
}