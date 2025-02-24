document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const payload = { email: email, password: password };

        fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                console.log("🔍 Risposta dal server:", response);
                return response.text();
            })
            .then(text => {
                console.log("🔍 Contenuto della risposta:", text);

                // Ora proviamo a convertire manualmente in JSON solo se è valido
                try {
                    const data = JSON.parse(text);
                    console.log("✅ JSON ricevuto:", data);
                    window.location.href = data.redirectUrl;
                } catch (error) {
                    console.error("ERRORE: La risposta non è un JSON valido!", error);
                }
            })
            .catch(error => {
                console.error('Errore durante il login:', error);
            });
    });
});
