/*
 * Copyright 2025 Blocker
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

package com.merxury.blocker.core.domain.controller

import com.merxury.blocker.core.controllers.IAppController
import com.merxury.blocker.core.controllers.di.RootApiAppControl
import com.merxury.blocker.core.controllers.di.ShizukuAppControl
import com.merxury.blocker.core.data.respository.userdata.UserDataRepository
import com.merxury.blocker.core.model.data.ControllerType.SHIZUKU
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAppControllerUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @RootApiAppControl private val rootAppController: IAppController,
    @ShizukuAppControl private val shizukuAppController: IAppController,
) {
    operator fun invoke(): Flow<IAppController> = userDataRepository.userData
        .distinctUntilChanged()
        .map { userData ->
            when (userData.controllerType) {
                SHIZUKU -> shizukuAppController
                else -> rootAppController
            }
        }
}
