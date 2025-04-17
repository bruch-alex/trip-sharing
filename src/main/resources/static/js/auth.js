<!--Password checking script-->

document.addEventListener('DOMContentLoaded', function () {
    // Password check
    const registrationForm = document.getElementById('registrationForm');
    if (registrationForm) {
        registrationForm.addEventListener('input', function () {
            const password = document.getElementById('registrationPassword').value;
            const confirmPassword = document.getElementById('registrationConfirmPassword').value;
            const error = document.getElementById('errorMessage');
            const button = document.getElementById('submitButton');

            if (password.length < 8) {
                error.textContent = "Password must be at least 8 characters long.";
                error.style.display = 'block';
                button.disabled = true;
            } else if (password !== confirmPassword) {
                error.textContent = "Passwords do not match!";
                error.style.display = 'block';
                button.disabled = true;
            } else {
                error.style.display = 'none';
                button.disabled = false;
            }
        })
    }


    const toggleBtn = document.getElementById('toggleFormButton');
    if (toggleBtn) {
        toggleBtn.addEventListener('click', function () {
            const loginForm = document.getElementById('login-form');
            const registerForm = document.getElementById('register-form');

            const isLoginHidden = window.getComputedStyle(loginForm).display === 'none';

            if (isLoginHidden) {
                // Show Login and hide Register
                loginForm.style.visibility = 'visible';
                registerForm.style.visibility = 'hidden';
                toggleBtn.textContent = "login"
            } else {
                // Show Register and hide Login
                loginForm.style.visibility = 'hidden';
                registerForm.style.visibility = 'visible';
                toggleBtn.textContent = "register"
            }
        })
    }
})