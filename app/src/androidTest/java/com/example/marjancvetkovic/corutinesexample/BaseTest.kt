package com.example.marjancvetkovic.corutinesexample

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.marjancvetkovic.corutinesexample.db.AppDatabase
import com.example.marjancvetkovic.corutinesexample.db.BmfDao
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.network.BmfApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import java.io.IOException

open class BaseTest {
    @JvmField
    val activityRuleMainActivity = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var targetContext: Context
    private lateinit var mockedWebServer: MockWebServer
    private lateinit var bmfDao: BmfDao

    @Before
    fun setup() {
        val mInstrumentation = InstrumentationRegistry.getInstrumentation()
        targetContext = mInstrumentation.targetContext

        bmfDao = AppDatabase.getInstance(targetContext).officeDao()

        bmfDao.deleteAll()

        targetContext.getSharedPreferences("main", Context.MODE_PRIVATE).edit {
            putLong(BmfRepo.BMF_LIST_KEY, 0)
        }
    }

    fun mockWebServerAndDownloadData(fileName: String) {
        mockWebServer()
        val response = try {
            readJsonFile(fileName)
        } catch (e: IOException) {
            ""
        }
        mockedWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(response)
        )

    }

    private fun mockWebServer() {
        mockedWebServer = MockWebServer()
        try {
            mockedWebServer.start()
        } catch (e: IOException) {
            Log.e("", e.message)
        }

        BmfApi.BASE_URL = mockedWebServer.url("/").toString()
    }

    fun launchMainActivity() {
        val intent = Intent()
        activityRuleMainActivity.launchActivity(intent)
    }

    private fun checkListViewSize(size: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(listView: View): Boolean {
                return listView is RecyclerView && listView.adapter?.itemCount == size
            }

            override fun describeTo(description: Description) {
                description.appendText("Check RecyclerView size.")
            }
        }
    }

    fun assertRecyclerViewSize(recyclerViewId: Int, size: Int) {
        Espresso.onView(ViewMatchers.withId(recyclerViewId)).check(ViewAssertions.matches(checkListViewSize(size)))
    }

    private fun readJsonFile(filename: String): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(filename)
        val source = Okio.buffer(Okio.source(inputStream))
        val result = source.readUtf8()
        source.close()
        return result
    }
}
