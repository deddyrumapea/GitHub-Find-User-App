package com.romnan.githubfinduser.feature_search_user.presentation

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.core.util.EspressoIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SearchUsersActivityTest {
    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        ActivityScenario.launch(SearchUsersActivity::class.java)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun submitQueryToSearchUsers_showingProgressBar() {
        val query = "deddyr"
        onView(withId(R.id.sv_user)).perform(typeSearchViewText(query), closeSoftKeyboard())
        onView(withId(R.id.pb_users_list)).check(matches(isDisplayed()))
    }

    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, true)
            }
        }
    }
}