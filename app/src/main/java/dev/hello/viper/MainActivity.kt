package dev.hello.viper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_add.text = hello(4, 5)
        hello(4, 8)
    }

    fun hello(string1: Int, string2: Int): String {
        Log.e("add--", (string1 + string2).toString())
        return (string1 + string2).toString()
    }
}
