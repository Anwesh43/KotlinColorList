package com.anwesome.games.kotlincolorlist

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(),OnClickListner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var customView:CustomView = CustomView(this)
        customView.addColor(Color.parseColor("#4CAF50"))
        customView.addColor(Color.parseColor("#00695C"))
        customView.addColor(Color.parseColor("#00838F"))
        customView.addColor(Color.parseColor("#0277BD"))
        customView.addColor(Color.parseColor("#0D47A1"))
        customView.addColor(Color.parseColor("#e53935"))
        customView.clickListener = this
        setContentView(customView)
    }
    override fun onClick(index:Int?) {
        Toast.makeText(this,"clicked $index",Toast.LENGTH_SHORT).show()
    }
}
