package jp.speakbuddy.edisonandroidexercise

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.data.datastore.UserStore
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.edisonandroidexercise.presentation.AppBottomBar
import jp.speakbuddy.edisonandroidexercise.presentation.AppToolBar
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val factViewModel: FactViewModel by viewModels()
    @Inject
    lateinit var userStore: UserStore
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val rootView: View = findViewById(android.R.id.content)
            val context = LocalContext.current
            EdisonAndroidExerciseTheme {
                Scaffold(topBar = {
                    AppToolBar()
                }, bottomBar = {
                    AppBottomBar()
                }, floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            val user = UserStore(context)
                            runBlocking {
                                // Access the factDesc and factLength values from the UserStore
                                lifecycleScope.launch {
                                    val userStoreData = userStore.getDetails().first()
                                    val factDesc = userStoreData[UserStore.factDesc] ?: ""
                                    val factLength = userStoreData[UserStore.factLength] ?: 0
                                    Toast.makeText(context, "actDesc -$factDesc", Toast.LENGTH_SHORT).show()
                                    Toast.makeText(context, "factLength -$factLength", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.White,
                        )
                    }
                }) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        FactScreen(viewModel = factViewModel)
                    }
                }
            }
        }
    }
}