package com.app.proofofconcept

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.app.proofofconcept.ui.home.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// Tests for HomeActivity
@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityInstrumentationTest {

//    private var mIdlingResource: IdlingResource? = null

    @get:Rule
    private val activityRule = ActivityTestRule(
        HomeActivity::class.java, false, false)
    private lateinit var fetchingIdlingResource : IdlingResource

    @Before
    fun setup() {
        activityRule.launchActivity(null)
        fetchingIdlingResource = activityRule.activity.getIdlingResource()
        IdlingRegistry.getInstance().register(fetchingIdlingResource)
    }


    @After
    fun unregisterIdlingResource() {
            IdlingRegistry.getInstance().unregister(fetchingIdlingResource)
    }

//    @Test
//    fun validateData() {
//        onView(withId(R.id.rv_items)).check(matches(hasDescendant(withText("Beavers"))))
//    }

    @Test
    @Throws(Exception::class)
    fun pullToRefresh_shouldPass() {
        onView(withId(R.id.swipe_to_refresh)).perform(swipeDown())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.no_internet_connection)))
    }

}