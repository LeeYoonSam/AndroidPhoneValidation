package com.ys.phoneinfo.view

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.ys.phoneinfo.Countries
import com.ys.phoneinfo.Country
import com.ys.phoneinfo.FlagAdapter
import com.ys.phoneinfo.R
import com.ys.phoneinfo.extension.getIdentifierHeight
import kotlinx.android.synthetic.main.dialcode_edittext.view.*
import java.util.*

class DialCodeEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val items = Countries.COUNTRIES
    var selectedCountry: Country

    private val mPhoneUtil = PhoneNumberUtil.getInstance()
    private var mDefaultCountryPosition = 0

    private var hideMessageArea: Boolean = false
    private var validText: String? = null
    private var inValidText: String? = null
    private var validTextColor: Int
    private var inValidTextColor: Int

    interface ValidationListener {
        fun onValid()
        fun onInValid()
    }

    private var validationListener: ValidationListener? = null

    fun setValidationListener(validationListener: ValidationListener) {
        this.validationListener = validationListener
    }

    init {
        val config = BundledEmojiCompatConfig(context)
        EmojiCompat.init(config)

        selectedCountry = items[0]

        View.inflate(getContext(), getLayoutResId(), this)
        updateLayoutAttributes()
        prepareView()

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DialCodeViewAttrs)
        validText = typedArray.getString(R.styleable.DialCodeViewAttrs_validText)
        inValidText = typedArray.getString(R.styleable.DialCodeViewAttrs_inValidText)

        validTextColor = typedArray.getColor(R.styleable.DialCodeViewAttrs_validTextColor, context.getColor(android.R.color.darker_gray))
        inValidTextColor = typedArray.getColor(R.styleable.DialCodeViewAttrs_inValidTextColor, context.getColor(android.R.color.holo_red_light))

        val spinnerBg = typedArray.getResourceId(R.styleable.DialCodeViewAttrs_spinnerBg, R.drawable.selector_edittext)
        setSpinnerBackground(spinnerBg)

        val editBg = typedArray.getResourceId(R.styleable.DialCodeViewAttrs_editBg, R.drawable.selector_edittext)
        setEditBackground(editBg)
    }

    private fun updateLayoutAttributes() {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getLayoutResId(): Int {
        return R.layout.dialcode_edittext
    }

    private fun prepareView() {
        val flagAdapter = FlagAdapter(context, items)
        spFlag.run {
            adapter = flagAdapter
            dropDownVerticalOffset = context.getIdentifierHeight("navigation_bar_height")
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedCountry = parent.adapter.getItem(position) as Country
                    mDefaultCountryPosition = position

                    isValid()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            setSelection(mDefaultCountryPosition)
        }

        setPhoneEditText()
        allEnable()
    }

    private fun setPhoneEditText() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    val inputText = s.toString()
                    if(inputText.isEmpty()) {
                        return
                    }

                    isValid()

                } catch (ignored: NumberParseException) { }
            }
        }

        etPhoneNumber.addTextChangedListener(textWatcher)

        try {
            // 유심정보를 가져옴
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCode = tm.simCountryIso
            setDefaultCountry(countryCode)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun getEditText(): EditText {
        return etPhoneNumber
    }

    fun getSpinner(): Spinner {
        return spFlag
    }

    fun getTextView(): TextView {
        return tvMessage
    }

    fun setHint(hint: String) {
        etPhoneNumber.hint = hint
    }

    fun hideMessageArea(isHide: Boolean) {
        hideMessageArea = isHide

        if(isHide) {
            tvMessage.visibility = View.GONE
            return
        }

        tvMessage.visibility = View.VISIBLE
    }

    fun getInputPhoneNumber(): String {
        return etPhoneNumber.text.toString()
    }

    fun getDialCode(): Int {
        return selectedCountry.dialCode
    }

    fun getIso2(): String {
        return selectedCountry.iso2
    }

    fun getIso3(): String {
        val local = Locale("", getIso2())
        return local.isO3Country
    }

    @Throws(NumberParseException::class)
    private fun parsePhoneNumber(number: String): Phonenumber.PhoneNumber {
        selectedCountry?.let {
            val defaultRegion = selectedCountry.iso2.toUpperCase()
            return mPhoneUtil.parseAndKeepRawInput(number, defaultRegion)
        }
    }

    private fun setDefaultCountry(countryCode: String) {
        for (i in Countries.COUNTRIES.indices) {
            val country = Countries.COUNTRIES[i]
            if (country.iso2 == countryCode) {
                selectedCountry = country
                mDefaultCountryPosition = i
                spFlag.setSelection(i)
            }
        }
    }

    fun isValid(): Boolean {
        val inputText = getInputPhoneNumber()
        if(inputText.isEmpty()) {
            return false
        }

        val isValid = mPhoneUtil.isValidNumber(parsePhoneNumber(inputText))

        if(isValid) {
            validationListener?.let {
                it.onValid()
            }

            tvMessage.text = validText ?: context.getString(R.string.default_valid_phone)
            tvMessage.setTextColor(validTextColor)
        } else {

            validationListener?.let {
                it.onInValid()
            }

            tvMessage.text = inValidText ?: context.getString(R.string.default_invalid_phone)
            tvMessage.setTextColor(inValidTextColor)
        }

        return isValid
    }

    fun allEnable() {
        setFlagEnable(true)
        setPhoneEditEnable(true)
    }

    fun allDisable() {
        setFlagEnable(false)
        setPhoneEditEnable(false)
    }

    fun setFlagEnable(status: Boolean) {
        setEnable(spFlag, status)
    }

    fun setPhoneEditEnable(status: Boolean) {
        setEnable(etPhoneNumber, status)
    }

    private fun setEnable(view: View, status: Boolean) {
        view.isSelected = status
        view.isEnabled = status
    }

    private fun setSpinnerBackground(@DrawableRes res: Int) {
        spFlag.setBackgroundResource(res)
    }

    private fun setEditBackground(@DrawableRes res: Int) {
        etPhoneNumber.setBackgroundResource(res)
    }
}
