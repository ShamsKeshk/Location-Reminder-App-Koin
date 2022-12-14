package com.udacity.project4.testingUtils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(private val testingDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(testingDispatcher) {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testingDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}