package References
//    private fun populateSpinner(listType: List<TypeItem?>?) {
//        when {
//            listType == null || listType.isNullOrEmpty() -> {
//                reportLayout.removeView(reportSpinner)
//                return
//            }
//            else -> {
//                val list: MutableList<String> = ArrayList()
//                list.add("Pilih Jenis Laporan")
//                for (i in listType.indices)
//                    list.add(listType[i]?.text.toString())
//
//                reportSpinner.apply {
//                    this.adapter = FormSpinnerAdapter( list, this@ReportForm, 0)
//                    dropDownVerticalOffset = (spinnerActivator.height - this.height) - 15
//                    dropDownWidth = this.width
//                    setPopupBackgroundResource(R.drawable.drwable_red__rounded_botleft_10)
//                    setPadding(
//                        0,
//                        0,
//                        0,
//                        TypedValue.applyDimension(
//                            TypedValue.COMPLEX_UNIT_DIP,
//                            20.toFloat(),
//                            Resources.getSystem().displayMetrics
//                        ).toInt()
//                    )
//                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View?,
//                            position: Int,
//                            id: Long
//                        ) {
//                            tvSpinner.apply {
//                                text = list[position]
//                                if (text.toString() == list[0])
//                                    setTextColor(Color.parseColor("#515C6F"))
//                                else setTextColor(Color.parseColor("#000000"))
//                            }
//                            partmap["type"] = when (position) {
//                                0 -> requestBodyGenerator("")
//                                else -> requestBodyGenerator(listType[position - 1]?.id.toString())
//                            }
//                        }
//
//                    }
//                }
//                spinnerActivator.setOnClickListener {
//                    reportSpinner.performClick()
//                }
//
//                utils.anim.animFadeOutRemove(loadingSpinner)
//            }
//        }
//    }