import Versions.navVersion
import Versions.serializationVersion

object Versions {
    const val koin = "3.3.3"
    const val kermit = "1.1.3"
    const val serializationVersion = "1.4.0"
    const val navVersion = "2.5.0"
}


object Deps {
    object Koin {
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Kermit {
        const val kermitMain = "co.touchlab:kermit:${Versions.kermit}"
    }

    object KotlinX {
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
    }

    object Compose {
        val composePlatformBom = "androidx.compose:compose-bom:2023.01.00"

        // Material Design 3
        val material3 = "androidx.compose.material3:material3"
        // or skip Material Design and build directly on top of foundational components
        val foundation = "androidx.compose.foundation:foundation"
        // or only import the main APIs for the underlying toolkit systems,
        // such as input and measurement/layout
        val ui = "androidx.compose.ui:ui"

        // Android Studio Preview support
        val preview = "androidx.compose.ui:ui-tooling-preview"
        val previewDebug = "androidx.compose.ui:ui-tooling"

        // UI Tests
        val uiJUnitTest = "androidx.compose.ui:ui-test-junit4"
        val uiManifestTest = "androidx.compose.ui:ui-test-manifest"


        // Integration with activities
        val activities  = "androidx.activity:activity-compose:1.6.1"
    }

    object Navigation {
        // Kotlin Navigation
        val navFragment = ("androidx.navigation:navigation-fragment-ktx:$navVersion")
        val ktx = "androidx.navigation:navigation-ui-ktx:$navVersion"
    }


}