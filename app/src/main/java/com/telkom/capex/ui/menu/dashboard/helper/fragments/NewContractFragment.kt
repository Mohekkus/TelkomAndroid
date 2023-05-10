package com.telkom.capex.ui.menu.dashboard.helper.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.telkom.capex.R
import com.telkom.capex.databinding.ComponentContractInputNameBinding
import com.telkom.capex.databinding.ComponentContractSpinnerUnitBinding
import com.telkom.capex.databinding.FragmentNewContractBinding
import java.text.DecimalFormat


class NewContractFragment : Fragment() {

    lateinit var binding: FragmentNewContractBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                ComponentContractSpinnerUnitBinding.inflate(layoutInflater).apply {
                    unitSelection.apply {
                        adapter = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.unit_list,
                            R.layout.component_contract_dropdown
                        )
                        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                pos: Int,
                                id: Long
                            ) {
                                parent?.getItemAtPosition(pos).toString().apply {
                                    unitName.text = this
//                                    Snackbar.make(
//                                        requireView().rootView,
//                                        this,
//                                        Snackbar.LENGTH_LONG
//                                    ).show()
                                }
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {}
                        }
                    }

                    spinnerActivator.setOnClickListener {
                        unitSelection.performClick()
                    }
                    addView(this.root)
                }
                ComponentContractInputNameBinding.inflate(layoutInflater).apply {

                    tietContract.apply {
                        onFocusChangeListener = View.OnFocusChangeListener { _, b ->
                            if (b) tilContract.helperText = ""
                            else tilContract.helperText = "Tap to input new contract header"
                        }
                    }
                    addView(this.root)
                }
                buttonSave.apply {
                    val validation = false

                    if (!validation) setBackgroundColor(
                        resources.getColor(R.color.white_kinda)
                    )
                    setOnClickListener {
                        if (!validation)
                            return@setOnClickListener
                    }
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