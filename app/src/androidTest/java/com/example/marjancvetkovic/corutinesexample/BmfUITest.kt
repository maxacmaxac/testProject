package com.example.marjancvetkovic.corutinesexample

import org.junit.Test

class BmfUITest : BaseTest() {
    companion object {
        const val RECYCLER_VIEW_SIZE_0 = 0
        const val RECYCLER_VIEW_SIZE_3 = 3

        const val FILE_OK_RESPONSE_PATH = "response_ok.json"
        const val FILE_BAD_RESPONSE_PATH = "response_bad.json"
    }

    @Test
    fun testRecyclerViewSizeResponseOK() {
        mockWebServerAndDownloadData(FILE_OK_RESPONSE_PATH)

        launchMainActivity()

        assertRecyclerViewSize(R.id.listView, RECYCLER_VIEW_SIZE_3)
    }

    @Test
    fun testRecyclerViewVisibilityOnBadResponse() {
        mockWebServerAndDownloadData(FILE_BAD_RESPONSE_PATH)

        launchMainActivity()

        assertRecyclerViewSize(R.id.listView, RECYCLER_VIEW_SIZE_0)
    }
}
