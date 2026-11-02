# starPHECDA Expressive Color System

## Global theme architecture

The app has one global Material 3 theme, selected by the existing persisted APP_THEME and
THEME_DARK_MODE preferences. DEFAULT continues to use Android Monet dynamic color where
available and the existing Tachiyomi fallback on older Android versions. All named built-in
palettes remain available.

The root theme resolves a complete Material 3 ColorScheme and applies two shared passes:

- withExpressiveSurfaceHierarchy creates a coherent surface ladder from the selected palette's
  surface tint. It sets surfaceDim, surfaceBright, and all five surfaceContainer roles.
- withAccessibleContentRoles keeps foreground roles that already meet WCAG AA contrast and
  minimally adjusts roles that do not. Text-oriented foreground pairs target 4.5:1. Translucent
  container roles are evaluated after compositing over the palette surface.

## Artwork color

Artwork uses the existing HCT and Material Color Utilities extraction path. Expressive covers use
an Expressive dynamic scheme; neutral covers use Monochrome. The resulting scheme passes through
the same surface and foreground-role normalization as the global theme.

The chapter/book header opts into ArtworkTheme using its loaded cover bitmap. That palette is
scoped to the book header, not the root application and not the reader. Seeds are cached in a
bounded 96-entry in-memory LRU so revisiting a book does not requantize its cover every time.
Only the cover request that needs sampling disables hardware bitmaps; other image requests retain
their existing loader behavior.

## Reader isolation

ReaderTheme, its color configuration, and its saved reader preferences are intentionally
unchanged. App palette changes and book artwork palettes do not overwrite reader-specific
backgrounds or text colors.

## Theme previews

The theme picker previews each preset's actual primary, secondary, and tertiary roles in the
currently resolved light/dark mode. It no longer uses a separate list of hardcoded swatch colors.

## Build artifact

Android CI runs the core UI unit tests and assembles the debug APK. The workflow uploads
app/build/outputs/apk/debug/*.apk as the starPHECDA-debug-apk artifact for 14 days.
