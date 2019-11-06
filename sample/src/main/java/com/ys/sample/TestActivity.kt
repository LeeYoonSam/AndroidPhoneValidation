package com.ys.sample

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ys.phoneinfo.view.DialCodeEditText
import kotlinx.android.synthetic.main.activity_text.*

class TestActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_text)

        etDialCode.hideMessageArea(true)
        etDialCode.setHint("please input your phone number")
        etDialCode.setValidationListener(object : DialCodeEditText.ValidationListener {
            override fun onValid() {
                etDialCode.getTextView().visibility = View.VISIBLE
            }

            override fun onInValid() {
                etDialCode.getTextView().visibility = View.INVISIBLE
            }

        })

        etDialCode.getEditText().run {
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("TestAcitivity", "dialcode: ${etDialCode.getDialCode()}, iso2: ${etDialCode.getIso2()}, phoneNumber: ${etDialCode.getInputPhoneNumber()}")
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
            })
        }
    }

    fun onClickEnable(view: View) {
        etDialCode.allEnable()
    }

    fun onClickDisable(view: View) {
        etDialCode.allDisable()
    }
}