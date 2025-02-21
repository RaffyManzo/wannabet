document.addEventListener('DOMContentLoaded', function() {
    // Seleziona il form per il login tramite il suo id
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(e) {
        // Impedisci il comportamento predefinito del form (invio tradizionale)
        e.preventDefault();

        // Ottieni i valori dai campi del form
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Crea l'oggetto JSON con i dati del form
        const payload = {
            email: email,
            password: password
        };

        // Esegui la chiamata fetch per inviare i dati come JSON
        fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "/home"; // Reindirizzamento manuale
                    console.log(response)
                } else {
                    console.error('Login fallito');
                }
            })
            .catch(error => {
                console.error('Errore durante il login:', error);
            });
    });
});