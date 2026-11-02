# Spacing & Layout Design

## Intent

Give NoveLA a deliberate, consistent spatial rhythm and make layouts adapt to available window width. The result should feel composed like a modern Material 3 Expressive app, not merely padded more generously.

## Goals

- Centralize an 4dp-based spacing scale: 4, 8, 12, 16, 20, 24, 32, 40, 48, and 64dp.
- Standardize page gutters: 16dp compact, 24dp medium, and 32dp expanded.
- Measure actual available window width and classify it as compact (<600dp), medium (600–839dp), or expanded (>=840dp).
- Provide maximum content widths of 600dp, 840dp, and 1200dp for compact, medium, and expanded layouts respectively.
- Keep book cover aspect ratios intact and give book grids a minimum cell width so columns respond to available width.
- Preserve the user's selected grid column count as the compact baseline; wider windows may add columns naturally, capped at six.
- Apply spacing and proportions to shared components first, then migrate screen composition according to each screen's purpose.
- Keep the reader text canvas and user-controlled reader font, text size, line spacing, and page margins independent of app-shell layout.

## Non-goals

- No new adaptive-layout dependency.
- No changes to library data, sorting, filtering, search, navigation, reading progression, or other feature behavior.
- No arbitrary fixed widths tied to device models.
- No user-facing spacing presets.
- No decorative card wrapping around every row.

## Approaches

1. Per-screen spacing literals: rejected because this preserves drift and makes responsive updates expensive.
2. Add a separate adaptive UI dependency: rejected because the existing Compose stack can measure window constraints and the requirement does not justify another dependency.
3. Centralized layout tokens and measured window classes, consumed by screen-specific composition: selected. The existing shared coreui module is the single source of truth, while screens retain distinct layouts.

## Architecture

ExpressiveTokens owns spacing, gutters, maximum content widths, grid minimums, and breakpoints. A LocalExpressiveWindowSizeClass is provided at the root of InternalTheme from measured Compose constraints, making the information available to main and secondary activities that use the shared theme. Shared components consume these values; feature screens use the same window class for meaningful composition changes such as multi-column content and side-by-side detail headers.

## Constraints

- Existing Shape System, Material color system, and typography remain unchanged.
- Do not change reader content typography or user reader-layout preferences.
- Keep the existing 2–6 grid preference. It is the compact baseline; adaptive widths may add columns, never exceeding six.
- Respect system bars, bottom navigation, keyboard insets, and touch targets.
- All screen-specific changes must preserve current actions and state handling.
- Verify with ./gradlew --no-daemon :coreui:testDebugUnitTest :app:assembleDebug.
