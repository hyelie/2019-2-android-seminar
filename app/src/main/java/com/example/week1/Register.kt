package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.screen_register.*
import android.widget.Toast
import kotlinx.android.synthetic.main.screen_login.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_register)

        register_send_button.setOnClickListener{
            if(register_pw.text.toString() == register_pw_re.text.toString()){
                Toast.makeText(this, "registered!", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Check your password again!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
