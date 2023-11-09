[中文README](https://github.com/lihenggui/blocker/blob/main/README.zh-CN.md)

## Blocker
[![release](https://img.shields.io/github/v/release/lihenggui/blocker?label=release&color=red)](https://github.com/lihenggui/blocker/releases)
[![download](https://shields.io/github/downloads/lihenggui/blocker/total?label=download)](https://github.com/lihenggui/blocker/releases/latest)
[![translation](https://weblate.sanmer.dev/widget/blocker/svg-badge.svg)](https://weblate.sanmer.dev/engage/blocker/)
[![follow](https://img.shields.io/badge/follow-Telegram-blue.svg?label=follow)](https://t.me/blockerandroid) 
[![license](https://img.shields.io/github/license/lihenggui/blocker)](LICENSE) 


Blocker is a component controller for Android applications that currently supports using
PackageManager and Intent Firewall to manage the state of components. For bloated applications, many
components within the application are redundant. Blocker provides a convenient control button to
manage the corresponding components, enabling the disabling of unnecessary functions and saving
application runtime resources.  
Blocker can be seamlessly switched between these controllers, and for application rules, you can
export and import them. Additionally, it is compatible with backup files generated by
MyAndroidTools, and you can effortlessly convert them to Intent Firewall rules. The application also
has the potential to be extended in the future.

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/com.merxury.blocker/)
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt="Get it on Google Play"
     height="80">](https://play.google.com/store/apps/details?id=com.merxury.blocker)

## Advantages
1. Lightweight, don't burden the system
2. Easy to use
3. Supports multiple control types

## Introduction to different component controllers
### Package Manager

The Android system provides a tool called PackageManager, which is used for managing installed
applications or obtaining information about them. One of its
methods, ```setComponentEnabledSetting(ComponentName, int, int)```, allows an application to control
the state of its own components. If attempting to use this method to control components in other
applications, a signature permission is required, or the call will fail.  
Fortunately, Android provides another tool called "pm" that allows users to control component states in command-line mode. However, the "pm" tool requires root permission to run. The following command can be used to disable a specific package or component:

```
pm disable [PackageName/ComponmentName]
```

Whether using PackageManager in the code or "pm" in command-line mode, the configurations will be written to ```/data/system/users/0/package_restrictions.xml```.

### Intent Firewall Mode
Intent Firewall was introduced in Android 4.4.2 (API 19) and is still effective in the latest Android systems. It is integrated into the Android Framework to filter the intents sent by applications or systems. 

#### What Intent Firewall can do
Each intent sent by an application is filtered by the Intent Firewall, with rules stored in XML files. The Intent Firewall updates rules immediately if changes occur in the configuration file.

#### Limitations of Intent Firewall
Only system applications can read and write the directory where the configuration file is stored, and third-party applications do not have permission to access it.

#### Differences between Intent Firewall and Package Manager
Intent Firewall, indeed it is a firewall, it has no impact on component status. The application detects the component is on, but it just cannot start the component.

For the components disabled by PackageManager, if an application starts it, an exception will be thrown. Developers can catch this exception to know whether the component is disabled or not, so they could re-enable this component. That's the reason why the components will be enabled unexpectedly. If you are using an Intent Firewall controller, there will be no problems.
#### References
[Intent Firewall](https://carteryagemann.com/pages/android-intent-firewall.html)

### Shizuku/Sui Mode
Shizuku is an application developed by Rikka, [RikkaApps/Shizuku](https://github.com/RikkaApps/Shizuku)

Starting from Android O, if we install a Test-Only application, users could use pm command to control the command status. We could modify the install package to set it into Test-Only mode, using APIs provided by Shizuku to control the component status.

Tutorial for modifying APKs (Chinese Only) [[实验性功能] [开发者向]如何免Root控制应用程序组件](https://github.com/lihenggui/blocker/wiki/%5B%E5%AE%9E%E9%AA%8C%E6%80%A7%E5%8A%9F%E8%83%BD%5D-%5B%E5%BC%80%E5%8F%91%E8%80%85%E5%90%91%5D%E5%A6%82%E4%BD%95%E5%85%8DRoot%E6%8E%A7%E5%88%B6%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F%E7%BB%84%E4%BB%B6)

Please note: For normal applications, the Shell permission in Shizuku mode is not sufficient to
change the switch status of components. In other words, unmodified APKs do not support non-root
modification. If you want to use Shizuku to modify the component status of normal applications,
please start Shizuku with Root privileges.  
Implementation of this restriction in
AOSP: [frameworks/base/services/core/java/com/android/server/pm/PackageManagerService.java](https://cs.android.com/android/platform/superproject/main/+/main:frameworks/base/services/core/java/com/android/server/pm/PackageManagerService.java;l=3750;drc=02a77ed61cbeec253a1b49e732d1f27a9ff4b303;bpv=0;bpt=1)

## Screenshot tests

**Blocker** uses [Roborazzi](https://github.com/takahirom/roborazzi) to do screenshot tests
of certain screens and components. To run these tests, run the `verifyRoborazziFossDebug` or
`recordRoborazziFossDebug` tasks. Note that screenshots are recorded on CI, using Linux, and other
platforms might generate slightly different images, making the tests fail.

## UI
The app was designed using [Material 3 guidelines](https://m3.material.io/). Learn more about the design process and obtain the [design files in Figma](https://www.figma.com/file/T903MNmXtahDVf1yoOgXoI/Blocker).
Huge thanks to our UI designer: [@COPtimer](https://github.com/COPtimer)  
The Screens and UI elements are built entirely using Jetpack Compose.

The app has two themes:

* Dynamic color - uses colors based on the user's current color theme (if supported)
* Default theme - uses predefined colors when dynamic color is not supported

Each theme also supports dark mode.

## Frequently Asked Questions

1. When clicking the button in Shizuku mode, the component state cannot be controlled, and an error
   pops up: SecurityException: Shell cannot change component state for 'xx' to state 'xx'.

* The Shell permission of Shizuku cannot disable unmodified application components. Please restart
  Shizuku with Root privileges or try modifying the APK.
