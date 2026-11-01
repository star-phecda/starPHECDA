# Expressive Typography System Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Give starPHECDA a distinctive app-wide Manrope/Literata type system while preserving independent reader typography.

**Architecture:** Add licensed variable font resources to :coreui and define the app's Material 3 Typography at its existing root theme entry point. Keep chapter font handling untouched; increment only the app version metadata for the next build.

**Tech Stack:** Kotlin, Jetpack Compose Material 3, Android font resources, JUnit, Gradle, GitHub Actions.

**Spec:** `docs/superpowers/specs/2026-11-01-expressive-typography-design.md`

## Global Constraints

- Minimum SDK remains 26.
- Application ID remains `my.novela`.
- Version changes from 1.6.1 / 39 to 1.6.2 / 40.
- Reader font preferences, reader font loader, and chapter text rendering remain unchanged.
- Font binaries retain their SIL Open Font License notices.

## Review Focus

- Android API 26 variable-font rendering: use supported variable font axes without raising minSdk.
- User font scaling: preserve sp units and do not hardcode pixel sizes.
- Reader independence: app type roles must not replace saved reader font preferences.
- Font matching: declared family weights must correspond to actual variable font weights.
- Update identity: keep applicationId unchanged and ensure versionCode increases.

---

### Task 1: Add the app typography contract and licensed typefaces

**Files:**
- Create: `coreui/src/test/java/my/noveldokusha/coreui/theme/TypographyTest.kt`
- Create: `coreui/src/main/res/font/manrope_variable.ttf`
- Create: `coreui/src/main/res/font/literata_variable.ttf`
- Create: `docs/licenses/Manrope-OFL.txt`
- Create: `docs/licenses/Literata-OFL.txt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/theme/Typography.kt`
- Modify: `docs/EXPRESSIVE_DESIGN_SYSTEM.md`

**Interfaces:**
- Produces: the existing public `typography: androidx.compose.material3.Typography` value, still consumed by `Theme.kt` and `ReaderTheme.kt`.
- The Material 3 scale and family assignments are specified in the design document.

- [ ] Write tests asserting all 15 roles use the app typefaces and exact sizes/line heights from the design document.
- [ ] Run `./gradlew --no-daemon :coreui:testDebugUnitTest` and confirm the tests fail on the existing system-sans/old-scale implementation.
- [ ] Add official variable font resources and both OFL notices.
- [ ] Replace the system-sans family and refine the role hierarchy using Manrope and Literata.
- [ ] Run the same core UI test command and confirm the suite passes.
- [ ] Commit the typography implementation.

### Task 2: Version the update and verify the Android build

**Files:**
- Modify: `app/build.gradle.kts`
- Modify: `docs/EXPRESSIVE_DESIGN_SYSTEM.md`

**Interfaces:**
- Consumes: the typography contract delivered by Task 1.
- Preserves: `applicationId = my.novela`.

- [ ] Change `versionCode` to 40 and `versionName` to 1.6.2.
- [ ] Run `./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug`.
- [ ] Commit version metadata and documentation.
- [ ] Verify the final branch head's GitHub Actions run succeeds and publishes `starPHECDA-debug-apk`.
- [ ] Inspect the resulting APK metadata and confirm package/version values match this plan.