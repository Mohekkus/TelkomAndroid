package com.telkom.capex.ui.menu.search.repo

import com.telkom.capex.network.access.AccessComposer
import com.telkom.capex.network.services.SearchService
import com.telkom.capex.ui.menu.search.model.SearchContractModel
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchService: SearchService
) {

    suspend fun getSearch(query: String) : Response<SearchContractModel> =
        searchService.search(
            AccessComposer.getSearchContract("api_select_kontrak_by", query)
        )
}