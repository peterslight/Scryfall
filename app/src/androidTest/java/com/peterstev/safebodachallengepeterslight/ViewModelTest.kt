package com.peterstev.safebodachallengepeterslight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.peterstev.scryfall.data.repository.MockRepository
import com.peterstev.scryfall.data.response.Resource
import com.peterstev.scryfall.data.response.Status
import com.peterstev.scryfall.presentation.viewmodels.MockViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class ViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val textExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule =
        TestCoroutineRule()

    @Inject
    lateinit var viewModel: MockViewModel

    @Inject
    lateinit var repository: MockRepository

    @Before
    fun init() {
        hiltRule.inject()
        viewModel.fetchMockData()
    }

    @Test
    fun assert_livedata_is_not_null() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            liveData.observeForever(Observer {
                Timber.d("data is not null, name = ${it.data?.get(1)?.oracleText}")
                assertTrue(it.data != null)
            })
        }
    }

    @Test
    fun assert_livedata_size() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            liveData.observeForever(Observer {
                Timber.d("data size is == ${it.data?.size}")
                assertTrue(it.data?.size == 2)
            })
        }
    }

    @Test
    fun assert_livedata_value() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            liveData.observeForever(Observer {
                Timber.d("name is == ${it.data?.get(1)?.name}")
                assertTrue(it.data?.get(0)?.name.equals("duress", true))
            })
        }
    }

    @Test
    fun assert_query_status_success() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            liveData.observeForever(Observer {
                Timber.d("status == ${it.status.name}")
                assertTrue(it.status.name.equals(Status.SUCCESS.name, true))
            })

        }
    }

    @Test
    fun assert_query_status_error() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            liveData.observeForever(Observer {
                Timber.d("status == ${it.status.name}")
                assertFalse(it.status.name.equals(Status.ERROR.name, true))
            })
        }
    }

    @Test
    fun assert_changed_value() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()
            val newName = "SomeRandomName"
            val data = liveData.value?.data
            data!![0].name = newName
            liveData.postValue(Resource.success(data))

            liveData.observeForever(Observer {
                Timber.d("new name == ${it.data?.get(0)?.name}")
                assertFalse(it.data?.get(0)?.name.equals("duress", true))
                assertTrue(it.data?.get(0)?.name.equals(newName, true))
            })
        }
    }

    @Test
    fun assert_repo_is_not_null() {
        assertTrue(repository != null)
    }

    @Test
    fun assert_repo_card_equals_livedata_card() {
        coroutineRule.runBlockingTest {
            val liveData = viewModel.getLiveData()

            liveData.observeForever {
                val data = liveData.value?.data
                val rawData = repository.getRawMockData().data
                assertTrue(data!!.equals(rawData))
            }
        }
    }
}