## Context

Initial project scaffold for 42 Classici Senza Tempo. A LibGDX multi-module project targeting WASM via TeaVM and desktop via LWJGL3. All rendering is geometric (ShapeRenderer) — no textures, bitmaps, or sprites. Multiplayer is local hot-seat only.

## Goals / Non-Goals

**Goals:**
- Buildable Gradle project with core, lwjgl3, and teavm modules
- Game interface as the central abstraction for all 42 games
- Menu screen displaying registered games as a grid
- Play screen wrapping any Game instance and routing input
- Player + HotSeatManager for turn-based local multiplayer
- Playable Tic-Tac-Toe with win/draw detection
- Desktop dev via LWJGL3
- WASM web deployment via TeaVM

**Non-Goals:**
- Online multiplayer
- Sprite/texture-based rendering
- 3D rendering
- Gamepad support
- Sound/audio
- Any game beyond Tic-Tac-Toe

## Decisions

- **LibGDX 1.14.1**: Stable, mature, TeaVM-compatible via gdx-teavm backend
- **TeaVM over GWT**: WASM output, better performance, actively maintained gdx-teavm bridge
- **ShapeRenderer for all visuals**: Enforces the geometric constraint, no texture imports possible
- **Game interface with lifecycle**: Consistent contract for all 42 games — init, update, render, input, multiplayer
- **HotSeatManager over raw index**: Encapsulates turn logic, player list management, supports future extension (score tracking, pass-device verification)
- **MenuScreen as grid of rectangles**: Simple, touch-friendly, scales with game count

## Risks / Trade-offs

- TeaVM build pipeline not yet fully verified — may need adjustments to build.gradle
- Core module depends on gdx-backend-lwjgl3 unnecessarily — should be in lwjgl3 module only
- ShapeRenderer lacks anti-aliasing — geometric shapes may look jagged at large sizes
- No viewport/camera system — games recalculate layout each frame from raw pixel dimensions
