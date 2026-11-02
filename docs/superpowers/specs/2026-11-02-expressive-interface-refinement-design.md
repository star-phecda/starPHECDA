# Expressive Interface Refinement Design

## Intent

Move NoveLA beyond a consistent set of rounded corners into a coherent, tactile Material 3 Expressive interface. Pixel Music and PixelPlayer are references for interaction quality, confident geometry, and tonal composition. NoveLA remains a reading product: book covers, discovery, chapters, and reading controls stay central rather than adopting a music-player layout.

## User-facing outcomes

- The floating navigation pill gives the active destination more room, showing its icon and label together while inactive destinations remain compact icon targets.
- Selection and press states use spring-driven geometry and scale feedback, not just a colour swap.
- Book tiles become intentional media surfaces: cleaner cover framing, legible gradient-backed titles, tactile press feedback, and capsule metadata strips.
- History entries give covers, chapter context, source, last-read time, and progress a clearer hierarchy.
- Search fields use the same capsule language as filters and navigation; dropdowns and buttons share the app's shape roles.
- Existing bottom sheets and dialogs retain their 36dp and 28dp silhouettes, with their current behavior and content preserved.

## Goals

1. Strengthen the expressive app shell and selected-navigation treatment.
2. Make reusable book media components feel tactile and visually deliberate.
3. Improve the hierarchy and density of history entries without changing history behavior.
4. Harmonize shared search and action controls with the same geometry and motion language.
5. Keep the visual system consistent across screens through shared components, not per-screen one-off styling.
6. Add focused tests for layout decisions that can be tested without a device.

## Non-goals

- No new dependencies, navigation architecture, or persistence settings.
- No change to existing colour palettes, typography roles, content data, search behavior, click targets, or app routes.
- No redesign of the reading canvas, manga/video surfaces, or chapter text.
- No music-player-specific controls or layouts.

## Approaches

1. **Shared-component refinement with targeted screen composition (selected).** Upgrade the navigation, book tile, button, and search primitives, then refine History's content composition. This gives multiple screens one consistent improvement while keeping the scope reviewable.
2. **Independent redesign of every screen.** Rejected because it would duplicate component logic, create visual drift, and risk disturbing mature feature flows.
3. **Add a new motion/UI dependency.** Rejected because Compose animation APIs already cover the needed spring and visibility behavior, and the app should not pay for a library to animate a few controls.

The user approved proceeding with the full refinement direction: shell/navigation, content components, interactive surfaces, motion, and screen-level polish.

## Design rules

- Keep the canonical corner scale at 4 / 8 / 12 / 20 / 28 / 36dp.
- Capsule controls use CircleShape.
- The app navigation is 68dp high in full mode and 56dp in compact mode. Its selected item has a target layout weight of 1.85; unselected and compact items use 1.0.
- The navigation target is 48dp high, uses a floating capsule container, and animates selection with a medium-bouncy spring.
- Book-cover press feedback scales to 0.985 and returns to 1.0 with a spring; the hit target and image crop remain unchanged.
- Standard history cards use the existing 20dp card role, a 64x96dp cover, tonal metadata pills, and a 4dp capsule progress indicator.
- The reusable button's selected shape morph uses spring motion. Explicit caller-supplied shapes remain respected.
- Search fields use the shared full-pill shape. Menus use the shared dropdown shape.
- Sheets and dialogs continue to use the existing shared sheet/dialog roles.

## Accessibility and performance

- Preserve selectable semantics and descriptive labels for every navigation destination.
- Keep minimum interaction targets at or above 48dp.
- Motion is limited to small transform/size changes and remains driven by Compose animation APIs; no continuous animation loops are introduced.
- Book imagery continues through the existing Coil/ImageView pipeline and existing prefetch/cache behavior.
- All layouts must remain usable with long labels and system font scaling.

## Verification

- Unit tests pin the selected-navigation weight contract and shared motion dimensions.
- CI must pass `:coreui:testDebugUnitTest` and `:app:assembleDebug`.
- A successful APK build proves compilation and packaging, not visual validation on every device. Physical-device review remains necessary for final visual acceptance.
