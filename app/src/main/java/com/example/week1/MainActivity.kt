package com.example.week1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_scrren.*
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.content.Intent
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.screen_login.*

class MainActivity : AppCompatActivity() {


    var ID : String = ""
    var PW : String = ""

    private lateinit var pref: SharedPreferences
    private lateinit var pref_edit: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_scrren)



        pref = getSharedPreferences("auto_login_saved_pref", Context.MODE_PRIVATE)
        pref_edit = pref.edit()

        login_button.setOnClickListener {
            ID = login_Id.text.toString()
            PW = login_pw.text.toString()

            if(ID == "poapper" && PW == "poapper"){
                //2: check되어있을 때, sharedpreference에 추가
                if(auto_login_check.isChecked){
                    pref_edit.putString("auto_id", ID)
                    pref_edit.putString("auto_pw", PW)
                    pref_edit.putBoolean("auto_login", true)
                }
                
                else{
                    pref_edit.clear()
                }
                //마지막에는 commit해야함
                pref_edit.commit()


                val login_info_intent = Intent(this, LogIn::class.java)
                login_info_intent.putExtra("login_passid", ID)
                login_info_intent.putExtra("login_passpw", PW)
                startActivity(login_info_intent)
                finish()
            }
            else{
                Toast.makeText(this, "Wrong Input!", Toast.LENGTH_LONG).show()
            }

        }

        register_button.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        //2. 종료 후 다시 실행했을 때를 말함
        if(pref.getBoolean("auto_login", false)){
            val login_info_intent = Intent(this, LogIn::class.java)
            login_info_intent.putExtra("login_passid", pref.getString("auto_id", ""))
            login_info_intent.putExtra("login_passpw", pref.getString("auto_pw", ""))
            startActivity(login_info_intent)
            finish()

        }



    }
}
