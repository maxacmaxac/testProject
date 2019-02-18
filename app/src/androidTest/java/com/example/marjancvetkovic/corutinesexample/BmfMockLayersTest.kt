package com.example.marjancvetkovic.corutinesexample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.marjancvetkovic.corutinesexample.mock.MockBmfViewModel
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import com.example.marjancvetkovic.corutinesexample.view.fragments.BmfListFragment
import com.example.marjancvetkovic.corutinesexample.viewModel.Response
import com.google.gson.Gson
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


@ObsoleteCoroutinesApi class BmfMockLayersTest : BaseTest() {
    private lateinit var bmfViewModel: MockBmfViewModel
    private lateinit var fragment: BmfListFragment

    private lateinit var uiAutomtor: UiDevice

    @Before
    override fun setup() {
        super.setup()
        launchMainActivity()
        uiAutomtor = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        fragment = activityRuleMainActivity.activity.supportFragmentManager
            .findFragmentByTag(BmfListFragment::class.java.simpleName) as BmfListFragment
    }

    @Test
    fun testMockedData() = runBlocking {
        val elements = Gson().fromJson("response_ok.json".readJsonFile(), Array<Bmf>::class.java)
        bmfViewModel = MockBmfViewModel(Response.Success(elements.toList()))
        fragment.officeViewModel = bmfViewModel
        fragment.subscribeBmfs()
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())

        assertRecyclerViewSize(R.id.listView, BmfUITest.RECYCLER_VIEW_SIZE_3)

        uiAutomtor.setOrientationLeft()

        fragment = activityRuleMainActivity.activity.supportFragmentManager
            .findFragmentByTag(BmfListFragment::class.java.simpleName) as BmfListFragment
        fragment.officeViewModel = bmfViewModel
        fragment.subscribeBmfs()
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())

        assertRecyclerViewSize(R.id.listView, BmfUITest.RECYCLER_VIEW_SIZE_3)
    }

    @Test
    fun testMockedNoData() = runBlocking {
        bmfViewModel = MockBmfViewModel(Response.Error())
        fragment.officeViewModel = bmfViewModel
        fragment.subscribeBmfs()
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())

        assertRecyclerViewSize(R.id.listView, BmfUITest.RECYCLER_VIEW_SIZE_0)

        uiAutomtor.setOrientationLeft()

        fragment = activityRuleMainActivity.activity.supportFragmentManager
            .findFragmentByTag(BmfListFragment::class.java.simpleName) as BmfListFragment
        fragment.officeViewModel = bmfViewModel
        fragment.subscribeBmfs()
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())

        assertRecyclerViewSize(R.id.listView, BmfUITest.RECYCLER_VIEW_SIZE_0)
    }
}