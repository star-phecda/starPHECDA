# Expressive Typography System Design

## Intent

Give starPHECDA a recognizable typographic identity instead of inheriting the device's generic sans-serif. This applies to the application interface: navigation, library, discovery, history, cards, metadata, and settings. Chapter reading remains governed by its existing independent font and reading preferences.

## Goals

- Use Manrope as the primary interface typeface, with its variable weight axis for a deliberate hierarchy.
- Use Literata as a restrained editorial display face for Material 3 display roles and prominent title moments.
- Define all 15 Material 3 typography roles with one coherent scale, explicit line heights, weights, and letter spacing.
- Preserve Android font scaling and the current minimum SDK (26).
- Keep the reader's existing font picker, imported fonts, saved font preference, size, and spacing behavior unchanged.
- Keep the existing application ID and increment versionCode/versionName for the new build.

## Non-goals

- No new reader typography settings or changes to chapter rendering.
- No global serif treatment for body copy or small controls.
- No per-screen redesign or arbitrary font overrides.
- No changes to theme colours, navigation geometry, or shape tokens.

## Type scale

| Role group | Sizes (sp) | Line heights (sp) |
|---|---:|---:|
| Display Large / Medium / Small | 40 / 32 / 28 | 48 / 40 / 36 |
| Headline Large / Medium / Small | 28 / 24 / 20 | 36 / 32 / 28 |
| Title Large / Medium / Small | 20 / 16 / 14 | 26 / 22 / 20 |
| Body Large / Medium / Small | 16 / 14 / 12 | 24 / 20 / 16 |
| Label Large / Medium / Small | 14 / 12 / 11 | 20 / 16 / 16 |

Display roles use Literata with optical sizing appropriate to their nominal display size. Headline, title, body, and label roles use Manrope. Weight contrast and modest tracking distinguish levels without turning every element into a headline.

## Approaches considered

1. **Manrope for every role:** coherent and lightweight, but does not create the editorial display character appropriate to a novel app.
2. **Manrope interface + Literata display:** selected. It establishes a clear identity while keeping utility text crisp and lets display headings feel literary without imposing serif text on the interface.
3. **Different fonts per screen:** rejected because it would fragment the visual language and make the global theme difficult to maintain.

## Architecture and constraints

- Fonts live in the :coreui Android library so the root Material 3 theme owns them.
- Use official Google Fonts variable TTF assets and retain their SIL Open Font License notices in the repository.
- The project minimum SDK is 26, which supports variable fonts.
- InternalTheme remains the single global Compose typography entry point.
- ReaderTheme continues inheriting the current theme typography for reader chrome; chapter text remains rendered using the existing reader-specific font resolver and preferences. This milestone must not touch the reader font loader or persisted reader settings.
- Maintain existing applicationId = my.novela; increase versionCode from 39 to 40 and versionName from 1.6.1 to 1.6.2. Signing continuity is a separate release concern and is not changed here.

## Verification

- Unit tests assert that every Material 3 role uses the intended app font family and that role sizes/line heights match the scale.
- Existing core UI tests continue to pass.
- Android CI builds the debug APK from the committed branch head.