# Expressive design system

The app shell uses a shared Material 3 design language inspired by Pixel Music's expressive implementation. It is adapted to a novel reader rather than copied as a music-player skin.

## Global foundation

- Material 3 uses the stable 1.4.0 line and the September 2026 Compose BOM.
- `coreui/theme/Shape.kt` defines the shared 4 / 8 / 16 / 24 / 32dp shape scale.
- `coreui/theme/Typography.kt` provides the app's typographic identity: variable Manrope for interface roles and Literata for display roles.
- The type scale defines all 15 Material 3 roles with explicit sizes, line heights, weight contrast, and restrained tracking. The scale uses `sp` so Android font scaling remains active.
- The chapter reader's font picker, imported fonts, saved font preference, text size, and line-spacing controls remain independent of this app interface type system.
- `coreui/theme/ExpressiveTokens.kt` centralizes navigation geometry and motion durations.
- Existing Monet and named colour themes remain available.

## Typography

Manrope is the primary interface face for headlines, titles, body copy, labels, navigation, and settings. Its variable weight axis provides a consistent range without bundling separate static files.

Literata is reserved for Material 3 display roles. Its weight and optical-size axes give major display headings a literary character while keeping everyday UI text crisp and readable. Small metadata and controls are not switched to serif.

The official font assets and their SIL Open Font License notices are included in the repository at `coreui/src/main/res/font/` and `docs/licenses/` respectively. Chapter text continues through the reader's existing font resolver and user preferences.

## Navigation

`ExpressiveNavigationBar` is the app-shell floating navigation component. It uses a tonal selection pill, always-visible labels, animated colour and a medium-bouncy icon scale. A compact mode is available for constrained layouts.

## Artwork-derived colour

`extractArtworkSeedColor(bitmap)` samples a cover at up to 128px and selects a representative HCT seed using Google's Material colour utilities. Call it off the main thread. Pass the resulting seed to `ArtworkTheme(seedColor)` around a contextual surface or screen.

Neutral artwork uses a monochrome scheme. Error roles are retained from the parent theme, while the artwork palette supplies the accent, tonal containers and surface hierarchy. The wrapper inherits the parent's typography and shapes.

## Motion

The navigation indicator and icon use explicit spring animations. This avoids requiring a beta Material 3 motion API while preserving the responsive spring behaviour that defines the expressive interaction style.