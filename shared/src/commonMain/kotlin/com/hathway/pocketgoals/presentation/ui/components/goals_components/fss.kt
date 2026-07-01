package com.hathway.pocketgoals.presentation.ui.components.goals_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.VideogameAsset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.pocketgoals.domain.Goal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreenw() {


    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All", "Active", "Completed", "Paused")

    val mockGoals = remember {
        listOf(
            Goal(
                id = "1",
                name = "Gaming PC",
                targetAmount = 15000.0,
                savedAmount = 3500.0,
                deadline = "30 Dec 2027",
                icon = Icons.Rounded.VideogameAsset,
                color = Color(0xFF0F766E)
            ), Goal(
                id = "2",
                name = "Japan Trip 2027",
                targetAmount = 12000.0,
                savedAmount = 7800.0,
                deadline = "15 Jul 2026",
                icon = Icons.Rounded.AirplanemodeActive,
                color = Color(0xFF6366F1)
            )
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = {
                    Text(
                        "My Goals",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }, actions = {
                    IconButton(onClick = { /* Add Goal */ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Goal")
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = Color(0xFF0F766E),
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFF0F766E)
                        )
                    }) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                )
                            })
                    }
                }
            }
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF8FAFC)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockGoals) { goal ->
                GoalCard(goal = goal)
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}