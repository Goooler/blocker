/*
 * Copyright 2022 Blocker
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.merxury.blocker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.merxury.blocker.feature.appdetail.navigation.appDetailScreen
import com.merxury.blocker.feature.appdetail.navigation.navigateToAppDetail
import com.merxury.blocker.feature.applist.navigation.appListRoute
import com.merxury.blocker.feature.applist.navigation.appListScreen
import com.merxury.blocker.feature.globalsearch.navigation.globalSearchScreen
import com.merxury.blocker.feature.helpandfeedback.navigation.navigateToSupportAndFeedback
import com.merxury.blocker.feature.helpandfeedback.navigation.supportAndFeedbackScreen
import com.merxury.blocker.feature.onlinerules.navigation.onlineRulesScreen
import com.merxury.blocker.feature.settings.navigation.navigateToSettings
import com.merxury.blocker.feature.settings.navigation.settingsScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BlockerNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = appListRoute,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        appListScreen(
            navigateToAppDetail = navController::navigateToAppDetail,
            navigateToSettings = navController::navigateToSettings,
            navigateToSupportAndFeedback = navController::navigateToSupportAndFeedback,
        )
        appDetailScreen(onBackClick)
        onlineRulesScreen()
        globalSearchScreen()
        settingsScreen(onBackClick)
        supportAndFeedbackScreen(onBackClick)
    }
}
