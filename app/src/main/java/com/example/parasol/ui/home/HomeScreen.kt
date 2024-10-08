package com.example.parasol.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parasol.R
import com.example.parasol.navigation.NavigationDestination
import com.example.parasol.ui.components.HomeScreenTopAppBar
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToCitySearch: () -> Unit,
    retryAction: () -> Unit,
    indexUiState: StateFlow<IndexUiState>,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currentIndexUiState by indexUiState.collectAsState(IndexUiState.Loading)



    Scaffold(topBar = {
        HomeScreenTopAppBar(
            navDrawer = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open()
                        else close()
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            citySearch = navigateToCitySearch,
            retryAction = { retryAction() },
            textInTopBar = stringResource(id = R.string.app_name)
        )
    }, contentWindowInsets = WindowInsets(bottom = 0.dp)) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when (currentIndexUiState) {
                is IndexUiState.Loading -> LoadingScreen()
                is IndexUiState.Success -> ResultScreen(
                    uvResponse = (currentIndexUiState as IndexUiState.Success).indexes,
                    modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection)
                )

                is IndexUiState.Error -> ErrorScreen(retryAction)
            }
        }
    }
}
