package com.anwesome.games.kotlincolorlist

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var customView:CustomView = CustomView(this)
        customView.addColor(Color.RED)
        customView.addColor(Color.GREEN)
        customView.addColor(Color.BLUE)
        customView.addColor(Color.CYAN)
        setContentView(customView)
    }
}
