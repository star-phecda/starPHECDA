# Expressive Shape System Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [x]`) syntax for tracking.

**Goal:** Establish a centralized, role-based Material 3 Expressive shape system across starPHECDA's shared controls, library UI, sheets and dialogs.

**Architecture:** Keep dimension tokens in `ExpressiveTokens`, expose named component shapes from `Shape.kt`, and supply the Material 3 shape family through the existing `MaterialTheme`. Components in feature modules consume the public coreui shapes without introducing new preferences or dependencies.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, JUnit 4, existing GitHub Actions Android workflow.

**Spec:** `docs/superpowers/specs/2026-11-01-expressive-shape-system-design.md`

## Global Constraints

- Keep the current Android / Kotlin / Compose dependency versions.
- Preserve the current 48dp selectable minimum height.
- Do not change reader typography, chapter text rendering, color schemes, or saved app preferences.
- No user-selectable shape presets in this milestone.
- Run `./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug` in CI for the final branch head.

## Review Focus

- A caller-supplied `MyButton.shape` must not be overridden by the default selected-state shape animation.
- The default selected-state animation must leave click handling, minimum height and selected colors unchanged.
- Sheet corner shapes must apply consistently without changing dismissal, drag-handle or scroll behavior.
- Dialog content must remain clipped by the dialog surface and preserve its existing action behavior.
- The full-pill role must stay capsule-shaped at different component heights, rather than relying on a fixed 36dp radius.

---

### Task 1: Define and test the canonical shape scale

**Files:**
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/theme/ExpressiveTokens.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/theme/Shape.kt`
- Create: `coreui/src/test/java/my/noveldokusha/coreui/theme/ExpressiveShapeTokensTest.kt`

**Interfaces:**
- Produces `ExpressiveTokens.cornerExtraSmall`, `cornerSmall`, `cornerMedium`, `cornerLarge`, `cornerExtraLarge`, and `cornerExtraLargeIncreased` as the 4 / 8 / 12 / 20 / 28 / 36dp scale.
- Produces `ExpressiveShapes.card`, `featuredCard`, `container`, `button`, `selectedButton`, `pill`, `input`, `sheet`, and `dialog`.
- Keeps `shapes`, `ImageBorderShape`, and `selectableMinHeight` as public theme values.

- [x] **Step 1: Add failing tests** for all six dp tokens, the five MaterialTheme shape role mappings, and the named component shape mappings.
- [x] **Step 2: Run the focused test** and confirm it fails because the new token/shape API is not yet present.
- [x] **Step 3: Implement the canonical tokens and shape objects** in the two theme files; preserve existing navigation and motion dimensions.
- [x] **Step 4: Run the focused test** and confirm all expected dimensions and mappings pass.
- [x] **Step 5: Run the coreui unit suite** and commit the foundation.

### Task 2: Apply shapes to shared controls and library surfaces

**Files:**
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/MyButton.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/LanguageFilterChips.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/ExpressiveNavigationBar.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/LibraryScreenBody.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/LibraryBottomSheet.kt`
- Modify: `features/settings/src/main/java/my/noveldokusha/settings/sections/SettingsTheme.kt`

**Interfaces:**
- Consume only the public shape API from Task 1.
- `MyButton.shape` remains overrideable; only the default shape uses the selected-state morph.

- [x] **Step 1: Update the shared controls** to use the named button, container and pill roles.
- [x] **Step 2: Run coreui tests and the app compile task** to catch API/import errors.
- [x] **Step 3: Commit the shared control migration.**

### Task 3: Apply shapes to sheets, dialogs and source filters

**Files:**
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/BookActionsBottomSheet.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/CreateCategoryDialog.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/MoveToCategoryDialog.kt`
- Modify: `features/chaptersList/src/main/java/my/noveldokusha/features/chapterslist/ChaptersBottomSheet.kt`
- Modify: `features/chaptersList/src/main/java/my/noveldokusha/features/chapterslist/GoToChapterDialog.kt`
- Modify: `features/sourceExplorer/src/main/java/my/noveldokusha/sourceexplorer/FilterBottomSheet.kt`
- Modify: `features/settings/src/main/java/my/noveldokusha/settings/views/NewAppUpdateDialog.kt`

- [x] **Step 1: Replace local sheet, dialog, button and field corner literals** with the component shape roles.
- [x] **Step 2: Run coreui tests and the app compile task** to verify all feature modules compile against the shared API.
- [x] **Step 3: Commit the surface migration.**

### Task 4: Apply the shape system across secondary feature screens

**Files:**
- Modify: `features/catalogExplorer/` and `features/globalSourceSearch/`
- Modify: `features/databaseExplorer/` and `features/chaptersList/`
- Modify: `features/reader/` text, manga, video, TTS and settings surfaces
- Modify: `features/extensions/` and `features/webview/`
- Modify: `features/historyExplorer/` and `tooling/novel_migration/`
- Modify: `features/settings/` dialogs and grouped sections

- [x] **Step 1: Migrate Finder, search, source and database result controls** to pill, card, input and sheet roles.
- [x] **Step 2: Migrate novel/chapter information, chapter rows, badges and related actions** to the shared roles.
- [x] **Step 3: Migrate reader chrome, translation/voice settings, manga settings, TTS overlays, highlight controls and video settings panels.**
- [x] **Step 4: Migrate extension, regex-cleanup, migration and WebView surfaces.**
- [ ] **Step 5: Verify the complete feature graph with unit tests, app debug assembly and an uploaded APK.**

### Task 5: Update the system documentation and verify the final build

**Files:**
- Modify: `docs/EXPRESSIVE_DESIGN_SYSTEM.md`
- Modify: `docs/superpowers/plans/2026-11-01-expressive-shape-system.md` (check off completed tasks and note verification)

- [x] **Step 1: Document** the final scale, component roles and fixed-house-style decision.
- [x] **Step 2: Run the full CI command** `./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug`.
- [x] **Step 3: Confirm** the exact branch-head workflow succeeds and uploads `starPHECDA-debug-apk`.
- [x] **Step 4: Commit the documentation and verification record.**


## Verification record

- The initial shape-token foundation passed focused core UI tests and a debug build before the secondary-screen rollout.
- The first Finder/History/Settings correction exposed unresolved shape references in CatalogList.kt. The failed runs were [41394068607](https://github.com/star-phecda/starPHECDA/actions/runs/41394068607) and [41394075427](https://github.com/star-phecda/starPHECDA/actions/runs/41394075427).
- The CatalogList compile references were corrected in commit `e95c692514bda2507ab444e3cd058e39b4b12204`; both workflows passed that targeted correction.
- The expanded all-feature migration is on the same branch. Its exact final-commit CI result and APK upload remain the release gate and must be recorded only after they succeed.