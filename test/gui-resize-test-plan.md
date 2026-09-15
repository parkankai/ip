# GUI resize checks

Run the JavaFX application with Java 25 using `./gradlew run`.

1. At the default window size, check that the full banner stays inside the window and the input and Send button are visible.
2. Resize to 400 × 480. Check that the banner switches to its compact face, margins and avatars shrink, and the input and Send button remain usable.
3. Display a task with a long description, including a long word without spaces. Check that the message wraps inside its card and that all lines can be reached by scrolling.
4. Build up several exchanges, scroll to roughly the middle, and resize narrower, wider, shorter, and taller. Check that the scrollbar keeps approximately the same relative position instead of jumping to the latest reply.
5. Submit another command while viewing earlier messages. Check that the latest reply becomes visible.
6. Return to the top in a window at least 560px wide with at least 650px of content height. Check that the full banner returns and fits inside the window.

The console interface is unaffected; its regression cases remain in `test/ui-test-plan.md`.
