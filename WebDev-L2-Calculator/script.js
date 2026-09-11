
// ------------------------------------------------------
// Simple calculator logic - no eval() used anywhere.
// We keep track of:
//   firstOperand   - the number entered before the operator
//   operator       - the pending operator (+, −, ×, ÷)
//   currentInput   - what the user is typing right now (as a string)
//   justEvaluated  - true right after "=" was pressed
// ------------------------------------------------------

const expressionEl = document.getElementById("expression");
const currentEl = document.getElementById("current");

let firstOperand = null;
let operator = null;
let currentInput = "0";
let justEvaluated = false;

function updateDisplay() {
  currentEl.textContent = currentInput;

  if (operator && firstOperand !== null) {
    expressionEl.textContent = firstOperand + " " + operator;
  } else {
    expressionEl.textContent = "";
  }
}

function inputNumber(num) {
  // if we just showed a result, start fresh on next digit
  if (justEvaluated) {
    currentInput = "0";
    justEvaluated = false;
  }

  if (num === ".") {
    // don't allow more than one decimal point
    if (currentInput.includes(".")) return;
    currentInput += ".";
    updateDisplay();
    return;
  }

  if (currentInput === "0") {
    currentInput = num;
  } else {
    currentInput += num;
  }
  updateDisplay();
}

function calculate(a, op, b) {
  a = parseFloat(a);
  b = parseFloat(b);

  switch (op) {
    case "+":
      return a + b;
    case "−":
      return a - b;
    case "×":
      return a * b;
    case "÷":
      if (b === 0) {
        // division by zero guard
        return "Error";
      }
      return a / b;
    default:
      return b;
  }
}

function handleOperator(nextOperator) {
  // chaining support: if an operator is already pending, resolve it first
  if (operator !== null && !justEvaluated) {
    const result = calculate(firstOperand, operator, currentInput);

    if (result === "Error") {
      currentInput = "Error";
      firstOperand = null;
      operator = null;
      updateDisplay();
      return;
    }

    firstOperand = result;
  } else {
    firstOperand = parseFloat(currentInput);
  }

  operator = nextOperator;
  currentInput = "0";
  justEvaluated = false;
  updateDisplay();
}

function handleEquals() {
  if (operator === null || firstOperand === null) return;

  const result = calculate(firstOperand, operator, currentInput);

  if (result === "Error") {
    currentInput = "Error";
    expressionEl.textContent = "";
  } else {
    // trim off long floating point tails
    currentInput = String(Math.round(result * 1e10) / 1e10);
  }

  firstOperand = null;
  operator = null;
  justEvaluated = true;
  updateDisplay();
}

function handleClear() {
  firstOperand = null;
  operator = null;
  currentInput = "0";
  justEvaluated = false;
  updateDisplay();
}

function handleBackspace() {
  if (justEvaluated || currentInput === "Error") {
    // nothing sensible to delete from a finished result
    handleClear();
    return;
  }

  if (currentInput.length <= 1) {
    currentInput = "0";
  } else {
    currentInput = currentInput.slice(0, -1);
  }
  updateDisplay();
}

// ---- wire up event listeners (no inline onclick used) ----

document.querySelectorAll(".number").forEach((btn) => {
  btn.addEventListener("click", () => inputNumber(btn.dataset.num));
});

document.querySelectorAll(".operator").forEach((btn) => {
  btn.addEventListener("click", () => handleOperator(btn.dataset.op));
});

document.getElementById("equals").addEventListener("click", handleEquals);
document.getElementById("clear").addEventListener("click", handleClear);
document.getElementById("backspace").addEventListener("click", handleBackspace);

updateDisplay();