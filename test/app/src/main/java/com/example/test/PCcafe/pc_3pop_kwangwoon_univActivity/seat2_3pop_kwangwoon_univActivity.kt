package com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.my_info.*
import kotlinx.android.synthetic.main.pc_seat_info.*

class seat2_3pop_kwangwoon_univActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.pc_seat_info)


        val actionBar = supportActionBar

        actionBar!!.title = "3POP 광운대 2번"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대").child("2")
        var check=0

        val memberRef: DatabaseReference = database.getReference("member")
        var user = 0
        memberRef.child(auth.currentUser?.uid.toString()).child("좌석번호")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val value = p0?.value
                    if ("$value".equals("") == true) {
                        user = 1
                    } else {
                        user = 0
                    }
                }
            })

        myRef.child("cpu").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                cpu.setText("$value")
            }
        })

        myRef.child("ram").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                ram.setText("$value")
            }
        })

        myRef.child("사용").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                use.setText("$value")
            }
        })
/*
        close.setOnClickListener {
            finish()
        }
*/
        reservation.setOnClickListener {
            if (user == 1) {
                myRef.child("사용").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                        val value = p0?.value

                        if (value!!.equals("X") && check == 0) {
                            myRef.child("uid").setValue(auth.currentUser?.uid.toString())
                            myRef.child("사용").setValue("O")
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

                            memberRef.child(auth.currentUser?.uid.toString()).child("좌석번호").setValue("2")
                            Toast.makeText(baseContext, "예약되었습니다.", Toast.LENGTH_SHORT).show()
                            check = 1
                        } else if (check == 0) {
                            Toast.makeText(baseContext, "사용 중인 자리입니다.", Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        }
                    }
                })
            } else {
                Toast.makeText(baseContext, "사용중인 자리가 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        // return super.onSupportNavigateUp()
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
        return true
    }
}
