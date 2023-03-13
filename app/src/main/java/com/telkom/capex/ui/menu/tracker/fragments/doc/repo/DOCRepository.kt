package com.telkom.capex.ui.menu.tracker.fragments.doc.repo

import com.telkom.capex.network.access.AccessComposer
import com.telkom.capex.network.services.TrackerService
import javax.inject.Inject

class DOCRepository @Inject constructor(private val trackerService: TrackerService) {


    suspend fun getDOC() = trackerService.getDOC(
        AccessComposer.getDoc(
            "api_all_doc",
            1
        )
    )

    suspend fun getDOC(page: Int) = trackerService.getDOC(
        AccessComposer.getDoc(
            "api_all_doc",
            page
        )
    )

    suspend fun getDOCSearch(pattern: String) = trackerService.getDOCSearch(
        AccessComposer.searchDoc(
            "api_all_doc_search",
            1,
            pattern
        )
    )

    suspend fun getDOCDetail(query: String) = trackerService.getDOC(
        AccessComposer.detailDoc(
            "api_select_doc_by",
            query
        )
    )
}