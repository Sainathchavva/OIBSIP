# Vanilla JavaScript Calculator

A simple browser-based calculator built with plain HTML, CSS, and JavaScript
(no frameworks, no libraries).

## Tech Stack
- HTML5
- CSS3 (Grid layout for the buttons)
- JavaScript (Vanilla — no `eval()` used)

## Files
| File         | Purpose                                      |
|--------------|-----------------------------------------------|
| `index.html` | Page structure / button markup                |
| `style.css`  | Layout and styling (dark theme, CSS Grid)      |
| `script.js`  | Calculator logic and event listeners           |

## Features
- Display screen showing the running expression and current input/result
- Digit buttons (0–9) and a decimal point button
- Operator buttons: `+`, `−`, `×`, `÷`
- `=` button to evaluate the expression
- `C` button to clear/reset
- `⌫` backspace button to delete the last character
- Division-by-zero protection (shows `Error` instead of crashing)
- Operator chaining — e.g. entering `5 + 3 ×` resolves the pending `+`
  before applying `×`, so you don't need to press `=` between every step
- Layout built with CSS Grid (`display: grid` on `.buttons`)
- All buttons wired up with `addEventListener` — no inline `onclick`
  attributes in the HTML

## How to Run
1. Download/clone all three files (`index.html`, `style.css`, `script.js`)
   into the same folder.
2. Open `index.html` in any modern browser (Chrome, Firefox, Edge, Safari).
3. No build step, no server, no dependencies required.

## How It Works (quick overview)
- `currentInput` holds the number currently being typed, as a string.
- `firstOperand` and `operator` hold the pending calculation once an
  operator button is pressed.
- Pressing another operator before `=` resolves the pending calculation
  first, which is what enables chaining (e.g. `5 + 3 × 2`).
- `calculate(a, op, b)` does the actual math using a `switch` statement —
  numbers are converted with `parseFloat()` before any arithmetic.
- Dividing by zero returns the string `"Error"` instead of `Infinity`,
  and the calculator resets cleanly on the next input.
- `justEvaluated` tracks whether `=` was just pressed, so the next digit
  typed starts a fresh number instead of appending to the old result.

## Possible Extensions
- Keyboard support (typing digits/operators instead of clicking)
- Calculation history log
- Percentage (`%`) and sign-toggle (`±`) buttons
- Unit tests for the `calculate()` function

## Author's Notes
Built as a practice project to work with `addEventListener`, `switch`
statements, and `parseFloat()` without relying on `eval()`.