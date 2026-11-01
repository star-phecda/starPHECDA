# Expressive design system

The app shell now uses a shared Material 3 design language inspired by Pixel Music's expressive
implementation. The implementation is intentionally adapted to a novel reader rather than copied
as a music-player skin.

## Global foundation

- Material 3 uses the stable 1.4.0 line and the September 2026 Compose BOM.
- `coreui/theme/Shape.kt` defines the shared 4 / 8 / 16 / 24 / 32dp shape scale.
- `coreui/theme/Typography.kt` defines an expressive interface type scale. The chapter reader's
  own font and line-height preferences remain independent.
- `coreui/theme/ExpressiveTokens.kt` centralizes navigation geometry and motion durations.
- Existing Monet and named colour themes remain available.

## Navigation

`ExpressiveNavigationBar` is the app-shell floating navigation component. It uses a tonal selection
pill, always-visible labels, animated colour and a medium-bouncy icon scale. A compact mode is
available for constrained layouts.

## Artwork-derived colour

`extractArtworkSeedColor(bitmap)` samples a cover at up to 128px and selects a representative HCT
seed using Google's Material colour utilities. Call it off the main thread. Pass the resulting seed
to `ArtworkTheme(seedColor)` around a contextual surface or screen.

Neutral artwork uses a monochrome scheme. Error roles are retained from the parent theme, while the
artwork palette supplies the accent, tonal containers and surface hierarchy. The wrapper inherits
the parent's typography and shapes.

## Motion

The navigation indicator and icon use explicit spring animations. This avoids requiring a beta
Material 3 motion API while preserving the responsive spring behaviour that defines the expressive
interaction style.
