# Expressive Shape System Design

## Intent

Give starPHECDA one coherent Material 3 Expressive shape language. The geometry should make the app feel deliberately designed, not like a collection of unrelated Compose defaults and hand-picked corner values. Pixel Music and PixelPlayer are references for confident, tactile geometry; Rhythm is a reference for expressive shape variation. The result must still feel like a novel library and reader, not a music-player reskin.

## Goals

- Replace overlapping shape scales with one canonical token scale.
- Assign geometry by component role: media, cards, grouped containers, pills, buttons, sheets, dialogs, and text fields.
- Make Material 3 components inherit the shared scale wherever their defaults are appropriate.
- Remove local corner literals from the high-impact components included in this milestone.
- Preserve existing layout, behavior, color roles, typography, reader settings, and navigation motion.
- Add tests that lock the token values and component-to-token mapping.

## Non-goals

- No shape customization settings, saved presets, or preference migrations in this milestone.
- No broad layout redesign, new cards around every row, or card-in-card decoration.
- No change to chapter-reader typography, font controls, or reading layout.
- No replacement of existing Material 3 components or addition of dependencies.

## Approaches

1. **One expressive house style with a central scale (selected for this milestone).** All components use shared role-based shapes. This gives the app a consistent identity and keeps the theme easy to reason about. The tokens remain centralized so presets can be layered on later.
2. **User-selectable shape presets now.** This would expose Subtle / Expressive / Extra Round settings and persist a selected preset. It adds preferences, UI, migration and testing scope before the base shape language is stable, so it is deferred.
3. **One large radius everywhere.** This is simple, but it destroys hierarchy between cards, controls, sheets and dialog surfaces. Rejected.

The user approved the expressive shape direction. Choosing a fixed house style rather than adding presets is the implementation call for this milestone.

## Shape scale

Use these canonical dimensions:

| Token | Value | Intended use |
|---|---:|---|
| extraSmall | 4dp | compact decorative details |
| small | 8dp | compact Material control role |
| medium | 12dp | fields and small nested controls |
| large | 20dp | buttons, standard cards, grouped containers and media |
| extraLarge | 28dp | featured cards and dialogs |
| extraLargeIncreased | 36dp | large sheets and selected-button morph |
| full | CircleShape | chips, navigation pills and capsule controls |

Map MaterialTheme.shapes to extraSmall=4dp, small=8dp, medium=12dp, large=20dp, and extraLarge=28dp. The 36dp and full roles are explicit expressive component shapes rather than another MaterialTheme.shapes slot.

## Component roles

- **Cards:** standard information and book-media cards use 20dp; featured/hero surfaces may use 28dp. Material 3 Card defaults inherit the theme scale. Avoid wrapping every row in an extra card.
- **Containers:** grouped filter and control areas use 20dp. Nested elements should use the smaller 12dp role or no additional surface; use tonal hierarchy and dividers instead of nested cards.
- **Pills:** chips and floating navigation use CircleShape, not a fixed 36dp radius. This keeps capsule geometry correct at different control heights.
- **Buttons:** the reusable app button uses 20dp at rest. When its selected state changes, its default geometry animates toward the 36dp role. Explicit caller-supplied shapes remain untouched.
- **Sheets:** modal bottom sheets use 36dp top-start and top-end corners. Content and sheet behavior stay unchanged.
- **Dialogs:** dialog surfaces use 28dp. Text fields inside dialogs use the 12dp field role.
- **Text fields and compact option surfaces:** use 12dp unless the component is intentionally a pill.

## Implementation boundaries

- `coreui/theme/ExpressiveTokens.kt` owns the canonical dp dimensions alongside existing navigation and motion tokens.
- `coreui/theme/Shape.kt` maps Material shape roles and exposes named component shapes.
- Shared controls and the existing library, chapter, source-filter and settings surfaces consume those shapes.
- `docs/EXPRESSIVE_DESIGN_SYSTEM.md` records the final system.
- Unit tests verify the token values and key shape mappings.

## Accessibility and behavior

Shape is supplementary to color, text and interaction state. Selected controls must continue to expose their existing selected semantics and content colors. The selected-button shape transition must not change hit targets, minimum control height, click behavior or explicit caller overrides. Existing light, dark and AMOLED themes share the same geometry.
