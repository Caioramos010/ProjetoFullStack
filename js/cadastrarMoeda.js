async function cadastrarMoeda(formData) {
    console.log("Iniciando cadastro de moeda...");
    console.log([...formData.entries()]); // Mostra todos os campos do FormData
    let options = {
        method: "POST",
        body: formData
    };
    try {
        const moedaJson = await fetch('http://localhost:8080/Colecionador/rest/coin/register', options);
        const moedaVO = await moedaJson.json();
        console.log("Resposta do servidor:", moedaVO);
        if (moedaVO.idMoeda != 0) {
            alert("Cadastro realizado com sucesso.");
            moeda = {};
            form.reset();
            principal.style.display = 'none';
            exibir.textContent = 'Mostrar';
            fileName.textContent = 'Nenhum arquivo selecionado.';
            buscarMoedas();
        } else {
            alert("Houve um problema no cadastro da moeda.");
        }
    } catch (error) {
        console.error("Erro ao cadastrar moeda:", error);
    }
}