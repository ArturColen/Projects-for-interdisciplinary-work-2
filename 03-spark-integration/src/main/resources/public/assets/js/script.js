function applyPhoneMask(event) {
    let input = event.target;
    let value = input.value;

    value = value.replace(/\D/g, '');

    if (value.length > 11) {
        value = value.slice(0, 11);
    }

    if (value.length === 0) {
        input.value = '';
    } else if (value.length <= 2) {
        input.value = `(${value}`;
    } else if (value.length <= 6) {
        input.value = `(${value.slice(0, 2)}) ${value.slice(2)}`;
    } else if (value.length <= 10) {
        input.value = `(${value.slice(0, 2)}) ${value.slice(2, 6)}-${value.slice(6)}`;
    } else {
        input.value = `(${value.slice(0, 2)}) ${value.slice(2, 7)}-${value.slice(7)}`;
    }
}

function applyNameMask(event) {
    let input = event.target;
    let value = input.value;

    value = value.toLowerCase();
    value = value.replace(/[^a-zA-Z\s]/g, '');
    value = value.replace(/\b\w/g, (char) => char.toUpperCase());

    input.value = value;
}

function initializeMasks() {
    const phoneField = document.getElementById('phone');
    phoneField.addEventListener('input', applyPhoneMask);

    const nameField = document.getElementById('name');
    nameField.addEventListener('input', applyNameMask);
}

document.addEventListener('DOMContentLoaded', initializeMasks);
