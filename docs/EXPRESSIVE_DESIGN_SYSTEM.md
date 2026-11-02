# Expressive design system

The app shell uses a shared Material 3 design language inspired by Pixel Music's expressive implementation. It is adapted to a novel reader rather than copied as a music-player skin.

## Global foundation

- Material 3 uses the stable 1.4.0 line and the September 2026 Compose BOM.
- `coreui/theme/Shape.kt` defines the shared expressive 4 / 8 / 12 / 20 / 28 / 36dp corner scale and named component shape roles. Full pills use `CircleShape` so their ends remain fully rounded at every height.
- `coreui/theme/Typography.kt` provides the app's typographic identity: variable Manrope for interface roles and Literata for display roles.
- The type scale defines all 15 Material 3 roles with explicit sizes, line heights, weight contrast, and restrained tracking. The scale uses `sp` so Android font scaling remains active.
- The chapter reader's font picker, imported fonts, saved font preference, text size, and line-spacing controls remain independent of this app interface type system.
- `coreui/theme/ExpressiveTokens.kt` centralizes navigation geometry and motion durations.
- Existing Monet and named colour themes remain available.

## Typography

Manrope is the primary interface face for headlines, titles, body copy, labels, navigation, and settings. Its variable weight axis provides a consistent range without bundling separate static files.

Literata is reserved for Material 3 display roles. Its weight and optical-size axes give major display headings a literary character while keeping everyday UI text crisp and readable. Small metadata and controls are not switched to serif.

The official font assets and their SIL Open Font License notices are included in the repository at `coreui/src/main/res/font/` and `docs/licenses/` respectively. Chapter text continues through the reader's existing font resolver and user preferences.

The next application build is version 1.6.2 (versionCode 40). The application ID remains `my.novela`; the debug build retains its existing `.debug` application ID suffix.

## Navigation

`ExpressiveNavigationBar` is the app-shell floating navigation component. It uses a tonal selection pill, always-visible labels, animated colour and a medium-bouncy icon scale. A compact mode is available for constrained layouts.

## Artwork-derived colour

`extractArtworkSeedColor(bitmap)` samples a cover at up to 128px and selects a representative HCT seed using Google's Material colour utilities. Call it off the main thread. Pass the resulting seed to `ArtworkTheme(seedColor)` around a contextual surface or screen.

Neutral artwork uses a monochrome scheme. Error roles are retained from the parent theme, while the artwork palette supplies the accent, tonal containers and surface hierarchy. The wrapper inherits the parent's typography and shapes.

## Motion

The navigation indicator and icon use explicit spring animations. This avoids requiring a beta Material 3 motion API while preserving the responsive spring behaviour that defines the expressive interaction style.

## Shape system

The app uses a fixed expressive house style. Shape tokens are centralized for consistency; user-selectable radius presets are intentionally deferred until the base language has settled.

The corner scale is 4 / 8 / 12 / 20 / 28 / 36dp. Material shape roles map to 4 / 8 / 12 / 20 / 28dp. The 36dp role is reserved for expanded sheet corners and the selected state of the shared app button. Capsule controls use CircleShape, not a fixed dp radius.

- Standard cards, book covers and grouped containers use 20dp. Featured cards and dialogs use 28dp.
- Nested controls and text fields use 12dp; compact metadata containers use 8dp.
- Category/filter chips and floating navigation use full capsule geometry.
- The shared app button uses 20dp at rest and animates to a 36dp corner when selected. Explicit caller-supplied shapes remain unchanged.
- Modal sheets use 36dp top corners. Dialogs use 28dp corners.
- Grouped surfaces use tonal hierarchy and spacing. Avoid wrapping every row in a card or nesting decorative cards inside cards.

## Shape coverage

The shape system covers primary navigation destinations and the secondary feature flows reached from them.

- **Library and History:** book covers, rounded history entries, category filters, book actions, progress indicators, menus, and dialogs.
- **Finder and discovery:** capsule tabs and filters, catalog/source rows, global search results, database search modes, result filters, and source filter sheets.
- **Novel and chapter details:** the chapter/novel information header, genre and category chips, chapter rows, selection bars, export/category dialogs, database novel information cards, and related-book actions.
- **Reader:** the reading screen chrome, chapter progress surfaces, reader-style and translation settings, voice controls, manga-reader settings, TTS mini-player, manual-highlight controls, and video settings panels. The text and manga image canvases remain content surfaces rather than being wrapped in decorative cards.
- **Settings and extensions:** grouped settings sections, theme/language choices, text fields, buttons, menus, extension cards, repository/editor dialogs, and regex-cleanup tools.
- **Migration and web tools:** migration tabs, source/book pickers, result cards, options/history dialogs, and the WebView URL field.

Shared buttons, list rows, book-image components, options, filters, inputs, sheets, and dialogs consume named roles from ExpressiveShapes. New screens should use these roles instead of adding local corner literals.
