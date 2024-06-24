function toggleView(showArticle) {
    const header = document.querySelector('header');
    const articleContent = document.getElementById('articleContent');
    const crearButton = document.querySelector('crear-button').shadowRoot.getElementById('crearButton');

    if (showArticle) {
        articleContent.style.display = 'block';
        header.style.display = 'none';
        document.body.classList.remove('full-header');
        crearButton.textContent = 'Buscar documento';
    } else {
        articleContent.style.display = 'none';
        header.style.display = 'flex';
        document.body.classList.add('full-header');
        crearButton.textContent = 'Crear documento';
    }
}

export { toggleView };
