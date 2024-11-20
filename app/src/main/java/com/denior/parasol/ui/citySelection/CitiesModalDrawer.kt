package com.denior.parasol.ui.citySelection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.denior.parasol.R
import com.denior.parasol.data.CityEntity


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CitiesModalDrawer(
    cityList: List<CityEntity>,
    drawerState: DrawerState,
    onCitySelected: (CityEntity) -> Unit,
    selectedCityId: Int?,
    content: @Composable () -> Unit,
    drawerStateHeaderAction: () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
                    stickyHeader {
                        Header(onClick = drawerStateHeaderAction)
                    }
                    items(cityList) { city ->
                        CityElementInDrawer(
                            city = city,
                            selected = city.id == selectedCityId,
                            onClick = { onCitySelected(city) }
                        )
                    }
                }

            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}

@Composable
private fun Header(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onClick) {
        }
    }
}


@Composable
private fun CityElementInDrawer(
    city: CityEntity,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = "${city.cityName}, ${city.countryName}") },
        selected = selected,
        onClick = onClick
    )
}
