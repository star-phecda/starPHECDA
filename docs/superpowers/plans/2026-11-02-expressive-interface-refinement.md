# Expressive Interface Refinement Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Refine NoveLA's shared Compose interface into a more tactile, layered, and expressive Material 3 experience.

**Architecture:** Improve the existing shared navigation, book-media, button, and search components, then refine the History card composition. Keep screen state and feature behavior in their existing ViewModels and composables; do not introduce a new design dependency.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3 1.4.0, Compose animation, JUnit 4.

**Spec:** `docs/superpowers/specs/2026-11-02-expressive-interface-refinement-design.md`

## Global Constraints

- Keep the canonical corner scale at 4 / 8 / 12 / 20 / 28 / 36dp.
- Use CircleShape for capsule controls.
- Preserve routes, content, colors, type roles, and reader preferences.
- Do not add dependencies or change applicationId/version identity.
- Keep interactive targets at least 48dp and preserve selectable semantics.
- Keep the text reader and manga/video canvases as content surfaces, not decorative cards.

## Review Focus

- Four long navigation labels at narrow widths: the selected capsule must remain legible without clipping.
- Compact navigation mode: all destinations must retain equal icon-only targets.
- Repeated or rapid destination taps: selection animation must not block or delay clicks.
- Book cover press feedback: preserve hit testing, crop, image loading, and long-click behavior.
- History entries with missing source/chapter metadata or zero chapters: no empty chips, divide-by-zero, or progress overflow.
- Font scaling and long localized titles: labels and metadata must ellipsize or wrap without overlapping controls.

---

### Task 1: Navigation layout contract

**Files:**
- Modify: `coreui/src/test/java/my/noveldokusha/coreui/theme/ExpressiveShapeTokensTest.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/theme/ExpressiveTokens.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/ExpressiveNavigationBar.kt`

**Interfaces:**
- Produces: `internal fun expressiveNavigationItemWeight(selected: Boolean, compactMode: Boolean): Float`
- Selected full navigation weight is 1.85f; unselected and compact items use 1.0f.

- [ ] Write tests for selected, unselected, and compact navigation item weights.
- [ ] Run `:coreui:testDebugUnitTest` and verify the new contract fails because the helper is not implemented.
- [ ] Implement the helper and update the navigation component to use an expanding active capsule with icon + label and compact inactive icons.
- [ ] Use spring-driven item width, icon scale, and color transitions; keep selectable semantics and the 48dp target.
- [ ] Run `:coreui:testDebugUnitTest`.

### Task 2: Tactile book-media surfaces

**Files:**
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/BookImageButtonView.kt`
- Modify: `features/libraryExplorer/src/main/java/my/noveldokusha/libraryexplorer/LibraryPageBody.kt` only if a selection overlay needs a cover-only clip.

**Interfaces:**
- Consumes: the existing `ExpressiveShapes.bookCover`, `ExpressiveTokens`, and caller-provided `MutableInteractionSource`.
- Preserves: existing callback signatures, cache settings, crop, source-strip modes, and book metadata.

- [ ] Add a token test for the cover press scale.
- [ ] Run `:coreui:testDebugUnitTest` to verify it fails before the token is added.
- [ ] Add subtle spring press feedback, a refined cover border/gradient title treatment, and capsule source metadata.
- [ ] Ensure selection overlays remain clipped to the cover itself.
- [ ] Run `:coreui:testDebugUnitTest`.

### Task 3: Shared search and action controls

**Files:**
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/MyButton.kt`
- Modify: `coreui/src/main/java/my/noveldokusha/coreui/components/TopAppBarSearch.kt`

**Interfaces:**
- Consumes: `ExpressiveShapes.button`, `ExpressiveShapes.pill`, and `ExpressiveShapes.dropdown`.
- Preserves: public function signatures and explicit button shape overrides.

- [ ] Use spring motion for the reusable button's selected shape morph.
- [ ] Use the shared pill geometry for search fields and the shared dropdown geometry for search menus.
- [ ] Run `:coreui:testDebugUnitTest`.

### Task 4: History composition

**Files:**
- Modify: `features/historyExplorer/src/main/java/my/noveldokusha/historyexplorer/HistoryScreen.kt`

**Interfaces:**
- Consumes: existing `HistoryUiState`, `HistoryItem`, `ExpressiveShapes`, and string resources.
- Preserves: delete, open, relative-time, and progress semantics.

- [ ] Refine history cards with larger covers, a subtle tonal outline, source/time pills, chapter context, and a capsule progress track.
- [ ] Preserve empty/missing metadata behavior and guard progress calculations.
- [ ] Run the relevant history tests and the core UI test suite.

### Task 5: Documentation and full verification

**Files:**
- Modify: `docs/EXPRESSIVE_DESIGN_SYSTEM.md`
- Create: this implementation plan and the matching design spec.

- [ ] Document the refined interaction and media patterns.
- [ ] Run `:coreui:testDebugUnitTest` and `:app:assembleDebug` through GitHub Actions.
- [ ] Inspect the resulting diff for accidental behavior changes and malformed imports/modifiers.
- [ ] Retrieve the CI APK artifact and verify the exact path and checksum before sharing it.
