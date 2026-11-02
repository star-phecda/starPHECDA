# Spacing & Layout Implementation Plan

> For agentic workers: use the executing-plans workflow to implement this plan task by task.

**Goal:** Implement a consistent spacing system and responsive layouts across NoveLA without changing reader-content preferences or feature behavior.

**Architecture:** Add spacing and adaptive-width tokens to shared coreui, expose a measured window-size class from the root theme, and migrate shared components before adapting feature screens. Keep page composition screen-specific rather than forcing every screen into one template.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, JUnit 4, GitHub Actions.

**Spec:** docs/superpowers/specs/2026-11-02-spacing-layout-design.md

## Global Constraints

- Spacing scale: 4 / 8 / 12 / 16 / 20 / 24 / 32 / 40 / 48 / 64dp.
- Page gutters: 16dp compact, 24dp medium, 32dp expanded.
- Width classes: compact <600dp, medium 600–839dp, expanded >=840dp.
- Maximum content widths: 600dp compact, 840dp medium, 1200dp expanded.
- Grid preference remains 2–6 and is the compact baseline; adaptive layouts may add columns, capped at six.
- Existing Shape System, Material color system, and typography remain unchanged.
- Reader text canvas and user-controlled reader text preferences remain independent.
- Do not add a new adaptive-layout dependency.

## Review Focus

- Width exactly at 600dp and 840dp must resolve to the expected next class.
- Narrow windows and large font scale must not cause horizontal overflow.
- Grid preference values 2 and 6 must remain valid, and adaptive columns must never exceed six.
- Resizing or rotating a window must update adaptive composition without losing UI state.
- Reader text size, font, line spacing, and page-margin preferences must not be affected.

---

### Task 1: Shared spacing and window-size foundation

**Files:**
- Modify: coreui/src/main/java/my/noveldokusha/coreui/theme/ExpressiveTokens.kt
- Create: coreui/src/main/java/my/noveldokusha/coreui/theme/ExpressiveLayout.kt
- Modify: coreui/src/main/java/my/noveldokusha/coreui/theme/Theme.kt
- Create: coreui/src/test/java/my/noveldokusha/coreui/theme/ExpressiveLayoutTokensTest.kt

**Interfaces:**
- Produces: ExpressiveWindowSizeClass, LocalExpressiveWindowSizeClass, expressiveWindowSizeClass(width: Dp), expressiveContentGutter(sizeClass), expressiveContentMaxWidth(sizeClass), and adaptiveGridColumnCount(availableWidth, preferredColumns, minCellWidth, maxColumns).
- InternalTheme measures root constraints using BoxWithConstraints and provides the corresponding size class to descendants.

- [ ] Write tests for all spacing token values, width class boundaries, gutter/max-width mapping, and grid-column behavior for compact/medium/expanded widths and 2/6 preferred-column bounds.
- [ ] Run ./gradlew --no-daemon :coreui:testDebugUnitTest and confirm the new tests fail because the API is not yet implemented.
- [ ] Implement the minimum token, pure helper, and theme-provider code required by the tests.
- [ ] Run ./gradlew --no-daemon :coreui:testDebugUnitTest and confirm all core UI tests pass.
- [ ] Commit as feat: add expressive spacing and adaptive layout foundation.

### Task 2: Shared content and book-grid components

**Files:**
- Modify: coreui/src/main/java/my/noveldokusha/coreui/components/BooksVerticalView.kt
- Modify: features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/LibraryPageBody.kt
- Modify: coreui/src/main/java/my/noveldokusha/coreui/components/Section.kt

**Interfaces:**
- Consumes: LocalExpressiveWindowSizeClass, spacing tokens, and adaptiveGridColumnCount from Task 1.
- Produces: shared grids and section surfaces that use the spacing system and respond to measured width without changing item actions, image loading, or list/grid preference semantics.

- [ ] Add tests for adaptive grid helper cases owned by the shared grid.
- [ ] Run the focused core UI test before implementation and confirm the intended adaptive behavior is missing.
- [ ] Replace hardcoded shared-grid insets and item spacing with tokens; preserve the compact grid preference and cap adaptive grids at six columns.
- [ ] Standardize section heading/content padding while preserving each section's existing slot content and interaction.
- [ ] Run :coreui:testDebugUnitTest and :app:assembleDebug through CI.

### Task 3: Primary app screens

**Files:**
- Modify: Library, Finder/catalog, History, global search, source catalog, and novel/chapter detail screen composables under features/.
- Update: docs/EXPRESSIVE_DESIGN_SYSTEM.md.

- [ ] Replace page-level hardcoded margins and repeated inter-section gaps with approved gutter and spacing tokens.
- [ ] Apply a maximum content width to long-form layouts on medium and expanded windows.
- [ ] Adapt novel/detail artwork and information into a side-by-side composition when available width permits; retain the existing vertical composition on compact windows.
- [ ] Preserve all existing navigation, search, category, filter, selection, and chapter actions.
- [ ] Run core UI tests and the debug app build.

### Task 4: Settings, secondary screens, and reader chrome

**Files:**
- Modify: settings and extension screens, database search/detail screens, migration and web screens, and reader settings/control surfaces under features/.
- Do not modify the reader text layout or user preference resolution.

- [ ] Standardize settings group spacing and constrain expanded settings content to a readable width.
- [ ] Use available width for multi-column grouping only where it improves scanability; preserve single-column compact settings.
- [ ] Apply shared spacing to reader controls, dialogs, sheets, and chrome only. Do not change reader text size, line height, page margins, or selected font.
- [ ] Verify compact-window and reader-preference regression cases by tests and source review.
- [ ] Run core UI tests and the debug app build.

### Task 5: Final review and verification

- [ ] Review the complete diff against the design spec and Review Focus conditions.
- [ ] Fix any critical or important findings with a failing test first.
- [ ] Run ./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug on the final commit.
- [ ] Confirm the GitHub Actions result and uploaded debug APK artifact before reporting completion.
