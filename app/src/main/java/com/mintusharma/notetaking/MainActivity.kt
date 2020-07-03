package com.mintusharma.notetaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.info_dialog.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayInfoDialog()
    }

    private fun displayInfoDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.info_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.dialog_ok.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}