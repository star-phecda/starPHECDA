# Expressive Shape System Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

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

- [ ] **Step 1: Add failing tests** for all six dp tokens, the five MaterialTheme shape role mappings, and the named component shape mappings.
- [ ] **Step 2: Run the focused test** and confirm it fails because the new token/shape API is not yet present.
- [ ] **Step 3: Implement the canonical tokens and shape objects** in the two theme files; preserve existing navigation and motion dimensions.
- [ ] **Step 4: Run the focused test** and confirm all expected dimensions and mappings pass.
- [ ] **Step 5: Run the coreui unit suite** and commit the foundation.

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

- [ ] **Step 1: Update the shared controls** to use the named button, container and pill roles.
- [ ] **Step 2: Run coreui tests and the app compile task** to catch API/import errors.
- [ ] **Step 3: Commit the shared control migration.**

### Task 3: Apply shapes to sheets, dialogs and source filters

**Files:**
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/BookActionsBottomSheet.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/CreateCategoryDialog.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/MoveToCategoryDialog.kt`
- Modify: `features/chaptersList/src/main/java/my/noveldokusha/features/chapterslist/ChaptersBottomSheet.kt`
- Modify: `features/chaptersList/src/main/java/my/noveldokusha/features/chapterslist/GoToChapterDialog.kt`
- Modify: `features/sourceExplorer/src/main/java/my/noveldokusha/sourceexplorer/FilterBottomSheet.kt`
- Modify: `features/settings/src/main/java/my/noveldokusha/settings/views/NewAppUpdateDialog.kt`

- [ ] **Step 1: Replace local sheet, dialog, button and field corner literals** with the component shape roles.
- [ ] **Step 2: Run coreui tests and the app compile task** to verify all feature modules compile against the shared API.
- [ ] **Step 3: Commit the surface migration.**

### Task 4: Update the system documentation and verify the final build

**Files:**
- Modify: `docs/EXPRESSIVE_DESIGN_SYSTEM.md`
- Modify: `docs/superpowers/plans/2026-11-01-expressive-shape-system.md` (check off completed tasks and note verification)

- [ ] **Step 1: Document** the final scale, component roles and fixed-house-style decision.
- [ ] **Step 2: Run the full CI command** `./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug`.
- [ ] **Step 3: Confirm** the exact branch-head workflow succeeds and uploads `starPHECDA-debug-apk`.
- [ ] **Step 4: Commit the documentation and verification record.**
