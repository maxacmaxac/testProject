package com.example.marjancvetkovic.corutinesexample

import com.example.marjancvetkovic.corutinesexample.BaseTest
import com.example.marjancvetkovic.corutinesexample.R
import org.junit.Test

class BmfUITest : BaseTest() {

    @Test
    fun testRecyclerViewSizeResponseOK() {
        mockWebServerAndDownloadData(FILE_OK_RESPONSE_PATH)

        launchMainActivity()

        assertRecyclerViewSize(R.id.listView, BaseTest.RECYCLER_VIEW_SIZE_3)
    }

    @Test
    fun testRecyclerViewVisibilityOnBadResponse() {
        mockWebServerAndDownloadData(FILE_BAD_RESPONSE_PATH)

        launchMainActivity()

        assertRecyclerViewSize(R.id.listView, BaseTest.RECYCLER_VIEW_SIZE_0)
    }
}
