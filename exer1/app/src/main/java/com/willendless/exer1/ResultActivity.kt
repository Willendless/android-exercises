package com.willendless.exer1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.group
import kotlinx.android.synthetic.main.activity_result.guestTeam
import kotlinx.android.synthetic.main.activity_result.homeTeam

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun actionStart(context: Context, group: String, homeTeam: String, guestTeam: String,
                        prediction: String) {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("group", group)
            intent.putExtra("homeTeam", homeTeam)
            intent.putExtra("guestTeam", guestTeam)
            intent.putExtra("prediction", prediction)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        group.text = intent.getStringExtra("group")
        homeTeam.text = intent.getStringExtra("homeTeam")
        guestTeam.text = intent.getStringExtra("guestTeam")
        prediction.text = intent.getStringExtra("prediction")
        back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        finish()
    }
}