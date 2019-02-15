package com.example.marjancvetkovic.corutinesexample

import android.content.Context
import android.content.Intent
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
    open fun setup() {
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
            fileName.readJsonFile()
        } catch (e: IOException) {
            throw Exception("can not read file $fileName")
        }
        mockedWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(response)
        )

    }

    private fun mockWebServer() {
        mockedWebServer = MockWebServer().apply {
            start()
        }.also {
            BmfApi.BASE_URL = it.url("/").toString()
        }
    }

    fun launchMainActivity() {
        activityRuleMainActivity.launchActivity(Intent())
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

    fun String.readJsonFile(): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(this)
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readUtf8().apply { source.close() }
    }
}