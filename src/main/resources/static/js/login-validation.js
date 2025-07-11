document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    const emailError = document.getElementById('emailError');
    const senhaError = document.getElementById('senhaError');

    // Validação em tempo real
    emailInput.addEventListener('input', validateEmail);
    senhaInput.addEventListener('input', validatePassword);

    // Validação no submit
    form.addEventListener('submit', (event) => {
        const isEmailValid = validateEmail();
        const isPasswordValid = validatePassword();
        
        if (!isEmailValid || !isPasswordValid) {
            event.preventDefault();
        }
    });

    function validateEmail() {
        const email = emailInput.value.trim();
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        
        if (!regex.test(email)) {
            emailError.textContent = 'Por favor, insira um email válido';
            emailInput.classList.add('is-invalid');
            return false;
        }
        
        emailError.textContent = '';
        emailInput.classList.remove('is-invalid');
        return true;
    }

    function validatePassword() {
        const senha = senhaInput.value;
        
        if (senha.length < 8) {
            senhaError.textContent = 'A senha deve ter no mínimo 8 caracteres';
            senhaInput.classList.add('is-invalid');
            return false;
        }
        
        senhaError.textContent = '';
        senhaInput.classList.remove('is-invalid');
        return true;
    }
});