package com.telkom.capex.ui.menu.contract.repo

import com.telkom.capex.network.services.ContractService
import javax.inject.Inject

class ContractRepository @Inject constructor(
    private val service: ContractService
)