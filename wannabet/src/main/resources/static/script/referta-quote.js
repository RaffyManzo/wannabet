document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('refertaModal');
    const closeModal = document.querySelector('.modal .close');
    const btns = document.querySelectorAll('.btn-referta');
    const form = document.getElementById('refertaForm');
    const modalIdInput = document.getElementById('modalIdQuota');

    // Imposta il comportamento per ogni pulsante "Referta"
    btns.forEach(function(btn) {
        btn.addEventListener('click', function() {
            const idQuota = btn.getAttribute('data-id');
            modalIdInput.value = idQuota;
            // Imposta direttamente l'URL con il valore numerico
            form.action = '/referta-quote/' + idQuota;
            modal.style.display = 'block';
        });
    });


    // Chiude il modal al click sulla "X"
    closeModal.addEventListener('click', function() {
        modal.style.display = 'none';
        // Ripristina l'action originale se necessario
        form.action = form.action.replace(/[0-9]+$/, '__id__');
    });

    // Chiude il modal se si clicca fuori dal contenuto
    window.addEventListener('click', function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
            form.action = form.action.replace(/[0-9]+$/, '__id__');
        }
    });
});
