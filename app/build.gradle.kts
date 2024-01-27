plugins {
    id("com.mxt.anitrend.plugin")
}

android {
    namespace = "com.mxt.anitrend"

    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }
}

val prettyTime = "4.0.4.Final"
val butterKnife = "10.2.3"
val multidex = "2.0.1"
val materialDialogs = "0.9.6.0"
val tapTarget = "3.3.2"
val jiaoziVideoPlayer = "7.7.2.3300"
val photoView = "2.3.0"
val onboarder = "1.0.4"
val mpAndroidChart = "3.1.0"
val aboutPage = "1.3.1"
val materialSearchView = "1.4.15"
val progressLayout = "master-SNAPSHOT"
val eventBus = "3.3.1"
val alerter = "7.0.1"
val stream = "1.2.2"
val circularProgressView = "2.5.0"
val txtmark = "0.16"

dependencies {
    /** Butter Knife Libraries */
    implementation("com.jakewharton:butterknife:${butterKnife}")
    kapt("com.jakewharton:butterknife-compiler:${butterKnife}")

    /** Material Dialogs */
    implementation("com.afollestad.material-dialogs:core:${materialDialogs}")

    /** Tap Target Prompt */
    implementation("uk.co.samuelwall:material-tap-target-prompt:${tapTarget}")

    /** Pretty Time */
    implementation("org.ocpsoft.prettytime:prettytime:${prettyTime}")

    /** Highly Customizable Video Player */
    implementation("cn.jzvd:jiaozivideoplayer:${jiaoziVideoPlayer}")

    /** Photo View */
    implementation("com.github.chrisbanes:PhotoView:${photoView}")

    /** On-boarding Experience */
    implementation("com.codemybrainsout.onboarding:onboarder:${onboarder}")

    /** Charts */
    implementation("com.github.PhilJay:MPAndroidChart:v${mpAndroidChart}")

    /** About Library */
    implementation("com.github.medyo:android-about-page:${aboutPage}")

    /** Multi Dex */
    implementation("androidx.multidex:multidex:${multidex}")

    /** Material Search View */
    implementation("com.github.ma-myair:MaterialSearchView:${materialSearchView}")

    /** State Layout Library */
    implementation("com.github.nguyenhoanglam:ProgressLayout:${progressLayout}")

    /** Event Bus Library */
    implementation("org.greenrobot:eventbus:${eventBus}")

    /** Alerter */
    implementation("com.tapadoo.android:alerter:${alerter}")

    /** Stream */
    implementation("com.annimon:stream:${stream}")

    /** Circular Progress View */
    implementation("com.github.rahatarmanahmed:circularprogressview:${circularProgressView}")

    /** Txtmark */
    implementation("es.nitaur.markdown:txtmark:${txtmark}")
}
