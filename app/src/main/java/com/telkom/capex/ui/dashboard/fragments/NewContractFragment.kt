package com.telkom.capex.ui.dashboard.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.telkom.capex.databinding.ComponentContractInputNameBinding
import com.telkom.capex.databinding.ComponentContractSelectUnitBinding
import com.telkom.capex.databinding.FragmentNewContractBinding
import java.text.DecimalFormat

class NewContractFragment : Fragment() {

    lateinit var binding: FragmentNewContractBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewContractBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    private var current: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            edittextAmount.apply {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        currencyTextWatcherHandler(this, this@apply, s)
                    }
                })
            }
            contractRequirement.apply {
                ComponentContractSelectUnitBinding.inflate(layoutInflater).apply {

                    spinnerActivator.setOnClickListener {
                        unitSelection.performClick()
                    }
                    addView(this.root)
                }
                ComponentContractInputNameBinding.inflate(layoutInflater).apply {

                    tietContract.apply {
                        onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                            if (b) tilContract.helperText = ""
                            else tilContract.helperText = "Tap to input new contract header"
                        }
                    }
                    addView(this.root)
                }
            }
        }
    }

    private fun currencyTextWatcherHandler(
        textWatcher: TextWatcher,
        editText: EditText,
        s: Editable?
    ) {
        editText.apply {
            if (s.isNullOrEmpty()) {
                val r = "0"
                current = r
                setText(r)
                setSelection(r.length)
            }
            else if (s.toString() != current) {
                textWatcher.apply {
                    removeTextChangedListener(this)

                    val cleanString: String = s.replace("""[$,.]""".toRegex(), "")

                    val parsed = cleanString.toDouble()
                    val formatted = DecimalFormat("#,###").format(parsed)

                    val idr = formatted.replace("""[$,.]""".toRegex(), ".")

                    current = idr
                    setText(idr)
                    setSelection(idr.length)

                    addTextChangedListener(this)
                }
            }
        }
    }
}