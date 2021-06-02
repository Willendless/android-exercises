package com.willendless.exer1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,  View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        group.onItemSelectedListener = this
        ack.setOnClickListener(this)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val adapter = when (parent?.getItemAtPosition(position) as String) {
            "美洲" -> ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.america))
            "亚洲" -> ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.asia))
            "欧洲" -> ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.europe))
            else -> ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item)
        }
        homeTeam.adapter = adapter
        guestTeam.adapter = adapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        homeTeam.adapter = null
        guestTeam.adapter = null
    }

    override fun onClick(v: View?) {
        Log.d("", "click")
        if (v != null && v == ack) {
            val group = group.selectedItem as String?
            if (group == null) {
                Toast.makeText(this, "请选择分组", Toast.LENGTH_SHORT).show()
                return
            }
            val homeTeam = homeTeam.selectedItem as String?
            val guestTeam = guestTeam.selectedItem as String?
            if (homeTeam == null || guestTeam == null || homeTeam == guestTeam) {
                Toast.makeText(this, "主队和客队应选择不同的队伍", Toast.LENGTH_SHORT).show()
                return
            }
            var predictResult: String? = null
            for (b in prediction.children) {
                if ((b as RadioButton).isChecked)
                    predictResult = b.text.toString()
            }
            if (predictResult == null) {
                Toast.makeText(this, "请选择比赛结果", Toast.LENGTH_SHORT).show()
                return
            }
            ResultActivity.actionStart(this, group, homeTeam, guestTeam, predictResult)
        }
    }
}