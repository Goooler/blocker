/*
 * Copyright 2023 Blocker
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

package com.merxury.blocker.core.model.data

import com.merxury.blocker.core.model.ComponentType

data class ComponentInfo(
    val name: String,
    val simpleName: String,
    val packageName: String,
    val type: ComponentType,
    val exported: Boolean,
    val pmBlocked: Boolean,
    val ifwBlocked: Boolean = false,
    val description: String? = null,
) {
    fun toAndroidComponentInfo() = android.content.pm.ComponentInfo().apply {
        this@apply.name = this@ComponentInfo.name
        this@apply.packageName = this@ComponentInfo.packageName
    }
}
