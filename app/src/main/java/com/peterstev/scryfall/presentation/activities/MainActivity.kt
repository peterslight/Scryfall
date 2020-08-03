package com.peterstev.scryfall.presentation.activities

import android.os.Bundle
import android.os.Debug
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.bumptech.glide.RequestManager
import com.peterstev.safebodachallengepeterslight.R
import com.peterstev.scryfall.presentation.TIMBER_END
import com.peterstev.scryfall.presentation.TIMBER_START
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            Debug.startMethodTracing("tracer")
        }
        setContentView(R.layout.activity_main)
        Timber.d("TIMBER time to init libs and main activity : ${System.currentTimeMillis() - TIMBER_START}ms")
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp()
}